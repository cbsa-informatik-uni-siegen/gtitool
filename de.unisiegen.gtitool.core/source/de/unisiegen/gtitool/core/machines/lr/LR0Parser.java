package de.unisiegen.gtitool.core.machines.lr;


import de.unisiegen.gtitool.core.entities.LR0ItemSet;
import de.unisiegen.gtitool.core.entities.ActionSet;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;


/**
 * TODO
 */
public interface LR0Parser extends LRMachine
{

  /**
   * Return the possible actions for a given LR0 item set and a terminal
   *
   * @param items
   * @param symbol
   * @return the actions
   */
  public ActionSet actions ( LR0ItemSet items, TerminalSymbol symbol );
}
