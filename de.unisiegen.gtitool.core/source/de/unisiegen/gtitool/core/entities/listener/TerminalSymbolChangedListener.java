package de.unisiegen.gtitool.core.entities.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.TerminalSymbol;


/**
 * The listener interface for receiving {@link TerminalSymbol} changes.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface TerminalSymbolChangedListener extends EventListener
{

  /**
   * Invoked when the {@link TerminalSymbol} changed.
   * 
   * @param newTerminalSymbol The new {@link TerminalSymbol}.
   */
  public void terminalSymbolChanged ( TerminalSymbol newTerminalSymbol );

}
