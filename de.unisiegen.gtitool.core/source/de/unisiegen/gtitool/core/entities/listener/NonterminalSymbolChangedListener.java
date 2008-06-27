package de.unisiegen.gtitool.core.entities.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.NonterminalSymbol;


/**
 * The listener interface for receiving {@link NonterminalSymbol} changes.
 * 
 * @author Christian Fehler
 * @version $Id: NonterminalSymbolChangedListener.java 731 2008-04-04 16:20:30Z
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
