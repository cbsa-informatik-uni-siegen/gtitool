package de.unisiegen.gtitool.ui.preferences.listener;


import java.util.EventListener;


/**
 * The listener interface for receiving exceptions changes.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface ExceptionsChangedListener extends EventListener
{

  /**
   * Invoked when the exceptions changed.
   */
  public void exceptionsChanged ();

}
