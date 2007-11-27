package de.unisiegen.gtitool.core.exceptions.state;


import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The <code>StateEmptyNameException</code> is used if the {@link State} is
 * not correct.
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
   * Allocates a new <code>StateEmptyNameException</code>.
   */
  public StateEmptyNameException ()
  {
    super ();
    // Message and Description
    setMessage ( Messages.getString ( "StateException.EmptyNameMessage" ) ); //$NON-NLS-1$
    setDescription ( Messages
        .getString ( "StateException.EmptyNameDescription" ) ); //$NON-NLS-1$
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
