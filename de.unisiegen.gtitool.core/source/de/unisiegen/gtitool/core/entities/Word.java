package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;
import java.util.Iterator;

import de.unisiegen.gtitool.core.exceptions.word.WordException;
import de.unisiegen.gtitool.core.exceptions.word.WordFinishedException;


/**
 * The <code>Word</code> entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class Word implements Entity, Iterable < Symbol >
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
   * The {@link Symbol} list.
   */
  private ArrayList < Symbol > symbolList;


  /**
   * The current position in this <code>Word</code>.
   */
  private int currentPosition;


  /**
   * Allocates a new <code>Word</code>.
   */
  public Word ()
  {
    // SymbolList
    this.symbolList = new ArrayList < Symbol > ();
    this.currentPosition = START_INDEX;
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
    addSymbols ( pSymbols );
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
    addSymbols ( pSymbols );
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
  public final void addSymbols ( Iterable < Symbol > pSymbols )
  {
    if ( pSymbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    if ( !pSymbols.iterator ().hasNext () )
    {
      throw new IllegalArgumentException ( "symbols is empty" ); //$NON-NLS-1$
    }
    for ( Symbol current : pSymbols )
    {
      addSymbol ( current );
    }
  }


  /**
   * Appends the specified {@link Symbol}s to the end of this <code>Word</code>.
   * 
   * @param pSymbols The {@link Symbol}s to be appended to this
   *          <code>Word</code>.
   */
  public final void addSymbols ( Symbol ... pSymbols )
  {
    if ( pSymbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    if ( pSymbols.length == 0 )
    {
      throw new IllegalArgumentException ( "symbols is empty" ); //$NON-NLS-1$
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
   */
  public final Symbol getCurrentSymbol ()
  {
    if ( this.currentPosition == START_INDEX )
    {
      throw new IllegalArgumentException ( "current symbol is not defined" ); //$NON-NLS-1$
    }
    return this.symbolList.get ( this.currentPosition );
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
