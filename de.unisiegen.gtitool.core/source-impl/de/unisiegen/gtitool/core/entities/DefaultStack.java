package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;
import java.util.Iterator;


/**
 * The <code>DefaultStack</code> entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class DefaultStack implements Stack
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -4411648655106144327L;


  /**
   * The start offset of this <code>DefaultStack</code> in the source code.
   * 
   * @see #getParserStartOffset()
   * @see #setParserStartOffset(int)
   */
  private int parserStartOffset = NO_PARSER_OFFSET;


  /**
   * The end offset of this <code>DefaultStack</code> in the source code.
   * 
   * @see #getParserEndOffset()
   * @see #setParserEndOffset(int)
   */
  private int parserEndOffset = NO_PARSER_OFFSET;


  /**
   * The list of {@link Symbol}s.
   */
  private ArrayList < Symbol > symbolList;


  /**
   * Allocates a new <code>DefaultStack</code>.
   */
  public DefaultStack ()
  {
    // SymbolList
    this.symbolList = new ArrayList < Symbol > ();
  }


  /**
   * Allocates a new <code>DefaultStack</code>.
   * 
   * @param pSymbols The array of {@link Symbol}s.
   */
  public DefaultStack ( Iterable < Symbol > pSymbols )
  {
    this ();
    // Symbols
    if ( pSymbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    push ( pSymbols );
  }


  /**
   * Allocates a new <code>DefaultStack</code>.
   * 
   * @param pSymbols The array of {@link Symbol}s.
   */
  public DefaultStack ( Symbol ... pSymbols )
  {
    this ();
    // Symbols
    if ( pSymbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    push ( pSymbols );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#clone()
   */
  @Override
  public final DefaultStack clone ()
  {
    DefaultStack newDefaultStack = new DefaultStack ();
    for ( Symbol current : this.symbolList )
    {
      newDefaultStack.push ( current.clone () );
    }
    return newDefaultStack;
  }


  /**
   * Returns <tt>true</tt> if this <code>DefaultStack</code> contains the
   * specified {@link Symbol}.
   * 
   * @param pSymbol {@link Symbol} whose presence in this
   *          <code>DefaultStack</code> is to be tested.
   * @return <code>true</code> if the specified {@link Symbol} is present;
   *         <code>false</code> otherwise.
   */
  public final boolean contains ( Symbol pSymbol )
  {
    return this.symbolList.contains ( pSymbol );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object pOther )
  {
    if ( pOther instanceof DefaultStack )
    {
      DefaultStack other = ( DefaultStack ) pOther;
      return this.symbolList.equals ( other.symbolList );
    }
    return false;
  }


  /**
   * Returns the {@link Symbol}s.
   * 
   * @return The {@link Symbol}s.
   */
  public final ArrayList < Symbol > get ()
  {
    return this.symbolList;
  }


  /**
   * Returns the {@link Symbol} with the given index.
   * 
   * @param pIndex The index.
   * @return The {@link Symbol} with the given index.
   * @see #symbolList
   */
  public final Symbol get ( int pIndex )
  {
    Iterator < Symbol > iterator = this.symbolList.iterator ();
    for ( int i = 0 ; i < pIndex ; i++ )
    {
      iterator.next ();
    }
    return iterator.next ();
  }


  /**
   * {@inheritDoc}
   */
  public final int getParserEndOffset ()
  {
    return this.parserEndOffset;
  }


  /**
   * {@inheritDoc}
   */
  public final int getParserStartOffset ()
  {
    return this.parserStartOffset;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#hashCode()
   */
  @Override
  public final int hashCode ()
  {
    return this.symbolList.hashCode ();
  }


  /**
   * Returns an iterator over the {@link Symbol}s in this
   * <code>DefaultStack</code>.
   * 
   * @return An iterator over the {@link Symbol}s in this
   *         <code>DefaultStack</code>.
   */
  public final Iterator < Symbol > iterator ()
  {
    return this.symbolList.iterator ();
  }


  /**
   * Looks at the {@link Symbol} at the top of this <code>DefaultStack</code>
   * without removing it.
   * 
   * @return The {@link Symbol} at the top of this <code>DefaultStack</code>.
   */
  public final Symbol peak ()
  {
    return this.symbolList.get ( this.symbolList.size () - 1 );
  }


  /**
   * Removes the {@link Symbol} at the top of this <code>DefaultStack</code>
   * and returns that {@link Symbol}.
   * 
   * @return The {@link Symbol} at the top of this <code>DefaultStack</code>.
   */
  public final Symbol pop ()
  {
    return this.symbolList.remove ( this.symbolList.size () - 1 );
  }


  /**
   * Pushes the {@link Symbol}s onto the top of this <code>DefaultStack</code>.
   * 
   * @param pSymbols The {@link Symbol}s to be pushed onto this
   *          <code>DefaultStack</code>.
   */
  public final void push ( Iterable < Symbol > pSymbols )
  {
    if ( pSymbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    for ( Symbol current : pSymbols )
    {
      push ( current );
    }
  }


  /**
   * Pushes the {@link Symbol} onto the top of this <code>DefaultStack</code>.
   * 
   * @param pSymbol The {@link Symbol} to be pushed onto this
   *          <code>DefaultStack</code>.
   */
  public final void push ( Symbol pSymbol )
  {
    // Symbol
    if ( pSymbol == null )
    {
      throw new NullPointerException ( "symbol is null" ); //$NON-NLS-1$
    }
    this.symbolList.add ( pSymbol );
  }


  /**
   * Pushes the {@link Symbol}s onto the top of this <code>DefaultStack</code>.
   * 
   * @param pSymbols The {@link Symbol}s to be pushed onto this
   *          <code>DefaultStack</code>.
   */
  public final void push ( Symbol ... pSymbols )
  {
    if ( pSymbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    for ( Symbol current : pSymbols )
    {
      push ( current );
    }
  }


  /**
   * {@inheritDoc}
   */
  public final void setParserEndOffset ( int pParserEndOffset )
  {
    this.parserEndOffset = pParserEndOffset;
  }


  /**
   * {@inheritDoc}
   */
  public final void setParserStartOffset ( int pParserStartOffset )
  {
    this.parserStartOffset = pParserStartOffset;
  }


  /**
   * Returns the number of {@link Symbol}s in this <code>DefaultStack</code>.
   * 
   * @return The number of {@link Symbol}s in this <code>DefaultStack</code>.
   */
  public final int size ()
  {
    return this.symbolList.size ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#toString()
   */
  @Override
  public final String toString ()
  {
    StringBuilder result = new StringBuilder ();
    for ( Symbol current : this.symbolList )
    {
      result.append ( current.getName () );
    }
    return result.toString ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#toString()
   */
  public final String toStringDebug ()
  {
    StringBuilder result = new StringBuilder ();
    for ( Symbol current : this.symbolList )
    {
      result.append ( current.getName () );
    }
    return result.toString ();
  }
}
