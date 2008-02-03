package de.unisiegen.gtitool.core.preferences;


import java.awt.Color;
import java.util.prefs.Preferences;

import javax.swing.event.EventListenerList;

import org.apache.log4j.Logger;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.preferences.item.ColorItem;
import de.unisiegen.gtitool.core.preferences.listener.ColorChangedListener;


/**
 * Manages the preferences for the core project.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class PreferenceManager
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
  public static final Color DEFAULT_STATE_ACTIVE_COLOR = new Color ( 0, 255, 0 );


  /**
   * The default {@link Color} of the active {@link Transition}.
   */
  public static final Color DEFAULT_TRANSITION_ACTIVE_COLOR = new Color ( 0,
      255, 0 );


  /**
   * The default {@link Color} of the active {@link Symbol}.
   */
  public static final Color DEFAULT_SYMBOL_ACTIVE_COLOR = new Color ( 0, 255, 0 );


  /**
   * The default {@link Color} of the error {@link State}.
   */
  public static final Color DEFAULT_STATE_ERROR_COLOR = new Color ( 255, 0, 0 );


  /**
   * The default {@link Color} of the selected {@link State}.
   */
  public static final Color DEFAULT_STATE_SELECTED_COLOR = new Color ( 255, 0,
      0 );


  /**
   * The default {@link Color} of the selected {@link Transition}.
   */
  public static final Color DEFAULT_TRANSITION_SELECTED_COLOR = new Color (
      255, 0, 0 );


  /**
   * The default {@link Color} of the start {@link State}.
   */
  public static final Color DEFAULT_STATE_START_COLOR = new Color ( 255, 255,
      255 );


  /**
   * The default {@link Color} of a {@link State}.
   */
  public static final Color DEFAULT_STATE_BACKGROUND_COLOR = new Color ( 255,
      255, 255 );


  /**
   * The default {@link Color} of a error {@link Symbol}.
   */
  public static final Color DEFAULT_SYMBOL_ERROR_COLOR = new Color ( 255, 0, 0 );


  /**
   * The default {@link Color} of a {@link Transition}.
   */
  public static final Color DEFAULT_TRANSITION_COLOR = new Color ( 0, 0, 0 );


  /**
   * The default {@link Color} of a error {@link Transition}.
   */
  public static final Color DEFAULT_TRANSITION_ERROR_COLOR = new Color ( 255,
      0, 0 );


  /**
   * The default {@link Color} of a parser warning.
   */
  public static final Color DEFAULT_STATE_COLOR = new Color ( 0, 0, 127 );


  /**
   * The default {@link Color} of a parser warning.
   */
  public static final Color DEFAULT_SYMBOL_COLOR = new Color ( 0, 0, 127 );


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
   * The default {@link Alphabet}.
   */
  public static Alphabet DEFAULT_ALPHABET;


  /**
   * The default push down {@link Alphabet}.
   */
  public static Alphabet DEFAULT_PUSH_DOWN_ALPHABET;

  static
  {
    try
    {
      DEFAULT_ALPHABET = new DefaultAlphabet (
          new DefaultSymbol ( "0" ), new DefaultSymbol ( "1" ) ); //$NON-NLS-1$ //$NON-NLS-2$
      DEFAULT_PUSH_DOWN_ALPHABET = new DefaultAlphabet ( new DefaultSymbol (
          "0" ), new DefaultSymbol ( "1" ) ); //$NON-NLS-1$ //$NON-NLS-2$
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( SymbolException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }


  /**
   * Returns the single instance of the <code>PreferenceManager</code>.
   * 
   * @return The single instance of the <code>PreferenceManager</code>.
   */
  public static PreferenceManager getInstance ()
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
  protected Preferences preferences;


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * Allocates a new <code>PreferencesManager</code>.
   */
  protected PreferenceManager ()
  {
    this.preferences = Preferences
        .userNodeForPackage ( PreferenceManager.class );
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
   * Let the listeners know that the color of the active {@link State} has
   * changed.
   * 
   * @param newColor The new color of the active {@link State}.
   */
  public final void fireColorChangedStateActive ( Color newColor )
  {
    ColorChangedListener [] listeners = this.listenerList
        .getListeners ( ColorChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].colorChangedStateActive ( newColor );
    }
  }


  /**
   * Let the listeners know that the background color of the {@link State} has
   * changed.
   * 
   * @param newColor The new background color of the {@link State}.
   */
  public final void fireColorChangedStateBackground ( Color newColor )
  {
    ColorChangedListener [] listeners = this.listenerList
        .getListeners ( ColorChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].colorChangedStateBackground ( newColor );
    }
  }


  /**
   * Let the listeners know that the color of the error {@link State} has
   * changed.
   * 
   * @param newColor The new color of the error {@link State}.
   */
  public final void fireColorChangedStateError ( Color newColor )
  {
    ColorChangedListener [] listeners = this.listenerList
        .getListeners ( ColorChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].colorChangedStateError ( newColor );
    }
  }


  /**
   * Let the listeners know that the color of the selected {@link State} has
   * changed.
   * 
   * @param newColor The new color of the selected {@link State}.
   */
  public final void fireColorChangedStateSelected ( Color newColor )
  {
    ColorChangedListener [] listeners = this.listenerList
        .getListeners ( ColorChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].colorChangedStateSelected ( newColor );
    }
  }


  /**
   * Let the listeners know that the color of the start {@link State} has
   * changed.
   * 
   * @param newColor The new color of the start {@link State}.
   */
  public final void fireColorChangedStateStart ( Color newColor )
  {
    ColorChangedListener [] listeners = this.listenerList
        .getListeners ( ColorChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].colorChangedStateStart ( newColor );
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
   * Let the listeners know that the color of the active {@link Symbol} has
   * changed.
   * 
   * @param newColor The new color of the active {@link Symbol}.
   */
  public final void fireColorChangedSymbolActive ( Color newColor )
  {
    ColorChangedListener [] listeners = this.listenerList
        .getListeners ( ColorChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].colorChangedSymbolActive ( newColor );
    }
  }


  /**
   * Let the listeners know that the color of the error {@link Symbol} has
   * changed.
   * 
   * @param newColor The new color of the error {@link Symbol}.
   */
  public final void fireColorChangedSymbolError ( Color newColor )
  {
    ColorChangedListener [] listeners = this.listenerList
        .getListeners ( ColorChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].colorChangedSymbolError ( newColor );
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
   * Let the listeners know that the color of the active {@link Transition} has
   * changed.
   * 
   * @param newColor The new color of the active {@link Transition}.
   */
  public final void fireColorChangedTransitionActive ( Color newColor )
  {
    ColorChangedListener [] listeners = this.listenerList
        .getListeners ( ColorChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].colorChangedTransitionActive ( newColor );
    }
  }


  /**
   * Let the listeners know that the color of the error {@link Transition} has
   * changed.
   * 
   * @param newColor The new color of the error {@link Transition}.
   */
  public final void fireColorChangedTransitionError ( Color newColor )
  {
    ColorChangedListener [] listeners = this.listenerList
        .getListeners ( ColorChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].colorChangedTransitionError ( newColor );
    }
  }


  /**
   * Let the listeners know that the color of the selected {@link Transition}
   * has changed.
   * 
   * @param newColor The new color of the selected {@link Transition}.
   */
  public final void fireColorChangedTransitionSelected ( Color newColor )
  {
    ColorChangedListener [] listeners = this.listenerList
        .getListeners ( ColorChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].colorChangedTransitionSelected ( newColor );
    }
  }


  /**
   * Returns the {@link ColorItem} of the parser highlighting.
   * 
   * @return The {@link ColorItem} of the parser highlighting.
   */
  public final ColorItem getColorItemParserHighlighting ()
  {
    int r = this.preferences.getInt ( "Preferences.ColorParserHighlightingR", //$NON-NLS-1$
        DEFAULT_PARSER_HIGHLIGHTING_COLOR.getRed () );
    int g = this.preferences.getInt ( "Preferences.ColorParserHighlightingG", //$NON-NLS-1$
        DEFAULT_PARSER_HIGHLIGHTING_COLOR.getGreen () );
    int b = this.preferences.getInt ( "Preferences.ColorParserHighlightingB", //$NON-NLS-1$
        DEFAULT_PARSER_HIGHLIGHTING_COLOR.getBlue () );
    String caption = Messages
        .getString ( "Preferences.ColorParserHighlightingCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorParserHighlightingDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DEFAULT_PARSER_HIGHLIGHTING_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the parser warning.
   * 
   * @return The {@link ColorItem} of the parser warning.
   */
  public final ColorItem getColorItemParserWarning ()
  {
    int r = this.preferences.getInt ( "Preferences.ColorParserWarningR", //$NON-NLS-1$
        DEFAULT_PARSER_WARNING_COLOR.getRed () );
    int g = this.preferences.getInt ( "Preferences.ColorParserWarningG", //$NON-NLS-1$
        DEFAULT_PARSER_WARNING_COLOR.getGreen () );
    int b = this.preferences.getInt ( "Preferences.ColorParserWarningB", //$NON-NLS-1$
        DEFAULT_PARSER_WARNING_COLOR.getBlue () );
    String caption = Messages
        .getString ( "Preferences.ColorParserWarningCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorParserWarningDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DEFAULT_PARSER_WARNING_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the {State}.
   * 
   * @return The {@link ColorItem} of the {State}.
   */
  public final ColorItem getColorItemState ()
  {
    int r = this.preferences.getInt ( "Preferences.ColorStateR", //$NON-NLS-1$
        DEFAULT_STATE_COLOR.getRed () );
    int g = this.preferences.getInt ( "Preferences.ColorStateG", //$NON-NLS-1$
        DEFAULT_STATE_COLOR.getGreen () );
    int b = this.preferences.getInt ( "Preferences.ColorStateB", //$NON-NLS-1$
        DEFAULT_STATE_COLOR.getBlue () );
    String caption = Messages.getString ( "Preferences.ColorStateCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorStateDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DEFAULT_STATE_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the active {@link State}.
   * 
   * @return The {@link ColorItem} of the active {@link State}.
   */
  public final ColorItem getColorItemStateActive ()
  {
    int r = this.preferences.getInt ( "Preferences.ColorStateActiveR", //$NON-NLS-1$
        DEFAULT_STATE_ACTIVE_COLOR.getRed () );
    int g = this.preferences.getInt ( "Preferences.ColorStateActiveG", //$NON-NLS-1$
        DEFAULT_STATE_ACTIVE_COLOR.getGreen () );
    int b = this.preferences.getInt ( "Preferences.ColorStateActiveB", //$NON-NLS-1$
        DEFAULT_STATE_ACTIVE_COLOR.getBlue () );
    String caption = Messages
        .getString ( "Preferences.ColorStateActiveCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorStateActiveDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DEFAULT_STATE_ACTIVE_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the {@link State} background.
   * 
   * @return The {@link ColorItem} of the {@link State} background.
   */
  public final ColorItem getColorItemStateBackground ()
  {
    int r = this.preferences.getInt ( "Preferences.ColorStateBackgroundR", //$NON-NLS-1$
        DEFAULT_STATE_BACKGROUND_COLOR.getRed () );
    int g = this.preferences.getInt ( "Preferences.ColorStateBackgroundG", //$NON-NLS-1$
        DEFAULT_STATE_BACKGROUND_COLOR.getGreen () );
    int b = this.preferences.getInt ( "Preferences.ColorStateBackgroundB", //$NON-NLS-1$
        DEFAULT_STATE_BACKGROUND_COLOR.getBlue () );
    String caption = Messages
        .getString ( "Preferences.ColorStateBackgroundCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorStateBackgroundDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DEFAULT_STATE_BACKGROUND_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the error {@link State}.
   * 
   * @return The {@link ColorItem} of the error {@link State}.
   */
  public final ColorItem getColorItemStateError ()
  {
    int r = this.preferences.getInt ( "Preferences.ColorStateErrorR", //$NON-NLS-1$
        DEFAULT_STATE_ERROR_COLOR.getRed () );
    int g = this.preferences.getInt ( "Preferences.ColorStateErrorG", //$NON-NLS-1$
        DEFAULT_STATE_ERROR_COLOR.getGreen () );
    int b = this.preferences.getInt ( "Preferences.ColorStateErrorB", //$NON-NLS-1$
        DEFAULT_STATE_ERROR_COLOR.getBlue () );
    String caption = Messages.getString ( "Preferences.ColorStateErrorCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorStateErrorDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DEFAULT_STATE_ERROR_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the selected {@link State}.
   * 
   * @return The {@link ColorItem} of the selected {@link State}.
   */
  public final ColorItem getColorItemStateSelected ()
  {
    int r = this.preferences.getInt ( "Preferences.ColorStateSelectedR", //$NON-NLS-1$
        DEFAULT_STATE_SELECTED_COLOR.getRed () );
    int g = this.preferences.getInt ( "Preferences.ColorStateSelectedG", //$NON-NLS-1$
        DEFAULT_STATE_SELECTED_COLOR.getGreen () );
    int b = this.preferences.getInt ( "Preferences.ColorStateSelectedB", //$NON-NLS-1$
        DEFAULT_STATE_SELECTED_COLOR.getBlue () );
    String caption = Messages
        .getString ( "Preferences.ColorStateSelectedCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorStateSelectedDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DEFAULT_STATE_SELECTED_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the start {@link State}.
   * 
   * @return The {@link ColorItem} of the start {@link State}.
   */
  public final ColorItem getColorItemStateStart ()
  {
    int r = this.preferences.getInt ( "Preferences.ColorStateStartR", //$NON-NLS-1$
        DEFAULT_STATE_START_COLOR.getRed () );
    int g = this.preferences.getInt ( "Preferences.ColorStateStartG", //$NON-NLS-1$
        DEFAULT_STATE_START_COLOR.getGreen () );
    int b = this.preferences.getInt ( "Preferences.ColorStateStartB", //$NON-NLS-1$
        DEFAULT_STATE_START_COLOR.getBlue () );
    String caption = Messages.getString ( "Preferences.ColorStateStartCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorStateStartDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DEFAULT_STATE_START_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the {Symbol}.
   * 
   * @return The {@link ColorItem} of the {Symbol}.
   */
  public final ColorItem getColorItemSymbol ()
  {
    int r = this.preferences.getInt ( "Preferences.ColorSymbolR", //$NON-NLS-1$
        DEFAULT_SYMBOL_COLOR.getRed () );
    int g = this.preferences.getInt ( "Preferences.ColorSymbolG", //$NON-NLS-1$
        DEFAULT_SYMBOL_COLOR.getGreen () );
    int b = this.preferences.getInt ( "Preferences.ColorSymbolB", //$NON-NLS-1$
        DEFAULT_SYMBOL_COLOR.getBlue () );
    String caption = Messages.getString ( "Preferences.ColorSymbolCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorSymbolDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DEFAULT_SYMBOL_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the active {@link Symbol}.
   * 
   * @return The {@link ColorItem} of the active {@link Symbol}.
   */
  public final ColorItem getColorItemSymbolActive ()
  {
    int r = this.preferences.getInt ( "Preferences.ColorSymbolActiveR", //$NON-NLS-1$
        DEFAULT_SYMBOL_ACTIVE_COLOR.getRed () );
    int g = this.preferences.getInt ( "Preferences.ColorSymbolActiveG", //$NON-NLS-1$
        DEFAULT_SYMBOL_ACTIVE_COLOR.getGreen () );
    int b = this.preferences.getInt ( "Preferences.ColorSymbolActiveB", //$NON-NLS-1$
        DEFAULT_SYMBOL_ACTIVE_COLOR.getBlue () );
    String caption = Messages
        .getString ( "Preferences.ColorSymbolActiveCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorSymbolActiveDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DEFAULT_SYMBOL_ACTIVE_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the error {@link Symbol}.
   * 
   * @return The {@link ColorItem} of the error {@link Symbol}.
   */
  public final ColorItem getColorItemSymbolError ()
  {
    int r = this.preferences.getInt ( "Preferences.ColorSymbolErrorR", //$NON-NLS-1$
        DEFAULT_SYMBOL_ERROR_COLOR.getRed () );
    int g = this.preferences.getInt ( "Preferences.ColorSymbolErrorG", //$NON-NLS-1$
        DEFAULT_SYMBOL_ERROR_COLOR.getGreen () );
    int b = this.preferences.getInt ( "Preferences.ColorSymbolErrorB", //$NON-NLS-1$
        DEFAULT_SYMBOL_ERROR_COLOR.getBlue () );
    String caption = Messages
        .getString ( "Preferences.ColorSymbolErrorCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorSymbolErrorDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DEFAULT_SYMBOL_ERROR_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the {@link Transition}.
   * 
   * @return The {@link ColorItem} of the {@link Transition}.
   */
  public final ColorItem getColorItemTransition ()
  {
    int r = this.preferences.getInt ( "Preferences.ColorTransitionR", //$NON-NLS-1$
        DEFAULT_TRANSITION_COLOR.getRed () );
    int g = this.preferences.getInt ( "Preferences.ColorTransitionG", //$NON-NLS-1$
        DEFAULT_TRANSITION_COLOR.getGreen () );
    int b = this.preferences.getInt ( "Preferences.ColorTransitionB", //$NON-NLS-1$
        DEFAULT_TRANSITION_COLOR.getBlue () );
    String caption = Messages.getString ( "Preferences.ColorTransitionCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorTransitionDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DEFAULT_TRANSITION_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the active {@link Transition}.
   * 
   * @return The {@link ColorItem} of the active {@link Transition}.
   */
  public final ColorItem getColorItemTransitionActive ()
  {
    int r = this.preferences.getInt ( "Preferences.ColorTransitionActiveR", //$NON-NLS-1$
        DEFAULT_TRANSITION_ACTIVE_COLOR.getRed () );
    int g = this.preferences.getInt ( "Preferences.ColorTransitionActiveG", //$NON-NLS-1$
        DEFAULT_TRANSITION_ACTIVE_COLOR.getGreen () );
    int b = this.preferences.getInt ( "Preferences.ColorTransitionActiveB", //$NON-NLS-1$
        DEFAULT_TRANSITION_ACTIVE_COLOR.getBlue () );
    String caption = Messages
        .getString ( "Preferences.ColorTransitionActiveCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorTransitionActiveDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DEFAULT_TRANSITION_ACTIVE_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the error {@link Transition}.
   * 
   * @return The {@link ColorItem} of the error {@link Transition}.
   */
  public final ColorItem getColorItemTransitionError ()
  {
    int r = this.preferences.getInt ( "Preferences.ColorTransitionErrorR", //$NON-NLS-1$
        DEFAULT_TRANSITION_ERROR_COLOR.getRed () );
    int g = this.preferences.getInt ( "Preferences.ColorTransitionErrorG", //$NON-NLS-1$
        DEFAULT_TRANSITION_ERROR_COLOR.getGreen () );
    int b = this.preferences.getInt ( "Preferences.ColorTransitionErrorB", //$NON-NLS-1$
        DEFAULT_TRANSITION_ERROR_COLOR.getBlue () );
    String caption = Messages
        .getString ( "Preferences.ColorTransitionErrorCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorTransitionErrorDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DEFAULT_TRANSITION_ERROR_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the selected {@link Transition}.
   * 
   * @return The {@link ColorItem} of the selected {@link Transition}.
   */
  public final ColorItem getColorItemTransitionSelected ()
  {
    int r = this.preferences.getInt ( "Preferences.ColorTransitionSelectedR", //$NON-NLS-1$
        DEFAULT_TRANSITION_SELECTED_COLOR.getRed () );
    int g = this.preferences.getInt ( "Preferences.ColorTransitionSelectedG", //$NON-NLS-1$
        DEFAULT_TRANSITION_SELECTED_COLOR.getGreen () );
    int b = this.preferences.getInt ( "Preferences.ColorTransitionSelectedB", //$NON-NLS-1$
        DEFAULT_TRANSITION_SELECTED_COLOR.getBlue () );
    String caption = Messages
        .getString ( "Preferences.ColorTransitionSelectedCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorTransitionSelectedDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DEFAULT_TRANSITION_SELECTED_COLOR );
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
    this.preferences.putInt ( "Preferences.ColorParserHighlightingR", colorItem //$NON-NLS-1$
        .getColor ().getRed () );
    this.preferences.putInt ( "Preferences.ColorParserHighlightingG", colorItem //$NON-NLS-1$
        .getColor ().getGreen () );
    this.preferences.putInt ( "Preferences.ColorParserHighlightingB", colorItem //$NON-NLS-1$
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
    this.preferences.putInt ( "Preferences.ColorParserWarningR", colorItem //$NON-NLS-1$
        .getColor ().getRed () );
    this.preferences.putInt ( "Preferences.ColorParserWarningG", colorItem //$NON-NLS-1$
        .getColor ().getGreen () );
    this.preferences.putInt ( "Preferences.ColorParserWarningB", colorItem //$NON-NLS-1$
        .getColor ().getBlue () );
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
    this.preferences.putInt ( "Preferences.ColorStateR", colorItem //$NON-NLS-1$
        .getColor ().getRed () );
    this.preferences.putInt ( "Preferences.ColorStateG", colorItem //$NON-NLS-1$
        .getColor ().getGreen () );
    this.preferences.putInt ( "Preferences.ColorStateB", colorItem //$NON-NLS-1$
        .getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the active {@link State}.
   * 
   * @param colorItem The {@link ColorItem} of the active {@link State}.
   */
  public final void setColorItemStateActive ( ColorItem colorItem )
  {
    logger.debug ( "set color of the avtive state to \"" //$NON-NLS-1$
        + "r=" + colorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + colorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + colorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "Preferences.ColorStateActiveR", //$NON-NLS-1$
        colorItem.getColor ().getRed () );
    this.preferences.putInt ( "Preferences.ColorStateActiveG", //$NON-NLS-1$
        colorItem.getColor ().getGreen () );
    this.preferences.putInt ( "Preferences.ColorStateActiveB", //$NON-NLS-1$
        colorItem.getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the {@link State} background.
   * 
   * @param colorItem The {@link ColorItem} of the {@link State} background.
   */
  public final void setColorItemStateBackground ( ColorItem colorItem )
  {
    logger.debug ( "set background color of the state to \"" //$NON-NLS-1$
        + "r=" + colorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + colorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + colorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "Preferences.ColorStateBackgroundR", colorItem //$NON-NLS-1$
        .getColor ().getRed () );
    this.preferences.putInt ( "Preferences.ColorStateBackgroundG", colorItem //$NON-NLS-1$
        .getColor ().getGreen () );
    this.preferences.putInt ( "Preferences.ColorStateBackgroundB", colorItem //$NON-NLS-1$
        .getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the error {@link State}.
   * 
   * @param colorItem The {@link ColorItem} of the error {@link State}.
   */
  public final void setColorItemStateError ( ColorItem colorItem )
  {
    logger.debug ( "set color of the error state to \"" //$NON-NLS-1$
        + "r=" + colorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + colorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + colorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "Preferences.ColorStateErrorR", //$NON-NLS-1$
        colorItem.getColor ().getRed () );
    this.preferences.putInt ( "Preferences.ColorStateErrorG", //$NON-NLS-1$
        colorItem.getColor ().getGreen () );
    this.preferences.putInt ( "Preferences.ColorStateErrorB", //$NON-NLS-1$
        colorItem.getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the selected {@link State}.
   * 
   * @param colorItem The {@link ColorItem} of the selected {@link State}.
   */
  public final void setColorItemStateSelected ( ColorItem colorItem )
  {
    logger.debug ( "set color of the selected state to \"" //$NON-NLS-1$
        + "r=" + colorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + colorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + colorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "Preferences.ColorStateSelectedR", //$NON-NLS-1$
        colorItem.getColor ().getRed () );
    this.preferences.putInt ( "Preferences.ColorStateSelectedG", //$NON-NLS-1$
        colorItem.getColor ().getGreen () );
    this.preferences.putInt ( "Preferences.ColorStateSelectedB", //$NON-NLS-1$
        colorItem.getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the start {@link State}.
   * 
   * @param colorItem The {@link ColorItem} of the start {@link State}.
   */
  public final void setColorItemStateStart ( ColorItem colorItem )
  {
    logger.debug ( "set color of the start state to \"" //$NON-NLS-1$
        + "r=" + colorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + colorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + colorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "Preferences.ColorStateStartR", //$NON-NLS-1$
        colorItem.getColor ().getRed () );
    this.preferences.putInt ( "Preferences.ColorStateStartG", //$NON-NLS-1$
        colorItem.getColor ().getGreen () );
    this.preferences.putInt ( "Preferences.ColorStateStartB", //$NON-NLS-1$
        colorItem.getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the parser {@link Symbol}.
   * 
   * @param colorItem The {@link ColorItem} of the parser {@link Symbol}.
   */
  public final void setColorItemSymbol ( ColorItem colorItem )
  {
    logger.debug ( "set color of the symbol to \"" //$NON-NLS-1$
        + "r=" + colorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + colorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + colorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "Preferences.ColorSymbolR", colorItem //$NON-NLS-1$
        .getColor ().getRed () );
    this.preferences.putInt ( "Preferences.ColorSymbolG", colorItem //$NON-NLS-1$
        .getColor ().getGreen () );
    this.preferences.putInt ( "Preferences.ColorSymbolB", colorItem //$NON-NLS-1$
        .getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the active {@link Symbol}.
   * 
   * @param colorItem The {@link ColorItem} of the active {@link Symbol}.
   */
  public final void setColorItemSymbolActive ( ColorItem colorItem )
  {
    logger.debug ( "set color of the active symbol to \"" //$NON-NLS-1$
        + "r=" + colorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + colorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + colorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "Preferences.ColorSymbolActiveR", //$NON-NLS-1$
        colorItem.getColor ().getRed () );
    this.preferences.putInt ( "Preferences.ColorSymbolActiveG", //$NON-NLS-1$
        colorItem.getColor ().getGreen () );
    this.preferences.putInt ( "Preferences.ColorSymbolActiveB", //$NON-NLS-1$
        colorItem.getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the error {@link Symbol}.
   * 
   * @param colorItem The {@link ColorItem} of the error {@link Symbol}.
   */
  public final void setColorItemSymbolError ( ColorItem colorItem )
  {
    logger.debug ( "set color of the error symbol to \"" //$NON-NLS-1$
        + "r=" + colorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + colorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + colorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "Preferences.ColorSymbolErrorR", colorItem //$NON-NLS-1$
        .getColor ().getRed () );
    this.preferences.putInt ( "Preferences.ColorSymbolErrorG", colorItem //$NON-NLS-1$
        .getColor ().getGreen () );
    this.preferences.putInt ( "Preferences.ColorSymbolErrorB", colorItem //$NON-NLS-1$
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
    this.preferences.putInt ( "Preferences.ColorTransitionR", colorItem //$NON-NLS-1$
        .getColor ().getRed () );
    this.preferences.putInt ( "Preferences.ColorTransitionG", colorItem //$NON-NLS-1$
        .getColor ().getGreen () );
    this.preferences.putInt ( "Preferences.ColorTransitionB", colorItem //$NON-NLS-1$
        .getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the active {@link Transition}.
   * 
   * @param colorItem The {@link ColorItem} of the active {@link Transition}.
   */
  public final void setColorItemTransitionActive ( ColorItem colorItem )
  {
    logger.debug ( "set color of the active transition to \"" //$NON-NLS-1$
        + "r=" + colorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + colorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + colorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "Preferences.ColorTransitionActiveR", //$NON-NLS-1$
        colorItem.getColor ().getRed () );
    this.preferences.putInt ( "Preferences.ColorTransitionActiveG", //$NON-NLS-1$
        colorItem.getColor ().getGreen () );
    this.preferences.putInt ( "Preferences.ColorTransitionActiveB", //$NON-NLS-1$
        colorItem.getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the error {@link Transition}.
   * 
   * @param colorItem The {@link ColorItem} of the error {@link Transition}.
   */
  public final void setColorItemTransitionError ( ColorItem colorItem )
  {
    logger.debug ( "set color of the error transition to \"" //$NON-NLS-1$
        + "r=" + colorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + colorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + colorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "Preferences.ColorTransitionErrorR", colorItem //$NON-NLS-1$
        .getColor ().getRed () );
    this.preferences.putInt ( "Preferences.ColorTransitionErrorG", colorItem //$NON-NLS-1$
        .getColor ().getGreen () );
    this.preferences.putInt ( "Preferences.ColorTransitionErrorB", colorItem //$NON-NLS-1$
        .getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the selected {@link Transition}.
   * 
   * @param colorItem The {@link ColorItem} of the selected {@link Transition}.
   */
  public final void setColorItemTransitionSelected ( ColorItem colorItem )
  {
    logger.debug ( "set color of the selected transition to \"" //$NON-NLS-1$
        + "r=" + colorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + colorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + colorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "Preferences.ColorTransitionSelectedR", //$NON-NLS-1$
        colorItem.getColor ().getRed () );
    this.preferences.putInt ( "Preferences.ColorTransitionSelectedG", //$NON-NLS-1$
        colorItem.getColor ().getGreen () );
    this.preferences.putInt ( "Preferences.ColorTransitionSelectedB", //$NON-NLS-1$
        colorItem.getColor ().getBlue () );
  }
}
