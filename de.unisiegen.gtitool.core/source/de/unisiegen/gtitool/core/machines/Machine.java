package de.unisiegen.gtitool.core.machines;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeSet;

import javax.swing.table.TableModel;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Stack;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.machine.MachineValidationException;
import de.unisiegen.gtitool.core.exceptions.word.WordFinishedException;
import de.unisiegen.gtitool.core.exceptions.word.WordNotAcceptedException;
import de.unisiegen.gtitool.core.exceptions.word.WordResetedException;
import de.unisiegen.gtitool.core.storage.Modifyable;


/**
 * The interface for all machines.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface Machine extends Serializable, TableModel, Modifyable
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
     * There is a {@link Transition} with {@link Stack} operations.
     */
    TRANSITION_STACK_OPERATION,

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
    SYMBOL_ONLY_ONE_TIME;
  }


  /**
   * The available machines.
   */
  public static final String [] AVAILABLE_MACHINES =
  { "DFA", "NFA", "ENFA", "PDA" }; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$


  /**
   * Adds the {@link State}s to this <code>Machine</code>.
   * 
   * @param states The {@link State}s to add.
   */
  public void addState ( Iterable < State > states );


  /**
   * Adds the {@link State} to this <code>Machine</code>.
   * 
   * @param state The {@link State} to add.
   */
  public void addState ( State state );


  /**
   * Adds the {@link State}s to this <code>Machine</code>.
   * 
   * @param states The {@link State}s to add.
   */
  public void addState ( State ... states );


  /**
   * Adds the {@link Transition}s to this <code>Machine</code>.
   * 
   * @param transitions The {@link Transition}s to add.
   */
  public void addTransition ( Iterable < Transition > transitions );


  /**
   * Adds the {@link Transition} to this <code>Machine</code>.
   * 
   * @param transition The {@link Transition} to add.
   */
  public void addTransition ( Transition transition );


  /**
   * Adds the {@link Transition}s to this <code>Machine</code>.
   * 
   * @param transitions The {@link Transition}s to add.
   */
  public void addTransition ( Transition ... transitions );


  /**
   * Returns the active {@link State}s.
   * 
   * @return The active {@link State}s.
   */
  public TreeSet < State > getActiveState ();


  /**
   * Returns the active {@link Symbol}s.
   * 
   * @return The active {@link Symbol}s.
   */
  public ArrayList < Symbol > getActiveSymbol ();


  /**
   * Returns the active {@link Transition}s.
   * 
   * @return The active {@link Transition}s.
   */
  public TreeSet < Transition > getActiveTransition ();


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
   * @throws WordFinishedException If something with the
   *           <code>DefaultWord</code> is not correct.
   * @throws WordResetedException If something with the <code>DefaultWord</code>
   *           is not correct.
   */
  public Symbol getCurrentSymbol () throws WordFinishedException,
      WordResetedException;


  /**
   * Returns the <code>Machine</code> type.
   * 
   * @return The <code>Machine</code> type.
   */
  public String getMachineType ();


  /**
   * Returns the push down {@link Alphabet}.
   * 
   * @return The push down {@link Alphabet}.
   */
  public Alphabet getPushDownAlphabet ();


  /**
   * Returns the readed {@link Symbol}s.
   * 
   * @return The readed {@link Symbol}s.
   * @throws WordFinishedException If something with the
   *           <code>DefaultWord</code> is not correct.
   * @throws WordResetedException If something with the <code>DefaultWord</code>
   *           is not correct.
   */
  public ArrayList < Symbol > getReadedSymbols () throws WordFinishedException,
      WordResetedException;


  /**
   * Returns the {@link State} list.
   * 
   * @return The {@link State} list.
   */
  public ArrayList < State > getState ();


  /**
   * Returns the {@link State} with the given index.
   * 
   * @param index The index to return.
   * @return The {@link State} list.
   */
  public State getState ( int index );


  /**
   * Returns the {@link Transition} list.
   * 
   * @return The {@link Transition} list.
   */
  public ArrayList < Transition > getTransition ();


  /**
   * Returns the {@link Transition} with the given index.
   * 
   * @param index pIndex The index to return.
   * @return The {@link Transition} list.
   */
  public Transition getTransition ( int index );


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
   * @param symbol The {@link Symbol} which should be checked.
   * @return True if the given {@link Symbol} can be removed from the
   *         {@link Alphabet} of this <code>Machine</code>, otherwise false.
   */
  public boolean isSymbolRemoveableFromAlphabet ( Symbol symbol );


  /**
   * Returns true if the given {@link Symbol} can be removed from the push down
   * {@link Alphabet} of this <code>Machine</code>, otherwise false.
   * 
   * @param symbol The {@link Symbol} which should be checked.
   * @return True if the given {@link Symbol} can be removed from the push down
   *         {@link Alphabet} of this <code>Machine</code>, otherwise false.
   */
  public boolean isSymbolRemoveableFromPushDownAlphabet ( Symbol symbol );


  /**
   * Returns the use push down {@link Alphabet}.
   * 
   * @return The use push down {@link Alphabet}.
   */
  public boolean isUsePushDownAlphabet ();


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
   * @throws WordFinishedException If something with the {@link Word} is not
   *           correct.
   * @throws WordResetedException If something with the {@link Word} is not
   *           correct.
   * @throws WordNotAcceptedException If something with the {@link Word} is not
   *           correct.
   */
  public void nextSymbol () throws WordFinishedException, WordResetedException,
      WordNotAcceptedException;


  /**
   * Removes the last step and returns the list of {@link Transition}s, which
   * contains the {@link Symbol}.
   * 
   * @throws WordFinishedException If something with the {@link Word} is not
   *           correct.
   * @throws WordResetedException If something with the {@link Word} is not
   *           correct.
   */
  public void previousSymbol () throws WordFinishedException,
      WordResetedException;


  /**
   * Removes the given {@link State}s from this <code>Machine</code>.
   * 
   * @param states The {@link State}s to remove.
   */
  public void removeState ( Iterable < State > states );


  /**
   * Removes the given {@link State} from this <code>Machine</code>.
   * 
   * @param state The {@link State} to remove.
   */
  public void removeState ( State state );


  /**
   * Removes the given {@link State}s from this <code>Machine</code>.
   * 
   * @param states The {@link State}s to remove.
   */
  public void removeState ( State ... states );


  /**
   * Removes the given {@link Symbol} from this <code>Machine</code>.
   * 
   * @param symbol The {@link Symbol} to remove.
   */
  public void removeSymbol ( Symbol symbol );


  /**
   * Removes the given {@link Transition}s from this <code>Machine</code>.
   * 
   * @param transitions The {@link Transition}s to remove.
   */
  public void removeTransition ( Iterable < Transition > transitions );


  /**
   * Removes the given {@link Transition} from this <code>Machine</code>.
   * 
   * @param transition The {@link Transition} to remove.
   */
  public void removeTransition ( Transition transition );


  /**
   * Removes the given {@link Transition}s from this <code>Machine</code>.
   * 
   * @param transitions The {@link Transition}s to remove.
   */
  public void removeTransition ( Transition ... transitions );


  /**
   * Sets the use push down {@link Alphabet}.
   * 
   * @param usePushDownAlphabet The use push down {@link Alphabet} to set.
   */
  public void setUsePushDownAlphabet ( boolean usePushDownAlphabet );


  /**
   * Starts the <code>Machine</code> after a validation with the given
   * {@link Word}.
   * 
   * @param word The {@link Word} to start with.
   */
  public void start ( Word word );


  /**
   * Validates that everything in the <code>Machine</code> is correct.
   * 
   * @throws MachineValidationException If the validation fails.
   */
  public void validate () throws MachineValidationException;
}
