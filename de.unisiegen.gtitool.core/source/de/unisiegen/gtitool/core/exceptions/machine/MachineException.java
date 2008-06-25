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
   * The serial version uid.
   */
  private static final long serialVersionUID = -7104191444360119894L;


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
  public MachineException ( PrettyString message, PrettyString description )
  {
    super ( message, description );
  }
}
