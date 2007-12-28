package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetMoreThanOneSymbolException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.storage.Attribute;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Storable;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;


/**
 * The <code>Alphabet</code> entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class Alphabet implements ParseableEntity, Storable,
    Iterable < Symbol >
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -4488353013296552669L;


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
   * The set of {@link Symbol}s.
   */
  private TreeSet < Symbol > symbolSet;


  /**
   * Allocates a new <code>Alphabet</code>.
   */
  public Alphabet ()
  {
    // SymbolSet
    this.symbolSet = new TreeSet < Symbol > ();
  }


  /**
   * Allocates a new <code>Alphabet</code>.
   * 
   * @param pElement The {@link Element}.
   * @throws AlphabetException If something with the <code>Alphabet</code> is
   *           not correct.
   * @throws SymbolException If something with the <code>Symbol</code> is not
   *           correct.
   * @throws StoreException If the {@link Element} can not be parsed.
   */
  public Alphabet ( Element pElement ) throws AlphabetException,
      SymbolException, StoreException
  {
    this ();
    // Check if the element is correct
    if ( !pElement.getName ().equals ( "Alphabet" ) ) //$NON-NLS-1$
    {
      throw new IllegalArgumentException ( "element \"" + pElement.getName () //$NON-NLS-1$
          + "\" is not a alphabet" ); //$NON-NLS-1$
    }

    // Attribute
    boolean foundParserStartOffset = false;
    boolean foundParserEndOffset = false;
    for ( Attribute current : pElement.getAttribute () )
    {
      if ( current.getName ().equals ( "parserStartOffset" ) ) //$NON-NLS-1$
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

    // Not all values found
    if ( ( !foundParserStartOffset ) || ( !foundParserEndOffset ) )
    {
      throw new StoreException ( Messages
          .getString ( "StoreException.MissingAttribute" ) ); //$NON-NLS-1$
    }

    // Element
    for ( Element current : pElement.getElement () )
    {
      if ( current.getName ().equals ( "Symbol" ) ) //$NON-NLS-1$
      {
        add ( new Symbol ( current ) );
      }
      else
      {
        throw new StoreException ( Messages
            .getString ( "StoreException.AdditionalElement" ) ); //$NON-NLS-1$
      }
    }
  }


  /**
   * Allocates a new <code>Alphabet</code>.
   * 
   * @param pSymbols The array of {@link Symbol}s.
   * @throws AlphabetException If something with the <code>Alphabet</code> is
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
    add ( pSymbols );
  }


  /**
   * Allocates a new <code>Alphabet</code>.
   * 
   * @param pSymbols The array of {@link Symbol}s.
   * @throws AlphabetException If something with the <code>Alphabet</code> is
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
    add ( pSymbols );
  }


  /**
   * Appends the specified {@link Symbol}s to the end of this
   * <code>Alphabet</code>.
   * 
   * @param pSymbols The {@link Symbol}s to be appended to this
   *          <code>Alphabet</code>.
   * @throws AlphabetException If something with the <code>Alphabet</code> is
   *           not correct.
   */
  public final void add ( Iterable < Symbol > pSymbols )
      throws AlphabetException
  {
    if ( pSymbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    ArrayList < Symbol > symbolList = new ArrayList < Symbol > ();
    for ( Symbol current : pSymbols )
    {
      symbolList.add ( current );
    }
    checkDuplicated ( symbolList );
    for ( Symbol current : pSymbols )
    {
      add ( current );
    }
  }


  /**
   * Appends the specified {@link Symbol} to the end of this
   * <code>Alphabet</code>.
   * 
   * @param pSymbol The {@link Symbol} to be appended to this
   *          <code>Alphabet</code>.
   * @throws AlphabetException If something with the <code>Alphabet</code> is
   *           not correct.
   */
  public final void add ( Symbol pSymbol ) throws AlphabetException
  {
    // Symbol
    if ( pSymbol == null )
    {
      throw new NullPointerException ( "symbol is null" ); //$NON-NLS-1$
    }
    /*
     * Throws an AlphabetException if the symbol which should be added is
     * already in this Alphabet.
     */
    if ( this.symbolSet.contains ( pSymbol ) )
    {
      ArrayList < Symbol > negativeSymbols = new ArrayList < Symbol > ();
      for ( Symbol current : this.symbolSet )
      {
        if ( pSymbol.equals ( current ) )
        {
          negativeSymbols.add ( current );
        }
      }
      negativeSymbols.add ( pSymbol );
      throw new AlphabetMoreThanOneSymbolException ( this, negativeSymbols );
    }
    this.symbolSet.add ( pSymbol );
  }


  /**
   * Appends the specified {@link Symbol}s to the end of this
   * <code>Alphabet</code>.
   * 
   * @param pSymbols The {@link Symbol}s to be appended to this
   *          <code>Alphabet</code>.
   * @throws AlphabetException If something with the <code>Alphabet</code> is
   *           not correct.
   */
  public final void add ( Symbol ... pSymbols ) throws AlphabetException
  {
    if ( pSymbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    ArrayList < Symbol > symbolList = new ArrayList < Symbol > ();
    for ( Symbol current : pSymbols )
    {
      symbolList.add ( current );
    }
    checkDuplicated ( symbolList );
    for ( Symbol current : pSymbols )
    {
      add ( current );
    }
  }


  /**
   * Checks the {@link Symbol} list for {@link Symbol}s with the same name.
   * 
   * @param pSymbolList The {@link Symbol} list.
   * @throws AlphabetException If a {@link Symbol} is duplicated.
   */
  private final void checkDuplicated ( ArrayList < Symbol > pSymbolList )
      throws AlphabetException
  {
    Symbol duplicated = null;
    loop : for ( int i = 0 ; i < pSymbolList.size () ; i++ )
    {
      for ( int j = i + 1 ; j < pSymbolList.size () ; j++ )
      {
        if ( pSymbolList.get ( i ).equals ( pSymbolList.get ( j ) ) )
        {
          duplicated = pSymbolList.get ( i );
          break loop;
        }
      }
    }
    if ( duplicated != null )
    {
      ArrayList < Symbol > negativeSymbols = new ArrayList < Symbol > ();
      for ( Symbol current : pSymbolList )
      {
        if ( duplicated.equals ( current ) )
        {
          negativeSymbols.add ( current );
        }
      }
      throw new AlphabetMoreThanOneSymbolException ( this, negativeSymbols );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#clone()
   */
  @Override
  public final Alphabet clone ()
  {
    Alphabet newAlphabet = new Alphabet ();
    for ( Symbol current : this.symbolSet )
    {
      try
      {
        newAlphabet.add ( current.clone () );
      }
      catch ( AlphabetException e )
      {
        e.printStackTrace ();
        System.exit ( 1 );
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
  public final boolean contains ( Symbol pSymbol )
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
   * Returns the {@link Symbol}s.
   * 
   * @return The {@link Symbol}s.
   */
  public final TreeSet < Symbol > get ()
  {
    return this.symbolSet;
  }


  /**
   * Returns the {@link Symbol} with the given index.
   * 
   * @param pIndex The index.
   * @return The {@link Symbol} with the given index.
   * @see #symbolSet
   */
  public final Symbol get ( int pIndex )
  {
    Iterator < Symbol > iterator = this.symbolSet.iterator ();
    for ( int i = 0 ; i < pIndex ; i++ )
    {
      iterator.next ();
    }
    return iterator.next ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Storable#getElement()
   */
  public final Element getElement ()
  {
    Element newElement = new Element ( "Alphabet" ); //$NON-NLS-1$
    newElement.addAttribute ( new Attribute ( "parserStartOffset", //$NON-NLS-1$
        this.parserStartOffset ) );
    newElement.addAttribute ( new Attribute ( "parserEndOffset", //$NON-NLS-1$
        this.parserEndOffset ) );
    for ( Symbol current : this.symbolSet )
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
   * Remove the given {@link Symbol}s from this <code>Alphabet</code>.
   * 
   * @param pSymbols The {@link Symbol}s to remove.
   */
  public final void remove ( Iterable < Symbol > pSymbols )
  {
    if ( pSymbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    for ( Symbol current : pSymbols )
    {
      remove ( current );
    }
  }


  /**
   * Removes the given {@link Symbol} from this <code>Alphabet</code>.
   * 
   * @param pSymbol The {@link Symbol} to remove.
   */
  public final void remove ( Symbol pSymbol )
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
   * Remove the given {@link Symbol}s from this <code>Alphabet</code>.
   * 
   * @param pSymbols The {@link Symbol}s to remove.
   */
  public final void remove ( Symbol ... pSymbols )
  {
    if ( pSymbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    for ( Symbol current : pSymbols )
    {
      remove ( current );
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
   * Returns the number of {@link Symbol}s in this <code>Alphabet</code>.
   * 
   * @return The number of {@link Symbol}s in this <code>Alphabet</code>.
   */
  public final int size ()
  {
    return this.symbolSet.size ();
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
    result.append ( "{" ); //$NON-NLS-1$
    Iterator < Symbol > iterator = this.symbolSet.iterator ();
    boolean first = true;
    while ( iterator.hasNext () )
    {
      if ( !first )
      {
        result.append ( ", " ); //$NON-NLS-1$
      }
      first = false;
      result.append ( iterator.next () );
    }
    result.append ( "}" ); //$NON-NLS-1$
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
    result.append ( "{" ); //$NON-NLS-1$
    Iterator < Symbol > iterator = this.symbolSet.iterator ();
    boolean first = true;
    while ( iterator.hasNext () )
    {
      if ( !first )
      {
        result.append ( ", " ); //$NON-NLS-1$
      }
      first = false;
      result.append ( iterator.next () );
    }
    result.append ( "}" ); //$NON-NLS-1$
    return result.toString ();
  }
}
