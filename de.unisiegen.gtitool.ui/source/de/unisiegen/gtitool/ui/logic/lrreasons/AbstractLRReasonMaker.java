package de.unisiegen.gtitool.ui.logic.lrreasons;


import de.unisiegen.gtitool.core.entities.AcceptAction;
import de.unisiegen.gtitool.core.entities.Action;
import de.unisiegen.gtitool.core.entities.LRItem;
import de.unisiegen.gtitool.core.entities.LRState;
import de.unisiegen.gtitool.core.entities.ReduceAction;
import de.unisiegen.gtitool.core.entities.ShiftAction;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;


/**
 * TODO
 */
public abstract class AbstractLRReasonMaker implements LRReasonMaker
{

  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.logic.lrreasons.LRReasonMaker#reason(de.unisiegen.gtitool.core.entities.LRState,
   *      de.unisiegen.gtitool.core.entities.TerminalSymbol,
   *      de.unisiegen.gtitool.core.entities.Action)
   */
  public final String reason ( final LRState state,
      final TerminalSymbol terminalSymbol, final Action action )
  {
    if ( action instanceof ShiftAction )
      return handleShift ( state, terminalSymbol );
    if ( action instanceof AcceptAction )
      return handleAccept ( state );

    return reduceReason ( state, terminalSymbol, ( ReduceAction ) action );
  }


  /**
   * Returns the reason for why a reduce action has been chosen
   * 
   * @param state
   * @param terminalSymbol
   * @param action
   * @return the reason
   */
  protected abstract String reduceReason ( final LRState state,
      final TerminalSymbol terminalSymbol, final ReduceAction action );


  /**
   * Returns the reason for why a shift action has been chosen
   * 
   * @param state
   * @param terminalSymbol
   * @param action
   * @return reason
   */
  private String handleShift ( final LRState state,
      final TerminalSymbol terminalSymbol )
  {
    for ( LRItem item : state.getItems ().baseList () )
      if ( item.dotPrecedesTerminal ()
          && item.getTerminalAfterDot ().equals ( terminalSymbol ) )
        return item.toString () + " " + terminalSymbol.toString ();

    throw new RuntimeException (
        "No reason for a Shift found! Shouldn't happen!" ); //$NON-NLS-1$
  }


  /**
   * Returns the reason for why an accept action has been chosen
   * 
   * @param state
   * @return reason
   */
  private String handleAccept ( final LRState state )
  {
    for ( LRItem item : state.getItems ().baseList () )
      if ( item.dotIsAtEnd () && item.getNonterminalSymbol ().isStart () )
        return item.toString ();

    throw new RuntimeException (
        "No reason for an Accept found! Shouldn't happen!" ); //$NON-NLS-1$
  }


  static protected String actionString ( final LRState state,
      final TerminalSymbol terminalSymbol, final Action action )
  {
    return action.toString () + " is in ACTIONS(" + state.getIndex () + ","
        + terminalSymbol.toString () + ")";
  }
}
