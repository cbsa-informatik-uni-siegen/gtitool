package de.unisiegen.gtitool.ui.redoundo;


import org.jgraph.graph.DefaultGraphModel;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultStateView;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;


/**
 * Representation of {@link RedoUndoItem} for {@link State} moved action.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public final class StateMovedItem extends RedoUndoItem
{

  /**
   * The {@link DefaultMachineModel}.
   */
  private DefaultMachineModel model;


  /**
   * The {@link DefaultStateView}.
   */
  private DefaultStateView stateView;


  /**
   * The old x value.
   */
  private double oldX;


  /**
   * The old y value.
   */
  private double oldY;


  /**
   * The new x value.
   */
  private double newX;


  /**
   * The new y value.
   */
  private double newY;


  /**
   * Allocates a new {@link StateMovedItem}.
   * 
   * @param model The {@link DefaultMachineModel}.
   * @param stateView The {@link DefaultStateView}.
   * @param oldX The old x value.
   * @param oldY The old y value.
   * @param newX The new x value.
   * @param newY The new y value.
   */
  public StateMovedItem ( DefaultMachineModel model,
      DefaultStateView stateView, double oldX, double oldY, double newX,
      double newY )
  {
    super ();
    this.model = model;
    this.stateView = stateView;
    this.oldX = oldX;
    this.oldY = oldY;
    this.newX = newX;
    this.newY = newY;
  }


  /**
   * {@inheritDoc}
   * 
   * @see RedoUndoItem#redo()
   */
  @Override
  public void redo ()
  {
    this.stateView.move ( this.newX, this.newY );
    this.model.getGraphModel ().cellsChanged (
        DefaultGraphModel.getAll ( this.model.getGraphModel () ) );
  }


  /**
   * {@inheritDoc}
   * 
   * @see RedoUndoItem#undo()
   */
  @Override
  public void undo ()
  {
    this.stateView.move ( this.oldX, this.oldY );
    this.model.getGraphModel ().cellsChanged (
        DefaultGraphModel.getAll ( this.model.getGraphModel () ) );
  }
}
