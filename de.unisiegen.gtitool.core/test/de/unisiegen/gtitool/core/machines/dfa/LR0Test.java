package de.unisiegen.gtitool.core.machines.dfa;

import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.DefaultProduction;
import de.unisiegen.gtitool.core.entities.DefaultProductionWord;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.cfg.LR0Grammar;


/**
 * Test the LR0 automaton
 *
 */
public class LR0Test
{
  /**
   * main method
   *
   * @param arguments
   */
  public static void main ( final String [] arguments )
  {
    NonterminalSymbol E = new DefaultNonterminalSymbol ( "E" ); //$NON-NLS-1$

    NonterminalSymbolSet nonterminalSet = new DefaultNonterminalSymbolSet ();

    NonterminalSymbol T = new DefaultNonterminalSymbol ( "T" ); //$NON-NLS-1$
    NonterminalSymbol F = new DefaultNonterminalSymbol ( "F" ); //$NON-NLS-1$

    try
    {
      nonterminalSet.add ( E );
      nonterminalSet.add ( T );
      nonterminalSet.add ( F );
    }
    catch ( NonterminalSymbolSetException e )
    {
      return;
    }

    TerminalSymbolSet terminalSet = new DefaultTerminalSymbolSet ();

    TerminalSymbol id = new DefaultTerminalSymbol ( "id" ); //$NON-NLS-1$
    TerminalSymbol lparen = new DefaultTerminalSymbol ( "(" ); //$NON-NLS-1$
    TerminalSymbol rparen = new DefaultTerminalSymbol ( ")" ); //$NON-NLS-1$
    TerminalSymbol plus = new DefaultTerminalSymbol ( "+" ); //$NON-NLS-1$
    TerminalSymbol multiplies = new DefaultTerminalSymbol ( "*" ); //$NON-NLS-1$

    try
    {
      terminalSet.add ( id );
      terminalSet.add ( lparen );
      terminalSet.add ( rparen );
      terminalSet.add ( plus );
      terminalSet.add ( multiplies );
    }
    catch ( TerminalSymbolSetException e )
    {
      return;
    }

    LR0Grammar grammar = new LR0Grammar ( nonterminalSet, terminalSet, E );

    grammar.addProduction ( new DefaultProduction ( E,
        new DefaultProductionWord ( E, plus, T ) ) );

    grammar.addProduction ( new DefaultProduction ( E,
        new DefaultProductionWord ( T ) ) );

    grammar.addProduction ( new DefaultProduction ( T,
        new DefaultProductionWord ( T, multiplies, F ) ) );

    grammar.addProduction ( new DefaultProduction ( T,
        new DefaultProductionWord ( F ) ) );

    grammar.addProduction ( new DefaultProduction ( F,
        new DefaultProductionWord ( id ) ) );

    grammar.addProduction ( new DefaultProduction ( F,
        new DefaultProductionWord ( lparen, E, rparen ) ) );

    try
    {
      LR0 automaton = new LR0(grammar);
      for(State state : automaton.getState())
        System.out.println(state.toString());
    }
    catch(AlphabetException e)
    {
      e.printStackTrace();
    }

  }
}
