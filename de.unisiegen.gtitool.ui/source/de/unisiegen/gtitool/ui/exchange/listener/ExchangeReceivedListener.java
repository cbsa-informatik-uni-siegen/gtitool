package de.unisiegen.gtitool.ui.exchange.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.ui.exchange.Exchange;


/**
 * The listener interface for receiving {@link Exchange}s.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface ExchangeReceivedListener extends EventListener
{

  /**
   * Invoked when a {@link Exchange} was received.
   * 
   * @param exchange The received {@link Exchange}.
   */
  public void exchangeReceived ( Exchange exchange );
}
