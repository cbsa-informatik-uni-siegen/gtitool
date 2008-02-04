package de.unisiegen.gtitool.core.exceptions.symbol;


import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The {@link SymbolException} is used if the {@link Symbol} is not correct.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class SymbolException extends CoreException
{

  /**
   * Allocates a new {@link SymbolException}.
   */
  public SymbolException ()
  {
    super ();
  }
}
