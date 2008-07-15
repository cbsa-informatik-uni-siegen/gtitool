package de.unisiegen.gtitool.ui.redoundo;


import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.Grammar;


/**
 * Representation of {@link RedoUndoItem} for edit symbols action.
 * 
 * @author Benjamin Mies
 * @version $Id: StateAddedItem.java 960 2008-06-03 23:32:54Z fehler $
 */
public final class GrammarSymbolsChangedItem extends RedoUndoItem
{

  /**
   * The {@link NonterminalSymbolSet}.
   */
  private NonterminalSymbolSet nonTerminalSymbols;


  /**
   * The {@link TerminalSymbolSet}.
   */
  private TerminalSymbolSet terminalSymbols;


  /**
   * The added symbols for the {@link TerminalSymbolSet}.
   */
  private TreeSet < TerminalSymbol > terminalSymbolsToAdd = new TreeSet < TerminalSymbol > ();


  /**
   * The removed symbols for the {@link TerminalSymbolSet}.
   */
  private TreeSet < TerminalSymbol > terminalSymbolsToRemove = new TreeSet < TerminalSymbol > ();


  /**
   * The added symbols for the {@link NonterminalSymbolSet}.
   */
  private TreeSet < NonterminalSymbol > nonTerminalSymbolsToAdd = new TreeSet < NonterminalSymbol > ();


  /**
   * The removed symbols for the {@link NonterminalSymbolSet}.
   */
  private TreeSet < NonterminalSymbol > nonTerminalSymbolsToRemove = new TreeSet < NonterminalSymbol > ();


  /**
   * The old start {@link Symbol}.
   */
  private NonterminalSymbol oldStartSymbol;


  /**
   * The new start {@link Symbol}.
   */
  private NonterminalSymbol newStartSymbol;


  /**
   * The {@link Grammar}.
   */
  private Grammar grammar;


  /**
   * Allocates a new {@link GrammarSymbolsChangedItem}.
   * 
   * @param grammar The {@link Grammar}.
   * @param nonTerminalSymbols The new {@link NonterminalSymbolSet}.
   * @param terminalSymbols The new {@link TerminalSymbolSet}
   * @param oldStartSymbol The old start {@link Symbol}.
   * @param newStartSymbol The new start {@link Symbol}.
   */
  public GrammarSymbolsChangedItem ( Grammar grammar,
      NonterminalSymbolSet nonTerminalSymbols,
      TerminalSymbolSet terminalSymbols, NonterminalSymbol oldStartSymbol,
      NonterminalSymbol newStartSymbol )
  {
    super ();
    this.grammar = grammar;
    this.nonTerminalSymbols = grammar.getNonterminalSymbolSet ();
    this.terminalSymbols = grammar.getTerminalSymbolSet ();
    performNonterminalSymbolCalculation ( this.nonTerminalSymbols,
        nonTerminalSymbols );
    performTerminalSymbolCalculation ( this.terminalSymbols, terminalSymbols );
    this.oldStartSymbol = oldStartSymbol;
    this.newStartSymbol = newStartSymbol;
  }


  /**
   * Preforms the {@link NonterminalSymbol} calculation.
   * 
   * @param oldNonterminalSymbols The old {@link NonterminalSymbolSet}.
   * @param newNonterminalSymbols The new {@link NonterminalSymbolSet}.
   */
  private final void performNonterminalSymbolCalculation (
      NonterminalSymbolSet oldNonterminalSymbols,
      NonterminalSymbolSet newNonterminalSymbols )
  {
    for ( NonterminalSymbol current : newNonterminalSymbols )
    {
      if ( !oldNonterminalSymbols.contains ( current ) )
      {
        this.nonTerminalSymbolsToAdd.add ( current );
      }
    }
    for ( NonterminalSymbol current : oldNonterminalSymbols )
    {
      if ( !newNonterminalSymbols.contains ( current ) )
      {
        this.nonTerminalSymbolsToRemove.add ( current );
      }
    }
  }


  /**
   * Performs the {@link TerminalSymbol} calculation.
   * 
   * @param oldTerminalSymbols The old {@link TerminalSymbolSet}.
   * @param newTerminalSymbols The new {@link TerminalSymbolSet}.
   */
  private final void performTerminalSymbolCalculation (
      TerminalSymbolSet oldTerminalSymbols, TerminalSymbolSet newTerminalSymbols )
  {
    for ( TerminalSymbol current : newTerminalSymbols )
    {
      if ( !oldTerminalSymbols.contains ( current ) )
      {
        this.terminalSymbolsToAdd.add ( current );
      }
    }
    for ( TerminalSymbol current : oldTerminalSymbols )
    {
      if ( !newTerminalSymbols.contains ( current ) )
      {
        this.terminalSymbolsToRemove.add ( current );
      }
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see RedoUndoItem#redo()
   */
  @Override
  public final void redo ()
  {
    try
    {
      this.grammar.getNonterminalSymbolSet ().add (
          this.nonTerminalSymbolsToAdd );
      this.grammar.getNonterminalSymbolSet ().remove (
          this.nonTerminalSymbolsToRemove );
      this.grammar.getTerminalSymbolSet ().add ( this.terminalSymbolsToAdd );
      this.grammar.getTerminalSymbolSet ().remove (
          this.terminalSymbolsToRemove );
      this.grammar.setStartSymbol ( this.newStartSymbol );
    }
    catch ( NonterminalSymbolSetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( TerminalSymbolSetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see RedoUndoItem#undo()
   */
  @Override
  public void undo ()
  {
    try
    {
      this.grammar.getNonterminalSymbolSet ().remove (
          this.nonTerminalSymbolsToAdd );
      this.grammar.getNonterminalSymbolSet ().add (
          this.nonTerminalSymbolsToRemove );
      this.grammar.getTerminalSymbolSet ().remove ( this.terminalSymbolsToAdd );
      this.grammar.getTerminalSymbolSet ().add ( this.terminalSymbolsToRemove );
      this.grammar.setStartSymbol ( this.oldStartSymbol );
    }
    catch ( NonterminalSymbolSetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( TerminalSymbolSetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }
}
