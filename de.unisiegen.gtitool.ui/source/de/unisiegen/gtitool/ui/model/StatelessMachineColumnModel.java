package de.unisiegen.gtitool.ui.model;


import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.parser.style.renderer.PrettyStringTableCellRenderer;
import de.unisiegen.gtitool.core.parser.style.renderer.PrettyStringTableHeaderCellRenderer;
import de.unisiegen.gtitool.ui.i18n.Messages;


/**
 * The {@link PTTableColumnModel}.
 * 
 * @author Christian Uhrhan
 */
public class StatelessMachineColumnModel extends DefaultTableColumnModel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -8274015605217375039L;


  /**
   * the input column
   */
  private final static int INPUT_COLUMN = 0;


  /**
   * the stack column
   */
  private final static int STACK_COLUMN = 1;


  /**
   * the action column
   */
  private final static int ACTION_COLUMN = 2;


  /**
   * Allocates a new {@link StatelessMachineColumnModel}
   */
  public StatelessMachineColumnModel ()
  {
    TableColumn symbolColumn = new TableColumn ( INPUT_COLUMN );
    symbolColumn
        .setHeaderValue ( new PrettyString (
            new PrettyToken (
                Messages.getString ( "StatelessMachineColumnModel.InputColumn" ), Style.NONE ) ) ); //$NON-NLS-1$
    symbolColumn
        .setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
    symbolColumn.setCellRenderer ( new PrettyStringTableCellRenderer () );
    addColumn ( symbolColumn );

    symbolColumn = new TableColumn ( STACK_COLUMN );
    symbolColumn
        .setHeaderValue ( new PrettyString (
            new PrettyToken (
                Messages.getString ( "StatelessMachineColumnModel.StackColumn" ), Style.NONE ) ) ); //$NON-NLS-1$
    symbolColumn
        .setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
    symbolColumn.setCellRenderer ( new PrettyStringTableCellRenderer () );
    addColumn ( symbolColumn );

    symbolColumn = new TableColumn ( ACTION_COLUMN );
    symbolColumn
        .setHeaderValue ( new PrettyString (
            new PrettyToken (
                Messages
                    .getString ( "StatelessMachineColumnModel.ActionColumn" ), Style.NONE ) ) ); //$NON-NLS-1$
    symbolColumn
        .setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
    symbolColumn.setCellRenderer ( new PrettyStringTableCellRenderer () );
    addColumn ( symbolColumn );
  }
}
