package de.unisiegen.gtitool.ui.redoundo;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Transition;


/**
 * Representation of {@link RedoUndoItem} for {@link Transition} removed action.
 */
public class MultiItem extends RedoUndoItem
{

  /**
   * List of {@link RedoUndoItem}s.
   */
  private ArrayList < RedoUndoItem > itemList = new ArrayList < RedoUndoItem > ();


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.redoundo.RedoUndoItem#redo()
   */
  @Override
  public void redo ()
  {
    for ( RedoUndoItem current : this.itemList )
    {
      current.redo ();
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
    for ( RedoUndoItem current : this.itemList )
    {
      current.undo ();
    }
  }
  
  /**
   * Add a new item to this {@link RedoUndoItem} list.
   *
   * @param item The new {@link RedoUndoItem}.
   */
  public void addItem(RedoUndoItem item){
    this.itemList.add ( item );
  }

}
