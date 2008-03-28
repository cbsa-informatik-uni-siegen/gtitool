package de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The {@link NonterminalSymbolSetMoreThanOneSymbolException} is used if the
 * {@link NonterminalSymbolSet} is not correct.
 * 
 * @author Christian Fehler
 * @version $Id: AlphabetMoreThanOneSymbolException.java 189 2007-11-17
 *          15:55:30Z fehler $
 */
public final class NonterminalSymbolSetMoreThanOneSymbolException extends
    NonterminalSymbolSetException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -5165963340426259945L;


  /**
   * Allocates a new {@link NonterminalSymbolSetMoreThanOneSymbolException}.
   * 
   * @param nonterminalSymbolSet The {@link NonterminalSymbolSet}.
   * @param nonterminalSymbolList The {@link NonterminalSymbol}s.
   */
  public NonterminalSymbolSetMoreThanOneSymbolException (
      NonterminalSymbolSet nonterminalSymbolSet,
      ArrayList < NonterminalSymbol > nonterminalSymbolList )
  {
    super ( nonterminalSymbolSet, nonterminalSymbolList );
    // Message and description
    setPrettyMessage ( Messages
        .getPrettyString ( "NonterminalSymbolSetException.MoreThanOneSymbolMessage" ) ); //$NON-NLS-1$
    setPrettyDescription ( Messages.getPrettyString (
        "NonterminalSymbolSetException.MoreThanOneSymbolDescription", //$NON-NLS-1$
        nonterminalSymbolList.get ( 0 ), nonterminalSymbolSet ) );
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
