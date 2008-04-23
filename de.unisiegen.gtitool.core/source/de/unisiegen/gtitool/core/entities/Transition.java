package de.unisiegen.gtitool.core.entities;


import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.listener.TransitionChangedListener;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * The {@link Transition} entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface Transition extends Entity < Transition >, Storable,
    Modifyable, Iterable < Symbol >
{

  /**
   * The value of the id of it was not defined so far.
   */
  public static final int ID_NOT_DEFINED = -1;


  /**
   * Appends the specified {@link Symbol}s to the end of this
   * {@link Transition}.
   * 
   * @param symbols The {@link Symbol}s to be appended to this
   *          {@link Transition}.
   * @throws TransitionSymbolNotInAlphabetException If something with the
   *           {@link Transition} is not correct.
   * @throws TransitionSymbolOnlyOneTimeException If something with the
   *           {@link Transition} is not correct.
   */
  public void add ( Iterable < Symbol > symbols )
      throws TransitionSymbolNotInAlphabetException,
      TransitionSymbolOnlyOneTimeException;


  /**
   * Appends the specified {@link Symbol} to the end of this {@link Transition}.
   * 
   * @param symbol The {@link Symbol} to be appended to this {@link Transition}.
   * @throws TransitionSymbolNotInAlphabetException If something with the
   *           {@link Transition} is not correct.
   * @throws TransitionSymbolOnlyOneTimeException If something with the
   *           {@link Transition} is not correct.
   */
  public void add ( Symbol symbol )
      throws TransitionSymbolNotInAlphabetException,
      TransitionSymbolOnlyOneTimeException;


  /**
   * Appends the specified {@link Symbol}s to the end of this
   * {@link Transition}.
   * 
   * @param symbols The {@link Symbol}s to be appended to this
   *          {@link Transition}.
   * @throws TransitionSymbolNotInAlphabetException If something with the
   *           {@link Transition} is not correct.
   * @throws TransitionSymbolOnlyOneTimeException If something with the
   *           {@link Transition} is not correct.
   */
  public void add ( Symbol ... symbols )
      throws TransitionSymbolNotInAlphabetException,
      TransitionSymbolOnlyOneTimeException;


  /**
   * Adds the given {@link TransitionChangedListener}.
   * 
   * @param listener The {@link TransitionChangedListener}.
   */
  public void addTransitionChangedListener ( TransitionChangedListener listener );


  /**
   * Removes all {@link Symbol}s.
   */
  public void clear ();


  /**
   * {@inheritDoc}
   * 
   * @see Object#clone()
   */
  public Transition clone ();


  /**
   * Returns true if this {@link Transition} contains the given {@link Symbol}.
   * Otherwise false.
   * 
   * @param symbol The {@link Symbol}.
   * @return True if this {@link Transition} contains the given {@link Symbol}.
   *         Otherwise false.
   */
  public boolean contains ( Symbol symbol );


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
   * Returns the id.
   * 
   * @return The id.
   */
  public int getId ();


  /**
   * Returns the push down {@link Alphabet}.
   * 
   * @return The push down {@link Alphabet}.
   */
  public Alphabet getPushDownAlphabet ();


  /**
   * Returns the {@link Word} which is read from the {@link Stack}.
   * 
   * @return The {@link Word} which is read from the {@link Stack}.
   */
  public Word getPushDownWordRead ();


  /**
   * Returns the {@link Word} which should be written on the {@link Stack}.
   * 
   * @return The {@link Word} which should be written on the {@link Stack}.
   */
  public Word getPushDownWordWrite ();


  /**
   * Returns the {@link State} where the {@link Transition} begins.
   * 
   * @return The {@link State} where the {@link Transition} begins.
   */
  public State getStateBegin ();


  /**
   * Returns the {@link State} id where the {@link Transition} begins.
   * 
   * @return The {@link State} id where the {@link Transition} begins.
   */
  public int getStateBeginId ();


  /**
   * Returns the {@link State} where the {@link Transition} ends.
   * 
   * @return The {@link State} where the {@link Transition} ends.
   */
  public State getStateEnd ();


  /**
   * Returns the {@link State} id where the {@link Transition} ends.
   * 
   * @return The {@link State} id where the {@link Transition} ends.
   */
  public int getStateEndId ();


  /**
   * Returns the symbolSet.
   * 
   * @return The symbolSet.
   */
  public TreeSet < Symbol > getSymbol ();


  /**
   * Returns the {@link Symbol} with the given index.
   * 
   * @param index The index.
   * @return The {@link Symbol} with the given index.
   */
  public Symbol getSymbol ( int index );


  /**
   * Returns true if this {@link Transition} is a active {@link Transition},
   * otherwise false.
   * 
   * @return True if this {@link Transition} is a active {@link Transition},
   *         otherwise false.
   */
  public boolean isActive ();


  /**
   * Returns true, if this {@link Transition} is a epsilon {@link Transition},
   * otherwise false.
   * 
   * @return True, if this {@link Transition} is a epsilon {@link Transition},
   *         otherwise false.
   */
  public boolean isEpsilonTransition ();


  /**
   * Returns true if this {@link Transition} is a error {@link Transition},
   * otherwise false.
   * 
   * @return True if this {@link Transition} is a error {@link Transition},
   *         otherwise false.
   */
  public boolean isError ();


  /**
   * Returns true if the id of this {@link Transition} is defined, otherwise
   * false.
   * 
   * @return True if the id of this {@link Transition} is defined, otherwise
   *         false.
   */
  public boolean isIdDefined ();


  /**
   * Remove the given {@link Symbol}s from this {@link Transition}.
   * 
   * @param symbols The {@link Symbol}s to remove.
   */
  public void remove ( Iterable < Symbol > symbols );


  /**
   * Removes the given {@link Symbol} from this {@link Transition}.
   * 
   * @param symbol The {@link Symbol} to remove.
   */
  public void remove ( Symbol symbol );


  /**
   * Remove the given {@link Symbol}s from this {@link Transition}.
   * 
   * @param symbols The {@link Symbol}s to remove.
   */
  public void remove ( Symbol ... symbols );


  /**
   * Removes the given {@link TransitionChangedListener}.
   * 
   * @param listener The {@link TransitionChangedListener}.
   */
  public void removeTransitionChangedListener (
      TransitionChangedListener listener );


  /**
   * Sets the active value.
   * 
   * @param active The active value to set.
   */
  public void setActive ( boolean active );


  /**
   * Sets the {@link Alphabet} of this {@link Transition}.
   * 
   * @param alphabet The {@link Alphabet} to set.
   */
  public void setAlphabet ( Alphabet alphabet );


  /**
   * Sets the error value.
   * 
   * @param error The error value to set.
   */
  public void setError ( boolean error );


  /**
   * Sets the id.
   * 
   * @param id The id to set.
   */
  public void setId ( int id );


  /**
   * Sets the push down {@link Alphabet} of this {@link DefaultTransition}.
   * 
   * @param pushDownAlphabet The push down {@link Alphabet} to set.
   */
  public void setPushDownAlphabet ( Alphabet pushDownAlphabet );


  /**
   * Sets the {@link Word} which is read from the {@link Stack}.
   * 
   * @param pushDownWordRead The {@link Word} to set.
   */
  public void setPushDownWordRead ( Word pushDownWordRead );


  /**
   * The {@link Word} which should be written on the {@link Stack}.
   * 
   * @param pushDownWordWrite The {@link Word} to set.
   */
  public void setPushDownWordWrite ( Word pushDownWordWrite );


  /**
   * Sets the {@link State} where the {@link Transition} begins.
   * 
   * @param stateBegin The {@link State} to set.
   */
  public void setStateBegin ( State stateBegin );


  /**
   * Sets the {@link State} where the {@link Transition} ends.
   * 
   * @param stateEnd The {@link State} to set.
   */
  public void setStateEnd ( State stateEnd );


  /**
   * Returns the number of {@link Symbol}s in this {@link Transition}.
   * 
   * @return The number of {@link Symbol}s in this {@link Transition}.
   */
  public int size ();
}
