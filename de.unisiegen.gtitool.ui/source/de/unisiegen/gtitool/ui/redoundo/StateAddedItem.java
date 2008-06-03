package de.unisiegen.gtitool.ui.redoundo;


import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;
import de.unisiegen.gtitool.ui.jgraph.DefaultTransitionView;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;


/**
 * Representation of {@link RedoUndoItem} for {@link State} added action.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public final class StateAddedItem extends RedoUndoItem
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
   * Allocates a new {@link StateAddedItem}.
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
   * @see RedoUndoItem#redo()
   */
  @Override
  public final void redo ()
  {
    this.stateView.addPort ();
    this.model.getMachine ().addState ( this.stateView.getState () );
    this.model.getJGTIGraph ().getGraphLayoutCache ().insert ( this.stateView );
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
   * @see RedoUndoItem#undo()
   */
  @Override
  public void undo ()
  {
    this.model.removeState ( this.stateView, false );
  }
}
