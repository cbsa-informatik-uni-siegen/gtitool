package de.unisiegen.gtitool.ui.swing;


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
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;

import de.unisiegen.gtitool.ui.logic.renderer.ModifiedListCellRenderer;


/**
 * Special {@link JList}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class JGTIList extends JList implements DropTargetListener
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 6892137428208392217L;


  /**
   * The into drag and drop mode.
   * 
   * @see #getDndMode()
   * @see #setDndMode(int)
   */
  public static final int DROP_INTO = 0;


  /**
   * The between drag and drop mode.
   * 
   * @see #getDndMode()
   * @see #setDndMode(int)
   */
  public static final int DROP_BETWEEN = 1;


  /**
   * The drop point.
   */
  private Point dropPoint = null;


  /**
   * The drop mode used for this {@link JGTIList}.
   * 
   * @see #getDndMode()
   * @see #setDndMode(int)
   */
  private int dndMode = DROP_INTO;


  /**
   * Allocates a new {@link JGTIList}.
   */
  public JGTIList ()
  {
    super ();
    setSelectionMode ( ListSelectionModel.SINGLE_SELECTION );
    setCellRenderer ( new ModifiedListCellRenderer () );

    // swing bugfix
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
          transferHandler.exportAsDrag ( JGTIList.this, event, transferHandler
              .getSourceActions ( JGTIList.this ) );
          event.consume ();
        }
      }
    } );
    setDropTarget ( new DropTarget ( this, this ) );
  }


  /**
   * {@inheritDoc}
   * 
   * @see DropTargetListener#dragEnter(DropTargetDragEvent)
   */
  public final void dragEnter ( DropTargetDragEvent event )
  {
    event.acceptDrag ( event.getDropAction () );
    this.dropPoint = event.getLocation ();
    repaint ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see DropTargetListener#dragExit(DropTargetEvent)
   */
  public final void dragExit ( @SuppressWarnings ( "unused" )
  DropTargetEvent event )
  {
    this.dropPoint = null;
    repaint ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see DropTargetListener#dragOver(DropTargetDragEvent)
   */
  public final void dragOver ( DropTargetDragEvent event )
  {
    event.acceptDrag ( event.getDropAction () );
    this.dropPoint = event.getLocation ();
    repaint ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see DropTargetListener#drop(DropTargetDropEvent)
   */
  public final void drop ( DropTargetDropEvent event )
  {
    event.acceptDrop ( event.getDropAction () );
    try
    {
      Transferable transferable = event.getTransferable ();
      event.dropComplete ( getTransferHandler ().importData ( this,
          transferable ) );
    }
    catch ( RuntimeException exc )
    {
      event.dropComplete ( false );
    }
    this.dropPoint = null;
    repaint ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see DropTargetListener#dropActionChanged(DropTargetDragEvent)
   */
  public final void dropActionChanged ( DropTargetDragEvent event )
  {
    event.acceptDrag ( event.getDropAction () );
    this.dropPoint = event.getLocation ();
    repaint ();
  }


  /**
   * Returns the drag and drop mode of this {@link JGTIList}.
   * 
   * @return The drag and drop mode of this {@link JGTIList}.
   * @see #setDndMode(int)
   */
  public final int getDndMode ()
  {
    return this.dndMode;
  }


  /**
   * Returns the drop point.
   * 
   * @return The drop point.
   */
  public final Point getDropPoint ()
  {
    return this.dropPoint;
  }


  /**
   * {@inheritDoc}
   * 
   * @see JComponent#paintComponent(Graphics)
   */
  @Override
  protected final void paintComponent ( Graphics graphics )
  {
    super.paintComponent ( graphics );
    if ( this.dropPoint != null )
    {
      if ( this.dndMode == DROP_INTO )
      {
        Rectangle rect = getVisibleRect ();
        graphics.setColor ( Color.BLACK );
        graphics.drawRect ( 0, rect.y, rect.width - 1, rect.height - 1 );
      }
      else if ( this.dndMode == DROP_BETWEEN )
      {
        int rowIndex = locationToIndex ( this.dropPoint );
        Rectangle rect = getCellBounds ( rowIndex, rowIndex );
        if ( ( rect == null )
            || ( this.dropPoint.getY () > rect.y + rect.height ) )
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
        graphics.setColor ( Color.BLACK );
        // Line
        graphics.drawLine ( size, y, width - size, y );
        graphics.drawLine ( size, y + 1, width - size, y + 1 );
        // Left upper
        graphics.drawLine ( size, y, 0, y - size );
        graphics.drawLine ( size, y + 1, 0, y - size + 1 );
        // Left lower
        graphics.drawLine ( size, y, 0, y + size );
        graphics.drawLine ( size, y + 1, 0, y + size + 1 );
        // Right upper
        graphics.drawLine ( width - size, y, width, y - size );
        graphics.drawLine ( width - size, y + 1, width, y - size + 1 );
        // Right lower
        graphics.drawLine ( width - size, y, width, y + size );
        graphics.drawLine ( width - size, y + 1, width, y + size + 1 );
      }
    }
  }


  /**
   * Sets the drag and drop mode of this {@link JGTIList}.
   * 
   * @param dndMode The new drag and drop mode.
   */
  public final void setDndMode ( int dndMode )
  {
    if ( ( dndMode != DROP_INTO ) && ( dndMode != DROP_BETWEEN ) )
    {
      throw new IllegalArgumentException ( "dnd mode is invalid" ); //$NON-NLS-1$
    }
    this.dndMode = dndMode;
  }
}
