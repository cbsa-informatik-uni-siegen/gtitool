package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.entities.listener.StateChangedListener;
import de.unisiegen.gtitool.core.exceptions.state.StateEmptyNameException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.state.StateIllegalNameException;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.parser.ParserOffset;
import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.storage.Attribute;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;


/**
 * The {@link DefaultState} entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class DefaultState implements State
{

  /**
   * The serial verion uid.
   */
  private static final long serialVersionUID = -1156609272782310462L;


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * The {@link Alphabet} of this {@link DefaultState}.
   */
  private Alphabet alphabet = null;


  /**
   * The push down {@link Alphabet} of this {@link DefaultState}.
   */
  private Alphabet pushDownAlphabet = null;


  /**
   * Flag that indicates if the default name can be set.
   */
  private boolean canSetDefaultName;


  /**
   * This {@link DefaultState} is a final {@link DefaultState}.
   */
  private boolean finalState = false;


  /**
   * The initial final {@link DefaultState}.
   */
  private boolean initialFinalState = false;


  /**
   * The id of this {@link DefaultState}.
   */
  private int id = ID_NOT_DEFINED;


  /**
   * The name of this {@link DefaultState}.
   */
  private String name;


  /**
   * The initial name of this {@link DefaultState}.
   */
  private String initialName = null;


  /**
   * The offset of this {@link DefaultState} in the source code.
   * 
   * @see #getParserOffset()
   * @see #setParserOffset(ParserOffset)
   */
  private ParserOffset parserOffset = NO_PARSER_OFFSET;


  /**
   * This {@link DefaultState} is a start {@link DefaultState}.
   */
  private boolean startState = false;


  /**
   * The initial start {@link DefaultState}.
   */
  private boolean initialStartState = false;


  /**
   * The list of {@link Transition}s, which begin in this {@link DefaultState}.
   */
  private ArrayList < Transition > transitionBeginList;


  /**
   * The list of {@link Transition} ids, which begin in this
   * {@link DefaultState}.
   */
  private ArrayList < Integer > transitionBeginIdList;


  /**
   * The list of {@link Transition}s, which end in this {@link DefaultState}.
   */
  private ArrayList < Transition > transitionEndList;


  /**
   * The list of {@link Transition} ids, which end in this {@link DefaultState}.
   */
  private ArrayList < Integer > transitionEndIdList;


  /**
   * The old modify status.
   */
  private boolean oldModifyStatus = false;


  /**
   * Allocates a new {@link DefaultState}.
   * 
   * @param alphabet The {@link Alphabet} of this {@link DefaultState}.
   * @param pushDownAlphabet The push down {@link Alphabet} of this
   *          {@link DefaultState}.
   * @param startState This {@link DefaultState} is a start {@link DefaultState}.
   * @param finalState This {@link DefaultState} is a final {@link DefaultState}.
   * @throws StateException If something with the {@link DefaultState} is not
   *           correct.
   */
  public DefaultState ( Alphabet alphabet, Alphabet pushDownAlphabet,
      boolean startState, boolean finalState ) throws StateException
  {
    this ( alphabet, pushDownAlphabet, "DEFAULTNAME", startState, finalState ); //$NON-NLS-1$
    this.canSetDefaultName = true;

    // Reset modify
    resetModify ();
  }


  /**
   * Allocates a new {@link DefaultState}.
   * 
   * @param alphabet The {@link Alphabet} of this {@link DefaultState}.
   * @param pushDownAlphabet The push down {@link Alphabet} of this
   *          {@link DefaultState}.
   * @param name The name of this {@link DefaultState}.
   * @param startState This {@link DefaultState} is a start {@link DefaultState}.
   * @param finalState This {@link DefaultState} is a final {@link DefaultState}.
   * @throws StateException If something with the {@link DefaultState} is not
   *           correct.
   */
  public DefaultState ( Alphabet alphabet, Alphabet pushDownAlphabet,
      String name, boolean startState, boolean finalState )
      throws StateException
  {
    this ( name );
    // Alphabet
    setAlphabet ( alphabet );
    // PushDownAlphabet
    setPushDownAlphabet ( pushDownAlphabet );
    // StartState
    setStartState ( startState );
    // FinalState
    setFinalState ( finalState );

    // Reset modify
    resetModify ();
  }


  /**
   * Allocates a new {@link DefaultState}.
   * 
   * @param element The {@link Element}.
   * @throws StateException If something with the {@link DefaultState} is not
   *           correct.
   * @throws StoreException If the {@link Element} can not be parsed.
   */
  public DefaultState ( Element element ) throws StateException, StoreException
  {
    // Check if the element is correct
    if ( !element.getName ().equals ( "State" ) ) //$NON-NLS-1$
    {
      throw new IllegalArgumentException ( "element \"" + element.getName () //$NON-NLS-1$
          + "\" is not a state" ); //$NON-NLS-1$
    }

    // TransitionBegin
    this.transitionBeginList = new ArrayList < Transition > ();
    this.transitionBeginIdList = new ArrayList < Integer > ();
    // TransitionEnd
    this.transitionEndList = new ArrayList < Transition > ();
    this.transitionEndIdList = new ArrayList < Integer > ();

    // CanSetDefaultName
    this.canSetDefaultName = false;

    // Attribute
    boolean foundId = false;
    boolean foundName = false;
    boolean foundStartState = false;
    boolean foundFinalState = false;
    for ( Attribute current : element.getAttribute () )
    {
      if ( current.getName ().equals ( "id" ) ) //$NON-NLS-1$
      {
        setId ( current.getValueInt () );
        foundId = true;
      }
      else if ( current.getName ().equals ( "name" ) ) //$NON-NLS-1$
      {
        setName ( current.getValue () );
        foundName = true;
      }
      else if ( current.getName ().equals ( "startState" ) ) //$NON-NLS-1$
      {
        setStartState ( current.getValueBoolean () );
        foundStartState = true;
      }
      else if ( current.getName ().equals ( "finalState" ) ) //$NON-NLS-1$
      {
        setFinalState ( current.getValueBoolean () );
        foundFinalState = true;
      }
      else
      {
        throw new StoreException ( Messages
            .getString ( "StoreException.AdditionalAttribute" ) ); //$NON-NLS-1$
      }
    }

    // Not all attribute values found
    if ( ( !foundId ) || ( !foundName ) || ( !foundStartState )
        || ( !foundFinalState ) )
    {
      throw new StoreException ( Messages
          .getString ( "StoreException.MissingAttribute" ) ); //$NON-NLS-1$
    }

    // Element
    for ( Element current : element.getElement () )
    {
      if ( current.getName ().equals ( "TransitionBegin" ) ) //$NON-NLS-1$
      {
        boolean foundTransitionBeginId = false;
        for ( Attribute currentAttribute : current.getAttribute () )
        {
          if ( currentAttribute.getName ().equals ( "id" ) ) //$NON-NLS-1$
          {
            this.transitionBeginIdList.add ( new Integer ( currentAttribute
                .getValueInt () ) );
            foundTransitionBeginId = true;
          }
          else
          {
            throw new StoreException ( Messages
                .getString ( "StoreException.AdditionalAttribute" ) ); //$NON-NLS-1$
          }
        }

        // Not all attribute values found
        if ( !foundTransitionBeginId )
        {
          throw new StoreException ( Messages
              .getString ( "StoreException.MissingAttribute" ) ); //$NON-NLS-1$
        }
      }
      else if ( current.getName ().equals ( "TransitionEnd" ) ) //$NON-NLS-1$
      {
        boolean foundTransitionEndId = false;
        for ( Attribute currentAttribute : current.getAttribute () )
        {
          if ( currentAttribute.getName ().equals ( "id" ) ) //$NON-NLS-1$
          {
            this.transitionEndIdList.add ( new Integer ( currentAttribute
                .getValueInt () ) );
            foundTransitionEndId = true;
          }
          else
          {
            throw new StoreException ( Messages
                .getString ( "StoreException.AdditionalAttribute" ) ); //$NON-NLS-1$
          }
        }

        // Not all attribute values found
        if ( !foundTransitionEndId )
        {
          throw new StoreException ( Messages
              .getString ( "StoreException.MissingAttribute" ) ); //$NON-NLS-1$
        }
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
   * Allocates a new {@link DefaultState}.
   * 
   * @param name The name of this {@link DefaultState}.
   * @throws StateException If something with the {@link DefaultState} is not
   *           correct.
   */
  public DefaultState ( String name ) throws StateException
  {
    // Name
    setName ( name );

    // TransitionBegin
    this.transitionBeginList = new ArrayList < Transition > ();
    this.transitionBeginIdList = new ArrayList < Integer > ();

    // TransitionEnd
    this.transitionEndList = new ArrayList < Transition > ();
    this.transitionEndIdList = new ArrayList < Integer > ();

    // DefaultName
    this.canSetDefaultName = false;

    // Reset modify
    resetModify ();
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
   * Adds the given {@link StateChangedListener}.
   * 
   * @param listener The {@link StateChangedListener}.
   */
  public final synchronized void addStateChangedListener (
      StateChangedListener listener )
  {
    this.listenerList.add ( StateChangedListener.class, listener );
  }


  /**
   * Adds the {@link Transition} to the list of {@link Transition}s, which
   * begin in this {@link DefaultState}.
   * 
   * @param transition The {@link Transition} to add.
   */
  public final void addTransitionBegin ( Transition transition )
  {
    if ( transition == null )
    {
      throw new NullPointerException ( "transition is null" ); //$NON-NLS-1$
    }
    if ( this.transitionBeginList.contains ( transition ) )
    {
      throw new IllegalArgumentException (
          "transition begins already in this state" ); //$NON-NLS-1$
    }
    if ( transition.getStateBegin () != this )
    {
      throw new IllegalArgumentException (
          "transition begins not in this state" ); //$NON-NLS-1$
    }
    this.transitionBeginList.add ( transition );
  }


  /**
   * Adds the {@link Transition} to the list of {@link Transition}s, which end
   * in this {@link DefaultState}.
   * 
   * @param transition The {@link Transition} to add.
   */
  public final void addTransitionEnd ( Transition transition )
  {
    if ( transition == null )
    {
      throw new NullPointerException ( "transition is null" ); //$NON-NLS-1$
    }
    if ( this.transitionEndList.contains ( transition ) )
    {
      throw new IllegalArgumentException (
          "transition ends already in this state" ); //$NON-NLS-1$
    }
    if ( transition.getStateEnd () != this )
    {
      throw new IllegalArgumentException ( "transition ends not in this state" ); //$NON-NLS-1$
    }
    this.transitionEndList.add ( transition );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#clone()
   */
  @Override
  public final DefaultState clone ()
  {
    try
    {
      DefaultState newDefaultState = new DefaultState ( this.alphabet.clone (),
          this.pushDownAlphabet.clone (), this.name, this.startState,
          this.finalState );
      for ( Transition current : this.transitionBeginList )
      {
        newDefaultState.addTransitionBegin ( current.clone () );
      }
      for ( Transition current : this.transitionEndList )
      {
        newDefaultState.addTransitionEnd ( current.clone () );
      }
      return newDefaultState;
    }
    catch ( StateException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
      return null;
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see Comparable#compareTo(Object)
   */
  public final int compareTo ( State other )
  {
    return this.id < other.getId () ? -1 : ( this.id > other.getId () ? 1 : 0 );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object other )
  {
    if ( other instanceof DefaultState )
    {
      DefaultState defaultState = ( DefaultState ) other;
      if ( ( this.id == ID_NOT_DEFINED )
          || ( defaultState.id == ID_NOT_DEFINED ) )
      {
        throw new IllegalArgumentException ( "id is not defined" ); //$NON-NLS-1$
      }
      return this.id == defaultState.id;
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
   * Let the listeners know that the {@link State} has changed.
   */
  private final void fireStateChanged ()
  {
    StateChangedListener [] listeners = this.listenerList
        .getListeners ( StateChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].stateChanged ( this );
    }
  }


  /**
   * Returns the {@link Alphabet}.
   * 
   * @return The {@link Alphabet}.
   * @see #alphabet
   */
  public final Alphabet getAlphabet ()
  {
    return this.alphabet;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Storable#getElement()
   */
  public final Element getElement ()
  {
    Element newElement = new Element ( "State" ); //$NON-NLS-1$
    newElement.addAttribute ( new Attribute ( "id", this.id ) ); //$NON-NLS-1$
    newElement.addAttribute ( new Attribute ( "name", this.name ) ); //$NON-NLS-1$
    newElement.addAttribute ( new Attribute ( "startState", this.startState ) ); //$NON-NLS-1$
    newElement.addAttribute ( new Attribute ( "finalState", this.finalState ) ); //$NON-NLS-1$
    for ( Transition current : this.transitionBeginList )
    {
      Element currentElement = new Element ( "TransitionBegin" ); //$NON-NLS-1$
      currentElement.addAttribute ( new Attribute ( "id", current.getId () ) ); //$NON-NLS-1$
      newElement.addElement ( currentElement );
    }
    for ( Transition current : this.transitionEndList )
    {
      Element currentElement = new Element ( "TransitionEnd" ); //$NON-NLS-1$
      currentElement.addAttribute ( new Attribute ( "id", current.getId () ) ); //$NON-NLS-1$
      newElement.addElement ( currentElement );
    }
    return newElement;
  }


  /**
   * Returns the id.
   * 
   * @return The id.
   * @see #id
   */
  public final int getId ()
  {
    return this.id;
  }


  /**
   * Returns the name.
   * 
   * @return The name.
   * @see #name
   */
  public final String getName ()
  {
    return this.name;
  }


  /**
   * {@inheritDoc}
   */
  public final ParserOffset getParserOffset ()
  {
    return this.parserOffset;
  }


  /**
   * Returns the push down {@link Alphabet}.
   * 
   * @return The push down {@link Alphabet}.
   * @see #pushDownAlphabet
   */
  public final Alphabet getPushDownAlphabet ()
  {
    return this.pushDownAlphabet;
  }


  /**
   * Returns the {@link Transition} begin list.
   * 
   * @return The {@link Transition} begin list.
   */
  public final ArrayList < Transition > getTransitionBegin ()
  {
    return this.transitionBeginList;
  }


  /**
   * Returns the {@link Transition} at the specified position in the
   * {@link Transition} begin list.
   * 
   * @param index The index of the {@link Transition} to return.
   * @return The {@link Transition} at the specified position in the
   *         {@link Transition} begin list.
   */
  public final Transition getTransitionBegin ( int index )
  {
    return this.transitionBeginList.get ( index );
  }


  /**
   * Returns the {@link Transition} id begin list.
   * 
   * @return The {@link Transition} id begin list.
   */
  public final ArrayList < Integer > getTransitionBeginId ()
  {
    return this.transitionBeginIdList;
  }


  /**
   * Returns the {@link Transition} id at the specified position in the
   * {@link Transition} begin list.
   * 
   * @param index The index of the {@link Transition} id to return.
   * @return The {@link Transition} at the specified position in the
   *         {@link Transition} id begin list.
   */
  public final int getTransitionBeginId ( int index )
  {
    return this.transitionBeginIdList.get ( index ).intValue ();
  }


  /**
   * Returns the {@link Transition} end list.
   * 
   * @return The {@link Transition} end list.
   */
  public final ArrayList < Transition > getTransitionEnd ()
  {
    return this.transitionEndList;
  }


  /**
   * Returns the {@link Transition} at the specified position in the
   * {@link Transition} end list.
   * 
   * @param index The index of the {@link Transition} to return.
   * @return The {@link Transition} at the specified position in the
   *         {@link Transition} end list.
   */
  public final Transition getTransitionEnd ( int index )
  {
    return this.transitionEndList.get ( index );
  }


  /**
   * Returns the {@link Transition} id end list.
   * 
   * @return The {@link Transition} id end list.
   */
  public final ArrayList < Integer > getTransitionEndId ()
  {
    return this.transitionEndIdList;
  }


  /**
   * Returns the {@link Transition} id at the specified position in the
   * {@link Transition} end list.
   * 
   * @param index The index of the {@link Transition} id to return.
   * @return The {@link Transition} at the specified position in the
   *         {@link Transition} id end list.
   */
  public final int getTransitionEndId ( int index )
  {
    return this.transitionEndIdList.get ( index ).intValue ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Entity#hashCode()
   */
  @Override
  public final int hashCode ()
  {
    if ( this.id == ID_NOT_DEFINED )
    {
      throw new IllegalArgumentException ( "id is not defined" ); //$NON-NLS-1$
    }
    return this.id;
  }


  /**
   * Returns the finalState.
   * 
   * @return The finalState.
   * @see #finalState
   */
  public final boolean isFinalState ()
  {
    return this.finalState;
  }


  /**
   * Returns true if the id of this {@link DefaultState} is defined, otherwise
   * false.
   * 
   * @return True if the id of this {@link DefaultState} is defined, otherwise
   *         false.
   */
  public final boolean isIdDefined ()
  {
    return this.id != ID_NOT_DEFINED;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#isModified()
   */
  public final boolean isModified ()
  {
    if ( this.name == null )
    {
      return false;
    }
    return ( !this.name.equals ( this.initialName ) )
        || ( this.startState != this.initialStartState )
        || ( this.finalState != this.initialFinalState );
  }


  /**
   * Returns the startState.
   * 
   * @return The startState.
   * @see #startState
   */
  public final boolean isStartState ()
  {
    return this.startState;
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
   * Removes the given {@link StateChangedListener}.
   * 
   * @param listener The {@link StateChangedListener}.
   */
  public final synchronized void removeStateChangedListener (
      StateChangedListener listener )
  {
    this.listenerList.remove ( StateChangedListener.class, listener );
  }


  /**
   * Removes the {@link Transition} from the list of {@link Transition}s, which
   * begin in this {@link DefaultState}.
   * 
   * @param transition The {@link Transition} to remove.
   */
  public final void removeTransitionBegin ( Transition transition )
  {
    if ( transition == null )
    {
      throw new NullPointerException ( "transition is null" ); //$NON-NLS-1$
    }
    if ( !this.transitionBeginList.contains ( transition ) )
    {
      throw new IllegalArgumentException (
          "transition begins not in this state" ); //$NON-NLS-1$
    }
    this.transitionBeginList.remove ( transition );
  }


  /**
   * Removes the {@link Transition} from the list of {@link Transition}s, which
   * end in this {@link DefaultState}.
   * 
   * @param transition The {@link Transition} to remove.
   */
  public final void removeTransitionEnd ( Transition transition )
  {
    if ( transition == null )
    {
      throw new NullPointerException ( "transition is null" ); //$NON-NLS-1$
    }
    if ( !this.transitionEndList.contains ( transition ) )
    {
      throw new IllegalArgumentException ( "transition ends not in this state" ); //$NON-NLS-1$
    }
    this.transitionEndList.remove ( transition );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Modifyable#resetModify()
   */
  public final void resetModify ()
  {
    this.initialName = this.name;
    this.initialStartState = this.startState;
    this.initialFinalState = this.finalState;
    this.oldModifyStatus = false;
  }


  /**
   * Sets the {@link Alphabet} of this {@link DefaultState}.
   * 
   * @param alphabet The {@link Alphabet} to set.
   */
  public final void setAlphabet ( Alphabet alphabet )
  {
    if ( alphabet == null )
    {
      throw new NullPointerException ( "alphabet is null" ); //$NON-NLS-1$
    }
    if ( this.alphabet != null )
    {
      throw new IllegalArgumentException ( "alphabet is already set" ); //$NON-NLS-1$
    }
    this.alphabet = alphabet;
  }


  /**
   * Sets the default name of this {@link DefaultState}.
   */
  public final void setDefaultName ()
  {
    if ( this.canSetDefaultName )
    {
      if ( this.id == ID_NOT_DEFINED )
      {
        throw new IllegalArgumentException ( "id is not defined" ); //$NON-NLS-1$ 
      }
      try
      {
        setName ( "z" + this.id ); //$NON-NLS-1$
      }
      catch ( StateException exc )
      {
        exc.printStackTrace ();
        System.exit ( 1 );
      }
    }
  }


  /**
   * Sets the finalState value.
   * 
   * @param finalState The finalState to set.
   */
  public final void setFinalState ( boolean finalState )
  {
    this.finalState = finalState;
    fireModifyStatusChanged ();
  }


  /**
   * Sets the id.
   * 
   * @param id The id to set.
   * @see #id
   */
  public final void setId ( int id )
  {
    if ( this.id != ID_NOT_DEFINED )
    {
      throw new IllegalArgumentException ( "id is already setted" ); //$NON-NLS-1$
    }
    this.id = id;
  }


  /**
   * Sets the name of this {@link DefaultState}.
   * 
   * @param name The name to set.
   * @throws StateException If something with the {@link DefaultState} is not
   *           correct.
   */
  public final void setName ( String name ) throws StateException
  {
    if ( name == null )
    {
      throw new NullPointerException ( "name is null" ); //$NON-NLS-1$
    }
    if ( name.equals ( "" ) ) //$NON-NLS-1$
    {
      throw new StateEmptyNameException ();
    }
    if ( !Character.isJavaIdentifierStart ( name.charAt ( 0 ) ) )
    {
      throw new StateIllegalNameException ( name );
    }
    for ( int i = 1 ; i < name.length () ; i++ )
    {
      if ( !Character.isJavaIdentifierPart ( name.charAt ( i ) ) )
      {
        throw new StateIllegalNameException ( name );
      }
    }
    this.name = name;
    fireStateChanged ();
    fireModifyStatusChanged ();
  }


  /**
   * {@inheritDoc}
   */
  public final void setParserOffset ( ParserOffset parserOffset )
  {
    this.parserOffset = parserOffset;
  }


  /**
   * Sets the push down {@link Alphabet} of this {@link DefaultState}.
   * 
   * @param pushDownAlphabet The push down {@link Alphabet} to set.
   */
  public final void setPushDownAlphabet ( Alphabet pushDownAlphabet )
  {
    if ( pushDownAlphabet == null )
    {
      throw new NullPointerException ( "push down alphabet is null" ); //$NON-NLS-1$
    }
    this.pushDownAlphabet = pushDownAlphabet;
  }


  /**
   * Sets the startState value.
   * 
   * @param startState The startState to set.
   */
  public final void setStartState ( boolean startState )
  {
    this.startState = startState;
    fireModifyStatusChanged ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see PrettyPrintable#toPrettyString()
   */
  public final PrettyString toPrettyString ()
  {
    PrettyString prettyString = new PrettyString ();
    prettyString.addPrettyToken ( new PrettyToken ( this.name, Style.STATE ) );
    return prettyString;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public final String toString ()
  {
    return this.name;
  }


  /**
   * Returns the debug string.
   * 
   * @return The debug string.
   */
  public final String toStringDebug ()
  {
    String lineBreak = System.getProperty ( "line.separator" ); //$NON-NLS-1$
    StringBuilder result = new StringBuilder ();
    result.append ( "Name:        " + this.name + lineBreak ); //$NON-NLS-1$
    result.append ( "Alphabet:    " + this.alphabet.toString () + lineBreak ); //$NON-NLS-1$
    result.append ( "Start state: " + this.startState + lineBreak ); //$NON-NLS-1$
    result.append ( "Final state: " + this.finalState + lineBreak ); //$NON-NLS-1$
    result.append ( "Transition begin:" + lineBreak ); //$NON-NLS-1$
    for ( Transition current : this.transitionBeginList )
    {
      result.append ( "- Alphabet:    " + current.getAlphabet ().toString () //$NON-NLS-1$
          + lineBreak );
      result.append ( "  Begin state: " + current.getStateBegin ().getName () //$NON-NLS-1$
          + lineBreak );
      result.append ( "  End state:   " + current.getStateEnd ().getName () //$NON-NLS-1$
          + lineBreak );
      result.append ( "  Symbols:     " + current.getSymbol ().toString () //$NON-NLS-1$
          + lineBreak );
    }
    result.append ( "Transition end:" + lineBreak ); //$NON-NLS-1$
    for ( int i = 0 ; i < this.transitionEndList.size () ; i++ )
    {
      result.append ( "- Alphabet:    " //$NON-NLS-1$
          + this.transitionEndList.get ( i ).getAlphabet ().toString ()
          + lineBreak );
      result.append ( "  Begin state: " //$NON-NLS-1$
          + this.transitionEndList.get ( i ).getStateBegin ().getName ()
          + lineBreak );
      result.append ( "  End state:   " //$NON-NLS-1$
          + this.transitionEndList.get ( i ).getStateEnd ().getName ()
          + lineBreak );
      result.append ( "  Symbols:     " //$NON-NLS-1$
          + this.transitionEndList.get ( i ).getSymbol ().toString () );
      if ( i < this.transitionEndList.size () - 1 )
      {
        result.append ( lineBreak );
      }
    }
    return result.toString ();
  }
}
