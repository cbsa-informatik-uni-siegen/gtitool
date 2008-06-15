package de.unisiegen.gtitool.ui.model;


import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import de.unisiegen.gtitool.core.parser.style.renderer.PrettyStringTableCellRenderer;
import de.unisiegen.gtitool.ui.i18n.Messages;


/**
 * The {@link MinimizeMachineTableColumnModel}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class MinimizeMachineTableColumnModel extends
    DefaultTableColumnModel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 4127761312520124149L;


  /**
   * The outline column.
   */
  private TableColumn outlineColumn;


  /**
   * Allocates a new {@link MinimizeMachineTableColumnModel}.
   */
  public MinimizeMachineTableColumnModel ()
  {
    // outline
    this.outlineColumn = new TableColumn (
        MinimizeMachineTableModel.OUTLINE_COLUMN );
    this.outlineColumn.setResizable ( false );
    this.outlineColumn.setHeaderValue ( Messages
        .getString ( "MinimizeMachineDialog.Outline" ) ); //$NON-NLS-1$
    this.outlineColumn.setCellRenderer ( new PrettyStringTableCellRenderer () );
    this.addColumn ( this.outlineColumn );
  }
}
