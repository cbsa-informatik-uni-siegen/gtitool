package de.unisiegen.gtitool.ui.swing.dnd;


import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;


/**
 * Transferable for {@link JGTITabbedPaneComponent}.
 * 
 * @author Christian Fehler
 * @version $Id: JGTIListModelRowsTransferable.java 728 2008-04-04 12:28:48Z
 *          fehler $
 */
public final class JGTITabbedPaneTransferable implements Transferable
{

  /**
   * The {@link DataFlavor}.
   */
  public static final DataFlavor dataFlavor;

  static
  {
    try
    {
      dataFlavor = new DataFlavor ( DataFlavor.javaJVMLocalObjectMimeType
          + ";class=" //$NON-NLS-1$
          + JGTITabbedPaneComponent.class.getCanonicalName () );
    }
    catch ( ClassNotFoundException e )
    {
      throw new RuntimeException ( e );
    }
  }


  /**
   * The {@link JGTITabbedPaneComponent}.
   * 
   * @see #getJGTITabbedPaneComponent()
   */
  private final JGTITabbedPaneComponent jGTITabbedPaneComponent;


  /**
   * Allocates a new {@link JGTITabbedPaneTransferable} for the specified rows.
   * 
   * @param jGTITabbedPaneComponent The {@link JGTITabbedPaneComponent} to
   *          transfer.
   * @throws NullPointerException if <code>rows</code> is <code>null</code>.
   */
  public JGTITabbedPaneTransferable (
      JGTITabbedPaneComponent jGTITabbedPaneComponent )
  {
    if ( jGTITabbedPaneComponent == null )
    {
      throw new NullPointerException ( "tabbed pane component can not be null" ); //$NON-NLS-1$
    }
    this.jGTITabbedPaneComponent = jGTITabbedPaneComponent;
  }


  /**
   * Returns the {@link JGTITabbedPaneComponent}.
   * 
   * @return The {@link JGTITabbedPaneComponent}.
   */
  public final JGTITabbedPaneComponent getJGTITabbedPaneComponent ()
  {
    return this.jGTITabbedPaneComponent;
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
      return this.jGTITabbedPaneComponent;
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
    { dataFlavor };
  }


  /**
   * {@inheritDoc}
   * 
   * @see Transferable#isDataFlavorSupported(DataFlavor)
   */
  public final boolean isDataFlavorSupported ( DataFlavor checkDataFlavor )
  {
    for ( DataFlavor supportedFlavor : getTransferDataFlavors () )
    {
      if ( supportedFlavor.equals ( checkDataFlavor ) )
      {
        return true;
      }
    }
    return false;
  }
}
