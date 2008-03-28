package de.unisiegen.gtitool.core.exceptions.state;


import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The {@link StateEmptyNameException} is used if the {@link State} is not
 * correct.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class StateEmptyNameException extends StateException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 1009149372517056401L;


  /**
   * Allocates a new {@link StateEmptyNameException}.
   */
  public StateEmptyNameException ()
  {
    super ();
    // Message and description
    setPrettyMessage ( Messages.getPrettyString ( "StateException.EmptyNameMessage" ) ); //$NON-NLS-1$
    setPrettyDescription ( Messages
        .getPrettyString ( "StateException.EmptyNameDescription" ) ); //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see CoreException#getType()
   */
  @Override
  public final ErrorType getType ()
  {
    return ErrorType.ERROR;
  }
}
