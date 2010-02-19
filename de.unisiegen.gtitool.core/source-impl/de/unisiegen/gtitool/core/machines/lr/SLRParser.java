package de.unisiegen.gtitool.core.machines.lr;


import de.unisiegen.gtitool.core.entities.LR0Item;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;
import de.unisiegen.gtitool.core.grammars.cfg.LR0Grammar;


/**
 * TODO
 */
public class SLRParser extends DefaultLR0Parser
{

  public SLRParser ( LR0Grammar grammar ) throws AlphabetException
  {
    super ( grammar );
  }


  protected boolean followCondition ( LR0Item item, TerminalSymbol symbol )
      throws GrammarInvalidNonterminalException
  {
    return this.getGrammar ().follow ( item.getNonterminalSymbol () ).contains (
        symbol );
  }
}
