package de.unisiegen.gtitool.ui.preferences;


import java.util.EventListener;


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
   * @param pNewValue The new {@link ZoomFactor}.
   */
  public void zoomFactorChanged ( ZoomFactor pNewValue );

}
