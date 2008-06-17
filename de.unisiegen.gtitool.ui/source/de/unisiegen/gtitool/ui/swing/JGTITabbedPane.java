package de.unisiegen.gtitool.ui.swing;


import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
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
  private static final Color HIGHLIGHT_COLOR = new Color ( 150, 200, 250 );


  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 5744054037095918506L;


  /**
   * The source tab index.
   */
  private int sourceTabIndex = -1;


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

    setTransferHandler ( new JGTITabbedPaneTransferHandler (
        TransferHandler.MOVE )
    {

      /**
       * The serial version uid.
       */
      private static final long serialVersionUID = -7483915272887199973L;


      @Override
      protected boolean importComponent ( @SuppressWarnings ( "unused" )
      JGTITabbedPane tabbedPane, @SuppressWarnings ( "unused" )
      Component component )
      {
        // TODOCF implement the second view drag and drop
        return false;
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
            && ( ( event.getModifiers () & InputEvent.BUTTON1_MASK ) != 0 ) )
        {
          TransferHandler transferHandler = getTransferHandler ();
          transferHandler.exportAsDrag ( JGTITabbedPane.this, event,
              transferHandler.getSourceActions ( JGTITabbedPane.this ) );
          event.consume ();

          int tabIndex = getTabIndex ( event.getX (), event.getY () );
          if ( tabIndex != -1 )
          {
            JGTITabbedPane.this.sourceTabIndex = tabIndex;
          }
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
   * Clears the highlight of the tabs.
   */
  private final void clearHighlightTab ()
  {
    for ( int i = 0 ; i < getComponentCount () ; i++ )
    {
      setBackgroundAt ( i, null );
    }
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
    clearHighlightTab ();

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
      JGTITabbedPaneComponent rows = ( JGTITabbedPaneComponent ) event
          .getTransferable ().getTransferData (
              JGTITabbedPaneTransferable.dataFlavor );
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

    if ( JGTITabbedPane.this.sourceTabIndex != -1 )
    {
      int targetTabIndex = getTabIndex ( this.dropPoint.x, this.dropPoint.y );
      if ( ( targetTabIndex == -1 )
          || ( JGTITabbedPane.this.sourceTabIndex == targetTabIndex ) )
      {
        clearHighlightTab ();
      }
      else
      {
        highlightTab ( targetTabIndex );
      }
    }

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

      if ( JGTITabbedPane.this.sourceTabIndex != -1 )
      {
        int tabIndex = getTabIndex ( event.getLocation ().x, event
            .getLocation ().y );
        if ( ( tabIndex != -1 )
            && ( tabIndex != JGTITabbedPane.this.sourceTabIndex ) )
        {
          moveTab ( JGTITabbedPane.this.sourceTabIndex, tabIndex );
          setSelectedIndex ( tabIndex );
        }
        JGTITabbedPane.this.sourceTabIndex = -1;
        clearHighlightTab ();
      }
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
   * Returns the source tab index.
   * 
   * @return The source tab index.
   * @see #sourceTabIndex
   */
  public final int getSourceTabIndex ()
  {
    return this.sourceTabIndex;
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
   * Highlights the tab with the given index.
   * 
   * @param tabIndex The tab index.
   */
  private final void highlightTab ( int tabIndex )
  {
    if ( tabIndex < 0 )
    {
      throw new IllegalArgumentException ( "tab index to small" ); //$NON-NLS-1$
    }
    if ( tabIndex >= getComponentCount () )
    {
      throw new IllegalArgumentException ( "tab index to large" ); //$NON-NLS-1$
    }

    clearHighlightTab ();
    setBackgroundAt ( tabIndex, HIGHLIGHT_COLOR );
  }


  /**
   * Moves the tab.
   * 
   * @param oldIndex The old index.
   * @param newIndex The new index.
   */
  private final void moveTab ( int oldIndex, int newIndex )
  {
    Component component = getComponentAt ( oldIndex );
    remove ( component );
    add ( component, newIndex );
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
