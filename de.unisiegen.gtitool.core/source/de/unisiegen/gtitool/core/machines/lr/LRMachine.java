package de.unisiegen.gtitool.core.machines.lr;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.LRAction;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.machine.MachineAmbigiousActionException;
import de.unisiegen.gtitool.core.machines.StateMachine;


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
   * Starts the {@link StateMachine} after a validation with the given
   * {@link Word}.
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
   * Try to automatically use the next transition
   * @throws MachineAmbigiousActionException 
   */
  public void autoTransit () throws MachineAmbigiousActionException;


  /**
   * Try to transit using LRAction transition
   * 
   * @param transition
   * @return true if the transit could be done
   */
  public boolean transit ( LRAction transition );
}
