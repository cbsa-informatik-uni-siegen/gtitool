package de.unisiegen.gtitool.core.exceptions.terminalsymbol;


import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The {@link TerminalSymbolException} is used if the {@link TerminalSymbol} is
 * not correct.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class TerminalSymbolException extends CoreException
{

  /**
   * Allocates a new {@link TerminalSymbolException}.
   */
  public TerminalSymbolException ()
  {
    super ();
  }
}
