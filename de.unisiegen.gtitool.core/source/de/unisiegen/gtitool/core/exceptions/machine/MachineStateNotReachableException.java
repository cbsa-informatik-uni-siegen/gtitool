package de.unisiegen.gtitool.core.exceptions.machine;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.exceptions.CoreException;
import de.unisiegen.gtitool.core.exceptions.StatesInvolvedException;
import de.unisiegen.gtitool.core.i18n.Messages;


/**
 * The {@link MachineStateNotReachableException} is used, if a {@link State} is
 * not reachable.
 * 
 * @author Christian Fehler
 * @version $Id: MachineStateNotReachableException.java 239 2007-11-27 23:13:57Z
 *          fehler $
 */
public final class MachineStateNotReachableException extends MachineException
    implements StatesInvolvedException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -1439009206933695235L;


  /**
   * The {@link State}.
   */
  private State state;


  /**
   * Allocates a new {@link MachineStateNotReachableException}.
   * 
   * @param state The {@link State}.
   */
  public MachineStateNotReachableException ( State state )
  {
    super ();
    // State
    if ( state == null )
    {
      throw new NullPointerException ( "state is null" ); //$NON-NLS-1$
    }
    this.state = state;
    // Message and description
    setPrettyMessage ( Messages
        .getPrettyString ( "MachineStateNotReachableException.Message" ) ); //$NON-NLS-1$
    setPrettyDescription ( Messages.getPrettyString (
        "MachineStateNotReachableException.Description", this.state ) ); //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see StatesInvolvedException#getState()
   */
  public final ArrayList < State > getState ()
  {
    ArrayList < State > result = new ArrayList < State > ( 1 );
    result.add ( this.state );
    return result;
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


  /**
   * {@inheritDoc}
   * 
   * @see Throwable#toString()
   */
  @Override
  public final String toString ()
  {
    String lineBreak = System.getProperty ( "line.separator" ); //$NON-NLS-1$
    StringBuilder result = new StringBuilder ( super.toString () );
    result.append ( lineBreak );
    result.append ( "State:       " ); //$NON-NLS-1$
    result.append ( this.state.getName () );
    return result.toString ();
  }
}
