package de.unisiegen.gtitool.core.machines.lr;


import de.unisiegen.gtitool.core.entities.LR0Item;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;
import de.unisiegen.gtitool.core.grammars.cfg.LR0Grammar;


/**
 * The {@link SLRParser}
 */
public class SLRParser extends DefaultLR0Parser
{

  /**
   * Allocates a new {@link SLRParser}
   * 
   * @param grammar The {@link LR0Grammar}
   * @throws AlphabetException
   */
  public SLRParser ( LR0Grammar grammar ) throws AlphabetException
  {
    super ( grammar );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.lr.DefaultLR0Parser#followCondition(de.unisiegen.gtitool.core.entities.LR0Item,
   *      de.unisiegen.gtitool.core.entities.TerminalSymbol)
   */
  @Override
  protected boolean followCondition ( LR0Item item, TerminalSymbol symbol )
      throws GrammarInvalidNonterminalException
  {
    return getGrammar ().follow ( item.getNonterminalSymbol () ).contains (
        symbol );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.Machine#getMachineType()
   */
  @Override
  public MachineType getMachineType ()
  {
    return MachineType.LR0Parser;
  }
}
