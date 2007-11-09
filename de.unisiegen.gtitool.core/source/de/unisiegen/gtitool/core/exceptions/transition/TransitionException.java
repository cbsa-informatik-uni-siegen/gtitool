package de.unisiegen.gtitool.core.exceptions.transition;


import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The <code>TransitionException</code> is used if the {@link Symbol} is not
 * correct.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class TransitionException extends CoreException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -7654554788622031787L;


  /**
   * Allocates a new <code>TransitionException</code>.
   * 
   * @param pMessage The detail message.
   * @param pDescription The detail description.
   */
  public TransitionException ( String pMessage, String pDescription )
  {
    super ( pMessage, pDescription );
  }
}
