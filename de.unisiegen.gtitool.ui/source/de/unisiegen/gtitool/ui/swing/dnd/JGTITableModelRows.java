package de.unisiegen.gtitool.ui.swing.dnd;


import java.util.Arrays;

import javax.swing.table.TableModel;


/**
 * This class stores the row indices and the {@link TableModel} which are used
 * during a drag and drop.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class JGTITableModelRows
{

  /**
   * The underlying table model.
   * 
   * @see #getModel()
   */
  private final TableModel model;


  /**
   * The indices of the rows to transfer.
   * 
   * @see #getRowIndices()
   */
  private final int [] rowIndices;


  /**
   * Allocates a new {@link JGTITableModelRows}.
   * 
   * @param model The {@link TableModel}.
   * @param rowIndices The row indices.
   */
  public JGTITableModelRows ( TableModel model, int [] rowIndices )
  {
    int rowCount = model.getRowCount ();
    for ( int rowIndex : rowIndices )
    {
      if ( rowIndex < 0 || rowIndex >= rowCount )
      {
        throw new IllegalArgumentException ( "invalid row index " + rowIndex ); //$NON-NLS-1$
      }
    }
    this.model = model;
    this.rowIndices = rowIndices;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#equals(Object)
   */
  @Override
  public final boolean equals ( Object other )
  {
    if ( other instanceof JGTITableModelRows )
    {
      JGTITableModelRows tableModelRows = ( JGTITableModelRows ) other;
      return ( this.model == tableModelRows.model && Arrays.equals (
          this.rowIndices, tableModelRows.rowIndices ) );
    }
    return false;
  }


  /**
   * Returns the {@link TableModel} from which to transfer the row data.
   * 
   * @return The source model.
   */
  public final TableModel getModel ()
  {
    return this.model;
  }


  /**
   * Returns the indices of the rows to transfer.
   * 
   * @return The row indices.
   */
  public final int [] getRowIndices ()
  {
    return this.rowIndices;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#hashCode()
   */
  @Override
  public final int hashCode ()
  {
    return this.model.hashCode () + Arrays.hashCode ( this.rowIndices );
  }
}
