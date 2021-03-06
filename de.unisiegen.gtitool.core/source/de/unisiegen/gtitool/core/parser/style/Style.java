package de.unisiegen.gtitool.core.parser.style;


import java.awt.Color;

import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.Transition;
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
   * Style of keywords.
   */
  KEYWORD ( true, false ),

  /**
   * No style.
   */
  NONE ( false, false ),

  /**
   * Style of {@link NonterminalSymbol}s.
   */
  NONTERMINAL_SYMBOL ( true, false ),

  /**
   * Style of error {@link NonterminalSymbol}s.
   */
  NONTERMINAL_SYMBOL_ERROR ( true, false ),

  /**
   * Style of error {@link NonterminalSymbol}s.
   */
  NONTERMINAL_SYMBOL_HIGHLIGHT ( true, false ),

  /**
   * Style of error {@link Production}s.
   */
  PRODUCTION_ERROR ( true, false ),

  /**
   * Style of error {@link Production}s.
   */
  PRODUCTION_HIGHLIGHT ( true, false ),

  /**
   * Style of a RegexPosition
   */
  REGEX_POSITION ( true, false ),

  /**
   * Style of RegexSymbols
   */
  REGEX_SYMBOL ( true, false ),

  /**
   * Style of a Tool tip text of a Regex
   */
  REGEX_TOOL_TIP_TEXT ( false, false ),

  /**
   * Style of start {@link NonterminalSymbol}s.
   */
  START_NONTERMINAL_SYMBOL ( true, false ),

  /**
   * Style of {@link State}s.
   */
  STATE ( true, false ),

  /**
   * Style of selected {@link State}s.
   */
  STATE_SELECTED ( true, false ),

  /**
   * Style of selected syntax {@link State}s.
   */
  STATE_SELECTED_SYNTAX ( false, false ),

  /**
   * Style of {@link Symbol}s.
   */
  SYMBOL ( true, false ),

  /**
   * Style of active {@link Symbol}s.
   */
  SYMBOL_ACTIVE ( true, false ),

  /**
   * Style of error {@link Symbol}s.
   */
  SYMBOL_ERROR ( true, false ),

  /**
   * Style of {@link TerminalSymbol}s.
   */
  TERMINAL_SYMBOL ( true, false ),

  /**
   * Style of error {@link TerminalSymbol}s.
   */
  TERMINAL_SYMBOL_ERROR ( true, false ),

  /**
   * Style of RegexTokens
   */
  TOKEN ( true, false ),

  /**
   * Style of Comments.
   */
  COMMENT ( false, true ),

  /**
   * Style of selected {@link Transition}s.
   */
  TRANSITION_SELECTED ( true, false );

  /**
   * The bold value.
   */
  private boolean bold;


  /**
   * The italic value.
   */
  private boolean italic;


  /**
   * Allocates a new {@link Style}.
   * 
   * @param bold The bold value.
   * @param italic The italic value.
   */
  private Style ( boolean bold, boolean italic )
  {
    this.bold = bold;
    this.italic = italic;
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
      case STATE_SELECTED :
      {
        return PreferenceManager.getInstance ().getColorItemStateSelected ()
            .getColor ();
      }
      case STATE_SELECTED_SYNTAX :
      {
        return PreferenceManager.getInstance ().getColorItemStateSelected ()
            .getColor ();
      }
      case TRANSITION_SELECTED :
      {
        return PreferenceManager.getInstance ()
            .getColorItemTransitionSelected ().getColor ();
      }
      case SYMBOL :
      {
        return PreferenceManager.getInstance ().getColorItemSymbol ()
            .getColor ();
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
      case PRODUCTION_ERROR :
      {
        return PreferenceManager.getInstance ().getColorItemProductionError ()
            .getColor ();
      }
      case PRODUCTION_HIGHLIGHT :
      {
        return PreferenceManager.getInstance ()
            .getColorItemProductionHighlight ().getColor ();
      }
      case NONTERMINAL_SYMBOL :
      {
        return PreferenceManager.getInstance ()
            .getColorItemNonterminalSymbol ().getColor ();
      }
      case NONTERMINAL_SYMBOL_ERROR :
      {
        return PreferenceManager.getInstance ()
            .getColorItemNonterminalSymbolError ().getColor ();
      }
      case NONTERMINAL_SYMBOL_HIGHLIGHT :
        return PreferenceManager.getInstance ()
            .getColorItemNonterminalSymbolHighlight ().getColor ();
      case START_NONTERMINAL_SYMBOL :
      {
        return PreferenceManager.getInstance ()
            .getColorItemStartNonterminalSymbol ().getColor ();
      }
      case TERMINAL_SYMBOL :
      {
        return PreferenceManager.getInstance ().getColorItemTerminalSymbol ()
            .getColor ();
      }
      case TERMINAL_SYMBOL_ERROR :
      {
        return PreferenceManager.getInstance ()
            .getColorItemTerminalSymbolError ().getColor ();
      }
      case KEYWORD :
      {
        return PreferenceManager.getInstance ().getColorItemParserKeyword ()
            .getColor ();
      }
      case REGEX_SYMBOL :
        return PreferenceManager.getInstance ().getColorItemRegexSymbol ()
            .getColor ();
      case TOKEN :
        return PreferenceManager.getInstance ().getColorItemRegexToken ()
            .getColor ();
      case REGEX_POSITION :
        return PreferenceManager.getInstance ().getColorItemRegexPosition ()
            .getColor ();
      case COMMENT :
        return PreferenceManager.getInstance ().getColorItemRegexComment ()
            .getColor ();
      case REGEX_TOOL_TIP_TEXT :
        return PreferenceManager.getInstance ().getColorItemRegexToolTipText ()
            .getColor ();
      default :
      {
        throw new IllegalArgumentException ( "enum value not supported" ); //$NON-NLS-1$
      }
    }
  }


  /**
   * Returns the bold value.
   * 
   * @return The bold value.
   * @see #bold
   */
  public final boolean isBold ()
  {
    return this.bold;
  }


  /**
   * Returns the italic value.
   * 
   * @return The italic value.
   * @see #italic
   */
  public final boolean isItalic ()
  {
    return this.italic;
  }
}
