package de.unisiegen.gtitool.core.entities;


import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.listener.TerminalSymbolSetChangedListener;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * The {@link TerminalSymbolSet} entity.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface TerminalSymbolSet extends Entity, Storable, Modifyable,
    Iterable < TerminalSymbol >
{

  /**
   * Appends the specified {@link TerminalSymbol}s to the end of this
   * {@link TerminalSymbolSet}.
   * 
   * @param terminalSymbols The {@link TerminalSymbol}s to be appended to this
   *          {@link TerminalSymbolSet}.
   * @throws TerminalSymbolSetException If something with the
   *           {@link TerminalSymbolSet} is not correct.
   */
  public void add ( Iterable < TerminalSymbol > terminalSymbols )
      throws TerminalSymbolSetException;


  /**
   * Appends the specified {@link TerminalSymbol} to the end of this
   * {@link TerminalSymbolSet}.
   * 
   * @param terminalSymbol The {@link TerminalSymbol} to be appended to this
   *          {@link TerminalSymbolSet}.
   * @throws TerminalSymbolSetException If something with the
   *           {@link TerminalSymbolSet} is not correct.
   */
  public void add ( TerminalSymbol terminalSymbol )
      throws TerminalSymbolSetException;


  /**
   * Appends the specified {@link TerminalSymbol}s to the end of this
   * {@link TerminalSymbolSet}.
   * 
   * @param terminalSymbols The {@link TerminalSymbol}s to be appended to this
   *          {@link TerminalSymbolSet}.
   * @throws TerminalSymbolSetException If something with the
   *           {@link TerminalSymbolSet} is not correct.
   */
  public void add ( TerminalSymbol ... terminalSymbols )
      throws TerminalSymbolSetException;


  /**
   * Adds the given {@link TerminalSymbolSetChangedListener}.
   * 
   * @param listener The {@link TerminalSymbolSetChangedListener}.
   */
  public void addTerminalSymbolSetChangedListener (
      TerminalSymbolSetChangedListener listener );


  /**
   * Removes all {@link TerminalSymbol}s.
   */
  public void clear ();


  /**
   * {@inheritDoc}
   * 
   * @see Entity#clone()
   */
  public TerminalSymbolSet clone ();


  /**
   * Returns true if this {@link TerminalSymbolSet} contains the specified
   * {@link TerminalSymbol}.
   * 
   * @param terminalSymbol {@link TerminalSymbol} whose presence in this
   *          {@link TerminalSymbolSet} is to be tested.
   * @return true if the specified {@link TerminalSymbol} is present; false
   *         otherwise.
   */
  public boolean contains ( TerminalSymbol terminalSymbol );


  /**
   * Returns the {@link TerminalSymbol}s.
   * 
   * @return The {@link TerminalSymbol}s.
   */
  public TreeSet < TerminalSymbol > get ();


  /**
   * Returns the {@link TerminalSymbol} with the given index.
   * 
   * @param index The index.
   * @return The {@link TerminalSymbol} with the given index.
   */
  public TerminalSymbol get ( int index );


  /**
   * Remove the given {@link TerminalSymbol}s from this
   * {@link TerminalSymbolSet}.
   * 
   * @param terminalSymbols The {@link TerminalSymbol}s to remove.
   */
  public void remove ( Iterable < TerminalSymbol > terminalSymbols );


  /**
   * Removes the given {@link TerminalSymbol} from this
   * {@link TerminalSymbolSet}.
   * 
   * @param terminalSymbol The {@link TerminalSymbol} to remove.
   */
  public void remove ( TerminalSymbol terminalSymbol );


  /**
   * Remove the given {@link TerminalSymbol}s from this
   * {@link TerminalSymbolSet}.
   * 
   * @param terminalSymbols The {@link TerminalSymbol}s to remove.
   */
  public void remove ( TerminalSymbol ... terminalSymbols );


  /**
   * Removes the given {@link TerminalSymbolSetChangedListener}.
   * 
   * @param listener The {@link TerminalSymbolSetChangedListener}.
   */
  public void removeTerminalSymbolSetChangedListener (
      TerminalSymbolSetChangedListener listener );


  /**
   * Returns the number of {@link TerminalSymbol}s in this
   * {@link TerminalSymbolSet}.
   * 
   * @return The number of {@link TerminalSymbol}s in this
   *         {@link TerminalSymbolSet}.
   */
  public int size ();
}
