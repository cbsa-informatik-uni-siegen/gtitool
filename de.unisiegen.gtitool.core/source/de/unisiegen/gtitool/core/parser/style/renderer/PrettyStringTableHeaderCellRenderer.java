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
 * @version $Id: PrettyStringTableHeaderCellRenderer.java 811 2008-04-18
 *          13:52:03Z fehler $
 */
public final class PrettyStringTableHeaderCellRenderer extends
    DefaultTableCellRenderer
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 8028186422772719475L;


  /**
   * The {@link PrettyStringComponent}.
   */
  private PrettyStringComponent prettyStringComponent;


  /**
   * Allocates a new {@link PrettyStringTableHeaderCellRenderer}.
   */
  public PrettyStringTableHeaderCellRenderer ()
  {
    super ();
    this.prettyStringComponent = new PrettyStringComponent ();
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

    this.prettyStringComponent.setPrettyString ( prettyString );

    this.prettyStringComponent.setCenterHorizontal ( true );
    this.prettyStringComponent.setCenterVertical ( true );

    JTableHeader header = table.getTableHeader ();
    this.prettyStringComponent.setForeground ( header.getForeground () );
    this.prettyStringComponent.setBackground ( header.getBackground () );
    this.prettyStringComponent.setFont ( header.getFont () );
    this.prettyStringComponent.setEnabled ( table.isEnabled () );
    this.prettyStringComponent.setBorder ( UIManager
        .getBorder ( "TableHeader.cellBorder" ) ); //$NON-NLS-1$

    return this.prettyStringComponent;
  }
}
