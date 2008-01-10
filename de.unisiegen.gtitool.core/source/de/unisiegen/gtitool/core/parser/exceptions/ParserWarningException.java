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
   * @param messages The message.
   * @param parserStartOffset The parser start offset.
   * @param parserEndOffset The parser end offset.
   */
  public ParserWarningException ( int parserStartOffset, int parserEndOffset,
      String messages )
  {
    this ( parserStartOffset, parserEndOffset, messages, "" ); //$NON-NLS-1$
  }


  /**
   * Initializes the exception.
   * 
   * @param parserStartOffset The parser start offset.
   * @param parserEndOffset The parser end offset.
   * @param messages The message.
   * @param insertText The text, which should be inserted.
   */
  public ParserWarningException ( int parserStartOffset, int parserEndOffset,
      String messages, String insertText )
  {
    super ( parserStartOffset, parserEndOffset, messages );
    this.insertText = insertText;
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
