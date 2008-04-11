package de.unisiegen.gtitool.ui.style.editor;


import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.CellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import de.unisiegen.gtitool.core.entities.StateSet;
import de.unisiegen.gtitool.ui.style.StyledStateSetParserPanel;


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
   * The {@link StyledStateSetParserPanel}.
   */
  private StyledStateSetParserPanel parserPanel;


  /**
   * Allocates a new {@link ParserCellEditor}.
   */
  public ParserCellEditor ()
  {
    this.parserPanel = new StyledStateSetParserPanel ();
    this.parserPanel.setSideBarVisible ( false );
    this.parserPanel.setScrollBarEnabled ( false );
  }


  /**
   * {@inheritDoc}
   * 
   * @see CellEditor#getCellEditorValue()
   */
  public final Object getCellEditorValue ()
  {
    return this.parserPanel.getStateSet ();
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
    this.parserPanel.setText ( ( ( StateSet ) value ) );
    return this.parserPanel;
  }
}
