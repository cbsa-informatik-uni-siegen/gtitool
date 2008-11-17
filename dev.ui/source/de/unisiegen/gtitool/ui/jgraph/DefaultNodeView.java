package de.unisiegen.gtitool.ui.jgraph;


import java.awt.geom.Rectangle2D;

import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;

import de.unisiegen.gtitool.core.entities.regex.RegexNode;


/**
 * A {@link DefaultGraphCell} for a {@link RegexNode}
 */
public class DefaultNodeView extends DefaultGraphCell implements Comparable < DefaultNodeView >
{

  /**
   * The serial version uid
   */
  private static final long serialVersionUID = -3502132571376216090L;


  /**
   * The {@link RegexNode}
   */
  private RegexNode regexNode;


  /**
   * The x coordinate
   */
  private int x;


  /**
   * The y coordinate
   */
  private int y;


  /**
   * @param regexNode
   * @param x
   * @param y
   */
  public DefaultNodeView ( RegexNode regexNode, int x, int y )
  {
    super ( regexNode );

    this.x = x;
    this.y = y;

    this.regexNode = regexNode;
  }


  /**
   * {@inheritDoc}
   * 
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo ( DefaultNodeView o )
  {
    if(this.y - o.y == 0) {
      return this.x - o.x;
    }
    return this.y - o.y;
  }


  /**
   * Returns the {@link RegexNode}
   *
   * @return The {@link RegexNode}
   */
  public RegexNode getNode ()
  {
    return this.regexNode;
  }


  /**
   * Returns the x.
   * 
   * @return The x.
   * @see #x
   */
  public int getX ()
  {
    return this.x;
  }


  /**
   * Returns the y.
   * 
   * @return The y.
   * @see #y
   */
  public int getY ()
  {
    return this.y;
  }


  /**
   * Moves this {@link DefaultStateView} relative to the current position.
   * 
   * @param dx The x offset value.
   * @param dy The y offset value.
   */
  public final void moveRelative ( double dx, double dy )
  {
    Rectangle2D bounds = GraphConstants.getBounds ( getAttributes () );

    double newX = bounds.getX () + dx < 0 ? 0 : bounds.getX () + dx;
    double newY = bounds.getY () + dy < 0 ? 0 : bounds.getY () + dy;

    bounds.setRect ( newX, newY, bounds.getWidth (), bounds.getHeight () );
    GraphConstants.setBounds ( getAttributes (), bounds );
  }

}
