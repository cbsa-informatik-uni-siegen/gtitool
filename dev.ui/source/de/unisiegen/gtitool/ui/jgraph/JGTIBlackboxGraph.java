package de.unisiegen.gtitool.ui.jgraph;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import org.jgraph.graph.DefaultGraphModel;

import de.unisiegen.gtitool.core.entities.regex.RegexNode;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;


/**
 * TODO
 */
public class JGTIBlackboxGraph extends JGTIGraph
{

  /**
   * TODO
   */
  private static final long serialVersionUID = 3156000727862499906L;


  /**
   * TODO
   */
  private ArrayList < DefaultBlackboxView > defaultBlackboxViews;


  /**
   * TODO
   * 
   * @param defaultMachineModel
   * @param defaultGraphModel
   */
  public JGTIBlackboxGraph ( DefaultMachineModel defaultMachineModel,
      DefaultGraphModel defaultGraphModel )
  {
    super ( defaultMachineModel, defaultGraphModel );
    this.defaultBlackboxViews = new ArrayList < DefaultBlackboxView > ();
  }


  public static int X_SPACE = 10;


  public static int Y_SPACE = 10;


  /**
   * TODO
   * 
   * @param graphics
   * @see de.unisiegen.gtitool.ui.jgraph.JGTIGraph#paintComponent(java.awt.Graphics)
   */
  @Override
  protected void paintComponent ( Graphics graphics )
  {
    super.paintComponent ( graphics );

    Graphics2D g = ( Graphics2D ) graphics;

    for ( DefaultBlackboxView bview : this.defaultBlackboxViews )
    {
      DefaultStateView startView = bview.getStartState ();
      DefaultStateView endView = bview.getFinalState ();
      int x0 = ( int ) ( bview.getStartState ().getPositionX () - this.X_SPACE );
      int y0 = ( int ) ( bview.getStartState ().getPositionY () - this.X_SPACE );
      int y1 = ( int ) ( bview.getStartState ().getHeight () + 2 * this.Y_SPACE );

      int x1 = ( int ) ( 2 * JGTIBlackboxGraph.X_SPACE + endView.getPositionX ()
          + endView.getWidth () - startView.getPositionX () );
      g.drawRoundRect ( x0, y0, x1, y1, 20, 20 );

      int xString = ( int ) ( bview.getStartState ().getPositionX ()
          + bview.getStartState ().getWidth () + this.X_SPACE );
      int yString = ( int ) ( bview.getStartState ().getPositionY () + bview
          .getStartState ().getHeight () / 2 );
      g.drawString ( bview.getContent ().toPrettyString ().toString (),
          xString, yString );
    }
  }


  /**
   * TODO
   * 
   * @param defaultBlackboxView
   */
  public void addBlackBox ( DefaultBlackboxView defaultBlackboxView )
  {
    if ( defaultBlackboxView == null )
    {
      throw new IllegalArgumentException ( "black box view is null" ); //$NON-NLS-1$
    }
    this.defaultBlackboxViews.add ( defaultBlackboxView );

    repaint ();
  }


  public DefaultBlackboxView getBlackboxViewForRegexNode ( RegexNode n )
  {
    for ( DefaultBlackboxView b : this.defaultBlackboxViews )
    {
      if ( b.getContent ().equals ( n ) )
      {
        return b;
      }
    }
    return null;
  }


  public void removeBlackBox ( RegexNode n )
  {
    if ( getBlackboxViewForRegexNode ( n ) != null )
    {
      removeBlackBox ( getBlackboxViewForRegexNode ( n ) );
    }
  }


  /**
   * TODO
   * 
   * @param defaultBlackboxView
   */
  public void removeBlackBox ( DefaultBlackboxView defaultBlackboxView )
  {
    if ( defaultBlackboxView == null )
    {
      throw new IllegalArgumentException ( "black box view is null" ); //$NON-NLS-1$
    }
    this.defaultBlackboxViews.remove ( defaultBlackboxView );

    repaint ();
  }
}
