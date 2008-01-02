package de.unisiegen.gtitool.core.machines;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.machine.MachineValidationException;
import de.unisiegen.gtitool.core.exceptions.word.WordException;
import de.unisiegen.gtitool.core.exceptions.word.WordFinishedException;
import de.unisiegen.gtitool.core.exceptions.word.WordNotAcceptedException;
import de.unisiegen.gtitool.core.exceptions.word.WordResetedException;


/**
 * The interface for all machines.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface Machine extends Serializable
{

  /**
   * This enum is used to indicate which validation elements should be checked
   * during a validation.
   * 
   * @author Christian Fehler
   */
  public enum ValidationElement
  {
    /**
     * There is a {@link State}, which {@link Transition}s do not contain all
     * {@link Symbol}s.
     */
    ALL_SYMBOLS,

    /**
     * There is a {@link Transition} without a {@link Symbol}.
     */
    EPSILON_TRANSITION,

    /**
     * There is no final state defined.
     */
    FINAL_STATE,

    /**
     * There is more than one start state defined.
     */
    MORE_THAN_ONE_START_STATE,

    /**
     * There is no start state is defined.
     */
    NO_START_STATE,

    /**
     * There are {@link State}s with the same name.
     */
    STATE_NAME,

    /**
     * There is a {@link State} which is not reachable.
     */
    STATE_NOT_REACHABLE,

    /**
     * There is a {@link State} with {@link Transition}s with the same
     * {@link Symbol}.
     */
    SYMBOL_ONLY_ONE_TIME
  }


  /**
   * The available machines.
   */
  public static final String [] AVAILABLE_MACHINES =
  { "DFA", "NFA", "ENFA", "PDA" }; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$


  /**
   * Adds the {@link State}s to this <code>Machine</code>.
   * 
   * @param pStates The {@link State}s to add.
   */
  public void addState ( Iterable < State > pStates );


  /**
   * Adds the {@link State} to this <code>Machine</code>.
   * 
   * @param pState The {@link State} to add.
   */
  public void addState ( State pState );


  /**
   * Adds the {@link State}s to this <code>Machine</code>.
   * 
   * @param pStates The {@link State}s to add.
   */
  public void addState ( State ... pStates );


  /**
   * Adds the {@link Transition}s to this <code>Machine</code>.
   * 
   * @param pTransitions The {@link Transition}s to add.
   */
  public void addTransition ( Iterable < Transition > pTransitions );


  /**
   * Adds the {@link Transition} to this <code>Machine</code>.
   * 
   * @param pTransition The {@link Transition} to add.
   */
  public void addTransition ( Transition pTransition );


  /**
   * Adds the {@link Transition}s to this <code>Machine</code>.
   * 
   * @param pTransitions The {@link Transition}s to add.
   */
  public void addTransition ( Transition ... pTransitions );


  /**
   * Returns the active {@link State}s.
   * 
   * @return The active {@link State}s.
   */
  public TreeSet < State > getActiveState ();


  /**
   * Returns the active {@link State} with the given index.
   * 
   * @param pIndex The index.
   * @return The active {@link State} with the given index.
   */
  public State getActiveState ( int pIndex );


  /**
   * Returns the {@link Alphabet}.
   * 
   * @return The {@link Alphabet}.
   */
  public Alphabet getAlphabet ();


  /**
   * Returns the current {@link Symbol}.
   * 
   * @return The current {@link Symbol}.
   * @throws WordException If something with the <code>Word</code> is not
   *           correct.
   */
  public Symbol getCurrentSymbol () throws WordException;


  /**
   * Returns the <code>Machine</code> type.
   * 
   * @return The <code>Machine</code> type.
   */
  public String getMachineType ();


  /**
   * Returns the {@link State} list.
   * 
   * @return The {@link State} list.
   */
  public ArrayList < State > getState ();


  /**
   * Returns the {@link State} with the given index.
   * 
   * @param pIndex The index to return.
   * @return The {@link State} list.
   */
  public State getState ( int pIndex );


  /**
   * Returns the {@link Transition} list.
   * 
   * @return The {@link Transition} list.
   */
  public ArrayList < Transition > getTransition ();


  /**
   * Returns the {@link Transition} with the given index.
   * 
   * @param pIndex pIndex The index to return.
   * @return The {@link Transition} list.
   */
  public Transition getTransition ( int pIndex );


  /**
   * Returns true if the {@link Word} is finished, otherwise false.
   * 
   * @return True if this {@link Word} is finished, otherwise false.
   */
  public boolean isFinished ();


  /**
   * Returns true if this {@link Word} is reseted, otherwise false.
   * 
   * @return True if this {@link Word} is reseted, otherwise false.
   */
  public boolean isReseted ();


  /**
   * Returns true if the given {@link Symbol} can be removed from the
   * {@link Alphabet} of this <code>Machine</code>, otherwise false.
   * 
   * @param pSymbol The {@link Symbol} which should be checked.
   * @return True if the given {@link Symbol} can be removed from the
   *         {@link Alphabet} of this <code>Machine</code>, otherwise false.
   */
  public boolean isSymbolRemoveable ( Symbol pSymbol );


  /**
   * Returns true if one of the active {@link State}s is a final {@link State},
   * otherwise false.
   * 
   * @return True if one of the active {@link State}s is a final {@link State},
   *         otherwise false.
   */
  public boolean isWordAccepted ();


  /**
   * Performs the next step and returns the list of {@link Transition}s, which
   * contains the {@link Symbol}.
   * 
   * @return The list of {@link Transition}s, which contains the {@link Symbol}.
   * @throws WordFinishedException If something with the {@link Word} is not
   *           correct.
   * @throws WordResetedException If something with the {@link Word} is not
   *           correct.
   * @throws WordNotAcceptedException If something with the {@link Word} is not
   *           correct.
   */
  public ArrayList < Transition > nextSymbol () throws WordFinishedException,
      WordResetedException, WordNotAcceptedException;


  /**
   * Removes the last step and returns the list of {@link Transition}s, which
   * contains the {@link Symbol}.
   * 
   * @return The list of {@link Transition}s, which contains the {@link Symbol}.
   * @throws WordFinishedException If something with the {@link Word} is not
   *           correct.
   * @throws WordResetedException If something with the {@link Word} is not
   *           correct.
   */
  public ArrayList < Transition > previousSymbol ()
      throws WordFinishedException, WordResetedException;


  /**
   * Removes the given {@link State}s from this <code>Machine</code>.
   * 
   * @param pStates The {@link State}s to remove.
   */
  public void removeState ( Iterable < State > pStates );


  /**
   * Removes the given {@link State} from this <code>Machine</code>.
   * 
   * @param pState The {@link State} to remove.
   */
  public void removeState ( State pState );


  /**
   * Removes the given {@link State}s from this <code>Machine</code>.
   * 
   * @param pStates The {@link State}s to remove.
   */
  public void removeState ( State ... pStates );


  /**
   * Removes the given {@link Symbol} from this <code>Machine</code>.
   * 
   * @param pSymbol The {@link Symbol} to remove.
   */
  public void removeSymbol ( Symbol pSymbol );


  /**
   * Removes the given {@link Transition}s from this <code>Machine</code>.
   * 
   * @param pTransitions The {@link Transition}s to remove.
   */
  public void removeTransition ( Iterable < Transition > pTransitions );


  /**
   * Removes the given {@link Transition} from this <code>Machine</code>.
   * 
   * @param pTransition The {@link Transition} to remove.
   */
  public void removeTransition ( Transition pTransition );


  /**
   * Removes the given {@link Transition}s from this <code>Machine</code>.
   * 
   * @param pTransitions The {@link Transition}s to remove.
   */
  public void removeTransition ( Transition ... pTransitions );


  /**
   * Starts the <code>Machine</code> after a validation with the given
   * {@link Word}.
   * 
   * @param pWord The {@link Word} to start with.
   * @throws MachineValidationException If the validation fails.
   */
  public void start ( Word pWord ) throws MachineValidationException;


  /**
   * Validates that everything in the <code>Machine</code> is correct.
   * 
   * @throws MachineValidationException If the validation fails.
   */
  public void validate () throws MachineValidationException;
}
