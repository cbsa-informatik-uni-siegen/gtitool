package de.unisiegen.gtitool.core.entities.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.Production;


/**
 * The listener interface for receiving {@link Production} changes.
 * 
 * @author Christian Fehler
 * @version $Id: ProductionChangedListener.java 731 2008-04-04 16:20:30Z fehler
 *          $
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
