package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;


/**
 * TODO
 */
public class LR1Item extends LR0Item
{

  public LR1Item ( NonterminalSymbol nonterminalSymbol,
      ProductionWord productionWord, int dotPosition, TerminalSymbol lookAhead )
  {
    super ( nonterminalSymbol, productionWord, dotPosition );
    this.lookAhead = lookAhead;
  }


  public LR1Item incDot ()
  {
    return new LR1Item ( getNonterminalSymbol (), getProductionWord (),
        getDotPosition () + 1, lookAhead );
  }


  public ProductionWord getRemainingProductionWord ()
  {
    ArrayList < ProductionWordMember > members = new ArrayList < ProductionWordMember > ();

    for ( int i = getDotPosition () + 2 ; i < this.getProductionWord ().size () ; ++i )
      members.add ( this.getProductionWord ().get ( i ) );

    return new DefaultProductionWord ( members );
  }


  public TerminalSymbol getLookAhead ()
  {
    return this.lookAhead;
  }


  public String toString ()
  {
    return "[" + super.toString () + "," + getLookAhead ().toString () + "]";
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

    return ( ( LR1Item ) ( other ) ).lookAhead.compareTo ( this.lookAhead );
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
    if ( other instanceof LR1Item )
      return super.equals ( other )
          && ( ( LR1Item ) ( other ) ).lookAhead == this.lookAhead;
    return false;
  }


  private TerminalSymbol lookAhead;
}
