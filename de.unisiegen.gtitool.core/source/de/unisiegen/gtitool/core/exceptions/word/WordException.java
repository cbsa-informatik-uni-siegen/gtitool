package de.unisiegen.gtitool.core.exceptions.word;


import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.CoreErrorException;


/**
 * The <code>WordException</code> is used if the {@link Word} is not correct.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class WordException extends CoreErrorException
{

  /**
   * Allocates a new <code>WordException</code>.
   */
  public WordException ()
  {
    super ();
  }
}
