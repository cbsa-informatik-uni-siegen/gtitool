package de.unisiegen.gtitool.ui.exchange;


import java.util.EventListener;


/**
 * The listener interface for {@link Network} connected events.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface NetworkConnectedListener extends EventListener
{

  /**
   * Invoked when the {@link Network} is connected.
   */
  public void networkConnected ();
}
