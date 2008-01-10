package de.unisiegen.gtitool.core.entities;


import java.util.Iterator;
import java.util.TreeSet;

import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * The <code>Alphabet</code> entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface Alphabet extends ParseableEntity, Storable, Iterable < Symbol >
{

  /**
   * Appends the specified {@link Symbol}s to the end of this
   * <code>Alphabet</code>.
   * 
   * @param symbols The {@link Symbol}s to be appended to this
   *          <code>Alphabet</code>.
   * @throws AlphabetException If something with the <code>Alphabet</code> is
   *           not correct.
   */
  public void add ( Iterable < Symbol > symbols ) throws AlphabetException;


  /**
   * Appends the specified {@link Symbol} to the end of this
   * <code>Alphabet</code>.
   * 
   * @param symbol The {@link Symbol} to be appended to this
   *          <code>Alphabet</code>.
   * @throws AlphabetException If something with the <code>Alphabet</code> is
   *           not correct.
   */
  public void add ( Symbol symbol ) throws AlphabetException;


  /**
   * Appends the specified {@link Symbol}s to the end of this
   * <code>Alphabet</code>.
   * 
   * @param symbols The {@link Symbol}s to be appended to this
   *          <code>Alphabet</code>.
   * @throws AlphabetException If something with the <code>Alphabet</code> is
   *           not correct.
   */
  public void add ( Symbol ... symbols ) throws AlphabetException;


  /**
   * {@inheritDoc}
   * 
   * @see Entity#clone()
   */
  public Alphabet clone ();


  /**
   * Returns <tt>true</tt> if this <code>Alphabet</code> contains the
   * specified {@link Symbol}.
   * 
   * @param symbol {@link Symbol} whose presence in this <code>Alphabet</code>
   *          is to be tested.
   * @return <code>true</code> if the specified {@link Symbol} is present;
   *         <code>false</code> otherwise.
   */
  public boolean contains ( Symbol symbol );


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  public boolean equals ( Object other );


  /**
   * Returns the {@link Symbol}s.
   * 
   * @return The {@link Symbol}s.
   */
  public TreeSet < Symbol > get ();


  /**
   * Returns the {@link Symbol} with the given index.
   * 
   * @param index The index.
   * @return The {@link Symbol} with the given index.
   */
  public Symbol get ( int index );


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
   * Returns an iterator over the {@link Symbol}s in this <code>Alphabet</code>.
   * 
   * @return An iterator over the {@link Symbol}s in this <code>Alphabet</code>.
   */
  public Iterator < Symbol > iterator ();


  /**
   * Remove the given {@link Symbol}s from this <code>Alphabet</code>.
   * 
   * @param symbols The {@link Symbol}s to remove.
   */
  public void remove ( Iterable < Symbol > symbols );


  /**
   * Removes the given {@link Symbol} from this <code>Alphabet</code>.
   * 
   * @param symbol The {@link Symbol} to remove.
   */
  public void remove ( Symbol symbol );


  /**
   * Remove the given {@link Symbol}s from this <code>Alphabet</code>.
   * 
   * @param symbols The {@link Symbol}s to remove.
   */
  public void remove ( Symbol ... symbols );


  /**
   * {@inheritDoc}
   */
  public void setParserEndOffset ( int parserEndOffset );


  /**
   * {@inheritDoc}
   */
  public void setParserStartOffset ( int parserStartOffset );


  /**
   * Returns the number of {@link Symbol}s in this <code>Alphabet</code>.
   * 
   * @return The number of {@link Symbol}s in this <code>Alphabet</code>.
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
   * @see Entity#toString()
   */
  public String toStringDebug ();
}
