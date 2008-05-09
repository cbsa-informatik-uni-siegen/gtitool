package de.unisiegen.gtitool.core.util;


/**
 * This helper class is used to store two {@link Object}s.
 * 
 * @author Christian Fehler
 * @version $Id$
 * @param <E> The type of the first {@link Object}.
 * @param <F> The type of the second {@link Object}.
 * @param <G> The type of the third {@link Object}.
 */
public class ObjectTriple < E, F, G >
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
   * The third {@link Object}.
   */
  private G third;


  /**
   * Allocates a new {@link ObjectTriple}.
   * 
   * @param first The first {@link Object}.
   * @param second The second {@link Object}.
   * @param third The third {@link Object}.
   */
  public ObjectTriple ( E first, F second, G third )
  {
    this.first = first;
    this.second = second;
    this.third = third;
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
   * Returns the third {@link Object}.
   * 
   * @return The third {@link Object}.
   * @see #third
   */
  public final G getThird ()
  {
    return this.third;
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
   * Sets the third {@link Object}.
   * 
   * @param third The third {@link Object} to set.
   * @see #third
   */
  public final void setThird ( G third )
  {
    this.third = third;
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
        + ( this.second == null ? "null" : this.second.toString () ) + " | " //$NON-NLS-1$//$NON-NLS-2$
        + ( this.third == null ? "null" : this.third.toString () ) + " | "; //$NON-NLS-1$//$NON-NLS-2$
  }
}
