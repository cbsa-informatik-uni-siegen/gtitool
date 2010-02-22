package de.unisiegen.gtitool.ui.redoundo;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;
import de.unisiegen.gtitool.ui.jgraph.DefaultTransitionView;
import de.unisiegen.gtitool.ui.model.DefaultStateMachineModel;


/**
 * Representation of {@link RedoUndoItem} for {@link State} removed action.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public final class StateRemovedItem extends RedoUndoItem
{

  /**
   * The {@link DefaultStateMachineModel}.
   */
  private DefaultStateMachineModel model;


  /**
   * The {@link DefaultStateView}.
   */
  private DefaultStateView stateView;


  /**
   * The {@link DefaultTransitionView}.
   */
  private ArrayList < DefaultTransitionView > transitions;


  /**
   * Allocates a new {@link StateRemovedItem}.
   * 
   * @param model The {@link DefaultStateMachineModel}.
   * @param stateView The {@link DefaultStateView}.
   * @param transitions The {@link DefaultTransitionView}.
   */
  public StateRemovedItem ( DefaultStateMachineModel model,
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
   * @see RedoUndoItem#redo()
   */
  @Override
  public final void redo ()
  {
    this.model.removeState ( this.stateView, false );
  }


  /**
   * {@inheritDoc}
   * 
   * @see RedoUndoItem#undo()
   */
  @Override
  public final void undo ()
  {
    // add state
    this.stateView.addPort ();
    this.model.getMachine ().addState ( this.stateView.getState () );
    this.model.getJGTIGraph ().getGraphLayoutCache ().insert ( this.stateView );
    this.model.getStateViewList ().add ( this.stateView );

    // add transitions
    for ( DefaultTransitionView current : this.transitions )
    {
      if ( current.getSourceView ().getChildCount () == 0 )
      {
        current.getSourceView ().addPort ();
      }
      if ( current.getTargetView ().getChildCount () == 0 )
      {
        current.getTargetView ().addPort ();
      }
      this.model.createTransitionView ( current.getTransition (), current
          .getSourceView (), current.getTargetView (), false, false, true );
    }
  }
}
