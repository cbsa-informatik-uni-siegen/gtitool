package de.unisiegen.gtitool.ui.redoundo;


import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.ui.jgraph.DefaultTransitionView;
import de.unisiegen.gtitool.ui.model.DefaultStateMachineModel;


/**
 * Representation of {@link RedoUndoItem} for {@link Transition} removed action.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public final class TransitionRemovedItem extends RedoUndoItem
{

  /**
   * The {@link DefaultStateMachineModel}.
   */
  private DefaultStateMachineModel model;


  /**
   * The {@link DefaultTransitionView}.
   */
  private DefaultTransitionView transitionView;


  /**
   * Allocates a new {@link TransitionRemovedItem}.
   * 
   * @param model The {@link DefaultStateMachineModel}.
   * @param transitionView The {@link DefaultTransitionView}.
   */
  public TransitionRemovedItem ( DefaultStateMachineModel model,
      DefaultTransitionView transitionView )
  {
    super ();
    this.model = model;
    this.transitionView = transitionView;
  }


  /**
   * {@inheritDoc}
   * 
   * @see RedoUndoItem#redo()
   */
  @Override
  public void redo ()
  {
    this.model.removeTransition ( this.transitionView, false );
  }


  /**
   * {@inheritDoc}
   * 
   * @see RedoUndoItem#undo()
   */
  @Override
  public void undo ()
  {
    this.model.createTransitionView ( this.transitionView.getTransition (),
        this.transitionView.getSourceView (), this.transitionView
            .getTargetView (), false, false, true );
  }
}
