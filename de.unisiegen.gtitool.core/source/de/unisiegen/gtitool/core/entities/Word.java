package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;
import java.util.Iterator;

import de.unisiegen.gtitool.core.exceptions.word.WordException;
import de.unisiegen.gtitool.core.exceptions.word.WordFinishedException;
import de.unisiegen.gtitool.core.exceptions.word.WordResetedException;
import de.unisiegen.gtitool.core.storage.Attribute;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * The <code>Word</code> entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class Word implements ParseableEntity, Storable, Iterable < Symbol >
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
   * The current position in this <code>Word</code>.
   */
  private int currentPosition = START_INDEX;


  /**
   * Allocates a new <code>Word</code>.
   */
  public Word ()
  {
    // SymbolList
    this.symbolList = new ArrayList < Symbol > ();
  }


  /**
   * Allocates a new <code>Word</code>.
   * 
   * @param pElement The {@link Element}.
   */
  public Word ( Element pElement )
  {
    this ();
    if ( !pElement.getName ().equals ( "Word" ) ) //$NON-NLS-1$
    {
      throw new IllegalArgumentException ( "element \"" + pElement.getName () //$NON-NLS-1$
          + "\" is not a word" ); //$NON-NLS-1$
    }
    try
    {
      this.currentPosition = Integer.parseInt ( pElement
          .getAttribute ( "currentPosition" ) ); //$NON-NLS-1$
    }
    catch ( NumberFormatException exc )
    {
      // Do nothing
    }
    try
    {
      this.parserStartOffset = Integer.parseInt ( pElement
          .getAttribute ( "parserStartOffset" ) ); //$NON-NLS-1$
    }
    catch ( NumberFormatException exc )
    {
      // Do nothing
    }
    try
    {
      this.parserEndOffset = Integer.parseInt ( pElement
          .getAttribute ( "parserEndOffset" ) ); //$NON-NLS-1$
    }
    catch ( NumberFormatException exc )
    {
      // Do nothing
    }
    for ( Element current : pElement.getElement () )
    {
      addSymbol ( new Symbol ( current ) );
    }
  }


  /**
   * Allocates a new <code>Word</code>.
   * 
   * @param pSymbols The array of {@link Symbol}s.
   */
  public Word ( Iterable < Symbol > pSymbols )
  {
    this ();
    // Symbols
    if ( pSymbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    addSymbol ( pSymbols );
  }


  /**
   * Allocates a new <code>Word</code>.
   * 
   * @param pSymbols The array of {@link Symbol}s.
   */
  public Word ( Symbol ... pSymbols )
  {
    this ();
    // Symbols
    if ( pSymbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    addSymbol ( pSymbols );
  }


  /**
   * Appends the specified {@link Symbol}s to the end of this <code>Word</code>.
   * 
   * @param pSymbols The {@link Symbol}s to be appended to this
   *          <code>Word</code>.
   */
  public final void addSymbol ( Iterable < Symbol > pSymbols )
  {
    if ( pSymbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    for ( Symbol current : pSymbols )
    {
      addSymbol ( current );
    }
  }


  /**
   * Appends the specified {@link Symbol} to the end of this <code>Word</code>.
   * 
   * @param pSymbol The {@link Symbol} to be appended to this <code>Word</code>.
   */
  public final void addSymbol ( Symbol pSymbol )
  {
    // Symbol
    if ( pSymbol == null )
    {
      throw new NullPointerException ( "symbol is null" ); //$NON-NLS-1$
    }
    this.symbolList.add ( pSymbol );
  }


  /**
   * Appends the specified {@link Symbol}s to the end of this <code>Word</code>.
   * 
   * @param pSymbols The {@link Symbol}s to be appended to this
   *          <code>Word</code>.
   */
  public final void addSymbol ( Symbol ... pSymbols )
  {
    if ( pSymbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    for ( Symbol current : pSymbols )
    {
      addSymbol ( current );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#clone()
   */
  @Override
  public final Word clone ()
  {
    Word newWord = new Word ();
    for ( Symbol current : this.symbolList )
    {

      newWord.addSymbol ( current.clone () );

    }
    return newWord;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object pOther )
  {
    if ( pOther instanceof Word )
    {
      Word other = ( Word ) pOther;
      return this.symbolList.equals ( other.symbolList );
    }
    return false;
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
   * @throws WordException If something with the <code>Word</code> is not
   *           correct.
   */
  public final Symbol getCurrentSymbol () throws WordException
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
   * Returns the {@link Symbol} with the given index.
   * 
   * @param pIndex The index.
   * @return The {@link Symbol} with the given index.
   * @see #symbolList
   */
  public final Symbol getSymbol ( int pIndex )
  {
    return this.symbolList.get ( pIndex );
  }


  /**
   * Returns the {@link Symbol}s.
   * 
   * @return The {@link Symbol}s.
   */
  public final ArrayList < Symbol > getSymbols ()
  {
    return this.symbolList;
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
   * Returns an iterator over the {@link Symbol}s in this <code>Word</code>.
   * 
   * @return An iterator over the {@link Symbol}s in this <code>Word</code>.
   */
  public final Iterator < Symbol > iterator ()
  {
    return this.symbolList.iterator ();
  }


  /**
   * Returns the next {@link Symbol} and increments the current position.
   * 
   * @return The next {@link Symbol}.
   * @throws WordException If something with the <code>Word</code> is not
   *           correct.
   */
  public final Symbol nextSymbol () throws WordException
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
   * @throws WordException If something with the <code>Word</code> is not
   *           correct.
   */
  public final Symbol previousSymbol () throws WordException
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
   * Resets the current position of this <code>Word</code>.
   */
  public final void start ()
  {
    this.currentPosition = START_INDEX;
  }


  /**
   * Returns the number of {@link Symbol}s in this <code>Word</code>.
   * 
   * @return The number of {@link Symbol}s in this <code>Word</code>.
   */
  public final int symbolSize ()
  {
    return this.symbolList.size ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
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
}
