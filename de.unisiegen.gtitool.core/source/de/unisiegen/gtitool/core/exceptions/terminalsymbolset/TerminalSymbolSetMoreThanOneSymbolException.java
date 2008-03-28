package de.unisiegen.gtitool.core.exceptions.terminalsymbolset;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The {@link TerminalSymbolSetMoreThanOneSymbolException} is used if the
 * {@link TerminalSymbolSet} is not correct.
 * 
 * @author Christian Fehler
 * @version $Id: AlphabetMoreThanOneSymbolException.java 189 2007-11-17
 *          15:55:30Z fehler $
 */
public final class TerminalSymbolSetMoreThanOneSymbolException extends
    TerminalSymbolSetException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 4989466995885741592L;


  /**
   * Allocates a new {@link TerminalSymbolSetMoreThanOneSymbolException}.
   * 
   * @param terminalSymbolSet The {@link TerminalSymbolSet}.
   * @param terminalSymbolList The {@link TerminalSymbol}s.
   */
  public TerminalSymbolSetMoreThanOneSymbolException (
      TerminalSymbolSet terminalSymbolSet,
      ArrayList < TerminalSymbol > terminalSymbolList )
  {
    super ( terminalSymbolSet, terminalSymbolList );
    // Message and description
    setPrettyMessage ( Messages
        .getPrettyString ( "TerminalSymbolSetException.MoreThanOneSymbolMessage" ) ); //$NON-NLS-1$
    setPrettyDescription ( Messages.getPrettyString (
        "TerminalSymbolSetException.MoreThanOneSymbolDescription", //$NON-NLS-1$
        terminalSymbolList.get ( 0 ), terminalSymbolSet ) );
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
