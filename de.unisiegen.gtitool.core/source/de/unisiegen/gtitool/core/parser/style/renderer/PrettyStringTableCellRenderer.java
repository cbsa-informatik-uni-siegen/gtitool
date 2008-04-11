package de.unisiegen.gtitool.core.parser.style.renderer;


import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyStringComponent;


/**
 * The production cell renderer.
 * 
 * @author Benjamin Mies
 * @author Christian Fehler
 */
public final class PrettyStringTableCellRenderer extends
    DefaultTableCellRenderer
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 8028186422772719475L;


  /**
   * Allocates a new {@link PrettyStringTableCellRenderer}.
   */
  public PrettyStringTableCellRenderer ()
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
      boolean isSelected, @SuppressWarnings ( "unused" )
      boolean hasFocus, @SuppressWarnings ( "unused" )
      int row, @SuppressWarnings ( "unused" )
      int column )
  {
    PrettyString prettyString = null;
    if ( value instanceof PrettyPrintable )
    {
      prettyString = ( ( PrettyPrintable ) value ).toPrettyString ();
    }
    else if ( value instanceof PrettyString )
    {
      prettyString = ( PrettyString ) value;
    }
    else
    {
      throw new IllegalArgumentException ( "the value can not be renderer" ); //$NON-NLS-1$
    }

    PrettyStringComponent component = new PrettyStringComponent ( prettyString );

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
