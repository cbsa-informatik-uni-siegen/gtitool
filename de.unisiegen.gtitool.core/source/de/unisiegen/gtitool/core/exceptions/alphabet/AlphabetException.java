package de.unisiegen.gtitool.core.exceptions.alphabet;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The <code>AlphabetException</code> is used if the {@link Alphabet} is not
 * correct.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class AlphabetException extends CoreException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 8267857615201989774L;


  /**
   * Allocates a new <code>AlphabetException</code>.
   * 
   * @param pMessage The detail message.
   * @param pDescription The detail description.
   */
  public AlphabetException ( String pMessage, String pDescription )
  {
    super ( pMessage, pDescription );
  }
}
