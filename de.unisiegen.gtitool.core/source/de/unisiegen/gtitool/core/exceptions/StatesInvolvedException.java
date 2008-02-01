package de.unisiegen.gtitool.core.exceptions;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.State;


/**
 * Involved {@link State} interface.
 * 
 * @author Christian Fehler
 * @version $Id: MachineEpsilonTransitionException.java 90 2007-11-04 16:20:27Z
 *          fehler $
 */
public interface StatesInvolvedException
{

  /**
   * Returns the {@link State}s.
   * 
   * @return The {@link State}s.
   */
  public ArrayList < State > getState ();

}
