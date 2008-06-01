package de.unisiegen.gtitool.ui.model;


import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import de.unisiegen.gtitool.core.parser.style.renderer.PrettyStringTableCellRenderer;
import de.unisiegen.gtitool.ui.i18n.Messages;


/**
 * The {@link ConvertMachineTableColumnModel}.
 * 
 * @author Christian Fehler
 * @version $Id:ConsoleColumnModel.java 305 2007-12-06 19:55:14Z mies $
 */
public final class ConvertMachineTableColumnModel extends
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
   * Allocates a new {@link ConvertMachineTableColumnModel}.
   */
  public ConvertMachineTableColumnModel ()
  {
    // outline
    this.outlineColumn = new TableColumn (
        ConvertMachineTableModel.OUTLINE_COLUMN );
    this.outlineColumn.setPreferredWidth ( 200 );
    this.outlineColumn.setMinWidth ( 200 );
    this.outlineColumn.setResizable ( false );
    this.outlineColumn.setHeaderValue ( Messages
        .getString ( "ConvertMachineDialog.Outline" ) ); //$NON-NLS-1$
    this.outlineColumn.setCellRenderer ( new PrettyStringTableCellRenderer () );
    this.addColumn ( this.outlineColumn );
  }
}
