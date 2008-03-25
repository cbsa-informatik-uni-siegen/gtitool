package de.unisiegen.gtitool.core.exceptions.grammar;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.exceptions.CoreException;
import de.unisiegen.gtitool.core.exceptions.NonterminalSymbolInvolvedException;


/**
 * The {@link GrammarNonterminalNotReachableException} is used, if there is a
 * {@link NonterminalSymbol}, which is not reachable.
 * 
 * @author Benjamin Mies
 * @version $Id: MachineAllSymbolsException.java 639 2008-03-14 11:43:47Z fehler $
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
    // Production
    if ( nonterminalSymbol == null )
    {
      throw new NullPointerException ( "nonterminalSymbol is null" ); //$NON-NLS-1$
    }
    this.nonterminalSymbol = nonterminalSymbol;

    setMessage ( Messages.getString("GrammarNonterminalNotReachableException.Message") ); //$NON-NLS-1$
    setDescription ( Messages.getString("GrammarNonterminalNotReachableException.Description", nonterminalSymbol) ); //$NON-NLS-1$

  }


  /**
   * {@inheritDoc}
   * 
   * @see NonterminalSymbolInvolvedException#getNonterminalSymbol()
   */
  public final ArrayList < NonterminalSymbol > getNonterminalSymbol ()
  {
    ArrayList < NonterminalSymbol > result = new ArrayList < NonterminalSymbol > (
        1 );
    result.add ( this.nonterminalSymbol );
    return result;
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
