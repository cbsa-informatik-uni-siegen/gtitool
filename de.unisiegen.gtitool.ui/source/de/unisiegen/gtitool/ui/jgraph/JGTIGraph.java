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

    if ( ( pageIndex * pageWidth ) < getWidth () )
    {
      graphics.translate ( - ( pageIndex * pageWidth ), 0 );
      print = true;
    }
    printAll ( graphics );
    if ( print )
      return Printable.PAGE_EXISTS;
    return Printable.NO_SUCH_PAGE;
  }
}
