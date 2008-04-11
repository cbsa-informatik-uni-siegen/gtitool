package de.unisiegen.gtitool.ui.redoundo;


import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultTransitionView;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;


/**
 * Representation of {@link RedoUndoItem} for {@link Transition} removed action.
 */
public class TransitionRemovedItem extends RedoUndoItem
{

  /**
   * The {@link DefaultMachineModel}.
   */
  private DefaultMachineModel model;


  /**
   * The {@link DefaultTransitionView}.
   */
  private DefaultTransitionView transitionView;


  /**
   * Allocate a new {@link TransitionRemovedItem}.
   * 
   * @param model The {@link DefaultMachineModel}.
   * @param transitionView The {@link DefaultTransitionView}.
   */
  public TransitionRemovedItem ( DefaultMachineModel model,
      DefaultTransitionView transitionView )
  {
    super ();
    this.model = model;
    this.transitionView = transitionView;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.redoundo.RedoUndoItem#redo()
   */
  @Override
  public void redo ()
  {
    this.model.removeTransition ( this.transitionView, false );
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.redoundo.RedoUndoItem#undo()
   */
  @Override
  public void undo ()
  {
    this.model.createTransitionView ( this.transitionView.getTransition (),
        this.transitionView.getSourceView (), this.transitionView
            .getTargetView (), false );
  }

}
