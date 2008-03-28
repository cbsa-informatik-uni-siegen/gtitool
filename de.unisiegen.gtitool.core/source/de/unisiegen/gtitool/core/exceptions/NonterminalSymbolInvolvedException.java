package de.unisiegen.gtitool.core.exceptions;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.NonterminalSymbol;


/**
 * Involved {@link NonterminalSymbol} interface.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public interface NonterminalSymbolInvolvedException
{

  /**
   * Returns the {@link NonterminalSymbol}s.
   * 
   * @return The {@link NonterminalSymbol}s.
   */
  public ArrayList < NonterminalSymbol > getNonterminalSymbol ();

}
