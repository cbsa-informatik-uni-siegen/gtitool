package de.unisiegen.gtitool.ui.swing.dnd;


import java.util.Arrays;

import javax.swing.ListModel;

import de.unisiegen.gtitool.ui.swing.JGTIList;


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
   * The source {@link JGTIList}.
   * 
   * @see #getSource()
   */
  private final JGTIList source;


  /**
   * The indices of the rows to transfer.
   * 
   * @see #getRowIndices()
   */
  private final int [] rowIndices;


  /**
   * Allocates a new {@link JGTIListModelRows}.
   * 
   * @param source The {@link JGTIList}.
   * @param rowIndices The row indices.
   */
  public JGTIListModelRows ( JGTIList source, int [] rowIndices )
  {
    if ( source == null )
    {
      throw new IllegalArgumentException ( "source is null" ); //$NON-NLS-1$
    }
    if ( source.getModel () == null )
    {
      throw new IllegalArgumentException ( "model is null" );//$NON-NLS-1$
    }
    int rowCount = source.getModel ().getSize ();
    for ( int rowIndex : rowIndices )
    {
      if ( ( rowIndex < 0 ) || ( rowIndex >= rowCount ) )
      {
        throw new IllegalArgumentException ( "invalid row index " + rowIndex ); //$NON-NLS-1$
      }
    }

    this.source = source;
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
      return ( ( this.source == listModelRows.source ) && Arrays.equals (
          this.rowIndices, listModelRows.rowIndices ) );
    }
    return false;
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
   * Returns the source {@link JGTIList}.
   * 
   * @return The source {@link JGTIList}.
   * @see #source
   */
  public final JGTIList getSource ()
  {
    return this.source;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Object#hashCode()
   */
  @Override
  public final int hashCode ()
  {
    return this.source.hashCode () + Arrays.hashCode ( this.rowIndices );
  }
}
