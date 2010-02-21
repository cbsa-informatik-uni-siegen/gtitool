package de.unisiegen.gtitool.core.machines;

import de.unisiegen.gtitool.core.entities.Alphabet;


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
   * Allocates a new {@link AbstractStatelessMachine}
   *
   * @param alphabet The {@link Alphabet}
   */
  public AbstractStatelessMachine(final Alphabet alphabet)
  {
    if(alphabet == null)
      throw new NullPointerException("alphabet is null"); //$NON-NLS-1$
    this.alphabet = alphabet;
  }
  
  
  /**
   * TODO
   *
   * @return
   */
  public Alphabet getAlphabet()
  {
    return this.alphabet;
  }
}
