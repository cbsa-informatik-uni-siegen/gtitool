package de.unisiegen.gtitool.ui.jgraph;


import org.jgraph.graph.VertexView;

import de.unisiegen.gtitool.core.entities.State;


/**
 * TODO
 */
public abstract class ViewBase extends VertexView
{

  public ViewBase ()
  {
    super ();
  }


  public ViewBase ( Object cell )
  {
    super ( cell );
  }


  public abstract int getWidth (State state);


  public abstract int getHeight (State state);
}
