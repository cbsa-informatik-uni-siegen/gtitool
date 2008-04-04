package de.unisiegen.gtitool.ui.swing.dnd;


import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.table.TableModel;


/**
 * Transferable for {@link JGTITableModelRows}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class JGTITableModelRowsTransferable implements Transferable
{

  /**
   * The {@link DataFlavor} used to identify data transfers of rows from a
   * {@link TableModel}.
   */
  public static final DataFlavor tableModelRowsFlavor;

  static
  {
    try
    {
      tableModelRowsFlavor = new DataFlavor (
          DataFlavor.javaJVMLocalObjectMimeType + ";class=" //$NON-NLS-1$
              + JGTITableModelRows.class.getCanonicalName () );
    }
    catch ( ClassNotFoundException e )
    {
      throw new RuntimeException ( e );
    }
  }


  /**
   * The {@link JGTITableModelRows}.
   * 
   * @see #getRows()
   */
  private final JGTITableModelRows tableModelRows;


  //

  /**
   * Allocates a new {@link JGTITableModelRowsTransferable} for the specified rows.
   * 
   * @param tableModelRows The {@link JGTITableModelRows} to transfer.
   * @throws NullPointerException if <code>rows</code> is <code>null</code>.
   */
  public JGTITableModelRowsTransferable ( JGTITableModelRows tableModelRows )
  {
    if ( tableModelRows == null )
    {
      throw new NullPointerException ( "rows can not be null" ); //$NON-NLS-1$
    }
    this.tableModelRows = tableModelRows;
  }


  /**
   * Returns the {@link JGTITableModelRows}.
   * 
   * @return The {@link JGTITableModelRows}.
   */
  public final JGTITableModelRows getRows ()
  {
    return this.tableModelRows;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Transferable#getTransferData(DataFlavor)
   */
  @SuppressWarnings ( "unused" )
  public final Object getTransferData ( DataFlavor flavor )
      throws UnsupportedFlavorException, IOException
  {
    if ( isDataFlavorSupported ( flavor ) )
    {
      return this.tableModelRows;
    }
    throw new UnsupportedFlavorException ( flavor );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Transferable#getTransferDataFlavors()
   */
  public final DataFlavor [] getTransferDataFlavors ()
  {
    return new DataFlavor []
    { tableModelRowsFlavor };
  }


  /**
   * {@inheritDoc}
   * 
   * @see Transferable#isDataFlavorSupported(DataFlavor)
   */
  public final boolean isDataFlavorSupported ( DataFlavor dataFlavor )
  {
    for ( DataFlavor supportedFlavor : getTransferDataFlavors () )
    {
      if ( supportedFlavor.equals ( dataFlavor ) )
      {
        return true;
      }
    }
    return false;
  }
}
