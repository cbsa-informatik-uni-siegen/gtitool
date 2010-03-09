package de.unisiegen.gtitool.ui.jgraph;


import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JComponent;

import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.VertexRenderer;
import org.jgraph.graph.VertexView;


/**
 * {@link VertexView} for BlackBox in the Regex -> ENFA Converter
 */
public class BlackboxView extends StateView
{

  /**
   * The {@link JGTIGraph} ellipse renderer.
   * 
   * @author Benjamin Mies
   * @author Christian Fehler
   */
  public static class JGraphEllipseRenderer extends VertexRenderer
  {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = -4785578750364707289L;


    /**
     * The {@link BlackboxView}.
     */
    private BlackboxView blackboxView;


    /**
     * The y space to the border of the BlackBox
     */
    private int Y_SPACE = JGTIBlackboxGraph.Y_SPACE;


    /**
     * The x space to the border of the BlackBox
     */
    private int X_SPACE = JGTIBlackboxGraph.X_SPACE;


    /**
     * Allocates a new {@link JGraphEllipseRenderer}.
     * 
     * @param blackboxView The {@link StateView}.
     */
    public JGraphEllipseRenderer ( BlackboxView blackboxView )
    {
      super ();
      this.blackboxView = blackboxView;
    }


    /**
     * {@inheritDoc}
     * 
     * @see JComponent#getPreferredSize()
     */
    @Override
    public final Dimension getPreferredSize ()
    {
      Dimension d = super.getPreferredSize ();
      d.width += d.width / 8;
      d.height += d.height / 2;
      return d;
    }


    /**
     * {@inheritDoc}
     * 
     * @see VertexRenderer#paint(java.awt.Graphics)
     */
    @Override
    public void paint ( Graphics g )
    {
      if ( ! ( this.blackboxView.getCell () instanceof DefaultBlackboxView ) )
      {
        return;
      }
      DefaultBlackboxView bview = ( DefaultBlackboxView ) this.blackboxView
          .getCell ();

      int height = 0;
      int width = 0;

      final StateView stateview = new StateView ( bview );

      height += stateview.getHeight ( bview.getStartState ().getState () );
      height += 2 * this.Y_SPACE;

      width += 4 * this.X_SPACE;
      width += 2 * stateview.getWidth ( bview.getStartState ().getState () );

      FontMetrics metrics = g.getFontMetrics ();

      width += metrics.stringWidth ( bview.getContent ().toPrettyString ()
          .toString () );

      g.drawRoundRect ( 0, 0, width, height, 4, 4 );
    }

  }


  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 209723961535408231L;


  /**
   * The {@link JGraphEllipseRenderer} for this view.
   */
  public transient VertexRenderer ellipseRenderer;


  /**
   * Creates a new {@link NodeView}
   */
  public BlackboxView ()
  {
    super ();
    this.ellipseRenderer = new JGraphEllipseRenderer ( this );
  }


  /**
   * Creates a new {@link NodeView}
   * 
   * @param cell The object for this view
   */
  public BlackboxView ( Object cell )
  {
    super ( cell );
    this.ellipseRenderer = new JGraphEllipseRenderer ( this );
  }


  /**
   * {@inheritDoc}
   * 
   * @see VertexView#getRenderer()
   */
  @Override
  public final CellViewRenderer getRenderer ()
  {
    return this.ellipseRenderer;
  }
}
