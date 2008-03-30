package de.unisiegen.gtitool.ui.style;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.listener.NonterminalSymbolChangedListener;
import de.unisiegen.gtitool.core.parser.exceptions.ParserException;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.core.parser.nonterminalsymbol.NonterminalSymbolParseable;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The styled {@link NonterminalSymbol} panel class.
 * 
 * @author Christian Fehler
 * @version $Id: StyledNonterminalSymbolParserPanel.java 532 2008-02-04
 *          23:54:55Z fehler $
 */
public final class StyledNonterminalSymbolParserPanel extends StyledParserPanel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -238479279154469378L;


  /**
   * The parsed {@link NonterminalSymbol} must be in this
   * {@link NonterminalSymbolSet}.
   */
  private NonterminalSymbolSet nonterminalSymbolSet = null;


  /**
   * Allocates a new {@link StyledNonterminalSymbolParserPanel}.
   */
  public StyledNonterminalSymbolParserPanel ()
  {
    super ( new NonterminalSymbolParseable () );
    super.addParseableChangedListener ( new ParseableChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void parseableChanged ( Object newObject )
      {
        fireNonterminalSymbolChanged ( ( NonterminalSymbol ) newObject );
      }
    } );
  }


  /**
   * Adds the given {@link NonterminalSymbolChangedListener}.
   * 
   * @param listener The {@link NonterminalSymbolChangedListener}.
   */
  public final synchronized void addNonterminalSymbolChangedListener (
      NonterminalSymbolChangedListener listener )
  {
    this.listenerList.add ( NonterminalSymbolChangedListener.class, listener );
  }


  /**
   * Checks the given {@link NonterminalSymbol}.
   * 
   * @param nonterminalSymbol The {@link NonterminalSymbol} to check.
   * @return The input {@link NonterminalSymbol} or null, if the
   *         {@link NonterminalSymbol} is not in the
   *         {@link NonterminalSymbolSet}.
   */
  private final NonterminalSymbol checkNonterminalSymbol (
      NonterminalSymbol nonterminalSymbol )
  {
    if ( this.nonterminalSymbolSet == null )
    {
      return nonterminalSymbol;
    }

    NonterminalSymbol checkedNonterminalSymbol = nonterminalSymbol;
    if ( checkedNonterminalSymbol != null )
    {
      ArrayList < ScannerException > exceptionList = new ArrayList < ScannerException > ();
      if ( !this.nonterminalSymbolSet.contains ( nonterminalSymbol ) )
      {
        exceptionList.add ( new ParserException ( nonterminalSymbol
            .getParserOffset ().getStart (), nonterminalSymbol
            .getParserOffset ().getEnd (), Messages.getString (
            "TerminalPanel.StartSymbolNoNonterminalSymbol", //$NON-NLS-1$
            nonterminalSymbol.getName (), this.nonterminalSymbolSet ) ) );
      }

      // Check for exceptions
      if ( exceptionList.size () > 0 )
      {
        checkedNonterminalSymbol = null;
        setException ( exceptionList );
      }
    }
    return checkedNonterminalSymbol;
  }


  /**
   * Let the listeners know that the {@link NonterminalSymbol} has changed.
   * 
   * @param newNonterminalSymbol The new {@link NonterminalSymbol}.
   */
  private final void fireNonterminalSymbolChanged (
      NonterminalSymbol newNonterminalSymbol )
  {
    NonterminalSymbol checkedNonterminalSymbol = checkNonterminalSymbol ( newNonterminalSymbol );
    NonterminalSymbolChangedListener [] listeners = this.listenerList
        .getListeners ( NonterminalSymbolChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].nonterminalSymbolChanged ( checkedNonterminalSymbol );
    }
  }


  /**
   * Returns the {@link NonterminalSymbol} for the program text within the
   * document.
   * 
   * @return The {@link NonterminalSymbol} for the program text.
   */
  public final NonterminalSymbol getNonterminalSymbol ()
  {
    try
    {
      NonterminalSymbol nonterminalSymbol = ( NonterminalSymbol ) getParsedObject ();
      return checkNonterminalSymbol ( nonterminalSymbol );
    }
    catch ( Exception exc )
    {
      return null;
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see StyledParserPanel#parse()
   */
  @Override
  public final NonterminalSymbol parse ()
  {
    NonterminalSymbol nonterminalSymbol = ( NonterminalSymbol ) super.parse ();
    return checkNonterminalSymbol ( nonterminalSymbol );
  }


  /**
   * Removes the given {@link NonterminalSymbolChangedListener}.
   * 
   * @param listener The {@link NonterminalSymbolChangedListener}.
   */
  public final synchronized void removeNonterminalSymbolChangedListener (
      NonterminalSymbolChangedListener listener )
  {
    this.listenerList
        .remove ( NonterminalSymbolChangedListener.class, listener );
  }


  /**
   * Sets the {@link NonterminalSymbolSet}. The parsed
   * {@link NonterminalSymbol} must be in this {@link NonterminalSymbolSet}.
   * 
   * @param nonterminalSymbolSet The {@link NonterminalSymbolSet} to set.
   */
  public final void setNonterminalSymbolSet (
      NonterminalSymbolSet nonterminalSymbolSet )
  {
    this.nonterminalSymbolSet = nonterminalSymbolSet;
  }


  /**
   * Sets the {@link NonterminalSymbol} of the document.
   * 
   * @param symbol The input {@link NonterminalSymbol}.
   */
  public final void setText ( NonterminalSymbol symbol )
  {
    getEditor ().setText ( symbol.toString () );
  }
}
