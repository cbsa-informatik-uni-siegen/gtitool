package de.unisiegen.gtitool.core.preferences;


import java.awt.Color;
import java.util.ArrayList;
import java.util.Locale;
import java.util.prefs.Preferences;

import javax.swing.event.EventListenerList;

import org.apache.log4j.Logger;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbol.NonterminalSymbolException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbol.TerminalSymbolException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.preferences.item.AlphabetItem;
import de.unisiegen.gtitool.core.preferences.item.ColorItem;
import de.unisiegen.gtitool.core.preferences.item.LanguageItem;
import de.unisiegen.gtitool.core.preferences.item.NonterminalSymbolSetItem;
import de.unisiegen.gtitool.core.preferences.item.TerminalSymbolSetItem;
import de.unisiegen.gtitool.core.preferences.listener.ColorChangedListener;
import de.unisiegen.gtitool.core.preferences.listener.LanguageChangedListener;


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
   * The default language language.
   */
  public static final String DEFAULT_LANGUAGE_LANGUAGE = Locale.getDefault ()
      .getLanguage ();


  /**
   * The default language title.
   */
  public static final String DEFAULT_LANGUAGE_TITLE = "Default"; //$NON-NLS-1$


  /**
   * The single instance of the {@link PreferenceManager}.
   */
  private static PreferenceManager preferenceManager;


  /**
   * The default {@link Color} of a parser warning.
   */
  public static final Color DEFAULT_STATE_COLOR = new Color ( 0, 0, 127 );


  /**
   * The default {@link Color} of a {@link State}.
   */
  public static final Color DEFAULT_STATE_BACKGROUND_COLOR = new Color ( 255,
      255, 255 );


  /**
   * The default {@link Color} of the selected {@link State}.
   */
  public static final Color DEFAULT_STATE_SELECTED_COLOR = new Color ( 255, 0,
      0 );


  /**
   * The default {@link Color} of the start {@link State}.
   */
  public static final Color DEFAULT_STATE_START_COLOR = new Color ( 255, 255,
      255 );


  /**
   * The default {@link Color} of the active {@link State}.
   */
  public static final Color DEFAULT_STATE_ACTIVE_COLOR = new Color ( 0, 255, 0 );


  /**
   * The default {@link Color} of the error {@link State}.
   */
  public static final Color DEFAULT_STATE_ERROR_COLOR = new Color ( 255, 0, 0 );


  /**
   * The default {@link Color} of a {@link Transition}.
   */
  public static final Color DEFAULT_TRANSITION_COLOR = new Color ( 0, 0, 0 );


  /**
   * The default {@link Color} of the selected {@link Transition}.
   */
  public static final Color DEFAULT_TRANSITION_SELECTED_COLOR = new Color (
      255, 0, 0 );


  /**
   * The default {@link Color} of the active {@link Transition}.
   */
  public static final Color DEFAULT_TRANSITION_ACTIVE_COLOR = new Color ( 0,
      255, 0 );


  /**
   * The default {@link Color} of a error {@link Transition}.
   */
  public static final Color DEFAULT_TRANSITION_ERROR_COLOR = new Color ( 255,
      0, 0 );


  /**
   * The default {@link Color} of a {@link Symbol}.
   */
  public static final Color DEFAULT_SYMBOL_COLOR = new Color ( 0, 0, 127 );


  /**
   * The default {@link Color} of a {@link NonterminalSymbol}.
   */
  public static final Color DEFAULT_NONTERMINAL_SYMBOL_COLOR = new Color ( 0,
      0, 127 );


  /**
   * The default {@link Color} of a {@link TerminalSymbol}.
   */
  public static final Color DEFAULT_TERMINAL_SYMBOL_COLOR = new Color ( 0, 0,
      127 );


  /**
   * The default {@link Color} of the active {@link Symbol}.
   */
  public static final Color DEFAULT_SYMBOL_ACTIVE_COLOR = new Color ( 0, 255, 0 );


  /**
   * The default {@link Color} of a error {@link Symbol}.
   */
  public static final Color DEFAULT_SYMBOL_ERROR_COLOR = new Color ( 255, 0, 0 );


  /**
   * The default {@link Color} of a parser keyword.
   */
  public static final Color DEFAULT_PARSER_KEYWORD_COLOR = new Color ( 127, 0,
      0 );


  /**
   * The default {@link Color} of a parser warning.
   */
  public static final Color DEFAULT_PARSER_WARNING_COLOR = new Color ( 232,
      242, 254 );


  /**
   * The default {@link Color} of a error warning.
   */
  public static final Color DEFAULT_PARSER_ERROR_COLOR = new Color ( 255, 0, 0 );


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


  /**
   * The default use push down {@link Alphabet}.
   */
  public static final boolean DEFAULT_USE_PUSH_DOWN_ALPHABET = false;


  /**
   * The default {@link NonterminalSymbolSet}.
   */
  public static NonterminalSymbolSet DEFAULT_NONTERMINAL_SYMBOL_SET;


  /**
   * The default {@link TerminalSymbolSet}.
   */
  public static TerminalSymbolSet DEFAULT_TERMINAL_SYMBOL_SET;

  static
  {
    try
    {
      DEFAULT_ALPHABET = new DefaultAlphabet (
          new DefaultSymbol ( "0" ), new DefaultSymbol ( "1" ) ); //$NON-NLS-1$ //$NON-NLS-2$

      DEFAULT_PUSH_DOWN_ALPHABET = new DefaultAlphabet ( new DefaultSymbol (
          "0" ), new DefaultSymbol ( "1" ) ); //$NON-NLS-1$ //$NON-NLS-2$

      DEFAULT_NONTERMINAL_SYMBOL_SET = new DefaultNonterminalSymbolSet (
          new DefaultNonterminalSymbol ( "E" ), new DefaultNonterminalSymbol ( //$NON-NLS-1$
              "F" ) ); //$NON-NLS-1$

      DEFAULT_TERMINAL_SYMBOL_SET = new DefaultTerminalSymbolSet (
          new DefaultTerminalSymbol ( "a" ), new DefaultTerminalSymbol ( "b" ) ); //$NON-NLS-1$ //$NON-NLS-2$
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
    catch ( NonterminalSymbolSetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( NonterminalSymbolException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( TerminalSymbolSetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( TerminalSymbolException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }


  /**
   * Returns the single instance of the {@link PreferenceManager}.
   * 
   * @return The single instance of the {@link PreferenceManager}.
   */
  public static PreferenceManager getInstance ()
  {
    if ( preferenceManager == null )
    {
      preferenceManager = new PreferenceManager ();
    }
    return preferenceManager;
  }


  /**
   * The system {@link Locale}.
   */
  private Locale systemLocale;


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
   * Allocates a new {@link PreferenceManager}.
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
   * Adds the given {@link LanguageChangedListener}.
   * 
   * @param listener The {@link LanguageChangedListener}.
   */
  public final synchronized void addLanguageChangedListener (
      LanguageChangedListener listener )
  {
    this.listenerList.add ( LanguageChangedListener.class, listener );
  }


  /**
   * Let the listeners know that the color of the {@link NonterminalSymbol} has
   * changed.
   * 
   * @param newColor The new color of the {@link NonterminalSymbol}.
   */
  public final void fireColorChangedNonterminalSymbol ( Color newColor )
  {
    ColorChangedListener [] listeners = this.listenerList
        .getListeners ( ColorChangedListener.class );
    for ( ColorChangedListener current : listeners )
    {
      current.colorChangedNonterminalSymbol ( newColor );
    }
  }


  /**
   * Let the listeners know that the color of the parser error has changed.
   * 
   * @param newColor The new color of the parser error.
   */
  public final void fireColorChangedParserError ( Color newColor )
  {
    ColorChangedListener [] listeners = this.listenerList
        .getListeners ( ColorChangedListener.class );
    for ( ColorChangedListener current : listeners )
    {
      current.colorChangedParserError ( newColor );
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
    for ( ColorChangedListener current : listeners )
    {
      current.colorChangedParserHighlighting ( newColor );
    }
  }


  /**
   * Let the listeners know that the color of the parser keyword has changed.
   * 
   * @param newColor The new color of the parser keyword.
   */
  public final void fireColorChangedParserKeyword ( Color newColor )
  {
    ColorChangedListener [] listeners = this.listenerList
        .getListeners ( ColorChangedListener.class );
    for ( ColorChangedListener current : listeners )
    {
      current.colorChangedParserKeyword ( newColor );
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
    for ( ColorChangedListener current : listeners )
    {
      current.colorChangedParserWarning ( newColor );
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
    for ( ColorChangedListener current : listeners )
    {
      current.colorChangedState ( newColor );
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
    for ( ColorChangedListener current : listeners )
    {
      current.colorChangedStateActive ( newColor );
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
    for ( ColorChangedListener current : listeners )
    {
      current.colorChangedStateBackground ( newColor );
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
    for ( ColorChangedListener current : listeners )
    {
      current.colorChangedStateError ( newColor );
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
    for ( ColorChangedListener current : listeners )
    {
      current.colorChangedStateSelected ( newColor );
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
    for ( ColorChangedListener current : listeners )
    {
      current.colorChangedStateStart ( newColor );
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
    for ( ColorChangedListener current : listeners )
    {
      current.colorChangedSymbol ( newColor );
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
    for ( ColorChangedListener current : listeners )
    {
      current.colorChangedSymbolActive ( newColor );
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
    for ( ColorChangedListener current : listeners )
    {
      current.colorChangedSymbolError ( newColor );
    }
  }


  /**
   * Let the listeners know that the color of the {@link TerminalSymbol} has
   * changed.
   * 
   * @param newColor The new color of the {@link TerminalSymbol}.
   */
  public final void fireColorChangedTerminalSymbol ( Color newColor )
  {
    ColorChangedListener [] listeners = this.listenerList
        .getListeners ( ColorChangedListener.class );
    for ( ColorChangedListener current : listeners )
    {
      current.colorChangedTerminalSymbol ( newColor );
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
    for ( ColorChangedListener current : listeners )
    {
      current.colorChangedTransition ( newColor );
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
    for ( ColorChangedListener current : listeners )
    {
      current.colorChangedTransitionActive ( newColor );
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
    for ( ColorChangedListener current : listeners )
    {
      current.colorChangedTransitionError ( newColor );
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
    for ( ColorChangedListener current : listeners )
    {
      current.colorChangedTransitionSelected ( newColor );
    }
  }


  /**
   * Let the listeners know that the language has changed.
   * 
   * @param newLocale The new {@link Locale}.
   */
  public final void fireLanguageChanged ( Locale newLocale )
  {
    Locale.setDefault ( newLocale );
    LanguageChangedListener [] listeners = this.listenerList
        .getListeners ( LanguageChangedListener.class );
    for ( LanguageChangedListener current : listeners )
    {
      current.languageChanged ();
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
    String end = "no item found"; //$NON-NLS-1$
    int count = 0;
    while ( true )
    {
      String symbol = this.preferences.get ( "DefaultAlphabet" + count, //$NON-NLS-1$
          end );
      if ( symbol.equals ( end ) )
      {
        break;
      }
      try
      {
        symbols.add ( new DefaultSymbol ( symbol ) );
      }
      catch ( SymbolException e )
      {
        e.printStackTrace ();
        System.exit ( 1 );
      }
      count++ ;
    }
    // Return the default alphabet if no alphabet is found.
    if ( symbols.size () == 0 )
    {
      return new AlphabetItem ( DEFAULT_ALPHABET, DEFAULT_ALPHABET );
    }
    try
    {
      return new AlphabetItem ( new DefaultAlphabet ( symbols ),
          DEFAULT_ALPHABET );
    }
    catch ( AlphabetException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
      return null;
    }
  }


  /**
   * Returns the {@link ColorItem} of the {NonterminalSymbol}.
   * 
   * @return The {@link ColorItem} of the {NonterminalSymbol}.
   */
  public final ColorItem getColorItemNonterminalSymbol ()
  {
    int rgb = this.preferences.getInt ( "Preferences.ColorNonterminalSymbol", //$NON-NLS-1$
        DEFAULT_NONTERMINAL_SYMBOL_COLOR.getRGB () );
    String caption = Messages
        .getString ( "Preferences.ColorNonterminalSymbolCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorNonterminalSymbolDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( rgb ), caption, description,
        DEFAULT_NONTERMINAL_SYMBOL_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the {@link NonterminalSymbol} group.
   * 
   * @return The {@link ColorItem} of the {@link NonterminalSymbol} group.
   */
  public final ColorItem getColorItemNonterminalSymbolGroup ()
  {
    String caption = Messages
        .getString ( "Preferences.ColorNonterminalSymbolGroup" );//$NON-NLS-1$
    boolean expanded = this.preferences.getBoolean (
        "Preferences.ColorNonterminalSymbolGroupExpanded", false ); //$NON-NLS-1$
    return new ColorItem ( caption, expanded );
  }


  /**
   * Returns the {@link ColorItem} of the parser error.
   * 
   * @return The {@link ColorItem} of the parser error.
   */
  public final ColorItem getColorItemParserError ()
  {
    int rgb = this.preferences.getInt ( "Preferences.ColorParserError", //$NON-NLS-1$
        DEFAULT_PARSER_ERROR_COLOR.getRGB () );
    String caption = Messages
        .getString ( "Preferences.ColorParserErrorCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorParserErrorDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( rgb ), caption, description,
        DEFAULT_PARSER_ERROR_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the parser group.
   * 
   * @return The {@link ColorItem} of the parser group.
   */
  public final ColorItem getColorItemParserGroup ()
  {
    String caption = Messages.getString ( "Preferences.ColorParserGroup" );//$NON-NLS-1$
    boolean expanded = this.preferences.getBoolean (
        "Preferences.ColorParserGroupExpanded", false ); //$NON-NLS-1$
    return new ColorItem ( caption, expanded );
  }


  /**
   * Returns the {@link ColorItem} of the parser highlighting.
   * 
   * @return The {@link ColorItem} of the parser highlighting.
   */
  public final ColorItem getColorItemParserHighlighting ()
  {
    int rgb = this.preferences.getInt ( "Preferences.ColorParserHighlighting", //$NON-NLS-1$
        DEFAULT_PARSER_HIGHLIGHTING_COLOR.getRGB () );
    String caption = Messages
        .getString ( "Preferences.ColorParserHighlightingCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorParserHighlightingDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( rgb ), caption, description,
        DEFAULT_PARSER_HIGHLIGHTING_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the parser keyword.
   * 
   * @return The {@link ColorItem} of the parser keyword.
   */
  public final ColorItem getColorItemParserKeyword ()
  {
    int rgb = this.preferences.getInt ( "Preferences.ColorParserKeyword", //$NON-NLS-1$
        DEFAULT_PARSER_KEYWORD_COLOR.getRGB () );
    String caption = Messages
        .getString ( "Preferences.ColorParserKeywordCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorParserKeywordDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( rgb ), caption, description,
        DEFAULT_PARSER_KEYWORD_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the parser warning.
   * 
   * @return The {@link ColorItem} of the parser warning.
   */
  public final ColorItem getColorItemParserWarning ()
  {
    int rgb = this.preferences.getInt ( "Preferences.ColorParserWarning", //$NON-NLS-1$
        DEFAULT_PARSER_WARNING_COLOR.getRGB () );
    String caption = Messages
        .getString ( "Preferences.ColorParserWarningCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorParserWarningDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( rgb ), caption, description,
        DEFAULT_PARSER_WARNING_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the {State}.
   * 
   * @return The {@link ColorItem} of the {State}.
   */
  public final ColorItem getColorItemState ()
  {
    int rgb = this.preferences.getInt ( "Preferences.ColorState", //$NON-NLS-1$
        DEFAULT_STATE_COLOR.getRGB () );
    String caption = Messages.getString ( "Preferences.ColorStateCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorStateDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( rgb ), caption, description,
        DEFAULT_STATE_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the active {@link State}.
   * 
   * @return The {@link ColorItem} of the active {@link State}.
   */
  public final ColorItem getColorItemStateActive ()
  {
    int rgb = this.preferences.getInt ( "Preferences.ColorStateActive", //$NON-NLS-1$
        DEFAULT_STATE_ACTIVE_COLOR.getRGB () );
    String caption = Messages
        .getString ( "Preferences.ColorStateActiveCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorStateActiveDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( rgb ), caption, description,
        DEFAULT_STATE_ACTIVE_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the {@link State} background.
   * 
   * @return The {@link ColorItem} of the {@link State} background.
   */
  public final ColorItem getColorItemStateBackground ()
  {
    int rgb = this.preferences.getInt ( "Preferences.ColorStateBackground", //$NON-NLS-1$
        DEFAULT_STATE_BACKGROUND_COLOR.getRGB () );
    String caption = Messages
        .getString ( "Preferences.ColorStateBackgroundCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorStateBackgroundDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( rgb ), caption, description,
        DEFAULT_STATE_BACKGROUND_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the error {@link State}.
   * 
   * @return The {@link ColorItem} of the error {@link State}.
   */
  public final ColorItem getColorItemStateError ()
  {
    int rgb = this.preferences.getInt ( "Preferences.ColorStateError", //$NON-NLS-1$
        DEFAULT_STATE_ERROR_COLOR.getRGB () );
    String caption = Messages.getString ( "Preferences.ColorStateErrorCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorStateErrorDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( rgb ), caption, description,
        DEFAULT_STATE_ERROR_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the {@link State} group.
   * 
   * @return The {@link ColorItem} of the {@link State} group.
   */
  public final ColorItem getColorItemStateGroup ()
  {
    String caption = Messages.getString ( "Preferences.ColorStateGroup" );//$NON-NLS-1$
    boolean expanded = this.preferences.getBoolean (
        "Preferences.ColorStateGroupExpanded", false ); //$NON-NLS-1$
    return new ColorItem ( caption, expanded );
  }


  /**
   * Returns the {@link ColorItem} of the selected {@link State}.
   * 
   * @return The {@link ColorItem} of the selected {@link State}.
   */
  public final ColorItem getColorItemStateSelected ()
  {
    int rgb = this.preferences.getInt ( "Preferences.ColorStateSelected", //$NON-NLS-1$
        DEFAULT_STATE_SELECTED_COLOR.getRGB () );
    String caption = Messages
        .getString ( "Preferences.ColorStateSelectedCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorStateSelectedDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( rgb ), caption, description,
        DEFAULT_STATE_SELECTED_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the start {@link State}.
   * 
   * @return The {@link ColorItem} of the start {@link State}.
   */
  public final ColorItem getColorItemStateStart ()
  {
    int rgb = this.preferences.getInt ( "Preferences.ColorStateStart", //$NON-NLS-1$
        DEFAULT_STATE_START_COLOR.getRGB () );
    String caption = Messages.getString ( "Preferences.ColorStateStartCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorStateStartDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( rgb ), caption, description,
        DEFAULT_STATE_START_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the {Symbol}.
   * 
   * @return The {@link ColorItem} of the {Symbol}.
   */
  public final ColorItem getColorItemSymbol ()
  {
    int rgb = this.preferences.getInt ( "Preferences.ColorSymbol", //$NON-NLS-1$
        DEFAULT_SYMBOL_COLOR.getRGB () );
    String caption = Messages.getString ( "Preferences.ColorSymbolCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorSymbolDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( rgb ), caption, description,
        DEFAULT_SYMBOL_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the active {@link Symbol}.
   * 
   * @return The {@link ColorItem} of the active {@link Symbol}.
   */
  public final ColorItem getColorItemSymbolActive ()
  {
    int rgb = this.preferences.getInt ( "Preferences.ColorSymbolActive", //$NON-NLS-1$
        DEFAULT_SYMBOL_ACTIVE_COLOR.getRGB () );
    String caption = Messages
        .getString ( "Preferences.ColorSymbolActiveCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorSymbolActiveDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( rgb ), caption, description,
        DEFAULT_SYMBOL_ACTIVE_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the error {@link Symbol}.
   * 
   * @return The {@link ColorItem} of the error {@link Symbol}.
   */
  public final ColorItem getColorItemSymbolError ()
  {
    int rgb = this.preferences.getInt ( "Preferences.ColorSymbolError", //$NON-NLS-1$
        DEFAULT_SYMBOL_ERROR_COLOR.getRGB () );
    String caption = Messages
        .getString ( "Preferences.ColorSymbolErrorCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorSymbolErrorDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( rgb ), caption, description,
        DEFAULT_SYMBOL_ERROR_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the {@link Symbol} group.
   * 
   * @return The {@link ColorItem} of the {@link Symbol} group.
   */
  public final ColorItem getColorItemSymbolGroup ()
  {
    String caption = Messages.getString ( "Preferences.ColorSymbolGroup" );//$NON-NLS-1$
    boolean expanded = this.preferences.getBoolean (
        "Preferences.ColorSymbolGroupExpanded", false ); //$NON-NLS-1$
    return new ColorItem ( caption, expanded );
  }


  /**
   * Returns the {@link ColorItem} of the {TerminalSymbol}.
   * 
   * @return The {@link ColorItem} of the {TerminalSymbol}.
   */
  public final ColorItem getColorItemTerminalSymbol ()
  {
    int rgb = this.preferences.getInt ( "Preferences.ColorTerminalSymbol", //$NON-NLS-1$
        DEFAULT_TERMINAL_SYMBOL_COLOR.getRGB () );
    String caption = Messages
        .getString ( "Preferences.ColorTerminalSymbolCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorTerminalSymbolDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( rgb ), caption, description,
        DEFAULT_TERMINAL_SYMBOL_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the {@link TerminalSymbol} group.
   * 
   * @return The {@link ColorItem} of the {@link TerminalSymbol} group.
   */
  public final ColorItem getColorItemTerminalSymbolGroup ()
  {
    String caption = Messages
        .getString ( "Preferences.ColorTerminalSymbolGroup" );//$NON-NLS-1$
    boolean expanded = this.preferences.getBoolean (
        "Preferences.ColorTerminalSymbolGroupExpanded", false ); //$NON-NLS-1$
    return new ColorItem ( caption, expanded );
  }


  /**
   * Returns the {@link ColorItem} of the {@link Transition}.
   * 
   * @return The {@link ColorItem} of the {@link Transition}.
   */
  public final ColorItem getColorItemTransition ()
  {
    int rgb = this.preferences.getInt ( "Preferences.ColorTransition", //$NON-NLS-1$
        DEFAULT_TRANSITION_COLOR.getRGB () );
    String caption = Messages.getString ( "Preferences.ColorTransitionCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorTransitionDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( rgb ), caption, description,
        DEFAULT_TRANSITION_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the active {@link Transition}.
   * 
   * @return The {@link ColorItem} of the active {@link Transition}.
   */
  public final ColorItem getColorItemTransitionActive ()
  {
    int rgb = this.preferences.getInt ( "Preferences.ColorTransitionActive", //$NON-NLS-1$
        DEFAULT_TRANSITION_ACTIVE_COLOR.getRGB () );
    String caption = Messages
        .getString ( "Preferences.ColorTransitionActiveCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorTransitionActiveDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( rgb ), caption, description,
        DEFAULT_TRANSITION_ACTIVE_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the error {@link Transition}.
   * 
   * @return The {@link ColorItem} of the error {@link Transition}.
   */
  public final ColorItem getColorItemTransitionError ()
  {
    int rgb = this.preferences.getInt ( "Preferences.ColorTransitionError", //$NON-NLS-1$
        DEFAULT_TRANSITION_ERROR_COLOR.getRGB () );
    String caption = Messages
        .getString ( "Preferences.ColorTransitionErrorCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorTransitionErrorDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( rgb ), caption, description,
        DEFAULT_TRANSITION_ERROR_COLOR );
  }


  /**
   * Returns the {@link ColorItem} of the {@link Transition} group.
   * 
   * @return The {@link ColorItem} of the {@link Transition} group.
   */
  public final ColorItem getColorItemTransitionGroup ()
  {
    String caption = Messages.getString ( "Preferences.ColorTransitionGroup" );//$NON-NLS-1$
    boolean expanded = this.preferences.getBoolean (
        "Preferences.ColorTransitionGroupExpanded", false ); //$NON-NLS-1$
    return new ColorItem ( caption, expanded );
  }


  /**
   * Returns the {@link ColorItem} of the selected {@link Transition}.
   * 
   * @return The {@link ColorItem} of the selected {@link Transition}.
   */
  public final ColorItem getColorItemTransitionSelected ()
  {
    int rgb = this.preferences.getInt ( "Preferences.ColorTransitionSelected", //$NON-NLS-1$
        DEFAULT_TRANSITION_SELECTED_COLOR.getRGB () );
    String caption = Messages
        .getString ( "Preferences.ColorTransitionSelectedCaption" );//$NON-NLS-1$
    String description = Messages
        .getString ( "Preferences.ColorTransitionSelectedDescription" );//$NON-NLS-1$
    return new ColorItem ( new Color ( rgb ), caption, description,
        DEFAULT_TRANSITION_SELECTED_COLOR );
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
   * Returns the {@link NonterminalSymbolSet}.
   * 
   * @return The {@link NonterminalSymbolSetItem}.
   */
  public final NonterminalSymbolSetItem getNonterminalSymbolSetItem ()
  {
    ArrayList < NonterminalSymbol > nonterminalSymbols = new ArrayList < NonterminalSymbol > ();
    String end = "no item found"; //$NON-NLS-1$
    int count = 0;
    while ( true )
    {
      String nonterminalSymbol = this.preferences.get (
          "DefaultNonterminalSymbolSet" + count, //$NON-NLS-1$
          end );
      if ( nonterminalSymbol.equals ( end ) )
      {
        break;
      }
      try
      {
        nonterminalSymbols.add ( new DefaultNonterminalSymbol (
            nonterminalSymbol ) );
      }
      catch ( NonterminalSymbolException e )
      {
        e.printStackTrace ();
        System.exit ( 1 );
      }
      count++ ;
    }
    /*
     * Return the default nonterminal symbol set if no nonterminal symbol set is
     * found.
     */
    if ( nonterminalSymbols.size () == 0 )
    {
      return new NonterminalSymbolSetItem ( DEFAULT_NONTERMINAL_SYMBOL_SET,
          DEFAULT_NONTERMINAL_SYMBOL_SET );
    }
    try
    {
      return new NonterminalSymbolSetItem ( new DefaultNonterminalSymbolSet (
          nonterminalSymbols ), DEFAULT_NONTERMINAL_SYMBOL_SET );
    }
    catch ( NonterminalSymbolSetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return null;
    }
  }


  /**
   * Returns the {@link AlphabetItem}.
   * 
   * @return The push down {@link AlphabetItem}.
   */
  public final AlphabetItem getPushDownAlphabetItem ()
  {
    ArrayList < Symbol > symbols = new ArrayList < Symbol > ();
    String end = "no item found"; //$NON-NLS-1$
    int count = 0;
    while ( true )
    {
      String symbol = this.preferences.get ( "DefaultPushDownAlphabet" + count, //$NON-NLS-1$
          end );
      if ( symbol.equals ( end ) )
      {
        break;
      }
      try
      {
        symbols.add ( new DefaultSymbol ( symbol ) );
      }
      catch ( SymbolException e )
      {
        e.printStackTrace ();
        System.exit ( 1 );
      }
      count++ ;
    }
    // Return the default alphabet if no alphabet is found.
    if ( symbols.size () == 0 )
    {
      return new AlphabetItem ( DEFAULT_PUSH_DOWN_ALPHABET,
          DEFAULT_PUSH_DOWN_ALPHABET );
    }
    try
    {
      return new AlphabetItem ( new DefaultAlphabet ( symbols ),
          DEFAULT_PUSH_DOWN_ALPHABET );
    }
    catch ( AlphabetException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
      return null;
    }
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
   * Returns the {@link TerminalSymbolSet}.
   * 
   * @return The {@link TerminalSymbolSetItem}.
   */
  public final TerminalSymbolSetItem getTerminalSymbolSetItem ()
  {
    ArrayList < TerminalSymbol > terminalSymbols = new ArrayList < TerminalSymbol > ();
    String end = "no item found"; //$NON-NLS-1$
    int count = 0;
    while ( true )
    {
      String terminalSymbol = this.preferences.get (
          "DefaultTerminalSymbolSet" + count, //$NON-NLS-1$
          end );
      if ( terminalSymbol.equals ( end ) )
      {
        break;
      }
      try
      {
        terminalSymbols.add ( new DefaultTerminalSymbol ( terminalSymbol ) );
      }
      catch ( TerminalSymbolException e )
      {
        e.printStackTrace ();
        System.exit ( 1 );
      }
      count++ ;
    }
    /*
     * Return the default terminal symbol set if no terminal symbol set is
     * found.
     */
    if ( terminalSymbols.size () == 0 )
    {
      return new TerminalSymbolSetItem ( DEFAULT_TERMINAL_SYMBOL_SET,
          DEFAULT_TERMINAL_SYMBOL_SET );
    }
    try
    {
      return new TerminalSymbolSetItem ( new DefaultTerminalSymbolSet (
          terminalSymbols ), DEFAULT_TERMINAL_SYMBOL_SET );
    }
    catch ( TerminalSymbolSetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
      return null;
    }
  }


  /**
   * Returns the use push down {@link Alphabet} value.
   * 
   * @return The use push down {@link Alphabet} value.
   */
  public final boolean getUsePushDownAlphabet ()
  {
    return this.preferences.getBoolean ( "UsePushDownAlphabet", //$NON-NLS-1$
        DEFAULT_USE_PUSH_DOWN_ALPHABET );
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
   * Removes the given {@link LanguageChangedListener}.
   * 
   * @param listener The {@link LanguageChangedListener}.
   */
  public final synchronized void removeLanguageChangedListener (
      LanguageChangedListener listener )
  {
    this.listenerList.remove ( LanguageChangedListener.class, listener );
  }


  /**
   * Sets the {@link AlphabetItem}.
   * 
   * @param alphabetItem The {@link AlphabetItem}.
   */
  public final void setAlphabetItem ( AlphabetItem alphabetItem )
  {
    logger.debug ( "set the alphabet to \"" + alphabetItem.getAlphabet () //$NON-NLS-1$
        + "\"" ); //$NON-NLS-1$

    // Delete old data
    String end = "no item found"; //$NON-NLS-1$
    int count = 0;
    loop : while ( true )
    {
      String symbol = this.preferences.get ( "DefaultAlphabet" + count, //$NON-NLS-1$
          end );
      if ( symbol.equals ( end ) )
      {
        break loop;
      }
      count++ ;
    }
    for ( int i = 0 ; i < count ; i++ )
    {
      this.preferences.remove ( "DefaultAlphabet" + i ); //$NON-NLS-1$
    }

    // Set the new data
    for ( int i = 0 ; i < alphabetItem.getAlphabet ().size () ; i++ )
    {
      this.preferences.put (
          "DefaultAlphabet" + i, alphabetItem.getAlphabet ().get ( i ) //$NON-NLS-1$
              .getName () );
    }
  }


  /**
   * Sets the {@link ColorItem} of the parser {@link NonterminalSymbol}.
   * 
   * @param colorItem The {@link ColorItem} of the parser
   *          {@link NonterminalSymbol}.
   */
  public final void setColorItemNonterminalSymbol ( ColorItem colorItem )
  {
    logger.debug ( "set color of the nonterminal symbol to \"" //$NON-NLS-1$
        + "r=" + colorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + colorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + colorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "Preferences.ColorNonterminalSymbol", colorItem //$NON-NLS-1$
        .getColor ().getRGB () & 0xFFFFFF );
  }


  /**
   * Sets the {@link ColorItem} of the {@link NonterminalSymbol} group.
   * 
   * @param colorItem The {@link ColorItem} of the {@link NonterminalSymbol}
   *          group.
   */
  public final void setColorItemNonterminalSymbolGroup ( ColorItem colorItem )
  {
    logger.debug ( "set expanded value of the nonterminal symbol group to \"" //$NON-NLS-1$
        + colorItem.isExpanded () + "\"" ); //$NON-NLS-1$
    this.preferences.putBoolean (
        "Preferences.ColorNonterminalSymbolGroupExpanded", colorItem //$NON-NLS-1$
            .isExpanded () );
  }


  /**
   * Sets the {@link ColorItem} of the parser error.
   * 
   * @param colorItem The {@link ColorItem} of the parser error.
   */
  public final void setColorItemParserError ( ColorItem colorItem )
  {
    logger.debug ( "set color of the parser error to \"" //$NON-NLS-1$
        + "r=" + colorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + colorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + colorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "Preferences.ColorParserError", colorItem //$NON-NLS-1$
        .getColor ().getRGB () & 0xFFFFFF );
  }


  /**
   * Sets the {@link ColorItem} of the parser group.
   * 
   * @param colorItem The {@link ColorItem} of the parser group.
   */
  public final void setColorItemParserGroup ( ColorItem colorItem )
  {
    logger.debug ( "set expanded value of the parser group to \"" //$NON-NLS-1$
        + colorItem.isExpanded () + "\"" ); //$NON-NLS-1$
    this.preferences.putBoolean ( "Preferences.ColorParserGroupExpanded", //$NON-NLS-1$
        colorItem.isExpanded () );
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
    this.preferences.putInt ( "Preferences.ColorParserHighlighting", colorItem //$NON-NLS-1$
        .getColor ().getRGB () & 0xFFFFFF );
  }


  /**
   * Sets the {@link ColorItem} of the parser keyword.
   * 
   * @param colorItem The {@link ColorItem} of the parser keyword.
   */
  public final void setColorItemParserKeyword ( ColorItem colorItem )
  {
    logger.debug ( "set color of the parser keyword to \"" //$NON-NLS-1$
        + "r=" + colorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + colorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + colorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "Preferences.ColorParserKeyword", colorItem //$NON-NLS-1$
        .getColor ().getRGB () & 0xFFFFFF );
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
    this.preferences.putInt ( "Preferences.ColorParserWarning", colorItem //$NON-NLS-1$
        .getColor ().getRGB () & 0xFFFFFF );
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
    this.preferences.putInt ( "Preferences.ColorState", colorItem //$NON-NLS-1$
        .getColor ().getRGB () & 0xFFFFFF );
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
    this.preferences.putInt ( "Preferences.ColorStateActive", //$NON-NLS-1$
        colorItem.getColor ().getRGB () & 0xFFFFFF );
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
    this.preferences.putInt ( "Preferences.ColorStateBackground", colorItem //$NON-NLS-1$
        .getColor ().getRGB () & 0xFFFFFF );
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
    this.preferences.putInt ( "Preferences.ColorStateError", //$NON-NLS-1$
        colorItem.getColor ().getRGB () & 0xFFFFFF );
  }


  /**
   * Sets the {@link ColorItem} of the {@link State} group.
   * 
   * @param colorItem The {@link ColorItem} of the {@link State} group.
   */
  public final void setColorItemStateGroup ( ColorItem colorItem )
  {
    logger
        .debug ( "set expanded value of the state group to \"" + colorItem.isExpanded () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putBoolean (
        "Preferences.ColorStateGroupExpanded", colorItem //$NON-NLS-1$
            .isExpanded () );
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
    this.preferences.putInt ( "Preferences.ColorStateSelected", //$NON-NLS-1$
        colorItem.getColor ().getRGB () & 0xFFFFFF );
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
    this.preferences.putInt ( "Preferences.ColorStateStart", //$NON-NLS-1$
        colorItem.getColor ().getRGB () & 0xFFFFFF );
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
    this.preferences.putInt ( "Preferences.ColorSymbol", colorItem //$NON-NLS-1$
        .getColor ().getRGB () & 0xFFFFFF );
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
    this.preferences.putInt ( "Preferences.ColorSymbolActive", //$NON-NLS-1$
        colorItem.getColor ().getRGB () & 0xFFFFFF );
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
    this.preferences.putInt ( "Preferences.ColorSymbolError", colorItem //$NON-NLS-1$
        .getColor ().getRGB () & 0xFFFFFF );
  }


  /**
   * Sets the {@link ColorItem} of the {@link Symbol} group.
   * 
   * @param colorItem The {@link ColorItem} of the {@link Symbol} group.
   */
  public final void setColorItemSymbolGroup ( ColorItem colorItem )
  {
    logger.debug ( "set expanded value of the symbol group to \"" //$NON-NLS-1$
        + colorItem.isExpanded () + "\"" ); //$NON-NLS-1$
    this.preferences.putBoolean ( "Preferences.ColorSymbolGroupExpanded", //$NON-NLS-1$
        colorItem.isExpanded () );
  }


  /**
   * Sets the {@link ColorItem} of the parser {@link TerminalSymbol}.
   * 
   * @param colorItem The {@link ColorItem} of the parser {@link TerminalSymbol}.
   */
  public final void setColorItemTerminalSymbol ( ColorItem colorItem )
  {
    logger.debug ( "set color of the terminal symbol to \"" //$NON-NLS-1$
        + "r=" + colorItem.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "g=" + colorItem.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
        + "b=" + colorItem.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "Preferences.ColorTerminalSymbol", colorItem //$NON-NLS-1$
        .getColor ().getRGB () & 0xFFFFFF );
  }


  /**
   * Sets the {@link ColorItem} of the {@link TerminalSymbol} group.
   * 
   * @param colorItem The {@link ColorItem} of the {@link TerminalSymbol} group.
   */
  public final void setColorItemTerminalSymbolGroup ( ColorItem colorItem )
  {
    logger.debug ( "set expanded value of the terminal symbol group to \"" //$NON-NLS-1$
        + colorItem.isExpanded () + "\"" ); //$NON-NLS-1$
    this.preferences.putBoolean (
        "Preferences.ColorTerminalSymbolGroupExpanded", colorItem //$NON-NLS-1$
            .isExpanded () );
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
    this.preferences.putInt ( "Preferences.ColorTransition", colorItem //$NON-NLS-1$
        .getColor ().getRGB () & 0xFFFFFF );
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
    this.preferences.putInt ( "Preferences.ColorTransitionActive", //$NON-NLS-1$
        colorItem.getColor ().getRGB () & 0xFFFFFF );
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
    this.preferences.putInt ( "Preferences.ColorTransitionError", colorItem //$NON-NLS-1$
        .getColor ().getRGB () & 0xFFFFFF );
  }


  /**
   * Sets the {@link ColorItem} of the {@link Transition} group.
   * 
   * @param colorItem The {@link ColorItem} of the {@link Transition} group.
   */
  public final void setColorItemTransitionGroup ( ColorItem colorItem )
  {
    logger
        .debug ( "set expanded value of the transition group to \"" + colorItem.isExpanded () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putBoolean (
        "Preferences.ColorTransitionGroupExpanded", colorItem //$NON-NLS-1$
            .isExpanded () );
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
    this.preferences.putInt ( "Preferences.ColorTransitionSelected", //$NON-NLS-1$
        colorItem.getColor ().getRGB () & 0xFFFFFF );
  }


  /**
   * Sets the {@link LanguageItem}.
   * 
   * @param languageItem The {@link LanguageItem}.
   */
  public final void setLanguageItem ( LanguageItem languageItem )
  {
    logger.debug ( "set language to \"" //$NON-NLS-1$
        + languageItem.getLocale ().getLanguage () + "\"" ); //$NON-NLS-1$
    this.preferences.put ( "PreferencesDialog.Language.Title", languageItem //$NON-NLS-1$
        .getTitle () );
    this.preferences.put ( "PreferencesDialog.Language.Language", languageItem //$NON-NLS-1$
        .getLocale ().getLanguage () );
  }


  /**
   * Sets the {@link NonterminalSymbolSetItem}.
   * 
   * @param nonterminalSymbolSetItem The {@link NonterminalSymbolSetItem}.
   */
  public final void setNonterminalSymbolSetItem (
      NonterminalSymbolSetItem nonterminalSymbolSetItem )
  {
    logger.debug ( "set the nonterminal symbol set to \"" //$NON-NLS-1$
        + nonterminalSymbolSetItem.getNonterminalSymbolSet () + "\"" ); //$NON-NLS-1$

    // Delete old data
    String end = "no item found"; //$NON-NLS-1$
    int count = 0;
    loop : while ( true )
    {
      String symbol = this.preferences.get (
          "DefaultNonterminalSymbolSet" + count, //$NON-NLS-1$
          end );
      if ( symbol.equals ( end ) )
      {
        break loop;
      }
      count++ ;
    }
    for ( int i = 0 ; i < count ; i++ )
    {
      this.preferences.remove ( "DefaultNonterminalSymbolSet" + i ); //$NON-NLS-1$
    }

    // Set new data
    for ( int i = 0 ; i < nonterminalSymbolSetItem.getNonterminalSymbolSet ()
        .size () ; i++ )
    {
      this.preferences.put ( "DefaultNonterminalSymbolSet" + i, //$NON-NLS-1$
          nonterminalSymbolSetItem.getNonterminalSymbolSet ().get ( i )
              .getName () );
    }
  }


  /**
   * Sets the push down {@link AlphabetItem}.
   * 
   * @param pushDownAlphabetItem The push down {@link AlphabetItem}.
   */
  public final void setPushDownAlphabetItem ( AlphabetItem pushDownAlphabetItem )
  {
    logger.debug ( "set the push down alphabet to \"" //$NON-NLS-1$
        + pushDownAlphabetItem.getAlphabet () + "\"" ); //$NON-NLS-1$

    // Delete old data
    String end = "no item found"; //$NON-NLS-1$
    int count = 0;
    loop : while ( true )
    {
      String symbol = this.preferences.get ( "DefaultPushDownAlphabet" + count, //$NON-NLS-1$
          end );
      if ( symbol.equals ( end ) )
      {
        break loop;
      }
      count++ ;
    }
    for ( int i = 0 ; i < count ; i++ )
    {
      this.preferences.remove ( "DefaultPushDownAlphabet" + i ); //$NON-NLS-1$
    }

    // Set new data
    for ( int i = 0 ; i < pushDownAlphabetItem.getAlphabet ().size () ; i++ )
    {
      this.preferences.put ( "DefaultPushDownAlphabet" + i, //$NON-NLS-1$
          pushDownAlphabetItem.getAlphabet ().get ( i ).getName () );
    }
  }


  /**
   * Sets the system {@link Locale}.
   * 
   * @param locale The system {@link Locale}.
   */
  public final void setSystemLocale ( Locale locale )
  {
    this.systemLocale = locale;
  }


  /**
   * Sets the {@link TerminalSymbolSetItem}.
   * 
   * @param terminalSymbolSetItem The {@link TerminalSymbolSetItem}.
   */
  public final void setTerminalSymbolSetItem (
      TerminalSymbolSetItem terminalSymbolSetItem )
  {
    logger.debug ( "set the terminal symbol set to \"" //$NON-NLS-1$
        + terminalSymbolSetItem.getTerminalSymbolSet () + "\"" ); //$NON-NLS-1$

    // Delete old data
    String end = "no item found"; //$NON-NLS-1$
    int count = 0;
    loop : while ( true )
    {
      String symbol = this.preferences.get (
          "DefaultTerminalSymbolSet" + count, //$NON-NLS-1$
          end );
      if ( symbol.equals ( end ) )
      {
        break loop;
      }
      count++ ;
    }
    for ( int i = 0 ; i < count ; i++ )
    {
      this.preferences.remove ( "DefaultTerminalSymbolSet" + i ); //$NON-NLS-1$
    }

    // Set new data
    for ( int i = 0 ; i < terminalSymbolSetItem.getTerminalSymbolSet ().size () ; i++ )
    {
      this.preferences.put ( "DefaultTerminalSymbolSet" + i, //$NON-NLS-1$
          terminalSymbolSetItem.getTerminalSymbolSet ().get ( i ).getName () );
    }
  }


  /**
   * Sets the use push down {@link Alphabet} value.
   * 
   * @param usePushDownAlphabet The use push down {@link Alphabet} to set.
   */
  public final void setUsePushDownAlphabet ( boolean usePushDownAlphabet )
  {
    this.preferences.putBoolean ( "UsePushDownAlphabet", //$NON-NLS-1$
        usePushDownAlphabet );
  }
}
