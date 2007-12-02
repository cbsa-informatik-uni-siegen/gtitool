package de.unisiegen.gtitool.core.exceptions.machine;


import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The <code>MachineStateFinalException</code> is used, if no final state is
 * defined.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class MachineStateFinalException extends MachineException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -8821706287748109941L;


  /**
   * Allocates a new <code>MachineStateFinalException</code>.
   */
  public MachineStateFinalException ()
  {
    super ();
    // Message and Description
    setMessage ( Messages.getString ( "MachineStateFinalException.Message" ) ); //$NON-NLS-1$
    setDescription ( Messages
        .getString ( "MachineStateFinalException.Description" ) ); //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see CoreException#getType()
   */
  @Override
  public final ErrorType getType ()
  {
    return ErrorType.WARNING;
  }
}