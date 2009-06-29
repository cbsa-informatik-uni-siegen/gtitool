package de.unisiegen.gtitool.ui.jgraph;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.VertexRenderer;
import org.jgraph.graph.VertexView;

import de.unisiegen.gtitool.core.entities.regex.LeafNode;
import de.unisiegen.gtitool.core.entities.regex.RegexNode;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


/**
 * A {@link VertexView} for {@link RegexNode}s
 */
public class NodeView extends VertexView
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
    private static final long serialVersionUID = 3497283943770041140L;


    /**
     * The {@link StateView}.
     */
    private NodeView nodeView;


    /**
     * Allocates a new {@link JGraphEllipseRenderer}.
     * 
     * @param nodeView The {@link StateView}.
     */
    public JGraphEllipseRenderer ( NodeView nodeView )
    {
      super ();
      this.nodeView = nodeView;

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
     * @see VertexRenderer#paint(Graphics)
     */
    @Override
    public final void paint ( Graphics g )
    {
      RegexNode node = null;
      DefaultNodeView defaultNodeView = null;
      if ( this.nodeView.getCell () instanceof DefaultNodeView )
      {
        defaultNodeView = ( DefaultNodeView ) this.nodeView.getCell ();
        node = defaultNodeView.getNode ();
      }
      else
      {
        return;
      }

      boolean b = false;

      if ( b && super.isOpaque () )
      {

        Color background = Color.CYAN;
        Graphics2D g2 = ( Graphics2D ) g;
        Dimension d = getSize ();

        g.setColor ( background );

        if ( ( this.gradientColor != null ) && !this.preview )
        {
          setOpaque ( false );
          g2.setPaint ( new GradientPaint ( 0, 0, background, getWidth (),
              getHeight (), this.gradientColor, true ) );
        }

        g.fillRoundRect ( 0, 0, d.width, d.height, 2, 2 );
      }

      Dimension d = getSize ();
      int dx = 0;
      int dy = 0;
      try
      {
        setOpaque ( false );

        FontMetrics metrics = g.getFontMetrics ();

        dx = ( d.width / 2 )
            - ( metrics.stringWidth ( node.getNodeString ().toString () ) / 2 )
            - 1;
        dy = ( d.height / 2 ) + ( metrics.getHeight () / 2 );

        g.setFont ( getFont () );

        PrettyString prettyString = new PrettyString ();
        prettyString.add ( node.toPrettyString () );

        for ( PrettyToken currentToken : prettyString )
        {
          Font font = null;

          if ( !currentToken.isBold () && !currentToken.isItalic () )
          {
            font = g.getFont ().deriveFont ( Font.PLAIN );
          }
          else if ( currentToken.isBold () && currentToken.isItalic () )
          {
            font = g.getFont ().deriveFont ( Font.BOLD | Font.ITALIC );
          }
          else if ( currentToken.isBold () )
          {
            font = g.getFont ().deriveFont ( Font.BOLD );
          }
          else if ( currentToken.isItalic () )
          {
            font = g.getFont ().deriveFont ( Font.ITALIC );
          }

          g.setFont ( font );
        }
      }
      finally
      {
        if ( node instanceof LeafNode )
        {
          LeafNode leaf = ( LeafNode ) node;
          FontMetrics metrics = g.getFontMetrics ();
          if ( this.selected )
          {
            g.setColor ( PreferenceManager.getInstance ()
                .getColorItemRegexSelectedNode ().getColor () );
          }
          else if ( leaf.isActive () )
          {
            g.setColor ( PreferenceManager.getInstance ()
                .getColorItemRegexMarkedNode ().getColor () );
          }
          else
          {
            g.setColor ( PreferenceManager.getInstance ()
                .getColorItemRegexToken ().getColor () );
          }
          g.drawString ( node.getNodeString ().toString (), dx, metrics
              .getHeight () );

          if ( leaf.isPositionShown () )
          {
            String position = String.valueOf ( leaf.getPosition () );
            dx = ( d.width / 2 ) - ( metrics.stringWidth ( position ) / 2 );

            if ( this.selected )
            {
              g.setColor ( PreferenceManager.getInstance ()
                  .getColorItemRegexSelectedNode ().getColor () );
            }
            else if ( leaf.isActive () )
            {
              g.setColor ( PreferenceManager.getInstance ()
                  .getColorItemRegexMarkedNode ().getColor () );
            }
            else
            {
              g.setColor ( PreferenceManager.getInstance ()
                  .getColorItemRegexPosition ().getColor () );
            }
            g.drawString ( position, dx, 2 * metrics.getHeight () );
          }
        }
        else
        {
          if ( this.selected )
          {
            g.setColor ( PreferenceManager.getInstance ()
                .getColorItemRegexSelectedNode ().getColor () );
          }
          else if ( node.isActive () )
          {
            g.setColor ( PreferenceManager.getInstance ()
                .getColorItemRegexMarkedNode ().getColor () );
          }
          else
          {
            g.setColor ( PreferenceManager.getInstance ()
                .getColorItemRegexSymbol ().getColor () );
          }
          g.drawString ( node.getNodeString ().toString (), dx, dy );
        }
      }
    }
  }


  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 7696001267184494753L;


  /**
   * The {@link JGraphEllipseRenderer} for this view.
   */
  public transient VertexRenderer ellipseRenderer;


  /**
   * Creates a new {@link NodeView}
   */
  public NodeView ()
  {
    super ();
    this.ellipseRenderer = new JGraphEllipseRenderer ( this );
  }


  /**
   * Creates a new {@link NodeView}
   * 
   * @param cell The object for this view
   */
  public NodeView ( Object cell )
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
