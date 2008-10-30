package de.unisiegen.gtitool.ui.jgraph;


import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import org.jgraph.JGraph;
import org.jgraph.graph.BasicMarqueeHandler;
import org.jgraph.graph.CellView;
import org.jgraph.graph.GraphModel;
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
    private final void handleSelection ( MouseEvent event )
    {
      PortView portView = getGraph ().getPortViewAt ( event.getX (),
          event.getY () );

      if ( ( portView != null )
          && ( portView.getParentView () instanceof StateView ) )
      {
        StateView stateView = ( StateView ) portView.getParentView ();

        if ( stateView.isSelectionAllowed ( event.getX (), event.getY (),
            getGraph ().getScale () ) )
        {
          selectCellForEvent ( this.cell.getCell (), event );
          setFocus ( this.cell );
          if ( getHandle () != null )
          {
            getHandle ().mousePressed ( event );
            this.handler = getHandle ();
          }
          event.consume ();
          this.cell = null;
        }
        else
        {
          event.consume ();
          this.cell = null;
          getGraph ().clearSelection ();
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
      while ( ( ancestor = getGraphModel ().getParent ( ancestor ) ) != null );

      return false;
    }


    /**
     * {@inheritDoc}
     * 
     * @see MouseMotionListener#mouseDragged(MouseEvent)
     */
    public final void mouseDragged ( MouseEvent event )
    {
      autoscroll ( getGraph (), event.getPoint () );
      if ( getGraph ().isEnabled () && !event.isConsumed () )
      {

        if ( ( this.handler != null ) && ( this.handler == getMarquee () ) )
        {
          getMarquee ().mouseDragged ( event );
        }
        else if ( ( this.handler == null ) && !isEditing ( getGraph () )
            && ( getFocus () != null ) )
        {
          if ( ( getFocus () != null ) && ( getFocus () instanceof StateView ) )
          {
            StateView stateView = ( StateView ) getFocus ();

            if ( !stateView.isSelectionAllowed ( event.getX (), event.getY (),
                getGraph ().getScale () ) )
            {
              event.consume ();
              return;
            }
          }

          if ( !getGraph ().isCellSelected ( getFocus ().getCell () ) )
          {
            selectCellForEvent ( getFocus ().getCell (), event );
            this.cell = null;
          }
          if ( getHandle () != null )
          {
            getHandle ().mousePressed ( event );
          }
          this.handler = getHandle ();
        }
        if ( ( getHandle () != null ) && ( this.handler == getHandle () ) )
        {
          getHandle ().mouseDragged ( event );
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
    @Override
    public final void mousePressed ( MouseEvent event )
    {
      this.handler = null;
      if ( !event.isConsumed () && getGraph ().isEnabled () )
      {
        getGraph ().requestFocus ();
        int s = getGraph ().getTolerance ();
        Rectangle2D r = getGraph ().fromScreen (
            new Rectangle2D.Double ( event.getX () - s, event.getY () - s,
                2 * s, 2 * s ) );
        setLastFocus ( getFocus () );
        setFocus ( ( ( getFocus () != null ) && getFocus ().intersects (
            getGraph (), r ) ) ? getFocus () : null );

        this.cell = getGraph ().getNextSelectableViewAt ( null, event.getX (),
            event.getY () );

        if ( getFocus () == null )
        {
          setFocus ( this.cell );
        }
        completeEditing ();

        boolean isForceMarquee = isForceMarqueeEvent ( event );
        if ( !isForceMarqueeEvent ( event ) )
        {
          if ( !isToggleSelectionEvent ( event ) )
          {
            if ( getHandle () != null )
            {
              getHandle ().mousePressed ( event );
              this.handler = getHandle ();
            }

            if ( !event.isConsumed () && ( this.cell != null )
                && !getGraph ().isCellSelected ( this.cell.getCell () ) )
            {
              if ( this.cell instanceof StateView )
              {
                handleSelection ( event );
              }
              else
              {
                selectCellForEvent ( this.cell.getCell (), event );
                setFocus ( this.cell );
                if ( getHandle () != null )
                {
                  getHandle ().mousePressed ( event );
                  this.handler = getHandle ();
                }
                event.consume ();
                this.cell = null;
              }
            }
            else if ( this.cell == null )
            {
              event.consume ();
              getGraph ().clearSelection ();
            }
            else
            {
              PortView portView = getGraph ().getPortViewAt ( event.getX (),
                  event.getY () );

              if ( ( portView != null )
                  && ( portView.getParentView () instanceof StateView ) )
              {
                StateView stateView = ( StateView ) portView.getParentView ();

                if ( !stateView.isSelectionAllowed ( event.getX (), event
                    .getY (), getGraph ().getScale () ) )
                {
                  event.consume ();
                  this.cell = null;
                  getGraph ().clearSelection ();
                }
              }
            }
          }
        }

        if ( !event.isConsumed ()
            && ( getMarquee () != null )
            && ( !isToggleSelectionEvent ( event ) || ( getGraph () == null ) || isForceMarquee ) )
        {
          getMarquee ().mousePressed ( event );
          this.handler = getMarquee ();
        }
      }
    }


    /**
     * {@inheritDoc}
     * 
     * @see MouseAdapter#mouseReleased(MouseEvent)
     */
    @Override
    public final void mouseReleased ( MouseEvent event )
    {
      try
      {
        if ( ( event != null ) && !event.isConsumed ()
            && ( getGraph () != null ) && getGraph ().isEnabled () )
        {
          if ( ( this.handler == getMarquee () ) && ( getMarquee () != null ) )
          {
            getMarquee ().mouseReleased ( event );
          }
          else if ( ( this.handler == getHandle () ) && ( getHandle () != null ) )
          {
            getHandle ().mouseReleased ( event );
          }
          if ( isDescendant ( this.cell, getFocus () )
              && ( event.getModifiers () != 0 ) )
          {
            // Do not switch to parent if Special Selection
            this.cell = getFocus ();
          }
          if ( !event.isConsumed () && ( this.cell != null ) )
          {
            Object tmp = this.cell.getCell ();
            boolean wasSelected = getGraph ().isCellSelected ( tmp );
            if ( !event.isPopupTrigger () || !wasSelected )
            {
              selectCellForEvent ( tmp, event );
              setFocus ( this.cell );
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
   * @see BasicGraphUI#completeEditing()
   */
  @Override
  protected final void completeEditing ()
  {
    super.completeEditing ();
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


  /**
   * Returns the focus {@link CellView}.
   * 
   * @return The focus {@link CellView}.
   */
  protected final CellView getFocus ()
  {
    return this.focus;
  }


  /**
   * Returns the {@link JGraph}.
   * 
   * @return The {@link JGraph}.
   */
  protected final JGraph getGraph ()
  {
    return this.graph;
  }


  /**
   * Returns the {@link GraphModel}.
   * 
   * @return The {@link GraphModel}.
   */
  protected final GraphModel getGraphModel ()
  {
    return this.graphModel;
  }


  /**
   * Returns the last focus {@link CellView}.
   * 
   * @return The last focus {@link CellView}.
   */
  protected final CellView getLastFocus ()
  {
    return this.lastFocus;
  }


  /**
   * Returns the {@link BasicMarqueeHandler}.
   * 
   * @return The {@link BasicMarqueeHandler}.
   */
  protected final BasicMarqueeHandler getMarquee ()
  {
    return this.marquee;
  }


  /**
   * Sets the focus {@link CellView}.
   * 
   * @param focus The focus {@link CellView}.
   */
  protected final void setFocus ( CellView focus )
  {
    this.focus = focus;
  }


  /**
   * Sets the last focus {@link CellView}.
   * 
   * @param lastFocus The last focus {@link CellView}.
   */
  protected final void setLastFocus ( CellView lastFocus )
  {
    this.lastFocus = lastFocus;
  }
}
