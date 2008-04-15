package de.unisiegen.gtitool.ui.redoundo;


import java.util.ArrayList;

import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultStateView;
import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultTransitionView;


/**
 * This class represents a redo or undo step.
 * 
 * @author Benjamin Mies
 */
public abstract class RedoUndoItem
{

  /**
   * The changed state view
   */
  private DefaultStateView stateView;


  /**
   * List of the changed transition views
   */
  private ArrayList < DefaultTransitionView > transitions = new ArrayList < DefaultTransitionView > ();


  /**
   * Signals what was done
   */
  private REDO_UNDO_ACTION action;


  /** Signals the redo/undo action */
  public enum REDO_UNDO_ACTION
  {
    /**
     * A state was added.
     */
    STATE_ADDED,

    /**
     * A Transiton was added.
     */
    TRANSITION_ADDED,

    /**
     * A state was removed.
     */
    STATE_REMOVED,

    /**
     * A Transiton was removed.
     */
    TRANSITION_REMOVED;
  }


  /**
   * Allocate a new {@link RedoUndoItem}.
   */
  public RedoUndoItem ()
  {
    // Nothing to do
  }


  /**
   * Allocate a new {@link RedoUndoItem}
   * 
   * @param action Signals what was done
   * @param stateView The changed state view
   */
  public RedoUndoItem ( REDO_UNDO_ACTION action, DefaultStateView stateView )
  {
    this.action = action;
    this.stateView = stateView;
  }


  /**
   * Allocate a new {@link RedoUndoItem}
   * 
   * @param action Signals what was done
   * @param stateView The changed state view
   * @param transitions List of changed transition views
   */
  public RedoUndoItem ( REDO_UNDO_ACTION action, DefaultStateView stateView,
      ArrayList < DefaultTransitionView > transitions )
  {
    this.action = action;
    this.stateView = stateView;
    this.transitions = transitions;
  }


  /**
   * Allocate a new {@link RedoUndoItem}
   * 
   * @param action Signals what was done
   * @param transitions
   */
  public RedoUndoItem ( REDO_UNDO_ACTION action,
      DefaultTransitionView transitions )
  {
    this.action = action;
    this.transitions.add ( transitions );
  }


  /**
   * Get the transitions for this redo / undo item
   * 
   * @return the list of changed transition views
   */
  public ArrayList < DefaultTransitionView > getTransitions ()
  {
    return this.transitions;
  }


  /**
   * Get the action for this redo / undo item
   * 
   * @return the action
   */
  public REDO_UNDO_ACTION getAction ()
  {
    return this.action;
  }


  /**
   * Get the stateview for this redo / undo item
   * 
   * @return the changed state view
   */
  public DefaultStateView getStateView ()
  {
    return this.stateView;
  }


  /**
   * Redo the action represented by this item.
   */
  public abstract void redo ();


  /**
   * Undo the action represented by this item.
   */
  public abstract void undo ();

}
