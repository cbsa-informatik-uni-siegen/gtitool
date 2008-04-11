package de.unisiegen.gtitool.ui.style.editor;


import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.CellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The parser {@link CellEditor}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class ParserCellEditor extends AbstractCellEditor implements
    TableCellEditor
{

  /**
   * The serial version id.
   */
  private static final long serialVersionUID = -2928324793424527710L;


  /**
   * The {@link StyledParserPanel}.
   */
  private StyledParserPanel styledParserPanel;


  /**
   * Allocates a new {@link ParserCellEditor}.
   * 
   * @param styledParserPanel The used {@link StyledParserPanel}.
   */
  public ParserCellEditor ( StyledParserPanel styledParserPanel )
  {
    this.styledParserPanel = styledParserPanel;
    this.styledParserPanel.setCellEditor ( true );
  }


  /**
   * {@inheritDoc}
   * 
   * @see CellEditor#getCellEditorValue()
   */
  public final Object getCellEditorValue ()
  {
    return this.styledParserPanel.getParsedObject ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see TableCellEditor#getTableCellEditorComponent(JTable, Object, boolean,
   *      int, int)
   */
  public final Component getTableCellEditorComponent (
      @SuppressWarnings ( "unused" )
      JTable table, Object value, @SuppressWarnings ( "unused" )
      boolean isSelected, @SuppressWarnings ( "unused" )
      int rowIndex, @SuppressWarnings ( "unused" )
      int colIndex )
  {
    this.styledParserPanel.setText ( value );
    return this.styledParserPanel;
  }
}
