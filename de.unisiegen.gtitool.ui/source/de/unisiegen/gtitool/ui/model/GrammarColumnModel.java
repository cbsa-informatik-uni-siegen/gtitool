package de.unisiegen.gtitool.ui.model;


import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import de.unisiegen.gtitool.core.parser.style.renderer.PrettyStringTableCellRenderer;
import de.unisiegen.gtitool.core.preferences.listener.LanguageChangedListener;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


/**
 * The {@link GrammarColumnModel} for the error and warning tables
 * 
 * @author Benjamin Mies
 * @version $Id:ConsoleColumnModel.java 305 2007-12-06 19:55:14Z mies $
 */
public final class GrammarColumnModel extends DefaultTableColumnModel implements
    LanguageChangedListener
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -6602993046240099848L;


  /**
   * The message column.
   */
  private TableColumn productionColumn;


  /**
   * Allocates a new {@link GrammarColumnModel}.
   */
  public GrammarColumnModel ()
  {
    // Production
    this.productionColumn = new TableColumn (
        MachineConsoleTableModel.MESSAGE_COLUMN );
    this.productionColumn.setHeaderValue ( Messages
        .getString ( "GrammarPanel.Productions" ) ); //$NON-NLS-1$
    this.productionColumn
        .setCellRenderer ( new PrettyStringTableCellRenderer () );
    addColumn ( this.productionColumn );

    // Language changed listener
    PreferenceManager.getInstance ().addLanguageChangedListener ( this );
  }


  /**
   * {@inheritDoc}
   * 
   * @see LanguageChangedListener#languageChanged()
   */
  public void languageChanged ()
  {
    removeColumn ( this.productionColumn );
    this.productionColumn.setHeaderValue ( Messages
        .getString ( "GrammarPanel.Productions" ) ); //$NON-NLS-1$
    addColumn ( this.productionColumn );
  }
}
