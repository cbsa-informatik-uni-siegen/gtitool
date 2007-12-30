package de.unisiegen.gtitool.core.machines.pda;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.machines.Machine;


/**
 * The class for <code>PDA</code> machines.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class PDA extends Machine
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 5236169999605256506L;


  /**
   * Allocates a new <code>PDA</code>.
   * 
   * @param pAlphabet The {@link Alphabet} of this <code>PDA</code>.
   */
  public PDA ( Alphabet pAlphabet )
  {
    super ( pAlphabet, ValidationElement.ALL_SYMBOLS,
        ValidationElement.EPSILON_TRANSITION, ValidationElement.FINAL_STATE,
        ValidationElement.MORE_THAN_ONE_START_STATE,
        ValidationElement.NO_START_STATE, ValidationElement.STATE_NAME,
        ValidationElement.SYMBOL_ONLY_ONE_TIME,
        ValidationElement.STATE_NOT_REACHABLE );
  }
}
