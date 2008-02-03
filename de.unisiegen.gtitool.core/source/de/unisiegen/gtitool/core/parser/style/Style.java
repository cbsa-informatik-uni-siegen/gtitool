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
   * Style of keywords.
   */
  KEYWORD,

  /**
   * Style of error {@link Symbol}s.
   */
  ERROR_SYMBOL;

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
  private Color errorSymbol;


  /**
   * Allocates a new {@link Style}.
   */
  private Style ()
  {
    // State
    this.state = PreferenceManager.getInstance ().getColorItemParserState ()
        .getColor ();
    PreferenceManager.getInstance ().addColorChangedListener (
        new ColorChangedAdapter ()
        {

          @SuppressWarnings ( "synthetic-access" )
          @Override
          public void colorChangedParserState ( Color newColor )
          {
            Style.this.state = newColor;
          }
        } );
    // Symbol
    this.symbol = PreferenceManager.getInstance ().getColorItemParserSymbol ()
        .getColor ();
    PreferenceManager.getInstance ().addColorChangedListener (
        new ColorChangedAdapter ()
        {

          @SuppressWarnings ( "synthetic-access" )
          @Override
          public void colorChangedParserSymbol ( Color newColor )
          {
            Style.this.symbol = newColor;
          }
        } );
    // Error symbol
    this.errorSymbol = PreferenceManager.getInstance ()
        .getColorItemErrorSymbol ().getColor ();
    PreferenceManager.getInstance ().addColorChangedListener (
        new ColorChangedAdapter ()
        {

          @SuppressWarnings ( "synthetic-access" )
          @Override
          public void colorChangedErrorSymbol ( Color newColor )
          {
            Style.this.errorSymbol = newColor;
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
      case ERROR_SYMBOL :
      {
        return this.errorSymbol;
      }
      default :
      {
        throw new IllegalArgumentException ( "enum value not supported" ); //$NON-NLS-1$
      }
    }
  }
}
