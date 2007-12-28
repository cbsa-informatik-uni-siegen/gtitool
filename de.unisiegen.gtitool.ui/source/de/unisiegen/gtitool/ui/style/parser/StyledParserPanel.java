package de.unisiegen.gtitool.ui.style.parser;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;

import de.unisiegen.gtitool.core.entities.ParseableEntity;
import de.unisiegen.gtitool.core.parser.Parseable;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.preferences.listener.LanguageChangedListener;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;
import de.unisiegen.gtitool.ui.style.sidebar.SideBar;
import de.unisiegen.gtitool.ui.style.sidebar.SideBarListener;
import de.unisiegen.gtitool.ui.utils.Clipboard;


/**
 * The styled parser panel class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class StyledParserPanel extends JPanel
{

  /**
   * The history of parsed objects.
   * 
   * @author Christian Fehler
   */
  private class History
  {

    /**
     * The history list.
     */
    private ArrayList < Object > list;


    /**
     * The current index.
     */
    private int index;


    /**
     * Flag that indicates if the neex object should be added.
     */
    private boolean addNextObject;


    /**
     * Allocates a new <code>History</code>.
     */
    public History ()
    {
      this.list = new ArrayList < Object > ();
      this.index = -1;
      this.addNextObject = true;
    }


    /**
     * Add a new object.
     * 
     * @param pNewObject The object to add.
     */
    public final void add ( Object pNewObject )
    {
      if ( this.addNextObject
          && ( ( this.list.size () == 0 ) || !this.list.get (
              this.list.size () - 1 ).toString ().equals (
              pNewObject.toString () ) ) )
      {
        this.list.add ( pNewObject );
        this.index = this.list.size () - 1;
      }
      this.addNextObject = true;
    }


    /**
     * Returns true if a redo can be performed.
     * 
     * @return True if a redo can be performed.
     */
    public final boolean canRedo ()
    {
      return this.index < this.list.size () - 1;
    }


    /**
     * Returns true if a undo can be performed.
     * 
     * @return True if a undo can be performed.
     */
    public final boolean canUndo ()
    {
      return this.index > 0;
    }


    /**
     * Preforms a redo.
     * 
     * @return The next object.
     */
    public final Object redo ()
    {
      this.index++ ;
      this.addNextObject = false;
      return this.list.get ( this.index );
    }


    /**
     * {@inheritDoc}
     * 
     * @see Object#toString()
     */
    @Override
    public final String toString ()
    {
      return "index: " + this.index + ": " + this.list.toString (); //$NON-NLS-1$//$NON-NLS-2$
    }


    /**
     * Preforms a undo.
     * 
     * @return The previous object.
     */
    public final Object undo ()
    {
      this.index-- ;
      this.addNextObject = false;
      return this.list.get ( this.index );
    }
  }


  /**
   * The normal {@link Color}.
   */
  private static final Color NORMAL_COLOR = Color.BLACK;


  /**
   * The error {@link Color}.
   */
  private static final Color ERROR_COLOR = Color.RED;


  /**
   * The {@link StyledParserEditor}.
   */
  private StyledParserEditor editor;


  /**
   * The {@link StyledParserDocument}.
   */
  private StyledParserDocument document;


  /**
   * The {@link JScrollPane}.
   */
  private JScrollPane jScrollPane;


  /**
   * The {@link SideBar}.
   */
  private SideBar sideBar;


  /**
   * Flag that indicates if the panel is editable.
   */
  private boolean editable;


  /**
   * Flag that indicates if the panel is copyable.
   */
  private boolean copyable;


  /**
   * The {@link JPopupMenu}.
   */
  private JPopupMenu jPopupMenu;


  /**
   * The undo {@link JMenuItem}.
   */
  private JMenuItem jMenuItemUndo;


  /**
   * The redo {@link JMenuItem}.
   */
  private JMenuItem jMenuItemRedo;


  /**
   * The cut {@link JMenuItem}.
   */
  private JMenuItem jMenuItemCut;


  /**
   * The copy {@link JMenuItem}.
   */
  private JMenuItem jMenuItemCopy;


  /**
   * The paste {@link JMenuItem}.
   */
  private JMenuItem jMenuItemPaste;


  /**
   * The {@link History}.
   */
  private History history;


  /**
   * Allocates a new <code>StyledPanel</code>.
   * 
   * @param pParseable The input {@link Parseable}.
   */
  public StyledParserPanel ( Parseable pParseable )
  {
    this.editable = true;
    this.copyable = false;
    this.editor = new StyledParserEditor ();

    // PopupMenu
    this.jPopupMenu = new JPopupMenu ();

    // Undo
    this.jMenuItemUndo = new JMenuItem ( Messages
        .getString ( "MainWindow.Undo" ) ); //$NON-NLS-1$
    this.jMenuItemUndo.setMnemonic ( Messages.getString (
        "MainWindow.UndoMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    this.jMenuItemUndo.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/undo16.gif" ) ) ); //$NON-NLS-1$
    this.jMenuItemUndo.setAccelerator ( KeyStroke.getKeyStroke ( KeyEvent.VK_X,
        InputEvent.CTRL_MASK ) );
    this.jMenuItemUndo.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent pEvent )
      {
        handleUndo ();
      }
    } );
    this.jPopupMenu.add ( this.jMenuItemUndo );

    // Redo
    this.jMenuItemRedo = new JMenuItem ( Messages
        .getString ( "MainWindow.Redo" ) ); //$NON-NLS-1$
    this.jMenuItemRedo.setMnemonic ( Messages.getString (
        "MainWindow.RedoMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    this.jMenuItemRedo.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/redo16.gif" ) ) ); //$NON-NLS-1$
    this.jMenuItemRedo.setAccelerator ( KeyStroke.getKeyStroke ( KeyEvent.VK_X,
        InputEvent.CTRL_MASK ) );
    this.jMenuItemRedo.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent pEvent )
      {
        handleRedo ();
      }
    } );
    this.jPopupMenu.add ( this.jMenuItemRedo );

    // Separator
    this.jPopupMenu.addSeparator ();

    // Cut
    this.jMenuItemCut = new JMenuItem ( Messages.getString ( "MainWindow.Cut" ) ); //$NON-NLS-1$
    this.jMenuItemCut.setMnemonic ( Messages.getString (
        "MainWindow.CutMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    this.jMenuItemCut.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/cut16.gif" ) ) ); //$NON-NLS-1$
    this.jMenuItemCut.setAccelerator ( KeyStroke.getKeyStroke ( KeyEvent.VK_X,
        InputEvent.CTRL_MASK ) );
    this.jMenuItemCut.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent pEvent )
      {
        try
        {
          Clipboard.getInstance ().copy (
              StyledParserPanel.this.editor.getSelectedText () );
          StyledParserPanel.this.document
              .remove (
                  StyledParserPanel.this.editor.getSelectionStart (),
                  ( StyledParserPanel.this.editor.getSelectionEnd () - StyledParserPanel.this.editor
                      .getSelectionStart () ) );
        }
        catch ( BadLocationException exc )
        {
          exc.printStackTrace ();
          System.exit ( 1 );
        }
      }
    } );
    this.jPopupMenu.add ( this.jMenuItemCut );

    // Copy
    this.jMenuItemCopy = new JMenuItem ( Messages
        .getString ( "MainWindow.Copy" ) ); //$NON-NLS-1$
    this.jMenuItemCopy.setMnemonic ( Messages.getString (
        "MainWindow.CopyMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    this.jMenuItemCopy.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/copy16.gif" ) ) ); //$NON-NLS-1$
    this.jMenuItemCopy.setAccelerator ( KeyStroke.getKeyStroke ( KeyEvent.VK_C,
        InputEvent.CTRL_MASK ) );
    this.jMenuItemCopy.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent pEvent )
      {
        Clipboard.getInstance ().copy (
            StyledParserPanel.this.editor.getSelectedText () );
      }
    } );
    this.jPopupMenu.add ( this.jMenuItemCopy );

    // Paste
    this.jMenuItemPaste = new JMenuItem ( Messages
        .getString ( "MainWindow.Paste" ) ); //$NON-NLS-1$
    this.jMenuItemPaste.setMnemonic ( Messages.getString (
        "MainWindow.PasteMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    this.jMenuItemPaste.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/paste16.gif" ) ) ); //$NON-NLS-1$
    this.jMenuItemPaste.setAccelerator ( KeyStroke.getKeyStroke (
        KeyEvent.VK_V, InputEvent.CTRL_MASK ) );
    this.jMenuItemPaste.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent pEvent )
      {
        StyledParserPanel.this.editor.replaceSelection ( Clipboard
            .getInstance ().paste () );
      }
    } );
    this.jPopupMenu.add ( this.jMenuItemPaste );

    // LanguageChangedListener
    PreferenceManager.getInstance ().addLanguageChangedListener (
        new LanguageChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void languageChanged ()
          {
            StyledParserPanel.this.jMenuItemCut.setText ( Messages
                .getString ( "MainWindow.Cut" ) ); //$NON-NLS-1$
            StyledParserPanel.this.jMenuItemCut.setMnemonic ( Messages
                .getString ( "MainWindow.CutMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
            StyledParserPanel.this.jMenuItemCopy.setText ( Messages
                .getString ( "MainWindow.Copy" ) ); //$NON-NLS-1$
            StyledParserPanel.this.jMenuItemCopy.setMnemonic ( Messages
                .getString ( "MainWindow.CopyMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
            StyledParserPanel.this.jMenuItemPaste.setText ( Messages
                .getString ( "MainWindow.Paste" ) ); //$NON-NLS-1$
            StyledParserPanel.this.jMenuItemPaste.setMnemonic ( Messages
                .getString ( "MainWindow.PasteMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
          }
        } );

    // Editor
    this.editor.addMouseListener ( new MouseAdapter ()
    {

      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mousePressed ( MouseEvent pEvent )
      {
        if ( pEvent.isPopupTrigger () )
        {
          showPopupMenu ( pEvent );
        }
      }


      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mouseReleased ( MouseEvent pEvent )
      {
        if ( pEvent.isPopupTrigger () )
        {
          showPopupMenu ( pEvent );
        }
      }
    } );

    // Document
    this.document = new StyledParserDocument ( pParseable );
    this.document.addParseableChangedListener ( new ParseableChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void parseableChanged ( Object pNewObject )
      {
        fireParseableChanged ( pNewObject );
      }
    } );
    setLayout ( new BorderLayout () );
    this.jScrollPane = new JScrollPane ();
    this.jScrollPane.setBorder ( new LineBorder ( NORMAL_COLOR ) );
    add ( this.jScrollPane, BorderLayout.CENTER );
    this.sideBar = new SideBar ( this.jScrollPane, this.document, this.editor );
    this.sideBar.addSideBarListener ( new SideBarListener ()
    {

      /**
       * Inserts a given text at the given index.
       * 
       * @param pIndex The index in the text, where the text should be inserted.
       * @param pInsertText The text which should be inserted.
       */
      @SuppressWarnings ( "synthetic-access" )
      public void insertText ( int pIndex, String pInsertText )
      {
        int countSpaces = 0;
        try
        {
          while ( StyledParserPanel.this.document.getText (
              pIndex + countSpaces, 1 ).equals ( " " ) ) //$NON-NLS-1$
          {
            countSpaces++ ;
          }
        }
        catch ( BadLocationException e )
        {
          // Do nothing
        }
        try
        {
          int offset = 0;
          String text = pInsertText;
          if ( ( countSpaces >= 1 )
              && ( text.substring ( 0, 1 ).equals ( " " ) ) ) //$NON-NLS-1$
          {
            text = text.substring ( 1 );
            offset++ ;
            countSpaces-- ;
          }
          if ( ( countSpaces >= 1 )
              && ( text.substring ( text.length () - 1 ).equals ( " " ) ) ) //$NON-NLS-1$
          {
            text = text.substring ( 0, text.length () - 1 );
          }
          StyledParserPanel.this.document.insertString ( pIndex + offset, text,
              null );
        }
        catch ( BadLocationException e )
        {
          // Do nothing
        }
      }


      /**
       * Marks the text with the given offsets.
       * 
       * @param pLeft The left offset of the text which should be marked.
       * @param pRight The right offset of the text which should be marked.
       */
      @SuppressWarnings ( "synthetic-access" )
      public void markText ( int pLeft, int pRight )
      {
        if ( ( StyledParserPanel.this.editor.getSelectionStart () == pLeft )
            && ( StyledParserPanel.this.editor.getSelectionEnd () == pRight ) )
        {
          StyledParserPanel.this.removeSelectedText ();
        }
        else
        {
          StyledParserPanel.this.selectErrorText ( pLeft, pRight );
        }
      }
    } );
    add ( this.sideBar, BorderLayout.WEST );
    this.jScrollPane.setViewportView ( this.editor );
    this.editor.setDocument ( this.document );
    this.editor.setAutoscrolls ( false );
    this.history = new History ();
    this.editor.registerKeyboardAction ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent pEvent )
      {
        handleUndo ();
      }

    }, KeyStroke.getKeyStroke ( KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK ),
        JComponent.WHEN_FOCUSED );

    this.editor.registerKeyboardAction ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent pEvent )
      {
        handleRedo ();
      }

    }, KeyStroke.getKeyStroke ( KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK ),
        JComponent.WHEN_FOCUSED );
    this.document.parse ();
  }


  /**
   * Adds the given {@link ParseableChangedListener}.
   * 
   * @param pListener The {@link ParseableChangedListener}.
   */
  protected final synchronized void addParseableChangedListener (
      ParseableChangedListener pListener )
  {
    this.listenerList.add ( ParseableChangedListener.class, pListener );
  }


  /**
   * Let the listeners know that the {@link Object} has changed.
   * 
   * @param pNewObject The new {@link Object}.
   */
  private final void fireParseableChanged ( Object pNewObject )
  {
    if ( ( pNewObject == null ) && ( this.editable ) )
    {
      this.jScrollPane.setBorder ( new LineBorder ( ERROR_COLOR ) );
    }
    else
    {
      this.jScrollPane.setBorder ( new LineBorder ( NORMAL_COLOR ) );
    }

    // History
    if ( pNewObject != null )
    {
      this.history.add ( pNewObject );
    }

    ParseableChangedListener [] listeners = this.listenerList
        .getListeners ( ParseableChangedListener.class );
    for ( int n = 0 ; n < listeners.length ; ++n )
    {
      listeners [ n ].parseableChanged ( pNewObject );
    }
  }


  /**
   * Returns the document.
   * 
   * @return The document.
   * @see #document
   */
  protected final StyledParserDocument getDocument ()
  {
    return this.document;
  }


  /**
   * Returns the editor.
   * 
   * @return The editor.
   * @see #editor
   */
  protected final StyledParserEditor getEditor ()
  {
    return this.editor;
  }


  /**
   * Returns the {@link JPopupMenu}.
   * 
   * @return The {@link JPopupMenu}.
   * @see #jPopupMenu
   */
  public final JPopupMenu getJPopupMenu ()
  {
    return this.jPopupMenu;
  }


  /**
   * Returns the {@link Object} for the program text within the document. Throws
   * an exception if a parsing error occurred.
   * 
   * @return The {@link Object} for the program text.
   * @throws Exception If a parsing error occurred.
   */
  protected final Object getParsedObject () throws Exception
  {
    return this.document.getParsedObject ();
  }


  /**
   * Handles the redo action.
   */
  private final void handleRedo ()
  {
    if ( this.history.canRedo () )
    {
      this.editor.setText ( this.history.redo ().toString () );
    }
  }


  /**
   * Handles the undo action.
   */
  private final void handleUndo ()
  {
    if ( this.history.canUndo () )
    {
      this.editor.setText ( this.history.undo ().toString () );
    }
  }


  /**
   * Return the copyable value.
   * 
   * @return The copyable value.
   */
  public final boolean isCopyable ()
  {
    return this.copyable;
  }


  /**
   * Return the editable value.
   * 
   * @return The editable value.
   */
  public final boolean isEditable ()
  {
    return this.editable;
  }


  /**
   * Removes the given {@link ParseableChangedListener}.
   * 
   * @param pListener The {@link ParseableChangedListener}.
   */
  protected final synchronized void removeParseableChangedListener (
      ParseableChangedListener pListener )
  {
    this.listenerList.remove ( ParseableChangedListener.class, pListener );
  }


  /**
   * Removes the selectedText.
   */
  private final void removeSelectedText ()
  {
    int start = this.editor.getSelectionStart ();
    int end = this.editor.getSelectionEnd ();
    try
    {
      if ( start < end )
      {
        this.document.remove ( start, ( end - start ) );
      }
      else
      {
        this.document.remove ( end, ( start - end ) );
      }
    }
    catch ( BadLocationException e )
    {
      e.printStackTrace ();
    }
  }


  /**
   * Selects the error text.
   * 
   * @param pLeft The left index.
   * @param pRight The right index.
   */
  private final void selectErrorText ( int pLeft, int pRight )
  {
    this.editor.select ( pLeft, pRight );
  }


  /**
   * Sets the specified boolean to indicate whether or not this
   * <code>StyledParserPanel</code> should be copyable.
   * 
   * @param pCopyable The boolean to be set.
   */
  public final void setCopyable ( boolean pCopyable )
  {
    this.copyable = pCopyable;
    setStatus ();
  }


  /**
   * Sets the specified boolean to indicate whether or not this
   * <code>StyledParserPanel</code> should be editable.
   * 
   * @param pEditable The boolean to be set.
   */
  public final void setEditable ( boolean pEditable )
  {
    this.editable = pEditable;
    setStatus ();
  }


  /**
   * Sets the exceptions.
   * 
   * @param pExceptions The exceptions to set.
   */
  public final void setException ( Iterable < ScannerException > pExceptions )
  {
    this.document.setException ( pExceptions );
  }


  /**
   * Sets the exception.
   * 
   * @param pException The exception to set.
   */
  public final void setException ( ScannerException pException )
  {
    this.document.setException ( pException );
  }


  /**
   * Sets the {@link ParseableEntity}s which should be highlighted.
   * 
   * @param pParseableEntities The {@link ParseableEntity}s which should be
   *          highlighted.
   */
  public final void setHighlightedParseableEntity (
      Iterable < ? extends ParseableEntity > pParseableEntities )
  {
    this.document.setHighlightedParseableEntity ( pParseableEntities );
  }


  /**
   * Sets the {@link ParseableEntity} which should be highlighted.
   * 
   * @param pParseableEntity The {@link ParseableEntity} which should be
   *          highlighted.
   */
  public final void setHighlightedParseableEntity (
      ParseableEntity pParseableEntity )
  {
    this.document.setHighlightedParseableEntity ( pParseableEntity );
  }


  /**
   * Sets the status.
   */
  private final void setStatus ()
  {
    if ( this.editable )
    {
      this.sideBar.setVisible ( true );
      this.editor.setEditable ( true );
      this.editor.setFocusable ( true );
    }
    else
    {
      this.sideBar.setVisible ( false );
      this.editor.setEditable ( false );
      if ( this.copyable )
      {
        this.editor.setFocusable ( true );
        this.editor.setCursor ( new Cursor ( Cursor.TEXT_CURSOR ) );
      }
      else
      {
        this.editor.setFocusable ( false );
        this.editor.setCursor ( new Cursor ( Cursor.DEFAULT_CURSOR ) );
      }
    }
  }


  /**
   * Shows the {@link JPopupMenu} and enables the copy and cut menu item if text
   * is selected, otherwise they are diasabled.
   * 
   * @param pEvent
   */
  private final void showPopupMenu ( MouseEvent pEvent )
  {
    if ( this.editable )
    {
      int start = this.editor.getSelectionStart ();
      int end = this.editor.getSelectionEnd ();
      this.jMenuItemUndo.setEnabled ( this.history.canUndo () );
      this.jMenuItemRedo.setEnabled ( this.history.canRedo () );
      this.jMenuItemCopy.setEnabled ( start != end );
      this.jMenuItemCut.setEnabled ( start != end );
      this.jMenuItemPaste.setEnabled ( true );
      this.jPopupMenu.show ( pEvent.getComponent (), pEvent.getX (), pEvent
          .getY () );
    }
    else if ( this.copyable )
    {
      int start = this.editor.getSelectionStart ();
      int end = this.editor.getSelectionEnd ();
      this.jMenuItemUndo.setEnabled ( false );
      this.jMenuItemRedo.setEnabled ( false );
      this.jMenuItemCopy.setEnabled ( start != end );
      this.jMenuItemCut.setEnabled ( false );
      this.jMenuItemPaste.setEnabled ( false );
      this.jPopupMenu.show ( pEvent.getComponent (), pEvent.getX (), pEvent
          .getY () );
    }
  }
}
