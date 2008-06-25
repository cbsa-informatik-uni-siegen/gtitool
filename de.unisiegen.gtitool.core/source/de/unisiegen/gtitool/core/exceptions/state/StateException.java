package de.unisiegen.gtitool.core.exceptions.state;


import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The {@link StateException} is used if the {@link Symbol} is not correct.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class StateException extends CoreException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -1337383732135123383L;


  /**
   * Allocates a new {@link StateException}.
   */
  public StateException ()
  {
    super ();
  }
}
