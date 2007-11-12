package de.unisiegen.gtitool.core.parser.exceptions;


/**
 * The parser multi exception class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class ParserMultiException extends ParserException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -3411147227563168312L;


  /**
   * The array of shown messages.
   */
  private String [] messages;


  /**
   * The array of parser start offsets.
   */
  private int [] parserStartOffset;


  /**
   * The array of parser end offsets.
   */
  private int [] parserEndOffset;


  /**
   * Initializes the exception.
   * 
   * @param pParserStartOffset The array of parser start offsets.
   * @param pParserEndOffset The array of parser end offsets.
   * @param pMessages The array of shown messages.
   */
  public ParserMultiException ( int [] pParserStartOffset,
      int [] pParserEndOffset, String [] pMessages )
  {
    super ( pParserStartOffset [ 0 ], pParserEndOffset [ 0 ], pMessages [ 0 ] );
    this.messages = pMessages;
    this.parserStartOffset = pParserStartOffset;
    this.parserEndOffset = pParserEndOffset;
  }


  /**
   * Returns the messages.
   * 
   * @return The messages.
   * @see #messages
   */
  public String [] getMessages ()
  {
    return this.messages;
  }


  /**
   * Returns the endOffset.
   * 
   * @return The endOffset.
   * @see #parserEndOffset
   */
  public int [] getParserEndOffset ()
  {
    return this.parserEndOffset;
  }


  /**
   * Returns the startOffset.
   * 
   * @return The startOffset.
   * @see #parserStartOffset
   */
  public int [] getParserStartOffset ()
  {
    return this.parserStartOffset;
  }
}
