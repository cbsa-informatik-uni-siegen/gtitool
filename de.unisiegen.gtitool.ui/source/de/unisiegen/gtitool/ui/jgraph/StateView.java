package de.unisiegen.gtitool.ui.jgraph;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.VertexRenderer;
import org.jgraph.graph.VertexView;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.preferences.listener.ColorChangedAdapter;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


/**
 * The state view class.
 * 
 * @author Benjamin Mies
 * @author Christian Fehler
 * @version $Id:StateView.java 910 2008-05-16 00:31:21Z fehler $
 */
public final class StateView extends VertexView
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
    private static final long serialVersionUID = 264864659062743923L;


    /**
     * The {@link State} color.
     */
    protected Color preferenceState;


    /**
     * The active {@link State} color.
     */
    protected Color preferenceStateActive;


    /**
     * The {@link State} background color.
     */
    protected Color preferenceStateBackground;


    /**
     * The error {@link State} color.
     */
    protected Color preferenceStateError;


    /**
     * The final {@link State} color.
     */
    protected Color preferenceStateFinal;


    /**
     * The slected {@link State} color.
     */
    protected Color preferenceStateSelected;


    /**
     * The start {@link State} color.
     */
    protected Color preferenceStateStart;


    /**
     * The normal {@link Transition} color.
     */
    protected Color preferenceTransition;


    /**
     * The {@link StateView}.
     */
    private StateView stateView;


    /**
     * Allocates a new {@link JGraphEllipseRenderer}.
     * 
     * @param stateView The {@link StateView}.
     */
    public JGraphEllipseRenderer ( StateView stateView )
    {
      super ();
      this.stateView = stateView;

      this.preferenceState = PreferenceManager.getInstance ()
          .getColorItemState ().getColor ();
      this.preferenceStateBackground = PreferenceManager.getInstance ()
          .getColorItemStateBackground ().getColor ();
      this.preferenceStateError = PreferenceManager.getInstance ()
          .getColorItemStateError ().getColor ();
      this.preferenceStateActive = PreferenceManager.getInstance ()
          .getColorItemStateActive ().getColor ();
      this.preferenceStateSelected = PreferenceManager.getInstance ()
          .getColorItemStateSelected ().getColor ();
      this.preferenceStateStart = PreferenceManager.getInstance ()
          .getColorItemStateStart ().getColor ();
      this.preferenceStateFinal = PreferenceManager.getInstance ()
          .getColorItemStateFinal ().getColor ();
      this.preferenceTransition = PreferenceManager.getInstance ()
          .getColorItemTransition ().getColor ();

      PreferenceManager.getInstance ().addColorChangedListener (
          new ColorChangedAdapter ()
          {

            @Override
            public void colorChangedState ( Color newColor )
            {
              JGraphEllipseRenderer.this.preferenceState = newColor;
            }


            @Override
            public void colorChangedStateActive ( Color newColor )
            {
              JGraphEllipseRenderer.this.preferenceStateActive = newColor;
            }


            @Override
            public void colorChangedStateBackground ( Color newColor )
            {
              JGraphEllipseRenderer.this.preferenceStateBackground = newColor;
            }


            @Override
            public void colorChangedStateError ( Color newColor )
            {
              JGraphEllipseRenderer.this.preferenceStateError = newColor;
            }


            @Override
            public void colorChangedStateFinal ( Color newColor )
            {
              JGraphEllipseRenderer.this.preferenceStateFinal = newColor;
            }


            @Override
            public void colorChangedStateSelected ( Color newColor )
            {
              JGraphEllipseRenderer.this.preferenceStateSelected = newColor;
            }


            @Override
            public void colorChangedStateStart ( Color newColor )
            {
              JGraphEllipseRenderer.this.preferenceStateStart = newColor;
            }


            @Override
            public void colorChangedTransition ( Color newColor )
            {
              JGraphEllipseRenderer.this.preferenceTransition = newColor;
            }
          } );
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
      State state = null;
      DefaultStateView defaultStateView = null;
      if ( this.stateView.getCell () instanceof DefaultStateView )
      {
        defaultStateView = ( DefaultStateView ) this.stateView.getCell ();
        state = defaultStateView.getState ();
      }
      else
      {
        return;
      }
      int b = this.borderWidth;

      int offsetX = state.isStartState () ? START_OFFSET : 0;
      int offsetY = state.isLoopTransition () ? LOOP_TRANSITION_OFFSET : 0;

      Graphics2D g2 = ( Graphics2D ) g;
      Dimension d = getSize ();
      boolean tmp = this.selected;
      if ( super.isOpaque () )
      {
        Color background = null;
        // overwritten color
        if ( defaultStateView.getOverwrittenColor () != null )
        {
          background = defaultStateView.getOverwrittenColor ();
        }
        // error
        else if ( state.isError () )
        {
          background = this.preferenceStateError;
        }
        // active
        else if ( state.isActive () )
        {
          background = this.preferenceStateActive;
        }
        // start
        else if ( state.isStartState () )
        {
          background = this.preferenceStateStart;
        }
        // final
        else if ( state.isFinalState () )
        {
          background = this.preferenceStateFinal;
        }
        // normal
        else
        {
          background = this.preferenceStateBackground;
        }

        g.setColor ( background );

        if ( ( this.gradientColor != null ) && !this.preview )
        {
          setOpaque ( false );
          g2.setPaint ( new GradientPaint ( 0, 0, background, getWidth (),
              getHeight (), this.gradientColor, true ) );
        }

        // final state
        if ( state.isPowerState () )
        {
          if ( state.isFinalState () )
          {
            g.fillRoundRect ( offsetX + b + 3, offsetY + b + 3, d.width - b - 8
                - offsetX, d.height - b - 8 - offsetY, 50, 50 );
          }
          else
          {
            g.fillRoundRect ( offsetX + b - 1, offsetY + b - 1, d.width - b
                - offsetX, d.height - b - offsetY, 50, 50 );
          }
        }
        else
        {
          if ( state.isFinalState () )
          {
            g.fillOval ( offsetX + b + 3, offsetY + b + 3, d.width - b - 8
                - offsetX, d.height - b - 8 - offsetY );
          }
          else
          {
            g.fillOval ( offsetX + b - 1, offsetY + b - 1, d.width - b
                - offsetX, d.height - b - offsetY );
          }
        }
      }
      try
      {
        setBorder ( null );
        setOpaque ( false );
        this.selected = false;

        g.setColor ( this.preferenceState );
        g.setFont ( getFont () );
        FontMetrics metrics = g.getFontMetrics ();

        int dx = offsetX + ( ( d.width - offsetX ) / 2 )
            - ( metrics.stringWidth ( state.toString () ) / 2 ) - 1;
        int dy = offsetY + ( ( d.height - offsetY ) / 2 )
            + ( metrics.getHeight () / 2 ) - 3;

        PrettyString prettyString = new PrettyString ();
        prettyString.add ( state.toPrettyString () );

        int insets = state.isFinalState () ? 20 : 10;
        // short version
        if ( ( metrics.stringWidth ( state.toString () ) + insets ) > ( d.width - offsetX ) )
        {
          state.setShortNameUsed ( true );

          dx = insets / 2;
          String dots = " ..."; //$NON-NLS-1$
          PrettyToken lastPrettyToken = null;
          while ( ( !prettyString.isEmpty () )
              && ( ( metrics.stringWidth ( prettyString.toString () + dots ) + 2 * dx ) > ( d.width - offsetX ) ) )
          {
            lastPrettyToken = prettyString.removeLastPrettyToken ();
          }

          if ( lastPrettyToken != null )
          {
            char [] chars = lastPrettyToken.getChar ();
            int i = 0;
            String addText = ""; //$NON-NLS-1$
            while ( ( i < chars.length )
                && ( ( metrics.stringWidth ( prettyString.toString () + addText
                    + dots ) + 2 * dx ) <= ( d.width - offsetX ) ) )
            {
              addText += chars [ i ];
              i++ ;
            }

            if ( addText.length () > 0 )
            {
              addText = addText.substring ( 0, addText.length () - 1 );
            }

            prettyString.add ( new PrettyToken ( addText, lastPrettyToken
                .getStyle () ) );
          }

          dx += offsetX;

          // center the dots if there are no pretty tokens
          if ( prettyString.isEmpty () )
          {
            dots = "..."; //$NON-NLS-1$
            dx = offsetX + ( ( d.width - offsetX ) / 2 )
                - ( metrics.stringWidth ( dots ) / 2 ) - 1;
          }

          prettyString.add ( new PrettyToken ( dots, Style.NONE ) );
        }
        // normal version
        else
        {
          state.setShortNameUsed ( false );
        }

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
          g.setColor ( currentToken.getColor () );

          char [] chars = currentToken.getChar ();
          for ( int i = 0 ; i < chars.length ; i++ )
          {
            g.drawChars ( chars, i, 1, dx, dy );
            dx += metrics.charWidth ( chars [ i ] );
          }
        }
      }
      finally
      {
        this.selected = tmp;
      }
      if ( this.bordercolor != null )
      {
        g.setColor ( this.bordercolor );
        g2.setStroke ( new BasicStroke ( b ) );

        if ( state.isPowerState () )
        {
          g.drawRoundRect ( offsetX + b - 1, offsetY + b - 1, d.width - b
              - offsetX, d.height - b - offsetY, 50, 50 );
          if ( state.isFinalState () )
          {
            g.drawRoundRect ( offsetX + b + 3, offsetY + b + 3, d.width - b - 8
                - offsetX, d.height - b - 8 - offsetY, 50, 50 );
          }
        }
        else
        {
          g.drawOval ( offsetX + b - 1, offsetY + b - 1, d.width - b - offsetX,
              d.height - b - offsetY );
          if ( state.isFinalState () )
          {
            g.drawOval ( offsetX + b + 3, offsetY + b + 3, d.width - b - 8
                - offsetX, d.height - b - 8 - offsetY );
          }
        }
      }
      if ( this.selected )
      {
        g.setColor ( this.preferenceStateSelected );

        if ( state.isPowerState () )
        {
          g.drawRoundRect ( offsetX + b - 1, offsetY + b - 1, d.width - b
              - offsetX, d.height - b - offsetY, 50, 50 );
          if ( state.isFinalState () )
          {
            g.drawRoundRect ( offsetX + b + 3, offsetY + b + 3, d.width - b - 8
                - offsetX, d.height - b - 8 - offsetY, 50, 50 );
          }
        }
        else
        {
          g.drawOval ( offsetX + b - 1, offsetY + b - 1, d.width - b - offsetX,
              d.height - b - offsetY );
          if ( state.isFinalState () )
          {
            g.drawOval ( offsetX + b + 3, offsetY + b + 3, d.width - b - 8
                - offsetX, d.height - b - 8 - offsetY );
          }
        }
      }
      if ( state.isStartState () )
      {
        g.setFont ( getFont ().deriveFont ( Font.BOLD ) );
        g.setColor ( this.preferenceTransition );

        g.drawLine ( 0, offsetY + 35, START_OFFSET, offsetY + 35 );

        g.drawLine ( START_OFFSET - 2, offsetY + 34, START_OFFSET - 2,
            offsetY + 36 );
        g.drawLine ( START_OFFSET - 3, offsetY + 33, START_OFFSET - 3,
            offsetY + 37 );
        g.drawLine ( START_OFFSET - 4, offsetY + 32, START_OFFSET - 4,
            offsetY + 38 );
        g.drawLine ( START_OFFSET - 5, offsetY + 31, START_OFFSET - 5,
            offsetY + 39 );
        g.drawLine ( START_OFFSET - 6, offsetY + 30, START_OFFSET - 6,
            offsetY + 40 );

        g.drawString ( "Start", 10, offsetY + 30 ); //$NON-NLS-1$
      }
    }
  }


  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -8873631550630271091L;


  /**
   * The start offset.
   */
  public final static int START_OFFSET = 50;


  /**
   * The loop {@link Transition} offset.
   */
  public final static int LOOP_TRANSITION_OFFSET = 36;


  /**
   * The {@link State} with 50 percent zzom factor.
   */
  private static BufferedImage STATE_50 = null;


  /**
   * The {@link State} with 100 percent zzom factor.
   */
  private static BufferedImage STATE_100 = null;


  /**
   * The {@link State} with 150 percent zzom factor.
   */
  private static BufferedImage STATE_150 = null;


  /**
   * The power {@link State} with 50 percent zzom factor.
   */
  private static BufferedImage POWER_STATE_50 = null;


  /**
   * The power {@link State} with 100 percent zzom factor.
   */
  private static BufferedImage POWER_STATE_100 = null;


  /**
   * The power {@link State} with 150 percent zzom factor.
   */
  private static BufferedImage POWER_STATE_150 = null;

  static
  {
    try
    {
      STATE_50 = ImageIO.read ( JGTIGraph.class
          .getResource ( "/de/unisiegen/gtitool/ui/icon/jgraph/state_50.png" ) );//$NON-NLS-1$
      STATE_100 = ImageIO
          .read ( JGTIGraph.class
              .getResource ( "/de/unisiegen/gtitool/ui/icon/jgraph/state_100.png" ) );//$NON-NLS-1$
      STATE_150 = ImageIO
          .read ( JGTIGraph.class
              .getResource ( "/de/unisiegen/gtitool/ui/icon/jgraph/state_150.png" ) );//$NON-NLS-1$
      POWER_STATE_50 = ImageIO
          .read ( JGTIGraph.class
              .getResource ( "/de/unisiegen/gtitool/ui/icon/jgraph/powerstate_50.png" ) );//$NON-NLS-1$
      POWER_STATE_100 = ImageIO
          .read ( JGTIGraph.class
              .getResource ( "/de/unisiegen/gtitool/ui/icon/jgraph/powerstate_100.png" ) );//$NON-NLS-1$
      POWER_STATE_150 = ImageIO
          .read ( JGTIGraph.class
              .getResource ( "/de/unisiegen/gtitool/ui/icon/jgraph/powerstate_150.png" ) );//$NON-NLS-1$
    }
    catch ( IOException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }


  /**
   * Return the {@link Color} of the {@link BufferedImage} with the given
   * coordinates.
   * 
   * @param image The {@link BufferedImage}.
   * @param x The x coordinate.
   * @param y The y coordinate.
   * @return The {@link Color} of the {@link BufferedImage} with the given
   *         coordinates.
   */
  private static final Color getColor ( BufferedImage image, int x, int y )
  {
    if ( ( x < 0 ) || ( x >= image.getWidth () ) || ( y < 0 )
        || ( y >= image.getHeight () ) )
    {
      return Color.WHITE;
    }
    return new Color ( image.getRGB ( x, y ) );
  }


  /**
   * Returns the height.
   * 
   * @param state The {@link State}.
   * @return The height.
   */
  public static final int getHeight ( State state )
  {
    int width = 70;

    if ( state.isLoopTransition () )
    {
      width += LOOP_TRANSITION_OFFSET;
    }

    return width;
  }


  /**
   * Returns the width.
   * 
   * @param state The {@link State}.
   * @return The width.
   */
  public static final int getWidth ( State state )
  {
    int width = 70;
    if ( state.isPowerState () )
    {
      width = 120;
    }

    if ( state.isStartState () )
    {
      width += START_OFFSET;
    }

    return width;
  }


  /**
   * The {@link JGraphEllipseRenderer} for this view.
   */
  public transient JGraphEllipseRenderer ellipseRenderer;


  /**
   * Allocates a new {@link StateView}.
   */
  public StateView ()
  {
    super ();
    this.ellipseRenderer = new JGraphEllipseRenderer ( this );
  }


  /**
   * Create a new {@link StateView}.
   * 
   * @param cell The cell {@link Object} for this view.
   */
  public StateView ( Object cell )
  {
    super ( cell );
    this.ellipseRenderer = new JGraphEllipseRenderer ( this );
  }


  /**
   * {@inheritDoc}
   * 
   * @see VertexView#getPerimeterPoint(EdgeView, Point2D, Point2D)
   */
  @Override
  public final Point2D getPerimeterPoint ( EdgeView edge,
      @SuppressWarnings ( "unused" ) Point2D source, Point2D p )
  {
    Rectangle2D r = getBounds ();

    State state = null;
    if ( getCell () instanceof DefaultStateView )
    {
      DefaultStateView defaultStateView = ( DefaultStateView ) getCell ();
      state = defaultStateView.getState ();
    }

    boolean loopTransition = false;
    State otherState = null;
    if ( edge != null )
    {
      loopTransition = edge.getSource ().getParentView () == edge.getTarget ()
          .getParentView ();

      if ( edge.getSource ().getParentView () == this )
      {
        StateView stateView = ( StateView ) edge.getTarget ().getParentView ();
        if ( stateView.getCell () instanceof DefaultStateView )
        {
          DefaultStateView defaultStateView = ( DefaultStateView ) stateView
              .getCell ();
          otherState = defaultStateView.getState ();
        }
      }
      else
      {
        StateView stateView = ( StateView ) edge.getSource ().getParentView ();
        if ( stateView.getCell () instanceof DefaultStateView )
        {
          DefaultStateView defaultStateView = ( DefaultStateView ) stateView
              .getCell ();
          otherState = defaultStateView.getState ();
        }
      }
    }

    double x = r.getX ();
    double y = r.getY ();
    double a = r.getWidth () / 2;
    double b = r.getHeight () / 2;

    // x1, y1 - point
    double x1 = p.getX ();
    double y1 = p.getY ();

    if ( ( state != null ) && ( otherState != null ) && !loopTransition )
    {
      boolean firstTransition = false;
      for ( Transition current : state.getTransitionBegin () )
      {
        if ( current.getStateEnd () == otherState )
        {
          firstTransition = true;
        }
      }

      boolean secondTransition = false;
      for ( Transition current : otherState.getTransitionBegin () )
      {
        if ( current.getStateEnd () == state )
        {
          secondTransition = true;
        }
      }

      if ( otherState.isStartState ()
          && ! ( firstTransition && secondTransition ) )
      {
        x1 += START_OFFSET / 2;
      }
      if ( otherState.isLoopTransition ()
          && ! ( firstTransition && secondTransition ) )
      {
        y1 += LOOP_TRANSITION_OFFSET / 2;
      }
    }

    if ( state != null )
    {
      if ( state.isStartState () )
      {
        x = r.getX () + START_OFFSET;
        a = ( r.getWidth () - START_OFFSET ) / 2;
      }
      if ( state.isLoopTransition () )
      {
        y = r.getY () + LOOP_TRANSITION_OFFSET;
        b = ( r.getHeight () - LOOP_TRANSITION_OFFSET ) / 2;
      }
    }

    // x0, y0 - center of ellipse
    double x0 = x + a;
    double y0 = y + b;

    // calculate straight line equation through point and ellipse center
    double dx = x1 - x0;
    double dy = y1 - y0;

    if ( dx == 0 )
    {
      return new Point ( ( int ) x0, ( int ) ( y0 + b * dy / Math.abs ( dy ) ) );
    }

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

    if ( ( state != null ) && state.isPowerState () )
    {
      int checkX = ( int ) ( xout - x );
      int checkY = ( int ) ( yout - y );

      if ( ( checkX >= a ) && ( checkY <= b ) )
      {
        Color color = getColor ( POWER_STATE_100, checkX, checkY );
        while ( ( color.getRed () == 0 ) && ( color.getGreen () == 0 )
            && ( color.getBlue () == 0 ) )
        {
          xout = xout + 1;
          yout = yout - 1;
          checkX = ( int ) ( xout - x );
          checkY = ( int ) ( yout - y );

          color = getColor ( POWER_STATE_100, checkX, checkY );
        }
      }
      else if ( ( checkX <= a ) && ( checkY <= b ) )
      {
        Color color = getColor ( POWER_STATE_100, checkX, checkY );
        while ( ( color.getRed () == 0 ) && ( color.getGreen () == 0 )
            && ( color.getBlue () == 0 ) )
        {
          xout = xout - 1;
          yout = yout - 1;
          checkX = ( int ) ( xout - x );
          checkY = ( int ) ( yout - y );

          color = getColor ( POWER_STATE_100, checkX, checkY );
        }
      }
      else if ( ( checkX <= a ) && ( checkY >= b ) )
      {
        Color color = getColor ( POWER_STATE_100, checkX, checkY );
        while ( ( color.getRed () == 0 ) && ( color.getGreen () == 0 )
            && ( color.getBlue () == 0 ) )
        {
          xout = xout - 1;
          yout = yout + 1;
          checkX = ( int ) ( xout - x );
          checkY = ( int ) ( yout - y );

          color = getColor ( POWER_STATE_100, checkX, checkY );
        }
      }
      else if ( ( checkX >= a ) && ( checkY >= b ) )
      {
        Color color = getColor ( POWER_STATE_100, checkX, checkY );
        while ( ( color.getRed () == 0 ) && ( color.getGreen () == 0 )
            && ( color.getBlue () == 0 ) )
        {
          xout = xout + 1;
          yout = yout + 1;
          checkX = ( int ) ( xout - x );
          checkY = ( int ) ( yout - y );

          color = getColor ( POWER_STATE_100, checkX, checkY );
        }
      }
    }

    return getAttributes ().createPoint ( xout, yout );
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


  /**
   * Returns true if the selection is allowed, otherwise false.
   * 
   * @param absoluteX The absolute x position of the {@link MouseEvent}.
   * @param absoluteY The absolute y position of the {@link MouseEvent}.
   * @param zoomFactor The zoom factor.
   * @return True if the selection is allowed, otherwise false.
   */
  public final boolean isSelectionAllowed ( int absoluteX, int absoluteY,
      double zoomFactor )
  {
    Rectangle2D r = getBounds ();

    int x = ( int ) ( r.getX () * zoomFactor );
    int y = ( int ) ( r.getY () * zoomFactor );
    int width = ( int ) ( r.getWidth () * zoomFactor );
    int height = ( int ) ( r.getHeight () * zoomFactor );

    State state = null;
    if ( getCell () instanceof DefaultStateView )
    {
      state = ( ( DefaultStateView ) getCell () ).getState ();
    }

    if ( state != null )
    {
      int relativeX = absoluteX - x;
      int relativeY = absoluteY - y;

      relativeX = relativeX < 0 ? 0 : relativeX;
      relativeX = relativeX >= width ? width - 1 : relativeX;

      relativeY = relativeY < 0 ? 0 : relativeY;
      relativeY = relativeY >= height ? height - 1 : relativeY;

      if ( state.isStartState () )
      {
        relativeX -= START_OFFSET * zoomFactor;
        width -= START_OFFSET * zoomFactor;
      }
      if ( state.isLoopTransition () )
      {
        relativeY -= LOOP_TRANSITION_OFFSET * zoomFactor;
        height -= LOOP_TRANSITION_OFFSET * zoomFactor;
      }

      if ( ( relativeX < 0 ) || ( relativeX >= width ) || ( relativeY < 0 )
          || ( relativeY >= height ) )
      {
        return false;
      }

      if ( state.isPowerState () )
      {
        if ( zoomFactor == 0.5 )
        {
          Color color = new Color ( POWER_STATE_50.getRGB ( relativeX,
              relativeY ) );
          if ( ( color.getRed () == 0 ) && ( color.getGreen () == 0 )
              && ( color.getBlue () == 0 ) )
          {
            return true;
          }
        }
        else if ( zoomFactor == 1.0 )
        {
          Color color = new Color ( POWER_STATE_100.getRGB ( relativeX,
              relativeY ) );
          if ( ( color.getRed () == 0 ) && ( color.getGreen () == 0 )
              && ( color.getBlue () == 0 ) )
          {
            return true;
          }
        }
        else if ( zoomFactor == 1.5 )
        {
          Color color = new Color ( POWER_STATE_150.getRGB ( relativeX,
              relativeY ) );
          if ( ( color.getRed () == 0 ) && ( color.getGreen () == 0 )
              && ( color.getBlue () == 0 ) )
          {
            return true;
          }
        }
        else
        {
          throw new IllegalArgumentException ( "unsupported scale" ); //$NON-NLS-1$
        }
      }
      else
      {
        if ( zoomFactor == 0.5 )
        {
          Color color = new Color ( STATE_50.getRGB ( relativeX, relativeY ) );
          if ( ( color.getRed () == 0 ) && ( color.getGreen () == 0 )
              && ( color.getBlue () == 0 ) )
          {
            return true;
          }
        }
        else if ( zoomFactor == 1.0 )
        {
          Color color = new Color ( STATE_100.getRGB ( relativeX, relativeY ) );
          if ( ( color.getRed () == 0 ) && ( color.getGreen () == 0 )
              && ( color.getBlue () == 0 ) )
          {
            return true;
          }
        }
        else if ( zoomFactor == 1.5 )
        {
          Color color = new Color ( STATE_150.getRGB ( relativeX, relativeY ) );
          if ( ( color.getRed () == 0 ) && ( color.getGreen () == 0 )
              && ( color.getBlue () == 0 ) )
          {
            return true;
          }
        }
        else
        {
          throw new IllegalArgumentException ( "unsupported scale" ); //$NON-NLS-1$
        }
      }
    }

    return false;
  }
}
