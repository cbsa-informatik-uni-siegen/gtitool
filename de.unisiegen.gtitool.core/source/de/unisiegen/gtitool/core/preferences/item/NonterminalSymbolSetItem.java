package de.unisiegen.gtitool.core.preferences.item;


import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;


/**
 * The {@link NonterminalSymbolSet} item class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class NonterminalSymbolSetItem implements Cloneable
{

  /**
   * The {@link NonterminalSymbolSet} of this item.
   */
  private NonterminalSymbolSet nonterminalSymbolSet;


  /**
   * The standard {@link NonterminalSymbolSet} of this item.
   */
  private NonterminalSymbolSet standardNonterminalSymbolSet;


  /**
   * Allocates a new {@link NonterminalSymbolSetItem}.
   * 
   * @param nonterminalSymbolSet The {@link NonterminalSymbolSet} of this item.
   * @param standardNonterminalSymbolSet The standard
   *          {@link NonterminalSymbolSet} of this item.
   */
  public NonterminalSymbolSetItem ( NonterminalSymbolSet nonterminalSymbolSet,
      NonterminalSymbolSet standardNonterminalSymbolSet )
  {
    // NonterminalSymbolSet
    setNonterminalSymbolSet ( nonterminalSymbolSet );
    // StandardNonterminalSymbolSet
    setStandardNonterminalSymbolSet ( standardNonterminalSymbolSet );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#clone()
   */
  @Override
  public final NonterminalSymbolSetItem clone ()
  {
    return new NonterminalSymbolSetItem ( this.nonterminalSymbolSet.clone (),
        this.standardNonterminalSymbolSet.clone () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object other )
  {
    if ( other instanceof NonterminalSymbolSetItem )
    {
      NonterminalSymbolSetItem nonterminalSymbolSetItem = ( NonterminalSymbolSetItem ) other;
      return ( ( this.nonterminalSymbolSet
          .equals ( nonterminalSymbolSetItem.nonterminalSymbolSet ) ) && ( this.standardNonterminalSymbolSet
          .equals ( nonterminalSymbolSetItem.standardNonterminalSymbolSet ) ) );
    }
    return false;
  }


  /**
   * Returns the {@link NonterminalSymbolSet}.
   * 
   * @return The {@link NonterminalSymbolSet}.
   * @see #nonterminalSymbolSet
   */
  public final NonterminalSymbolSet getNonterminalSymbolSet ()
  {
    return this.nonterminalSymbolSet;
  }


  /**
   * Returns the standard {@link NonterminalSymbolSet}.
   * 
   * @return The standard {@link NonterminalSymbolSet}.
   * @see #standardNonterminalSymbolSet
   */
  public final NonterminalSymbolSet getStandardNonterminalSymbolSet ()
  {
    return this.standardNonterminalSymbolSet;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#hashCode()
   */
  @Override
  public final int hashCode ()
  {
    return this.nonterminalSymbolSet.hashCode ()
        + this.standardNonterminalSymbolSet.hashCode ();
  }


  /**
   * Restores the default {@link NonterminalSymbolSet} of this item.
   */
  public final void restore ()
  {
    this.nonterminalSymbolSet = this.standardNonterminalSymbolSet;
  }


  /**
   * Sets the {@link NonterminalSymbolSet}.
   * 
   * @param nonterminalSymbolSet The {@link NonterminalSymbolSet} to set.
   */
  public final void setNonterminalSymbolSet (
      NonterminalSymbolSet nonterminalSymbolSet )
  {
    if ( nonterminalSymbolSet == null )
    {
      throw new NullPointerException ( "nonterminal symbol set is null" ); //$NON-NLS-1$
    }
    this.nonterminalSymbolSet = nonterminalSymbolSet;
  }


  /**
   * Sets the standard {@link NonterminalSymbolSet}.
   * 
   * @param standardNonterminalSymbolSet The standard
   *          {@link NonterminalSymbolSet} to set.
   */
  public final void setStandardNonterminalSymbolSet (
      NonterminalSymbolSet standardNonterminalSymbolSet )
  {
    if ( standardNonterminalSymbolSet == null )
    {
      throw new NullPointerException (
          "standard nonterminal symbol set is null" ); //$NON-NLS-1$
    }
    this.standardNonterminalSymbolSet = standardNonterminalSymbolSet;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public final String toString ()
  {
    return this.nonterminalSymbolSet.toString ();
  }
}
