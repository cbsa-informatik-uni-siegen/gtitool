package de.unisiegen.gtitool.core.entities.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.State;


/**
 * TODO
 */
public interface StateSelectionChangedListener extends EventListener
{

  /**
   * Invoked when the {@link State} changed.
   * 
   * @param state The new {@link State}.
   */
  public void stateSelectionChanged ( State state );

}
