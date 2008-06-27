package de.unisiegen.gtitool.core.entities.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;


/**
 * The listener interface for receiving {@link TerminalSymbolSet} changes.
 * 
 * @author Christian Fehler
 * @version $Id: TerminalSymbolSetChangedListener.java 731 2008-04-04 16:20:30Z
 *          fehler $
 */
public interface TerminalSymbolSetChangedListener extends EventListener
{

  /**
   * Invoked when the {@link TerminalSymbolSet} changed.
   * 
   * @param newTerminalSymbolSet The new {@link TerminalSymbolSet}.
   */
  public void terminalSymbolSetChanged ( TerminalSymbolSet newTerminalSymbolSet );
}
