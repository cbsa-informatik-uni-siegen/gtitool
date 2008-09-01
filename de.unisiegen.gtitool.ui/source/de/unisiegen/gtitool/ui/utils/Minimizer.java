package de.unisiegen.gtitool.ui.utils;


import java.awt.Color;
import java.util.ArrayList;
import java.util.Stack;

import org.jgraph.graph.DefaultGraphModel;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;
import de.unisiegen.gtitool.ui.logic.MinimizeMachineDialog;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.redoundo.MultiItem;
import de.unisiegen.gtitool.ui.redoundo.RedoUndoItem;


/**
 * Minimize a given {@link Machine}.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public class Minimizer
{

  /**
   * This class represents a minimize item. The is on item per minimize step.
   */
  public class MinimizeItem
  {

    /**
     * The {@link DefaultStateView} groups.
     */
    public ArrayList < ArrayList < DefaultStateView > > groups;


    /**
     * The {@link PrettyString}.
     */
    public PrettyString prettyString;


    /**
     * The {@link RedoUndoItem}.
     */
    public RedoUndoItem redoUndoItem;


    /**
     * List of the {@link Transition}s.
     */
    public ArrayList < Transition > transitionList;


    /**
     * Allocate a new {@link MinimizeItem}.
     * 
     * @param groups The actual groups for this step.
     * @param prettyString The {@link PrettyString} for this row.
     * @param transitionList List of the {@link Transition}s.
     * @param redoUndoItem The {@link RedoUndoItem}.
     */
    public MinimizeItem ( ArrayList < ArrayList < DefaultStateView > > groups,
        PrettyString prettyString, ArrayList < Transition > transitionList,
        RedoUndoItem redoUndoItem )
    {
      this.groups = groups;
      this.prettyString = prettyString;
      this.transitionList = transitionList;
      this.redoUndoItem = redoUndoItem;
    }
  }


  /**
   * The {@link DefaultStateView} groups.
   */
  private ArrayList < ArrayList < DefaultStateView > > activeGroups = new ArrayList < ArrayList < DefaultStateView > > ();


  /**
   * The active {@link MinimizeItem}.
   */
  private MinimizeItem activeMinimizeItem;


  /**
   * Flag indicates if begin reached.
   */
  private boolean begin = true;


  /**
   * The predefined {@link Color}s of the groups.
   */
  Color [] colors = new Color []
  { Color.blue, Color.cyan, Color.lightGray, Color.green, Color.magenta,
      Color.yellow, Color.orange, Color.pink, Color.white, Color.darkGray };


  /**
   * The {@link MinimizeMachineDialog}.
   */
  private MinimizeMachineDialog dialog;


  /**
   * Flag indicates if minimization operation finished.
   */
  private boolean finished = false;


  /**
   * The {@link DefaultMachineModel}.
   */
  private DefaultMachineModel model;


  /**
   * The states for a new group.
   */
  private ArrayList < DefaultStateView > newGroupStates = new ArrayList < DefaultStateView > ();


  /**
   * The old groups needed for previous step.
   */
  private Stack < MinimizeItem > nextStep = new Stack < MinimizeItem > ();


  /**
   * The not reachable states.
   */
  private ArrayList < DefaultStateView > notReachable = new ArrayList < DefaultStateView > ();


  /**
   * The old groups needed for previous step.
   */
  private Stack < MinimizeItem > previousSteps = new Stack < MinimizeItem > ();


  /**
   * List of the {@link Transition}s.
   */
  private ArrayList < Transition > transitions = new ArrayList < Transition > ();


  /**
   * Allocate a new {@link Minimizer}.
   * 
   * @param model The {@link DefaultMachineModel}.
   * @param dialog The {@link MinimizeMachineDialog}.
   */
  public Minimizer ( DefaultMachineModel model, MinimizeMachineDialog dialog )
  {
    this.model = model;
    this.dialog = dialog;
  }


  /**
   * Cretate the initial groups.
   */
  private void createInitialGroups ()
  {
    ArrayList < DefaultStateView > notFinalStates = new ArrayList < DefaultStateView > ();
    ArrayList < DefaultStateView > finalStates = new ArrayList < DefaultStateView > ();

    for ( DefaultStateView current : this.model.getStateViewList () )
    {
      if ( current.getState ().isFinalState () )
      {
        if ( !this.notReachable.contains ( current ) )
        {
          finalStates.add ( current );
        }
      }
      else
      {
        if ( !this.notReachable.contains ( current ) )
        {
          notFinalStates.add ( current );
        }
      }
    }

    if ( notFinalStates.size () > 0 )
    {
      this.activeGroups.add ( notFinalStates );
    }
    if ( finalStates.size () > 0 )
    {
      this.activeGroups.add ( finalStates );
    }

  }


  /**
   * Create a new {@link PrettyString}.
   * 
   * @param states the list of the {@link DefaultStateView}s.
   * @param oldGroup The old group of the removed states.
   * @return The created {@link PrettyString}.
   */
  private PrettyString createPrettyString (
      ArrayList < DefaultStateView > states, ArrayList < DefaultStateView > oldGroup )
  {
    PrettyString prettyString = new PrettyString ();
    
    for ( int i = 0 ; i < states.size () ; i++ )
    {
      if ( i != 0 && i < ( states.size () - 1 ) )
      {
        prettyString.add ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$>
      }
      if ( i != 0 && i == ( states.size () - 1 ) )
      {
        prettyString.add ( new PrettyToken ( " " + Messages //$NON-NLS-1$
            .getString ( "And" ) + " ", Style.NONE ) ); //$NON-NLS-1$ //$NON-NLS-2$>
      }
      prettyString.add ( states.get ( i ).getState () );
    }
    


    prettyString.add ( new PrettyToken ( " " + Messages //$NON-NLS-1$
        .getString ( "MinimizeMachineDialog.PrettyString" ) //$NON-NLS-1$
        + " ", Style.NONE ) ); //$NON-NLS-1$
    
    
    for ( int i = 0 ; i < oldGroup.size () ; i++ )
    {
      if ( i != 0 && i < ( oldGroup.size () - 1 ) )
      {
        prettyString.add ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$>
      }
      if ( i != 0 && i == ( oldGroup.size () - 1 ) )
      {
        prettyString.add ( new PrettyToken ( " " + Messages //$NON-NLS-1$
            .getString ( "And" ) + " ", Style.NONE ) ); //$NON-NLS-1$ //$NON-NLS-2$>
      }
      prettyString.add ( oldGroup.get ( i ).getState () );
    }
    return prettyString;
  }


  /**
   * Returns the groups.
   * 
   * @return The groups.
   * @see #activeGroups
   */
  public ArrayList < ArrayList < DefaultStateView >> getGroups ()
  {
    return this.activeGroups;
  }


  /**
   * Highlight the groups
   */
  private void highlightGroups ()
  {
    if ( this.activeGroups.size () == 1 )
    {
      return;
    }
    for ( ArrayList < DefaultStateView > group : this.activeGroups )
    {
      Color color = null;
      boolean notReachableGroup = true;
      for ( DefaultStateView current : group )
      {
        notReachableGroup = this.notReachable.contains ( current );
      }

      if ( notReachableGroup )
      {
        color = Color.white;
      }
      else
      {
        int index = ( this.activeGroups.indexOf ( group ) ) % 10;
        color = this.colors [ index ];
      }

      if ( group.containsAll ( this.notReachable ) )
      {
        for ( DefaultStateView current : group )
        {
          current.setOverwrittenColor ( Color.white );
        }
      }
      for ( DefaultStateView current : group )
      {
        current.setOverwrittenColor ( color );
      }
    }

    this.model.getGraphModel ().cellsChanged (
        DefaultGraphModel.getAll ( this.model.getGraphModel () ) );
  }


  /**
   * Minimize the given {@link Machine}.
   */
  public void initialize ()
  {
    ArrayList < ArrayList < DefaultStateView > > oldGroups = new ArrayList < ArrayList < DefaultStateView > > ();
    ArrayList < DefaultStateView > tmpList = new ArrayList < DefaultStateView > ();
    tmpList.addAll ( this.model.getStateViewList () );
    oldGroups.add ( tmpList );
    this.activeMinimizeItem = new MinimizeItem ( oldGroups, null,
        new ArrayList < Transition > (), null );
    this.previousSteps.push ( this.activeMinimizeItem );

    oldGroups = new ArrayList < ArrayList < DefaultStateView > > ();
    tmpList = new ArrayList < DefaultStateView > ();
    tmpList.addAll ( this.model.getStateViewList () );
    for ( State current : this.model.getMachine ().getNotReachableStates () )
    {
      DefaultStateView defaultStateView = this.model.getStateById ( current
          .getId () );
      tmpList.remove ( defaultStateView );
      this.notReachable.add ( defaultStateView );
    }
    oldGroups.add ( tmpList );
    oldGroups.add ( this.notReachable );

    PrettyString prettyString = new PrettyString ();
    if ( this.notReachable.size () > 0 )
    {
      prettyString.add ( new PrettyToken ( Messages
          .getString ( "MinimizeMachineDialog.PrettyStringNotReachable" ) //$NON-NLS-1$
          + " ", Style.NONE ) ); //$NON-NLS-1$
      for ( int i = 0 ; i < this.notReachable.size () ; i++ )
      {
        if ( i != 0 && i < ( this.notReachable.size () - 1 ) )
        {
          prettyString.add ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$>
        }
        if ( i != 0 && i == ( this.notReachable.size () - 1 ) )
        {
          prettyString.add ( new PrettyToken ( " " + Messages //$NON-NLS-1$
              .getString ( "And" ) + " ", Style.NONE ) ); //$NON-NLS-1$ //$NON-NLS-2$>
        }
        prettyString.add ( this.notReachable.get ( i ).getState () );
      }
    }
    else
    {
      prettyString.add ( new PrettyToken ( Messages
          .getString ( "MinimizeMachineDialog.PrettyStringAllReachable" ) //$NON-NLS-1$
          , Style.NONE ) );

    }
    MultiItem item = new MultiItem ();
    for ( DefaultStateView current : this.notReachable )
    {
      item.addItem ( this.model.removeState ( current, false ) );
    }
    item.undo ();
    this.activeMinimizeItem = new MinimizeItem ( oldGroups, prettyString,
        new ArrayList < Transition > (), item );
    this.previousSteps.push ( this.activeMinimizeItem );

    createInitialGroups ();

    oldGroups = new ArrayList < ArrayList < DefaultStateView > > ();
    for ( ArrayList < DefaultStateView > current : this.activeGroups )
    {
      ArrayList < DefaultStateView > tmpGroup = new ArrayList < DefaultStateView > ();

      tmpGroup.addAll ( current );
      oldGroups.add ( tmpGroup );
    }

    prettyString = new PrettyString ();
    if ( this.activeGroups.size () > 1 )
    {
      prettyString.add ( new PrettyToken ( Messages
          .getString ( "MinimizeMachineDialog.PrettyStringFinalStates" ) //$NON-NLS-1$
          + " ", Style.NONE ) ); //$NON-NLS-1$

      ArrayList < DefaultStateView > states = new ArrayList < DefaultStateView > ();

      states.addAll ( this.activeGroups.get ( 1 ) );

      for ( int i = 0 ; i < states.size () ; i++ )
      {
        if ( i != 0 && i < ( states.size () - 1 ) )
        {
          prettyString.add ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$>
        }
        if ( i != 0 && i == ( states.size () - 1 ) )
        {
          prettyString.add ( new PrettyToken ( " " + Messages //$NON-NLS-1$
              .getString ( "And" ) + " ", Style.NONE ) ); //$NON-NLS-1$ //$NON-NLS-2$>
        }
        prettyString.add ( states.get ( i ).getState () );
      }
    }
    else
    {
      prettyString.add ( new PrettyToken ( Messages
          .getString ( "MinimizeMachineDialog.PrettyStringNoFinalStates" ) //$NON-NLS-1$
          , Style.NONE ) );
    }
    this.activeMinimizeItem = new MinimizeItem ( oldGroups, prettyString,
        new ArrayList < Transition > (), null );
    this.previousSteps.push ( this.activeMinimizeItem );

    minimize ();

    // Add a final step to show the new machine.
    this.previousSteps.push ( new MinimizeItem ( getGroups (), Messages
        .getPrettyString ( "MinimizeMachineDialog.FinalPrettyString" ), //$NON-NLS-1$
        new ArrayList < Transition > (), null ) );

    while ( this.previousSteps.size () > 1 )
    {
      this.nextStep.push ( this.previousSteps.pop () );
    }
    this.activeMinimizeItem = this.previousSteps.pop ();
    this.activeGroups = this.activeMinimizeItem.groups;

    highlightGroups ();

  }


  /**
   * Returns the begin.
   * 
   * @return The begin.
   * @see #begin
   */
  public boolean isBegin ()
  {
    return this.begin;
  }


  /**
   * Returns true if minimization operation finished.
   * 
   * @return true if minimization operation finished.
   */
  public boolean isFinished ()
  {
    return this.finished;
  }


  /**
   * The internal minimize operation.
   */
  private void minimize ()
  {
    ArrayList < DefaultStateView > currentGroup = null;
    for ( ArrayList < DefaultStateView > current : this.activeGroups )
    {
      currentGroup = current;
      if ( current.size () > 1 )
      {
        tryToSplitGroup ( current );
        if ( this.newGroupStates.size () > 0 )
        {
          break;
        }
      }
    }

    if ( this.newGroupStates.size () > 0 )
    {
      this.activeGroups.add ( this.newGroupStates );

      ArrayList < ArrayList < DefaultStateView > > oldGroup = new ArrayList < ArrayList < DefaultStateView > > ();
      for ( ArrayList < DefaultStateView > group : this.activeGroups )
      {
        ArrayList < DefaultStateView > tmpGroup = new ArrayList < DefaultStateView > ();

        tmpGroup.addAll ( group );
        oldGroup.add ( tmpGroup );
      }

      ArrayList < Transition > transitionList = new ArrayList < Transition > ();
      transitionList.addAll ( this.transitions );
      this.previousSteps.push ( new MinimizeItem ( oldGroup,
          createPrettyString ( this.newGroupStates, currentGroup ), transitionList, null ) );
      this.newGroupStates = new ArrayList < DefaultStateView > ();
      minimize ();
      return;
    }

  }


  /**
   * Handle next step of the minimization.
   */
  public void nextStep ()
  {
    this.previousSteps.push ( this.activeMinimizeItem );
    this.activeMinimizeItem = this.nextStep.pop ();
    this.activeGroups = this.activeMinimizeItem.groups;

    if ( this.activeMinimizeItem.redoUndoItem != null )
    {
      this.activeMinimizeItem.redoUndoItem.redo ();
    }

    highlightGroups ();

    this.begin = false;
    this.finished = this.nextStep.isEmpty ();

    this.dialog.addOutlineComment ( this.activeMinimizeItem.prettyString,
        this.activeMinimizeItem.transitionList );
  }


  /**
   * Handle previous step of the minimization.
   */
  public void previousStep ()
  {
    if ( this.activeMinimizeItem.redoUndoItem != null )
    {
      this.activeMinimizeItem.redoUndoItem.undo ();
    }
    this.nextStep.push ( this.activeMinimizeItem );
    this.activeMinimizeItem = this.previousSteps.pop ();
    this.activeGroups = this.activeMinimizeItem.groups;

    highlightGroups ();

    this.begin = this.previousSteps.isEmpty ();
    this.finished = false;
  }


  /**
   * Try to split the actual group.
   * 
   * @param group The actual group.
   */
  private void tryToSplitGroup ( ArrayList < DefaultStateView > group )
  {
    this.transitions.clear ();
    for ( Symbol symbol : this.model.getMachine ().getAlphabet () )
    {
      for ( DefaultStateView defaultStateView : group )
      {
        for ( Transition transition : defaultStateView.getState ()
            .getTransitionBegin () )
        {
          if ( transition.getSymbol ().contains ( symbol ) )
          {
            if ( !group.contains ( this.model.getStateViewForState ( transition
                .getStateEnd () ) ) )
            {
              this.newGroupStates.add ( defaultStateView );
              this.transitions.add ( transition );
            }
          }
        }
      }
    }
    if ( this.newGroupStates.size () == group.size () )
    {
      this.newGroupStates.clear ();
    }
    group.removeAll ( this.newGroupStates );
  }

}
