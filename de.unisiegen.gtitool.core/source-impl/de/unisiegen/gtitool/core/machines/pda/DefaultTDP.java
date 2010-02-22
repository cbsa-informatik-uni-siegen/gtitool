package de.unisiegen.gtitool.core.machines.pda;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Word;
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
   * TODO
   *
   * @throws MachineAmbigiousActionException
   * @see de.unisiegen.gtitool.core.machines.StatelessMachine#autoTransit()
   */
  public void autoTransit () throws MachineAmbigiousActionException
  {
  }


  /**
   * TODO
   *
   * @return
   * @see de.unisiegen.gtitool.core.machines.StatelessMachine#isWordAccepted()
   */
  public boolean isWordAccepted ()
  {
    return false;
  }


  /**
   * TODO
   *
   * @param word
   * @see de.unisiegen.gtitool.core.machines.StatelessMachine#start(de.unisiegen.gtitool.core.entities.Word)
   */
  public void start ( Word word )
  {
  }
}
