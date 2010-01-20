package de.unisiegen.gtitool.ui.model;

import javax.swing.table.AbstractTableModel;


/**
 * the table model for the table (parser movements)
 * 
 * @author Christian Uhrhan
 *
 */
public class TDPModel extends AbstractTableModel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -6693897333759433432L;

  /**
   * the stack column
   */
  private final static int STACK_COLUMN = 0;
  
  /**
   * the input column
   */
  private final static int INPUT_COLUMN = 1;
  
  /**
   * the action column
   */
  private final static int ACTION_COLUMN = 2;
  
  /**
   * the column count
   */
  private final static int COLUMN_COUNT = 3;

  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getColumnCount()
   */
  public int getColumnCount ()
  {
    return TDPModel.COLUMN_COUNT;
  }


  /**
   * TODO
   *
   * @return
   * @see javax.swing.table.TableModel#getRowCount()
   */
  public int getRowCount ()
  {
    return 0;
  }


  /**
   * TODO
   *
   * @param arg0
   * @param arg1
   * @return
   * @see javax.swing.table.TableModel#getValueAt(int, int)
   */
  public Object getValueAt ( int arg0, int arg1 )
  {
    return null;
  }

}
