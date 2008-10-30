package de.unisiegen.gtitool.core.entities.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.Production;


/**
 * The listener interface for receiving {@link Production} changes.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface ProductionChangedListener extends EventListener
{

  /**
   * Invoked when the {@link Production} changed.
   * 
   * @param newProduction The new {@link Production}.
   */
  public void productionChanged ( Production newProduction );
}
