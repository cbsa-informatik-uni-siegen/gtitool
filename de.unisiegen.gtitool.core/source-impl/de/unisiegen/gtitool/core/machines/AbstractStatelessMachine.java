package de.unisiegen.gtitool.core.machines;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
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


  private Word word;


  private int wordIndex;


  private boolean wordAccepted;


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
  }


  protected TerminalSymbol currentTerminal ()
  {
    return this.wordIndex < this.word.size () ? new DefaultTerminalSymbol (
        this.word.get ( this.wordIndex ).toString () ) : null;
  }


  /**
   * TODO
   * 
   * @return
   */
  public Alphabet getAlphabet ()
  {
    return this.alphabet;
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.machines.lr.LRMachine#isWordAccepted()
   */
  public boolean isWordAccepted ()
  {
    return this.wordAccepted;
  }


  /**
   * TODO
   * 
   * @param word
   * @see de.unisiegen.gtitool.core.machines.lr.LRMachine#start(de.unisiegen.gtitool.core.entities.Word)
   */
  public void start ( Word word )
  {
    this.word = word;
    this.wordIndex = 0;
    this.wordAccepted = false;
  }


  protected void nextSymbol ()
  {
    ++this.wordIndex;
  }


  protected void accept ()
  {
    this.wordAccepted = true;
  }
}
