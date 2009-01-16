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
import de.unisiegen.gtitool.core.entities.ProductionWord;
import de.unisiegen.gtitool.core.entities.ProductionWordMember;
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
  public GrammarTest ( boolean real )
  {
    if ( !real )
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
        cfg.addProduction ( new DefaultProduction ( E,
            new DefaultProductionWord ( E, plus, T ) ) );
        cfg.addProduction ( new DefaultProduction ( E,
            new DefaultProductionWord ( T ) ) );
        cfg.addProduction ( new DefaultProduction ( T,
            new DefaultProductionWord ( T, star, F ) ) );
        cfg.addProduction ( new DefaultProduction ( T,
            new DefaultProductionWord ( F ) ) );
        cfg.addProduction ( new DefaultProduction ( F,
            new DefaultProductionWord ( lbrace, E, rbrace ) ) );
        cfg.addProduction ( new DefaultProduction ( F,
            new DefaultProductionWord ( id ) ) );
        System.err.println ( "Nonterminals: " + cfg.getNonterminalSymbolSet () );
        System.err.println ( "Productions:" );
        for ( Production p : cfg.getProduction () )
        {
          System.err.println ( p );
        }

        CFG newCFG = eliminateDirectLeftRecursion ( E, cfg );
        System.err.println ( "New Nonterminals: "
            + newCFG.getNonterminalSymbolSet () );
        System.err.println ( "New Productions:" );
        for ( Production p : newCFG.getProduction () )
        {
          System.err.println ( p );
        }
        newCFG = eliminateDirectLeftRecursion ( T, newCFG );
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
    else
    {
      try
      {
        DefaultNonterminalSymbolSet nonTerminalSet = new DefaultNonterminalSymbolSet ();
        DefaultNonterminalSymbol S = new DefaultNonterminalSymbol ( "S" );
        nonTerminalSet.add ( S );
        DefaultNonterminalSymbol A = new DefaultNonterminalSymbol ( "A" );
        nonTerminalSet.add ( A );
        DefaultTerminalSymbolSet terminalSet = new DefaultTerminalSymbolSet ();
        DefaultTerminalSymbol a = new DefaultTerminalSymbol ( "a" );
        DefaultTerminalSymbol b = new DefaultTerminalSymbol ( "b" );
        DefaultTerminalSymbol c = new DefaultTerminalSymbol ( "c" );
        DefaultTerminalSymbol d = new DefaultTerminalSymbol ( "d" );
        terminalSet.add ( a );
        terminalSet.add ( b );
        terminalSet.add ( c );
        terminalSet.add ( d );
        DefaultCFG cfg = new DefaultCFG ( nonTerminalSet, terminalSet, S );
        cfg.addProduction ( new DefaultProduction ( S,
            new DefaultProductionWord ( A, a ) ) );
        cfg.addProduction ( new DefaultProduction ( A,
            new DefaultProductionWord ( S, d ) ) );
        cfg.addProduction ( new DefaultProduction ( A,
            new DefaultProductionWord () ) );
        System.err.println ( "Nonterminals: " + cfg.getNonterminalSymbolSet () );
        System.err.println ( "Productions:" );
        for ( Production p : cfg.getProduction () )
        {
          System.err.println ( p );
        }

        System.err.println ( "EpsilonProductions: " );
        for ( Production p : getEpsilonProductions ( cfg ) )
        {
          System.err.println ( p );
        }
        CFG newerCFG = eliminateEpsilonProductions ( cfg );
        System.err.println ( "Newer Nonterminals: "
            + newerCFG.getNonterminalSymbolSet () );
        System.err.println ( "Newer Productions:" );
        for ( Production p : newerCFG.getProduction () )
        {
          System.err.println ( p );
        }
        System.err.println ( "EinheitsProductions: " );
        for ( Production p : getEntityProductions ( newerCFG ) )
        {
          System.err.println ( p );
        }
        CFG newestCFG = eliminateEntityProductions ( newerCFG );
        System.err.println ( "Newest Nonterminals: "
            + newestCFG.getNonterminalSymbolSet () );
        System.err.println ( "Newest Productions:" );
        for ( Production p : newestCFG.getProduction () )
        {
          System.err.println ( p );
        }

        System.err.println ( "After elimination of left recursion" );
        CFG newCFG = eliminateLeftRecursion ( cfg );
        System.err.println ( "Newer Nonterminals: "
            + newCFG.getNonterminalSymbolSet () );
        System.err.println ( "Newer Productions:" );
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
  }


  public CFG eliminateLeftRecursion ( CFG cfg )
      throws NonterminalSymbolSetException
  {
    CFG newCFG = new DefaultCFG ( cfg.getNonterminalSymbolSet (), cfg
        .getTerminalSymbolSet (), cfg.getStartSymbol () );
    for ( Production p : cfg.getProduction () )
    {
      newCFG.addProduction ( p );
    }
    ArrayList < NonterminalSymbol > nonterminals = new ArrayList < NonterminalSymbol > ();
    nonterminals.addAll ( newCFG.getNonterminalSymbolSet ().get () );
    for ( int i = 1 ; i <= nonterminals.size () ; i++ )
    {
      NonterminalSymbol ai = nonterminals.get ( i - 1 );
      System.out.println ( "Ai = " + ai );
      for ( int j = 0 ; j < i - 1 ; j++ )
      {
        NonterminalSymbol aj = nonterminals.get ( j );
        System.out.println ( "Aj = " + aj );
        ArrayList < Production > productions = getProductionsForNonterminals (
            ai, aj, newCFG );
        ArrayList < Production > productionsOfAj = newCFG
            .getProductionForNonTerminal ( aj );
        for ( Production pi : productions )
        {
          newCFG.getProduction ().remove ( pi );
          pi.getProductionWord ().get ().remove ( 0 );
          for ( Production pj : productionsOfAj )
          {
            ProductionWord word = new DefaultProductionWord ( pj
                .getProductionWord ().get () );
            word.add ( pi.getProductionWord ().get () );
            newCFG.addProduction ( new DefaultProduction ( ai, word ) );
          }
        }
      }
      newCFG = eliminateDirectLeftRecursion ( ai, newCFG );
    }
    return newCFG;
  }


  private ArrayList < Production > getProductionsForNonterminals (
      NonterminalSymbol a, NonterminalSymbol b, CFG cfg )
  {
    ArrayList < Production > productions = new ArrayList < Production > ();
    for ( Production p : cfg.getProduction () )
    {
      if ( p.getNonterminalSymbol ().equals ( a )
          && p.getProductionWord ().size () > 0
          && p.getProductionWord ().get ( 0 ).equals ( b ) )
      {
        productions.add ( p );
      }
    }
    return productions;
  }


  /**
   * TODO
   * 
   * @param cfg
   * @return
   */
  public CFG eliminateEntityProductions ( CFG cfg )
  {
    CFG newCFG = new DefaultCFG ( cfg.getNonterminalSymbolSet (), cfg
        .getTerminalSymbolSet (), cfg.getStartSymbol () );
    for ( Production p : cfg.getProduction () )
    {
      newCFG.addProduction ( p );
    }
    ArrayList < Production > entityProductions = getEntityProductions ( cfg );
    for ( Production p : entityProductions )
    {
      NonterminalSymbol a = p.getNonterminalSymbol ();
      NonterminalSymbol b = ( NonterminalSymbol ) p.getProductionWord ().get (
          0 );
      for ( Production p2 : newCFG.getProductionForNonTerminal ( b ) )
      {
        newCFG.addProduction ( new DefaultProduction ( a, p2
            .getProductionWord () ) );
      }
      newCFG.getProduction ().remove ( p );
    }
    if ( !getEntityProductions ( newCFG ).isEmpty () )
    {
      newCFG = eliminateEntityProductions ( newCFG );
    }
    return newCFG;
  }


  private ArrayList < Production > getEntityProductions ( CFG cfg )
  {
    ArrayList < Production > productions = new ArrayList < Production > ();
    for ( Production p : cfg.getProduction () )
    {
      if ( p.getProductionWord ().size () == 1
          && p.getProductionWord ().get ( 0 ) instanceof NonterminalSymbol )
      {
        productions.add ( p );
      }
    }
    return productions;
  }


  private CFG eliminateEpsilonProductions ( CFG cfg )
  {
    CFG newCFG = new DefaultCFG ( cfg.getNonterminalSymbolSet (), cfg
        .getTerminalSymbolSet (), cfg.getStartSymbol () );
    ArrayList < NonterminalSymbol > symbolsToEpsilon = new ArrayList < NonterminalSymbol > ();
    ArrayList < Production > productions = new ArrayList < Production > ();
    productions.addAll ( cfg.getProduction () );
    for ( Production p : getEpsilonProductions ( cfg ) )
    {
      symbolsToEpsilon.add ( p.getNonterminalSymbol () );
      productions.remove ( p );
    }
    for ( Production p : productions )
    {
      newCFG.addProduction ( p );
    }
    for ( NonterminalSymbol n : symbolsToEpsilon )
    {
      for ( Production p : cfg.getProduction () )
      {
        if ( p.getProductionWord ().get ().contains ( n ) )
        {
          ProductionWord word = new DefaultProductionWord ();
          ArrayList < ProductionWordMember > members = new ArrayList < ProductionWordMember > ();
          members.addAll ( p.getProductionWord ().get () );
          members.remove ( n );
          word.add ( members );
          newCFG.addProduction ( new DefaultProduction ( p
              .getNonterminalSymbol (), word ) );
        }
      }
    }

    return newCFG;
  }


  private ArrayList < Production > getEpsilonProductions ( CFG cfg )
  {
    ArrayList < Production > symbols = new ArrayList < Production > ();
    for ( Production p : cfg.getProduction () )
    {
      if ( p.getProductionWord ().equals ( new DefaultProductionWord () ) )
      {
        symbols.add ( p );
      }
    }
    return symbols;
  }


  private ArrayList < Production > getDirectLeftRecursion (
      NonterminalSymbol s, CFG cfg )
  {
    ArrayList < Production > productions = new ArrayList < Production > ();
    for ( Production p : cfg.getProductionForNonTerminal ( s ) )
    {
      if ( p.getProductionWord ().size () > 0
          && p.getProductionWord ().get ( 0 ).equals ( s ) )
      {
        productions.add ( p );
      }
    }
    return productions;
  }


  private ArrayList < Production > getDirectLeftRecursion ( CFG cfg )
  {
    ArrayList < Production > productions = new ArrayList < Production > ();
    for ( NonterminalSymbol s : cfg.getNonterminalSymbolSet () )
    {
      productions.addAll ( getDirectLeftRecursion ( s, cfg ) );
    }
    return productions;
  }


  private CFG eliminateDirectLeftRecursion ( NonterminalSymbol s, CFG cfg )
      throws NonterminalSymbolSetException
  {
    NonterminalSymbolSet nonterminals = new DefaultNonterminalSymbolSet ();
    nonterminals.add ( cfg.getNonterminalSymbolSet () );
    ArrayList < Production > productions = new ArrayList < Production > ();
    ArrayList < Production > leftRecursions = getDirectLeftRecursion ( s, cfg );

    for ( Production p : cfg.getProduction () )
    {
      if ( !p.getNonterminalSymbol ().equals ( s ) )
      {
        productions.add ( p );
      }
    }
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
    CFG newCFG = new DefaultCFG ( nonterminals, cfg.getTerminalSymbolSet (),
        cfg.getStartSymbol () );
    newCFG.setProductions ( productions );
    return newCFG;
  }


  private CFG eliminateDirectLeftRecursion ( CFG cfg )
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
    new GrammarTest ( true );
  }

}
