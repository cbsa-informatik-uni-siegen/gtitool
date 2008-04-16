package de.unisiegen.gtitool.ui.style;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Entity;
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
public final class StyledNonterminalSymbolParserPanel extends
    StyledParserPanel < NonterminalSymbol >
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
    super
        .addParseableChangedListener ( new ParseableChangedListener < NonterminalSymbol > ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void parseableChanged ( NonterminalSymbol newNonterminalSymbol )
          {
            fireNonterminalSymbolChanged ( newNonterminalSymbol );
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
   * {@inheritDoc}
   * 
   * @see StyledParserPanel#checkParsedObject(Entity)
   */
  @Override
  protected final NonterminalSymbol checkParsedObject (
      NonterminalSymbol nonterminalSymbol )
  {
    ArrayList < ScannerException > exceptionList = new ArrayList < ScannerException > ();

    if ( ( this.nonterminalSymbolSet != null ) && ( nonterminalSymbol != null ) )
    {
      if ( !this.nonterminalSymbolSet.contains ( nonterminalSymbol ) )
      {
        exceptionList.add ( new ParserException ( nonterminalSymbol
            .getParserOffset ().getStart (), nonterminalSymbol
            .getParserOffset ().getEnd (), Messages.getString (
            "TerminalPanel.StartSymbolNoNonterminalSymbol", //$NON-NLS-1$
            nonterminalSymbol.getName (), this.nonterminalSymbolSet ) ) );
      }
    }

    if ( exceptionList.size () > 0 )
    {
      setException ( exceptionList );
      return null;
    }

    return nonterminalSymbol;
  }


  /**
   * Let the listeners know that the {@link NonterminalSymbol} has changed.
   * 
   * @param newNonterminalSymbol The new {@link NonterminalSymbol}.
   */
  private final void fireNonterminalSymbolChanged (
      NonterminalSymbol newNonterminalSymbol )
  {
    NonterminalSymbol checkedNonterminalSymbol = checkParsedObject ( newNonterminalSymbol );
    NonterminalSymbolChangedListener [] listeners = this.listenerList
        .getListeners ( NonterminalSymbolChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].nonterminalSymbolChanged ( checkedNonterminalSymbol );
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
    return checkParsedObject ( nonterminalSymbol );
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
}
