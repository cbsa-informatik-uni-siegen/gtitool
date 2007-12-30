package de.unisiegen.gtitool.core.machines.nfa;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.machines.AbstractMachine;


/**
 * The class for <code>NFA</code> machines.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class DefaultNFA extends AbstractMachine implements NFA
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 6954358306581597015L;


  /**
   * Allocates a new <code>NFA</code>.
   * 
   * @param pAlphabet The {@link Alphabet} of this <code>NFA</code>.
   */
  public DefaultNFA ( Alphabet pAlphabet )
  {
    super ( pAlphabet, ValidationElement.EPSILON_TRANSITION,
        ValidationElement.FINAL_STATE,
        ValidationElement.MORE_THAN_ONE_START_STATE,
        ValidationElement.NO_START_STATE, ValidationElement.STATE_NAME,
        ValidationElement.STATE_NOT_REACHABLE );
  }
}
