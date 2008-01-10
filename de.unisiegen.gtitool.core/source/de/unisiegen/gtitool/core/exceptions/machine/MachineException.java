package de.unisiegen.gtitool.core.exceptions.machine;


import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The <code>MachineException</code> is used to be collected in a
 * {@link MachineValidationException}.
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
   * @param message The detail message.
   * @param description The detail description.
   */
  public MachineException ( String message, String description )
  {
    super ( message, description );
  }
}
