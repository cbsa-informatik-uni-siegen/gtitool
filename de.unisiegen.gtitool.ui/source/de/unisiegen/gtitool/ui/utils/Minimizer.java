package de.unisiegen.gtitool.ui.utils;


import java.awt.Color;
import java.util.ArrayList;
import java.util.Stack;

import org.jgraph.graph.DefaultGraphModel;

import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.StatesInvolvedException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineStateNotReachableException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineValidationException;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;
import de.unisiegen.gtitool.ui.logic.MinimizeMachineDialog;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;


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
     * List of the {@link Transition}s.
     */
    public ArrayList < Transition > transitionList;


    /**
     * Allocate a new {@link MinimizeItem}.
     * 
     * @param groups The actual groups for this step.
     * @param prettyString The {@link PrettyString} for this row.
     * @param transitionList List of the {@link Transition}s.
     */
    public MinimizeItem ( ArrayList < ArrayList < DefaultStateView > > groups,
        PrettyString prettyString, ArrayList < Transition > transitionList )
    {
      this.groups = groups;
      this.prettyString = prettyString;
      this.transitionList = transitionList;
    }
  }


  /**
   * Flag indicates if minimization operation finished.
   */
  private boolean finished = false;


  /**
   * The old groups needed for previous step.
   */
  private Stack < MinimizeItem > previousSteps = new Stack < MinimizeItem > ();


  /**
   * The old groups needed for previous step.
   */
  private Stack < MinimizeItem > nextStep = new Stack < MinimizeItem > ();


  /**
   * The {@link DefaultStateView} groups.
   */
  private ArrayList < ArrayList < DefaultStateView > > activeGroups = new ArrayList < ArrayList < DefaultStateView > > ();


  /**
   * The {@link DefaultMachineModel}.
   */
  private DefaultMachineModel model;


  /**
   * The active {@link MinimizeItem}.
   */
  private MinimizeItem activeMinimizeItem;


  /**
   * The states for a new group.
   */
  private ArrayList < DefaultStateView > newGroupStates = new ArrayList < DefaultStateView > ();


  /**
   * The {@link MinimizeMachineDialog}.
   */
  private MinimizeMachineDialog dialog;


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
        finalStates.add ( current );
      }
      else
      {
        notFinalStates.add ( current );
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
   * Minimize the given {@link Machine}.
   */
  public void initialize ()
  {
    removeNotReachableStates ();
    createInitialGroups ();

    ArrayList < ArrayList < DefaultStateView > > oldGroups = new ArrayList < ArrayList < DefaultStateView > > ();
    for ( ArrayList < DefaultStateView > current : this.activeGroups )
    {
      ArrayList < DefaultStateView > tmpGroup = new ArrayList < DefaultStateView > ();

      tmpGroup.addAll ( current );
      oldGroups.add ( tmpGroup );
    }

    this.activeMinimizeItem = new MinimizeItem ( oldGroups, null,
        new ArrayList < Transition > () );
    this.previousSteps.push ( this.activeMinimizeItem );

    minimize ();

    while ( this.previousSteps.size () > 1 )
    {
      this.nextStep.push ( this.previousSteps.pop () );
    }
    this.activeMinimizeItem = this.previousSteps.pop ();
    this.activeGroups = this.activeMinimizeItem.groups;

    highlightGroups ();

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
   * The predefined {@link Color}s of the groups.
   */
  Color [] colors = new Color []
  { Color.blue, Color.cyan, Color.lightGray, Color.green, Color.magenta,
      Color.yellow, Color.orange, Color.pink, Color.white, Color.darkGray };


  /**
   * Handle previous step of the minimization.
   */
  public void previousStep ()
  {
    this.nextStep.push ( this.activeMinimizeItem );
    this.activeMinimizeItem = this.previousSteps.pop ();
    this.activeGroups = this.activeMinimizeItem.groups;

    highlightGroups ();

    this.begin = this.previousSteps.isEmpty ();
    this.finished = false;
  }


  /**
   * Handle next step of the minimization.
   */
  public void nextStep ()
  {
    this.previousSteps.push ( this.activeMinimizeItem );
    this.activeMinimizeItem = this.nextStep.pop ();
    this.activeGroups = this.activeMinimizeItem.groups;

    highlightGroups ();

    this.begin = false;
    this.finished = this.nextStep.isEmpty ();

    this.dialog.addOutlineComment ( this.activeMinimizeItem.prettyString,
        this.activeMinimizeItem.transitionList );
  }


  /**
   * Flag indicates if begin reached.
   */
  private boolean begin = true;

  /**
   * List of the {@link Transition}s.
   */
  private ArrayList < Transition > transitions = new ArrayList < Transition > ();


  /**
   * The internal minimize operation.
   */
  private void minimize ()
  {

    for ( ArrayList < DefaultStateView > group : this.activeGroups )
    {
      if ( group.size () > 1 )
      {
        tryToSplitGroup ( group );
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
         createPrettyString(this.newGroupStates), transitionList ) );
      this.newGroupStates = new ArrayList < DefaultStateView > ();
      this.minimize ();
      return;
    }

  }


  /**
   * Create a new {@link PrettyString}.
   *
   * @param states the list of the {@link DefaultStateView}s.
   * 
   * @return The created {@link PrettyString}.
   */
  private PrettyString createPrettyString (
      ArrayList < DefaultStateView > states )
  {
    PrettyString prettyString = new PrettyString();
    
    prettyString.addPrettyToken ( new PrettyToken ( Messages
        .getString ( "MinimizeMachineDialog.PrettyString" ) //$NON-NLS-1$
        + " ", Style.NONE ) ); //$NON-NLS-1$
    
    for ( int i = 0 ; i < states.size (); i++){
      if (i != 0){
        prettyString.addPrettyToken ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$>
      }
      prettyString.addPrettyPrintable( states.get(i).getState () );
    }
    return prettyString;
  }


  /**
   * Highlight the groups
   */
  private void highlightGroups ()
  {
    for ( ArrayList < DefaultStateView > group : this.activeGroups )
    {
      for ( DefaultStateView current : group )
      {
        current.setGroupColor ( this.colors [ this.activeGroups
            .indexOf ( group ) ] );
      }
    }

    this.model.getGraphModel ().cellsChanged (
        DefaultGraphModel.getAll ( this.model.getGraphModel () ) );
  }


  /**
   * Remove the not reachable states.
   */
  private void removeNotReachableStates ()
  {
    try
    {
      this.model.getMachine ().validate ();
    }
    catch ( MachineValidationException exc )
    {
      for ( MachineException machineException : exc.getMachineException () )
      {

        if ( machineException instanceof MachineStateNotReachableException )
        {
          StatesInvolvedException exception = ( StatesInvolvedException ) machineException;
          this.model.removeState ( this.model.getStateViewForState ( exception
              .getState ().get ( 0 ) ), true );
        }
      }
    }
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
   * Returns the begin.
   * 
   * @return The begin.
   * @see #begin
   */
  public boolean isBegin ()
  {
    return this.begin;
  }

}
