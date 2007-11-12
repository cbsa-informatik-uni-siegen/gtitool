package de.unisiegen.gtitool.core.parser.exceptions;


/**
 * The parser warning exception class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class ParserWarningException extends ParserException
{

  /**
   * The unique serialization identifier of this class.
   */
  private static final long serialVersionUID = 5276244635558528418L;


  /**
   * The text, which should be inserted.
   */
  private String insertText;


  /**
   * Initializes the exception.
   * 
   * @param pMessages The message.
   * @param pParserStartOffset The parser start offset.
   * @param pParserEndOffset The parser end offset.
   */
  public ParserWarningException ( int pParserStartOffset, int pParserEndOffset,
      String pMessages )
  {
    this ( pParserStartOffset, pParserEndOffset, pMessages, "" ); //$NON-NLS-1$
  }


  /**
   * Initializes the exception.
   * 
   * @param pParserStartOffset The parser start offset.
   * @param pParserEndOffset The parser end offset.
   * @param pMessages The message.
   * @param pInsertText The text, which should be inserted.
   */
  public ParserWarningException ( int pParserStartOffset, int pParserEndOffset,
      String pMessages, String pInsertText )
  {
    super ( pParserStartOffset, pParserEndOffset, pMessages );
    this.insertText = pInsertText;
  }


  /**
   * Returns the insertText.
   * 
   * @return The insertText.
   * @see #insertText
   */
  public String getInsertText ()
  {
    return this.insertText;
  }
}
