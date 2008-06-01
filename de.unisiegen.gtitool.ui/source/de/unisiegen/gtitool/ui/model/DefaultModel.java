package de.unisiegen.gtitool.ui.model;


import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.grammars.rg.RG;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.machines.dfa.DFA;
import de.unisiegen.gtitool.core.machines.enfa.ENFA;
import de.unisiegen.gtitool.core.machines.nfa.NFA;
import de.unisiegen.gtitool.core.machines.pda.PDA;
import de.unisiegen.gtitool.core.storage.Element;


/**
 * Interface representing a model managing the {@link Machine}s and
 * {@link Grammar}s.
 * 
 * @author Benjamin Mies
 * @author Christian Fehler
 * @version $Id$
 */
public interface DefaultModel
{

  /**
   * Signals the entity type.
   * 
   * @author Christian Fehler
   */
  public interface EntityType
  {
    // do nothing
  }


  /**
   * Signals the grammar type.
   * 
   * @author Christian Fehler
   */
  public enum GrammarType implements EntityType
  {
    /**
     * The {@link RG} grammar type.
     */
    RG,

    /**
     * The {@link CFG} grammar type.
     */
    CFG;
  }


  /**
   * Signals the machine type.
   * 
   * @author Christian Fehler
   */
  public enum MachineType implements EntityType
  {
    /**
     * The {@link DFA} machine type.
     */
    DFA,

    /**
     * The {@link ENFA} machine type.
     */
    ENFA,

    /**
     * The {@link NFA} machine type.
     */
    NFA,

    /**
     * The {@link PDA} machine type.
     */
    PDA;
  }


  /**
   * Returns the {@link Element}.
   * 
   * @return the {@link Element}.
   */
  public Element getElement ();
}
