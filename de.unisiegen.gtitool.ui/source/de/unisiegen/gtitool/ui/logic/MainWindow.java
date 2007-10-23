/*
 * MainWindow.java Created on 15. September 2006, 14:56
 */
package de.unisiegen.gtitool.ui.logic;


import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFileChooser;

import de.unisiegen.gtitool.ui.EditorPanel;
import de.unisiegen.gtitool.ui.PreferenceManager;
import de.unisiegen.gtitool.ui.Versions;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


/**
 * The main programm window.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public class MainWindow
{

  //
  // Constants
  //
  /**
   * The unique serialization identifier for this class.
   */
  private static final long serialVersionUID = -3820623104618482450L;


  /** The Main Window Frame */
  private MainWindowForm window;
  
  private static int num = 0;


  // private PropertyChangeListener editorPanelListener;

  /**
   * The preferences for the <code>de.unisiegen.tpml.ui</code> package.
   */
  // private static final Preferences preferences = Preferences
  // .userNodeForPackage(MainWindow.class);
  //
  // Constructor
  //
  /**
   * Creates new form <code>MainWindow</code>.
   */
  public MainWindow ()
  {
    this.window = new MainWindowForm ();
    this.window.setMainWindow ( this );

    // TODOBenny clean up code

    // initComponents ( ) ;
    this.window.setTitle ( "GTI Tool " + Versions.UI ); //$NON-NLS-1$
    // position the window
    PreferenceManager prefmanager = PreferenceManager.getInstance ();
    this.window.setBounds ( prefmanager.getMainWindowBounds () );
    this.window.setVisible ( true );
    // Setting the default states
    setGeneralStates ( false );
    this.window.saveItem.setEnabled ( false );
    this.window.saveButton.setEnabled ( false );
    this.window.preferencesItem.setEnabled ( true );
    this.window.copyItem.setEnabled ( false );
    this.window.pasteItem.setEnabled ( false );
    this.window.recentFilesMenu.setEnabled ( false );
    this.window.cutButton.setEnabled ( false );
    this.window.copyButton.setEnabled ( false );
    this.window.pasteButton.setEnabled ( false );
    // Finished setting the states.
    this.window.addWindowListener ( new WindowAdapter ()
    {

      @Override
      public void windowClosing ( @SuppressWarnings ( "unused" )
      WindowEvent e )
      {
        MainWindow.this.handleQuit ();
      }
    } );

    // this.editorPanelListener = new PropertyChangeListener ( )
    // {
    // public void propertyChange ( PropertyChangeEvent evt )
    // {
    //            
    // editorStatusChange ( evt.getPropertyName ( ) , evt.getNewValue ( ) ) ;
    // }
    // } ;

    // KeyboardFocusManager.getCurrentKeyboardFocusManager ( )
    // .addKeyEventDispatcher ( new KeyEventDispatcher ( )
    // {
    // public boolean dispatchKeyEvent ( KeyEvent evt )
    // {
    // if ( ( evt.getID ( ) == KeyEvent.KEY_PRESSED ) )
    // {
    // if ( ( ( evt.getKeyCode ( ) == KeyEvent.VK_RIGHT ) && evt
    // .isAltDown ( ) )
    // || ( ( evt.getKeyCode ( ) == KeyEvent.VK_PAGE_DOWN ) && evt
    // .isControlDown ( ) ) )
    // {
    // if ( window.tabbedPane.getSelectedIndex ( ) + 1 == window.tabbedPane
    // .getTabCount ( ) )
    // {
    // window.tabbedPane.setSelectedIndex ( 0 ) ;
    // return true ;
    // }
    // else
    // {
    // window.tabbedPane
    // .setSelectedIndex ( window.tabbedPane.getSelectedIndex ( ) + 1 ) ;
    // return true ;
    // }
    // }
    // else
    // {
    // if ( ( ( evt.getKeyCode ( ) == KeyEvent.VK_LEFT ) && evt
    // .isAltDown ( ) )
    // || ( ( evt.getKeyCode ( ) == KeyEvent.VK_PAGE_UP ) && evt
    // .isControlDown ( ) ) )
    // {
    // if ( window.tabbedPane.getSelectedIndex ( ) == 0 )
    // {
    // window.tabbedPane
    // .setSelectedIndex ( window.tabbedPane.getTabCount ( ) - 1 ) ;
    // return true ;
    // }
    // else
    // {
    // window.tabbedPane.setSelectedIndex ( window.tabbedPane
    // .getSelectedIndex ( ) - 1 ) ;
    // return true ;
    // }
    // }
    // }
    // }
    // return false ;
    // }
    // } ) ;
    // this.recentlyUsed = prefmanager.getRecentlyUsed ( ) ;
    // // TODO this is ugly :(
    // for ( int i = 0 ; i < recentlyUsed.size ( ) ; i ++ )
    // {
    // recentlyUsed.get ( i ).setWindow ( this ) ;
    // }
    // updateRecentlyUsed ( ) ;
    // // apply the last "advanced mode" setting
    // boolean advanced = prefmanager.getAdvanced ( ) ;
    // window.advancedRadioButton.setSelected ( advanced ) ;
    // window.beginnerRadioButton.setSelected ( ! advanced ) ;
    // // apply the last maximization state
    // if ( prefmanager.getWindowMaximized ( ) )
    // {
    //
    // // set to maximized
    // window
    // .setExtendedState ( window.getExtendedState ( ) | JFrame.MAXIMIZED_BOTH )
    // ;
    // }
  }


  // // End of variables declaration//GEN-END:variables
  // private PropertyChangeListener editorPanelListener ;
  //
  //
  // private static final Logger logger = Logger.getLogger ( MainWindow.class )
  // ;
  //
  //
  // // private PreferenceManager prefmanager;
  // private static int historyLength = 9 ;
  //
  //
  // private LinkedList < HistoryItem > recentlyUsed ;
  //
  //
  // // Self-defined methods:
  // void openFile ( File file )
  // {
  // if ( file == null )
  // {
  // throw new NullPointerException ( "file is null" ) ;
  // }
  // try
  // {
  // // check if we already have an editor panel for the file
  // EditorPanel editorPanel = null ;
  // for ( Component component : window.tabbedPane.getComponents ( ) )
  // {
  // if ( component instanceof EditorPanelForm
  // && file.equals ( ( (( EditorPanelForm ) component).getCaller() ).getFile (
  // ) ) )
  // {
  // editorPanel = (( EditorPanelForm ) component).getCaller() ;
  // break ;
  // }
  // }
  // // if we don't already have the editor panel, create a new one
  // if ( editorPanel == null )
  // {
  // LanguageFactory langfactory = LanguageFactory.newInstance ( ) ;
  // Language language = langfactory.getLanguageByFile ( file ) ;
  // String str = null ;
  // StringBuilder buffer = new StringBuilder ( ) ;
  // int onechar ;
  // try
  // {
  // BufferedReader in = new BufferedReader ( new InputStreamReader (
  // new FileInputStream ( file ) , "UTF8" ) ) ;
  // while ( ( onechar = in.read ( ) ) != - 1 )
  // {
  // buffer.append ( ( char ) onechar ) ;
  // }
  // in.close ( ) ;
  // }
  // catch ( UnsupportedEncodingException e )
  // {
  // System.err.println ( "UnsupportedEncodingException" ) ;
  // }
  // if (language.isTypeLanguage ( )){
  // editorPanel = new EditorPanelTypes ( language , this ) ;
  // }
  // else {
  // editorPanel = new EditorPanelExpression ( language , this ) ;
  // }
  // window.tabbedPane.add ( ( Component ) editorPanel.getPanel() ) ;
  // editorPanel.setAdvanced ( window.advancedRadioButton.isSelected ( ) ) ;
  // editorPanel.setFileName ( file.getName ( ) ) ;
  // editorPanel.setEditorText ( buffer.toString ( ) ) ;
  // editorPanel.setFile ( file ) ;
  // editorPanel.addPropertyChangeListener ( editorPanelListener ) ;
  // editorPanel.setTexteditor ( true ) ;
  // }
  // window.tabbedPane.setSelectedComponent ( ( Component )
  // editorPanel.getPanel() ) ;
  // setGeneralStates ( true ) ;
  // updateEditorStates ( editorPanel ) ;
  // }
  // catch ( NoSuchLanguageException e )
  // {
  // logger.error ( "Language does not exist." , e ) ;
  // JOptionPane.showMessageDialog ( window , java.util.ResourceBundle
  // .getBundle ( "de/unisiegen/tpml/ui/ui" ).getString (
  // "FileNotSupported" ) , java.util.ResourceBundle.getBundle (
  // "de/unisiegen/tpml/ui/ui" ).getString ( "Open_File" ) ,
  // JOptionPane.ERROR_MESSAGE ) ;
  // }
  // catch ( FileNotFoundException e )
  // {
  // logger.error ( "File specified could not be found" , e ) ;
  // JOptionPane.showMessageDialog ( window , java.util.ResourceBundle
  // .getBundle ( "de/unisiegen/tpml/ui/ui" ).getString (
  // "FileCannotBeFound" ) , java.util.ResourceBundle.getBundle (
  // "de/unisiegen/tpml/ui/ui" ).getString ( "Open_File" ) ,
  // JOptionPane.ERROR_MESSAGE ) ;
  // }
  // catch ( IOException e )
  // {
  // logger.error ( "Could not read from the file specified" , e ) ;
  // JOptionPane.showMessageDialog ( window , java.util.ResourceBundle
  // .getBundle ( "de/unisiegen/tpml/ui/ui" ).getString (
  // "FileCannotBeRead" ) , java.util.ResourceBundle.getBundle (
  // "de/unisiegen/tpml/ui/ui" ).getString ( "Open_File" ) ,
  // JOptionPane.ERROR_MESSAGE ) ;
  // }
  // }
  //
  //
  // private void editorStatusChange ( String ident , Object newValue )
  // {
  // // logger.debug ( "Editor status changed: " + ident ) ;
  // // if ( ident.equals ( "redoStatus" ) )
  // // {
  // // logger.debug ( "Editor status changed. Ident: redoStatus" ) ;
  // // setRedoState ( ( Boolean ) newValue ) ;
  // // }
  // // else if ( ident.equals ( "filename" ) )
  // // {
  // // logger.debug ( "Editor status changed. Ident: filename" ) ;
  // // tabbedPane.setTitleAt ( tabbedPane.getSelectedIndex ( ) ,
  // // ( String ) newValue ) ;
  // // // TODO merge undostatus and changestatus
  // // }
  // // else if ( ident.equals ( "undoStatus" ) )
  // // {
  // // logger.debug ( "Editor status changed. Ident: undoStatus" ) ;
  // // setUndoState ( ( Boolean ) newValue ) ;
  // // // setChangeState((Boolean) newValue);
  // // }
  // // else if ( ident.equals ( "changed" ) )
  // // {
  // // logger.debug ( "Editor status changed. Ident: changed" ) ;
  // // setChangeState ( ( Boolean ) newValue ) ;
  // // // setSaveState((Boolean) newValue);
  // // }
  // // else if ( ident.equals ( "texteditor" ) )
  // // {
  // // logger.debug ( "Editor status changed. Ident: textditor" ) ;
  // // cutItem.setEnabled ( ( Boolean ) newValue ) ;
  // // cutButton.setEnabled ( ( Boolean ) newValue ) ;
  // // copyItem.setEnabled ( ( Boolean ) newValue ) ;
  // // copyButton.setEnabled ( ( Boolean ) newValue ) ;
  // // pasteItem.setEnabled ( ( Boolean ) newValue ) ;
  // // pasteButton.setEnabled ( ( Boolean ) newValue ) ;
  // // }
  // updateEditorStates((EditorPanel)((EditorPanelForm)
  // window.tabbedPane.getSelectedComponent()).getCaller());
  // }
  //
  // public void updateEditorStates (EditorPanel editor){
  // updateEditorStates((EditorPanelForm)editor.getPanel());
  // }
  //
  // public void updateEditorStates ( EditorPanelForm editorform )
  // {
  // if ( editorform == null )
  // {// last tab was closed
  // setGeneralStates ( false ) ;
  // // }
  // // if (getActiveEditor() == null) { // the same as above?
  // // setGeneralStates(false);
  // }
  // else
  // {
  // EditorPanel editor = editorform.getCaller();
  // setRedoState ( editor.isRedoStatus ( ) ) ;
  // setUndoState ( editor.isUndoStatus ( ) ) ;
  // // setSaveState(editor.isUndoStatus());
  // if ( editor.isTexteditor ( ) )
  // {
  // setChangeState ( editor.isSaveStatus ( ) ) ;
  // setEditorFunctions ( true ) ;
  // }
  // else
  // {
  // setChangeState (editor.isSaveStatus());
  // setEditorFunctions ( false ) ;
  // }
  // }
  // if (this.getActiveEditor ( ) instanceof EditorPanelExpression)
  // setExpressionMode();
  // else
  // setTypeMode();
  // }
  //
  //
  // private void setEditorFunctions ( boolean state )
  // {
  // window.cutButton.setEnabled ( state ) ;
  // window.cutItem.setEnabled ( state ) ;
  // window.copyButton.setEnabled ( state ) ;
  // window.copyItem.setEnabled ( state ) ;
  // window.pasteButton.setEnabled ( state ) ;
  // window.pasteItem.setEnabled ( state ) ;
  // }
  //
  //
  // private void updateRecentlyUsed ( )
  // {
  // final int length = ( this.recentlyUsed.size ( ) > historyLength ) ?
  // historyLength
  // : this.recentlyUsed.size ( ) ;
  // if ( length > historyLength )
  // {
  // logger.error ( "Error: The list of recently used files is larger than "
  // + historyLength ) ;
  // }
  // HistoryItem item ;
  // window.recentFilesMenu.setVisible ( length > 0 ) ;
  // window.fileMenuSeperator3.setVisible ( length > 0 ) ;
  // window.recentFilesMenu.removeAll ( ) ;
  // for ( int i = 0 ; i < length ; i ++ )
  // {
  // item = this.recentlyUsed.get ( i ) ;
  // item.setText ( "" + ( i + 1 ) + ". " + item.getFile ( ).getName ( ) ) ;
  // window.recentFilesMenu.add ( item ) ;
  // }
  // window.recentFilesMenu.addSeparator ( ) ;
  // JMenuItem openAllItem = new JMenuItem ( ResourceBundle.getBundle (
  // "de/unisiegen/tpml/ui/ui" ).getString ( "OpenAll" ) ) ;
  // openAllItem
  // .setMnemonic ( ResourceBundle.getBundle ( "de/unisiegen/tpml/ui/ui" )
  // .getString ( "OpenAllMnemonic" ).charAt ( 0 ) ) ;
  // openAllItem.addActionListener ( new ActionListener ( )
  // {
  // public void actionPerformed ( ActionEvent e )
  // {
  // for ( int i = 0 ; i < length ; ++ i )
  // {
  // File file = MainWindow.this.recentlyUsed.get ( i ).getFile ( ) ;
  // openFile ( file ) ;
  // }
  // }
  // } ) ;
  // window.recentFilesMenu.add ( openAllItem ) ;
  // }
  //
  //
  // public void addRecentlyUsed ( HistoryItem historyItem )
  // {
  // boolean alreadyPresent = false ;
  // // check if a similar entry in the history already exists:
  // for ( int i = 0 ; i < recentlyUsed.size ( ) ; i ++ )
  // {
  // if ( recentlyUsed.get ( i ).getFile ( ).toURI ( ).equals (
  // historyItem.getFile ( ).toURI ( ) ) )
  // {
  // alreadyPresent = true ;
  // }
  // }
  // if ( ! alreadyPresent ) recentlyUsed.addFirst ( historyItem ) ;
  // historyItem.setWindow ( this ) ;
  // if ( recentlyUsed.size ( ) > historyLength ) recentlyUsed.removeLast ( ) ;
  // updateRecentlyUsed ( ) ;
  // }
  //
  //
  // private void setChangeState ( Boolean state )
  // {
  // if ( state )
  // {
  // window.tabbedPane.setTitleAt ( window.tabbedPane.getSelectedIndex ( ) , "*"
  // + ( (( EditorPanelForm ) window.tabbedPane.getSelectedComponent ( )
  // )).getCaller()
  // .getFileName ( ) ) ;
  // setSaveState ( true ) ;
  // }
  // else
  // {
  // window.tabbedPane.setTitleAt ( window.tabbedPane.getSelectedIndex ( ) ,
  // (( ( EditorPanelForm ) window.tabbedPane.getSelectedComponent ( )
  // )).getCaller()
  // .getFileName ( ) ) ;
  // setSaveState ( false ) ;
  // }
  // }
  //
  //
  // public EditorPanel getActiveEditor ( )
  // {
  // if (window.tabbedPane.getSelectedComponent ( ) == null) return null;
  // return (( EditorPanelForm ) window.tabbedPane.getSelectedComponent (
  // )).getCaller();
  // }
  //
  //
  //
  //
  // private void setTypeMode ( ) {
  // window.smallstepItem.setVisible ( false ) ;
  // window.bigstepItem.setVisible ( false ) ;
  // window.typecheckerItem.setVisible ( false ) ;
  // window.minimaltypingItem.setVisible ( false ) ;
  // window.typeinferenceItem.setVisible ( false ) ;
  // window.subtypingItem.setVisible ( true ) ;
  // window.subtypingrecItem.setVisible ( true );
  // }
  //
  //
  // private void setExpressionMode ( ) {
  //
  // window.smallstepItem.setVisible ( true ) ;
  // window.bigstepItem.setVisible ( true ) ;
  // window.typecheckerItem.setVisible ( true ) ;
  // window.minimaltypingItem.setVisible ( true ) ;
  // window.typeinferenceItem.setVisible ( true ) ;
  // window.subtypingItem.setVisible ( false ) ;
  // window.subtypingrecItem.setVisible ( false );
  // }
  //
  //

  //
  //
  // /**
  // * Closes the active editor window.
  // *
  // * @return true if the active editor could be closed.
  // */
  // public boolean handleClose ( )
  // {
  // EditorPanel selectedEditor = getActiveEditor ( ) ;
  // boolean success ;
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
  // {
  // window.tabbedPane.remove ( window.tabbedPane.getSelectedIndex ( ) ) ;
  // window.repaint ( ) ;
  // success = true ;
  // }
  // if ( getActiveEditor ( ) == null )
  // {
  // setGeneralStates ( false ) ;
  // window.saveItem.setEnabled ( false ) ;
  // window.saveButton.setEnabled ( false ) ;
  // }
  // return success ;
  // }
  //
  //
  // public void handleSaveAll ( )
  // {
  // int tabcount = window.tabbedPane.getComponentCount ( ) ;
  // for ( int i = 0 ; i < tabcount ; i ++ )
  // {
  // if ( ! (( ( EditorPanelForm ) window.tabbedPane.getComponentAt ( i )
  // )).getCaller().handleSave ( ) )
  // return ;
  // }
  // }
  //
  //
  // /**
  // * Stores the list of open files for the next start (see
  // * {@link #restoreOpenFiles()}), that is the list of files from the
  // * {@link EditorPanel}s that have valid <code>File</code> objects. This is
  // * called exactly once on quit.
  // *
  // * @see #restoreOpenFiles()
  // */
  // public void saveOpenFiles ( )
  // {
  // int tabcount = window.tabbedPane.getComponentCount ( ) ;
  // LinkedList < File > filelist = new LinkedList < File > ( ) ;
  // File file ;
  // for ( int i = 0 ; i < tabcount ; i ++ )
  // {
  // file = ( ( EditorPanelForm ) window.tabbedPane.getComponentAt ( i )
  // ).getCaller().getFile ( ) ;
  // if ( file != null )
  // {
  // filelist.add ( file ) ;
  // }
  // }
  // PreferenceManager.get ( ).setOpenFiles ( filelist ) ;
  // }
  //
  //
  // /**
  // * Restores the list of open files from a previous session, previously saved
  // * by the {@link #saveOpenFiles()} method. This is called on startup if no
  // * files where provided.
  // *
  // * @see #saveOpenFiles()
  // */
  // public void restoreOpenFiles ( )
  // {
  // LinkedList < File > filelist = PreferenceManager.get ( ).getOpenFiles ( ) ;
  // File currentfile ;
  // for ( int i = 0 ; i < filelist.size ( ) ; i ++ )
  // {
  // currentfile = filelist.get ( i ) ;
  // if ( currentfile.exists ( ) && currentfile.canRead ( ) )
  // {
  // openFile ( currentfile ) ;
  // }
  // }
  // }

  /**
   * Handle the action event of the about item
   */
  public void handleAbout ()
  {
    AboutDialog aboutDialog = new AboutDialog ( this.window );
    aboutDialog.show ();
  }


  /**
   * Handle the open event
   */public void handleNew ()
   {
     NewDialog newDialog = new NewDialog ( window );
     // newDialog.setLocationRelativeTo ( window ) ;
     newDialog.show ();
     
     EditorPanel newEditorPanel = newDialog.getEditorPanel ();
     if ( newEditorPanel != null )
     {
       

       this.window.tabbedPane.add ( ( Component ) newEditorPanel.getPanel () );
       this.window.tabbedPane.setSelectedComponent ( ( Component ) newEditorPanel
           .getPanel () );
       this.window.tabbedPane.setTitleAt ( this.window.tabbedPane.getSelectedIndex (),
           "newFile" + num + ".test" );
       num++ ;
       // newEditorPanel.addPropertyChangeListener ( editorPanelListener ) ;
       setGeneralStates ( true );
       // updateEditorStates ( newEditorPanel );
     }
   }
  public void handleOpen ()
  {
    PreferenceManager prefmanager = PreferenceManager.getInstance ();
    JFileChooser chooser = new JFileChooser ( prefmanager.getWorkingPath () );
    chooser.setMultiSelectionEnabled ( true );

    // final LanguageFactory factory = LanguageFactory.newInstance ( ) ;
    // chooser.addChoosableFileFilter ( new FileFilter ( )
    // {
    // @ Override
    // public boolean accept ( File f )
    // {
    // if ( f.isDirectory ( ) )
    // {
    // return true ;
    // }
    // try
    // {
    // factory.getLanguageByFile ( f ) ;
    // return true ;
    // }
    // catch ( NoSuchLanguageException e )
    // {
    // return false ;
    // }
    // }
    //
    //
    // @ Override
    // public String getDescription ( )
    // {
    // Language [ ] languages = factory.getAvailableLanguages ( ) ;
    // StringBuilder builder = new StringBuilder ( 128 ) ;
    // builder.append ( "Source Files (" ) ;
    // for ( int n = 0 ; n < languages.length ; ++ n )
    // {
    // if ( n > 0 )
    // {
    // builder.append ( "; " ) ;
    // }
    // builder.append ( "*." ) ;
    // builder.append ( languages [ n ].getName ( ).toLowerCase ( ) ) ;
    // }
    // builder.append ( ')' ) ;
    // return builder.toString ( ) ;
    // }
    // } ) ;
    // chooser.setAcceptAllFileFilterUsed ( false ) ;

    int returnVal = chooser.showOpenDialog ( this.window );

    // if ( returnVal == JFileChooser.APPROVE_OPTION )
    // {
    // File [ ] files = chooser.getSelectedFiles ( ) ;
    // for ( int i = 0 ; i < files.length ; i ++ )
    // {
    // openFile ( files [ i ] ) ;
    // }
    // }

    prefmanager.setWorkingPath ( chooser.getCurrentDirectory ()
        .getAbsolutePath () );
  }


  /**
   * handle the close event
   */
  public void handleQuit ()
  {
    // // be sure to save all files first
    // for ( Component component : window.tabbedPane.getComponents ( ) )
    // {
    // if ( component instanceof EditorPanelForm )
    // {
    // EditorPanel editorPanel = (( EditorPanelForm ) component).getCaller() ;
    // if ( ! editorPanel.shouldBeSaved ( ) )
    // {
    // continue ;
    // }
    // // Custom button text
    // Object [ ] options =
    // {
    // java.util.ResourceBundle.getBundle ( "de/unisiegen/tpml/ui/ui" )
    // .getString ( "Yes" ) ,
    // java.util.ResourceBundle.getBundle ( "de/unisiegen/tpml/ui/ui" )
    // .getString ( "No" ) ,
    // java.util.ResourceBundle.getBundle ( "de/unisiegen/tpml/ui/ui" )
    // .getString ( "Cancel" ) } ;
    // int n = JOptionPane.showOptionDialog ( window , editorPanel
    // .getFileName ( )
    // + " "
    // + java.util.ResourceBundle.getBundle ( "de/unisiegen/tpml/ui/ui" )
    // .getString ( "WantTosave" ) , java.util.ResourceBundle
    // .getBundle ( "de/unisiegen/tpml/ui/ui" ).getString ( "Save_File" ) ,
    // JOptionPane.YES_NO_CANCEL_OPTION , JOptionPane.QUESTION_MESSAGE ,
    // null , options , options [ 2 ] ) ;
    // switch ( n )
    // {
    // case 0 : // Save changes
    // logger.debug ( "Quit dialog: YES" ) ;
    // if ( ! editorPanel.handleSave ( ) )
    // {
    // // abort the quit
    // return ;
    // }
    // break ;
    // case 1 : // Do not save changes
    // logger.debug ( "Quit dialog: NO" ) ;
    // break ;
    // default : // Cancelled
    // logger.debug ( "Quit dialog: CANCEL" ) ;
    // return ;
    // }
    // }
    // }
    // // save the session
    // saveOpenFiles ( ) ;
    // // remember the settings
    PreferenceManager prefmanager = PreferenceManager.getInstance ();
    // prefmanager.setAdvanced ( window.advancedRadioButton.isSelected ( ) ) ;
    // // remember the history
    // prefmanager.setRecentlyUsed ( recentlyUsed ) ;
    // // remember window state
    prefmanager.setMainWindowPreferences ( this.window );
    // // terminate the application
    System.exit ( 0 );
  }


  /**
   * set general states for items and buttons
   * 
   * @param state the new state
   */
  private void setGeneralStates ( boolean state )
  {
    this.window.saveAsItem.setEnabled ( state );
    this.window.saveAsButton.setEnabled ( state );
    this.window.saveAllItem.setEnabled ( state );
    this.window.closeItem.setEnabled ( state );
//    this.window.cutItem.setEnabled ( state );
    this.window.cutButton.setVisible ( false );
//    this.window.cutButton.setEnabled ( state );
    this.window.cutButton.setVisible(false);
//    this.window.copyItem.setEnabled ( state );
    this.window.copyItem.setVisible ( false );
//    this.window.copyButton.setEnabled ( state );
    this.window.copyButton.setVisible ( false );
//    this.window.pasteItem.setEnabled ( state );
    this.window.pasteItem.setVisible ( false );
//    this.window.pasteButton.setEnabled ( state );
    this.window.pasteButton.setVisible ( false );
    this.window.undoButton.setVisible ( false );
    this.window.undoItem.setVisible ( false );
    this.window.redoButton.setVisible ( false );
    this.window.redoItem.setVisible ( false );
    setUndoState ( state );
    setRedoState ( state );
  }


  /**
   * Set the state of the redo button and item
   * 
   * @param state the new state for redo
   */
  private void setRedoState ( boolean state )
  {
    this.window.redoButton.setEnabled ( state );
    this.window.redoItem.setEnabled ( state );
  }


  /**
   * Set the state of the undo button and item
   * 
   * @param state the new state for undo
   */
  private void setUndoState ( boolean state )
  {
    // logger.debug ( "UndoStatus of MainWindow set to " + state ) ;
    this.window.undoButton.setEnabled ( state );
    this.window.undoItem.setEnabled ( state );
  }


  /**
   * Set the state of the save button and item
   * 
   * @param state the new state for save
   */
  private void setSaveState ( boolean state )
  {
    this.window.saveButton.setEnabled ( state );
    this.window.saveItem.setEnabled ( state );
  }


}
