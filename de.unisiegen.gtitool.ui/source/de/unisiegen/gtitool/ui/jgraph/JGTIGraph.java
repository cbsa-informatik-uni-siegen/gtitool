package de.unisiegen.gtitool.ui.jgraph;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

import javax.swing.JComponent;
import javax.swing.ToolTipManager;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphModel;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Transition;


/**
 * Special {@link JGraph}.
 * 
 * @author Benjamin Mies
 * @author Christian Fehler
 * @version $Id:GPCellViewFactory.java 910 2008-05-16 00:31:21Z fehler $
 */
public final class JGTIGraph extends JGraph implements Printable
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 6157004880461808684L;


  /**
   * The height of the graph.
   */
  private double graphHeight = 0;


  /**
   * The width of the graph.
   */
  private double graphWidth = 0;


  /**
   * The page count of a row.
   */
  private int pagesPerRow = 0;


  /**
   * The bottom margin.
   */
  private int marginBottom = 50;


  /**
   * The right margin.
   */
  private int marginRight = 50;


  /**
   * The left margin.
   */
  private int marginLeft = 50;


  /**
   * The top margin.
   */
  private int marginTop = 50;


  /**
   * The {@link StateView} which is used to render the painted
   * {@link Transition}.
   */
  private StateView stateView = null;


  /**
   * The end {@link Point} which is used to render the painted
   * {@link Transition}.
   */
  private Point endPoint = null;


  /**
   * Allocates a new {@link JGTIGraph}.
   * 
   * @param graphModel The {@link DefaultGraphModel}.
   */
  public JGTIGraph ( DefaultGraphModel graphModel )
  {
    super ( graphModel );
    ToolTipManager.sharedInstance ().registerComponent ( this );
  }


  /**
   * {@inheritDoc}
   * 
   * @see JGraph#getToolTipText(MouseEvent)
   */
  @Override
  public final String getToolTipText ( MouseEvent event )
  {
    Object cell = getFirstCellForLocation ( event.getX (), event.getY () );

    if ( cell instanceof DefaultStateView )
    {
      DefaultStateView defaultStateView = ( DefaultStateView ) cell;
      State state = defaultStateView.getState ();

      // only if the short name is used
      if ( !state.isShortNameUsed () )
      {
        return null;
      }

      // translate
      int x = event.getX () - ( int ) defaultStateView.getPositionX ();
      int y = event.getY () - ( int ) defaultStateView.getPositionY ();
      int width = ( int ) defaultStateView.getWidth ();
      int height = ( int ) defaultStateView.getHeight ();

      // clip
      x = x < 0 ? 0 : x;
      x = x > width ? width : x;
      y = y < 0 ? 0 : y;
      y = y > height ? height : y;

      // only if the mouse is over the state name
      int inset = 8;
      if ( ( x > inset ) && ( x < ( width - inset ) )
          && ( y > ( ( height / 2 ) - inset ) )
          && ( y < ( ( height / 2 ) + inset ) ) )
      {
        return state.toPrettyString ().toHTMLString ();
      }
    }
    return null;
  }


  /**
   * Returns the used bounds.
   * 
   * @return The used bounds.
   */
  public final Rectangle getUsedBounds ()
  {
    int minX = Integer.MAX_VALUE;
    int maxX = 0;
    int minY = Integer.MAX_VALUE;
    int maxY = 0;

    for ( Object object : DefaultGraphModel.getAll ( getModel () ) )
    {
      if ( object instanceof DefaultStateView )
      {
        DefaultStateView current = ( DefaultStateView ) object;

        int x = ( int ) current.getPositionX ();
        int y = ( int ) current.getPositionY ();
        int width = ( int ) current.getWidth ();
        int height = ( int ) current.getHeight ();

        minX = Math.min ( minX, x );
        maxX = Math.max ( maxX, x + width );
        minY = Math.min ( minY, y );
        maxY = Math.max ( maxY, y + height );
      }
      else if ( object instanceof DefaultTransitionView )
      {
        DefaultTransitionView current = ( DefaultTransitionView ) object;
        Rectangle bounds = current.getTransition ().getLabelBounds ();

        int x = bounds.x;
        int y = bounds.y;
        int width = bounds.width;
        int height = bounds.height;

        minX = Math.min ( minX, x );
        maxX = Math.max ( maxX, x + width );
        minY = Math.min ( minY, y );
        maxY = Math.max ( maxY, y + height );
      }
    }

    return new Rectangle ( minX, minY, maxX - minX, maxY - minY );
  }


  /**
   * {@inheritDoc}
   * 
   * @see JComponent#paintComponent(Graphics)
   */
  @Override
  protected final void paintComponent ( Graphics graphics )
  {
    super.paintComponent ( graphics );

    Graphics2D g = ( Graphics2D ) graphics;

    if ( ( this.stateView != null ) && ( this.endPoint != null ) )
    {
      Point2D startPoint = this.stateView.getPerimeterPoint ( null, null,
          this.endPoint );

      int x1 = ( int ) startPoint.getX ();
      int y1 = ( int ) startPoint.getY ();
      int x2 = this.endPoint.x;
      int y2 = this.endPoint.y;

      g.drawLine ( x1, y1, x2, y2 );

      int dx = x2 - x1;
      int dy = y2 - y1;

      double lenght = Math.sqrt ( Math.pow ( dx, 2 ) + Math.pow ( dy, 2 ) );

      if ( lenght > 0 )
      {
        if ( dx >= 0 && dy >= 0 )
        {
          double cos = dx / lenght;
          double acos = Math.acos ( cos );
          g.rotate ( acos, x2, y2 );
        }
        else if ( dx >= 0 && dy < 0 )
        {
          double cos = -dx / lenght;
          double acos = Math.acos ( cos );
          g.rotate ( acos - Math.PI, x2, y2 );
        }
        else if ( dx < 0 && dy >= 0 )
        {
          double cos = dx / lenght;
          double acos = Math.acos ( cos );
          g.rotate ( acos, x2, y2 );
        }
        else if ( dx < 0 && dy < 0 )
        {
          double cos = -dx / lenght;
          double acos = Math.acos ( cos );
          g.rotate ( acos - Math.PI, x2, y2 );
        }
      }

      g.fillPolygon ( new int []
      { x2 - 5, x2 - 5, x2 }, new int []
      { y2 + 5, y2 - 5, y2 }, 3 );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see Printable#print(Graphics, PageFormat, int)
   */
  public int print ( Graphics graphics, PageFormat pageFormat, int pageIndex )
  {
    boolean print = false;
    int pageWidth = ( int ) pageFormat.getWidth ();
    int pageHeight = ( int ) pageFormat.getHeight ();

    if ( pageIndex == 0 )
    {
      int width = pageWidth - this.marginRight;
      int height = pageHeight - this.marginBottom;
      graphics.setClip ( 0, 0, width, height );
    }
    else
    {
      int width = pageWidth - this.marginRight - this.marginLeft;
      int height = pageHeight - this.marginBottom - this.marginTop;
      graphics.setClip ( this.marginLeft, this.marginTop, width, height );
    }
    Rectangle rect = getUsedBounds ();
    this.graphWidth = rect.getWidth () + rect.x;
    this.graphHeight = rect.getHeight () + rect.y;

    int x = 0;

    int y = 0;

    if ( ( pageIndex * ( pageWidth - this.marginRight - this.marginLeft ) ) < this.graphWidth )
    {
      x = - ( pageIndex * pageWidth );
      x = x
          + ( pageIndex * this.marginLeft + this.marginRight * ( pageIndex + 1 ) );
      graphics.translate ( x, 0 + this.marginTop );
      print = true;
      this.pagesPerRow = pageIndex + 1;
    }
    else
    {
      int row = pageIndex / this.pagesPerRow;

      if ( ( row * pageHeight ) < this.graphHeight )
      {

        x = - ( ( ( pageIndex - row * this.pagesPerRow ) ) * pageWidth );
        y = - ( row * pageHeight );

        x = x
            + ( ( pageIndex - row * this.pagesPerRow ) * this.marginLeft + this.marginRight
                * ( pageIndex - row * this.pagesPerRow + 1 ) );
        y = y + ( row * this.marginTop + this.marginBottom * ( row + 1 ) );

        graphics.translate ( x, y );
        print = true;
      }

    }

    printAll ( graphics );
    if ( print )
    {
      return Printable.PAGE_EXISTS;
    }
    return Printable.NO_SUCH_PAGE;
  }


  /**
   * Resets the painted {@link Transition}.
   */
  public final void resetPaintedTransition ()
  {
    this.stateView = null;
    this.endPoint = null;

    repaint ();
  }


  /**
   * Sets the marginBottom.
   * 
   * @param marginBottom The marginBottom to set.
   * @see #marginBottom
   */
  public final void setMarginBottom ( int marginBottom )
  {
    this.marginBottom = marginBottom;
  }


  /**
   * Sets the marginLeft.
   * 
   * @param marginLeft The marginLeft to set.
   * @see #marginLeft
   */
  public final void setMarginLeft ( int marginLeft )
  {
    this.marginLeft = marginLeft;
  }


  /**
   * Sets the marginRight.
   * 
   * @param marginRight The marginRight to set.
   * @see #marginRight
   */
  public final void setMarginRight ( int marginRight )
  {
    this.marginRight = marginRight;
  }


  /**
   * Sets the marginTop.
   * 
   * @param marginTop The marginTop to set.
   * @see #marginTop
   */
  public final void setMarginTop ( int marginTop )
  {
    this.marginTop = marginTop;
  }


  /**
   * Sets the painted {@link Transition}.
   * 
   * @param stateView The used {@link StateView}.
   * @param endPoint The end {@link Point}.
   */
  public final void setPaintedTransition ( StateView stateView, Point endPoint )
  {
    if ( stateView == null )
    {
      throw new IllegalArgumentException ( "state view is null" ); //$NON-NLS-1$
    }
    this.stateView = stateView;

    if ( endPoint == null )
    {
      throw new IllegalArgumentException ( "end point is null" ); //$NON-NLS-1$
    }
    this.endPoint = endPoint;

    repaint ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see JGraph#updateUI()
   */
  @Override
  public final void updateUI ()
  {
    setUI ( new JGTIBasicGraphUI () );
    invalidate ();
  }
}
