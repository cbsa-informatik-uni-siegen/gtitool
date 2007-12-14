package de.unisiegen.gtitool.ui.model;


import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.machine.MachineAllSymbolsException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineEpsilonTransitionException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineStateException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineStateNameException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineStateStartException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineSymbolOnlyOneTimeException;


/**
 * The Table Model for the warning and error tables
 * 
 * @author Benjamin Mies
 * @author Christian Fehler
 * @version $Id:DefaultTableModel.java 305 2007-12-06 19:55:14Z mies $
 */
public final class ConsoleTableModel extends AbstractTableModel
{

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
   * The states column
   */
  public final static int STATES_COLUMN = 2;


  /**
   * The transitions column
   */
  public final static int TRANSITIONS_COLUMN = 3;


  /**
   * The column count
   */
  public final static int COLUMN_COUNT = 4;


  /**
   * The data of this table model
   */
  private ArrayList < Object [] > data;


  /**
   * Allocates a new <code>ConsoleTableModel</code>.
   */
  public ConsoleTableModel ()
  {
    this.data = new ArrayList < Object [] > ();
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

    if ( pMachineException instanceof MachineAllSymbolsException )
    {
      states.add ( ( ( MachineAllSymbolsException ) pMachineException )
          .getState () );
    }

    if ( pMachineException instanceof MachineEpsilonTransitionException )
    {
      states.add ( ( ( MachineEpsilonTransitionException ) pMachineException )
          .getStateBegin () );
      states.add ( ( ( MachineEpsilonTransitionException ) pMachineException )
          .getStateEnd () );
      transitions
          .add ( ( ( MachineEpsilonTransitionException ) pMachineException )
              .getTransition () );
    }

    if ( pMachineException instanceof MachineStateException )
    {
      states.addAll ( ( ( MachineStateException ) pMachineException )
          .getStateList () );
    }

    if ( pMachineException instanceof MachineStateNameException )
    {
      states.addAll ( ( ( MachineStateNameException ) pMachineException )
          .getStateList () );
    }

    if ( pMachineException instanceof MachineStateStartException )
    {
      states.addAll ( ( ( MachineStateStartException ) pMachineException )
          .getStateList () );
    }

    if ( pMachineException instanceof MachineSymbolOnlyOneTimeException )
    {
      states.add ( ( ( MachineSymbolOnlyOneTimeException ) pMachineException )
          .getState () );
      transitions
          .addAll ( ( ( MachineSymbolOnlyOneTimeException ) pMachineException )
              .getTransitionList () );
    }

    this.data.add ( new Object []
    { pMachineException.getMessage () ,pMachineException.getDescription (), states, transitions } );
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
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getValueAt(int, int)
   */
  public final Object getValueAt ( int pRowIndex, int pColumnIndex )
  {
    return this.data.get ( pRowIndex ) [ pColumnIndex ];
  }

}
