package de.unisiegen.gtitool.core.preferences.item;


import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;


/**
 * The {@link TerminalSymbolSet} item class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class TerminalSymbolSetItem implements Cloneable
{

  /**
   * The {@link TerminalSymbolSet} of this item.
   */
  private TerminalSymbolSet terminalSymbolSet;


  /**
   * The standard {@link TerminalSymbolSet} of this item.
   */
  private TerminalSymbolSet standardTerminalSymbolSet;


  /**
   * Allocates a new {@link TerminalSymbolSetItem}.
   * 
   * @param terminalSymbolSet The {@link TerminalSymbolSet} of this item.
   * @param standardTerminalSymbolSet The standard {@link TerminalSymbolSet} of
   *          this item.
   */
  public TerminalSymbolSetItem ( TerminalSymbolSet terminalSymbolSet,
      TerminalSymbolSet standardTerminalSymbolSet )
  {
    // TerminalSymbolSet
    setTerminalSymbolSet ( terminalSymbolSet );
    // StandardTerminalSymbolSet
    setStandardTerminalSymbolSet ( standardTerminalSymbolSet );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#clone()
   */
  @Override
  public final TerminalSymbolSetItem clone ()
  {
    return new TerminalSymbolSetItem ( this.terminalSymbolSet.clone (),
        this.standardTerminalSymbolSet.clone () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object other )
  {
    if ( other instanceof TerminalSymbolSetItem )
    {
      TerminalSymbolSetItem terminalSymbolSetItem = ( TerminalSymbolSetItem ) other;
      return ( ( this.terminalSymbolSet
          .equals ( terminalSymbolSetItem.terminalSymbolSet ) ) && ( this.standardTerminalSymbolSet
          .equals ( terminalSymbolSetItem.standardTerminalSymbolSet ) ) );
    }
    return false;
  }


  /**
   * Returns the standard {@link TerminalSymbolSet}.
   * 
   * @return The standard {@link TerminalSymbolSet}.
   * @see #standardTerminalSymbolSet
   */
  public final TerminalSymbolSet getStandardTerminalSymbolSet ()
  {
    return this.standardTerminalSymbolSet;
  }


  /**
   * Returns the {@link TerminalSymbolSet}.
   * 
   * @return The {@link TerminalSymbolSet}.
   * @see #terminalSymbolSet
   */
  public final TerminalSymbolSet getTerminalSymbolSet ()
  {
    return this.terminalSymbolSet;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#hashCode()
   */
  @Override
  public final int hashCode ()
  {
    return this.terminalSymbolSet.hashCode ()
        + this.standardTerminalSymbolSet.hashCode ();
  }


  /**
   * Restores the default {@link TerminalSymbolSet} of this item.
   */
  public final void restore ()
  {
    this.terminalSymbolSet = this.standardTerminalSymbolSet;
  }


  /**
   * Sets the standard {@link TerminalSymbolSet}.
   * 
   * @param standardTerminalSymbolSet The standard {@link TerminalSymbolSet} to
   *          set.
   */
  public final void setStandardTerminalSymbolSet (
      TerminalSymbolSet standardTerminalSymbolSet )
  {
    if ( standardTerminalSymbolSet == null )
    {
      throw new NullPointerException ( "standard terminal symbol set is null" ); //$NON-NLS-1$
    }
    this.standardTerminalSymbolSet = standardTerminalSymbolSet;
  }


  /**
   * Sets the {@link TerminalSymbolSet}.
   * 
   * @param terminalSymbolSet The {@link TerminalSymbolSet} to set.
   */
  public final void setTerminalSymbolSet ( TerminalSymbolSet terminalSymbolSet )
  {
    if ( terminalSymbolSet == null )
    {
      throw new NullPointerException ( "terminal symbol set is null" ); //$NON-NLS-1$
    }
    this.terminalSymbolSet = terminalSymbolSet;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public final String toString ()
  {
    return this.terminalSymbolSet.toString ();
  }
}
