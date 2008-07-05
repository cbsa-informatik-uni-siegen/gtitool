package de.unisiegen.gtitool.ui.history;


import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;


/**
 * The {@link HistoryPath} {@link TableCellRenderer}.
 * 
 * @author Christian Fehler
 * @version $Id: HistoryPathTableCellRenderer.java 1031 2008-06-25 16:57:06Z
 *          fehler $
 */
public final class HistoryPathTableCellRenderer extends
    DefaultTableCellRenderer
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 2083830686506423666L;


  /**
   * Allocates a new {@link HistoryPathTableCellRenderer}.
   */
  public HistoryPathTableCellRenderer ()
  {
    super ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see TableCellRenderer#getTableCellRendererComponent(JTable, Object,
   *      boolean, boolean, int, int)
   */
  @Override
  public Component getTableCellRendererComponent ( JTable table, Object value,
      boolean isSelected, @SuppressWarnings ( "unused" ) boolean hasFocus,
      int row, @SuppressWarnings ( "unused" ) int column )
  {
    HistoryPath historyPath = null;
    if ( value instanceof HistoryPath )
    {
      historyPath = ( ( HistoryPath ) value );
    }
    else
    {
      throw new IllegalArgumentException ( "the value is not a history path" ); //$NON-NLS-1$
    }

    HistoryPathComponent component = new HistoryPathComponent ( historyPath,
        table, row );

    if ( isSelected )
    {
      component.setBackground ( table.getSelectionBackground () );
      component.setForeground ( table.getSelectionForeground () );
    }
    else
    {
      component.setBackground ( table.getBackground () );
      component.setForeground ( table.getForeground () );
    }

    component.setEnabled ( table.isEnabled () );
    component.setFont ( table.getFont () );

    return component;
  }
}
