package de.unisiegen.gtitool.core.exceptions.grammar;


import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.exceptions.NonterminalSymbolInvolvedException;
import de.unisiegen.gtitool.core.i18n.Messages;


/**
 * TODO
 */
public final class GrammarInvalidNonterminalException extends GrammarException
    implements NonterminalSymbolInvolvedException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -4178590258354628604L;


  /**
   * The invalid nonterminal
   */
  private final NonterminalSymbol nonterminalSymbol;


  /**
   * Allocates a new {@link GrammarInvalidNonterminalException}
   * 
   * @param nonterminalSymbol The {@link NonterminalSymbol}.
   * @param nonterminalSymbolSet The {@link NonterminalSymbolSet}.
   */
  public GrammarInvalidNonterminalException (
      NonterminalSymbol nonterminalSymbol,
      NonterminalSymbolSet nonterminalSymbolSet )
  {
    super ();

    // nonterminal symbol
    if ( nonterminalSymbol == null )
      throw new NullPointerException ( "nonterminal symbol is null" ); //$NON-NLS-1$
    this.nonterminalSymbol = nonterminalSymbol;

    // Message and description
    setPrettyMessage ( Messages
        .getPrettyString ( "GrammarSymbolSetException.InvalidNonterminalMessage" ) ); //$NON-NLS-1$

    setPrettyDescription ( Messages.getPrettyString (
        "GrammarSymbolSetException.InvalidNonterminalDescription", //$NON-NLS-1$
        nonterminalSymbol.toPrettyString (), nonterminalSymbolSet
            .toPrettyString () ) );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.exceptions.CoreException#getType()
   */
  @Override
  public ErrorType getType ()
  {
    return ErrorType.ERROR;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.exceptions.NonterminalSymbolInvolvedException#getNonterminalSymbol()
   */
  public NonterminalSymbol getNonterminalSymbol ()
  {
    return this.nonterminalSymbol;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Throwable#toString()
   */
  @Override
  public final String toString ()
  {
    String lineBreak = System.getProperty ( "line.separator" ); //$NON-NLS-1$
    StringBuilder result = new StringBuilder ( super.toString () );
    result.append ( lineBreak );
    result.append ( "NonterminalSymbol: " ); //$NON-NLS-1$
    result.append ( this.nonterminalSymbol.toString () );
    return result.toString ();
  }
}
