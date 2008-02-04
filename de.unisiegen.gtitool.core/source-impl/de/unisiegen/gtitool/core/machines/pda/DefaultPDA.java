package de.unisiegen.gtitool.core.machines.pda;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.machines.AbstractMachine;
import de.unisiegen.gtitool.core.machines.Machine;


/**
 * The class for push down automatons.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class DefaultPDA extends AbstractMachine implements PDA
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 5236169999605256506L;


  /**
   * Allocates a new {@link PDA}.
   * 
   * @param alphabet The {@link Alphabet} of this {@link PDA}.
   * @param pushDownAlphabet The push down {@link Alphabet} of this {@link PDA}.
   * @param usePushDownAlphabet The use push down {@link Alphabet}.
   */
  public DefaultPDA ( Alphabet alphabet, Alphabet pushDownAlphabet,
      boolean usePushDownAlphabet )
  {
    super ( alphabet, pushDownAlphabet, usePushDownAlphabet,
        ValidationElement.ALL_SYMBOLS, ValidationElement.EPSILON_TRANSITION,
        ValidationElement.FINAL_STATE,
        ValidationElement.MORE_THAN_ONE_START_STATE,
        ValidationElement.NO_START_STATE, ValidationElement.STATE_NAME,
        ValidationElement.SYMBOL_ONLY_ONE_TIME,
        ValidationElement.STATE_NOT_REACHABLE );
  }


  /**
   * Returns the {@link Machine} type.
   * 
   * @return The {@link Machine} type.
   */
  @Override
  public final String getMachineType ()
  {
    return "PDA"; //$NON-NLS-1$
  }
}
