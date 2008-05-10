package de.unisiegen.gtitool.ui.logic;


import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.TreeSet;

import javax.imageio.ImageIO;

import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.exceptions.CoreException.ErrorType;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarValidationException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineValidationException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbol.NonterminalSymbolException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbol.TerminalSymbolException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.preferences.listener.LanguageChangedListener;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.EditorPanel;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.Version;
import de.unisiegen.gtitool.ui.exchange.Exchange;
import de.unisiegen.gtitool.ui.model.DefaultGrammarModel;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.model.DefaultModel;
import de.unisiegen.gtitool.ui.model.DefaultGrammarModel.GrammarType;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel.MachineType;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;
import de.unisiegen.gtitool.ui.netbeans.helperclasses.RecentlyUsedMenuItem;
import de.unisiegen.gtitool.ui.popup.TabPopupMenu;
import de.unisiegen.gtitool.ui.popup.TabPopupMenu.TabPopupMenuType;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.preferences.item.OpenedFilesItem;
import de.unisiegen.gtitool.ui.preferences.item.RecentlyUsedFilesItem;
import de.unisiegen.gtitool.ui.storage.Storage;
import de.unisiegen.gtitool.ui.utils.LayoutManager;


/**
 * The main programm window.
 * 
 * @author Benjamin Mies
 * @author Christian Fehler
 * @version $Id$
 */
public final class MainWindow implements LanguageChangedListener
{

  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger ( MainWindow.class );


  /**
   * The {@link MainWindowForm}.
   */
  private MainWindowForm gui;


  /**
   * The {@link ModifyStatusChangedListener}.
   */
  private ModifyStatusChangedListener modifyStatusChangedListener;


  /**
   * List contains the recently used files
   */
  private ArrayList < RecentlyUsedMenuItem > recentlyUsedFiles = new ArrayList < RecentlyUsedMenuItem > ();


  /**
   * Creates new form {@link MainWindow}.
   */
  public MainWindow ()
  {
    this.gui = new MainWindowForm ( this );
    try
    {
      this.gui.setIconImage ( ImageIO.read ( getClass ().getResource (
          "/de/unisiegen/gtitool/ui/icon/gtitool.png" ) ) ); //$NON-NLS-1$
    }
    catch ( Exception exc )
    {
      exc.printStackTrace ();
    }
    this.gui.setTitle ( "GTI Tool " + Version.VERSION ); //$NON-NLS-1$
    this.gui.setBounds ( PreferenceManager.getInstance ()
        .getMainWindowBounds () );

    setStateGeneral ( false );
    setStateSave ( false );
    setStateHistoryItem ();
    setStateValidate ( false );
    setStateMenuEnterWord ( false );

    // Edit Machine
    // TODO
    this.gui.getJMenuItemEditMachine ().setEnabled ( false );

    setStateRecentlyUsed ( false );
    setStateGrammarButtons ( false );
    setStateMachineButtons ( false );
    setStateEditDocument ( false );

    // Console and table visibility
    this.gui.getJCheckBoxMenuItemConsole ().setSelected (
        PreferenceManager.getInstance ().getVisibleConsole () );
    this.gui.getJCheckBoxMenuItemTable ().setSelected (
        PreferenceManager.getInstance ().getVisibleTable () );

    this.gui.setVisible ( true );
    if ( PreferenceManager.getInstance ().getMainWindowMaximized () )
    {
      this.gui.setExtendedState ( this.gui.getExtendedState ()
          | Frame.MAXIMIZED_BOTH );
    }
    // Language changed listener
    PreferenceManager.getInstance ().addLanguageChangedListener ( this );

    for ( File file : PreferenceManager.getInstance ()
        .getRecentlyUsedFilesItem ().getFiles () )
    {
      this.recentlyUsedFiles.add ( new RecentlyUsedMenuItem ( this, file ) );
    }
    organizeRecentlyUsedFilesMenu ();

    this.modifyStatusChangedListener = new ModifyStatusChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void modifyStatusChanged ( boolean modified )
      {
        setStateSave ( modified );
      }
    };
  }


  /**
   * Returns the gui.
   * 
   * @return The gui.
   * @see #gui
   */
  public final MainWindowForm getGui ()
  {
    return this.gui;
  }


  /**
   * Getter for the {@link ModifyStatusChangedListener}.
   * 
   * @return The {@link ModifyStatusChangedListener}.
   */
  public ModifyStatusChangedListener getModifyStatusChangedListener ()
  {
    return this.modifyStatusChangedListener;
  }


  /**
   * Handles the action event of the about item.
   */
  public final void handleAbout ()
  {
    AboutDialog aboutDialog = new AboutDialog ( this.gui );
    aboutDialog.show ();
  }


  /**
   * Handle add production button pressed.
   */
  public final void handleAddProduction ()
  {
    EditorPanel panel = this.gui.getEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( panel instanceof GrammarPanel )
    {
      GrammarPanel grammarPanel = ( GrammarPanel ) panel;
      grammarPanel.handleAddProduction ();
    }
  }


  /**
   * Handles the auto step stopped by exception event.
   */
  public final void handleAutoStepStopped ()
  {
    setStateAutoStep ( false );
  }


  /**
   * Closes the selected {@link EditorPanel}.
   * 
   * @return True if the panel is closed, false if the close was canceled.
   */
  public final boolean handleClose ()
  {
    if ( this.gui.getEditorPanelTabbedPane ().getSelectedEditorPanel () == null )
    {
      throw new RuntimeException ( "no selected editor panel" ); //$NON-NLS-1$
    }
    return handleClose ( this.gui.getEditorPanelTabbedPane ()
        .getSelectedEditorPanel () );
  }


  /**
   * Closes the {@link EditorPanel}.
   * 
   * @param panel The {@link EditorPanel} to close.
   * @return True if the panel is closed, false if the close was canceled.
   */
  public final boolean handleClose ( EditorPanel panel )
  {
    if ( panel.isModified () )
    {
      ConfirmDialog confirmDialog = new ConfirmDialog ( this.gui,
          Messages.getString (
              "MainWindow.CloseModifyMessage", panel.getName () ), Messages //$NON-NLS-1$
              .getString ( "MainWindow.CloseModifyTitle" ), true, true, true ); //$NON-NLS-1$
      confirmDialog.show ();

      if ( confirmDialog.isConfirmed () )
      {
        handleSave ( panel );
      }
      else if ( confirmDialog.isCanceled () )
      {
        return false;
      }
    }

    this.gui.getEditorPanelTabbedPane ().removeSelectedEditorPanel ();
    // All editor panels are closed
    if ( this.gui.getEditorPanelTabbedPane ().getSelectedEditorPanel () == null )
    {
      setStateGrammarButtons ( false );
      setStateMachineButtons ( false );
      setStateEditDocument ( false );
    }
    return true;
  }


  /**
   * Handle the close all files event
   */
  public final void handleCloseAll ()
  {
    for ( EditorPanel current : this.gui.getEditorPanelTabbedPane () )
    {
      // Check if the close was canceled
      if ( !handleClose ( current ) )
      {
        break;
      }
    }
  }


  /**
   * Handles console state changes.
   */
  public final void handleConsoleStateChanged ()
  {
    EditorPanel panel = this.gui.getEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( PreferenceManager.getInstance ().getVisibleConsole () != this.gui
        .getJCheckBoxMenuItemConsole ().getState () )
    {
      panel.setVisibleConsole ( this.gui.getJCheckBoxMenuItemConsole ()
          .getState () );
      PreferenceManager.getInstance ().setVisibleConsole (
          this.gui.getJCheckBoxMenuItemConsole ().getState () );
    }
  }


  /**
   * Handle delete production button pressed.
   */
  public final void handleDeleteProduction ()
  {
    EditorPanel panel = this.gui.getEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( panel instanceof GrammarPanel )
    {
      GrammarPanel grammarPanel = ( GrammarPanel ) panel;
      grammarPanel.handleDeleteProduction ();
    }
  }


  /**
   * Use the active Editor Panel as draf for a new file
   * 
   * @param type Type of the new file
   */
  public final void handleDraftFor ( GrammarType type )
  {
    try
    {
      DefaultGrammarModel model = new DefaultGrammarModel ( this.gui
          .getEditorPanelTabbedPane ().getSelectedEditorPanel ().getModel ()
          .getElement (), type.toString () );
      EditorPanel newEditorPanel = new GrammarPanel ( this.gui, model, null );

      TreeSet < String > nameList = new TreeSet < String > ();
      int count = 0;
      for ( EditorPanel current : this.gui.getEditorPanelTabbedPane () )
      {
        if ( current.getFile () == null )
        {
          nameList.add ( current.getName () );
          count++ ;
        }
      }

      String name = Messages.getString ( "MainWindow.NewFile" ) + count //$NON-NLS-1$
          + "." + type.toString ().toLowerCase (); //$NON-NLS-1$
      while ( nameList.contains ( name ) )
      {
        count++ ;
        name = Messages.getString ( "MainWindow.NewFile" ) + count //$NON-NLS-1$
            + "." + type.toString ().toLowerCase (); //$NON-NLS-1$
      }

      newEditorPanel.setName ( name );
      this.gui.getEditorPanelTabbedPane ().addEditorPanel ( newEditorPanel );
      newEditorPanel
          .addModifyStatusChangedListener ( this.modifyStatusChangedListener );
      this.gui.getEditorPanelTabbedPane ().setSelectedEditorPanel (
          newEditorPanel );

      setStateGeneral ( true );
      setStateValidate ( true );
      setStateEditItem ( true );
    }
    catch ( StoreException exc )
    {
      InfoDialog infoDialog = new InfoDialog ( this.gui, exc.getMessage (),
          Messages.getString ( "MainWindow.ErrorLoad" ) ); //$NON-NLS-1$
      infoDialog.show ();
    }
    catch ( NonterminalSymbolSetException exc )
    {
      // Do nothing
    }
    catch ( NonterminalSymbolException exc )
    {
      // Do nothing
    }
    catch ( TerminalSymbolSetException exc )
    {
      // Do nothing
    }
    catch ( TerminalSymbolException exc )
    {
      // Do nothing
    }
  }


  /**
   * Use the active Editor Panel as draf for a new file
   * 
   * @param type Type of the new file
   */
  public final void handleDraftFor ( MachineType type )
  {
    try
    {
      DefaultMachineModel model = new DefaultMachineModel ( this.gui
          .getEditorPanelTabbedPane ().getSelectedEditorPanel ().getModel ()
          .getElement (), type.toString () );
      EditorPanel newEditorPanel = new MachinePanel ( this.gui, model, null );

      TreeSet < String > nameList = new TreeSet < String > ();
      int count = 0;
      for ( EditorPanel current : this.gui.getEditorPanelTabbedPane () )
      {
        if ( current.getFile () == null )
        {
          nameList.add ( current.getName () );
          count++ ;
        }
      }

      String name = Messages.getString ( "MainWindow.NewFile" ) + count //$NON-NLS-1$
          + "." + type.toString ().toLowerCase (); //$NON-NLS-1$
      while ( nameList.contains ( name ) )
      {
        count++ ;
        name = Messages.getString ( "MainWindow.NewFile" ) + count //$NON-NLS-1$
            + "." + type.toString ().toLowerCase (); //$NON-NLS-1$
      }

      newEditorPanel.setName ( name );
      this.gui.getEditorPanelTabbedPane ().addEditorPanel ( newEditorPanel );
      newEditorPanel
          .addModifyStatusChangedListener ( this.modifyStatusChangedListener );
      this.gui.getEditorPanelTabbedPane ().setSelectedEditorPanel (
          newEditorPanel );

      setStateGeneral ( true );
      setStateValidate ( true );
      setStateEditItem ( true );
    }
    catch ( StoreException exc )
    {
      InfoDialog infoDialog = new InfoDialog ( this.gui, exc.getMessage (),
          Messages.getString ( "MainWindow.ErrorLoad" ) ); //$NON-NLS-1$
      infoDialog.show ();
    }
    catch ( TransitionSymbolOnlyOneTimeException e )
    {
      // Do nothing
    }
    catch ( StateException e )
    {
      // Dot nothing
    }
    catch ( SymbolException e )
    {
      // Do nothing
    }
    catch ( AlphabetException e )
    {
      // Do nothing
    }
    catch ( TransitionException e )
    {
      // Do nothing
    }
  }


  /**
   * Handle edit document action in the toolbar.
   */
  public final void handleEditDocument ()
  {
    EditorPanel panel = this.gui.getEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    panel.handleToolbarEditDocument ();
  }


  /**
   * Handle Edit Machine button pressed
   */
  public final void handleEditMachine ()
  {
    EditorPanel panel = this.gui.getEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( ! ( panel instanceof MachinePanel ) )
    {
      throw new IllegalArgumentException ( "not a machine panel" ); //$NON-NLS-1$
    }

    setStateEditItem ( true );
    setStateEnterWord ( false );
    MachinePanel machinePanel = ( MachinePanel ) panel;
    machinePanel.handleEditMachine ();
    machinePanel.setVisibleConsole ( this.gui.getJCheckBoxMenuItemConsole ()
        .getState () );
    // TODO
    this.gui.getJCheckBoxMenuItemConsole ().setEnabled ( true );
    setStateMenuEnterWord ( true );
    // TODO
    this.gui.getJMenuItemEditMachine ().setEnabled ( false );

    setStateValidate ( true );
  }


  /**
   * Handle edit production button pressed.
   */
  public final void handleEditProduction ()
  {
    EditorPanel panel = this.gui.getEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( panel instanceof GrammarPanel )
    {
      GrammarPanel grammarPanel = ( GrammarPanel ) panel;
      grammarPanel.handleEditProduction ();
    }
  }


  /**
   * Handle the action event of the enter word item.
   */
  public final void handleEnterWord ()
  {
    EditorPanel panel = this.gui.getEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( ! ( panel instanceof MachinePanel ) )
    {
      throw new IllegalArgumentException ( "not a machine panel" ); //$NON-NLS-1$
    }
    MachinePanel machinePanel = ( MachinePanel ) panel;

    if ( handleValidate ( false ) )
    {
      setStateEditItem ( false );
      // TODO
      this.gui.getJGTIToolBarButtonStart ().setEnabled ( true );
      machinePanel.handleEnterWord ();
      // TODO
      this.gui.getJCheckBoxMenuItemConsole ().setEnabled ( false );
      machinePanel.setVisibleConsole ( false );
      setStateMenuEnterWord ( false );
      // TODO
      this.gui.getJMenuItemEditMachine ().setEnabled ( true );
      setStateValidate ( false );
    }
  }


  /**
   * Handles the {@link Exchange}.
   */
  public final void handleExchange ()
  {
    EditorPanel panel = this.gui.getEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( panel == null )
    {
      ExchangeDialog exchangeDialog = new ExchangeDialog ( this, null, null );
      exchangeDialog.show ();
    }
    else
    {
      panel.handleExchange ();
    }
  }


  /**
   * Handles the history event.
   */
  public final void handleHistory ()
  {
    if ( this.gui.getEditorPanelTabbedPane ().getSelectedEditorPanel () instanceof MachinePanel )
    {
      MachinePanel machinePanel = ( MachinePanel ) this.gui
          .getEditorPanelTabbedPane ().getSelectedEditorPanel ();
      machinePanel.handleHistory ();
    }
    else
    {
      throw new RuntimeException ( "the selected panel is not a machine panel" ); //$NON-NLS-1$
    }
  }


  /**
   * Handle the new event.
   */
  public final void handleNew ()
  {
    NewDialog newDialog = new NewDialog ( this.gui );
    // newDialog.setLocationRelativeTo ( window ) ;
    newDialog.show ();
    EditorPanel newEditorPanel = newDialog.getEditorPanel ();
    if ( newEditorPanel != null )
    {
      TreeSet < String > nameList = new TreeSet < String > ();
      int count = 0;
      for ( EditorPanel current : this.gui.getEditorPanelTabbedPane () )
      {
        if ( current.getFile () == null )
        {
          nameList.add ( current.getName () );
          count++ ;
        }
      }

      String name = Messages.getString ( "MainWindow.NewFile" ) + count //$NON-NLS-1$
          + newDialog.getEditorPanel ().getFileEnding ();
      while ( nameList.contains ( name ) )
      {
        count++ ;
        name = Messages.getString ( "MainWindow.NewFile" ) + count //$NON-NLS-1$
            + newDialog.getEditorPanel ().getFileEnding ();
      }

      newEditorPanel.setName ( name );
      this.gui.getEditorPanelTabbedPane ().addEditorPanel ( newEditorPanel );
      newEditorPanel
          .addModifyStatusChangedListener ( this.modifyStatusChangedListener );
      this.gui.getEditorPanelTabbedPane ().setSelectedEditorPanel (
          newEditorPanel );

      setStateGeneral ( true );
      setStateValidate ( true );

      // toolbar items
      setStateEditItem ( true );
    }
  }


  /**
   * Handle the new event with a given {@link Element}.
   * 
   * @param element The {@link Element}.
   */
  public final void handleNew ( Element element )
  {
    DefaultModel model = null;
    try
    {
      if ( element.getName ().equals ( "MachineModel" ) ) //$NON-NLS-1$
      {
        model = new DefaultMachineModel ( element, null );
      }
      else if ( element.getName ().equals ( "GrammarModel" ) ) //$NON-NLS-1$
      {
        model = new DefaultGrammarModel ( element, null );
      }
    }
    catch ( TransitionSymbolOnlyOneTimeException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( StateException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( SymbolException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( TransitionException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( StoreException exc )
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

    if ( model != null )
    {
      EditorPanel newEditorPanel;
      if ( model instanceof DefaultMachineModel )
      {
        newEditorPanel = new MachinePanel ( this.gui,
            ( DefaultMachineModel ) model, null );
      }
      else if ( model instanceof DefaultGrammarModel )
      {
        newEditorPanel = new GrammarPanel ( this.gui,
            ( DefaultGrammarModel ) model, null );
      }
      else
      {
        throw new RuntimeException ( "incorrect model" ); //$NON-NLS-1$
      }

      TreeSet < String > nameList = new TreeSet < String > ();
      int count = 0;
      for ( EditorPanel current : this.gui.getEditorPanelTabbedPane () )
      {
        if ( current.getFile () == null )
        {
          nameList.add ( current.getName () );
          count++ ;
        }
      }

      String name = Messages.getString ( "MainWindow.NewFile" ) + count //$NON-NLS-1$
          + newEditorPanel.getFileEnding ();
      while ( nameList.contains ( name ) )
      {
        count++ ;
        name = Messages.getString ( "MainWindow.NewFile" ) + count //$NON-NLS-1$
            + newEditorPanel.getFileEnding ();
      }

      newEditorPanel.setName ( name );
      this.gui.getEditorPanelTabbedPane ().addEditorPanel ( newEditorPanel );
      newEditorPanel
          .addModifyStatusChangedListener ( this.modifyStatusChangedListener );
      this.gui.getEditorPanelTabbedPane ().setSelectedEditorPanel (
          newEditorPanel );

      setStateGeneral ( true );
      setStateValidate ( true );

      // toolbar items
      setStateEditItem ( true );
    }
  }


  /**
   * Handles the open event.
   */
  public final void handleOpen ()
  {
    OpenDialog openDialog = new OpenDialog ( this.gui, PreferenceManager
        .getInstance ().getWorkingPath () );
    openDialog.show ();
    if ( ( !openDialog.isConfirmed () )
        || ( openDialog.getSelectedFile () == null ) )
    {
      return;
    }

    for ( File file : openDialog.getSelectedFiles () )
    {
      openFile ( file, true );
    }
    PreferenceManager.getInstance ().setWorkingPath (
        openDialog.getSelectedFile ().getParentFile ().getAbsolutePath () );
  }


  /**
   * Handle the action event of the preferences item.
   */
  public final void handlePreferences ()
  {
    PreferencesDialog preferencesDialog = new PreferencesDialog ( this.gui );
    preferencesDialog.show ();
  }


  /**
   * Handles the quit event.
   */
  public final void handleQuit ()
  {
    // Active file
    File activeFile = this.gui.getEditorPanelTabbedPane ()
        .getSelectedEditorPanel () == null ? null : this.gui
        .getEditorPanelTabbedPane ().getSelectedEditorPanel ().getFile ();

    // Opened file
    ArrayList < File > openedFiles = new ArrayList < File > ();
    for ( EditorPanel current : this.gui.getEditorPanelTabbedPane () )
    {
      if ( current.getFile () != null )
      {
        openedFiles.add ( current.getFile () );
      }
    }
    // Close the tabs
    for ( int i = this.gui.getEditorPanelTabbedPane ().getComponentCount () - 1 ; i >= 0 ; i-- )
    {
      EditorPanel current = this.gui.getEditorPanelTabbedPane ()
          .getEditorPanel ( i );
      if ( current.isModified () )
      {
        this.gui.getEditorPanelTabbedPane ().setSelectedEditorPanel ( current );

        ConfirmDialog confirmDialog = new ConfirmDialog ( this.gui,
            Messages.getString (
                "MainWindow.CloseModifyMessage", current.getName () ), Messages //$NON-NLS-1$
                .getString ( "MainWindow.CloseModifyTitle" ), true, true, true ); //$NON-NLS-1$
        confirmDialog.show ();

        if ( confirmDialog.isConfirmed () )
        {
          File file = current.handleSave ();
          if ( file != null )
          {
            this.gui.getEditorPanelTabbedPane ().setEditorPanelTitle ( current,
                file.getName () );
          }
        }
        else if ( confirmDialog.isCanceled () )
        {
          return;
        }
      }
      this.gui.getEditorPanelTabbedPane ().removeEditorPanel ( current );
    }
    PreferenceManager.getInstance ().setMainWindowPreferences ( this.gui );
    PreferenceManager.getInstance ().setOpenedFilesItem (
        new OpenedFilesItem ( openedFiles, activeFile ) );

    ArrayList < File > files = new ArrayList < File > ();
    for ( RecentlyUsedMenuItem item : this.recentlyUsedFiles )
    {
      files.add ( item.getFile () );
    }
    PreferenceManager.getInstance ().setRecentlyUsedFilesItem (
        new RecentlyUsedFilesItem ( files ) );

    // System exit
    System.exit ( 0 );
  }


  /**
   * Handle redo button pressed
   */
  public final void handleRedo ()
  {
    EditorPanel panel = this.gui.getEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( panel != null )
    {
      panel.handleRedo ();
    }
  }


  /**
   * Handles the save file event on the selected editor panel.
   */
  public final void handleSave ()
  {
    if ( this.gui.getEditorPanelTabbedPane ().getSelectedEditorPanel () == null )
    {
      throw new RuntimeException ( "no selected editor panel" ); //$NON-NLS-1$
    }
    handleSave ( this.gui.getEditorPanelTabbedPane ().getSelectedEditorPanel () );
  }


  /**
   * Handles the save file event.
   * 
   * @param panel The {@link EditorPanel} to be saved
   */
  public final void handleSave ( EditorPanel panel )
  {
    File file = panel.handleSave ();
    if ( file != null )
    {
      RecentlyUsedMenuItem item = new RecentlyUsedMenuItem ( this, file );
      this.recentlyUsedFiles.remove ( item );
      this.recentlyUsedFiles.add ( 0, item );
      organizeRecentlyUsedFilesMenu ();

      for ( EditorPanel current : this.gui.getEditorPanelTabbedPane () )
      {
        if ( ( !current.equals ( this.gui.getEditorPanelTabbedPane ()
            .getSelectedEditorPanel () ) && file.equals ( current.getFile () ) ) )
        {
          this.gui.getEditorPanelTabbedPane ().removeEditorPanel ( current );
        }
      }
      this.gui.getEditorPanelTabbedPane ().setEditorPanelTitle ( panel,
          file.getName () );
    }
  }


  /**
   * Handle the save all files event
   */
  public final void handleSaveAll ()
  {
    EditorPanel active = this.gui.getEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    for ( EditorPanel current : this.gui.getEditorPanelTabbedPane () )
    {
      this.gui.getEditorPanelTabbedPane ().setSelectedEditorPanel ( current );
      handleSave ( current );
    }
    this.gui.getEditorPanelTabbedPane ().setSelectedEditorPanel ( active );
  }


  /**
   * Handle the save as event.
   */
  public final void handleSaveAs ()
  {
    EditorPanel panel = this.gui.getEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    File file = panel.handleSaveAs ();
    if ( file != null )
    {
      RecentlyUsedMenuItem item = new RecentlyUsedMenuItem ( this, file );
      this.recentlyUsedFiles.remove ( item );
      this.recentlyUsedFiles.add ( 0, item );
      organizeRecentlyUsedFilesMenu ();
      for ( EditorPanel current : this.gui.getEditorPanelTabbedPane () )
      {
        if ( ( !current.equals ( this.gui.getEditorPanelTabbedPane ()
            .getSelectedEditorPanel () ) && file.equals ( current.getFile () ) ) )
        {
          this.gui.getEditorPanelTabbedPane ().removeEditorPanel ( current );
        }
      }
      this.gui.getEditorPanelTabbedPane ().setEditorPanelTitle ( panel,
          file.getName () );
    }
  }


  /**
   * Handles the tabbed pane mouse released event.
   * 
   * @param event The {@link MouseEvent}.
   */
  public final void handleTabbedPaneMouseReleased ( MouseEvent event )
  {
    int tabIndex = this.gui.getEditorPanelTabbedPane ().getUI ()
        .tabForCoordinate ( this.gui.getEditorPanelTabbedPane (),
            event.getX (), event.getY () );

    if ( ( event.getButton () == MouseEvent.BUTTON1 )
        && ( event.getClickCount () >= 2 ) && ( tabIndex == -1 ) )
    {
      handleNew ();
    }
    else if ( event.getButton () == MouseEvent.BUTTON3 )
    {
      TabPopupMenu popupMenu;
      if ( tabIndex == -1 )
      {
        popupMenu = new TabPopupMenu ( this, TabPopupMenuType.TAB_DEACTIVE );
      }
      else
      {
        popupMenu = new TabPopupMenu ( this, TabPopupMenuType.TAB_ACTIVE );
      }
      popupMenu.show ( ( Component ) event.getSource (), event.getX (), event
          .getY () );
    }
  }


  /**
   * Handles the tabbed pane state changed event.
   */
  public final void handleTabbedPaneStateChanged ()
  {
    EditorPanel panel = this.gui.getEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( panel != null )
    {
      setStateMachineButtons ( panel instanceof MachinePanel );
      setStateGrammarButtons ( panel instanceof GrammarPanel );
      // MachinePanel
      if ( panel instanceof MachinePanel )
      {
        setStateDraftForItems ( true );

        MachinePanel machinePanel = ( MachinePanel ) panel;
        // TODO
        this.gui.getJCheckBoxMenuItemConsole ().setEnabled (
            !machinePanel.isWordEnterMode () );
        // TODO
        machinePanel.setVisibleConsole ( this.gui
            .getJCheckBoxMenuItemConsole ().getState ()
            && !machinePanel.isWordEnterMode () );
        // TODO
        machinePanel.setVisibleTable ( this.gui.getJCheckBoxMenuItemTable ()
            .getState () );
        setStateEditItem ( !machinePanel.isWordEnterMode () );
        setStateEnterWord ( machinePanel.isWordEnterMode () );
        // TODO
        this.gui.getJMenuItemEditMachine ().setEnabled (
            machinePanel.isWordEnterMode () );
        // TODO
        this.gui.getJMenuItemValidate ().setEnabled (
            !machinePanel.isWordEnterMode () );
        setStateMenuEnterWord ( !machinePanel.isWordEnterMode () );
        // TODO
        this.gui.getJCheckBoxMenuItemTable ().setEnabled ( true );

        // Set the status of the word navigation icons
        // TODO
        this.gui.getJGTIToolBarButtonStart ().setEnabled (
            machinePanel.isWordEnterMode ()
                && !machinePanel.isWordNavigation () );
        // TODO
        this.gui.getJGTIToolBarButtonPrevious ().setEnabled (
            machinePanel.isWordNavigation () );
        // TODO
        this.gui.getJGTIToolBarButtonNextStep ().setEnabled (
            machinePanel.isWordNavigation () );
        // TODO
        this.gui.getJGTIToolBarToggleButtonAutoStep ().setEnabled (
            machinePanel.isWordNavigation () );
        // TODO
        this.gui.getJGTIToolBarButtonStop ().setEnabled (
            machinePanel.isWordNavigation () );

      }
      // GrammarPanel
      else if ( panel instanceof GrammarPanel )
      {
        // TODO
        this.gui.getJCheckBoxMenuItemTable ().setEnabled ( false );
        panel.setVisibleConsole ( this.gui.getJCheckBoxMenuItemConsole ()
            .getState () );
        setStateMenuEnterWord ( false );

        setStateDraftForItems ( false );
      }
      else
      {
        throw new RuntimeException ( "unsupported panel" ); //$NON-NLS-1$
      }

      setStateUndo ( panel.isUndoAble () );
      setStateRedo ( panel.isRedoAble () );
    }

    // Save status
    setStateSave ();

    // Item status
    setStateHistoryItem ();
  }


  /**
   * Handles table state changes.
   */
  public final void handleTableStateChanged ()
  {
    EditorPanel panel = this.gui.getEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( ( panel instanceof MachinePanel ) )
    {
      MachinePanel machinePanel = ( MachinePanel ) panel;

      if ( PreferenceManager.getInstance ().getVisibleTable () != this.gui
          .getJCheckBoxMenuItemTable ().getState () )
      {
        PreferenceManager.getInstance ().setVisibleTable (
            this.gui.getJCheckBoxMenuItemTable ().getState () );
        machinePanel.setVisibleTable ( this.gui.getJCheckBoxMenuItemTable ()
            .getState () );
      }
    }

  }


  /**
   * Handle Toolbar Add State button value changed
   * 
   * @param state The new State of the Add State Toolbar button
   */
  public final void handleToolbarAddState ( boolean state )
  {
    for ( EditorPanel panel : this.gui.getEditorPanelTabbedPane () )
    {
      if ( ( panel instanceof MachinePanel ) )
      {
        MachinePanel machinePanel = ( MachinePanel ) panel;

        machinePanel.handleToolbarAddState ( state );
      }
    }
  }


  /**
   * Handle Toolbar End button value changed
   * 
   * @param state The new State of the End Toolbar button
   */
  public final void handleToolbarEnd ( boolean state )
  {
    for ( EditorPanel panel : this.gui.getEditorPanelTabbedPane () )
    {
      if ( ( panel instanceof MachinePanel ) )
      {
        MachinePanel machinePanel = ( MachinePanel ) panel;

        machinePanel.handleToolbarEnd ( state );
      }
    }
  }


  /**
   * Handle Toolbar Mouse button value changed
   * 
   * @param state The new State of the Mouse Toolbar button
   */
  public final void handleToolbarMouse ( boolean state )
  {
    for ( EditorPanel panel : this.gui.getEditorPanelTabbedPane () )
    {
      if ( ( panel instanceof MachinePanel ) )
      {
        MachinePanel machinePanel = ( MachinePanel ) panel;

        machinePanel.handleToolbarMouse ( state );
      }
    }
  }


  /**
   * Handle Toolbar Start button value changed
   * 
   * @param state The new State of the Start Toolbar button
   */
  public final void handleToolbarStart ( boolean state )
  {
    for ( EditorPanel panel : this.gui.getEditorPanelTabbedPane () )
    {
      if ( ( panel instanceof MachinePanel ) )
      {
        MachinePanel machinePanel = ( MachinePanel ) panel;

        machinePanel.handleToolbarStart ( state );
      }
    }
  }


  /**
   * Handle Toolbar Transition button value changed
   * 
   * @param state The new State of the Transition Toolbar button
   */
  public final void handleToolbarTransition ( boolean state )
  {
    for ( EditorPanel panel : this.gui.getEditorPanelTabbedPane () )
    {
      if ( ( panel instanceof MachinePanel ) )
      {
        MachinePanel machinePanel = ( MachinePanel ) panel;

        machinePanel.handleToolbarTransition ( state );
      }
    }
  }


  /**
   * Handle undo button pressed
   */
  public final void handleUndo ()
  {
    EditorPanel panel = this.gui.getEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( panel != null )
    {
      panel.handleUndo ();
    }
  }


  /**
   * Handle the action event of the enter word item.
   */
  public final void handleValidate ()
  {
    handleValidate ( true );
  }


  /**
   * Handle the action event of the enter word item.
   * 
   * @param showDialogIfWarning If true the dialog is shown if there a no errors
   *          but warnings.
   * @return True if the validating finished without errors, otherwise false.
   */
  public final boolean handleValidate ( boolean showDialogIfWarning )
  {
    EditorPanel panel = this.gui.getEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();

    int errorCount = 0;
    int warningCount = 0;
    boolean machinePanelSelected;

    if ( panel instanceof MachinePanel )
    {
      machinePanelSelected = true;
      MachinePanel machinePanel = ( MachinePanel ) panel;
      try
      {
        panel.clearValidationMessages ();
        machinePanel.getMachine ().validate ();
      }
      catch ( MachineValidationException e )
      {

        for ( MachineException error : e.getMachineException () )
        {
          if ( error.getType ().equals ( ErrorType.ERROR ) )
          {
            machinePanel.addError ( error );
            errorCount++ ;
          }
          else if ( error.getType ().equals ( ErrorType.WARNING ) )
          {
            machinePanel.addWarning ( error );
            warningCount++ ;
          }
        }
      }
    }
    else if ( panel instanceof GrammarPanel )
    {
      machinePanelSelected = false;
      GrammarPanel grammarPanel = ( GrammarPanel ) panel;
      try
      {
        panel.clearValidationMessages ();
        grammarPanel.getGrammar ().validate ();
      }
      catch ( GrammarValidationException e )
      {

        for ( GrammarException error : e.getGrammarException () )
        {
          if ( error.getType ().equals ( ErrorType.ERROR ) )
          {
            grammarPanel.addError ( error );
            errorCount++ ;
          }
          else if ( error.getType ().equals ( ErrorType.WARNING ) )
          {
            grammarPanel.addWarning ( error );
            warningCount++ ;
          }
        }
      }
    }
    else
    {
      throw new RuntimeException (
          "the select panel is not a machine or grammar panel" ); //$NON-NLS-1$
    }

    // Return if only errors should be displayes
    if ( !showDialogIfWarning && ( errorCount == 0 ) )
    {
      if ( warningCount > 0 )
      {
        // Select the warning tab
        panel.getJTabbedPaneConsole ().setSelectedIndex ( 1 );

        // Update the titles
        panel.getJTabbedPaneConsole ().setTitleAt ( 0,
            machinePanelSelected ? Messages.getString ( "MachinePanel.Error" ) //$NON-NLS-1$
                : Messages.getString ( "GrammarPanel.Error" ) ); //$NON-NLS-1$

        panel.getJTabbedPaneConsole ().setTitleAt (
            1,
            Messages.getString (
                machinePanelSelected ? "MachinePanel.WarningFound" //$NON-NLS-1$
                    : "GrammarPanel.WarningFound", false, new Integer ( //$NON-NLS-1$
                    warningCount ) ) );
      }
      return true;
    }

    InfoDialog infoDialog = null;
    // Error and warning
    if ( ( errorCount > 0 ) && ( warningCount > 0 ) )
    {
      String message = null;
      if ( ( errorCount == 1 ) && ( warningCount == 1 ) )
      {
        message = Messages
            .getString ( machinePanelSelected ? "MainWindow.ErrorWarningMachineCount0" //$NON-NLS-1$
                : "MainWindow.ErrorWarningGrammarCount0" ); //$NON-NLS-1$
      }
      else if ( ( errorCount == 1 ) && ( warningCount > 1 ) )
      {
        message = Messages.getString (
            machinePanelSelected ? "MainWindow.ErrorWarningMachineCount1" //$NON-NLS-1$
                : "MainWindow.ErrorWarningGrammarCount1", false, String //$NON-NLS-1$
                .valueOf ( warningCount ) );
      }
      else if ( ( errorCount > 1 ) && ( warningCount == 1 ) )
      {
        message = Messages.getString (
            machinePanelSelected ? "MainWindow.ErrorWarningMachineCount2" //$NON-NLS-1$
                : "MainWindow.ErrorWarningGrammarCount2", false, String //$NON-NLS-1$
                .valueOf ( errorCount ) );
      }
      else
      {
        message = Messages.getString (
            machinePanelSelected ? "MainWindow.ErrorWarningMachineCount3" //$NON-NLS-1$
                : "MainWindow.ErrorWarningGrammarCount3", false, String //$NON-NLS-1$
                .valueOf ( errorCount ), String.valueOf ( warningCount ) );
      }

      // Update the titles
      panel.getJTabbedPaneConsole ().setTitleAt ( 0,
          Messages.getString ( machinePanelSelected ? "MachinePanel.ErrorFound" //$NON-NLS-1$
              : "GrammarPanel.ErrorFound", false, new Integer ( errorCount ) ) ); //$NON-NLS-1$
      panel.getJTabbedPaneConsole ().setTitleAt (
          1,
          Messages.getString (
              machinePanelSelected ? "MachinePanel.WarningFound" //$NON-NLS-1$
                  : "GrammarPanel.WarningFound", false, new Integer ( //$NON-NLS-1$
                  warningCount ) ) );

      // Select the error tab
      panel.getJTabbedPaneConsole ().setSelectedIndex ( 0 );

      infoDialog = new InfoDialog ( this.gui, message, Messages
          .getString ( machinePanelSelected ? "MainWindow.ErrorWarningMachine" //$NON-NLS-1$
              : "MainWindow.ErrorWarningGrammar" ) ); //$NON-NLS-1$
    }
    // Only error
    else if ( errorCount > 0 )
    {
      String message;
      if ( errorCount == 1 )
      {
        message = Messages
            .getString ( machinePanelSelected ? "MainWindow.ErrorMachineCountOne"//$NON-NLS-1$
                : "MainWindow.ErrorGrammarCountOne" );//$NON-NLS-1$
      }
      else
      {
        message = Messages.getString (
            machinePanelSelected ? "MainWindow.ErrorMachineCount"//$NON-NLS-1$
                : "MainWindow.ErrorGrammarCount", false, String//$NON-NLS-1$
                .valueOf ( errorCount ) );
      }

      // Update the titles
      panel.getJTabbedPaneConsole ().setTitleAt ( 0,
          Messages.getString ( machinePanelSelected ? "MachinePanel.ErrorFound"//$NON-NLS-1$
              : "GrammarPanel.ErrorFound", false, new Integer ( errorCount ) ) );//$NON-NLS-1$
      panel.getJTabbedPaneConsole ().setTitleAt ( 1,
          Messages.getString ( machinePanelSelected ? "MachinePanel.Warning"//$NON-NLS-1$
              : "GrammarPanel.Warning" ) );//$NON-NLS-1$

      panel.getJTabbedPaneConsole ().setSelectedIndex ( 0 );

      infoDialog = new InfoDialog ( this.gui, message, Messages
          .getString ( machinePanelSelected ? "MainWindow.ErrorMachine"//$NON-NLS-1$
              : "MainWindow.ErrorGrammar" ) );//$NON-NLS-1$
    }
    // Only warning
    else if ( warningCount > 0 )
    {
      String message;
      if ( warningCount == 1 )
      {
        message = Messages
            .getString ( machinePanelSelected ? "MainWindow.WarningMachineCountOne"//$NON-NLS-1$
                : "MainWindow.WarningGrammarCountOne" );//$NON-NLS-1$
      }
      else
      {
        message = Messages.getString (
            machinePanelSelected ? "MainWindow.WarningMachineCount"//$NON-NLS-1$
                : "MainWindow.WarningGrammarCount", false, String//$NON-NLS-1$
                .valueOf ( warningCount ) );
      }

      // Update the titles
      panel.getJTabbedPaneConsole ().setTitleAt ( 0,
          Messages.getString ( machinePanelSelected ? "MachinePanel.Error"//$NON-NLS-1$
              : "GrammarPanel.Error" ) );//$NON-NLS-1$
      panel.getJTabbedPaneConsole ().setTitleAt (
          1,
          Messages.getString (
              machinePanelSelected ? "MachinePanel.WarningFound"//$NON-NLS-1$
                  : "GrammarPanel.WarningFound", false, new Integer ( //$NON-NLS-1$
                  warningCount ) ) );

      // Select the warning tab
      panel.getJTabbedPaneConsole ().setSelectedIndex ( 1 );

      infoDialog = new InfoDialog ( this.gui, message, Messages
          .getString ( machinePanelSelected ? "MainWindow.WarningMachine"//$NON-NLS-1$
              : "MainWindow.WarningGrammar" ) );//$NON-NLS-1$
    }
    // No error and no warning
    else
    {
      // Update the titles
      panel.getJTabbedPaneConsole ().setTitleAt ( 0,
          Messages.getString ( machinePanelSelected ? "MachinePanel.Error"//$NON-NLS-1$
              : "GrammarPanel.Error" ) );//$NON-NLS-1$
      panel.getJTabbedPaneConsole ().setTitleAt ( 1,
          Messages.getString ( machinePanelSelected ? "MachinePanel.Warning"//$NON-NLS-1$
              : "GrammarPanel.Warning" ) );//$NON-NLS-1$

      infoDialog = new InfoDialog (
          this.gui,
          Messages
              .getString ( machinePanelSelected ? "MainWindow.NoErrorNoWarningMachineCount"//$NON-NLS-1$
                  : "MainWindow.NoErrorNoWarningGrammarCount" ),//$NON-NLS-1$
          Messages
              .getString ( machinePanelSelected ? "MainWindow.NoErrorNoWarningMachine"//$NON-NLS-1$
                  : "MainWindow.NoErrorNoWarningGrammar" ) ); //$NON-NLS-1$
    }

    setStateConsole ( true );
    panel.setVisibleConsole ( true );

    infoDialog.show ();
    return false;
  }


  /**
   * Handle Auto Step Action in the Word Enter Mode
   * 
   * @param event
   */
  public final void handleWordAutoStep ( ItemEvent event )
  {
    EditorPanel panel = this.gui.getEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( ! ( panel instanceof MachinePanel ) )
    {
      throw new IllegalArgumentException ( "not a machine panel" ); //$NON-NLS-1$
    }
    MachinePanel machinePanel = ( MachinePanel ) panel;

    machinePanel.handleWordAutoStep ( event );
  }


  /**
   * Handle Next Step Action in the Word Enter Mode
   */
  public final void handleWordNextStep ()
  {
    EditorPanel panel = this.gui.getEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( ! ( panel instanceof MachinePanel ) )
    {
      throw new IllegalArgumentException ( "not a machine panel" ); //$NON-NLS-1$
    }
    MachinePanel machinePanel = ( MachinePanel ) panel;

    machinePanel.handleWordNextStep ();
  }


  /**
   * Handle Previous Step Action in the Word Enter Mode
   */
  public final void handleWordPreviousStep ()
  {
    EditorPanel panel = this.gui.getEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( ! ( panel instanceof MachinePanel ) )
    {
      throw new IllegalArgumentException ( "not a machine panel" ); //$NON-NLS-1$
    }
    MachinePanel machinePanel = ( MachinePanel ) panel;

    machinePanel.handleWordPreviousStep ();
  }


  /**
   * Handle Start Action in the Word Enter Mode
   */
  public final void handleWordStart ()
  {
    EditorPanel panel = this.gui.getEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( ! ( panel instanceof MachinePanel ) )
    {
      throw new IllegalArgumentException ( "not a machine panel" ); //$NON-NLS-1$
    }
    MachinePanel machinePanel = ( MachinePanel ) panel;

    if ( machinePanel.handleWordStart () )
    {
      setStateWordNavigation ( true );
    }

    // Item status
    setStateHistoryItem ();
  }


  /**
   * Handle Stop Action in the Word Enter Mode
   */
  public final void handleWordStop ()
  {
    EditorPanel panel = this.gui.getEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( ! ( panel instanceof MachinePanel ) )
    {
      throw new IllegalArgumentException ( "not a machine panel" ); //$NON-NLS-1$
    }
    MachinePanel machinePanel = ( MachinePanel ) panel;

    setStateWordNavigation ( false );

    machinePanel.handleWordStop ();

    // Item status
    setStateHistoryItem ();
  }


  /**
   * Returns the close active state.
   * 
   * @return The close active state.
   */
  public final boolean isCloseActiveState ()
  {
    return this.gui.getJMenuItemClose ().isEnabled ();
  }


  /**
   * Returns the close all active state.
   * 
   * @return The close all active state.
   */
  public final boolean isCloseAllActiveState ()
  {
    return this.gui.getJMenuItemCloseAll ().isEnabled ();
  }


  /**
   * Returns the new active state.
   * 
   * @return The new active state.
   */
  public final boolean isNewActiveState ()
  {
    return this.gui.getJMenuItemNew ().isEnabled ();
  }


  /**
   * Returns the save active state.
   * 
   * @return The save active state.
   */
  public final boolean isSaveActiveState ()
  {
    return this.gui.getJMenuItemSave ().isEnabled ();
  }


  /**
   * Returns the save as active state.
   * 
   * @return The save as active state.
   */
  public final boolean isSaveAsActiveState ()
  {
    return this.gui.getJMenuItemSaveAs ().isEnabled ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see LanguageChangedListener#languageChanged()
   */
  public final void languageChanged ()
  {
    // File
    MainWindow.this.gui.getJMenuFile ().setText (
        Messages.getString ( "MainWindow.File" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuFile ().setMnemonic (
        Messages.getString ( "MainWindow.FileMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // New
    MainWindow.this.gui.getJMenuItemNew ().setText (
        Messages.getString ( "MainWindow.New" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemNew ().setMnemonic (
        Messages.getString ( "MainWindow.NewMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJGTIToolBarButtonNew ().setToolTipText (
        Messages.getString ( "MainWindow.NewToolTip" ) ); //$NON-NLS-1$
    // Open
    MainWindow.this.gui.getJMenuItemOpen ().setText (
        Messages.getString ( "MainWindow.Open" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemOpen ().setMnemonic (
        Messages.getString ( "MainWindow.OpenMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJGTIToolBarButtonOpen ().setToolTipText (
        Messages.getString ( "MainWindow.OpenToolTip" ) ); //$NON-NLS-1$
    // Close
    MainWindow.this.gui.getJMenuItemClose ().setText (
        Messages.getString ( "MainWindow.Close" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemClose ().setMnemonic (
        Messages.getString ( "MainWindow.CloseMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // CloseAll
    MainWindow.this.gui.getJMenuItemCloseAll ().setText (
        Messages.getString ( "MainWindow.CloseAll" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemCloseAll ().setMnemonic (
        Messages.getString ( "MainWindow.CloseAllMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Save
    MainWindow.this.gui.getJMenuItemSave ().setText (
        Messages.getString ( "MainWindow.Save" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemSave ().setMnemonic (
        Messages.getString ( "MainWindow.SaveMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJGTIToolBarButtonSave ().setToolTipText (
        Messages.getString ( "MainWindow.SaveToolTip" ) ); //$NON-NLS-1$
    // SaveAs
    MainWindow.this.gui.getJMenuItemSaveAs ().setText (
        Messages.getString ( "MainWindow.SaveAs" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemSaveAs ().setMnemonic (
        Messages.getString ( "MainWindow.SaveAsMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJGTIToolBarButtonSaveAs ().setToolTipText (
        Messages.getString ( "MainWindow.SaveAsToolTip" ) ); //$NON-NLS-1$
    // SaveAll
    MainWindow.this.gui.getJMenuItemSaveAll ().setText (
        Messages.getString ( "MainWindow.SaveAll" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemSaveAll ().setMnemonic (
        Messages.getString ( "MainWindow.SaveAllMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Draft for
    MainWindow.this.gui.getJMenuDraft ().setText (
        Messages.getString ( "MainWindow.DraftFor" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuDraft ().setMnemonic (
        Messages.getString ( "MainWindow.DraftForMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemDFA ().setText (
        Messages.getString ( "MainWindow.DFA" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemNFA ().setText (
        Messages.getString ( "MainWindow.NFA" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemENFA ().setText (
        Messages.getString ( "MainWindow.ENFA" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemPDA ().setText (
        Messages.getString ( "MainWindow.PDA" ) ); //$NON-NLS-1$
    // RecentlyUsed
    MainWindow.this.gui.getJMenuRecentlyUsed ().setText (
        Messages.getString ( "MainWindow.RecentlyUsed" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuRecentlyUsed ().setMnemonic (
        Messages.getString ( "MainWindow.RecentlyUsedMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Quit
    MainWindow.this.gui.getJMenuItemQuit ().setText (
        Messages.getString ( "MainWindow.Quit" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemQuit ().setMnemonic (
        Messages.getString ( "MainWindow.QuitMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Edit
    MainWindow.this.gui.getJMenuEdit ().setText (
        Messages.getString ( "MainWindow.Edit" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuEdit ().setMnemonic (
        Messages.getString ( "MainWindow.EditMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Undo
    MainWindow.this.gui.getJMenuItemUndo ().setText (
        Messages.getString ( "MainWindow.Undo" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemUndo ().setMnemonic (
        Messages.getString ( "MainWindow.UndoMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Redo
    MainWindow.this.gui.getJMenuItemRedo ().setText (
        Messages.getString ( "MainWindow.Redo" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemRedo ().setMnemonic (
        Messages.getString ( "MainWindow.RedoMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Preferences
    MainWindow.this.gui.getJMenuItemPreferences ().setText (
        Messages.getString ( "MainWindow.Preferences" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemPreferences ().setMnemonic (
        Messages.getString ( "MainWindow.PreferencesMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // View
    MainWindow.this.gui.getJMenuView ().setText (
        Messages.getString ( "MainWindow.View" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuView ().setMnemonic (
        Messages.getString ( "MainWindow.ViewMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Console
    MainWindow.this.gui.getJCheckBoxMenuItemConsole ().setText (
        Messages.getString ( "MainWindow.Console" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJCheckBoxMenuItemConsole ().setMnemonic (
        Messages.getString ( "MainWindow.ConsoleMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Table
    MainWindow.this.gui.getJCheckBoxMenuItemTable ().setText (
        Messages.getString ( "MainWindow.Table" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJCheckBoxMenuItemTable ().setMnemonic (
        Messages.getString ( "MainWindow.TableMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Execute
    MainWindow.this.gui.getJMenuExecute ().setText (
        Messages.getString ( "MainWindow.Execute" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuExecute ().setMnemonic (
        Messages.getString ( "MainWindow.ExecuteMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Validate
    MainWindow.this.gui.getJMenuItemValidate ().setText (
        Messages.getString ( "MainWindow.Validate" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemValidate ().setMnemonic (
        Messages.getString ( "MainWindow.ValidateMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // EnterWord
    MainWindow.this.gui.getJMenuItemEnterWord ().setText (
        Messages.getString ( "MainWindow.EnterWord" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemEnterWord ().setMnemonic (
        Messages.getString ( "MainWindow.EnterWordMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // History
    MainWindow.this.gui.getJMenuItemHistory ().setText (
        Messages.getString ( "MainWindow.History" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemHistory ().setMnemonic (
        Messages.getString ( "MainWindow.HistoryMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // EditMachine
    MainWindow.this.gui.getJMenuItemEditMachine ().setText (
        Messages.getString ( "MainWindow.EditMachine" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemEditMachine ().setMnemonic (
        Messages.getString ( "MainWindow.EditMachineMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Extras
    MainWindow.this.gui.getJMenuExtras ().setText (
        Messages.getString ( "MainWindow.Extras" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuExtras ().setMnemonic (
        Messages.getString ( "MainWindow.ExtrasMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Exchange
    MainWindow.this.gui.getJMenuItemExchange ().setText (
        Messages.getString ( "MainWindow.Exchange" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemExchange ().setMnemonic (
        Messages.getString ( "MainWindow.ExchangeMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Help
    MainWindow.this.gui.getJMenuHelp ().setText (
        Messages.getString ( "MainWindow.Help" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuHelp ().setMnemonic (
        Messages.getString ( "MainWindow.HelpMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // About
    MainWindow.this.gui.getJMenuItemAbout ().setText (
        Messages.getString ( "MainWindow.About" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemAbout ().setMnemonic (
        Messages.getString ( "MainWindow.AboutMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Mouse
    MainWindow.this.gui.getJGTIToolBarToggleButtonMouse ().setToolTipText (
        Messages.getString ( "MachinePanel.Mouse" ) ); //$NON-NLS-1$
    // Add state
    MainWindow.this.gui.getJGTIToolBarToggleButtonAddState ().setToolTipText (
        Messages.getString ( "MachinePanel.AddState" ) ); //$NON-NLS-1$
    // Add transition
    MainWindow.this.gui.getJGTIToolBarToggleButtonAddTransition ()
        .setToolTipText ( Messages.getString ( "MachinePanel.AddTransition" ) ); //$NON-NLS-1$
    // Start state
    MainWindow.this.gui.getJGTIToolBarToggleButtonStartState ().setToolTipText (
        Messages.getString ( "MachinePanel.StartState" ) ); //$NON-NLS-1$
    // Final state
    MainWindow.this.gui.getJGTIToolBarToggleButtonFinalState ().setToolTipText (
        Messages.getString ( "MachinePanel.FinalState" ) ); //$NON-NLS-1$
    // Edit Document
    MainWindow.this.gui.getJGTIToolBarButtonEditDocument ().setToolTipText (
        Messages.getString ( "MachinePanel.EditDocument" ) ); //$NON-NLS-1$
    // Previous Step
    MainWindow.this.gui.getJGTIToolBarButtonPrevious ().setToolTipText (
        Messages.getString ( "MachinePanel.WordModePreviousStep" ) ); //$NON-NLS-1$
    // Start Word
    MainWindow.this.gui.getJGTIToolBarButtonStart ().setToolTipText (
        Messages.getString ( "MachinePanel.WordModeStart" ) ); //$NON-NLS-1$
    // Next Step
    MainWindow.this.gui.getJGTIToolBarButtonNextStep ().setToolTipText (
        Messages.getString ( "MachinePanel.WordModeNextStep" ) ); //$NON-NLS-1$
    // Auto Step
    MainWindow.this.gui.getJGTIToolBarToggleButtonAutoStep ().setToolTipText (
        Messages.getString ( "MachinePanel.WordModeAutoStep" ) ); //$NON-NLS-1$
    // Stop Word
    MainWindow.this.gui.getJGTIToolBarButtonStop ().setToolTipText (
        Messages.getString ( "MachinePanel.WordModeStop" ) ); //$NON-NLS-1$
    // Add production
    MainWindow.this.gui.getJGTIToolBarButtonAddProduction ().setToolTipText (
        Messages.getString ( "GrammarPanel.AddProduction" ) ); //$NON-NLS-1$
    // Edit production
    MainWindow.this.gui.getJGTIToolBarButtonEditProduction ().setToolTipText (
        Messages.getString ( "GrammarPanel.ProductionProperties" ) ); //$NON-NLS-1$
    // Delete production
    MainWindow.this.gui.getJGTIToolBarButtonDeleteProduction ().setToolTipText (
        Messages.getString ( "GrammarPanel.DeleteProduction" ) ); //$NON-NLS-1$
  }


  /**
   * Try to open the given file
   * 
   * @param file The file to open
   * @param addToRecentlyUsed Flag signals if file should be added to recently
   *          used files
   */
  public final void openFile ( File file, boolean addToRecentlyUsed )
  {
    // check if we already have an editor panel for the file
    for ( EditorPanel current : this.gui.getEditorPanelTabbedPane () )
    {
      if ( file.equals ( current.getFile () ) )
      {
        this.gui.getEditorPanelTabbedPane ().setSelectedEditorPanel ( current );

        // reorganize recently used files
        if ( addToRecentlyUsed )
        {
          RecentlyUsedMenuItem item = new RecentlyUsedMenuItem ( this, file );
          this.recentlyUsedFiles.remove ( item );
          this.recentlyUsedFiles.add ( 0, item );
          if ( this.recentlyUsedFiles.size () > 10 )
          {
            this.recentlyUsedFiles.remove ( 10 );
          }
          organizeRecentlyUsedFilesMenu ();
        }

        return;
      }
    }
    try
    {
      DefaultModel element = ( DefaultModel ) Storage.getInstance ().load (
          file );

      if ( element instanceof DefaultMachineModel )
      {
        DefaultMachineModel model = ( DefaultMachineModel ) element;
        EditorPanel newEditorPanel = new MachinePanel ( this.gui, model, file );

        this.gui.getEditorPanelTabbedPane ().addEditorPanel ( newEditorPanel );
        newEditorPanel
            .addModifyStatusChangedListener ( this.modifyStatusChangedListener );
        this.gui.getEditorPanelTabbedPane ().setSelectedEditorPanel (
            newEditorPanel );
        this.gui.getEditorPanelTabbedPane ().setEditorPanelTitle (
            newEditorPanel, file.getName () );

        setStateGeneral ( true );
        setStateValidate ( true );
        setStateEditItem ( true );
      }
      else if ( element instanceof DefaultGrammarModel )
      {
        DefaultGrammarModel model = ( DefaultGrammarModel ) element;

        EditorPanel newEditorPanel = new GrammarPanel ( this.gui, model, file );

        this.gui.getEditorPanelTabbedPane ().addEditorPanel ( newEditorPanel );
        newEditorPanel
            .addModifyStatusChangedListener ( this.modifyStatusChangedListener );
        this.gui.getEditorPanelTabbedPane ().setSelectedEditorPanel (
            newEditorPanel );
        this.gui.getEditorPanelTabbedPane ().setEditorPanelTitle (
            newEditorPanel, file.getName () );

        setStateGeneral ( true );
      }
      else
      {
        throw new RuntimeException ( "not supported element" ); //$NON-NLS-1$
      }

      // reorganize recently used files
      if ( addToRecentlyUsed )
      {
        RecentlyUsedMenuItem item = new RecentlyUsedMenuItem ( this, file );
        this.recentlyUsedFiles.remove ( item );
        this.recentlyUsedFiles.add ( 0, item );
        if ( this.recentlyUsedFiles.size () > 10 )
        {
          this.recentlyUsedFiles.remove ( 10 );
        }
        organizeRecentlyUsedFilesMenu ();
      }
    }
    catch ( StoreException exc )
    {
      InfoDialog infoDialog = new InfoDialog ( this.gui, exc.getMessage (),
          Messages.getString ( "MainWindow.ErrorLoad" ) ); //$NON-NLS-1$
      infoDialog.show ();
    }
    PreferenceManager.getInstance ().setWorkingPath (
        file.getParentFile ().getAbsolutePath () );
  }


  /**
   * Organize the recently used files in the menu
   */
  private final void organizeRecentlyUsedFilesMenu ()
  {
    ArrayList < RecentlyUsedMenuItem > notExistingFiles = new ArrayList < RecentlyUsedMenuItem > ();

    this.gui.getJMenuRecentlyUsed ().removeAll ();

    for ( RecentlyUsedMenuItem item : this.recentlyUsedFiles )
    {
      if ( item.getFile ().exists () )
      {
        this.gui.getJMenuRecentlyUsed ().add ( item );
      }
      else
      {
        notExistingFiles.add ( item );
      }
    }

    this.recentlyUsedFiles.removeAll ( notExistingFiles );

    setStateRecentlyUsed ( this.recentlyUsedFiles.size () > 0 );
  }


  /**
   * Open all files which was open at last session
   */
  public final void restoreOpenFiles ()
  {
    for ( File file : PreferenceManager.getInstance ().getOpenedFilesItem ()
        .getFiles () )
    {
      openFile ( file, false );
    }
    File activeFile = PreferenceManager.getInstance ().getOpenedFilesItem ()
        .getActiveFile ();
    if ( activeFile != null )
    {
      for ( EditorPanel current : this.gui.getEditorPanelTabbedPane () )
      {
        if ( current.getFile ().getAbsolutePath ().equals (
            activeFile.getAbsolutePath () ) )
        {
          this.gui.getEditorPanelTabbedPane ()
              .setSelectedEditorPanel ( current );
          break;
        }
      }
    }
  }


  /**
   * Sets the auto step state.
   * 
   * @param state The new state.
   */
  public final void setStateAutoStep ( boolean state )
  {
    this.gui.getJGTIToolBarToggleButtonAutoStep ().setSelected ( state );
  }


  /**
   * Sets the button mouse state.
   * 
   * @param state The new state.
   */
  public final void setStateButtonMouse ( boolean state )
  {
    this.gui.getJGTIToolBarToggleButtonMouse ().setSelected ( state );
  }


  /**
   * Sets the console state for items and buttons.
   * 
   * @param state The new state.
   */
  public final void setStateConsole ( boolean state )
  {
    this.gui.getJCheckBoxMenuItemConsole ().setSelected ( state );
  }


  /**
   * Sets the edit document state for items and buttons.
   * 
   * @param state The new state.
   */
  public final void setStateEditDocument ( boolean state )
  {
    this.gui.getJGTIToolBarButtonEditDocument ().setEnabled ( state );
  }


  /**
   * Sets the state of the recently used items.
   * 
   * @param state The new state.
   */
  private final void setStateRecentlyUsed ( boolean state )
  {
    this.gui.getJMenuRecentlyUsed ().setEnabled ( state );
  }


  /**
   * Sets the state of the draft for items.
   * 
   * @param state The new state.
   */
  private final void setStateDraftForItems ( boolean state )
  {
    this.gui.getJMenuItemDFA ().setEnabled ( state );
    this.gui.getJMenuItemNFA ().setEnabled ( state );
    this.gui.getJMenuItemPDA ().setEnabled ( state );
    this.gui.getJMenuItemENFA ().setEnabled ( state );
    this.gui.getJMenuItemRG ().setEnabled ( !state );
    this.gui.getJMenuItemCFG ().setEnabled ( !state );
  }


  /**
   * Sets the state of the edit machine toolbar items.
   * 
   * @param state The new state.
   */
  private final void setStateEditItem ( boolean state )
  {
    this.gui.getJGTIToolBarToggleButtonAddState ().setEnabled ( state );
    this.gui.getJGTIToolBarToggleButtonAddTransition ().setEnabled ( state );
    this.gui.getJGTIToolBarToggleButtonFinalState ().setEnabled ( state );
    this.gui.getJGTIToolBarToggleButtonMouse ().setEnabled ( state );
    this.gui.getJGTIToolBarToggleButtonStartState ().setEnabled ( state );
    this.gui.getJGTIToolBarButtonEditDocument ().setEnabled ( state );
  }


  /**
   * Sets the state of the enter word menu item.
   * 
   * @param state The new state.
   */
  private final void setStateMenuEnterWord ( boolean state )
  {
    this.gui.getJMenuItemEnterWord ().setEnabled ( state );
  }


  /**
   * Sets the state of the enter word toolbar items.
   * 
   * @param state The new state.
   */
  private final void setStateEnterWord ( boolean state )
  {
    this.gui.getJGTIToolBarButtonStart ().setEnabled ( state );
    this.gui.getJGTIToolBarButtonNextStep ().setEnabled ( state );
    this.gui.getJGTIToolBarButtonPrevious ().setEnabled ( state );
    this.gui.getJGTIToolBarToggleButtonAutoStep ().setEnabled ( state );
    this.gui.getJGTIToolBarButtonStop ().setEnabled ( state );
  }


  /**
   * Sets general states for items and buttons.
   * 
   * @param state The new state.
   */
  private final void setStateGeneral ( boolean state )
  {
    // SaveAs
    this.gui.getJGTIToolBarButtonSaveAs ().setEnabled ( state );
    this.gui.getJMenuItemSaveAs ().setEnabled ( state );

    // SaveAll
    this.gui.getJMenuItemSaveAll ().setEnabled ( state );

    // Close
    this.gui.getJMenuItemClose ().setEnabled ( state );

    // CloseAll
    this.gui.getJMenuItemCloseAll ().setEnabled ( state );

    // View
    this.gui.getJCheckBoxMenuItemConsole ().setEnabled ( state );
    this.gui.getJCheckBoxMenuItemTable ().setEnabled ( state );

    // Enter word
    this.gui.getJMenuItemEnterWord ().setEnabled ( state );

    // Undo
    this.gui.getJMenuItemUndo ().setEnabled ( false );
    this.gui.getJGTIToolBarButtonUndo ().setEnabled ( false );

    // Redo
    this.gui.getJMenuItemRedo ().setEnabled ( false );
    this.gui.getJGTIToolBarButtonRedo ().setEnabled ( false );

    // Edit document
    this.gui.getJGTIToolBarButtonEditDocument ().setEnabled ( state );

    // Draft for
    this.gui.getJMenuDraft ().setEnabled ( state );
  }


  /**
   * Shows or hides the buttons needed in the {@link GrammarPanel}.
   * 
   * @param state The visible state of the buttons.
   */
  private void setStateGrammarButtons ( boolean state )
  {
    this.gui.getJGTIToolBarButtonAddProduction ().setVisible ( state );
    this.gui.getJGTIToolBarButtonEditProduction ().setVisible ( state );
    this.gui.getJGTIToolBarButtonDeleteProduction ().setVisible ( state );
  }


  /**
   * Sets the history item state.
   */
  private final void setStateHistoryItem ()
  {
    // MachinePanel
    if ( this.gui.getEditorPanelTabbedPane ().getSelectedEditorPanel () instanceof MachinePanel )
    {
      MachinePanel machinePanel = ( MachinePanel ) this.gui
          .getEditorPanelTabbedPane ().getSelectedEditorPanel ();

      this.gui.getJMenuItemHistory ().setEnabled (
          machinePanel.isWordNavigation () );
    }
    // GrammarPanel
    else if ( this.gui.getEditorPanelTabbedPane ().getSelectedEditorPanel () instanceof GrammarPanel )
    {
      this.gui.getJMenuItemHistory ().setEnabled ( false );
    }
    // No selected editor
    else
    {
      this.gui.getJMenuItemHistory ().setEnabled ( false );
    }
  }


  /**
   * Shows or hides the buttons needed in the {@link MachinePanel}.
   * 
   * @param state The visible state of the buttons.
   */
  private void setStateMachineButtons ( boolean state )
  {
    this.gui.getJSeparatorNavigation ().setVisible ( state );
    this.gui.getJGTIToolBarToggleButtonMouse ().setVisible ( state );
    this.gui.getJGTIToolBarToggleButtonAddState ().setVisible ( state );
    this.gui.getJGTIToolBarToggleButtonStartState ().setVisible ( state );
    this.gui.getJGTIToolBarToggleButtonFinalState ().setVisible ( state );
    this.gui.getJGTIToolBarToggleButtonAddTransition ().setVisible ( state );

    this.gui.getJGTIToolBarButtonStart ().setVisible ( state );
    this.gui.getJGTIToolBarButtonPrevious ().setVisible ( state );
    this.gui.getJGTIToolBarButtonNextStep ().setVisible ( state );
    this.gui.getJGTIToolBarToggleButtonAutoStep ().setVisible ( state );
    this.gui.getJGTIToolBarButtonStop ().setVisible ( state );
  }


  /**
   * Sets the redo state for items and buttons.
   * 
   * @param state The new state.
   */
  public final void setStateRedo ( boolean state )
  {
    this.gui.getJMenuItemRedo ().setEnabled ( state );
    this.gui.getJGTIToolBarButtonRedo ().setEnabled ( state );
  }


  /**
   * Sets the state of the save button and item.
   */
  private final void setStateSave ()
  {
    boolean state = false;
    EditorPanel panel = this.gui.getEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( panel != null )
    {
      state = panel.isModified ();
    }
    setStateSave ( state );
  }


  /**
   * Sets the state of the save button and item.
   * 
   * @param state The new state of the save button.
   */
  private final void setStateSave ( boolean state )
  {
    logger.debug ( "setSaveState", "set save status to " + Messages.QUOTE //$NON-NLS-1$//$NON-NLS-2$
        + state + Messages.QUOTE );

    EditorPanel panel = this.gui.getEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( panel != null )
    {
      if ( state )
      {
        this.gui.getEditorPanelTabbedPane ().setEditorPanelTitle ( panel, "*" //$NON-NLS-1$
            + panel.getName () );
      }
      else
      {
        this.gui.getEditorPanelTabbedPane ().setEditorPanelTitle ( panel,
            panel.getName () );
      }
    }
    else if ( state == true )
    {
      throw new IllegalArgumentException ( "save button error" ); //$NON-NLS-1$
    }

    this.gui.getJGTIToolBarButtonSave ().setEnabled ( state );
    this.gui.getJMenuItemSave ().setEnabled ( state );
  }


  /**
   * Sets the redo state for items and buttons.
   * 
   * @param state The new state.
   */
  public final void setStateUndo ( boolean state )
  {
    this.gui.getJMenuItemUndo ().setEnabled ( state );
    this.gui.getJGTIToolBarButtonUndo ().setEnabled ( state );
  }


  /**
   * Sets the validate state for items and buttons.
   * 
   * @param state The new state.
   */
  public final void setStateValidate ( boolean state )
  {
    this.gui.getJMenuItemValidate ().setEnabled ( state );
  }


  /**
   * Sets the state of the word navigation items.
   * 
   * @param state The new state.
   */
  public final void setStateWordNavigation ( boolean state )
  {
    this.gui.getJGTIToolBarButtonStart ().setEnabled ( !state );
    this.gui.getJGTIToolBarButtonNextStep ().setEnabled ( state );
    this.gui.getJGTIToolBarButtonPrevious ().setEnabled ( state );
    this.gui.getJGTIToolBarToggleButtonAutoStep ().setEnabled ( state );
    this.gui.getJGTIToolBarButtonStop ().setEnabled ( state );
  }


  /**
   * Handle convert to action performed.
   * 
   * @param type The {@link MachineType} to convert to.
   */
  public void handleConvertTo ( MachineType type )
  {
    EditorPanel panel = this.gui.getEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    
    // TODO remove if statement when converter for machines are ready
    if ( panel.getConverter () != null )
    {
      panel.getConverter ().convert (type);
    }
  }


  /**
   * Handle auto layout action performed.
   */
  public void doAutoLayout ()
  {
    EditorPanel panel = this.gui.getEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( panel instanceof MachinePanel )
    {
      MachinePanel machinePanel = ( MachinePanel ) panel;
      new LayoutManager ( machinePanel.getModel () ).doLayout ();
    }
  }
}
