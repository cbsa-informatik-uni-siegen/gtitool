package de.unisiegen.gtitool.core.entities;


import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.listener.TerminalSymbolSetChangedListener;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.storage.Modifyable;
import de.unisiegen.gtitool.core.storage.Storable;


/**
 * Implementation of the first set (see dragon book)
 */
public interface FirstSet extends Entity < TerminalSymbolSet >, Storable,
    Modifyable, Iterable < TerminalSymbol >
{

  /**
   * returns whether epsilon is in this FirstSet or not
   * 
   * @return true if epsilon is in this FirstSet
   */
  public boolean epsilon ();


  /**
   * sets epsilon to be in this set
   * 
   * @param epsilon true to add epsilon to this FirstSet
   */
  public void epsilon ( boolean epsilon );


  /**
   * TODO
   *
   * @param terminalSymbols
   * @throws TerminalSymbolSetException
   */
  public void add ( Iterable < TerminalSymbol > terminalSymbols )
      throws TerminalSymbolSetException;


  /**
   * TODO
   *
   * @param terminalSymbol
   * @throws TerminalSymbolSetException
   */
  public void add ( TerminalSymbol terminalSymbol )
      throws TerminalSymbolSetException;

  /**
   * TODO
   *
   * @param listener
   */
  public void addTerminalSymbolSetChangedListener (
      TerminalSymbolSetChangedListener listener );
  
  /**
   * clears the set
   *
   */
  public void clear ();
  
  /**
   * checks whether terminalSymbol is in this set or not
   * 
   * @param terminalSymbol the symbol we're checking against
   *                       the other ones
   * @return true if terminalSymbol is in the set, false otherwise
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
  
  
  /**
   * 
   * returns if last set-operation modified the set
   *
   * @return true, if last set-operation modified the set
   */
  public boolean modified();
}
