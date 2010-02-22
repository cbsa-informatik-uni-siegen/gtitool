package de.unisiegen.gtitool.core.machines;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.LRAction;
import de.unisiegen.gtitool.core.entities.LRActionSet;
import de.unisiegen.gtitool.core.exceptions.machine.MachineAmbigiousActionException;
import de.unisiegen.gtitool.core.machines.lr.LRMachine;


/**
 * TODO
 */
public abstract class AbstractLRMachine extends AbstractStatelessMachine
    implements LRMachine
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
  public abstract boolean transit ( final LRAction transition );


  /**
   * TODO
   * 
   * @throws MachineAmbigiousActionException
   * @see de.unisiegen.gtitool.core.machines.StatelessMachine#autoTransit()
   */
  public abstract void autoTransit () throws MachineAmbigiousActionException;


  /**
   * Tries to use a transition from possibleActions. Also asserts that the
   * transition is valid.
   * 
   * @param possibleActions
   * @throws MachineAmbigiousActionException if the action set's size is not 1
   */
  protected void assertTransit ( final LRActionSet possibleActions )
      throws MachineAmbigiousActionException
  {
    if ( possibleActions.size () != 1 )
      throw new MachineAmbigiousActionException ();

    if ( transit ( possibleActions.first () ) == false )
    {
      // shouldn't happen
      System.err.println ( "Internal parser error" ); //$NON-NLS-1$
      System.exit ( 1 );
    }
  }
}
