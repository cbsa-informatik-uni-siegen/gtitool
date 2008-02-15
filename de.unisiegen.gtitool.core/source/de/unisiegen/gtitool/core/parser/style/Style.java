package de.unisiegen.gtitool.core.parser.style;


import java.awt.Color;

import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.preferences.PreferenceManager;


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
   * Style of active {@link Symbol}s.
   */
  SYMBOL_ACTIVE,

  /**
   * Style of error {@link Symbol}s.
   */
  SYMBOL_ERROR,

  /**
   * Style of {@link NonterminalSymbol}s.
   */
  NONTERMINAL_SYMBOL,

  /**
   * Style of active {@link NonterminalSymbol}s.
   */
  NONTERMINAL_SYMBOL_ACTIVE,

  /**
   * Style of error {@link NonterminalSymbol}s.
   */
  NONTERMINAL_SYMBOL_ERROR,

  /**
   * Style of {@link TerminalSymbol}s.
   */
  TERMINAL_SYMBOL,

  /**
   * Style of active {@link TerminalSymbol}s.
   */
  TERMINAL_SYMBOL_ACTIVE,

  /**
   * Style of error {@link TerminalSymbol}s.
   */
  TERMINAL_SYMBOL_ERROR,

  /**
   * Style of keywords.
   */
  KEYWORD;

  /**
   * Allocates a new {@link Style}.
   */
  private Style ()
  {
    // Do nothing
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
        return PreferenceManager.getInstance ().getColorItemState ()
            .getColor ();
      }
      case SYMBOL :
      {
        return PreferenceManager.getInstance ().getColorItemSymbol ()
            .getColor ();
      }
      case KEYWORD :
      {
        return Color.BLACK;
      }
      case SYMBOL_ACTIVE :
      {
        return PreferenceManager.getInstance ().getColorItemSymbolActive ()
            .getColor ();
      }
      case SYMBOL_ERROR :
      {
        return PreferenceManager.getInstance ().getColorItemSymbolError ()
            .getColor ();
      }
      default :
      {
        throw new IllegalArgumentException ( "enum value not supported" ); //$NON-NLS-1$
      }
    }
  }
}
