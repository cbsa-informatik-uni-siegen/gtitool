package de.unisiegen.gtitool.core.machines.lr;


import de.unisiegen.gtitool.core.entities.ActionSet;
import de.unisiegen.gtitool.core.entities.LR1ItemSet;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.grammars.cfg.LR1Grammar;
import de.unisiegen.gtitool.core.machines.dfa.LR1;


/**
 * TODO
 */
public interface LR1Parser extends LRMachine
{

  public ActionSet actions ( LR1ItemSet items, TerminalSymbol symbol );


  public LR1 getLR1 ();


  public LR1Grammar getGrammar ();
}
