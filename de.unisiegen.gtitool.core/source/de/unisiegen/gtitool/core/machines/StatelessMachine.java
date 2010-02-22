package de.unisiegen.gtitool.core.machines;


import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.machine.MachineAmbigiousActionException;


/**
 * The interface for stateless machines
 */
public interface StatelessMachine extends Machine
{

  /**
   * Starts the {@link StateMachine} after a validation with the given
   * {@link Word}.
   * 
   * @param word The {@link Word} to start with.
   */
  public void start ( Word word );


  /**
   * Returns true if one of the active {@link State}s is a final {@link State},
   * otherwise false.
   * 
   * @return True if one of the active {@link State}s is a final {@link State},
   *         otherwise false.
   */
  public boolean isWordAccepted ();


  /**
   * Try to automatically use the next transition
   * 
   * @throws MachineAmbigiousActionException
   */
  public void autoTransit () throws MachineAmbigiousActionException;
}
