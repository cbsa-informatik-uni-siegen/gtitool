package de.unisiegen.gtitool.ui.model;


import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import de.unisiegen.gtitool.ui.Messages;


/**
 * The <code>ConsoleColumnModel</code> for the error and warning tables
 * 
 * @author Benjamin Mies
 * @version $Id:ConsoleColumnModel.java 305 2007-12-06 19:55:14Z mies $
 */
public final class ConsoleColumnModel extends DefaultTableColumnModel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 8539044321404059407L;


  /**
   * Allocates a new <code>ConsoleColumnModel</code>.
   */
  public ConsoleColumnModel ()
  {
    // Message
    TableColumn messageColumn;
    messageColumn = new TableColumn ( ConsoleTableModel.MESSAGE_COLUMN );
    messageColumn
        .setHeaderValue ( Messages.getString ( "MachinePanel.Message" ) ); //$NON-NLS-1$
    messageColumn.setPreferredWidth ( 200 );
    messageColumn.setMinWidth ( 200 );
    this.addColumn ( messageColumn );
    // Description
    TableColumn descriptionColumn;
    descriptionColumn = new TableColumn ( ConsoleTableModel.DESCRIPTION_COLUMN );
    descriptionColumn.setHeaderValue ( Messages
        .getString ( "MachinePanel.Description" ) ); //$NON-NLS-1$
    descriptionColumn.setPreferredWidth ( 800 );
    this.addColumn ( descriptionColumn );
    /*
     * Only for debug
     */
    /*// States
    TableColumn statesColumn;
    statesColumn = new TableColumn ( ConsoleTableModel.STATES_COLUMN );
    statesColumn.setHeaderValue ( Messages.getString ( "MachinePanel.States" ) ); //$NON-NLS-1$
    this.addColumn ( statesColumn );
    // Transitions
    TableColumn transitionsColumn;
    transitionsColumn = new TableColumn ( ConsoleTableModel.TRANSITIONS_COLUMN );
    transitionsColumn.setHeaderValue ( Messages
        .getString ( "MachinePanel.Transitions" ) ); //$NON-NLS-1$
    this.addColumn ( transitionsColumn );
    // Symbols
    TableColumn symbolsColumn;
    symbolsColumn = new TableColumn ( ConsoleTableModel.SYMBOL_COLUMN );
    symbolsColumn.setHeaderValue ( Messages
        .getString ( "MachinePanel.Symbols" ) ); //$NON-NLS-1$
    this.addColumn ( symbolsColumn );*/
  }
}
