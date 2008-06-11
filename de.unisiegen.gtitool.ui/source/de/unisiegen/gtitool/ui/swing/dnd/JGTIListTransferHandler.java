package de.unisiegen.gtitool.ui.swing.dnd;


import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import de.unisiegen.gtitool.ui.swing.JGTIList;


/**
 * Drag and drop transfer handler class for {@link JGTIList}s.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class JGTIListTransferHandler extends TransferHandler
{

  /**
   * The source actions supported for dragging using this
   * {@link JGTIListTransferHandler}.
   * 
   * @see #getSourceActions(JComponent)
   */
  private final int sourceActions;


  /**
   * Allocates a new {@link JGTIListTransferHandler}.
   * 
   * @param sourceActions The actions to support for dragging using this
   *          {@link JGTIListTransferHandler}.
   */
  public JGTIListTransferHandler ( int sourceActions )
  {
    super ();
    this.sourceActions = sourceActions;
  }


  /**
   * {@inheritDoc}
   * 
   * @see TransferHandler#canImport(JComponent, DataFlavor[])
   */
  @Override
  public final boolean canImport ( JComponent jComponent,
      DataFlavor [] dataFlavor )
  {
    if ( jComponent instanceof JGTIList )
    {
      for ( DataFlavor transferFlavor : dataFlavor )
      {
        if ( transferFlavor
            .equals ( JGTIListModelRowsTransferable.listModelRowsFlavor ) )
        {
          return true;
        }
      }
    }
    return super.canImport ( jComponent, dataFlavor );
  }


  /**
   * {@inheritDoc}
   * 
   * @see TransferHandler#createTransferable(JComponent)
   */
  @Override
  protected final Transferable createTransferable ( JComponent jComponent )
  {
    JGTIList list = ( JGTIList ) jComponent;
    int [] selectedRows = list.getSelectedIndices ();
    if ( selectedRows.length > 0 )
    {
      return new JGTIListModelRowsTransferable ( new JGTIListModelRows ( list,
          selectedRows ) );
    }
    return null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see TransferHandler#getSourceActions(JComponent)
   */
  @Override
  public final int getSourceActions ( @SuppressWarnings ( "unused" )
  JComponent jComponent )
  {
    return this.sourceActions;
  }


  /**
   * {@inheritDoc}
   * 
   * @see TransferHandler#importData(JComponent, Transferable)
   */
  @Override
  public final boolean importData ( JComponent jComponent,
      Transferable transferable )
  {
    JGTIList list = ( JGTIList ) jComponent;
    try
    {
      JGTIListModelRows rows = ( JGTIListModelRows ) transferable
          .getTransferData ( JGTIListModelRowsTransferable.listModelRowsFlavor );

      int sourceIndex = rows.getRowIndices () [ 0 ];
      int targetIndex = list.locationToIndex ( list.getDropPoint () );
      if ( targetIndex == -1 )
      {
        targetIndex = list.getModel ().getSize () - 1;
      }
      else if ( sourceIndex < targetIndex )
      {
        targetIndex-- ;
      }

      if ( importListModelRows ( list, rows, targetIndex ) )
      {
        return true;
      }
      return super.importData ( jComponent, transferable );
    }
    catch ( IOException e )
    {
      throw new RuntimeException ( e );
    }
    catch ( UnsupportedFlavorException e )
    {
      throw new RuntimeException ( e );
    }
  }


  /**
   * Imports the rows from the drag source into the specified list.
   * 
   * @param list The {@link JGTIList} into which to import the rows.
   * @param rows The rows to import from the drag source.
   * @param targetIndex The target index.
   * @return True if the import was successfull.
   * @see #importData(JComponent, Transferable)
   */
  protected abstract boolean importListModelRows ( JGTIList list,
      JGTIListModelRows rows, int targetIndex );
}
