package de.unisiegen.gtitool.core.machines.enfa;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.machines.AbstractMachine;
import de.unisiegen.gtitool.core.machines.Machine;


/**
 * The class for \u03B5 - nondeterministic finite automatons.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class DefaultENFA extends AbstractMachine implements ENFA
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 8012968054911675138L;


  /**
   * Allocates a new <code>ENFA</code>.
   * 
   * @param pAlphabet The {@link Alphabet} of this <code>ENFA</code>.
   */
  public DefaultENFA ( Alphabet pAlphabet )
  {
    super ( pAlphabet, ValidationElement.FINAL_STATE,
        ValidationElement.MORE_THAN_ONE_START_STATE,
        ValidationElement.NO_START_STATE, ValidationElement.STATE_NAME,
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
    return "ENFA"; //$NON-NLS-1$
  }
}
