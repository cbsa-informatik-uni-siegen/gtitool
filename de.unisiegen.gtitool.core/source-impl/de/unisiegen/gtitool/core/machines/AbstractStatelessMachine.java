package de.unisiegen.gtitool.core.machines;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultStack;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.LRActionSet;
import de.unisiegen.gtitool.core.entities.Stack;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;
import de.unisiegen.gtitool.core.exceptions.lractionset.LRActionSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.word.WordFinishedException;
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
   * actual input position
   */
  private int wordIndex;


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
  // TODO: change type of argument alphabet to Grammar and add creation of
  // LR0/1Parser
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
    return new DefaultTerminalSymbol ( this.word.get ( this.wordIndex )
        .toString () );
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
  public void start ( final Word word )
  {
    this.word = word;
    this.word.start ();
    this.wordAccepted = false;
  }


  /**
   * increment current input position
   */
  public void nextSymbol ()
  {
    this.history.add ( new StatelessMachineHistoryItem ( this.word, this.stack,
        this.wordAccepted ) );
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
    if ( this.history.isEmpty () )
      throw new RuntimeException ( "history is empty" ); //$NON-NLS-1$
    final StatelessMachineHistoryItem historyItem = this.history.pop ();

    this.word = historyItem.getWord ();
    this.stack = historyItem.getStack ();
    this.wordAccepted = historyItem.getWordAccepted ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.machines.StatelessMachine#isWordAccepted()
   */
  public final boolean isWordAccepted ()
  {
    if ( this.stack.peak ().equals ( DefaultTerminalSymbol.EndMarker )
        && this.word.equals ( DefaultTerminalSymbol.EndMarker ) )
      return true;
    return false;
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
  
  
  public final Word getWord()
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
  }


  public Element getElement ()
  {
    return null; // TODO
  }


  /**
   * returns the number of possible reductions
   * 
   * @return set of available productions for reduction
   * @throws LRActionSetException
   */
  abstract protected LRActionSet getPossibleActions () throws LRActionSetException;
}
