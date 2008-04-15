package de.unisiegen.gtitool.ui.redoundo;


import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultStateView;
import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultTransitionView;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;


/**
 * Representation of {@link RedoUndoItem} for {@link Transition} removed action.
 */
public class TransitionAddedItem extends RedoUndoItem
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
   * The {@link DefaultStateView}.
   */
  private DefaultStateView stateView;


  /**
   * Allocate a new {@link TransitionAddedItem}.
   * 
   * @param model The {@link DefaultMachineModel}.
   * @param transitionView The {@link DefaultTransitionView}.
   * @param stateView The {@link DefaultStateView}.
   */
  public TransitionAddedItem ( DefaultMachineModel model,
      DefaultTransitionView transitionView, DefaultStateView stateView )
  {
    super ();
    this.model = model;
    this.transitionView = transitionView;
    this.stateView = stateView;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.redoundo.RedoUndoItem#redo()
   */
  @Override
  public void redo ()
  {
    if ( this.stateView != null )
    {
      this.stateView.addPort ();
      this.model.getMachine ().addState ( this.stateView.getState () );
      this.model.getJGraph ().getGraphLayoutCache ().insert ( this.stateView );
      this.model.getStateViewList ().add ( this.stateView );
    }

    this.model.createTransitionView ( this.transitionView.getTransition (),
        this.transitionView.getSourceView (), this.transitionView
            .getTargetView (), true, false, true );

  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.redoundo.RedoUndoItem#undo()
   */
  @Override
  public void undo ()
  {
    this.model.removeTransition ( this.transitionView, false );
    if ( this.stateView != null )
    {
      this.model.removeState ( this.stateView, false );
    }
  }

}
