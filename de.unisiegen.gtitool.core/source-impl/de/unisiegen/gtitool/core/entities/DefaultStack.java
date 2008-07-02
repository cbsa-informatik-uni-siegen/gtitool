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
    return toString ().compareTo ( other.toString () );
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
      DefaultStack stack = ( DefaultStack ) other;
      return this.symbolList.equals ( stack.symbolList );
    }
    return false;
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
    return this.symbolList.get ( 0 );
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
      result.add ( this.symbolList.get ( i ) );
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
    return this.symbolList.remove ( 0 );
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
    this.symbolList.add ( 0, symbol );
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
