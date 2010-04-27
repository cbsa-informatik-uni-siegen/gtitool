package de.unisiegen.gtitool.ui.model;


import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import de.unisiegen.gtitool.core.entities.FirstSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.parser.style.PrettyString;


/**
 * The {@link FirstSetStepByStepTableModel}
 */
public class FirstSetStepByStepTableModel extends AbstractTableModel
{

  /**
   * The generated serial
   */
  private static final long serialVersionUID = -3067534907135194015L;


  /**
   * The column count
   */
  private static final int COLUMN_COUNT = 3;


  /**
   * The nonterminal column
   */
  private static final int NONTERMINALCOLUMN = 0;


  /**
   * The first set column
   */
  private static final int FIRSTCOLUMN = 1;


  /**
   * The reason column
   */
  private static final int REASONCOLUMN = 2;


  /**
   * The {@link CFG}
   */
  private CFG cfg;


  /**
   * The {@link NonterminalSymbol}s
   */
  private NonterminalSymbolSet nonterminals;


  /**
   * The {@link FirstSet}s
   */
  private ArrayList < FirstSet > firstSets;


  /**
   * The reasons
   */
  private ArrayList < PrettyString > reasons;


  /**
   * Allocates a new {@link FirstSetStepByStepTableModel}
   * 
   * @param cfg The {@link CFG}
   * @param firstSets The {@link FirstSet}s
   * @param reasons The reasons
   */
  public FirstSetStepByStepTableModel ( final CFG cfg,
      final ArrayList < FirstSet > firstSets,
      final ArrayList < PrettyString > reasons )
  {
    this.cfg = cfg;
    this.nonterminals = this.cfg.getNonterminalSymbolSet ();
    this.firstSets = firstSets;
    this.reasons = reasons;
  }


  /**
   * Sets a new reason
   * 
   * @param ns The {@link NonterminalSymbol}
   * @param reason The {@link PrettyString}
   */
  public void setReason ( final NonterminalSymbol ns, final PrettyString reason )
  {
    int idx = 0;
    for ( int i = 0 ; i < this.nonterminals.size () ; ++i )
      if ( this.nonterminals.get ( i ).getName ().equals ( ns.getName () ) )
        idx = i;
    this.reasons.set ( idx, reason );
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
      case FirstSetStepByStepTableModel.NONTERMINALCOLUMN :
        return this.nonterminals.get ( rowIndex );
      case FirstSetStepByStepTableModel.FIRSTCOLUMN :
        return this.firstSets.get ( rowIndex );
      case FirstSetStepByStepTableModel.REASONCOLUMN :
        return this.reasons.get ( rowIndex );
    }
    return new PrettyString ();
  }

}
