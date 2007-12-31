package de.unisiegen.gtitool.core.machines.dfa;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.machines.AbstractMachine;
import de.unisiegen.gtitool.core.machines.Machine;


/**
 * The class for <code>DFA</code> machines.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class DefaultDFA extends AbstractMachine implements DFA
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 6284801477317314839L;


  /**
   * Allocates a new <code>DFA</code>.
   * 
   * @param pAlphabet The {@link Alphabet} of this <code>DFA</code>.
   */
  public DefaultDFA ( Alphabet pAlphabet )
  {
    super ( pAlphabet, ValidationElement.ALL_SYMBOLS,
        ValidationElement.EPSILON_TRANSITION, ValidationElement.FINAL_STATE,
        ValidationElement.MORE_THAN_ONE_START_STATE,
        ValidationElement.NO_START_STATE, ValidationElement.STATE_NAME,
        ValidationElement.STATE_NOT_REACHABLE,
        ValidationElement.SYMBOL_ONLY_ONE_TIME );
  }


  /**
   * Returns the {@link Machine} type.
   * 
   * @return The {@link Machine} type.
   */
  @Override
  public final String getMachineType ()
  {
    return "DFA"; //$NON-NLS-1$
  }
}
