package de.unisiegen.gtitool.core.grammars;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.DefaultProduction;
import de.unisiegen.gtitool.core.entities.DefaultProductionWord;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.grammars.cfg.DefaultCFG;


/**
 * TODO
 */
public class GrammarTest
{

  /**
   * TODO
   */
  public GrammarTest ()
  {

    try
    {
      DefaultNonterminalSymbolSet nonTerminalSet = new DefaultNonterminalSymbolSet ();
      DefaultNonterminalSymbol E = new DefaultNonterminalSymbol ( "E" );
      nonTerminalSet.add ( E );
      DefaultNonterminalSymbol F = new DefaultNonterminalSymbol ( "F" );
      nonTerminalSet.add ( F );
      DefaultNonterminalSymbol T = new DefaultNonterminalSymbol ( "T" );
      nonTerminalSet.add ( T );
      DefaultTerminalSymbolSet terminalSet = new DefaultTerminalSymbolSet ();
      DefaultTerminalSymbol plus = new DefaultTerminalSymbol ( "+" );
      DefaultTerminalSymbol star = new DefaultTerminalSymbol ( "*" );
      DefaultTerminalSymbol id = new DefaultTerminalSymbol ( "id" );
      DefaultTerminalSymbol lbrace = new DefaultTerminalSymbol ( "(" );
      DefaultTerminalSymbol rbrace = new DefaultTerminalSymbol ( ")" );
      terminalSet.add ( plus );
      terminalSet.add ( star );
      terminalSet.add ( id );
      terminalSet.add ( lbrace );
      terminalSet.add ( rbrace );
      DefaultCFG cfg = new DefaultCFG ( nonTerminalSet, terminalSet, E );
      cfg.addProduction ( new DefaultProduction ( E, new DefaultProductionWord (
          E, plus, T ) ) );
      cfg.addProduction ( new DefaultProduction ( E, new DefaultProductionWord (
          T ) ) );
      cfg.addProduction ( new DefaultProduction ( T, new DefaultProductionWord (
          T, star, F ) ) );
      cfg.addProduction ( new DefaultProduction ( T, new DefaultProductionWord (
          F ) ) );
      cfg.addProduction ( new DefaultProduction ( F, new DefaultProductionWord (
          lbrace, E, rbrace ) ) );
      cfg.addProduction ( new DefaultProduction ( F, new DefaultProductionWord (
          id ) ) );
      System.err.println ( "Nonterminals: " + cfg.getNonterminalSymbolSet () );
      System.err.println ( "Productions:" );
      for ( Production p : cfg.getProduction () )
      {
        System.err.println ( p );
      }

      CFG newCFG = eleminateDirectLeftRecursion ( cfg );
      System.err.println ( "New Nonterminals: "
          + newCFG.getNonterminalSymbolSet () );
      System.err.println ( "New Productions:" );
      for ( Production p : newCFG.getProduction () )
      {
        System.err.println ( p );
      }
    }
    catch ( NonterminalSymbolSetException exc )
    {
      exc.printStackTrace ();
    }
    catch ( TerminalSymbolSetException exc )
    {
      exc.printStackTrace ();
    }
  }


  private ArrayList < Production > getDirectLeftRecursion (
      NonterminalSymbol s, CFG cfg )
  {
    ArrayList < Production > productions = new ArrayList < Production > ();
    for ( Production p : cfg.getProductionForNonTerminal ( s ) )
    {
      if ( p.getProductionWord ().get ( 0 ).equals ( s ) )
      {
        productions.add ( p );
      }
    }
    return productions;
  }


  private CFG eleminateDirectLeftRecursion ( CFG cfg )
      throws NonterminalSymbolSetException
  {
    NonterminalSymbolSet nonterminals = new DefaultNonterminalSymbolSet ();
    ArrayList < Production > productions = new ArrayList < Production > ();

    for ( NonterminalSymbol s : cfg.getNonterminalSymbolSet () )
    {
      nonterminals.add ( s );
      ArrayList < Production > leftRecursions = getDirectLeftRecursion ( s, cfg );
      if ( leftRecursions.isEmpty () )
      {
        productions.addAll ( cfg.getProductionForNonTerminal ( s ) );
      }
      else
      {
        DefaultNonterminalSymbol otherS = new DefaultNonterminalSymbol ( s
            .getName ()
            + "1" );
        nonterminals.add ( otherS );
        ArrayList < Production > otherProductions = new ArrayList < Production > ();
        otherProductions.addAll ( cfg.getProductionForNonTerminal ( s ) );
        otherProductions.removeAll ( leftRecursions );
        for ( Production p : otherProductions )
        {
          p.getProductionWord ().add ( otherS );
          productions.add ( p );
        }
        for ( Production p : leftRecursions )
        {
          p.setNonterminalSymbol ( otherS );
          p.getProductionWord ().get ().remove ( 0 );
          p.getProductionWord ().add ( otherS );
          productions.add ( p );
        }
        productions.add ( new DefaultProduction ( otherS,
            new DefaultProductionWord () ) );

      }
    }
    CFG newCFG = new DefaultCFG ( nonterminals, cfg.getTerminalSymbolSet (),
        cfg.getStartSymbol () );
    newCFG.setProductions ( productions );
    return newCFG;
  }


  /**
   * TODO
   * 
   * @param args
   */
  public static void main ( String [] args )
  {
    new GrammarTest ();
  }

}
