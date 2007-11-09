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
   * @param pStateList The list of {@link State}s.
   */
  public MachineStateException ( ArrayList < State > pStateList )
  {
    // StateList
    if ( pStateList == null )
    {
      throw new NullPointerException ( "state list is null" ); //$NON-NLS-1$
    }
    this.stateList = pStateList;
  }


  /**
   * Returns the {@link State} at the specified position in the list of
   * {@link State}s.
   * 
   * @param pIndex The index of the {@link State} to return.
   * @return The {@link State} at the specified position in the list of
   *         {@link State}s.
   */
  public final State getState ( int pIndex )
  {
    return this.stateList.get ( pIndex );
  }


  /**
   * Returns the {@link State} list.
   * 
   * @return The {@link State} list.
   */
  public final ArrayList < State > getStateList ()
  {
    return this.stateList;
  }


  /**
   * Returns the number of {@link State}s in the list of {@link State}s.
   * 
   * @return The number of {@link State}s in the list of {@link State}s.
   */
  public final int stateSize ()
  {
    return this.stateList.size ();
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
    StringBuilder result = new StringBuilder ();
    result.append ( "Message:     " + getMessage () + lineBreak ); //$NON-NLS-1$
    result.append ( "Description: " + getDescription () + lineBreak ); //$NON-NLS-1$
    result.append ( "State:       " ); //$NON-NLS-1$
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
