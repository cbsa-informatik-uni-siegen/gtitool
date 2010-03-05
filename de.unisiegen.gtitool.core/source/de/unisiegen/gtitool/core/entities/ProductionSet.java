package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;
import java.util.Collection;

import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * The {@link ProductionSet} entity
 * 
 * @author Christian Uhrhan
 */
public interface ProductionSet extends Entity < ProductionSet >, Storable,
    Modifyable, Iterable < Production >
{

  /**
   * Adds the given {@link Production} to the set
   * 
   * @param p the {@link Production}
   * @return true if the {@link Production} was added
   */
  public boolean add ( final Production p );


  /**
   * Adds the given {@link Production}s to the set
   * 
   * @param prods the {@link Production}s
   * @return true if one of the give {@link Production} was added
   */
  public boolean add ( final Iterable < Production > prods );


  /**
   * Adds the given {@link Production}s to the set
   * 
   * @param prods the {@link Production}s
   * @return true if one of the given {@link Production} was added
   */
  public boolean add ( final Production ... prods );


  /**
   * TODO
   * 
   * @param index
   * @param prod
   * @return
   */
  public boolean add ( int index, Production prod );


  /**
   * TODO
   * 
   * @param index
   * @param set
   * @return
   */
  public boolean add ( int index, ProductionSet set );


  /**
   * TODO
   * 
   * @param index
   * @param productions
   * @return
   */
  public boolean add ( final int index,
      final ArrayList < Production > productions );


  /**
   * TODO
   * 
   * @return
   */
  public Collection < Production > getRep ();


  /**
   * Removes all Productions from the set
   */
  public void clear ();


  /**
   * Removes the given {@link Production} from the set
   * 
   * @param p the {@link Production}
   * @return true if the {@link Production} was removed
   */
  public boolean remove ( final Production p );


  /**
   * TODO
   * 
   * @param p
   * @return
   */
  public boolean remove ( final Production ... p );


  /**
   * TODO
   * 
   * @param p
   * @return
   */
  public boolean remove ( final Iterable < Production > p );


  /**
   * Checks if the given {@link Production} is in this set
   * 
   * @param p the {@link Production}
   * @return true if the {@link Production} was found
   */
  public boolean contains ( final Production p );


  /**
   * Returns the size of the set
   * 
   * @return the size of the set
   */
  public int size ();


  /**
   * Returns whether the set is empty or not
   * 
   * @return true if the set is empty, false otherwise
   */
  public boolean isEmpty ();


  /**
   * Returns the ith element
   * 
   * @param index The index
   * @return The element
   */
  public Production get ( int index );


  /**
   * Removes the ith element
   * 
   * @param index The index
   */
  public void remove ( int index );
}
