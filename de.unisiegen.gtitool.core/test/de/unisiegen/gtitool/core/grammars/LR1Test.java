package de.unisiegen.gtitool.core.grammars;


import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.DefaultProduction;
import de.unisiegen.gtitool.core.entities.DefaultProductionWord;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.LR1Item;
import de.unisiegen.gtitool.core.entities.LR1ItemSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.cfg.LR1Grammar;


/**
 * TODO
 */
public class LR1Test
{

  public static void main ( String [] arguments )
  {
    NonterminalSymbol E = new DefaultNonterminalSymbol ( "E" );

    NonterminalSymbolSet nonterminalSet = new DefaultNonterminalSymbolSet ();

    NonterminalSymbol T = new DefaultNonterminalSymbol ( "T" );
    NonterminalSymbol F = new DefaultNonterminalSymbol ( "F" );

    try
    {
      nonterminalSet.add ( E ); // ??
      nonterminalSet.add ( T );
      nonterminalSet.add ( F );
    }
    catch ( NonterminalSymbolSetException e )
    {
      return;
    }

    TerminalSymbolSet terminalSet = new DefaultTerminalSymbolSet ();

    TerminalSymbol id = new DefaultTerminalSymbol ( "id" );
    TerminalSymbol lparen = new DefaultTerminalSymbol ( "(" );
    TerminalSymbol rparen = new DefaultTerminalSymbol ( ")" );
    TerminalSymbol plus = new DefaultTerminalSymbol ( "+" );
    TerminalSymbol multiplies = new DefaultTerminalSymbol ( "*" );

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

    LR1Grammar grammar = new LR1Grammar ( nonterminalSet, terminalSet, E );

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

    LR1ItemSet closure0 = grammar.closure ( grammar.startClosure () );

    System.out.println ( "closure(start())" );
    for ( LR1Item item : closure0 )
      System.out.println ( item.toString () );

    LR1ItemSet move0E = grammar.move ( closure0, E );

    System.out.println ( "move(0, E)" );
    for ( LR1Item item : move0E )
      System.out.println ( item.toString () );

    LR1ItemSet closure1 = grammar.closure ( move0E );

    System.out.println ( "closure(move(0, E)) = 1" );
    for ( LR1Item item : closure1 )
      System.out.println ( item.toString () );

    LR1ItemSet move0T = grammar.move ( closure0, T );

    System.out.println ( "move(0, T)" );
    for ( LR1Item item : move0T )
      System.out.println ( item.toString () );

    LR1ItemSet closure2 = grammar.closure ( move0T );

    System.out.println ( "closure(move(0, T)) = 2" );
    for ( LR1Item item : closure2 )
      System.out.println ( item.toString () );
  }
}
