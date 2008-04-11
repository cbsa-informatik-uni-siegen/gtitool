package de.unisiegen.gtitool.core.exceptions.grammar;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.exceptions.CoreException;
import de.unisiegen.gtitool.core.exceptions.ProductionInvolvedException;


/**
 * The {@link GrammarRegularGrammarException} is used, if the grammar is not
 * regular.
 * 
 * @author Benjamin Mies
 * @version $Id: GrammarNonterminalNotReachableException.java 695 2008-03-28
 *          18:02:32Z fehler $
 */
public final class GrammarRegularGrammarException extends GrammarException
    implements ProductionInvolvedException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -1567778166577632469L;


  /**
   * List of involved productions.
   */
  private ArrayList < Production > productions = new ArrayList < Production > ();


  /**
   * Allocates a new {@link GrammarRegularGrammarException}.
   * 
   * @param production The {@link Production}.
   */
  public GrammarRegularGrammarException ( Production production )
  {
    super ();
    // Production
    if ( production == null )
    {
      throw new NullPointerException ( "production is null" ); //$NON-NLS-1$
    }
    this.productions.add ( production );
    // Message and description
    setPrettyMessage ( Messages
        .getPrettyString ( "GrammarRegularGrammarException.Message" ) ); //$NON-NLS-1$
    setPrettyDescription ( Messages.getPrettyString (
        "GrammarRegularGrammarException.Description", //$NON-NLS-1$
        this.productions.get ( 0 ) ) );
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
    result.append ( "Production: " ); //$NON-NLS-1$
    result.append ( this.productions.get ( 0 ).toString () );
    return result.toString ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.exceptions.ProductionInvolvedException#getProductions()
   */
  public ArrayList < Production > getProductions ()
  {
    return this.productions;
  }
}