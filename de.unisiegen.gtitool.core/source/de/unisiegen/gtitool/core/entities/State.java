package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.listener.StateChangedListener;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * The <code>State</code> entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface State extends ParseableEntity, Storable, Modifyable,
    Comparable < State >
{

  /**
   * The value of the id of it was not defined so far.
   */
  public static final int ID_NOT_DEFINED = -1;


  /**
   * Adds the given {@link StateChangedListener}.
   * 
   * @param listener The {@link StateChangedListener}.
   */
  public void addStateChangedListener ( StateChangedListener listener );


  /**
   * Adds the {@link Transition} to the list of {@link Transition}s, which
   * begin in this <code>State</code>.
   * 
   * @param transition The {@link Transition} to add.
   */
  public void addTransitionBegin ( Transition transition );


  /**
   * Adds the {@link Transition} to the list of {@link Transition}s, which end
   * in this <code>State</code>.
   * 
   * @param transition The {@link Transition} to add.
   */
  public void addTransitionEnd ( Transition transition );


  /**
   * {@inheritDoc}
   * 
   * @see Entity#clone()
   */
  public State clone ();


  /**
   * {@inheritDoc}
   * 
   * @see Comparable#compareTo(Object)
   */
  public int compareTo ( State other );


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  public boolean equals ( Object other );


  /**
   * Returns the {@link Alphabet}.
   * 
   * @return The {@link Alphabet}.
   */
  public Alphabet getAlphabet ();


  /**
   * {@inheritDoc}
   * 
   * @see Storable#getElement()
   */
  public Element getElement ();


  /**
   * Returns the id.
   * 
   * @return The id.
   */
  public int getId ();


  /**
   * Returns the name.
   * 
   * @return The name.
   */
  public String getName ();


  /**
   * Returns the push down {@link Alphabet}.
   * 
   * @return The push down {@link Alphabet}.
   */
  public Alphabet getPushDownAlphabet ();


  /**
   * Returns the {@link Transition} begin list.
   * 
   * @return The {@link Transition} begin list.
   */
  public ArrayList < Transition > getTransitionBegin ();


  /**
   * Returns the {@link Transition} at the specified position in the
   * {@link Transition} begin list.
   * 
   * @param index The index of the {@link Transition} to return.
   * @return The {@link Transition} at the specified position in the
   *         {@link Transition} begin list.
   */
  public Transition getTransitionBegin ( int index );


  /**
   * Returns the {@link Transition} id begin list.
   * 
   * @return The {@link Transition} id begin list.
   */
  public ArrayList < Integer > getTransitionBeginId ();


  /**
   * Returns the {@link Transition} id at the specified position in the
   * {@link Transition} begin list.
   * 
   * @param index The index of the {@link Transition} id to return.
   * @return The {@link Transition} at the specified position in the
   *         {@link Transition} id begin list.
   */
  public int getTransitionBeginId ( int index );


  /**
   * Returns the {@link Transition} end list.
   * 
   * @return The {@link Transition} end list.
   */
  public ArrayList < Transition > getTransitionEnd ();


  /**
   * Returns the {@link Transition} at the specified position in the
   * {@link Transition} end list.
   * 
   * @param index The index of the {@link Transition} to return.
   * @return The {@link Transition} at the specified position in the
   *         {@link Transition} end list.
   */
  public Transition getTransitionEnd ( int index );


  /**
   * Returns the {@link Transition} id end list.
   * 
   * @return The {@link Transition} id end list.
   */
  public ArrayList < Integer > getTransitionEndId ();


  /**
   * Returns the {@link Transition} id at the specified position in the
   * {@link Transition} end list.
   * 
   * @param index The index of the {@link Transition} id to return.
   * @return The {@link Transition} at the specified position in the
   *         {@link Transition} id end list.
   */
  public int getTransitionEndId ( int index );


  /**
   * Returns the State.
   * 
   * @return The State.
   */
  public boolean isFinalState ();


  /**
   * Returns true if the id of this <code>State</code> is defined, otherwise
   * false.
   * 
   * @return True if the id of this <code>State</code> is defined, otherwise
   *         false.
   */
  public boolean isIdDefined ();


  /**
   * Returns the startState.
   * 
   * @return The startState.
   */
  public boolean isStartState ();


  /**
   * Removes the given {@link StateChangedListener}.
   * 
   * @param listener The {@link StateChangedListener}.
   */
  public void removeStateChangedListener ( StateChangedListener listener );


  /**
   * Removes the {@link Transition} from the list of {@link Transition}s, which
   * begin in this <code>State</code>.
   * 
   * @param transition The {@link Transition} to remove.
   */
  public void removeTransitionBegin ( Transition transition );


  /**
   * Removes the {@link Transition} from the list of {@link Transition}s, which
   * end in this <code>State</code>.
   * 
   * @param transition The {@link Transition} to remove.
   */
  public void removeTransitionEnd ( Transition transition );


  /**
   * Sets the {@link Alphabet} of this <code>State</code>.
   * 
   * @param alphabet The {@link Alphabet} to set.
   */
  public void setAlphabet ( Alphabet alphabet );


  /**
   * Sets the default name of this <code>State</code>.
   */
  public void setDefaultName ();


  /**
   * Sets the State value.
   * 
   * @param finalState The State to set.
   */
  public void setFinalState ( boolean finalState );


  /**
   * Sets the id.
   * 
   * @param id The id to set.
   */
  public void setId ( int id );


  /**
   * Sets the name of this <code>State</code>.
   * 
   * @param name The name to set.
   * @throws StateException If something with the <code>State</code> is not
   *           correct.
   */
  public void setName ( String name ) throws StateException;


  /**
   * Sets the push down {@link Alphabet} of this <code>State</code>.
   * 
   * @param pushDownAlphabet The push down {@link Alphabet} to set.
   */
  public void setPushDownAlphabet ( Alphabet pushDownAlphabet );


  /**
   * Sets the startState value.
   * 
   * @param startState The startState to set.
   */
  public void setStartState ( boolean startState );


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  public String toString ();


  /**
   * Returns the debug string.
   * 
   * @return The debug string.
   */
  public String toStringDebug ();
}
