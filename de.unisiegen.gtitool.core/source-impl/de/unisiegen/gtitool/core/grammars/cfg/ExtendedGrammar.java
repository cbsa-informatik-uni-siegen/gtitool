package de.unisiegen.gtitool.core.grammars.cfg;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultProduction;
import de.unisiegen.gtitool.core.entities.DefaultProductionWord;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.grammars.AbstractGrammar;


/**
 * TODO
 */
public class ExtendedGrammar extends AbstractGrammar implements CFG
{

  /**
   * TODO
   */
  private static final long serialVersionUID = 1L;


  /**
   * TODO
   * 
   * @param nonterminalSymbolSet
   * @param terminalSymbolSet
   * @param startSymbol
   */
  public ExtendedGrammar ( NonterminalSymbolSet nonterminalSymbolSet,
      TerminalSymbolSet terminalSymbolSet, NonterminalSymbol startSymbol )
  {
    super ( nonterminalSymbolSet, terminalSymbolSet, startSymbol,
        ValidationElement.DUPLICATE_PRODUCTION,
        ValidationElement.NONTERMINAL_NOT_REACHABLE );

    // TODO: find a unique symbol here!
    this.setStartSymbol ( new DefaultNonterminalSymbol ( startSymbol
        .toString ()
        + "'" ) );

    this.startProduction = new DefaultProduction ( this.getStartSymbol (),
        new DefaultProductionWord ( startSymbol ) );

    this.addProduction ( this.startProduction );
  }


  /**
   * TODO
   * 
   * @see de.unisiegen.gtitool.core.grammars.AbstractGrammar#getGrammarType()
   */
  @Override
  public GrammarType getGrammarType ()
  {
    return GrammarType.CFG;
  }


  /**
   * TODO
   *
   * @return
   */
  public Production getStartProduction ()
  {
    return this.startProduction;
  }


  /**
   * TODO
   *
   * @return
   * @throws AlphabetException
   */
  public Alphabet makeAutomatonAlphabet () throws AlphabetException
  {
    ArrayList < Symbol > symbols = new ArrayList < Symbol > ();

    for ( TerminalSymbol symbol : this.getTerminalSymbolSet () )
      symbols.add ( new DefaultSymbol ( symbol.toString () ) );

    for ( NonterminalSymbol symbol : this.getNonterminalSymbolSet () )
      symbols.add ( new DefaultSymbol ( symbol.toString () ) );

    return new DefaultAlphabet ( symbols );
  }


  private DefaultProduction startProduction;
}
