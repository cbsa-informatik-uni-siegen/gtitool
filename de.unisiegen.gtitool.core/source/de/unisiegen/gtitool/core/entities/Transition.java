package de.unisiegen.gtitool.core.entities;


import java.util.Iterator;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolNotInAlphabetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * The <code>Transition</code> entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface Transition extends ParseableEntity, Storable,
    Comparable < Transition >, Iterable < Symbol >
{

  /**
   * The value of the id of it was not defined so far.
   */
  public static final int ID_NOT_DEFINED = -1;


  /**
   * Appends the specified {@link Symbol}s to the end of this
   * <code>Transition</code>.
   * 
   * @param pSymbols The {@link Symbol}s to be appended to this
   *          <code>Transition</code>.
   * @throws TransitionSymbolNotInAlphabetException If something with the
   *           <code>Transition</code> is not correct.
   * @throws TransitionSymbolOnlyOneTimeException If something with the
   *           <code>Transition</code> is not correct.
   */
  public void add ( Iterable < Symbol > pSymbols )
      throws TransitionSymbolNotInAlphabetException,
      TransitionSymbolOnlyOneTimeException;


  /**
   * Appends the specified {@link Symbol} to the end of this
   * <code>Transition</code>.
   * 
   * @param pSymbol The {@link Symbol} to be appended to this
   *          <code>Transition</code>.
   * @throws TransitionSymbolNotInAlphabetException If something with the
   *           <code>Transition</code> is not correct.
   * @throws TransitionSymbolOnlyOneTimeException If something with the
   *           <code>Transition</code> is not correct.
   */
  public void add ( Symbol pSymbol )
      throws TransitionSymbolNotInAlphabetException,
      TransitionSymbolOnlyOneTimeException;


  /**
   * Appends the specified {@link Symbol}s to the end of this
   * <code>Transition</code>.
   * 
   * @param pSymbols The {@link Symbol}s to be appended to this
   *          <code>Transition</code>.
   * @throws TransitionSymbolNotInAlphabetException If something with the
   *           <code>Transition</code> is not correct.
   * @throws TransitionSymbolOnlyOneTimeException If something with the
   *           <code>Transition</code> is not correct.
   */
  public void add ( Symbol ... pSymbols )
      throws TransitionSymbolNotInAlphabetException,
      TransitionSymbolOnlyOneTimeException;


  /**
   * {@inheritDoc}
   * 
   * @see Object#clone()
   */
  public Transition clone ();


  /**
   * {@inheritDoc}
   * 
   * @see Comparable#compareTo(Object)
   */
  public int compareTo ( Transition pOther );


  /**
   * Returns true if the {@link Alphabet} of this <code>Transition</code>
   * contains the given {@link Symbol}. Otherwise false.
   * 
   * @param pSymbol The {@link Symbol}.
   * @return True if the {@link Alphabet} of this <code>Transition</code>
   *         contains the given {@link Symbol}. Otherwise false.
   */
  public boolean contains ( Symbol pSymbol );


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
   * {@inheritDoc}
   */
  public int getParserEndOffset ();


  /**
   * {@inheritDoc}
   */
  public int getParserStartOffset ();


  /**
   * Returns the {@link State} where the <code>Transition</code> begins.
   * 
   * @return The {@link State} where the <code>Transition</code> begins.
   */
  public State getStateBegin ();


  /**
   * Returns the {@link State} id where the <code>Transition</code> begins.
   * 
   * @return The {@link State} id where the <code>Transition</code> begins.
   */
  public int getStateBeginId ();


  /**
   * Returns the {@link State} where the <code>Transition</code> ends.
   * 
   * @return The {@link State} where the <code>Transition</code> ends.
   */
  public State getStateEnd ();


  /**
   * Returns the {@link State} id where the <code>Transition</code> ends.
   * 
   * @return The {@link State} id where the <code>Transition</code> ends.
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
   * @param pIndex The index.
   * @return The {@link Symbol} with the given index.
   */
  public Symbol getSymbol ( int pIndex );


  /**
   * {@inheritDoc}
   * 
   * @see Object#hashCode()
   */
  public int hashCode ();


  /**
   * Returns true, if this <code>Transition</code> is a epsilon
   * <code>Transition</code>, otherwise false.
   * 
   * @return True, if this <code>Transition</code> is a epsilon
   *         <code>Transition</code>, otherwise false.
   */
  public boolean isEpsilonTransition ();


  /**
   * Returns true if the id of this <code>Transition</code> is defined,
   * otherwise false.
   * 
   * @return True if the id of this <code>Transition</code> is defined,
   *         otherwise false.
   */
  public boolean isIdDefined ();


  /**
   * Returns an iterator over the {@link Symbol}s in this
   * <code>Transition</code>.
   * 
   * @return An iterator over the {@link Symbol}s in this
   *         <code>Transition</code>.
   */
  public Iterator < Symbol > iterator ();


  /**
   * Remove the given {@link Symbol}s from this <code>Transition</code>.
   * 
   * @param pSymbols The {@link Symbol}s to remove.
   */
  public void remove ( Iterable < Symbol > pSymbols );


  /**
   * Removes the given {@link Symbol} from this <code>Transition</code>.
   * 
   * @param pSymbol The {@link Symbol} to remove.
   */
  public void remove ( Symbol pSymbol );


  /**
   * Remove the given {@link Symbol}s from this <code>Transition</code>.
   * 
   * @param pSymbols The {@link Symbol}s to remove.
   */
  public void remove ( Symbol ... pSymbols );


  /**
   * Sets the {@link Alphabet} of this <code>Transition</code>.
   * 
   * @param pAlphabet The {@link Alphabet} to set.
   */
  public void setAlphabet ( Alphabet pAlphabet );


  /**
   * Sets the id.
   * 
   * @param pId The id to set.
   */
  public void setId ( int pId );


  /**
   * {@inheritDoc}
   */
  public void setParserEndOffset ( int pParserEndOffset );


  /**
   * {@inheritDoc}
   */
  public void setParserStartOffset ( int pParserStartOffset );


  /**
   * Sets the {@link State} where the <code>Transition</code> begins.
   * 
   * @param pStateBegin The {@link State} to set.
   */
  public void setStateBegin ( State pStateBegin );


  /**
   * Sets the {@link State} where the <code>Transition</code> ends.
   * 
   * @param pStateEnd The {@link State} to set.
   */
  public void setStateEnd ( State pStateEnd );


  /**
   * Returns the number of {@link Symbol}s in this <code>Transition</code>.
   * 
   * @return The number of {@link Symbol}s in this <code>Transition</code>.
   */
  public int size ();


  /**
   * {@inheritDoc}
   * 
   * @see Entity#toString()
   */
  public String toString ();


  /**
   * {@inheritDoc}
   * 
   * @see Entity#toStringDebug()
   */
  public String toStringDebug ();
}
