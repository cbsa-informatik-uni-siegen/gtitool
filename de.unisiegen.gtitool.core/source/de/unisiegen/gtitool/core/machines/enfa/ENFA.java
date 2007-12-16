package de.unisiegen.gtitool.core.machines.enfa;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.machines.Machine;


/**
 * The class for <code>ENFA</code> machines.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class ENFA extends Machine
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 8012968054911675138L;


  /**
   * Allocates a new <code>ENFA</code>.
   * 
   * @param pAlphabet The {@link Alphabet} of this <code>ENFA</code>.
   */
  public ENFA ( Alphabet pAlphabet )
  {
    super ( pAlphabet, ValidationElement.FINAL_STATE,
        ValidationElement.MORE_THAN_ONE_START_STATE,
        ValidationElement.NO_START_STATE, ValidationElement.STATE_NAME );
  }
}
