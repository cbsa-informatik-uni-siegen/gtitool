package de.unisiegen.gtitool.core.entities;


import java.io.Serializable;
import java.util.Iterator;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.exceptions.AlphabetException;


/**
 * The <code>Alphabet</code> entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class Alphabet implements Serializable, Cloneable,
    Iterable < Symbol >
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -4488353013296552669L;


  /**
   * The set of {@link Symbol}s.
   */
  private TreeSet < Symbol > symbolSet;


  /**
   * Allocates a new <code>Alphabet</code>.
   */
  public Alphabet ()
  {
    // SymbolList
    this.symbolSet = new TreeSet < Symbol > ();
  }


  /**
   * Allocates a new <code>Alphabet</code>.
   * 
   * @param pSymbols The array of {@link Symbol}s.
   * @throws AlphabetException If something in the <code>Alphabet</code> is
   *           not correct.
   */
  public Alphabet ( Iterable < Symbol > pSymbols ) throws AlphabetException
  {
    this ();
    // Symbols
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
   * Allocates a new <code>Alphabet</code>.
   * 
   * @param pSymbols The array of {@link Symbol}s.
   * @throws AlphabetException If something in the <code>Alphabet</code> is
   *           not correct.
   */
  public Alphabet ( Symbol ... pSymbols ) throws AlphabetException
  {
    this ();
    // Symbols
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
   * Appends the specified {@link Symbol} to the end of this
   * <code>Alphabet</code>.
   * 
   * @param pSymbol The {@link Symbol} to be appended to this
   *          <code>Alphabet</code>.
   * @throws AlphabetException If something in the <code>Alphabet</code> is
   *           not correct.
   */
  public final void addSymbol ( Symbol pSymbol ) throws AlphabetException
  {
    if ( pSymbol == null )
    {
      throw new NullPointerException ( "symbol is null" ); //$NON-NLS-1$
    }
    if ( this.symbolSet.contains ( pSymbol ) )
    {
      throw new AlphabetException ( Messages
          .getString ( "AlphabetException.MoreThanOneSymbolMessage" ), Messages //$NON-NLS-1$
          .getString ( "AlphabetException.MoreThanOneSymbolDescription", //$NON-NLS-1$
              pSymbol.getName () ) );
    }
    this.symbolSet.add ( pSymbol );
  }


  /**
   * Appends the specified {@link Symbol}s to the end of this
   * <code>Alphabet</code>.
   * 
   * @param pSymbols The {@link Symbol}s to be appended to this
   *          <code>Alphabet</code>.
   * @throws AlphabetException If something in the <code>Alphabet</code> is
   *           not correct.
   */
  public final void addSymbols ( Iterable < Symbol > pSymbols )
      throws AlphabetException
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
   * Appends the specified {@link Symbol}s to the end of this
   * <code>Alphabet</code>.
   * 
   * @param pSymbols The {@link Symbol}s to be appended to this
   *          <code>Alphabet</code>.
   * @throws AlphabetException If something in the <code>Alphabet</code> is
   *           not correct.
   */
  public final void addSymbols ( Symbol ... pSymbols ) throws AlphabetException
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
   * @see Object#clone()
   */
  @Override
  public final Alphabet clone ()
  {
    Alphabet newAlphabet = new Alphabet ();
    for ( Symbol current : this.symbolSet )
    {
      try
      {
        newAlphabet.addSymbol ( current.clone () );
      }
      catch ( AlphabetException e )
      {
        throw new IllegalArgumentException ( "this should not happen" ); //$NON-NLS-1$
      }
    }
    return newAlphabet;
  }


  /**
   * Returns <tt>true</tt> if this <code>Alphabet</code> contains the
   * specified {@link Symbol}.
   * 
   * @param pSymbol {@link Symbol} whose presence in this <code>Alphabet</code>
   *          is to be tested.
   * @return <code>true</code> if the specified {@link Symbol} is present;
   *         <code>false</code> otherwise.
   */
  public final boolean containsSymbol ( Symbol pSymbol )
  {
    return this.symbolSet.contains ( pSymbol );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object pOther )
  {
    if ( pOther instanceof Alphabet )
    {
      Alphabet other = ( Alphabet ) pOther;
      return this.symbolSet.equals ( other.symbolSet );
    }
    return false;
  }


  /**
   * Returns the {@link Symbol} with the given index.
   * 
   * @param pIndex The index.
   * @return The {@link Symbol} with the given index.
   * @see #symbolSet
   */
  public final Symbol getSymbol ( int pIndex )
  {
    Iterator < Symbol > iterator = this.symbolSet.iterator ();
    for ( int i = 0 ; i < pIndex ; i++ )
    {
      iterator.next ();
    }
    return iterator.next ();
  }


  /**
   * Returns the symbolSet.
   * 
   * @return The symbolSet.
   * @see #symbolSet
   */
  public final TreeSet < Symbol > getSymbolSet ()
  {
    return this.symbolSet;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#hashCode()
   */
  @Override
  public final int hashCode ()
  {
    return this.symbolSet.hashCode ();
  }


  /**
   * Returns an iterator over the {@link Symbol}s in this <code>Alphabet</code>.
   * 
   * @return An iterator over the {@link Symbol}s in this <code>Alphabet</code>.
   */
  public final Iterator < Symbol > iterator ()
  {
    return this.symbolSet.iterator ();
  }


  /**
   * Removes the {@link Symbol} from this <code>Alphabet</code>.
   * 
   * @param pSymbol The {@link Symbol} to remove.
   */
  public final void removeSymbol ( Symbol pSymbol )
  {
    if ( pSymbol == null )
    {
      throw new NullPointerException ( "symbol is null" ); //$NON-NLS-1$
    }
    if ( !this.symbolSet.contains ( pSymbol ) )
    {
      throw new IllegalArgumentException ( "symbol is not in this alphabet" ); //$NON-NLS-1$
    }
    this.symbolSet.remove ( pSymbol );
  }


  /**
   * Returns the number of {@link Symbol}s in this <code>Alphabet</code>.
   * 
   * @return The number of {@link Symbol}s in this <code>Alphabet</code>.
   */
  public final int symbolSize ()
  {
    return this.symbolSet.size ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public final String toString ()
  {
    return this.symbolSet.toString ();
  }
}
