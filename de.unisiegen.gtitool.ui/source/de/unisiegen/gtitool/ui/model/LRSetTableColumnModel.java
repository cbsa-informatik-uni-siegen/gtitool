package de.unisiegen.gtitool.ui.model;


import java.util.TreeMap;

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
    // TODO!
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
        final TableColumn column = new TableColumn ( 0 );
        column.setHeaderValue ( new PrettyString ( new PrettyToken ( stateName,
            Style.NONE ) ) );
        column.setHeaderRenderer ( new PrettyStringTableHeaderCellRenderer () );
        column.setCellRenderer ( new PrettyStringTableCellRenderer () );
        this.columns.put ( stateName, new ColumnEntry ( column ) );

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


  private class ColumnEntry
  {

    public ColumnEntry ( final TableColumn column )
    {
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


    private TableColumn column;


    private int refcount;
  }


  private TreeMap < String, ColumnEntry > columns = new TreeMap < String, ColumnEntry > ();
}
