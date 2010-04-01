package de.unisiegen.gtitool.ui.model;


import javax.swing.table.TableColumn;

import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.parser.style.renderer.PrettyStringTableCellRenderer;
import de.unisiegen.gtitool.core.parser.style.renderer.PrettyStringTableHeaderCellRenderer;
import de.unisiegen.gtitool.ui.i18n.Messages;


/**
 * The {@link LRMachineColumnModel}
 * 
 * @author Christian Uhrhan
 */
public class LRMachineColumnModel extends StatelessMachineTableColumnModel
{

  /**
   * The generated serial
   */
  private static final long serialVersionUID = -2752715129381192028L;


  /**
   * the input column
   */
  private final static int INPUT_COLUMN = 0;


  /**
   * the stack column
   */
  private final static int STACK_COLUMN = 1;


  /**
   * the state stack column
   */
  private final static int STATE_STACK_COLUMN = 2;


  /**
   * the action column
   */
  private final static int ACTION_COLUMN = 3;


  /**
   * Allocates a new {@link LRMachineColumnModel}
   */
  public LRMachineColumnModel ()
  {
    {
      final TableColumn symbolColumn = new TableColumn ( STATE_STACK_COLUMN );
      symbolColumn
          .setHeaderValue ( new PrettyString (
              new PrettyToken (
                  Messages
                      .getString ( "StatelessMachineColumnModel.StateStackColumn" ), Style.NONE ) ) ); //$NON-NLS-1$
      symbolColumn
          .setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
      symbolColumn.setCellRenderer ( new PrettyStringTableCellRenderer () );
      addColumn ( symbolColumn );
    }

    {
      final TableColumn stackColumn = new TableColumn ( STACK_COLUMN );
      stackColumn
          .setHeaderValue ( new PrettyString (
              new PrettyToken (
                  Messages
                      .getString ( "StatelessMachineColumnModel.StackColumn" ), Style.NONE ) ) ); //$NON-NLS-1$
      stackColumn
          .setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
      stackColumn.setCellRenderer ( new PrettyStringTableCellRenderer () );
      PrettyStringTableCellRenderer pstcr = new PrettyStringTableCellRenderer ();
      pstcr.setRightHorizontal ( true );
      stackColumn.setCellRenderer ( pstcr );
      addColumn ( stackColumn );
    }

    {
      final TableColumn inputColumn = new TableColumn ( INPUT_COLUMN );
      inputColumn
          .setHeaderValue ( new PrettyString (
              new PrettyToken (
                  Messages
                      .getString ( "StatelessMachineColumnModel.InputColumn" ), Style.NONE ) ) ); //$NON-NLS-1$
      inputColumn
          .setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
      inputColumn.setCellRenderer ( new PrettyStringTableCellRenderer () );
      addColumn ( inputColumn );
    }

    {
      TableColumn actionColumn = new TableColumn ( ACTION_COLUMN );
      actionColumn
          .setHeaderValue ( new PrettyString (
              new PrettyToken (
                  Messages
                      .getString ( "StatelessMachineColumnModel.ActionColumn" ), Style.NONE ) ) ); //$NON-NLS-1$
      actionColumn
          .setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
      actionColumn.setCellRenderer ( new PrettyStringTableCellRenderer () );
      addColumn ( actionColumn );
    }
  }
}
