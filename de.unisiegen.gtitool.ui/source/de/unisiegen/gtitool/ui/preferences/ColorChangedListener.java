package de.unisiegen.gtitool.ui.preferences;


import java.awt.Color;
import java.util.EventListener;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;


/**
 * The listener interface for receiving color changes.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface ColorChangedListener extends EventListener
{

  /**
   * Invoked when the color of the active {@link State} changed.
   * 
   * @param pNewColor The new color of the active {@link State}.
   */
  public void colorChangedActiveState ( Color pNewColor );


  /**
   * Invoked when the color of the final {@link State} changed.
   * 
   * @param pNewColor The new color of the final {@link State}.
   */
  public void colorChangedFinalState ( Color pNewColor );


  /**
   * Invoked when the color of the selected {@link State} changed.
   * 
   * @param pNewColor The new color of the selected {@link State}.
   */
  public void colorChangedSelectedState ( Color pNewColor );


  /**
   * Invoked when the color of the start {@link State} changed.
   * 
   * @param pNewColor The new color of the start {@link State}.
   */
  public void colorChangedStartState ( Color pNewColor );


  /**
   * Invoked when the color of the {@link State} changed.
   * 
   * @param pNewColor The new color of the {@link State}.
   */
  public void colorChangedState ( Color pNewColor );


  /**
   * Invoked when the color of the {@link Symbol} changed.
   * 
   * @param pNewColor The new color of the {@link Symbol}.
   */
  public void colorChangedSymbol ( Color pNewColor );


  /**
   * Invoked when the color of the {@link Transition} changed.
   * 
   * @param pNewColor The new color of the {@link Transition}.
   */
  public void colorChangedTransition ( Color pNewColor );
}
