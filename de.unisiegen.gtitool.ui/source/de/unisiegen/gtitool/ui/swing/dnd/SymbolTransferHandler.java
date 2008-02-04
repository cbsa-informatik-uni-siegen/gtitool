package de.unisiegen.gtitool.ui.swing.dnd;


import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.ui.logic.TransitionDialog;
import de.unisiegen.gtitool.ui.swing.JGTIList;


/**
 * The {@link Symbol} transfer handler class.
 * 
 * @author Christian Fehler
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
     * Allocates a new {@link ArrayListTransferable}.
     * 
     * @param data The data.
     */
    public ArrayListTransferable ( ArrayList < Symbol > data )
    {
      this.data = data;
    }


    /**
     * {@inheritDoc}
     * 
     * @see Transferable#getTransferData(DataFlavor)
     */
    public final Object getTransferData ( DataFlavor dataFlavor )
        throws UnsupportedFlavorException
    {
      if ( !isDataFlavorSupported ( dataFlavor ) )
      {
        throw new UnsupportedFlavorException ( dataFlavor );
      }
      return this.data;
    }


    /**
     * {@inheritDoc}
     * 
     * @see Transferable#getTransferDataFlavors()
     */
    @SuppressWarnings ( "synthetic-access" )
    public final DataFlavor [] getTransferDataFlavors ()
    {
      return new DataFlavor []
      { SymbolTransferHandler.this.symbolDataFlavor };
    }


    /**
     * {@inheritDoc}
     * 
     * @see Transferable#isDataFlavorSupported(DataFlavor)
     */
    @SuppressWarnings ( "synthetic-access" )
    public final boolean isDataFlavorSupported ( DataFlavor dataFlavor )
    {
      if ( SymbolTransferHandler.this.symbolDataFlavor.equals ( dataFlavor ) )
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
  private DataFlavor symbolDataFlavor;


  /**
   * The local array list mime type.
   */
  private String localArrayListType = DataFlavor.javaJVMLocalObjectMimeType
      + ";class=java.util.ArrayList"; //$NON-NLS-1$


  /**
   * The source {@link JGTIList}.
   */
  private JGTIList source = null;


  /**
   * The {@link TransitionDialog}.
   */
  private TransitionDialog transitionDialog;


  /**
   * Allocates a new {@link SymbolTransferHandler}.
   * 
   * @param transitionDialog
   */
  public SymbolTransferHandler ( TransitionDialog transitionDialog )
  {
    try
    {
      this.symbolDataFlavor = new DataFlavor ( this.localArrayListType );
    }
    catch ( ClassNotFoundException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    this.transitionDialog = transitionDialog;
  }


  /**
   * {@inheritDoc}
   * 
   * @see TransferHandler#canImport(JComponent, DataFlavor[])
   */
  @Override
  public final boolean canImport ( @SuppressWarnings ( "unused" )
  JComponent targetList, DataFlavor [] dataFlavors )
  {
    if ( hasLocalArrayListFlavor ( dataFlavors ) )
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
  protected final Transferable createTransferable ( JComponent sourceList )
  {
    if ( sourceList instanceof JGTIList )
    {
      this.source = ( JGTIList ) sourceList;
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
   * @see TransferHandler#getSourceActions(JComponent)
   */
  @Override
  public final int getSourceActions ( @SuppressWarnings ( "unused" )
  JComponent sourceList )
  {
    return MOVE;
  }


  /**
   * Returns true if the local array list flavor is used.
   * 
   * @param dataFlavors
   * @return True if the local array list flavor is used.
   */
  private final boolean hasLocalArrayListFlavor ( DataFlavor [] dataFlavors )
  {
    if ( this.symbolDataFlavor == null )
    {
      return false;
    }
    for ( int i = 0 ; i < dataFlavors.length ; i++ )
    {
      if ( dataFlavors [ i ].equals ( this.symbolDataFlavor ) )
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
  public final boolean importData ( JComponent targetList,
      Transferable transferable )
  {
    JGTIList targetJList = null;
    ArrayList < Symbol > receivedList = null;
    if ( !canImport ( targetList, transferable.getTransferDataFlavors () ) )
    {
      return false;
    }
    try
    {
      targetJList = ( JGTIList ) targetList;
      if ( hasLocalArrayListFlavor ( transferable.getTransferDataFlavors () ) )
      {
        receivedList = ( ArrayList < Symbol > ) transferable
            .getTransferData ( this.symbolDataFlavor );
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
      return true;
    }

    if ( this.source == this.transitionDialog.getGui ().jGTIListAlphabet )
    {
      this.transitionDialog.addToChangeOver ( receivedList );
    }
    else
    {
      this.transitionDialog.removeFromChangeOver ( receivedList );
    }
    return true;
  }
}
