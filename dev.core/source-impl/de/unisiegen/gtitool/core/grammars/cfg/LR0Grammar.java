package de.unisiegen.gtitool.core.grammars.cfg;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultProduction;
import de.unisiegen.gtitool.core.entities.DefaultProductionWord;
import de.unisiegen.gtitool.core.entities.LR0Item;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.ProductionWordMember;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.grammars.AbstractGrammar;


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
  public ArrayList < LR0Item > closure ( final ArrayList < LR0Item > items )
  {
    ArrayList < LR0Item > ret = new ArrayList < LR0Item > ( items );

    for ( int oldSize = ret.size (), newSize = 0 ; newSize != oldSize ; newSize = ret
        .size () )
    {
      oldSize = ret.size ();

      ArrayList < LR0Item > currentItems = new ArrayList < LR0Item > ( ret );
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

  public ArrayList < LR0Item > startClosure ()
  {
    ArrayList < LR0Item > ret = new ArrayList < LR0Item > ();
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
  public ArrayList < LR0Item > move ( ArrayList < LR0Item > items,
      ProductionWordMember productionWord )
  {
    ArrayList < LR0Item > ret = new ArrayList < LR0Item > ();

    for ( LR0Item item : items )
      if ( item.getProductionWord ().get ( item.getDotPosition () ).equals (
          productionWord ) )
        ret.add ( item.incDot () );

    return ret;
  }

}
