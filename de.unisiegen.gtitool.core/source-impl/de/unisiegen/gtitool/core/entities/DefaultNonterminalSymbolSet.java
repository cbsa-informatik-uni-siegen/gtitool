package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import javax.swing.event.EventListenerList;

import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.entities.listener.NonterminalSymbolSetChangedListener;
import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetMoreThanOneSymbolException;
import de.unisiegen.gtitool.core.i18n.Messages;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.parser.style.PrettyString.PrettyStringMode;
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
   * The cached {@link PrettyString}.
   */
  private PrettyString cachedPrettyString = null;


  /**
   * The {@link PrettyStringChangedListener}.
   */
  private PrettyStringChangedListener prettyStringChangedListener;


  /**
   * Allocates a new {@link DefaultNonterminalSymbolSet}.
   */
  public DefaultNonterminalSymbolSet ()
  {
    this.prettyStringChangedListener = new PrettyStringChangedListener ()
    {

      public void prettyStringChanged ()
      {
        firePrettyStringChanged ();
      }
    };

    // NonterminalSymbol
    this.nonterminalSymbolSet = new TreeSet < NonterminalSymbol > ();
    this.initialTerminalSymbolSet = new TreeSet < NonterminalSymbol > ();

    resetModify ();
  }


  /**
   * Allocates a new {@link DefaultNonterminalSymbolSet}.
   * 
   * @param element The {@link Element}.
   * @throws NonterminalSymbolSetException If something with the
   *           {@link DefaultNonterminalSymbolSet} is not correct.
   * @throws StoreException If the {@link Element} can not be parsed.
   */
  public DefaultNonterminalSymbolSet ( Element element )
      throws NonterminalSymbolSetException, StoreException
  {
    this ();

    // Check if the element is correct
    if ( !element.getName ().equals ( "NonterminalSymbolSet" ) )
      throw new IllegalArgumentException (
          "element " + Messages.QUOTE + element.getName () //$NON-NLS-1$
              + Messages.QUOTE + " is not a nonterminal symbol set" ); //$NON-NLS-1$

    // Attribute
    if ( element.getAttribute ().size () > 0 )
      throw new StoreException ( Messages
          .getString ( "StoreException.AdditionalAttribute" ) ); //$NON-NLS-1$

    // Element
    for ( Element current : element.getElement () )
      if ( current.getName ().equals ( "NonterminalSymbol" ) )
        add ( new DefaultNonterminalSymbol ( current ) );
      else
        throw new StoreException ( Messages
            .getString ( "StoreException.AdditionalElement" ) ); //$NON-NLS-1$

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
      throw new NullPointerException ( "nonterminal symbols is null" ); //$NON-NLS-1$
    add ( nonterminalSymbols );

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
      throw new NullPointerException ( "nonterminal symbols is null" ); //$NON-NLS-1$
    add ( nonterminalSymbols );

    resetModify ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see NonterminalSymbolSet#add(Iterable)
   */
  public final void add ( Iterable < NonterminalSymbol > nonterminalSymbols )
      throws NonterminalSymbolSetException
  {
    if ( nonterminalSymbols == null )
      throw new NullPointerException ( "nonterminal symbols is null" ); //$NON-NLS-1$
    ArrayList < NonterminalSymbol > symbolList = new ArrayList < NonterminalSymbol > ();
    for ( NonterminalSymbol current : nonterminalSymbols )
      symbolList.add ( current );
    checkDuplicated ( symbolList );
    for ( NonterminalSymbol current : nonterminalSymbols )
      add ( current );
  }


  /**
   * {@inheritDoc}
   * 
   * @see NonterminalSymbolSet#add(NonterminalSymbol)
   */
  public final void add ( NonterminalSymbol nonterminalSymbol )
      throws NonterminalSymbolSetException
  {
    // NonterminalSymbol
    if ( nonterminalSymbol == null )
      throw new NullPointerException ( "nonterminal symbol is null" ); //$NON-NLS-1$
    /*
     * Throws an NonterminalSymbolSetException if the symbol which should be
     * added is already in this NonterminalSymbolSet.
     */
    if ( this.nonterminalSymbolSet.contains ( nonterminalSymbol ) )
    {
      ArrayList < NonterminalSymbol > negativeSymbols = new ArrayList < NonterminalSymbol > ();
      for ( NonterminalSymbol current : this.nonterminalSymbolSet )
        if ( nonterminalSymbol.equals ( current ) )
          negativeSymbols.add ( current );
      negativeSymbols.add ( nonterminalSymbol );
      throw new NonterminalSymbolSetMoreThanOneSymbolException ( this,
          negativeSymbols );
    }

    nonterminalSymbol
        .addPrettyStringChangedListener ( this.prettyStringChangedListener );

    this.nonterminalSymbolSet.add ( nonterminalSymbol );

    fireNonterminalSymbolSetChanged ();
    fireModifyStatusChanged ();
    firePrettyStringChanged ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see NonterminalSymbolSet#add(NonterminalSymbol[])
   */
  public final void add ( NonterminalSymbol ... nonterminalSymbols )
      throws NonterminalSymbolSetException
  {
    if ( nonterminalSymbols == null )
      throw new NullPointerException ( "nonterminal symbols is null" ); //$NON-NLS-1$
    ArrayList < NonterminalSymbol > symbolList = new ArrayList < NonterminalSymbol > ();
    for ( NonterminalSymbol current : nonterminalSymbols )
      symbolList.add ( current );
    checkDuplicated ( symbolList );
    for ( NonterminalSymbol current : nonterminalSymbols )
      add ( current );
  }


  /**
   * blub
   * 
   * @param nonterminalSymbols blub
   * @throws NonterminalSymbolSetMoreThanOneSymbolException
   */
  public final void add2 ( final NonterminalSymbol ... nonterminalSymbols )
      throws NonterminalSymbolSetMoreThanOneSymbolException
  {
    if ( nonterminalSymbols == null )
      throw new NullPointerException ( "nonterminal symbols is null" ); //$NON-NLS-1$
    ArrayList < NonterminalSymbol > negativeSymbols = new ArrayList < NonterminalSymbol > ();
    for ( NonterminalSymbol current : nonterminalSymbols )
    {
      boolean mod = this.nonterminalSymbolSet.add ( current );
      if ( !mod )
        negativeSymbols.add ( current );
    }
    throw new NonterminalSymbolSetMoreThanOneSymbolException ( this,
        negativeSymbols );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#addModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public final void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.add ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see NonterminalSymbolSet#addNonterminalSymbolSetChangedListener(NonterminalSymbolSetChangedListener)
   */
  public final void addNonterminalSymbolSetChangedListener (
      NonterminalSymbolSetChangedListener listener )
  {
    this.listenerList
        .add ( NonterminalSymbolSetChangedListener.class, listener );
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
      for ( int j = i + 1 ; j < nonterminalSymbols.size () ; j++ )
        if ( nonterminalSymbols.get ( i )
            .equals ( nonterminalSymbols.get ( j ) ) )
        {
          duplicated = nonterminalSymbols.get ( i );
          break loop;
        }
    if ( duplicated != null )
    {
      ArrayList < NonterminalSymbol > negativeSymbols = new ArrayList < NonterminalSymbol > ();
      for ( NonterminalSymbol current : nonterminalSymbols )
        if ( duplicated.equals ( current ) )
          negativeSymbols.add ( current );
      throw new NonterminalSymbolSetMoreThanOneSymbolException ( this,
          negativeSymbols );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see NonterminalSymbolSet#clear()
   */
  public final void clear ()
  {
    for ( NonterminalSymbol current : this.nonterminalSymbolSet )
      current
          .removePrettyStringChangedListener ( this.prettyStringChangedListener );

    fireNonterminalSymbolSetChanged ();
    fireModifyStatusChanged ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Comparable#compareTo(Object)
   */
  public final int compareTo ( NonterminalSymbolSet other )
  {
    ArrayList < NonterminalSymbol > firstList = new ArrayList < NonterminalSymbol > ();
    ArrayList < NonterminalSymbol > secondList = new ArrayList < NonterminalSymbol > ();

    firstList.addAll ( this.nonterminalSymbolSet );
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
   * @see NonterminalSymbolSet#contains(NonterminalSymbol)
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
   * Let the listeners know that the modify status has changed.
   */
  private final void fireModifyStatusChanged ()
  {
    ModifyStatusChangedListener [] listeners = this.listenerList
        .getListeners ( ModifyStatusChangedListener.class );
    boolean newModifyStatus = isModified ();
    for ( ModifyStatusChangedListener current : listeners )
      current.modifyStatusChanged ( newModifyStatus );
  }


  /**
   * Let the listeners know that the {@link NonterminalSymbolSet} has changed.
   */
  private final void fireNonterminalSymbolSetChanged ()
  {
    NonterminalSymbolSetChangedListener [] listeners = this.listenerList
        .getListeners ( NonterminalSymbolSetChangedListener.class );
    for ( NonterminalSymbolSetChangedListener current : listeners )
      current.nonterminalSymbolSetChanged ( this );
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
   * @see NonterminalSymbolSet#get()
   */
  public final TreeSet < NonterminalSymbol > get ()
  {
    return this.nonterminalSymbolSet;
  }


  /**
   * {@inheritDoc}
   * 
   * @see NonterminalSymbolSet#get(int)
   */
  public final NonterminalSymbol get ( int index )
  {
    Iterator < NonterminalSymbol > iterator = this.nonterminalSymbolSet
        .iterator ();
    for ( int i = 0 ; i < index ; i++ )
      iterator.next ();
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
   * {@inheritDoc}
   * 
   * @see Iterable#iterator()
   */
  public final Iterator < NonterminalSymbol > iterator ()
  {
    return this.nonterminalSymbolSet.iterator ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see NonterminalSymbolSet#remove(Iterable)
   */
  public final void remove ( Iterable < NonterminalSymbol > nonterminalSymbols )
  {
    if ( nonterminalSymbols == null )
      throw new NullPointerException ( "nonterminal symbols is null" ); //$NON-NLS-1$
    for ( NonterminalSymbol current : nonterminalSymbols )
      remove ( current );
  }


  /**
   * {@inheritDoc}
   * 
   * @see NonterminalSymbolSet#remove(NonterminalSymbol)
   */
  public final void remove ( NonterminalSymbol nonterminalSymbol )
  {
    if ( nonterminalSymbol == null )
      throw new NullPointerException ( "nonterminal symbol is null" ); //$NON-NLS-1$
    if ( !this.nonterminalSymbolSet.contains ( nonterminalSymbol ) )
      throw new IllegalArgumentException (
          "nonterminal symbol is not in this nonterminal symbol set" ); //$NON-NLS-1$

    nonterminalSymbol
        .removePrettyStringChangedListener ( this.prettyStringChangedListener );

    this.nonterminalSymbolSet.remove ( nonterminalSymbol );

    fireNonterminalSymbolSetChanged ();
    fireModifyStatusChanged ();
    firePrettyStringChanged ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see NonterminalSymbolSet#remove(NonterminalSymbol[])
   */
  public final void remove ( NonterminalSymbol ... nonterminalSymbols )
  {
    if ( nonterminalSymbols == null )
      throw new NullPointerException ( "nonterminal symbols is null" ); //$NON-NLS-1$
    for ( NonterminalSymbol current : nonterminalSymbols )
      remove ( current );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#removeModifyStatusChangedListener(ModifyStatusChangedListener)
   */
  public final void removeModifyStatusChangedListener (
      ModifyStatusChangedListener listener )
  {
    this.listenerList.remove ( ModifyStatusChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see NonterminalSymbolSet#removeNonterminalSymbolSetChangedListener(NonterminalSymbolSetChangedListener)
   */
  public final void removeNonterminalSymbolSetChangedListener (
      NonterminalSymbolSetChangedListener listener )
  {
    this.listenerList.remove ( NonterminalSymbolSetChangedListener.class,
        listener );
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
   * @see Modifyable#resetModify()
   */
  public final void resetModify ()
  {
    this.initialTerminalSymbolSet.clear ();
    this.initialTerminalSymbolSet.addAll ( this.nonterminalSymbolSet );
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
   * @see NonterminalSymbolSet#size()
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
    if ( ( this.cachedPrettyString == null )
        || PrettyString.MODE.equals ( PrettyStringMode.CACHING_OFF ) )
    {
      this.cachedPrettyString = new PrettyString ();
      this.cachedPrettyString.add ( new PrettyToken ( "{", Style.NONE ) ); //$NON-NLS-1$
      Iterator < NonterminalSymbol > iterator = this.nonterminalSymbolSet
          .iterator ();
      boolean first = true;
      while ( iterator.hasNext () )
      {
        if ( !first )
          this.cachedPrettyString.add ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$
        first = false;
        this.cachedPrettyString.add ( iterator.next () );
      }
      this.cachedPrettyString.add ( new PrettyToken ( "}", Style.NONE ) ); //$NON-NLS-1$
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
    result.append ( "{" ); //$NON-NLS-1$
    Iterator < NonterminalSymbol > iterator = this.nonterminalSymbolSet
        .iterator ();
    boolean first = true;
    while ( iterator.hasNext () )
    {
      if ( !first )
        result.append ( ", " ); //$NON-NLS-1$
      first = false;
      result.append ( iterator.next () );
    }
    result.append ( "}" ); //$NON-NLS-1$
    return result.toString ();
  }


  /**
   * TODO
   * 
   * @param name
   * @return The symbol with the given name
   * @see de.unisiegen.gtitool.core.entities.NonterminalSymbolSet#get(java.lang.String)
   */
  public NonterminalSymbol get ( String name )
  {
    Iterator < NonterminalSymbol > iterator = this.nonterminalSymbolSet
        .iterator ();
    while ( iterator.hasNext () )
    {
      NonterminalSymbol next = iterator.next ();

      if ( next.toString ().equals ( name ) )
        return next;
    }
    return null;
  }
}
