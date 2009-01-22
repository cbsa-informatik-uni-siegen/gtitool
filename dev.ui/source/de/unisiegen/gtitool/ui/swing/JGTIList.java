package de.unisiegen.gtitool.ui.swing;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;

import de.unisiegen.gtitool.ui.swing.dnd.JGTIListModelRows;
import de.unisiegen.gtitool.ui.swing.dnd.JGTIListModelRowsTransferable;


/**
 * Special {@link JList}.
 * 
 * @author Christian Fehler
 * @version $Id: JGTIList.java 1372 2008-10-30 08:36:20Z fehler $
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
   * The allowed drag and drop sources.
   */
  private ArrayList < JComponent > allowedDndSources;


  /**
   * Flag that indicates that the drag and drop is allowed.
   */
  protected boolean dragAndDropAllowed = true;


  /**
   * Allocates a new {@link JGTIList}.
   */
  public JGTIList ()
  {
    super ();
    init ();
  }


  /**
   * Allocates a new {@link JGTIList}.
   * 
   * @param dataModel The {@link ListModel}.
   */
  public JGTIList ( ListModel dataModel )
  {
    super ( dataModel );
    init ();
  }


  /**
   * Allocates a new {@link JGTIList}.
   * 
   * @param listData The list data.
   */
  public JGTIList ( final Object [] listData )
  {
    super ( listData );
    init ();
  }


  /**
   * Allocates a new {@link JGTIList}.
   * 
   * @param listData The list data.
   */
  public JGTIList ( final Vector < ? > listData )
  {
    super ( listData );
    init ();
  }


  /**
   * Adds the given {@link JComponent} to the allowed drag and drop sources.
   * 
   * @param jComponent The {@link JComponent} to add.
   */
  public final void addAllowedDndSource ( JComponent jComponent )
  {
    if ( !this.allowedDndSources.contains ( jComponent ) )
    {
      this.allowedDndSources.add ( jComponent );
    }
  }


  /**
   * Clears the allowed drag and drop sources.
   */
  public final void clearAllowedDndSources ()
  {
    this.allowedDndSources.clear ();
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
    try
    {
      JGTIListModelRows rows = ( JGTIListModelRows ) event.getTransferable ()
          .getTransferData ( JGTIListModelRowsTransferable.dataFlavor );
      if ( !this.allowedDndSources.contains ( rows.getSource () ) )
      {
        event.rejectDrag ();
        this.dropPoint = null;
        repaint ();
        return;
      }
    }
    catch ( UnsupportedFlavorException exc )
    {
      event.rejectDrag ();
      this.dropPoint = null;
      repaint ();
      return;
    }
    catch ( IOException exc )
    {
      event.rejectDrag ();
      this.dropPoint = null;
      repaint ();
      return;
    }

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
   * Initializes this {@link JComponent}.
   */
  private final void init ()
  {
    setSelectionMode ( ListSelectionModel.SINGLE_SELECTION );
    this.allowedDndSources = new ArrayList < JComponent > ();

    // must be removed because of problems with the drag and drop
    for ( MouseMotionListener current : getMouseMotionListeners () )
    {
      removeMouseMotionListener ( current );
    }

    // store the drag and drop allowed state
    addMouseListener ( new MouseAdapter ()
    {

      @Override
      public void mousePressed ( MouseEvent event )
      {
        int rowIndex = locationToIndex ( event.getPoint () );
        Rectangle rect = getCellBounds ( rowIndex, rowIndex );
        if ( ( rect == null )
            || ( event.getPoint ().getY () > rect.y + rect.height ) )
        {
          JGTIList.this.dragAndDropAllowed = false;
        }
        else
        {
          JGTIList.this.dragAndDropAllowed = true;
        }
      }
    } );

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
            && ( ( event.getModifiers () & InputEvent.BUTTON1_MASK ) != 0 )
            && JGTIList.this.dragAndDropAllowed )
        {
          TransferHandler transferHandler = getTransferHandler ();
          transferHandler.exportAsDrag ( JGTIList.this, event, transferHandler
              .getSourceActions ( JGTIList.this ) );
          event.consume ();
        }
      }
    } );
    setDropTarget ( new DropTarget ( this, this ) );

    // disable cut, copy and paste
    getActionMap ().put ( "cut", new AbstractAction () { //$NON-NLS-1$

          /**
           * The serial version uid.
           */
          private static final long serialVersionUID = -4319963661932864508L;


          public void actionPerformed ( @SuppressWarnings ( "unused" )
          ActionEvent e )
          {
            // do nothing
          }
        } );
    getActionMap ().put ( "copy", new AbstractAction () { //$NON-NLS-1$

          /**
           * The serial version uid.
           */
          private static final long serialVersionUID = 2449576847404790643L;


          public void actionPerformed ( @SuppressWarnings ( "unused" )
          ActionEvent e )
          {
            // do nothing
          }
        } );
    getActionMap ().put ( "paste", new AbstractAction () { //$NON-NLS-1$

          /**
           * The serial version uid.
           */
          private static final long serialVersionUID = -7404714019259587536L;


          public void actionPerformed ( @SuppressWarnings ( "unused" )
          ActionEvent e )
          {
            // do nothing
          }
        } );
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
   * {@inheritDoc}
   * 
   * @see javax.swing.JList#locationToIndex(java.awt.Point)
   */
  @Override
  public int locationToIndex ( Point p )
  {
    Rectangle r = getCellBounds ( 0, getModel ().getSize () - 1 );
    if ( r.contains ( p ) )
    {
      return super.locationToIndex ( p );
    }
    return getModel ().getSize ();
  }


  /**
   * Removes the given {@link JComponent} from the allowed drag and drop
   * sources.
   * 
   * @param jComponent The {@link JComponent} to remove.
   */
  public final void removeAllowedDndSource ( JComponent jComponent )
  {
    this.allowedDndSources.remove ( jComponent );
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
