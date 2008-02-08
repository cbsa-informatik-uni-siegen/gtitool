package de.unisiegen.gtitool.ui.jgraphcomponents;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.jgraph.JGraph;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.VertexRenderer;
import org.jgraph.graph.VertexView;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


/**
 * The state view class.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public class StateView extends VertexView
{

  /**
   * The {@link JGraph} ellipse renderer.
   * 
   * @author Benjamin Mies
   */
  public static class JGraphEllipseRenderer extends VertexRenderer
  {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 264864659062743923L;


    /** The {@link StateView} */
    private StateView stateView;


    /**
     * Allocate a new {@link JGraphEllipseRenderer}
     * 
     * @param stateView the {@link StateView}
     */
    public JGraphEllipseRenderer ( StateView stateView )
    {
      super ();
      this.stateView = stateView;
    }


    /**
     * {@inheritDoc}
     * 
     * @see javax.swing.JComponent#getPreferredSize()
     */
    @Override
    public Dimension getPreferredSize ()
    {
      Dimension d = super.getPreferredSize ();
      d.width += d.width / 8;
      d.height += d.height / 2;
      return d;
    }


    /**
     * {@inheritDoc}
     * 
     * @see org.jgraph.graph.VertexRenderer#paint(java.awt.Graphics)
     */
    @Override
    public void paint ( Graphics g )
    {
      State state = null;
      if ( this.stateView.getCell () instanceof DefaultStateView )
      {
        state = ( ( DefaultStateView ) this.stateView.getCell () ).getState ();
      }
      else
      {
        throw new RuntimeException ( "not a state view" ); //$NON-NLS-1$
      }
      int b = this.borderWidth;
      Graphics2D g2 = ( Graphics2D ) g;
      Dimension d = getSize ();
      boolean tmp = this.selected;
      if ( super.isOpaque () )
      {
        Color background = null;
        // Error
        if ( state.isError () )
        {
          background = PreferenceManager.getInstance ()
              .getColorItemStateError ().getColor ();
        }
        // Active
        else if ( state.isActive () )
        {
          background = PreferenceManager.getInstance ()
              .getColorItemStateActive ().getColor ();
        }
        // Start
        else if ( state.isStartState () )
        {
          background = PreferenceManager.getInstance ()
              .getColorItemStateStart ().getColor ();
        }
        // Normal
        else
        {
          background = PreferenceManager.getInstance ()
              .getColorItemStateBackground ().getColor ();
        }

        g.setColor ( background );

        if ( this.gradientColor != null && !this.preview )
        {
          setOpaque ( false );
          g2.setPaint ( new GradientPaint ( 0, 0, background, getWidth (),
              getHeight (), this.gradientColor, true ) );
        }
        if ( state.isFinalState () )
        {
          g.fillOval ( b + 3, b + 3, d.width - b - 8, d.height - b - 8 );
        }
        else
        {
          g.fillOval ( b - 1, b - 1, d.width - b, d.height - b );
        }
      }
      try
      {
        setBorder ( null );
        setOpaque ( false );
        this.selected = false;

        g.setColor ( PreferenceManager.getInstance ().getColorItemState ()
            .getColor () );
        g.setFont ( getFont () );
        FontMetrics metrics = getFontMetrics ( getFont () );
        g.drawString ( state.toString (), ( d.width / 2 )
            - ( metrics.stringWidth ( state.toString () ) / 2 ) - 1,
            ( d.height / 2 ) + ( metrics.getHeight () / 2 ) - 3 );
      }
      finally
      {
        this.selected = tmp;
      }
      if ( this.bordercolor != null )
      {
        g.setColor ( this.bordercolor );
        g2.setStroke ( new BasicStroke ( b ) );
        g.drawOval ( b - 1, b - 1, d.width - b, d.height - b );
        if ( state.isFinalState () )
        {
          g.drawOval ( b + 3, b + 3, d.width - b - 8, d.height - b - 8 );
        }
      }
      if ( this.selected )
      {
        g.setColor ( PreferenceManager.getInstance ()
            .getColorItemStateSelected ().getColor () );
        g.drawOval ( b - 1, b - 1, d.width - b, d.height - b );
        if ( state.isFinalState () )
        {
          g.drawOval ( b + 3, b + 3, d.width - b - 8, d.height - b - 8 );
        }
      }
      if ( state.isStartState () )
      {
        g.setColor ( PreferenceManager.getInstance ().getColorItemTransition ()
            .getColor () );
        // Manipulate the clipping area
        g2.setClip ( -100, 0, 150, 70 );

        // Paint an arrow and a string "start" if state is start state
        g.drawLine ( -50, 35, 0, 35 );
        g.fillPolygon ( new int []
        { -6, -6, 0 }, new int []
        { 30, 40, 35 }, 3 );
        g.drawString ( "Start", -40, 30 ); //$NON-NLS-1$
      }
    }
  }


  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -8873631550630271091L;


  /**
   * The renderer for this view
   */
  public transient JGraphEllipseRenderer ellipseRenderer;


  /**
   * Create a new {@link StateView}
   */
  public StateView ()
  {
    super ();
    this.ellipseRenderer = new JGraphEllipseRenderer ( this );
  }


  /**
   * Create a new {@link StateView}
   * 
   * @param cell the Cell Object for this view
   */
  public StateView ( Object cell )
  {
    super ( cell );
    this.ellipseRenderer = new JGraphEllipseRenderer ( this );
  }


  /**
   * {@inheritDoc}
   * 
   * @see org.jgraph.graph.VertexView#getPerimeterPoint(org.jgraph.graph.EdgeView,
   *      java.awt.geom.Point2D, java.awt.geom.Point2D)
   */
  @Override
  @SuppressWarnings ( "unused" )
  public Point2D getPerimeterPoint ( EdgeView edge, Point2D source, Point2D p )
  {
    Rectangle2D r = getBounds ();

    double x = r.getX ();
    double y = r.getY ();
    double a = ( r.getWidth () + 1 ) / 2;
    double b = ( r.getHeight () + 1 ) / 2;

    // x0,y0 - center of ellipse
    double x0 = x + a;
    double y0 = y + b;

    // x1, y1 - point
    double x1 = p.getX ();
    double y1 = p.getY ();

    // calculate straight line equation through point and ellipse center
    // y = d * x + h
    double dx = x1 - x0;
    double dy = y1 - y0;

    if ( dx == 0 )
      return new Point ( ( int ) x0, ( int ) ( y0 + b * dy / Math.abs ( dy ) ) );

    double d = dy / dx;
    double h = y0 - d * x0;

    // calculate intersection
    double e = a * a * d * d + b * b;
    double f = -2 * x0 * e;
    double g = a * a * d * d * x0 * x0 + b * b * x0 * x0 - a * a * b * b;

    double det = Math.sqrt ( f * f - 4 * e * g );

    // two solutions (perimeter points)
    double xout1 = ( -f + det ) / ( 2 * e );
    double xout2 = ( -f - det ) / ( 2 * e );
    double yout1 = d * xout1 + h;
    double yout2 = d * xout2 + h;

    double dist1Squared = Math.pow ( ( xout1 - x1 ), 2 )
        + Math.pow ( ( yout1 - y1 ), 2 );
    double dist2Squared = Math.pow ( ( xout2 - x1 ), 2 )
        + Math.pow ( ( yout2 - y1 ), 2 );

    // correct solution
    double xout, yout;

    if ( dist1Squared < dist2Squared )
    {
      xout = xout1;
      yout = yout1;
    }
    else
    {
      xout = xout2;
      yout = yout2;
    }

    return getAttributes ().createPoint ( xout, yout );
  }


  /**
   * {@inheritDoc}
   * 
   * @see VertexView#getRenderer()
   */
  @Override
  public CellViewRenderer getRenderer ()
  {
    return this.ellipseRenderer;
  }
}
