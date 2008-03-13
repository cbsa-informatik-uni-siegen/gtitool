package de.unisiegen.gtitool.ui.model;


import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.preferences.listener.LanguageChangedListener;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.style.ProductionComponent;


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
   * The production cell renderer.
   * 
   * @author Benjamin Mies
   */
  protected final class ProductionCellRenderer extends DefaultTableCellRenderer
      implements TableCellRenderer
  {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = -3513916873397198013L;


    /**
     * {@inheritDoc}
     * 
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable,
     *      java.lang.Object, boolean, boolean, int, int)
     */
    @Override 
    public Component getTableCellRendererComponent ( JTable table,
        @SuppressWarnings("unused")
        Object value, boolean isSelected, @SuppressWarnings("unused")
        boolean hasFocus, int row, @SuppressWarnings("unused")
        int column )
    {
      Production production = ( Production ) table.getValueAt ( row, 0 );

      ProductionComponent component = new ProductionComponent ( production );

      if ( isSelected )
        component.setBackground ( table.getSelectionBackground () );

      return component; 
    }
  }


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
    // Message
    this.productionColumn = new TableColumn ( ConsoleTableModel.MESSAGE_COLUMN );
    this.productionColumn.setPreferredWidth ( 200 );
    this.productionColumn.setMinWidth ( 200 );
    this.productionColumn.setCellRenderer ( new ProductionCellRenderer () );
    this.addColumn ( this.productionColumn );

    PreferenceManager.getInstance ().addLanguageChangedListener ( this );
  }


  /**
   * {@inheritDoc}
   * 
   * @see LanguageChangedListener#languageChanged()
   */
  public void languageChanged ()
  {
    this.productionColumn.setHeaderValue ( Messages
        .getString ( "GrammarPanel.Productions" ) ); //$NON-NLS-1$
  }
}
