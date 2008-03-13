package de.unisiegen.gtitool.ui.exchange.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.ui.exchange.Exchange;


/**
 * The listener interface for {@link Exchange} finished events.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface ExchangeFinishedListener extends EventListener
{

  /**
   * Invoked when the {@link Exchange} is finished.
   */
  public void exchangeFinished ();
}
