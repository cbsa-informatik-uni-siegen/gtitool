package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;
import java.util.Iterator;


/**
 * The <code>Stack</code> entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface Stack extends Entity, Iterable < Symbol >
{

  /**
   * {@inheritDoc}
   * 
   * @see Entity#clone()
   */
  public Stack clone ();


  /**
   * Returns <tt>true</tt> if this <code>Stack</code> contains the specified
   * {@link Symbol}.
   * 
   * @param symbol {@link Symbol} whose presence in this <code>Stack</code> is
   *          to be tested.
   * @return <code>true</code> if the specified {@link Symbol} is present;
   *         <code>false</code> otherwise.
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
   * Returns an iterator over the {@link Symbol}s in this <code>Stack</code>.
   * 
   * @return An iterator over the {@link Symbol}s in this <code>Stack</code>.
   */
  public Iterator < Symbol > iterator ();


  /**
   * Looks at the {@link Symbol} at the top of this <code>Stack</code> without
   * removing it.
   * 
   * @return The {@link Symbol} at the top of this <code>Stack</code>.
   */
  public Symbol peak ();


  /**
   * Removes the {@link Symbol} at the top of this <code>Stack</code> and
   * returns that {@link Symbol}.
   * 
   * @return The {@link Symbol} at the top of this <code>Stack</code>.
   */
  public Symbol pop ();


  /**
   * Pushes the {@link Symbol}s onto the top of this <code>Stack</code>.
   * 
   * @param symbols The {@link Symbol}s to be pushed onto this
   *          <code>Stack</code>.
   */
  public void push ( Iterable < Symbol > symbols );


  /**
   * Pushes the {@link Symbol} onto the top of this <code>Stack</code>.
   * 
   * @param symbol The {@link Symbol} to be pushed onto this <code>Stack</code>.
   */
  public void push ( Symbol symbol );


  /**
   * Pushes the {@link Symbol}s onto the top of this <code>Stack</code>.
   * 
   * @param symbols The {@link Symbol}s to be pushed onto this
   *          <code>Stack</code>.
   */
  public void push ( Symbol ... symbols );


  /**
   * Returns the number of {@link Symbol}s in this <code>Stack</code>.
   * 
   * @return The number of {@link Symbol}s in this <code>Stack</code>.
   */
  public int size ();

}
