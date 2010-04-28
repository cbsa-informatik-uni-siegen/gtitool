package de.unisiegen.gtitool.core.machines.lr;


import de.unisiegen.gtitool.core.grammars.cfg.LR1Grammar;
import de.unisiegen.gtitool.core.machines.dfa.LR1;


/**
 * The base class for an LR1Parser
 */
public interface LR1Parser extends LRMachine
{
  /**
   * Get the associated LR1 automaton
   * 
   * @return the automaton
   */
  public LR1 getLR1 ();


  /**
   * Get the associated Grammar
   * 
   * @return the grammar
   */
  public LR1Grammar getGrammar ();
}
