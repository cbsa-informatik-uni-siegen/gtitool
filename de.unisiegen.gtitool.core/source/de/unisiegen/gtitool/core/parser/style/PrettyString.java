package de.unisiegen.gtitool.core.parser.style;


import java.util.ArrayList;
import java.util.Iterator;


/**
 * This class represents a {@link PrettyString} which contains
 * {@link PrettyToken}s.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class PrettyString implements Iterable < PrettyToken >
{

  /**
   * The {@link PrettyToken} list.
   */
  private ArrayList < PrettyToken > prettyCharList;


  /**
   * Allocates a new {@link PrettyString}.
   */
  public PrettyString ()
  {
    this.prettyCharList = new ArrayList < PrettyToken > ();
  }


  /**
   * Adds the given {@link PrettyToken} to the list of {@link PrettyToken}s.
   * 
   * @param prettyToken The {@link PrettyToken} to add.
   */
  public final void addPrettyChar ( PrettyToken prettyToken )
  {
    this.prettyCharList.add ( prettyToken );
  }


  /**
   * Adds the given {@link PrettyPrintable} to the list of {@link PrettyToken}s.
   * 
   * @param prettyPrintable The {@link PrettyPrintable} to add.
   */
  public final void addPrettyPrintable ( PrettyPrintable prettyPrintable )
  {
    for ( PrettyToken current : prettyPrintable.getPrettyString () )
    {
      this.prettyCharList.add ( current );
    }
  }


  /**
   * Returns the {@link PrettyToken} list.
   * 
   * @return The {@link PrettyToken} list.
   */
  public final ArrayList < PrettyToken > getPrettyChar ()
  {
    return this.prettyCharList;
  }


  /**
   * Returns the {@link PrettyToken} with the given index.
   * 
   * @param index The index of the {@link PrettyToken} to return.
   * @return The {@link PrettyToken} with the given index.
   */
  public final PrettyToken getPrettyChar ( int index )
  {
    return this.prettyCharList.get ( index );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Iterable#iterator()
   */
  public final Iterator < PrettyToken > iterator ()
  {
    return this.prettyCharList.iterator ();
  }
}
