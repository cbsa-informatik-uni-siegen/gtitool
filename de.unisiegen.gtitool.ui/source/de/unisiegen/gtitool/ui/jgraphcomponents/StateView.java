package de.unisiegen.gtitool.ui.jgraphcomponents;


import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.VertexRenderer;
import org.jgraph.graph.VertexView;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


/**
 * TODO
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public class StateView extends VertexView
{

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
   * @param pCell the Cell Object for this view
   */
  public StateView ( Object pCell )
  {
    super ( pCell );
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
   * @see org.jgraph.graph.VertexView#getRenderer()
   */
  @Override
  public CellViewRenderer getRenderer ()
  {
    return this.ellipseRenderer;
  }


  /**
   * TODO
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
     * @param pStateView the {@link StateView}
     */
    public JGraphEllipseRenderer ( StateView pStateView )
    {
      super ();
      this.stateView = pStateView;
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
        state = ( ( DefaultStateView ) this.stateView.getCell () ).getState ();
      int b = this.borderWidth;
      Graphics2D g2 = ( Graphics2D ) g;
      Dimension d = getSize ();
      boolean tmp = this.selected;
      if ( super.isOpaque () )
      {
        g.setColor ( super.getBackground () );
        if ( this.gradientColor != null && !this.preview )
        {
          setOpaque ( false );
          g2.setPaint ( new GradientPaint ( 0, 0, getBackground (),
              getWidth (), getHeight (), this.gradientColor, true ) );
        }
        if ( state != null && state.isFinalState () )
        {
          g.fillOval ( b + 3, b + 3, d.width - b - 8, d.height - b - 8 );
        }
        else
          g.fillOval ( b - 1, b - 1, d.width - b, d.height - b );
      }
      try
      {
        setBorder ( null );
        setOpaque ( false );
        this.selected = false;
        super.paint ( g );
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

        if ( state != null && state.isFinalState () )
        {
          g.drawOval ( b + 3, b + 3, d.width - b - 8, d.height - b - 8 );
        }
      }
      if ( this.selected )
      {

        g.setColor ( PreferenceManager.getInstance ()
            .getColorItemSelectedState ().getColor () );
        g.drawOval ( b - 1, b - 1, d.width - b, d.height - b );

      }
    }
  }
}
