package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;
import java.util.Iterator;

import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;
import de.unisiegen.gtitool.core.parser.style.PrettyString;


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
   * The offset of this {@link DefaultStack} in the source code.
   * 
   * @see #getParserOffset()
   * @see #setParserOffset(ParserOffset)
   */
  private ParserOffset parserOffset = NO_PARSER_OFFSET;


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
   * @param symbols The array of {@link Symbol}s.
   */
  public DefaultStack ( Iterable < Symbol > symbols )
  {
    this ();
    // Symbols
    if ( symbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    push ( symbols );
  }


  /**
   * Allocates a new <code>DefaultStack</code>.
   * 
   * @param symbols The array of {@link Symbol}s.
   */
  public DefaultStack ( Symbol ... symbols )
  {
    this ();
    // Symbols
    if ( symbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    push ( symbols );
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
   * @param symbol {@link Symbol} whose presence in this
   *          <code>DefaultStack</code> is to be tested.
   * @return <code>true</code> if the specified {@link Symbol} is present;
   *         <code>false</code> otherwise.
   */
  public final boolean contains ( Symbol symbol )
  {
    return this.symbolList.contains ( symbol );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object other )
  {
    if ( other instanceof DefaultStack )
    {
      DefaultStack defaultStack = ( DefaultStack ) other;
      return this.symbolList.equals ( defaultStack.symbolList );
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
   * @param index The index.
   * @return The {@link Symbol} with the given index.
   * @see #symbolList
   */
  public final Symbol get ( int index )
  {
    Iterator < Symbol > iterator = this.symbolList.iterator ();
    for ( int i = 0 ; i < index ; i++ )
    {
      iterator.next ();
    }
    return iterator.next ();
  }


  /**
   * {@inheritDoc}
   */
  public final ParserOffset getParserOffset ()
  {
    return this.parserOffset;
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
   * @param symbols The {@link Symbol}s to be pushed onto this
   *          <code>DefaultStack</code>.
   */
  public final void push ( Iterable < Symbol > symbols )
  {
    if ( symbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    for ( Symbol current : symbols )
    {
      push ( current );
    }
  }


  /**
   * Pushes the {@link Symbol} onto the top of this <code>DefaultStack</code>.
   * 
   * @param symbol The {@link Symbol} to be pushed onto this
   *          <code>DefaultStack</code>.
   */
  public final void push ( Symbol symbol )
  {
    // Symbol
    if ( symbol == null )
    {
      throw new NullPointerException ( "symbol is null" ); //$NON-NLS-1$
    }
    this.symbolList.add ( symbol );
  }


  /**
   * Pushes the {@link Symbol}s onto the top of this <code>DefaultStack</code>.
   * 
   * @param symbols The {@link Symbol}s to be pushed onto this
   *          <code>DefaultStack</code>.
   */
  public final void push ( Symbol ... symbols )
  {
    if ( symbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    for ( Symbol current : symbols )
    {
      push ( current );
    }
  }


  /**
   * {@inheritDoc}
   */
  public final void setParserOffset ( ParserOffset parserOffset )
  {
    this.parserOffset = parserOffset;
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
   * @see PrettyPrintable#toPrettyString()
   */
  public final PrettyString toPrettyString ()
  {
    PrettyString prettyString = new PrettyString ();
    for ( Symbol current : this.symbolList )
    {
      prettyString.addPrettyPrintable ( current );
    }
    return prettyString;
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
