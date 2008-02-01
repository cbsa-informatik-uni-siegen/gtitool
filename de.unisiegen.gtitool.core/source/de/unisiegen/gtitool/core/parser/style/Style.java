package de.unisiegen.gtitool.core.parser.style;


import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;


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
}
