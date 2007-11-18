package de.unisiegen.gtitool.ui.style.listener;


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
   * @param pNewState The new {@link State}.
   */
  public void stateChanged ( State pNewState );

}