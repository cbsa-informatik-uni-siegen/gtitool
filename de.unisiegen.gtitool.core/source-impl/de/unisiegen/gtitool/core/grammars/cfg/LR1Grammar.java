package de.unisiegen.gtitool.core.grammars.cfg;


import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.FirstSet;
import de.unisiegen.gtitool.core.entities.LR1Item;
import de.unisiegen.gtitool.core.entities.LR1ItemSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.ProductionSet;
import de.unisiegen.gtitool.core.entities.ProductionWord;
import de.unisiegen.gtitool.core.entities.ProductionWordMember;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.Grammar;


/**
 * The LR1 grammar
 */
public class LR1Grammar extends ExtendedGrammar
{

  /**
   * TODO
   */
  private static final long serialVersionUID = 1L;


  /**
   * Wrap an LR1 grammar from a grammar
   * 
   * @param grammar
   */
  public LR1Grammar ( final Grammar grammar )
  {
    super ( grammar );
  }


  /**
   * Create an LR1 grammar out of an existing grammar
   * 
   * @param nonterminalSymbolSet
   * @param terminalSymbolSet
   * @param startSymbol
   * @throws NonterminalSymbolSetException
   */
  public LR1Grammar ( final NonterminalSymbolSet nonterminalSymbolSet,
      final TerminalSymbolSet terminalSymbolSet,
      final NonterminalSymbol startSymbol )
      throws NonterminalSymbolSetException
  {
    super ( nonterminalSymbolSet, terminalSymbolSet, startSymbol );
  }


  /**
   * TODO
   * 
   * @param nonterminalSymbolSet
   * @param terminalSymbolSet
   * @param startSymbol
   * @param productions
   * @throws NonterminalSymbolSetException
   */
  public LR1Grammar ( final NonterminalSymbolSet nonterminalSymbolSet,
      final TerminalSymbolSet terminalSymbolSet,
      final NonterminalSymbol startSymbol, final ProductionSet productions )
      throws NonterminalSymbolSetException
  {
    this ( nonterminalSymbolSet, terminalSymbolSet, startSymbol );

    for ( Production production : productions )
      this.addProduction ( production );
  }


  /**
   * The start LR1 item set
   * 
   * @return the set
   */
  public LR1ItemSet startClosure ()
  {
    LR1ItemSet ret = new LR1ItemSet ();
    ret.add ( new LR1Item ( getStartProduction ().getNonterminalSymbol (),
        getStartProduction ().getProductionWord (), 0,
        DefaultTerminalSymbol.EndMarker ) );
    return ret;
  }


  /**
   * The closure function
   * 
   * @param items
   * @return the closure
   */
  public LR1ItemSet closure ( final LR1ItemSet items )
  {
    final LR1ItemSet ret = new LR1ItemSet ( items );

    for ( int oldSize = ret.size (), newSize = 0 ; newSize != oldSize ; newSize = ret
        .size () )
    {
      oldSize = ret.size ();

      LR1ItemSet currentItems = new LR1ItemSet ( ret );
      for ( LR1Item item : currentItems )
      {
        if ( !item.dotPrecedesNonterminal () )
          continue;

        for ( Production production : getProduction () )
        {
          if ( !item.getNonterminalAfterDot ().equals (
              production.getNonterminalSymbol () ) )
            continue;

          ProductionWord remainingPart = item.getRemainingProductionWord ();

          remainingPart.add ( item.getLookAhead () );

          FirstSet firstElements;
          try
          {
            firstElements = super.first ( remainingPart );

            for ( TerminalSymbol symbol : firstElements )
            {
              final LR1Item newProduction = new LR1Item ( production
                  .getNonterminalSymbol (), production.getProductionWord (), 0,
                  symbol );

              if ( !ret.contains ( newProduction ) )
                ret.add ( newProduction );
            }
          }
          catch ( GrammarInvalidNonterminalException exc )
          {
            exc.printStackTrace ();
            System.exit ( 1 );
          }
        }
      }
    }

    return ret;
  }


  /**
   * The extended start production
   * 
   * @return the start production
   */
  public LR1ItemSet startProduction ()
  {
    LR1ItemSet ret = new LR1ItemSet ();
    ret.add ( new LR1Item ( this.getStartProduction ().getNonterminalSymbol (),
        this.getStartProduction ().getProductionWord (), 0,
        DefaultTerminalSymbol.EndMarker ) );
    return ret;
  }


  /**
   * The move function
   * 
   * @param items
   * @param productionWord
   * @return the new set
   */
  public LR1ItemSet move ( final LR1ItemSet items,
      final ProductionWordMember productionWord )
  {
    LR1ItemSet ret = new LR1ItemSet ();

    for ( LR1Item item : items )
      if ( !item.dotIsAtEnd ()
          && item.getProductionWord ().get ( item.getDotPosition () ).equals (
              productionWord ) )
        ret.add ( item.incDot () );

    return ret;
  }

}
