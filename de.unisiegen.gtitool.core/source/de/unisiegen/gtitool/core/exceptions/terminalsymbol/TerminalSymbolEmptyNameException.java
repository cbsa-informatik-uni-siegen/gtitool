package de.unisiegen.gtitool.core.exceptions.terminalsymbol;


import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The {@link TerminalSymbolEmptyNameException} is used if the
 * {@link TerminalSymbol} is not correct.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class TerminalSymbolEmptyNameException extends
    TerminalSymbolException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -7238482015460468562L;


  /**
   * Allocates a new {@link TerminalSymbolEmptyNameException}.
   */
  public TerminalSymbolEmptyNameException ()
  {
    super ();
    // Message and Description
    setMessage ( Messages
        .getString ( "TerminalSymbolException.EmptyNameMessage" ) ); //$NON-NLS-1$
    setDescription ( Messages
        .getString ( "TerminalSymbolException.EmptyNameDescription" ) ); //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see CoreException#getType()
   */
  @Override
  public final ErrorType getType ()
  {
    return ErrorType.ERROR;
  }
}
