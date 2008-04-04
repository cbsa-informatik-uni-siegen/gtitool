package de.unisiegen.gtitool.ui.swing.dnd;


import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.ListModel;


/**
 * Transferable for {@link JGTIListModelRows}.
 * 
 * @author Christian Fehler
 * @version $Id: JGTIListModelRowsTransferable.java 728 2008-04-04 12:28:48Z
 *          fehler $
 */
public final class JGTIListModelRowsTransferable implements Transferable
{

  /**
   * The {@link DataFlavor} used to identify data transfers of rows from a
   * {@link ListModel}.
   */
  public static final DataFlavor listModelRowsFlavor;

  static
  {
    try
    {
      listModelRowsFlavor = new DataFlavor (
          DataFlavor.javaJVMLocalObjectMimeType + ";class=" //$NON-NLS-1$
              + JGTIListModelRows.class.getCanonicalName () );
    }
    catch ( ClassNotFoundException e )
    {
      throw new RuntimeException ( e );
    }
  }


  /**
   * The {@link JGTIListModelRows}.
   * 
   * @see #getRows()
   */
  private final JGTIListModelRows listModelRows;


  /**
   * Allocates a new {@link JGTIListModelRowsTransferable} for the specified
   * rows.
   * 
   * @param listModelRows The {@link JGTIListModelRows} to transfer.
   * @throws NullPointerException if <code>rows</code> is <code>null</code>.
   */
  public JGTIListModelRowsTransferable ( JGTIListModelRows listModelRows )
  {
    if ( listModelRows == null )
    {
      throw new NullPointerException ( "rows can not be null" ); //$NON-NLS-1$
    }
    this.listModelRows = listModelRows;
  }


  /**
   * Returns the {@link JGTIListModelRows}.
   * 
   * @return The {@link JGTIListModelRows}.
   */
  public final JGTIListModelRows getRows ()
  {
    return this.listModelRows;
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
      return this.listModelRows;
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
    { listModelRowsFlavor };
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
