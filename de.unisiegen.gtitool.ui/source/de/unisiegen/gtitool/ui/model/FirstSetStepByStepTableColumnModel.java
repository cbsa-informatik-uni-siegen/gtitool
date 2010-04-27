package de.unisiegen.gtitool.ui.model;


import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.parser.style.renderer.PrettyStringTableHeaderCellRenderer;
import de.unisiegen.gtitool.ui.i18n.Messages;


/**
 * The {@link FirstSetStepByStepTableColumnModel}
 */
public class FirstSetStepByStepTableColumnModel extends DefaultTableColumnModel
{

  /**
   * The generated serial
   */
  private static final long serialVersionUID = 3588209635549841473L;


  /**
   * The nonterminal column
   */
  private static int NONTERMINALCOLUMN = 0;


  /**
   * The first set column
   */
  private static int FIRSTCOLUMN = 1;


  /**
   * The reason column
   */
  private static int REASONCOLUMN = 2;


  /**
   * 
   * Allocates a new {@link FirstSetStepByStepTableColumnModel}
   *
   */
  public FirstSetStepByStepTableColumnModel ()
  {
    TableColumn symbolColumn = new TableColumn ( NONTERMINALCOLUMN );
    symbolColumn.setHeaderValue ( new PrettyString ( new PrettyToken ( Messages
        .getString ( "FirstSetDialog.Nonterminal" ), Style.NONE ) ) ); //$NON-NLS-1$
    symbolColumn
        .setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
    addColumn ( symbolColumn );
    symbolColumn = new TableColumn ( FIRSTCOLUMN );
    symbolColumn.setHeaderValue ( new PrettyString ( new PrettyToken (
        "FIRST", Style.NONE ) ) ); //$NON-NLS-1$
    symbolColumn
        .setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
    addColumn ( symbolColumn );
    symbolColumn = new TableColumn ( REASONCOLUMN );
    symbolColumn.setHeaderValue ( new PrettyString ( new PrettyToken ( Messages
        .getString ( "FirstSetDialog.Reason" ), Style.NONE ) ) ); //$NON-NLS-1$
    symbolColumn
        .setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
    addColumn ( symbolColumn );
  }
}
