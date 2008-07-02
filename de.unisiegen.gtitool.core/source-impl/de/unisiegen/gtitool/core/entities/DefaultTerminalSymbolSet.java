package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import javax.swing.event.EventListenerList;

import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.entities.listener.TerminalSymbolSetChangedListener;
import de.unisiegen.gtitool.core.exceptions.terminalsymbol.TerminalSymbolException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetMoreThanOneSymbolException;
import de.unisiegen.gtitool.core.i18n.Messages;
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
 * The {@link DefaultTerminalSymbolSet} entity.
 * 
 * @author Christian Fehler
 * @version $Id: DefaultTerminalSymbolSet.java 1043 2008-06-27 00:09:58Z fehler
 *          $
 */
public final class DefaultTerminalSymbolSet implements TerminalSymbolSet
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 2256414069656309793L;


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * The offset of this {@link DefaultTerminalSymbolSet} in the source code.
   * 
   * @see #getParserOffset()
   * @see #setParserOffset(ParserOffset)
   */
  private ParserOffset parserOffset = NO_PARSER_OFFSET;


  /**
   * The set of {@link TerminalSymbol}s.
   */
  private TreeSet < TerminalSymbol > terminalSymbolSet;


  /**
   * The initial set of {@link TerminalSymbol}s.
   */
  private TreeSet < TerminalSymbol > initialTerminalSymbolSet;


  /**
   * Allocates a new {@link DefaultTerminalSymbolSet}.
   */
  public DefaultTerminalSymbolSet ()
  {
    // TerminalSymbol
    this.terminalSymbolSet = new TreeSet < TerminalSymbol > ();
    this.initialTerminalSymbolSet = new TreeSet < TerminalSymbol > ();

    // Reset modify
    resetModify ();
  }


  /**
   * Allocates a new {@link DefaultTerminalSymbolSet}.
   * 
   * @param element The {@link Element}.
   * @throws TerminalSymbolSetException If something with the
   *           {@link DefaultTerminalSymbolSet} is not correct.
   * @throws TerminalSymbolException If something with the
   *           {@link TerminalSymbol} is not correct.
   * @throws StoreException If the {@link Element} can not be parsed.
   */
  public DefaultTerminalSymbolSet ( Element element )
      throws TerminalSymbolSetException, TerminalSymbolException,
      StoreException
  {
    this ();
    // Check if the element is correct
    if ( !element.getName ().equals ( "TerminalSymbolSet" ) ) //$NON-NLS-1$
    {
      throw new IllegalArgumentException (
          "element " + Messages.QUOTE + element.getName () //$NON-NLS-1$
              + Messages.QUOTE + " is not a terminal symbol set" ); //$NON-NLS-1$
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
      if ( current.getName ().equals ( "TerminalSymbol" ) ) //$NON-NLS-1$
      {
        add ( new DefaultTerminalSymbol ( current ) );
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
   * Allocates a new {@link DefaultTerminalSymbolSet}.
   * 
   * @param terminalSymbols The array of {@link TerminalSymbol}s.
   * @throws TerminalSymbolSetException If something with the
   *           {@link DefaultTerminalSymbolSet} is not correct.
   */
  public DefaultTerminalSymbolSet ( Iterable < TerminalSymbol > terminalSymbols )
      throws TerminalSymbolSetException
  {
    this ();
    // TerminalSymbols
    if ( terminalSymbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    add ( terminalSymbols );

    // Reset modify
    resetModify ();
  }


  /**
   * Allocates a new {@link DefaultTerminalSymbolSet}.
   * 
   * @param terminalSymbols The array of {@link TerminalSymbol}s.
   * @throws TerminalSymbolSetException If something with the
   *           {@link DefaultTerminalSymbolSet} is not correct.
   */
  public DefaultTerminalSymbolSet ( TerminalSymbol ... terminalSymbols )
      throws TerminalSymbolSetException
  {
    this ();
    // TerminalSymbols
    if ( terminalSymbols == null )
    {
      throw new NullPointerException ( "symbols is null" ); //$NON-NLS-1$
    }
    add ( terminalSymbols );

    // Reset modify
    resetModify ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see TerminalSymbolSet#add(java.lang.Iterable)
   */
  public final void add ( Iterable < TerminalSymbol > terminalSymbols )
      throws TerminalSymbolSetException
  {
    if ( terminalSymbols == null )
    {
      throw new NullPointerException ( "terminal symbols is null" ); //$NON-NLS-1$
    }
    ArrayList < TerminalSymbol > symbolList = new ArrayList < TerminalSymbol > ();
    for ( TerminalSymbol current : terminalSymbols )
    {
      symbolList.add ( current );
    }
    checkDuplicated ( symbolList );
    for ( TerminalSymbol current : terminalSymbols )
    {
      add ( current );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see TerminalSymbolSet#add(TerminalSymbol)
   */
  public final void add ( TerminalSymbol terminalSymbol )
      throws TerminalSymbolSetException
  {
    // TerminalSymbol
    if ( terminalSymbol == null )
    {
      throw new NullPointerException ( "terminal symbol is null" ); //$NON-NLS-1$
    }
    /*
     * Throws an TerminalSymbolSetException if the symbol which should be added
     * is already in this TerminalSymbolSet.
     */
    if ( this.terminalSymbolSet.contains ( terminalSymbol ) )
    {
      ArrayList < TerminalSymbol > negativeSymbols = new ArrayList < TerminalSymbol > ();
      for ( TerminalSymbol current : this.terminalSymbolSet )
      {
        if ( terminalSymbol.equals ( current ) )
        {
          negativeSymbols.add ( current );
        }
      }
      negativeSymbols.add ( terminalSymbol );
      throw new TerminalSymbolSetMoreThanOneSymbolException ( this,
          negativeSymbols );
    }
    this.terminalSymbolSet.add ( terminalSymbol );
    fireTerminalSymbolSetChanged ();
    fireModifyStatusChanged ();
  }


  /**
   *{@inheritDoc}
   * 
   * @see TerminalSymbolSet#add(TerminalSymbol[])
   */
  public final void add ( TerminalSymbol ... terminalSymbols )
      throws TerminalSymbolSetException
  {
    if ( terminalSymbols == null )
    {
      throw new NullPointerException ( "terminal symbols is null" ); //$NON-NLS-1$
    }
    ArrayList < TerminalSymbol > symbolList = new ArrayList < TerminalSymbol > ();
    for ( TerminalSymbol current : terminalSymbols )
    {
      symbolList.add ( current );
    }
    checkDuplicated ( symbolList );
    for ( TerminalSymbol current : terminalSymbols )
    {
      add ( current );
    }
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
   * @see TerminalSymbolSet#addTerminalSymbolSetChangedListener(TerminalSymbolSetChangedListener)
   */
  public final void addTerminalSymbolSetChangedListener (
      TerminalSymbolSetChangedListener listener )
  {
    this.listenerList.add ( TerminalSymbolSetChangedListener.class, listener );
  }


  /**
   * Checks the {@link TerminalSymbol} list for {@link TerminalSymbol}s with the
   * same name.
   * 
   * @param terminalSymbols The {@link TerminalSymbol} list.
   * @throws TerminalSymbolSetException If a {@link TerminalSymbol} is
   *           duplicated.
   */
  private final void checkDuplicated (
      ArrayList < TerminalSymbol > terminalSymbols )
      throws TerminalSymbolSetException
  {
    TerminalSymbol duplicated = null;
    loop : for ( int i = 0 ; i < terminalSymbols.size () ; i++ )
    {
      for ( int j = i + 1 ; j < terminalSymbols.size () ; j++ )
      {
        if ( terminalSymbols.get ( i ).equals ( terminalSymbols.get ( j ) ) )
        {
          duplicated = terminalSymbols.get ( i );
          break loop;
        }
      }
    }
    if ( duplicated != null )
    {
      ArrayList < TerminalSymbol > negativeSymbols = new ArrayList < TerminalSymbol > ();
      for ( TerminalSymbol current : terminalSymbols )
      {
        if ( duplicated.equals ( current ) )
        {
          negativeSymbols.add ( current );
        }
      }
      throw new TerminalSymbolSetMoreThanOneSymbolException ( this,
          negativeSymbols );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see TerminalSymbolSet#clear()
   */
  public final void clear ()
  {
    this.terminalSymbolSet.clear ();
    fireTerminalSymbolSetChanged ();
    fireModifyStatusChanged ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Comparable#compareTo(Object)
   */
  public final int compareTo ( TerminalSymbolSet other )
  {
    ArrayList < TerminalSymbol > firstList = new ArrayList < TerminalSymbol > ();
    ArrayList < TerminalSymbol > secondList = new ArrayList < TerminalSymbol > ();

    firstList.addAll ( this.terminalSymbolSet );
    secondList.addAll ( other.get () );

    int minSize = firstList.size () < secondList.size () ? firstList.size ()
        : secondList.size ();

    for ( int i = 0 ; i < minSize ; i++ )
    {
      int compare = firstList.get ( i ).compareTo ( secondList.get ( i ) );
      if ( compare != 0 )
      {
        return compare;
      }
    }

    if ( firstList.size () == secondList.size () )
    {
      return 0;
    }

    return firstList.size () < secondList.size () ? -1 : 1;
  }


  /**
   * {@inheritDoc}
   * 
   * @see TerminalSymbolSet#contains(TerminalSymbol)
   */
  public final boolean contains ( TerminalSymbol terminalSymbol )
  {
    return this.terminalSymbolSet.contains ( terminalSymbol );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object other )
  {
    if ( other instanceof DefaultTerminalSymbolSet )
    {
      DefaultTerminalSymbolSet defaultTerminalSymbolSet = ( DefaultTerminalSymbolSet ) other;
      return this.terminalSymbolSet
          .equals ( defaultTerminalSymbolSet.terminalSymbolSet );
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
    {
      current.modifyStatusChanged ( newModifyStatus );
    }
  }


  /**
   * Let the listeners know that the {@link TerminalSymbolSet} has changed.
   */
  private final void fireTerminalSymbolSetChanged ()
  {
    TerminalSymbolSetChangedListener [] listeners = this.listenerList
        .getListeners ( TerminalSymbolSetChangedListener.class );
    for ( TerminalSymbolSetChangedListener current : listeners )
    {
      current.terminalSymbolSetChanged ( this );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see TerminalSymbolSet#get()
   */
  public final TreeSet < TerminalSymbol > get ()
  {
    return this.terminalSymbolSet;
  }


  /**
   * {@inheritDoc}
   * 
   * @see TerminalSymbolSet#get(int)
   */
  public final TerminalSymbol get ( int index )
  {
    Iterator < TerminalSymbol > iterator = this.terminalSymbolSet.iterator ();
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
    Element newElement = new Element ( "TerminalSymbolSet" ); //$NON-NLS-1$
    for ( TerminalSymbol current : this.terminalSymbolSet )
    {
      newElement.addElement ( current );
    }
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
    return this.terminalSymbolSet.hashCode ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#isModified()
   */
  public final boolean isModified ()
  {
    return ( !this.terminalSymbolSet.equals ( this.initialTerminalSymbolSet ) );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Iterable#iterator()
   */
  public final Iterator < TerminalSymbol > iterator ()
  {
    return this.terminalSymbolSet.iterator ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see TerminalSymbolSet#remove(Iterable)
   */
  public final void remove ( Iterable < TerminalSymbol > terminalSymbols )
  {
    if ( terminalSymbols == null )
    {
      throw new NullPointerException ( "terminal symbols is null" ); //$NON-NLS-1$
    }
    for ( TerminalSymbol current : terminalSymbols )
    {
      remove ( current );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see TerminalSymbolSet#remove(TerminalSymbol)
   */
  public final void remove ( TerminalSymbol terminalSymbol )
  {
    if ( terminalSymbol == null )
    {
      throw new NullPointerException ( "terminal symbol is null" ); //$NON-NLS-1$
    }
    if ( !this.terminalSymbolSet.contains ( terminalSymbol ) )
    {
      throw new IllegalArgumentException (
          "terminal symbol is not in this terminal symbol set" ); //$NON-NLS-1$
    }
    this.terminalSymbolSet.remove ( terminalSymbol );
    fireTerminalSymbolSetChanged ();
    fireModifyStatusChanged ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see TerminalSymbolSet#remove(TerminalSymbol[])
   */
  public final void remove ( TerminalSymbol ... terminalSymbols )
  {
    if ( terminalSymbols == null )
    {
      throw new NullPointerException ( "terminal symbols is null" ); //$NON-NLS-1$
    }
    for ( TerminalSymbol current : terminalSymbols )
    {
      remove ( current );
    }
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
   * @see TerminalSymbolSet#removeTerminalSymbolSetChangedListener(TerminalSymbolSetChangedListener)
   */
  public final void removeTerminalSymbolSetChangedListener (
      TerminalSymbolSetChangedListener listener )
  {
    this.listenerList
        .remove ( TerminalSymbolSetChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#resetModify()
   */
  public final void resetModify ()
  {
    this.initialTerminalSymbolSet.clear ();
    this.initialTerminalSymbolSet.addAll ( this.terminalSymbolSet );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#setParserOffset(de.unisiegen.gtitool.core.parser.ParserOffset)
   */
  public final void setParserOffset ( ParserOffset parserOffset )
  {
    this.parserOffset = parserOffset;
  }


  /**
   * {@inheritDoc}
   * 
   * @see TerminalSymbolSet#size()
   */
  public final int size ()
  {
    return this.terminalSymbolSet.size ();
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
    Iterator < TerminalSymbol > iterator = this.terminalSymbolSet.iterator ();
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
    Iterator < TerminalSymbol > iterator = this.terminalSymbolSet.iterator ();
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
    Iterator < TerminalSymbol > iterator = this.terminalSymbolSet.iterator ();
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
