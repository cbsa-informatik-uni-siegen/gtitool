package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;


/**
 * Contains multiple productions with the same nonterminal on the left
 */
public interface MultiProduction extends PrettyPrintable,
    Entity < MultiProduction >
{

  /**
   * Returns the nonterminal associated with this MultiProduction
   * 
   * @return the nonterminal
   */
  public NonterminalSymbol getNonterminalSymbol ();


  /**
   * Returns a set of all "right sides" of this MultiProduction
   * 
   * @return the ProductionWords
   */
  public ProductionWordSet getProductionWords ();
}
