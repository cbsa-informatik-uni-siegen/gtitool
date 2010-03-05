package de.unisiegen.gtitool.core.machines;


import javax.swing.table.TableColumnModel;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Stack;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.entities.InputEntity.EntityType;
import de.unisiegen.gtitool.core.machines.dfa.DFA;
import de.unisiegen.gtitool.core.machines.dfa.LR0;
import de.unisiegen.gtitool.core.machines.dfa.LR1;
import de.unisiegen.gtitool.core.machines.enfa.ENFA;
import de.unisiegen.gtitool.core.machines.lr.LR0Parser;
import de.unisiegen.gtitool.core.machines.lr.LR1Parser;
import de.unisiegen.gtitool.core.machines.nfa.NFA;
import de.unisiegen.gtitool.core.machines.pda.DefaultTDP;
import de.unisiegen.gtitool.core.machines.pda.PDA;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * The interface for all machines
 */
public interface Machine extends Storable
{

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
    PDA,

    /**
     * The {@link LR0} machine type.
     */
    LR0,

    /**
     * The {@link LR1} machine type.
     */
    LR1,

    /**
     * The {@link LR0Parser} machine type.
     */
    LR0Parser,

    /**
     * The {@link LR1Parser} machine type.
     */
    LR1Parser,

    /**
     * The LALR1Parser machine type.
     */
    LALR1Parser,

    /**
     * The {@link DefaultTDP} machine type.
     */
    TDP;

    /**
     * The file ending.
     * 
     * @return The file ending.
     */
    public final String getFileEnding ()
    {
      return toString ().toLowerCase ();
    }


    /**
     * {@inheritDoc}
     * 
     * @see Enum#toString()
     */
    @Override
    public final String toString ()
    {
      switch ( this )
      {
        case DFA :
        {
          return "DFA"; //$NON-NLS-1$
        }
        case ENFA :
        {
          return "ENFA"; //$NON-NLS-1$
        }
        case NFA :
        {
          return "NFA"; //$NON-NLS-1$
        }
        case PDA :
        {
          return "PDA"; //$NON-NLS-1$
        }
        case LR0 :
        {
          return "LR0"; //$NON-NLS-1$
        }
        case LR1 :
        {
          return "LR1"; //$NON-NLS-1$
        }
        case LR0Parser :
        {
          return "LR0Parser"; //$NON-NLS-1$
        }
        case LALR1Parser:
        {
          return "LALR1Parser"; //$NON-NLS-1$
        }
        case LR1Parser :
        {
          return "LR1Parser"; //$NON-NLS-1$
        }
        case TDP :
        {
          return "TDP"; //$NON-NLS-1$
        }
      }
      throw new IllegalArgumentException ( "unsupported machine type" ); //$NON-NLS-1$
    }
  }


  /**
   * retrieves the {@link Alphabet}
   * 
   * @return the {@link Alphabet}
   */
  public Alphabet getAlphabet ();


  /**
   * Returns the {@link TableColumnModel}.
   * 
   * @return The {@link TableColumnModel}.
   */
  public TableColumnModel getTableColumnModel ();


  /**
   * Returns the {@link Machine.MachineType}.
   * 
   * @return The {@link Machine.MachineType}.
   */
  public MachineType getMachineType ();
  
  /**
   * Starts the {@link StateMachine} after a validation with the given
   * {@link Word}.
   * 
   * @param word The {@link Word} to start with.
   */
  public void start ( Word word );
  
  
  public void stop();
  
  public Stack getStack ();
}
