package de.unisiegen.gtitool.ui.model;


import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import de.unisiegen.gtitool.core.entities.LRState;
import de.unisiegen.gtitool.core.machines.dfa.AbstractLR;


/**
 * TODO
 */
public class LRSetTableModel extends AbstractTableModel
{

  /**
   * TODO
   */
  private static final long serialVersionUID = 6968918861124621294L;


  public LRSetTableModel ( final AbstractLR automaton )
  {
    this.automaton = automaton;
  }


  /**
   * TODO
   * 
   * @return
   * @see javax.swing.table.TableModel#getColumnCount()
   */
  public int getColumnCount ()
  {
    return this.activeStates.size ();
  }


  /**
   * TODO
   * 
   * @return
   * @see javax.swing.table.TableModel#getRowCount()
   */
  public int getRowCount ()
  {
    int result = 0;

    for ( LRState state : this.activeStates )
      result = Math.max ( state.getItems ().size (), result );

    return result;
  }


  /**
   * TODO
   * 
   * @param rowIndex
   * @param columnIndex
   * @return
   * @see javax.swing.table.TableModel#getValueAt(int, int)
   */
  public Object getValueAt ( int rowIndex, int columnIndex )
  {
    return this.activeStates.get ( columnIndex ).getItems ().stringEntries ()
        .get ( rowIndex );
  }


  private AbstractLR automaton;


  private ArrayList < LRState > activeStates = new ArrayList < LRState > ();
}
