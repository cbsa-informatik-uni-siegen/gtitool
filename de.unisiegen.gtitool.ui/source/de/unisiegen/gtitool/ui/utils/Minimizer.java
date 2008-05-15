package de.unisiegen.gtitool.ui.utils;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.StatesInvolvedException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineStateNotReachableException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineValidationException;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultStateView;
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
   * The {@link DefaultStateView} groups.
   */
  private ArrayList < ArrayList < DefaultStateView > > groups = new ArrayList < ArrayList < DefaultStateView > > ();


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
      this.groups.add ( notFinalStates );
    }
    if ( finalStates.size () > 0 )
    {
      this.groups.add ( finalStates );
    }
  }


  /**
   * The internal minimize operation.
   */
  private void minimizeInternal ()
  {

    for ( ArrayList < DefaultStateView > group : this.groups )
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
      this.groups.add ( this.newGroupStates );
      this.newGroupStates = new ArrayList < DefaultStateView > ();
      minimizeInternal ();
    }
  }


  /**
   * Minimize the given {@link Machine}.
   */
  public void minimze ()
  {
    removeNotReachableStates ();

    createInitialGroups ();

    minimizeInternal ();

    // TODO just for testing

    for ( ArrayList < DefaultStateView > group : this.groups )
    {
      System.err.println ( "group+++++++++++" );
      for ( DefaultStateView current : group )
      {
        System.err.println ( current );
      }
      System.err.println ();
    }

    // TODO end

    this.model.getGraphModel ().cellsChanged (
        this.model.getStateViewList ().toArray () );
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
}
