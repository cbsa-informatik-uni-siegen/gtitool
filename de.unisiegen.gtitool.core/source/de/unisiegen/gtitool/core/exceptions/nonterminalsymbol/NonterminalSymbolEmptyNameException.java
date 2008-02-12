package de.unisiegen.gtitool.core.exceptions.nonterminalsymbol;


import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The {@link NonterminalSymbolEmptyNameException} is used if the
 * {@link NonterminalSymbol} is not correct.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class NonterminalSymbolEmptyNameException extends
    NonterminalSymbolException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -5229380924514259506L;


  /**
   * Allocates a new {@link NonterminalSymbolEmptyNameException}.
   */
  public NonterminalSymbolEmptyNameException ()
  {
    super ();
    // Message and Description
    setMessage ( Messages
        .getString ( "NonterminalSymbolException.EmptyNameMessage" ) ); //$NON-NLS-1$
    setDescription ( Messages
        .getString ( "NonterminalSymbolException.EmptyNameDescription" ) ); //$NON-NLS-1$
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
