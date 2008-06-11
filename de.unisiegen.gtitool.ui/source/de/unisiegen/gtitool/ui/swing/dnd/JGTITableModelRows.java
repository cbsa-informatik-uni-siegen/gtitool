package de.unisiegen.gtitool.ui.swing.dnd;


import java.util.Arrays;

import javax.swing.table.TableModel;

import de.unisiegen.gtitool.ui.swing.JGTITable;


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
   * The source {@link JGTITable}.
   * 
   * @see #getSource()
   */
  private final JGTITable source;


  /**
   * The indices of the rows to transfer.
   * 
   * @see #getRowIndices()
   */
  private final int [] rowIndices;


  /**
   * Allocates a new {@link JGTITableModelRows}.
   * 
   * @param source The source {@link JGTITable}.
   * @param rowIndices The row indices.
   */
  public JGTITableModelRows ( JGTITable source, int [] rowIndices )
  {
    if ( source == null )
    {
      throw new IllegalArgumentException ( "source is null" ); //$NON-NLS-1$
    }
    if ( source.getModel () == null )
    {
      throw new IllegalArgumentException ( "model is null" );//$NON-NLS-1$
    }
    int rowCount = source.getModel ().getRowCount ();
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
    if ( other instanceof JGTITableModelRows )
    {
      JGTITableModelRows tableModelRows = ( JGTITableModelRows ) other;
      return ( ( this.source == tableModelRows.source ) && Arrays.equals (
          this.rowIndices, tableModelRows.rowIndices ) );
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
   * Returns the source {@link JGTITable}.
   * 
   * @return The source {@link JGTITable}.
   * @see #source
   */
  public final JGTITable getSource ()
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
