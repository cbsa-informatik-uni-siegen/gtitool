package de.unisiegen.gtitool.core.machines.dfa;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.machines.AbstractStateMachine;


/**
 * The class for deterministic finite automatons.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class DefaultDFA extends AbstractStateMachine implements DFA
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 6284801477317314839L;


  /**
   * Allocates a new {@link DFA}.
   * 
   * @param alphabet The {@link Alphabet} of this {@link DFA}.
   * @param pushDownAlphabet The push down {@link Alphabet} of this {@link DFA}.
   * @param usePushDownAlphabet The use push down {@link Alphabet}.
   */
  public DefaultDFA ( Alphabet alphabet, Alphabet pushDownAlphabet,
      boolean usePushDownAlphabet )
  {
    super ( alphabet, pushDownAlphabet, usePushDownAlphabet,
        ValidationElement.ALL_SYMBOLS, ValidationElement.EPSILON_TRANSITION,
        ValidationElement.TRANSITION_STACK_OPERATION,
        ValidationElement.FINAL_STATE,
        ValidationElement.MORE_THAN_ONE_START_STATE,
        ValidationElement.NO_START_STATE, ValidationElement.STATE_NAME,
        ValidationElement.STATE_NOT_REACHABLE,
        ValidationElement.SYMBOL_ONLY_ONE_TIME );
  }


  /**
   * {@inheritDoc}
   * 
   * @see AbstractStateMachine#getMachineType()
   */
  @Override
  public final MachineType getMachineType ()
  {
    return MachineType.DFA;
  }
}
