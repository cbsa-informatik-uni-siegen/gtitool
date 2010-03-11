package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.event.EventListenerList;

import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyString.PrettyStringMode;


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
   * The cached {@link PrettyString}.
   */
  private PrettyString cachedPrettyString = null;


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * The {@link PrettyStringChangedListener}.
   */
  private PrettyStringChangedListener prettyStringChangedListener;


  /**
   * Allocates a new {@link DefaultStack}.
   */
  public DefaultStack ()
  {
    this.prettyStringChangedListener = new PrettyStringChangedListener ()
    {

      public void prettyStringChanged ()
      {
        firePrettyStringChanged ();
      }
    };

    this.symbolList = new ArrayList < Symbol > ();
  }


  /**
   * The Copy-CTor
   * 
   * @param stack The {@link Stack}
   */
  public DefaultStack ( final Stack stack )
  {
    this ();
    DefaultStack other = ( DefaultStack ) stack;
    for ( Symbol s : other.symbolList )
      this.symbolList.add ( new DefaultSymbol ( s ) );
  }


  /**
   * {@inheritDoc}
   * 
   * @see PrettyPrintable#addPrettyStringChangedListener(PrettyStringChangedListener)
   */
  public final void addPrettyStringChangedListener (
      PrettyStringChangedListener listener )
  {
    this.listenerList.add ( PrettyStringChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Stack#clear()
   */
  public final void clear ()
  {
    for ( Symbol current : this.symbolList )
      current
          .removePrettyStringChangedListener ( this.prettyStringChangedListener );

    this.symbolList.clear ();

    firePrettyStringChanged ();
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
   * Let the listeners know that the {@link PrettyString} has changed.
   */
  protected final void firePrettyStringChanged ()
  {
    this.cachedPrettyString = null;

    PrettyStringChangedListener [] listeners = this.listenerList
        .getListeners ( PrettyStringChangedListener.class );
    for ( PrettyStringChangedListener current : listeners )
      current.prettyStringChanged ();
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
      result.add ( this.symbolList.get ( i ) );
    return result;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Stack#pop()
   */
  public final Symbol pop ()
  {
    Symbol symbol = this.symbolList.remove ( 0 );

    symbol
        .removePrettyStringChangedListener ( this.prettyStringChangedListener );

    firePrettyStringChanged ();

    return symbol;
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
      throw new NullPointerException ( "symbol is null" ); //$NON-NLS-1$

    symbol.addPrettyStringChangedListener ( this.prettyStringChangedListener );

    this.symbolList.add ( 0, symbol );

    firePrettyStringChanged ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see PrettyPrintable#removePrettyStringChangedListener(PrettyStringChangedListener)
   */
  public final void removePrettyStringChangedListener (
      PrettyStringChangedListener listener )
  {
    this.listenerList.remove ( PrettyStringChangedListener.class, listener );
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
    if ( ( this.cachedPrettyString == null )
        || PrettyString.MODE.equals ( PrettyStringMode.CACHING_OFF ) )
    {
      this.cachedPrettyString = new PrettyString ();
      for ( Symbol current : this.symbolList )
        this.cachedPrettyString.add ( current );
    }

    return this.cachedPrettyString;
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
      result.append ( current.getName () );
    return result.toString ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.Stack#isEmpty()
   */
  public boolean isEmpty ()
  {
    return this.symbolList.isEmpty ();
  }
}
