package de.unisiegen.gtitool.ui.model;


import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

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
    final String stateName = lrstate.getName ();
    if ( selected )
    {
      final ColumnEntry previousEntry = this.columns.get ( stateName );

      if ( previousEntry != null )
        previousEntry.addRef ();
      else
      {
        final int index = this.columns.size ();

        final TableColumn column = new TableColumn ( index );
        column.setHeaderValue ( new PrettyString ( new PrettyToken ( stateName,
            Style.NONE ) ) );
        column.setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
        column.setCellRenderer ( new PrettyStringTableCellRenderer () );
        this.columns
            .put ( stateName, new ColumnEntry ( index, lrstate, column ) );

        addColumn ( column );
      }
    }
    else
    {
      final ColumnEntry entry = this.columns.get ( stateName );
      if ( entry.Release () )
      {
        this.columns.remove ( stateName );
        this.removeColumn ( entry.getColumn () );
      }
    }
  }


  /**
   * @return the row count
   */
  public int getRowCount ()
  {
    int rowCount = 0;

    final Set < Entry < String, ColumnEntry >> entries = this.columns
        .entrySet ();

    for ( Entry < String, ColumnEntry > entry : entries )
      rowCount = Math.max ( rowCount, entry.getValue ().getState ().getItems ()
          .size () );

    return rowCount;
  }


  /**
   * TODO
   * 
   * @param rowIndex
   * @param columnIndex
   * @return
   */
  public PrettyString getEntry ( final int rowIndex, final int columnIndex )
  {
    final Set < Entry < String, ColumnEntry >> entries = this.columns
        .entrySet ();

    for ( Entry < String, ColumnEntry > entry : entries )
    {
      final ColumnEntry columnEntry = entry.getValue ();

      if ( columnEntry.getIndex () == columnIndex )
      {
        final ArrayList < PrettyString > stringEntries = columnEntry
            .getState ().getItems ().stringEntries ();

        return rowIndex < stringEntries.size () ? stringEntries.get ( rowIndex )
            : new PrettyString ();
      }
    }

    throw new RuntimeException ( "Column not found!" ); //$NON-NLS-1$
  }


  /**
   * TODO
   */
  private class ColumnEntry
  {

    /**
     * TODO
     * 
     * @param index
     * @param state
     * @param column
     */
    public ColumnEntry ( final int index, final LRState state,
        final TableColumn column )
    {
      this.index = index;
      this.state = state;
      this.refcount = 1;
      this.column = column;
    }


    /**
     * TODO
     */
    void addRef ()
    {
      ++this.refcount;
    }


    /**
     * TODO
     * 
     * @return
     */
    boolean Release ()
    {
      --this.refcount;

      return this.refcount == 0;
    }


    /**
     * TODO
     * 
     * @return
     */
    TableColumn getColumn ()
    {
      return this.column;
    }


    /**
     * TODO
     * 
     * @return
     */
    LRState getState ()
    {
      return this.state;
    }


    /**
     * TODO
     * 
     * @return
     */
    public int getIndex ()
    {
      return this.index;
    }


    /**
     * TODO
     */
    private int index;


    /**
     * TODO
     */
    private LRState state;


    /**
     * TODO
     */
    private TableColumn column;


    /**
     * TODO
     */
    private int refcount;
  }


  /**
   * TODO
   */
  private TreeMap < String, ColumnEntry > columns = new TreeMap < String, ColumnEntry > ();
}
