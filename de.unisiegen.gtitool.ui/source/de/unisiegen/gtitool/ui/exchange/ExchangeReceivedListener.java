package de.unisiegen.gtitool.ui.exchange;


import java.util.EventListener;


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
