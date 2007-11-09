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
    // RecentlyUsedFiles
    this.gui.jMenuRecentlyUsedFiles.setEnabled ( false );

    /*
     * Language changed listener
     */
    PreferenceManager.getInstance ().addLanguageChangedListener (
        new LanguageChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void languageChanged ()
          {
            MainWindow.this.gui.jMenuItemAbout.setText ( Messages
                .getString ( "MainWindow.About" ) ); //$NON-NLS-1$
            // TODOChristian Complete this list
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
