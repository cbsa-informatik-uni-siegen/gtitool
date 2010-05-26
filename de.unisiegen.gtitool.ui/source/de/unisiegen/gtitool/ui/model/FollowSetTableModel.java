package de.unisiegen.gtitool.ui.model;


import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.parser.style.PrettyString;


/**
 * Table model to present <Nonterminal,FollowSet> data
 * 
 * @author Christian Uhrhan
 */
public class FollowSetTableModel extends AbstractTableModel
{

  /**
   * The generated serial
   */
  private static final long serialVersionUID = 2021815057054014805L;


  /**
   * The {@link NonterminalSymbol} column index
   */
  private static final int NONTERMINAL_COLUMN = 0;


  /**
   * The follow set column index
   */
  private static final int FOLLOW_SET_COLUMN = 1;


  /**
   * Number of columns
   */
  private static final int COLUMN_COUNT = 2;


  /**
   * The {@link NonterminalSymbolSet}
   */
  private NonterminalSymbolSet nonterminals;


  /**
   * The list of follow sets
   */
  private ArrayList < TerminalSymbolSet > followSets;


  /**
   * Allocates a new {@link FollowSetTableModel}
   * 
   * @param cfg The {@link CFG}
   * @throws TerminalSymbolSetException
   * @throws GrammarInvalidNonterminalException
   */
  public FollowSetTableModel ( final Grammar cfg )
      throws GrammarInvalidNonterminalException, TerminalSymbolSetException
  {
    if ( cfg == null )
      throw new NullPointerException ( "cfg is null" ); //$NON-NLS-1$

    this.followSets = new ArrayList < TerminalSymbolSet > ();

    this.nonterminals = cfg.getNonterminalSymbolSet ();
    for ( NonterminalSymbol ns : this.nonterminals )
      this.followSets.add ( cfg.follow ( ns ) );
  }


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getColumnCount()
   */
  public int getColumnCount ()
  {
    return COLUMN_COUNT;
  }


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getRowCount()
   */
  public int getRowCount ()
  {
    return this.nonterminals.size ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.table.TableModel#getValueAt(int, int)
   */
  public Object getValueAt ( int rowIndex, int columnIndex )
  {
    switch ( columnIndex )
    {
      case NONTERMINAL_COLUMN :
        return this.nonterminals.get ( rowIndex );
      case FOLLOW_SET_COLUMN :
        return this.followSets.get ( rowIndex );
    }
    return new PrettyString ();
  }

}
