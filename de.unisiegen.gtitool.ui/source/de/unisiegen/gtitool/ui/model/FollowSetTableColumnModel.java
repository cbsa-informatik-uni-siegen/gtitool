package de.unisiegen.gtitool.ui.model;


import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.parser.style.renderer.PrettyStringTableHeaderCellRenderer;
import de.unisiegen.gtitool.ui.i18n.Messages;


/**
 * Defines the columns used to display <Nonterminal,FollowSet> data
 */
public class FollowSetTableColumnModel extends DefaultTableColumnModel
{

  /**
   * The generated serial
   */
  private static final long serialVersionUID = 2841053177088812823L;


  /**
   * The {@link NonterminalSymbol} column index
   */
  private static final int NONTERMINAL_COLUMN = 0;


  /**
   * The follow set column index
   */
  private static final int FOLLOW_SET_COLUMN = 1;


  /**
   * Allocates a new {@link FollowSetTableColumnModel}
   */
  public FollowSetTableColumnModel ()
  {
    TableColumn symbolColumn = new TableColumn ( NONTERMINAL_COLUMN );
    symbolColumn
        .setHeaderValue ( new PrettyString (
            new PrettyToken (
                Messages
                    .getString ( "FollowSetColumnModel.NonterminalSymbolColumn" ), Style.NONE ) ) ); //$NON-NLS-1$
    symbolColumn
        .setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
    addColumn ( symbolColumn );
    symbolColumn = new TableColumn ( FOLLOW_SET_COLUMN );
    symbolColumn.setHeaderValue ( new PrettyString ( new PrettyToken ( Messages
        .getString ( "FollowSetColumnModel.FollowSetColumn" ), Style.NONE ) ) ); //$NON-NLS-1$
    symbolColumn
        .setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
    addColumn ( symbolColumn );
  }
}
