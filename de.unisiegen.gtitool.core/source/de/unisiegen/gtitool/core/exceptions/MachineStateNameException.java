package de.unisiegen.gtitool.core.exceptions;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.State;


/**
 * The <code>MachineStateNameException</code> is used, if there are
 * {@link State}s with the same name.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class MachineStateNameException extends MachineStateException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 3424843449164286994L;


  /**
   * Allocates a new <code>MachineStateNameException</code>.
   * 
   * @param pStateList The list of {@link State}s.
   */
  public MachineStateNameException ( ArrayList < State > pStateList )
  {
    super ( pStateList );
    // StateList
    if ( pStateList.size () < 2 )
    {
      throw new IllegalArgumentException ( "no exception: list size too small" ); //$NON-NLS-1$
    }
    // Message and Description
    setMessage ( Messages.getString ( "MachineStateNameException.Message" ) ); //$NON-NLS-1$
    setDescription ( Messages.getString (
        "MachineStateNameException.Description", getState ( 0 ).getName () ) ); //$NON-NLS-1$
  }
}