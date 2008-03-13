package de.unisiegen.gtitool.ui.exchange.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.ui.exchange.Network;


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
