package de.unisiegen.gtitool.ui.swing.dnd;


import java.util.Arrays;

import javax.swing.ListModel;


/**
 * This class stores the row indices and the {@link ListModel} which are used
 * during a drag and drop.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class JGTIListModelRows
{

  /**
   * The underlying {@link ListModel}.
   * 
   * @see #getModel()
   */
  private final ListModel model;


  /**
   * The indices of the rows to transfer.
   * 
   * @see #getRowIndices()
   */
  private final int [] rowIndices;


  /**
   * Allocates a new {@link JGTIListModelRows}.
   * 
   * @param model The {@link ListModel}.
   * @param rowIndices The row indices.
   */
  public JGTIListModelRows ( ListModel model, int [] rowIndices )
  {
    int rowCount = model.getSize ();
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
    if ( other instanceof JGTIListModelRows )
    {
      JGTIListModelRows listModelRows = ( JGTIListModelRows ) other;
      return ( this.model == listModelRows.model && Arrays.equals (
          this.rowIndices, listModelRows.rowIndices ) );
    }
    return false;
  }


  /**
   * Returns the {@link ListModel} from which to transfer the row data.
   * 
   * @return The source model.
   */
  public final ListModel getModel ()
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
