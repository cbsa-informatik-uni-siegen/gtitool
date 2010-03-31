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
   * The available machine names, used to determine which file extensions we can
   * load
   */
  public static final String [] AVAILABLE_MACHINES =
  { "LR0Parser", "SLR", "LR1Parser", "TDP" }; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$ //$NON-NLS-4$


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
