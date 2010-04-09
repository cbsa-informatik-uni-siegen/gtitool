package de.unisiegen.gtitool.core.entities;


import java.awt.Rectangle;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.listener.TransitionChangedListener;
import de.unisiegen.gtitool.core.entities.listener.TransitionSelectionChangedListener;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
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
   * The {@link Transition} type.
   * 
   * @author Christian Fehler
   */
  public enum TransitionType
  {

    /**
     * The {@link Transition} contains only normal {@link Symbol}s.
     */
    SYMBOL,

    /**
     * The {@link Transition} contains only the epsilon {@link Symbol}.
     */
    EPSILON_ONLY,

    /**
     * The {@link Transition} contains the epsilon {@link Symbol} and other
     * {@link Symbol}s.
     */
    EPSILON_SYMBOL;
  }


  /**
   * The value of the id of it was not defined so far.
   */
  public static final int ID_NOT_DEFINED = -1;


  /**
   * Appends the specified {@link Symbol}s to the end of this {@link Transition}
   * .
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
   * Appends the specified {@link Symbol}s to the end of this {@link Transition}
   * .
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
   * Adds the given {@link TransitionSelectionChangedListener}.
   * 
   * @param listener
   */
  public void addTransitionSelectedListener (
      TransitionSelectionChangedListener listener );


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
   * Returns the label bounds.
   * 
   * @return The label bounds.
   */
  public Rectangle getLabelBounds ();


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
   * Returns the {@link TransitionType}.
   * 
   * @return The {@link TransitionType}.
   */
  public TransitionType getTransitionType ();


  /**
   * Returns true if this {@link Transition} is a active {@link Transition},
   * otherwise false.
   * 
   * @return True if this {@link Transition} is a active {@link Transition},
   *         otherwise false.
   */
  public boolean isActive ();


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
   * Returns true if this {@link Transition} is a selected {@link Transition},
   * otherwise false.
   * 
   * @return True if this {@link Transition} is a selected {@link Transition},
   *         otherwise false.
   */
  public boolean isSelected ();


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
   * Clears the {@link Symbol}s and appends the specified {@link Symbol}s to the
   * end of this {@link Transition}.
   * 
   * @param symbols The {@link Symbol}s to be appended to this
   *          {@link Transition}.
   * @throws TransitionSymbolNotInAlphabetException If something with the
   *           {@link Transition} is not correct.
   * @throws TransitionSymbolOnlyOneTimeException If something with the
   *           {@link Transition} is not correct.
   */
  public void replace ( Iterable < Symbol > symbols )
      throws TransitionSymbolNotInAlphabetException,
      TransitionSymbolOnlyOneTimeException;


  /**
   * Clears the {@link Symbol}s and add the specified {@link Symbol} to the end
   * of this {@link Transition}.
   * 
   * @param symbol The {@link Symbol} to be appended to this {@link Transition}.
   * @throws TransitionSymbolNotInAlphabetException If something with the
   *           {@link Transition} is not correct.
   * @throws TransitionSymbolOnlyOneTimeException If something with the
   *           {@link Transition} is not correct.
   */
  public void replace ( Symbol symbol )
      throws TransitionSymbolNotInAlphabetException,
      TransitionSymbolOnlyOneTimeException;


  /**
   * Clears the {@link Symbol}s and append the specified {@link Symbol}s to the
   * end of this {@link Transition}.
   * 
   * @param symbols The {@link Symbol}s to be appended to this
   *          {@link Transition}.
   * @throws TransitionSymbolNotInAlphabetException If something with the
   *           {@link Transition} is not correct.
   * @throws TransitionSymbolOnlyOneTimeException If something with the
   *           {@link Transition} is not correct.
   */
  public void replace ( Symbol ... symbols )
      throws TransitionSymbolNotInAlphabetException,
      TransitionSymbolOnlyOneTimeException;


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
   * Sets the label bounds.
   * 
   * @param labelBounds The label bounds.
   */
  public void setLabelBounds ( Rectangle labelBounds );


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
   * Sets the selected value.
   * 
   * @param selected The selected value to set.
   */
  public void setSelected ( boolean selected );


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


  /**
   * Return the {@link PrettyString} for the stack operation.
   * 
   * @return The {@link PrettyString} for the stack operation.
   */
  public PrettyString toStackOperationPrettyString ();


  /**
   * Compares two transitions by their states
   * 
   * @param other
   * @return true if both states are the same
   */
  public boolean compareByStates ( Transition other );
}
