package de.unisiegen.gtitool.ui.model;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.ui.utils.StateList;


/**
 * The Table Model for the warning and error tables
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public class MachineTableModel extends AbstractTableModel
{
  
  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -2416930446859200288L;
  
  /** The {@link Alphabet} */
  private Alphabet alphabet ;
  
  /** The states and the coresponding row */
  private HashMap < State, Integer > states = new HashMap < State, Integer > ();
  
  /**The Symbols and the coresponding column */
  private HashMap < Symbol, Integer > symbols = new HashMap < Symbol, Integer > ();
  
  /**
   * 
   * Allocate a new <code>MachineTableModel</code>
   *
   * @param pAlphabet The {@link Alphabet}
   */
  public MachineTableModel ( Alphabet pAlphabet ) {
    this.alphabet = pAlphabet;
    initialize();
  }

  
  /** The data of this table model */
  private ArrayList < Object [] > data = new ArrayList < Object [] > ();

  /**
   * {@inheritDoc}
   * @see javax.swing.table.TableModel#getColumnCount()
   */
  public int getColumnCount ()
  {
    return this.alphabet.symbolSize () + 1;
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
   * Add a state to this data model
   *
   * @param state The {@link State} to add
   */
  public void addState (State state){
    this.states.put (state, new Integer(this.data.size () ) );
    Object [] row = new Object [getColumnCount ()];
    row[0] = state;
    for ( int i = 1; i < getColumnCount () ; i ++ ){
      row[i] = new StateList < State > ();
    }
    this.data.add ( row );
    fireTableRowsInserted ( this.data.size ()-1, this.data.size ()-1 );
  }
  
  public void removeState (State state){
    int index = this.states.get ( state ).intValue ();
    this.data.remove ( state );
    // TODOBenny
    fireTableDataChanged ();
  }
  
  /** 
   * 
   * Add a transition to this data model
   *
   * @param transition The {@link Transition} to add
   */
  @SuppressWarnings("unchecked")
  public void addTransition (Transition transition){
    int row = this.states.get ( transition.getStateBegin () ).intValue () ;
    
    for (Symbol symbol : transition.getSymbol ()){
      int column = this.symbols.get ( symbol ).intValue () + 1 ;
      ( ( StateList < State > ) this.data.get ( row )[column] ).add( transition.getStateEnd () );
    }
    fireTableDataChanged ();
  }
  
  @SuppressWarnings("unchecked")
  public void removeTransition (Transition transition){
   int row = this.states.get ( transition.getStateBegin () ).intValue () ;
    
    for (Symbol symbol : transition.getSymbol ()){
      int column = this.symbols.get ( symbol ).intValue () + 1 ;
      ( ( StateList < State > ) this.data.get ( row )[column] ).remove ( transition.getStateEnd () );
    }
    fireTableDataChanged ();
  }
  
  /**
   * Clear the data of this table model
   */
  public void clearData() {
    this.data.clear ();
    fireTableDataChanged ();
  }
  
  /**
   * 
   * Initialize this table model
   *
   */
  private void initialize(){
    for ( int i = 0 ; i < this.alphabet.symbolSize () ; i++ ) {
      this.symbols.put(this.alphabet.getSymbol ( i ), new Integer ( i ) ) ;
    }
  }

}
