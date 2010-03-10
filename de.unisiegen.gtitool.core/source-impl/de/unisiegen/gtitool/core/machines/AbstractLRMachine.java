package de.unisiegen.gtitool.core.machines;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.exceptions.machine.MachineAmbigiousActionException;
import de.unisiegen.gtitool.core.grammars.cfg.ExtendedGrammar;
import de.unisiegen.gtitool.core.machines.lr.LRMachine;
import de.unisiegen.gtitool.core.storage.Element;


/**
 * Represents an {@link AbstractLRMachine}
 */
public abstract class AbstractLRMachine extends AbstractStatelessMachine
    implements LRMachine
{

  /**
   * Allocates a new {@link AbstractLRMachine}
   * 
   * @param alphabet
   */
  public AbstractLRMachine ( final Alphabet alphabet )
  {
    super ( alphabet );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.StatelessMachine#autoTransit()
   */
  public abstract void autoTransit () throws MachineAmbigiousActionException;


  /**
   * {@inheritDoc}
   */
  @Override
  public Element getElement ()
  {
    Element element = new Element ( "Grammar" ); //$NON-NLS-1$
    element.addElement ( getGrammar ().getElement () );
    return element;
  }


  /**
   * Returns the ExtendedGrammar
   * 
   * @return the grammar
   */
  @Override
  public abstract ExtendedGrammar getGrammar ();


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.Machine#getMachineType()
   */
  public abstract MachineType getMachineType ();
}
