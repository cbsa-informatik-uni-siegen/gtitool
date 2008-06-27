package de.unisiegen.gtitool.core.parser.style.renderer;


import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyStringComponent;


/**
 * The {@link PrettyString} {@link TableCellRenderer}.
 * 
 * @author Christian Fehler
 * @version $Id: PrettyStringTableCellRenderer.java 811 2008-04-18 13:52:03Z
 *          fehler $
 */
public final class PrettyStringTableCellRenderer extends
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
   * Allocates a new {@link PrettyStringTableCellRenderer}.
   */
  public PrettyStringTableCellRenderer ()
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
      boolean isSelected, @SuppressWarnings ( "unused" ) boolean hasFocus,
      @SuppressWarnings ( "unused" ) int row,
      @SuppressWarnings ( "unused" ) int column )
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

    if ( isSelected )
    {
      this.prettyStringComponent.setBackground ( table
          .getSelectionBackground () );
      this.prettyStringComponent.setForeground ( table
          .getSelectionForeground () );
    }
    else
    {
      this.prettyStringComponent.setBackground ( table.getBackground () );
      this.prettyStringComponent.setForeground ( table.getForeground () );
    }

    this.prettyStringComponent.setEnabled ( table.isEnabled () );
    this.prettyStringComponent.setFont ( table.getFont () );

    return this.prettyStringComponent;
  }
}
