package de.unisiegen.gtitool.ui.style.parser;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.CellEditor;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;

import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.parser.Parseable;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.core.preferences.listener.LanguageChangedListener;
import de.unisiegen.gtitool.core.util.Theme;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.style.document.StyledParserDocument;
import de.unisiegen.gtitool.ui.style.editor.StyledParserEditor;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;
import de.unisiegen.gtitool.ui.style.sidebar.SideBar;
import de.unisiegen.gtitool.ui.style.sidebar.SideBarListener;
import de.unisiegen.gtitool.ui.utils.Clipboard;


/**
 * The styled parser panel class.
 * 
 * @author Christian Fehler
 * @version $Id$
 * @param <E> The {@link Entity}.
 */
public abstract class StyledParserPanel < E extends Entity < E >> extends
    JPanel
{

  /**
   * The accepted status.
   * 
   * @author Christian Fehler
   */
  public enum AcceptedStatus
  {
    /**
     * The none status.
     */
    NONE ( Color.BLACK ),

    /**
     * The accepted status.
     */
    ACCEPTED ( Color.GREEN ),

    /**
     * The not accepted status.
     */
    NOT_ACCEPTED ( Color.RED );

    /**
     * The used {@link Color}.
     */
    private Color color;


    /**
     * Allocates a new {@link AcceptedStatus}.
     * 
     * @param color The {@link Color}.
     */
    private AcceptedStatus ( Color color )
    {
      this.color = color;
    }


    /**
     * Returns the color.
     * 
     * @return The color.
     * @see #color
     */
    public final Color getColor ()
    {
      return this.color;
    }
  }


  /**
   * The history of parsed objects.
   * 
   * @author Christian Fehler
   */
  private class History
  {

    /**
     * Flag that indicates if the neex object should be added.
     */
    private boolean addNextObject;


    /**
     * The current index.
     */
    private int index;


    /**
     * The history list.
     */
    private ArrayList < E > list;


    /**
     * Allocates a new {@link History}.
     */
    public History ()
    {
      this.list = new ArrayList < E > ();
      this.index = -1;
      this.addNextObject = true;
    }


    /**
     * Add a new object.
     * 
     * @param newObject The object to add.
     */
    public final void add ( E newObject )
    {
      if ( this.addNextObject
          && ( ( this.list.size () == 0 ) || !this.list.get (
              this.list.size () - 1 ).toString ().equals (
              newObject.toString () ) ) )
      {
        this.list.add ( newObject );
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
    public final E redo ()
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
    public final E undo ()
    {
      this.index-- ;
      this.addNextObject = false;
      return this.list.get ( this.index );
    }
  }


  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -1954392510970145068L;


  /**
   * The error {@link Color}.
   */
  private static final Color ERROR_COLOR = Color.RED;


  /**
   * The normal {@link Color}.
   */
  private static final Color NORMAL_COLOR = Color.BLACK;


  /**
   * Flag that indicates if the {@link SideBar} is visible.
   */
  private boolean sideBarVisible;


  /**
   * Flag that indicates if the panel is copyable.
   */
  private boolean copyable;


  /**
   * Flag that indicates if the panel is editable.
   */
  private boolean editable;


  /**
   * Flag that indicates if the panel is used as a {@link CellEditor}.
   */
  private boolean cellEditor = false;


  /**
   * The {@link StyledParserDocument}.
   */
  protected StyledParserDocument < E > document;


  /**
   * The {@link StyledParserEditor}.
   */
  protected StyledParserEditor < E > editor;


  /**
   * The {@link History}.
   */
  private History history;


  /**
   * The copy {@link JMenuItem}.
   */
  protected JMenuItem jMenuItemCopy;


  /**
   * The cut {@link JMenuItem}.
   */
  protected JMenuItem jMenuItemCut;


  /**
   * The paste {@link JMenuItem}.
   */
  protected JMenuItem jMenuItemPaste;


  /**
   * The redo {@link JMenuItem}.
   */
  protected JMenuItem jMenuItemRedo;


  /**
   * The undo {@link JMenuItem}.
   */
  protected JMenuItem jMenuItemUndo;


  /**
   * The {@link JPopupMenu}.
   */
  private JPopupMenu jPopupMenu;


  /**
   * The {@link JScrollPane}.
   */
  protected JScrollPane jScrollPane;


  /**
   * The {@link ParseableChangedListener} for the other
   * {@link StyledParserPanel}.
   */
  protected ParseableChangedListener < E > parseableChangedListenerOther;


  /**
   * The {@link ParseableChangedListener} for this {@link StyledParserPanel}.
   */
  protected ParseableChangedListener < E > parseableChangedListenerThis;


  /**
   * The {@link SideBar}.
   */
  private SideBar < E > sideBar;


  /**
   * The {@link StyledParserPanel}.
   */
  protected StyledParserPanel < E > synchronizedStyledParserPanel = null;


  /**
   * The {@link AcceptedStatus}.
   */
  private AcceptedStatus acceptedStatus = AcceptedStatus.NONE;


  /**
   * Allocates a new {@link StyledParserPanel}.
   * 
   * @param parseable The input {@link Parseable}.
   */
  public StyledParserPanel ( Parseable parseable )
  {
    this.editable = true;
    this.copyable = false;
    this.sideBarVisible = true;
    this.editor = new StyledParserEditor < E > ();

    // PopupMenu
    this.jPopupMenu = new JPopupMenu ();

    // Undo
    this.jMenuItemUndo = new JMenuItem ( Messages
        .getString ( "MainWindow.Undo" ) ); //$NON-NLS-1$
    this.jMenuItemUndo.setMnemonic ( Messages.getString (
        "MainWindow.UndoMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    this.jMenuItemUndo.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/small/undo.png" ) ) ); //$NON-NLS-1$
    this.jMenuItemUndo.setAccelerator ( KeyStroke.getKeyStroke ( KeyEvent.VK_X,
        InputEvent.CTRL_MASK ) );
    this.jMenuItemUndo.addActionListener ( new ActionListener ()
    {

      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
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
        "/de/unisiegen/gtitool/ui/icon/small/redo.png" ) ) ); //$NON-NLS-1$
    this.jMenuItemRedo.setAccelerator ( KeyStroke.getKeyStroke ( KeyEvent.VK_X,
        InputEvent.CTRL_MASK ) );
    this.jMenuItemRedo.addActionListener ( new ActionListener ()
    {

      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
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
        "/de/unisiegen/gtitool/ui/icon/small/cut.png" ) ) ); //$NON-NLS-1$
    this.jMenuItemCut.setAccelerator ( KeyStroke.getKeyStroke ( KeyEvent.VK_X,
        InputEvent.CTRL_MASK ) );
    this.jMenuItemCut.addActionListener ( new ActionListener ()
    {

      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
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
        "/de/unisiegen/gtitool/ui/icon/small/copy.png" ) ) ); //$NON-NLS-1$
    this.jMenuItemCopy.setAccelerator ( KeyStroke.getKeyStroke ( KeyEvent.VK_C,
        InputEvent.CTRL_MASK ) );
    this.jMenuItemCopy.addActionListener ( new ActionListener ()
    {

      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
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
        "/de/unisiegen/gtitool/ui/icon/small/paste.png" ) ) ); //$NON-NLS-1$
    this.jMenuItemPaste.setAccelerator ( KeyStroke.getKeyStroke (
        KeyEvent.VK_V, InputEvent.CTRL_MASK ) );
    this.jMenuItemPaste.addActionListener ( new ActionListener ()
    {

      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
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

          public void languageChanged ()
          {
            StyledParserPanel.this.jMenuItemUndo.setText ( Messages
                .getString ( "MainWindow.Undo" ) ); //$NON-NLS-1$
            StyledParserPanel.this.jMenuItemUndo.setMnemonic ( Messages
                .getString ( "MainWindow.UndoMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
            StyledParserPanel.this.jMenuItemRedo.setText ( Messages
                .getString ( "MainWindow.Redo" ) ); //$NON-NLS-1$
            StyledParserPanel.this.jMenuItemRedo.setMnemonic ( Messages
                .getString ( "MainWindow.RedoMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
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

      @Override
      public void mousePressed ( MouseEvent event )
      {
        if ( event.isPopupTrigger () )
        {
          showPopupMenu ( event );
        }
      }


      @Override
      public void mouseReleased ( MouseEvent event )
      {
        if ( event.isPopupTrigger () )
        {
          showPopupMenu ( event );
        }
      }
    } );

    // Document
    this.document = new StyledParserDocument < E > ( parseable );
    this.document
        .addParseableChangedListener ( new ParseableChangedListener < E > ()
        {

          public void parseableChanged ( E newObject )
          {
            fireParseableChanged ( newObject );
          }
        } );
    setLayout ( new BorderLayout () );
    this.jScrollPane = new JScrollPane ();
    this.jScrollPane.setBorder ( new LineBorder ( NORMAL_COLOR ) );
    add ( this.jScrollPane, BorderLayout.CENTER );
    this.sideBar = new SideBar < E > ( this.jScrollPane, this.document,
        this.editor );
    this.sideBar.addSideBarListener ( new SideBarListener ()
    {

      /**
       * Inserts a given text at the given index.
       * 
       * @param index The index in the text, where the text should be inserted.
       * @param insertText The text which should be inserted.
       */

      public void insertText ( int index, String insertText )
      {
        int countSpaces = 0;
        try
        {
          while ( StyledParserPanel.this.document.getText (
              index + countSpaces, 1 ).equals ( " " ) ) //$NON-NLS-1$
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
          String text = insertText;
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
          StyledParserPanel.this.document.insertString ( index + offset, text,
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
       * @param left The left offset of the text which should be marked.
       * @param right The right offset of the text which should be marked.
       */

      public void markText ( int left, int right )
      {
        if ( ( StyledParserPanel.this.editor.getSelectionStart () == left )
            && ( StyledParserPanel.this.editor.getSelectionEnd () == right ) )
        {
          StyledParserPanel.this.removeSelectedText ();
        }
        else
        {
          StyledParserPanel.this.selectErrorText ( left, right );
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

      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        handleUndo ();
      }

    }, KeyStroke.getKeyStroke ( KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK ),
        JComponent.WHEN_FOCUSED );

    this.editor.registerKeyboardAction ( new ActionListener ()
    {

      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        handleRedo ();
      }

    }, KeyStroke.getKeyStroke ( KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK ),
        JComponent.WHEN_FOCUSED );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Component#addFocusListener(FocusListener)
   */
  @Override
  public final void addFocusListener ( FocusListener listener )
  {
    this.editor.addFocusListener ( listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Component#addKeyListener(KeyListener)
   */
  @Override
  public final void addKeyListener ( KeyListener listener )
  {
    this.editor.addKeyListener ( listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Component#addMouseListener(java.awt.event.MouseListener)
   */
  @Override
  public synchronized void addMouseListener ( MouseListener l )
  {
    this.editor.addMouseListener ( l );
    this.sideBar.addMouseListener ( l );
    super.addMouseListener ( l );
  }


  /**
   * Returns the sideBar.
   * 
   * @return The sideBar.
   * @see #sideBar
   */
  public SideBar < E > getSideBar ()
  {
    return this.sideBar;
  }


  /**
   * Returns the editor.
   * 
   * @return The editor.
   * @see #editor
   */
  public StyledParserEditor < E > getEditor ()
  {
    return this.editor;
  }


  /**
   * Adds the given style.
   * 
   * @param token The token.
   * @param style The {@link Style}.
   */
  protected final void addOverwrittenStyle ( String token, Style style )
  {
    this.document.addOverwrittenStyle ( token, style );
  }


  /**
   * Adds the given {@link ParseableChangedListener}.
   * 
   * @param listener The {@link ParseableChangedListener}.
   */
  public final void addParseableChangedListener (
      ParseableChangedListener < E > listener )
  {
    this.listenerList.add ( ParseableChangedListener.class, listener );
  }


  /**
   * Returns the document.
   * 
   * @return The document.
   * @see #document
   */
  public StyledParserDocument < E > getDocument ()
  {
    return this.document;
  }


  /**
   * Checks the given parsed {@link Object}.
   * 
   * @param parsedObject The parsed {@link Object} to check.
   * @return The input parsed {@link Object} or null, if the parsed
   *         {@link Object} is not correct.
   */
  protected abstract E checkParsedObject ( E parsedObject );


  /**
   * Clears the overwritten {@link Style}.
   * 
   * @param style The {@link Style} to clear.
   */
  protected final void clearOverwrittenStyle ( Style style )
  {
    this.document.clearOverwrittenStyle ( style );
  }


  /**
   * Let the listeners know that the {@link Object} has changed.
   * 
   * @param newObject The new {@link Object}.
   */
  @SuppressWarnings ( "unchecked" )
  public final void fireParseableChanged ( E newObject )
  {
    E checkedObject = checkParsedObject ( newObject );
    setErrorIndicator ( checkedObject == null );

    // History
    if ( checkedObject != null )
    {
      this.history.add ( checkedObject );
    }

    ParseableChangedListener [] listeners = this.listenerList
        .getListeners ( ParseableChangedListener.class );
    for ( ParseableChangedListener < E > current : listeners )
    {
      current.parseableChanged ( checkedObject );
    }
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
   */
  public final E getParsedObject ()
  {
    return checkParsedObject ( this.document.getParsedObject () );
  }


  /**
   * Returns the text contained in the editor.
   * 
   * @return The text contained in the editor.
   */
  public final String getText ()
  {
    return this.editor.getText ();
  }


  /**
   * Handles the redo action.
   */
  protected final void handleRedo ()
  {
    if ( this.history.canRedo () )
    {
      this.editor.setText ( this.history.redo ().toString () );
    }
  }


  /**
   * Handles the undo action.
   */
  protected final void handleUndo ()
  {
    if ( this.history.canUndo () )
    {
      this.editor.setText ( this.history.undo ().toString () );
    }
  }


  /**
   * Returns true if this {@link StyledParserPanel} is used as a
   * {@link CellEditor}, otherwise false.
   * 
   * @return True if this {@link StyledParserPanel} is used as a
   *         {@link CellEditor}, otherwise false.
   */
  public boolean isCellEditor ()
  {
    return this.cellEditor;
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
   * Return the enabled value.
   * 
   * @return The enabled value.
   */
  @Override
  public final boolean isEnabled ()
  {
    return super.isEnabled ();
  }


  /**
   * Returns the right alignment value.
   * 
   * @return The right alignment value.
   */
  public final boolean isRightAlignment ()
  {
    return this.document.isRightAlignment ();
  }


  /**
   * Return the sideBarVisible value.
   * 
   * @return The sideBarVisible value.
   */
  public final boolean isSideBarVisible ()
  {
    return this.sideBarVisible;
  }


  /**
   * Parses the document and returns the parsed object or null, if the text
   * could not be parsed.
   * 
   * @return The parsed object or null, if the text could not be parsed.
   */
  public final E parse ()
  {
    E checkedObject = checkParsedObject ( this.document.parse () );
    setErrorIndicator ( checkedObject == null );
    return checkedObject;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Component#removeFocusListener(FocusListener)
   */
  @Override
  public final void removeFocusListener ( FocusListener listener )
  {
    this.editor.removeFocusListener ( listener );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Component#removeKeyListener(KeyListener)
   */
  @Override
  public final void removeKeyListener ( KeyListener listener )
  {
    this.editor.removeKeyListener ( listener );
  }


  /**
   * Removes the given {@link ParseableChangedListener}.
   * 
   * @param listener The {@link ParseableChangedListener}.
   */
  public final void removeParseableChangedListener (
      ParseableChangedListener < E > listener )
  {
    this.listenerList.remove ( ParseableChangedListener.class, listener );
  }


  /**
   * Removes the selectedText.
   */
  protected final void removeSelectedText ()
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
   * {@inheritDoc}
   * 
   * @see JComponent#requestFocus()
   */
  @Override
  public final void requestFocus ()
  {
    this.editor.requestFocus ();
  }


  /**
   * Selects the error text.
   * 
   * @param left The left index.
   * @param right The right index.
   */
  protected final void selectErrorText ( int left, int right )
  {
    this.editor.select ( left, right );
  }


  /**
   * Sets the {@link AcceptedStatus}.
   * 
   * @param acceptedStatus The {@link AcceptedStatus} to set.
   */
  public final void setAcceptedStatus ( AcceptedStatus acceptedStatus )
  {
    this.acceptedStatus = acceptedStatus;
    setErrorIndicator ( false );
  }


  /**
   * Sets the cell editor flag.
   * 
   * @param cellEditor The cell editor flag.
   */
  public final void setCellEditor ( boolean cellEditor )
  {
    this.cellEditor = cellEditor;
    if ( this.cellEditor )
    {
      this.jScrollPane
          .setHorizontalScrollBarPolicy ( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
      this.jScrollPane
          .setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER );

      this.editor.setCellEditor ( true );

      setSideBarVisible ( false );
    }
    else
    {
      this.jScrollPane
          .setHorizontalScrollBarPolicy ( ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED );
      this.jScrollPane
          .setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED );

      this.editor.setCellEditor ( false );
    }
  }


  /**
   * Sets the specified boolean to indicate whether or not this
   * {@link StyledParserPanel} should be copyable.
   * 
   * @param copyable The boolean to be set.
   */
  public final void setCopyable ( boolean copyable )
  {
    this.copyable = copyable;
    setStatus ();
  }


  /**
   * Sets the specified boolean to indicate whether or not this
   * {@link StyledParserPanel} should be editable.
   * 
   * @param editable The boolean to be set.
   */
  public final void setEditable ( boolean editable )
  {
    this.editable = editable;
    setStatus ();
  }


  /**
   * Sets the specified boolean to indicate whether or not this
   * {@link StyledParserPanel} should be enabled.
   * 
   * @param enabled The boolean to be set.
   */
  @Override
  public final void setEnabled ( boolean enabled )
  {
    this.sideBar.setEnabled ( enabled );
    this.editor.setEnabled ( enabled );
    if ( enabled )
    {
      this.editor.setBackground ( Color.WHITE );
    }
    else
    {
      this.editor.setBackground ( Theme.DISABLED_COMPONENT_COLOR );
    }
    super.setEnabled ( enabled );
  }


  /**
   * Sets the error indicator of this {@link StyledParserPanel}.
   * 
   * @param error Flag that indicates if there is an error.
   */
  private final void setErrorIndicator ( boolean error )
  {
    if ( this.acceptedStatus.equals ( AcceptedStatus.NONE ) )
    {
      if ( ( this.document.getParsedObject () == null ) || error )
      {
        this.jScrollPane.setBorder ( new LineBorder ( ERROR_COLOR ) );
      }
      else
      {
        this.jScrollPane.setBorder ( new LineBorder ( NORMAL_COLOR ) );
      }
    }
    else if ( this.acceptedStatus.equals ( AcceptedStatus.ACCEPTED ) )
    {
      this.jScrollPane.setBorder ( new LineBorder ( AcceptedStatus.ACCEPTED
          .getColor () ) );
    }
    else if ( this.acceptedStatus.equals ( AcceptedStatus.NOT_ACCEPTED ) )
    {
      this.jScrollPane.setBorder ( new LineBorder ( AcceptedStatus.NOT_ACCEPTED
          .getColor () ) );
    }
    else
    {
      throw new RuntimeException ( "unsupported accepted status" ); //$NON-NLS-1$
    }
  }


  /**
   * Sets the extern {@link ScannerException}s.
   * 
   * @param exceptions The {@link ScannerException}s to set.
   */
  protected final void setException ( Iterable < ScannerException > exceptions )
  {
    this.document.setException ( exceptions );
    setErrorIndicator ( exceptions.iterator ().hasNext () );
  }


  /**
   * Sets the {@link Entity}s which should be highlighted.
   * 
   * @param entities The {@link Entity}s which should be highlighted.
   */
  public final void setHighlightedParseableEntity ( Entity < ? > ... entities )
  {
    this.document.setHighlightedParseableEntity ( entities );
  }


  /**
   * Sets the {@link Entity} which should be highlighted.
   * 
   * @param entity The {@link Entity} which should be highlighted.
   */
  public final void setHighlightedParseableEntity ( Entity < ? > entity )
  {
    this.document.setHighlightedParseableEntity ( entity );
  }


  /**
   * Sets the {@linkEntity}s which should be highlighted.
   * 
   * @param entities The {@link Entity}s which should be highlighted.
   */
  public final void setHighlightedParseableEntity (
      Iterable < ? extends Entity < ? > > entities )
  {
    this.document.setHighlightedParseableEntity ( entities );
  }


  /**
   * Sets the right alignment.
   * 
   * @param rightAlignment The right alignment to set.
   */
  public final void setRightAlignment ( boolean rightAlignment )
  {
    this.document.setRightAlignment ( rightAlignment );
  }


  /**
   * Sets the specified boolean to indicate whether or not the {@link SideBar}
   * should be visible.
   * 
   * @param sideBarVisible The boolean to be set.
   */
  public final void setSideBarVisible ( boolean sideBarVisible )
  {
    if ( sideBarVisible )
    {
      if ( !this.cellEditor )
      {
        this.sideBarVisible = true;
        setStatus ();
      }
    }
    else
    {
      this.sideBarVisible = false;
      setStatus ();
    }
  }


  /**
   * Sets the status.
   */
  private final void setStatus ()
  {
    this.editor.setEditable ( this.editable );
    if ( this.editable )
    {
      this.editor.setFocusable ( true );
    }
    else
    {
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
    // SideBar
    this.sideBar.setVisible ( this.sideBarVisible );
  }


  /**
   * Sets the {@link Object} of the {@link StyledParserPanel}.
   * 
   * @param object The input {@link Object}.
   */
  public final void setText ( Object object )
  {
    if ( object == null )
    {
      this.editor.setText ( "" ); //$NON-NLS-1$
    }
    else
    {
      this.editor.setText ( object.toString () );
    }
  }


  /**
   * Shows the {@link JPopupMenu} and enables the copy and cut menu item if text
   * is selected, otherwise they are diasabled.
   * 
   * @param event
   */
  protected final void showPopupMenu ( MouseEvent event )
  {
    if ( !isEnabled () )
    {
      return;
    }
    if ( this.editable )
    {
      int start = this.editor.getSelectionStart ();
      int end = this.editor.getSelectionEnd ();
      this.jMenuItemUndo.setEnabled ( this.history.canUndo () );
      this.jMenuItemRedo.setEnabled ( this.history.canRedo () );
      this.jMenuItemCopy.setEnabled ( start != end );
      this.jMenuItemCut.setEnabled ( start != end );
      this.jMenuItemPaste.setEnabled ( true );
      this.jPopupMenu.show ( event.getComponent (), event.getX (), event
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
      this.jPopupMenu.show ( event.getComponent (), event.getX (), event
          .getY () );
    }
  }


  /**
   * Synchronizes this {@link StyledParserPanel} with the given
   * {@link StyledParserPanel}.
   * 
   * @param styledParserPanel The other {@link StyledParserPanel} which should
   *          be synchronized.
   */
  public final void synchronize ( StyledParserPanel < E > styledParserPanel )
  {
    if ( styledParserPanel == null )
    {
      this.synchronizedStyledParserPanel
          .removeParseableChangedListener ( this.parseableChangedListenerOther );
      removeParseableChangedListener ( this.parseableChangedListenerThis );
      this.parseableChangedListenerThis = null;
      this.parseableChangedListenerOther = null;
      this.synchronizedStyledParserPanel = null;
      return;
    }
    this.synchronizedStyledParserPanel = styledParserPanel;

    this.editor.setText ( this.synchronizedStyledParserPanel.getText () );

    this.parseableChangedListenerOther = new ParseableChangedListener < E > ()
    {

      public void parseableChanged ( @SuppressWarnings ( "unused" ) E newObject )
      {
        removeParseableChangedListener ( StyledParserPanel.this.parseableChangedListenerThis );
        StyledParserPanel.this.editor
            .setText ( StyledParserPanel.this.synchronizedStyledParserPanel
                .getText () );
        addParseableChangedListener ( StyledParserPanel.this.parseableChangedListenerThis );
      }
    };
    this.parseableChangedListenerThis = new ParseableChangedListener < E > ()
    {

      public void parseableChanged ( @SuppressWarnings ( "unused" ) E newObject )
      {
        StyledParserPanel.this.synchronizedStyledParserPanel
            .removeParseableChangedListener ( StyledParserPanel.this.parseableChangedListenerOther );
        StyledParserPanel.this.synchronizedStyledParserPanel
            .setText ( StyledParserPanel.this.editor.getText () );
        StyledParserPanel.this.synchronizedStyledParserPanel
            .addParseableChangedListener ( StyledParserPanel.this.parseableChangedListenerOther );
      }
    };
    this.synchronizedStyledParserPanel
        .addParseableChangedListener ( this.parseableChangedListenerOther );
    addParseableChangedListener ( this.parseableChangedListenerThis );
  }
}
