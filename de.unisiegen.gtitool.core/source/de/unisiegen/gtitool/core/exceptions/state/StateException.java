package de.unisiegen.gtitool.core.exceptions.state;


import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The <code>StateException</code> is used if the {@link State} is not
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
  private static final long serialVersionUID = -7209512277199327343L;


  /**
   * Allocates a new <code>StateException</code>.
   * 
   * @param pMessage The detail message.
   * @param pDescription The detail description.
   */
  public StateException ( String pMessage, String pDescription )
  {
    super ( pMessage, pDescription );
  }
}
