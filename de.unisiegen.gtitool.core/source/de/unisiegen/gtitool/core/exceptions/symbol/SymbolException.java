package de.unisiegen.gtitool.core.exceptions.symbol;


import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The <code>SymbolException</code> is used if the {@link Symbol} is not
 * correct.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class SymbolException extends CoreException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 8434656882158475737L;


  /**
   * Allocates a new <code>SymbolException</code>.
   * 
   * @param pMessage The detail message.
   * @param pDescription The detail description.
   */
  public SymbolException ( String pMessage, String pDescription )
  {
    super ( pMessage, pDescription );
  }
}
