package de.unisiegen.gtitool.ui.model;


import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.parser.style.renderer.PrettyStringTableCellRenderer;
import de.unisiegen.gtitool.core.parser.style.renderer.PrettyStringTableHeaderCellRenderer;
import de.unisiegen.gtitool.ui.i18n.Messages;


/**
 * Column model
 */
public class FollowSetStepByStepTableColumnModel extends DefaultTableColumnModel
{

  /**
   * generated serial
   */
  private static final long serialVersionUID = 7571139206180870719L;


  /**
   * The {@link NonterminalSymbol} column index
   */
  private static final int NONTERMINAL_COLUMN = 0;


  /**
   * The follow set column index
   */
  private static final int FOLLOW_SET_COLUMN = 1;


  /**
   * 
   * ctor
   *
   */
  public FollowSetStepByStepTableColumnModel ()
  {
    TableColumn symbolColumn = new TableColumn ( NONTERMINAL_COLUMN );
    symbolColumn
        .setHeaderValue ( new PrettyString (
            new PrettyToken (
                Messages
                    .getString ( "FollowSetColumnModel.NonterminalSymbolColumn" ), Style.NONE ) ) ); //$NON-NLS-1$
    symbolColumn
        .setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
    symbolColumn.setCellRenderer ( new PrettyStringTableCellRenderer () );
    addColumn ( symbolColumn );
    symbolColumn = new TableColumn ( FOLLOW_SET_COLUMN );
    symbolColumn.setHeaderValue ( new PrettyString ( new PrettyToken ( Messages
        .getString ( "FollowSetColumnModel.FollowSetColumn" ), Style.NONE ) ) ); //$NON-NLS-1$
    symbolColumn
        .setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
    symbolColumn.setCellRenderer ( new PrettyStringTableCellRenderer () );
    addColumn ( symbolColumn );
  }
}
