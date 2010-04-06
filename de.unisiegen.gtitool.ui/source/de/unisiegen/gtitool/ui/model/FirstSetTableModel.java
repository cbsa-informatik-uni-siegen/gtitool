package de.unisiegen.gtitool.ui.model;


import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.FirstSet;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.ProductionWord;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.parser.style.PrettyString;


/**
 * Table model for presenting <derivation,first set of derivation> data
 * 
 * @author Christian Uhrhan
 */
public class FirstSetTableModel extends AbstractTableModel
{

  /**
   * The generated serial
   */
  private static final long serialVersionUID = -7145401686929536494L;


  /**
   * the derivation column index
   */
  private static final int DERIVATION_COLUMN = 0;


  /**
   * The first set column index
   */
  private static final int FIRST_SET_COLUMN = 1;


  /**
   * The number of columns
   */
  private static final int COLUMN_COUNT = 2;


  /**
   * The {@link ProductionWord}s
   */
  private ArrayList < ProductionWord > productionWordData;


  /**
   * The {@link FirstSet}s
   */
  private ArrayList < FirstSet > firstSetData;


  /**
   * Allocates a new {@link FirstSetTableModel}
   * 
   * @param cfg The {@link CFG}
   * @throws GrammarInvalidNonterminalException
   * @throws TerminalSymbolSetException
   */
  public FirstSetTableModel ( final CFG cfg )
      throws GrammarInvalidNonterminalException, TerminalSymbolSetException
  {
    for ( Production p : cfg.getProduction () )
      this.productionWordData.add ( p.getProductionWord () );

    for ( ProductionWord pw : this.productionWordData )
    {
      FirstSet fs = cfg.first ( pw );
      // add the epsilon element explicit to the
      // set if there is one
      if ( fs.epsilon () )
        fs.add ( new DefaultTerminalSymbol ( new DefaultSymbol ( "epsilon" ) ) ); //$NON-NLS-1$
      this.firstSetData.add ( fs );
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
    return this.productionWordData.size ();
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
      case DERIVATION_COLUMN :
        return this.productionWordData.get ( rowIndex );
      case FIRST_SET_COLUMN :
        return this.firstSetData.get ( rowIndex );
    }
    return new PrettyString ();
  }

}
