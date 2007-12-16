package de.unisiegen.gtitool.core.machines.sm;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.machines.Machine;


/**
 * The class for <code>SM</code> machines.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class SM extends Machine
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 6284801477317314839L;


  /**
   * Allocates a new <code>SM</code>.
   * 
   * @param pAlphabet The {@link Alphabet} of this <code>SM</code>.
   */
  public SM ( Alphabet pAlphabet )
  {
    super ( pAlphabet, ValidationElement.ALL_SYMBOLS,
        ValidationElement.EPSILON_TRANSITION, ValidationElement.FINAL_STATE,
        ValidationElement.MORE_THAN_ONE_START_STATE,
        ValidationElement.NO_START_STATE, ValidationElement.STATE_NAME,
        ValidationElement.SYMBOL_ONLY_ONE_TIME );
  }
}
