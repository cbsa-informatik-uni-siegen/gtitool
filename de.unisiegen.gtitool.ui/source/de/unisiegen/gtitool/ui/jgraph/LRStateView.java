package de.unisiegen.gtitool.ui.jgraph;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.jgraph.graph.CellView;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.VertexRenderer;

import de.unisiegen.gtitool.core.entities.LRState;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;


/**
 * A view for an LRState This view renders rounded rects and makes sure that
 * there is enough space for all LR items
 */
public class LRStateView extends StateView
{

  /**
   * The renderer associated with this view
   */
  public static class JGraphEllipseRenderer extends
      StateView.JGraphEllipseRenderer
  {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = -4785578750364707289L;


    /**
     * The {@link LRStateView}.
     */
    private LRStateView lrstateview;


    /**
     * Allocates a new {@link JGraphEllipseRenderer}.
     * 
     * @param lrstateview
     */
    public JGraphEllipseRenderer ( final LRStateView lrstateview )
    {
      super ( lrstateview );
      this.lrstateview = lrstateview;
    }


    /**
     * Get the state's color
     * 
     * @param state
     * @return The color
     */
    private Color stateColor ( final State state )
    {
      if ( state.isActive () )
        return this.preferenceStateActive;
      if ( this.selected )
        return this.preferenceStateSelected;
      if ( state.isStartState () )
        return this.preferenceTransition;

      return Color.black;
    }


    /**
     * {@inheritDoc}
     * 
     * @see VertexRenderer#paint(java.awt.Graphics)
     */
    @Override
    public void paint ( final Graphics g )
    {
      final Dimension d = staticDimension ( this.lrstateview.getState () );
      g.setFont ( getFont () );
      g.setClip ( null );

      final LRState state = this.lrstateview.getState ();

      g.setColor ( stateColor ( state ) );

      final int offsetx = state.isStartState () ? START_OFFSET : 0;

      g.drawRect ( offsetx, 0, d.width - 1, d.height - 1 );

      int xStart = 15 + offsetx, y = 15, x = xStart;

      final ArrayList < PrettyString > strings = state.getItems ()
          .stringEntries ();

      for ( PrettyString string : strings )
      {
        final Dimension dim = getStringBounds ( string.toString () );
        for ( PrettyToken token : string )
        {
          g.setFont ( super.stateFont ( token, g ) );
          g.setColor ( token.getColor () );
          g.drawString ( token.toString (), x, y + dim.height / 2 );
          x += dim.width;
        }
        x = xStart;
        y += dim.height;
      }

      drawStartArrow ( g, state, 0 );
    }
  }


  /**
   * Calculate the dimension needed by a state
   * 
   * @param state
   * @return the dimension
   */
  public static Dimension staticDimension ( final LRState state )
  {
    final Dimension ret = new Dimension ( 20, 20 ); // minimum size
    for ( PrettyString entry : state.getItems ().stringEntries () )
    {
      final Dimension bounds = getStringBounds ( entry.toString () );
      ret.width = Math.max ( bounds.width, ret.width );
      ret.height += bounds.height;
    }

    return ret;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.jgraph.StateView#getWidth(de.unisiegen.gtitool.core.entities.State)
   */
  @Override
  public int getWidth ( State nstate )
  {
    final int extraWidth = nstate.isStartState () ? START_OFFSET : 0;
    return staticDimension ( ( LRState ) nstate ).width + extraWidth;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.jgraph.StateView#getHeight(de.unisiegen.gtitool.core.entities.State)
   */
  @Override
  public int getHeight ( State nstate )
  {
    return staticDimension ( ( LRState ) nstate ).height;
  }


  /**
   * Calculate the string bounds of the text
   * 
   * @param text
   * @return the bounds
   */
  static Dimension getStringBounds ( final String text )
  {
    FontRenderContext context = new FontRenderContext ( null, false, false );
    final Rectangle2D rectangle = getFont ().getStringBounds ( text, context );
    return new Dimension ( ( int ) rectangle.getWidth (), ( int ) rectangle
        .getHeight () );
  }


  /**
   * Get the font used by this view
   * 
   * @return the font
   */
  public static Font getFont ()
  {
    return font;
  }


  /**
   * The font used by this view
   */
  private static Font font = new Font ( "Dialog", Font.PLAIN, 20 ); //$NON-NLS-1$


  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 209723961535408231L;


  /**
   * Creates a new {@link NodeView}
   * 
   * @param cell The object for this view
   */
  public LRStateView ( final Object cell )
  {
    super ( cell );

    this.state = ( LRState ) ( ( DefaultStateView ) cell ).getState ();

    this.ellipseRenderer = new JGraphEllipseRenderer ( this );
  }


  /**
   * Get the associated state
   * 
   * @return the state
   */
  public LRState getState ()
  {
    return this.state;
  }


  private class Line
  {

    public Line ( Point2D start, Point2D dest )
    {
      this.start = start;
      this.dest = dest;
    }


    public Point2D intersection ( final Line other )
    {
      Point2D p4 = other.dest, p3 = other.start, p2 = this.dest, p1 = this.start;

      final double det = ( p4.getY () - p3.getY () )
          * ( p2.getX () - p1.getX () ) - ( p4.getX () - p3.getX () )
          * ( p2.getY () - p1.getY () );

      if ( det == 0.0 )
        return null;

      final double u1 = ( ( p4.getX () - p3.getX () )
          * ( p1.getY () - p3.getY () ) - ( p4.getY () - p3.getY () )
          * ( p1.getX () - p3.getX () ) )
          / det;

      final double u2 = ( ( p2.getX () - p1.getX () )
          * ( p1.getY () - p3.getY () ) - ( p2.getY () - p1.getY () )
          * ( p1.getX () - p3.getX () ) )
          / det;

      if ( u1 < 0 || u1 > 1 || u2 < 0 || u2 > 1 )
        return null;

      return new Point2D.Double (
          p1.getX () + u1 * ( p2.getX () - p1.getX () ), p1.getY () + u1
              * ( p2.getY () - p1.getY () ) );
    }


    private Point2D start;


    private Point2D dest;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.jgraph.StateView#getPerimeterPoint(org.jgraph.graph.EdgeView,
   *      java.awt.geom.Point2D, java.awt.geom.Point2D)
   */
  @Override
  public Point2D getPerimeterPoint ( EdgeView edge, Point2D source, Point2D p )
  {
    Point2D ret = calculateIntersection ( edge.getSource (), p );
    // TODO: which is the right one?
    if ( ret == null )
      ret = calculateIntersection ( edge.getTarget (), p );
    if ( ret == null )
      ret = super.getPerimeterPoint ( edge, source, p );
    return ret;

  }


  private Point2D calculateIntersection ( final CellView cellSource,
      final Point2D p )
  {
    final Rectangle2D myBounds = this.getBounds ();

    final Line testLine = new Line ( new Point2D.Double ( cellSource
        .getBounds ().getX (), cellSource.getBounds ().getY () ), p );

    ArrayList < Line > lines = new ArrayList < Line > ();

    // top
    lines
        .add ( new Line ( new Point2D.Double ( myBounds.getX (), myBounds
            .getY () ), new Point2D.Double ( myBounds.getMaxX (), myBounds
            .getY () ) ) );

    // bottom
    lines.add ( new Line ( new Point2D.Double ( myBounds.getX (), myBounds
        .getMaxY () ), new Point2D.Double ( myBounds.getMaxX (), myBounds
        .getMaxY () ) ) );

    // left
    lines
        .add ( new Line ( new Point2D.Double ( myBounds.getX (), myBounds
            .getY () ), new Point2D.Double ( myBounds.getX (), myBounds
            .getMaxY () ) ) );

    // right
    lines.add ( new Line ( new Point2D.Double ( myBounds.getMaxX (), myBounds
        .getY () ), new Point2D.Double ( myBounds.getMaxX (), myBounds
        .getMaxY () ) ) );

    // calculate the intersections with each side of the rectangle and take the
    // closest

    Point2D ret = null;

    double currentDistance = Double.MAX_VALUE;

    for ( Line line : lines )
    {
      final Point2D intersectionPoint = line.intersection ( testLine );

      if ( intersectionPoint == null )
        continue;

      final double newDistance = distance ( intersectionPoint, p );

      if ( newDistance < currentDistance )
      {
        currentDistance = newDistance;
        ret = intersectionPoint;
      }
    }

    return ret;
  }


  private static double distance ( final Point2D p1, final Point2D p2 )
  {
    final double dx = p1.getX () - p2.getX ();

    final double dy = p1.getY () - p2.getY ();

    return ( Math.sqrt ( dx * dx + dy * dy ) );
  }


  /**
   * The associated state
   */
  private LRState state;
}
