package de.unisiegen.gtitool.core.parser.style.renderer;


import java.awt.Component;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyStringComponent;


/**
 * The {@link PrettyString} {@link JTableHeader} {@link TableCellRenderer}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class PrettyStringTableHeaderCellRenderer extends
    DefaultTableCellRenderer
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 8028186422772719475L;


  /**
   * Allocates a new {@link PrettyStringTableHeaderCellRenderer}.
   */
  public PrettyStringTableHeaderCellRenderer ()
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
      @SuppressWarnings ( "unused" )
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
    component.setCenterHorizontal ( true );
    component.setCenterVertical ( true );

    JTableHeader header = table.getTableHeader ();
    component.setForeground ( header.getForeground () );
    component.setBackground ( header.getBackground () );
    component.setFont ( header.getFont () );
    component.setEnabled ( table.isEnabled () );
    component.setBorder ( UIManager.getBorder ( "TableHeader.cellBorder" ) ); //$NON-NLS-1$

    return component;
  }
}
