package de.unisiegen.gtitool.ui.swing.dnd;


import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import de.unisiegen.gtitool.ui.swing.JGTITabbedPane;


/**
 * Drag and drop transfer handler class for {@link JGTITabbedPaneComponent}s.
 * 
 * @author Christian Fehler
 * @version $Id: JGTITabbedPaneTransferHandler.java 1008 2008-06-17 20:39:54Z
 *          fehler $
 */
public abstract class JGTITabbedPaneTransferHandler extends TransferHandler
{

  /**
   * The source actions supported for dragging using this
   * {@link JGTITabbedPaneTransferHandler}.
   * 
   * @see #getSourceActions(JComponent)
   */
  private final int sourceActions;


  /**
   * Allocates a new {@link JGTITabbedPaneTransferHandler}.
   * 
   * @param sourceActions The actions to support for dragging using this
   *          {@link JGTITabbedPaneTransferHandler}.
   */
  public JGTITabbedPaneTransferHandler ( int sourceActions )
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
    if ( jComponent instanceof JGTITabbedPane )
    {
      for ( DataFlavor transferFlavor : dataFlavor )
      {
        if ( transferFlavor.equals ( JGTITabbedPaneTransferable.dataFlavor ) )
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
    JGTITabbedPane jGTITabbedPane = ( JGTITabbedPane ) jComponent;
    Component component = jGTITabbedPane.getSelectedComponent ();
    if ( component != null )
    {
      return new JGTITabbedPaneTransferable ( new JGTITabbedPaneComponent (
          jGTITabbedPane, component ) );
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
   * Imports the {@link Component}.
   * 
   * @param source The source {@link JGTITabbedPane}.
   * @param target The target {@link JGTITabbedPane}.
   * @param component The {@link Component}
   * @return True if the import was successfull.
   */
  protected abstract boolean importComponent ( JGTITabbedPane source,
      JGTITabbedPane target, Component component );


  /**
   * {@inheritDoc}
   * 
   * @see TransferHandler#importData(JComponent, Transferable)
   */
  @Override
  public final boolean importData ( JComponent jComponent,
      Transferable transferable )
  {
    JGTITabbedPane jGTITabbedPane = ( JGTITabbedPane ) jComponent;
    try
    {
      JGTITabbedPaneComponent component = ( JGTITabbedPaneComponent ) transferable
          .getTransferData ( JGTITabbedPaneTransferable.dataFlavor );

      if ( importComponent ( component.getSource (), jGTITabbedPane, component
          .getComponent () ) )
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
}
