package de.unisiegen.gtitool.ui.model;


import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.preferences.listener.LanguageChangedListener;


/**
 * The <code>ConsoleColumnModel</code> for the error and warning tables
 * 
 * @author Benjamin Mies
 * @version $Id:ConsoleColumnModel.java 305 2007-12-06 19:55:14Z mies $
 */
public final class ConsoleColumnModel extends DefaultTableColumnModel implements
    LanguageChangedListener
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 8539044321404059407L;


  /**
   * The message column.
   */
  private TableColumn messageColumn;


  /**
   * The description column.
   */
  private TableColumn descriptionColumn;


  /**
   * Allocates a new <code>ConsoleColumnModel</code>.
   */
  public ConsoleColumnModel ()
  {
    // Message
    this.messageColumn = new TableColumn ( ConsoleTableModel.MESSAGE_COLUMN );
    this.messageColumn.setHeaderValue ( Messages
        .getString ( "MachinePanel.Message" ) ); //$NON-NLS-1$
    this.messageColumn.setPreferredWidth ( 200 );
    this.messageColumn.setMinWidth ( 200 );
    this.addColumn ( this.messageColumn );
    // Description
    this.descriptionColumn = new TableColumn (
        ConsoleTableModel.DESCRIPTION_COLUMN );
    this.descriptionColumn.setHeaderValue ( Messages
        .getString ( "MachinePanel.Description" ) ); //$NON-NLS-1$
    this.descriptionColumn.setPreferredWidth ( 800 );
    this.addColumn ( this.descriptionColumn );

    PreferenceManager.getInstance ().addLanguageChangedListener ( this );
    /*
     * Only for debug
     */
    /*
     * // States TableColumn statesColumn; statesColumn = new TableColumn (
     * ConsoleTableModel.STATES_COLUMN ); statesColumn.setHeaderValue (
     * Messages.getString ( "MachinePanel.States" ) ); //$NON-NLS-1$
     * this.addColumn ( statesColumn ); // Transitions TableColumn
     * transitionsColumn; transitionsColumn = new TableColumn (
     * ConsoleTableModel.TRANSITIONS_COLUMN ); transitionsColumn.setHeaderValue (
     * Messages .getString ( "MachinePanel.Transitions" ) ); //$NON-NLS-1$
     * this.addColumn ( transitionsColumn ); // Symbols TableColumn
     * symbolsColumn; symbolsColumn = new TableColumn (
     * ConsoleTableModel.SYMBOL_COLUMN ); symbolsColumn.setHeaderValue (
     * Messages .getString ( "MachinePanel.Symbols" ) ); //$NON-NLS-1$
     * this.addColumn ( symbolsColumn );
     */
  }


  /**
   * {@inheritDoc}
   * 
   * @see LanguageChangedListener#languageChanged()
   */
  public void languageChanged ()
  {
    this.messageColumn.setHeaderValue ( Messages
        .getString ( "MachinePanel.Message" ) ); //$NON-NLS-1$
    this.descriptionColumn.setHeaderValue ( Messages
        .getString ( "MachinePanel.Description" ) ); //$NON-NLS-1$
  }
}
