package de.unisiegen.gtitool.core.entities.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.State;


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
   * @param newState The new {@link State}.
   */
  public void stateChanged ( State newState );
}
