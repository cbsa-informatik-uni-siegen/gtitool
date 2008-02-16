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
  NONE ( false, false, false ),

  /**
   * Style of {@link State}s.
   */
  STATE ( true, false, false ),

  /**
   * Style of {@link Symbol}s.
   */
  SYMBOL ( true, false, false ),

  /**
   * Style of active {@link Symbol}s.
   */
  SYMBOL_ACTIVE ( true, false, false ),

  /**
   * Style of error {@link Symbol}s.
   */
  SYMBOL_ERROR ( true, false, false ),

  /**
   * Style of {@link NonterminalSymbol}s.
   */
  NONTERMINAL_SYMBOL ( true, false, false ),

  /**
   * Style of {@link TerminalSymbol}s.
   */
  TERMINAL_SYMBOL ( true, false, false ),

  /**
   * Style of keywords.
   */
  KEYWORD ( true, false, false );

  /**
   * The bold value.
   */
  private boolean bold;


  /**
   * The italic value.
   */
  private boolean italic;


  /**
   * The underline value.
   */
  private boolean underline;


  /**
   * Allocates a new {@link Style}.
   * 
   * @param bold The bold value.
   * @param italic The italic value.
   * @param underline The underline value.
   */
  private Style ( boolean bold, boolean italic, boolean underline )
  {
    this.bold = bold;
    this.italic = italic;
    this.underline = underline;
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
      case NONTERMINAL_SYMBOL :
      {
        return PreferenceManager.getInstance ()
            .getColorItemNonterminalSymbol ().getColor ();
      }
      case TERMINAL_SYMBOL :
      {
        return PreferenceManager.getInstance ().getColorItemTerminalSymbol ()
            .getColor ();
      }
      case KEYWORD :
      {
        return PreferenceManager.getInstance ().getColorItemParserKeyword ()
            .getColor ();
      }
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


  /**
   * Returns the underline value.
   * 
   * @return The underline value.
   * @see #underline
   */
  public final boolean isUnderline ()
  {
    return this.underline;
  }
}
