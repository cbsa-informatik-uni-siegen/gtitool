package de.unisiegen.gtitool.ui.jgraph;


import org.jgraph.graph.VertexView;

import de.unisiegen.gtitool.core.entities.State;


/**
 * The base class for a Graph view that knows its size
 */
public abstract class ViewBase extends VertexView
{

  /**
   * TODO
   */
  private static final long serialVersionUID = 1L;


  /**
   * The cell will be null
   */
  public ViewBase ()
  {
    super ();
  }


  /**
   * Construct the view with a given cell
   * 
   * @param cell
   */
  public ViewBase ( final Object cell )
  {
    super ( cell );
  }


  /**
   * The width this view needs if it wants to display the state
   * 
   * @param state
   * @return the width
   */
  public abstract int getWidth ( State state );


  /**
   * The height this view needs if it wants to display the state
   * 
   * @param state
   * @return the height
   */
  public abstract int getHeight ( State state );
}
