package de.unisiegen.gtitool.ui.model;


import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import de.unisiegen.gtitool.core.entities.LRState;
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
  public LRTableModel ( final ArrayList < LRState > states,
      final TerminalSymbolSet terminals,
      final ArrayList < ArrayList < PrettyString > > entries )
  {
    this.states = states;

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
    return this.states.size ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getValueAt(int, int)
   */
  public Object getValueAt ( final int rowIndex, final int columnIndex )
  {
    final PrettyString ret = columnIndex == 0 ? this.states.get ( rowIndex )
        .shortName () : this.entries.get ( columnIndex - 1 ).get (
        rowIndex );

    return ret;
  }


  /**
   * TODO
   */
  private ArrayList < LRState > states;


  /**
   * TODO
   */
  private TerminalSymbolSet terminals;


  /**
   * TODO
   */
  private ArrayList < ArrayList < PrettyString > > entries;
}
