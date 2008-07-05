package de.unisiegen.gtitool.ui.utils;


import java.util.ArrayList;
import java.util.Collection;


/**
 * An {@link ArrayList} with an override toString method.
 * 
 * @author Benjamin Mies
 * @version $Id$
 * @param <E> The Element type of this list.
 */
public class StateList < E > extends ArrayList < E >
{

  /**
   * The serial verion uid.
   */
  private static final long serialVersionUID = -4656420055778102792L;


  /**
   * Constructs an empty list with an initial capacity of ten.
   */
  public StateList ()
  {
    super ();
  }


  /**
   * Constructs a list containing the elements of the specified collection, in
   * the order they are returned by the collection's iterator. The
   * <tt>ArrayList</tt> instance has an initial capacity of 110% the size of the
   * specified collection.
   * 
   * @param collection the collection whose elements are to be placed into this
   *          list.
   * @throws NullPointerException if the specified collection is null.
   */
  public StateList ( Collection < E > collection )
  {
    super ( collection );
  }


  /**
   * Constructs an empty list with the specified initial capacity.
   * 
   * @param initialCapacity the initial capacity of the list.
   * @exception IllegalArgumentException if the specified initial capacity is
   *              negative
   */
  public StateList ( int initialCapacity )
  {
    super ( initialCapacity );
  }


  /**
   * {@inheritDoc}
   * 
   * @see java.util.AbstractCollection#toString()
   */
  @Override
  public String toString ()
  {
    StringBuilder builder = new StringBuilder ();
    for ( int i = 0 ; i < size () ; i++ )
    {
      builder.append ( get ( i ) );
      if ( i < size () - 1 )
      {
        builder.append ( ", " ); //$NON-NLS-1$
      }
    }
    return builder.toString ();
  }
}
