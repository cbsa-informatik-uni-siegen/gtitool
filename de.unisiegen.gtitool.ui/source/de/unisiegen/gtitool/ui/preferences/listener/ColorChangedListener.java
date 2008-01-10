package de.unisiegen.gtitool.ui.preferences.listener;


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
   * @param newColor The new color of the active {@link State}.
   */
  public void colorChangedActiveState ( Color newColor );


  /**
   * Invoked when the color of the active {@link Transition} changed.
   * 
   * @param newColor The new color of the active {@link Transition}.
   */
  public void colorChangedActiveTransition ( Color newColor );


  /**
   * Invoked when the color of the error {@link State} changed.
   * 
   * @param newColor The new color of the error {@link State}.
   */
  public void colorChangedErrorState ( Color newColor );


  /**
   * Invoked when the color of the error {@link Symbol} changed.
   * 
   * @param newColor The new color of the error {@link Symbol}.
   */
  public void colorChangedErrorSymbol ( Color newColor );


  /**
   * Invoked when the color of the error {@link Transition} changed.
   * 
   * @param newColor The new color of the error {@link Transition}.
   */
  public void colorChangedErrorTransition ( Color newColor );


  /**
   * Invoked when the color of the parser highlighting changed.
   * 
   * @param newColor The new color of the parser highlighting.
   */
  public void colorChangedParserHighlighting ( Color newColor );


  /**
   * Invoked when the color of the parser {@link State} changed.
   * 
   * @param newColor The new color of the parser {@link State}.
   */
  public void colorChangedParserState ( Color newColor );


  /**
   * Invoked when the color of the parser {@link Symbol} changed.
   * 
   * @param newColor The new color of the parser {@link Symbol}.
   */
  public void colorChangedParserSymbol ( Color newColor );


  /**
   * Invoked when the color of the parser warning changed.
   * 
   * @param newColor The new color of the parser warning.
   */
  public void colorChangedParserWarning ( Color newColor );


  /**
   * Invoked when the color of the selected {@link State} changed.
   * 
   * @param newColor The new color of the selected {@link State}.
   */
  public void colorChangedSelectedState ( Color newColor );


  /**
   * Invoked when the color of the start {@link State} changed.
   * 
   * @param newColor The new color of the start {@link State}.
   */
  public void colorChangedStartState ( Color newColor );


  /**
   * Invoked when the color of the {@link State} changed.
   * 
   * @param newColor The new color of the {@link State}.
   */
  public void colorChangedState ( Color newColor );


  /**
   * Invoked when the color of the {@link Symbol} changed.
   * 
   * @param newColor The new color of the {@link Symbol}.
   */
  public void colorChangedSymbol ( Color newColor );


  /**
   * Invoked when the color of the {@link Transition} changed.
   * 
   * @param newColor The new color of the {@link Transition}.
   */
  public void colorChangedTransition ( Color newColor );
}
