package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import javax.swing.event.EventListenerList;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.listener.AlphabetChangedListener;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetMoreThanOneSymbolException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;


/**
 * The <code>DefaultAlphabet</code> entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class DefaultAlphabet implements Alphabet
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -4488353013296552669L;


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * The offset of this {@link DefaultAlphabet} in the source code.
   * 
   * @see #getParserOffset()
   * @see #setParserOffset(ParserOffset)
   */
  private ParserOffset parserOffset = NO_PARSER_OFFSET;


  /**
   * The set of {@link Symbol}s.
   */
  private TreeSet < Symbol > symbolSet;


  /**
   * The initial set of {@link Symbol}s.
   */
  private TreeSet < Symbol > initialSymbolSet;


  /**
   * The old modify status.
   */
  private boolean oldModifyStatus = false;


  /**
   * Allocates a new <code>DefaultAlphabet</code>.
   */
  public DefaultAlphabet ()
  {
    // SymbolSet
    this.symbolSet = new TreeSet < Symbol > ();
    this.initialSymbolSet = new TreeSet < Symbol > ();

    // Reset modify
    resetModify ();
  }


  /**
   * Allocates a new <code>DefaultAlphabet</code>.
   * 
   * @param element The {@link Element}.
   * @throws AlphabetException If something with the
   *           <code>DefaultAlphabet</code> is not correct.
   * @throws SymbolException If something with the <code>Symbol</code> is not
   *           correct.
   * @throws StoreException If the {@link Element} can not be parsed.
   */
  public DefaultAlphabet ( Element element ) throws AlphabetException,
      SymbolException, StoreException
  {
    this ();
    // Check if the element is correct
    if ( !element.getName ().equals ( "Alphabet" ) ) //$NON-NLS-1$
    {
      throw new IllegalArgumentException ( "element \"" + element.getName () //$NON-NLS-1$
          + "\" is not a alphabet" ); //$NON-NLS-1$
    }

    // Attribute
    if ( element.getAttribute ().size () > 0 )
    {
      throw new StoreException ( Messages
          .getString ( "StoreException.AdditionalAttribute" ) ); //$NON-NLS-1$
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

    // Reset modify
    resetModify ();
  }


  /**
   * Allocates a new <code>DefaultAlphabet</code>.
   * 
   * @param symbols The array of {@link Symbol}s.
   * @throws AlphabetException If something with the
   *           <code>DefaultAlphabet</code> is not correct.
   */
  public DefaultAlphabet ( Iterable < Symbol > symbols )
      throws AlphabetException
  {
    this ();
    // Symbols
    if ( symbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    add ( symbols );

    // Reset modify
    resetModify ();
  }


  /**
   * Allocates a new <code>DefaultAlphabet</code>.
   * 
   * @param symbols The array of {@link Symbol}s.
   * @throws AlphabetException If something with the
   *           <code>DefaultAlphabet</code> is not correct.
   */
  public DefaultAlphabet ( Symbol ... symbols ) throws AlphabetException
  {
    this ();
    // Symbols
    if ( symbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    add ( symbols );

    // Reset modify
    resetModify ();
  }


  /**
   * Appends the specified {@link Symbol}s to the end of this
   * <code>DefaultAlphabet</code>.
   * 
   * @param symbols The {@link Symbol}s to be appended to this
   *          <code>DefaultAlphabet</code>.
   * @throws AlphabetException If something with the
   *           <code>DefaultAlphabet</code> is not correct.
   */
  public final void add ( Iterable < Symbol > symbols )
      throws AlphabetException
  {
    if ( symbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    ArrayList < Symbol > symbolList = new ArrayList < Symbol > ();
    for ( Symbol current : symbols )
    {
      symbolList.add ( current );
    }
    checkDuplicated ( symbolList );
    for ( Symbol current : symbols )
    {
      add ( current );
    }
  }


  /**
   * Appends the specified {@link Symbol} to the end of this
   * <code>DefaultAlphabet</code>.
   * 
   * @param symbol The {@link Symbol} to be appended to this
   *          <code>DefaultAlphabet</code>.
   * @throws AlphabetException If something with the
   *           <code>DefaultAlphabet</code> is not correct.
   */
  public final void add ( Symbol symbol ) throws AlphabetException
  {
    // Symbol
    if ( symbol == null )
    {
      throw new NullPointerException ( "symbol is null" ); //$NON-NLS-1$
    }
    /*
     * Throws an AlphabetException if the symbol which should be added is
     * already in this Alphabet.
     */
    if ( this.symbolSet.contains ( symbol ) )
    {
      ArrayList < Symbol > negativeSymbols = new ArrayList < Symbol > ();
      for ( Symbol current : this.symbolSet )
      {
        if ( symbol.equals ( current ) )
        {
          negativeSymbols.add ( current );
        }
      }
      negativeSymbols.add ( symbol );
      throw new AlphabetMoreThanOneSymbolException ( this, negativeSymbols );
    }
    this.symbolSet.add ( symbol );
    fireAlphabetChanged ();
    fireModifyStatusChanged ();
  }


  /**
   * Appends the specified {@link Symbol}s to the end of this
   * <code>DefaultAlphabet</code>.
   * 
   * @param symbols The {@link Symbol}s to be appended to this
   *          <code>DefaultAlphabet</code>.
   * @throws AlphabetException If something with the
   *           <code>DefaultAlphabet</code> is not correct.
   */
  public final void add ( Symbol ... symbols ) throws AlphabetException
  {
    if ( symbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    ArrayList < Symbol > symbolList = new ArrayList < Symbol > ();
    for ( Symbol current : symbols )
    {
      symbolList.add ( current );
    }
    checkDuplicated ( symbolList );
    for ( Symbol current : symbols )
    {
      add ( current );
    }
  }


  /**
   * Adds the given {@link AlphabetChangedListener}.
   * 
   * @param listener The {@link AlphabetChangedListener}.
   */
  public final synchronized void addAlphabetChangedListener (
      AlphabetChangedListener listener )
  {
    this.listenerList.add ( AlphabetChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Machine#addModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public final synchronized void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.add ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * Checks the {@link Symbol} list for {@link Symbol}s with the same name.
   * 
   * @param symbols The {@link Symbol} list.
   * @throws AlphabetException If a {@link Symbol} is duplicated.
   */
  private final void checkDuplicated ( ArrayList < Symbol > symbols )
      throws AlphabetException
  {
    Symbol duplicated = null;
    loop : for ( int i = 0 ; i < symbols.size () ; i++ )
    {
      for ( int j = i + 1 ; j < symbols.size () ; j++ )
      {
        if ( symbols.get ( i ).equals ( symbols.get ( j ) ) )
        {
          duplicated = symbols.get ( i );
          break loop;
        }
      }
    }
    if ( duplicated != null )
    {
      ArrayList < Symbol > negativeSymbols = new ArrayList < Symbol > ();
      for ( Symbol current : symbols )
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
   * Removes all {@link Symbol}s.
   */
  public final void clear ()
  {
    this.symbolSet.clear ();
    fireAlphabetChanged ();
    fireModifyStatusChanged ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#clone()
   */
  @Override
  public final DefaultAlphabet clone ()
  {
    DefaultAlphabet newDefaultAlphabet = new DefaultAlphabet ();
    for ( Symbol current : this.symbolSet )
    {
      try
      {
        newDefaultAlphabet.add ( current.clone () );
      }
      catch ( AlphabetException e )
      {
        e.printStackTrace ();
        System.exit ( 1 );
      }
    }
    return newDefaultAlphabet;
  }


  /**
   * Returns <tt>true</tt> if this <code>DefaultAlphabet</code> contains the
   * specified {@link Symbol}.
   * 
   * @param symbol {@link Symbol} whose presence in this
   *          <code>DefaultAlphabet</code> is to be tested.
   * @return <code>true</code> if the specified {@link Symbol} is present;
   *         <code>false</code> otherwise.
   */
  public final boolean contains ( Symbol symbol )
  {
    return this.symbolSet.contains ( symbol );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object other )
  {
    if ( other instanceof DefaultAlphabet )
    {
      DefaultAlphabet defaultAlphabet = ( DefaultAlphabet ) other;
      return this.symbolSet.equals ( defaultAlphabet.symbolSet );
    }
    return false;
  }


  /**
   * Let the listeners know that the {@link Alphabet} has changed.
   */
  private final void fireAlphabetChanged ()
  {
    AlphabetChangedListener [] listeners = this.listenerList
        .getListeners ( AlphabetChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].alphabetChanged ( this );
    }
  }


  /**
   * Let the listeners know that the modify status has changed.
   */
  private final void fireModifyStatusChanged ()
  {
    ModifyStatusChangedListener [] listeners = this.listenerList
        .getListeners ( ModifyStatusChangedListener.class );
    boolean newModifyStatus = isModified ();
    if ( newModifyStatus != this.oldModifyStatus )
    {
      this.oldModifyStatus = newModifyStatus;
      for ( int n = 0 ; n < listeners.length ; ++n )
      {
        listeners [ n ].modifyStatusChanged ( newModifyStatus );
      }
    }
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
   * @param index The index.
   * @return The {@link Symbol} with the given index.
   * @see #symbolSet
   */
  public final Symbol get ( int index )
  {
    Iterator < Symbol > iterator = this.symbolSet.iterator ();
    for ( int i = 0 ; i < index ; i++ )
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
    for ( Symbol current : this.symbolSet )
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
   * {@inheritDoc}
   * 
   * @see Modifyable#isModified()
   */
  public final boolean isModified ()
  {
    return ( !this.symbolSet.equals ( this.initialSymbolSet ) );
  }


  /**
   * Returns an iterator over the {@link Symbol}s in this
   * <code>DefaultAlphabet</code>.
   * 
   * @return An iterator over the {@link Symbol}s in this
   *         <code>DefaultAlphabet</code>.
   */
  public final Iterator < Symbol > iterator ()
  {
    return this.symbolSet.iterator ();
  }


  /**
   * Remove the given {@link Symbol}s from this <code>DefaultAlphabet</code>.
   * 
   * @param symbols The {@link Symbol}s to remove.
   */
  public final void remove ( Iterable < Symbol > symbols )
  {
    if ( symbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    for ( Symbol current : symbols )
    {
      remove ( current );
    }
  }


  /**
   * Removes the given {@link Symbol} from this <code>DefaultAlphabet</code>.
   * 
   * @param symbol The {@link Symbol} to remove.
   */
  public final void remove ( Symbol symbol )
  {
    if ( symbol == null )
    {
      throw new NullPointerException ( "symbol is null" ); //$NON-NLS-1$
    }
    if ( !this.symbolSet.contains ( symbol ) )
    {
      throw new IllegalArgumentException ( "symbol is not in this alphabet" ); //$NON-NLS-1$
    }
    this.symbolSet.remove ( symbol );
    fireAlphabetChanged ();
    fireModifyStatusChanged ();
  }


  /**
   * Remove the given {@link Symbol}s from this <code>DefaultAlphabet</code>.
   * 
   * @param symbols The {@link Symbol}s to remove.
   */
  public final void remove ( Symbol ... symbols )
  {
    if ( symbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    for ( Symbol current : symbols )
    {
      remove ( current );
    }
  }


  /**
   * Removes the given {@link AlphabetChangedListener}.
   * 
   * @param listener The {@link AlphabetChangedListener}.
   */
  public final synchronized void removeAlphabetChangedListener (
      AlphabetChangedListener listener )
  {
    this.listenerList.remove ( AlphabetChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Machine#removeModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public final synchronized void removeModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.remove ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#resetModify()
   */
  public final void resetModify ()
  {
    this.initialSymbolSet.clear ();
    this.initialSymbolSet.addAll ( this.symbolSet );
    this.oldModifyStatus = false;
  }


  /**
   * {@inheritDoc}
   */
  public final void setParserOffset ( ParserOffset parserOffset )
  {
    this.parserOffset = parserOffset;
  }


  /**
   * Returns the number of {@link Symbol}s in this <code>DefaultAlphabet</code>.
   * 
   * @return The number of {@link Symbol}s in this <code>DefaultAlphabet</code>.
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
