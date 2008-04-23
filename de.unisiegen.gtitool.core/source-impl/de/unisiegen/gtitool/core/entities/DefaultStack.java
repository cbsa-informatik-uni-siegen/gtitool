package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;
import java.util.Iterator;

import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;
import de.unisiegen.gtitool.core.parser.style.PrettyString;


/**
 * The {@link DefaultStack} entity.
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
   * Allocates a new {@link DefaultStack}.
   */
  public DefaultStack ()
  {
    // SymbolList
    this.symbolList = new ArrayList < Symbol > ();
  }


  /**
   * Allocates a new {@link DefaultStack}.
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
   * Allocates a new {@link DefaultStack}.
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
   * Removes all {@link Symbol}s.
   */
  public final void clear ()
  {
    this.symbolList.clear ();
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
   * {@inheritDoc}
   * 
   * @see Comparable#compareTo( Object)
   */
  public final int compareTo ( @SuppressWarnings ( "unused" )
  Stack other )
  {
    // TODOCF
    return 0;
  }


  /**
   * Returns true if this {@link DefaultStack} contains the specified
   * {@link Symbol}.
   * 
   * @param symbol {@link Symbol} whose presence in this {@link DefaultStack} is
   *          to be tested.
   * @return true if the specified {@link Symbol} is present; false otherwise.
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
   * Returns an iterator over the {@link Symbol}s in this {@link DefaultStack}.
   * 
   * @return An iterator over the {@link Symbol}s in this {@link DefaultStack}.
   */
  public final Iterator < Symbol > iterator ()
  {
    return this.symbolList.iterator ();
  }


  /**
   * Looks at the {@link Symbol} at the top of this {@link DefaultStack} without
   * removing it.
   * 
   * @return The {@link Symbol} at the top of this {@link DefaultStack}.
   */
  public final Symbol peak ()
  {
    return this.symbolList.get ( this.symbolList.size () - 1 );
  }


  /**
   * Looks at the {@link Symbol}s at the top of this {@link DefaultStack}
   * without removing them.
   * 
   * @param size The number of returned symbols.
   * @return The {@link Symbol} at the top of this {@link DefaultStack}.
   */
  public ArrayList < Symbol > peak ( int size )
  {
    ArrayList < Symbol > result = new ArrayList < Symbol > ();
    if ( size > this.symbolList.size () )
    {
      result.addAll ( this.symbolList );
      return result;
    }

    for ( int i = 0 ; i < size ; i++ )
    {
      result.add ( this.symbolList.get ( this.symbolList.size () - size + i ) );
    }
    return result;
  }


  /**
   * Removes the {@link Symbol} at the top of this {@link DefaultStack} and
   * returns that {@link Symbol}.
   * 
   * @return The {@link Symbol} at the top of this {@link DefaultStack}.
   */
  public final Symbol pop ()
  {
    return this.symbolList.remove ( this.symbolList.size () - 1 );
  }


  /**
   * Removes the {@link Symbol}s at the top of this {@link DefaultStack} and
   * returns the {@link Symbol}s.
   * 
   * @param size The number of returned symbols.
   * @return The {@link Symbol}s at the top of this {@link DefaultStack}.
   */
  public final ArrayList < Symbol > pop ( int size )
  {
    ArrayList < Symbol > result = new ArrayList < Symbol > ();
    if ( size > this.symbolList.size () )
    {
      result.addAll ( this.symbolList );
      this.symbolList.clear ();
      return result;
    }

    int oldSize = this.symbolList.size ();
    for ( int i = 0 ; i < size ; i++ )
    {
      result.add ( this.symbolList.remove ( oldSize - size ) );
    }
    return result;
  }


  /**
   * Pushes the {@link Symbol}s onto the top of this {@link DefaultStack}.
   * 
   * @param symbols The {@link Symbol}s to be pushed onto this
   *          {@link DefaultStack}.
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
   * Pushes the {@link Symbol} onto the top of this {@link DefaultStack}.
   * 
   * @param symbol The {@link Symbol} to be pushed onto this
   *          {@link DefaultStack}.
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
   * Pushes the {@link Symbol}s onto the top of this {@link DefaultStack}.
   * 
   * @param symbols The {@link Symbol}s to be pushed onto this
   *          {@link DefaultStack}.
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
   * Returns the number of {@link Symbol}s in this {@link DefaultStack}.
   * 
   * @return The number of {@link Symbol}s in this {@link DefaultStack}.
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
