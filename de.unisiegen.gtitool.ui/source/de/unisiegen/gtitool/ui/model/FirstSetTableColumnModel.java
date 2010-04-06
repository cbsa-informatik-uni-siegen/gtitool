package de.unisiegen.gtitool.ui.model;


import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.parser.style.renderer.PrettyStringTableHeaderCellRenderer;
import de.unisiegen.gtitool.ui.i18n.Messages;


/**
 * Defines the columns used to display <derivation,first set of derivation>
 * 
 * @author Christian Uhrhan
 */
public class FirstSetTableColumnModel extends DefaultTableColumnModel
{

  /**
   * The generated serial
   */
  private static final long serialVersionUID = -2219901201483795491L;


  /**
   * The derivation column
   */
  private static final int DERIVATION_COLUMN = 0;


  /**
   * The first set column
   */
  private static final int FIRST_SET_COLUMN = 1;


  /**
   * Allocates a new {@link FirstSetTableColumnModel}
   */
  public FirstSetTableColumnModel ()
  {
    TableColumn symbolColumn = new TableColumn ( DERIVATION_COLUMN );
    symbolColumn.setHeaderValue ( new PrettyString ( new PrettyToken ( Messages
        .getString ( "FirstSetColumnModel.DerivationColumn" ), Style.NONE ) ) ); //$NON-NLS-1$
    symbolColumn
        .setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );

    symbolColumn = new TableColumn ( FIRST_SET_COLUMN );
    symbolColumn.setHeaderValue ( new PrettyString ( new PrettyToken ( Messages
        .getString ( "FirstSetColumnModel.FirstSetColumn" ), Style.NONE ) ) ); //$NON-NLS-1$
    symbolColumn
        .setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
  }
}
