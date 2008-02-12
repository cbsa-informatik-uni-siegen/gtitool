package de.unisiegen.gtitool.ui.style;


import java.util.ArrayList;

import javax.swing.border.LineBorder;

import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.listener.TerminalSymbolSetChangedListener;
import de.unisiegen.gtitool.core.parser.exceptions.ParserException;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.core.parser.terminalsymbolset.TerminalSymbolSetParseable;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The styled {@link TerminalSymbolSet} panel class.
 * 
 * @author Christian Fehler
 * @version $Id: StyledTerminalSymbolSetParserPanel.java 532 2008-02-04
 *          23:54:55Z fehler $
 */
public final class StyledTerminalSymbolSetParserPanel extends StyledParserPanel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -928492541925565704L;


  /**
   * Every {@link TerminalSymbol} in the {@link TerminalSymbolSet} can not be be
   * in the {@link NonterminalSymbolSet}.
   */
  private NonterminalSymbolSet nonterminalSymbolSet = null;


  /**
   * Allocates a new {@link StyledTerminalSymbolSetParserPanel}.
   */
  public StyledTerminalSymbolSetParserPanel ()
  {
    super ( new TerminalSymbolSetParseable () );
    super.addParseableChangedListener ( new ParseableChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void parseableChanged ( Object newObject )
      {
        fireTerminalSymbolSetChanged ( ( TerminalSymbolSet ) newObject );
      }
    } );
  }


  /**
   * Adds the given {@link TerminalSymbolSetChangedListener}.
   * 
   * @param listener The {@link TerminalSymbolSetChangedListener}.
   */
  public final synchronized void addTerminalSymbolSetChangedListener (
      TerminalSymbolSetChangedListener listener )
  {
    this.listenerList.add ( TerminalSymbolSetChangedListener.class, listener );
  }


  /**
   * Checks the given {@link TerminalSymbolSet}.
   * 
   * @param terminalSymbolSet The {@link TerminalSymbolSet} to check.
   * @return The input {@link TerminalSymbolSet} or null, if a
   *         {@link TerminalSymbol} in the {@link TerminalSymbolSet} is in the
   *         {@link NonterminalSymbolSet}.
   */
  private final TerminalSymbolSet checkTerminalSymbolSet (
      TerminalSymbolSet terminalSymbolSet )
  {
    if ( this.nonterminalSymbolSet == null )
    {
      return terminalSymbolSet;
    }

    TerminalSymbolSet checkedTerminalSymbolSet = terminalSymbolSet;
    if ( checkedTerminalSymbolSet != null )
    {
      ArrayList < ScannerException > exceptionList = new ArrayList < ScannerException > ();
      for ( TerminalSymbol currentTerminal : checkedTerminalSymbolSet )
      {
        for ( NonterminalSymbol currentNonterminal : this.nonterminalSymbolSet )
        {
          if ( currentTerminal.getName ().equals (
              currentNonterminal.getName () ) )
          {
            exceptionList.add ( new ParserException ( currentTerminal
                .getParserOffset ().getStart (), currentTerminal
                .getParserOffset ().getEnd (), Messages.getString (
                "TerminalPanel.AlreadyNonterminalSymbol", //$NON-NLS-1$
                currentTerminal.getName (), this.nonterminalSymbolSet ) ) );
          }
        }
      }
      // Check for exceptions
      if ( exceptionList.size () > 0 )
      {
        checkedTerminalSymbolSet = null;
        this.jScrollPane.setBorder ( new LineBorder ( ERROR_COLOR ) );
        getDocument ().setException ( exceptionList );
      }
    }
    return checkedTerminalSymbolSet;
  }


  /**
   * Let the listeners know that the {@link TerminalSymbolSet} has changed.
   * 
   * @param newTerminalSymbolSet The new {@link TerminalSymbolSet}.
   */
  private final void fireTerminalSymbolSetChanged (
      TerminalSymbolSet newTerminalSymbolSet )
  {
    TerminalSymbolSet checkedTerminalSymbolSet = checkTerminalSymbolSet ( newTerminalSymbolSet );
    TerminalSymbolSetChangedListener [] listeners = this.listenerList
        .getListeners ( TerminalSymbolSetChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].terminalSymbolSetChanged ( checkedTerminalSymbolSet );
    }
  }


  /**
   * Returns the {@link TerminalSymbolSet} for the program text within the
   * document.
   * 
   * @return The {@link TerminalSymbolSet} for the program text.
   */
  public final TerminalSymbolSet getTerminalSymbolSet ()
  {
    try
    {
      TerminalSymbolSet terminalSymbolSet = ( TerminalSymbolSet ) getParsedObject ();
      return checkTerminalSymbolSet ( terminalSymbolSet );
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
  public final TerminalSymbolSet parse ()
  {
    TerminalSymbolSet terminalSymbolSet = ( TerminalSymbolSet ) super.parse ();
    return checkTerminalSymbolSet ( terminalSymbolSet );
  }


  /**
   * Removes the given {@link TerminalSymbolSetChangedListener}.
   * 
   * @param listener The {@link TerminalSymbolSetChangedListener}.
   */
  public final synchronized void removeTerminalSymbolSetChangedListener (
      TerminalSymbolSetChangedListener listener )
  {
    this.listenerList
        .remove ( TerminalSymbolSetChangedListener.class, listener );
  }


  /**
   * Sets the {@link TerminalSymbol}s which should be highlighted.
   * 
   * @param terminalSymbols The {@link TerminalSymbol}s which should be
   *          highlighted.
   */
  public final void setHighlightedTerminalSymbol (
      Iterable < TerminalSymbol > terminalSymbols )
  {
    setHighlightedParseableEntity ( terminalSymbols );
  }


  /**
   * Sets the {@link TerminalSymbol}s which should be highlighted.
   * 
   * @param terminalSymbols The {@link TerminalSymbol}s which should be
   *          highlighted.
   */
  public final void setHighlightedTerminalSymbol (
      TerminalSymbol ... terminalSymbols )
  {
    Entity [] entities = new Entity [ terminalSymbols.length ];
    for ( int i = 0 ; i < terminalSymbols.length ; i++ )
    {
      entities [ i ] = terminalSymbols [ i ];
    }
    setHighlightedParseableEntity ( entities );
  }


  /**
   * Sets the {@link TerminalSymbol} which should be highlighted.
   * 
   * @param terminalSymbol The {@link TerminalSymbol} which should be
   *          highlighted.
   */
  public final void setHighlightedTerminalSymbol ( TerminalSymbol terminalSymbol )
  {
    setHighlightedParseableEntity ( terminalSymbol );
  }


  /**
   * Sets the {@link NonterminalSymbolSet}. Every {@link TerminalSymbol} in the
   * {@link TerminalSymbolSet} can not be be in the {@link NonterminalSymbolSet}.
   * 
   * @param nonterminalSymbolSet The {@link NonterminalSymbolSet} to set.
   */
  public final void setNonterminalSymbolSet (
      NonterminalSymbolSet nonterminalSymbolSet )
  {
    this.nonterminalSymbolSet = nonterminalSymbolSet;
  }


  /**
   * Sets the {@link TerminalSymbolSet} of the document.
   * 
   * @param terminalSymbolSet The input {@link TerminalSymbolSet}.
   */
  public final void setTerminalSymbolSet ( TerminalSymbolSet terminalSymbolSet )
  {
    getEditor ().setText ( terminalSymbolSet.toString () );
  }
}
