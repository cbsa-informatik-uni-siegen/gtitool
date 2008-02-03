package de.unisiegen.gtitool.ui.preferences;


import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.event.EventListenerList;

import org.apache.log4j.Logger;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.ui.logic.MainWindow;
import de.unisiegen.gtitool.ui.logic.PreferencesDialog;
import de.unisiegen.gtitool.ui.preferences.item.AlphabetItem;
import de.unisiegen.gtitool.ui.preferences.item.AutoStepItem;
import de.unisiegen.gtitool.ui.preferences.item.LanguageItem;
import de.unisiegen.gtitool.ui.preferences.item.LookAndFeelItem;
import de.unisiegen.gtitool.ui.preferences.item.MouseSelectionItem;
import de.unisiegen.gtitool.ui.preferences.item.OpenedFilesItem;
import de.unisiegen.gtitool.ui.preferences.item.RecentlyUsedFilesItem;
import de.unisiegen.gtitool.ui.preferences.item.TransitionItem;
import de.unisiegen.gtitool.ui.preferences.item.ZoomFactorItem;
import de.unisiegen.gtitool.ui.preferences.listener.LanguageChangedListener;
import de.unisiegen.gtitool.ui.preferences.listener.ZoomFactorChangedListener;


/**
 * Manages the preferences for the ui project.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class PreferenceManager extends
    de.unisiegen.gtitool.core.preferences.PreferenceManager
{

  /**
   * The default use push down {@link Alphabet}.
   */
  public static boolean DEFAULT_USE_PUSH_DOWN_ALPHABET = false;


  /**
   * The default console divider location.
   */
  public static int DEFAULT_DIVIDER_LOCATION_CONSOLE = -1;


  /**
   * The default table divider location.
   */
  public static int DEFAULT_DIVIDER_LOCATION_TABLE = -1;


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
   * The default {@link MouseSelectionItem}.
   */
  public static final MouseSelectionItem DEFAULT_MOUSE_SELECTION_ITEM = MouseSelectionItem.WITHOUT_RETURN_TO_MOUSE;


  /**
   * The default {@link MouseSelectionItem}.
   */
  public static final TransitionItem DEFAULT_TRANSITION_ITEM = TransitionItem.DRAG_MODE;


  /**
   * The default look and feel class name.
   */
  public static final String DEFAULT_LOOK_AND_FEEL_CLASS_NAME = UIManager
      .getSystemLookAndFeelClassName ();


  /**
   * The default look and feel name.
   */
  public static final String DEFAULT_LOOK_AND_FEEL_NAME = "System"; //$NON-NLS-1$


  /**
   * The default maximized state of the {@link MainWindow}.
   */
  public static boolean DEFAULT_MAXIMIZED = false;


  /**
   * The default width of the {@link MainWindow}.
   */
  public static int DEFAULT_WIDTH = 960;


  /**
   * The default hight of the {@link MainWindow}.
   */
  public static int DEFAULT_HEIGHT = 600;


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
   * The default preference dialog last active tab.
   */
  public static int DEFAULT_PREFERENCES_DIALOG_LAST_ACTIVE_TAB = 0;


  /**
   * The visible console value.
   */
  public static boolean DEFAULT_VISIBLE_CONSOLE = true;


  /**
   * The visible table value.
   */
  public static boolean DEFAULT_VISIBLE_TABLE = true;


  /**
   * The default working path.
   */
  public static String DEFAULT_WORKING_PATH = "."; //$NON-NLS-1$


  /**
   * The default zoom factor value.
   */
  public static ZoomFactorItem DEFAULT_ZOOM_FACTOR_ITEM = ZoomFactorItem.ZOOM_100;


  /**
   * The default {@link AutoStepItem}.
   */
  public static AutoStepItem DEFAULT_AUTO_STEP_INTERVAL_ITEM = AutoStepItem.AUTO_STEP_2000;


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
   * The system {@link Locale}.
   */
  private Locale systemLocale;


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * Allocates a new <code>PreferencesManager</code>.
   */
  protected PreferenceManager ()
  {
    super ();
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
   * Adds the given {@link ZoomFactorChangedListener}.
   * 
   * @param listener The {@link ZoomFactorChangedListener}.
   */
  public final synchronized void addZoomFactorChangedListener (
      ZoomFactorChangedListener listener )
  {
    this.listenerList.add ( ZoomFactorChangedListener.class, listener );
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
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].languageChanged ();
    }
  }


  /**
   * Let the listeners know that the zoom factor has changed.
   * 
   * @param zoomFactor The new {@link ZoomFactorItem}.
   */
  public final void fireZoomFactorChanged ( ZoomFactorItem zoomFactor )
  {
    ZoomFactorChangedListener [] listeners = this.listenerList
        .getListeners ( ZoomFactorChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].zoomFactorChanged ( zoomFactor );
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
        symbols.add ( new DefaultSymbol ( symbol ) );
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
   * Returns the {@link AutoStepItem}.
   * 
   * @return The {@link AutoStepItem}.
   */
  public final AutoStepItem getAutoStepItem ()
  {
    return AutoStepItem.create ( this.preferences.getInt (
        "AutoStep", DEFAULT_AUTO_STEP_INTERVAL_ITEM.getAutoStepInterval () ) ); //$NON-NLS-1$
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
   * Returns the {@link MouseSelectionItem}.
   * 
   * @return The {@link MouseSelectionItem}.
   */
  public final MouseSelectionItem getMouseSelectionItem ()
  {
    int index = this.preferences.getInt (
        "PreferencesDialog.MouseSelectionItem.Index", //$NON-NLS-1$
        DEFAULT_MOUSE_SELECTION_ITEM.getIndex () );
    return MouseSelectionItem.create ( index );
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
    String activeFileName = this.preferences.get (
        "MainWindow.OpenedActiveFile", "" ); //$NON-NLS-1$ //$NON-NLS-2$
    File activeFile = activeFileName.equals ( "" ) ? null //$NON-NLS-1$
        : new File ( activeFileName );
    return new OpenedFilesItem ( files, activeFile );
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
   * Returns the {@link AlphabetItem}.
   * 
   * @return The push down {@link AlphabetItem}.
   */
  public final AlphabetItem getPushDownAlphabetItem ()
  {
    ArrayList < Symbol > symbols = new ArrayList < Symbol > ();
    int count = this.preferences.getInt ( "DefaultPushDownAlphabetCount", //$NON-NLS-1$
        Integer.MAX_VALUE );
    String end = "no item found"; //$NON-NLS-1$
    loop : for ( int i = 0 ; i < count ; i++ )
    {
      String symbol = this.preferences.get ( "DefaultPushDownAlphabet" + i, //$NON-NLS-1$
          end );
      if ( symbol.equals ( end ) )
      {
        break loop;
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
   * Returns the {@link TransitionItem}.
   * 
   * @return The {@link TransitionItem}.
   */
  public final TransitionItem getTransitionItem ()
  {
    int index = this.preferences.getInt (
        "PreferencesDialog.TransitionItem.Index", //$NON-NLS-1$
        DEFAULT_TRANSITION_ITEM.getIndex () );
    return TransitionItem.create ( index );
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
   * Returns the visible console value.
   * 
   * @return The visible console value.
   */
  public final boolean getVisibleConsole ()
  {
    return this.preferences.getBoolean ( "MachinePanel.ConsoleVisible", //$NON-NLS-1$
        DEFAULT_VISIBLE_CONSOLE );
  }


  /**
   * Returns the visible table value.
   * 
   * @return The visible table value.
   */
  public final boolean getVisibleTable ()
  {
    return this.preferences.getBoolean ( "MachinePanel.TableVisible", //$NON-NLS-1$
        DEFAULT_VISIBLE_TABLE );
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
    return ZoomFactorItem.create ( this.preferences.getInt (
        "ZoomFactor", DEFAULT_ZOOM_FACTOR_ITEM.getFactor () ) ); //$NON-NLS-1$
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
   * Removes the given {@link ZoomFactorChangedListener}.
   * 
   * @param listener The {@link ZoomFactorChangedListener}.
   */
  public final synchronized void removeZoomFactorChangedListener (
      ZoomFactorChangedListener listener )
  {
    this.listenerList.remove ( ZoomFactorChangedListener.class, listener );
  }


  /**
   * Sets the {@link AlphabetItem}.
   * 
   * @param alphabetItem The {@link AlphabetItem}.
   */
  public final void setAlphabetItem ( AlphabetItem alphabetItem )
  {
    logger
        .debug ( "set the alphabet to \"" + alphabetItem.getAlphabet () + "\"" ); //$NON-NLS-1$//$NON-NLS-2$
    for ( int i = 0 ; i < alphabetItem.getAlphabet ().size () ; i++ )
    {
      this.preferences.put (
          "DefaultAlphabet" + i, alphabetItem.getAlphabet ().get ( i ) //$NON-NLS-1$
              .getName () );
    }
    this.preferences.putInt ( "DefaultAlphabetCount", alphabetItem //$NON-NLS-1$
        .getAlphabet ().size () );
  }


  /**
   * Sets the {@link AutoStepItem}.
   * 
   * @param autoStepInterval The {@link AutoStepItem}.
   */
  public final void setAutoStepItem ( AutoStepItem autoStepInterval )
  {
    logger.debug ( "set auto step interval to \"" //$NON-NLS-1$
        + autoStepInterval.getAutoStepInterval () + "\"" ); //$NON-NLS-1$
    this.preferences.putInt ( "AutoStep", autoStepInterval //$NON-NLS-1$
        .getAutoStepInterval () );
  }


  /**
   * Sets the console divider location.
   * 
   * @param location The console divider location.
   */
  public final void setDividerLocationConsole ( int location )
  {
    logger.debug ( "set console divider location to \"" + location + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "MachinePanel.DividerConsole", location ); //$NON-NLS-1$
  }


  /**
   * Sets the table divider location.
   * 
   * @param location The table divider location.
   */
  public final void setDividerLocationTable ( int location )
  {
    logger.debug ( "set table divider location to \"" + location + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "MachinePanel.DividerTable", location ); //$NON-NLS-1$
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
   * Sets the {@link LookAndFeelItem}.
   * 
   * @param lookAndFeelItem The {@link LookAndFeelItem}.
   */
  public final void setLookAndFeelItem ( LookAndFeelItem lookAndFeelItem )
  {
    logger.debug ( "set look and feel to \"" //$NON-NLS-1$
        + lookAndFeelItem.getName () + "\"" ); //$NON-NLS-1$
    this.preferences.put (
        "PreferencesDialog.LookAndFeel.Name", lookAndFeelItem.getName () ); //$NON-NLS-1$
    this.preferences.put ( "PreferencesDialog.LookAndFeel.ClassName", //$NON-NLS-1$
        lookAndFeelItem.getClassName () );
  }


  /**
   * Sets the {@link MainWindow} preferences.
   * 
   * @param jFrame The {@link JFrame} of the {@link MainWindow}.
   */
  public final void setMainWindowPreferences ( JFrame jFrame )
  {
    if ( ( jFrame.getExtendedState () & Frame.MAXIMIZED_BOTH ) == 0 )
    {
      logger.debug ( "set main window maximized to \"false\"" ); //$NON-NLS-1$
      this.preferences.putBoolean ( "MainWindow.Maximized", false ); //$NON-NLS-1$
      Rectangle bounds = jFrame.getBounds ();
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
   * Sets the {@link MouseSelectionItem}.
   * 
   * @param mouseSelectionItem The {@link MouseSelectionItem}.
   */
  public final void setMouseSelectionItem (
      MouseSelectionItem mouseSelectionItem )
  {
    logger.debug ( "set mouse selection item to \"" //$NON-NLS-1$
        + mouseSelectionItem.getIndex () + "\"" ); //$NON-NLS-1$
    this.preferences.putInt ( "PreferencesDialog.MouseSelectionItem.Index", //$NON-NLS-1$
        mouseSelectionItem.getIndex () );
  }


  /**
   * Sets the opened files and the index of the last active file.
   * 
   * @param openedFilesItem The {@link OpenedFilesItem}.
   */
  public final void setOpenedFilesItem ( OpenedFilesItem openedFilesItem )
  {
    for ( int i = 0 ; i < openedFilesItem.getFiles ().size () ; i++ )
    {
      logger.debug ( "set opened file \"" + i + "\" to \"" //$NON-NLS-1$//$NON-NLS-2$
          + openedFilesItem.getFiles ().get ( i ).getAbsolutePath () + "\"" ); //$NON-NLS-1$
      this.preferences.put ( "MainWindow.OpenedFiles" + i, openedFilesItem //$NON-NLS-1$
          .getFiles ().get ( i ).getAbsolutePath () );
    }
    this.preferences.putInt ( "MainWindow.OpenedFilesCount", //$NON-NLS-1$
        openedFilesItem.getFiles ().size () );

    if ( openedFilesItem.getActiveFile () == null )
    {
      logger.debug ( "set last opened file to \"null\"" ); //$NON-NLS-1$
      this.preferences.put ( "MainWindow.OpenedActiveFile", "" ); //$NON-NLS-1$ //$NON-NLS-2$
    }
    else
    {
      logger.debug ( "set last opened file to \"" //$NON-NLS-1$
          + openedFilesItem.getActiveFile ().getAbsolutePath () + "\"" ); //$NON-NLS-1$
      this.preferences.put ( "MainWindow.OpenedActiveFile", //$NON-NLS-1$
          openedFilesItem.getActiveFile ().getAbsolutePath () );
    }
  }


  /**
   * Sets the last active tab of the {@link PreferencesDialog}.
   * 
   * @param index The index of the last active {@link PreferencesDialog}.
   */
  public final void setPreferencesDialogLastActiveTab ( int index )
  {
    logger.debug ( "set last active tab to \"" + index + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "PreferencesDialog.LastActiveTab", index ); //$NON-NLS-1$
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
    for ( int i = 0 ; i < pushDownAlphabetItem.getAlphabet ().size () ; i++ )
    {
      this.preferences.put ( "DefaultPushDownAlphabet" + i, //$NON-NLS-1$
          pushDownAlphabetItem.getAlphabet ().get ( i ).getName () );
    }
    this.preferences.putInt (
        "DefaultPushDownAlphabetCount", pushDownAlphabetItem //$NON-NLS-1$
            .getAlphabet ().size () );
  }


  /**
   * Sets the recently used files.
   * 
   * @param recentlyUsedFilesItem The {@link RecentlyUsedFilesItem}.
   */
  public final void setRecentlyUsedFilesItem (
      RecentlyUsedFilesItem recentlyUsedFilesItem )
  {
    for ( int i = 0 ; i < recentlyUsedFilesItem.getFiles ().size () ; i++ )
    {
      logger.debug ( "set recently used file \"" + i + "\" to \"" //$NON-NLS-1$//$NON-NLS-2$
          + recentlyUsedFilesItem.getFiles ().get ( i ).getAbsolutePath ()
          + "\"" ); //$NON-NLS-1$
      this.preferences.put (
          "MainWindow.RecentlyUsedFiles" + i, recentlyUsedFilesItem //$NON-NLS-1$
              .getFiles ().get ( i ).getAbsolutePath () );
    }
    this.preferences.putInt ( "MainWindow.RecentlyUsedFilesCount", //$NON-NLS-1$
        recentlyUsedFilesItem.getFiles ().size () );
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
   * Sets the {@link TransitionItem}.
   * 
   * @param transitionItem The {@link TransitionItem}.
   */
  public final void setTransitionItem ( TransitionItem transitionItem )
  {
    logger.debug ( "set transition item to \"" //$NON-NLS-1$
        + transitionItem.getIndex () + "\"" ); //$NON-NLS-1$
    this.preferences.putInt ( "PreferencesDialog.TransitionItem.Index", //$NON-NLS-1$
        transitionItem.getIndex () );
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


  /**
   * Sets the visible console value.
   * 
   * @param visible The visible console value.
   */
  public final void setVisibleConsole ( boolean visible )
  {
    logger.debug ( "set the visible console to \"" + visible + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putBoolean ( "MachinePanel.ConsoleVisible", //$NON-NLS-1$
        visible );
  }


  /**
   * Sets the visible table value.
   * 
   * @param visible The visible table value.
   */
  public final void setVisibleTable ( boolean visible )
  {
    logger.debug ( "set the visible table to \"" + visible + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putBoolean ( "MachinePanel.TableVisible", //$NON-NLS-1$
        visible );
  }


  /**
   * Sets the working path.
   * 
   * @param path The working path.
   */
  public final void setWorkingPath ( String path )
  {
    logger.debug ( "set the working path to \"" + path + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.put ( "WorkingPath", path ); //$NON-NLS-1$
  }


  /**
   * Sets the {@link ZoomFactorItem}.
   * 
   * @param zoomFactor The {@link ZoomFactorItem}.
   */
  public final void setZoomFactorItem ( ZoomFactorItem zoomFactor )
  {
    logger.debug ( "set zoom factor to \"" //$NON-NLS-1$
        + zoomFactor.getFactor () + "\"" ); //$NON-NLS-1$
    this.preferences.putInt ( "ZoomFactor", zoomFactor.getFactor () ); //$NON-NLS-1$
  }
}
