package de.unisiegen.gtitool.core.machines.pda;


import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.DefaultParsingTable;
import de.unisiegen.gtitool.core.entities.DefaultProduction;
import de.unisiegen.gtitool.core.entities.DefaultProductionWord;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.cfg.DefaultCFG;


/**
 * test class for the parsing table
 */
@SuppressWarnings (
{ "all" } )
public class ParsingTableTest
{

  /**
   * entry point of the test program
   * 
   * @param arguments command line arguments
   */
  public static void main ( final String [] arguments )
  {
    NonterminalSymbol E = new DefaultNonterminalSymbol ( "E" ); //$NON-NLS-1$
    NonterminalSymbol E_ = new DefaultNonterminalSymbol ( "E'" ); //$NON-NLS-1$

    NonterminalSymbolSet nonterminalSet = new DefaultNonterminalSymbolSet ();

    NonterminalSymbol T = new DefaultNonterminalSymbol ( "T" ); //$NON-NLS-1$
    NonterminalSymbol T_ = new DefaultNonterminalSymbol ( "T'" ); //$NON-NLS-1$
    NonterminalSymbol F = new DefaultNonterminalSymbol ( "F" ); //$NON-NLS-1$

    try
    {
      nonterminalSet.add ( E );
      nonterminalSet.add ( E_);
      nonterminalSet.add ( T );
      nonterminalSet.add ( T_ );
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
    TerminalSymbol epsilon = new DefaultTerminalSymbol ("");

    try
    {
      terminalSet.add ( id );
      terminalSet.add ( lparen );
      terminalSet.add ( rparen );
      terminalSet.add ( plus );
      terminalSet.add ( multiplies );
      terminalSet.add ( DefaultTerminalSymbol.EndMarker );
    }
    catch ( TerminalSymbolSetException e )
    {
      return;
    }

    DefaultCFG grammar = new DefaultCFG ( nonterminalSet, terminalSet, E );

    grammar.addProduction ( new DefaultProduction ( E,
        new DefaultProductionWord ( T, E_ ) ) );

    grammar.addProduction ( new DefaultProduction ( E_,
        new DefaultProductionWord ( plus, T, E_ ) ) );
    
    grammar.addProduction ( new DefaultProduction ( E_,
        new DefaultProductionWord ( epsilon ) ) );

    grammar.addProduction ( new DefaultProduction ( T,
        new DefaultProductionWord ( F, T_ ) ) );

    grammar.addProduction ( new DefaultProduction ( T_,
        new DefaultProductionWord ( multiplies, F, T_ ) ) );
    
    grammar.addProduction ( new DefaultProduction ( T_,
        new DefaultProductionWord ( epsilon ) ) );

    grammar.addProduction ( new DefaultProduction ( F,
        new DefaultProductionWord ( id ) ) );

    grammar.addProduction ( new DefaultProduction ( F,
        new DefaultProductionWord ( lparen, E, rparen ) ) );

    try
    {
      DefaultParsingTable parsingTable = new DefaultParsingTable ( grammar );
      
      System.out.println(grammar.follow ( E_ ));
      
      for ( NonterminalSymbol ns : grammar.getNonterminalSymbolSet () )
        for ( TerminalSymbol ts : grammar.getTerminalSymbolSet () )
        {
          TreeSet < Production > prods = parsingTable.get ( ns, ts );
          if ( prods.isEmpty () )
            System.out.println ( "M[" + ns.getName () + "," + ts.getName ()
                + "] is empty" );
          else
            System.out.println ( "M[" + ns.getName () + "," + ts.getName ()
                + "] = " + prods.toString () );
        }
    }
    catch ( GrammarInvalidNonterminalException exc )
    {
      exc.printStackTrace ();
    }
    catch ( TerminalSymbolSetException exc )
    {
      exc.printStackTrace ();
    }
  }
}
