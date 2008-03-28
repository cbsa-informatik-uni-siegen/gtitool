package de.unisiegen.gtitool.core.exceptions.machine;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.exceptions.CoreException;
import de.unisiegen.gtitool.core.exceptions.StatesInvolvedException;


/**
 * The {@link MachineStateNameException} is used, if there are {@link State}s
 * with the same name.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class MachineStateNameException extends MachineException implements
    StatesInvolvedException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 3424843449164286994L;


  /**
   * The list of {@link State}s.
   */
  private ArrayList < State > stateList = new ArrayList < State > ();


  /**
   * Allocates a new {@link MachineStateNameException}.
   * 
   * @param stateList The list of {@link State}s.
   */
  public MachineStateNameException ( ArrayList < State > stateList )
  {
    // StateList
    if ( stateList == null )
    {
      throw new NullPointerException ( "state list is null" ); //$NON-NLS-1$
    }
    this.stateList = stateList;
    // StateList
    if ( stateList.size () < 2 )
    {
      throw new IllegalArgumentException ( "no exception: list size too small" ); //$NON-NLS-1$
    }
    // Message and Description
    setMessage ( Messages.getString ( "MachineStateNameException.Message" ) ); //$NON-NLS-1$
    setDescription ( Messages.getPrettyString (
        "MachineStateNameException.Description", stateList.get ( 0 ) ) ); //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see StatesInvolvedException#getState()
   */
  public final ArrayList < State > getState ()
  {
    return this.stateList;
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
    result.append ( "States:      " ); //$NON-NLS-1$
    for ( int i = 0 ; i < this.stateList.size () ; i++ )
    {
      if ( i > 0 )
      {
        result.append ( ", " ); //$NON-NLS-1$
      }
      result.append ( this.stateList.get ( i ).getName () );
    }
    return result.toString ();
  }
}
