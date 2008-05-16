package de.unisiegen.gtitool.ui.utils;


import java.util.ArrayList;

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
public class LayoutManager
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
  private DefaultStateView swapNode1;


  /**
   * The actual second node to swap.
   */
  private DefaultStateView swapNode2;


  /**
   * The {@link RedoUndoHandler}.
   */
  private RedoUndoHandler redoUndoHandler;


  /**
   * The {@link MultiItem} for redo / undo.
   */
  MultiItem item;


  /**
   * Allocate a new {@link LayoutManager}.
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
   * Calculate the cuts of the graph.
   * 
   * @return The count of the cuts.
   */
  private int calculateAllCuts ()
  {
    int count = 0;
    for ( int i = 0 ; i < this.groups.size () ; i++ )
    {
      count += calculateCuts ( i );
    }
    return count;
  }


  /**
   * Calculate the cuts between the given index and the groups below.
   * 
   * @param index The index of the given group.
   * @return The count of cuts.
   */
  private int calculateCuts ( final int index )
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
   * Layout the states into a grid to perform next layout steps.
   * 
   * @param states List with all states of the machine.
   */
  private void createGrid ( ArrayList < DefaultStateView > states )
  {
    int x = -50;
    int y = 100;

    ArrayList < DefaultStateView > group = new ArrayList < DefaultStateView > ();

    int rowSize = ( int ) Math.ceil ( Math.sqrt ( this.model
        .getStateViewList ().size () ) );

    rowSize *= 2;

    int count = 0;
    int pos = 0;
    for ( DefaultStateView current : states )
    {
      if ( count != 0 && ( count % rowSize ) == 0 )
      {
        this.groups.add ( group );
        pos = 0;
        group = new ArrayList < DefaultStateView > ();
        x = 50;
        y += 200;
      }
      else
      {
        x += 100;
      }
      group.add ( current );
      if ( this.item != null )
      {
        if ( ( pos % 2 ) != 0 )
        {
          this.item.addItem ( new StateMovedItem ( this.model, current, current
              .getPositionX (), current.getPositionY (), x, y + 50 ) );
          current.move ( x, y + 50 );
        }
        else
        {
          this.item.addItem ( new StateMovedItem ( this.model, current, current
              .getPositionX (), current.getPositionY (), x, y - 50 ) );
          current.move ( x, y - 50 );
        }
      }
      count++ ;
      pos++ ;
    }
    this.groups.add ( group );
  }


  /**
   * Perform the graph layout.
   */
  public void doLayout ()
  {
    prelayout ();

    doLayoutInternal ();

    finishLayout ();

    this.model.getGraphModel ().cellsChanged (
        this.model.getStateViewList ().toArray () );
  }


  /**
   * Perform the internal layout action.
   */
  private void doLayoutInternal ()
  {
    for ( int index = 0 ; index < this.groups.size () - 1 ; index++ )
    {
      while ( !this.fixedNodes.containsAll ( this.groups.get ( index + 1 ) ) )
      {
        findNodesToSwap ( index );
        if ( this.swapNode1 == null )
        {
          continue;
        }
        if ( calculateAllCuts () > this.minCosts )
          swap ( index, this.swapNode1, index + 1, this.swapNode2 );
        this.fixedNodes.add ( this.swapNode1 );
        this.fixedNodes.add ( this.swapNode2 );
        this.minCosts = Integer.MAX_VALUE;
        this.swapNode1 = null;
        this.swapNode2 = null;
      }
      this.fixedNodes.clear ();
    }
  }


  /**
   * Find the best nodes to swap.
   * 
   * @param index The index of the actual group.
   */
  private void findNodesToSwap ( int index )
  {
    ArrayList < DefaultStateView > group1 = this.groups.get ( index );
    ArrayList < DefaultStateView > group2 = this.groups.get ( index + 1 );

    for ( int i = 0 ; i < group1.size () ; i++ )
    {
      DefaultStateView node1 = group1.get ( i );
      if ( this.fixedNodes.contains ( node1 ) )
      {
        continue;
      }
      for ( int j = 0 ; j < group2.size () ; j++ )
      {

        DefaultStateView node2 = group2.get ( j );

        if ( this.fixedNodes.contains ( node2 ) )
        {
          continue;
        }

        swap ( index, node1, index + 1, node2 );
        int costs = calculateAllCuts ();
        if ( costs < this.minCosts )
        {
          this.minCosts = costs;
          this.swapNode1 = node1;
          this.swapNode2 = node2;
        }
        swap ( index, node2, index + 1, node1 );
      }
    }
  }


  /**
   * Layout the graph into a grid.
   */
  private void finishLayout ()
  {

    ArrayList < DefaultStateView > states = new ArrayList < DefaultStateView > ();

    for ( ArrayList < DefaultStateView > current : this.groups )
    {
      states.addAll ( current );
    }
    this.item = new MultiItem ();
    createGrid ( states );

    this.redoUndoHandler.addItem ( this.item );

    this.item = null;
  }


  /**
   * Check if there is a cut for the given {@link State}.
   * 
   * @param state The {@link State}.
   * @param groupIndex The actual group index.
   * @return True if there is a cut, else false.
   */
  private boolean isCut ( State state, int groupIndex )
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
   * Layout the graph into a grid.
   */
  private void prelayout ()
  {
    createGrid ( this.model.getStateViewList () );
  }


  // /**
  // * Resort the groups.
  // */
  // private void resortGroups ()
  // {
  // ArrayList < ArrayList < DefaultStateView > > oldGroups = this.groups;
  // this.groups = new ArrayList < ArrayList < DefaultStateView > > ();
  //
  // for ( int j = 0 ; j < oldGroups.get ( 0 ).size () ; j++ )
  // {
  // ArrayList < DefaultStateView > group = new ArrayList < DefaultStateView >
  // ();
  // for ( int i = 0 ; i < oldGroups.size () ; i++ )
  // {
  // if ( oldGroups.get ( i ).size () > j )
  // {
  // DefaultStateView node = oldGroups.get ( i ).get ( j );
  // group.add ( node );
  // }
  // }
  // this.groups.add ( group );
  // }
  // }

  /**
   * Swap to {@link DefaultStateView}.
   * 
   * @param groupIndex1 The group index of the first {@link DefaultStateView}.
   * @param stateView1 The first {@link DefaultStateView}.
   * @param groupIndex2 The group index of the second {@link DefaultStateView}.
   * @param stateView2 The second {@link DefaultStateView}.
   */
  private void swap ( int groupIndex1, DefaultStateView stateView1,
      int groupIndex2, DefaultStateView stateView2 )
  {
    ArrayList < DefaultStateView > list1 = this.groups.get ( groupIndex1 );
    ArrayList < DefaultStateView > list2 = this.groups.get ( groupIndex2 );
    int index1 = list1.indexOf ( stateView1 );
    int index2 = list2.indexOf ( stateView2 );

    list1.add ( index1, stateView2 );
    list1.remove ( stateView1 );
    list2.add ( index2, stateView1 );
    list2.remove ( stateView2 );
  }
}
