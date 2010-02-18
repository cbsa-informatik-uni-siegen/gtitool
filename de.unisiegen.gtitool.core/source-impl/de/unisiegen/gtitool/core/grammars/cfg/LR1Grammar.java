package de.unisiegen.gtitool.core.grammars.cfg;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.FirstSet;
import de.unisiegen.gtitool.core.entities.LR1Item;
import de.unisiegen.gtitool.core.entities.LR1ItemSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.ProductionWord;
import de.unisiegen.gtitool.core.entities.ProductionWordMember;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;


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


  public TerminalSymbol endMarker ()
  {
    // where to
    // get the
    // constant
    // for $?
    return new DefaultTerminalSymbol ( "$" );
  }


  public LR1ItemSet startClosure ()
  {
    LR1ItemSet ret = new LR1ItemSet ();
    ret.add ( new LR1Item ( getStartProduction ().getNonterminalSymbol (),
        getStartProduction ().getProductionWord (), 0, endMarker () ) );
    return ret;
  }


  public LR1ItemSet closure ( LR1ItemSet items )
  {
    LR1ItemSet ret = new LR1ItemSet ( items );

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

            System.out.println ( "FIRST: " + remainingPart.toString () );

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
          catch ( GrammarInvalidNonterminalException exc )
          {
            exc.printStackTrace ();
            return null;
          }
        }
      }
    }

    return ret;
  }


  public LR1ItemSet startProduction ()
  {
    LR1ItemSet ret = new LR1ItemSet ();
    ret.add ( new LR1Item ( this.getStartProduction ().getNonterminalSymbol (),
        this.getStartProduction ().getProductionWord (), 0, endMarker () ) );
    return ret;
  }


  public LR1ItemSet move ( LR1ItemSet items, ProductionWordMember productionWord )
  {
    LR1ItemSet ret = new LR1ItemSet ();

    for ( LR1Item item : items )
      if ( item.getProductionWord ().get ( item.getDotPosition () ).equals (
          productionWord ) )
        ret.add ( item.incDot () );

    return ret;
  }


  public Alphabet makeAutomatonAlphabet () throws AlphabetException
  {
    ArrayList < Symbol > symbols = new ArrayList < Symbol > ();

    for ( TerminalSymbol symbol : this.getTerminalSymbolSet () )
      symbols.add ( new DefaultSymbol ( symbol.toString () ) );

    for ( NonterminalSymbol symbol : this.getNonterminalSymbolSet () )
      symbols.add ( new DefaultSymbol ( symbol.toString () ) );

    return new DefaultAlphabet ( symbols );
  }
}
