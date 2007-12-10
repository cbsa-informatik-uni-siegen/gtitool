package de.unisiegen.gtitool.ui.style.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.Transition;


/**
 * The listener interface for receiving {@link Transition} changes.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface TransitionChangedListener extends EventListener
{

  /**
   * Invoked when the {@link Transition} changed.
   * 
   * @param pNewTransition The new {@link Transition}.
   */
  public void transitionChanged ( Transition pNewTransition );

}
