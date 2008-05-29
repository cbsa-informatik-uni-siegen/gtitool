package de.unisiegen.gtitool.ui.utils;


import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.jgraph.graph.GraphConstants;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.redoundo.MultiItem;
import de.unisiegen.gtitool.ui.redoundo.RedoUndoHandler;
import de.unisiegen.gtitool.ui.redoundo.StateMovedItem;


/**
 * This class manages the layout of the graph.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public final class LayoutManager
{

  /**
   * The already fixed nodes.
   */
  private ArrayList < DefaultStateView > fixedNodes = new ArrayList < DefaultStateView > ();


  /**
   * List of all {@link DefaultStateView} groups.
   */
  private ArrayList < ArrayList < DefaultStateView >> groups = new ArrayList < ArrayList < DefaultStateView > > ();


  /**
   * The actual min costs.
   */
  private int minCosts = Integer.MAX_VALUE;


  /**
   * The {@link DefaultMachineModel}.
   */
  private DefaultMachineModel model;


  /**
   * The actual first node to swap.
   */
  private DefaultStateView swapNode0;


  /**
   * The actual second node to swap.
   */
  private DefaultStateView swapNode1;


  /**
   * The {@link RedoUndoHandler}.
   */
  private RedoUndoHandler redoUndoHandler;


  /**
   * The {@link MultiItem} for redo/undo.
   */
  private MultiItem item;


  /**
   * Allocates a new {@link LayoutManager}.
   * 
   * @param model The {@link DefaultMachineModel}.
   * @param redoUndoHandler The {@link RedoUndoHandler}.
   */
  public LayoutManager ( DefaultMachineModel model,
      RedoUndoHandler redoUndoHandler )
  {
    this.model = model;
    this.redoUndoHandler = redoUndoHandler;
  }


  /**
   * Calculates the cuts of the graph.
   * 
   * @return The count of the cuts.
   */
  private final int calculateAllCuts ()
  {
    int count = 0;
    for ( int i = 0 ; i < this.groups.size () ; i++ )
    {
      count += calculateCuts ( i );
    }
    return count;
  }


  /**
   * Calculates the cuts between the given index and the groups below.
   * 
   * @param index The index of the given group.
   * @return The count of cuts.
   */
  private final int calculateCuts ( final int index )
  {
    int count = 0;

    for ( int i = 0 ; i <= index ; i++ )
    {
      for ( DefaultStateView current : this.groups.get ( i ) )
      {

        for ( Transition transition : current.getState ().getTransitionBegin () )
        {
          if ( isCut ( transition.getStateEnd (), index ) )
          {
            count++ ;
          }
        }
        for ( Transition transition : current.getState ().getTransitionEnd () )
        {
          if ( isCut ( transition.getStateBegin (), index ) )
          {
            count++ ;
          }
        }
      }
    }

    return count;
  }


  /**
   * Layouts the states into a grid to perform next layout steps.
   * 
   * @param states List with all states of the machine.
   */
  private final void createGrid ( ArrayList < DefaultStateView > states )
  {
    int xPosition = 100;
    int yPosition = 100;

    int xStartPosition = 50;
    int xSpace = 100;

    if ( states.size () > 0 )
    {
      Rectangle2D rect = GraphConstants.getBounds ( states.get ( 0 )
          .getAttributes () );
      xSpace = ( int ) rect.getWidth () + 50;
    }

    ArrayList < DefaultStateView > group = new ArrayList < DefaultStateView > ();

    int rowSize = ( int ) Math.ceil ( Math.sqrt ( this.model
        .getStateViewList ().size () ) );

    rowSize *= 2;

    int count = 0;
    int pos = 0;
    for ( DefaultStateView current : states )
    {
      if ( count != 0 )
      {
        if ( ( count % rowSize ) == 0 )
        {
          this.groups.add ( group );
          pos = 0;
          group = new ArrayList < DefaultStateView > ();
          xPosition = xStartPosition;
          yPosition += 200;
        }
        else
        {
          xPosition += xSpace;
        }
      }
      group.add ( current );
      if ( this.item != null )
      {
        if ( ( pos % 2 ) != 0 )
        {
          if ( ( current.getPositionX () != xPosition )
              || ( current.getPositionY () != yPosition + 50 ) )
          {
            this.item.addItem ( new StateMovedItem ( this.model, current,
                current.getPositionX (), current.getPositionY (), xPosition,
                yPosition + 50 ) );
            current.move ( xPosition, yPosition + 50 );
          }
        }
        else
        {
          if ( ( current.getPositionX () != xPosition )
              || ( current.getPositionY () != yPosition - 50 ) )
          {
            this.item.addItem ( new StateMovedItem ( this.model, current,
                current.getPositionX (), current.getPositionY (), xPosition,
                yPosition - 50 ) );
            current.move ( xPosition, yPosition - 50 );
          }
        }
      }
      count++ ;
      pos++ ;
    }
    this.groups.add ( group );
  }


  /**
   * Performs the graph layout.
   */
  public final void doLayout ()
  {
    prelayout ();

    doLayoutInternal ();

    finishLayout ();

    this.model.getGraphModel ().cellsChanged (
        this.model.getStateViewList ().toArray () );
  }


  /**
   * Performs the internal layout action.
   */
  private final void doLayoutInternal ()
  {
    for ( int index = 0 ; index < this.groups.size () - 1 ; index++ )
    {
      while ( !this.fixedNodes.containsAll ( this.groups.get ( index + 1 ) ) )
      {
        findNodesToSwap ( index );
        if ( this.swapNode0 == null )
        {
          continue;
        }
        if ( calculateAllCuts () > this.minCosts )
        {
          swap ( index, this.swapNode0, index + 1, this.swapNode1 );
        }
        this.fixedNodes.add ( this.swapNode0 );
        this.fixedNodes.add ( this.swapNode1 );
        this.minCosts = Integer.MAX_VALUE;
        this.swapNode0 = null;
        this.swapNode1 = null;
      }
      this.fixedNodes.clear ();
    }
  }


  /**
   * Finds the best nodes to swap.
   * 
   * @param index The index of the actual group.
   */
  private final void findNodesToSwap ( int index )
  {
    ArrayList < DefaultStateView > group0 = this.groups.get ( index );
    ArrayList < DefaultStateView > group1 = this.groups.get ( index + 1 );

    for ( int i = 0 ; i < group0.size () ; i++ )
    {
      DefaultStateView node0 = group0.get ( i );
      if ( this.fixedNodes.contains ( node0 ) )
      {
        continue;
      }
      for ( int j = 0 ; j < group1.size () ; j++ )
      {

        DefaultStateView node1 = group1.get ( j );

        if ( this.fixedNodes.contains ( node1 ) )
        {
          continue;
        }

        swap ( index, node0, index + 1, node1 );
        int costs = calculateAllCuts ();
        if ( costs < this.minCosts )
        {
          this.minCosts = costs;
          this.swapNode0 = node0;
          this.swapNode1 = node1;
        }
        swap ( index, node1, index + 1, node0 );
      }
    }
  }


  /**
   * Layouts the graph into a grid.
   */
  private final void finishLayout ()
  {
    ArrayList < DefaultStateView > states = new ArrayList < DefaultStateView > ();

    for ( ArrayList < DefaultStateView > current : this.groups )
    {
      states.addAll ( current );
    }
    this.item = new MultiItem ();
    createGrid ( states );

    if ( ( this.redoUndoHandler != null ) && ( this.item.size () > 0 ) )
    {
      this.redoUndoHandler.addItem ( this.item );
    }

    this.item = null;
  }


  /**
   * Checks if there is a cut for the given {@link State}.
   * 
   * @param state The {@link State}.
   * @param groupIndex The actual group index.
   * @return True if there is a cut, else false.
   */
  private final boolean isCut ( State state, int groupIndex )
  {
    for ( int i = groupIndex + 1 ; i < this.groups.size () ; i++ )
    {
      for ( DefaultStateView current : this.groups.get ( i ) )
      {
        if ( current.getState ().equals ( state ) )
        {
          return true;
        }
      }
    }
    return false;
  }


  /**
   * Layouts the graph into a grid.
   */
  private final void prelayout ()
  {
    createGrid ( this.model.getStateViewList () );
  }


  /**
   * Resorts the groups. Not used in the moment.
   */
  @SuppressWarnings ( "unused" )
  private void resortGroups ()
  {
    ArrayList < ArrayList < DefaultStateView > > oldGroups = this.groups;
    this.groups = new ArrayList < ArrayList < DefaultStateView > > ();

    for ( int j = 0 ; j < oldGroups.get ( 0 ).size () ; j++ )
    {
      ArrayList < DefaultStateView > group = new ArrayList < DefaultStateView > ();
      for ( int i = 0 ; i < oldGroups.size () ; i++ )
      {
        if ( oldGroups.get ( i ).size () > j )
        {
          DefaultStateView node = oldGroups.get ( i ).get ( j );
          group.add ( node );
        }
      }
      this.groups.add ( group );
    }
  }


  /**
   * Swaps the {@link DefaultStateView}.
   * 
   * @param groupIndex0 The group index of the first {@link DefaultStateView}.
   * @param stateView0 The first {@link DefaultStateView}.
   * @param groupIndex1 The group index of the second {@link DefaultStateView}.
   * @param stateView1 The second {@link DefaultStateView}.
   */
  private final void swap ( int groupIndex0, DefaultStateView stateView0,
      int groupIndex1, DefaultStateView stateView1 )
  {
    ArrayList < DefaultStateView > list0 = this.groups.get ( groupIndex0 );
    ArrayList < DefaultStateView > list1 = this.groups.get ( groupIndex1 );
    int index0 = list0.indexOf ( stateView0 );
    int index1 = list1.indexOf ( stateView1 );

    list0.add ( index0, stateView1 );
    list0.remove ( stateView0 );
    list1.add ( index1, stateView0 );
    list1.remove ( stateView1 );
  }
}
