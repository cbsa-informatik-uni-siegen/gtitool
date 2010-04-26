package de.unisiegen.gtitool.core.machines;


import javax.swing.table.TableColumnModel;

import de.unisiegen.gtitool.core.entities.AcceptAction;
import de.unisiegen.gtitool.core.entities.Action;
import de.unisiegen.gtitool.core.entities.ActionSet;
import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultStack;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.ReplaceAction;
import de.unisiegen.gtitool.core.entities.ShiftAction;
import de.unisiegen.gtitool.core.entities.ShiftActionBase;
import de.unisiegen.gtitool.core.entities.Stack;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;
import de.unisiegen.gtitool.core.exceptions.lractionset.ActionSetException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineAmbigiousActionException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.word.WordFinishedException;
import de.unisiegen.gtitool.core.exceptions.word.WordResetedException;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.i18n.Messages;
import de.unisiegen.gtitool.core.machines.pda.DefaultTDP;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;


/**
 * The abstract class for all stateless machines.
 * 
 * @author Christian Uhrhan
 */
public abstract class AbstractStatelessMachine implements StatelessMachine
{

  /**
   * The alphabet
   */
  private Alphabet alphabet;


  /**
   * the input word
   */
  private Word word;


  /**
   * the stack
   */
  private Stack stack;


  /**
   * word is accepted
   */
  private boolean wordAccepted;


  /**
   * the history {@link java.util.Stack}
   */
  private java.util.Stack < StatelessMachineHistoryItem > history;


  /**
   * Returns the {@link StateMachine} with the given {@link StateMachine} type.
   * 
   * @param machineType The {@link StateMachine} type.
   * @param grammar The {@link Grammar}.
   * @return The {@link StateMachine} with the given {@link StateMachine} type.
   * @throws StoreException If the {@link StateMachine} type is unknown.
   * @throws TerminalSymbolSetException
   * @throws AlphabetException
   * @throws GrammarInvalidNonterminalException
   */
  // TODO: add creation of LR0/1Parser
  public static final StatelessMachine createMachine ( String machineType,
      Grammar grammar ) throws StoreException,
      GrammarInvalidNonterminalException, AlphabetException,
      TerminalSymbolSetException
  {
    if ( machineType.equals ( ( "TDP" ) ) ) //$NON-NLS-1$
      return new DefaultTDP ( ( CFG ) grammar );
    throw new StoreException ( Messages
        .getString ( "StoreException.WrongMachineType" ) ); //$NON-NLS-1$
  }


  /**
   * Allocates a new {@link AbstractStatelessMachine}
   * 
   * @param alphabet The {@link Alphabet}
   */
  public AbstractStatelessMachine ( final Alphabet alphabet )
  {
    if ( alphabet == null )
      throw new NullPointerException ( "alphabet is null" ); //$NON-NLS-1$
    this.alphabet = alphabet;
    this.stack = new DefaultStack ();
    this.history = new java.util.Stack < StatelessMachineHistoryItem > ();
  }


  /**
   * clears the History
   */
  public void clearHistory ()
  {
    this.history.clear ();
  }


  /**
   * actual input terminal
   * 
   * @return The actual {@link TerminalSymbol}
   */
  protected TerminalSymbol currentTerminal ()
  {
    return new DefaultTerminalSymbol ( this.word.getCurrentSymbol () );
  }


  /**
   * gets the {@link Alphabet}
   * 
   * @return the {@link Alphabet}
   */
  public Alphabet getAlphabet ()
  {
    return this.alphabet;
  }


  /**
   * Sets the Start input {@link Word}
   * 
   * @param word the input {@link Word}
   */
  public void start ( @SuppressWarnings ( "hiding" ) final Word word )
  {
    this.word = word;
    this.word.start ();
    this.wordAccepted = false;
  }


  /**
   * creates a new history entry
   */
  protected void createHistoryEntry ()
  {
    this.history.add ( new StatelessMachineHistoryItem ( this.word, this.stack,
        this.wordAccepted ) );
  }


  /**
   * restores the last history entry (goes one step back)
   */
  protected void restoreHistoryEntry ()
  {
    if ( this.history.isEmpty () )
      throw new RuntimeException ( "history is empty" ); //$NON-NLS-1$
    final StatelessMachineHistoryItem historyItem = this.history.pop ();

    this.word = historyItem.getWord ();
    this.stack = historyItem.getStack ();
    this.wordAccepted = historyItem.getWordAccepted ();
  }


  /**
   * increment current input position
   */
  public void nextSymbol ()
  {
    try
    {
      this.word.nextSymbol ();
    }
    catch ( WordFinishedException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }


  /**
   * go one step back
   */
  public void previousSymbol ()
  {
    try
    {
      this.word.previousSymbol ();
    }
    catch ( WordResetedException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.StatelessMachine#isWordAccepted()
   */
  public final boolean isWordAccepted ()
  {
    return this.wordAccepted;
  }


  /**
   * accept the input
   */
  public void accept ()
  {
    this.wordAccepted = true;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.StatelessMachine#getStack()
   */
  public final Stack getStack ()
  {
    return this.stack;
  }


  /**
   * returns the current input {@link Word}
   * 
   * @return the current input {@link Word}
   * @see de.unisiegen.gtitool.core.machines.Machine#getWord()
   */
  public final Word getWord ()
  {
    return this.word;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.Machine#stop()
   */
  public final void stop ()
  {
    this.word = null;
    this.stack.clear ();
    this.history.clear ();
    this.wordAccepted = false;

    onStop ();
  }


  /**
   * Can be overridden for a custom stop action
   */
  protected void onStop ()
  {
    // default: do nothing
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.storage.Storable#getElement()
   */
  public Element getElement ()
  {
    final Element element = new Element ( "Grammar" ); //$NON-NLS-1$
    element.addElement ( getGrammar ().getElement () );
    return element;
  }


  /**
   * returns the number of possible reductions
   * 
   * @return set of available productions for reduction
   * @throws ActionSetException
   */
  abstract public ActionSet getPossibleActions () throws ActionSetException;


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.Machine#isNextStepAmbigious()
   */
  public boolean isNextStepAmbigious () throws ActionSetException
  {
    return getPossibleActions ().size () >= 2;
  }


  /**
   * Returns the associated grammar
   * 
   * @return the grammar
   */
  public abstract Grammar getGrammar ();


  /**
   * handles the specific {@link ShiftActionBase}
   * 
   * @param action The {@link Action}
   * @return true if the {@link ShiftAction} could be applied
   */
  protected boolean onShift ( @SuppressWarnings ( "unused" ) final Action action )
  {
    return true;
  }


  /**
   * handles the specific {@link ReplaceAction}
   * 
   * @param action The {@link Action}
   * @return true if the {@link ReplaceAction} could be applied
   */
  protected boolean onReduce (
      @SuppressWarnings ( "unused" ) final Action action )
  {
    return true;
  }


  /**
   * handles the {@link AcceptAction}
   * 
   * @param action The {@link Action}
   * @return true if accept could be applied
   */
  protected boolean onAccept (
      @SuppressWarnings ( "unused" ) final Action action )
  {
    accept ();
    return true;
  }


  /**
   * carry out a {@link Action}
   * 
   * @param transition the {@link Action}
   * @return true if the action could be performed successfully
   */
  public boolean transit ( final Action transition )
  {
    createHistoryEntry ();
    switch ( transition.getTransitionType () )
    {
      case SHIFT :
      case CANCEL :
        return onShift ( transition );
      case REDUCE :
      case REVERSEREDUCE :
        return onReduce ( transition );
      case ACCEPT :
        return onAccept ( transition );
      case REJECTED:
        break;
    }
    return true;
  }


  /**
   * Tries to use a transition from possibleActions. Also asserts that the
   * transition is valid.
   * 
   * @param possibleActions
   * @return The {@link Action} which was taken
   * @throws MachineAmbigiousActionException if the action set's size is not 1
   */
  protected Action assertTransit ( final ActionSet possibleActions )
      throws MachineAmbigiousActionException
  {
    if ( possibleActions.size () != 1 )
      throw new MachineAmbigiousActionException ();

    if ( transit ( possibleActions.first () ) == false )
    {
      // shouldn't happen
      System.err.println ( "Internal parser error" ); //$NON-NLS-1$
      System.exit ( 1 );
    }

    return possibleActions.first ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.StatelessMachine#backTransit()
   */
  public final void backTransit ()
  {
    restoreHistoryEntry ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.Machine#isNextSymbolAvailable()
   */
  public boolean isNextSymbolAvailable ()
  {
    return !this.word.isFinished ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.Machine#isPreviousSymbolAvailable()
   */
  public boolean isPreviousSymbolAvailable ()
  {
    if ( this.word == null )
      return false;
    return !this.history.isEmpty ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.Machine#getTableColumnModel()
   */
  public TableColumnModel getTableColumnModel ()
  {
    throw new RuntimeException (
        "AbstractMachine.getTableColumnModel() not implemented!" ); //$NON-NLS-1$
  }
}
