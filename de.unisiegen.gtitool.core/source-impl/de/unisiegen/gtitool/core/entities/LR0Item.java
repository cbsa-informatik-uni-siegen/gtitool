package de.unisiegen.gtitool.core.entities;


/**
 * An LR0Item is like a production but contains a dot at the right side of it
 * (the ProductionWord). The dot is not part of the ProductionWord but saved in
 * this class as a position instead.
 */
public class LR0Item extends DefaultProduction
{

  /**
   * TODO
   */
  private static final long serialVersionUID = 1L;


  /**
   * TODO
   * 
   * @param nonterminalSymbol
   * @param productionWord
   * @param dotPosition
   */
  public LR0Item ( NonterminalSymbol nonterminalSymbol,
      ProductionWord productionWord, int dotPosition )
  {
    super ( nonterminalSymbol, productionWord );
    this.dotPosition = dotPosition;
  }


  /**
   * TODO
   * 
   * @return
   */
  public int getDotPosition ()
  {
    return this.dotPosition;
  }


  public boolean dotIsAtEnd ()
  {
    return getDotPosition () == this.getProductionWord ().size ();
  }


  /**
   * Returns whether the dot of this LR0Item precedes a Nonterminal symbol
   * 
   * @return See description
   */
  public boolean dotPrecedesNonterminal ()
  {
    return getDotPosition () < this.getProductionWord ().size ()
        && getProductionWord ().get ( getDotPosition () ) instanceof NonterminalSymbol;
  }


  public ProductionWordMember getProductionWordMemberAfterDot ()
  {
    return getProductionWord ().get ( getDotPosition () );
  }


  /**
   * TODO
   * 
   * @return
   */
  public NonterminalSymbol getNonterminalAfterDot ()
  {
    return ( NonterminalSymbol ) getProductionWordMemberAfterDot ();
  }


  public LR0Item incDot ()
  {
    return new LR0Item ( getNonterminalSymbol (), getProductionWord (),
        getDotPosition () + 1);
  }


  /**
   * TODO
   * 
   * @param other
   * @return
   * @see de.unisiegen.gtitool.core.entities.DefaultProduction#compareTo(de.unisiegen.gtitool.core.entities.Production)
   */
  @Override
  public int compareTo ( Production other )
  {
    int compare = super.compareTo ( other );

    if ( compare != 0 )
      return compare;

    return ( ( LR0Item ) ( other ) ).dotPosition - this.dotPosition;
  }


  /**
   * TODO
   * 
   * @param other
   * @return
   * @see de.unisiegen.gtitool.core.entities.DefaultProduction#equals(java.lang.Object)
   */
  @Override
  public boolean equals ( final Object other )
  {
    if ( other instanceof LR0Item )
      return super.equals ( other )
          && ( ( LR0Item ) ( other ) ).dotPosition == this.dotPosition;
    return false;
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.entities.DefaultProduction#toString()
   */
  @Override
  public String toString ()
  {
    final String leftSide = this.getNonterminalSymbol ().toString ()
        + " \u2192 "; //$NON-NLS-1$

    final String rightSide = this.getProductionWord ().toString ();

    int stringDotIndex = 0;
    
    for(int i = 0; i != this.getDotPosition(); ++i)
      stringDotIndex += this.getProductionWord ().get(i).toString ().length ();
    
    return leftSide + rightSide.substring ( 0, stringDotIndex ) + "\u2022" //$NON-NLS-1$
        + rightSide.substring ( stringDotIndex );
  }


  /**
   * The dot's position in the ProductionWord. 0 means, the point is at the
   * leftmost position size means, the point is at the rightmost position
   */
  private int dotPosition;
}