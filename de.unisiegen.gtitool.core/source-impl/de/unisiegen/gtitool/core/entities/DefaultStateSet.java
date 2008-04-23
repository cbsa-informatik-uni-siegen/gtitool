package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import javax.swing.event.EventListenerList;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.entities.listener.StateSetChangedListener;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.stateset.StateSetException;
import de.unisiegen.gtitool.core.exceptions.stateset.StateSetMoreThanOneStateException;
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
   * Allocates a new {@link DefaultStateSet}.
   */
  public DefaultStateSet ()
  {
    // State
    this.stateSet = new ArrayList < State > ();
    this.initialStateSet = new TreeSet < State > ();

    // Reset modify
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
    // Check if the element is correct
    if ( !element.getName ().equals ( "StateSet" ) ) //$NON-NLS-1$
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

    // Reset modify
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
    // States
    if ( states == null )
    {
      throw new NullPointerException ( "states is null" ); //$NON-NLS-1$
    }
    add ( states );

    // Reset modify
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
    // States
    if ( states == null )
    {
      throw new NullPointerException ( "states is null" ); //$NON-NLS-1$
    }
    add ( states );

    // Reset modify
    resetModify ();
  }


  /**
   * Appends the specified {@link State}s to the end of this
   * {@link DefaultStateSet}.
   * 
   * @param states The {@link State}s to be appended to this
   *          {@link DefaultStateSet}.
   * @throws StateSetException If something with the {@link DefaultStateSet} is
   *           not correct.
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
   * Appends the specified {@link State} to the end of this
   * {@link DefaultStateSet}.
   * 
   * @param state The {@link State} to be appended to this
   *          {@link DefaultStateSet}.
   */
  public final void add ( State state )
  {
    // State
    if ( state == null )
    {
      throw new NullPointerException ( "state is null" ); //$NON-NLS-1$
    }
    this.stateSet.add ( state );
    fireStateSetChanged ();
    fireModifyStatusChanged ();
  }


  /**
   * Appends the specified {@link State}s to the end of this
   * {@link DefaultStateSet}.
   * 
   * @param states The {@link State}s to be appended to this
   *          {@link DefaultStateSet}.
   * @throws StateSetException If something with the {@link DefaultStateSet} is
   *           not correct.
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
   * Adds the given {@link StateSetChangedListener}.
   * 
   * @param listener The {@link StateSetChangedListener}.
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
   * Removes all {@link State}s.
   */
  public final void clear ()
  {
    this.stateSet.clear ();
    fireStateSetChanged ();
    fireModifyStatusChanged ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#clone()
   */
  @Override
  public final DefaultStateSet clone ()
  {
    DefaultStateSet newDefaultStateSet = new DefaultStateSet ();
    for ( State current : this.stateSet )
    {
      newDefaultStateSet.add ( current.clone () );
    }
    return newDefaultStateSet;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Comparable#compareTo( Object)
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
   * Returns true if this {@link DefaultStateSet} contains the specified
   * {@link State}.
   * 
   * @param state {@link State} whose presence in this {@link DefaultStateSet}
   *          is to be tested.
   * @return true if the specified {@link State} is present; false otherwise.
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
   * Returns the {@link State}s.
   * 
   * @return The {@link State}s.
   */
  public final ArrayList < State > get ()
  {
    return this.stateSet;
  }


  /**
   * Returns the {@link State} with the given index.
   * 
   * @param index The index.
   * @return The {@link State} with the given index.
   * @see #stateSet
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
   * Returns an iterator over the {@link State}s in this
   * {@link DefaultStateSet}.
   * 
   * @return An iterator over the {@link State}s in this
   *         {@link DefaultStateSet}.
   */
  public final Iterator < State > iterator ()
  {
    return this.stateSet.iterator ();
  }


  /**
   * Remove the given {@link State}s from this {@link DefaultStateSet}.
   * 
   * @param states The {@link State}s to remove.
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
   * Removes the given {@link State} from this {@link StateSet}.
   * 
   * @param state The {@link State} to remove.
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
    this.stateSet.remove ( state );
    fireStateSetChanged ();
    fireModifyStatusChanged ();
  }


  /**
   * Remove the given {@link State}s from this {@link StateSet}.
   * 
   * @param states The {@link State}s to remove.
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
   * Removes the given {@link StateSetChangedListener}.
   * 
   * @param listener The {@link StateSetChangedListener}.
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
   */
  public final void setParserOffset ( ParserOffset parserOffset )
  {
    this.parserOffset = parserOffset;
  }


  /**
   * Returns the number of {@link State}s in this {@link DefaultStateSet}.
   * 
   * @return The number of {@link State}s in this {@link DefaultStateSet}.
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
    PrettyString prettyString = new PrettyString ();
    Iterator < State > iterator = this.stateSet.iterator ();
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


  /**
   * {@inheritDoc}
   * 
   * @see Entity#toString()
   */
  public final String toStringDebug ()
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
