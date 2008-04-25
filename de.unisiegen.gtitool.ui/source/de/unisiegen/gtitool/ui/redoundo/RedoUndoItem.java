package de.unisiegen.gtitool.ui.redoundo;


/**
 * This class represents a redo or undo step.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public abstract class RedoUndoItem
{

  /**
   * Allocates a new {@link RedoUndoItem}.
   */
  public RedoUndoItem ()
  {
    // Do nothing
  }


  /**
   * Redos the action represented by this {@link RedoUndoItem}.
   */
  public abstract void redo ();


  /**
   * Undos the action represented by this {@link RedoUndoItem}.
   */
  public abstract void undo ();
}
