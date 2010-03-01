package de.unisiegen.gtitool.core.entities;


/**
 * An LR0Item is essentially like its base LRItem but made a distinct class.
 */
public class LR0Item extends LRItem
{

  /**
   * Represents an LR0 item
   */
  private static final long serialVersionUID = 1L;


  /**
   * Constructs an LR0 item
   * 
   * @param nonterminalSymbol
   * @param productionWord
   * @param dotPosition
   */
  public LR0Item ( final NonterminalSymbol nonterminalSymbol,
      final ProductionWord productionWord, final int dotPosition )
  {
    super ( nonterminalSymbol, productionWord, dotPosition );
  }


  /**
   * Increments the dot's position
   * 
   * @return A new LR0 item with the dot incremented
   */
  public LR0Item incDot ()
  {
    return new LR0Item ( getNonterminalSymbol (), getProductionWord (),
        getDotPosition () + 1 );
  }

}
