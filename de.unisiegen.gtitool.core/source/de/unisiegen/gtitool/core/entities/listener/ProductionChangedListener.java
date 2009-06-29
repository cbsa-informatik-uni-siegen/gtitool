package de.unisiegen.gtitool.core.entities.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.Production;


/**
 * The listener interface for receiving {@link Production} changes.
 * 
 * @author Christian Fehler
 * @version $Id: ProductionChangedListener.java 1372 2008-10-30 08:36:20Z fehler
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
