package de.unisiegen.gtitool.ui.jgraphcomponents;

import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultEdge;

import de.unisiegen.gtitool.core.entities.Transition;


public class DefaultTransitionView extends DefaultEdge
{
  private Transition transition ;

  public DefaultTransitionView (Transition pTransition)
  {
    this ( null, null );
    this.transition = pTransition;
  }


  public DefaultTransitionView ( Transition pTransition, Object userObject )
  {
    super ( userObject );
    this.transition = pTransition;
  }


  public DefaultTransitionView ( Transition pTransition, Object userObject, AttributeMap storageMap )
  {
    super ( userObject, storageMap );
    this.transition = pTransition;
  }


  
  public Transition getTransition ()
  {
    return this.transition;
  }

}
