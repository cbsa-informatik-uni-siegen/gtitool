package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;
import java.util.Iterator;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.exceptions.word.WordFinishedException;
import de.unisiegen.gtitool.core.exceptions.word.WordResetedException;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
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
   * Allocates a new {@link DefaultWord}.
   */
  public DefaultWord ()
  {
    // SymbolList
    this.symbolList = new ArrayList < Symbol > ();
  }


  /**
   * Allocates a new {@link DefaultWord}.
   * 
   * @param element The {@link Element}.
   * @throws SymbolException If something with the {@link Symbol} is not
   *           correct.
   * @throws StoreException If the {@link Element} can not be parsed.
   */
  public DefaultWord ( Element element ) throws SymbolException, StoreException
  {
    this ();
    // Check if the element is correct
    if ( !element.getName ().equals ( "Word" ) ) //$NON-NLS-1$
    {
      throw new IllegalArgumentException (
          "element " + Messages.QUOTE + element.getName () //$NON-NLS-1$
              + Messages.QUOTE + " is not a word" ); //$NON-NLS-1$
    }

    // Element
    for ( Element current : element.getElement () )
    {
      if ( current.getName ().equals ( "Symbol" ) ) //$NON-NLS-1$
      {
        add ( new DefaultSymbol ( current ) );
      }
      else
      {
        throw new StoreException ( Messages
            .getString ( "StoreException.AdditionalElement" ) ); //$NON-NLS-1$
      }
    }

    // Attribute
    if ( element.getAttribute ().size () > 0 )
    {
      throw new StoreException ( Messages
          .getString ( "StoreException.AdditionalAttribute" ) ); //$NON-NLS-1$
    }
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
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
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
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    add ( symbols );
  }


  /**
   * Appends the specified {@link Symbol}s to the end of this
   * {@link DefaultWord}.
   * 
   * @param symbols The {@link Symbol}s to be appended to this
   *          {@link DefaultWord}.
   */
  public final void add ( Iterable < Symbol > symbols )
  {
    if ( symbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    for ( Symbol current : symbols )
    {
      add ( current );
    }
  }


  /**
   * Appends the specified {@link Symbol} to the end of this {@link DefaultWord}.
   * 
   * @param symbol The {@link Symbol} to be appended to this {@link DefaultWord}.
   */
  public final void add ( Symbol symbol )
  {
    // Symbol
    if ( symbol == null )
    {
      throw new NullPointerException ( "symbol is null" ); //$NON-NLS-1$
    }
    this.symbolList.add ( symbol );
  }


  /**
   * Appends the specified {@link Symbol}s to the end of this
   * {@link DefaultWord}.
   * 
   * @param symbols The {@link Symbol}s to be appended to this
   *          {@link DefaultWord}.
   */
  public final void add ( Symbol ... symbols )
  {
    if ( symbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    for ( Symbol current : symbols )
    {
      add ( current );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#clone()
   */
  @Override
  public final DefaultWord clone ()
  {
    DefaultWord newDefaultWord = new DefaultWord ();
    for ( Symbol current : this.symbolList )
    {
      newDefaultWord.add ( current.clone () );
    }
    return newDefaultWord;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Comparable#compareTo( Object)
   */
  public final int compareTo ( @SuppressWarnings ( "unused" )
  Word other )
  {
    // TODOCF
    return 0;
  }


  /**
   * Returns true if this {@link DefaultWord} contains the given {@link Symbol}.
   * Otherwise false.
   * 
   * @param symbol The {@link Symbol}.
   * @return True if this {@link DefaultWord} contains the given {@link Symbol}.
   *         Otherwise false.
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
    return this.symbolList.get ( index );
  }


  /**
   * Returns the current position.
   * 
   * @return The current position.
   * @see #currentPosition
   */
  public final int getCurrentPosition ()
  {
    return this.currentPosition;
  }


  /**
   * Returns the current {@link Symbol}.
   * 
   * @return The current {@link Symbol}.
   * @throws WordFinishedException If something with the {@link DefaultWord} is
   *           not correct.
   * @throws WordResetedException If something with the {@link DefaultWord} is
   *           not correct.
   */
  public final Symbol getCurrentSymbol () throws WordFinishedException,
      WordResetedException
  {
    if ( this.currentPosition == START_INDEX )
    {
      throw new WordResetedException ( this );
    }
    if ( this.currentPosition >= this.symbolList.size () )
    {
      throw new WordFinishedException ( this );
    }
    return this.symbolList.get ( this.currentPosition );
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
    {
      newElement.addElement ( current );
    }
    return newElement;
  }


  /**
   * {@inheritDoc}
   */
  public final ParserOffset getParserOffset ()
  {
    return this.parserOffset;
  }


  /**
   * Returns the readed {@link Symbol}s.
   * 
   * @return The readed {@link Symbol}s.
   * @throws WordFinishedException If something with the {@link DefaultWord} is
   *           not correct.
   * @throws WordResetedException If something with the {@link DefaultWord} is
   *           not correct.
   */
  public final ArrayList < Symbol > getReadedSymbols ()
      throws WordFinishedException, WordResetedException
  {
    if ( this.currentPosition == START_INDEX )
    {
      throw new WordResetedException ( this );
    }
    if ( this.currentPosition >= this.symbolList.size () )
    {
      throw new WordFinishedException ( this );
    }
    ArrayList < Symbol > readedSymbols = new ArrayList < Symbol > ();
    for ( int i = 0 ; i <= this.currentPosition ; i++ )
    {
      readedSymbols.add ( this.symbolList.get ( i ) );
    }
    return readedSymbols;
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
   * Returns true if this word is finished, otherwise false.
   * 
   * @return True if this word is finished, otherwise false.
   */
  public final boolean isFinished ()
  {
    return this.currentPosition == this.symbolList.size () - 1;
  }


  /**
   * Returns true if this word is reseted, otherwise false.
   * 
   * @return True if this word is reseted, otherwise false.
   */
  public final boolean isReseted ()
  {
    return this.currentPosition == START_INDEX;
  }


  /**
   * Returns an iterator over the {@link Symbol}s in this {@link DefaultWord}.
   * 
   * @return An iterator over the {@link Symbol}s in this {@link DefaultWord}.
   */
  public final Iterator < Symbol > iterator ()
  {
    return this.symbolList.iterator ();
  }


  /**
   * Returns the next {@link Symbol} and increments the current position.
   * 
   * @return The next {@link Symbol}.
   * @throws WordFinishedException If something with the {@link DefaultWord} is
   *           not correct.
   * @throws WordResetedException If something with the {@link DefaultWord} is
   *           not correct.
   */
  public final Symbol nextSymbol () throws WordFinishedException,
      WordResetedException
  {
    if ( this.currentPosition == this.symbolList.size () - 1 )
    {
      throw new WordFinishedException ( this );
    }
    this.currentPosition++ ;
    return getCurrentSymbol ();
  }


  /**
   * Returns the previous {@link Symbol} and decrements the current position.
   * 
   * @return The previous {@link Symbol}.
   * @throws WordFinishedException If something with the {@link DefaultWord} is
   *           not correct.
   * @throws WordResetedException If something with the {@link DefaultWord} is
   *           not correct.
   */
  public final Symbol previousSymbol () throws WordFinishedException,
      WordResetedException
  {
    if ( this.currentPosition == START_INDEX )
    {
      throw new WordResetedException ( this );
    }
    Symbol symbol = getCurrentSymbol ();
    this.currentPosition-- ;
    return symbol;
  }


  /**
   * Sets the current position.
   * 
   * @param currentPosition The current position.
   */
  public final void setCurrentPosition ( int currentPosition )
  {
    if ( this.currentPosition < START_INDEX )
    {
      this.currentPosition = START_INDEX;
    }
    else if ( this.currentPosition >= this.symbolList.size () )
    {
      this.currentPosition = this.symbolList.size () - 1;
    }
    else
    {
      this.currentPosition = currentPosition;
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
   * Returns the number of {@link Symbol}s in this {@link DefaultWord}.
   * 
   * @return The number of {@link Symbol}s in this {@link DefaultWord}.
   */
  public final int size ()
  {
    return this.symbolList.size ();
  }


  /**
   * Resets the current position of this {@link DefaultWord}.
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
