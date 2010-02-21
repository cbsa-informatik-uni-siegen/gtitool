package de.unisiegen.gtitool.core.machines.enfa;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.machines.AbstractStateMachine;


/**
 * The class for \u03B5 - nondeterministic finite automatons.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class DefaultENFA extends AbstractStateMachine implements ENFA
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 8012968054911675138L;


  /**
   * Allocates a new {@link ENFA}.
   * 
   * @param alphabet The {@link Alphabet} of this {@link ENFA}.
   * @param pushDownAlphabet The push down {@link Alphabet} of this {@link ENFA}
   *          .
   * @param usePushDownAlphabet The use push down {@link Alphabet}.
   */
  public DefaultENFA ( Alphabet alphabet, Alphabet pushDownAlphabet,
      boolean usePushDownAlphabet )
  {
    super ( alphabet, pushDownAlphabet, usePushDownAlphabet,
        ValidationElement.TRANSITION_STACK_OPERATION,
        ValidationElement.FINAL_STATE,
        ValidationElement.MORE_THAN_ONE_START_STATE,
        ValidationElement.NO_START_STATE, ValidationElement.STATE_NAME,
        ValidationElement.STATE_NOT_REACHABLE );
  }


  /**
   * {@inheritDoc}
   * 
   * @see AbstractStateMachine#getMachineType()
   */
  @Override
  public final MachineType getMachineType ()
  {
    return MachineType.ENFA;
  }
}
