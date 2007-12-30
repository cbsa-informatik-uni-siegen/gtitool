package de.unisiegen.gtitool.ui.model;


import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.machine.MachineAllSymbolsException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineEpsilonTransitionException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineStateFinalException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineStateNameException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineStateNotReachableException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineStateStartException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineSymbolOnlyOneTimeException;


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
     * Allocates a new <code>ConsoleTableEntry</code>.
     * 
     * @param pMessage The message.
     * @param pDescrition The description.
     * @param pStates The {@link State}s.
     * @param pTransitions The {@link Transition}s.
     * @param pSymbols The {@link Symbol}s.
     */
    public ConsoleTableEntry ( String pMessage, String pDescrition,
        ArrayList < State > pStates, ArrayList < Transition > pTransitions,
        ArrayList < Symbol > pSymbols )
    {
      this.message = pMessage;
      this.description = pDescrition;
      this.states = pStates;
      this.transitions = pTransitions;
      this.symbols = pSymbols;
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
   * Allocates a new <code>ConsoleTableModel</code>.
   */
  public ConsoleTableModel ()
  {
    this.data = new ArrayList < ConsoleTableEntry > ();
  }


  /**
   * Add a row to this data model
   * 
   * @param pMachineException the MachineException containing the data for the
   *          new row
   */
  public final void addRow ( MachineException pMachineException )
  {
    ArrayList < State > states = new ArrayList < State > ();
    ArrayList < Transition > transitions = new ArrayList < Transition > ();
    ArrayList < Symbol > symbols = new ArrayList < Symbol > ();

    if ( pMachineException instanceof MachineStateNameException )
    {
      MachineStateNameException exception = ( MachineStateNameException ) pMachineException;
      states.addAll ( exception.getStateList () );
    }
    else if ( pMachineException instanceof MachineStateStartException )
    {
      MachineStateStartException exception = ( MachineStateStartException ) pMachineException;
      states.addAll ( exception.getStateList () );
    }
    else if ( pMachineException instanceof MachineAllSymbolsException )
    {
      MachineAllSymbolsException exception = ( MachineAllSymbolsException ) pMachineException;
      states.add ( exception.getState () );
      symbols.addAll ( exception.getSymbol () );
    }
    else if ( pMachineException instanceof MachineEpsilonTransitionException )
    {
      MachineEpsilonTransitionException exception = ( MachineEpsilonTransitionException ) pMachineException;
      states.add ( exception.getStateBegin () );
      states.add ( exception.getStateEnd () );
      transitions.add ( exception.getTransition () );
    }
    else if ( pMachineException instanceof MachineStateFinalException )
    {
      // Do nothing
    }
    else if ( pMachineException instanceof MachineStateNotReachableException )
    {
      MachineStateNotReachableException exception = ( MachineStateNotReachableException ) pMachineException;
      states.add ( exception.getState () );
    }
    else if ( pMachineException instanceof MachineSymbolOnlyOneTimeException )
    {
      MachineSymbolOnlyOneTimeException exception = ( MachineSymbolOnlyOneTimeException ) pMachineException;
      states.add ( exception.getState () );
      symbols.add ( exception.getSymbol () );
      transitions.addAll ( exception.getTransitionList () );
    }

    this.data.add ( new ConsoleTableEntry ( pMachineException.getMessage (),
        pMachineException.getDescription (), states, transitions, symbols ) );
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
   * @param pRowIndex The given row index.
   * @return The {@link State}s of the given row index.
   */
  public final ArrayList < State > getStates ( int pRowIndex )
  {
    return this.data.get ( pRowIndex ).states;
  }


  /**
   * Returns the {@link Symbol}s of the given row index.
   * 
   * @param pRowIndex The given row index.
   * @return The {@link Symbol}s of the given row index.
   */
  public final ArrayList < Symbol > getSymbols ( int pRowIndex )
  {
    return this.data.get ( pRowIndex ).symbols;
  }


  /**
   * Returns the {@link Transition}s of the given row index.
   * 
   * @param pRowIndex The given row index.
   * @return The {@link Transition}s of the given row index.
   */
  public final ArrayList < Transition > getTransitions ( int pRowIndex )
  {
    return this.data.get ( pRowIndex ).transitions;
  }


  /**
   * {@inheritDoc}
   * 
   * @see TableModel#getValueAt(int, int)
   */
  public final Object getValueAt ( int pRowIndex, int pColumnIndex )
  {
    switch ( pColumnIndex )
    {
      case MESSAGE_COLUMN :
      {
        return this.data.get ( pRowIndex ).message;
      }
      case DESCRIPTION_COLUMN :
      {
        return this.data.get ( pRowIndex ).description;
      }
      case STATES_COLUMN :
      {
        return this.data.get ( pRowIndex ).states;
      }
      case TRANSITIONS_COLUMN :
      {
        return this.data.get ( pRowIndex ).transitions;
      }
      case SYMBOL_COLUMN :
      {
        return this.data.get ( pRowIndex ).symbols;
      }
    }
    return null;
  }
}
