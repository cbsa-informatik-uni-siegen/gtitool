package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import javax.swing.event.EventListenerList;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.entities.listener.NonterminalSymbolSetChangedListener;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbol.NonterminalSymbolException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetMoreThanOneSymbolException;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;


/**
 * The {@link DefaultNonterminalSymbolSet} entity.
 * 
 * @author Christian Fehler
 * @version $Id: DefaultNonterminalSymbolSet.java 555 2008-02-12 00:50:47Z
 *          fehler $
 */
public final class DefaultNonterminalSymbolSet implements NonterminalSymbolSet
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -9132421655568694668L;


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * The offset of this {@link DefaultNonterminalSymbolSet} in the source code.
   * 
   * @see #getParserOffset()
   * @see #setParserOffset(ParserOffset)
   */
  private ParserOffset parserOffset = NO_PARSER_OFFSET;


  /**
   * The set of {@link NonterminalSymbol}s.
   */
  private TreeSet < NonterminalSymbol > nonterminalSymbolSet;


  /**
   * The initial set of {@link NonterminalSymbol}s.
   */
  private TreeSet < NonterminalSymbol > initialTerminalSymbolSet;


  /**
   * Allocates a new {@link DefaultNonterminalSymbolSet}.
   */
  public DefaultNonterminalSymbolSet ()
  {
    // NonterminalSymbol
    this.nonterminalSymbolSet = new TreeSet < NonterminalSymbol > ();
    this.initialTerminalSymbolSet = new TreeSet < NonterminalSymbol > ();

    // Reset modify
    resetModify ();
  }


  /**
   * Allocates a new {@link DefaultNonterminalSymbolSet}.
   * 
   * @param element The {@link Element}.
   * @throws NonterminalSymbolSetException If something with the
   *           {@link DefaultNonterminalSymbolSet} is not correct.
   * @throws NonterminalSymbolException If something with the {@link Symbol} is
   *           not correct.
   * @throws StoreException If the {@link Element} can not be parsed.
   */
  public DefaultNonterminalSymbolSet ( Element element )
      throws NonterminalSymbolSetException, NonterminalSymbolException,
      StoreException
  {
    this ();
    // Check if the element is correct
    if ( !element.getName ().equals ( "NonterminalSymbolSet" ) ) //$NON-NLS-1$
    {
      throw new IllegalArgumentException (
          "element " + Messages.QUOTE + element.getName () //$NON-NLS-1$
              + Messages.QUOTE + " is not a nonterminal symbol set" ); //$NON-NLS-1$
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
      if ( current.getName ().equals ( "NonterminalSymbol" ) ) //$NON-NLS-1$
      {
        add ( new DefaultNonterminalSymbol ( current ) );
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
   * Allocates a new {@link DefaultNonterminalSymbolSet}.
   * 
   * @param nonterminalSymbols The array of {@link NonterminalSymbol}s.
   * @throws NonterminalSymbolSetException If something with the
   *           {@link DefaultNonterminalSymbolSet} is not correct.
   */
  public DefaultNonterminalSymbolSet (
      Iterable < NonterminalSymbol > nonterminalSymbols )
      throws NonterminalSymbolSetException
  {
    this ();
    // NonterminalSymbols
    if ( nonterminalSymbols == null )
    {
      throw new NullPointerException ( "nonterminal symbols is null" ); //$NON-NLS-1$
    }
    add ( nonterminalSymbols );

    // Reset modify
    resetModify ();
  }


  /**
   * Allocates a new {@link DefaultNonterminalSymbolSet}.
   * 
   * @param nonterminalSymbols The array of {@link NonterminalSymbol}s.
   * @throws NonterminalSymbolSetException If something with the
   *           {@link DefaultNonterminalSymbolSet} is not correct.
   */
  public DefaultNonterminalSymbolSet ( NonterminalSymbol ... nonterminalSymbols )
      throws NonterminalSymbolSetException
  {
    this ();
    // NonterminalSymbols
    if ( nonterminalSymbols == null )
    {
      throw new NullPointerException ( "nonterminal symbols is null" ); //$NON-NLS-1$
    }
    add ( nonterminalSymbols );

    // Reset modify
    resetModify ();
  }


  /**
   * Appends the specified {@link NonterminalSymbol}s to the end of this
   * {@link DefaultNonterminalSymbolSet}.
   * 
   * @param nonterminalSymbols The {@link NonterminalSymbol}s to be appended to
   *          this {@link DefaultNonterminalSymbolSet}.
   * @throws NonterminalSymbolSetException If something with the
   *           {@link DefaultNonterminalSymbolSet} is not correct.
   */
  public final void add ( Iterable < NonterminalSymbol > nonterminalSymbols )
      throws NonterminalSymbolSetException
  {
    if ( nonterminalSymbols == null )
    {
      throw new NullPointerException ( "nonterminal symbols is null" ); //$NON-NLS-1$
    }
    ArrayList < NonterminalSymbol > symbolList = new ArrayList < NonterminalSymbol > ();
    for ( NonterminalSymbol current : nonterminalSymbols )
    {
      symbolList.add ( current );
    }
    checkDuplicated ( symbolList );
    for ( NonterminalSymbol current : nonterminalSymbols )
    {
      add ( current );
    }
  }


  /**
   * Appends the specified {@link NonterminalSymbol} to the end of this
   * {@link DefaultNonterminalSymbolSet}.
   * 
   * @param nonterminalSymbol The {@link NonterminalSymbol} to be appended to
   *          this {@link DefaultNonterminalSymbolSet}.
   * @throws NonterminalSymbolSetException If something with the
   *           {@link DefaultNonterminalSymbolSet} is not correct.
   */
  public final void add ( NonterminalSymbol nonterminalSymbol )
      throws NonterminalSymbolSetException
  {
    // NonterminalSymbol
    if ( nonterminalSymbol == null )
    {
      throw new NullPointerException ( "nonterminal symbol is null" ); //$NON-NLS-1$
    }
    /*
     * Throws an NonterminalSymbolSetException if the symbol which should be
     * added is already in this NonterminalSymbolSet.
     */
    if ( this.nonterminalSymbolSet.contains ( nonterminalSymbol ) )
    {
      ArrayList < NonterminalSymbol > negativeSymbols = new ArrayList < NonterminalSymbol > ();
      for ( NonterminalSymbol current : this.nonterminalSymbolSet )
      {
        if ( nonterminalSymbol.equals ( current ) )
        {
          negativeSymbols.add ( current );
        }
      }
      negativeSymbols.add ( nonterminalSymbol );
      throw new NonterminalSymbolSetMoreThanOneSymbolException ( this,
          negativeSymbols );
    }
    this.nonterminalSymbolSet.add ( nonterminalSymbol );
    fireNonterminalSymbolSetChanged ();
    fireModifyStatusChanged ();
  }


  /**
   * Appends the specified {@link NonterminalSymbol}s to the end of this
   * {@link DefaultNonterminalSymbolSet}.
   * 
   * @param nonterminalSymbols The {@link NonterminalSymbol}s to be appended to
   *          this {@link DefaultNonterminalSymbolSet}.
   * @throws NonterminalSymbolSetException If something with the
   *           {@link DefaultNonterminalSymbolSet} is not correct.
   */
  public final void add ( NonterminalSymbol ... nonterminalSymbols )
      throws NonterminalSymbolSetException
  {
    if ( nonterminalSymbols == null )
    {
      throw new NullPointerException ( "nonterminal symbols is null" ); //$NON-NLS-1$
    }
    ArrayList < NonterminalSymbol > symbolList = new ArrayList < NonterminalSymbol > ();
    for ( NonterminalSymbol current : nonterminalSymbols )
    {
      symbolList.add ( current );
    }
    checkDuplicated ( symbolList );
    for ( NonterminalSymbol current : nonterminalSymbols )
    {
      add ( current );
    }
  }


  /**
   * Adds the given {@link NonterminalSymbolSetChangedListener}.
   * 
   * @param listener The {@link NonterminalSymbolSetChangedListener}.
   */
  public final synchronized void addNonterminalSymbolSetChangedListener (
      NonterminalSymbolSetChangedListener listener )
  {
    this.listenerList
        .add ( NonterminalSymbolSetChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#addModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public final synchronized void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.add ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * Checks the {@link NonterminalSymbol} list for {@link NonterminalSymbol}s
   * with the same name.
   * 
   * @param nonterminalSymbols The {@link NonterminalSymbol} list.
   * @throws NonterminalSymbolSetException If a {@link NonterminalSymbol} is
   *           duplicated.
   */
  private final void checkDuplicated (
      ArrayList < NonterminalSymbol > nonterminalSymbols )
      throws NonterminalSymbolSetException
  {
    NonterminalSymbol duplicated = null;
    loop : for ( int i = 0 ; i < nonterminalSymbols.size () ; i++ )
    {
      for ( int j = i + 1 ; j < nonterminalSymbols.size () ; j++ )
      {
        if ( nonterminalSymbols.get ( i )
            .equals ( nonterminalSymbols.get ( j ) ) )
        {
          duplicated = nonterminalSymbols.get ( i );
          break loop;
        }
      }
    }
    if ( duplicated != null )
    {
      ArrayList < NonterminalSymbol > negativeSymbols = new ArrayList < NonterminalSymbol > ();
      for ( NonterminalSymbol current : nonterminalSymbols )
      {
        if ( duplicated.equals ( current ) )
        {
          negativeSymbols.add ( current );
        }
      }
      throw new NonterminalSymbolSetMoreThanOneSymbolException ( this,
          negativeSymbols );
    }
  }


  /**
   * Removes all {@link NonterminalSymbol}s.
   */
  public final void clear ()
  {
    this.nonterminalSymbolSet.clear ();
    fireNonterminalSymbolSetChanged ();
    fireModifyStatusChanged ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#clone()
   */
  @Override
  public final DefaultNonterminalSymbolSet clone ()
  {
    DefaultNonterminalSymbolSet newDefaultNonterminalSymbolSet = new DefaultNonterminalSymbolSet ();
    for ( NonterminalSymbol current : this.nonterminalSymbolSet )
    {
      try
      {
        newDefaultNonterminalSymbolSet.add ( current.clone () );
      }
      catch ( NonterminalSymbolSetException e )
      {
        e.printStackTrace ();
        System.exit ( 1 );
      }
    }
    return newDefaultNonterminalSymbolSet;
  }


  /**
   * Returns true if this {@link DefaultNonterminalSymbolSet} contains the
   * specified {@link NonterminalSymbol}.
   * 
   * @param nonterminalSymbol {@link NonterminalSymbol} whose presence in this
   *          {@link DefaultNonterminalSymbolSet} is to be tested.
   * @return true if the specified {@link NonterminalSymbol} is present; false
   *         otherwise.
   */
  public final boolean contains ( NonterminalSymbol nonterminalSymbol )
  {
    return this.nonterminalSymbolSet.contains ( nonterminalSymbol );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object other )
  {
    if ( other instanceof DefaultNonterminalSymbolSet )
    {
      DefaultNonterminalSymbolSet defaultNonterminalSymbolSet = ( DefaultNonterminalSymbolSet ) other;
      return this.nonterminalSymbolSet
          .equals ( defaultNonterminalSymbolSet.nonterminalSymbolSet );
    }
    return false;
  }


  /**
   * Let the listeners know that the {@link NonterminalSymbolSet} has changed.
   */
  private final void fireNonterminalSymbolSetChanged ()
  {
    NonterminalSymbolSetChangedListener [] listeners = this.listenerList
        .getListeners ( NonterminalSymbolSetChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].nonterminalSymbolSetChanged ( this );
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
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].modifyStatusChanged ( newModifyStatus );
    }
  }


  /**
   * Returns the {@link NonterminalSymbol}s.
   * 
   * @return The {@link NonterminalSymbol}s.
   */
  public final TreeSet < NonterminalSymbol > get ()
  {
    return this.nonterminalSymbolSet;
  }


  /**
   * Returns the {@link NonterminalSymbol} with the given index.
   * 
   * @param index The index.
   * @return The {@link NonterminalSymbol} with the given index.
   * @see #nonterminalSymbolSet
   */
  public final NonterminalSymbol get ( int index )
  {
    Iterator < NonterminalSymbol > iterator = this.nonterminalSymbolSet
        .iterator ();
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
    Element newElement = new Element ( "NonterminalSymbolSet" ); //$NON-NLS-1$
    for ( NonterminalSymbol current : this.nonterminalSymbolSet )
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
    return this.nonterminalSymbolSet.hashCode ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#isModified()
   */
  public final boolean isModified ()
  {
    return ( !this.nonterminalSymbolSet.equals ( this.initialTerminalSymbolSet ) );
  }


  /**
   * Returns an iterator over the {@link NonterminalSymbol}s in this
   * {@link DefaultNonterminalSymbolSet}.
   * 
   * @return An iterator over the {@link NonterminalSymbol}s in this
   *         {@link DefaultNonterminalSymbolSet}.
   */
  public final Iterator < NonterminalSymbol > iterator ()
  {
    return this.nonterminalSymbolSet.iterator ();
  }


  /**
   * Remove the given {@link NonterminalSymbol}s from this
   * {@link DefaultNonterminalSymbolSet}.
   * 
   * @param nonterminalSymbols The {@link NonterminalSymbol}s to remove.
   */
  public final void remove ( Iterable < NonterminalSymbol > nonterminalSymbols )
  {
    if ( nonterminalSymbols == null )
    {
      throw new NullPointerException ( "nonterminal symbols is null" ); //$NON-NLS-1$
    }
    for ( NonterminalSymbol current : nonterminalSymbols )
    {
      remove ( current );
    }
  }


  /**
   * Removes the given {@link NonterminalSymbol} from this
   * {@link NonterminalSymbolSet}.
   * 
   * @param nonterminalSymbol The {@link NonterminalSymbol} to remove.
   */
  public final void remove ( NonterminalSymbol nonterminalSymbol )
  {
    if ( nonterminalSymbol == null )
    {
      throw new NullPointerException ( "nonterminal symbol is null" ); //$NON-NLS-1$
    }
    if ( !this.nonterminalSymbolSet.contains ( nonterminalSymbol ) )
    {
      throw new IllegalArgumentException (
          "nonterminal symbol is not in this nonterminal symbol set" ); //$NON-NLS-1$
    }
    this.nonterminalSymbolSet.remove ( nonterminalSymbol );
    fireNonterminalSymbolSetChanged ();
    fireModifyStatusChanged ();
  }


  /**
   * Remove the given {@link NonterminalSymbol}s from this
   * {@link NonterminalSymbolSet}.
   * 
   * @param nonterminalSymbols The {@link NonterminalSymbol}s to remove.
   */
  public final void remove ( NonterminalSymbol ... nonterminalSymbols )
  {
    if ( nonterminalSymbols == null )
    {
      throw new NullPointerException ( "nonterminal symbols is null" ); //$NON-NLS-1$
    }
    for ( NonterminalSymbol current : nonterminalSymbols )
    {
      remove ( current );
    }
  }


  /**
   * Removes the given {@link NonterminalSymbolSetChangedListener}.
   * 
   * @param listener The {@link NonterminalSymbolSetChangedListener}.
   */
  public final synchronized void removeNonterminalSymbolSetChangedListener (
      NonterminalSymbolSetChangedListener listener )
  {
    this.listenerList.remove ( NonterminalSymbolSetChangedListener.class,
        listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#removeModifyStatusChangedListener(ModifyStatusChangedListener)
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
    this.initialTerminalSymbolSet.clear ();
    this.initialTerminalSymbolSet.addAll ( this.nonterminalSymbolSet );
  }


  /**
   * {@inheritDoc}
   */
  public final void setParserOffset ( ParserOffset parserOffset )
  {
    this.parserOffset = parserOffset;
  }


  /**
   * Returns the number of {@link NonterminalSymbol}s in this
   * {@link DefaultNonterminalSymbolSet}.
   * 
   * @return The number of {@link NonterminalSymbol}s in this
   *         {@link DefaultNonterminalSymbolSet}.
   */
  public final int size ()
  {
    return this.nonterminalSymbolSet.size ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see PrettyPrintable#toPrettyString()
   */
  public final PrettyString toPrettyString ()
  {
    PrettyString prettyString = new PrettyString ();
    prettyString.addPrettyToken ( new PrettyToken ( "{", Style.NONE ) ); //$NON-NLS-1$
    Iterator < NonterminalSymbol > iterator = this.nonterminalSymbolSet
        .iterator ();
    boolean first = true;
    while ( iterator.hasNext () )
    {
      if ( !first )
      {
        prettyString.addPrettyToken ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$
      }
      first = false;
      prettyString.addPrettyPrintable ( iterator.next () );
    }
    prettyString.addPrettyToken ( new PrettyToken ( "}", Style.NONE ) ); //$NON-NLS-1$
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
    result.append ( "{" ); //$NON-NLS-1$
    Iterator < NonterminalSymbol > iterator = this.nonterminalSymbolSet
        .iterator ();
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
    Iterator < NonterminalSymbol > iterator = this.nonterminalSymbolSet
        .iterator ();
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
