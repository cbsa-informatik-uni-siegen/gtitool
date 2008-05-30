package de.unisiegen.gtitool.core.exceptions.nonterminalsymbol;


import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.exceptions.CoreException;
import de.unisiegen.gtitool.core.i18n.Messages;


/**
 * The {@link NonterminalSymbolEmptyNameException} is used if the
 * {@link NonterminalSymbol} is not correct.
 * 
 * @author Christian Fehler
 * @version $Id: NonterminalSymbolEmptyNameException.java 555 2008-02-12
 *          00:50:47Z fehler $
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
    // Message and description
    setPrettyMessage ( Messages
        .getPrettyString ( "NonterminalSymbolException.EmptyNameMessage" ) ); //$NON-NLS-1$
    setPrettyDescription ( Messages
        .getPrettyString ( "NonterminalSymbolException.EmptyNameDescription" ) ); //$NON-NLS-1$
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
