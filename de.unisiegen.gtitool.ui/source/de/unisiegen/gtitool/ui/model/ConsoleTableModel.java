package de.unisiegen.gtitool.ui.model;


import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.StatesInvolvedException;
import de.unisiegen.gtitool.core.exceptions.SymbolsInvolvedException;
import de.unisiegen.gtitool.core.exceptions.TransitionsInvolvedException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineException;


/**
 * The table model for the warning and error tables.
 * 
 * @author Benjamin Mies
 * @author Christian Fehler
 * @version $Id:DefaultTableModel.java 305 2007-12-06 19:55:14Z mies $
 */
public final class ConsoleTableModel extends AbstractTableModel
{

  /**
   * The console table entry.
   * 
   * @author Christian Fehler
   */
  private final class ConsoleTableEntry
  {

    /**
     * The message.
     */
    public String message;


    /**
     * The description.
     */
    public String description;


    /**
     * The {@link State}s.
     */
    public ArrayList < State > states = new ArrayList < State > ();


    /**
     * The {@link Transition}s.
     */
    public ArrayList < Transition > transitions = new ArrayList < Transition > ();


    /**
     * The {@link Symbol}s.
     */
    public ArrayList < Symbol > symbols = new ArrayList < Symbol > ();


    /**
     * Allocates a new {@link ConsoleTableEntry}.
     * 
     * @param message The message.
     * @param descrition The description.
     * @param states The {@link State}s.
     * @param transitions The {@link Transition}s.
     * @param symbols The {@link Symbol}s.
     */
    public ConsoleTableEntry ( String message, String descrition,
        ArrayList < State > states, ArrayList < Transition > transitions,
        ArrayList < Symbol > symbols )
    {
      this.message = message;
      this.description = descrition;
      this.states = states;
      this.transitions = transitions;
      this.symbols = symbols;
    }
  }


  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 4029649018802825113L;


  /**
   * The message column
   */
  public final static int MESSAGE_COLUMN = 0;


  /**
   * The description column
   */
  public final static int DESCRIPTION_COLUMN = 1;


  /**
   * The {@link State} column
   */
  public final static int STATES_COLUMN = 2;


  /**
   * The {@link Transition} column
   */
  public final static int TRANSITIONS_COLUMN = 3;


  /**
   * The {@link Symbol} column
   */
  public final static int SYMBOL_COLUMN = 4;


  /**
   * The column count
   */
  public final static int COLUMN_COUNT = 5;


  /**
   * The data of this table model
   */
  private ArrayList < ConsoleTableEntry > data;


  /**
   * Allocates a new {@link ConsoleTableModel}.
   */
  public ConsoleTableModel ()
  {
    this.data = new ArrayList < ConsoleTableEntry > ();
  }


  /**
   * Add a row to this data model
   * 
   * @param machineException the MachineException containing the data for the
   *          new row
   */
  public final void addRow ( MachineException machineException )
  {
    ArrayList < State > states = new ArrayList < State > ();
    ArrayList < Transition > transitions = new ArrayList < Transition > ();
    ArrayList < Symbol > symbols = new ArrayList < Symbol > ();

    // State
    if ( machineException instanceof StatesInvolvedException )
    {
      StatesInvolvedException exception = ( StatesInvolvedException ) machineException;
      states.addAll ( exception.getState () );
    }

    // Transition
    if ( machineException instanceof TransitionsInvolvedException )
    {
      TransitionsInvolvedException exception = ( TransitionsInvolvedException ) machineException;
      transitions.addAll ( exception.getTransition () );
    }

    // Symbol
    if ( machineException instanceof SymbolsInvolvedException )
    {
      SymbolsInvolvedException exception = ( SymbolsInvolvedException ) machineException;
      symbols.addAll ( exception.getSymbol () );
    }

    this.data.add ( new ConsoleTableEntry ( machineException.getMessage (),
        machineException.getDescription (), states, transitions, symbols ) );
    fireTableRowsInserted ( this.data.size () - 1, this.data.size () - 1 );
  }


  /**
   * Clear the data of this table model
   */
  public final void clearData ()
  {
    this.data.clear ();
    fireTableDataChanged ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see TableModel#getColumnCount()
   */
  public final int getColumnCount ()
  {
    return COLUMN_COUNT;
  }


  /**
   * {@inheritDoc}
   * 
   * @see TableModel#getRowCount()
   */
  public final int getRowCount ()
  {
    return this.data.size ();
  }


  /**
   * Returns the {@link State}s of the given row index.
   * 
   * @param rowIndex The given row index.
   * @return The {@link State}s of the given row index.
   */
  public final ArrayList < State > getStates ( int rowIndex )
  {
    return this.data.get ( rowIndex ).states;
  }


  /**
   * Returns the {@link Symbol}s of the given row index.
   * 
   * @param rowIndex The given row index.
   * @return The {@link Symbol}s of the given row index.
   */
  public final ArrayList < Symbol > getSymbols ( int rowIndex )
  {
    return this.data.get ( rowIndex ).symbols;
  }


  /**
   * Returns the {@link Transition}s of the given row index.
   * 
   * @param rowIndex The given row index.
   * @return The {@link Transition}s of the given row index.
   */
  public final ArrayList < Transition > getTransitions ( int rowIndex )
  {
    return this.data.get ( rowIndex ).transitions;
  }


  /**
   * {@inheritDoc}
   * 
   * @see TableModel#getValueAt(int, int)
   */
  public final Object getValueAt ( int rowIndex, int columnIndex )
  {
    switch ( columnIndex )
    {
      case MESSAGE_COLUMN :
      {
        return this.data.get ( rowIndex ).message;
      }
      case DESCRIPTION_COLUMN :
      {
        return this.data.get ( rowIndex ).description;
      }
      case STATES_COLUMN :
      {
        return this.data.get ( rowIndex ).states;
      }
      case TRANSITIONS_COLUMN :
      {
        return this.data.get ( rowIndex ).transitions;
      }
      case SYMBOL_COLUMN :
      {
        return this.data.get ( rowIndex ).symbols;
      }
    }
    return null;
  }
}
