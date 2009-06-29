package de.unisiegen.gtitool.ui.jgraph;


import de.unisiegen.gtitool.core.entities.regex.RegexNode;


/**
 * A {@link DefaultEdge} for a Connection between to {@link RegexNode}s
 */
public class DefaultRegexEdgeView extends DefaultEdge
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 1384693439062425467L;


  /**
   * The child of the {@link DefaultRegexEdgeView}
   */
  private DefaultNodeView childView;


  /**
   * The parent of the {@link DefaultRegexEdgeView}
   */
  private DefaultNodeView parentView;


  /**
   * Creates a new {@link DefaultRegexEdgeView}
   * 
   * @param parentView The parent view
   * @param childView The child view
   */
  public DefaultRegexEdgeView ( DefaultNodeView parentView,
      DefaultNodeView childView )
  {
    this.parentView = parentView;
    this.childView = childView;
  }


  /**
   * Returns the childView.
   * 
   * @return The childView.
   * @see #childView
   */
  public DefaultNodeView getChildView ()
  {
    return this.childView;
  }


  /**
   * Returns the parentView.
   * 
   * @return The parentView.
   * @see #parentView
   */
  public DefaultNodeView getParentView ()
  {
    return this.parentView;
  }

}
