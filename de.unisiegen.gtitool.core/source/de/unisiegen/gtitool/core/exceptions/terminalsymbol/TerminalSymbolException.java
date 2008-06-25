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
   * The serial version uid.
   */
  private static final long serialVersionUID = 3641040757624990095L;


  /**
   * Allocates a new {@link TerminalSymbolException}.
   */
  public TerminalSymbolException ()
  {
    super ();
  }
}
