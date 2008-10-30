package de.unisiegen.gtitool.core.exceptions;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.State;


/**
 * Involved {@link State} interface.
 * 
 * @author Christian Fehler
 * @version $Id$
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
