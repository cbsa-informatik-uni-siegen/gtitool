package de.unisiegen.gtitool.core.exceptions.grammar;


import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.exceptions.CoreException;
import de.unisiegen.gtitool.core.exceptions.NonterminalSymbolInvolvedException;
import de.unisiegen.gtitool.core.i18n.Messages;


/**
 * The {@link GrammarNonterminalNotReachableException} is used, if there is a
 * {@link NonterminalSymbol}, which is not reachable.
 * 
 * @author Benjamin Mies
 * @version $Id: GrammarNonterminalNotReachableException.java 695 2008-03-28
 *          18:02:32Z fehler $
 */
public final class GrammarNonterminalNotReachableException extends
    GrammarException implements NonterminalSymbolInvolvedException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -1567778166577632469L;


  /**
   * The {@link NonterminalSymbol}.
   */
  private NonterminalSymbol nonterminalSymbol;


  /**
   * Allocates a new {@link GrammarNonterminalNotReachableException}.
   * 
   * @param nonterminalSymbol The {@link NonterminalSymbol}.
   */
  public GrammarNonterminalNotReachableException (
      NonterminalSymbol nonterminalSymbol )
  {
    super ();
    // NonterminalSymbol
    if ( nonterminalSymbol == null )
    {
      throw new NullPointerException ( "nonterminalSymbol is null" ); //$NON-NLS-1$
    }
    this.nonterminalSymbol = nonterminalSymbol;
    // Message and description
    setPrettyMessage ( Messages
        .getPrettyString ( "GrammarNonterminalNotReachableException.Message" ) ); //$NON-NLS-1$
    setPrettyDescription ( Messages.getPrettyString (
        "GrammarNonterminalNotReachableException.Description", //$NON-NLS-1$
        nonterminalSymbol ) );
  }


  /**
   * {@inheritDoc}
   * 
   * @see NonterminalSymbolInvolvedException#getNonterminalSymbol()
   */
  public final NonterminalSymbol getNonterminalSymbol ()
  {
    return this.nonterminalSymbol;
  }


  /**
   * {@inheritDoc}
   * 
   * @see CoreException#getType()
   */
  @Override
  public final ErrorType getType ()
  {
    return ErrorType.WARNING;
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
