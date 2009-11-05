package de.unisiegen.gtitool.core.grammars.cfg;


import java.util.ArrayList;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.LR0Item;
import de.unisiegen.gtitool.core.entities.LR0ItemSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.ProductionWordMember;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.machines.dfa.LR0;


/**
 * LRGrammar is a grammar class with some additional methods needed for the
 * construction of LR parsers
 */
public class LR0Grammar extends ExtendedGrammar
{

  /**
   * TODO
   */
  private static final long serialVersionUID = 1L;


  /**
   * Creates a new LRGrammar
   * 
   * @param nonterminalSymbolSet
   * @param terminalSymbolSet
   * @param startSymbol
   */
  public LR0Grammar ( NonterminalSymbolSet nonterminalSymbolSet,
      TerminalSymbolSet terminalSymbolSet, NonterminalSymbol startSymbol )
  {
    super ( nonterminalSymbolSet, terminalSymbolSet, startSymbol );
  }


  /**
   * Calculates the function Closure for a given set of LR0 items
   * 
   * @param items
   * @return The closure
   */
  public LR0ItemSet closure ( final LR0ItemSet items )
  {
    LR0ItemSet ret = new LR0ItemSet ( items );

    for ( int oldSize = ret.size (), newSize = 0 ; newSize != oldSize ; newSize = ret
        .size () )
    {
      oldSize = ret.size ();

      LR0ItemSet currentItems = new LR0ItemSet ( ret );
      for ( LR0Item item : currentItems )
      {
        if ( !item.dotPrecedesNonterminal () )
          continue;

        for ( Production production : this.getProduction () )
        {
          if ( !item.getNonterminalAfterDot ().equals (
              production.getNonterminalSymbol () ) )
            continue;

          final LR0Item newProduction = new LR0Item ( production
              .getNonterminalSymbol (), production.getProductionWord (), 0 );
          if ( !ret.contains ( newProduction ) )
            ret.add ( newProduction );
        }
      }
    }

    return ret;
  }


  /**
   * Calculates the closure of the new start rule a LR0 grammar has
   * 
   * @return The start closure
   */

  public LR0ItemSet startClosure ()
  {
    LR0ItemSet ret = new LR0ItemSet ();
    ret.add ( new LR0Item ( this.getStartProduction().getNonterminalSymbol (),
        this.getStartProduction().getProductionWord (), 0 ) );
    return ret;
  }


  /**
   * Calculates the move function for a given set of LR0items and a terminal or
   * nonterminal symbol the dot should be moved over
   * 
   * @param items
   * @param productionWord
   * @return
   */
  public LR0ItemSet move ( LR0ItemSet items,
      ProductionWordMember productionWord )
  {
    LR0ItemSet ret = new LR0ItemSet ();

    for ( LR0Item item : items )
      if ( item.getProductionWord ().get ( item.getDotPosition () ).equals (
          productionWord ) )
        ret.add ( item.incDot () );

    return ret;
  }

  public LR0 makeLR0Automata() throws AlphabetException
  {    
    ArrayList<Symbol> symbols = new ArrayList<Symbol>();
   
    for(TerminalSymbol symbol : this.getTerminalSymbolSet())
      symbols.add ( new DefaultSymbol(symbol.toString()) );
    
    for(NonterminalSymbol symbol : this.getNonterminalSymbolSet())
      symbols.add ( new DefaultSymbol(symbol.toString()) );
    
    Alphabet alphabet = new DefaultAlphabet(symbols);
    
    LR0 lr0Automata = new LR0(alphabet);
    
    TreeSet<LR0ItemSet> lr0States = new TreeSet<LR0ItemSet>();
    
    lr0States.add ( this.startClosure() );
    
    
    
    return lr0Automata;
  }
  
}
