package de.unisiegen.gtitool.ui.logic;


import javax.swing.JFileChooser;

import de.unisiegen.gtitool.ui.EditorPanel;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.Versions;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.preferences.listener.LanguageChangedListener;


/**
 * The main programm window.
 * 
 * @author Benjamin Mies
 * @author Christian Fehler
 * @version $Id$
 */
public class MainWindow
{

  /**
   * The number of opened files.
   */
  private static int count = 0;


  /**
   * The {@link MainWindowForm}.
   */
  private MainWindowForm gui;


  /**
   * Creates new form <code>MainWindow</code>.
   */
  public MainWindow ()
  {
    this.gui = new MainWindowForm ( this );
    this.gui.setTitle ( "GTI Tool " + Versions.UI ); //$NON-NLS-1$
    PreferenceManager preferenceManager = PreferenceManager.getInstance ();
    this.gui.setBounds ( preferenceManager.getMainWindowBounds () );
    this.gui.setVisible ( true );
    // Setting the default states
    setGeneralStates ( false );
    // Save
    this.gui.jButtonSave.setEnabled ( false );
    this.gui.jMenuItemSave.setEnabled ( false );
    // Copy
    this.gui.jButtonCopy.setEnabled ( false );
    this.gui.jMenuItemCopy.setEnabled ( false );
    // Paste
    this.gui.jButtonPaste.setEnabled ( false );
    this.gui.jMenuItemPaste.setEnabled ( false );
    // Cut
    this.gui.jButtonCut.setEnabled ( false );
    this.gui.jMenuItemCut.setEnabled ( false );
    // Preferences
    this.gui.jMenuItemPreferences.setEnabled ( true );
    // RecentlyUsed
    this.gui.jMenuRecentlyUsed.setEnabled ( false );

    /*
     * Language changed listener
     */
    PreferenceManager.getInstance ().addLanguageChangedListener (
        new LanguageChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void languageChanged ()
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
            MainWindow.this.gui.jMenuItemClose.setMnemonic ( Messages
                .getString ( "MainWindow.CloseMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
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
            MainWindow.this.gui.jMenuItemSaveAs.setMnemonic ( Messages
                .getString ( "MainWindow.SaveAsMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
            MainWindow.this.gui.jButtonSaveAs.setToolTipText ( Messages
                .getString ( "MainWindow.SaveAsToolTip" ) ); //$NON-NLS-1$
            // SaveAll
            MainWindow.this.gui.jMenuItemSaveAll.setText ( Messages
                .getString ( "MainWindow.SaveAll" ) ); //$NON-NLS-1$
            MainWindow.this.gui.jMenuItemSaveAll.setMnemonic ( Messages
                .getString ( "MainWindow.SaveAllMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
            // RecentlyUsed
            MainWindow.this.gui.jMenuRecentlyUsed.setText ( Messages
                .getString ( "MainWindow.RecentlyUsed" ) ); //$NON-NLS-1$
            MainWindow.this.gui.jMenuRecentlyUsed.setMnemonic ( Messages
                .getString ( "MainWindow.RecentlyUsedMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
            // Quit
            MainWindow.this.gui.jMenuItemQuit.setText ( Messages
                .getString ( "MainWindow.Quit" ) ); //$NON-NLS-1$
            MainWindow.this.gui.jMenuItemQuit.setMnemonic ( Messages
                .getString ( "MainWindow.QuitMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
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
            MainWindow.this.gui.jButtonCut.setToolTipText ( Messages
                .getString ( "MainWindow.CutToolTip" ) ); //$NON-NLS-1$
            // Copy
            MainWindow.this.gui.jMenuItemCopy.setText ( Messages
                .getString ( "MainWindow.Copy" ) ); //$NON-NLS-1$
            MainWindow.this.gui.jMenuItemCopy.setMnemonic ( Messages.getString (
                "MainWindow.CopyMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
            MainWindow.this.gui.jButtonCopy.setToolTipText ( Messages
                .getString ( "MainWindow.CopyToolTip" ) ); //$NON-NLS-1$           
            // Paste
            MainWindow.this.gui.jMenuItemPaste.setText ( Messages
                .getString ( "MainWindow.Paste" ) ); //$NON-NLS-1$
            MainWindow.this.gui.jMenuItemPaste.setMnemonic ( Messages.getString (
                "MainWindow.PasteMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
            MainWindow.this.gui.jButtonPaste.setToolTipText ( Messages
                .getString ( "MainWindow.PasteToolTip" ) ); //$NON-NLS-1$
            // Undo
            MainWindow.this.gui.jMenuItemUndo.setText ( Messages
                .getString ( "MainWindow.Undo" ) ); //$NON-NLS-1$
            MainWindow.this.gui.jMenuItemUndo.setMnemonic ( Messages.getString (
                "MainWindow.UndoMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
            MainWindow.this.gui.jButtonUndo.setToolTipText ( Messages
                .getString ( "MainWindow.UndoToolTip" ) ); //$NON-NLS-1$           
            // Redo
            MainWindow.this.gui.jMenuItemRedo.setText ( Messages
                .getString ( "MainWindow.Redo" ) ); //$NON-NLS-1$
            MainWindow.this.gui.jMenuItemRedo.setMnemonic ( Messages.getString (
                "MainWindow.RedoMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
            MainWindow.this.gui.jButtonRedo.setToolTipText ( Messages
                .getString ( "MainWindow.RedoToolTip" ) ); //$NON-NLS-1$              
            // Preferences
            MainWindow.this.gui.jMenuItemPreferences.setText ( Messages
                .getString ( "MainWindow.Preferences" ) ); //$NON-NLS-1$
            MainWindow.this.gui.jMenuItemPreferences.setMnemonic ( Messages
                .getString ( "MainWindow.PreferencesMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
            // Help
            MainWindow.this.gui.jMenuHelp.setText ( Messages
                .getString ( "MainWindow.Help" ) ); //$NON-NLS-1$
            MainWindow.this.gui.jMenuHelp.setMnemonic ( Messages.getString (
                "MainWindow.HelpMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
            // About
            MainWindow.this.gui.jMenuItemAbout.setText ( Messages
                .getString ( "MainWindow.About" ) ); //$NON-NLS-1$
            MainWindow.this.gui.jMenuItemAbout.setMnemonic ( Messages
                .getString ( "MainWindow.AboutMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
          }
        } );
  }


  /**
   * Handle the action event of the about item.
   */
  public void handleAbout ()
  {
    AboutDialog aboutDialog = new AboutDialog ( this.gui );
    aboutDialog.show ();
  }


  /**
   * Handle the open event.
   */
  public void handleNew ()
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
      this.gui.jTabbedPaneMain.setTitleAt ( this.gui.jTabbedPaneMain
          .getSelectedIndex (), "newFile" + count + ".test" ); //$NON-NLS-1$ //$NON-NLS-2$
      count++ ;
      setGeneralStates ( true );
    }
  }


  /**
   * Handles the open event.
   */
  public void handleOpen ()
  {
    PreferenceManager prefmanager = PreferenceManager.getInstance ();
    JFileChooser chooser = new JFileChooser ( prefmanager.getWorkingPath () );
    chooser.setMultiSelectionEnabled ( true );
    chooser.showOpenDialog ( this.gui );
    prefmanager.setWorkingPath ( chooser.getCurrentDirectory ()
        .getAbsolutePath () );
  }


  /**
   * Handle the action event of the preferences item.
   */
  public void handlePreferences ()
  {
    PreferencesDialog preferencesDialog = new PreferencesDialog ( this.gui );
    preferencesDialog.show ();
  }


  /**
   * Handles the quit event.
   */
  public void handleQuit ()
  {
    PreferenceManager preferenceManager = PreferenceManager.getInstance ();
    preferenceManager.setMainWindowPreferences ( this.gui );
    System.exit ( 0 );
  }


  /**
   * Sets general states for items and buttons.
   * 
   * @param pState The new state.
   */
  private void setGeneralStates ( boolean pState )
  {
    // SaveAs
    this.gui.jButtonSaveAs.setEnabled ( pState );
    this.gui.jMenuItemSaveAs.setEnabled ( pState );

    // SaveAll
    this.gui.jMenuItemSaveAll.setEnabled ( pState );

    // Close
    this.gui.jMenuItemClose.setEnabled ( pState );

    // Cut
    // this.gui.jButtonCut.setEnabled ( pState );
    this.gui.jButtonCut.setVisible ( false );
    // this.gui.jMenuItemCut.setEnabled ( pState );
    this.gui.jMenuItemCut.setVisible ( false );

    // Copy
    // this.gui.jButtonCopy.setEnabled ( pState );
    this.gui.jButtonCopy.setVisible ( false );
    // this.gui.jMenuItemCopy.setEnabled ( pState );
    this.gui.jMenuItemCopy.setVisible ( false );

    // Paste
    // this.gui.jButtonPaste.setEnabled ( pState );
    this.gui.jButtonPaste.setVisible ( false );
    // this.gui.jMenuItemPaste.setEnabled ( pState );
    this.gui.jMenuItemPaste.setVisible ( false );

    // Undo
    this.gui.jButtonUndo.setVisible ( false );
    this.gui.jMenuItemUndo.setVisible ( false );
    setUndoState ( pState );

    // Redo
    this.gui.jButtonRedo.setVisible ( false );
    this.gui.jMenuItemRedo.setVisible ( false );
    setRedoState ( pState );

    // Separator
    this.gui.jSeparatorEdit1.setVisible ( false );
    this.gui.jSeparatorEdit2.setVisible ( false );
  }


  /**
   * Sets the state of the redo button and item.
   * 
   * @param pState The new state for redo.
   */
  private void setRedoState ( boolean pState )
  {
    this.gui.jButtonRedo.setEnabled ( pState );
    this.gui.jMenuItemRedo.setEnabled ( pState );
  }


  /**
   * Sets the state of the save button and item.
   * 
   * @param pState The new state for save.
   */
  @SuppressWarnings ( "unused" )
  private void setSaveState ( boolean pState )
  {
    this.gui.jButtonSave.setEnabled ( pState );
    this.gui.jMenuItemSave.setEnabled ( pState );
  }


  /**
   * Sets the state of the undo button and item.
   * 
   * @param pState The new state for undo.
   */
  private void setUndoState ( boolean pState )
  {
    this.gui.jButtonUndo.setEnabled ( pState );
    this.gui.jMenuItemUndo.setEnabled ( pState );
  }
}
