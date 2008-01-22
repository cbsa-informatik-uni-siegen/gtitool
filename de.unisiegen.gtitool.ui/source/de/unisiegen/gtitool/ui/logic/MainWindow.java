package de.unisiegen.gtitool.ui.logic;


import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ItemEvent;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.apache.log4j.Logger;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.exceptions.CoreException.ErrorType;
import de.unisiegen.gtitool.core.exceptions.machine.MachineException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineValidationException;
import de.unisiegen.gtitool.core.grammars.Grammar;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.storage.Storage;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.ui.EditorPanel;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.Version;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.netbeans.MachinesPanelForm;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;
import de.unisiegen.gtitool.ui.netbeans.helperclasses.EditorPanelForm;
import de.unisiegen.gtitool.ui.netbeans.helperclasses.RecentlyUsedMenuItem;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.preferences.item.OpenedFilesItem;
import de.unisiegen.gtitool.ui.preferences.item.RecentlyUsedFilesItem;
import de.unisiegen.gtitool.ui.preferences.listener.LanguageChangedListener;


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
   * The number of opened files.
   */
  private static int count = 0;


  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger ( MainWindow.class );


  /**
   * The {@link MainWindowForm}.
   */
  private MainWindowForm gui;


  /**
   * Flag signals if Console Preferences should be saved
   */
  private boolean saveConsolePreferences = true;


  /**
   * The {@link ModifyStatusChangedListener}.
   */
  private ModifyStatusChangedListener modifyStatusChangedListener;


  /**
   * Creates new form <code>MainWindow</code>.
   */
  public MainWindow ()
  {
    this.gui = new MainWindowForm ( this );
    try
    {
      this.gui.setIconImage ( ImageIO.read ( getClass ().getResource (
          "/de/unisiegen/gtitool/ui/icon/gtitool.png" ) ) ); //$NON-NLS-1$
    }
    catch ( Exception e )
    {
      e.printStackTrace ();
    }
    this.gui.setTitle ( "GTI Tool " + Version.VERSION ); //$NON-NLS-1$
    this.gui.setBounds ( PreferenceManager.getInstance ()
        .getMainWindowBounds () );
    // Setting the default states
    setGeneralStates ( false );
    // Save
    setSaveState ();
    // Copy
    // Validate
    this.gui.jMenuItemValidate.setEnabled ( false );
    // EnterWord
    this.gui.jMenuItemEnterWord.setEnabled ( false );
    // Preferences
    this.gui.jMenuItemPreferences.setEnabled ( true );
    // RecentlyUsed
    this.gui.jMenuRecentlyUsed.setEnabled ( false );

    // Toolbar items
    setToolBarEditItemState ( false );
    setToolBarEnterWordItemState ( false );

    this.gui.jButtonNextStep.setEnabled ( false );
    this.gui.jButtonPrevious.setEnabled ( false );
    this.gui.jButtonAutoStep.setEnabled ( false );
    this.gui.jButtonStop.setEnabled ( false );

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

    // Set the recently used files
    ArrayList < File > recentlyUsedFiles = PreferenceManager.getInstance ()
        .getRecentlyUsedFilesItem ().getFiles ();
    if ( recentlyUsedFiles.size () > 0 )
      this.gui.jMenuRecentlyUsed.setEnabled ( true );
    for ( File file : recentlyUsedFiles )
    {
      this.gui.jMenuRecentlyUsed.add ( new RecentlyUsedMenuItem ( this, file ) );
    }

    this.modifyStatusChangedListener = new ModifyStatusChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void modifyStatusChanged ()
      {
        setSaveState ();
      }
    };

    File activeFile = PreferenceManager.getInstance ().getOpenedFilesItem ()
        .getActiveFile ();
    if ( activeFile != null )
    {
      for ( int i = 0 ; i < this.gui.jTabbedPaneMain.getTabCount () ; i++ )
      {
        EditorPanel panel = ( ( MachinesPanelForm ) this.gui.jTabbedPaneMain
            .getComponentAt ( i ) ).getLogic ();
        if ( panel.getFile ().equals ( activeFile ) )
        {
          this.gui.jTabbedPaneMain.setSelectedIndex ( i );
          break;
        }
      }
    }
  }


  /**
   * Getter for the active EditorPanel
   * 
   * @return the active EditorPanel
   */
  public EditorPanel getActiveEditor ()
  {
    if ( this.gui.jTabbedPaneMain.getSelectedComponent () == null )
    {
      return null;
    }
    return ( ( ( MachinesPanelForm ) this.gui.jTabbedPaneMain
        .getSelectedComponent () ).getLogic () );
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
   * Returns the {@link Alphabet} of the selected {@link EditorPanel}.
   * 
   * @return The {@link Alphabet} of the selected {@link EditorPanel}.
   */
  public final Alphabet getSelectedAlphabet ()
  {
    EditorPanel editorPanel = getSelectedEditorPanel ();
    if ( editorPanel == null )
    {
      return null;
    }
    return editorPanel.getAlphabet ();
  }


  /**
   * Returns the selected {@link EditorPanel}.
   * 
   * @return The selected {@link EditorPanel}.
   */
  public final EditorPanel getSelectedEditorPanel ()
  {
    return ( ( MachinesPanelForm ) this.gui.jTabbedPaneMain
        .getSelectedComponent () ).getLogic ();
  }


  /**
   * Handle the action event of the about item.
   */
  public final void handleAbout ()
  {
    AboutDialog aboutDialog = new AboutDialog ( this.gui );
    aboutDialog.show ();
  }


  /**
   * Closes the active editor window.
   * 
   * @return true if the active editor could be closed.
   */
  public boolean handleClose ()
  {
    // EditorPanel selectedEditor = getActiveEditor ();
    boolean success;
    // if ( selectedEditor.shouldBeSaved ( ) )
    // {
    // Object [ ] options =
    // {
    // java.util.ResourceBundle.getBundle ( "de/unisiegen/tpml/ui/ui" )
    // .getString ( "Yes" ) ,
    // java.util.ResourceBundle.getBundle ( "de/unisiegen/tpml/ui/ui" )
    // .getString ( "No" ) ,
    // java.util.ResourceBundle.getBundle ( "de/unisiegen/tpml/ui/ui" )
    // .getString ( "Cancel" ) } ;
    // int n = JOptionPane.showOptionDialog ( window , selectedEditor
    // .getFileName ( )
    // + java.util.ResourceBundle.getBundle ( "de/unisiegen/tpml/ui/ui" )
    // .getString ( "WantTosave" ) , java.util.ResourceBundle.getBundle (
    // "de/unisiegen/tpml/ui/ui" ).getString ( "Save_File" ) ,
    // JOptionPane.YES_NO_CANCEL_OPTION , JOptionPane.QUESTION_MESSAGE ,
    // null , options , options [ 2 ] ) ;
    // switch ( n )
    // {
    // case 0 : // Save Changes
    // logger.debug ( "Close dialog: YES" ) ;
    // success = selectedEditor.handleSave ( ) ;
    // if ( success )
    // {
    // window.tabbedPane.remove ( window.tabbedPane.getSelectedIndex ( ) ) ;
    // window.repaint ( ) ;
    // }
    // return success ;
    // case 1 : // Do not save changes
    // logger.debug ( "Close dialog: NO" ) ;
    // window.tabbedPane.remove ( window.tabbedPane.getSelectedIndex ( ) ) ;
    // window.repaint ( ) ;
    // success = true ;
    // case 2 : // Cancelled.
    // logger.debug ( "Close dialog: CANCEL" ) ;
    // success = false ;
    // default :
    // success = false ;
    // }
    // }
    // else
    {
      this.gui.jTabbedPaneMain.remove ( this.gui.jTabbedPaneMain
          .getSelectedIndex () );
      this.gui.repaint ();
      success = true;
    }
    if ( getActiveEditor () == null )
    {
      setGeneralStates ( false );
      setSaveState ();

      // toolbar items
      this.gui.jButtonAddState.setEnabled ( false );
      this.gui.jButtonAddTransition.setEnabled ( false );
      this.gui.jButtonFinalState.setEnabled ( false );
      this.gui.jButtonMouse.setEnabled ( false );
      this.gui.jButtonStartState.setEnabled ( false );
      this.gui.jButtonEditAlphabet.setEnabled ( false );
    }
    return success;
  }


  /**
   * Handles console state changes.
   */
  public final void handleConsoleStateChanged ()
  {
    if ( PreferenceManager.getInstance ().getVisibleConsole () != this.gui.jCheckBoxMenuItemConsole
        .getState ()
        && this.saveConsolePreferences )
    {
      PreferenceManager.getInstance ().setVisibleConsole (
          this.gui.jCheckBoxMenuItemConsole.getState () );
      MachinePanel current = ( MachinePanel ) getActiveEditor ();
      current
          .setVisibleConsole ( this.gui.jCheckBoxMenuItemConsole.getState () );
      current
          .setConsoleVisible ( this.gui.jCheckBoxMenuItemConsole.getState () );

    }
  }


  /**
   * Handle Edit Alphabet Action in the Toolbar
   */
  public void handleEditAlphabet ()
  {
    MachinePanel current = ( MachinePanel ) getActiveEditor ();
    current.handleToolbarAlphabet ();
  }


  /**
   * Handle Edit Machine button pressed
   */
  public void handleEditMachine ()
  {
    setToolBarEditItemState ( true );
    setToolBarEnterWordItemState ( false );
    MachinePanel machinePanel = ( MachinePanel ) ( ( EditorPanelForm ) this.gui.jTabbedPaneMain
        .getSelectedComponent () ).getLogic ();

    machinePanel.handleEditMachine ();
    machinePanel.setVisibleConsole ( this.gui.jCheckBoxMenuItemConsole
        .getState () );
    this.gui.jCheckBoxMenuItemConsole.setEnabled ( true );
    machinePanel.setWordEnterMode ( false );
  }


  /**
   * Handle the action event of the enter word item.
   */
  public final void handleEnterWord ()
  {
    MachinePanel panel = ( MachinePanel ) ( ( EditorPanelForm ) this.gui.jTabbedPaneMain
        .getSelectedComponent () ).getLogic ();
    int errorCount = 0;
    int warningCount = 0;
    try
    {
      panel.clearValidationMessages ();
      panel.getMachine ().validate ();
    }
    catch ( MachineValidationException e )
    {
      for ( MachineException error : e.getMachineException () )
      {
        if ( error.getType ().equals ( ErrorType.ERROR ) )
        {
          panel.addError ( error );
          errorCount++ ;
        }
        else if ( error.getType ().equals ( ErrorType.WARNING ) )
        {
          panel.addWarning ( error );
          warningCount++ ;
        }
      }
    }
    if ( errorCount > 0 )
    {
      JOptionPane.showMessageDialog ( this.gui, errorCount
          + " Error(s) in the Machine", "Error", JOptionPane.ERROR_MESSAGE ); //$NON-NLS-1$//$NON-NLS-2$
      return;
    }
    setToolBarEditItemState ( false );
    setToolBarEnterWordItemState ( true );
    MachinePanel machinePanel = ( MachinePanel ) getActiveEditor ();
    machinePanel.handleEnterWord ();
    this.gui.jCheckBoxMenuItemConsole.setEnabled ( false );
    machinePanel.setWordEnterMode ( true );
  }


  /**
   * Handle the open event.
   */
  public final void handleNew ()
  {
    NewDialog newDialog = new NewDialog ( this.gui );
    // newDialog.setLocationRelativeTo ( window ) ;
    newDialog.show ();
    EditorPanel newEditorPanel = newDialog.getEditorPanel ();
    if ( newEditorPanel != null )
    {

      this.gui.jTabbedPaneMain.add ( newEditorPanel.getPanel () );
      this.gui.jTabbedPaneMain.setSelectedComponent ( newEditorPanel
          .getPanel () );
      this.gui.jTabbedPaneMain
          .setTitleAt (
              this.gui.jTabbedPaneMain.getSelectedIndex (),
              Messages.getString ( "MainWindow.NewFile" ) + count + newDialog.getFileEnding () ); //$NON-NLS-1$ 
      count++ ;
      setGeneralStates ( true );

      // toolbar items
      setToolBarEditItemState ( true );
    }
  }


  /**
   * Handles the open event.
   */
  public final void handleOpen ()
  {
    PreferenceManager prefmanager = PreferenceManager.getInstance ();
    JFileChooser chooser = new JFileChooser ( prefmanager.getWorkingPath () );
    chooser.setMultiSelectionEnabled ( true );
    chooser.setAcceptAllFileFilterUsed ( false );

    // Source files
    FileFilter sourceFileFilter = new FileFilter ()
    {

      @Override
      public boolean accept ( File file )
      {
        if ( file.isDirectory () )
        {
          return true;
        }
        for ( String current : Machine.AVAILABLE_MACHINES )
        {
          if ( file.getName ().toLowerCase ().matches (
              ".+\\." + current.toLowerCase () ) ) //$NON-NLS-1$
          {
            return true;
          }
        }
        for ( String current : Grammar.AVAILABLE_GRAMMARS )
        {
          if ( file.getName ().toLowerCase ().matches (
              ".+\\." + current.toLowerCase () ) ) //$NON-NLS-1$
          {
            return true;
          }
        }
        return false;
      }


      @Override
      public String getDescription ()
      {
        StringBuilder result = new StringBuilder ();
        result.append ( Messages.getString ( "MainWindow.OpenSourceFiles" ) ); //$NON-NLS-1$
        result.append ( " (" ); //$NON-NLS-1$
        for ( int i = 0 ; i < Machine.AVAILABLE_MACHINES.length ; i++ )
        {
          result.append ( "*." ); //$NON-NLS-1$
          result.append ( Machine.AVAILABLE_MACHINES [ i ].toLowerCase () );
          if ( i != Machine.AVAILABLE_MACHINES.length - 1 )
          {
            result.append ( "; " ); //$NON-NLS-1$
          }
        }
        if ( ( Machine.AVAILABLE_MACHINES.length > 0 )
            && ( Grammar.AVAILABLE_GRAMMARS.length > 0 ) )
        {
          result.append ( "; " ); //$NON-NLS-1$
        }
        for ( int i = 0 ; i < Grammar.AVAILABLE_GRAMMARS.length ; i++ )
        {
          result.append ( "*." ); //$NON-NLS-1$
          result.append ( Grammar.AVAILABLE_GRAMMARS [ i ].toLowerCase () );
          if ( i != Grammar.AVAILABLE_GRAMMARS.length - 1 )
          {
            result.append ( "; " ); //$NON-NLS-1$
          }
        }
        result.append ( ")" ); //$NON-NLS-1$
        return result.toString ();
      }
    };

    // Machine files
    FileFilter machineFileFilter = new FileFilter ()
    {

      @Override
      public boolean accept ( File file )
      {
        if ( file.isDirectory () )
        {
          return true;
        }
        for ( String current : Machine.AVAILABLE_MACHINES )
        {
          if ( file.getName ().toLowerCase ().matches (
              ".+\\." + current.toLowerCase () ) ) //$NON-NLS-1$
          {
            return true;
          }
        }
        return false;
      }


      @Override
      public String getDescription ()
      {
        StringBuilder result = new StringBuilder ();
        result.append ( Messages
            .getString ( "MainWindow.OpenSourceFilesMachine" ) ); //$NON-NLS-1$
        result.append ( " (" ); //$NON-NLS-1$
        for ( int i = 0 ; i < Machine.AVAILABLE_MACHINES.length ; i++ )
        {
          result.append ( "*." ); //$NON-NLS-1$
          result.append ( Machine.AVAILABLE_MACHINES [ i ].toLowerCase () );
          if ( i != Machine.AVAILABLE_MACHINES.length - 1 )
          {
            result.append ( "; " ); //$NON-NLS-1$
          }
        }
        result.append ( ")" ); //$NON-NLS-1$
        return result.toString ();
      }
    };

    // Grammar files
    FileFilter grammarFileFilter = new FileFilter ()
    {

      @Override
      public boolean accept ( File file )
      {
        if ( file.isDirectory () )
        {
          return true;
        }
        for ( String current : Grammar.AVAILABLE_GRAMMARS )
        {
          if ( file.getName ().toLowerCase ().matches (
              ".+\\." + current.toLowerCase () ) ) //$NON-NLS-1$
          {
            return true;
          }
        }
        return false;
      }


      @Override
      public String getDescription ()
      {
        StringBuilder result = new StringBuilder ();
        result.append ( Messages
            .getString ( "MainWindow.OpenSourceFilesGrammar" ) ); //$NON-NLS-1$
        result.append ( " (" ); //$NON-NLS-1$
        for ( int i = 0 ; i < Grammar.AVAILABLE_GRAMMARS.length ; i++ )
        {
          result.append ( "*." ); //$NON-NLS-1$
          result.append ( Grammar.AVAILABLE_GRAMMARS [ i ].toLowerCase () );
          if ( i != Grammar.AVAILABLE_GRAMMARS.length - 1 )
          {
            result.append ( "; " ); //$NON-NLS-1$
          }
        }
        result.append ( ")" ); //$NON-NLS-1$
        return result.toString ();
      }
    };

    chooser.addChoosableFileFilter ( sourceFileFilter );
    chooser.addChoosableFileFilter ( machineFileFilter );
    chooser.addChoosableFileFilter ( grammarFileFilter );
    chooser.setFileFilter ( sourceFileFilter );

    int n = chooser.showOpenDialog ( this.gui );
    if ( n == JFileChooser.CANCEL_OPTION || chooser.getSelectedFile () == null )
      return;

    for ( File file : chooser.getSelectedFiles () )
    {
      openFile ( file, true );
    }

    PreferenceManager.getInstance ().setWorkingPath (
        chooser.getSelectedFile ().getParentFile ().getAbsolutePath () );
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
    PreferenceManager preferenceManager = PreferenceManager.getInstance ();
    preferenceManager.setMainWindowPreferences ( this.gui );

    EditorPanel editorPanel;
    ArrayList < File > files = new ArrayList < File > ();
    for ( Component component : this.gui.jTabbedPaneMain.getComponents () )
    {
      editorPanel = ( ( EditorPanelForm ) component ).getLogic ();
      if ( editorPanel.getFile () != null )
        files.add ( editorPanel.getFile () );
    }
    OpenedFilesItem item = new OpenedFilesItem ( files, getActiveEditor ()
        .getFile () );
    preferenceManager.setOpenedFilesItem ( item );
    System.exit ( 0 );
  }


  /**
   * Handle the save file event
   */
  public void handleSave ()
  {
    EditorPanel panel = ( ( EditorPanelForm ) this.gui.jTabbedPaneMain
        .getSelectedComponent () ).getLogic ();
    String fileName = panel.handleSave ();
    if ( fileName != null )
    {
      this.gui.jTabbedPaneMain.setTitleAt ( this.gui.jTabbedPaneMain
          .getSelectedIndex (), fileName );
    }
  }


  /**
   * Handle the save file as event
   */
  public void handleSaveAs ()
  {
    EditorPanel panel = ( ( EditorPanelForm ) this.gui.jTabbedPaneMain
        .getSelectedComponent () ).getLogic ();
    String fileName = panel.handleSaveAs ();

    if ( fileName != null )
      this.gui.jTabbedPaneMain.setTitleAt ( this.gui.jTabbedPaneMain
          .getSelectedIndex (), fileName );
  }


  /**
   * Handle TabbedPane state changed event
   */
  public void handleTabbedPaneStateChanged ()
  {
    MachinePanel machinePanel = ( MachinePanel ) getActiveEditor ();
    if ( machinePanel != null )
    {
      this.gui.jCheckBoxMenuItemConsole.setEnabled ( !machinePanel
          .isWordEnterMode () );
      this.saveConsolePreferences = false;
      this.gui.jCheckBoxMenuItemConsole.setState ( machinePanel
          .isConsoleVisible () );
      // machinePanel.setVisibleConsole ( !machinePanel.isWordEnterMode ()
      // && machinePanel.isConsoleVisible () );
      this.saveConsolePreferences = true;
      this.gui.jCheckBoxMenuItemTable
          .setState ( machinePanel.isTableVisible () );
      setToolBarEditItemState ( !machinePanel.isWordEnterMode () );
      setToolBarEnterWordItemState ( machinePanel.isWordEnterMode () );

      for ( int i = 0 ; i < this.gui.jTabbedPaneMain.getComponentCount () ; i++ )
      {
        MachinesPanelForm panel = ( MachinesPanelForm ) this.gui.jTabbedPaneMain
            .getComponentAt ( i );
        ( ( MachinePanel ) panel.getLogic () )
            .removeModifyStatusChangedListener ( this.modifyStatusChangedListener );
      }
      machinePanel
          .addModifyStatusChangedListener ( this.modifyStatusChangedListener );
    }
    // Save status
    setSaveState ();
  }


  /**
   * Handles table state changes.
   */
  public final void handleTableStateChanged ()
  {
    if ( PreferenceManager.getInstance ().getVisibleTable () != this.gui.jCheckBoxMenuItemTable
        .getState () )
    {
      PreferenceManager.getInstance ().setVisibleTable (
          this.gui.jCheckBoxMenuItemTable.getState () );
      MachinePanel current = ( MachinePanel ) getActiveEditor ();
      current.setVisibleTable ( this.gui.jCheckBoxMenuItemTable.getState () );
    }
  }


  /**
   * Handle Toolbar Add State button value changed
   * 
   * @param state The new State of the Add State Toolbar button
   */
  public void handleToolbarAddState ( boolean state )
  {
    MachinePanel panel = ( MachinePanel ) ( ( EditorPanelForm ) this.gui.jTabbedPaneMain
        .getSelectedComponent () ).getLogic ();
    panel.handleToolbarAddState ( state );
  }


  /**
   * Handle Toolbar End button value changed
   * 
   * @param state The new State of the End Toolbar button
   */
  public void handleToolbarEnd ( boolean state )
  {
    MachinePanel panel = ( MachinePanel ) ( ( EditorPanelForm ) this.gui.jTabbedPaneMain
        .getSelectedComponent () ).getLogic ();
    panel.handleToolbarEnd ( state );

  }


  /**
   * Handle Toolbar Mouse button value changed
   * 
   * @param state The new State of the Mouse Toolbar button
   */
  public void handleToolbarMouse ( boolean state )
  {
    if ( this.gui != null )
    {
      MachinePanel panel = ( MachinePanel ) ( ( EditorPanelForm ) this.gui.jTabbedPaneMain
          .getSelectedComponent () ).getLogic ();
      panel.handleToolbarMouse ( state );
    }
  }


  /**
   * Handle Toolbar Start button value changed
   * 
   * @param state The new State of the Start Toolbar button
   */
  public void handleToolbarStart ( boolean state )
  {
    MachinePanel panel = ( MachinePanel ) ( ( EditorPanelForm ) this.gui.jTabbedPaneMain
        .getSelectedComponent () ).getLogic ();
    panel.handleToolbarStart ( state );
  }


  /**
   * Handle Toolbar Transition button value changed
   * 
   * @param state The new State of the Transition Toolbar button
   */
  public void handleToolbarTransition ( boolean state )
  {
    MachinePanel panel = ( MachinePanel ) ( ( EditorPanelForm ) this.gui.jTabbedPaneMain
        .getSelectedComponent () ).getLogic ();
    panel.handleToolbarTransition ( state );
  }


  /**
   * Handle the action event of the enter word item.
   */
  public final void handleValidate ()
  {
    MachinePanel panel = ( MachinePanel ) ( ( EditorPanelForm ) this.gui.jTabbedPaneMain
        .getSelectedComponent () ).getLogic ();
    int errorCount = 0;
    int warningCount = 0;
    try
    {
      panel.clearValidationMessages ();
      panel.getMachine ().validate ();
    }
    catch ( MachineValidationException e )
    {
      for ( MachineException error : e.getMachineException () )
      {
        if ( error.getType ().equals ( ErrorType.ERROR ) )
        {
          panel.addError ( error );
          errorCount++ ;
        }
        else if ( error.getType ().equals ( ErrorType.WARNING ) )
        {
          panel.addWarning ( error );
          warningCount++ ;
        }
      }
    }
    JOptionPane.showMessageDialog ( this.gui, errorCount
        + " Errors in the Machine", "Error", JOptionPane.ERROR_MESSAGE ); //$NON-NLS-1$//$NON-NLS-2$
  }


  /**
   * Handle Auto Step Action in the Word Enter Mode
   * 
   * @param evt
   */
  public void handleWordAutoStep ( ItemEvent evt )
  {
    MachinePanel current = ( MachinePanel ) getActiveEditor ();
    current.handleWordAutoStep ( evt );

  }


  /**
   * Handle Next Step Action in the Word Enter Mode
   */
  public void handleWordNextStep ()
  {
    MachinePanel current = ( MachinePanel ) getActiveEditor ();
    current.handleWordNextStep ();

  }


  /**
   * Handle Previous Step Action in the Word Enter Mode
   */
  public void handleWordPreviousStep ()
  {
    MachinePanel current = ( MachinePanel ) getActiveEditor ();
    current.handleWordPreviousStep ();

  }


  /**
   * Handle Start Action in the Word Enter Mode
   */
  public void handleWordStart ()
  {
    MachinePanel panel = ( MachinePanel ) ( ( EditorPanelForm ) this.gui.jTabbedPaneMain
        .getSelectedComponent () ).getLogic ();
    int errorCount = 0;
    int warningCount = 0;
    try
    {
      panel.clearValidationMessages ();
      panel.getMachine ().validate ();
    }
    catch ( MachineValidationException e )
    {
      for ( MachineException error : e.getMachineException () )
      {
        if ( error.getType ().equals ( ErrorType.ERROR ) )
        {
          panel.addError ( error );
          errorCount++ ;
        }
        else if ( error.getType ().equals ( ErrorType.WARNING ) )
        {
          panel.addWarning ( error );
          warningCount++ ;
        }
      }
    }
    if ( errorCount > 0 )
    {
      JOptionPane.showMessageDialog ( this.gui, errorCount
          + " Errors in the Machine", "Error", JOptionPane.ERROR_MESSAGE ); //$NON-NLS-1$//$NON-NLS-2$
      return;
    }

    MachinePanel current = ( MachinePanel ) getActiveEditor ();

    if ( current.handleWordStart () )
    {
      this.gui.jButtonStart.setEnabled ( false );
      this.gui.jButtonNextStep.setEnabled ( true );
      this.gui.jButtonPrevious.setEnabled ( true );
      this.gui.jButtonAutoStep.setEnabled ( true );
      this.gui.jButtonStop.setEnabled ( true );

    }

  }


  /**
   * Handle Stop Action in the Word Enter Mode
   */
  public void handleWordStop ()
  {
    this.gui.jButtonStart.setEnabled ( true );
    this.gui.jButtonNextStep.setEnabled ( false );
    this.gui.jButtonPrevious.setEnabled ( false );
    this.gui.jButtonAutoStep.setEnabled ( false );
    this.gui.jButtonStop.setEnabled ( false );
    MachinePanel current = ( MachinePanel ) getActiveEditor ();
    current.handleWordStop ();

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
    MainWindow.this.gui.jButtonNew.setToolTipText ( Messages
        .getString ( "MainWindow.NewToolTip" ) ); //$NON-NLS-1$
    // Open
    MainWindow.this.gui.jMenuItemOpen.setText ( Messages
        .getString ( "MainWindow.Open" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuItemOpen.setMnemonic ( Messages.getString (
        "MainWindow.OpenMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    MainWindow.this.gui.jButtonOpen.setToolTipText ( Messages
        .getString ( "MainWindow.OpenToolTip" ) ); //$NON-NLS-1$
    // Close
    MainWindow.this.gui.jMenuItemClose.setText ( Messages
        .getString ( "MainWindow.Close" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuItemClose.setMnemonic ( Messages.getString (
        "MainWindow.CloseMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Save
    MainWindow.this.gui.jMenuItemSave.setText ( Messages
        .getString ( "MainWindow.Save" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuItemSave.setMnemonic ( Messages.getString (
        "MainWindow.SaveMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    MainWindow.this.gui.jButtonSave.setToolTipText ( Messages
        .getString ( "MainWindow.SaveToolTip" ) ); //$NON-NLS-1$
    // SaveAs
    MainWindow.this.gui.jMenuItemSaveAs.setText ( Messages
        .getString ( "MainWindow.SaveAs" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuItemSaveAs.setMnemonic ( Messages.getString (
        "MainWindow.SaveAsMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    MainWindow.this.gui.jButtonSaveAs.setToolTipText ( Messages
        .getString ( "MainWindow.SaveAsToolTip" ) ); //$NON-NLS-1$
    // SaveAll
    MainWindow.this.gui.jMenuItemSaveAll.setText ( Messages
        .getString ( "MainWindow.SaveAll" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jMenuItemSaveAll.setMnemonic ( Messages.getString (
        "MainWindow.SaveAllMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
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
    MainWindow.this.gui.jButtonMouse.setToolTipText ( Messages
        .getString ( "MachinePanel.Mouse" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jButtonAddState.setToolTipText ( Messages
        .getString ( "MachinePanel.AddState" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jButtonAddTransition.setToolTipText ( Messages
        .getString ( "MachinePanel.AddTransition" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jButtonStartState.setToolTipText ( Messages
        .getString ( "MachinePanel.StartState" ) ); //$NON-NLS-1$
    MainWindow.this.gui.jButtonFinalState.setToolTipText ( Messages
        .getString ( "MachinePanel.FinalState" ) ); //$NON-NLS-1$
  }


  /**
   * Try to open the given file
   * 
   * @param file The file to open
   * @param addToRecentlyUsed Flag signals if file should be added to recently
   *          used files
   */
  public void openFile ( File file, boolean addToRecentlyUsed )
  {

    // check if we already have an editor panel for the file
    EditorPanel editorPanel = null;
    for ( Component component : this.gui.jTabbedPaneMain.getComponents () )
    {
      editorPanel = ( ( EditorPanelForm ) component ).getLogic ();
      if ( file.equals ( editorPanel.getFile () ) )
      {
        this.gui.jTabbedPaneMain.setSelectedComponent ( component );

        // reorganize recently used files
        if ( addToRecentlyUsed )
        {
          ArrayList < File > fileList = PreferenceManager.getInstance ()
              .getRecentlyUsedFilesItem ().getFiles ();
          fileList.remove ( file );
          fileList.add ( 0, file );
          if ( fileList.size () > 10 )
            fileList.remove ( 10 );
          if ( !this.gui.jMenuRecentlyUsed.isEnabled () )
            this.gui.jMenuRecentlyUsed.setEnabled ( true );

          PreferenceManager.getInstance ().setRecentlyUsedFilesItem (
              new RecentlyUsedFilesItem ( fileList ) );
          organizeRecentlyUsedFilesMenu ();
        }

        return;
      }
    }
    try
    {
      DefaultMachineModel model = ( DefaultMachineModel ) Storage
          .getInstance ().load ( file, DefaultMachineModel.class );
      EditorPanel newEditorPanel = new MachinePanel ( this.gui, model, file );

      this.gui.jTabbedPaneMain.add ( newEditorPanel.getPanel () );
      this.gui.jTabbedPaneMain.setSelectedComponent ( newEditorPanel
          .getPanel () );
      this.gui.jTabbedPaneMain.setTitleAt ( this.gui.jTabbedPaneMain
          .getSelectedIndex (), file.getName () );
      count++ ;
      setGeneralStates ( true );

      // toolbar items
      setToolBarEditItemState ( true );

      // reorganize recently used files
      if ( addToRecentlyUsed )
      {
        ArrayList < File > fileList = PreferenceManager.getInstance ()
            .getRecentlyUsedFilesItem ().getFiles ();
        fileList.remove ( file );
        fileList.add ( 0, file );
        if ( fileList.size () > 10 )
          fileList.remove ( 10 );
        if ( !this.gui.jMenuRecentlyUsed.isEnabled () )
          this.gui.jMenuRecentlyUsed.setEnabled ( true );

        PreferenceManager.getInstance ().setRecentlyUsedFilesItem (
            new RecentlyUsedFilesItem ( fileList ) );
        organizeRecentlyUsedFilesMenu ();
      }
    }
    catch ( StoreException e )
    {
      JOptionPane.showMessageDialog ( this.gui, e.getMessage (), Messages
          .getString ( "MainWindow.ErrorLoad" ), JOptionPane.ERROR_MESSAGE ); //$NON-NLS-1$
    }
    PreferenceManager.getInstance ().setWorkingPath (
        file.getParentFile ().getAbsolutePath () );
  }


  /**
   * Organize the recently used files in the menu
   */
  private void organizeRecentlyUsedFilesMenu ()
  {
    ArrayList < File > fileList = PreferenceManager.getInstance ()
        .getRecentlyUsedFilesItem ().getFiles ();

    this.gui.jMenuRecentlyUsed.removeAll ();

    for ( File file : fileList )
    {
      this.gui.jMenuRecentlyUsed.add ( new RecentlyUsedMenuItem ( this, file ) );
    }
  }


  /**
   * Open all files which was open at last session
   */
  public void restoreOpenFiles ()
  {
    for ( File file : PreferenceManager.getInstance ().getOpenedFilesItem ()
        .getFiles () )
    {
      openFile ( file, false );
    }

  }


  /**
   * Sets general states for items and buttons.
   * 
   * @param pState The new state.
   */
  private final void setGeneralStates ( boolean pState )
  {
    // SaveAs
    this.gui.jButtonSaveAs.setEnabled ( pState );
    this.gui.jMenuItemSaveAs.setEnabled ( pState );

    // SaveAll
    this.gui.jMenuItemSaveAll.setEnabled ( pState );

    // Close
    this.gui.jMenuItemClose.setEnabled ( pState );

    // Validate
    this.gui.jMenuItemValidate.setEnabled ( pState );

    // EnterWord
    this.gui.jMenuItemEnterWord.setEnabled ( pState );

    // Cut
    // this.gui.jMenuItemCut.setEnabled ( pState );
    this.gui.jMenuItemCut.setVisible ( false );

    // Copy
    // this.gui.jMenuItemCopy.setEnabled ( pState );
    this.gui.jMenuItemCopy.setVisible ( false );

    // Paste
    // this.gui.jMenuItemPaste.setEnabled ( pState );
    this.gui.jMenuItemPaste.setVisible ( false );

    // Undo
    this.gui.jMenuItemUndo.setVisible ( false );

    // Redo
    this.gui.jMenuItemRedo.setVisible ( false );

    // Separator
    this.gui.jSeparatorEdit1.setVisible ( false );
    this.gui.jSeparatorEdit2.setVisible ( false );
  }


  /**
   * Sets the state of the save button and item.
   */
  private final void setSaveState ()
  {
    boolean state = false;
    MachinePanel machinePanel = ( MachinePanel ) getActiveEditor ();
    if ( machinePanel != null )
    {
      state = machinePanel.isModified ();
    }
    logger.debug ( "set save status to \"" + state + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.gui.jButtonSave.setEnabled ( state );
    this.gui.jMenuItemSave.setEnabled ( state );
  }


  /**
   * Set the state of the edit machine toolbar items
   * 
   * @param state the new state
   */
  private void setToolBarEditItemState ( boolean state )
  {
    this.gui.jButtonAddState.setEnabled ( state );
    this.gui.jButtonAddTransition.setEnabled ( state );
    this.gui.jButtonFinalState.setEnabled ( state );
    this.gui.jButtonMouse.setEnabled ( state );
    this.gui.jButtonStartState.setEnabled ( state );
    this.gui.jButtonEditAlphabet.setEnabled ( state );
  }


  /**
   * Set the state of the enter word toolbar items
   * 
   * @param state the new state
   */
  private void setToolBarEnterWordItemState ( boolean state )
  {
    // this.gui.jButtonPrevious.setEnabled ( state );
    this.gui.jButtonStart.setEnabled ( state );
    // this.gui.jButtonNextStep.setEnabled ( state );
    // this.gui.jButtonAutoStep.setEnabled ( state );
    // this.gui.jButtonStop.setEnabled ( state );
  }


  /**
   * Handle Auto Step Stopped by Exception
   */
  public void handleAutoStepStopped ()
  {
    this.gui.jButtonAutoStep.setSelected ( false );
  }

}
