package de.unisiegen.gtitool.core.util;


/**
 * This helper class is used to store two {@link Object}s.
 * 
 * @author Christian Fehler
 * @version $Id$
 * @param <E> The type of the first {@link Object}.
 * @param <F> The type of the second {@link Object}.
 */
public class ObjectPair < E, F >
{

  /**
   * The first {@link Object}.
   */
  private E first;


  /**
   * The second {@link Object}.
   */
  private F second;


  /**
   * Allocates a new {@link ObjectPair}.
   * 
   * @param first The first {@link Object}.
   * @param second The second {@link Object}.
   */
  public ObjectPair ( E first, F second )
  {
    this.first = first;
    this.second = second;
  }


  /**
   * Returns the first {@link Object}.
   * 
   * @return The first {@link Object}.
   * @see #first
   */
  public final E getFirst ()
  {
    return this.first;
  }


  /**
   * Returns the second {@link Object}.
   * 
   * @return The second {@link Object}.
   * @see #second
   */
  public final F getSecond ()
  {
    return this.second;
  }


  /**
   * Sets the first {@link Object}.
   * 
   * @param first The first {@link Object} to set.
   * @see #first
   */
  public final void setFirst ( E first )
  {
    this.first = first;
  }


  /**
   * Sets the second {@link Object}.
   * 
   * @param second The second {@link Object} to set.
   * @see #second
   */
  public final void setSecond ( F second )
  {
    this.second = second;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public final String toString ()
  {
    return ( this.first == null ? "null" : this.first.toString () ) + " | " //$NON-NLS-1$//$NON-NLS-2$
        + ( this.second == null ? "null" : this.second.toString () ); //$NON-NLS-1$
  }
}
