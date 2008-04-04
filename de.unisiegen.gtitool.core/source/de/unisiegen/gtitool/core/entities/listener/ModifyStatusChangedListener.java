package de.unisiegen.gtitool.core.entities.listener;


import java.util.EventListener;


/**
 * The listener interface for receiving modify changes.
 * 
 * @author Christian Fehler
 * @version $Id: ModifyStatusChangedListener.java 446 2008-01-18 15:37:55Z
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
