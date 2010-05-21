package de.unisiegen.gtitool.ui.logic.lrreasons;


import de.unisiegen.gtitool.core.entities.LR1Item;
import de.unisiegen.gtitool.core.entities.LR1ItemSet;
import de.unisiegen.gtitool.core.entities.LRState;
import de.unisiegen.gtitool.core.entities.ReduceAction;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;


/**
 * TODO
 */
public class LR1ReasonMaker extends AbstractLRReasonMaker
{

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
    final LR1ItemSet items = ( LR1ItemSet ) state.getItems ();

    for ( LR1Item item : items )
      if ( item.dotIsAtEnd () && action.getReduceAction ().equals ( item )
          && item.getLookAhead ().equals ( terminalSymbol ) )
        return item.toString ();

    throw new RuntimeException (
        "No reason for a Reduce found! Shouldn't happen!" ); //$NON-NLS-1$
  }

}
