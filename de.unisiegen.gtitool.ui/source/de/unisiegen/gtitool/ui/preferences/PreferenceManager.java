package de.unisiegen.gtitool.ui.preferences;


import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.event.EventListenerList;

import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.entities.InputEntity.EntityType;
import de.unisiegen.gtitool.core.grammars.Grammar.GrammarType;
import de.unisiegen.gtitool.core.machines.Machine.MachineType;
import de.unisiegen.gtitool.core.machines.pda.PDA;
import de.unisiegen.gtitool.core.util.ObjectPair;
import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.logic.ConvertMachineDialog;
import de.unisiegen.gtitool.ui.logic.MainWindow;
import de.unisiegen.gtitool.ui.logic.MinimizeMachineDialog;
import de.unisiegen.gtitool.ui.logic.PreferencesDialog;
import de.unisiegen.gtitool.ui.logic.ReachableStatesDialog;
import de.unisiegen.gtitool.ui.netbeans.ConvertMachineDialogForm;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;
import de.unisiegen.gtitool.ui.netbeans.MinimizeMachineDialogForm;
import de.unisiegen.gtitool.ui.netbeans.ReachableStatesDialogForm;
import de.unisiegen.gtitool.ui.preferences.item.AutoStepItem;
import de.unisiegen.gtitool.ui.preferences.item.LookAndFeelItem;
import de.unisiegen.gtitool.ui.preferences.item.MouseSelectionItem;
import de.unisiegen.gtitool.ui.preferences.item.OpenedFilesItem;
import de.unisiegen.gtitool.ui.preferences.item.PDAModeItem;
import de.unisiegen.gtitool.ui.preferences.item.RecentlyUsedFilesItem;
import de.unisiegen.gtitool.ui.preferences.item.TransitionItem;
import de.unisiegen.gtitool.ui.preferences.item.WordModeItem;
import de.unisiegen.gtitool.ui.preferences.item.ZoomFactorItem;
import de.unisiegen.gtitool.ui.preferences.listener.PDAModeChangedListener;
import de.unisiegen.gtitool.ui.preferences.listener.WordModeChangedListener;
import de.unisiegen.gtitool.ui.preferences.listener.ZoomFactorChangedListener;
import de.unisiegen.gtitool.ui.swing.specialized.JGTIMainSplitPane.ActiveEditor;


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
   * The default {@link PDAModeItem}.
   */
  public static final PDAModeItem DEFAULT_PDA_MODE_ITEM = PDAModeItem.SHOW;


  /**
   * The default {@link WordModeItem}.
   */
  public static final WordModeItem DEFAULT_WORD_MODE_ITEM = WordModeItem.LEFT;


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
   * The default width of the {@link ConvertMachineDialog}.
   */
  public static final int DEFAULT_CONVERT_MACHINE_DIALOG_WIDTH = 960;


  /**
   * The default hight of the {@link ConvertMachineDialog}.
   */
  public static final int DEFAULT_CONVERT_MACHINE_DIALOG_HEIGHT = 600;


  /**
   * The default x position of the {@link ConvertMachineDialog}.
   */
  public static final int DEFAULT_CONVERT_MACHINE_DIALOG_POSITION_X = Integer.MAX_VALUE;


  /**
   * The default y position of the {@link ConvertMachineDialog}.
   */
  public static final int DEFAULT_CONVERT_MACHINE_DIALOG_POSITION_Y = Integer.MAX_VALUE;


  /**
   * The default width of the {@link MinimizeMachineDialog}.
   */
  public static final int DEFAULT_MINIMIZE_MACHINE_DIALOG_WIDTH = 960;


  /**
   * The default hight of the {@link MinimizeMachineDialog}.
   */
  public static final int DEFAULT_MINIMIZE_MACHINE_DIALOG_HEIGHT = 600;


  /**
   * The default x position of the {@link MinimizeMachineDialog}.
   */
  public static final int DEFAULT_MINIMIZE_MACHINE_DIALOG_POSITION_X = Integer.MAX_VALUE;


  /**
   * The default y position of the {@link MinimizeMachineDialog}.
   */
  public static final int DEFAULT_MINIMIZE_MACHINE_DIALOG_POSITION_Y = Integer.MAX_VALUE;


  /**
   * The default x position of the {@link ReachableStatesDialog}.
   */
  public static final int DEFAULT_REACHABLE_STATES_DIALOG_POSITION_X = Integer.MAX_VALUE;


  /**
   * The default y position of the {@link ReachableStatesDialog}.
   */
  public static final int DEFAULT_REACHABLE_STATES_DIALOG_POSITION_Y = Integer.MAX_VALUE;


  /**
   * The default width of the {@link ReachableStatesDialog}.
   */
  public static final int DEFAULT_REACHABLE_STATES_DIALOG_WIDTH = 960;


  /**
   * The default hight of the {@link ReachableStatesDialog}.
   */
  public static final int DEFAULT_REACHABLE_STATES_DIALOG_HEIGHT = 600;


  /**
   * The default console divider location.
   */
  public static final int DEFAULT_DIVIDER_LOCATION_CONSOLE = DEFAULT_HEIGHT / 2;


  /**
   * The default table divider location.
   */
  public static final int DEFAULT_DIVIDER_LOCATION_TABLE = ( DEFAULT_WIDTH / 4 ) * 3;


  /**
   * The default second view divider location.
   */
  public static final int DEFAULT_DIVIDER_LOCATION_SECOND_VIEW = DEFAULT_WIDTH / 2;


  /**
   * The default convert machine divider location.
   */
  public static final int DEFAULT_DIVIDER_LOCATION_CONVERT_MACHINE = 250;


  /**
   * The default convert machine outline divider location.
   */
  public static final int DEFAULT_DIVIDER_LOCATION_CONVERT_MACHINE_OUTLINE = 700;


  /**
   * The default reachable states divider location.
   */
  public static final int DEFAULT_DIVIDER_LOCATION_REACHABLE_STATES = 250;


  /**
   * The default reachable states outline divider location.
   */
  public static final int DEFAULT_DIVIDER_LOCATION_REACHABLE_STATES_OUTLINE = 700;


  /**
   * The default minimize machine divider location.
   */
  public static final int DEFAULT_DIVIDER_LOCATION_MINIMIZE_MACHINE = 250;


  /**
   * The default minimize machine outline divider location.
   */
  public static final int DEFAULT_DIVIDER_LOCATION_MINIMIZE_MACHINE_OUTLINE = 700;


  /**
   * The default pda table divider location.
   */
  public static final int DEFAULT_DIVIDER_LOCATION_PDA_TABLE = DEFAULT_HEIGHT / 4;


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
   * The second view used value.
   */
  public static final boolean DEFAULT_SECOND_VIEW_USED = false;


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
   * The default {@link EntityType}.
   */
  public static final EntityType DEFAULT_ENTITY_TYPE = MachineType.DFA;


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
   * Adds the given {@link PDAModeChangedListener}.
   * 
   * @param listener The {@link PDAModeChangedListener}.
   */
  public final void addPDAModeChangedListener ( PDAModeChangedListener listener )
  {
    this.listenerList.add ( PDAModeChangedListener.class, listener );
  }


  /**
   * Adds the given {@link WordModeChangedListener}.
   * 
   * @param listener The {@link WordModeChangedListener}.
   */
  public final void addWordModeChangedListener (
      WordModeChangedListener listener )
  {
    this.listenerList.add ( WordModeChangedListener.class, listener );
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
   * Let the listeners know that the {@link PDA} mode has changed.
   * 
   * @param pdaModeItem The new {@link PDAModeItem}.
   */
  public final void firePDAModeChanged ( PDAModeItem pdaModeItem )
  {
    PDAModeChangedListener [] listeners = this.listenerList
        .getListeners ( PDAModeChangedListener.class );
    for ( PDAModeChangedListener current : listeners )
    {
      current.pdaModeChanged ( pdaModeItem );
    }
  }


  /**
   * Let the listeners know that the {@link Word} mode has changed.
   * 
   * @param wordModeItem The new {@link WordModeItem}.
   */
  public final void fireWordModeChanged ( WordModeItem wordModeItem )
  {
    WordModeChangedListener [] listeners = this.listenerList
        .getListeners ( WordModeChangedListener.class );
    for ( WordModeChangedListener current : listeners )
    {
      current.wordModeChanged ( wordModeItem );
    }
  }


  /**
   * Let the listeners know that the zoom factor has changed.
   * 
   * @param zoomFactorItem The new {@link ZoomFactorItem}.
   */
  public final void fireZoomFactorChanged ( ZoomFactorItem zoomFactorItem )
  {
    ZoomFactorChangedListener [] listeners = this.listenerList
        .getListeners ( ZoomFactorChangedListener.class );
    for ( ZoomFactorChangedListener current : listeners )
    {
      current.zoomFactorChanged ( zoomFactorItem );
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
   * Returns the {@link ConvertMachineDialog} bounds.
   * 
   * @return The {@link ConvertMachineDialog} bounds.
   */
  public final Rectangle getConvertMachineDialogBounds ()
  {
    int x = this.preferences.getInt ( "ConvertMachineDialog.XPosition", //$NON-NLS-1$
        DEFAULT_CONVERT_MACHINE_DIALOG_POSITION_X );
    int y = this.preferences.getInt ( "ConvertMachineDialog.YPosition", //$NON-NLS-1$
        DEFAULT_CONVERT_MACHINE_DIALOG_POSITION_Y );
    int width = this.preferences.getInt ( "ConvertMachineDialog.Width",//$NON-NLS-1$
        DEFAULT_CONVERT_MACHINE_DIALOG_WIDTH );
    int height = this.preferences.getInt ( "ConvertMachineDialog.Height",//$NON-NLS-1$
        DEFAULT_CONVERT_MACHINE_DIALOG_HEIGHT );
    return new Rectangle ( x, y, width, height );
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
   * Returns the convert machine divider location.
   * 
   * @return The convert machine divider location.
   */
  public final int getDividerLocationConvertMachine ()
  {
    return this.preferences.getInt ( "ConvertMachineDialog.Divider", //$NON-NLS-1$
        DEFAULT_DIVIDER_LOCATION_CONVERT_MACHINE );
  }


  /**
   * Returns the convert machine outline divider location.
   * 
   * @return The convert machine outline divider location.
   */
  public final int getDividerLocationConvertMachineOutline ()
  {
    return this.preferences.getInt ( "ConvertMachineDialog.DividerOutline", //$NON-NLS-1$
        DEFAULT_DIVIDER_LOCATION_CONVERT_MACHINE_OUTLINE );
  }


  /**
   * Returns the minimize machine divider location.
   * 
   * @return The minimize machine divider location.
   */
  public final int getDividerLocationMinimizeMachine ()
  {
    return this.preferences.getInt ( "MinimizeMachineDialog.Divider", //$NON-NLS-1$
        DEFAULT_DIVIDER_LOCATION_MINIMIZE_MACHINE );
  }


  /**
   * Returns the minimize machine outline divider location.
   * 
   * @return The minimize machine outline divider location.
   */
  public final int getDividerLocationMinimizeMachineOutline ()
  {
    return this.preferences.getInt ( "MinimizeMachineDialog.DividerOutline", //$NON-NLS-1$
        DEFAULT_DIVIDER_LOCATION_MINIMIZE_MACHINE_OUTLINE );
  }


  /**
   * Returns the pda table divider location.
   * 
   * @return The pda table divider location.
   */
  public final int getDividerLocationPDATable ()
  {
    return this.preferences.getInt ( "MachinePanel.DividerPDATable", //$NON-NLS-1$
        DEFAULT_DIVIDER_LOCATION_PDA_TABLE );
  }


  /**
   * Returns the reachable states outline divider location.
   * 
   * @return The reachable states outline divider location.
   */
  public final int getDividerLocationReachableStatesOutline ()
  {
    return this.preferences.getInt ( "ReachableStatesDialog.DividerOutline", //$NON-NLS-1$
        DEFAULT_DIVIDER_LOCATION_REACHABLE_STATES_OUTLINE );
  }


  /**
   * Returns the second view divider location.
   * 
   * @return The second view divider location.
   */
  public final int getDividerLocationSecondView ()
  {
    return this.preferences.getInt ( "MachinePanel.DividerSecondView", //$NON-NLS-1$
        DEFAULT_DIVIDER_LOCATION_SECOND_VIEW );
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
   * Returns the last choosen {@link EntityType}.
   * 
   * @return The last choosen {@link EntityType}.
   */
  public final EntityType getLastChoosenEntityType ()
  {
    String entityType = this.preferences.get ( "LastChoosenEntityType", //$NON-NLS-1$
        DEFAULT_ENTITY_TYPE.toString () );

    if ( entityType.equals ( MachineType.DFA.toString () ) )
    {
      return MachineType.DFA;
    }
    else if ( entityType.equals ( MachineType.NFA.toString () ) )
    {
      return MachineType.NFA;
    }
    else if ( entityType.equals ( MachineType.ENFA.toString () ) )
    {
      return MachineType.ENFA;
    }
    else if ( entityType.equals ( MachineType.PDA.toString () ) )
    {
      return MachineType.PDA;
    }
    else if ( entityType.equals ( GrammarType.RG.toString () ) )
    {
      return GrammarType.RG;
    }
    else if ( entityType.equals ( GrammarType.CFG.toString () ) )
    {
      return GrammarType.CFG;
    }

    return DEFAULT_ENTITY_TYPE;
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
   * Returns the {@link MinimizeMachineDialog} bounds.
   * 
   * @return The {@link MinimizeMachineDialog} bounds.
   */
  public final Rectangle getMinimizeMachineDialogBounds ()
  {
    int x = this.preferences.getInt ( "MinimizeMachineDialog.XPosition", //$NON-NLS-1$
        DEFAULT_MINIMIZE_MACHINE_DIALOG_POSITION_X );
    int y = this.preferences.getInt ( "MinimizeMachineDialog.YPosition", //$NON-NLS-1$
        DEFAULT_MINIMIZE_MACHINE_DIALOG_POSITION_Y );
    int width = this.preferences.getInt ( "MinimizeMachineDialog.Width",//$NON-NLS-1$
        DEFAULT_MINIMIZE_MACHINE_DIALOG_WIDTH );
    int height = this.preferences.getInt ( "MinimizeMachineDialog.Height",//$NON-NLS-1$
        DEFAULT_MINIMIZE_MACHINE_DIALOG_HEIGHT );
    return new Rectangle ( x, y, width, height );
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
    ArrayList < ObjectPair < File, ActiveEditor > > files = new ArrayList < ObjectPair < File, ActiveEditor > > ();

    String end = "no item found"; //$NON-NLS-1$
    int count = 0;
    while ( true )
    {
      String file = this.preferences.get ( "MainWindow.OpenedFiles" + count, //$NON-NLS-1$
          end );
      String editor = this.preferences.get (
          "MainWindow.OpenedFilesEditor" + count, //$NON-NLS-1$
          end );

      if ( file.equals ( end ) || editor.equals ( end ) )
      {
        break;
      }

      if ( editor.equals ( "right" ) ) //$NON-NLS-1$
      {
        files.add ( new ObjectPair < File, ActiveEditor > ( new File ( file ),
            ActiveEditor.RIGHT_EDITOR ) );
      }
      else
      {
        files.add ( new ObjectPair < File, ActiveEditor > ( new File ( file ),
            ActiveEditor.LEFT_EDITOR ) );
      }

      count++ ;
    }

    String activeFileName = this.preferences.get (
        "MainWindow.OpenedActiveFile", "" ); //$NON-NLS-1$ //$NON-NLS-2$
    File activeFile = activeFileName.equals ( "" ) ? null //$NON-NLS-1$
        : new File ( activeFileName );

    return new OpenedFilesItem ( files, activeFile );
  }


  /**
   * Returns the {@link PDAModeItem}.
   * 
   * @return The {@link PDAModeItem}.
   */
  public final PDAModeItem getPDAModeItem ()
  {
    int index = this.preferences.getInt (
        "PreferencesDialog.PDAModeItem.Index", //$NON-NLS-1$
        DEFAULT_PDA_MODE_ITEM.getIndex () );
    return PDAModeItem.create ( index );
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
   * Returns the {@link ReachableStatesDialog} bounds.
   * 
   * @return The {@link ReachableStatesDialog} bounds.
   */
  public final Rectangle getReachableStatesDialogBounds ()
  {
    int x = this.preferences.getInt ( "ReachableStatesDialog.XPosition", //$NON-NLS-1$
        DEFAULT_REACHABLE_STATES_DIALOG_POSITION_X );
    int y = this.preferences.getInt ( "ReachableStatesDialog.YPosition", //$NON-NLS-1$
        DEFAULT_REACHABLE_STATES_DIALOG_POSITION_Y );
    int width = this.preferences.getInt ( "ReachableStatesDialog.Width",//$NON-NLS-1$
        DEFAULT_REACHABLE_STATES_DIALOG_WIDTH );
    int height = this.preferences.getInt ( "ReachableStatesDialog.Height",//$NON-NLS-1$
        DEFAULT_REACHABLE_STATES_DIALOG_HEIGHT );
    return new Rectangle ( x, y, width, height );
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
   * Returns the second view used value.
   * 
   * @return The second view used value.
   */
  public final boolean getSeconsViewUsed ()
  {
    return this.preferences.getBoolean ( "MachinePanel.SecondViewUsed", //$NON-NLS-1$
        DEFAULT_SECOND_VIEW_USED );
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
   * Returns the {@link WordModeItem}.
   * 
   * @return The {@link WordModeItem}.
   */
  public final WordModeItem getWordModeItem ()
  {
    int index = this.preferences.getInt (
        "PreferencesDialog.WordModeItem.Index", //$NON-NLS-1$
        DEFAULT_WORD_MODE_ITEM.getIndex () );
    return WordModeItem.create ( index );
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
   * Removes the given {@link PDAModeChangedListener}.
   * 
   * @param listener The {@link PDAModeChangedListener}.
   */
  public final void removePDAModeChangedListener (
      PDAModeChangedListener listener )
  {
    this.listenerList.remove ( PDAModeChangedListener.class, listener );
  }


  /**
   * Removes the given {@link WordModeChangedListener}.
   * 
   * @param listener The {@link WordModeChangedListener}.
   */
  public final void removeWordModeChangedListener (
      WordModeChangedListener listener )
  {
    this.listenerList.remove ( WordModeChangedListener.class, listener );
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
        + Messages.QUOTE
        + autoStepInterval.getAutoStepInterval ()
        + Messages.QUOTE );
    this.preferences.putInt ( "AutoStep", autoStepInterval //$NON-NLS-1$
        .getAutoStepInterval () );
  }


  /**
   * Sets the {@link ConvertMachineDialog} preferences.
   * 
   * @param dialog The {@link JDialog} of the {@link ConvertMachineDialog}.
   */
  public final void setConvertMachineDialogPreferences (
      ConvertMachineDialogForm dialog )
  {
    Rectangle bounds = dialog.getBounds ();
    logger.debug ( "setConvertMachineDialogPreferences",//$NON-NLS-1$
        "set convert machine dialog bounds to " + Messages.QUOTE + "x="//$NON-NLS-1$ //$NON-NLS-2$
            + bounds.x + ", y=" + bounds.y + ", width="//$NON-NLS-1$ //$NON-NLS-2$ 
            + bounds.width + ", height=" + bounds.height + Messages.QUOTE ); //$NON-NLS-1$ 
    this.preferences.putInt ( "ConvertMachineDialog.XPosition", bounds.x ); //$NON-NLS-1$
    this.preferences.putInt ( "ConvertMachineDialog.YPosition", bounds.y ); //$NON-NLS-1$
    this.preferences.putInt ( "ConvertMachineDialog.Width", bounds.width ); //$NON-NLS-1$
    this.preferences.putInt ( "ConvertMachineDialog.Height", bounds.height ); //$NON-NLS-1$
  }


  /**
   * Sets the console divider location.
   * 
   * @param location The console divider location.
   */
  public final void setDividerLocationConsole ( int location )
  {
    logger.debug ( "setDividerLocationConsole",//$NON-NLS-1$
        "set console divider location to " + Messages.QUOTE + location //$NON-NLS-1$
            + Messages.QUOTE );
    this.preferences.putInt ( "MachinePanel.DividerConsole", location ); //$NON-NLS-1$
  }


  /**
   * Sets the convert machine divider location.
   * 
   * @param location The convert machine divider location.
   */
  public final void setDividerLocationConvertMachine ( int location )
  {
    logger.debug ( "setDividerLocationConvertMachine", //$NON-NLS-1$
        "set convert machine divider location to " + Messages.QUOTE//$NON-NLS-1$ 
            + location + Messages.QUOTE );
    this.preferences.putInt ( "ConvertMachineDialog.Divider", location );//$NON-NLS-1$
  }


  /**
   * Sets the convert machine outline divider location.
   * 
   * @param location The convert machine outline divider location.
   */
  public final void setDividerLocationConvertMachineOutline ( int location )
  {
    logger.debug ( "setDividerLocationConvertMachineOutline", //$NON-NLS-1$
        "set convert machine outline divider location to " //$NON-NLS-1$
            + Messages.QUOTE + location + Messages.QUOTE );
    this.preferences.putInt ( "ConvertMachineDialog.DividerOutline", location );//$NON-NLS-1$
  }


  /**
   * Sets the minimize machine divider location.
   * 
   * @param location The minimize machine divider location.
   */
  public final void setDividerLocationMinimizeMachine ( int location )
  {
    logger.debug ( "setDividerLocationMinimizeMachine", //$NON-NLS-1$
        "set minimize machine divider location to " + Messages.QUOTE//$NON-NLS-1$ 
            + location + Messages.QUOTE );
    this.preferences.putInt ( "MinimizeMachineDialog.Divider", location );//$NON-NLS-1$
  }


  /**
   * Sets the minimize machine outline divider location.
   * 
   * @param location The minimize machine outline divider location.
   */
  public final void setDividerLocationMinimizeMachineOutline ( int location )
  {
    logger.debug ( "setDividerLocationMinimizeMachineOutline", //$NON-NLS-1$
        "set minimize machine outline divider location to " //$NON-NLS-1$ 
            + Messages.QUOTE + location + Messages.QUOTE );
    this.preferences.putInt ( "MinimizeMachineDialog.DividerOutline", location );//$NON-NLS-1$
  }


  /**
   * Sets the pda table divider location.
   * 
   * @param location The pda table divider location.
   */
  public final void setDividerLocationPDATable ( int location )
  {
    logger.debug (
        "setDividerLocationPDATable", "set pda table divider location to "//$NON-NLS-1$//$NON-NLS-2$
            + Messages.QUOTE + location + Messages.QUOTE );
    this.preferences.putInt ( "MachinePanel.DividerPDATable", location ); //$NON-NLS-1$
  }


  /**
   * Sets the reachable states outline divider location.
   * 
   * @param location The reachable states outline divider location.
   */
  public final void setDividerLocationReachableStatesOutline ( int location )
  {
    logger.debug ( "setDividerLocationReachableStatesOutline", //$NON-NLS-1$
        "set reachable states outline divider location to " //$NON-NLS-1$
            + Messages.QUOTE + location + Messages.QUOTE );
    this.preferences.putInt ( "ReachableStatesDialog.DividerOutline", location );//$NON-NLS-1$
  }


  /**
   * Sets the second view divider location.
   * 
   * @param location The second view divider location.
   */
  public final void setDividerLocationSecondView ( int location )
  {
    logger.debug ( "setDividerLocationSecondView", //$NON-NLS-1$
        "set second view divider location to " + Messages.QUOTE + location //$NON-NLS-1$
            + Messages.QUOTE );
    this.preferences.putInt ( "MachinePanel.DividerSecondView", location ); //$NON-NLS-1$
  }


  /**
   * Sets the table divider location.
   * 
   * @param location The table divider location.
   */
  public final void setDividerLocationTable ( int location )
  {
    logger.debug ( "setDividerLocationTable", "set table divider location to "//$NON-NLS-1$//$NON-NLS-2$
        + Messages.QUOTE + location + Messages.QUOTE );
    this.preferences.putInt ( "MachinePanel.DividerTable", location ); //$NON-NLS-1$
  }


  /**
   * Sets the host.
   * 
   * @param host The host.
   */
  public final void setHost ( String host )
  {
    logger.debug ( "setHost", "set the host to " + Messages.QUOTE + host //$NON-NLS-1$//$NON-NLS-2$
        + Messages.QUOTE );
    this.preferences.put ( "Host", host ); //$NON-NLS-1$
  }


  /**
   * Sets the last choosen {@link EntityType}.
   * 
   * @param entityType The last choosen {@link EntityType}.
   */
  public final void setLastChoosenEntityType ( EntityType entityType )
  {
    logger.debug ( "setLastChoosenEntityType",//$NON-NLS-1$
        "set last choosen entity type to " + Messages.QUOTE + entityType//$NON-NLS-1$
            + Messages.QUOTE );
    this.preferences.put ( "LastChoosenEntityType", entityType.toString () ); //$NON-NLS-1$
  }


  /**
   * Sets the {@link LookAndFeelItem}.
   * 
   * @param lookAndFeelItem The {@link LookAndFeelItem}.
   */
  public final void setLookAndFeelItem ( LookAndFeelItem lookAndFeelItem )
  {
    logger.debug ( "setLookAndFeelItem", "set look and feel to "//$NON-NLS-1$//$NON-NLS-2$
        + Messages.QUOTE + lookAndFeelItem.getName () + Messages.QUOTE );
    this.preferences.put (
        "PreferencesDialog.LookAndFeel.Name", lookAndFeelItem.getName () ); //$NON-NLS-1$
    this.preferences.put ( "PreferencesDialog.LookAndFeel.ClassName", //$NON-NLS-1$
        lookAndFeelItem.getClassName () );
  }


  /**
   * Sets the {@link MainWindow} preferences.
   * 
   * @param frame The {@link JFrame} of the {@link MainWindow}.
   */
  public final void setMainWindowPreferences ( MainWindowForm frame )
  {
    if ( ( frame.getExtendedState () & Frame.MAXIMIZED_BOTH ) == 0 )
    {
      logger.debug ( "setMainWindowPreferences",//$NON-NLS-1$
          "set main window maximized to " + Messages.QUOTE + "false"//$NON-NLS-1$//$NON-NLS-2$
              + Messages.QUOTE );
      this.preferences.putBoolean ( "MainWindow.Maximized", false ); //$NON-NLS-1$
      Rectangle bounds = frame.getBounds ();
      logger.debug ( "setMainWindowPreferences", "set main window bounds to "//$NON-NLS-1$ //$NON-NLS-2$
          + Messages.QUOTE + "x=" + bounds.x + ", " + "y=" + bounds.y + ", "//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
          + "width=" + bounds.width + ", " + "height=" + bounds.height//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
          + Messages.QUOTE );
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
   * Sets the {@link MinimizeMachineDialog} preferences.
   * 
   * @param dialog The {@link JDialog} of the {@link MinimizeMachineDialog}.
   */
  public final void setMinimizeMachineDialogPreferences (
      MinimizeMachineDialogForm dialog )
  {
    Rectangle bounds = dialog.getBounds ();
    logger.debug ( "setMinimizeMachineDialogPreferences",//$NON-NLS-1$
        "set minimize machine dialog bounds to " + Messages.QUOTE + "x="//$NON-NLS-1$ //$NON-NLS-2$
            + bounds.x + ", " + "y=" + bounds.y + ", " + "width="//$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
            + bounds.width + ", " + "height=" + bounds.height + Messages.QUOTE ); //$NON-NLS-1$ //$NON-NLS-2$
    this.preferences.putInt ( "MinimizeMachineDialog.XPosition", bounds.x ); //$NON-NLS-1$
    this.preferences.putInt ( "MinimizeMachineDialog.YPosition", bounds.y ); //$NON-NLS-1$
    this.preferences.putInt ( "MinimizeMachineDialog.Width", bounds.width ); //$NON-NLS-1$
    this.preferences.putInt ( "MinimizeMachineDialog.Height", bounds.height ); //$NON-NLS-1$
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
        + Messages.QUOTE + mouseSelectionItem.getIndex () + Messages.QUOTE );
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
      logger.debug ( "setOpenedFilesItem", "set opened file "//$NON-NLS-1$ //$NON-NLS-2$
          + Messages.QUOTE
          + i
          + Messages.QUOTE
          + " to "//$NON-NLS-1$
          + Messages.QUOTE
          + openedFilesItem.getFiles ().get ( i ).getFirst ()
              .getAbsolutePath () + Messages.QUOTE + " on the "//$NON-NLS-1$
          + openedFilesItem.getFiles ().get ( i ).getSecond () );

      this.preferences.put ( "MainWindow.OpenedFiles" + i, openedFilesItem //$NON-NLS-1$
          .getFiles ().get ( i ).getFirst ().getAbsolutePath () );

      this.preferences.put ( "MainWindow.OpenedFilesEditor" + i,//$NON-NLS-1$
          openedFilesItem.getFiles ().get ( i ).getSecond ().equals (
              ActiveEditor.RIGHT_EDITOR ) ? "right" : "left" );//$NON-NLS-1$ //$NON-NLS-2$
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
          + openedFilesItem.getActiveFile ().getAbsolutePath ()
          + Messages.QUOTE );
      this.preferences.put ( "MainWindow.OpenedActiveFile", //$NON-NLS-1$
          openedFilesItem.getActiveFile ().getAbsolutePath () );
    }
  }


  /**
   * Sets the {@link PDAModeItem}.
   * 
   * @param pdaModeItem The {@link PDAModeItem}.
   */
  public final void setPDAModeItem ( PDAModeItem pdaModeItem )
  {
    logger.debug ( "setPDAModeItem", "set pda mode item to "//$NON-NLS-1$//$NON-NLS-2$
        + Messages.QUOTE + pdaModeItem.getIndex () + Messages.QUOTE );
    this.preferences.putInt ( "PreferencesDialog.PDAModeItem.Index", //$NON-NLS-1$
        pdaModeItem.getIndex () );
  }


  /**
   * Sets the port.
   * 
   * @param port The port.
   */
  public final void setPort ( int port )
  {
    logger.debug ( "setPort", "set port to " + Messages.QUOTE + port //$NON-NLS-1$//$NON-NLS-2$
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
        "set last active tab to " + Messages.QUOTE + index //$NON-NLS-1$
            + Messages.QUOTE );
    this.preferences.putInt ( "PreferencesDialog.LastActiveTab", index ); //$NON-NLS-1$
  }


  /**
   * Sets the {@link ReachableStatesDialog} preferences.
   * 
   * @param dialog The {@link JDialog} of the {@link ReachableStatesDialog}.
   */
  public final void setReachableStatesDialogPreferences (
      ReachableStatesDialogForm dialog )
  {
    Rectangle bounds = dialog.getBounds ();
    logger.debug ( "setReachableStatesDialogPreferences",//$NON-NLS-1$
        "set reachable states dialog bounds to " + Messages.QUOTE + "x="//$NON-NLS-1$ //$NON-NLS-2$
            + bounds.x + ", y=" + bounds.y + ", width="//$NON-NLS-1$ //$NON-NLS-2$ 
            + bounds.width + ", height=" + bounds.height + Messages.QUOTE ); //$NON-NLS-1$ 
    this.preferences.putInt ( "ReachableStatesDialog.XPosition", bounds.x ); //$NON-NLS-1$
    this.preferences.putInt ( "ReachableStatesDialog.YPosition", bounds.y ); //$NON-NLS-1$
    this.preferences.putInt ( "ReachableStatesDialog.Width", bounds.width ); //$NON-NLS-1$
    this.preferences.putInt ( "ReachableStatesDialog.Height", bounds.height ); //$NON-NLS-1$
  }


  /**
   * Sets the receive flag.
   * 
   * @param enabled True if the receive modus is active, otherwise false.
   */
  public final void setReceiveModus ( boolean enabled )
  {
    logger.debug ( "setReceiveModus", "set the receive modus to "//$NON-NLS-1$//$NON-NLS-2$
        + Messages.QUOTE + enabled + Messages.QUOTE );
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
          + recentlyUsedFilesItem.getFiles ().get ( i ).getAbsolutePath ()
          + Messages.QUOTE );
      this.preferences.put (
          "MainWindow.RecentlyUsedFiles" + i, recentlyUsedFilesItem //$NON-NLS-1$
              .getFiles ().get ( i ).getAbsolutePath () );
    }
  }


  /**
   * Sets the second view used value.
   * 
   * @param used The used value.
   */
  public final void setSecondViewUsed ( boolean used )
  {
    logger.debug ( "setSecondViewUsed", "set the second view used to "//$NON-NLS-1$//$NON-NLS-2$
        + Messages.QUOTE + used + Messages.QUOTE );
    this.preferences.putBoolean ( "MachinePanel.SecondViewUsed", //$NON-NLS-1$
        used );
  }


  /**
   * Sets the {@link TransitionItem}.
   * 
   * @param transitionItem The {@link TransitionItem}.
   */
  public final void setTransitionItem ( TransitionItem transitionItem )
  {
    logger.debug ( "setTransitionItem", "set transition item to "//$NON-NLS-1$//$NON-NLS-2$
        + Messages.QUOTE + transitionItem.getIndex () + Messages.QUOTE );
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
        + Messages.QUOTE + visible + Messages.QUOTE );
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
        + Messages.QUOTE + visible + Messages.QUOTE );
    this.preferences.putBoolean ( "MachinePanel.TableVisible", //$NON-NLS-1$
        visible );
  }


  /**
   * Sets the {@link WordModeItem}.
   * 
   * @param wordModeItem The {@link WordModeItem}.
   */
  public final void setWordModeItem ( WordModeItem wordModeItem )
  {
    logger.debug ( "setWordModeItem", "set word mode item to "//$NON-NLS-1$//$NON-NLS-2$
        + Messages.QUOTE + wordModeItem.getIndex () + Messages.QUOTE );
    this.preferences.putInt ( "PreferencesDialog.WordModeItem.Index", //$NON-NLS-1$
        wordModeItem.getIndex () );
  }


  /**
   * Sets the working path.
   * 
   * @param path The working path.
   */
  public final void setWorkingPath ( String path )
  {
    logger.debug ( "setWorkingPath", "set the working path to "//$NON-NLS-1$//$NON-NLS-2$
        + Messages.QUOTE + path + Messages.QUOTE );
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
        + zoomFactor.getFactor () + Messages.QUOTE );
    this.preferences.putInt ( "ZoomFactor", zoomFactor.getFactor () ); //$NON-NLS-1$
  }
}
