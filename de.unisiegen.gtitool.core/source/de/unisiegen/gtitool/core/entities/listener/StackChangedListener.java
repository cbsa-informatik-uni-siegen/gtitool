package de.unisiegen.gtitool.core.entities.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.Stack;


/**
 * The listener interface for receiving {@link Stack} changes.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface StackChangedListener extends EventListener
{

  /**
   * Invoked when the {@link Stack} changed.
   * 
   * @param newStack The new {@link Stack}.
   */
  public void stackChanged ( Stack newStack );
}
