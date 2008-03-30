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
    StyledParserPanel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -349502367082489237L;


  /**
   * Every {@link NonterminalSymbol} in the {@link NonterminalSymbolSet} can not
   * be be in the {@link TerminalSymbolSet}.
   */
  private TerminalSymbolSet terminalSymbolSet = null;


  /**
   * The parsed {@link NonterminalSymbolSet} can not contain this
   * {@link NonterminalSymbol}s.
   */
  private TreeSet < NonterminalSymbol > notRemoveableNonterminalSymbols = null;


  /**
   * Allocates a new {@link StyledNonterminalSymbolSetParserPanel}.
   */
  public StyledNonterminalSymbolSetParserPanel ()
  {
    super ( new NonterminalSymbolSetParseable () );
    super.addParseableChangedListener ( new ParseableChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void parseableChanged ( Object newObject )
      {
        fireNonterminalSymbolSetChanged ( ( NonterminalSymbolSet ) newObject );
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
   * Checks the given {@link NonterminalSymbolSet}.
   * 
   * @param nonterminalSymbolSet The {@link NonterminalSymbolSet} to check.
   * @return The input {@link NonterminalSymbolSet} or null, if a
   *         {@link NonterminalSymbol} in the {@link NonterminalSymbolSet} is in
   *         the {@link TerminalSymbolSet}.
   */
  private final NonterminalSymbolSet checkNonterminalSymbolSet (
      NonterminalSymbolSet nonterminalSymbolSet )
  {
    ArrayList < ScannerException > exceptionList = new ArrayList < ScannerException > ();

    if ( this.terminalSymbolSet != null && nonterminalSymbolSet != null )
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

    if ( this.notRemoveableNonterminalSymbols != null
        && nonterminalSymbolSet != null )
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

    NonterminalSymbolSet checkedNonterminalSymbolSet = nonterminalSymbolSet;
    if ( exceptionList.size () > 0 )
    {
      checkedNonterminalSymbolSet = null;
      setException ( exceptionList );
    }

    return checkedNonterminalSymbolSet;
  }


  /**
   * Let the listeners know that the {@link NonterminalSymbolSet} has changed.
   * 
   * @param newNonterminalSymbolSet The new {@link NonterminalSymbolSet}.
   */
  private final void fireNonterminalSymbolSetChanged (
      NonterminalSymbolSet newNonterminalSymbolSet )
  {
    NonterminalSymbolSet checkedNonterminalSymbolSet = checkNonterminalSymbolSet ( newNonterminalSymbolSet );
    NonterminalSymbolSetChangedListener [] listeners = this.listenerList
        .getListeners ( NonterminalSymbolSetChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ]
          .nonterminalSymbolSetChanged ( checkedNonterminalSymbolSet );
    }
  }


  /**
   * Returns the {@link NonterminalSymbolSet} for the program text within the
   * document.
   * 
   * @return The {@link NonterminalSymbolSet} for the program text.
   */
  public final NonterminalSymbolSet getNonterminalSymbolSet ()
  {
    try
    {
      NonterminalSymbolSet nonterminalSymbolSet = ( NonterminalSymbolSet ) getParsedObject ();
      return checkNonterminalSymbolSet ( nonterminalSymbolSet );
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
  public final NonterminalSymbolSet parse ()
  {
    NonterminalSymbolSet nonterminalSymbolSet = ( NonterminalSymbolSet ) super
        .parse ();
    return checkNonterminalSymbolSet ( nonterminalSymbolSet );
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
   * Sets the {@link TerminalSymbolSet}. Every {@link NonterminalSymbol} in the
   * {@link NonterminalSymbolSet} can not be be in the {@link TerminalSymbolSet}.
   * 
   * @param terminalSymbolSet The {@link TerminalSymbolSet} to set.
   */
  public final void setTerminalSymbolSet ( TerminalSymbolSet terminalSymbolSet )
  {
    this.terminalSymbolSet = terminalSymbolSet;
  }


  /**
   * Sets the {@link NonterminalSymbolSet} of the document.
   * 
   * @param nonterminalSymbolSet The input {@link NonterminalSymbolSet}.
   */
  public final void setText ( NonterminalSymbolSet nonterminalSymbolSet )
  {
    getEditor ().setText ( nonterminalSymbolSet.toString () );
  }
}
