package de.unisiegen.gtitool.ui.dnd;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.TransferHandler;

import de.unisiegen.gtitool.ui.logic.renderer.ModifiedListCellRenderer;


/**
 * Special {@link JTable}, that supports drag and drop.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class JDragList extends JList implements DropTargetListener
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 6892137428208392217L;


  /**
   * The drop location.
   */
  private Point dropLocation = null;


  /**
   * Allocates a new <code>JDragList</code>.
   */
  public JDragList ()
  {
    super ();
    // Java swing bugfix
    addMouseMotionListener ( new MouseMotionAdapter ()
    {

      /**
       * {@inheritDoc}
       * 
       * @see MouseMotionAdapter#mouseDragged(MouseEvent)
       */
      @Override
      public void mouseDragged ( MouseEvent event )
      {
        if ( getDragEnabled ()
            && ( event.getModifiers () & InputEvent.BUTTON1_MASK ) != 0 )
        {
          TransferHandler transferHandler = getTransferHandler ();
          transferHandler.exportAsDrag ( JDragList.this, event, transferHandler
              .getSourceActions ( JDragList.this ) );
          event.consume ();
        }
      }
    } );
    setCellRenderer ( new ModifiedListCellRenderer () );
    setDropTarget ( new DropTarget ( this, this ) );
  }


  /**
   * {@inheritDoc}
   * 
   * @see DropTargetListener#dragEnter(DropTargetDragEvent)
   */
  public void dragEnter ( DropTargetDragEvent pEvent )
  {
    pEvent.acceptDrag ( pEvent.getDropAction () );
    this.dropLocation = pEvent.getLocation ();
    repaint ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see DropTargetListener#dragExit(DropTargetEvent)
   */
  public void dragExit ( @SuppressWarnings ( "unused" )
  DropTargetEvent pEvent )
  {
    this.dropLocation = null;
    repaint ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see DropTargetListener#dragOver(DropTargetDragEvent)
   */
  public void dragOver ( DropTargetDragEvent pEvent )
  {
    pEvent.acceptDrag ( pEvent.getDropAction () );
    this.dropLocation = pEvent.getLocation ();
    repaint ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see DropTargetListener#drop(DropTargetDropEvent)
   */
  public void drop ( DropTargetDropEvent pEvent )
  {
    pEvent.acceptDrop ( pEvent.getDropAction () );
    try
    {
      Transferable transferable = pEvent.getTransferable ();
      pEvent.dropComplete ( getTransferHandler ().importData ( this,
          transferable ) );
    }
    catch ( RuntimeException exc )
    {
      pEvent.dropComplete ( false );
    }
    this.dropLocation = null;
    repaint ();

  }


  /**
   * {@inheritDoc}
   * 
   * @see DropTargetListener#dropActionChanged(DropTargetDragEvent)
   */
  public void dropActionChanged ( DropTargetDragEvent pEvent )
  {
    pEvent.acceptDrag ( pEvent.getDropAction () );
    this.dropLocation = pEvent.getLocation ();
    repaint ();
  }


  /**
   * Returns the drop point.
   * 
   * @return The drop point.
   */
  public final Point getDropPoint ()
  {
    return this.dropLocation;
  }


  /**
   * {@inheritDoc}
   * 
   * @see JComponent#paintComponent(Graphics)
   */
  @Override
  protected void paintComponent ( Graphics pGraphics )
  {
    super.paintComponent ( pGraphics );
    if ( this.dropLocation != null )
    {
      int rowIndex = locationToIndex ( this.dropLocation );
      Rectangle rect = getCellBounds ( rowIndex, rowIndex );
      if ( ( rect == null )
          || ( this.dropLocation.getY () > rect.y + rect.height ) )
      {
        rowIndex = getModel ().getSize ();
      }
      int y = 0;
      for ( int i = 0 ; i < rowIndex ; i++ )
      {
        y += getCellBounds ( i, i ).height; // getRowHeight(rowIndex);
      }
      if ( y > 0 )
      {
        y -= 1;
      }
      int width = getWidth () - 1;
      int size = 3;
      // Color
      pGraphics.setColor ( Color.BLACK );
      // Line
      pGraphics.drawLine ( size, y, width - size, y );
      // Left
      pGraphics.drawLine ( size, y, 0, y - size );
      pGraphics.drawLine ( size, y, 0, y + size );
      // Right
      pGraphics.drawLine ( width - size, y, width, y - size );
      pGraphics.drawLine ( width - size, y, width, y + size );
    }
  }
}
