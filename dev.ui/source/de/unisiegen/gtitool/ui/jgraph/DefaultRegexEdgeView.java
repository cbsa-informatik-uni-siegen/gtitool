package de.unisiegen.gtitool.ui.jgraph;


/**
 * TODO
 */
public class DefaultRegexEdgeView extends DefaultEdge
{

  /**
   * TODO
   */
  private static final long serialVersionUID = 1384693439062425467L;


  private DefaultNodeView parentView;


  private DefaultNodeView childView;


  /**
   * TODO
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
