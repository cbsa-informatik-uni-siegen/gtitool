package de.unisiegen.gtitool.ui.model;


import java.util.ArrayList;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import de.unisiegen.gtitool.core.entities.LRState;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.parser.style.renderer.PrettyStringTableCellRenderer;
import de.unisiegen.gtitool.core.parser.style.renderer.PrettyStringTableHeaderCellRenderer;


/**
 * TODO
 */
public class LRSetTableColumnModel extends DefaultTableColumnModel
{

  /**
   * TODO
   */
  private static final long serialVersionUID = -1779133828069781598L;


  /**
   * TODO
   */
  public LRSetTableColumnModel ()
  {
    // nothing to do here
  }


  /**
   * TODO
   * 
   * @param transition
   */
  public void transitionChanged ( final Transition transition )
  {
    this.clearSelections ();

    stateSelectionChanged ( transition.getStateBegin (), transition
        .isSelected () );
    stateSelectionChanged ( transition.getStateEnd (), transition.isSelected () );

  }


  /**
   * TODO
   * 
   * @param state
   */
  public void stateChanged ( final State state )
  {
    this.clearSelections ();

    stateSelectionChanged ( state, state.isSelected () );
  }


  /**
   * TODO
   * 
   * @param state
   * @param selected
   */
  private void stateSelectionChanged ( final State state, final boolean selected )
  {
    final LRState lrstate = ( LRState ) state;

    if ( !selected )
      return;

    final String stateName = lrstate.getName ();
    final int index = this.columns.size ();

    final TableColumn column = new TableColumn ( index );
    column.setHeaderValue ( new PrettyString ( new PrettyToken ( stateName,
        Style.NONE ) ) );
    column.setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
    column.setCellRenderer ( new PrettyStringTableCellRenderer () );
    this.columns.add ( new ColumnEntry ( lrstate, column ) );

    addColumn ( column );
  }


  /**
   * Removes all previous selections
   */
  private void clearSelections ()
  {
    for ( ColumnEntry entry : this.columns )
      this.removeColumn ( entry.getColumn () );

    this.columns.clear ();
  }


  /**
   * @return the row count
   */
  public int getRowCount ()
  {
    int rowCount = 0;

    for ( ColumnEntry entry : this.columns )
      rowCount = Math.max ( rowCount, entry.getState ().getItems ().size () );

    return rowCount;
  }


  /**
   * @param rowIndex
   * @param columnIndex
   * @return The string
   */
  public PrettyString getEntry ( final int rowIndex, final int columnIndex )
  {
    final ArrayList < PrettyString > stringEntries = this.columns.get (
        columnIndex ).getState ().getItems ().stringEntries ();

    return rowIndex < stringEntries.size () ? stringEntries.get ( rowIndex )
        : new PrettyString ();
  }


  /**
   * TODO
   */
  private class ColumnEntry
  {

    /**
     * TODO
     * 
     * @param state
     * @param column
     */
    public ColumnEntry ( final LRState state, final TableColumn column )
    {
      this.state = state;
      this.column = column;
    }


    /**
     * The column
     * 
     * @return the column
     */
    TableColumn getColumn ()
    {
      return this.column;
    }


    /**
     * The state
     * 
     * @return the state
     */
    LRState getState ()
    {
      return this.state;
    }


    /**
     * TODO
     */
    private LRState state;


    /**
     * TODO
     */
    private TableColumn column;
  }


  /**
   * TODO
   */
  private ArrayList < ColumnEntry > columns = new ArrayList < ColumnEntry > ();
}
