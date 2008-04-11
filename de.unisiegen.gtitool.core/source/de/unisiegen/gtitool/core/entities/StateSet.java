package de.unisiegen.gtitool.core.entities;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.listener.StateSetChangedListener;
import de.unisiegen.gtitool.core.exceptions.stateset.StateSetException;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * The {@link StateSet} entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface StateSet extends Entity, Storable, Modifyable,
    Iterable < State >
{

  /**
   * Appends the specified {@link State}s to the end of this {@link StateSet}.
   * 
   * @param states The {@link State}s to be appended to this {@link StateSet}.
   * @throws StateSetException If something with the {@link StateSet} is not
   *           correct.
   */
  public void add ( Iterable < State > states ) throws StateSetException;


  /**
   * Appends the specified {@link State} to the end of this {@link StateSet}.
   * 
   * @param nonterminalSymbol The {@link State} to be appended to this
   *          {@link StateSet}.
   * @throws StateSetException If something with the {@link StateSet} is not
   *           correct.
   */
  public void add ( State nonterminalSymbol ) throws StateSetException;


  /**
   * Appends the specified {@link State}s to the end of this {@link StateSet}.
   * 
   * @param nonterminalSymbols The {@link State}s to be appended to this
   *          {@link StateSet}.
   * @throws StateSetException If something with the {@link StateSet} is not
   *           correct.
   */
  public void add ( State ... nonterminalSymbols ) throws StateSetException;


  /**
   * Adds the given {@link StateSetChangedListener}.
   * 
   * @param listener The {@link StateSetChangedListener}.
   */
  public void addStateSetChangedListener ( StateSetChangedListener listener );


  /**
   * Removes all {@link State}s.
   */
  public void clear ();


  /**
   * {@inheritDoc}
   * 
   * @see Entity#clone()
   */
  public StateSet clone ();


  /**
   * Returns true if this {@link StateSet} contains the specified {@link State}.
   * 
   * @param nonterminalSymbol {@link State} whose presence in this
   *          {@link StateSet} is to be tested.
   * @return true if the specified {@link State} is present; false otherwise.
   */
  public boolean contains ( State nonterminalSymbol );


  /**
   * Returns the {@link State}s.
   * 
   * @return The {@link State}s.
   */
  public ArrayList < State > get ();


  /**
   * Returns the {@link State} with the given index.
   * 
   * @param index The index.
   * @return The {@link State} with the given index.
   */
  public State get ( int index );


  /**
   * Remove the given {@link State}s from this {@link StateSet}.
   * 
   * @param nonterminalSymbols The {@link State}s to remove.
   */
  public void remove ( Iterable < State > nonterminalSymbols );


  /**
   * Removes the given {@link State} from this {@link StateSet}.
   * 
   * @param nonterminalSymbol The {@link State} to remove.
   */
  public void remove ( State nonterminalSymbol );


  /**
   * Remove the given {@link State}s from this {@link StateSet}.
   * 
   * @param nonterminalSymbols The {@link State}s to remove.
   */
  public void remove ( State ... nonterminalSymbols );


  /**
   * Removes the given {@link StateSetChangedListener}.
   * 
   * @param listener The {@link StateSetChangedListener}.
   */
  public void removeStateSetChangedListener ( StateSetChangedListener listener );


  /**
   * Returns the number of {@link State}s in this {@link StateSet}.
   * 
   * @return The number of {@link State}s in this {@link StateSet}.
   */
  public int size ();
}
