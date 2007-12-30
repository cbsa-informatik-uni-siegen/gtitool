package de.unisiegen.gtitool.core.exceptions.machine;


import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The <code>MachineStateNotReachableException</code> is used, if a
 * {@link State} is not reachable.
 * 
 * @author Christian Fehler
 * @version $Id: MachineStateNotReachableException.java 239 2007-11-27 23:13:57Z
 *          fehler $
 */
public final class MachineStateNotReachableException extends MachineException
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
   * Allocates a new <code>MachineStateNotReachableException</code>.
   * 
   * @param pState The {@link State}.
   */
  public MachineStateNotReachableException ( State pState )
  {
    super ();
    // State
    if ( pState == null )
    {
      throw new NullPointerException ( "state is null" ); //$NON-NLS-1$
    }
    this.state = pState;
    // Message and Description
    setMessage ( Messages
        .getString ( "MachineStateNotReachableException.Message" ) ); //$NON-NLS-1$
    setDescription ( Messages.getString (
        "MachineStateNotReachableException.Description", this.state ) ); //$NON-NLS-1$
  }


  /**
   * Returns the {@link State}.
   * 
   * @return The {@link State}.
   * @see #state
   */
  public final State getState ()
  {
    return this.state;
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
    result.append ( getState ().getName () );
    return result.toString ();
  }
}
