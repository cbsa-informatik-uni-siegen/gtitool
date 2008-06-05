package de.unisiegen.gtitool.ui.utils;


import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import org.jgraph.graph.DefaultGraphModel;

import de.unisiegen.gtitool.core.entities.DefaultState;
import de.unisiegen.gtitool.core.entities.DefaultTransition;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.StatesInvolvedException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineStateNotReachableException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineValidationException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;
import de.unisiegen.gtitool.ui.jgraph.DefaultTransitionView;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.redoundo.MultiItem;
import de.unisiegen.gtitool.ui.redoundo.RedoUndoHandler;
import de.unisiegen.gtitool.ui.redoundo.RedoUndoItem;
import de.unisiegen.gtitool.ui.redoundo.StateAddedItem;
import de.unisiegen.gtitool.ui.redoundo.StateRemovedItem;
import de.unisiegen.gtitool.ui.redoundo.TransitionAddedItem;


/**
 * Minimize a given {@link Machine}.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public class Minimizer
{

  /**
   * Flag indicates if minimization operation finished.
   */
  private boolean finished = false;


  /**
   * The old groups needed for previous step.
   */
  private Stack < ArrayList < ArrayList < DefaultStateView > > > previousSteps = new Stack < ArrayList < ArrayList < DefaultStateView > > > ();


  /**
   * The old groups needed for previous step.
   */
  private Stack < ArrayList < ArrayList < DefaultStateView > > > nextStep = new Stack < ArrayList < ArrayList < DefaultStateView > > > ();


  /**
   * The {@link DefaultStateView} groups.
   */
  private ArrayList < ArrayList < DefaultStateView > > activeGroups = new ArrayList < ArrayList < DefaultStateView > > ();


  /**
   * The {@link DefaultMachineModel}.
   */
  private DefaultMachineModel model;


  /**
   * The states for a new group.
   */
  private ArrayList < DefaultStateView > newGroupStates = new ArrayList < DefaultStateView > ();


  /**
   * Allocate a new {@link Minimizer}.
   * 
   * @param model The {@link DefaultMachineModel}.
   */
  public Minimizer ( DefaultMachineModel model )
  {
    this.model = model;
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
    this.previousSteps.push ( oldGroups );

    minimize ();

    while ( this.previousSteps.size () > 1 )
    {
      this.nextStep.push ( this.previousSteps.pop () );
    }

    this.activeGroups = this.previousSteps.pop ();

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
      Color.yellow, Color.pink };

  /**
   * Handle previous step of the minimization.
   */
  public void previousStep ()
  {
    this.nextStep.push ( this.activeGroups );
    this.activeGroups = this.previousSteps.pop ();

    highlightGroups ();

    this.begin = this.previousSteps.isEmpty ();
    this.finished = false;
  }
  
  /**
   * Handle next step of the minimization.
   */
  public void nextStep ()
  {
//    if(this.nextStep.isEmpty ()){
//      this.lastStep.redo ();
//      createTransitions ();
//      new LayoutManager(this.model, null).doLayout ();
//      this.finished = true;
//    }
//    else {
      this.previousSteps.push ( this.activeGroups );
      this.activeGroups = this.nextStep.pop ();

      highlightGroups ();
//    }


    this.begin = false;
    this.finished = this.nextStep.isEmpty ();
  }


  /**
   * Flag indicates if begin reached.
   */
  private boolean begin = true;


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
      this.newGroupStates = new ArrayList < DefaultStateView > ();
      
      ArrayList < ArrayList < DefaultStateView > > oldGroup = new ArrayList < ArrayList < DefaultStateView > > ();
      for ( ArrayList < DefaultStateView > group : this.activeGroups )
      {
        ArrayList < DefaultStateView > tmpGroup = new ArrayList < DefaultStateView > ();

        tmpGroup.addAll ( group );
        oldGroup.add ( tmpGroup );
      }

      this.previousSteps.push ( oldGroup );

      this.minimize ();
      return;
    }

//    viewMachine();
//    this.lastStep.undo ();

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
        current.setGroupColor ( this.colors [ this.activeGroups.indexOf ( group ) ] );
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
  
  

  /**
   * The new created states.
   */
  private HashMap < State, DefaultStateView > states = new HashMap < State, DefaultStateView > ();
  
  
  MultiItem lastStep = new MultiItem();

  /**
   * build the minimal {@link Machine}.
   */
  private void viewMachine ()
  {
    int stateCount = 0;
    removeOldStates();
    this.states.clear ();

    try
    {
      for ( ArrayList < DefaultStateView > current : getGroups () )
      {
        boolean startState = false;
        String name = "{"; //$NON-NLS-1$
        int count = 0;
        for ( DefaultStateView defaultStateView : current )
        {
          if ( defaultStateView.getState ().isStartState () )
          {
            startState = true;
          }
          if ( count > 0 )
          {
            name += ", "; //$NON-NLS-1$
          }
          name += defaultStateView.toString ();
          count++ ;
        }
        name += "}"; //$NON-NLS-1$

        DefaultState state = new DefaultState ( name );
        state.setStartState ( startState );
        state.setFinalState ( current.get ( 0 ).getState ().isFinalState () );
        DefaultStateView stateView = this.model.createStateView ( 100, 100,
            state, false );
        
        stateView.setGroupColor ( this.colors[stateCount] );
        stateCount++;
        
        RedoUndoItem item = new StateAddedItem(this.model, stateView, null);
        this.lastStep.addItem ( item );
//        this.model.removeState ( stateView, false );

//        item.undo ();
        
        this.states.put ( current.get ( 0 ).getState (), stateView );
        
        
      }
    }
    catch ( StateException exc )
    {
      exc.printStackTrace ();
    }

    createTransitions ();
  }


  /**
   * TODO
   *
   */
  private void removeOldStates ()
  {
    ArrayList < DefaultStateView > removeStates = new ArrayList < DefaultStateView >();
    for (DefaultStateView current : this.model.getStateViewList ()){
      
      ArrayList < DefaultTransitionView > removeList = new ArrayList < DefaultTransitionView > ();
      for ( DefaultTransitionView transition : this.model.getTransitionViewList () )
      {
        if ( ( transition.getTransition ().getStateBegin ().equals ( current
            .getState () ) )
            || ( transition.getTransition ().getStateEnd ().equals ( current
                .getState () ) ) )
        {
          removeList.add ( transition );
        }
      }

      for ( DefaultTransitionView transition : removeList )
      {
        this.model.removeTransition ( transition, false );
      }
      
      RedoUndoItem item = new StateRemovedItem(this.model, current, removeList);
      this.lastStep.addItem ( item );
      removeStates.add(current);
      
    }
    
    for (DefaultStateView current : removeStates){
      this.model.removeState ( current, false );
    }
  }


  /**
   * Create the transitions of the {@link Machine}.
   */
  private void createTransitions ()
  {
    System.err.println (this.states.size ());
    for ( State current : this.states.keySet () )
    {
      HashMap < State, Transition > transitions = new HashMap < State, Transition > ();
      for ( Transition transition : current.getTransitionBegin () )
      {

        try
        {
          DefaultStateView target = getTargetStateView ( transition
              .getStateEnd () );
          Transition newTransition;
//          if ( transitions.containsKey ( target.getState () ) )
//          {
//            transitions.get ( target.getState () ).add (
//                transition.getSymbol () );
//          }
//          else
//          {
            newTransition = new DefaultTransition ( transition.getAlphabet (),
                transition.getPushDownAlphabet (), transition
                    .getPushDownWordRead (),
                transition.getPushDownWordWrite (), this.states.get ( current )
                    .getState (), target.getState (), transition.getSymbol () );
           DefaultTransitionView transitionView =  this.model.createTransitionView ( newTransition, this.states
                .get ( current ), target, false, false, true );
            
           RedoUndoItem item = new TransitionAddedItem(this.model, transitionView, null );
            this.lastStep.addItem ( item );
//          }

        }
        catch ( TransitionSymbolNotInAlphabetException exc )
        {
          exc.printStackTrace ();
        }
        catch ( TransitionSymbolOnlyOneTimeException exc )
        {
          exc.printStackTrace ();
        }

      }
    }
  }
  
  /**
   * Get the target state view representing the group.
   * 
   * @param state The {@link State}.
   * @return The {@link DefaultStateView} representing the group.
   */
  private DefaultStateView getTargetStateView ( State state )
  {
    DefaultStateView target = null;
    for ( ArrayList < DefaultStateView > current : getGroups () )
    {
      target = this.states.get ( current.get ( 0 ).getState () );
      for ( DefaultStateView defaultStateView : current )
      {
        if ( state.equals ( defaultStateView.getState () ) )
        {
          return target;
        }
      }
    }
    return target;
  }
}
