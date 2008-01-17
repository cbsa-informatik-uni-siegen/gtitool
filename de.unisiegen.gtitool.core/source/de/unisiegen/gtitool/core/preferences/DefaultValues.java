package de.unisiegen.gtitool.core.preferences;


import java.awt.Color;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;


/**
 * The default values interface.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public interface DefaultValues
{

  /**
   * The default {@link Color} of the active {@link State}.
   */
  public final Color DEFAULT_ACTIVE_STATE_COLOR = new Color ( 0, 0, 255 );


  /**
   * The default {@link Color} of the active {@link Transition}.
   */
  public final Color DEFAULT_ACTIVE_TRANSITION_COLOR = new Color ( 0, 0, 255 );


  /**
   * The default {@link Color} of the error {@link State}.
   */
  public final Color DEFAULT_ERROR_STATE_COLOR = new Color ( 255, 0, 0 );


  /**
   * The default {@link Color} of the selected {@link State}.
   */
  public final Color DEFAULT_SELECTED_STATE_COLOR = new Color ( 255, 0, 0 );


  /**
   * The default {@link Color} of the start {@link State}.
   */
  public final Color DEFAULT_START_STATE_COLOR = new Color ( 255, 255, 255 );


  /**
   * The default {@link Color} of a {@link State}.
   */
  public final Color DEFAULT_STATE_COLOR = new Color ( 255, 255, 255 );


  /**
   * The default {@link Color} of a {@link Symbol}.
   */
  public final Color DEFAULT_SYMBOL_COLOR = new Color ( 255, 127, 0 );


  /**
   * The default {@link Color} of a error {@link Symbol}.
   */
  public final Color DEFAULT_ERROR_SYMBOL_COLOR = new Color ( 255, 0, 0 );


  /**
   * The default {@link Color} of a {@link Transition}.
   */
  public final Color DEFAULT_TRANSITION_COLOR = new Color ( 0, 0, 0 );


  /**
   * The default {@link Color} of a error {@link Transition}.
   */
  public final Color DEFAULT_ERROR_TRANSITION_COLOR = new Color ( 255, 0, 0 );


  /**
   * The default {@link Color} of a parser warning.
   */
  public final Color DEFAULT_PARSER_STATE_COLOR = new Color ( 0, 0, 127 );


  /**
   * The default {@link Color} of a parser warning.
   */
  public final Color DEFAULT_PARSER_SYMBOL_COLOR = new Color ( 0, 0, 127 );


  /**
   * The default {@link Color} of a parser warning.
   */
  public final Color DEFAULT_PARSER_WARNING_COLOR = new Color ( 232, 242, 254 );


  /**
   * The default {@link Color} of a parser highlighting.
   */
  public final Color DEFAULT_PARSER_HIGHLIGHTING_COLOR = new Color ( 255, 255,
      0 );
}
