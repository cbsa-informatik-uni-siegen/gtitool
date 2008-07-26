package de.unisiegen.gtitool.ui.jgraph;


import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import org.jgraph.graph.CellView;
import org.jgraph.graph.PortView;
import org.jgraph.plaf.basic.BasicGraphUI;


/**
 * This class extends the {@link BasicGraphUI} class and overwrites the
 * {@link MouseAdapter}.
 * 
 * @author Christian Fehler
 * @version $Id:GPCellViewFactory.java 910 2008-05-16 00:31:21Z fehler $
 */
public final class JGTIBasicGraphUI extends BasicGraphUI
{

  /**
   * The special {@link MouseAdapter}.
   * 
   * @author Christian Fehler
   */
  public final class GTIMouseHandler extends MouseAdapter implements
      MouseMotionListener, Serializable
  {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = -890027322927621665L;


    /**
     * The {@link CellView}.
     */
    protected CellView cell;


    /**
     * The handler.
     */
    protected Object handler;


    /**
     * The previous {@link Cursor}.
     */
    protected transient Cursor previousCursor = null;


    /**
     * Handles the selection.
     * 
     * @param event The {@link MouseEvent}.
     */
    @SuppressWarnings ( "synthetic-access" )
    private final void handleSelection ( MouseEvent event )
    {
      PortView portView = JGTIBasicGraphUI.this.graph.getPortViewAt ( event
          .getX (), event.getY () );

      if ( ( portView != null )
          && ( portView.getParentView () instanceof StateView ) )
      {
        StateView stateView = ( StateView ) portView.getParentView ();

        if ( stateView.isSelectionAllowed ( event.getX (), event.getY () ) )
        {
          selectCellForEvent ( this.cell.getCell (), event );
          JGTIBasicGraphUI.this.focus = this.cell;
          if ( JGTIBasicGraphUI.this.handle != null )
          {
            JGTIBasicGraphUI.this.handle.mousePressed ( event );
            this.handler = JGTIBasicGraphUI.this.handle;
          }
          event.consume ();
          this.cell = null;
        }
        else
        {
          event.consume ();
          this.cell = null;
          JGTIBasicGraphUI.this.graph.clearSelection ();
        }
      }
    }


    /**
     * Returns true if descendant, otherwise false.
     * 
     * @param parentView The parent view.
     * @param childView The child view.
     * @return True if descendant, otherwise false.
     */
    @SuppressWarnings ( "synthetic-access" )
    protected final boolean isDescendant ( CellView parentView,
        CellView childView )
    {
      if ( ( parentView == null ) || ( childView == null ) )
      {
        return false;
      }

      Object parent = parentView.getCell ();
      Object child = childView.getCell ();
      Object ancestor = child;

      do
      {
        if ( ancestor == parent )
        {
          return true;
        }
      }
      while ( ( ancestor = JGTIBasicGraphUI.this.graphModel
          .getParent ( ancestor ) ) != null );

      return false;
    }


    /**
     * {@inheritDoc}
     * 
     * @see MouseMotionListener#mouseDragged(MouseEvent)
     */
    @SuppressWarnings ( "synthetic-access" )
    public final void mouseDragged ( MouseEvent event )
    {
      autoscroll ( JGTIBasicGraphUI.this.graph, event.getPoint () );
      if ( JGTIBasicGraphUI.this.graph.isEnabled () )
      {
        if ( ( this.handler != null )
            && ( this.handler == JGTIBasicGraphUI.this.marquee ) )
        {
          JGTIBasicGraphUI.this.marquee.mouseDragged ( event );
        }
        else if ( ( this.handler == null )
            && !isEditing ( JGTIBasicGraphUI.this.graph )
            && ( JGTIBasicGraphUI.this.focus != null ) )
        {
          if ( ( JGTIBasicGraphUI.this.focus != null )
              && ( JGTIBasicGraphUI.this.focus instanceof StateView ) )
          {
            StateView stateView = ( StateView ) JGTIBasicGraphUI.this.focus;

            if ( !stateView.isSelectionAllowed ( event.getX (), event.getY () ) )
            {
              event.consume ();
              return;
            }
          }

          if ( !JGTIBasicGraphUI.this.graph
              .isCellSelected ( JGTIBasicGraphUI.this.focus.getCell () ) )
          {
            selectCellForEvent ( JGTIBasicGraphUI.this.focus.getCell (), event );
            this.cell = null;
          }
          if ( JGTIBasicGraphUI.this.handle != null )
          {
            JGTIBasicGraphUI.this.handle.mousePressed ( event );
          }
          this.handler = JGTIBasicGraphUI.this.handle;
        }
        if ( ( JGTIBasicGraphUI.this.handle != null )
            && ( this.handler == JGTIBasicGraphUI.this.handle ) )
        {
          JGTIBasicGraphUI.this.handle.mouseDragged ( event );
        }
      }
    }


    /**
     * {@inheritDoc}
     * 
     * @see MouseMotionListener#mouseMoved(MouseEvent)
     */
    public final void mouseMoved (
        @SuppressWarnings ( "unused" ) MouseEvent event )
    {
      // do nothing
    }


    /**
     * Invoked when a mouse button has been pressed on a component.
     */
    @SuppressWarnings ( "synthetic-access" )
    @Override
    public final void mousePressed ( MouseEvent event )
    {
      this.handler = null;
      if ( !event.isConsumed () && JGTIBasicGraphUI.this.graph.isEnabled () )
      {
        JGTIBasicGraphUI.this.graph.requestFocus ();
        int s = JGTIBasicGraphUI.this.graph.getTolerance ();
        Rectangle2D r = JGTIBasicGraphUI.this.graph
            .fromScreen ( new Rectangle2D.Double ( event.getX () - s, event
                .getY ()
                - s, 2 * s, 2 * s ) );
        JGTIBasicGraphUI.this.lastFocus = JGTIBasicGraphUI.this.focus;
        JGTIBasicGraphUI.this.focus = ( ( JGTIBasicGraphUI.this.focus != null ) && JGTIBasicGraphUI.this.focus
            .intersects ( JGTIBasicGraphUI.this.graph, r ) ) ? JGTIBasicGraphUI.this.focus
            : null;

        this.cell = JGTIBasicGraphUI.this.graph.getNextSelectableViewAt ( null,
            event.getX (), event.getY () );

        if ( JGTIBasicGraphUI.this.focus == null )
        {
          JGTIBasicGraphUI.this.focus = this.cell;
        }
        completeEditing ();

        boolean isForceMarquee = isForceMarqueeEvent ( event );
        if ( !isForceMarqueeEvent ( event ) )
        {
          if ( !isToggleSelectionEvent ( event ) )
          {
            if ( JGTIBasicGraphUI.this.handle != null )
            {
              JGTIBasicGraphUI.this.handle.mousePressed ( event );
              this.handler = JGTIBasicGraphUI.this.handle;
            }

            if ( !event.isConsumed ()
                && ( this.cell != null )
                && !JGTIBasicGraphUI.this.graph.isCellSelected ( this.cell
                    .getCell () ) )
            {
              if ( this.cell instanceof StateView )
              {
                handleSelection ( event );
              }
              else
              {
                selectCellForEvent ( this.cell.getCell (), event );
                JGTIBasicGraphUI.this.focus = this.cell;
                if ( JGTIBasicGraphUI.this.handle != null )
                {
                  JGTIBasicGraphUI.this.handle.mousePressed ( event );
                  this.handler = JGTIBasicGraphUI.this.handle;
                }
                event.consume ();
                this.cell = null;
              }
            }
            else if ( this.cell == null )
            {
              event.consume ();
              JGTIBasicGraphUI.this.graph.clearSelection ();
            }
            else
            {
              PortView portView = JGTIBasicGraphUI.this.graph.getPortViewAt (
                  event.getX (), event.getY () );

              if ( ( portView != null )
                  && ( portView.getParentView () instanceof StateView ) )
              {
                StateView stateView = ( StateView ) portView.getParentView ();

                if ( !stateView.isSelectionAllowed ( event.getX (), event
                    .getY () ) )
                {
                  event.consume ();
                  this.cell = null;
                  JGTIBasicGraphUI.this.graph.clearSelection ();
                }
              }
            }
          }
        }

        if ( !event.isConsumed ()
            && ( JGTIBasicGraphUI.this.marquee != null )
            && ( !isToggleSelectionEvent ( event )
                || ( JGTIBasicGraphUI.this.focus == null ) || isForceMarquee ) )
        {
          JGTIBasicGraphUI.this.marquee.mousePressed ( event );
          this.handler = JGTIBasicGraphUI.this.marquee;
        }
      }
    }


    /**
     * {@inheritDoc}
     * 
     * @see MouseAdapter#mouseReleased(MouseEvent)
     */
    @SuppressWarnings ( "synthetic-access" )
    @Override
    public final void mouseReleased ( MouseEvent event )
    {
      try
      {
        if ( ( event != null ) && !event.isConsumed ()
            && ( JGTIBasicGraphUI.this.graph != null )
            && JGTIBasicGraphUI.this.graph.isEnabled () )
        {
          if ( ( this.handler == JGTIBasicGraphUI.this.marquee )
              && ( JGTIBasicGraphUI.this.marquee != null ) )
          {
            JGTIBasicGraphUI.this.marquee.mouseReleased ( event );
          }
          else if ( ( this.handler == JGTIBasicGraphUI.this.handle )
              && ( JGTIBasicGraphUI.this.handle != null ) )
          {
            JGTIBasicGraphUI.this.handle.mouseReleased ( event );
          }
          if ( isDescendant ( this.cell, JGTIBasicGraphUI.this.focus )
              && ( event.getModifiers () != 0 ) )
          {
            // Do not switch to parent if Special Selection
            this.cell = JGTIBasicGraphUI.this.focus;
          }
          if ( !event.isConsumed () && ( this.cell != null ) )
          {
            Object tmp = this.cell.getCell ();
            boolean wasSelected = JGTIBasicGraphUI.this.graph
                .isCellSelected ( tmp );
            if ( !event.isPopupTrigger () || !wasSelected )
            {
              selectCellForEvent ( tmp, event );
              JGTIBasicGraphUI.this.focus = this.cell;
            }
          }
        }
      }
      finally
      {
        this.handler = null;
        this.cell = null;
      }
    }
  }


  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -2884236006080533712L;


  /**
   * Allocates a new {@link JGTIBasicGraphUI}.
   */
  public JGTIBasicGraphUI ()
  {
    super ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see BasicGraphUI#createMouseListener()
   */
  @Override
  protected final MouseListener createMouseListener ()
  {
    return new GTIMouseHandler ();
  }
}
