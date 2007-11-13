package de.unisiegen.gtitool.ui.style;


import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;

import de.unisiegen.gtitool.core.parser.Parseable;


/**
 * The styled panel class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class StyledParserPanel extends JPanel
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 8647430131132449298L;


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
  private JScrollPane scrollpane;


  /**
   * The {@link SideBar}.
   */
  private SideBar sideBar;


  /**
   * Allocates a new <code>StyledPanel</code>.
   * 
   * @param pParseable The input {@link Parseable}.
   */
  public StyledParserPanel ( Parseable pParseable )
  {
    this.editor = new StyledParserEditor ();
    this.document = new StyledParserDocument ( pParseable );
    setLayout ( new BorderLayout () );
    this.scrollpane = new JScrollPane ();
    add ( this.scrollpane, BorderLayout.CENTER );
    this.sideBar = new SideBar ( this.scrollpane, this.document, this.editor );
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
    this.scrollpane.setViewportView ( this.editor );
    this.editor.setDocument ( this.document );
    this.editor.setAutoscrolls ( false );
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
