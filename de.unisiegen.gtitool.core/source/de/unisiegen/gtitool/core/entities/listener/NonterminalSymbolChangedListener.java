package de.unisiegen.gtitool.core.entities.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.NonterminalSymbol;


/**
 * The listener interface for receiving {@link NonterminalSymbol} changes.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface NonterminalSymbolChangedListener extends EventListener
{

  /**
   * Invoked when the {@link NonterminalSymbol} changed.
   * 
   * @param newNonterminalSymbol The new {@link NonterminalSymbol}.
   */
  public void nonterminalSymbolChanged ( NonterminalSymbol newNonterminalSymbol );
}
