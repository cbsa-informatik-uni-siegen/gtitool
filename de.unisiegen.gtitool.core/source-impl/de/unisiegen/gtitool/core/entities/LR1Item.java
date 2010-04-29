package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;


/**
 * TODO
 */
public class LR1Item extends LRItem
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
        getDotPosition () + 1, this.lookAhead );
  }


  public ProductionWord getRemainingProductionWord ()
  {
    ArrayList < ProductionWordMember > members = new ArrayList < ProductionWordMember > ();

    for ( int i = getDotPosition () + 1 ; i < this.getProductionWord ().size () ; ++i )
      members.add ( this.getProductionWord ().get ( i ) );

    return new DefaultProductionWord ( members );
  }


  public TerminalSymbol getLookAhead ()
  {
    return this.lookAhead;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.LRItem#itemString()
   */
  @Override
  public String itemString ()
  {
    return "[" + super.itemString () + "," + getLookAhead ().toString () + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.DefaultProduction#compareTo(de.unisiegen.gtitool.core.entities.Production)
   */
  @Override
  public int compareTo ( final Production other )
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
