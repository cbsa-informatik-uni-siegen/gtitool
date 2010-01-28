package de.unisiegen.gtitool.core.entities;


import java.util.TreeSet;

import de.unisiegen.gtitool.core.exceptions.lractionset.LRActionSetException;
import de.unisiegen.gtitool.core.exceptions.stateset.StateSetException;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * TODO
 */
public interface LRActionSet extends Entity < LRActionSet >, Storable,
    Modifyable, Iterable < LRAction >
{
  /**
   * Appends the specified {@link State}s to the end of this {@link StateSet}.
   * 
   * @param states The {@link State}s to be appended to this {@link StateSet}.
   * @throws StateSetException If something with the {@link StateSet} is not
   *           correct.
   */
  public void add ( Iterable < LRAction > actions ) throws LRActionSetException;


  /**
   * Appends the specified {@link State} to the end of this {@link StateSet}.
   * 
   * @param nonterminalSymbol The {@link State} to be appended to this
   *          {@link StateSet}.
   * @throws StateSetException If something with the {@link StateSet} is not
   *           correct.
   */
  public void add ( LRAction actions ) throws LRActionSetException;


  /**
   * Appends the specified {@link State}s to the end of this {@link StateSet}.
   * 
   * @param actions The {@link State}s to be appended to this
   *          {@link StateSet}.
   * @throws StateSetException If something with the {@link StateSet} is not
   *           correct.
   */
  public void add ( LRAction ... actions ) throws LRActionSetException;


  /**
   * Returns true if this {@link StateSet} contains the specified {@link State}.
   * 
   * @param action {@link State} whose presence in this
   *          {@link StateSet} is to be tested.
   * @return true if the specified {@link State} is present; false otherwise.
   */
  public boolean contains ( LRAction action );


  /**
   * Returns the {@link State}s.
   * 
   * @return The {@link State}s.
   */
  public TreeSet < LRAction > get ();




  /**
   * Returns the number of {@link State}s in this {@link StateSet}.
   * 
   * @return The number of {@link State}s in this {@link StateSet}.
   */
  public int size ();
}
