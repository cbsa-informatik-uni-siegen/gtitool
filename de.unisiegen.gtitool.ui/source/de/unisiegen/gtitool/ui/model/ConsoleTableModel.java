package de.unisiegen.gtitool.ui.model;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

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
 * @version $Id:DefaultTableModel.java 305 2007-12-06 19:55:14Z mies $
 */
public class ConsoleTableModel extends AbstractTableModel
{
  
  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 4029649018802825113L;

  /** The description column  */
  public static int DESCRIPTION_COLUMN = 0 ;
  
  /** The states column  */
  public static int STATES_COLUMN = 1 ;
  
  /** The transitions column  */
  public static int TRANSITIONS_COLUMN = 2 ;
  
  /** The column count */
  public static int COLUMN_COUNT = 3 ;
  
  /** The data of this table model */
  private ArrayList < Object [] > data = new ArrayList < Object [] > ();

  /**
   * {@inheritDoc}
   * @see javax.swing.table.TableModel#getColumnCount()
   */
  public int getColumnCount ()
  {
    return COLUMN_COUNT;
  }


  /**
   * {@inheritDoc}
   * @see javax.swing.table.TableModel#getRowCount()
   */
  public int getRowCount ()
  {
    return this.data.size ();
  }


  /**
   * {@inheritDoc}
   * @see javax.swing.table.TableModel#getValueAt(int, int)
   */
  public Object getValueAt ( int rowIndex, int columnIndex )
  {
    return this.data.get ( rowIndex ) [columnIndex];
  }
  
  /** 
   * 
   * Add a row to this data model
   *
   * @param e the MachineException containing the data for the new row
   */
  public void addRow (MachineException e){
    ArrayList < State > states = new ArrayList < State > ();
    ArrayList < Transition > transitions = new ArrayList < Transition > ();
    
    if ( e instanceof MachineAllSymbolsException ) {
      states.add( ( ( MachineAllSymbolsException ) e ).getState () );
    }
    if ( e instanceof MachineEpsilonTransitionException ) {
      states.add( ( ( MachineEpsilonTransitionException ) e ).getStateBegin () );
      states.add( ( ( MachineEpsilonTransitionException ) e ).getStateEnd () );
      transitions.add( ( ( MachineEpsilonTransitionException ) e ).getTransition () );
    }
    if ( e instanceof MachineStateException ) {
      states.addAll( ( ( MachineStateException ) e ).getStateList () );
    }
    if ( e instanceof MachineStateNameException ) {
      states.addAll( ( ( MachineStateNameException ) e ).getStateList ());
    }
    if ( e instanceof MachineStateStartException ) {
      states.addAll( ( ( MachineStateStartException ) e ).getStateList () );
    }
    
    if ( e instanceof MachineSymbolOnlyOneTimeException ) {
      states.add( ( ( MachineSymbolOnlyOneTimeException ) e ).getState () );
      transitions.addAll( ( ( MachineSymbolOnlyOneTimeException ) e ).getTransitionList () );
    }
    this.data.add ( new Object[] { e.getDescription (), states, transitions } );
    fireTableRowsInserted ( this.data.size ()-1, this.data.size ()-1 );
  }
  
  /**
   * Clear the data of this table model
   */
  public void clearData() {
    this.data.clear ();
    fireTableDataChanged ();
  }

}
