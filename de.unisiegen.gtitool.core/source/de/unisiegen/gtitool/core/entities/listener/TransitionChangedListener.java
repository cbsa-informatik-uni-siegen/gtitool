package de.unisiegen.gtitool.core.entities.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.Transition;


/**
 * The listener interface for receiving {@link Transition} changes.
 * 
 * @author Christian Fehler
 * @version $Id: TransitionChangedListener.java 731 2008-04-04 16:20:30Z fehler
 *          $
 */
public interface TransitionChangedListener extends EventListener
{

  /**
   * Invoked when the {@link Transition} changed.
   * 
   * @param newTransition The new {@link Transition}.
   */
  public void transitionChanged ( Transition newTransition );
}
