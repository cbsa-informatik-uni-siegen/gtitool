package de.unisiegen.gtitool.core.entities.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.TerminalSymbol;


/**
 * The listener interface for receiving {@link TerminalSymbol} changes.
 * 
 * @author Christian Fehler
 * @version $Id: TerminalSymbolChangedListener.java 731 2008-04-04 16:20:30Z
 *          fehler $
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
