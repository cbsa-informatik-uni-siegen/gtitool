package de.unisiegen.gtitool.core.grammars.cfg;


import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultProduction;
import de.unisiegen.gtitool.core.entities.DefaultProductionWord;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.grammars.AbstractGrammar;
import de.unisiegen.gtitool.core.grammars.Grammar.GrammarType;


/**
 * TODO
 */
public class ExtendedGrammar extends AbstractGrammar implements CFG
{

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


  public Production getStartProduction ()
  {
    return this.startProduction;
  }


  private DefaultProduction startProduction;
}
