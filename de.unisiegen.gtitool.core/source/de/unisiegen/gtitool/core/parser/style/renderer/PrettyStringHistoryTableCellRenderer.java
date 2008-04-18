package de.unisiegen.gtitool.core.parser.style.renderer;


import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import de.unisiegen.gtitool.core.machines.HistoryItem;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyStringHistoryComponent;


/**
 * The {@link PrettyString} {@link HistoryItem} {@link TableCellRenderer}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class PrettyStringHistoryTableCellRenderer extends
    DefaultTableCellRenderer
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 2083830686506423666L;


  /**
   * Allocates a new {@link PrettyStringHistoryTableCellRenderer}.
   */
  public PrettyStringHistoryTableCellRenderer ()
  {
    super ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see TableCellRenderer#getTableCellRendererComponent(JTable, Object,
   *      boolean, boolean, int, int)
   */
  @SuppressWarnings ( "unchecked" )
  @Override
  public Component getTableCellRendererComponent ( JTable table, Object value,
      boolean isSelected, @SuppressWarnings ( "unused" )
      boolean hasFocus, @SuppressWarnings ( "unused" )
      int row, @SuppressWarnings ( "unused" )
      int column )
  {
    ArrayList < HistoryItem > historyItemList = null;
    if ( value instanceof ArrayList )
    {
      historyItemList = ( ( ArrayList ) value );
    }
    else
    {
      throw new IllegalArgumentException ( "the value can not be renderer" ); //$NON-NLS-1$
    }

    PrettyStringHistoryComponent component = new PrettyStringHistoryComponent (
        historyItemList );
    
    table.setRowHeight ( row, component.getRowHeight () );

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
