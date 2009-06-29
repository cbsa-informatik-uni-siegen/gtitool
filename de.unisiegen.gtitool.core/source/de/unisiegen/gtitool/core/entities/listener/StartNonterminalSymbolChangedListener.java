package de.unisiegen.gtitool.core.entities.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.NonterminalSymbol;


/**
 * The listener interface for receiving start {@link NonterminalSymbol} changes.
 * 
 * @author Christian Fehler
 * @version $Id: StartNonterminalSymbolChangedListener.java 1372 2008-10-30
 *          08:36:20Z fehler $
 */
public interface StartNonterminalSymbolChangedListener extends EventListener
{

  /**
   * Invoked when the start {@link NonterminalSymbol} changed.
   * 
   * @param newStartNonterminalSymbol The new start {@link NonterminalSymbol}.
   */
  public void startNonterminalSymbolChanged (
      NonterminalSymbol newStartNonterminalSymbol );
}
