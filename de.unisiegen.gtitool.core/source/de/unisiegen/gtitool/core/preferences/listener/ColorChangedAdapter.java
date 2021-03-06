package de.unisiegen.gtitool.core.preferences.listener;


import java.awt.Color;


/**
 * An abstract adapter class for receiving color changes.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class ColorChangedAdapter implements ColorChangedListener
{

  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChanged()
   */
  public void colorChanged ()
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedNonterminalSymbol(Color)
   */
  public void colorChangedNonterminalSymbol (
      @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedRegexMarkedNode(java.awt.Color)
   */
  public void colorChangedRegexMarkedNode (
      @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedRegexSelectedNode(java.awt.Color)
   */
  public void colorChangedRegexSelectedNode (
      @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedNonterminalSymbolError(Color)
   */
  public void colorChangedNonterminalSymbolError (
      @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedNonterminalSymbolHighlight(Color)
   */
  public void colorChangedNonterminalSymbolHighlight (
      @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedParserError(Color)
   */
  public void colorChangedParserError (
      @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedParserHighlighting(Color)
   */
  public void colorChangedParserHighlighting (
      @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedParserKeyword(Color)
   */
  public void colorChangedParserKeyword (
      @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedParserWarning(Color)
   */
  public void colorChangedParserWarning (
      @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedProductionError(Color)
   */
  public void colorChangedProductionError (
      @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedProductionHighlight(Color)
   */
  public void colorChangedProductionHighlight (
      @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedRegexPosition(Color)
   */
  public void colorChangedRegexPosition (
      @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedRegexSymbol(Color)
   */
  public void colorChangedRegexSymbol (
      @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.core.preferences.listener.ColorChangedListener#colorChangedRegexComment(java.awt.Color)
   */
  public void colorChangedRegexComment (
      @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedRegexToken(Color)
   */
  public void colorChangedRegexToken (
      @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedRegexToolTip(Color)
   */
  public void colorChangedRegexToolTip (
      @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedStartNonterminalSymbol(Color)
   */
  public void colorChangedStartNonterminalSymbol (
      @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedState(Color)
   */
  public void colorChangedState ( @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedStateActive(Color)
   */
  public void colorChangedStateActive (
      @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedStateBackground(Color)
   */
  public void colorChangedStateBackground (
      @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedStateError(Color)
   */
  public void colorChangedStateError (
      @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedStateFinal(Color)
   */
  public void colorChangedStateFinal (
      @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedStateSelected(Color)
   */
  public void colorChangedStateSelected (
      @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedStateStart(Color)
   */
  public void colorChangedStateStart (
      @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedSymbol(Color)
   */
  public void colorChangedSymbol ( @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedSymbolActive(Color)
   */
  public void colorChangedSymbolActive (
      @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedSymbolError(Color)
   */
  public void colorChangedSymbolError (
      @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedTerminalSymbol(Color)
   */
  public void colorChangedTerminalSymbol (
      @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedTerminalSymbolError(Color)
   */
  public void colorChangedTerminalSymbolError (
      @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedTransition(Color)
   */
  public void colorChangedTransition (
      @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedTransitionActive(Color)
   */
  public void colorChangedTransitionActive (
      @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedTransitionError(Color)
   */
  public void colorChangedTransitionError (
      @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedTransitionSelected(Color)
   */
  public void colorChangedTransitionSelected (
      @SuppressWarnings ( "unused" ) Color newColor )
  {
    // Override this method if needed.
  }
}
