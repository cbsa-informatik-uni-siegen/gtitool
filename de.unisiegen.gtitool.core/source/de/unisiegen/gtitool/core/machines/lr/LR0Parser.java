package de.unisiegen.gtitool.core.machines.lr;


import de.unisiegen.gtitool.core.entities.LR0ItemSet;
import de.unisiegen.gtitool.core.entities.LRActionSet;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;


/**
 * TODO
 */
public interface LR0Parser extends LRMachine
{

  public LRActionSet actions ( LR0ItemSet items, TerminalSymbol symbol );
}
