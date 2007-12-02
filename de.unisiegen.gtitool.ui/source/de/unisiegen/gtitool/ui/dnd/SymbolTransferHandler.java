package de.unisiegen.gtitool.ui.dnd;


import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.TransferHandler;

import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.ui.logic.TransitionDialog.SymbolListModel;


/**
 * The {@link Symbol} transfer handler class.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public final class SymbolTransferHandler extends TransferHandler
{

  /**
   * The array list {@link Transferable} class.
   * 
   * @author Christian Fehler
   */
  public final class ArrayListTransferable implements Transferable
  {

    /**
     * The data.
     */
    private ArrayList < Symbol > data;


    /**
     * Allocates a new <code>ArrayListTransferable</code>.
     * 
     * @param pData The data.
     */
    public ArrayListTransferable ( ArrayList < Symbol > pData )
    {
      this.data = pData;
    }


    /**
     * {@inheritDoc}
     * 
     * @see Transferable#getTransferData(DataFlavor)
     */
    public  final Object getTransferData ( DataFlavor pDataFlavor )
        throws UnsupportedFlavorException
    {
      if ( !isDataFlavorSupported ( pDataFlavor ) )
      {
        throw new UnsupportedFlavorException ( pDataFlavor );
      }
      return this.data;
    }


    /**
     * {@inheritDoc}
     * 
     * @see Transferable#getTransferDataFlavors()
     */
    @SuppressWarnings ( "synthetic-access" )
    public  final DataFlavor [] getTransferDataFlavors ()
    {
      return new DataFlavor []
      { SymbolTransferHandler.this.dataFlavor };
    }


    /**
     * {@inheritDoc}
     * 
     * @see Transferable#isDataFlavorSupported(DataFlavor)
     */
    @SuppressWarnings ( "synthetic-access" )
    public  final boolean isDataFlavorSupported ( DataFlavor flavor )
    {
      if ( SymbolTransferHandler.this.dataFlavor.equals ( flavor ) )
      {
        return true;
      }
      return false;
    }
  }


  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 7974892286066949945L;


  /**
   * The {@link DataFlavor}.
   */
  private DataFlavor dataFlavor;


  /**
   * The local array list mime type.
   */
  private String localArrayListType = DataFlavor.javaJVMLocalObjectMimeType
      + ";class=java.util.ArrayList"; //$NON-NLS-1$


  /**
   * The source {@link JList}.
   */
  private JList source = null;


  /**
   * The indices which should be moved.
   */
  private int [] indices = null;


  /**
   * Allocates a new <code>SymbolTransferHandler</code>.
   */
  public SymbolTransferHandler ()
  {
    try
    {
      this.dataFlavor = new DataFlavor ( this.localArrayListType );
    }
    catch ( ClassNotFoundException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see TransferHandler#canImport(JComponent, DataFlavor[])
   */
  @Override
  public final boolean canImport ( @SuppressWarnings ( "unused" )
  JComponent pTarget, DataFlavor [] pDataFlavors )
  {
    if ( hasLocalArrayListFlavor ( pDataFlavors ) )
    {
      return true;
    }
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see TransferHandler#createTransferable(JComponent)
   */
  @Override
  protected  final Transferable createTransferable ( JComponent pSource )
  {
    if ( pSource instanceof JList )
    {
      this.source = ( JList ) pSource;
      this.indices = this.source.getSelectedIndices ();
      Object [] values = this.source.getSelectedValues ();
      if ( values == null || values.length == 0 )
      {
        return null;
      }
      ArrayList < Symbol > sendList = new ArrayList < Symbol > ( values.length );
      for ( int i = 0 ; i < values.length ; i++ )
      {
        sendList.add ( ( Symbol ) values [ i ] );
      }
      return new ArrayListTransferable ( sendList );
    }
    return null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see TransferHandler#exportDone(JComponent, Transferable, int)
   */
  @Override
  protected  final void exportDone ( @SuppressWarnings ( "unused" )
  JComponent pSource, @SuppressWarnings ( "unused" )
  Transferable pTransferable, int pAction )
  {
    if ( ( pAction == MOVE ) && ( this.indices != null ) )
    {
      SymbolListModel symbolListModel = ( SymbolListModel ) this.source
          .getModel ();
      for ( int i = this.indices.length - 1 ; i >= 0 ; i-- )
      {
        symbolListModel.remove ( this.indices [ i ] );
      }
    }
    this.indices = null;
  }


  /**
   * {@inheritDoc}
   * 
   * @see TransferHandler#getSourceActions(JComponent)
   */
  @Override
  public  final int getSourceActions ( @SuppressWarnings ( "unused" )
  JComponent pSource )
  {
    return MOVE;
  }


  /**
   * Returns true if the local array list flavor is used.
   * 
   * @param pDataFlavors
   * @return True if the local array list flavor is used.
   */
  private  final boolean hasLocalArrayListFlavor ( DataFlavor [] pDataFlavors )
  {
    if ( this.dataFlavor == null )
    {
      return false;
    }
    for ( int i = 0 ; i < pDataFlavors.length ; i++ )
    {
      if ( pDataFlavors [ i ].equals ( this.dataFlavor ) )
      {
        return true;
      }
    }
    return false;
  }


  /**
   * {@inheritDoc}
   * 
   * @see TransferHandler#importData(JComponent, Transferable)
   */
  @SuppressWarnings ( "unchecked" )
  @Override
  public  final boolean importData ( JComponent pTarget, Transferable pTransferable )
  {
    JList targetJList = null;
    ArrayList < Symbol > receivedList = null;
    if ( !canImport ( pTarget, pTransferable.getTransferDataFlavors () ) )
    {
      return false;
    }
    try
    {
      targetJList = ( JList ) pTarget;
      if ( hasLocalArrayListFlavor ( pTransferable.getTransferDataFlavors () ) )
      {
        receivedList = ( ArrayList < Symbol > ) pTransferable
            .getTransferData ( this.dataFlavor );
      }
      else
      {
        return false;
      }
    }
    catch ( UnsupportedFlavorException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return false;
    }
    catch ( IOException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return false;
    }

    if ( this.source.equals ( targetJList ) )
    {
      this.indices = null;
      return true;
    }

    SymbolListModel symbolListModel = ( SymbolListModel ) targetJList
        .getModel ();
    for ( int i = 0 ; i < receivedList.size () ; i++ )
    {
      symbolListModel.add ( receivedList.get ( i ) );
    }
    return true;
  }
}
