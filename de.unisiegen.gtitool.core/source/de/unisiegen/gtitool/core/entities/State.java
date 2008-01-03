package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * The <code>State</code> entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface State extends ParseableEntity, Storable, Comparable < State >
{

  /**
   * The value of the id of it was not defined so far.
   */
  public static final int ID_NOT_DEFINED = -1;


  /**
   * Adds the {@link Transition} to the list of {@link Transition}s, which
   * begin in this <code>State</code>.
   * 
   * @param pTransition The {@link Transition} to add.
   */
  public void addTransitionBegin ( Transition pTransition );


  /**
   * Adds the {@link Transition} to the list of {@link Transition}s, which end
   * in this <code>State</code>.
   * 
   * @param pTransition The {@link Transition} to add.
   */
  public void addTransitionEnd ( Transition pTransition );


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
  public int compareTo ( State pOther );


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  public boolean equals ( Object pOther );


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
   * {@inheritDoc}
   */
  public int getParserEndOffset ();


  /**
   * {@inheritDoc}
   */
  public int getParserStartOffset ();


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
   * @param pIndex The index of the {@link Transition} to return.
   * @return The {@link Transition} at the specified position in the
   *         {@link Transition} begin list.
   */
  public Transition getTransitionBegin ( int pIndex );


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
   * @param pIndex The index of the {@link Transition} id to return.
   * @return The {@link Transition} at the specified position in the
   *         {@link Transition} id begin list.
   */
  public int getTransitionBeginId ( int pIndex );


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
   * @param pIndex The index of the {@link Transition} to return.
   * @return The {@link Transition} at the specified position in the
   *         {@link Transition} end list.
   */
  public Transition getTransitionEnd ( int pIndex );


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
   * @param pIndex The index of the {@link Transition} id to return.
   * @return The {@link Transition} at the specified position in the
   *         {@link Transition} id end list.
   */
  public int getTransitionEndId ( int pIndex );


  /**
   * {@inheritDoc}
   * 
   * @see Entity#hashCode()
   */
  public int hashCode ();


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
   * Removes the {@link Transition} from the list of {@link Transition}s, which
   * begin in this <code>State</code>.
   * 
   * @param pTransition The {@link Transition} to remove.
   */
  public void removeTransitionBegin ( Transition pTransition );


  /**
   * Removes the {@link Transition} from the list of {@link Transition}s, which
   * end in this <code>State</code>.
   * 
   * @param pTransition The {@link Transition} to remove.
   */
  public void removeTransitionEnd ( Transition pTransition );


  /**
   * Sets the {@link Alphabet} of this <code>State</code>.
   * 
   * @param pAlphabet The {@link Alphabet} to set.
   */
  public void setAlphabet ( Alphabet pAlphabet );


  /**
   * Sets the default name of this <code>State</code>.
   */
  public void setDefaultName ();


  /**
   * Sets the State value.
   * 
   * @param pFinalState The State to set.
   */
  public void setFinalState ( boolean pFinalState );


  /**
   * Sets the id.
   * 
   * @param pId The id to set.
   */
  public void setId ( int pId );


  /**
   * Sets the name of this <code>State</code>.
   * 
   * @param pName The name to set.
   * @throws StateException If something with the <code>State</code> is not
   *           correct.
   */
  public void setName ( String pName ) throws StateException;


  /**
   * {@inheritDoc}
   */
  public void setParserEndOffset ( int pParserEndOffset );


  /**
   * {@inheritDoc}
   */
  public void setParserStartOffset ( int pParserStartOffset );


  /**
   * Sets the push down {@link Alphabet} of this <code>State</code>.
   * 
   * @param pPushDownAlphabet The push down {@link Alphabet} to set.
   */
  public void setPushDownAlphabet ( Alphabet pPushDownAlphabet );


  /**
   * Sets the startState value.
   * 
   * @param pStartState The startState to set.
   */
  public void setStartState ( boolean pStartState );


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
