package de.unisiegen.gtitool.ui.style.parser;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;

import de.unisiegen.gtitool.core.parser.Parseable;
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
   * The list of {@link ParseableChangedListener}.
   */
  private ArrayList < ParseableChangedListener > parseableChangedListenerList = new ArrayList < ParseableChangedListener > ();


  /**
   * Flag that indicates if the panel is read only.
   */
  private boolean editable = true;


  /**
   * The {@link JPopupMenu}.
   */
  private JPopupMenu jPopupMenu;


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
   * Allocates a new <code>StyledPanel</code>.
   * 
   * @param pParseable The input {@link Parseable}.
   */
  public StyledParserPanel ( Parseable pParseable )
  {
    this.editor = new StyledParserEditor ();

    // PopupMenu
    this.jPopupMenu = new JPopupMenu ();

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
        if ( ( pEvent.isPopupTrigger () ) && ( StyledParserPanel.this.editable ) )
        {
          StyledParserPanel.this.jPopupMenu.show ( pEvent.getComponent (),
              pEvent.getX (), pEvent.getY () );
        }
      }


      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mouseReleased ( MouseEvent pEvent )
      {
        if ( ( pEvent.isPopupTrigger () ) && ( StyledParserPanel.this.editable ) )
        {
          StyledParserPanel.this.jPopupMenu.show ( pEvent.getComponent (),
              pEvent.getX (), pEvent.getY () );
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
  }


  /**
   * Adds the given {@link ParseableChangedListener}.
   * 
   * @param pListener The {@link ParseableChangedListener}.
   */
  protected final synchronized void addParseableChangedListener (
      ParseableChangedListener pListener )
  {
    this.parseableChangedListenerList.add ( pListener );
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
    for ( ParseableChangedListener current : this.parseableChangedListenerList )
    {
      current.parseableChanged ( pNewObject );
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
   * @return <tt>true</tt> if the list contained the specified element.
   */
  protected final synchronized boolean removeParseableChangedListener (
      ParseableChangedListener pListener )
  {
    return this.parseableChangedListenerList.remove ( pListener );
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
   * <code>StyledParserPanel</code> should be editable.
   * 
   * @param pEditable The boolean to be set.
   */
  public final void setEditable ( boolean pEditable )
  {
    boolean change = this.editable != pEditable;
    this.editable = pEditable;
    if ( change )
    {
      this.sideBar.setVisible ( this.editable );
      this.editor.setEditable ( this.editable );
      this.editor.setFocusable ( this.editable );
      this.document.setEditable ( this.editable );
    }
  }
}
