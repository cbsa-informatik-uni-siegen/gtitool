package de.unisiegen.gtitool.ui.logic.lrreasons;


import de.unisiegen.gtitool.core.entities.LR0Item;
import de.unisiegen.gtitool.core.entities.LR0ItemSet;
import de.unisiegen.gtitool.core.entities.LRState;
import de.unisiegen.gtitool.core.entities.ReduceAction;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;


/**
 * TODO
 */
public class LR0ReasonMaker extends AbstractLRReasonMaker
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
      @SuppressWarnings ( "unused" ) TerminalSymbol terminalSymbol,
      final ReduceAction action )
  {
    final LR0ItemSet items = ( LR0ItemSet ) state.getItems ();

    for ( LR0Item item : items )
      if ( item.dotIsAtEnd () && action.getReduceAction ().equals ( item ) )
        return item.toString ();

    throw new RuntimeException (
        "No reason for a Reduce found! Shouldn't happen!" ); //$NON-NLS-1$
  }
}
