package de.unisiegen.gtitool.ui.preferences.listener;


import java.util.EventListener;

import de.unisiegen.gtitool.core.machines.pda.PDA;
import de.unisiegen.gtitool.ui.preferences.item.PDAModeItem;


/**
 * The listener interface for receiving {@link PDA} mode changes.
 * 
 * @author Christian Fehler
 * @version $Id: ZoomFactorChangedListener.java 430 2008-01-11 10:40:45Z fehler
 *          $
 */
public interface PDAModeChangedListener extends EventListener
{

  /**
   * Invoked when the {@link PDA} mode changed.
   * 
   * @param newValue The new {@link PDAModeItem}.
   */
  public void pdaModeChanged ( PDAModeItem newValue );

}
