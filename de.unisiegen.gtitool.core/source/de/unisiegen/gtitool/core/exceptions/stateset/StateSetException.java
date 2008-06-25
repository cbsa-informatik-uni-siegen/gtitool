package de.unisiegen.gtitool.core.exceptions.stateset;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.StateSet;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The {@link StateSetException} is used if the {@link StateSet} is not correct.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class StateSetException extends CoreException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 5805197047703666253L;


  /**
   * The {@link StateSet}.
   */
  private StateSet stateSet;


  /**
   * The {@link State}s.
   */
  private ArrayList < State > stateList;


  /**
   * Allocates a new {@link StateSetException}.
   * 
   * @param stateSet The {@link StateSet}.
   * @param stateList The {@link State}s.
   */
  public StateSetException ( StateSet stateSet, ArrayList < State > stateList )
  {
    super ();
    // StateSet
    if ( stateSet == null )
    {
      throw new NullPointerException ( "state set is null" ); //$NON-NLS-1$
    }
    this.stateSet = stateSet;
    // StateList
    if ( stateList == null )
    {
      throw new NullPointerException ( "state list is null" ); //$NON-NLS-1$
    }
    if ( stateList.size () < 2 )
    {
      throw new IllegalArgumentException (
          "state list must contain at least two elements" ); //$NON-NLS-1$
    }
    this.stateList = stateList;
  }


  /**
   * Returns the {@link State}s.
   * 
   * @return The {@link State}s.
   * @see #stateList
   */
  public final ArrayList < State > getState ()
  {
    return this.stateList;
  }


  /**
   * Returns the {@link State} with the given index.
   * 
   * @param index The index.
   * @return The {@link State} with the given index.
   * @see #stateList
   */
  public final State getState ( int index )
  {
    return this.stateList.get ( index );
  }


  /**
   * Returns the {@link StateSet}.
   * 
   * @return The {@link StateSet}.
   * @see #stateSet
   */
  public final StateSet getStateSet ()
  {
    return this.stateSet;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Throwable#toString()
   */
  @Override
  public String toString ()
  {
    String lineBreak = System.getProperty ( "line.separator" ); //$NON-NLS-1$
    StringBuilder result = new StringBuilder ( super.toString () );
    result.append ( lineBreak );
    result.append ( "state set: " ); //$NON-NLS-1$
    result.append ( this.stateSet );
    result.append ( lineBreak );
    result.append ( "states:    " ); //$NON-NLS-1$
    for ( int i = 0 ; i < this.stateSet.size () ; i++ )
    {
      if ( i > 0 )
      {
        result.append ( ", " ); //$NON-NLS-1$
      }
      result.append ( this.stateSet.get ( i ) );
    }
    return result.toString ();
  }
}
