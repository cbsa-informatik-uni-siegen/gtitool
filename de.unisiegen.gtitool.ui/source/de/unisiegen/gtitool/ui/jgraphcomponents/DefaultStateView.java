package de.unisiegen.gtitool.ui.jgraphcomponents;

import javax.swing.tree.MutableTreeNode;

import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultGraphCell;

import de.unisiegen.gtitool.core.entities.State;


public class DefaultStateView extends DefaultGraphCell
{
  State state;

  public DefaultStateView ()
  {
    this(null, null);
  }


  public DefaultStateView ( State pState, Object userObject )
  {
    super ( userObject );
    this.state = pState;
  }


  public DefaultStateView ( State pState, Object userObject, AttributeMap storageMap )
  {
    super ( userObject, storageMap );
    this.state = pState;
  }


  public DefaultStateView ( State pState, Object userObject, AttributeMap storageMap,
      MutableTreeNode [] children )
  {
    super ( userObject, storageMap, children );
    this.state = pState;
  }


  
  public State getState ()
  {
    return this.state;
  }

}
