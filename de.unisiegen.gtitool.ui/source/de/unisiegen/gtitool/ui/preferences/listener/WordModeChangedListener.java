package de.unisiegen.gtitool.ui.preferences.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.ui.preferences.item.WordModeItem;


/**
 * The listener interface for receiving {@link Word} mode changes.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface WordModeChangedListener extends EventListener
{

  /**
   * Invoked when the {@link Word} mode changed.
   * 
   * @param newValue The new {@link WordModeItem}.
   */
  public void wordModeChanged ( WordModeItem newValue );

}
