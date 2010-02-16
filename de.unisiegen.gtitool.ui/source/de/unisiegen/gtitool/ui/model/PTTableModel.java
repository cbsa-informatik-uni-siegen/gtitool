package de.unisiegen.gtitool.ui.model;


import javax.swing.table.AbstractTableModel;

import de.unisiegen.gtitool.core.entities.DefaultParsingTable;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;


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
   * set of nonterminals (row description)
   */
  private NonterminalSymbolSet nonterminals;


  /**
   * the underlying data object of the jtable
   */
  private DefaultParsingTable data;


  /**
   * allocates a new {@link PTTableModel}
   * 
   * @param cfg the grammar from which we're creating the parsing table
   */
  public PTTableModel ( final CFG cfg )
  {
    try
    {
      this.data = new DefaultParsingTable ( cfg );
      this.nonterminals = cfg.getNonterminalSymbolSet ();
    }
    catch ( GrammarInvalidNonterminalException exc )
    {
      exc.printStackTrace ();
    }
    catch ( TerminalSymbolSetException exc )
    {
      exc.printStackTrace ();
    }
  }


  /**
   * @{inherit}
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
    return this.data.getColumnCount ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getRowCount()
   */
  public int getRowCount ()
  {
    return this.data.getRowCount ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getValueAt(int, int)
   */
  public Object getValueAt ( int arg0, int arg1 )
  {
    if ( arg1 == NONTERMINAL_COLUMN )
      return this.nonterminals.get ( arg0 );
    // arg1 - 1 cause we have one column more (the nonterminal/row description)
    return this.data.get ( arg0, arg1 - FIRST_TERMINAL_COLUMN );
  }
}
