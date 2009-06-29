package de.unisiegen.gtitool.core.entities.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;


/**
 * The listener interface for receiving {@link TerminalSymbolSet} changes.
 * 
 * @author Christian Fehler
 * @version $Id: TerminalSymbolSetChangedListener.java 1372 2008-10-30 08:36:20Z
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
