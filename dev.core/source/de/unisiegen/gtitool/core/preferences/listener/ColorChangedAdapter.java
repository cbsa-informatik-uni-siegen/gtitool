package de.unisiegen.gtitool.core.preferences.listener;


import java.awt.Color;


/**
 * An abstract adapter class for receiving color changes.
 * 
 * @author Christian Fehler
 * @version $Id: ColorChangedAdapter.java 1043 2008-06-27 00:09:58Z fehler $
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
  public void colorChangedNonterminalSymbol ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedNonterminalSymbolError(Color)
   */
  public void colorChangedNonterminalSymbolError (
      @SuppressWarnings ( "unused" )
      Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedParserError(Color)
   */
  public void colorChangedParserError ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedParserHighlighting(Color)
   */
  public void colorChangedParserHighlighting ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedParserKeyword(Color)
   */
  public void colorChangedParserKeyword ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedParserWarning(Color)
   */
  public void colorChangedParserWarning ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedProductionError(Color)
   */
  public void colorChangedProductionError ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedStartNonterminalSymbol(Color)
   */
  public void colorChangedStartNonterminalSymbol (
      @SuppressWarnings ( "unused" )
      Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedState(Color)
   */
  public void colorChangedState ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedStateActive(Color)
   */
  public void colorChangedStateActive ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedStateBackground(Color)
   */
  public void colorChangedStateBackground ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedStateError(Color)
   */
  public void colorChangedStateError ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * TODO
   * 
   * @param newColor
   * @see de.unisiegen.gtitool.core.preferences.listener.ColorChangedListener#colorChangedRegexToolTip(java.awt.Color)
   */
  public void colorChangedRegexToolTip ( Color newColor )
  {
    // Override this method if needed.
  }
  
  /**
   * TODO
   *
   * @param newColor
   * @see de.unisiegen.gtitool.core.preferences.listener.ColorChangedListener#colorChangedRegexPosition(java.awt.Color)
   */
  public void colorChangedRegexPosition ( Color newColor )
  {
  }
  /**
   * TODO
   *
   * @param newColor
   * @see de.unisiegen.gtitool.core.preferences.listener.ColorChangedListener#colorChangedRegexSymbol(java.awt.Color)
   */
  public void colorChangedRegexSymbol ( Color newColor )
  {
  }
  /**
   * TODO
   *
   * @param newColor
   * @see de.unisiegen.gtitool.core.preferences.listener.ColorChangedListener#colorChangedRegexToken(java.awt.Color)
   */
  public void colorChangedRegexToken ( Color newColor )
  {}

  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedStateFinal(Color)
   */
  public void colorChangedStateFinal ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedStateSelected(Color)
   */
  public void colorChangedStateSelected ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedStateStart(Color)
   */
  public void colorChangedStateStart ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedSymbol(Color)
   */
  public void colorChangedSymbol ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedSymbolActive(Color)
   */
  public void colorChangedSymbolActive ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedSymbolError(Color)
   */
  public void colorChangedSymbolError ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedTerminalSymbol(Color)
   */
  public void colorChangedTerminalSymbol ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedTerminalSymbolError(Color)
   */
  public void colorChangedTerminalSymbolError ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedTransition(Color)
   */
  public void colorChangedTransition ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedTransitionActive(Color)
   */
  public void colorChangedTransitionActive ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedTransitionError(Color)
   */
  public void colorChangedTransitionError ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }


  /**
   * {@inheritDoc}
   * 
   * @see ColorChangedListener#colorChangedTransitionSelected(Color)
   */
  public void colorChangedTransitionSelected ( @SuppressWarnings ( "unused" )
  Color newColor )
  {
    // Override this method if needed.
  }
}
