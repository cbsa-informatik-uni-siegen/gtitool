package de.unisiegen.gtitool.core.entities.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.StateSet;


/**
 * The listener interface for receiving {@link StateSet} changes.
 * 
 * @author Christian Fehler
 * @version $Id: NonterminalSymbolSetChangedListener.java 731 2008-04-04
 *          16:20:30Z fehler $
 */
public interface StateSetChangedListener extends EventListener
{

  /**
   * Invoked when the {@link StateSet} changed.
   * 
   * @param newStateSet The new {@link StateSet}.
   */
  public void stateSetChanged ( StateSet newStateSet );
}
