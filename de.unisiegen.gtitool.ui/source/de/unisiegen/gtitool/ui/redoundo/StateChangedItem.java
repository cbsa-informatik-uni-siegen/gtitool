package de.unisiegen.gtitool.ui.redoundo;


import org.jgraph.JGraph;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultStateView;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;


/**
 * Representation of {@link RedoUndoItem} for {@link State} added action.
 */
public class StateChangedItem extends RedoUndoItem
{

  /**
   * The {@link State}.
   */
  private State state;


  /**
   * The old {@link State}.
   */
  private State oldState;


  /**
   * The new {@link State}.
   */
  private State newState;


  /**
   * The {@link JGraph}.
   */
  private JGraph graph;


  /**
   * Allocate a new {@link StateChangedItem}.
   * 
   * @param graph The {@link JGraph}.
   * @param oldState The {@link DefaultMachineModel}.
   * @param newState The {@link DefaultStateView}.
   */
  public StateChangedItem ( JGraph graph, State oldState, State newState )
  {
    super ();
    this.graph = graph;
    this.oldState = oldState;
    this.state = newState;
    this.newState = newState.clone ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.redoundo.RedoUndoItem#redo()
   */
  @Override
  public void redo ()
  {
    try
    {
      this.state.setName ( this.newState.getName () );
      this.graph.getGraphLayoutCache ().valueForCellChanged ( this.state,
          this.newState.getName () );
      this.state.setStartState ( this.newState.isStartState () );
      this.state.setFinalState ( this.newState.isFinalState () );
    }
    catch ( StateException exc )
    {
      // Do nothing
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
    try
    {
      this.state.setName ( this.oldState.getName () );
      this.graph.getGraphLayoutCache ().valueForCellChanged ( this.state,
          this.oldState.getName () );
      this.state.setStartState ( this.oldState.isStartState () );
      this.state.setFinalState ( this.oldState.isFinalState () );
    }
    catch ( StateException exc )
    {
      // Do nothing
    }
  }

}
