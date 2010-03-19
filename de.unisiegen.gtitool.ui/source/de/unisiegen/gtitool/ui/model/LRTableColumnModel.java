package de.unisiegen.gtitool.ui.model;


import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.parser.style.renderer.PrettyStringTableCellRenderer;
import de.unisiegen.gtitool.core.parser.style.renderer.PrettyStringTableHeaderCellRenderer;


/**
 * TODO
 */
public class LRTableColumnModel extends DefaultTableColumnModel
{

  /**
   * TODO
   */
  private static final long serialVersionUID = 5924664697189863144L;


  /**
   * The index of the {@link NonterminalSymbol} column
   */
  public static final int LRITEM_COLUMN = 0;


  /**
   * The NonTerminalSymbolColumn
   */
  private TableColumn lrColumnTable;


  public LRTableColumnModel ( final TerminalSymbolSet terminals )
  {
    /*
     * Nonterminal column
     */
    this.lrColumnTable = new TableColumn ( LRITEM_COLUMN );
    this.lrColumnTable.setHeaderValue ( new PrettyString ( new PrettyToken (
        "", //$NON-NLS-1$
        Style.NONE ) ) );
    this.lrColumnTable
        .setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
    this.lrColumnTable.setCellRenderer ( new PrettyStringTableCellRenderer () );
    addColumn ( this.lrColumnTable );

    /*
     * TerminalSymbol columns
     */
    for ( int i = 0 ; i < terminals.size () ; i++ )
    {
      TableColumn symbolColumn = new TableColumn ( i + 1 );
      symbolColumn.setHeaderValue ( terminals.get ( i ) );
      symbolColumn
          .setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
      symbolColumn.setCellRenderer ( new PrettyStringTableCellRenderer () );
      addColumn ( symbolColumn );
    }
  }
}