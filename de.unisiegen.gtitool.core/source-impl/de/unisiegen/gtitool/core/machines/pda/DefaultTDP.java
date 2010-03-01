package de.unisiegen.gtitool.core.machines.pda;


import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.exceptions.machine.MachineAmbigiousActionException;
import de.unisiegen.gtitool.core.machines.AbstractStatelessMachine;


/**
 * The class for the top down parser (pda)
 * 
 *@author Christian Uhrhan
 */
public class DefaultTDP extends AbstractStatelessMachine implements TDP
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 1371164970141783189L;


  /**
   * Allocates a new {@link PDA}.
   * 
   * @param alphabet The {@link Alphabet} of this {@link PDA}.
   */
  public DefaultTDP ( Alphabet alphabet )
  {
    super ( alphabet );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.StatelessMachine#autoTransit()
   */
  public void autoTransit () throws MachineAmbigiousActionException
  {
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.StatelessMachine#getTableModel()
   */
  public TableModel getTableModel ()
  {
    return null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.Machine#getTableColumnModel()
   */
  public TableColumnModel getTableColumnModel ()
  {
    return null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.Machine#getMachineType()
   */
  public MachineType getMachineType ()
  {
    return MachineType.TDP;
  }
}
