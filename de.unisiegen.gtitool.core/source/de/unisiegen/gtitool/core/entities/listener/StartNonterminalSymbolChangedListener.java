package de.unisiegen.gtitool.core.entities.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.NonterminalSymbol;


/**
 * The listener interface for receiving start {@link NonterminalSymbol} changes.
 * 
 * @author Christian Fehler
 * @version $Id$
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
