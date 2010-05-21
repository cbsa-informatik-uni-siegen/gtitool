package de.unisiegen.gtitool.ui.logic.lrreasons;


import de.unisiegen.gtitool.core.entities.LR0Item;
import de.unisiegen.gtitool.core.entities.LR0ItemSet;
import de.unisiegen.gtitool.core.entities.LRState;
import de.unisiegen.gtitool.core.entities.ReduceAction;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.Grammar;


/**
 * TODO
 */
public class SLRReasonMaker extends AbstractLRReasonMaker
{

  /**
   * The grammar is needed to look in FOLLOW()
   * 
   * @param grammar
   */
  public SLRReasonMaker ( final Grammar grammar )
  {
    this.grammar = grammar;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.lrreasons.LRReasonMaker#reason(de.unisiegen.gtitool.core.entities.LRState,
   *      de.unisiegen.gtitool.core.entities.TerminalSymbol,
   *      de.unisiegen.gtitool.core.entities.Action)
   */
  @Override
  public String reduceReason ( final LRState state,
      final TerminalSymbol terminalSymbol, final ReduceAction action )
  {
    final LR0ItemSet items = ( LR0ItemSet ) state.getItems ();

    try
    {
      for ( LR0Item item : items )
        if ( item.dotIsAtEnd ()
            && action.getReduceAction ().equals ( item )
            && this.grammar.follow (
                action.getReduceAction ().getNonterminalSymbol () ).contains (
                terminalSymbol ) )
          return item.toString ();
    }
    catch ( TerminalSymbolSetException exn )
    {
      exn.printStackTrace ();
    }
    catch ( GrammarInvalidNonterminalException exn )
    {
      exn.printStackTrace ();
    }
    throw new RuntimeException (
        "No reason for a Reduce found! Shouldn't happen!" ); //$NON-NLS-1$
  }


  /**
   * The associated grammar
   */
  private Grammar grammar;
}
