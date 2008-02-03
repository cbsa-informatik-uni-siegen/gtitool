package de.unisiegen.gtitool.core.parser.style;


import java.awt.Color;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.preferences.PreferenceManager;
import de.unisiegen.gtitool.core.preferences.listener.ColorChangedAdapter;


/**
 * The style enum used to highlight the tokens.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public enum Style
{
  /**
   * No style.
   */
  NONE,

  /**
   * Style of {@link State}s.
   */
  STATE,

  /**
   * Style of {@link Symbol}s.
   */
  SYMBOL,
  
  /**
   * Style of error {@link Symbol}s.
   */
  SYMBOL_ERROR,

  /**
   * Style of keywords.
   */
  KEYWORD;

  /**
   * The {@link State} color.
   */
  private Color state;


  /**
   * The {@link Symbol} color.
   */
  private Color symbol;


  /**
   * The error {@link Symbol} color.
   */
  private Color symbolError;


  /**
   * Allocates a new {@link Style}.
   */
  private Style ()
  {
    // State
    this.state = PreferenceManager.getInstance ().getColorItemState ()
        .getColor ();
    PreferenceManager.getInstance ().addColorChangedListener (
        new ColorChangedAdapter ()
        {

          @SuppressWarnings ( "synthetic-access" )
          @Override
          public void colorChangedState ( Color newColor )
          {
            Style.this.state = newColor;
          }
        } );
    // Symbol
    this.symbol = PreferenceManager.getInstance ().getColorItemSymbol ()
        .getColor ();
    PreferenceManager.getInstance ().addColorChangedListener (
        new ColorChangedAdapter ()
        {

          @SuppressWarnings ( "synthetic-access" )
          @Override
          public void colorChangedSymbol ( Color newColor )
          {
            Style.this.symbol = newColor;
          }
        } );
    // Error symbol
    this.symbolError = PreferenceManager.getInstance ()
        .getColorItemSymbolError ().getColor ();
    PreferenceManager.getInstance ().addColorChangedListener (
        new ColorChangedAdapter ()
        {

          @SuppressWarnings ( "synthetic-access" )
          @Override
          public void colorChangedSymbolError ( Color newColor )
          {
            Style.this.symbolError = newColor;
          }
        } );
  }


  /**
   * Returns the {@link Color} of this {@link Style}.
   * 
   * @return The {@link Color} of this {@link Style}.
   */
  public final Color getColor ()
  {
    switch ( this )
    {
      case NONE :
      {
        return Color.BLACK;
      }
      case STATE :
      {
        return this.state;
      }
      case SYMBOL :
      {
        return this.symbol;
      }
      case KEYWORD :
      {
        return Color.BLACK;
      }
      case SYMBOL_ERROR :
      {
        return this.symbolError;
      }
      default :
      {
        throw new IllegalArgumentException ( "enum value not supported" ); //$NON-NLS-1$
      }
    }
  }
}
