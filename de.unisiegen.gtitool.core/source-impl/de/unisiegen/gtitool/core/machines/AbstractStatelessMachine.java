package de.unisiegen.gtitool.core.machines;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultStack;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.Stack;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.i18n.Messages;
import de.unisiegen.gtitool.core.machines.pda.DefaultTDP;
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
  private java.util.Stack<StatelessMachineHistoryItem> history;


  /**
   * Returns the {@link StateMachine} with the given {@link StateMachine} type.
   * 
   * @param machineType The {@link StateMachine} type.
   * @param alphabet The {@link Alphabet}.
   * @return The {@link StateMachine} with the given {@link StateMachine} type.
   * @throws StoreException If the {@link StateMachine} type is unknown.
   */
  public static final StatelessMachine createMachine ( String machineType,
      Alphabet alphabet ) throws StoreException
  {
    if ( machineType.equals ( ( "TDP" ) ) ) //$NON-NLS-1$
      return new DefaultTDP ( alphabet );
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
  }


  /**
   * actual input terminal
   * 
   * @return The actual {@link TerminalSymbol}
   */
  protected TerminalSymbol currentTerminal ()
  {
    return this.wordIndex < this.word.size () ? new DefaultTerminalSymbol (
        this.word.get ( this.wordIndex ).toString () ) : null;
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
   * gets the accept-status
   * 
   * @return The accept-status
   */
  public boolean isWordAccepted ()
  {
    return this.wordAccepted;
  }


  /**
   * Sets the Start input {@link Word}
   * 
   * @param word the input {@link Word}
   */
  public void start ( final Word word )
  {
    this.word = word;
    this.wordIndex = 0;
    this.wordAccepted = false;
  }


  /**
   * increment current input position
   */
  protected void nextSymbol ()
  {
    ++this.wordIndex;
  }


  /**
   * accept the input
   */
  protected void accept ()
  {
    this.wordAccepted = true;
  }


  public final Stack getStack ()
  {
    return this.stack;
  }
  
  public final void stop()
  {
    //TODO: implement
  }
}
