package de.unisiegen.gtitool.ui.utils;


import java.util.ArrayList;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;


/**
 * The {@link DefaultStyledDocument} for a Algorithm
 * 
 * @author Simon Meurer
 */
public class AlgorithmDocument extends DefaultStyledDocument
{

  /**
   * The serial version uid
   */
  private static final long serialVersionUID = -3070979666976888175L;


  /**
   * The keywords that should be highlighted
   */
  private String [] keyWords = new String []
  { "if", "else", "end", "for", "do", "begin", "while", "then", "case", "void", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$ //$NON-NLS-10$
      "match", "firstpos", "lastpos", "followpos" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$


  /**
   * The words
   */
  private ArrayList < String > words;


  /**
   * The default constructor
   */
  public AlgorithmDocument ()
  {
    super ();
    this.words = new ArrayList < String > ();
    for ( String keyWord : this.keyWords )
    {
      this.words.add ( keyWord );
    }
  }


  /**
   * Tries to highlight the text
   * 
   * @param startOffset The startOffset
   * @param endOffset The endOffset
   * @throws BadLocationException When index is out of the document
   */
  public void highlight ( int startOffset, int endOffset )
      throws BadLocationException
  {
    String content = getText ( 0, getLength () );
    for ( String s : this.words )
    {
      int offset = startOffset;
      int index = content.indexOf ( s, offset );
      while ( ( index > -1 ) && ( index <= endOffset ) )
      {
        MutableAttributeSet attr = new SimpleAttributeSet ();
        StyleConstants.setBold ( attr, true );

        setCharacterAttributes ( index, s.length (), attr, false );
        offset = index + 1;
        index = content.indexOf ( s, offset );
      }
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see javax.swing.text.AbstractDocument#insertString(int, java.lang.String,
   *      javax.swing.text.AttributeSet)
   */
  @Override
  public void insertString ( int offset, String str, AttributeSet a )
      throws BadLocationException
  {
    super.insertString ( offset, str, a );
    highlight ( 0, getLength () );
  }

}
