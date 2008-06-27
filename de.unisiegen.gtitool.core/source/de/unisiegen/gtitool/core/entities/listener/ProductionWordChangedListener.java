package de.unisiegen.gtitool.core.entities.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.ProductionWord;


/**
 * The listener interface for receiving {@link ProductionWord} changes.
 * 
 * @author Christian Fehler
 * @version $Id: ProductionWordChangedListener.java 731 2008-04-04 16:20:30Z
 *          fehler $
 */
public interface ProductionWordChangedListener extends EventListener
{

  /**
   * Invoked when the {@link ProductionWord} changed.
   * 
   * @param newProductionWord The new {@link ProductionWord}.
   */
  public void productionWordChanged ( ProductionWord newProductionWord );
}
