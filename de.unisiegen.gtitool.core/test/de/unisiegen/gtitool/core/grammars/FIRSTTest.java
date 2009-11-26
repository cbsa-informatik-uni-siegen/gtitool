package de.unisiegen.gtitool.core.grammars;


import de.unisiegen.gtitool.core.entities.DefaultFirstSet;
import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.DefaultProduction;
import de.unisiegen.gtitool.core.entities.DefaultProductionWord;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.cfg.DefaultCFG;


/**
 * TODO
 */
public class FIRSTTest
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

    DefaultCFG grammar = new DefaultCFG ( nonterminalSet, terminalSet, E );

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

    DefaultFirstSet fs;
    try
    {
      fs = ( DefaultFirstSet ) grammar.first ( grammar
          .getProductionAt ( 0 ).getProductionWord () );
      printFirstSet(fs);
    }
    catch ( GrammarInvalidNonterminalException exc )
    {
      exc.printStackTrace();
    }
  }
  
  private static void printFirstSet(final DefaultFirstSet fs)
  {
    for(TerminalSymbol ts : fs)
    {
      DefaultTerminalSymbol dts = (DefaultTerminalSymbol)ts;
      System.out.println(dts);
    }
  }
}
