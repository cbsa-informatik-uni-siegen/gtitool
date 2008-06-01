package de.unisiegen.gtitool.ui.jgraph;

import java.awt.event.MouseEvent;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphModel;


/**
 * Own implementation of Jgrah. Modified for use in GTITool.
 *
 */
public class GTIJgraph extends JGraph
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -8954085273729895046L;

  /**
   * Allocate a new {@link GTIJgraph}.
   *
   * @param graphModel
   */
  public GTIJgraph ( DefaultGraphModel graphModel )
  {
    super(graphModel);
  }

  /**
   * {@inheritDoc}
   *
   * @see org.jgraph.JGraph#getToolTipText(java.awt.event.MouseEvent)
   */
  @Override
  public String getToolTipText(MouseEvent event) {
    Object cell = getFirstCellForLocation(event.getX(), event.getY());
    if (cell instanceof DefaultStateView) {
      return ((DefaultStateView) cell).getToolTipString();
    }
    return null;
  }

}
