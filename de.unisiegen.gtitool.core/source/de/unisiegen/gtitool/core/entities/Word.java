package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;
import java.util.Iterator;

import de.unisiegen.gtitool.core.exceptions.word.WordFinishedException;
import de.unisiegen.gtitool.core.exceptions.word.WordResetedException;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * The <code>Word</code> entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface Word extends ParseableEntity, Storable, Iterable < Symbol >
{

  /**
   * Appends the specified {@link Symbol}s to the end of this <code>Word</code>.
   * 
   * @param pSymbols The {@link Symbol}s to be appended to this
   *          <code>Word</code>.
   */
  public void add ( Iterable < Symbol > pSymbols );


  /**
   * Appends the specified {@link Symbol} to the end of this <code>Word</code>.
   * 
   * @param pSymbol The {@link Symbol} to be appended to this <code>Word</code>.
   */
  public void add ( Symbol pSymbol );


  /**
   * Appends the specified {@link Symbol}s to the end of this <code>Word</code>.
   * 
   * @param pSymbols The {@link Symbol}s to be appended to this
   *          <code>Word</code>.
   */
  public void add ( Symbol ... pSymbols );


  /**
   * {@inheritDoc}
   * 
   * @see Entity#clone()
   */
  public Word clone ();


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  public boolean equals ( Object pOther );


  /**
   * Returns the {@link Symbol}s.
   * 
   * @return The {@link Symbol}s.
   */
  public ArrayList < Symbol > get ();


  /**
   * Returns the {@link Symbol} with the given index.
   * 
   * @param pIndex The index.
   * @return The {@link Symbol} with the given index.
   */
  public Symbol get ( int pIndex );


  /**
   * Returns the current position.
   * 
   * @return The current position.
   */
  public int getCurrentPosition ();


  /**
   * Returns the current {@link Symbol}.
   * 
   * @return The current {@link Symbol}.
   * @throws WordFinishedException If something with the <code>Word</code> is
   *           not correct.
   * @throws WordResetedException If something with the <code>Word</code> is
   *           not correct.
   */
  public Symbol getCurrentSymbol () throws WordFinishedException,
      WordResetedException;


  /**
   * {@inheritDoc}
   * 
   * @see Storable#getElement()
   */
  public Element getElement ();


  /**
   * {@inheritDoc}
   */
  public int getParserEndOffset ();


  /**
   * {@inheritDoc}
   */
  public int getParserStartOffset ();


  /**
   * {@inheritDoc}
   * 
   * @see Entity#hashCode()
   */
  public int hashCode ();


  /**
   * Returns true if this word is finished, otherwise false.
   * 
   * @return True if this word is finished, otherwise false.
   */
  public boolean isFinished ();


  /**
   * Returns true if this word is reseted, otherwise false.
   * 
   * @return True if this word is reseted, otherwise false.
   */
  public boolean isReseted ();


  /**
   * Returns an iterator over the {@link Symbol}s in this <code>Word</code>.
   * 
   * @return An iterator over the {@link Symbol}s in this <code>Word</code>.
   */
  public Iterator < Symbol > iterator ();


  /**
   * Returns the next {@link Symbol} and increments the current position.
   * 
   * @return The next {@link Symbol}.
   * @throws WordFinishedException If something with the <code>Word</code> is
   *           not correct.
   * @throws WordResetedException If something with the <code>Word</code> is
   *           not correct.
   */
  public Symbol nextSymbol () throws WordFinishedException,
      WordResetedException;


  /**
   * Returns the previous {@link Symbol} and decrements the current position.
   * 
   * @return The previous {@link Symbol}.
   * @throws WordFinishedException If something with the <code>Word</code> is
   *           not correct.
   * @throws WordResetedException If something with the <code>Word</code> is
   *           not correct.
   */
  public Symbol previousSymbol () throws WordFinishedException,
      WordResetedException;


  /**
   * {@inheritDoc}
   */
  public void setParserEndOffset ( int pParserEndOffset );


  /**
   * {@inheritDoc}
   */
  public void setParserStartOffset ( int pParserStartOffset );


  /**
   * Returns the number of {@link Symbol}s in this <code>Word</code>.
   * 
   * @return The number of {@link Symbol}s in this <code>Word</code>.
   */
  public int size ();


  /**
   * Resets the current position of this <code>Word</code>.
   */
  public void start ();


  /**
   * {@inheritDoc}
   * 
   * @see Entity#toString()
   */
  public String toString ();


  /**
   * {@inheritDoc}
   * 
   * @see Entity#toString()
   */
  public String toStringDebug ();
}
