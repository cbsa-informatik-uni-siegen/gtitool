package de.unisiegen.gtitool.ui.preferences.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.ui.preferences.item.ZoomFactorItem;


/**
 * The listener interface for receiving zoom factor changes.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface ZoomFactorChangedListener extends EventListener
{

  /**
   * Invoked when the zoom factor changed.
   * 
   * @param pNewValue The new {@link ZoomFactorItem}.
   */
  public void zoomFactorChanged ( ZoomFactorItem pNewValue );

}
