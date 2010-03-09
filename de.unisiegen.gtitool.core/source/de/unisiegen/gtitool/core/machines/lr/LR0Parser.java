package de.unisiegen.gtitool.core.machines.lr;


import de.unisiegen.gtitool.core.entities.LR0ItemSet;
import de.unisiegen.gtitool.core.entities.ActionSet;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;


/**
 * TODO
 */
public interface LR0Parser extends LRMachine
{

  public ActionSet actions ( LR0ItemSet items, TerminalSymbol symbol );
}
