package de.unisiegen.gtitool.ui.style.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.parser.Parseable;


/**
 * The listener interface for receiving {@link Parseable} changes.
 * 
 * @author Christian Fehler
 * @version $Id$
 * @param <E> The {@link Entity}.
 */
public interface ParseableChangedListener < E extends Entity > extends
    EventListener
{

  /**
   * Invoked when the {@link Object} changed.
   * 
   * @param newObject The new {@link Object}.
   */
  public void parseableChanged ( E newObject );

}
