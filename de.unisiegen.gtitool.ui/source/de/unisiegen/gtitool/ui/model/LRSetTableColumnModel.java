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

  public LRSetTableColumnModel ()
  {
    // nothing to do here
  }


  public void transitionChanged ( final Transition transition )
  {
    stateSelectionChanged ( transition.getStateBegin (), transition
        .isSelected () );
    stateSelectionChanged ( transition.getStateEnd (), transition.isSelected () );

  }


  public void stateChanged ( final State state )
  {
    stateSelectionChanged ( state, state.isSelected () );
  }


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


  private class ColumnEntry
  {

    public ColumnEntry ( final int index, final LRState state,
        final TableColumn column )
    {
      this.index = index;
      this.state = state;
      this.refcount = 1;
      this.column = column;
    }


    void addRef ()
    {
      ++this.refcount;
    }


    boolean Release ()
    {
      --this.refcount;

      return this.refcount == 0;
    }


    TableColumn getColumn ()
    {
      return this.column;
    }


    LRState getState ()
    {
      return this.state;
    }


    public int getIndex ()
    {
      return this.index;
    }


    private int index;


    private LRState state;


    private TableColumn column;


    private int refcount;
  }


  private TreeMap < String, ColumnEntry > columns = new TreeMap < String, ColumnEntry > ();
}
