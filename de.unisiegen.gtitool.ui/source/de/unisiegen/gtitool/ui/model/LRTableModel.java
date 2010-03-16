package de.unisiegen.gtitool.ui.model;


import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import de.unisiegen.gtitool.core.entities.LRItemSet;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.parser.style.PrettyString;


/**
 * TODO
 */
public class LRTableModel extends AbstractTableModel
{

  /**
   * TODO
   */
  private static final long serialVersionUID = -5402915280270115303L;


  /**
   * TODO
   * 
   * @param items
   * @param terminals
   * @param entries
   */
  public LRTableModel ( final ArrayList < LRItemSet > items,
      final TerminalSymbolSet terminals,
      final ArrayList < ArrayList < PrettyString > > entries )
  {
    this.items = items;

    this.terminals = terminals;

    this.entries = entries;
  }


  /**
   * TODO
   * 
   * @return
   * @see javax.swing.table.TableModel#getColumnCount()
   */
  public int getColumnCount ()
  {
    return this.terminals.size () + 1;
  }


  /**
   * TODO
   * 
   * @return
   * @see javax.swing.table.TableModel#getRowCount()
   */
  public int getRowCount ()
  {
    return this.items.size ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getValueAt(int, int)
   */
  public Object getValueAt ( final int rowIndex, final int columnIndex )
  {
    final PrettyString ret = columnIndex == 0 ? this.items.get ( rowIndex )
        .toPrettyString () : this.entries.get ( columnIndex - 1 ).get (
        rowIndex );

    return ret;
  }


  /**
   * TODO
   */
  private ArrayList < LRItemSet > items;


  /**
   * TODO
   */
  private TerminalSymbolSet terminals;


  /**
   * TODO
   */
  private ArrayList < ArrayList < PrettyString > > entries;
}
