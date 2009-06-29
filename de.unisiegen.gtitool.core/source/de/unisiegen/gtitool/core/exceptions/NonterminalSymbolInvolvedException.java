package de.unisiegen.gtitool.core.exceptions;


import de.unisiegen.gtitool.core.entities.NonterminalSymbol;


/**
 * Involved {@link NonterminalSymbol} interface.
 * 
 * @author Benjamin Mies
 * @version $Id: NonterminalSymbolInvolvedException.java 1372 2008-10-30
 *          08:36:20Z fehler $
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
