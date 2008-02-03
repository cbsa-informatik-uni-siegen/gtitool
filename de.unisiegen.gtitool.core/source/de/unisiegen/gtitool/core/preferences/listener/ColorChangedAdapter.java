package de.unisiegen.gtitool.core.preferences.listener;


import java.awt.Color;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;


/**
 * An abstract adapter class for receiving color changes.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class ColorChangedAdapter implements ColorChangedListener
{

  /**
   * Invoked when the color of the parser highlighting changed.
   * 
   * @param newColor The new color of the parser highlighting.
   */
  public void colorChangedParserHighlighting ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * Invoked when the color of the parser warning changed.
   * 
   * @param newColor The new color of the parser warning.
   */
  public void colorChangedParserWarning ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * Invoked when the color of the {@link State} changed.
   * 
   * @param newColor The new color of the {@link State}.
   */
  public void colorChangedState ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * Invoked when the color of the active {@link State} changed.
   * 
   * @param newColor The new color of the active {@link State}.
   */
  public void colorChangedStateActive ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * Invoked when the background color of the {@link State} changed.
   * 
   * @param newColor The new color of the {@link State}.
   */
  public void colorChangedStateBackground ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * Invoked when the color of the error {@link State} changed.
   * 
   * @param newColor The new color of the error {@link State}.
   */
  public void colorChangedStateError ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * Invoked when the color of the selected {@link State} changed.
   * 
   * @param newColor The new color of the selected {@link State}.
   */
  public void colorChangedStateSelected ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * Invoked when the color of the start {@link State} changed.
   * 
   * @param newColor The new color of the start {@link State}.
   */
  public void colorChangedStateStart ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * Invoked when the color of the {@link Symbol} changed.
   * 
   * @param newColor The new color of the {@link Symbol}.
   */
  public void colorChangedSymbol ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * Invoked when the color of the error {@link Symbol} changed.
   * 
   * @param newColor The new color of the error {@link Symbol}.
   */
  public void colorChangedSymbolError ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * Invoked when the color of the {@link Transition} changed.
   * 
   * @param newColor The new color of the {@link Transition}.
   */
  public void colorChangedTransition ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * Invoked when the color of the active {@link Transition} changed.
   * 
   * @param newColor The new color of the active {@link Transition}.
   */
  public void colorChangedTransitionActive ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * Invoked when the color of the error {@link Transition} changed.
   * 
   * @param newColor The new color of the error {@link Transition}.
   */
  public void colorChangedTransitionError ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * Invoked when the color of the selected {@link Transition} changed.
   * 
   * @param newColor The new color of the selected {@link Transition}.
   */
  public void colorChangedTransitionSelected ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }
}
