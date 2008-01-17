package de.unisiegen.gtitool.core.entities.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.Symbol;


/**
 * The listener interface for receiving {@link Symbol} changes.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface SymbolChangedListener extends EventListener
{

  /**
   * Invoked when the {@link Symbol} changed.
   * 
   * @param newSymbol The new {@link Symbol}.
   */
  public void symbolChanged ( Symbol newSymbol );

}
