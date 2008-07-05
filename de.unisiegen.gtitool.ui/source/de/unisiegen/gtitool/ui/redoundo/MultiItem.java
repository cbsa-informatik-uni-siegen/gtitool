package de.unisiegen.gtitool.ui.redoundo;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Transition;


/**
 * Representation of {@link RedoUndoItem} for {@link Transition} removed action.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public final class MultiItem extends RedoUndoItem
{

  /**
   * List of {@link RedoUndoItem}s.
   */
  private ArrayList < RedoUndoItem > itemList = new ArrayList < RedoUndoItem > ();


  /**
   * Add a new item to this {@link RedoUndoItem} list.
   * 
   * @param item The new {@link RedoUndoItem}.
   */
  public final void addItem ( RedoUndoItem item )
  {
    this.itemList.add ( item );
  }


  /**
   * {@inheritDoc}
   * 
   * @see RedoUndoItem#redo()
   */
  @Override
  public final void redo ()
  {
    for ( RedoUndoItem current : this.itemList )
    {
      current.redo ();
    }
  }


  /**
   * Return the size of this item.
   * 
   * @return the size of this item.
   */
  public int size ()
  {
    return this.itemList.size ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see RedoUndoItem#undo()
   */
  @Override
  public final void undo ()
  {
    for ( RedoUndoItem current : this.itemList )
    {
      current.undo ();
    }
  }
}
