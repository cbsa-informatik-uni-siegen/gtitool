package de.unisiegen.gtitool.core.entities.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.Word;


/**
 * The listener interface for receiving {@link Word} changes.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface WordChangedListener extends EventListener
{

  /**
   * Invoked when the {@link Word} changed.
   * 
   * @param newWord The new {@link Word}.
   */
  public void wordChanged ( Word newWord );
}
