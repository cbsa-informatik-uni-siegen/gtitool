/*
 * Copyright (c) 2001-2005, Gaudenz Alder All rights reserved. See LICENSE file
 * in distribution for license details
 */
package de.unisiegen.gtitool.ui.jgraph;


import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphModel;
import org.jgraph.graph.PortView;

import de.unisiegen.gtitool.core.entities.State;


@SuppressWarnings ( "all" )
public class JGraphpadParallelSplineRouter extends DefaultEdge.LoopRouting
{

  protected static GraphModel emptyModel = new DefaultGraphModel ();


  public static JGraphpadParallelSplineRouter sharedInstance = new JGraphpadParallelSplineRouter ();


  /**
   * @return Returns the sharedInstance.
   */
  public static JGraphpadParallelSplineRouter getSharedInstance ()
  {
    return sharedInstance;
  }


  /**
   * The distance between the control point and the middle line. A larger number
   * will lead to a more "bubbly" appearance of the bezier edges.
   */
  public double edgeSeparation = 25;


  private JGraphpadParallelSplineRouter ()
  {
    // empty
  }


  /**
   * @return Returns the edgeSeparation.
   */
  public double getEdgeSeparation ()
  {
    return this.edgeSeparation;
  }


  public int getEdgeStyle ()
  {
    return GraphConstants.STYLE_SPLINE;
  }


  /**
   * Returns the array of parallel edges.
   * 
   * @param edge
   */
  public Object [] getParallelEdges ( EdgeView edge )
  {
    return DefaultGraphModel.getEdgesBetween ( emptyModel, edge.getSource ()
        .getParentView ().getCell (), edge.getTarget ().getParentView ()
        .getCell (), false );
  }


  public List routeEdge ( GraphLayoutCache cache, EdgeView edge )
  {
    List newPoints = new ArrayList ();

    // Check presence of source/target nodes
    if ( ( null == edge.getSource () ) || ( null == edge.getTarget () )
        || ( null == edge.getSource ().getParentView () )
        || ( null == edge.getTarget ().getParentView () ) )
    {
      return null;
    }
    newPoints.add ( edge.getSource () );

    Object [] edges = getParallelEdges ( edge );
    // Find the position of the current edge that we are currently routing
    if ( edges == null )
    {
      return null;
    }
    int position = 0;
    for ( int i = 0 ; i < edges.length ; i++ )
    {
      Object e = edges [ i ];
      if ( e == edge.getCell () )
      {
        position = i;
      }
    }

    // If there is only 1 edge between the two vertices, we don't need this
    // special routing
    if ( edges.length >= 2 )
    {
      // MODIFYBEGIN
      // Find the end point positions
      Point2D from = ( ( PortView ) edge.getSource () ).getLocation ();
      Point2D to = ( ( PortView ) edge.getTarget () ).getLocation ();

      if ( ( from != null ) && ( to != null ) )
      {
        double fromX = from.getX ();
        double fromY = from.getY ();
        double toX = to.getX ();
        double toY = to.getY ();

        StateView stateView = ( StateView ) ( ( PortView ) edge.getSource () )
            .getParentView ();
        if ( stateView.getCell () instanceof DefaultStateView )
        {
          DefaultStateView defaultStateView = ( DefaultStateView ) stateView
              .getCell ();
          State state = defaultStateView.getState ();

          if ( state.isStartState () )
          {
            fromX += StateView.START_OFFSET / 2;
          }
          if ( state.isLoopTransition () )
          {
            fromY += StateView.LOOP_TRANSITION_OFFSET / 2;
          }
        }

        StateView stateViewTo = ( StateView ) ( ( PortView ) edge.getTarget () )
            .getParentView ();
        if ( stateViewTo.getCell () instanceof DefaultStateView )
        {
          DefaultStateView defaultStateView = ( DefaultStateView ) stateViewTo
              .getCell ();
          State state = defaultStateView.getState ();

          if ( state.isStartState () )
          {
            toX += StateView.START_OFFSET / 2;
          }
          if ( state.isLoopTransition () )
          {
            toY += StateView.LOOP_TRANSITION_OFFSET / 2;
          }
        }

        // calculate mid-point of the main edge
        double midX = Math.min ( fromX, toX ) + Math.abs ( ( fromX - toX ) / 2 );
        double midY = Math.min ( fromY, toY ) + Math.abs ( ( fromY - toY ) / 2 );

        // compute the normal slope. The normal of a slope is the
        // negative
        // inverse of the original slope.
        double m = ( fromY - toY ) / ( fromX - toX );

        // bug fix
        if ( m == 0 )
        {
          // System.err.println ("bugfix");
          m = -0.0000000001;
        }

        double theta = Math.atan ( -1 / m );

        // modify the location of the control point along the axis of
        // the
        // normal using the edge position
        double r = this.edgeSeparation * ( Math.floor ( position / 2 ) + 1 );
        if ( ( position % 2 ) == 0 )
        {
          r = -r;
        }

        // convert polar coordinates to cartesian and translate axis to
        // the
        // mid-point
        double ex = r * Math.cos ( theta ) + midX;
        double ey = r * Math.sin ( theta ) + midY;
        Point2D controlPoint = new Point2D.Double ( ex, ey );

        // add the control point to the points list
        newPoints.add ( controlPoint );
        // MODIFYEND
      }
    }
    newPoints.add ( edge.getTarget () );
    return newPoints;
  }


  /**
   * @param edgeSeparation The edgeSeparation to set.
   */
  public void setEdgeSeparation ( double edgeSeparation )
  {
    this.edgeSeparation = edgeSeparation;
  }
}
