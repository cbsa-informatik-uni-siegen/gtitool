package de.unisiegen.gtitool.core.machines;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeSet;

import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.InputEntity;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.Stack;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.machine.MachineValidationException;
import de.unisiegen.gtitool.core.exceptions.word.WordFinishedException;
import de.unisiegen.gtitool.core.exceptions.word.WordResetedException;
import de.unisiegen.gtitool.core.machines.listener.MachineChangedListener;
import de.unisiegen.gtitool.core.storage.Modifyable;


/**
 * The interface for state machines.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface StateMachine extends Machine, InputEntity, Serializable,
    TableModel, Modifyable
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
   * The index of the {@link State} column.
   */
  public static final int STATE_COLUMN = 0;


  /**
   * The index of the epsilon {@link Transition} column.
   */
  public static final int EPSILON_COLUMN = 1;


  /**
   * The count of special columns.
   */
  public static final int SPECIAL_COLUMN_COUNT = 2;


  /**
   * The index of the {@link NonterminalSymbol} column
   */
  public static final int NONTERMINAL_COLUMN = 0;


  /**
   * The available machines.
   */
  public static final String [] AVAILABLE_MACHINES =
  { "DFA", "NFA", "ENFA", "PDA", "LR0" }; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$ //$NON-NLS-5$


  /**
   * Adds the given {@link MachineChangedListener}.
   * 
   * @param listener The {@link MachineChangedListener}.
   */
  public void addMachineChangedListener ( MachineChangedListener listener );


  /**
   * Adds the {@link State}s to this {@link StateMachine}.
   * 
   * @param states The {@link State}s to add.
   */
  public void addState ( Iterable < State > states );


  /**
   * Adds the {@link State} to this {@link StateMachine}.
   * 
   * @param state The {@link State} to add.
   */
  public void addState ( State state );


  /**
   * Adds the {@link State}s to this {@link StateMachine}.
   * 
   * @param states The {@link State}s to add.
   */
  public void addState ( State ... states );


  /**
   * Adds the {@link Transition}s to this {@link StateMachine}.
   * 
   * @param transitions The {@link Transition}s to add.
   */
  public void addTransition ( Iterable < Transition > transitions );


  /**
   * Adds the {@link Transition} to this {@link StateMachine}.
   * 
   * @param transition The {@link Transition} to add.
   */
  public void addTransition ( Transition transition );


  /**
   * Adds the {@link Transition}s to this {@link StateMachine}.
   * 
   * @param transitions The {@link Transition}s to add.
   */
  public void addTransition ( Transition ... transitions );


  /**
   * Clears all selected {@link Transition}s.
   */
  public void clearSelectedTransition ();


  /**
   * Returns the accepted {@link Word} list.
   * 
   * @param maxLength The maximum {@link Word} length.
   * @return The accepted {@link Word} list.
   */
  public ArrayList < Word > getAcceptedWords ( int maxLength );


  /**
   * Returns the {@link Alphabet}.
   * 
   * @return The {@link Alphabet}.
   */
  public Alphabet getAlphabet ();


  /**
   * Returns the {@link Machine.MachineType}.
   * 
   * @return The {@link Machine.MachineType}.
   */
  public MachineType getMachineType ();


  /**
   * Returns the next {@link State} name.
   * 
   * @return The next {@link State} name.
   */
  public String getNextStateName ();


  /**
   * Returns the not reachable {@link State} list.
   * 
   * @return The not reachable {@link State} list.
   */
  public ArrayList < State > getNotReachableStates ();


  /**
   * Returns the {@link Symbol}s which are not removeable from the
   * {@link Alphabet}.
   * 
   * @return The {@link Symbol}s which are not removeable from the
   *         {@link Alphabet}.
   */
  public TreeSet < Symbol > getNotRemoveableSymbolsFromAlphabet ();


  /**
   * Returns the {@link Symbol}s which are not removeable from the
   * {@link Alphabet}.
   * 
   * @return The {@link Symbol}s which are not removeable from the
   *         {@link Alphabet}.
   */
  public TreeSet < Symbol > getNotRemoveableSymbolsFromPushDownAlphabet ();


  /**
   * Returns the possible {@link Transition}s.
   * 
   * @return The possible {@link Transition}s.
   */
  public ArrayList < Transition > getPossibleTransitions ();


  /**
   * Returns the push down {@link Alphabet}.
   * 
   * @return The push down {@link Alphabet}.
   */
  public Alphabet getPushDownAlphabet ();


  /**
   * Returns the reachable {@link State} list.
   * 
   * @return The reachable {@link State} list.
   */
  public ArrayList < State > getReachableStates ();


  /**
   * Returns the readed {@link Symbol}s.
   * 
   * @return The readed {@link Symbol}s.
   * @throws WordFinishedException If something with the {@link Word} is not
   *           correct.
   * @throws WordResetedException If something with the {@link Word} is not
   *           correct.
   */
  public ArrayList < Symbol > getReadedSymbols () throws WordFinishedException,
      WordResetedException;


  /**
   * Returns the {@link Stack}.
   * 
   * @return The {@link Stack}.
   */
  public Stack getStack ();


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
   * Returns the {@link TableColumnModel}.
   * 
   * @return The {@link TableColumnModel}.
   */
  public TableColumnModel getTableColumnModel ();


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
   * Returns the {@link Word}.
   * 
   * @return The {@link Word}.
   */
  public Word getWord ();


  /**
   * Returns true if every {@link State} name is unique in this
   * {@link StateMachine}, otherwise false.
   * 
   * @return True if every {@link State} name is unique in this
   *         {@link StateMachine}, otherwise false.
   */
  public boolean isEveryStateUnique ();


  /**
   * Returns true if the next {@link Symbol} is available, otherwise false.
   * 
   * @return True if the next {@link Symbol} is available, otherwise false.
   */
  public boolean isNextSymbolAvailable ();


  /**
   * Returns true if the previous {@link Symbol} is available, otherwise false.
   * 
   * @return True if the previous {@link Symbol} is available, otherwise false.
   */
  public boolean isPreviousSymbolAvailable ();


  /**
   * Returns the use push down {@link Alphabet}.
   * 
   * @return The use push down {@link Alphabet}.
   */
  public boolean isUsePushDownAlphabet ();


  /**
   * Returns true if a user input is needed, otherwise false.
   * 
   * @return True if a user input is needed, otherwise false.
   */
  public boolean isUserInputNeeded ();


  /**
   * Returns true if one of the active {@link State}s is a final {@link State},
   * otherwise false.
   * 
   * @return True if one of the active {@link State}s is a final {@link State},
   *         otherwise false.
   */
  public boolean isWordAccepted ();


  /**
   * Returns true if the given {@link Word} is accepted, otherwise false.
   * 
   * @param testWord The {@link Word} to test.
   * @return True if the given {@link Word} is accepted, otherwise false.
   */
  public boolean isWordAccepted ( Word testWord );


  /**
   * Performs the next step.
   */
  public void nextSymbol ();


  /**
   * Performs the next step using the given {@link Transition}.
   * 
   * @param transition The {@link Transition}.
   */
  public void nextSymbol ( Transition transition );


  /**
   * Removes the last step.
   */
  public void previousSymbol ();


  /**
   * Removes the given {@link MachineChangedListener}.
   * 
   * @param listener The {@link MachineChangedListener}.
   */
  public void removeMachineChangedListener ( MachineChangedListener listener );


  /**
   * Removes the given {@link State}s from this {@link StateMachine}.
   * 
   * @param states The {@link State}s to remove.
   */
  public void removeState ( Iterable < State > states );


  /**
   * Removes the given {@link State} from this {@link StateMachine}.
   * 
   * @param state The {@link State} to remove.
   */
  public void removeState ( State state );


  /**
   * Removes the given {@link State}s from this {@link StateMachine}.
   * 
   * @param states The {@link State}s to remove.
   */
  public void removeState ( State ... states );


  /**
   * Removes the given {@link Symbol} from this {@link StateMachine}.
   * 
   * @param symbol The {@link Symbol} to remove.
   */
  public void removeSymbol ( Symbol symbol );


  /**
   * Removes the given {@link Transition}s from this {@link StateMachine}.
   * 
   * @param transitions The {@link Transition}s to remove.
   */
  public void removeTransition ( Iterable < Transition > transitions );


  /**
   * Removes the given {@link Transition} from this {@link StateMachine}.
   * 
   * @param transition The {@link Transition} to remove.
   */
  public void removeTransition ( Transition transition );


  /**
   * Removes the given {@link Transition}s from this {@link StateMachine}.
   * 
   * @param transitions The {@link Transition}s to remove.
   */
  public void removeTransition ( Transition ... transitions );


  /**
   * Sets the given {@link State} selected.
   * 
   * @param state The {@link State} which should be selected.
   */
  public void setSelectedState ( State state );


  /**
   * Sets the given {@link Transition}s selected.
   * 
   * @param transitionList The {@link Transition}s which should be selected.
   */
  public void setSelectedTransition ( ArrayList < Transition > transitionList );


  /**
   * Sets the use push down {@link Alphabet}.
   * 
   * @param usePushDownAlphabet The use push down {@link Alphabet} to set.
   */
  public void setUsePushDownAlphabet ( boolean usePushDownAlphabet );


  /**
   * Starts the {@link StateMachine} after a validation with the given
   * {@link Word}.
   * 
   * @param word The {@link Word} to start with.
   */
  public void start ( Word word );


  /**
   * Stops the {@link StateMachine}.
   */
  public void stop ();


  /**
   * Validates that everything in the {@link StateMachine} is correct.
   * 
   * @throws MachineValidationException If the validation fails.
   */
  public void validate () throws MachineValidationException;
}
