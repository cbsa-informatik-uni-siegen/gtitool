package de.unisiegen.gtitool.ui.swing;


import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import de.unisiegen.gtitool.core.util.Theme;
import de.unisiegen.gtitool.ui.swing.dnd.JGTITableModelRows;
import de.unisiegen.gtitool.ui.swing.dnd.JGTITableModelRowsTransferable;


/**
 * Special {@link JTable}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class JGTITable extends JTable implements DropTargetListener
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 6702446627778010971L;


  /**
   * The between drag and drop mode.
   * 
   * @see #getDndMode()
   * @see #setDndMode(int)
   */
  public static final int DROP_BETWEEN = 0;


  /**
   * The into drag and drop mode.
   * 
   * @see #getDndMode()
   * @see #setDndMode(int)
   */
  public static final int DROP_INTO = 1;


  /**
   * The drop mode used for this {@link JGTITable}.
   * 
   * @see #getDndMode()
   * @see #setDndMode(int)
   */
  private int dndMode = DROP_BETWEEN;


  /**
   * The current drop point or null if not dragging to this {@link JGTITable} at
   * the moment.
   */
  private Point dropPoint = null;


  /**
   * The allowed drag and drop sources.
   */
  private ArrayList < JComponent > allowedDndSources;


  /**
   * Allocates a new {@link JGTITable}.
   */
  public JGTITable ()
  {
    super ();
    init ();
  }


  /**
   * Allocates a new {@link JGTITable}.
   * 
   * @param numRows The row number.
   * @param numColumns The column number.
   */
  public JGTITable ( int numRows, int numColumns )
  {
    super ( numRows, numColumns );
    init ();
  }


  /**
   * Allocates a new {@link JGTITable}.
   * 
   * @param rowData The row data.
   * @param columnNames The column names.
   */
  public JGTITable ( final Object [][] rowData, final Object [] columnNames )
  {
    super ( rowData, columnNames );
    init ();
  }


  /**
   * Allocates a new {@link JGTITable}.
   * 
   * @param dm The {@link TableModel}.
   */
  public JGTITable ( TableModel dm )
  {
    super ( dm );
    init ();
  }


  /**
   * Allocates a new {@link JGTITable}.
   * 
   * @param dm The {@link TableModel}.
   * @param cm The {@link TableColumnModel}.
   */
  public JGTITable ( TableModel dm, TableColumnModel cm )
  {
    super ( dm, cm );
    init ();
  }


  /**
   * Allocates a new {@link JGTITable}.
   * 
   * @param dm The {@link TableModel}.
   * @param cm The {@link TableColumnModel}.
   * @param sm The {@link ListSelectionModel}.
   */
  public JGTITable ( TableModel dm, TableColumnModel cm, ListSelectionModel sm )
  {
    super ( dm, cm, sm );
    init ();
  }


  /**
   * Allocates a new {@link JGTITable}.
   * 
   * @param rowData The row data.
   * @param columnNames The column names.
   */
  public JGTITable ( Vector < ? > rowData, Vector < ? > columnNames )
  {
    super ( rowData, columnNames );
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
  public final void dragEnter ( DropTargetDragEvent dtde )
  {
    dragOver ( dtde );
  }


  /**
   * {@inheritDoc}
   * 
   * @see DropTargetListener#dragExit(DropTargetEvent)
   */
  public final void dragExit (
      @SuppressWarnings ( "unused" ) DropTargetEvent dte )
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
      JGTITableModelRows rows = ( JGTITableModelRows ) event.getTransferable ()
          .getTransferData ( JGTITableModelRowsTransferable.dataFlavor );
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
    Point point = event.getLocation ();
    this.dropPoint = point;

    event.acceptDrop ( event.getDropAction () );
    try
    {
      Transferable transferable = event.getTransferable ();
      event.dropComplete ( getTransferHandler ().importData ( this,
          transferable ) );
    }
    catch ( RuntimeException re )
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
    dragOver ( event );
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
   * @return the drop point.
   */
  public final Point getDropPoint ()
  {
    return this.dropPoint;
  }


  /**
   * {@inheritDoc}
   * 
   * @see JComponent#getPreferredSize()
   */
  @Override
  public final Dimension getPreferredSize ()
  {
    Dimension size = super.getPreferredSize ();
    Container parent = getParent ();
    if ( parent instanceof JViewport )
    {
      parent = parent.getParent ();
      if ( parent instanceof JScrollPane )
      {
        JScrollPane scrollPane = ( JScrollPane ) parent;
        int height = scrollPane.getViewportBorderBounds ().height;
        if ( size.height < height )
        {
          size.height = height;
        }
      }
    }
    return size;
  }


  /**
   * Initializes this {@link JComponent}.
   */
  private final void init ()
  {
    this.allowedDndSources = new ArrayList < JComponent > ();

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
            && ( rowAtPoint ( event.getPoint () ) != -1 ) )
        {
          TransferHandler transferHandler = getTransferHandler ();
          transferHandler.exportAsDrag ( JGTITable.this, event, transferHandler
              .getSourceActions ( JGTITable.this ) );
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
          private static final long serialVersionUID = 2442639750331718901L;


          public void actionPerformed (
              @SuppressWarnings ( "unused" ) ActionEvent e )
          {
            // do nothing
          }
        } );
    getActionMap ().put ( "copy", new AbstractAction () { //$NON-NLS-1$

          /**
           * The serial version uid.
           */
          private static final long serialVersionUID = 4781268710775959195L;


          public void actionPerformed (
              @SuppressWarnings ( "unused" ) ActionEvent e )
          {
            // do nothing
          }
        } );
    getActionMap ().put ( "paste", new AbstractAction () { //$NON-NLS-1$

          /**
           * The serial version uid.
           */
          private static final long serialVersionUID = 8934656186755813050L;


          public void actionPerformed (
              @SuppressWarnings ( "unused" ) ActionEvent e )
          {
            // do nothing
          }
        } );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Component#isEnabled()
   */
  @Override
  public final boolean isEnabled ()
  {
    return super.isEnabled ();
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
      int rowIndex = rowAtPoint ( this.dropPoint );

      if ( this.dndMode == DROP_BETWEEN )
      {
        int y = 0;
        if ( rowIndex < 0 )
        {
          rowIndex = getRowCount ();
        }
        while ( --rowIndex >= 0 )
        {
          y += getRowHeight ( rowIndex );
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
      else if ( this.dndMode == DROP_INTO )
      {
        Rectangle r = getVisibleRect ();
        graphics.setColor ( Color.BLACK );
        graphics.drawRect ( 0, r.y, r.width - 1, r.height - 1 );
      }
      else
      {
        throw new RuntimeException ( "dnd mode is invalid" ); //$NON-NLS-1$
      }
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
   * Sets the drag and drop mode of this {@link JGTITable}.
   * 
   * @param dndMode The new drag and drop mode.
   */
  public final void setDndMode ( int dndMode )
  {
    if ( ( dndMode != DROP_BETWEEN ) && ( dndMode != DROP_INTO ) )
    {
      throw new IllegalArgumentException ( "dnd mode is invalid" ); //$NON-NLS-1$
    }
    this.dndMode = dndMode;
  }


  /**
   * {@inheritDoc}
   * 
   * @see JComponent#setEnabled(boolean)
   */
  @Override
  public final void setEnabled ( boolean enabled )
  {
    super.setEnabled ( enabled );
    getTableHeader ().setEnabled ( enabled );
    getTableHeader ().setResizingAllowed ( enabled );
    if ( enabled )
    {
      setBackground ( Color.WHITE );
    }
    else
    {
      setBackground ( Theme.DISABLED_COMPONENT_COLOR );
    }
  }
}
