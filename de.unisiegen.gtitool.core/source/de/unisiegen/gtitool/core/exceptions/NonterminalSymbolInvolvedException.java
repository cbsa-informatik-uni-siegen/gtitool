package de.unisiegen.gtitool.core.exceptions;


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
   * Returns the {@link NonterminalSymbol}.
   * 
   * @return The {@link NonterminalSymbol}.
   */
  public NonterminalSymbol getNonterminalSymbol ();
}
