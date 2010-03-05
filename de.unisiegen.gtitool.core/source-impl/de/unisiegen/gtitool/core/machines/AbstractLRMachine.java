package de.unisiegen.gtitool.core.machines;


import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.LRAction;
import de.unisiegen.gtitool.core.entities.LRActionSet;
import de.unisiegen.gtitool.core.exceptions.machine.MachineAmbigiousActionException;
import de.unisiegen.gtitool.core.grammars.cfg.ExtendedGrammar;
import de.unisiegen.gtitool.core.machines.lr.LRMachine;
import de.unisiegen.gtitool.core.storage.Element;


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


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.machines.StatelessMachine#getTableModel()
   */
  public TableModel getTableModel ()
  {
    return null;
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.machines.Machine#getTableColumnModel()
   */
  public TableColumnModel getTableColumnModel ()
  {
    return null;
  }


  /**
   * TODO
   *
   * @return
   * @see de.unisiegen.gtitool.core.machines.AbstractStatelessMachine#getElement()
   */
  @Override
  public Element getElement ()
  {
    Element element = new Element ( "Grammar" ); //$NON-NLS-1$
    element.addElement ( this.getGrammar ().getElement () );
    return element;
  }


  /**
   * TODO
   *
   * @return
   */
  public abstract ExtendedGrammar getGrammar ();


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.Machine#getMachineType()
   */
  public abstract MachineType getMachineType ();
}