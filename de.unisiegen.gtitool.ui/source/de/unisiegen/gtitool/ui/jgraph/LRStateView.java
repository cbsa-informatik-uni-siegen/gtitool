package de.unisiegen.gtitool.ui.jgraph;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import org.jgraph.graph.VertexRenderer;

import de.unisiegen.gtitool.core.entities.LRState;
import de.unisiegen.gtitool.core.entities.State;


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
      final Dimension d = staticDimension ( this.lrstateview.getState () );
      g.setFont ( getFont () );
      g.setClip ( null );
      g.drawRoundRect ( 0, 0, d.width - 1, d.height - 1, 30, 30 );

      int y = 15;

      final ArrayList < String > strings = this.lrstateview.getState ()
          .getItems ().stringEntries ();

      for ( String string : strings )
      {
        final Dimension dim = getStringBounds ( string );
        g.drawString ( string, 15, y + dim.height / 2 );
        y += dim.height;
      }

    }
  }


  /**
   * TODO
   * 
   * @param state
   * @return
   */
  public static Dimension staticDimension ( final LRState state )
  {
    Dimension ret = new Dimension ( 20, 20 ); // minimum size
    for ( String entry : state.getItems ().stringEntries () )
    {
      final Dimension bounds = getStringBounds ( entry );
      ret.width = Math.max ( bounds.width, ret.width );
      ret.height += bounds.height;
    }

    return ret;
  }


  @Override
  public int getWidth ( State state )
  {
    return staticDimension ( ( LRState ) state ).width;
  }


  @Override
  public int getHeight ( State state )
  {
    return staticDimension ( ( LRState ) state ).height;
  }


  /**
   * TODO
   * 
   * @param text
   * @return
   */
  static Dimension getStringBounds ( final String text )
  {
    FontRenderContext context = new FontRenderContext ( null, false, false );
    final Rectangle2D rectangle = getFont ().getStringBounds ( text, context );
    return new Dimension ( ( int ) rectangle.getWidth (), ( int ) rectangle
        .getHeight () );
  }


  /**
   * TODO
   * 
   * @return
   */
  public static Font getFont ()
  {
    return font;
  }


  /**
   * TODO
   */
  private static Font font = new Font ( "Dialog", Font.PLAIN, 20 );


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
   * TODO
   * 
   * @return
   */
  public LRState getState ()
  {
    return this.state;
  }


  private LRState state;
}
