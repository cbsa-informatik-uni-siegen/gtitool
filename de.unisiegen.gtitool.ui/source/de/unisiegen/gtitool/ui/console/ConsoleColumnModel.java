package de.unisiegen.gtitool.ui.console;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

/**
 * The <code>ConsoleColumnModel</code> for the error and warning tables
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public class ConsoleColumnModel extends DefaultTableColumnModel {

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 8539044321404059407L;

  /**
   * Allocates a new <code>ConsoleColumnModel</code>.
   */
  public ConsoleColumnModel() {
    
    // Target Name
    TableColumn descriptionColumn;
    descriptionColumn = new TableColumn(DefaultTableModel.DESCRIPTION_COLUMN);
    descriptionColumn.setHeaderValue(""); //$NON-NLS-1$
    this.addColumn(descriptionColumn);
  }
}
