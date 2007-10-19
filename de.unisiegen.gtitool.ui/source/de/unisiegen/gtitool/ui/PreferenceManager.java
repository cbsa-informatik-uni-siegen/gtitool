package de.unisiegen.gtitool.ui;


import java.awt.Frame;
import java.awt.Rectangle;
import java.util.prefs.Preferences;

import javax.swing.JFrame;


/**
 * Manages the preferences for the user interface.
 * 
 * @author Christian Fehler
 * @version $Id: Messages.java 28 2007-10-18 14:29:54Z fehler $
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
   * Allocates a new <code>PreferencesManager</code>.
   */
  private PreferenceManager ()
  {
    this.preferences = Preferences.userNodeForPackage ( this.getClass () );
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
}
