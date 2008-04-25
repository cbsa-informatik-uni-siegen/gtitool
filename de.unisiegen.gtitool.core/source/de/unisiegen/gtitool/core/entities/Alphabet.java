package de.unisiegen.gtitool.core.entities;


import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.listener.AlphabetChangedListener;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * The {@link Alphabet} entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface Alphabet extends Entity < Alphabet >, Storable, Modifyable,
    Iterable < Symbol >
{

  /**
   * Appends the specified {@link Symbol}s to the end of this {@link Alphabet}.
   * 
   * @param symbols The {@link Symbol}s to be appended to this {@link Alphabet}.
   * @throws AlphabetException If something with the {@link Alphabet} is not
   *           correct.
   */
  public void add ( Iterable < Symbol > symbols ) throws AlphabetException;


  /**
   * Appends the specified {@link Symbol} to the end of this {@link Alphabet}.
   * 
   * @param symbol The {@link Symbol} to be appended to this {@link Alphabet}.
   * @throws AlphabetException If something with the {@link Alphabet} is not
   *           correct.
   */
  public void add ( Symbol symbol ) throws AlphabetException;


  /**
   * Appends the specified {@link Symbol}s to the end of this {@link Alphabet}.
   * 
   * @param symbols The {@link Symbol}s to be appended to this {@link Alphabet}.
   * @throws AlphabetException If something with the {@link Alphabet} is not
   *           correct.
   */
  public void add ( Symbol ... symbols ) throws AlphabetException;


  /**
   * Adds the given {@link AlphabetChangedListener}.
   * 
   * @param listener The {@link AlphabetChangedListener}.
   */
  public void addAlphabetChangedListener ( AlphabetChangedListener listener );


  /**
   * Removes all {@link Symbol}s.
   */
  public void clear ();


  /**
   * Returns true if this {@link Alphabet} contains the specified {@link Symbol}.
   * 
   * @param symbol {@link Symbol} whose presence in this {@link Alphabet} is to
   *          be tested.
   * @return true if the specified {@link Symbol} is present; false otherwise.
   */
  public boolean contains ( Symbol symbol );


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
   * Remove the given {@link Symbol}s from this {@link Alphabet}.
   * 
   * @param symbols The {@link Symbol}s to remove.
   */
  public void remove ( Iterable < Symbol > symbols );


  /**
   * Removes the given {@link Symbol} from this {@link Alphabet}.
   * 
   * @param symbol The {@link Symbol} to remove.
   */
  public void remove ( Symbol symbol );


  /**
   * Remove the given {@link Symbol}s from this {@link Alphabet}.
   * 
   * @param symbols The {@link Symbol}s to remove.
   */
  public void remove ( Symbol ... symbols );


  /**
   * Removes the given {@link AlphabetChangedListener}.
   * 
   * @param listener The {@link AlphabetChangedListener}.
   */
  public void removeAlphabetChangedListener ( AlphabetChangedListener listener );


  /**
   * Returns the number of {@link Symbol}s in this {@link Alphabet}.
   * 
   * @return The number of {@link Symbol}s in this {@link Alphabet}.
   */
  public int size ();
}
