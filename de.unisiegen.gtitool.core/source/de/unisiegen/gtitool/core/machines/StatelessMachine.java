package de.unisiegen.gtitool.core.machines;


import javax.swing.table.TableModel;

import de.unisiegen.gtitool.core.entities.Stack;
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
   * Try to automatically use the next transition
   * 
   * @throws MachineAmbigiousActionException
   */
  public void autoTransit () throws MachineAmbigiousActionException;


  /**
   * creates the table model
   * 
   * @return the {@link TableModel}
   */
  public TableModel getTableModel ();
}
