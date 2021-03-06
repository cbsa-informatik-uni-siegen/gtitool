package de.unisiegen.gtitool.ui.model;


import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.parser.style.PrettyString;


/**
 * The table model for the convert machine outline.
 * 
 * @author Benjamin Mies
 * @version $Id: MinimizeMachineTableModel.java 996 2008-06-15 13:51:25Z fehler
 *          $
 */
public final class MinimizeMachineTableModel extends AbstractTableModel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -6476953833850164557L;


  /**
   * The outline column.
   */
  public final static int OUTLINE_COLUMN = 0;


  /**
   * The column count.
   */
  private final static int COLUMN_COUNT = 1;


  /**
   * The data of this table model
   */
  private ArrayList < PrettyString > data;


  /**
   * List of the {@link Transition}s.
   */
  private ArrayList < ArrayList < Transition >> transitionsList = new ArrayList < ArrayList < Transition > > ();


  /**
   * Allocates a new {@link PDATableModel}.
   */
  public MinimizeMachineTableModel ()
  {
    this.data = new ArrayList < PrettyString > ();
  }


  /**
   * Adds a row to this data model.
   * 
   * @param prettyString The {@link PrettyString}s of the row.
   * @param transitions The {@link Transition}s.
   */
  public final void addRow ( PrettyString prettyString,
      ArrayList < Transition > transitions )
  {
    this.data.add ( prettyString );
    this.transitionsList.add ( transitions );
    fireTableRowsInserted ( this.data.size () - 1, this.data.size () - 1 );
  }


  /**
   * Clears the data of this table model.
   */
  public final void clearData ()
  {
    this.data.clear ();
    fireTableDataChanged ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see AbstractTableModel#getColumnClass(int)
   */
  @Override
  public final Class < ? > getColumnClass ( int columnIndex )
  {
    switch ( columnIndex )
    {
      case OUTLINE_COLUMN :
      {
        return PrettyString.class;
      }
      default :
      {
        return Object.class;
      }
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see TableModel#getColumnCount()
   */
  public final int getColumnCount ()
  {
    return COLUMN_COUNT;
  }


  /**
   * {@inheritDoc}
   * 
   * @see AbstractTableModel#getColumnName(int)
   */
  @Override
  public final String getColumnName ( int columnIndex )
  {
    switch ( columnIndex )
    {
      case OUTLINE_COLUMN :
      {
        return "Outline"; //$NON-NLS-1$
      }
      default :
      {
        return ""; //$NON-NLS-1$
      }
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see TableModel#getRowCount()
   */
  public final int getRowCount ()
  {
    return this.data.size ();
  }


  /**
   * Return the {@link Transition}s for the given row.
   * 
   * @param row The row of interest.
   * @return The {@link Transition}s of the given row.
   */
  public ArrayList < Transition > getTransitionsAt ( int row )
  {
    return this.transitionsList.get ( row );
  }


  /**
   * {@inheritDoc}
   * 
   * @see TableModel#getValueAt(int, int)
   */
  public final Object getValueAt ( int rowIndex, int columnIndex )
  {
    switch ( columnIndex )
    {
      case OUTLINE_COLUMN :
      {
        return this.data.get ( rowIndex );
      }
      default :
      {
        return null;
      }
    }
  }


  /**
   * Removes the last row.
   */
  public final void removeLastRow ()
  {
    int index = this.data.size () - 1;
    this.data.remove ( index );
    this.transitionsList.remove ( index );
    fireTableRowsDeleted ( index, index );
  }


  /**
   * Removes a row from this data model.
   * 
   * @param prettyString The {@link PrettyString}.
   */
  public final void removeRow ( PrettyString prettyString )
  {
    int index = -1;
    for ( int i = 0 ; i < this.data.size () ; i++ )
    {
      if ( this.data.get ( i ) == prettyString )
      {
        index = i;
        break;
      }
    }
    if ( index != -1 )
    {
      this.data.remove ( prettyString );
      this.transitionsList.remove ( index );
      fireTableRowsDeleted ( index, index );
    }
  }
}
