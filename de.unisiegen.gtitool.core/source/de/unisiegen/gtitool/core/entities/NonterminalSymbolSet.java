package de.unisiegen.gtitool.core.entities;


import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.listener.NonterminalSymbolSetChangedListener;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * The {@link NonterminalSymbolSet} entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface NonterminalSymbolSet extends Entity < NonterminalSymbolSet >,
    Storable, Modifyable, Iterable < NonterminalSymbol >
{

  /**
   * Appends the specified {@link NonterminalSymbol}s to the end of this
   * {@link NonterminalSymbolSet}.
   * 
   * @param nonterminalSymbols The {@link NonterminalSymbol}s to be appended to
   *          this {@link NonterminalSymbolSet}.
   * @throws NonterminalSymbolSetException If something with the
   *           {@link NonterminalSymbolSet} is not correct.
   */
  public void add ( Iterable < NonterminalSymbol > nonterminalSymbols )
      throws NonterminalSymbolSetException;


  /**
   * Appends the specified {@link NonterminalSymbol} to the end of this
   * {@link NonterminalSymbolSet}.
   * 
   * @param nonterminalSymbol The {@link NonterminalSymbol} to be appended to
   *          this {@link NonterminalSymbolSet}.
   * @throws NonterminalSymbolSetException If something with the
   *           {@link NonterminalSymbolSet} is not correct.
   */
  public void add ( NonterminalSymbol nonterminalSymbol )
      throws NonterminalSymbolSetException;


  /**
   * Appends the specified {@link NonterminalSymbol}s to the end of this
   * {@link NonterminalSymbolSet}.
   * 
   * @param nonterminalSymbols The {@link NonterminalSymbol}s to be appended to
   *          this {@link NonterminalSymbolSet}.
   * @throws NonterminalSymbolSetException If something with the
   *           {@link NonterminalSymbolSet} is not correct.
   */
  public void add ( NonterminalSymbol ... nonterminalSymbols )
      throws NonterminalSymbolSetException;


  /**
   * Adds the given {@link NonterminalSymbolSetChangedListener}.
   * 
   * @param listener The {@link NonterminalSymbolSetChangedListener}.
   */
  public void addNonterminalSymbolSetChangedListener (
      NonterminalSymbolSetChangedListener listener );


  /**
   * Removes all {@link NonterminalSymbol}s.
   */
  public void clear ();


  /**
   * Returns true if this {@link NonterminalSymbolSet} contains the specified
   * {@link NonterminalSymbol}.
   * 
   * @param nonterminalSymbol {@link NonterminalSymbol} whose presence in this
   *          {@link NonterminalSymbolSet} is to be tested.
   * @return true if the specified {@link NonterminalSymbol} is present; false
   *         otherwise.
   */
  public boolean contains ( NonterminalSymbol nonterminalSymbol );


  /**
   * Returns the {@link NonterminalSymbol}s.
   * 
   * @return The {@link NonterminalSymbol}s.
   */
  public TreeSet < NonterminalSymbol > get ();


  /**
   * Returns the {@link NonterminalSymbol} with the given index.
   * 
   * @param index The index.
   * @return The {@link NonterminalSymbol} with the given index.
   */
  public NonterminalSymbol get ( int index );


  /**
   * Returns the {@link NonterminalSymbol} with the given name.
   *
   * @param name The name
   * @return The {@link NonterminalSymbol} with the given name
   */
  public NonterminalSymbol get ( String name );


  /**
   * Remove the given {@link NonterminalSymbol}s from this
   * {@link NonterminalSymbolSet}.
   * 
   * @param nonterminalSymbols The {@link NonterminalSymbol}s to remove.
   */
  public void remove ( Iterable < NonterminalSymbol > nonterminalSymbols );


  /**
   * Removes the given {@link NonterminalSymbol} from this
   * {@link NonterminalSymbolSet}.
   * 
   * @param nonterminalSymbol The {@link NonterminalSymbol} to remove.
   */
  public void remove ( NonterminalSymbol nonterminalSymbol );


  /**
   * Remove the given {@link NonterminalSymbol}s from this
   * {@link NonterminalSymbolSet}.
   * 
   * @param nonterminalSymbols The {@link NonterminalSymbol}s to remove.
   */
  public void remove ( NonterminalSymbol ... nonterminalSymbols );


  /**
   * Removes the given {@link NonterminalSymbolSetChangedListener}.
   * 
   * @param listener The {@link NonterminalSymbolSetChangedListener}.
   */
  public void removeNonterminalSymbolSetChangedListener (
      NonterminalSymbolSetChangedListener listener );


  /**
   * Returns the number of {@link NonterminalSymbol}s in this
   * {@link NonterminalSymbolSet}.
   * 
   * @return The number of {@link NonterminalSymbol}s in this
   *         {@link NonterminalSymbolSet}.
   */
  public int size ();
}
