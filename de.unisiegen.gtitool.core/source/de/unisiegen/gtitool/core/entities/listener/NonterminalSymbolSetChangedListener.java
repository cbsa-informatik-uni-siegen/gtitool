package de.unisiegen.gtitool.core.entities.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;


/**
 * The listener interface for receiving {@link NonterminalSymbolSet} changes.
 * 
 * @author Christian Fehler
 * @version $Id: NonterminalSymbolSetChangedListener.java 731 2008-04-04
 *          16:20:30Z fehler $
 */
public interface NonterminalSymbolSetChangedListener extends EventListener
{

  /**
   * Invoked when the {@link NonterminalSymbolSet} changed.
   * 
   * @param newNonterminalSymbolSet The new {@link NonterminalSymbolSet}.
   */
  public void nonterminalSymbolSetChanged (
      NonterminalSymbolSet newNonterminalSymbolSet );
}
