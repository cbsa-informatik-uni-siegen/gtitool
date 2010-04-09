package de.unisiegen.gtitool.ui.model;


import javax.swing.table.AbstractTableModel;


/**
 * TODO
 */
public class LRSetTableModel extends AbstractTableModel
{

  /**
   * TODO
   */
  private static final long serialVersionUID = 6968918861124621294L;


  /**
   * TODO
   *
   * @param columnModel
   */
  public LRSetTableModel ( final LRSetTableColumnModel columnModel )
  {
    this.columnModel = columnModel;
  }


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getColumnCount()
   */
  public int getColumnCount ()
  {
    return this.columnModel.getColumnCount ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getRowCount()
   */
  public int getRowCount ()
  {
    return this.columnModel.getRowCount ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getValueAt(int, int)
   */
  public Object getValueAt ( final int rowIndex, final int columnIndex )
  {
    return this.columnModel.getEntry ( rowIndex, columnIndex );
  }


  /**
   * TODO
   */
  private final LRSetTableColumnModel columnModel;
}
