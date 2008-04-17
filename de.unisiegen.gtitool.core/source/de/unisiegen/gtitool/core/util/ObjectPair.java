package de.unisiegen.gtitool.core.util;


/**
 * This helper class is used to store two {@link Object}s.
 * 
 * @author Christian Fehler
 * @version $Id$
 * @param <E> The type of the first {@link Object}.
 * @param <T> The type of the second {@link Object}.
 */
public final class ObjectPair < E, T >
{

  /**
   * The first {@link Object}.
   */
  private E first;


  /**
   * The second {@link Object}.
   */
  private T second;


  /**
   * Allocates a new {@link ObjectPair}.
   * 
   * @param first The first {@link Object}.
   * @param second The second {@link Object}.
   */
  public ObjectPair ( E first, T second )
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
  public final T getSecond ()
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
  public final void setSecond ( T second )
  {
    this.second = second;
  }
}
