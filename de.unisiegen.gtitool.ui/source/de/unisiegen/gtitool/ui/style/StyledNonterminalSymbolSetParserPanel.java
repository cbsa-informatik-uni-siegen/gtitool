package de.unisiegen.gtitool.ui.style;


import java.util.ArrayList;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.listener.NonterminalSymbolSetChangedListener;
import de.unisiegen.gtitool.core.parser.exceptions.ParserException;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.core.parser.nonterminalsymbolset.NonterminalSymbolSetParseable;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The styled {@link NonterminalSymbolSet} panel class.
 * 
 * @author Christian Fehler
 * @version $Id: StyledNonterminalSymbolSetParserPanel.java 532 2008-02-04
 *          23:54:55Z fehler $
 */
public final class StyledNonterminalSymbolSetParserPanel extends
    StyledParserPanel < NonterminalSymbolSet >
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -349502367082489237L;


  /**
   * The parsed {@link NonterminalSymbolSet} can not contain this
   * {@link NonterminalSymbol}s.
   */
  private TreeSet < NonterminalSymbol > notRemoveableNonterminalSymbols = null;


  /**
   * Every {@link NonterminalSymbol} in the {@link NonterminalSymbolSet} can not
   * be be in the {@link TerminalSymbolSet}.
   */
  private TerminalSymbolSet terminalSymbolSet = null;


  /**
   * The start {@link NonterminalSymbol}.
   */
  private NonterminalSymbol startNonterminalSymbol;


  /**
   * Allocates a new {@link StyledNonterminalSymbolSetParserPanel}.
   */
  public StyledNonterminalSymbolSetParserPanel ()
  {
    super ( new NonterminalSymbolSetParseable () );
    super
        .addParseableChangedListener ( new ParseableChangedListener < NonterminalSymbolSet > ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void parseableChanged (
              NonterminalSymbolSet newNonterminalSymbolSet )
          {
            fireNonterminalSymbolSetChanged ( newNonterminalSymbolSet );
          }
        } );
  }


  /**
   * Adds the given {@link NonterminalSymbolSetChangedListener}.
   * 
   * @param listener The {@link NonterminalSymbolSetChangedListener}.
   */
  public final synchronized void addNonterminalSymbolSetChangedListener (
      NonterminalSymbolSetChangedListener listener )
  {
    this.listenerList
        .add ( NonterminalSymbolSetChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StyledParserPanel#checkParsedObject(Entity)
   */
  @Override
  protected final NonterminalSymbolSet checkParsedObject (
      NonterminalSymbolSet nonterminalSymbolSet )
  {
    ArrayList < ScannerException > exceptionList = new ArrayList < ScannerException > ();

    if ( ( this.terminalSymbolSet != null ) && ( nonterminalSymbolSet != null ) )
    {
      for ( NonterminalSymbol currentNonterminal : nonterminalSymbolSet )
      {
        for ( TerminalSymbol currentTerminal : this.terminalSymbolSet )
        {
          if ( currentNonterminal.getName ().equals (
              currentTerminal.getName () ) )
          {
            exceptionList.add ( new ParserException ( currentNonterminal
                .getParserOffset ().getStart (), currentNonterminal
                .getParserOffset ().getEnd (), Messages.getString (
                "TerminalPanel.AlreadyTerminalSymbol", //$NON-NLS-1$
                currentTerminal.getName (), this.terminalSymbolSet ) ) );
          }
        }
      }
    }

    if ( ( this.notRemoveableNonterminalSymbols != null )
        && ( nonterminalSymbolSet != null ) )
    {
      for ( NonterminalSymbol current : this.notRemoveableNonterminalSymbols )
      {
        if ( !nonterminalSymbolSet.contains ( current ) )
        {
          exceptionList.add ( new ParserException ( current.getParserOffset ()
              .getStart (), current.getParserOffset ().getEnd (), Messages
              .getString ( "TerminalPanel.SymbolUsed", current ) ) ); //$NON-NLS-1$
        }
      }
    }

    if ( exceptionList.size () > 0 )
    {
      setException ( exceptionList );
      return null;
    }

    return nonterminalSymbolSet;
  }


  /**
   * Let the listeners know that the {@link NonterminalSymbolSet} has changed.
   * 
   * @param newNonterminalSymbolSet The new {@link NonterminalSymbolSet}.
   */
  private final void fireNonterminalSymbolSetChanged (
      NonterminalSymbolSet newNonterminalSymbolSet )
  {
    NonterminalSymbolSet checkedNonterminalSymbolSet = checkParsedObject ( newNonterminalSymbolSet );
    NonterminalSymbolSetChangedListener [] listeners = this.listenerList
        .getListeners ( NonterminalSymbolSetChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ]
          .nonterminalSymbolSetChanged ( checkedNonterminalSymbolSet );
    }
  }


  /**
   * Returns the start {@link NonterminalSymbol}.
   * 
   * @return The start {@link NonterminalSymbol}.
   * @see #startNonterminalSymbol
   */
  public final NonterminalSymbol getStartNonterminalSymbol ()
  {
    return this.startNonterminalSymbol;
  }


  /**
   * {@inheritDoc}
   * 
   * @see StyledParserPanel#parse()
   */
  @Override
  public final NonterminalSymbolSet parse ()
  {
    NonterminalSymbolSet nonterminalSymbolSet = ( NonterminalSymbolSet ) super
        .parse ();
    return checkParsedObject ( nonterminalSymbolSet );
  }


  /**
   * Removes the given {@link NonterminalSymbolSetChangedListener}.
   * 
   * @param listener The {@link NonterminalSymbolSetChangedListener}.
   */
  public final synchronized void removeNonterminalSymbolSetChangedListener (
      NonterminalSymbolSetChangedListener listener )
  {
    this.listenerList.remove ( NonterminalSymbolSetChangedListener.class,
        listener );
  }


  /**
   * Sets the {@link NonterminalSymbol}s which should be highlighted.
   * 
   * @param nonterminalSymbols The {@link NonterminalSymbol}s which should be
   *          highlighted.
   */
  public final void setHighlightedNonterminalSymbol (
      Iterable < NonterminalSymbol > nonterminalSymbols )
  {
    setHighlightedParseableEntity ( nonterminalSymbols );
  }


  /**
   * Sets the {@link NonterminalSymbol}s which should be highlighted.
   * 
   * @param nonterminalSymbols The {@link NonterminalSymbol}s which should be
   *          highlighted.
   */
  public final void setHighlightedNonterminalSymbol (
      NonterminalSymbol ... nonterminalSymbols )
  {
    Entity [] entities = new Entity [ nonterminalSymbols.length ];
    for ( int i = 0 ; i < nonterminalSymbols.length ; i++ )
    {
      entities [ i ] = nonterminalSymbols [ i ];
    }
    setHighlightedParseableEntity ( entities );
  }


  /**
   * Sets the {@link NonterminalSymbol} which should be highlighted.
   * 
   * @param nonterminalSymbol The {@link NonterminalSymbol} which should be
   *          highlighted.
   */
  public final void setHighlightedNonterminalSymbol (
      NonterminalSymbol nonterminalSymbol )
  {
    setHighlightedParseableEntity ( nonterminalSymbol );
  }


  /**
   * Sets the {@link NonterminalSymbol}s which should not be removeable.
   * 
   * @param notRemoveableNonterminalSymbols The {@link NonterminalSymbol}s
   *          which should not be removeable.
   * @see #notRemoveableNonterminalSymbols
   */
  public final void setNotRemoveableNonterminalSymbols (
      TreeSet < NonterminalSymbol > notRemoveableNonterminalSymbols )
  {
    this.notRemoveableNonterminalSymbols = notRemoveableNonterminalSymbols;
  }


  /**
   * Sets the start {@link NonterminalSymbol}.
   * 
   * @param startNonterminalSymbol The start {@link NonterminalSymbol} to set.
   */
  public final void setStartNonterminalSymbol (
      NonterminalSymbol startNonterminalSymbol )
  {
    this.startNonterminalSymbol = startNonterminalSymbol;

    // Set the overwritten style
    clearOverwrittenStyle ( Style.START_NONTERMINAL_SYMBOL );
    if ( this.startNonterminalSymbol != null )
    {
      addOverwrittenStyle ( this.startNonterminalSymbol.getName (),
          Style.START_NONTERMINAL_SYMBOL );
    }
  }


  /**
   * Sets the {@link TerminalSymbolSet}. Every {@link NonterminalSymbol} in the
   * {@link NonterminalSymbolSet} can not be be in the {@link TerminalSymbolSet}.
   * 
   * @param terminalSymbolSet The {@link TerminalSymbolSet} to set.
   */
  public final void setTerminalSymbolSet ( TerminalSymbolSet terminalSymbolSet )
  {
    this.terminalSymbolSet = terminalSymbolSet;
  }
}
