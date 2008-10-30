package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import javax.swing.event.EventListenerList;

import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.entities.listener.PrettyStringChangedListener;
import de.unisiegen.gtitool.core.entities.listener.StateSetChangedListener;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.stateset.StateSetException;
import de.unisiegen.gtitool.core.exceptions.stateset.StateSetMoreThanOneStateException;
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
 * The {@link DefaultStateSet} entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class DefaultStateSet implements StateSet
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 582050988169496803L;


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * The offset of this {@link DefaultStateSet} in the source code.
   * 
   * @see #getParserOffset()
   * @see #setParserOffset(ParserOffset)
   */
  private ParserOffset parserOffset = NO_PARSER_OFFSET;


  /**
   * The set of {@link State}s.
   */
  private ArrayList < State > stateSet;


  /**
   * The initial set of {@link State}s.
   */
  private TreeSet < State > initialStateSet;


  /**
   * The cached {@link PrettyString}.
   */
  private PrettyString cachedPrettyString = null;


  /**
   * The {@link PrettyStringChangedListener}.
   */
  private PrettyStringChangedListener prettyStringChangedListener;


  /**
   * Allocates a new {@link DefaultStateSet}.
   */
  public DefaultStateSet ()
  {
    this.prettyStringChangedListener = new PrettyStringChangedListener ()
    {

      public void prettyStringChanged ()
      {
        firePrettyStringChanged ();
      }
    };

    // state
    this.stateSet = new ArrayList < State > ();
    this.initialStateSet = new TreeSet < State > ();

    resetModify ();
  }


  /**
   * Allocates a new {@link DefaultStateSet}.
   * 
   * @param element The {@link Element}.
   * @throws StateException If something with the {@link Symbol} is not correct.
   * @throws StoreException If the {@link Element} can not be parsed.
   */
  public DefaultStateSet ( Element element ) throws StateException,
      StoreException
  {
    this ();

    // check if the element is correct
    if ( !element.getName ().equals ( "StateSet" ) ) //$NON-NLS-1$
    {
      throw new IllegalArgumentException (
          "element " + Messages.QUOTE + element.getName () //$NON-NLS-1$
              + Messages.QUOTE + " is not a nonterminal symbol set" ); //$NON-NLS-1$
    }

    // attribute
    if ( element.getAttribute ().size () > 0 )
    {
      throw new StoreException ( Messages
          .getString ( "StoreException.AdditionalAttribute" ) ); //$NON-NLS-1$
    }

    // element
    for ( Element current : element.getElement () )
    {
      if ( current.getName ().equals ( "State" ) ) //$NON-NLS-1$
      {
        add ( new DefaultState ( current ) );
      }
      else
      {
        throw new StoreException ( Messages
            .getString ( "StoreException.AdditionalElement" ) ); //$NON-NLS-1$
      }
    }

    resetModify ();
  }


  /**
   * Allocates a new {@link DefaultStateSet}.
   * 
   * @param states The array of {@link State}s.
   * @throws StateSetException If something with the {@link DefaultStateSet} is
   *           not correct.
   */
  public DefaultStateSet ( Iterable < State > states ) throws StateSetException
  {
    this ();

    // states
    if ( states == null )
    {
      throw new NullPointerException ( "states is null" ); //$NON-NLS-1$
    }
    add ( states );

    resetModify ();
  }


  /**
   * Allocates a new {@link DefaultStateSet}.
   * 
   * @param states The array of {@link State}s.
   * @throws StateSetException If something with the {@link DefaultStateSet} is
   *           not correct.
   */
  public DefaultStateSet ( State ... states ) throws StateSetException
  {
    this ();

    // states
    if ( states == null )
    {
      throw new NullPointerException ( "states is null" ); //$NON-NLS-1$
    }
    add ( states );

    resetModify ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateSet#add(Iterable)
   */
  public final void add ( Iterable < State > states ) throws StateSetException
  {
    if ( states == null )
    {
      throw new NullPointerException ( "states is null" ); //$NON-NLS-1$
    }
    ArrayList < State > symbolList = new ArrayList < State > ();
    for ( State current : states )
    {
      symbolList.add ( current );
    }
    checkDuplicated ( symbolList );
    for ( State current : states )
    {
      add ( current );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateSet#add(State)
   */
  public final void add ( State state )
  {
    // State
    if ( state == null )
    {
      throw new NullPointerException ( "state is null" ); //$NON-NLS-1$
    }

    state.addPrettyStringChangedListener ( this.prettyStringChangedListener );

    this.stateSet.add ( state );

    fireStateSetChanged ();
    fireModifyStatusChanged ();
    firePrettyStringChanged ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateSet#add(State[])
   */
  public final void add ( State ... states ) throws StateSetException
  {
    if ( states == null )
    {
      throw new NullPointerException ( "states is null" ); //$NON-NLS-1$
    }
    ArrayList < State > symbolList = new ArrayList < State > ();
    for ( State current : states )
    {
      symbolList.add ( current );
    }
    checkDuplicated ( symbolList );
    for ( State current : states )
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
   * @see PrettyPrintable#addPrettyStringChangedListener(PrettyStringChangedListener)
   */
  public final void addPrettyStringChangedListener (
      PrettyStringChangedListener listener )
  {
    this.listenerList.add ( PrettyStringChangedListener.class, listener );
  }


  /**
   *{@inheritDoc}
   * 
   * @see StateSet#addStateSetChangedListener(StateSetChangedListener)
   */
  public final void addStateSetChangedListener (
      StateSetChangedListener listener )
  {
    this.listenerList.add ( StateSetChangedListener.class, listener );
  }


  /**
   * Checks the {@link State} list for {@link State}s with the same name.
   * 
   * @param states The {@link State} list.
   * @throws StateSetException If a {@link State} is duplicated.
   */
  private final void checkDuplicated ( ArrayList < State > states )
      throws StateSetException
  {
    State duplicated = null;
    loop : for ( int i = 0 ; i < states.size () ; i++ )
    {
      for ( int j = i + 1 ; j < states.size () ; j++ )
      {
        if ( states.get ( i ).getName ().equals ( states.get ( j ).getName () ) )
        {
          duplicated = states.get ( i );
          break loop;
        }
      }
    }
    if ( duplicated != null )
    {
      ArrayList < State > negativeSymbols = new ArrayList < State > ();
      for ( State current : states )
      {
        if ( duplicated.getName ().equals ( current.getName () ) )
        {
          negativeSymbols.add ( current );
        }
      }
      throw new StateSetMoreThanOneStateException ( this, negativeSymbols );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see Comparable#compareTo(Object)
   */
  public final int compareTo ( StateSet other )
  {
    ArrayList < State > firstList = new ArrayList < State > ();
    ArrayList < State > secondList = new ArrayList < State > ();

    firstList.addAll ( this.stateSet );
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
   * @see StateSet#contains(State)
   */
  public final boolean contains ( State state )
  {
    return this.stateSet.contains ( state );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object other )
  {
    if ( other instanceof DefaultStateSet )
    {
      DefaultStateSet defaultStateSet = ( DefaultStateSet ) other;
      return this.stateSet.equals ( defaultStateSet.stateSet );
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
   * Let the listeners know that the {@link PrettyString} has changed.
   */
  protected final void firePrettyStringChanged ()
  {
    this.cachedPrettyString = null;

    PrettyStringChangedListener [] listeners = this.listenerList
        .getListeners ( PrettyStringChangedListener.class );
    for ( PrettyStringChangedListener current : listeners )
    {
      current.prettyStringChanged ();
    }
  }


  /**
   * Let the listeners know that the {@link StateSet} has changed.
   */
  private final void fireStateSetChanged ()
  {
    StateSetChangedListener [] listeners = this.listenerList
        .getListeners ( StateSetChangedListener.class );
    for ( StateSetChangedListener current : listeners )
    {
      current.stateSetChanged ( this );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateSet#get()
   */
  public final ArrayList < State > get ()
  {
    return this.stateSet;
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateSet#get(int)
   */
  public final State get ( int index )
  {
    Iterator < State > iterator = this.stateSet.iterator ();
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
    Element newElement = new Element ( "StateSet" ); //$NON-NLS-1$
    for ( State current : this.stateSet )
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
    return this.stateSet.hashCode ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#isModified()
   */
  public final boolean isModified ()
  {
    return ( !this.stateSet.equals ( this.initialStateSet ) );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Iterable#iterator()
   */
  public final Iterator < State > iterator ()
  {
    return this.stateSet.iterator ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateSet#remove(Iterable)
   */
  public final void remove ( Iterable < State > states )
  {
    if ( states == null )
    {
      throw new NullPointerException ( "states is null" ); //$NON-NLS-1$
    }
    for ( State current : states )
    {
      remove ( current );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateSet#remove(State)
   */
  public final void remove ( State state )
  {
    if ( state == null )
    {
      throw new NullPointerException ( "state is null" ); //$NON-NLS-1$
    }
    if ( !this.stateSet.contains ( state ) )
    {
      throw new IllegalArgumentException ( "state is not in this state set" ); //$NON-NLS-1$
    }

    state.removePrettyStringChangedListener ( this.prettyStringChangedListener );

    this.stateSet.remove ( state );

    fireStateSetChanged ();
    fireModifyStatusChanged ();
    firePrettyStringChanged ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see StateSet#remove(State[])
   */
  public final void remove ( State ... states )
  {
    if ( states == null )
    {
      throw new NullPointerException ( "states is null" ); //$NON-NLS-1$
    }
    for ( State current : states )
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
   * @see StateSet#removeStateSetChangedListener(StateSetChangedListener)
   */
  public final void removeStateSetChangedListener (
      StateSetChangedListener listener )
  {
    this.listenerList.remove ( StateSetChangedListener.class, listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#resetModify()
   */
  public final void resetModify ()
  {
    this.initialStateSet.clear ();
    this.initialStateSet.addAll ( this.stateSet );
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
   * @see StateSet#size()
   */
  public final int size ()
  {
    return this.stateSet.size ();
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
      Iterator < State > iterator = this.stateSet.iterator ();
      boolean first = true;
      while ( iterator.hasNext () )
      {
        if ( !first )
        {
          this.cachedPrettyString.add ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$
        }
        first = false;
        this.cachedPrettyString.add ( iterator.next () );
      }
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
    Iterator < State > iterator = this.stateSet.iterator ();
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
    return result.toString ();
  }
}
