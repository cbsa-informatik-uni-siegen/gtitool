package de.unisiegen.gtitool.core.entities;


import java.util.TreeSet;

import de.unisiegen.gtitool.core.exceptions.lractionset.ActionSetException;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * Represents a set of {@link Action}s
 */
public interface ActionSet extends Entity < ActionSet >, Storable, Modifyable,
    Iterable < Action >
{

  /**
   * Appends the specified {@link Action}s to the end of this {@link ActionSet}.
   * 
   * @param actions The {@link Action}s to be appended to this {@link ActionSet}
   *          .
   * @throws ActionSetException If something with the {@link StateSet} is not
   *           correct.
   */
  public void add ( Iterable < Action > actions ) throws ActionSetException;


  /**
   * Appends the specified {@link Action} to the end of this {@link ActionSet}.
   * 
   * @param action The {@link Action} to be appended to this {@link ActionSet}.
   * @throws ActionSetException If something with the {@link StateSet} is not
   *           correct.
   */
  public void add ( Action action ) throws ActionSetException;


  /**
   * Appends the specified {@link Action}s to the end of this {@link ActionSet}.
   * 
   * @param actions The {@link Action}s to be appended to this {@link ActionSet}
   *          .
   * @throws ActionSetException If something with the {@link ActionSet} is not
   *           correct.
   */
  public void add ( Action ... actions ) throws ActionSetException;


  /**
   * Appends a new action to this set if it doesn't exist already
   * 
   * @param action
   * @return true if the action has been inserted
   */
  public boolean addIfNonExistant ( Action action );


  /**
   * Returns true if this {@link ActionSet} contains the specified
   * {@link Action}.
   * 
   * @param action {@link Action} whose presence in this {@link ActionSet} is to
   *          be tested.
   * @return true if the specified {@link Action} is present; false otherwise.
   */
  public boolean contains ( Action action );


  /**
   * Checks if this set is empty
   *
   * @return true if the set is empty
   */
  public boolean isEmpty ();


  /**
   * Returns the {@link Action}s.
   * 
   * @return The {@link Action}s.
   */
  public TreeSet < Action > get ();


  /**
   * Returns the {@link Action} at Index {@code index}.
   * 
   * @param index The Index of the {@link Action} we want to return
   * @return {@link Action} at Index {@code index}
   */
  public Action get ( int index );


  /**
   * Returns the first {@link Action}
   * 
   * @return the first {@link Action}
   */
  public Action first ();


  /**
   * Returns the number of {@link Action}s in this {@link ActionSet}.
   * 
   * @return The number of {@link Action}s in this {@link ActionSet}.
   */
  public int size ();
}
