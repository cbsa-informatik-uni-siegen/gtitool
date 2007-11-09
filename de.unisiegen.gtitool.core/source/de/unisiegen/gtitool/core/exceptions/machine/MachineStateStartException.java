package de.unisiegen.gtitool.core.exceptions.machine;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.State;


/**
 * The <code>MachineStateStartException</code> is used, if no start state is
 * defined or more than one start state is defined.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class MachineStateStartException extends MachineStateException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -6464106217286292160L;


  /**
   * Allocates a new <code>MachineStateStartException</code>.
   * 
   * @param pStateList The list of {@link State}s.
   */
  public MachineStateStartException ( ArrayList < State > pStateList )
  {
    super ( pStateList );
    // StateList
    if ( pStateList.size () == 1 )
    {
      throw new IllegalArgumentException ( "no exception: one start state" ); //$NON-NLS-1$
    }
    // Message and Description
    if ( pStateList.size () == 0 )
    {
      setMessage ( Messages
          .getString ( "MachineStateStartException.NoStartStateMessage" ) ); //$NON-NLS-1$
      setDescription ( Messages
          .getString ( "MachineStateStartException.NoStartStateDescription" ) ); //$NON-NLS-1$
    }
    else
    {
      setMessage ( Messages
          .getString ( "MachineStateStartException.MoreThanOneStartStateMessage" ) ); //$NON-NLS-1$
      StringBuilder states = new StringBuilder ();
      for ( int i = 0 ; i < pStateList.size () ; i++ )
      {
        states.append ( "\"" ); //$NON-NLS-1$
        states.append ( pStateList.get ( i ).getName () );
        states.append ( "\"" ); //$NON-NLS-1$
        if ( i < pStateList.size () - 2 )
        {
          states.append ( ", " ); //$NON-NLS-1$
        }
        if ( i == pStateList.size () - 2 )
        {
          states.append ( " " + Messages.getString ( "And" ) + " " ); //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$
        }
      }
      setDescription ( Messages.getString (
          "MachineStateStartException.MoreThanOneStartStateDescription", states //$NON-NLS-1$
              .toString () ) );
    }
  }
}
