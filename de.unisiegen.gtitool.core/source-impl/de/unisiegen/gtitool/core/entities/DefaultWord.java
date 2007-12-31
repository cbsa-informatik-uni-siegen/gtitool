package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;
import java.util.Iterator;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.exceptions.word.WordFinishedException;
import de.unisiegen.gtitool.core.exceptions.word.WordResetedException;
import de.unisiegen.gtitool.core.storage.Attribute;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Storable;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;


/**
 * The <code>DefaultWord</code> entity.
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
   * The start offset of this <code>Alphabet</code> in the source code.
   * 
   * @see #getParserStartOffset()
   * @see #setParserStartOffset(int)
   */
  private int parserStartOffset = NO_PARSER_OFFSET;


  /**
   * The end offset of this <code>Alphabet</code> in the source code.
   * 
   * @see #getParserEndOffset()
   * @see #setParserEndOffset(int)
   */
  private int parserEndOffset = NO_PARSER_OFFSET;


  /**
   * The {@link Symbol} list.
   */
  private ArrayList < Symbol > symbolList;


  /**
   * The current position in this <code>DefaultWord</code>.
   */
  private int currentPosition = START_INDEX;


  /**
   * Allocates a new <code>DefaultWord</code>.
   */
  public DefaultWord ()
  {
    // SymbolList
    this.symbolList = new ArrayList < Symbol > ();
  }


  /**
   * Allocates a new <code>DefaultWord</code>.
   * 
   * @param pElement The {@link Element}.
   * @throws SymbolException If something with the <code>Symbol</code> is not
   *           correct.
   * @throws StoreException If the {@link Element} can not be parsed.
   */
  public DefaultWord ( Element pElement ) throws SymbolException,
      StoreException
  {
    this ();
    // Check if the element is correct
    if ( !pElement.getName ().equals ( "Word" ) ) //$NON-NLS-1$
    {
      throw new IllegalArgumentException ( "element \"" + pElement.getName () //$NON-NLS-1$
          + "\" is not a word" ); //$NON-NLS-1$
    }

    // Element
    for ( Element current : pElement.getElement () )
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
    boolean foundCurrentPosition = false;
    boolean foundParserStartOffset = false;
    boolean foundParserEndOffset = false;
    for ( Attribute current : pElement.getAttribute () )
    {
      if ( current.getName ().equals ( "currentPosition" ) ) //$NON-NLS-1$
      {
        setCurrentPosition ( current.getValueInt () );
        foundCurrentPosition = true;
      }
      else if ( current.getName ().equals ( "parserStartOffset" ) ) //$NON-NLS-1$
      {
        setParserStartOffset ( current.getValueInt () );
        foundParserStartOffset = true;
      }
      else if ( current.getName ().equals ( "parserEndOffset" ) ) //$NON-NLS-1$
      {
        setParserEndOffset ( current.getValueInt () );
        foundParserEndOffset = true;
      }
      else
      {
        throw new StoreException ( Messages
            .getString ( "StoreException.AdditionalAttribute" ) ); //$NON-NLS-1$
      }
    }

    // Not all attribute values found
    if ( ( !foundCurrentPosition ) || ( !foundParserStartOffset )
        || ( !foundParserEndOffset ) )
    {
      throw new StoreException ( Messages
          .getString ( "StoreException.MissingAttribute" ) ); //$NON-NLS-1$
    }
  }


  /**
   * Allocates a new <code>DefaultWord</code>.
   * 
   * @param pSymbols The array of {@link Symbol}s.
   */
  public DefaultWord ( Iterable < Symbol > pSymbols )
  {
    this ();
    // Symbols
    if ( pSymbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    add ( pSymbols );
  }


  /**
   * Allocates a new <code>DefaultWord</code>.
   * 
   * @param pSymbols The array of {@link Symbol}s.
   */
  public DefaultWord ( Symbol ... pSymbols )
  {
    this ();
    // Symbols
    if ( pSymbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    add ( pSymbols );
  }


  /**
   * Appends the specified {@link Symbol}s to the end of this
   * <code>DefaultWord</code>.
   * 
   * @param pSymbols The {@link Symbol}s to be appended to this
   *          <code>DefaultWord</code>.
   */
  public final void add ( Iterable < Symbol > pSymbols )
  {
    if ( pSymbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    for ( Symbol current : pSymbols )
    {
      add ( current );
    }
  }


  /**
   * Appends the specified {@link Symbol} to the end of this
   * <code>DefaultWord</code>.
   * 
   * @param pSymbol The {@link Symbol} to be appended to this
   *          <code>DefaultWord</code>.
   */
  public final void add ( Symbol pSymbol )
  {
    // Symbol
    if ( pSymbol == null )
    {
      throw new NullPointerException ( "symbol is null" ); //$NON-NLS-1$
    }
    this.symbolList.add ( pSymbol );
  }


  /**
   * Appends the specified {@link Symbol}s to the end of this
   * <code>DefaultWord</code>.
   * 
   * @param pSymbols The {@link Symbol}s to be appended to this
   *          <code>DefaultWord</code>.
   */
  public final void add ( Symbol ... pSymbols )
  {
    if ( pSymbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    for ( Symbol current : pSymbols )
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
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object pOther )
  {
    if ( pOther instanceof DefaultWord )
    {
      DefaultWord other = ( DefaultWord ) pOther;
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
    return this.symbolList.get ( pIndex );
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
   * @throws WordFinishedException If something with the
   *           <code>DefaultWord</code> is not correct.
   * @throws WordResetedException If something with the <code>DefaultWord</code>
   *           is not correct.
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
    newElement.addAttribute ( new Attribute ( "currentPosition", //$NON-NLS-1$
        this.currentPosition ) );
    newElement.addAttribute ( new Attribute ( "parserStartOffset", //$NON-NLS-1$
        this.parserStartOffset ) );
    newElement.addAttribute ( new Attribute ( "parserEndOffset", //$NON-NLS-1$
        this.parserEndOffset ) );
    for ( Symbol current : this.symbolList )
    {
      newElement.addElement ( current );
    }
    return newElement;
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
   * Returns an iterator over the {@link Symbol}s in this
   * <code>DefaultWord</code>.
   * 
   * @return An iterator over the {@link Symbol}s in this
   *         <code>DefaultWord</code>.
   */
  public final Iterator < Symbol > iterator ()
  {
    return this.symbolList.iterator ();
  }


  /**
   * Returns the next {@link Symbol} and increments the current position.
   * 
   * @return The next {@link Symbol}.
   * @throws WordFinishedException If something with the
   *           <code>DefaultWord</code> is not correct.
   * @throws WordResetedException If something with the <code>DefaultWord</code>
   *           is not correct.
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
   * @throws WordFinishedException If something with the
   *           <code>DefaultWord</code> is not correct.
   * @throws WordResetedException If something with the <code>DefaultWord</code>
   *           is not correct.
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
   * @param pCurrentPosition The current position.
   */
  private final void setCurrentPosition ( int pCurrentPosition )
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
      this.currentPosition = pCurrentPosition;
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
   * Returns the number of {@link Symbol}s in this <code>DefaultWord</code>.
   * 
   * @return The number of {@link Symbol}s in this <code>DefaultWord</code>.
   */
  public final int size ()
  {
    return this.symbolList.size ();
  }


  /**
   * Resets the current position of this <code>DefaultWord</code>.
   */
  public final void start ()
  {
    this.currentPosition = START_INDEX;
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
