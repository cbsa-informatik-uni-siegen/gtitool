package de.unisiegen.gtitool.ui.model;


import javax.swing.table.AbstractTableModel;

import de.unisiegen.gtitool.core.entities.DefaultParsingTable;


/**
 * the model for the TDP (PDA) parsing table
 * 
 * @author Christian Uhrhan
 */
public final class PTTableModel extends AbstractTableModel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -316758216882858877L;


  /**
   * the nonterminal column
   */
  private static final int NONTERMINAL_COLUMN = 0;


  /**
   * the first column containing a terminal symbol
   */
  private static final int FIRST_TERMINAL_COLUMN = 1;


  /**
   * the underlying data object of the jtable
   */
  private DefaultParsingTable data;


  /**
   * @{inherit
   * @see javax.swing.table.AbstractTableModel#getColumnName(int)
   */
  @Override
  public final String getColumnName (
      @SuppressWarnings ( "unused" ) int columnIndex )
  {
    return ""; //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getColumnCount()
   */
  public int getColumnCount ()
  {
    return 1;
  }


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getRowCount()
   */
  public int getRowCount ()
  {
    return 1;
  }


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getValueAt(int, int)
   */
  public Object getValueAt ( int arg0, int arg1 )
  {
    return "test";
  }

}
