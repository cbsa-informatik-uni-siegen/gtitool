package de.unisiegen.gtitool.ui.utils;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


/**
 * The power set helper class.
 * 
 * @author Christian Fehler
 * @version $Id$
 * @param <E> The type.
 */
public final class PowerSet < E > implements Iterable < Set < E >>
{

  /**
   * The power set iterator.
   * 
   * @author Christian Fehler
   * @param <F> The type.
   */
  private final class PowerSetIterator < F > implements Iterator < Set < F >>
  {

    /**
     * The {@link PowerSet}.
     */
    private PowerSet < F > powerSet;


    /**
     * The ordered list.
     */
    private ArrayList < F > orderedList = new ArrayList < F > ();


    /**
     * The complete list.
     */
    private ArrayList < F > completeList = new ArrayList < F > ();


    /**
     * Flag that indicates if there is a next value.
     */
    private boolean hasNext = true;


    /**
     * Allocates a new {@link PowerSetIterator}
     * 
     * @param powerSet The {@link PowerSet}.
     */
    public PowerSetIterator ( PowerSet < F > powerSet )
    {
      this.powerSet = powerSet;
      this.orderedList.addAll ( powerSet.getCollection () );
    }


    /**
     * Calculates the next value.
     */
    private final void calculateNext ()
    {
      int i = 0;
      while ( true )
      {
        if ( i < this.completeList.size () )
        {
          F bit = this.completeList.get ( i );
          if ( bit == null )
          {
            this.completeList.set ( i, this.orderedList.get ( i ) );
            return;
          }
          this.completeList.set ( i, null );
          i++ ;
        }
        else
        {
          this.completeList.add ( this.orderedList.get ( i ) );
          return;
        }
      }
    }


    /**
     * {@inheritDoc}
     * 
     * @see Iterator#hasNext()
     */
    public final boolean hasNext ()
    {
      return this.hasNext;
    }


    /**
     * Returns true if everything ist complete, otherwise false.
     * 
     * @return True if everything ist complete, otherwise false.
     */
    private final boolean isComplete ()
    {
      for ( F bit : this.completeList )
      {
        if ( bit == null )
        {
          return false;
        }
      }
      return true;
    }


    /**
     * {@inheritDoc}
     * 
     * @see Iterator#next()
     */
    public final Set < F > next ()
    {
      Set < F > result = new TreeSet < F > ();

      for ( F current : this.completeList )
      {
        if ( current != null )
        {
          result.add ( current );
        }
      }

      this.hasNext = ( this.completeList.size () < this.powerSet
          .getCollection ().size () )
          || !isComplete ();

      if ( this.hasNext )
      {
        calculateNext ();
      }

      return result;
    }


    /**
     * {@inheritDoc}
     * 
     * @see Iterator#remove()
     */
    public final void remove ()
    {
      throw new UnsupportedOperationException ();
    }
  }


  /**
   * The collection.
   */
  private Collection < E > collection;


  /**
   * Allocates a new {@link PowerSet}.
   * 
   * @param collection The {@link Collection}.
   */
  public PowerSet ( Collection < E > collection )
  {
    if ( collection == null )
    {
      throw new IllegalArgumentException ( "collection is null" ); //$NON-NLS-1$
    }
    this.collection = collection;
  }


  /**
   * Returns the {@link Collection}.
   * 
   * @return The {@link Collection}.
   * @see #collection
   */
  public final Collection < E > getCollection ()
  {
    return this.collection;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Iterable#iterator()
   */
  public final Iterator < Set < E >> iterator ()
  {
    return new PowerSetIterator < E > ( this );
  }
}
