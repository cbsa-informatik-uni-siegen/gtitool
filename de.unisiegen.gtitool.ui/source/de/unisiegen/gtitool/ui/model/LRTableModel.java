package de.unisiegen.gtitool.ui.model;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.LRState;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.parser.style.PrettyString;


/**
 * TODO
 */
public class LRTableModel extends StatelessMachineTableModel
{

  /**
   * TODO
   */
  private static final long serialVersionUID = -5402915280270115303L;


  /**
   * TODO
   * 
   * @param states
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
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getColumnCount()
   */
  public int getColumnCount ()
  {
    return this.terminals.size ();// + 1;
  }


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getRowCount()
   */
  @Override
  public int getRowCount ()
  {
    return this.states.size ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getValueAt(int, int)
   */
  public PrettyString getValueAt ( final int rowIndex, final int columnIndex )
  {
    return columnIndex == 0 ? this.states.get ( rowIndex ).shortName ()
        : this.entries.get ( columnIndex - 2 ).get ( rowIndex );
  }


  /**
   * The states
   */
  private ArrayList < LRState > states;


  /**
   * The terminals
   */
  private TerminalSymbolSet terminals;


  /**
   * The entries for the table
   */
  private ArrayList < ArrayList < PrettyString > > entries;
}
