package de.unisiegen.gtitool.core.exceptions.symbol;


import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.exceptions.CoreErrorException;


/**
 * The <code>SymbolException</code> is used if the {@link Symbol} is not
 * correct.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class SymbolException extends CoreErrorException
{

  /**
   * Allocates a new <code>SymbolException</code>.
   */
  public SymbolException ()
  {
    super ();
  }
}
