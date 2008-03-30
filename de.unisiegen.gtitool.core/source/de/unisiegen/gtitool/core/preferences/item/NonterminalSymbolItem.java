package de.unisiegen.gtitool.core.preferences.item;


import de.unisiegen.gtitool.core.entities.NonterminalSymbol;


/**
 * The {@link NonterminalSymbol} item class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class NonterminalSymbolItem implements Cloneable
{

  /**
   * The {@link NonterminalSymbol} of this item.
   */
  private NonterminalSymbol nonterminalSymbol;


  /**
   * The standard {@link NonterminalSymbol} of this item.
   */
  private NonterminalSymbol standardNonterminalSymbol;


  /**
   * Allocates a new {@link NonterminalSymbolItem}.
   * 
   * @param nonterminalSymbol The {@link NonterminalSymbol} of this item.
   * @param standardNonterminalSymbol The standard {@link NonterminalSymbol} of
   *          this item.
   */
  public NonterminalSymbolItem ( NonterminalSymbol nonterminalSymbol,
      NonterminalSymbol standardNonterminalSymbol )
  {
    // NonterminalSymbol
    setNonterminalSymbol ( nonterminalSymbol );
    // StandardNonterminalSymbol
    setStandardNonterminalSymbol ( standardNonterminalSymbol );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#clone()
   */
  @Override
  public final NonterminalSymbolItem clone ()
  {
    return new NonterminalSymbolItem ( this.nonterminalSymbol.clone (),
        this.standardNonterminalSymbol.clone () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object other )
  {
    if ( other instanceof NonterminalSymbolItem )
    {
      NonterminalSymbolItem nonterminalSymbolItem = ( NonterminalSymbolItem ) other;
      return ( ( this.nonterminalSymbol
          .equals ( nonterminalSymbolItem.nonterminalSymbol ) ) && ( this.standardNonterminalSymbol
          .equals ( nonterminalSymbolItem.standardNonterminalSymbol ) ) );
    }
    return false;
  }


  /**
   * Returns the {@link NonterminalSymbol}.
   * 
   * @return The {@link NonterminalSymbol}.
   * @see #nonterminalSymbol
   */
  public final NonterminalSymbol getNonterminalSymbol ()
  {
    return this.nonterminalSymbol;
  }


  /**
   * Returns the standard {@link NonterminalSymbol}.
   * 
   * @return The standard {@link NonterminalSymbol}.
   * @see #standardNonterminalSymbol
   */
  public final NonterminalSymbol getStandardNonterminalSymbol ()
  {
    return this.standardNonterminalSymbol;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#hashCode()
   */
  @Override
  public final int hashCode ()
  {
    return this.nonterminalSymbol.hashCode ()
        + this.standardNonterminalSymbol.hashCode ();
  }


  /**
   * Restores the default {@link NonterminalSymbol} of this item.
   */
  public final void restore ()
  {
    this.nonterminalSymbol = this.standardNonterminalSymbol;
  }


  /**
   * Sets the {@link NonterminalSymbol}.
   * 
   * @param nonterminalSymbol The {@link NonterminalSymbol} to set.
   */
  public final void setNonterminalSymbol ( NonterminalSymbol nonterminalSymbol )
  {
    if ( nonterminalSymbol == null )
    {
      throw new NullPointerException ( "nonterminal symbol is null" ); //$NON-NLS-1$
    }
    this.nonterminalSymbol = nonterminalSymbol;
  }


  /**
   * Sets the standard {@link NonterminalSymbol}.
   * 
   * @param standardNonterminalSymbol The standard {@link NonterminalSymbol} to
   *          set.
   */
  public final void setStandardNonterminalSymbol (
      NonterminalSymbol standardNonterminalSymbol )
  {
    if ( standardNonterminalSymbol == null )
    {
      throw new NullPointerException ( "standard nonterminal symbol is null" ); //$NON-NLS-1$
    }
    this.standardNonterminalSymbol = standardNonterminalSymbol;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public final String toString ()
  {
    return this.nonterminalSymbol.toString ();
  }
}
