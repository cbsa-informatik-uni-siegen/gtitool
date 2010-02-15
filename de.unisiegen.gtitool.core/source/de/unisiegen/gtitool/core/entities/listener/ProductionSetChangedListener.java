package de.unisiegen.gtitool.core.entities.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.ProductionSet;


/**
 The listener interface for receiving {@link ProductionSet} changes.
 * 
 * @author Christian Uhrhan
 */
public interface ProductionSetChangedListener extends EventListener
{
  /**
   * Invoked when the {@link ProductionSet} changed.
   * 
   * @param newProductionSet The new {@link ProductionSet}.
   */
  public void productionSetChanged ( ProductionSet newProductionSet );
}
