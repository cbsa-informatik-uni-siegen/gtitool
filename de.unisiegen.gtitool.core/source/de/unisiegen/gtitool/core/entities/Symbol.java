package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;


/**
 * The <code>Symbol</code> entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class Symbol implements Entity, Comparable < Symbol >
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
   * The start offset of this <code>Symbol</code> in the source code.
   * 
   * @see #getParserStartOffset()
   * @see #setParserStartOffset(int)
   */
  protected int parserStartOffset = -1;


  /**
   * The end offset of this <code>Symbol</code> in the source code.
   * 
   * @see #getParserEndOffset()
   * @see #setParserEndOffset(int)
   */
  protected int parserEndOffset = -1;


  /**
   * Allocates a new <code>Symbol</code>.
   * 
   * @param pName The name of this symbol.
   * @throws SymbolException If something with the <code>Symbol</code> is not
   *           correct.
   */
  public Symbol ( char pName ) throws SymbolException
  {
    // Name
    setName ( String.valueOf ( pName ) );
  }


  /**
   * Allocates a new <code>Symbol</code>.
   * 
   * @param pName The name of this symbol.
   * @throws SymbolException If something with the <code>Symbol</code> is not
   *           correct.
   */
  public Symbol ( Character pName ) throws SymbolException
  {
    // Name
    if ( pName == null )
    {
      throw new NullPointerException ( "name is null" ); //$NON-NLS-1$
    }
    setName ( String.valueOf ( pName.charValue () ) );
  }


  /**
   * Allocates a new <code>Symbol</code>.
   * 
   * @param pName The name of this symbol.
   * @throws SymbolException If something with the <code>Symbol</code> is not
   *           correct.
   */
  public Symbol ( String pName ) throws SymbolException
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
    try
    {
      return new Symbol ( this.name );
    }
    catch ( SymbolException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
      return null;
    }
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
   * Returns the parserEndOffset.
   * 
   * @return The parserEndOffset.
   * @see #parserEndOffset
   * @see #setParserEndOffset(int)
   */
  public final int getParserEndOffset ()
  {
    return this.parserEndOffset;
  }


  /**
   * Returns the parserStartOffset.
   * 
   * @return The parserStartOffset.
   * @see #parserStartOffset
   * @see #setParserStartOffset(int)
   */
  public final int getParserStartOffset ()
  {
    return this.parserStartOffset;
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
   * @throws SymbolException If something with the <code>Symbol</code> is not
   *           correct.
   */
  private final void setName ( String pName ) throws SymbolException
  {
    // Name
    if ( pName == null )
    {
      throw new NullPointerException ( "name is null" ); //$NON-NLS-1$
    }
    if ( pName.equals ( "" ) ) //$NON-NLS-1$
    {
      throw new SymbolException ();
    }
    this.name = pName;
  }


  /**
   * Sets the parser end offset.
   * 
   * @param pParserEndOffset The new parser end offset.
   * @see #getParserEndOffset()
   * @see #parserEndOffset
   */
  public final void setParserEndOffset ( int pParserEndOffset )
  {
    this.parserEndOffset = pParserEndOffset;
  }


  /**
   * Sets the parser start offset.
   * 
   * @param pParserStartOffset The new parser start offset.
   * @see #getParserStartOffset()
   * @see #parserStartOffset
   */
  public final void setParserStartOffset ( int pParserStartOffset )
  {
    this.parserStartOffset = pParserStartOffset;
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
