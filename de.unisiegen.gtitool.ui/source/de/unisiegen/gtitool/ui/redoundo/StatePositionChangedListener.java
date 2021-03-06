package de.unisiegen.gtitool.ui.redoundo;


import java.util.EventListener;

import de.unisiegen.gtitool.ui.jgraph.DefaultStateView;


/**
 * The listener interface for state position changes.
 * 
 * @author Benjamin Mies
 * @version $Id: StatePositionChangedListener.java 446 2008-01-18 15:37:55Z
 *          fehler $
 */
public interface StatePositionChangedListener extends EventListener
{

  /**
   * Invoked when the state position changed.
   * 
   * @param stateView The {@link DefaultStateView}.
   * @param oldX The old x value.
   * @param oldY The old y value.
   * @param newX The new x value.
   * @param newY The new y value.
   */
  public void statePositionChanged ( DefaultStateView stateView, double oldX,
      double oldY, double newX, double newY );
}
