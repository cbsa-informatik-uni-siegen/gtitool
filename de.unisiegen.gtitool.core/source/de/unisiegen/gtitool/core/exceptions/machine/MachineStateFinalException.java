package de.unisiegen.gtitool.core.exceptions.machine;


import de.unisiegen.gtitool.core.exceptions.CoreException;
import de.unisiegen.gtitool.core.i18n.Messages;


/**
 * The {@link MachineStateFinalException} is used, if no final state is defined.
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
   * Allocates a new {@link MachineStateFinalException}.
   */
  public MachineStateFinalException ()
  {
    super ();
    // Message and description
    setPrettyMessage ( Messages
        .getPrettyString ( "MachineStateFinalException.Message" ) ); //$NON-NLS-1$
    setPrettyDescription ( Messages
        .getPrettyString ( "MachineStateFinalException.Description" ) ); //$NON-NLS-1$
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
