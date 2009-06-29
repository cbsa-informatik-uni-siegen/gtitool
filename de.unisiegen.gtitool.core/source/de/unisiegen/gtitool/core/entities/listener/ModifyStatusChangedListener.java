package de.unisiegen.gtitool.core.entities.listener;


import java.util.EventListener;


/**
 * The listener interface for receiving modify status changes.
 * 
 * @author Christian Fehler
 * @version $Id: ModifyStatusChangedListener.java 1372 2008-10-30 08:36:20Z
 *          fehler $
 */
public interface ModifyStatusChangedListener extends EventListener
{

  /**
   * Invoked when the modify status changed.
   * 
   * @param modified True if the status is modified, otherwise false.
   */
  public void modifyStatusChanged ( boolean modified );
}
