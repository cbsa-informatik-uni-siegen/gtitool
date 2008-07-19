package de.unisiegen.gtitool.ui.swing;


import java.awt.Color;
import java.awt.Component;
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
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.TransferHandler;
import javax.swing.border.EmptyBorder;

import de.unisiegen.gtitool.ui.swing.dnd.JGTITabbedPaneComponent;
import de.unisiegen.gtitool.ui.swing.dnd.JGTITabbedPaneTransferHandler;
import de.unisiegen.gtitool.ui.swing.dnd.JGTITabbedPaneTransferable;


/**
 * Special {@link JTabbedPane}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class JGTITabbedPane extends JTabbedPane implements DropTargetListener
{

  /**
   * The highlight {@link Color}.
   */
  private static final Color HIGHLIGHT_COLOR = new Color ( 50, 150, 250 );


  /**
   * The default tab width.
   */
  private static final int DEFAULT_TAB_WIDTH = 100;


  /**
   * The default tab height.
   */
  private static final int DEFAULT_TAB_HEIGHT = 28;


  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 5744054037095918506L;


  /**
   * The drag enabled value.
   */
  private boolean dragEnabled = false;


  /**
   * The drop point.
   */
  private Point dropPoint = null;


  /**
   * The allowed drag and drop sources.
   */
  private ArrayList < JComponent > allowedDndSources;


  /**
   * Allocates a new {@link JGTITabbedPane}.
   */
  public JGTITabbedPane ()
  {
    this ( TOP );
  }


  /**
   * Allocates a new {@link JGTITabbedPane}.
   * 
   * @param tabPlacement The tab placement.
   */
  public JGTITabbedPane ( int tabPlacement )
  {
    this ( tabPlacement, WRAP_TAB_LAYOUT );
  }


  /**
   * Allocates a new {@link JGTITabbedPane}.
   * 
   * @param tabPlacement The tab placement.
   * @param tabLayoutPolicy The tab layout policy.
   */
  public JGTITabbedPane ( int tabPlacement, int tabLayoutPolicy )
  {
    super ( tabPlacement, tabLayoutPolicy );

    this.allowedDndSources = new ArrayList < JComponent > ();

    setBorder ( new EmptyBorder ( 1, 1, 1, 1 ) );

    setTransferHandler ( new JGTITabbedPaneTransferHandler (
        TransferHandler.MOVE )
    {

      /**
       * The serial version uid.
       */
      private static final long serialVersionUID = -7483915272887199973L;


      @SuppressWarnings ( "synthetic-access" )
      @Override
      protected boolean importComponent ( JGTITabbedPane source,
          @SuppressWarnings ( "unused" ) JGTITabbedPane target,
          Component component )
      {
        int sourceIndex = source.getSelectedIndex ();
        String title = source.getTitleAt ( sourceIndex );

        int targetIndex = getTabIndex ( JGTITabbedPane.this.dropPoint.x,
            JGTITabbedPane.this.dropPoint.y );

        source.remove ( component );
        if ( targetIndex == -1 )
        {
          add ( title, component );
        }
        else
        {
          add ( component, targetIndex );
          setTitleAt ( targetIndex, title );
        }

        setSelectedComponent ( component );

        return true;
      }
    } );

    // swing bugfix
    addMouseMotionListener ( new MouseMotionAdapter ()
    {

      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mouseDragged ( MouseEvent event )
      {
        if ( getDragEnabled ()
            && ( ( event.getModifiers () & InputEvent.BUTTON1_MASK ) != 0 )
            && ( getTabIndex ( event.getPoint ().x, event.getPoint ().y ) != -1 ) )
        {
          TransferHandler transferHandler = getTransferHandler ();
          transferHandler.exportAsDrag ( JGTITabbedPane.this, event,
              transferHandler.getSourceActions ( JGTITabbedPane.this ) );
          event.consume ();
        }
      }
    } );
    setDropTarget ( new DropTarget ( this, this ) );
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
  public final void dragExit (
      @SuppressWarnings ( "unused" ) DropTargetEvent event )
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
    JGTITabbedPaneComponent draggedComponent;
    try
    {
      draggedComponent = ( JGTITabbedPaneComponent ) event.getTransferable ()
          .getTransferData ( JGTITabbedPaneTransferable.dataFlavor );
      if ( !this.allowedDndSources.contains ( draggedComponent.getSource () ) )
      {
        event.rejectDrag ();
        this.dropPoint = null;
        repaint ();
        return;
      }

      // reject the drag if the mouse is not beside the tabs
      if ( getTabCount () > 0 )
      {
        Rectangle bounds = getUI ().getTabBounds ( this, 0 );
        Point point = event.getLocation ();

        if ( ( point.y < bounds.y ) || ( point.y > bounds.y + bounds.height ) )
        {
          event.rejectDrag ();
          this.dropPoint = null;
          repaint ();
          return;
        }
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
   * Returns the drag enabled value.
   * 
   * @return The drag enabled value.
   */
  public final boolean getDragEnabled ()
  {
    return this.dragEnabled;
  }


  /**
   * Returns the tab index.
   * 
   * @param x The x position.
   * @param y The y position.
   * @return the tab index.
   */
  private final int getTabIndex ( int x, int y )
  {
    return getUI ().tabForCoordinate ( this, x, y );
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
      Rectangle visibleRect = getVisibleRect ();
      int vw = visibleRect.width;
      int vh = visibleRect.height;

      int x;
      int y;
      int w;
      int h;

      int index = getTabIndex ( this.dropPoint.x, this.dropPoint.y );
      if ( index == -1 )
      {
        if ( getComponentCount () == 0 )
        {
          x = 2;
          y = 3;
          w = DEFAULT_TAB_WIDTH;
          h = DEFAULT_TAB_HEIGHT;
        }
        else
        {
          Rectangle tabRect = getUI ().getTabBounds ( this,
              getComponentCount () - 1 );

          x = tabRect.x + tabRect.width - 2;
          y = tabRect.y;
          w = DEFAULT_TAB_WIDTH;
          h = tabRect.height;
        }
      }
      else
      {
        Rectangle tabRect = getUI ().getTabBounds ( this, index );

        x = tabRect.x;
        y = tabRect.y;
        w = tabRect.width;
        h = tabRect.height;
      }

      graphics.setColor ( HIGHLIGHT_COLOR );

      // top horizontal
      graphics.drawLine ( x + 1, y, x + w - 2, y );
      graphics.drawLine ( x, y + 1, x + w - 1, y + 1 );

      // left vertical
      graphics.drawLine ( x, y + 1, x, y + h - 1 );
      graphics.drawLine ( x + 1, y, x + 1, y + h - 2 );

      // right vertical
      graphics.drawLine ( x + w - 1, y + 1, x + w - 1, y + h - 1 );
      graphics.drawLine ( x + w - 2, y, x + w - 2, y + h - 2 );

      // left to tab horizontal
      graphics.drawLine ( 1, y + h - 2, x - 1, y + h - 2 );
      graphics.drawLine ( 0, y + h - 1, x, y + h - 1 );

      // tab to right horizontal
      graphics.drawLine ( x + w - 1, y + h - 2, vw - 2, y + h - 2 );
      graphics.drawLine ( x + w, y + h - 1, vw - 1, y + h - 1 );

      // left vertical
      graphics.drawLine ( 0, y + h - 1, 0, vh - 2 );
      graphics.drawLine ( 1, y + h, 1, vh - 1 );

      // right vertical
      graphics.drawLine ( vw - 1, y + h, vw - 1, vh - 2 );
      graphics.drawLine ( vw - 2, y + h - 1, vw - 2, vh - 1 );

      // bottom horizontal
      graphics.drawLine ( 0, vh - 2, vw - 1, vh - 2 );
      graphics.drawLine ( 1, vh - 1, vw - 2, vh - 1 );
    }
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
   * Sets the drag enabled value.
   * 
   * @param dragEnabled The value.
   */
  public final void setDragEnabled ( boolean dragEnabled )
  {
    this.dragEnabled = dragEnabled;
  }
}
