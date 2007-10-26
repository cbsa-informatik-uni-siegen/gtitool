package de.unisiegen.gtitool.ui.preferences;


import java.awt.Color;
import java.awt.Frame;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.UIManager;

import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.logic.MainWindow;
import de.unisiegen.gtitool.ui.logic.PreferencesDialog;


/**
 * Manages the preferences for the user interface.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class PreferenceManager
{

  /**
   * The single instance of the <code>PreferenceManager<code>
   */
  private static PreferenceManager singlePreferenceManager;


  /**
   * The default width of the {@link MainWindow}.
   */
  private static int DEFAULT_WIDTH = 640;


  /**
   * The default hight of the {@link MainWindow}.
   */
  private static int DEFAULT_HEIGHT = 480;


  /**
   * The default x position of the {@link MainWindow}.
   */
  private static int DEFAULT_POSTION_X = 0;


  /**
   * The default y position of the {@link MainWindow}.
   */
  private static int DEFAULT_POSTION_Y = 0;


  /**
   * The default maximized state of the {@link MainWindow}.
   */
  private static boolean DEFAULT_MAXIMIZED = false;


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
  private Preferences preferences;


  /**
   * The list of {@link LanguageChangedListener}.
   */
  private ArrayList < LanguageChangedListener > languageChangedListenerList = new ArrayList < LanguageChangedListener > ();


  /**
   * The system {@link Locale}.
   */
  private Locale systemLocale;


  /**
   * Allocates a new <code>PreferencesManager</code>.
   */
  private PreferenceManager ()
  {
    this.preferences = Preferences.userNodeForPackage ( this.getClass () );
  }


  /**
   * Adds the given {@link LanguageChangedListener}.
   * 
   * @param pListener The {@link LanguageChangedListener}.
   */
  public void addLanguageChangedListener ( LanguageChangedListener pListener )
  {
    this.languageChangedListenerList.add ( pListener );
  }


  /**
   * Let the listeners know that the language has changed.
   * 
   * @param pNewLocale The new {@link Locale}.
   */
  public void fireLanguageChanged ( Locale pNewLocale )
  {
    Locale.setDefault ( pNewLocale );
    for ( LanguageChangedListener current : this.languageChangedListenerList )
    {
      current.languageChanged ();
    }
  }


  /**
   * Returns the {@link ColorItem} of the <code>pItem</code>.
   * 
   * @param pName The name of the {@link ColorItem}.
   * @param pDefault The default {@link Color} of the {@link ColorItem}.
   * @return The {@link ColorItem} of the <code>pItem</code>.
   */
  public ColorItem getColorItem ( String pName, Color pDefault )
  {
    int r = this.preferences.getInt (
        "PreferencesDialog.Color" + pName + "R", pDefault.getRed () ); //$NON-NLS-1$ //$NON-NLS-2$
    int g = this.preferences.getInt (
        "PreferencesDialog.Color" + pName + "G", pDefault.getGreen () ); //$NON-NLS-1$ //$NON-NLS-2$ 
    int b = this.preferences.getInt (
        "PreferencesDialog.Color" + pName + "B", pDefault.getBlue () ); //$NON-NLS-1$ //$NON-NLS-2$ 
    String caption = Messages
        .getString ( "PreferencesDialog.Color" + pName + "Caption" );//$NON-NLS-1$ //$NON-NLS-2$ 
    String description = Messages
        .getString ( "PreferencesDialog.Color" + pName + "Description" );//$NON-NLS-1$ //$NON-NLS-2$
    return new ColorItem ( pName, new Color ( r, g, b ), caption, description );
  }


  /**
   * Returns the {@link LanguageItem}.
   * 
   * @return The {@link LanguageItem}.
   */
  public LanguageItem getLanguageItem ()
  {
    String title = this.preferences.get ( "PreferencesDialog.Language.Title", //$NON-NLS-1$
        "Default" ); //$NON-NLS-1$
    String language = this.preferences.get (
        "PreferencesDialog.Language.Language", getSystemLocale ().getLanguage () ); //$NON-NLS-1$
    return new LanguageItem ( title, new Locale ( language ) );
  }


  /**
   * Returns the {@link LookAndFeelItem}.
   * 
   * @return The {@link LookAndFeelItem}.
   */
  public LookAndFeelItem getLookAndFeelItem ()
  {
    String name = this.preferences.get ( "PreferencesDialog.LookAndFeel.Name", //$NON-NLS-1$
        "System" ); //$NON-NLS-1$
    String className = this.preferences.get (
        "PreferencesDialog.LookAndFeel.ClassName", UIManager //$NON-NLS-1$
            .getSystemLookAndFeelClassName () );
    return new LookAndFeelItem ( name, className );
  }


  /**
   * Returns the {@link MainWindow} bounds.
   * 
   * @return The {@link MainWindow} bounds.
   */
  public Rectangle getMainWindowBounds ()
  {
    int x = this.preferences
        .getInt ( "mainWindow.xPosition", DEFAULT_POSTION_X ); //$NON-NLS-1$
    int y = this.preferences
        .getInt ( "mainWindow.yPosition", DEFAULT_POSTION_Y ); //$NON-NLS-1$
    int width = this.preferences.getInt ( "mainWindow.width", DEFAULT_WIDTH ); //$NON-NLS-1$
    int height = this.preferences.getInt ( "mainWindow.height", DEFAULT_HEIGHT ); //$NON-NLS-1$
    return new Rectangle ( x, y, width, height );
  }


  /**
   * Returns the maximized state of the {@link MainWindow}.
   * 
   * @return The maximized state of the {@link MainWindow}.
   */
  public boolean getMainWindowMaximized ()
  {
    return this.preferences.getBoolean ( "mainWindow.maximized", //$NON-NLS-1$
        DEFAULT_MAXIMIZED );
  }


  /**
   * Returns the last active tab of the {@link PreferencesDialog}.
   * 
   * @return The last active tab of the {@link PreferencesDialog}.
   */
  public int getPreferencesDialogLastActiveTab ()
  {
    return this.preferences.getInt ( "PreferencesDialog.LastActiveTab", 0 ); //$NON-NLS-1$
  }


  /**
   * Returns the recently used files and the index of the last active file.
   * 
   * @return The recently used files and the index of the last active file.
   */
  public RecentlyUsed getRecentlyUsed ()
  {
    ArrayList < File > files = new ArrayList < File > ();
    int i = 0;
    String end = "no item found"; //$NON-NLS-1$
    while ( true )
    {
      String file = this.preferences.get ( "mainWindow.recentlyUsedFiles" + i, //$NON-NLS-1$
          end );
      if ( file.equals ( end ) )
      {
        break;
      }
      files.add ( new File ( file ) );
    }
    int activeIndex = this.preferences.getInt (
        "mainWindow.recentlyUsedActiveIndex", -1 ); //$NON-NLS-1$
    return new RecentlyUsed ( files, activeIndex );
  }


  /**
   * Returns the system {@link Locale}.
   * 
   * @return The system {@link Locale}.
   */
  public Locale getSystemLocale ()
  {
    return this.systemLocale;
  }


  /**
   * Returns the working path.
   * 
   * @return The working path.
   */
  public String getWorkingPath ()
  {
    return this.preferences.get ( "workingPath", "." ); //$NON-NLS-1$ //$NON-NLS-2$
  }


  /**
   * Removes the given {@link LanguageChangedListener}.
   * 
   * @param pListener The {@link LanguageChangedListener}.
   */
  public void removeLanguageChangedListener ( LanguageChangedListener pListener )
  {
    this.languageChangedListenerList.remove ( pListener );
  }


  /**
   * Sets the {@link ColorItem}.
   * 
   * @param pColorItem The {@link ColorItem}.
   */
  public void setColorItem ( ColorItem pColorItem )
  {
    this.preferences.putInt ( "PreferencesDialog.Color" + pColorItem.getName () //$NON-NLS-1$
        + "R", pColorItem.getColor ().getRed () ); //$NON-NLS-1$
    this.preferences.putInt ( "PreferencesDialog.Color" + pColorItem.getName () //$NON-NLS-1$
        + "G", pColorItem.getColor ().getGreen () ); //$NON-NLS-1$
    this.preferences.putInt ( "PreferencesDialog.Color" + pColorItem.getName () //$NON-NLS-1$
        + "B", pColorItem.getColor ().getBlue () ); //$NON-NLS-1$
    this.preferences.put ( "PreferencesDialog.Color" + pColorItem.getName () //$NON-NLS-1$
        + "Caption", pColorItem.getCaption () ); //$NON-NLS-1$
    this.preferences.put ( "PreferencesDialog.Color" + pColorItem.getName () //$NON-NLS-1$
        + "Description", pColorItem.getDescription () ); //$NON-NLS-1$
  }


  /**
   * Sets the {@link LanguageItem}.
   * 
   * @param pLanguageItem The {@link LanguageItem}.
   */
  public void setLanguageItem ( LanguageItem pLanguageItem )
  {
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
  public void setLookAndFeelItem ( LookAndFeelItem pLookAndFeelItem )
  {
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
  public void setMainWindowPreferences ( JFrame pJFrame )
  {
    if ( ( pJFrame.getExtendedState () & Frame.MAXIMIZED_BOTH ) == 0 )
    {
      this.preferences.putBoolean ( "mainWindow.maximized", false ); //$NON-NLS-1$
      Rectangle bounds = pJFrame.getBounds ();
      this.preferences.putInt ( "mainWindow.xPosition", bounds.x ); //$NON-NLS-1$
      this.preferences.putInt ( "mainWindow.yPosition", bounds.y ); //$NON-NLS-1$
      this.preferences.putInt ( "mainWindow.width", bounds.width ); //$NON-NLS-1$
      this.preferences.putInt ( "mainWindow.height", bounds.height ); //$NON-NLS-1$
    }
    else
    {
      this.preferences.putBoolean ( "mainWindow.maximized", true ); //$NON-NLS-1$
    }
  }


  /**
   * Sets the last active tab of the {@link PreferencesDialog}.
   * 
   * @param pIndex The index of the last active {@link PreferencesDialog}.
   */
  public void setPreferencesDialogLastActiveTab ( int pIndex )
  {
    this.preferences.putInt ( "PreferencesDialog.LastActiveTab", pIndex ); //$NON-NLS-1$
  }


  /**
   * Sets the recently used files and the index of the last active file.
   * 
   * @param pRecentlyUsed The {@link RecentlyUsed}.
   */
  public void setRecentlyUsed ( RecentlyUsed pRecentlyUsed )
  {
    for ( int i = 0 ; i < pRecentlyUsed.getFiles ().size () ; i++ )
    {
      this.preferences.put ( "mainWindow.recentlyUsedFiles" + i, pRecentlyUsed //$NON-NLS-1$
          .getFiles ().get ( i ).getAbsolutePath () );
    }
    this.preferences.putInt ( "mainWindow.recentlyUsedActiveIndex", //$NON-NLS-1$
        pRecentlyUsed.getActiveIndex () );
  }


  /**
   * Sets the system {@link Locale}.
   * 
   * @param pLocale The system {@link Locale}.
   */
  public void setSystemLocale ( Locale pLocale )
  {
    this.systemLocale = pLocale;
  }


  /**
   * Sets the working path.
   * 
   * @param pPath The working path.
   */
  public void setWorkingPath ( String pPath )
  {
    this.preferences.put ( "workingPath", pPath ); //$NON-NLS-1$
  }

}
