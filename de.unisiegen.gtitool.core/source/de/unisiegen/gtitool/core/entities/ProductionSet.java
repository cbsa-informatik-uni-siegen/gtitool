package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * The {@link ProductionSet} entity
 * 
 * @author Christian Uhrhan
 */
public interface ProductionSet extends Entity < Production >, Storable,
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
}
