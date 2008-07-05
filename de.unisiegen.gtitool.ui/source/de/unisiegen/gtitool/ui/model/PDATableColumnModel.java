package de.unisiegen.gtitool.ui.model;


import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.parser.style.renderer.StackOperationTableCellRenderer;
import de.unisiegen.gtitool.core.preferences.listener.LanguageChangedListener;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


/**
 * The {@link PDATableColumnModel}.
 * 
 * @author Benjamin Mies
 * @version $Id:ConsoleColumnModel.java 305 2007-12-06 19:55:14Z mies $
 */
public final class PDATableColumnModel extends DefaultTableColumnModel
    implements LanguageChangedListener
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 5754161755614117908L;


  /**
   * The {@link Transition} column.
   */
  private TableColumn transitionColumn;


  /**
   * Allocates a new {@link PDATableColumnModel}.
   */
  public PDATableColumnModel ()
  {
    // transition
    this.transitionColumn = new TableColumn (
        MachineConsoleTableModel.MESSAGE_COLUMN );
    this.transitionColumn.setHeaderValue ( Messages
        .getString ( "MachinePanel.StackOperation" ) ); //$NON-NLS-1$
    this.transitionColumn
        .setCellRenderer ( new StackOperationTableCellRenderer () );
    addColumn ( this.transitionColumn );

    // language changed listener
    PreferenceManager.getInstance ().addLanguageChangedListener ( this );
  }


  /**
   * {@inheritDoc}
   * 
   * @see LanguageChangedListener#languageChanged()
   */
  public final void languageChanged ()
  {
    removeColumn ( this.transitionColumn );
    this.transitionColumn.setHeaderValue ( Messages
        .getString ( "MachinePanel.StackOperation" ) ); //$NON-NLS-1$
    addColumn ( this.transitionColumn );
  }
}
