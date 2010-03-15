package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.event.EventListenerList;

import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;
import de.unisiegen.gtitool.core.exceptions.word.WordFinishedException;
import de.unisiegen.gtitool.core.exceptions.word.WordResetedException;
import de.unisiegen.gtitool.core.i18n.Messages;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyString.PrettyStringMode;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Storable;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;


/**
 * The {@link DefaultWord} entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class DefaultWord implements Word
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 550317433435393215L;


  /**
   * The start index.
   */
  private static int START_INDEX = -1;


  /**
   * The offset of this {@link DefaultAlphabet} in the source code.
   * 
   * @see #getParserOffset()
   * @see #setParserOffset(ParserOffset)
   */
  private ParserOffset parserOffset = NO_PARSER_OFFSET;


  /**
   * The {@link Symbol} list.
   */
  private ArrayList < Symbol > symbolList;


  /**
   * The current position in this {@link DefaultWord}.
   */
  private int currentPosition = START_INDEX;


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
   * Allocates a new {@link DefaultWord}.
   */
  public DefaultWord ()
  {
    this.prettyStringChangedListener = new PrettyStringChangedListener ()
    {

      public void prettyStringChanged ()
      {
        firePrettyStringChanged ();
      }
    };

    // SymbolList
    this.symbolList = new ArrayList < Symbol > ();
  }


  /**
   * The Copy-CTor
   * 
   * @param word The {@link Word}
   */
  public DefaultWord ( final Word word )
  {
    this ();
    DefaultWord other = ( DefaultWord ) word;
    for ( Symbol s : other.symbolList )
      this.symbolList.add ( new DefaultSymbol ( s ) );
    this.currentPosition = other.currentPosition;
  }


  /**
   * Allocates a new {@link DefaultWord}.
   * 
   * @param element The {@link Element}.
   * @throws StoreException If the {@link Element} can not be parsed.
   */
  public DefaultWord ( Element element ) throws StoreException
  {
    this ();

    // Check if the element is correct
    if ( !element.getName ().equals ( "Word" ) )
      throw new IllegalArgumentException (
          "element " + Messages.QUOTE + element.getName () //$NON-NLS-1$
              + Messages.QUOTE + " is not a word" ); //$NON-NLS-1$

    // Element
    for ( Element current : element.getElement () )
      if ( current.getName ().equals ( "Symbol" ) )
        add ( new DefaultSymbol ( current ) );
      else
        throw new StoreException ( Messages
            .getString ( "StoreException.AdditionalElement" ) ); //$NON-NLS-1$

    // Attribute
    if ( element.getAttribute ().size () > 0 )
      throw new StoreException ( Messages
          .getString ( "StoreException.AdditionalAttribute" ) ); //$NON-NLS-1$
  }


  /**
   * Allocates a new {@link DefaultWord}.
   * 
   * @param symbols The array of {@link Symbol}s.
   */
  public DefaultWord ( Iterable < Symbol > symbols )
  {
    this ();

    // Symbols
    if ( symbols == null )
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    add ( symbols );
  }


  /**
   * Allocates a new {@link DefaultWord}.
   * 
   * @param symbols The array of {@link Symbol}s.
   */
  public DefaultWord ( Symbol ... symbols )
  {
    this ();

    // Symbols
    if ( symbols == null )
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    add ( symbols );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Word#add(Iterable)
   */
  public final void add ( Iterable < Symbol > symbols )
  {
    if ( symbols == null )
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    for ( Symbol current : symbols )
      add ( current );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Word#add(Symbol)
   */
  public final void add ( Symbol symbol )
  {
    // Symbol
    if ( symbol == null )
      throw new NullPointerException ( "symbol is null" ); //$NON-NLS-1$

    symbol.addPrettyStringChangedListener ( this.prettyStringChangedListener );

    this.symbolList.add ( symbol );

    firePrettyStringChanged ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Word#add(Symbol[])
   */
  public final void add ( Symbol ... symbols )
  {
    if ( symbols == null )
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    for ( Symbol current : symbols )
      add ( current );
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
   * @see Comparable#compareTo(Object)
   */
  public final int compareTo ( Word other )
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
        return compare;
    }

    if ( firstList.size () == secondList.size () )
      return 0;

    return firstList.size () < secondList.size () ? -1 : 1;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Word#contains(Symbol)
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
    if ( other instanceof DefaultWord )
    {
      DefaultWord defaultWord = ( DefaultWord ) other;
      return this.symbolList.equals ( defaultWord.symbolList );
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
   * @see Word#get()
   */
  public final ArrayList < Symbol > get ()
  {
    return this.symbolList;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Word#get(int)
   */
  public final Symbol get ( int index )
  {
    return this.symbolList.get ( index );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Storable#getElement()
   */
  public final Element getElement ()
  {
    Element newElement = new Element ( "Word" ); //$NON-NLS-1$
    for ( Symbol current : this.symbolList )
      newElement.addElement ( current );
    return newElement;
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
   * @see Word#getReadSymbols()
   */
  public final ArrayList < Symbol > getReadSymbols ()
      throws WordFinishedException, WordResetedException
  {
    if ( this.currentPosition == START_INDEX )
      throw new WordResetedException ( this );
    if ( this.currentPosition >= this.symbolList.size () )
      throw new WordFinishedException ( this );

    ArrayList < Symbol > readSymbols = new ArrayList < Symbol > ();
    for ( int i = 0 ; i <= this.currentPosition ; i++ )
      readSymbols.add ( this.symbolList.get ( i ) );
    return readSymbols;
  }


  /**
   * {@inheritDoc}
   * 
   * @throws WordFinishedException
   * @see de.unisiegen.gtitool.core.entities.Word#getRemainingSymbols()
   */
  public final ArrayList < Symbol > getRemainingSymbols ()
      throws WordFinishedException
  {
    if ( this.currentPosition >= this.symbolList.size () )
      throw new WordFinishedException ( this );
    ArrayList < Symbol > remainingSymbols = new ArrayList < Symbol > ();
    if ( this.currentPosition == START_INDEX )
      remainingSymbols.addAll ( this.symbolList );
    else
      for ( int i = this.currentPosition + 1 ; i < this.symbolList.size () ; ++i )
        remainingSymbols.add ( this.symbolList.get ( i ) );
    return remainingSymbols;
  }


  /**
   * {@inheritDoc}
   * 
   * @throws WordFinishedException
   * @see de.unisiegen.gtitool.core.entities.Word#getRemainingWord()
   */
  public final Word getRemainingWord () throws WordFinishedException
  {
    return new DefaultWord ( getRemainingSymbols () );
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
   * @see Word#isFinished()
   */
  public final boolean isFinished ()
  {
    return this.currentPosition == this.symbolList.size () - 1;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Word#isResetted()
   */
  public final boolean isResetted ()
  {
    return this.currentPosition == START_INDEX;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Iterable#iterator()
   */
  public final Iterator < Symbol > iterator ()
  {
    return this.symbolList.iterator ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Word#nextSymbol()
   */
  public final Symbol nextSymbol () throws WordFinishedException
  {
    if ( this.currentPosition == this.symbolList.size () - 1 )
      throw new WordFinishedException ( this );
    this.currentPosition++ ;
    if ( this.currentPosition >= this.symbolList.size () )
      throw new WordFinishedException ( this );
    return this.symbolList.get ( this.currentPosition );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Word#previousSymbol()
   */
  public final Symbol previousSymbol () throws WordResetedException
  {
    if ( this.currentPosition == START_INDEX )
      throw new WordResetedException ( this );
    if ( this.currentPosition == START_INDEX )
      throw new WordResetedException ( this );

    Symbol symbol = this.symbolList.get ( this.currentPosition );
    this.currentPosition-- ;
    return symbol;
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
   * {@inheritDoc}
   * 
   * @see Word#size()
   */
  public final int size ()
  {
    return this.symbolList.size ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Word#start()
   */
  public final void start ()
  {
    this.currentPosition = START_INDEX;
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
   * @see de.unisiegen.gtitool.core.entities.Word#getCurrentPosition()
   */
  public int getCurrentPosition ()
  {
    return this.currentPosition + 1;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.entities.Word#getCurrentSymbol()
   */
  public Symbol getCurrentSymbol ()
  {
    return get ( getCurrentPosition () );
  }
}
