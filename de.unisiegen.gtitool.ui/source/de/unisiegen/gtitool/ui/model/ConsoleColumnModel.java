package de.unisiegen.gtitool.ui.model;


import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import de.unisiegen.gtitool.core.parser.style.renderer.PrettyStringTableCellRenderer;
import de.unisiegen.gtitool.core.preferences.listener.LanguageChangedListener;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


/**
 * The {@link ConsoleColumnModel} for the error and warning tables
 * 
 * @author Benjamin Mies
 * @author Christian Fehler
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
   * Allocates a new {@link ConsoleColumnModel}.
   */
  public ConsoleColumnModel ()
  {
    // Message
    this.messageColumn = new TableColumn (
        MachineConsoleTableModel.MESSAGE_COLUMN );
    this.messageColumn.setHeaderValue ( Messages
        .getString ( "MachinePanel.Message" ) ); //$NON-NLS-1$
    this.messageColumn.setPreferredWidth ( 200 );
    this.messageColumn.setMinWidth ( 200 );
    this.messageColumn.setCellRenderer ( new PrettyStringTableCellRenderer () );
    addColumn ( this.messageColumn );

    // Description
    this.descriptionColumn = new TableColumn (
        MachineConsoleTableModel.DESCRIPTION_COLUMN );
    this.descriptionColumn.setHeaderValue ( Messages
        .getString ( "MachinePanel.Description" ) ); //$NON-NLS-1$
    this.descriptionColumn.setPreferredWidth ( 800 );
    this.descriptionColumn
        .setCellRenderer ( new PrettyStringTableCellRenderer () );
    addColumn ( this.descriptionColumn );

    // Language changed listener
    PreferenceManager.getInstance ().addLanguageChangedListener ( this );

    /*
     * Only for debug
     */

    // // States
    // TableColumn statesColumn;
    // statesColumn = new TableColumn ( MachineConsoleTableModel.STATES_COLUMN
    // );
    // statesColumn.setHeaderValue ( Messages.getString ( "MachinePanel.States"
    // ) ); //$NON-NLS-1$
    // this.addColumn ( statesColumn );
    // // Transitions
    // TableColumn transitionsColumn;
    // transitionsColumn = new TableColumn (
    // MachineConsoleTableModel.TRANSITIONS_COLUMN );
    // transitionsColumn.setHeaderValue ( Messages
    // .getString ( "MachinePanel.Transitions" ) ); //$NON-NLS-1$
    // this.addColumn ( transitionsColumn );
    // // Symbols
    // TableColumn symbolsColumn;
    // symbolsColumn = new TableColumn ( MachineConsoleTableModel.SYMBOL_COLUMN
    // );
    // symbolsColumn
    // .setHeaderValue ( Messages.getString ( "MachinePanel.Symbols" ) );
    // //$NON-NLS-1$
    // this.addColumn ( symbolsColumn );
  }


  /**
   * {@inheritDoc}
   * 
   * @see LanguageChangedListener#languageChanged()
   */
  public final void languageChanged ()
  {
    this.messageColumn.setHeaderValue ( Messages
        .getString ( "MachinePanel.Message" ) ); //$NON-NLS-1$
    this.descriptionColumn.setHeaderValue ( Messages
        .getString ( "MachinePanel.Description" ) ); //$NON-NLS-1$
  }
}
