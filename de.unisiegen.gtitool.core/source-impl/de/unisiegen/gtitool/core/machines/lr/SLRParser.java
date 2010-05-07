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
  public SLRParser ( final LR0Grammar grammar ) throws AlphabetException
  {
    super ( grammar );
    getGrammar ().calculateAllFollowSets (); // reicht nicht?
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.lr.DefaultLR0Parser#followCondition(de.unisiegen.gtitool.core.entities.LR0Item,
   *      de.unisiegen.gtitool.core.entities.TerminalSymbol)
   */
  @Override
  protected boolean followCondition ( final LR0Item item,
      final TerminalSymbol symbol ) throws GrammarInvalidNonterminalException
  {
    // getGrammar ().calculateAllFollowSets (); // dann funktioniert es
    System.out.println ( item.getNonterminalSymbol ().toString () + ' '
        + symbol + ' ' + getGrammar ().follow ( item.getNonterminalSymbol () ) );
    System.out.println ( getGrammar ().getNonterminalSymbolSet () );
    System.out.println ( getGrammar ().getProduction () );
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
    return MachineType.SLR;
  }
}
