package de.unisiegen.gtitool.ui.model;


import java.util.HashMap;

import javax.swing.table.AbstractTableModel;

import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.FirstSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.ProductionWordMember;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
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
  private static final int COLUMN_COUNT = 2;


  /**
   * The nonterminal column
   */
  private static final int NONTERMINALCOLUMN = 0;


  /**
   * The first set column
   */
  private static final int FIRSTCOLUMN = 1;


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
  private HashMap < ProductionWordMember, FirstSet > firstSets;


  /**
   * Allocates a new {@link FirstSetStepByStepTableModel}
   * 
   * @param cfg The {@link CFG}
   * @param firstSets The {@link FirstSet}s
   */
  public FirstSetStepByStepTableModel ( final CFG cfg,
      final HashMap < ProductionWordMember, FirstSet > firstSets)
  {
    this.cfg = cfg;
    /*
     * Use the order of NonterminalSymbols as they appear in the list of
     * productions cause this is the order used by the calculation of FIRST(X) X
     * \in N
     */
    this.nonterminals = new DefaultNonterminalSymbolSet ();
    for ( Production p : this.cfg.getProduction () )
      try
      {
        this.nonterminals.add ( p.getNonterminalSymbol () );
      }
      catch ( NonterminalSymbolSetException exc )
      {
        continue;
      }
    this.firstSets = firstSets;
  }


  /**
   * Set all
   * 
   * @param firstSets as it says
   */
  public final void setAll (
      final HashMap < ProductionWordMember, FirstSet > firstSets)
  {
    this.firstSets = firstSets;
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
        return this.firstSets.get ( this.nonterminals.get ( rowIndex ) );
    }
    return new PrettyString ();
  }

}
