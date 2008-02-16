package de.unisiegen.gtitool.core.preferences.listener;


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
   * Invoked when the color of the parser error changed.
   * 
   * @param newColor The new color of the error warning.
   */
  public void colorChangedParserError ( Color newColor );


  /**
   * Invoked when the color of the parser highlighting changed.
   * 
   * @param newColor The new color of the parser highlighting.
   */
  public void colorChangedParserHighlighting ( Color newColor );


  /**
   * Invoked when the color of the parser warning changed.
   * 
   * @param newColor The new color of the parser warning.
   */
  public void colorChangedParserWarning ( Color newColor );


  /**
   * Invoked when the color of the {@link State} changed.
   * 
   * @param newColor The new color of the {@link State}.
   */
  public void colorChangedState ( Color newColor );


  /**
   * Invoked when the color of the active {@link State} changed.
   * 
   * @param newColor The new color of the active {@link State}.
   */
  public void colorChangedStateActive ( Color newColor );


  /**
   * Invoked when the background color of the {@link State} changed.
   * 
   * @param newColor The new color of the {@link State}.
   */
  public void colorChangedStateBackground ( Color newColor );


  /**
   * Invoked when the color of the error {@link State} changed.
   * 
   * @param newColor The new color of the error {@link State}.
   */
  public void colorChangedStateError ( Color newColor );


  /**
   * Invoked when the color of the selected {@link State} changed.
   * 
   * @param newColor The new color of the selected {@link State}.
   */
  public void colorChangedStateSelected ( Color newColor );


  /**
   * Invoked when the color of the start {@link State} changed.
   * 
   * @param newColor The new color of the start {@link State}.
   */
  public void colorChangedStateStart ( Color newColor );


  /**
   * Invoked when the color of the {@link Symbol} changed.
   * 
   * @param newColor The new color of the {@link Symbol}.
   */
  public void colorChangedSymbol ( Color newColor );


  /**
   * Invoked when the color of the active {@link Symbol} changed.
   * 
   * @param newColor The new color of the active {@link Symbol}.
   */
  public void colorChangedSymbolActive ( Color newColor );


  /**
   * Invoked when the color of the error {@link Symbol} changed.
   * 
   * @param newColor The new color of the error {@link Symbol}.
   */
  public void colorChangedSymbolError ( Color newColor );


  /**
   * Invoked when the color of the {@link Transition} changed.
   * 
   * @param newColor The new color of the {@link Transition}.
   */
  public void colorChangedTransition ( Color newColor );


  /**
   * Invoked when the color of the active {@link Transition} changed.
   * 
   * @param newColor The new color of the active {@link Transition}.
   */
  public void colorChangedTransitionActive ( Color newColor );


  /**
   * Invoked when the color of the error {@link Transition} changed.
   * 
   * @param newColor The new color of the error {@link Transition}.
   */
  public void colorChangedTransitionError ( Color newColor );


  /**
   * Invoked when the color of the selected {@link Transition} changed.
   * 
   * @param newColor The new color of the selected {@link Transition}.
   */
  public void colorChangedTransitionSelected ( Color newColor );
}
