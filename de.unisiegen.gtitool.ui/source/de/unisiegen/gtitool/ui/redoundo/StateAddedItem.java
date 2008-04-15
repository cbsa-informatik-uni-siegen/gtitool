package de.unisiegen.gtitool.ui.redoundo;


import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultStateView;
import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultTransitionView;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;


/**
 * Representation of {@link RedoUndoItem} for {@link State} added action.
 */
public class StateAddedItem extends RedoUndoItem
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
  private DefaultTransitionView transition;


  /**
   * Allocate a new {@link StateAddedItem}.
   * 
   * @param model The {@link DefaultMachineModel}.
   * @param stateView The {@link DefaultStateView}.
   * @param transition The {@link DefaultTransitionView}.
   */
  public StateAddedItem ( DefaultMachineModel model,
      DefaultStateView stateView, DefaultTransitionView transition )
  {
    super ();
    this.model = model;
    this.stateView = stateView;
    this.transition = transition;
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.redoundo.RedoUndoItem#redo()
   */
  @Override
  public void redo ()
  {
    this.stateView.addPort ();
    this.model.getMachine ().addState ( this.stateView.getState () );
    this.model.getJGraph ().getGraphLayoutCache ().insert ( this.stateView );
    this.model.getStateViewList ().add ( this.stateView );

    // add transitions
    if ( this.transition != null )
    {
      this.model.createTransitionView ( this.transition.getTransition (),
          this.transition.getSourceView (), this.transition.getTargetView (),
          false, false, true );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.redoundo.RedoUndoItem#undo()
   */
  @Override
  public void undo ()
  {
    this.model.removeState ( this.stateView, false );
  }

}
