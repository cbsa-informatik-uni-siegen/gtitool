package de.unisiegen.gtitool.core.machines.nfa;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.machines.AbstractMachine;


/**
 * The class for nondeterministic finite automatons.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class DefaultNFA extends AbstractMachine implements NFA
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 6954358306581597015L;


  /**
   * Allocates a new {@link NFA}.
   * 
   * @param alphabet The {@link Alphabet} of this {@link NFA}.
   * @param pushDownAlphabet The push down {@link Alphabet} of this {@link NFA}.
   * @param usePushDownAlphabet The use push down {@link Alphabet}.
   */
  public DefaultNFA ( Alphabet alphabet, Alphabet pushDownAlphabet,
      boolean usePushDownAlphabet )
  {
    super ( alphabet, pushDownAlphabet, usePushDownAlphabet,
        ValidationElement.EPSILON_TRANSITION,
        ValidationElement.TRANSITION_STACK_OPERATION,
        ValidationElement.FINAL_STATE,
        ValidationElement.MORE_THAN_ONE_START_STATE,
        ValidationElement.NO_START_STATE, ValidationElement.STATE_NAME,
        ValidationElement.STATE_NOT_REACHABLE );
  }


  /**
   * {@inheritDoc}
   * 
   * @see AbstractMachine#getMachineType()
   */
  @Override
  public final MachineType getMachineType ()
  {
    return MachineType.NFA;
  }
}
