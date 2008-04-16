package de.unisiegen.gtitool.ui.style.editor;


import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.CellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The parser {@link TableCellEditor}.
 * 
 * @author Christian Fehler
 * @version $Id$
 * @param <E> The {@link Entity}.
 */
public class ParserTableCellEditor < E extends Entity > extends
    AbstractCellEditor implements TableCellEditor
{

  /**
   * The {@link TableEditorDelegate} class.
   */
  private class TableEditorDelegate implements ActionListener, ItemListener,
      Serializable
  {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 7773336300469014628L;


    /**
     * The value of this cell.
     */
    private Object value;


    /**
     * Allocates a new {@link TableEditorDelegate}.
     */
    public TableEditorDelegate ()
    {
      // Do nothing
    }


    /**
     * {@inheritDoc}
     * 
     * @see ActionListener#actionPerformed(ActionEvent)
     */
    public void actionPerformed ( @SuppressWarnings ( "unused" )
    ActionEvent event )
    {
      ParserTableCellEditor.this.stopCellEditing ();
    }


    /**
     * Cancels the editing.
     */
    @SuppressWarnings ( "synthetic-access" )
    public void cancelCellEditing ()
    {
      fireEditingCanceled ();
    }


    /**
     * Returns the value of this cell.
     * 
     * @return the value of this cell
     */
    public Object getCellEditorValue ()
    {
      return this.value;
    }


    /**
     * Returns true if cell is ready for editing, false otherwise.
     * 
     * @param event The {@link EventObject}.
     * @return True if cell is ready for editing, false otherwise.
     */
    public boolean isCellEditable ( EventObject event )
    {
      if ( event instanceof MouseEvent )
      {
        return ( ( MouseEvent ) event ).getClickCount () >= 2;
      }
      return true;
    }


    /**
     * {@inheritDoc}
     * 
     * @see ItemListener#itemStateChanged(ItemEvent)
     */
    public void itemStateChanged ( @SuppressWarnings ( "unused" )
    ItemEvent event )
    {
      ParserTableCellEditor.this.stopCellEditing ();
    }


    /**
     * Sets the value of this cell.
     * 
     * @param value The new value of this cell.
     */
    public void setValue ( Object value )
    {
      this.value = value;
    }


    /**
     * Returns true to indicate that the editing cell may be selected.
     * 
     * @param event The {@link EventObject}.
     * @return True
     */
    public boolean shouldSelectCell ( EventObject event )
    {
      return true;
    }


    /**
     * Starts the cell editing.
     * 
     * @param event The {@link EventObject}.
     * @return True.
     */
    public boolean startCellEditing ( EventObject event )
    {
      return true;
    }


    /**
     * Stops the cell editing.
     * 
     * @return True.
     */
    @SuppressWarnings ( "synthetic-access" )
    public boolean stopCellEditing ()
    {
      fireEditingStopped ();
      return true;
    }
  }


  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 2722971039504632588L;


  /**
   * The {@link StyledParserPanel}.
   */
  private StyledParserPanel < E > styledParserPanel;


  /**
   * The {@link TableEditorDelegate}.
   */
  private TableEditorDelegate delegate;


  /**
   * Allocates a new {@link ParserTableCellEditor}.
   * 
   * @param styledParserPanel The {@link StyledParserPanel}.
   */
  public ParserTableCellEditor ( StyledParserPanel < E > styledParserPanel )
  {
    this.styledParserPanel = styledParserPanel;
    this.styledParserPanel.setCellEditor ( true );

    this.delegate = new TableEditorDelegate ()
    {

      /**
       * The serial version uid.
       */
      private static final long serialVersionUID = -6531666207572045343L;


      @SuppressWarnings ( "synthetic-access" )
      @Override
      public Object getCellEditorValue ()
      {
        return ParserTableCellEditor.this.styledParserPanel.getParsedObject ();
      }


      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void setValue ( Object value )
      {
        ParserTableCellEditor.this.styledParserPanel.setText ( value );
      }
    };
  }


  /**
   * {@inheritDoc}
   * 
   * @see AbstractCellEditor#cancelCellEditing()
   */
  @Override
  public void cancelCellEditing ()
  {
    this.delegate.cancelCellEditing ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see CellEditor#getCellEditorValue()
   */
  public Object getCellEditorValue ()
  {
    return this.delegate.getCellEditorValue ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see TableCellEditor#getTableCellEditorComponent(JTable, Object, boolean,
   *      int, int)
   */
  public Component getTableCellEditorComponent ( @SuppressWarnings ( "unused" )
  JTable table, Object value, @SuppressWarnings ( "unused" )
  boolean isSelected, @SuppressWarnings ( "unused" )
  int row, @SuppressWarnings ( "unused" )
  int column )
  {
    this.delegate.setValue ( value );
    return this.styledParserPanel;
  }


  /**
   * {@inheritDoc}
   * 
   * @see AbstractCellEditor#isCellEditable(EventObject)
   */
  @Override
  public final boolean isCellEditable ( EventObject anEvent )
  {
    return this.delegate.isCellEditable ( anEvent );
  }


  /**
   * {@inheritDoc}
   * 
   * @see AbstractCellEditor#shouldSelectCell(EventObject)
   */
  @Override
  public final boolean shouldSelectCell ( EventObject anEvent )
  {
    return this.delegate.shouldSelectCell ( anEvent );
  }


  /**
   * {@inheritDoc}
   * 
   * @see AbstractCellEditor#stopCellEditing()
   */
  @Override
  public final boolean stopCellEditing ()
  {
    return this.delegate.stopCellEditing ();
  }
}
