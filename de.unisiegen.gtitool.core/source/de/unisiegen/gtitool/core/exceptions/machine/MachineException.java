package de.unisiegen.gtitool.core.exceptions.machine;


import de.unisiegen.gtitool.core.exceptions.CoreException;
import de.unisiegen.gtitool.core.parser.style.PrettyString;


/**
 * The {@link MachineException} is used to be collected in a
 * {@link MachineValidationException}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class MachineException extends CoreException
{

  /**
   * Allocates a new {@link MachineException}.
   */
  public MachineException ()
  {
    super ();
  }


  /**
   * Allocates a new {@link MachineException}.
   * 
   * @param message The detail message.
   * @param description The detail description.
   */
  public MachineException ( String message, PrettyString description )
  {
    super ( message, description );
  }
}
