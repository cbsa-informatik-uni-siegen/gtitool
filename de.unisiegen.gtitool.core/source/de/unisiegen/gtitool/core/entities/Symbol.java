package de.unisiegen.gtitool.core.entities;


import java.io.Serializable;


/**
 * The <code>Symbol</code> entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class Symbol implements Serializable, Cloneable,
    Comparable < Symbol >
{

  /**
   * the serial verion uid.
   */
  private static final long serialVersionUID = 121593210993378021L;


  /**
   * The name of this state.
   */
  private String name;


  /**
   * Allocates a new <code>Symbol</code>.
   * 
   * @param pName The name of this symbol.
   */
  public Symbol ( char pName )
  {
    // Name
    setName ( String.valueOf ( pName ) );
  }


  /**
   * Allocates a new <code>Symbol</code>.
   * 
   * @param pName The name of this symbol.
   */
  public Symbol ( Character pName )
  {
    // Name
    setName ( String.valueOf ( pName.charValue () ) );
  }


  /**
   * Allocates a new <code>Symbol</code>.
   * 
   * @param pName The name of this symbol.
   */
  public Symbol ( String pName )
  {
    // Name
    setName ( pName );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#clone()
   */
  @Override
  public final Symbol clone ()
  {
    return new Symbol ( this.name );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Comparable#compareTo(Object)
   */
  public final int compareTo ( Symbol pOther )
  {
    return this.name.compareTo ( pOther.name );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object pOther )
  {
    if ( pOther instanceof Symbol )
    {
      Symbol other = ( Symbol ) pOther;
      return this.name.equals ( other.name );
    }
    return false;
  }


  /**
   * Returns the name of this symbol.
   * 
   * @return The name of this symbol.
   * @see #name
   */
  public final String getName ()
  {
    return this.name;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#hashCode()
   */
  @Override
  public final int hashCode ()
  {
    return this.name.hashCode ();
  }


  /**
   * Sets the name of this symbol.
   * 
   * @param pName The name to set.
   */
  public final void setName ( String pName )
  {
    // Name
    if ( pName == null )
    {
      throw new NullPointerException ( "name is null" ); //$NON-NLS-1$
    }
    if ( pName.equals ( "" ) ) //$NON-NLS-1$
    {
      throw new IllegalArgumentException ( "name is empty" ); //$NON-NLS-1$
    }
    this.name = pName;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public final String toString ()
  {
    return this.name;
  }
}
