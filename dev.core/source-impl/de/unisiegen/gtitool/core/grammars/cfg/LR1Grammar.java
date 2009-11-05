package de.unisiegen.gtitool.core.grammars.cfg;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.FirstSet;
import de.unisiegen.gtitool.core.entities.LR1Item;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.ProductionWord;
import de.unisiegen.gtitool.core.entities.ProductionWordMember;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;


/**
 * TODO
 */
public class LR1Grammar extends ExtendedGrammar
{

  public LR1Grammar ( NonterminalSymbolSet nonterminalSymbolSet,
      TerminalSymbolSet terminalSymbolSet, NonterminalSymbol startSymbol )
  {
    super ( nonterminalSymbolSet, terminalSymbolSet, startSymbol );
  }


  public ArrayList < LR1Item > startClosure ()
  {
    ArrayList < LR1Item > ret = new ArrayList < LR1Item > ();
    ret.add ( new LR1Item ( this.getStartProduction ().getNonterminalSymbol (),
        this.getStartProduction ().getProductionWord (), 0,
        new DefaultTerminalSymbol ( "$" ) ) ); // TODO:
    // where to
    // get the
    // constant
    // for $?
    return ret;
  }


  public ArrayList < LR1Item > closure ( ArrayList < LR1Item > items )
  {
    ArrayList < LR1Item > ret = new ArrayList < LR1Item > ( items );

    for ( int oldSize = ret.size (), newSize = 0 ; newSize != oldSize ; newSize = ret
        .size () )
    {
      oldSize = ret.size ();

      ArrayList < LR1Item > currentItems = new ArrayList < LR1Item > ( ret );
      for ( LR1Item item : currentItems )
      {
        if ( !item.dotPrecedesNonterminal () )
          continue;

        for ( Production production : this.getProduction () )
        {
          if ( !item.getNonterminalAfterDot ().equals (
              production.getNonterminalSymbol () ) )
            continue;

          ProductionWord remainingPart = item.getRemainingProductionWord ();

          remainingPart.add ( item.getLookAhead () );

          FirstSet firstElements = super.first ( remainingPart );

          System.out.println ( "FIRST: " + remainingPart.toString() );

          for ( TerminalSymbol symbol : firstElements )
          {
            System.out.println ( "\t" + symbol );
            final LR1Item newProduction = new LR1Item ( production
                .getNonterminalSymbol (), production.getProductionWord (), 0,
                symbol );

            if ( !ret.contains ( newProduction ) )
              ret.add ( newProduction );
          }

        }
      }
    }

    return ret;
  }


  public ArrayList < LR1Item > move ( ArrayList < LR1Item > items,
      ProductionWordMember productionWord )
  {
    ArrayList < LR1Item > ret = new ArrayList < LR1Item > ();

    for ( LR1Item item : items )
      if ( item.getProductionWord ().get ( item.getDotPosition () ).equals (
          productionWord ) )
        ret.add ( item.incDot () );

    return ret;
  }
}
