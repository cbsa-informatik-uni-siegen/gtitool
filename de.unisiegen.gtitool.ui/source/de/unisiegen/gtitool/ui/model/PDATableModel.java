package de.unisiegen.gtitool.ui.model;


import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.parser.style.PrettyString;


/**
 * The table model for the pda table.
 * 
 * @author Benjamin Mies
 * @author Christian Fehler
 * @version $Id:DefaultTableModel.java 305 2007-12-06 19:55:14Z mies $
 */
public final class PDATableModel extends AbstractTableModel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -8148566233530100878L;


  /**
   * The transition column.
   */
  public final static int TRANSITION_COLUMN = 0;


  /**
   * The column count.
   */
  private final static int COLUMN_COUNT = 1;


  /**
   * The data of this table model
   */
  private ArrayList < Transition > data = new ArrayList < Transition > ();


  /**
   * Adds a row to this data model.
   * 
   * @param transition the {@link Transition}.
   */
  public final void addRow ( Transition transition )
  {
    this.data.add ( transition );
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
      case TRANSITION_COLUMN :
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
      case TRANSITION_COLUMN :
      {
        return "Stack Operation"; //$NON-NLS-1$
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
   * Returns the {@link Transition} of the given row index.
   * 
   * @param rowIndex The given row index.
   * @return The {@link Transition} list of the given row index.
   */
  public final Transition getTransition ( int rowIndex )
  {
    return this.data.get ( rowIndex );
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
      case TRANSITION_COLUMN :
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
   * Removes a row to this data model.
   * 
   * @param transition the {@link Transition}.
   */
  public final void removeRow ( Transition transition )
  {
    int index = -1;
    for ( int i = 0 ; i < this.data.size () ; i++ )
    {
      if ( this.data.get ( i ) == transition )
      {
        index = i;
        break;
      }
    }
    if ( index != -1 )
    {
      this.data.remove ( transition );
      fireTableRowsDeleted ( index, index );
    }
  }
}
