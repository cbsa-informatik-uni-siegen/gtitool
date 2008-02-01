package de.unisiegen.gtitool.core.exceptions;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Transition;


/**
 * Involved {@link Transition} interface.
 * 
 * @author Christian Fehler
 * @version $Id: MachineEpsilonTransitionException.java 90 2007-11-04 16:20:27Z
 *          fehler $
 */
public interface TransitionsInvolvedException
{

  /**
   * Returns the {@link Transition}s.
   * 
   * @return The {@link Transition}s.
   */
  public ArrayList < Transition > getTransition ();

}
