package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;


/**
 * An LRItem is like a production but contains a dot at the right side of it
 * (the ProductionWord). The dot is not part of the ProductionWord but saved in
 * this class as a position instead.
 */
public class LRItem extends DefaultProduction
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
  public LRItem ( final NonterminalSymbol nonterminalSymbol,
      final ProductionWord productionWord, final int dotPosition )
  {
    super ( nonterminalSymbol, productionWord );
    this.dotPosition = dotPosition;
  }


  /**
   * Returns the index of the dot
   * 
   * @return the index
   */
  public int getDotPosition ()
  {
    return this.dotPosition;
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

    return ( ( LRItem ) ( other ) ).dotPosition - this.dotPosition;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.DefaultProduction#equals(java.lang.Object)
   */
  @Override
  public boolean equals ( final Object other )
  {
    if ( other instanceof LRItem )
      return super.equals ( other )
          && ( ( LRItem ) ( other ) ).dotPosition == this.dotPosition;
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.DefaultProduction#toString()
   */
  @Override
  public String toString ()
  {
    final String leftSide = this.getNonterminalSymbol ().toString ()
        + " \u2192 "; //$NON-NLS-1$

    final String rightSide = this.getProductionWord ().toString ();

    int stringDotIndex = 0;

    for ( int i = 0 ; i != this.getDotPosition () ; ++i )
      stringDotIndex += this.getProductionWord ().get ( i ).toString ()
          .length ();

    return leftSide + rightSide.substring ( 0, stringDotIndex ) + dotString ()
        + rightSide.substring ( stringDotIndex );
  }


  /**
   * Returns a string that contains the dot
   * 
   * @return the string
   */
  public static String dotString ()
  {
    return "\u2022"; //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.DefaultProduction#toPrettyString()
   */
  @Override
  public PrettyString toPrettyString ()
  {
    return new PrettyString ( new PrettyToken ( toString (), Style.NONE ) );
  }


  /**
   * Tells if the dot is at the end
   * 
   * @return if the dot is at the end
   */
  public boolean dotIsAtEnd ()
  {
    return getDotPosition () == this.getProductionWord ().size ();
  }


  /**
   * Returns whether the dot of this LRItem precedes a Nonterminal symbol
   * 
   * @return See description
   */
  public boolean dotPrecedesNonterminal ()
  {
    return getDotPosition () < this.getProductionWord ().size ()
        && getProductionWord ().get ( getDotPosition () ) instanceof NonterminalSymbol;
  }


  /**
   * @return true if the dot precedes a terminal
   */
  public boolean dotPrecedesTerminal ()
  {
    return getDotPosition () < this.getProductionWord ().size ()
        && getProductionWord ().get ( getDotPosition () ) instanceof TerminalSymbol;
  }


  /**
   * @return The terminal/nonterminal after the dot
   */
  public ProductionWordMember getProductionWordMemberAfterDot ()
  {
    return getProductionWord ().get ( getDotPosition () );
  }


  /**
   * @return The nonterminal after the dot
   */
  public NonterminalSymbol getNonterminalAfterDot ()
  {
    return ( NonterminalSymbol ) getProductionWordMemberAfterDot ();
  }


  /**
   * @return The terminal after the dot
   */
  public TerminalSymbol getTerminalAfterDot ()
  {
    return ( TerminalSymbol ) getProductionWordMemberAfterDot ();
  }


  /**
   * The dot's position in the ProductionWord. 0 means, the point is at the
   * leftmost position size means, the point is at the rightmost position
   */
  private int dotPosition;
}
