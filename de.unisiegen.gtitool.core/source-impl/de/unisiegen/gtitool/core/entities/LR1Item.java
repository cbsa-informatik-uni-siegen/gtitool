package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;


/**
 * An LR1 item
 */
public class LR1Item extends LRItem
{

  /**
   * The serial version
   */
  private static final long serialVersionUID = 4240776560497597943L;


  /**
   * Constructs the LR1 item
   * 
   * @param nonterminalSymbol
   * @param productionWord
   * @param dotPosition
   * @param lookAhead
   */
  public LR1Item ( final NonterminalSymbol nonterminalSymbol,
      final ProductionWord productionWord, final int dotPosition,
      final TerminalSymbol lookAhead )
  {
    super ( nonterminalSymbol, productionWord, dotPosition );
    this.lookAhead = lookAhead;
  }


  /**
   * Increments the dot
   * 
   * @return the new item
   */
  public LR1Item incDot ()
  {
    return new LR1Item ( getNonterminalSymbol (), getProductionWord (),
        getDotPosition () + 1, this.lookAhead );
  }


  /**
   * Returns the production word after the dot
   * 
   * @return the production word
   */
  public ProductionWord getRemainingProductionWord ()
  {
    ArrayList < ProductionWordMember > members = new ArrayList < ProductionWordMember > ();

    for ( int i = getDotPosition () + 1 ; i < this.getProductionWord ().size () ; ++i )
      members.add ( this.getProductionWord ().get ( i ) );

    return new DefaultProductionWord ( members );
  }


  /**
   * Returns the lookahead token
   * 
   * @return the lookahead
   */
  public TerminalSymbol getLookAhead ()
  {
    return this.lookAhead;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.LRItem#toString()
   */
  @Override
  public String toString ()
  {
    return "[" + super.toString () + "," + getLookAhead ().toString () + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
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
   * {@inheritDoc}
   * 
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


  /**
   * The additional lookahead
   */
  private TerminalSymbol lookAhead;
}
