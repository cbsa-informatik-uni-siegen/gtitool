package de.unisiegen.gtitool.core.preferences;


import java.awt.Color;
import java.util.prefs.Preferences;

import javax.swing.event.EventListenerList;

import org.apache.log4j.Logger;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.preferences.item.ColorItem;
import de.unisiegen.gtitool.core.preferences.listener.ColorChangedListener;


/**
 * Manages the preferences for the core project.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class PreferenceManager
{

  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger
      .getLogger ( PreferenceManager.class );


  /**
   * The single instance of the <code>PreferenceManager<code>
   */
  private static PreferenceManager singlePreferenceManager;


  /**
   * The default {@link Color} of the active {@link State}.
   */
  public static final Color DEFAULT_ACTIVE_STATE_COLOR = new Color ( 0, 0, 255 );


  /**
   * The default {@link Color} of the active {@link Transition}.
   */
  public static final Color DEFAULT_ACTIVE_TRANSITION_COLOR = new Color ( 0, 0,
      255 );


  /**
   * The default {@link Color} of the error {@link State}.
   */
  public static final Color DEFAULT_ERROR_STATE_COLOR = new Color ( 255, 0, 0 );


  /**
   * The default {@link Color} of the selected {@link State}.
   */
  public static final Color DEFAULT_SELECTED_STATE_COLOR = new Color ( 255, 0,
      0 );


  /**
   * The default {@link Color} of the start {@link State}.
   */
  public static final Color DEFAULT_START_STATE_COLOR = new Color ( 255, 255,
      255 );


  /**
   * The default {@link Color} of a {@link State}.
   */
  public static final Color DEFAULT_STATE_COLOR = new Color ( 255, 255, 255 );


  /**
   * The default {@link Color} of a {@link Symbol}.
   */
  public static final Color DEFAULT_SYMBOL_COLOR = new Color ( 255, 127, 0 );


  /**
   * The default {@link Color} of a error {@link Symbol}.
   */
  public static final Color DEFAULT_ERROR_SYMBOL_COLOR = new Color ( 255, 0, 0 );


  /**
   * The default {@link Color} of a {@link Transition}.
   */
  public static final Color DEFAULT_TRANSITION_COLOR = new Color ( 0, 0, 0 );


  /**
   * The default {@link Color} of a error {@link Transition}.
   */
  public static final Color DEFAULT_ERROR_TRANSITION_COLOR = new Color ( 255,
      0, 0 );


  /**
   * The default {@link Color} of a parser warning.
   */
  public static final Color DEFAULT_PARSER_STATE_COLOR = new Color ( 0, 0, 127 );


  /**
   * The default {@link Color} of a parser warning.
   */
  public static final Color DEFAULT_PARSER_SYMBOL_COLOR = new Color ( 0, 0, 127 );


  /**
   * The default {@link Color} of a parser warning.
   */
  public static final Color DEFAULT_PARSER_WARNING_COLOR = new Color ( 232,
      242, 254 );


  /**
   * The default {@link Color} of a parser highlighting.
   */
  public static final Color DEFAULT_PARSER_HIGHLIGHTING_COLOR = new Color (
      255, 255, 0 );


  /**
   * Returns the single instance of the <code>PreferenceManager</code>.
   * 
   * @return The single instance of the <code>PreferenceManager</code>.
   */
  public final static PreferenceManager getInstance ()
  {
    if ( singlePreferenceManager == null )
    {
      singlePreferenceManager = new PreferenceManager ();
    }
    return singlePreferenceManager;
  }


  /**
   * The {@link Preferences} object for the node where the settings are stored
   * and loaded.
   * 
   * @see Preferences
   */
  private Preferences preferences;


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * Allocates a new <code>PreferencesManager</code>.
   */
  private PreferenceManager ()
  {
    this.preferences = Preferences.userRoot ();
  }


  /**
   * Adds the given {@link ColorChangedListener}.
   * 
   * @param listener The {@link ColorChangedListener}.
   */
  public final synchronized void addColorChangedListener (
      ColorChangedListener listener )
  {
    this.listenerList.add ( ColorChangedListener.class, listener );
  }


  /**
   * Let the listeners know that the color of the active {@link State} has
   * changed.
   * 
   * @param newColor The new color of the active {@link State}.
   */
  public final void fireColorChangedActiveState ( Color newColor )
  {
    ColorChangedListener [] listeners = this.listenerList
        .getListeners ( ColorChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].colorChangedActiveState ( newColor );
    }
  }


  /**
   * Let the listeners know that the color of the active {@link Transition} has
   * changed.
   * 
   * @param newColor The new color of the active {@link Transition}.
   */
  public final void fireColorChangedActiveTransition ( Color newColor )
  {
    ColorChangedListener [] listeners = this.listenerList
        .getListeners ( ColorChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].colorChangedActiveTransition ( newColor );
    }
  }


  /**
   * Let the listeners know that the color of the error {@link State} has
   * changed.
   * 
   * @param newColor The new color of the error {@link State}.
   */
  public final void fireColorChangedErrorState ( Color newColor )
  {
    ColorChangedListener [] listeners = this.listenerList
        .getListeners ( ColorChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].colorChangedErrorState ( newColor );
    }
  }


  /**
   * Let the listeners know that the color of the error {@link Symbol} has
   * changed.
   * 
   * @param newColor The new color of the error {@link Symbol}.
   */
  public final void fireColorChangedErrorSymbol ( Color newColor )
  {
    ColorChangedListener [] listeners = this.listenerList
        .getListeners ( ColorChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].colorChangedErrorSymbol ( newColor );
    }
  }


  /**
   * Let the listeners know that the color of the error {@link Transition} has
   * changed.
   * 
   * @param newColor The new color of the error {@link Transition}.
   */
  public final void fireColorChangedErrorTransition ( Color newColor )
  {
    ColorChangedListener [] listeners = this.listenerList
        .getListeners ( ColorChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].colorChangedErrorTransition ( newColor );
    }
  }


  /**
   * Let the listeners know that the color of the parser highlighting has
   * changed.
   * 
   * @param newColor The new color of the parser warning.
   */
  public final void fireColorChangedParserHighlighting ( Color newColor )
  {
    ColorChangedListener [] listeners = this.listenerList
        .getListeners ( ColorChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].colorChangedParserHighlighting ( newColor );
    }
  }


  /**
   * Let the listeners know that the color of the parser {@link State} has
   * changed.
   * 
   * @param newColor The new color of the parser {@link State}.
   */
  public final void fireColorChangedParserState ( Color newColor )
  {
    ColorChangedListener [] listeners = this.listenerList
        .getListeners ( ColorChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].colorChangedParserState ( newColor );
    }
  }


  /**
   * Let the listeners know that the color of the parser {@link Symbol} has
   * changed.
   * 
   * @param newColor The new color of the parser {@link Symbol}.
   */
  public final void fireColorChangedParserSymbol ( Color newColor )
  {
    ColorChangedListener [] listeners = this.listenerList
        .getListeners ( ColorChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].colorChangedParserSymbol ( newColor );
    }
  }


  /**
   * Let the listeners know that the color of the parser warning has changed.
   * 
   * @param newColor The new color of the parser warning.
   */
  public final void fireColorChangedParserWarning ( Color newColor )
  {
    ColorChangedListener [] listeners = this.listenerList
        .getListeners ( ColorChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].colorChangedParserWarning ( newColor );
    }
  }


  /**
   * Let the listeners know that the color of the selected {@link State} has
   * changed.
   * 
   * @param newColor The new color of the selected {@link State}.
   */
  public final void fireColorChangedSelectedState ( Color newColor )
  {
    ColorChangedListener [] listeners = this.listenerList
        .getListeners ( ColorChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].colorChangedSelectedState ( newColor );
    }
  }


  /**
   * Let the listeners know that the color of the start {@link State} has
   * changed.
   * 
   * @param newColor The new color of the start {@link State}.
   */
  public final void fireColorChangedStartState ( Color newColor )
  {
    ColorChangedListener [] listeners = this.listenerList
        .getListeners ( ColorChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].colorChangedStartState ( newColor );
    }
  }


  /**
   * Let the listeners know that the color of the {@link State} has changed.
   * 
   * @param newColor The new color of the {@link State}.
   */
  public final void fireColorChangedState ( Color newColor )
  {
    ColorChangedListener [] listeners = this.listenerList
        .getListeners ( ColorChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].colorChangedState ( newColor );
    }
  }


  /**
   * Let the listeners know that the color of the {@link Symbol} has changed.
   * 
   * @param newColor The new color of the {@link Symbol}.
   */
  public final void fireColorChangedSymbol ( Color newColor )
  {
    ColorChangedListener [] listeners = this.listenerList
        .getListeners ( ColorChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].colorChangedSymbol ( newColor );
    }
  }


  /**
   * Let the listeners know that the color of the {@link Transition} has
   * changed.
   * 
   * @param newColor The new color of the {@link Transition}.
   */
  public final void fireColorChangedTransition ( Color newColor )
  {
    ColorChangedListener [] listeners = this.listenerList
        .getListeners ( ColorChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].colorChangedTransition ( newColor );
    }
  }


  /**
   * Returns the {@link ColorItem} of the active {@link State}.
   * 
   * @return The {@link ColorItem} of the active {@link State}.
   */
  public final ColorItem getColorItemActiveState ()
  {
    int r = this.preferences.getInt ( "PreferencesDialog.ColorActiveStateR", //$NON-NLS-1$
        DEFAULT_ACTIVE_STATE_COLOR.getRed () );
    int g = this.preferences.getInt ( "PreferencesDialog.ColorActiveStateG", //$NON-NLS-1$
        DEFAULT_ACTIVE_STATE_COLOR.getGreen () );
    int b = this.preferences.getInt ( "PreferencesDialog.ColorActiveStateB", //$NON-NLS-1$
        DEFAULT_ACTIVE_STATE_COLOR.getBlue () );
    String caption = Messages
        .getString ( "PreferencesDialog.ColorActiveStateCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "PreferencesDialog.ColorActiveStateDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DEFAULT_ACTIVE_STATE_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the active {@link Transition}.
   * 
   * @return The {@link ColorItem} of the active {@link Transition}.
   */
  public final ColorItem getColorItemActiveTransition ()
  {
    int r = this.preferences.getInt (
        "PreferencesDialog.ColorActiveTransitionR", //$NON-NLS-1$
        DEFAULT_ACTIVE_TRANSITION_COLOR.getRed () );
    int g = this.preferences.getInt (
        "PreferencesDialog.ColorActiveTransitionG", //$NON-NLS-1$
        DEFAULT_ACTIVE_TRANSITION_COLOR.getGreen () );
    int b = this.preferences.getInt (
        "PreferencesDialog.ColorActiveTransitionB", //$NON-NLS-1$
        DEFAULT_ACTIVE_TRANSITION_COLOR.getBlue () );
    String caption = Messages
        .getString ( "PreferencesDialog.ColorActiveTransitionCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "PreferencesDialog.ColorActiveTransitionDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DEFAULT_ACTIVE_TRANSITION_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the error {@link State}.
   * 
   * @return The {@link ColorItem} of the error {@link State}.
   */
  public final ColorItem getColorItemErrorState ()
  {
    int r = this.preferences.getInt ( "PreferencesDialog.ColorErrorStateR", //$NON-NLS-1$
        DEFAULT_ERROR_STATE_COLOR.getRed () );
    int g = this.preferences.getInt ( "PreferencesDialog.ColorErrorStateG", //$NON-NLS-1$
        DEFAULT_ERROR_STATE_COLOR.getGreen () );
    int b = this.preferences.getInt ( "PreferencesDialog.ColorErrorStateB", //$NON-NLS-1$
        DEFAULT_ERROR_STATE_COLOR.getBlue () );
    String caption = Messages
        .getString ( "PreferencesDialog.ColorErrorStateCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "PreferencesDialog.ColorErrorStateDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DEFAULT_ERROR_STATE_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the error {@link Symbol}.
   * 
   * @return The {@link ColorItem} of the error {@link Symbol}.
   */
  public final ColorItem getColorItemErrorSymbol ()
  {
    int r = this.preferences.getInt ( "PreferencesDialog.ColorErrorSymbolR", //$NON-NLS-1$
        DEFAULT_ERROR_SYMBOL_COLOR.getRed () );
    int g = this.preferences.getInt ( "PreferencesDialog.ColorErrorSymbolG", //$NON-NLS-1$
        DEFAULT_ERROR_SYMBOL_COLOR.getGreen () );
    int b = this.preferences.getInt ( "PreferencesDialog.ColorErrorSymbolB", //$NON-NLS-1$
        DEFAULT_ERROR_SYMBOL_COLOR.getBlue () );
    String caption = Messages
        .getString ( "PreferencesDialog.ColorErrorSymbolCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "PreferencesDialog.ColorErrorSymbolDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DEFAULT_ERROR_SYMBOL_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the error {@link Transition}.
   * 
   * @return The {@link ColorItem} of the error {@link Transition}.
   */
  public final ColorItem getColorItemErrorTransition ()
  {
    int r = this.preferences.getInt (
        "PreferencesDialog.ColorErrorTransitionR", //$NON-NLS-1$
        DEFAULT_ERROR_TRANSITION_COLOR.getRed () );
    int g = this.preferences.getInt (
        "PreferencesDialog.ColorErrorTransitionG", //$NON-NLS-1$
        DEFAULT_ERROR_TRANSITION_COLOR.getGreen () );
    int b = this.preferences.getInt (
        "PreferencesDialog.ColorErrorTransitionB", //$NON-NLS-1$
        DEFAULT_ERROR_TRANSITION_COLOR.getBlue () );
    String caption = Messages
        .getString ( "PreferencesDialog.ColorErrorTransitionCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "PreferencesDialog.ColorErrorTransitionDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DEFAULT_ERROR_TRANSITION_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the parser highlighting.
   * 
   * @return The {@link ColorItem} of the parser highlighting.
   */
  public final ColorItem getColorItemParserHighlighting ()
  {
    int r = this.preferences.getInt (
        "PreferencesDialog.ColorParserHighlightingR", //$NON-NLS-1$
        DEFAULT_PARSER_HIGHLIGHTING_COLOR.getRed () );
    int g = this.preferences.getInt (
        "PreferencesDialog.ColorParserHighlightingG", //$NON-NLS-1$
        DEFAULT_PARSER_HIGHLIGHTING_COLOR.getGreen () );
    int b = this.preferences.getInt (
        "PreferencesDialog.ColorParserHighlightingB", //$NON-NLS-1$
        DEFAULT_PARSER_HIGHLIGHTING_COLOR.getBlue () );
    String caption = Messages
        .getString ( "PreferencesDialog.ColorParserHighlightingCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "PreferencesDialog.ColorParserHighlightingDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DEFAULT_PARSER_HIGHLIGHTING_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the parser {State}.
   * 
   * @return The {@link ColorItem} of the parser {State}.
   */
  public final ColorItem getColorItemParserState ()
  {
    int r = this.preferences.getInt ( "PreferencesDialog.ColorParserStateR", //$NON-NLS-1$
        DEFAULT_PARSER_STATE_COLOR.getRed () );
    int g = this.preferences.getInt ( "PreferencesDialog.ColorParserStateG", //$NON-NLS-1$
        DEFAULT_PARSER_STATE_COLOR.getGreen () );
    int b = this.preferences.getInt ( "PreferencesDialog.ColorParserStateB", //$NON-NLS-1$
        DEFAULT_PARSER_STATE_COLOR.getBlue () );
    String caption = Messages
        .getString ( "PreferencesDialog.ColorParserStateCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "PreferencesDialog.ColorParserStateDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DEFAULT_PARSER_STATE_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the parser {Symbol}.
   * 
   * @return The {@link ColorItem} of the parser {Symbol}.
   */
  public final ColorItem getColorItemParserSymbol ()
  {
    int r = this.preferences.getInt ( "PreferencesDialog.ColorParserSymbolR", //$NON-NLS-1$
        DEFAULT_PARSER_SYMBOL_COLOR.getRed () );
    int g = this.preferences.getInt ( "PreferencesDialog.ColorParserSymbolG", //$NON-NLS-1$
        DEFAULT_PARSER_SYMBOL_COLOR.getGreen () );
    int b = this.preferences.getInt ( "PreferencesDialog.ColorParserSymbolB", //$NON-NLS-1$
        DEFAULT_PARSER_SYMBOL_COLOR.getBlue () );
    String caption = Messages
        .getString ( "PreferencesDialog.ColorParserSymbolCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "PreferencesDialog.ColorParserSymbolDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DEFAULT_PARSER_SYMBOL_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the parser warning.
   * 
   * @return The {@link ColorItem} of the parser warning.
   */
  public final ColorItem getColorItemParserWarning ()
  {
    int r = this.preferences.getInt ( "PreferencesDialog.ColorParserWarningR", //$NON-NLS-1$
        DEFAULT_PARSER_WARNING_COLOR.getRed () );
    int g = this.preferences.getInt ( "PreferencesDialog.ColorParserWarningG", //$NON-NLS-1$
        DEFAULT_PARSER_WARNING_COLOR.getGreen () );
    int b = this.preferences.getInt ( "PreferencesDialog.ColorParserWarningB", //$NON-NLS-1$
        DEFAULT_PARSER_WARNING_COLOR.getBlue () );
    String caption = Messages
        .getString ( "PreferencesDialog.ColorParserWarningCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "PreferencesDialog.ColorParserWarningDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DEFAULT_PARSER_WARNING_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the selected {@link State}.
   * 
   * @return The {@link ColorItem} of the selected {@link State}.
   */
  public final ColorItem getColorItemSelectedState ()
  {
    int r = this.preferences.getInt ( "PreferencesDialog.ColorSelectedStateR", //$NON-NLS-1$
        DEFAULT_SELECTED_STATE_COLOR.getRed () );
    int g = this.preferences.getInt ( "PreferencesDialog.ColorSelectedStateG", //$NON-NLS-1$
        DEFAULT_SELECTED_STATE_COLOR.getGreen () );
    int b = this.preferences.getInt ( "PreferencesDialog.ColorSelectedStateB", //$NON-NLS-1$
        DEFAULT_SELECTED_STATE_COLOR.getBlue () );
    String caption = Messages
        .getString ( "PreferencesDialog.ColorSelectedStateCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "PreferencesDialog.ColorSelectedStateDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DEFAULT_SELECTED_STATE_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the start {@link State}.
   * 
   * @return The {@link ColorItem} of the start {@link State}.
   */
  public final ColorItem getColorItemStartState ()
  {
    int r = this.preferences.getInt ( "PreferencesDialog.ColorStartStateR", //$NON-NLS-1$
        DEFAULT_START_STATE_COLOR.getRed () );
    int g = this.preferences.getInt ( "PreferencesDialog.ColorStartStateG", //$NON-NLS-1$
        DEFAULT_START_STATE_COLOR.getGreen () );
    int b = this.preferences.getInt ( "PreferencesDialog.ColorStartStateB", //$NON-NLS-1$
        DEFAULT_START_STATE_COLOR.getBlue () );
    String caption = Messages
        .getString ( "PreferencesDialog.ColorStartStateCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "PreferencesDialog.ColorStartStateDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DEFAULT_START_STATE_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the {@link State}.
   * 
   * @return The {@link ColorItem} of the {@link State}.
   */
  public final ColorItem getColorItemState ()
  {
    int r = this.preferences.getInt ( "PreferencesDialog.ColorStateR", //$NON-NLS-1$
        DEFAULT_STATE_COLOR.getRed () );
    int g = this.preferences.getInt ( "PreferencesDialog.ColorStateG", //$NON-NLS-1$
        DEFAULT_STATE_COLOR.getGreen () );
    int b = this.preferences.getInt ( "PreferencesDialog.ColorStateB", //$NON-NLS-1$
        DEFAULT_STATE_COLOR.getBlue () );
    String caption = Messages
        .getString ( "PreferencesDialog.ColorStateCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "PreferencesDialog.ColorStateDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DEFAULT_STATE_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the {@link Symbol}.
   * 
   * @return The {@link ColorItem} of the {@link Symbol}.
   */
  public final ColorItem getColorItemSymbol ()
  {
    int r = this.preferences.getInt ( "PreferencesDialog.ColorSymbolR", //$NON-NLS-1$
        DEFAULT_SYMBOL_COLOR.getRed () );
    int g = this.preferences.getInt ( "PreferencesDialog.ColorSymbolG", //$NON-NLS-1$
        DEFAULT_SYMBOL_COLOR.getGreen () );
    int b = this.preferences.getInt ( "PreferencesDialog.ColorSymbolB", //$NON-NLS-1$
        DEFAULT_SYMBOL_COLOR.getBlue () );
    String caption = Messages
        .getString ( "PreferencesDialog.ColorSymbolCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "PreferencesDialog.ColorSymbolDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DEFAULT_SYMBOL_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the {@link Transition}.
   * 
   * @return The {@link ColorItem} of the {@link Transition}.
   */
  public final ColorItem getColorItemTransition ()
  {
    int r = this.preferences.getInt ( "PreferencesDialog.ColorTransitionR", //$NON-NLS-1$
        DEFAULT_TRANSITION_COLOR.getRed () );
    int g = this.preferences.getInt ( "PreferencesDialog.ColorTransitionG", //$NON-NLS-1$
        DEFAULT_TRANSITION_COLOR.getGreen () );
    int b = this.preferences.getInt ( "PreferencesDialog.ColorTransitionB", //$NON-NLS-1$
        DEFAULT_TRANSITION_COLOR.getBlue () );
    String caption = Messages
        .getString ( "PreferencesDialog.ColorTransitionCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "PreferencesDialog.ColorTransitionDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DEFAULT_TRANSITION_COLOR );
  }


  /**
   * Removes the given {@link ColorChangedListener}.
   * 
   * @param listener The {@link ColorChangedListener}.
   */
  public final synchronized void removeColorChangedListener (
      ColorChangedListener listener )
  {
    this.listenerList.remove ( ColorChangedListener.class, listener );
  }


  /**
   * Sets the {@link ColorItem} of the active {@link State}.
   * 
   * @param colorItem The {@link ColorItem} of the active {@link State}.
   */
  public final void setColorItemActiveState ( ColorItem colorItem )
  {
    logger.debug ( "set color of the avtive state to \"" //$NON-NLS-1$
        + "r=" + colorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + colorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + colorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "PreferencesDialog.ColorActiveStateR", //$NON-NLS-1$
        colorItem.getColor ().getRed () );
    this.preferences.putInt ( "PreferencesDialog.ColorActiveStateG", //$NON-NLS-1$
        colorItem.getColor ().getGreen () );
    this.preferences.putInt ( "PreferencesDialog.ColorActiveStateB", //$NON-NLS-1$
        colorItem.getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the active {@link Transition}.
   * 
   * @param colorItem The {@link ColorItem} of the active {@link Transition}.
   */
  public final void setColorItemActiveTransition ( ColorItem colorItem )
  {
    logger.debug ( "set color of the active transition to \"" //$NON-NLS-1$
        + "r=" + colorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + colorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + colorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "PreferencesDialog.ColorActiveTransitionR", //$NON-NLS-1$
        colorItem.getColor ().getRed () );
    this.preferences.putInt ( "PreferencesDialog.ColorActiveTransitionG", //$NON-NLS-1$
        colorItem.getColor ().getGreen () );
    this.preferences.putInt ( "PreferencesDialog.ColorActiveTransitionB", //$NON-NLS-1$
        colorItem.getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the error {@link State}.
   * 
   * @param colorItem The {@link ColorItem} of the error {@link State}.
   */
  public final void setColorItemErrorState ( ColorItem colorItem )
  {
    logger.debug ( "set color of the error state to \"" //$NON-NLS-1$
        + "r=" + colorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + colorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + colorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "PreferencesDialog.ColorErrorStateR", //$NON-NLS-1$
        colorItem.getColor ().getRed () );
    this.preferences.putInt ( "PreferencesDialog.ColorErrorStateG", //$NON-NLS-1$
        colorItem.getColor ().getGreen () );
    this.preferences.putInt ( "PreferencesDialog.ColorErrorStateB", //$NON-NLS-1$
        colorItem.getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the error {@link Symbol}.
   * 
   * @param colorItem The {@link ColorItem} of the error {@link Symbol}.
   */
  public final void setColorItemErrorSymbol ( ColorItem colorItem )
  {
    logger.debug ( "set color of the error symbol to \"" //$NON-NLS-1$
        + "r=" + colorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + colorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + colorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "PreferencesDialog.ColorErrorSymbolR", colorItem //$NON-NLS-1$
        .getColor ().getRed () );
    this.preferences.putInt ( "PreferencesDialog.ColorErrorSymbolG", colorItem //$NON-NLS-1$
        .getColor ().getGreen () );
    this.preferences.putInt ( "PreferencesDialog.ColorErrorSymbolB", colorItem //$NON-NLS-1$
        .getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the error {@link Transition}.
   * 
   * @param colorItem The {@link ColorItem} of the error {@link Transition}.
   */
  public final void setColorItemErrorTransition ( ColorItem colorItem )
  {
    logger.debug ( "set color of the error transition to \"" //$NON-NLS-1$
        + "r=" + colorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + colorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + colorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt (
        "PreferencesDialog.ColorErrorTransitionR", colorItem //$NON-NLS-1$
            .getColor ().getRed () );
    this.preferences.putInt (
        "PreferencesDialog.ColorErrorTransitionG", colorItem //$NON-NLS-1$
            .getColor ().getGreen () );
    this.preferences.putInt (
        "PreferencesDialog.ColorErrorTransitionB", colorItem //$NON-NLS-1$
            .getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the parser highlighting.
   * 
   * @param colorItem The {@link ColorItem} of the parser highlighting.
   */
  public final void setColorItemParserHighlighting ( ColorItem colorItem )
  {
    logger.debug ( "set color of the parser highlighting to \"" //$NON-NLS-1$
        + "r=" + colorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + colorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + colorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt (
        "PreferencesDialog.ColorParserHighlightingR", colorItem //$NON-NLS-1$
            .getColor ().getRed () );
    this.preferences.putInt (
        "PreferencesDialog.ColorParserHighlightingG", colorItem //$NON-NLS-1$
            .getColor ().getGreen () );
    this.preferences.putInt (
        "PreferencesDialog.ColorParserHighlightingB", colorItem //$NON-NLS-1$
            .getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the parser {@link State}.
   * 
   * @param colorItem The {@link ColorItem} of the parser {@link State}.
   */
  public final void setColorItemParserState ( ColorItem colorItem )
  {
    logger.debug ( "set color of the parser state to \"" //$NON-NLS-1$
        + "r=" + colorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + colorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + colorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "PreferencesDialog.ColorParserStateR", colorItem //$NON-NLS-1$
        .getColor ().getRed () );
    this.preferences.putInt ( "PreferencesDialog.ColorParserStateG", colorItem //$NON-NLS-1$
        .getColor ().getGreen () );
    this.preferences.putInt ( "PreferencesDialog.ColorParserStateB", colorItem //$NON-NLS-1$
        .getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the parser {@link Symbol}.
   * 
   * @param colorItem The {@link ColorItem} of the parser {@link Symbol}.
   */
  public final void setColorItemParserSymbol ( ColorItem colorItem )
  {
    logger.debug ( "set color of the parser symbol to \"" //$NON-NLS-1$
        + "r=" + colorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + colorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + colorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "PreferencesDialog.ColorParserSymbolR", colorItem //$NON-NLS-1$
        .getColor ().getRed () );
    this.preferences.putInt ( "PreferencesDialog.ColorParserSymbolG", colorItem //$NON-NLS-1$
        .getColor ().getGreen () );
    this.preferences.putInt ( "PreferencesDialog.ColorParserSymbolB", colorItem //$NON-NLS-1$
        .getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the parser warning.
   * 
   * @param colorItem The {@link ColorItem} of the parser warning.
   */
  public final void setColorItemParserWarning ( ColorItem colorItem )
  {
    logger.debug ( "set color of the parser warning to \"" //$NON-NLS-1$
        + "r=" + colorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + colorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + colorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt (
        "PreferencesDialog.ColorParserWarningR", colorItem //$NON-NLS-1$
            .getColor ().getRed () );
    this.preferences.putInt (
        "PreferencesDialog.ColorParserWarningG", colorItem //$NON-NLS-1$
            .getColor ().getGreen () );
    this.preferences.putInt (
        "PreferencesDialog.ColorParserWarningB", colorItem //$NON-NLS-1$
            .getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the selected {@link State}.
   * 
   * @param colorItem The {@link ColorItem} of the selected {@link State}.
   */
  public final void setColorItemSelectedState ( ColorItem colorItem )
  {
    logger.debug ( "set color of the selected state to \"" //$NON-NLS-1$
        + "r=" + colorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + colorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + colorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "PreferencesDialog.ColorSelectedStateR", //$NON-NLS-1$
        colorItem.getColor ().getRed () );
    this.preferences.putInt ( "PreferencesDialog.ColorSelectedStateG", //$NON-NLS-1$
        colorItem.getColor ().getGreen () );
    this.preferences.putInt ( "PreferencesDialog.ColorSelectedStateB", //$NON-NLS-1$
        colorItem.getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the start {@link State}.
   * 
   * @param colorItem The {@link ColorItem} of the start {@link State}.
   */
  public final void setColorItemStartState ( ColorItem colorItem )
  {
    logger.debug ( "set color of the start state to \"" //$NON-NLS-1$
        + "r=" + colorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + colorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + colorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "PreferencesDialog.ColorStartStateR", //$NON-NLS-1$
        colorItem.getColor ().getRed () );
    this.preferences.putInt ( "PreferencesDialog.ColorStartStateG", //$NON-NLS-1$
        colorItem.getColor ().getGreen () );
    this.preferences.putInt ( "PreferencesDialog.ColorStartStateB", //$NON-NLS-1$
        colorItem.getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the {@link State}.
   * 
   * @param colorItem The {@link ColorItem} of the {@link State}.
   */
  public final void setColorItemState ( ColorItem colorItem )
  {
    logger.debug ( "set color of the state to \"" //$NON-NLS-1$
        + "r=" + colorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + colorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + colorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "PreferencesDialog.ColorStateR", colorItem //$NON-NLS-1$
        .getColor ().getRed () );
    this.preferences.putInt ( "PreferencesDialog.ColorStateG", colorItem //$NON-NLS-1$
        .getColor ().getGreen () );
    this.preferences.putInt ( "PreferencesDialog.ColorStateB", colorItem //$NON-NLS-1$
        .getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the {@link Symbol}.
   * 
   * @param colorItem The {@link ColorItem} of the {@link Symbol}.
   */
  public final void setColorItemSymbol ( ColorItem colorItem )
  {
    logger.debug ( "set color of the symbol to \"" //$NON-NLS-1$
        + "r=" + colorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + colorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + colorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "PreferencesDialog.ColorSymbolR", colorItem //$NON-NLS-1$
        .getColor ().getRed () );
    this.preferences.putInt ( "PreferencesDialog.ColorSymbolG", colorItem //$NON-NLS-1$
        .getColor ().getGreen () );
    this.preferences.putInt ( "PreferencesDialog.ColorSymbolB", colorItem //$NON-NLS-1$
        .getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the {@link Transition}.
   * 
   * @param colorItem The {@link ColorItem} of the {@link Transition}.
   */
  public final void setColorItemTransition ( ColorItem colorItem )
  {
    logger.debug ( "set color of the transition to \"" //$NON-NLS-1$
        + "r=" + colorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + colorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + colorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "PreferencesDialog.ColorTransitionR", colorItem //$NON-NLS-1$
        .getColor ().getRed () );
    this.preferences.putInt ( "PreferencesDialog.ColorTransitionG", colorItem //$NON-NLS-1$
        .getColor ().getGreen () );
    this.preferences.putInt ( "PreferencesDialog.ColorTransitionB", colorItem //$NON-NLS-1$
        .getColor ().getBlue () );
  }
}
