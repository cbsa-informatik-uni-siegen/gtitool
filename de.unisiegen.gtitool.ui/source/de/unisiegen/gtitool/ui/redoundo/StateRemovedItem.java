package de.unisiegen.gtitool.ui.redoundo;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultStateView;
import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultTransitionView;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;


/**
 * Representation of {@link RedoUndoItem} for {@link State} removed action.
 */
public class StateRemovedItem extends RedoUndoItem
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
   * The {@link DefaultTransitionView}.
   */
  private ArrayList < DefaultTransitionView > transitions;


  /**
   * Allocate a new {@link StateRemovedItem}.
   * 
   * @param model The {@link DefaultMachineModel}.
   * @param stateView The {@link DefaultStateView}.
   * @param transitions The {@link DefaultTransitionView}.
   */
  public StateRemovedItem ( DefaultMachineModel model,
      DefaultStateView stateView,
      ArrayList < DefaultTransitionView > transitions )
  {
    super ();
    this.model = model;
    this.stateView = stateView;
    this.transitions = transitions;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.redoundo.RedoUndoItem#redo()
   */
  @Override
  public void redo ()
  {
    this.model.removeState ( this.stateView, false );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.redoundo.RedoUndoItem#undo()
   */
  @Override
  public void undo ()
  {
    // add state
    this.stateView.addPort ();
    this.model.getMachine ().addState ( this.stateView.getState () );
    this.model.getJGraph ().getGraphLayoutCache ().insert ( this.stateView );
    this.model.getStateViewList ().add ( this.stateView );

    // add transitions
    for ( DefaultTransitionView current : this.transitions )
    {
      this.model.createTransitionView ( current.getTransition (), current
          .getSourceView (), current.getTargetView (), false, false, true );
    }
  }

}
