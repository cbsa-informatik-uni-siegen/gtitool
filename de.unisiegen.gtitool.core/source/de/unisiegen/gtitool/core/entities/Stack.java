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
   * Returns true if this {@link Stack} contains the specified {@link Symbol}.
   * 
   * @param symbol {@link Symbol} whose presence in this {@link Stack} is to be
   *          tested.
   * @return true if the specified {@link Symbol} is present; false otherwise.
   */
  public boolean contains ( Symbol symbol );


  /**
   * Returns the {@link Symbol}s.
   * 
   * @return The {@link Symbol}s.
   */
  public ArrayList < Symbol > get ();


  /**
   * Returns the {@link Symbol} with the given index.
   * 
   * @param index The index.
   * @return The {@link Symbol} with the given index.
   */
  public Symbol get ( int index );


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
   * Removes the {@link Symbol}s at the top of this {@link Stack} and returns
   * the {@link Symbol}s.
   * 
   * @param size The number of returned symbols.
   * @return The {@link Symbol}s at the top of this {@link Stack}.
   */
  public ArrayList < Symbol > pop ( int size );


  /**
   * Pushes the {@link Symbol}s onto the top of this {@link Stack}.
   * 
   * @param symbols The {@link Symbol}s to be pushed onto this {@link Stack}.
   */
  public void push ( Iterable < Symbol > symbols );


  /**
   * Pushes the {@link Symbol} onto the top of this {@link Stack}.
   * 
   * @param symbol The {@link Symbol} to be pushed onto this {@link Stack}.
   */
  public void push ( Symbol symbol );


  /**
   * Pushes the {@link Symbol}s onto the top of this {@link Stack}.
   * 
   * @param symbols The {@link Symbol}s to be pushed onto this {@link Stack}.
   */
  public void push ( Symbol ... symbols );


  /**
   * Returns the number of {@link Symbol}s in this {@link Stack}.
   * 
   * @return The number of {@link Symbol}s in this {@link Stack}.
   */
  public int size ();

}
