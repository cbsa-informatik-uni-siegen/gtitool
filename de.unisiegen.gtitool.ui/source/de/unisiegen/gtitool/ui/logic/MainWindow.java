package de.unisiegen.gtitool.ui.logic;


import java.awt.Frame;
import java.awt.event.ItemEvent;
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
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.preferences.item.OpenedFilesItem;
import de.unisiegen.gtitool.ui.preferences.item.RecentlyUsedFilesItem;
import de.unisiegen.gtitool.ui.storage.Storage;


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
    // Setting the default states
    setGeneralStates ( false );
    // Save
    setSaveState ( false );
    // Copy
    // Validate
    this.gui.jMenuItemValidate.setEnabled ( false );
    // EnterWord
    this.gui.jMenuItemEnterWord.setEnabled ( false );
    // Edit Machine
    this.gui.jMenuItemEditMachine.setEnabled ( false );
    // Preferences
    this.gui.jMenuItemPreferences.setEnabled ( true );
    // RecentlyUsed
    this.gui.jMenuRecentlyUsed.setEnabled ( false );
    // Draft for
    this.gui.jMenuDraft.setEnabled ( false );

    // // Toolbar items
    // setToolBarEditItemState ( false );
    // setToolBarEnterWordItemState ( false );
    //
    // this.gui.jGTIToolBarButtonNextStep.setEnabled ( false );
    // this.gui.jGTIToolBarButtonPrevious.setEnabled ( false );
    // this.gui.jGTIToolBarToggleButtonAutoStep.setEnabled ( false );
    // this.gui.jGTIToolBarButtonStop.setEnabled ( false );

    activateGrammarButtons ( false );
    activateMachineButtons ( false );
    this.gui.jGTIToolBarButtonEditDocument.setEnabled ( false );

    // Console and table visibility
    this.gui.jCheckBoxMenuItemConsole.setSelected ( PreferenceManager
        .getInstance ().getVisibleConsole () );
    this.gui.jCheckBoxMenuItemTable.setSelected ( PreferenceManager
        .getInstance ().getVisibleTable () );

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
        setSaveState ( modified );
      }
    };
  }


  /**
   * Show or hide the buttons needed in the GrammarPanel
   * 
   * @param state the visible state of the buttons
   */
  private void activateGrammarButtons ( boolean state )
  {
    this.gui.jGTIToolBarButtonAddProduction.setVisible ( state );
    this.gui.jGTIToolBarButtonEditProduction.setVisible ( state );
    this.gui.jGTIToolBarButtonDeleteProduction.setVisible ( state );
  }


  /**
   * Show or hide the buttons needed in the MachinePanel
   * 
   * @param state the visible state of the buttons
   */
  private void activateMachineButtons ( boolean state )
  {
    this.gui.jSeparatorNavigation.setVisible ( state );
    this.gui.jGTIToolBarToggleButtonMouse.setVisible ( state );
    this.gui.jGTIToolBarToggleButtonAddState.setVisible ( state );
    this.gui.jGTIToolBarToggleButtonStartState.setVisible ( state );
    this.gui.jGTIToolBarToggleButtonFinalState.setVisible ( state );
    this.gui.jGTIToolBarToggleButtonAddTransition.setVisible ( state );

    this.gui.jGTIToolBarButtonStart.setVisible ( state );
    this.gui.jGTIToolBarButtonPrevious.setVisible ( state );
    this.gui.jGTIToolBarButtonNextStep.setVisible ( state );
    this.gui.jGTIToolBarToggleButtonAutoStep.setVisible ( state );
    this.gui.jGTIToolBarButtonStop.setVisible ( state );
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
  public void handleAddProduction ()
  {
    EditorPanel panel = this.gui.editorPanelTabbedPane
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
    this.gui.jGTIToolBarToggleButtonAutoStep.setSelected ( false );
  }


  /**
   * Closes the selected {@link EditorPanel}.
   * 
   * @param panel The {@link EditorPanel} to be saved
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

    this.gui.editorPanelTabbedPane.removeSelectedEditorPanel ();
    // All editor panels are closed
    if ( this.gui.editorPanelTabbedPane.getSelectedEditorPanel () == null )
    {
      activateGrammarButtons ( false );
      activateMachineButtons ( false );
      this.gui.jGTIToolBarButtonEditDocument.setEnabled ( false );
    }
    return true;
  }


  /**
   * Handle the close all files event
   */
  public final void handleCloseAll ()
  {
    for ( EditorPanel current : this.gui.editorPanelTabbedPane )
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
    EditorPanel panel = this.gui.editorPanelTabbedPane
        .getSelectedEditorPanel ();
    if ( PreferenceManager.getInstance ().getVisibleConsole () != this.gui.jCheckBoxMenuItemConsole
        .getState () )
    {
      panel.setVisibleConsole ( this.gui.jCheckBoxMenuItemConsole.getState () );
      PreferenceManager.getInstance ().setVisibleConsole (
          this.gui.jCheckBoxMenuItemConsole.getState () );
    }
  }


  /**
   * Handle delete production button pressed.
   */
  public void handleDeleteProduction ()
  {
    EditorPanel panel = this.gui.editorPanelTabbedPane
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
      DefaultGrammarModel model = new DefaultGrammarModel (
          this.gui.editorPanelTabbedPane.getSelectedEditorPanel ().getModel ()
              .getElement (), type.toString () );
      EditorPanel newEditorPanel = new GrammarPanel ( this.gui, model, null );

      TreeSet < String > nameList = new TreeSet < String > ();
      int count = 0;
      for ( EditorPanel current : this.gui.editorPanelTabbedPane )
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
      this.gui.editorPanelTabbedPane.addEditorPanel ( newEditorPanel );
      newEditorPanel
          .addModifyStatusChangedListener ( this.modifyStatusChangedListener );
      this.gui.editorPanelTabbedPane.setSelectedEditorPanel ( newEditorPanel );
      setGeneralStates ( true );
      this.gui.jMenuItemValidate.setEnabled ( true );

      // toolbar items
      setToolBarEditItemState ( true );

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
      DefaultMachineModel model = new DefaultMachineModel (
          this.gui.editorPanelTabbedPane.getSelectedEditorPanel ().getModel ()
              .getElement (), type.toString () );
      EditorPanel newEditorPanel = new MachinePanel ( this.gui, model, null );

      TreeSet < String > nameList = new TreeSet < String > ();
      int count = 0;
      for ( EditorPanel current : this.gui.editorPanelTabbedPane )
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
      this.gui.editorPanelTabbedPane.addEditorPanel ( newEditorPanel );
      newEditorPanel
          .addModifyStatusChangedListener ( this.modifyStatusChangedListener );
      this.gui.editorPanelTabbedPane.setSelectedEditorPanel ( newEditorPanel );
      setGeneralStates ( true );
      this.gui.jMenuItemValidate.setEnabled ( true );

      // toolbar items
      setToolBarEditItemState ( true );

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
    EditorPanel panel = this.gui.editorPanelTabbedPane
        .getSelectedEditorPanel ();
    panel.handleToolbarEditDocument ();
  }


  /**
   * Handle Edit Machine button pressed
   */
  public final void handleEditMachine ()
  {
    EditorPanel panel = this.gui.editorPanelTabbedPane
        .getSelectedEditorPanel ();
    if ( ! ( panel instanceof MachinePanel ) )
    {
      throw new IllegalArgumentException ( "not a machine panel" ); //$NON-NLS-1$
    }

    setToolBarEditItemState ( true );
    setToolBarEnterWordItemState ( false );
    MachinePanel machinePanel = ( MachinePanel ) panel;
    machinePanel.handleEditMachine ();
    machinePanel.setVisibleConsole ( this.gui.jCheckBoxMenuItemConsole
        .getState () );
    this.gui.jCheckBoxMenuItemConsole.setEnabled ( true );
    this.gui.jMenuItemEnterWord.setEnabled ( true );
    this.gui.jMenuItemEditMachine.setEnabled ( false );
    this.gui.jMenuItemValidate.setEnabled ( true );
  }


  /**
   * Handle edit production button pressed.
   */
  public void handleEditProduction ()
  {
    EditorPanel panel = this.gui.editorPanelTabbedPane
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
    EditorPanel panel = this.gui.editorPanelTabbedPane
        .getSelectedEditorPanel ();
    if ( ! ( panel instanceof MachinePanel ) )
    {
      throw new IllegalArgumentException ( "not a machine panel" ); //$NON-NLS-1$
    }
    MachinePanel machinePanel = ( MachinePanel ) panel;

    if ( handleValidate ( false ) )
    {
      setToolBarEditItemState ( false );
      this.gui.jGTIToolBarButtonStart.setEnabled ( true );
      machinePanel.handleEnterWord ();
      this.gui.jCheckBoxMenuItemConsole.setEnabled ( false );
      machinePanel.setVisibleConsole ( false );
      this.gui.jMenuItemEnterWord.setEnabled ( false );
      this.gui.jMenuItemEditMachine.setEnabled ( true );
      this.gui.jMenuItemValidate.setEnabled ( false );
    }
  }


  /**
   * Handles the {@link Exchange}.
   */
  public final void handleExchange ()
  {
    EditorPanel panel = this.gui.editorPanelTabbedPane
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
    if ( this.gui.editorPanelTabbedPane.getSelectedEditorPanel () instanceof MachinePanel )
    {
      MachinePanel machinePanel = ( MachinePanel ) this.gui.editorPanelTabbedPane
          .getSelectedEditorPanel ();
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
      for ( EditorPanel current : this.gui.editorPanelTabbedPane )
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
      this.gui.editorPanelTabbedPane.addEditorPanel ( newEditorPanel );
      newEditorPanel
          .addModifyStatusChangedListener ( this.modifyStatusChangedListener );
      this.gui.editorPanelTabbedPane.setSelectedEditorPanel ( newEditorPanel );

      setGeneralStates ( true );
      this.gui.jMenuItemValidate.setEnabled ( true );

      // toolbar items
      setToolBarEditItemState ( true );
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
      for ( EditorPanel current : this.gui.editorPanelTabbedPane )
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
      this.gui.editorPanelTabbedPane.addEditorPanel ( newEditorPanel );
      newEditorPanel
          .addModifyStatusChangedListener ( this.modifyStatusChangedListener );
      this.gui.editorPanelTabbedPane.setSelectedEditorPanel ( newEditorPanel );

      setGeneralStates ( true );
      this.gui.jMenuItemValidate.setEnabled ( true );

      // toolbar items
      setToolBarEditItemState ( true );
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
    File activeFile = this.gui.editorPanelTabbedPane.getSelectedEditorPanel () == null ? null
        : this.gui.editorPanelTabbedPane.getSelectedEditorPanel ().getFile ();

    // Opened file
    ArrayList < File > openedFiles = new ArrayList < File > ();
    for ( EditorPanel current : this.gui.editorPanelTabbedPane )
    {
      if ( current.getFile () != null )
      {
        openedFiles.add ( current.getFile () );
      }
    }
    // Close the tabs
    for ( int i = this.gui.editorPanelTabbedPane.getComponentCount () - 1 ; i >= 0 ; i-- )
    {
      EditorPanel current = this.gui.editorPanelTabbedPane.getEditorPanel ( i );
      if ( current.isModified () )
      {
        this.gui.editorPanelTabbedPane.setSelectedEditorPanel ( current );

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
            this.gui.editorPanelTabbedPane.setEditorPanelTitle ( current, file
                .getName () );
          }
        }
        else if ( confirmDialog.isCanceled () )
        {
          return;
        }
      }
      this.gui.editorPanelTabbedPane.removeEditorPanel ( current );
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
    EditorPanel panel = this.gui.editorPanelTabbedPane
        .getSelectedEditorPanel ();
    if ( panel != null )
    {
      panel.handleRedo ();
    }
  }


  /**
   * Handle the save file event
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

      for ( EditorPanel current : this.gui.editorPanelTabbedPane )
      {
        if ( ( !current.equals ( this.gui.editorPanelTabbedPane
            .getSelectedEditorPanel () ) && file.equals ( current.getFile () ) ) )
        {
          this.gui.editorPanelTabbedPane.removeEditorPanel ( current );
        }
      }
      this.gui.editorPanelTabbedPane.setEditorPanelTitle ( panel, file
          .getName () );
    }
  }


  /**
   * Handle the save all files event
   */
  public final void handleSaveAll ()
  {
    EditorPanel active = this.gui.editorPanelTabbedPane
        .getSelectedEditorPanel ();
    for ( EditorPanel current : this.gui.editorPanelTabbedPane )
    {
      this.gui.editorPanelTabbedPane.setSelectedEditorPanel ( current );
      handleSave ( current );
    }
    this.gui.editorPanelTabbedPane.setSelectedEditorPanel ( active );
  }


  /**
   * Handle the save as event.
   */
  public final void handleSaveAs ()
  {
    EditorPanel panel = this.gui.editorPanelTabbedPane
        .getSelectedEditorPanel ();
    File file = panel.handleSaveAs ();
    if ( file != null )
    {
      RecentlyUsedMenuItem item = new RecentlyUsedMenuItem ( this, file );
      this.recentlyUsedFiles.remove ( item );
      this.recentlyUsedFiles.add ( 0, item );
      organizeRecentlyUsedFilesMenu ();
      for ( EditorPanel current : this.gui.editorPanelTabbedPane )
      {
        if ( ( !current.equals ( this.gui.editorPanelTabbedPane
            .getSelectedEditorPanel () ) && file.equals ( current.getFile () ) ) )
        {
          this.gui.editorPanelTabbedPane.removeEditorPanel ( current );
        }
      }
      this.gui.editorPanelTabbedPane.setEditorPanelTitle ( panel, file
          .getName () );
    }
  }


  /**
   * Handle TabbedPane state changed event.
   */
  public final void handleTabbedPaneStateChanged ()
  {
    EditorPanel panel = this.gui.editorPanelTabbedPane
        .getSelectedEditorPanel ();
    if ( panel != null )
    {
      activateMachineButtons ( panel instanceof MachinePanel );
      activateGrammarButtons ( panel instanceof GrammarPanel );
      // MachinePanel
      if ( panel instanceof MachinePanel )
      {

        this.gui.jMenuItemDFA.setEnabled ( true );
        this.gui.jMenuItemNFA.setEnabled ( true );
        this.gui.jMenuItemPDA.setEnabled ( true );
        this.gui.jMenuItemENFA.setEnabled ( true );
        this.gui.jMenuItemRG.setEnabled ( false );
        this.gui.jMenuItemCFG.setEnabled ( false );

        MachinePanel machinePanel = ( MachinePanel ) panel;
        this.gui.jCheckBoxMenuItemConsole.setEnabled ( !machinePanel
            .isWordEnterMode () );
        machinePanel.setVisibleConsole ( this.gui.jCheckBoxMenuItemConsole
            .getState ()
            && !machinePanel.isWordEnterMode () );
        machinePanel.setVisibleTable ( this.gui.jCheckBoxMenuItemTable
            .getState () );
        setToolBarEditItemState ( !machinePanel.isWordEnterMode () );
        setToolBarEnterWordItemState ( machinePanel.isWordEnterMode () );
        this.gui.jMenuItemEditMachine.setEnabled ( machinePanel
            .isWordEnterMode () );
        this.gui.jMenuItemValidate.setEnabled ( !machinePanel
            .isWordEnterMode () );
        this.gui.jMenuItemEnterWord.setEnabled ( !machinePanel
            .isWordEnterMode () );
        this.gui.jCheckBoxMenuItemTable.setEnabled ( true );
        this.gui.jMenuItemEnterWord.setEnabled ( true );

        // Set the status of the word navigation icons
        this.gui.jGTIToolBarButtonStart.setEnabled ( machinePanel
            .isWordEnterMode ()
            && !machinePanel.isWordNavigation () );
        this.gui.jGTIToolBarButtonPrevious.setEnabled ( machinePanel
            .isWordNavigation () );
        this.gui.jGTIToolBarButtonNextStep.setEnabled ( machinePanel
            .isWordNavigation () );
        this.gui.jGTIToolBarToggleButtonAutoStep.setEnabled ( machinePanel
            .isWordNavigation () );
        this.gui.jGTIToolBarButtonStop.setEnabled ( machinePanel
            .isWordNavigation () );

      }
      // Grammar Panel
      else
      {
        this.gui.jCheckBoxMenuItemTable.setEnabled ( false );
        panel
            .setVisibleConsole ( this.gui.jCheckBoxMenuItemConsole.getState () );
        this.gui.jMenuItemEnterWord.setEnabled ( false );
        this.gui.jMenuItemDFA.setEnabled ( false );
        this.gui.jMenuItemNFA.setEnabled ( false );
        this.gui.jMenuItemPDA.setEnabled ( false );
        this.gui.jMenuItemENFA.setEnabled ( false );
        this.gui.jMenuItemRG.setEnabled ( true );
        this.gui.jMenuItemCFG.setEnabled ( true );
      }
      // Undo
      this.gui.jMenuItemUndo.setEnabled ( panel.isUndoAble () );
      this.gui.jGTIToolBarButtonUndo.setEnabled ( panel.isUndoAble () );
      // Redo
      this.gui.jMenuItemRedo.setEnabled ( panel.isRedoAble () );
      this.gui.jGTIToolBarButtonRedo.setEnabled ( panel.isRedoAble () );
    }
    // Save status
    setSaveState ();
  }


  /**
   * Handles table state changes.
   */
  public final void handleTableStateChanged ()
  {
    EditorPanel panel = this.gui.editorPanelTabbedPane
        .getSelectedEditorPanel ();
    if ( ( panel instanceof MachinePanel ) )
    {
      MachinePanel machinePanel = ( MachinePanel ) panel;

      if ( PreferenceManager.getInstance ().getVisibleTable () != this.gui.jCheckBoxMenuItemTable
          .getState () )
      {
        PreferenceManager.getInstance ().setVisibleTable (
            this.gui.jCheckBoxMenuItemTable.getState () );
        machinePanel.setVisibleTable ( this.gui.jCheckBoxMenuItemTable
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
    for ( EditorPanel panel : this.gui.editorPanelTabbedPane )
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
    for ( EditorPanel panel : this.gui.editorPanelTabbedPane )
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
    for ( EditorPanel panel : this.gui.editorPanelTabbedPane )
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
    for ( EditorPanel panel : this.gui.editorPanelTabbedPane )
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
    for ( EditorPanel panel : this.gui.editorPanelTabbedPane )
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
    EditorPanel panel = this.gui.editorPanelTabbedPane
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
    EditorPanel panel = this.gui.editorPanelTabbedPane
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
    if ( !showDialogIfWarning && errorCount == 0 )
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
      if ( errorCount == 1 && warningCount == 1 )
      {
        message = Messages
            .getString ( machinePanelSelected ? "MainWindow.ErrorWarningMachineCount0" //$NON-NLS-1$
                : "MainWindow.ErrorWarningGrammarCount0" ); //$NON-NLS-1$
      }
      else if ( errorCount == 1 && warningCount > 1 )
      {
        message = Messages.getString (
            machinePanelSelected ? "MainWindow.ErrorWarningMachineCount1" //$NON-NLS-1$
                : "MainWindow.ErrorWarningGrammarCount1", false, String //$NON-NLS-1$
                .valueOf ( warningCount ) );
      }
      else if ( errorCount > 1 && warningCount == 1 )
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

    this.gui.jCheckBoxMenuItemConsole.setSelected ( true );
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
    EditorPanel panel = this.gui.editorPanelTabbedPane
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
    EditorPanel panel = this.gui.editorPanelTabbedPane
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
    EditorPanel panel = this.gui.editorPanelTabbedPane
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
    EditorPanel panel = this.gui.editorPanelTabbedPane
        .getSelectedEditorPanel ();
    if ( ! ( panel instanceof MachinePanel ) )
    {
      throw new IllegalArgumentException ( "not a machine panel" ); //$NON-NLS-1$
    }
    MachinePanel machinePanel = ( MachinePanel ) panel;

    if ( machinePanel.handleWordStart () )
    {
      this.gui.jGTIToolBarButtonStart.setEnabled ( false );
      this.gui.jGTIToolBarButtonNextStep.setEnabled ( true );
      this.gui.jGTIToolBarButtonPrevious.setEnabled ( true );
      this.gui.jGTIToolBarToggleButtonAutoStep.setEnabled ( true );
      this.gui.jGTIToolBarButtonStop.setEnabled ( true );
    }
  }


  /**
   * Handle Stop Action in the Word Enter Mode
   */
  public final void handleWordStop ()
  {
    EditorPanel panel = this.gui.editorPanelTabbedPane
        .getSelectedEditorPanel ();
    if ( ! ( panel instanceof MachinePanel ) )
    {
      throw new IllegalArgumentException ( "not a machine panel" ); //$NON-NLS-1$
    }
    MachinePanel machinePanel = ( MachinePanel ) panel;

    this.gui.jGTIToolBarButtonStart.setEnabled ( true );
    this.gui.jGTIToolBarButtonNextStep.setEnabled ( false );
    this.gui.jGTIToolBarButtonPrevious.setEnabled ( false );
    this.gui.jGTIToolBarToggleButtonAutoStep.setEnabled ( false );
    this.gui.jGTIToolBarButtonStop.setEnabled ( false );
    machinePanel.handleWordStop ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see LanguageChangedListener#languageChanged()
   */
  public final void languageChanged ()
  {
    // File
    MainWindow.this.gui.jMenuFile.setText ( Messages
        .getString ( "MainWindow.File" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuFile.setMnemonic ( Messages.getString (
        "MainWindow.FileMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // New
    MainWindow.this.gui.jMenuItemNew.setText ( Messages
        .getString ( "MainWindow.New" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuItemNew.setMnemonic ( Messages.getString (
        "MainWindow.NewMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    MainWindow.this.gui.jGTIToolBarButtonNew.setToolTipText ( Messages
        .getString ( "MainWindow.NewToolTip" ) ); //$NON-NLS-1$
    // Open
    MainWindow.this.gui.jMenuItemOpen.setText ( Messages
        .getString ( "MainWindow.Open" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuItemOpen.setMnemonic ( Messages.getString (
        "MainWindow.OpenMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    MainWindow.this.gui.jGTIToolBarButtonOpen.setToolTipText ( Messages
        .getString ( "MainWindow.OpenToolTip" ) ); //$NON-NLS-1$
    // Close
    MainWindow.this.gui.jMenuItemClose.setText ( Messages
        .getString ( "MainWindow.Close" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuItemClose.setMnemonic ( Messages.getString (
        "MainWindow.CloseMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // CloseAll
    MainWindow.this.gui.jMenuItemCloseAll.setText ( Messages
        .getString ( "MainWindow.CloseAll" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuItemCloseAll.setMnemonic ( Messages.getString (
        "MainWindow.CloseAllMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Save
    MainWindow.this.gui.jMenuItemSave.setText ( Messages
        .getString ( "MainWindow.Save" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuItemSave.setMnemonic ( Messages.getString (
        "MainWindow.SaveMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    MainWindow.this.gui.jGTIToolBarButtonSave.setToolTipText ( Messages
        .getString ( "MainWindow.SaveToolTip" ) ); //$NON-NLS-1$
    // SaveAs
    MainWindow.this.gui.jMenuItemSaveAs.setText ( Messages
        .getString ( "MainWindow.SaveAs" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuItemSaveAs.setMnemonic ( Messages.getString (
        "MainWindow.SaveAsMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    MainWindow.this.gui.jGTIToolBarButtonSaveAs.setToolTipText ( Messages
        .getString ( "MainWindow.SaveAsToolTip" ) ); //$NON-NLS-1$
    // SaveAll
    MainWindow.this.gui.jMenuItemSaveAll.setText ( Messages
        .getString ( "MainWindow.SaveAll" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuItemSaveAll.setMnemonic ( Messages.getString (
        "MainWindow.SaveAllMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Draft for
    MainWindow.this.gui.jMenuDraft.setText ( Messages
        .getString ( "MainWindow.DraftFor" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuDraft.setMnemonic ( Messages.getString (
        "MainWindow.DraftForMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuItemDFA.setText ( Messages
        .getString ( "MainWindow.DFA" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuItemNFA.setText ( Messages
        .getString ( "MainWindow.NFA" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuItemENFA.setText ( Messages
        .getString ( "MainWindow.ENFA" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuItemPDA.setText ( Messages
        .getString ( "MainWindow.PDA" ) ); //$NON-NLS-1$
    // RecentlyUsed
    MainWindow.this.gui.jMenuRecentlyUsed.setText ( Messages
        .getString ( "MainWindow.RecentlyUsed" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuRecentlyUsed.setMnemonic ( Messages.getString (
        "MainWindow.RecentlyUsedMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Quit
    MainWindow.this.gui.jMenuItemQuit.setText ( Messages
        .getString ( "MainWindow.Quit" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuItemQuit.setMnemonic ( Messages.getString (
        "MainWindow.QuitMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Edit
    MainWindow.this.gui.jMenuEdit.setText ( Messages
        .getString ( "MainWindow.Edit" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuEdit.setMnemonic ( Messages.getString (
        "MainWindow.EditMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Cut
    MainWindow.this.gui.jMenuItemCut.setText ( Messages
        .getString ( "MainWindow.Cut" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuItemCut.setMnemonic ( Messages.getString (
        "MainWindow.CutMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Copy
    MainWindow.this.gui.jMenuItemCopy.setText ( Messages
        .getString ( "MainWindow.Copy" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuItemCopy.setMnemonic ( Messages.getString (
        "MainWindow.CopyMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Paste
    MainWindow.this.gui.jMenuItemPaste.setText ( Messages
        .getString ( "MainWindow.Paste" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuItemPaste.setMnemonic ( Messages.getString (
        "MainWindow.PasteMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Undo
    MainWindow.this.gui.jMenuItemUndo.setText ( Messages
        .getString ( "MainWindow.Undo" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuItemUndo.setMnemonic ( Messages.getString (
        "MainWindow.UndoMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Redo
    MainWindow.this.gui.jMenuItemRedo.setText ( Messages
        .getString ( "MainWindow.Redo" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuItemRedo.setMnemonic ( Messages.getString (
        "MainWindow.RedoMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Preferences
    MainWindow.this.gui.jMenuItemPreferences.setText ( Messages
        .getString ( "MainWindow.Preferences" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuItemPreferences.setMnemonic ( Messages.getString (
        "MainWindow.PreferencesMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // View
    MainWindow.this.gui.jMenuView.setText ( Messages
        .getString ( "MainWindow.View" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuView.setMnemonic ( Messages.getString (
        "MainWindow.ViewMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Console
    MainWindow.this.gui.jCheckBoxMenuItemConsole.setText ( Messages
        .getString ( "MainWindow.Console" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jCheckBoxMenuItemConsole.setMnemonic ( Messages
        .getString ( "MainWindow.ConsoleMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Table
    MainWindow.this.gui.jCheckBoxMenuItemTable.setText ( Messages
        .getString ( "MainWindow.Table" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jCheckBoxMenuItemTable.setMnemonic ( Messages
        .getString ( "MainWindow.TableMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Execute
    MainWindow.this.gui.jMenuExecute.setText ( Messages
        .getString ( "MainWindow.Execute" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuExecute.setMnemonic ( Messages.getString (
        "MainWindow.ExecuteMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Validate
    MainWindow.this.gui.jMenuItemValidate.setText ( Messages
        .getString ( "MainWindow.Validate" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuItemValidate.setMnemonic ( Messages.getString (
        "MainWindow.ValidateMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // EnterWord
    MainWindow.this.gui.jMenuItemEnterWord.setText ( Messages
        .getString ( "MainWindow.EnterWord" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuItemEnterWord.setMnemonic ( Messages.getString (
        "MainWindow.EnterWordMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // EnterWord
    MainWindow.this.gui.jMenuItemEditMachine.setText ( Messages
        .getString ( "MainWindow.EditMachine" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuItemEditMachine.setMnemonic ( Messages.getString (
        "MainWindow.EditMachineMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Extras
    MainWindow.this.gui.jMenuExtras.setText ( Messages
        .getString ( "MainWindow.Extras" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuExtras.setMnemonic ( Messages.getString (
        "MainWindow.ExtrasMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Exchange
    MainWindow.this.gui.jMenuItemExchange.setText ( Messages
        .getString ( "MainWindow.Exchange" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuItemExchange.setMnemonic ( Messages.getString (
        "MainWindow.ExchangeMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // History
    MainWindow.this.gui.jMenuItemHistory.setText ( Messages
        .getString ( "MainWindow.History" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuItemHistory.setMnemonic ( Messages.getString (
        "MainWindow.HistoryMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Help
    MainWindow.this.gui.jMenuHelp.setText ( Messages
        .getString ( "MainWindow.Help" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuHelp.setMnemonic ( Messages.getString (
        "MainWindow.HelpMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // About
    MainWindow.this.gui.jMenuItemAbout.setText ( Messages
        .getString ( "MainWindow.About" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuItemAbout.setMnemonic ( Messages.getString (
        "MainWindow.AboutMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Mouse
    MainWindow.this.gui.jGTIToolBarToggleButtonMouse.setToolTipText ( Messages
        .getString ( "MachinePanel.Mouse" ) ); //$NON-NLS-1$
    // Add state
    MainWindow.this.gui.jGTIToolBarToggleButtonAddState
        .setToolTipText ( Messages.getString ( "MachinePanel.AddState" ) ); //$NON-NLS-1$
    // Add transition
    MainWindow.this.gui.jGTIToolBarToggleButtonAddTransition
        .setToolTipText ( Messages.getString ( "MachinePanel.AddTransition" ) ); //$NON-NLS-1$
    // Start state
    MainWindow.this.gui.jGTIToolBarToggleButtonStartState
        .setToolTipText ( Messages.getString ( "MachinePanel.StartState" ) ); //$NON-NLS-1$
    // Final state
    MainWindow.this.gui.jGTIToolBarToggleButtonFinalState
        .setToolTipText ( Messages.getString ( "MachinePanel.FinalState" ) ); //$NON-NLS-1$
    // Edit Document
    MainWindow.this.gui.jGTIToolBarButtonEditDocument.setToolTipText ( Messages
        .getString ( "MachinePanel.EditDocument" ) ); //$NON-NLS-1$
    // Previous Step
    MainWindow.this.gui.jGTIToolBarButtonPrevious.setToolTipText ( Messages
        .getString ( "MachinePanel.WordModePreviousStep" ) ); //$NON-NLS-1$
    // Start Word
    MainWindow.this.gui.jGTIToolBarButtonStart.setToolTipText ( Messages
        .getString ( "MachinePanel.WordModeStart" ) ); //$NON-NLS-1$
    // Next Step
    MainWindow.this.gui.jGTIToolBarButtonNextStep.setToolTipText ( Messages
        .getString ( "MachinePanel.WordModeNextStep" ) ); //$NON-NLS-1$
    // Auto Step
    MainWindow.this.gui.jGTIToolBarToggleButtonAutoStep
        .setToolTipText ( Messages.getString ( "MachinePanel.WordModeAutoStep" ) ); //$NON-NLS-1$
    // Stop Word
    MainWindow.this.gui.jGTIToolBarButtonStop.setToolTipText ( Messages
        .getString ( "MachinePanel.WordModeStop" ) ); //$NON-NLS-1$
    // Add production
    MainWindow.this.gui.jGTIToolBarButtonAddProduction
        .setToolTipText ( Messages.getString ( "GrammarPanel.AddProduction" ) ); //$NON-NLS-1$
    // Edit production
    MainWindow.this.gui.jGTIToolBarButtonEditProduction
        .setToolTipText ( Messages
            .getString ( "GrammarPanel.ProductionProperties" ) ); //$NON-NLS-1$
    // Delete production
    MainWindow.this.gui.jGTIToolBarButtonDeleteProduction
        .setToolTipText ( Messages.getString ( "GrammarPanel.DeleteProduction" ) ); //$NON-NLS-1$
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
    for ( EditorPanel current : this.gui.editorPanelTabbedPane )
    {
      if ( file.equals ( current.getFile () ) )
      {
        this.gui.editorPanelTabbedPane.setSelectedEditorPanel ( current );

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

        this.gui.editorPanelTabbedPane.addEditorPanel ( newEditorPanel );
        newEditorPanel
            .addModifyStatusChangedListener ( this.modifyStatusChangedListener );
        this.gui.editorPanelTabbedPane.setSelectedEditorPanel ( newEditorPanel );
        this.gui.editorPanelTabbedPane.setEditorPanelTitle ( newEditorPanel,
            file.getName () );
        setGeneralStates ( true );
        this.gui.jMenuItemValidate.setEnabled ( true );

        // toolbar items
        setToolBarEditItemState ( true );
      }

      else
      {
        DefaultGrammarModel model = ( DefaultGrammarModel ) element;

        EditorPanel newEditorPanel = new GrammarPanel ( this.gui, model, file );

        this.gui.editorPanelTabbedPane.addEditorPanel ( newEditorPanel );
        newEditorPanel
            .addModifyStatusChangedListener ( this.modifyStatusChangedListener );
        this.gui.editorPanelTabbedPane.setSelectedEditorPanel ( newEditorPanel );
        this.gui.editorPanelTabbedPane.setEditorPanelTitle ( newEditorPanel,
            file.getName () );
        setGeneralStates ( true );
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

    this.gui.jMenuRecentlyUsed.removeAll ();

    for ( RecentlyUsedMenuItem item : this.recentlyUsedFiles )
    {
      if ( item.getFile ().exists () )
      {
        this.gui.jMenuRecentlyUsed.add ( item );
      }
      else
      {
        notExistingFiles.add ( item );
      }
    }

    this.recentlyUsedFiles.removeAll ( notExistingFiles );

    if ( this.recentlyUsedFiles.size () > 0 )
    {
      this.gui.jMenuRecentlyUsed.setEnabled ( true );
    }
    else
    {
      this.gui.jMenuRecentlyUsed.setEnabled ( false );
    }
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
      for ( EditorPanel current : this.gui.editorPanelTabbedPane )
      {
        if ( current.getFile ().getAbsolutePath ().equals (
            activeFile.getAbsolutePath () ) )
        {
          this.gui.editorPanelTabbedPane.setSelectedEditorPanel ( current );
          break;
        }
      }
    }
  }


  /**
   * Sets general states for items and buttons.
   * 
   * @param state The new state.
   */
  private final void setGeneralStates ( boolean state )
  {
    // SaveAs
    this.gui.jGTIToolBarButtonSaveAs.setEnabled ( state );
    this.gui.jMenuItemSaveAs.setEnabled ( state );
    // SaveAll
    this.gui.jMenuItemSaveAll.setEnabled ( state );
    // Close
    this.gui.jMenuItemClose.setEnabled ( state );
    // CloseAll
    this.gui.jMenuItemCloseAll.setEnabled ( state );
    // View
    this.gui.jCheckBoxMenuItemConsole.setEnabled ( state );
    this.gui.jCheckBoxMenuItemTable.setEnabled ( state );
    // Enter word
    this.gui.jMenuItemEnterWord.setEnabled ( state );
    // Cut
    this.gui.jMenuItemCut.setVisible ( false );
    // Copy
    this.gui.jMenuItemCopy.setVisible ( false );
    // Paste
    this.gui.jMenuItemPaste.setVisible ( false );
    // Undo
    this.gui.jMenuItemUndo.setEnabled ( false );
    this.gui.jGTIToolBarButtonUndo.setEnabled ( false );
    // Redo
    this.gui.jMenuItemRedo.setEnabled ( false );
    this.gui.jGTIToolBarButtonRedo.setEnabled ( false );
    // Separator
    this.gui.jSeparatorEdit1.setVisible ( false );
    this.gui.jSeparatorEdit2.setVisible ( false );
    // Edit document
    this.gui.jGTIToolBarButtonEditDocument.setEnabled ( state );
    // Draft for
    this.gui.jMenuDraft.setEnabled ( state );
  }


  /**
   * Sets the state of the save button and item.
   */
  private final void setSaveState ()
  {
    boolean state = false;
    EditorPanel panel = this.gui.editorPanelTabbedPane
        .getSelectedEditorPanel ();
    if ( panel != null )
    {
      state = panel.isModified ();
    }
    setSaveState ( state );
  }


  /**
   * Sets the state of the save button and item.
   * 
   * @param state The new state of the save button.
   */
  private final void setSaveState ( boolean state )
  {
    logger.debug ( "setSaveState", "set save status to " + Messages.QUOTE //$NON-NLS-1$//$NON-NLS-2$
        + state + Messages.QUOTE );

    EditorPanel panel = this.gui.editorPanelTabbedPane
        .getSelectedEditorPanel ();
    if ( panel != null )
    {
      if ( state )
      {
        this.gui.editorPanelTabbedPane.setEditorPanelTitle ( panel, "*" //$NON-NLS-1$
            + panel.getName () );
      }
      else
      {
        this.gui.editorPanelTabbedPane.setEditorPanelTitle ( panel, panel
            .getName () );
      }
    }
    else if ( state == true )
    {
      throw new IllegalArgumentException ( "save button error" ); //$NON-NLS-1$
    }

    this.gui.jGTIToolBarButtonSave.setEnabled ( state );
    this.gui.jMenuItemSave.setEnabled ( state );
  }


  /**
   * Set the state of the edit machine toolbar items
   * 
   * @param state the new state
   */
  private final void setToolBarEditItemState ( boolean state )
  {
    this.gui.jGTIToolBarToggleButtonAddState.setEnabled ( state );
    this.gui.jGTIToolBarToggleButtonAddTransition.setEnabled ( state );
    this.gui.jGTIToolBarToggleButtonFinalState.setEnabled ( state );
    this.gui.jGTIToolBarToggleButtonMouse.setEnabled ( state );
    this.gui.jGTIToolBarToggleButtonStartState.setEnabled ( state );
    this.gui.jGTIToolBarButtonEditDocument.setEnabled ( state );
  }


  /**
   * Set the state of the enter word toolbar items
   * 
   * @param state the new state
   */
  private final void setToolBarEnterWordItemState ( boolean state )
  {
    this.gui.jGTIToolBarButtonPrevious.setEnabled ( state );
    this.gui.jGTIToolBarButtonStart.setEnabled ( state );
    this.gui.jGTIToolBarButtonNextStep.setEnabled ( state );
    this.gui.jGTIToolBarToggleButtonAutoStep.setEnabled ( state );
    this.gui.jGTIToolBarButtonStop.setEnabled ( state );
  }
}
