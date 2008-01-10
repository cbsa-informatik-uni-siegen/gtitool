package de.unisiegen.gtitool.core.exceptions.machine;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.State;


/**
 * The <code>MachineStateException</code> is used, if there are validation
 * errors with {@link State}s.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class MachineStateException extends MachineException
{

  /**
   * The list of {@link State}s.
   */
  private ArrayList < State > stateList = new ArrayList < State > ();


  /**
   * Allocates a new <code>MachineStateException</code>.
   * 
   * @param stateList The list of {@link State}s.
   */
  public MachineStateException ( ArrayList < State > stateList )
  {
    // StateList
    if ( stateList == null )
    {
      throw new NullPointerException ( "state list is null" ); //$NON-NLS-1$
    }
    this.stateList = stateList;
  }


  /**
   * Returns the {@link State} list.
   * 
   * @return The {@link State} list.
   */
  public final ArrayList < State > getState ()
  {
    return this.stateList;
  }


  /**
   * Returns the {@link State} at the specified position in the list of
   * {@link State}s.
   * 
   * @param index The index of the {@link State} to return.
   * @return The {@link State} at the specified position in the list of
   *         {@link State}s.
   */
  public final State getState ( int index )
  {
    return this.stateList.get ( index );
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
