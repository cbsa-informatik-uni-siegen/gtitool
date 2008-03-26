package de.unisiegen.gtitool.core.parser.style;


import java.util.ArrayList;
import java.util.Iterator;


/**
 * This class represents a {@link PrettyPrintable} list which contains
 * {@link PrettyPrintable}s.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class PrettyPrintableList implements PrettyPrintable,
    Iterable < PrettyPrintable >
{

  /**
   * The {@link PrettyPrintable} list.
   */
  private ArrayList < PrettyPrintable > list;


  /**
   * Allocates a new {@link PrettyPrintableList}.
   */
  public PrettyPrintableList ()
  {
    this.list = new ArrayList < PrettyPrintable > ();
  }


  /**
   * Adds the given {@link PrettyPrintable}.
   * 
   * @param prettyPrintable The {@link PrettyPrintable} to add.
   */
  public final void add ( PrettyPrintable prettyPrintable )
  {
    this.list.add ( prettyPrintable );
  }


  /**
   * Clears the {@link PrettyPrintableList}.
   */
  public final void clear ()
  {
    this.list.clear ();
  }


  /**
   * Returns the {@link PrettyPrintable} with the given index.
   * 
   * @param index The index.
   * @return The {@link PrettyPrintable} with the given index.
   */
  public final PrettyPrintable get ( int index )
  {
    return this.list.get ( index );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Iterable#iterator()
   */
  public final Iterator < PrettyPrintable > iterator ()
  {
    return this.list.iterator ();
  }


  /**
   * Returns the size.
   * 
   * @return The size.
   */
  public final int size ()
  {
    return this.list.size ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see PrettyPrintable#toPrettyString()
   */
  public final PrettyString toPrettyString ()
  {
    PrettyString prettyString = new PrettyString ();

    for ( int i = 0 ; i < this.list.size () ; i++ )
    {
      if ( i > 0 )
      {
        prettyString.addPrettyToken ( new PrettyToken ( ", ", Style.NONE ) ); //$NON-NLS-1$
      }
      prettyString.addPrettyPrintable ( this.list.get ( i ) );
    }
    return prettyString;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#toString()
   */
  @Override
  public final String toString ()
  {
    StringBuilder result = new StringBuilder ();
    for ( int i = 0 ; i < this.list.size () ; i++ )
    {
      if ( i > 0 )
      {
        result.append ( ", " ); //$NON-NLS-1$
      }
      result.append ( this.list.get ( i ) );
    }
    return result.toString ();
  }
}
