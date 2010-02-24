package de.unisiegen.gtitool.ui.jgraph;


import java.awt.Color;
import java.awt.Graphics;

import org.jgraph.graph.VertexRenderer;


/**
 * TODO
 */
public class LRStateView extends StateView
{

  /**
   * TODO
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
     * {@inheritDoc}
     * 
     * @see VertexRenderer#paint(java.awt.Graphics)
     */
    @Override
    public void paint ( final Graphics g )
    {
      g.setColor ( Color.BLACK );
      g.drawRect ( 0, 0, 100, 100 );
    }
  }


  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 209723961535408231L;


  /**
   * Creates a new {@link NodeView}
   */
  public LRStateView ()
  {
    super ();
    this.ellipseRenderer = new JGraphEllipseRenderer ( this );
  }


  /**
   * Creates a new {@link NodeView}
   * 
   * @param cell The object for this view
   */
  public LRStateView ( Object cell )
  {
    super ( cell );
  }
}
