package de.unisiegen.gtitool.ui.preferences;


import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.event.EventListenerList;

import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.logic.MainWindow;
import de.unisiegen.gtitool.ui.logic.PreferencesDialog;
import de.unisiegen.gtitool.ui.preferences.item.AutoStepItem;
import de.unisiegen.gtitool.ui.preferences.item.LookAndFeelItem;
import de.unisiegen.gtitool.ui.preferences.item.MouseSelectionItem;
import de.unisiegen.gtitool.ui.preferences.item.OpenedFilesItem;
import de.unisiegen.gtitool.ui.preferences.item.RecentlyUsedFilesItem;
import de.unisiegen.gtitool.ui.preferences.item.TransitionItem;
import de.unisiegen.gtitool.ui.preferences.item.ZoomFactorItem;
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
  public static final String DEFAULT_LOOK_AND_FEEL_CLASS_NAME = "de.muntjak.tinylookandfeel.TinyLookAndFeel";//$NON-NLS-1$


  /**
   * The default look and feel name.
   */
  public static final String DEFAULT_LOOK_AND_FEEL_NAME = "TinyLaF"; //$NON-NLS-1$


  /**
   * The default maximized state of the {@link MainWindow}.
   */
  public static final boolean DEFAULT_MAXIMIZED = false;


  /**
   * The default width of the {@link MainWindow}.
   */
  public static final int DEFAULT_WIDTH = 960;


  /**
   * The default hight of the {@link MainWindow}.
   */
  public static final int DEFAULT_HEIGHT = 600;


  /**
   * The default x position of the {@link MainWindow}.
   */
  public static final int DEFAULT_POSITION_X = ( Toolkit.getDefaultToolkit ()
      .getScreenSize ().width - DEFAULT_WIDTH ) / 2;


  /**
   * The default y position of the {@link MainWindow}.
   */
  public static final int DEFAULT_POSITION_Y = ( Toolkit.getDefaultToolkit ()
      .getScreenSize ().height - DEFAULT_HEIGHT ) / 2;


  /**
   * The default console divider location.
   */
  public static final int DEFAULT_DIVIDER_LOCATION_CONSOLE = DEFAULT_HEIGHT / 2;


  /**
   * The default table divider location.
   */
  public static final int DEFAULT_DIVIDER_LOCATION_TABLE = ( DEFAULT_WIDTH / 4 ) * 3;


  /**
   * The default preference dialog last active tab.
   */
  public static final int DEFAULT_PREFERENCES_DIALOG_LAST_ACTIVE_TAB = 0;


  /**
   * The visible console value.
   */
  public static final boolean DEFAULT_VISIBLE_CONSOLE = true;


  /**
   * The visible table value.
   */
  public static final boolean DEFAULT_VISIBLE_TABLE = true;


  /**
   * The default working path.
   */
  public static final String DEFAULT_WORKING_PATH = "."; //$NON-NLS-1$


  /**
   * The default host.
   */
  public static final String DEFAULT_HOST = "localhost"; //$NON-NLS-1$


  /**
   * The default port.
   */
  public static final int DEFAULT_PORT = 64528;


  /**
   * The default received modus.
   */
  public static final boolean DEFAULT_RECEIVED_MODUS = true;


  /**
   * The default zoom factor value.
   */
  public static final ZoomFactorItem DEFAULT_ZOOM_FACTOR_ITEM = ZoomFactorItem.ZOOM_100;


  /**
   * The default {@link AutoStepItem}.
   */
  public static final AutoStepItem DEFAULT_AUTO_STEP_INTERVAL_ITEM = AutoStepItem.AUTO_STEP_2000;


  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger
      .getLogger ( PreferenceManager.class );


  /**
   * The single instance of the {@link PreferenceManager}.
   */
  private static PreferenceManager preferenceManager;


  /**
   * Returns the single instance of the {@link PreferenceManager}.
   * 
   * @return The single instance of the {@link PreferenceManager}.
   */
  public final static PreferenceManager getInstance ()
  {
    if ( preferenceManager == null )
    {
      preferenceManager = new PreferenceManager ();
    }
    return preferenceManager;
  }


  /**
   * The {@link EventListenerList}.
   */
  private EventListenerList listenerList = new EventListenerList ();


  /**
   * Allocates a new {@link PreferenceManager}.
   */
  protected PreferenceManager ()
  {
    super ();
  }


  /**
   * Adds the given {@link ZoomFactorChangedListener}.
   * 
   * @param listener The {@link ZoomFactorChangedListener}.
   */
  public final void addZoomFactorChangedListener (
      ZoomFactorChangedListener listener )
  {
    this.listenerList.add ( ZoomFactorChangedListener.class, listener );
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
    for ( ZoomFactorChangedListener current : listeners )
    {
      current.zoomFactorChanged ( zoomFactor );
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
   * Returns the host.
   * 
   * @return The host.
   */
  public final String getHost ()
  {
    return this.preferences.get ( "Host", DEFAULT_HOST ); //$NON-NLS-1$
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
    String end = "no item found"; //$NON-NLS-1$
    int count = 0;
    while ( true )
    {
      String file = this.preferences.get ( "MainWindow.OpenedFiles" + count, //$NON-NLS-1$
          end );
      if ( file.equals ( end ) )
      {
        break;
      }
      files.add ( new File ( file ) );
      count++ ;
    }
    String activeFileName = this.preferences.get (
        "MainWindow.OpenedActiveFile", "" ); //$NON-NLS-1$ //$NON-NLS-2$
    File activeFile = activeFileName.equals ( "" ) ? null //$NON-NLS-1$
        : new File ( activeFileName );
    return new OpenedFilesItem ( files, activeFile );
  }


  /**
   * Returns the port.
   * 
   * @return The port.
   */
  public final int getPort ()
  {
    int port = this.preferences.getInt ( "Port", DEFAULT_PORT ); //$NON-NLS-1$
    if ( ( port < 0 ) || ( port > 65535 ) )
    {
      port = DEFAULT_PORT;
    }
    return port;
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
   * Returns the receive flag.
   * 
   * @return True if the receive modus is active, otherwise false.
   */
  public final boolean getReceiveModus ()
  {
    return this.preferences.getBoolean ( "ReceivedModus", //$NON-NLS-1$
        DEFAULT_RECEIVED_MODUS );
  }


  /**
   * Returns the recently used files.
   * 
   * @return The recently used files.
   */
  public final RecentlyUsedFilesItem getRecentlyUsedFilesItem ()
  {
    ArrayList < File > files = new ArrayList < File > ();
    String end = "no item found"; //$NON-NLS-1$
    int count = 0;
    while ( true )
    {
      String file = this.preferences.get ( "MainWindow.RecentlyUsedFiles" //$NON-NLS-1$
          + count, end );
      if ( file.equals ( end ) )
      {
        break;
      }
      files.add ( new File ( file ) );
      count++ ;
    }
    return new RecentlyUsedFilesItem ( files );
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
   * Removes the given {@link ZoomFactorChangedListener}.
   * 
   * @param listener The {@link ZoomFactorChangedListener}.
   */
  public final void removeZoomFactorChangedListener (
      ZoomFactorChangedListener listener )
  {
    this.listenerList.remove ( ZoomFactorChangedListener.class, listener );
  }


  /**
   * Sets the {@link AutoStepItem}.
   * 
   * @param autoStepInterval The {@link AutoStepItem}.
   */
  public final void setAutoStepItem ( AutoStepItem autoStepInterval )
  {
    logger.debug ( "setAutoStepItem", "set auto step interval to "//$NON-NLS-1$ //$NON-NLS-2$
        + Messages.QUOTE + autoStepInterval.getAutoStepInterval () + ""//$NON-NLS-1$
        + Messages.QUOTE );
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
    logger.debug ( "setDividerLocationConsole",//$NON-NLS-1$
        "set console divider location to " + Messages.QUOTE + location + ""//$NON-NLS-1$//$NON-NLS-2$
            + Messages.QUOTE );
    this.preferences.putInt ( "MachinePanel.DividerConsole", location ); //$NON-NLS-1$
  }


  /**
   * Sets the table divider location.
   * 
   * @param location The table divider location.
   */
  public final void setDividerLocationTable ( int location )
  {
    logger.debug ( "setDividerLocationTable", "set table divider location to "//$NON-NLS-1$//$NON-NLS-2$
        + Messages.QUOTE + location + "" + Messages.QUOTE );//$NON-NLS-1$
    this.preferences.putInt ( "MachinePanel.DividerTable", location ); //$NON-NLS-1$
  }


  /**
   * Sets the host.
   * 
   * @param host The host.
   */
  public final void setHost ( String host )
  {
    logger.debug ( "setHost", "set the host to " + Messages.QUOTE + host + ""//$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
        + Messages.QUOTE );
    this.preferences.put ( "Host", host ); //$NON-NLS-1$
  }


  /**
   * Sets the {@link LookAndFeelItem}.
   * 
   * @param lookAndFeelItem The {@link LookAndFeelItem}.
   */
  public final void setLookAndFeelItem ( LookAndFeelItem lookAndFeelItem )
  {
    logger.debug ( "setLookAndFeelItem", "set look and feel to "//$NON-NLS-1$//$NON-NLS-2$
        + Messages.QUOTE + lookAndFeelItem.getName () + "" + Messages.QUOTE );//$NON-NLS-1$
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
      logger.debug ( "setMainWindowPreferences",//$NON-NLS-1$
          "set main window maximized to " + Messages.QUOTE + "false"//$NON-NLS-1$//$NON-NLS-2$
              + Messages.QUOTE );
      this.preferences.putBoolean ( "MainWindow.Maximized", false ); //$NON-NLS-1$
      Rectangle bounds = jFrame.getBounds ();
      logger.debug ( "", "set main window bounds to " + Messages.QUOTE + "x=" //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
          + bounds.x + ", " + "y=" + bounds.y + ", " + "width=" + bounds.width //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
          + ", " + "height=" + bounds.height + "" + Messages.QUOTE ); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
      this.preferences.putInt ( "MainWindow.XPosition", bounds.x ); //$NON-NLS-1$
      this.preferences.putInt ( "MainWindow.YPosition", bounds.y ); //$NON-NLS-1$
      this.preferences.putInt ( "MainWindow.Width", bounds.width ); //$NON-NLS-1$
      this.preferences.putInt ( "MainWindow.Height", bounds.height ); //$NON-NLS-1$
    }
    else
    {
      logger.debug ( "setMainWindowPreferences",//$NON-NLS-1$
          "set main window maximized to " + Messages.QUOTE + "true"//$NON-NLS-1$//$NON-NLS-2$
              + Messages.QUOTE );
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
    logger.debug ( "setMouseSelectionItem", "set mouse selection item to "//$NON-NLS-1$//$NON-NLS-2$
        + Messages.QUOTE + mouseSelectionItem.getIndex () + ""//$NON-NLS-1$
        + Messages.QUOTE );
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
    // Delete old data
    String end = "no item found"; //$NON-NLS-1$
    int count = 0;
    loop : while ( true )
    {
      String symbol = this.preferences.get ( "MainWindow.OpenedFiles" + count, //$NON-NLS-1$
          end );
      if ( symbol.equals ( end ) )
      {
        break loop;
      }
      count++ ;
    }
    for ( int i = 0 ; i < count ; i++ )
    {
      this.preferences.remove ( "MainWindow.OpenedFiles" + i ); //$NON-NLS-1$
    }

    // Set the new data
    for ( int i = 0 ; i < openedFilesItem.getFiles ().size () ; i++ )
    {
      logger.debug ( "setOpenedFilesItem", "set opened file " + Messages.QUOTE//$NON-NLS-1$//$NON-NLS-2$
          + i + Messages.QUOTE + " to " + Messages.QUOTE//$NON-NLS-1$
          + openedFilesItem.getFiles ().get ( i ).getAbsolutePath () + ""//$NON-NLS-1$
          + Messages.QUOTE );
      this.preferences.put ( "MainWindow.OpenedFiles" + i, openedFilesItem //$NON-NLS-1$
          .getFiles ().get ( i ).getAbsolutePath () );
    }

    if ( openedFilesItem.getActiveFile () == null )
    {
      logger.debug ( "setOpenedFilesItem", "set last opened file to "//$NON-NLS-1$//$NON-NLS-2$
          + Messages.QUOTE + "null" + Messages.QUOTE );//$NON-NLS-1$
      this.preferences.put ( "MainWindow.OpenedActiveFile", "" );//$NON-NLS-1$ //$NON-NLS-2$
    }
    else
    {
      logger.debug ( "setOpenedFilesItem", "set last opened file to "//$NON-NLS-1$//$NON-NLS-2$
          + Messages.QUOTE
          + openedFilesItem.getActiveFile ().getAbsolutePath () + ""//$NON-NLS-1$
          + Messages.QUOTE );
      this.preferences.put ( "MainWindow.OpenedActiveFile", //$NON-NLS-1$
          openedFilesItem.getActiveFile ().getAbsolutePath () );
    }
  }


  /**
   * Sets the port.
   * 
   * @param port The port.
   */
  public final void setPort ( int port )
  {
    logger.debug ( "setPort", "set port to " + Messages.QUOTE + port + ""//$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
        + Messages.QUOTE );
    this.preferences.putInt ( "Port", port ); //$NON-NLS-1$
  }


  /**
   * Sets the last active tab of the {@link PreferencesDialog}.
   * 
   * @param index The index of the last active {@link PreferencesDialog}.
   */
  public final void setPreferencesDialogLastActiveTab ( int index )
  {
    logger.debug ( "setPreferencesDialogLastActiveTab",//$NON-NLS-1$
        "set last active tab to " + Messages.QUOTE + index + ""//$NON-NLS-1$//$NON-NLS-2$
            + Messages.QUOTE );
    this.preferences.putInt ( "PreferencesDialog.LastActiveTab", index ); //$NON-NLS-1$
  }


  /**
   * Sets the receive flag.
   * 
   * @param enabled True if the receive modus is active, otherwise false.
   */
  public final void setReceiveModus ( boolean enabled )
  {
    logger.debug ( "setReceiveModus", "set the receive modus to "//$NON-NLS-1$//$NON-NLS-2$
        + Messages.QUOTE + enabled + "" + Messages.QUOTE );//$NON-NLS-1$
    this.preferences.putBoolean ( "ReceivedModus", enabled ); //$NON-NLS-1$
  }


  /**
   * Sets the recently used files.
   * 
   * @param recentlyUsedFilesItem The {@link RecentlyUsedFilesItem}.
   */
  public final void setRecentlyUsedFilesItem (
      RecentlyUsedFilesItem recentlyUsedFilesItem )
  {
    // Delete old data
    String end = "no item found"; //$NON-NLS-1$
    int count = 0;
    loop : while ( true )
    {
      String symbol = this.preferences.get (
          "MainWindow.RecentlyUsedFiles" + count, //$NON-NLS-1$
          end );
      if ( symbol.equals ( end ) )
      {
        break loop;
      }
      count++ ;
    }
    for ( int i = 0 ; i < count ; i++ )
    {
      this.preferences.remove ( "MainWindow.RecentlyUsedFiles" + i ); //$NON-NLS-1$
    }

    // Set the new data
    for ( int i = 0 ; i < recentlyUsedFilesItem.getFiles ().size () ; i++ )
    {
      logger.debug ( "setRecentlyUsedFilesItem", "set recently used file "//$NON-NLS-1$//$NON-NLS-2$
          + Messages.QUOTE + i + Messages.QUOTE + " to " + Messages.QUOTE//$NON-NLS-1$
          + recentlyUsedFilesItem.getFiles ().get ( i ).getAbsolutePath () + ""//$NON-NLS-1$
          + Messages.QUOTE );
      this.preferences.put (
          "MainWindow.RecentlyUsedFiles" + i, recentlyUsedFilesItem //$NON-NLS-1$
              .getFiles ().get ( i ).getAbsolutePath () );
    }
  }


  /**
   * Sets the {@link TransitionItem}.
   * 
   * @param transitionItem The {@link TransitionItem}.
   */
  public final void setTransitionItem ( TransitionItem transitionItem )
  {
    logger.debug ( "setTransitionItem", "set transition item to "//$NON-NLS-1$//$NON-NLS-2$
        + Messages.QUOTE + transitionItem.getIndex () + "" + Messages.QUOTE );//$NON-NLS-1$
    this.preferences.putInt ( "PreferencesDialog.TransitionItem.Index", //$NON-NLS-1$
        transitionItem.getIndex () );
  }


  /**
   * Sets the visible console value.
   * 
   * @param visible The visible console value.
   */
  public final void setVisibleConsole ( boolean visible )
  {
    logger.debug ( "setVisibleConsole", "set the visible console to "//$NON-NLS-1$//$NON-NLS-2$
        + Messages.QUOTE + visible + "" + Messages.QUOTE );//$NON-NLS-1$
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
    logger.debug ( "setVisibleTable", "set the visible table to "//$NON-NLS-1$//$NON-NLS-2$
        + Messages.QUOTE + visible + "" + Messages.QUOTE );//$NON-NLS-1$
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
    logger.debug ( "setWorkingPath", "set the working path to "//$NON-NLS-1$//$NON-NLS-2$
        + Messages.QUOTE + path + "" + Messages.QUOTE );//$NON-NLS-1$
    this.preferences.put ( "WorkingPath", path ); //$NON-NLS-1$
  }


  /**
   * Sets the {@link ZoomFactorItem}.
   * 
   * @param zoomFactor The {@link ZoomFactorItem}.
   */
  public final void setZoomFactorItem ( ZoomFactorItem zoomFactor )
  {
    logger.debug ( "setZoomFactorItem", "set zoom factor to " + Messages.QUOTE//$NON-NLS-1$//$NON-NLS-2$
        + zoomFactor.getFactor () + "" + Messages.QUOTE );//$NON-NLS-1$
    this.preferences.putInt ( "ZoomFactor", zoomFactor.getFactor () ); //$NON-NLS-1$
  }
}
