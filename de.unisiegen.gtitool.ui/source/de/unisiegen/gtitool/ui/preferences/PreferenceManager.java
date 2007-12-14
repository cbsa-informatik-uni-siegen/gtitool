package de.unisiegen.gtitool.ui.preferences;


import java.awt.Color;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.preferences.DefaultValues;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.logic.MainWindow;
import de.unisiegen.gtitool.ui.logic.PreferencesDialog;
import de.unisiegen.gtitool.ui.preferences.item.AlphabetItem;
import de.unisiegen.gtitool.ui.preferences.item.ColorItem;
import de.unisiegen.gtitool.ui.preferences.item.LanguageItem;
import de.unisiegen.gtitool.ui.preferences.item.LookAndFeelItem;
import de.unisiegen.gtitool.ui.preferences.item.OpenedFilesItem;
import de.unisiegen.gtitool.ui.preferences.item.RecentlyUsedFilesItem;
import de.unisiegen.gtitool.ui.preferences.item.ZoomFactorItem;
import de.unisiegen.gtitool.ui.preferences.listener.ColorChangedListener;
import de.unisiegen.gtitool.ui.preferences.listener.LanguageChangedListener;
import de.unisiegen.gtitool.ui.preferences.listener.ZoomFactorChangedListener;


/**
 * Manages the preferences for the user interface.
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
   * The default width of the {@link MainWindow}.
   */
  public static int DEFAULT_WIDTH = 800;


  /**
   * The default hight of the {@link MainWindow}.
   */
  public static int DEFAULT_HEIGHT = 600;


  /**
   * The default hight of the {@link MainWindow}.
   */
  public static Alphabet DEFAULT_ALPHABET;


  /**
   * The default language title.
   */
  public static final String DEFAULT_LANGUAGE_TITLE = "Default"; //$NON-NLS-1$


  /**
   * The default language language.
   */
  public static final String DEFAULT_LANGUAGE_LANGUAGE = Locale.getDefault ()
      .getLanguage ();


  /**
   * The default look and feel name.
   */
  public static final String DEFAULT_LOOK_AND_FEEL_NAME = "System"; //$NON-NLS-1$


  /**
   * The default look and feel class name.
   */
  public static final String DEFAULT_LOOK_AND_FEEL_CLASS_NAME = UIManager
      .getSystemLookAndFeelClassName ();


  /**
   * The default x position of the {@link MainWindow}.
   */
  public static int DEFAULT_POSITION_X = ( Toolkit.getDefaultToolkit ()
      .getScreenSize ().width - DEFAULT_WIDTH ) / 2;


  /**
   * The default y position of the {@link MainWindow}.
   */
  public static int DEFAULT_POSITION_Y = ( Toolkit.getDefaultToolkit ()
      .getScreenSize ().height - DEFAULT_HEIGHT ) / 2;


  /**
   * The default maximized state of the {@link MainWindow}.
   */
  public static boolean DEFAULT_MAXIMIZED = false;


  /**
   * The default console divider location.
   */
  public static int DEFAULT_DIVIDER_LOCATION_CONSOLE = -1;


  /**
   * The default table divider location.
   */
  public static int DEFAULT_DIVIDER_LOCATION_TABLE = -1;


  /**
   * The default preference dialog last active tab.
   */
  public static int DEFAULT_PREFERENCES_DIALOG_LAST_ACTIVE_TAB = 0;


  /**
   * The default working path.
   */
  public static String DEFAULT_WORKING_PATH = "."; //$NON-NLS-1$


  /**
   * The default zoom factor value.
   */
  public static int DEFAULT_ZOOM_FACTOR = 100;

  static
  {
    try
    {
      DEFAULT_ALPHABET = new Alphabet ( new Symbol ( "0" ), new Symbol ( "1" ) ); //$NON-NLS-1$ //$NON-NLS-2$
    }
    catch ( AlphabetException e )
    {
      e.printStackTrace ();
    }
    catch ( SymbolException e )
    {
      e.printStackTrace ();
    }
  }


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
   * The list of {@link LanguageChangedListener}.
   */
  private ArrayList < LanguageChangedListener > languageChangedListenerList = new ArrayList < LanguageChangedListener > ();


  /**
   * The list of {@link ColorChangedListener}.
   */
  private ArrayList < ColorChangedListener > colorChangedListenerList = new ArrayList < ColorChangedListener > ();


  /**
   * The list of {@link ZoomFactorChangedListener}.
   */
  private ArrayList < ZoomFactorChangedListener > zoomFactorChangedListenerList = new ArrayList < ZoomFactorChangedListener > ();


  /**
   * The system {@link Locale}.
   */
  private Locale systemLocale;


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
   * @param pListener The {@link ColorChangedListener}.
   */
  public final synchronized void addColorChangedListener (
      ColorChangedListener pListener )
  {
    this.colorChangedListenerList.add ( pListener );
  }


  /**
   * Adds the given {@link LanguageChangedListener}.
   * 
   * @param pListener The {@link LanguageChangedListener}.
   */
  public final synchronized void addLanguageChangedListener (
      LanguageChangedListener pListener )
  {
    this.languageChangedListenerList.add ( pListener );
  }


  /**
   * Adds the given {@link ZoomFactorChangedListener}.
   * 
   * @param pListener The {@link ZoomFactorChangedListener}.
   */
  public final synchronized void addZoomFactorChangedListener (
      ZoomFactorChangedListener pListener )
  {
    this.zoomFactorChangedListenerList.add ( pListener );
  }


  /**
   * Let the listeners know that the color of the active {@link State} has
   * changed.
   * 
   * @param pNewColor The new color of the active {@link State}.
   */
  public final void fireColorChangedActiveState ( Color pNewColor )
  {
    for ( ColorChangedListener current : this.colorChangedListenerList )
    {
      current.colorChangedActiveState ( pNewColor );
    }
  }


  /**
   * Let the listeners know that the color of the error {@link State} has
   * changed.
   * 
   * @param pNewColor The new color of the error {@link State}.
   */
  public final void fireColorChangedErrorState ( Color pNewColor )
  {
    for ( ColorChangedListener current : this.colorChangedListenerList )
    {
      current.colorChangedErrorState ( pNewColor );
    }
  }


  /**
   * Let the listeners know that the color of the error {@link Symbol} has
   * changed.
   * 
   * @param pNewColor The new color of the error {@link Symbol}.
   */
  public final void fireColorChangedErrorSymbol ( Color pNewColor )
  {
    for ( ColorChangedListener current : this.colorChangedListenerList )
    {
      current.colorChangedErrorSymbol ( pNewColor );
    }
  }


  /**
   * Let the listeners know that the color of the error {@link Transition} has
   * changed.
   * 
   * @param pNewColor The new color of the error {@link Transition}.
   */
  public final void fireColorChangedErrorTransition ( Color pNewColor )
  {
    for ( ColorChangedListener current : this.colorChangedListenerList )
    {
      current.colorChangedErrorTransition ( pNewColor );
    }
  }


  /**
   * Let the listeners know that the color of the parser {@link State} has
   * changed.
   * 
   * @param pNewColor The new color of the parser {@link State}.
   */
  public final void fireColorChangedParserState ( Color pNewColor )
  {
    for ( ColorChangedListener current : this.colorChangedListenerList )
    {
      current.colorChangedParserState ( pNewColor );
    }
  }


  /**
   * Let the listeners know that the color of the parser {@link Symbol} has
   * changed.
   * 
   * @param pNewColor The new color of the parser {@link Symbol}.
   */
  public final void fireColorChangedParserSymbol ( Color pNewColor )
  {
    for ( ColorChangedListener current : this.colorChangedListenerList )
    {
      current.colorChangedParserSymbol ( pNewColor );
    }
  }


  /**
   * Let the listeners know that the color of the parser warning has changed.
   * 
   * @param pNewColor The new color of the parser warning.
   */
  public final void fireColorChangedParserWarning ( Color pNewColor )
  {
    for ( ColorChangedListener current : this.colorChangedListenerList )
    {
      current.colorChangedParserWarning ( pNewColor );
    }
  }


  /**
   * Let the listeners know that the color of the selected {@link State} has
   * changed.
   * 
   * @param pNewColor The new color of the selected {@link State}.
   */
  public final void fireColorChangedSelectedState ( Color pNewColor )
  {
    for ( ColorChangedListener current : this.colorChangedListenerList )
    {
      current.colorChangedSelectedState ( pNewColor );
    }
  }


  /**
   * Let the listeners know that the color of the start {@link State} has
   * changed.
   * 
   * @param pNewColor The new color of the start {@link State}.
   */
  public final void fireColorChangedStartState ( Color pNewColor )
  {
    for ( ColorChangedListener current : this.colorChangedListenerList )
    {
      current.colorChangedStartState ( pNewColor );
    }
  }


  /**
   * Let the listeners know that the color of the {@link State} has changed.
   * 
   * @param pNewColor The new color of the {@link State}.
   */
  public final void fireColorChangedState ( Color pNewColor )
  {
    for ( ColorChangedListener current : this.colorChangedListenerList )
    {
      current.colorChangedState ( pNewColor );
    }
  }


  /**
   * Let the listeners know that the color of the {@link Symbol} has changed.
   * 
   * @param pNewColor The new color of the {@link Symbol}.
   */
  public final void fireColorChangedSymbol ( Color pNewColor )
  {
    for ( ColorChangedListener current : this.colorChangedListenerList )
    {
      current.colorChangedSymbol ( pNewColor );
    }
  }


  /**
   * Let the listeners know that the color of the {@link Transition} has
   * changed.
   * 
   * @param pNewColor The new color of the {@link Transition}.
   */
  public final void fireColorChangedTransition ( Color pNewColor )
  {
    for ( ColorChangedListener current : this.colorChangedListenerList )
    {
      current.colorChangedTransition ( pNewColor );
    }
  }


  /**
   * Let the listeners know that the language has changed.
   * 
   * @param pNewLocale The new {@link Locale}.
   */
  public final void fireLanguageChanged ( Locale pNewLocale )
  {
    Locale.setDefault ( pNewLocale );
    for ( LanguageChangedListener current : this.languageChangedListenerList )
    {
      current.languageChanged ();
    }
  }


  /**
   * Let the listeners know that the zoom factor has changed.
   * 
   * @param pZoomFactor The new {@link ZoomFactorItem}.
   */
  public final void fireZoomFactorChanged ( ZoomFactorItem pZoomFactor )
  {
    for ( ZoomFactorChangedListener current : this.zoomFactorChangedListenerList )
    {
      current.zoomFactorChanged ( pZoomFactor );
    }
  }


  /**
   * Returns the {@link AlphabetItem}.
   * 
   * @return The {@link AlphabetItem}.
   */
  public final AlphabetItem getAlphabetItem ()
  {
    ArrayList < Symbol > symbols = new ArrayList < Symbol > ();
    int count = this.preferences.getInt ( "DefaultAlphabetCount", //$NON-NLS-1$
        Integer.MAX_VALUE );
    String end = "no item found"; //$NON-NLS-1$
    loop : for ( int i = 0 ; i < count ; i++ )
    {
      String symbol = this.preferences.get ( "DefaultAlphabet" + i, //$NON-NLS-1$
          end );
      if ( symbol.equals ( end ) )
      {
        break loop;
      }
      try
      {
        symbols.add ( new Symbol ( symbol ) );
      }
      catch ( SymbolException e )
      {
        e.printStackTrace ();
        System.exit ( 1 );
      }
    }
    // Return the default alphabet if no alphabet is found.
    if ( symbols.size () == 0 )
    {
      return new AlphabetItem ( DEFAULT_ALPHABET, DEFAULT_ALPHABET );
    }
    try
    {
      return new AlphabetItem ( new Alphabet ( symbols ), DEFAULT_ALPHABET );
    }
    catch ( AlphabetException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
      return null;
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
        DefaultValues.DEFAULT_ACTIVE_STATE_COLOR.getRed () );
    int g = this.preferences.getInt ( "PreferencesDialog.ColorActiveStateG", //$NON-NLS-1$
        DefaultValues.DEFAULT_ACTIVE_STATE_COLOR.getGreen () );
    int b = this.preferences.getInt ( "PreferencesDialog.ColorActiveStateB", //$NON-NLS-1$
        DefaultValues.DEFAULT_ACTIVE_STATE_COLOR.getBlue () );
    String caption = Messages
        .getString ( "PreferencesDialog.ColorActiveStateCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "PreferencesDialog.ColorActiveStateDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DefaultValues.DEFAULT_ACTIVE_STATE_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the error {@link State}.
   * 
   * @return The {@link ColorItem} of the error {@link State}.
   */
  public final ColorItem getColorItemErrorState ()
  {
    int r = this.preferences.getInt ( "PreferencesDialog.ColorErrorStateR", //$NON-NLS-1$
        DefaultValues.DEFAULT_ERROR_STATE_COLOR.getRed () );
    int g = this.preferences.getInt ( "PreferencesDialog.ColorErrorStateG", //$NON-NLS-1$
        DefaultValues.DEFAULT_ERROR_STATE_COLOR.getGreen () );
    int b = this.preferences.getInt ( "PreferencesDialog.ColorErrorStateB", //$NON-NLS-1$
        DefaultValues.DEFAULT_ERROR_STATE_COLOR.getBlue () );
    String caption = Messages
        .getString ( "PreferencesDialog.ColorErrorStateCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "PreferencesDialog.ColorErrorStateDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DefaultValues.DEFAULT_ERROR_STATE_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the error {@link Symbol}.
   * 
   * @return The {@link ColorItem} of the error {@link Symbol}.
   */
  public final ColorItem getColorItemErrorSymbol ()
  {
    int r = this.preferences.getInt ( "PreferencesDialog.ColorErrorSymbolR", //$NON-NLS-1$
        DefaultValues.DEFAULT_ERROR_SYMBOL_COLOR.getRed () );
    int g = this.preferences.getInt ( "PreferencesDialog.ColorErrorSymbolG", //$NON-NLS-1$
        DefaultValues.DEFAULT_ERROR_SYMBOL_COLOR.getGreen () );
    int b = this.preferences.getInt ( "PreferencesDialog.ColorErrorSymbolB", //$NON-NLS-1$
        DefaultValues.DEFAULT_ERROR_SYMBOL_COLOR.getBlue () );
    String caption = Messages
        .getString ( "PreferencesDialog.ColorErrorSymbolCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "PreferencesDialog.ColorErrorSymbolDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DefaultValues.DEFAULT_ERROR_SYMBOL_COLOR );
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
        DefaultValues.DEFAULT_ERROR_TRANSITION_COLOR.getRed () );
    int g = this.preferences.getInt (
        "PreferencesDialog.ColorErrorTransitionG", //$NON-NLS-1$
        DefaultValues.DEFAULT_ERROR_TRANSITION_COLOR.getGreen () );
    int b = this.preferences.getInt (
        "PreferencesDialog.ColorErrorTransitionB", //$NON-NLS-1$
        DefaultValues.DEFAULT_ERROR_TRANSITION_COLOR.getBlue () );
    String caption = Messages
        .getString ( "PreferencesDialog.ColorErrorTransitionCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "PreferencesDialog.ColorErrorTransitionDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DefaultValues.DEFAULT_ERROR_TRANSITION_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the parser {State}.
   * 
   * @return The {@link ColorItem} of the parser {State}.
   */
  public final ColorItem getColorItemParserState ()
  {
    int r = this.preferences.getInt ( "PreferencesDialog.ColorParserStateR", //$NON-NLS-1$
        DefaultValues.DEFAULT_PARSER_STATE_COLOR.getRed () );
    int g = this.preferences.getInt ( "PreferencesDialog.ColorParserStateG", //$NON-NLS-1$
        DefaultValues.DEFAULT_PARSER_STATE_COLOR.getGreen () );
    int b = this.preferences.getInt ( "PreferencesDialog.ColorParserStateB", //$NON-NLS-1$
        DefaultValues.DEFAULT_PARSER_STATE_COLOR.getBlue () );
    String caption = Messages
        .getString ( "PreferencesDialog.ColorParserStateCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "PreferencesDialog.ColorParserStateDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DefaultValues.DEFAULT_PARSER_STATE_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the parser {Symbol}.
   * 
   * @return The {@link ColorItem} of the parser {Symbol}.
   */
  public final ColorItem getColorItemParserSymbol ()
  {
    int r = this.preferences.getInt ( "PreferencesDialog.ColorParserSymbolR", //$NON-NLS-1$
        DefaultValues.DEFAULT_PARSER_SYMBOL_COLOR.getRed () );
    int g = this.preferences.getInt ( "PreferencesDialog.ColorParserSymbolG", //$NON-NLS-1$
        DefaultValues.DEFAULT_PARSER_SYMBOL_COLOR.getGreen () );
    int b = this.preferences.getInt ( "PreferencesDialog.ColorParserSymbolB", //$NON-NLS-1$
        DefaultValues.DEFAULT_PARSER_SYMBOL_COLOR.getBlue () );
    String caption = Messages
        .getString ( "PreferencesDialog.ColorParserSymbolCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "PreferencesDialog.ColorParserSymbolDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DefaultValues.DEFAULT_PARSER_SYMBOL_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the parser warning.
   * 
   * @return The {@link ColorItem} of the parser warning.
   */
  public final ColorItem getColorItemParserWarning ()
  {
    int r = this.preferences.getInt ( "PreferencesDialog.ColorParserWarningR", //$NON-NLS-1$
        DefaultValues.DEFAULT_PARSER_WARNING_COLOR.getRed () );
    int g = this.preferences.getInt ( "PreferencesDialog.ColorParserWarningG", //$NON-NLS-1$
        DefaultValues.DEFAULT_PARSER_WARNING_COLOR.getGreen () );
    int b = this.preferences.getInt ( "PreferencesDialog.ColorParserWarningB", //$NON-NLS-1$
        DefaultValues.DEFAULT_PARSER_WARNING_COLOR.getBlue () );
    String caption = Messages
        .getString ( "PreferencesDialog.ColorParserWarningCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "PreferencesDialog.ColorParserWarningDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DefaultValues.DEFAULT_PARSER_WARNING_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the selected {@link State}.
   * 
   * @return The {@link ColorItem} of the selected {@link State}.
   */
  public final ColorItem getColorItemSelectedState ()
  {
    int r = this.preferences.getInt ( "PreferencesDialog.ColorSelectedStateR", //$NON-NLS-1$
        DefaultValues.DEFAULT_SELECTED_STATE_COLOR.getRed () );
    int g = this.preferences.getInt ( "PreferencesDialog.ColorSelectedStateG", //$NON-NLS-1$
        DefaultValues.DEFAULT_SELECTED_STATE_COLOR.getGreen () );
    int b = this.preferences.getInt ( "PreferencesDialog.ColorSelectedStateB", //$NON-NLS-1$
        DefaultValues.DEFAULT_SELECTED_STATE_COLOR.getBlue () );
    String caption = Messages
        .getString ( "PreferencesDialog.ColorSelectedStateCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "PreferencesDialog.ColorSelectedStateDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DefaultValues.DEFAULT_SELECTED_STATE_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the start {@link State}.
   * 
   * @return The {@link ColorItem} of the start {@link State}.
   */
  public final ColorItem getColorItemStartState ()
  {
    int r = this.preferences.getInt ( "PreferencesDialog.ColorStartStateR", //$NON-NLS-1$
        DefaultValues.DEFAULT_START_STATE_COLOR.getRed () );
    int g = this.preferences.getInt ( "PreferencesDialog.ColorStartStateG", //$NON-NLS-1$
        DefaultValues.DEFAULT_START_STATE_COLOR.getGreen () );
    int b = this.preferences.getInt ( "PreferencesDialog.ColorStartStateB", //$NON-NLS-1$
        DefaultValues.DEFAULT_START_STATE_COLOR.getBlue () );
    String caption = Messages
        .getString ( "PreferencesDialog.ColorStartStateCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "PreferencesDialog.ColorStartStateDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DefaultValues.DEFAULT_START_STATE_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the {@link State}.
   * 
   * @return The {@link ColorItem} of the {@link State}.
   */
  public final ColorItem getColorItemState ()
  {
    int r = this.preferences.getInt ( "PreferencesDialog.ColorStateR", //$NON-NLS-1$
        DefaultValues.DEFAULT_STATE_COLOR.getRed () );
    int g = this.preferences.getInt ( "PreferencesDialog.ColorStateG", //$NON-NLS-1$
        DefaultValues.DEFAULT_STATE_COLOR.getGreen () );
    int b = this.preferences.getInt ( "PreferencesDialog.ColorStateB", //$NON-NLS-1$
        DefaultValues.DEFAULT_STATE_COLOR.getBlue () );
    String caption = Messages
        .getString ( "PreferencesDialog.ColorStateCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "PreferencesDialog.ColorStateDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DefaultValues.DEFAULT_STATE_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the {@link Symbol}.
   * 
   * @return The {@link ColorItem} of the {@link Symbol}.
   */
  public final ColorItem getColorItemSymbol ()
  {
    int r = this.preferences.getInt ( "PreferencesDialog.ColorSymbolR", //$NON-NLS-1$
        DefaultValues.DEFAULT_SYMBOL_COLOR.getRed () );
    int g = this.preferences.getInt ( "PreferencesDialog.ColorSymbolG", //$NON-NLS-1$
        DefaultValues.DEFAULT_SYMBOL_COLOR.getGreen () );
    int b = this.preferences.getInt ( "PreferencesDialog.ColorSymbolB", //$NON-NLS-1$
        DefaultValues.DEFAULT_SYMBOL_COLOR.getBlue () );
    String caption = Messages
        .getString ( "PreferencesDialog.ColorSymbolCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "PreferencesDialog.ColorSymbolDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DefaultValues.DEFAULT_SYMBOL_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the {@link Transition}.
   * 
   * @return The {@link ColorItem} of the {@link Transition}.
   */
  public final ColorItem getColorItemTransition ()
  {
    int r = this.preferences.getInt ( "PreferencesDialog.ColorTransitionR", //$NON-NLS-1$
        DefaultValues.DEFAULT_TRANSITION_COLOR.getRed () );
    int g = this.preferences.getInt ( "PreferencesDialog.ColorTransitionG", //$NON-NLS-1$
        DefaultValues.DEFAULT_TRANSITION_COLOR.getGreen () );
    int b = this.preferences.getInt ( "PreferencesDialog.ColorTransitionB", //$NON-NLS-1$
        DefaultValues.DEFAULT_TRANSITION_COLOR.getBlue () );
    String caption = Messages
        .getString ( "PreferencesDialog.ColorTransitionCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "PreferencesDialog.ColorTransitionDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( r, g, b ), caption, description,
        DefaultValues.DEFAULT_TRANSITION_COLOR );
  }


  /**
   * Returns the console divider location.
   * 
   * @return The console divider location.
   */
  public final int getDividerLocationConsole ()
  {
    return this.preferences.getInt ( "MachinePanel.DividerConsole", //$NON-NLS-1$
        DEFAULT_DIVIDER_LOCATION_CONSOLE );
  }


  /**
   * Returns the table divider location.
   * 
   * @return The table divider location.
   */
  public final int getDividerLocationTable ()
  {
    return this.preferences.getInt ( "MachinePanel.DividerTable", //$NON-NLS-1$
        DEFAULT_DIVIDER_LOCATION_TABLE );
  }


  /**
   * Returns the {@link LanguageItem}.
   * 
   * @return The {@link LanguageItem}.
   */
  public final LanguageItem getLanguageItem ()
  {
    String title = this.preferences.get ( "PreferencesDialog.Language.Title", //$NON-NLS-1$
        DEFAULT_LANGUAGE_TITLE );
    String language = this.preferences.get (
        "PreferencesDialog.Language.Language", DEFAULT_LANGUAGE_LANGUAGE ); //$NON-NLS-1$
    return new LanguageItem ( title, new Locale ( language ) );
  }


  /**
   * Returns the {@link LookAndFeelItem}.
   * 
   * @return The {@link LookAndFeelItem}.
   */
  public final LookAndFeelItem getLookAndFeelItem ()
  {
    String name = this.preferences.get ( "PreferencesDialog.LookAndFeel.Name", //$NON-NLS-1$
        DEFAULT_LOOK_AND_FEEL_NAME );
    String className = this.preferences.get (
        "PreferencesDialog.LookAndFeel.ClassName", //$NON-NLS-1$
        DEFAULT_LOOK_AND_FEEL_CLASS_NAME );
    return new LookAndFeelItem ( name, className );
  }


  /**
   * Returns the {@link MainWindow} bounds.
   * 
   * @return The {@link MainWindow} bounds.
   */
  public final Rectangle getMainWindowBounds ()
  {
    int x = this.preferences.getInt ( "MainWindow.XPosition", //$NON-NLS-1$
        DEFAULT_POSITION_X );
    int y = this.preferences.getInt ( "MainWindow.YPosition", //$NON-NLS-1$
        DEFAULT_POSITION_Y );
    int width = this.preferences.getInt ( "MainWindow.Width", DEFAULT_WIDTH ); //$NON-NLS-1$
    int height = this.preferences.getInt ( "MainWindow.Height", DEFAULT_HEIGHT ); //$NON-NLS-1$
    return new Rectangle ( x, y, width, height );
  }


  /**
   * Returns the maximized state of the {@link MainWindow}.
   * 
   * @return The maximized state of the {@link MainWindow}.
   */
  public final boolean getMainWindowMaximized ()
  {
    return this.preferences.getBoolean ( "MainWindow.Maximized", //$NON-NLS-1$
        DEFAULT_MAXIMIZED );
  }


  /**
   * Returns the opened files and the index of the last active file.
   * 
   * @return The opened files and the index of the last active file.
   */
  public final OpenedFilesItem getOpenedFilesItem ()
  {
    ArrayList < File > files = new ArrayList < File > ();
    int count = this.preferences.getInt ( "MainWindow.OpenedFilesCount", //$NON-NLS-1$
        Integer.MAX_VALUE );
    String end = "no item found"; //$NON-NLS-1$
    for ( int i = 0 ; i < count ; i++ )
    {
      String file = this.preferences.get ( "MainWindow.OpenedFiles" + i, //$NON-NLS-1$
          end );
      if ( file.equals ( end ) )
      {
        break;
      }
      files.add ( new File ( file ) );
    }
    int activeIndex = this.preferences.getInt (
        "MainWindow.OpenedActiveIndex", -1 ); //$NON-NLS-1$
    return new OpenedFilesItem ( files, activeIndex );
  }


  /**
   * Returns the last active tab of the {@link PreferencesDialog}.
   * 
   * @return The last active tab of the {@link PreferencesDialog}.
   */
  public final int getPreferencesDialogLastActiveTab ()
  {
    return this.preferences.getInt ( "PreferencesDialog.LastActiveTab", //$NON-NLS-1$
        DEFAULT_PREFERENCES_DIALOG_LAST_ACTIVE_TAB );
  }


  /**
   * Returns the recently used files.
   * 
   * @return The recently used files.
   */
  public final RecentlyUsedFilesItem getRecentlyUsedFilesItem ()
  {
    ArrayList < File > files = new ArrayList < File > ();
    int count = this.preferences.getInt ( "MainWindow.RecentlyUsedFilesCount", //$NON-NLS-1$
        Integer.MAX_VALUE );
    String end = "no item found"; //$NON-NLS-1$
    for ( int i = 0 ; i < count ; i++ )
    {
      String file = this.preferences.get ( "MainWindow.RecentlyUsedFiles" + i, //$NON-NLS-1$
          end );
      if ( file.equals ( end ) )
      {
        break;
      }
      files.add ( new File ( file ) );
    }
    return new RecentlyUsedFilesItem ( files );
  }


  /**
   * Returns the system {@link Locale}.
   * 
   * @return The system {@link Locale}.
   */
  public final Locale getSystemLocale ()
  {
    return this.systemLocale;
  }


  /**
   * Returns the working path.
   * 
   * @return The working path.
   */
  public final String getWorkingPath ()
  {
    return this.preferences.get ( "WorkingPath", DEFAULT_WORKING_PATH ); //$NON-NLS-1$
  }


  /**
   * Returns the {@link ZoomFactorItem}.
   * 
   * @return The {@link ZoomFactorItem}.
   */
  public final ZoomFactorItem getZoomFactorItem ()
  {
    return ZoomFactorItem.createFactor ( this.preferences.getInt (
        "ZoomFactor", DEFAULT_ZOOM_FACTOR ) ); //$NON-NLS-1$
  }


  /**
   * Removes the given {@link ColorChangedListener}.
   * 
   * @param pListener The {@link ColorChangedListener}.
   * @return <tt>true</tt> if the list contained the specified element.
   */
  public final synchronized boolean removeColorChangedListener (
      ColorChangedListener pListener )
  {
    return this.colorChangedListenerList.remove ( pListener );
  }


  /**
   * Removes the given {@link LanguageChangedListener}.
   * 
   * @param pListener The {@link LanguageChangedListener}.
   * @return <tt>true</tt> if the list contained the specified element.
   */
  public final synchronized boolean removeLanguageChangedListener (
      LanguageChangedListener pListener )
  {
    return this.languageChangedListenerList.remove ( pListener );
  }


  /**
   * Removes the given {@link ZoomFactorChangedListener}.
   * 
   * @param pListener The {@link ZoomFactorChangedListener}.
   * @return <tt>true</tt> if the list contained the specified element.
   */
  public final synchronized boolean removeZoomFactorChangedListener (
      ZoomFactorChangedListener pListener )
  {
    return this.zoomFactorChangedListenerList.remove ( pListener );
  }


  /**
   * Sets the {@link AlphabetItem}.
   * 
   * @param pAlphabetItem The {@link AlphabetItem}.
   */
  public final void setAlphabetItem ( AlphabetItem pAlphabetItem )
  {
    logger
        .debug ( "set the alphabet to \"" + pAlphabetItem.getAlphabet () + "\"" ); //$NON-NLS-1$//$NON-NLS-2$
    for ( int i = 0 ; i < pAlphabetItem.getAlphabet ().symbolSize () ; i++ )
    {
      this.preferences.put (
          "DefaultAlphabet" + i, pAlphabetItem.getAlphabet ().getSymbol ( i ) //$NON-NLS-1$
              .getName () );
    }
    this.preferences.putInt ( "DefaultAlphabetCount", pAlphabetItem //$NON-NLS-1$
        .getAlphabet ().symbolSize () );
  }


  /**
   * Sets the {@link ColorItem} of the active {@link State}.
   * 
   * @param pColorItem The {@link ColorItem} of the active {@link State}.
   */
  public final void setColorItemActiveState ( ColorItem pColorItem )
  {
    logger.debug ( "set color of the avtive state to \"" //$NON-NLS-1$
        + "r=" + pColorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + pColorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + pColorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "PreferencesDialog.ColorActiveStateR", //$NON-NLS-1$
        pColorItem.getColor ().getRed () );
    this.preferences.putInt ( "PreferencesDialog.ColorActiveStateG", //$NON-NLS-1$
        pColorItem.getColor ().getGreen () );
    this.preferences.putInt ( "PreferencesDialog.ColorActiveStateB", //$NON-NLS-1$
        pColorItem.getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the error {@link State}.
   * 
   * @param pColorItem The {@link ColorItem} of the error {@link State}.
   */
  public final void setColorItemErrorState ( ColorItem pColorItem )
  {
    logger.debug ( "set color of the error state to \"" //$NON-NLS-1$
        + "r=" + pColorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + pColorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + pColorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "PreferencesDialog.ColorErrorStateR", //$NON-NLS-1$
        pColorItem.getColor ().getRed () );
    this.preferences.putInt ( "PreferencesDialog.ColorErrorStateG", //$NON-NLS-1$
        pColorItem.getColor ().getGreen () );
    this.preferences.putInt ( "PreferencesDialog.ColorErrorStateB", //$NON-NLS-1$
        pColorItem.getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the error {@link Symbol}.
   * 
   * @param pColorItem The {@link ColorItem} of the error {@link Symbol}.
   */
  public final void setColorItemErrorSymbol ( ColorItem pColorItem )
  {
    logger.debug ( "set color of the error symbol to \"" //$NON-NLS-1$
        + "r=" + pColorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + pColorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + pColorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "PreferencesDialog.ColorErrorSymbolR", pColorItem //$NON-NLS-1$
        .getColor ().getRed () );
    this.preferences.putInt ( "PreferencesDialog.ColorErrorSymbolG", pColorItem //$NON-NLS-1$
        .getColor ().getGreen () );
    this.preferences.putInt ( "PreferencesDialog.ColorErrorSymbolB", pColorItem //$NON-NLS-1$
        .getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the error {@link Transition}.
   * 
   * @param pColorItem The {@link ColorItem} of the error {@link Transition}.
   */
  public final void setColorItemErrorTransition ( ColorItem pColorItem )
  {
    logger.debug ( "set color of the error transition to \"" //$NON-NLS-1$
        + "r=" + pColorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + pColorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + pColorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt (
        "PreferencesDialog.ColorErrorTransitionR", pColorItem //$NON-NLS-1$
            .getColor ().getRed () );
    this.preferences.putInt (
        "PreferencesDialog.ColorErrorTransitionG", pColorItem //$NON-NLS-1$
            .getColor ().getGreen () );
    this.preferences.putInt (
        "PreferencesDialog.ColorErrorTransitionB", pColorItem //$NON-NLS-1$
            .getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the parser {@link State}.
   * 
   * @param pColorItem The {@link ColorItem} of the parser {@link State}.
   */
  public final void setColorItemParserState ( ColorItem pColorItem )
  {
    logger.debug ( "set color of the parser state to \"" //$NON-NLS-1$
        + "r=" + pColorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + pColorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + pColorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "PreferencesDialog.ColorParserStateR", pColorItem //$NON-NLS-1$
        .getColor ().getRed () );
    this.preferences.putInt ( "PreferencesDialog.ColorParserStateG", pColorItem //$NON-NLS-1$
        .getColor ().getGreen () );
    this.preferences.putInt ( "PreferencesDialog.ColorParserStateB", pColorItem //$NON-NLS-1$
        .getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the parser {@link Symbol}.
   * 
   * @param pColorItem The {@link ColorItem} of the parser {@link Symbol}.
   */
  public final void setColorItemParserSymbol ( ColorItem pColorItem )
  {
    logger.debug ( "set color of the parser symbol to \"" //$NON-NLS-1$
        + "r=" + pColorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + pColorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + pColorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt (
        "PreferencesDialog.ColorParserSymbolR", pColorItem //$NON-NLS-1$
            .getColor ().getRed () );
    this.preferences.putInt (
        "PreferencesDialog.ColorParserSymbolG", pColorItem //$NON-NLS-1$
            .getColor ().getGreen () );
    this.preferences.putInt (
        "PreferencesDialog.ColorParserSymbolB", pColorItem //$NON-NLS-1$
            .getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the parser warning.
   * 
   * @param pColorItem The {@link ColorItem} of the parser warning.
   */
  public final void setColorItemParserWarning ( ColorItem pColorItem )
  {
    logger.debug ( "set color of the parser warning to \"" //$NON-NLS-1$
        + "r=" + pColorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + pColorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + pColorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt (
        "PreferencesDialog.ColorParserWarningR", pColorItem //$NON-NLS-1$
            .getColor ().getRed () );
    this.preferences.putInt (
        "PreferencesDialog.ColorParserWarningG", pColorItem //$NON-NLS-1$
            .getColor ().getGreen () );
    this.preferences.putInt (
        "PreferencesDialog.ColorParserWarningB", pColorItem //$NON-NLS-1$
            .getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the selected {@link State}.
   * 
   * @param pColorItem The {@link ColorItem} of the selected {@link State}.
   */
  public final void setColorItemSelectedState ( ColorItem pColorItem )
  {
    logger.debug ( "set color of the selected state to \"" //$NON-NLS-1$
        + "r=" + pColorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + pColorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + pColorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "PreferencesDialog.ColorSelectedStateR", //$NON-NLS-1$
        pColorItem.getColor ().getRed () );
    this.preferences.putInt ( "PreferencesDialog.ColorSelectedStateG", //$NON-NLS-1$
        pColorItem.getColor ().getGreen () );
    this.preferences.putInt ( "PreferencesDialog.ColorSelectedStateB", //$NON-NLS-1$
        pColorItem.getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the start {@link State}.
   * 
   * @param pColorItem The {@link ColorItem} of the start {@link State}.
   */
  public final void setColorItemStartState ( ColorItem pColorItem )
  {
    logger.debug ( "set color of the start state to \"" //$NON-NLS-1$
        + "r=" + pColorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + pColorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + pColorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "PreferencesDialog.ColorStartStateR", //$NON-NLS-1$
        pColorItem.getColor ().getRed () );
    this.preferences.putInt ( "PreferencesDialog.ColorStartStateG", //$NON-NLS-1$
        pColorItem.getColor ().getGreen () );
    this.preferences.putInt ( "PreferencesDialog.ColorStartStateB", //$NON-NLS-1$
        pColorItem.getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the {@link State}.
   * 
   * @param pColorItem The {@link ColorItem} of the {@link State}.
   */
  public final void setColorItemState ( ColorItem pColorItem )
  {
    logger.debug ( "set color of the state to \"" //$NON-NLS-1$
        + "r=" + pColorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + pColorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + pColorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "PreferencesDialog.ColorStateR", pColorItem //$NON-NLS-1$
        .getColor ().getRed () );
    this.preferences.putInt ( "PreferencesDialog.ColorStateG", pColorItem //$NON-NLS-1$
        .getColor ().getGreen () );
    this.preferences.putInt ( "PreferencesDialog.ColorStateB", pColorItem //$NON-NLS-1$
        .getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the {@link Symbol}.
   * 
   * @param pColorItem The {@link ColorItem} of the {@link Symbol}.
   */
  public final void setColorItemSymbol ( ColorItem pColorItem )
  {
    logger.debug ( "set color of the symbol to \"" //$NON-NLS-1$
        + "r=" + pColorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + pColorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + pColorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "PreferencesDialog.ColorSymbolR", pColorItem //$NON-NLS-1$
        .getColor ().getRed () );
    this.preferences.putInt ( "PreferencesDialog.ColorSymbolG", pColorItem //$NON-NLS-1$
        .getColor ().getGreen () );
    this.preferences.putInt ( "PreferencesDialog.ColorSymbolB", pColorItem //$NON-NLS-1$
        .getColor ().getBlue () );
  }


  /**
   * Sets the {@link ColorItem} of the {@link Transition}.
   * 
   * @param pColorItem The {@link ColorItem} of the {@link Transition}.
   */
  public final void setColorItemTransition ( ColorItem pColorItem )
  {
    logger.debug ( "set color of the transition to \"" //$NON-NLS-1$
        + "r=" + pColorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + pColorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + pColorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "PreferencesDialog.ColorTransitionR", pColorItem //$NON-NLS-1$
        .getColor ().getRed () );
    this.preferences.putInt ( "PreferencesDialog.ColorTransitionG", pColorItem //$NON-NLS-1$
        .getColor ().getGreen () );
    this.preferences.putInt ( "PreferencesDialog.ColorTransitionB", pColorItem //$NON-NLS-1$
        .getColor ().getBlue () );
  }


  /**
   * Sets the console divider location.
   * 
   * @param pLocation The console divider location.
   */
  public final void setDividerLocationConsole ( int pLocation )
  {
    logger.debug ( "set console divider location to \"" + pLocation + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "MachinePanel.DividerConsole", pLocation ); //$NON-NLS-1$
  }


  /**
   * Sets the table divider location.
   * 
   * @param pLocation The table divider location.
   */
  public final void setDividerLocationTable ( int pLocation )
  {
    logger.debug ( "set table divider location to \"" + pLocation + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "MachinePanel.DividerTable", pLocation ); //$NON-NLS-1$
  }


  /**
   * Sets the {@link LanguageItem}.
   * 
   * @param pLanguageItem The {@link LanguageItem}.
   */
  public final void setLanguageItem ( LanguageItem pLanguageItem )
  {
    logger.debug ( "set language to \"" //$NON-NLS-1$
        + pLanguageItem.getLocale ().getLanguage () + "\"" ); //$NON-NLS-1$
    this.preferences.put ( "PreferencesDialog.Language.Title", pLanguageItem //$NON-NLS-1$
        .getTitle () );
    this.preferences.put ( "PreferencesDialog.Language.Language", pLanguageItem //$NON-NLS-1$
        .getLocale ().getLanguage () );
  }


  /**
   * Sets the {@link LookAndFeelItem}.
   * 
   * @param pLookAndFeelItem The {@link LookAndFeelItem}.
   */
  public final void setLookAndFeelItem ( LookAndFeelItem pLookAndFeelItem )
  {
    logger.debug ( "set look and feel to \"" //$NON-NLS-1$
        + pLookAndFeelItem.getName () + "\"" ); //$NON-NLS-1$
    this.preferences.put (
        "PreferencesDialog.LookAndFeel.Name", pLookAndFeelItem.getName () ); //$NON-NLS-1$
    this.preferences
        .put (
            "PreferencesDialog.LookAndFeel.ClassName", pLookAndFeelItem.getClassName () ); //$NON-NLS-1$
  }


  /**
   * Sets the {@link MainWindow} preferences.
   * 
   * @param pJFrame The {@link JFrame} of the {@link MainWindow}.
   */
  public final void setMainWindowPreferences ( JFrame pJFrame )
  {
    if ( ( pJFrame.getExtendedState () & Frame.MAXIMIZED_BOTH ) == 0 )
    {
      logger.debug ( "set main window maximized to \"false\"" ); //$NON-NLS-1$
      this.preferences.putBoolean ( "mainWindow.maximized", false ); //$NON-NLS-1$
      Rectangle bounds = pJFrame.getBounds ();
      logger.debug ( "set main window bounds to \"" + "x=" + bounds.x + ", " //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
          + "y=" + bounds.y + ", " + "width=" + bounds.width + ", " + "height=" //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
          + bounds.height + "\"" ); //$NON-NLS-1$
      this.preferences.putInt ( "MainWindow.XPosition", bounds.x ); //$NON-NLS-1$
      this.preferences.putInt ( "MainWindow.YPosition", bounds.y ); //$NON-NLS-1$
      this.preferences.putInt ( "MainWindow.Width", bounds.width ); //$NON-NLS-1$
      this.preferences.putInt ( "MainWindow.Height", bounds.height ); //$NON-NLS-1$
    }
    else
    {
      logger.debug ( "set main window maximized to \"true\"" ); //$NON-NLS-1$
      this.preferences.putBoolean ( "MainWindow.Maximized", true ); //$NON-NLS-1$
    }
  }


  /**
   * Sets the opened files and the index of the last active file.
   * 
   * @param pOpenedFilesItem The {@link OpenedFilesItem}.
   */
  public final void setOpenedFilesItem ( OpenedFilesItem pOpenedFilesItem )
  {
    for ( int i = 0 ; i < pOpenedFilesItem.getFiles ().size () ; i++ )
    {
      logger.debug ( "set opened file \"" + i + "\" to \"" //$NON-NLS-1$//$NON-NLS-2$
          + pOpenedFilesItem.getFiles ().get ( i ).getAbsolutePath () + "\"" ); //$NON-NLS-1$
      this.preferences.put ( "MainWindow.OpenedFiles" + i, pOpenedFilesItem //$NON-NLS-1$
          .getFiles ().get ( i ).getAbsolutePath () );
    }
    this.preferences.putInt ( "MainWindow.OpenedActiveIndex", //$NON-NLS-1$
        pOpenedFilesItem.getActiveIndex () );
    this.preferences.putInt ( "MainWindow.OpenedFilesCount", //$NON-NLS-1$
        pOpenedFilesItem.getFiles ().size () );
  }


  /**
   * Sets the last active tab of the {@link PreferencesDialog}.
   * 
   * @param pIndex The index of the last active {@link PreferencesDialog}.
   */
  public final void setPreferencesDialogLastActiveTab ( int pIndex )
  {
    logger.debug ( "set last active tab to \"" + pIndex + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "PreferencesDialog.LastActiveTab", pIndex ); //$NON-NLS-1$
  }


  /**
   * Sets the recently used files.
   * 
   * @param pRecentlyUsedFilesItem The {@link RecentlyUsedFilesItem}.
   */
  public final void setRecentlyUsedFilesItem (
      RecentlyUsedFilesItem pRecentlyUsedFilesItem )
  {
    for ( int i = 0 ; i < pRecentlyUsedFilesItem.getFiles ().size () ; i++ )
    {
      logger.debug ( "set recently used file \"" + i + "\" to \"" //$NON-NLS-1$//$NON-NLS-2$
          + pRecentlyUsedFilesItem.getFiles ().get ( i ).getAbsolutePath ()
          + "\"" ); //$NON-NLS-1$
      this.preferences.put (
          "MainWindow.RecentlyUsedFiles" + i, pRecentlyUsedFilesItem //$NON-NLS-1$
              .getFiles ().get ( i ).getAbsolutePath () );
    }
    this.preferences.putInt ( "MainWindow.RecentlyUsedFilesCount", //$NON-NLS-1$
        pRecentlyUsedFilesItem.getFiles ().size () );
  }


  /**
   * Sets the system {@link Locale}.
   * 
   * @param pLocale The system {@link Locale}.
   */
  public final void setSystemLocale ( Locale pLocale )
  {
    this.systemLocale = pLocale;
  }


  /**
   * Sets the working path.
   * 
   * @param pPath The working path.
   */
  public final void setWorkingPath ( String pPath )
  {
    logger.debug ( "set the working path to \"" + pPath + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.put ( "WorkingPath", pPath ); //$NON-NLS-1$
  }


  /**
   * Sets the {@link ZoomFactorItem}.
   * 
   * @param pZoomFactor The {@link ZoomFactorItem}.
   */
  public final void setZoomFactorItem ( ZoomFactorItem pZoomFactor )
  {
    logger.debug ( "set zoom factor to \"" //$NON-NLS-1$
        + pZoomFactor.getFactor () + "\"" ); //$NON-NLS-1$
    this.preferences.putInt ( "ZoomFactor", pZoomFactor.getFactor () ); //$NON-NLS-1$
  }
}
