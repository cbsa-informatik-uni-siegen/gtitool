package de.unisiegen.gtitool.ui.jgraph;


import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

import javax.swing.ToolTipManager;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphModel;

import de.unisiegen.gtitool.core.entities.State;


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
  double graphHeight = 0;


  /**
   * The width of the graph.
   */
  double graphWidth = 0;


  /**
   * The page count of a row.
   */
  int pagesPerRow = 0;


  /**
   * The bottom margin.
   */
  private int marginBottom = 50;


  /**
   * The right margin.
   */
  private int marginRight = 50;


  
  /**
   * Sets the marginBottom.
   *
   * @param marginBottom The marginBottom to set.
   * @see #marginBottom
   */
  public void setMarginBottom ( int marginBottom )
  {
    this.marginBottom = marginBottom;
  }


  
  /**
   * Sets the marginRight.
   *
   * @param marginRight The marginRight to set.
   * @see #marginRight
   */
  public void setMarginRight ( int marginRight )
  {
    this.marginRight = marginRight;
  }


  
  /**
   * Sets the marginLeft.
   *
   * @param marginLeft The marginLeft to set.
   * @see #marginLeft
   */
  public void setMarginLeft ( int marginLeft )
  {
    this.marginLeft = marginLeft;
  }


  
  /**
   * Sets the marginTop.
   *
   * @param marginTop The marginTop to set.
   * @see #marginTop
   */
  public void setMarginTop ( int marginTop )
  {
    this.marginTop = marginTop;
  }


  /**
   * The left margin.
   */
  private int marginLeft = 50;


  /**
   * The top margin.
   */
  private int marginTop = 50;


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
   * Calculate the width and heigth of the graph.
   */
  private void calculateGraphSize ()
  {
    for ( Object object : DefaultGraphModel.getAll ( getModel () ) )
    {
      if ( object instanceof DefaultStateView )
      {
        DefaultStateView current = ( DefaultStateView ) object;

        this.graphWidth = Math.max ( current.getPositionX ()
            + current.getWidth (), this.graphWidth );
        this.graphHeight = Math.max ( current.getPositionY ()
            + current.getHeight (), this.graphHeight );
      }
    }
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
      DefaultStateView stateView = ( DefaultStateView ) cell;
      State state = stateView.getState ();

      // only if the short name is used
      if ( !state.isShortNameUsed () )
      {
        return null;
      }

      // translate
      int x = event.getX () - ( int ) stateView.getPositionX ();
      int y = event.getY () - ( int ) stateView.getPositionY ();
      int width = ( int ) stateView.getWidth ();
      int height = ( int ) stateView.getHeight ();

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
   * {@inheritDoc}
   * 
   * @see java.awt.print.Printable#print(java.awt.Graphics,
   *      java.awt.print.PageFormat, int)
   */
  public int print ( Graphics graphics, PageFormat pageFormat, int pageIndex )
  {
    boolean print = false;
    int pageWidth = ( int ) pageFormat.getWidth ();
    int pageHeight = ( int ) pageFormat.getHeight ();
    
    int width = pageWidth - this.marginRight;
    int height = pageHeight - this.marginBottom ;
    
//    graphics.setClip ( 0, 0, width, height );

    this.graphWidth = 0;
    this.graphHeight = 0;

    calculateGraphSize ();

    int x = 0;

    int y = 0;

    if ( ( pageIndex * pageWidth ) < this.graphWidth )
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
            + ( (pageIndex - row * this.pagesPerRow ) * this.marginLeft + this.marginRight
                * ( pageIndex - row * this.pagesPerRow + 1 ) );
        y = y
            + ( row * this.marginTop + this.marginBottom
                * ( row + 1 ) );

        graphics.translate ( x, y );
        print = true;
      }

    }
    
    printAll ( graphics );
    if ( print )
      return Printable.PAGE_EXISTS;
    return Printable.NO_SUCH_PAGE;
  }
}
