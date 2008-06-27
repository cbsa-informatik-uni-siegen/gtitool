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
   * {@inheritDoc}
   * 
   * @see Stack#clear()
   */
  public final void clear ()
  {
    this.symbolList.clear ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Comparable#compareTo(Object)
   */
  public final int compareTo ( Stack other )
  {
    ArrayList < Symbol > firstList = new ArrayList < Symbol > ();
    ArrayList < Symbol > secondList = new ArrayList < Symbol > ();

    firstList.addAll ( this.symbolList );
    secondList.addAll ( other.get () );

    int minSize = firstList.size () < secondList.size () ? firstList.size ()
        : secondList.size ();

    for ( int i = 0 ; i < minSize ; i++ )
    {
      int compare = firstList.get ( i ).compareTo ( secondList.get ( i ) );
      if ( compare != 0 )
      {
        return compare;
      }
    }

    if ( firstList.size () == secondList.size () )
    {
      return 0;
    }

    return firstList.size () < secondList.size () ? -1 : 1;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Stack#contains(Symbol)
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
   * {@inheritDoc}
   * 
   * @see Stack#get()
   */
  public final ArrayList < Symbol > get ()
  {
    return this.symbolList;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Stack#get(int)
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
   * 
   * @see Entity#getParserOffset()
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
   * {@inheritDoc}
   * 
   * @see Stack#iterator()
   */
  public final Iterator < Symbol > iterator ()
  {
    return this.symbolList.iterator ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Stack#peak()
   */
  public final Symbol peak ()
  {
    return this.symbolList.get ( this.symbolList.size () - 1 );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Stack#peak(int)
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
   * {@inheritDoc}
   * 
   * @see Stack#pop()
   */
  public final Symbol pop ()
  {
    return this.symbolList.remove ( this.symbolList.size () - 1 );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Stack#pop(int)
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
   * {@inheritDoc}
   * 
   * @see Stack#push(Iterable)
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
   * {@inheritDoc}
   * 
   * @see Stack#push(Symbol)
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
   * {@inheritDoc}
   * 
   * @see Stack#push(Symbol[])
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
   * 
   * @see Entity#setParserOffset(ParserOffset)
   */
  public final void setParserOffset ( ParserOffset parserOffset )
  {
    this.parserOffset = parserOffset;
  }


  /**
   *{@inheritDoc}
   * 
   * @see Stack#size()
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
