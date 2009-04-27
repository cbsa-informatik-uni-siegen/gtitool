package de.unisiegen.gtitool.ui.jgraph;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import org.jgraph.graph.DefaultGraphModel;

import de.unisiegen.gtitool.core.entities.regex.RegexNode;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;


/**
 * A {@link JGTIGraph} with BlackBoxes
 */
public class JGTIBlackboxGraph extends JGTIGraph
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 3156000727862499906L;


  /**
   * The {@link DefaultBlackboxView}s
   */
  private ArrayList < DefaultBlackboxView > defaultBlackboxViews;


  /**
   * Creates new {@link JGTIBlackboxGraph}
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


  /**
   * The x space
   */
  public static int X_SPACE = 8;


  /**
   * The y space
   */
  public static int Y_SPACE = 8;


  /**
   * The scale factor
   */
  public static double SCALE_FACTOR = 0.8;


  /**
   * {@inheritDoc}
   * 
   * @see JGTIGraph#paintComponent(java.awt.Graphics)
   */
  @Override
  protected void paintComponent ( Graphics graphics )
  {
    super.paintComponent ( graphics );

    Graphics2D g = ( Graphics2D ) graphics;
    g.scale ( SCALE_FACTOR, SCALE_FACTOR );

    for ( DefaultBlackboxView bview : this.defaultBlackboxViews )
    {
      DefaultStateView startView = bview.getStartState ();
      DefaultStateView endView = bview.getFinalState ();
      int x0 = ( int ) ( bview.getStartState ().getPositionX () - JGTIBlackboxGraph.X_SPACE );
      int y0 = ( int ) ( bview.getStartState ().getPositionY () - JGTIBlackboxGraph.Y_SPACE );
      int y1 = ( int ) ( bview.getStartState ().getHeight () + 2 * JGTIBlackboxGraph.Y_SPACE );

      int x1 = ( int ) ( endView.getPositionX () - startView.getPositionX ()
          + endView.getWidth () + 2 * X_SPACE );
      g.drawRoundRect ( x0, y0, x1, y1, 20, 20 );

      int xString = ( int ) ( bview.getStartState ().getPositionX ()
          + bview.getStartState ().getWidth () + 2 * JGTIBlackboxGraph.X_SPACE );
      int yString = ( int ) ( bview.getStartState ().getPositionY () + bview
          .getStartState ().getHeight () / 2 );
      g.drawString ( bview.getContentString (), xString, yString );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.jgraph.JGTIGraph#getToolTipText(java.awt.event.MouseEvent)
   */
  @Override
  public String getToolTipText ( MouseEvent event )
  {
    for ( DefaultBlackboxView bview : this.defaultBlackboxViews )
    {
      if ( bview.containsPoint ( event.getPoint () ) && bview.needsToolTip () )
      {
        return bview.getContent ().toString ();
      }
    }
    return super.getToolTipText ( event );
  }


  /**
   * Adds a BlackBox for a specified {@link DefaultBlackboxView}
   * 
   * @param defaultBlackboxView The {@link DefaultBlackboxView}
   */
  public void addBlackBox ( DefaultBlackboxView defaultBlackboxView )
  {
    if ( defaultBlackboxView == null )
    {
      throw new IllegalArgumentException ( "black box view is null" ); //$NON-NLS-1$
    }
    if ( this.defaultBlackboxViews.contains ( defaultBlackboxView ) )
    {
      repaint ();
      return;
    }
    this.defaultBlackboxViews.add ( defaultBlackboxView );

    repaint ();
  }


  /**
   * Returns the {@link DefaultBlackboxView} for a specified {@link RegexNode}
   * 
   * @param n The {@link RegexNode}
   * @return The {@link DefaultBlackboxView} if exists, else <code>null</code>
   */
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


  /**
   * Removes the black box for a specified {@link RegexNode}
   * 
   * @param n The {@link RegexNode}
   */
  public void removeBlackBox ( RegexNode n )
  {
    if ( getBlackboxViewForRegexNode ( n ) != null )
    {
      removeBlackBox ( getBlackboxViewForRegexNode ( n ) );
    }
  }


  /**
   * Removes the blackbox for a specified {@link DefaultBlackboxView}
   * 
   * @param defaultBlackboxView The {@link DefaultBlackboxView}
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
