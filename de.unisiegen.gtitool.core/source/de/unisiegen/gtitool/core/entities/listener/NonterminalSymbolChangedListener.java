package de.unisiegen.gtitool.core.entities.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.NonterminalSymbol;


/**
 * The listener interface for receiving {@link NonterminalSymbol} changes.
 * 
 * @author Christian Fehler
 * @version $Id: NonterminalSymbolChangedListener.java 1372 2008-10-30 08:36:20Z
 *          fehler $
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
