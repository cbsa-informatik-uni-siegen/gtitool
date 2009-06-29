package de.unisiegen.gtitool.core.exceptions;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Transition;


/**
 * Involved {@link Transition} interface.
 * 
 * @author Christian Fehler
 * @version $Id: TransitionsInvolvedException.java 1372 2008-10-30 08:36:20Z
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
