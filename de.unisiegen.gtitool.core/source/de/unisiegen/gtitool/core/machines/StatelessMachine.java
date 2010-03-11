package de.unisiegen.gtitool.core.machines;


import de.unisiegen.gtitool.core.entities.Action;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.exceptions.machine.MachineAmbigiousActionException;


/**
 * The interface for stateless machines
 */
public interface StatelessMachine extends Machine
{

  /**
   * Returns true if one of the active {@link State}s is a final {@link State},
   * otherwise false.
   * 
   * @return True if one of the active {@link State}s is a final {@link State},
   *         otherwise false.
   */
  public boolean isWordAccepted ();


  /**
   * Try to automatically use the next transition (next step)
   * 
   * @return The {@link Action} which was taken
   * @throws MachineAmbigiousActionException
   */
  public Action autoTransit () throws MachineAmbigiousActionException;


  /**
   * one step backward (previous step)
   */
  public void backTransit ();
}
