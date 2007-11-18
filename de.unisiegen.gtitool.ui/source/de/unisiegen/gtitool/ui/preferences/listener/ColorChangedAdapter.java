package de.unisiegen.gtitool.ui.preferences.listener;


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
   * Invoked when the color of the active {@link State} changed.
   * 
   * @param pNewColor The new color of the active {@link State}.
   */
  public void colorChangedActiveState ( @SuppressWarnings ( "unused" )
  Color pNewColor )
  {
    // Override this method if needed.
  }


  /**
   * Invoked when the color of the error {@link State} changed.
   * 
   * @param pNewColor The new color of the error {@link State}.
   */
  public void colorChangedErrorState ( @SuppressWarnings ( "unused" )
  Color pNewColor )
  {
    // Override this method if needed.
  }


  /**
   * Invoked when the color of the error {@link Symbol} changed.
   * 
   * @param pNewColor The new color of the error {@link Symbol}.
   */
  public void colorChangedErrorSymbol ( @SuppressWarnings ( "unused" )
  Color pNewColor )
  {
    // Override this method if needed.
  }


  /**
   * Invoked when the color of the error {@link Transition} changed.
   * 
   * @param pNewColor The new color of the error {@link Transition}.
   */
  public void colorChangedErrorTransition ( @SuppressWarnings ( "unused" )
  Color pNewColor )
  {
    // Override this method if needed.
  }


  /**
   * Invoked when the color of the parser {@link State} changed.
   * 
   * @param pNewColor The new color of the parser {@link State}.
   */
  public void colorChangedParserState ( @SuppressWarnings ( "unused" )
  Color pNewColor )
  {
    // Override this method if needed.
  }


  /**
   * Invoked when the color of the parser {@link Symbol} changed.
   * 
   * @param pNewColor The new color of the parser {@link Symbol}.
   */
  public void colorChangedParserSymbol ( @SuppressWarnings ( "unused" )
  Color pNewColor )
  {
    // Override this method if needed.
  }


  /**
   * Invoked when the color of the parser warning changed.
   * 
   * @param pNewColor The new color of the parser warning.
   */
  public void colorChangedParserWarning ( @SuppressWarnings ( "unused" )
  Color pNewColor )
  {
    // Override this method if needed.
  }


  /**
   * Invoked when the color of the selected {@link State} changed.
   * 
   * @param pNewColor The new color of the selected {@link State}.
   */
  public void colorChangedSelectedState ( @SuppressWarnings ( "unused" )
  Color pNewColor )
  {
    // Override this method if needed.
  }


  /**
   * Invoked when the color of the start {@link State} changed.
   * 
   * @param pNewColor The new color of the start {@link State}.
   */
  public void colorChangedStartState ( @SuppressWarnings ( "unused" )
  Color pNewColor )
  {
    // Override this method if needed.
  }


  /**
   * Invoked when the color of the {@link State} changed.
   * 
   * @param pNewColor The new color of the {@link State}.
   */
  public void colorChangedState ( @SuppressWarnings ( "unused" )
  Color pNewColor )
  {
    // Override this method if needed.
  }


  /**
   * Invoked when the color of the {@link Symbol} changed.
   * 
   * @param pNewColor The new color of the {@link Symbol}.
   */
  public void colorChangedSymbol ( @SuppressWarnings ( "unused" )
  Color pNewColor )
  {
    // Override this method if needed.
  }


  /**
   * Invoked when the color of the {@link Transition} changed.
   * 
   * @param pNewColor The new color of the {@link Transition}.
   */
  public void colorChangedTransition ( @SuppressWarnings ( "unused" )
  Color pNewColor )
  {
    // Override this method if needed.
  }
}
