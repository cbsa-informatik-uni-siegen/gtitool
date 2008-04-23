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
public interface ParseableChangedListener < E extends Entity < E >> extends
    EventListener
{

  /**
   * Invoked when the {@link Entity} changed.
   * 
   * @param newEntity The new {@link Entity}.
   */
  public void parseableChanged ( E newEntity );

}
