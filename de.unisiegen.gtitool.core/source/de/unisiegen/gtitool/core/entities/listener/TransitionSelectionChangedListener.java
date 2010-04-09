package de.unisiegen.gtitool.core.entities.listener;

import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.Transition;


/**
 * TODO
 *
 */
public interface TransitionSelectionChangedListener extends EventListener
{

  /**
   * Invoked when the {@link Transition} changed.
   * 
   * @param newTransition The new {@link Transition}.
   */
  public void transitionSelectionChanged ( Transition newTransition );
}
