package de.unisiegen.gtitool.core.machines;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.LRAction;
import de.unisiegen.gtitool.core.exceptions.machine.MachineAmbigiousActionException;
import de.unisiegen.gtitool.core.machines.lr.LRMachine;


/**
 * TODO
 */
public class AbstractLRMachine extends AbstractStatelessMachine implements
    LRMachine
{

  /**
   * TODO
   * 
   * @param alphabet
   */
  public AbstractLRMachine ( final Alphabet alphabet )
  {
    super ( alphabet );
  }


  /**
   * TODO
   * 
   * @param transition
   * @return
   * @see de.unisiegen.gtitool.core.machines.lr.LRMachine#transit(de.unisiegen.gtitool.core.entities.LRAction)
   */
  public boolean transit ( final LRAction transition )
  {
    return false;
  }


  public void autoTransit () throws MachineAmbigiousActionException
  {

  }
}
