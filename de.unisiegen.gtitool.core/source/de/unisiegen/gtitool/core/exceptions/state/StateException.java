package de.unisiegen.gtitool.core.exceptions.state;


import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The <code>StateException</code> is used if the {@link Symbol} is not
 * correct.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class StateException extends CoreException
{

  /**
   * Allocates a new <code>StateException</code>.
   */
  public StateException ()
  {
    super ();
  }
}
