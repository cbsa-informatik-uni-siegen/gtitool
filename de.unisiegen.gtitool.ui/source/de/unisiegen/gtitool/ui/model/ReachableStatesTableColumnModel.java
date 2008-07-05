package de.unisiegen.gtitool.ui.model;


import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import de.unisiegen.gtitool.core.parser.style.renderer.PrettyStringTableCellRenderer;
import de.unisiegen.gtitool.ui.i18n.Messages;


/**
 * The {@link ReachableStatesTableColumnModel}.
 * 
 * @author Christian Fehler
 * @version $Id:ConsoleColumnModel.java 305 2007-12-06 19:55:14Z mies $
 */
public final class ReachableStatesTableColumnModel extends
    DefaultTableColumnModel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -7840743082957665584L;


  /**
   * The outline column.
   */
  private TableColumn outlineColumn;


  /**
   * Allocates a new {@link ReachableStatesTableColumnModel}.
   */
  public ReachableStatesTableColumnModel ()
  {
    // outline
    this.outlineColumn = new TableColumn (
        ConvertMachineTableModel.OUTLINE_COLUMN );
    this.outlineColumn.setResizable ( false );
    this.outlineColumn.setHeaderValue ( Messages
        .getString ( "ReachableStatesDialog.Outline" ) ); //$NON-NLS-1$
    this.outlineColumn.setCellRenderer ( new PrettyStringTableCellRenderer () );
    addColumn ( this.outlineColumn );
  }
}
