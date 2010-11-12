package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;
import java.util.Iterator;


/**
 * The {@link Stack} entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface Stack extends Entity < Stack >, Iterable < Symbol >
{

  /**
   * Removes all {@link Symbol}s.
   */
  public void clear ();


  /**
   * Returns an iterator over the {@link Symbol}s in this {@link Stack}.
   * 
   * @return An iterator over the {@link Symbol}s in this {@link Stack}.
   */
  public Iterator < Symbol > iterator ();


  /**
   * Looks at the {@link Symbol} at the top of this {@link Stack} without
   * removing it.
   * 
   * @return The {@link Symbol} at the top of this {@link Stack}.
   */
  public Symbol peak ();


  /**
   * Looks at the {@link Symbol}s at the top of this {@link Stack} without
   * removing them.
   * 
   * @param size The number of returned symbols.
   * @return The {@link Symbol} at the top of this {@link Stack}.
   */
  public ArrayList < Symbol > peak ( int size );


  /**
   * Removes the {@link Symbol} at the top of this {@link Stack} and returns
   * that {@link Symbol}.
   * 
   * @return The {@link Symbol} at the top of this {@link Stack}.
   */
  public Symbol pop ();


  /**
   * Pushes the {@link Symbol} onto the top of this {@link Stack}.
   * 
   * @param symbol The {@link Symbol} to be pushed onto this {@link Stack}.
   */
  public void push ( Symbol symbol );


  /**
   * Removes the {@link Symbol} from the front.
   * 
   * @return the current front
   */
  public Symbol pop_front ();


  /**
   * Pushes the {@link Symbol} to the front
   * 
   * @param symbol
   */
  public void push_front ( Symbol symbol );


  /**
   * Returns the number of {@link Symbol}s in this {@link Stack}.
   * 
   * @return The number of {@link Symbol}s in this {@link Stack}.
   */
  public int size ();


  /**
   * Returns whether this {@link Stack} is empty or not
   * 
   * @return true if the {@link Stack} is empty
   */
  public boolean isEmpty ();


  /**
   * Returns the element string in reverse
   * 
   * @return the string
   */
  public String reverseElementString ();
}
