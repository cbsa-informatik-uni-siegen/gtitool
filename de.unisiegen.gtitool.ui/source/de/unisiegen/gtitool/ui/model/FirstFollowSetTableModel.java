package de.unisiegen.gtitool.ui.model;


import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import de.unisiegen.gtitool.core.entities.FirstSet;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.parser.style.PrettyString;


/**
 * blub
 */
public class FirstFollowSetTableModel extends AbstractTableModel
{

  /**
   * blub
   */
  private static final long serialVersionUID = 4321485908259332568L;


  /**
   * The production column
   */
  private static final int PRODUCTION_COLUMN = 0;


  /**
   * The first set column
   */
  private static final int FIRST_SET_COLUMN = 1;


  /**
   * The follow set column
   */
  private static final int FOLLOW_SET_COLUMN = 2;


  /**
   * blub
   */
  private static int COLUMN_COUNT = 2;


  /**
   * blub
   */
  private ArrayList < Production > productions;


  /**
   * The {@link FirstSet}s
   */
  private ArrayList < FirstSet > firstSetData;


  /**
   * The list of follow sets
   */
  private ArrayList < TerminalSymbolSet > followSets;


  /**
   * blub
   * 
   * @param cfg blub
   */
  public FirstFollowSetTableModel ( final Grammar cfg )
  {
    if ( cfg == null )
      throw new NullPointerException ( "cfg is null" ); //$NON-NLS-1$

    this.productions = new ArrayList < Production > ();
    this.firstSetData = new ArrayList < FirstSet > ();
    this.followSets = new ArrayList < TerminalSymbolSet > ();

    for ( Production p : cfg.getProduction () )
    {
      this.productions.add ( p );
      try
      {
        this.firstSetData.add ( cfg.first ( p.getProductionWord () ) );
        this.followSets.add ( cfg.follow ( p.getNonterminalSymbol () ) );
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
    return this.productions.size ();
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
      case PRODUCTION_COLUMN :
        return this.productions.get ( rowIndex );
      case FIRST_SET_COLUMN :
        return this.firstSetData.get ( rowIndex );
      case FOLLOW_SET_COLUMN :
        return this.followSets.get ( rowIndex );
    }
    return new PrettyString ();
  }
}
