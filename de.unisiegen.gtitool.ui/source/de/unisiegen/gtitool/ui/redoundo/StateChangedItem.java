package de.unisiegen.gtitool.ui.redoundo;


import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.ui.jgraph.JGTIGraph;


/**
 * Representation of {@link RedoUndoItem} for {@link State} added action.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public final class StateChangedItem extends RedoUndoItem
{

  /**
   * The {@link State}.
   */
  private State state;


  /**
   * The {@link JGTIGraph}.
   */
  private JGTIGraph jGTIGraph;


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
   * Allocates a new {@link StateChangedItem}.
   * 
   * @param jGTIGraph The {@link JGTIGraph}.
   * @param state The {@link State}.
   * @param oldName The old state name.
   * @param oldStartState The old start state flag.
   * @param oldFinalState The old final state flag.
   */
  public StateChangedItem ( JGTIGraph jGTIGraph, State state, String oldName,
      boolean oldStartState, boolean oldFinalState )
  {
    super ();
    this.jGTIGraph = jGTIGraph;
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
   * @see RedoUndoItem#redo()
   */
  @Override
  public final void redo ()
  {
    try
    {
      this.state.setName ( this.newName );
      this.jGTIGraph.getGraphLayoutCache ().valueForCellChanged ( this.state,
          this.newName );
      this.state.setStartState ( this.newStartState );
      this.state.setFinalState ( this.newFinalState );
    }
    catch ( StateException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see RedoUndoItem#undo()
   */
  @Override
  public final void undo ()
  {
    try
    {
      this.state.setName ( this.oldName );
      this.jGTIGraph.getGraphLayoutCache ().valueForCellChanged ( this.state,
          this.oldName );
      this.state.setStartState ( this.oldStartState );
      this.state.setFinalState ( this.oldFinalState );
    }
    catch ( StateException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }
}
