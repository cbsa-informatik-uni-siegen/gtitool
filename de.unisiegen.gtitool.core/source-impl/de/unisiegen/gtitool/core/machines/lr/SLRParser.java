package de.unisiegen.gtitool.core.machines.lr;


import de.unisiegen.gtitool.core.entities.LR0ItemSet;
import de.unisiegen.gtitool.core.entities.LRActionSet;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.machines.AbstractLRMachine;


/**
 * TODO
 */
public class SLRParser extends AbstractLRMachine implements LR0Parser
{

  /**
   * TODO
   * 
   * @param items
   * @param symbol
   * @return
   * @see de.unisiegen.gtitool.core.machines.lr.LR0Parser#actions(de.unisiegen.gtitool.core.entities.LR0ItemSet,
   *      de.unisiegen.gtitool.core.entities.TerminalSymbol)
   */
  public LRActionSet actions ( LR0ItemSet items, TerminalSymbol symbol )
  {
    return null;
  }

}
