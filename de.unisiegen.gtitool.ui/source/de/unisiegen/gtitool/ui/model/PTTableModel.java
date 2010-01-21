package de.unisiegen.gtitool.ui.model;

import javax.swing.table.AbstractTableModel;


/**
 * the model for the TDP (PDA) parsing table
 * 
 * @author Christian Uhrhan
 *
 */
public final class PTTableModel extends AbstractTableModel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -316758216882858877L;
  
  private String data;

  @Override
  public final String getColumnName(int columnIndex)
  {
    if(columnIndex == 0)
      return "ParsingTable";
    return "";
  }

  /**
   * TODO
   *
   * @return
   * @see javax.swing.table.TableModel#getColumnCount()
   */
  public int getColumnCount ()
  {
    return 1;
  }


  /**
   * TODO
   *
   * @return
   * @see javax.swing.table.TableModel#getRowCount()
   */
  public int getRowCount ()
  {
    return 1;
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
    return "test";
  }

}
