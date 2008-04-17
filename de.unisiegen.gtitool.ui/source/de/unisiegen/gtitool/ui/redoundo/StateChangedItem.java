package de.unisiegen.gtitool.ui.redoundo;


import org.jgraph.JGraph;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.exceptions.state.StateException;


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
   * The {@link JGraph}.
   */
  private JGraph graph;


  /**
   * The old state name.
   */
  private String oldName;


  /**
   * The old start state flag.
   */
  private boolean oldStartState;


  /**
   * The old final state flag.
   */
  private boolean oldFinalState;


  /**
   * The new state name.
   */
  private String newName;


  /**
   * The new start state flag.
   */
  private boolean newStartState;


  /**
   * The new final state flag.
   */
  private boolean newFinalState;


  /**
   * Allocate a new {@link StateChangedItem}.
   * 
   * @param graph The {@link JGraph}.
   * @param state The {@link State}.
   * @param oldName The old state name.
   * @param oldStartState The old start state flag.
   * @param oldFinalState The old final state flag.
   */
  public StateChangedItem ( JGraph graph,  State state,
      String oldName, boolean oldStartState, boolean oldFinalState )
  {
    super ();
    this.graph = graph;
    this.state = state;
    this.oldName = oldName;
    this.oldStartState = oldStartState;
    this.oldFinalState = oldFinalState;
    this.newName = state.getName ();
    this.newStartState = state.isStartState ();
    this.newFinalState = state.isFinalState ();

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
      this.state.setName ( this.newName );
      this.graph.getGraphLayoutCache ().valueForCellChanged ( this.state,
          this.newName );
      this.state.setStartState ( this.newStartState );
      this.state.setFinalState ( this.newFinalState );
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
      this.state.setName ( this.oldName );
      this.graph.getGraphLayoutCache ().valueForCellChanged ( this.state,
          this.oldName );
      this.state.setStartState ( this.oldStartState );
      this.state.setFinalState ( this.oldFinalState );
    }
    catch ( StateException exc )
    {
      // Do nothing
    }
  }

}
