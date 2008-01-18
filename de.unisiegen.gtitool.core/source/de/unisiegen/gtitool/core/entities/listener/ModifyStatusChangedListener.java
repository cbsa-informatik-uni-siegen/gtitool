package de.unisiegen.gtitool.core.entities.listener;


import java.util.EventListener;


/**
 * The listener interface for receiving modify changes.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface ModifyStatusChangedListener extends EventListener
{

  /**
   * Invoked when the modify status changed.
   * 
   * @param newModifyStatus The new modify status.
   */
  public void modifyStatusChanged ( boolean newModifyStatus );

}
