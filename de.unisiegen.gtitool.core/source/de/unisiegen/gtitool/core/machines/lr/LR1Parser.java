package de.unisiegen.gtitool.core.machines.lr;


import de.unisiegen.gtitool.core.entities.LR1ItemSet;
import de.unisiegen.gtitool.core.entities.ActionSet;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;


/**
 * TODO
 */
public interface LR1Parser extends LRMachine
{

  public ActionSet actions ( LR1ItemSet items, TerminalSymbol symbol );
}
