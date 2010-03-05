package de.unisiegen.gtitool.ui.model;


import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.parser.style.renderer.PrettyStringTableCellRenderer;
import de.unisiegen.gtitool.core.parser.style.renderer.PrettyStringTableHeaderCellRenderer;


/**
 * The {@link PTTableColumnModel}.
 * 
 * @author Christian Uhrhan
 */
public class PTTableColumnModel extends DefaultTableColumnModel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -8089761279751218832L;


  /**
   * The index of the {@link NonterminalSymbol} column
   */
  public static final int NONTERMINAL_COLUMN = 0;


  /**
   * The NonTerminalSymbolColumn
   */
  private TableColumn nonTerminalColumn;


  /**
   * Allocates a new {@link PTTableColumnModel}
   * 
   * @param alphabet the alphabet we're using to produce the header
   */
  public PTTableColumnModel ( final Alphabet alphabet )
  {
    /*
     * Nonterminal column
     */
    this.nonTerminalColumn = new TableColumn ( NONTERMINAL_COLUMN );
    this.nonTerminalColumn.setHeaderValue ( new PrettyString ( new PrettyToken (
        "", //$NON-NLS-1$
        Style.NONE ) ) );
    this.nonTerminalColumn
        .setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
    this.nonTerminalColumn
        .setCellRenderer ( new PrettyStringTableCellRenderer () );
    addColumn ( this.nonTerminalColumn );

    /*
     * TerminalSymbol columns
     */
    for ( int i = 0 ; i < alphabet.size () ; i++ )
    {
      TableColumn symbolColumn = new TableColumn ( i + 1 );
      symbolColumn.setHeaderValue ( alphabet.get ( i ) );
      symbolColumn
          .setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
      symbolColumn.setCellRenderer ( new PrettyStringTableCellRenderer () );
      addColumn ( symbolColumn );
    }
  }
}