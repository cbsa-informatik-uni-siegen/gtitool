package de.unisiegen.gtitool.core.entities.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.Transition;


/**
 * The listener interface for receiving {@link Transition} changes.
 * 
 * @author Christian Fehler
 * @version $Id: TransitionChangedListener.java 1372 2008-10-30 08:36:20Z fehler
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
