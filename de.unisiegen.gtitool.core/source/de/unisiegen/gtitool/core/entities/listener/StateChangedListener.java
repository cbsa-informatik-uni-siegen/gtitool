package de.unisiegen.gtitool.core.entities.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Transition;


/**
 * The listener interface for receiving {@link State} changes.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface StateChangedListener extends EventListener
{

  /**
   * Invoked when the {@link State} changed.
   * 
   * @param nameChanged Flag that indicates if the name has changed.
   * @param startChanged Flag that indicates if the start value has changed.
   * @param loopTransitionChanged Flag that indicates if the loop
   *          {@link Transition} value has changed.
   */
  public void stateChanged ( boolean nameChanged, boolean startChanged,
      boolean loopTransitionChanged );
}
