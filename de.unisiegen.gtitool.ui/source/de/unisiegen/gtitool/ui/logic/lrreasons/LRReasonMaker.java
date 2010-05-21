package de.unisiegen.gtitool.ui.logic.lrreasons;


import de.unisiegen.gtitool.core.entities.Action;
import de.unisiegen.gtitool.core.entities.LRState;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;


/**
 * TODO
 */
public interface LRReasonMaker
{

  /**
   * Returns a string representation of why action is chosen for ACTIONS(state,
   * terminalSymbol)
   * 
   * @param state
   * @param terminalSymbol
   * @param action
   * @return the string
   */
  public String reason ( LRState state, TerminalSymbol terminalSymbol,
      Action action );
}
