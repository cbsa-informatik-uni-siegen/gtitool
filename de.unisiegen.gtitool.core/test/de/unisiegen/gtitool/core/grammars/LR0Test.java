package de.unisiegen.gtitool.core.grammars;


import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.DefaultProduction;
import de.unisiegen.gtitool.core.entities.DefaultProductionWord;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.LR0Item;
import de.unisiegen.gtitool.core.entities.LR0ItemSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.cfg.LR0Grammar;


/**
 * TODO
 */
public class LR0Test
{

  /**
   * The test grammar for arithmetic expressions containing +, * and (, )
   * 
   * @return The grammar
   * @throws TerminalSymbolSetException
   * @throws NonterminalSymbolSetException
   */
  public static LR0Grammar testGrammar () throws TerminalSymbolSetException,
      NonterminalSymbolSetException
  {
    NonterminalSymbol E = new DefaultNonterminalSymbol ( "E" ); //$NON-NLS-1$

    NonterminalSymbolSet nonterminalSet = new DefaultNonterminalSymbolSet ();

    NonterminalSymbol T = new DefaultNonterminalSymbol ( "T" ); //$NON-NLS-1$
    NonterminalSymbol F = new DefaultNonterminalSymbol ( "F" ); //$NON-NLS-1$

    try
    {
      nonterminalSet.add ( E ); // ??
      nonterminalSet.add ( T );
      nonterminalSet.add ( F );
    }
    catch ( NonterminalSymbolSetException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
    }

    TerminalSymbolSet terminalSet = new DefaultTerminalSymbolSet ();

    TerminalSymbol id = new DefaultTerminalSymbol ( "id" ); //$NON-NLS-1$
    TerminalSymbol lparen = new DefaultTerminalSymbol ( "(" ); //$NON-NLS-1$
    TerminalSymbol rparen = new DefaultTerminalSymbol ( ")" ); //$NON-NLS-1$
    TerminalSymbol plus = new DefaultTerminalSymbol ( "+" ); //$NON-NLS-1$
    TerminalSymbol multiplies = new DefaultTerminalSymbol ( "*" ); //$NON-NLS-1$

    terminalSet.add ( id );
    terminalSet.add ( lparen );
    terminalSet.add ( rparen );
    terminalSet.add ( plus );
    terminalSet.add ( multiplies );

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

    return grammar;
  }


  /**
   * TODO
   * 
   * @param arguments
   */
  public static void main ( String [] arguments )
  {
    LR0Grammar grammar;
    try
    {
      grammar = testGrammar ();
    }
    catch ( Exception e )
    {
      e.printStackTrace ();

      System.exit ( 1 );

      return;
    }

    LR0ItemSet closure0 = grammar.closure ( grammar.startProduction () );

    System.out.println ( "closure(start())" ); //$NON-NLS-1$
    for ( LR0Item item : closure0 )
      System.out.println ( item.toString () );

    LR0ItemSet move0E = grammar.move ( closure0, grammar
        .getNonterminalSymbolSet ().get ( "E" ) ); //$NON-NLS-1$

    System.out.println ( "move(0, E)" ); //$NON-NLS-1$
    for ( LR0Item item : move0E )
      System.out.println ( item.toString () );

    LR0ItemSet closure1 = grammar.closure ( move0E );

    System.out.println ( "closure(move(0, E)) = 1" ); //$NON-NLS-1$
    for ( LR0Item item : closure1 )
      System.out.println ( item.toString () );

    LR0ItemSet move0T = grammar.move ( closure0, grammar
        .getNonterminalSymbolSet ().get ( "T" ) ); //$NON-NLS-1$

    System.out.println ( "move(0, T)" ); //$NON-NLS-1$
    for ( LR0Item item : move0T )
      System.out.println ( item.toString () );

    LR0ItemSet closure2 = grammar.closure ( move0T );

    System.out.println ( "closure(move(0, T)) = 2" ); //$NON-NLS-1$
    for ( LR0Item item : closure2 )
      System.out.println ( item.toString () );

    LR0ItemSet move0lparen = grammar.move ( closure0, grammar
        .getTerminalSymbolSet ().get ( "(" ) ); //$NON-NLS-1$

    System.out.println ( "move(0, ()" ); //$NON-NLS-1$
    for ( LR0Item item : move0lparen )
      System.out.println ( item.toString () );

    LR0ItemSet closure3 = grammar.closure ( move0lparen );

    System.out.println ( "closure(move(0, ()) = 3" ); //$NON-NLS-1$
    for ( LR0Item item : closure3 )
      System.out.println ( item.toString () );
  }
}
