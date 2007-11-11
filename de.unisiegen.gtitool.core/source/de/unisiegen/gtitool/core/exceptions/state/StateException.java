package de.unisiegen.gtitool.core.exceptions.state;


import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The <code>StateException</code> is used if the {@link Symbol} is not
 * correct.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class StateException extends CoreException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 1439251511317477443L;


  /**
   * Allocates a new <code>StateException</code>.
   */
  public StateException ()
  {
    super ();
    // Message and Description
    setMessage ( Messages.getString ( "StateException.EmptyNameMessage" ) ); //$NON-NLS-1$
    setDescription ( Messages
        .getString ( "StateException.EmptyNameDescription" ) ); //$NON-NLS-1$
  }
}
