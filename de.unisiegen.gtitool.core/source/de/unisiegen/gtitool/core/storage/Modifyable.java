package de.unisiegen.gtitool.core.storage;


import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;


/**
 * The interface of all modify able objects.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface Modifyable
{

  /**
   * Adds the given {@link ModifyStatusChangedListener}.
   * 
   * @param listener The {@link ModifyStatusChangedListener}.
   */
  public void addModifyStatusChangedListener (
      ModifyStatusChangedListener listener );


  /**
   * Returns true if this {@link Modifyable} is modified, otherwise false.
   * 
   * @return True if this {@link Modifyable} is modified, otherwise false.
   */
  public boolean isModified ();


  /**
   * Removes the given {@link ModifyStatusChangedListener}.
   * 
   * @param listener The {@link ModifyStatusChangedListener}.
   */
  public void removeModifyStatusChangedListener (
      ModifyStatusChangedListener listener );


  /**
   * Resets the modify status of this {@link Modifyable}.
   */
  public void resetModify ();
}
