package de.unisiegen.gtitool.ui.logic.renderer;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.ui.style.ProductionComponent;



/**
 * The production cell renderer.
 * 
 * @author Benjamin Mies
 */
public final class ProductionCellRenderer extends DefaultTableCellRenderer
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
