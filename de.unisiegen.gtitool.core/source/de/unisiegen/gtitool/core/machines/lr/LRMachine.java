package de.unisiegen.gtitool.core.machines.lr;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.LRAction;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.machines.Machine;


/**
 * TODO
 */
public interface LRMachine
{

  /**
   * Returns the {@link Alphabet}.
   * 
   * @return The {@link Alphabet}.
   */
  public Alphabet getAlphabet ();


  /**
   * Starts the {@link Machine} after a validation with the given {@link Word}.
   * 
   * @param word The {@link Word} to start with.
   */
  public void start ( Word word );


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


  public boolean transit ( LRAction transition );
}
