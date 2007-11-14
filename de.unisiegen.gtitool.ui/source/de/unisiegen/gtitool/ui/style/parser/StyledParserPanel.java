package de.unisiegen.gtitool.ui.style.parser;


import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;

import de.unisiegen.gtitool.core.parser.Parseable;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;
import de.unisiegen.gtitool.ui.style.sidebar.SideBar;
import de.unisiegen.gtitool.ui.style.sidebar.SideBarListener;


/**
 * The styled panel class.
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
   * Allocates a new <code>StyledPanel</code>.
   * 
   * @param pParseable The input {@link Parseable}.
   */
  public StyledParserPanel ( Parseable pParseable )
  {
    this.editor = new StyledParserEditor ();
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
    if ( pNewObject == null )
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
  protected StyledParserDocument getDocument ()
  {
    return this.document;
  }


  /**
   * Returns the editor.
   * 
   * @return The editor.
   * @see #editor
   */
  protected StyledParserEditor getEditor ()
  {
    return this.editor;
  }


  /**
   * Returns the {@link Object} for the program text within the document. Throws
   * an exception if a parsing error occurred.
   * 
   * @return The {@link Object} for the program text.
   * @throws Exception If a parsing error occurred.
   */
  protected Object getParsedObject () throws Exception
  {
    return this.document.getParsedObject ();
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
  private void removeSelectedText ()
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
  private void selectErrorText ( int pLeft, int pRight )
  {
    this.editor.select ( pLeft, pRight );
  }
}
