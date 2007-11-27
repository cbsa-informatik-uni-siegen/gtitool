package de.unisiegen.gtitool.core.exceptions.machine;


import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The <code>MachineException</code> is used to be collected in a
 * {@link MachineValidationException}
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class MachineException extends CoreException
{

  /**
   * Allocates a new <code>MachineException</code>.
   */
  public MachineException ()
  {
    super ();
  }


  /**
   * Allocates a new <code>MachineException</code>.
   * 
   * @param pMessage The detail message.
   * @param pDescription The detail description.
   */
  public MachineException ( String pMessage, String pDescription )
  {
    super ( pMessage, pDescription );
  }


  /**
   * {@inheritDoc}
   * 
   * @see CoreException#getType()
   */
  @Override
  public abstract ErrorType getType ();
}
