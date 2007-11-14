package de.unisiegen.gtitool.ui.style.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.parser.Parseable;


/**
 * The listener interface for receiving {@link Parseable} changes.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface ParseableChangedListener extends EventListener
{

  /**
   * Invoked when the {@link Object} changed.
   * 
   * @param pNewObject The new {@link Object}.
   */
  public void parseableChanged ( Object pNewObject );

}
