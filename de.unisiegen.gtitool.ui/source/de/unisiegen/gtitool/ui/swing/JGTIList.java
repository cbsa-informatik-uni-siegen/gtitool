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
import javax.swing.TransferHandler;

import de.unisiegen.gtitool.ui.logic.renderer.ModifiedListCellRenderer;


/**
 * Special {@link JList}, that supports drag and drop.
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
   * The into drop mode.
   * 
   * @see #getDropMode()
   * @see #setDropMode(int)
   */
  public static final int DROP_INTO = 0;


  /**
   * The between drop mode.
   * 
   * @see #getDropMode()
   * @see #setDropMode(int)
   */
  public static final int DROP_BETWEEN = 1;


  /**
   * The drop location.
   */
  private Point dropLocation = null;


  /**
   * The drop mode used for this {@link JGTIList}.
   * 
   * @see #getDropMode()
   * @see #setDropMode(int)
   */
  private int dropMode = DROP_INTO;


  /**
   * Allocates a new {@link JGTIList}.
   */
  public JGTIList ()
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
          transferHandler.exportAsDrag ( JGTIList.this, event, transferHandler
              .getSourceActions ( JGTIList.this ) );
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
  public final void dragEnter ( DropTargetDragEvent event )
  {
    event.acceptDrag ( event.getDropAction () );
    this.dropLocation = event.getLocation ();
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
    this.dropLocation = null;
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
    this.dropLocation = event.getLocation ();
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
    this.dropLocation = null;
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
    this.dropLocation = event.getLocation ();
    repaint ();
  }


  /**
   * Returns the drop mode of this {@link JGTIList}.
   * 
   * @return The drop mode of this {@link JGTIList}.
   * @see #setDropMode(int)
   */
  public final int getDropMode ()
  {
    return this.dropMode;
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
  protected final void paintComponent ( Graphics graphics )
  {
    super.paintComponent ( graphics );
    if ( this.dropLocation != null )
    {
      if ( this.dropMode == DROP_INTO )
      {
        Rectangle rect = getVisibleRect ();
        graphics.setColor ( Color.BLACK );
        graphics.drawRect ( 0, rect.y, rect.width - 1, rect.height - 1 );
      }
      else if ( this.dropMode == DROP_BETWEEN )
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
        graphics.setColor ( Color.BLACK );
        // Line
        graphics.drawLine ( size, y, width - size, y );
        // Left
        graphics.drawLine ( size, y, 0, y - size );
        graphics.drawLine ( size, y, 0, y + size );
        // Right
        graphics.drawLine ( width - size, y, width, y - size );
        graphics.drawLine ( width - size, y, width, y + size );
      }
    }
  }


  /**
   * Sets the drop modeof this {@link JGTIList}.
   * 
   * @param dropMode The new drop mode.
   */
  public final void setDropMode ( int dropMode )
  {
    if ( ( dropMode != DROP_INTO ) && ( dropMode != DROP_BETWEEN ) )
    {
      throw new IllegalArgumentException ( "drop mode is invalid" ); //$NON-NLS-1$
    }

    this.dropMode = dropMode;
  }
}
