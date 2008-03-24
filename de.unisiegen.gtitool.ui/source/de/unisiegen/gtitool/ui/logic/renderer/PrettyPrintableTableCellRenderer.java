package de.unisiegen.gtitool.ui.logic.renderer;


import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;
import de.unisiegen.gtitool.ui.style.PrettyPrintableComponent;


/**
 * The production cell renderer.
 * 
 * @author Benjamin Mies
 * @author Christian Fehler
 */
public final class PrettyPrintableTableCellRenderer extends
    DefaultTableCellRenderer
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 8028186422772719475L;


  /**
   * Allocates a new {@link PrettyPrintableTableCellRenderer}.
   */
  public PrettyPrintableTableCellRenderer ()
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
  public Component getTableCellRendererComponent ( JTable table,
      @SuppressWarnings ( "unused" )
      Object value, boolean isSelected, @SuppressWarnings ( "unused" )
      boolean hasFocus, int row, int column )
  {
    PrettyPrintable prettyPrintable = ( PrettyPrintable ) table.getValueAt (
        row, column );
    PrettyPrintableComponent component = new PrettyPrintableComponent (
        prettyPrintable );
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
