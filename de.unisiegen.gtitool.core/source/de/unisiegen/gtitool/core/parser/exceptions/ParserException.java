package de.unisiegen.gtitool.core.parser.exceptions;


/**
 * The parser exception class.
 * 
 * @author Christian Fehler
 * @version $Id: Machine.java 124 2007-11-11 19:54:18Z fehler $
 */
public class ParserException extends ScannerException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -6750690466685873423L;


  /**
   * Allocates a new <code>ParserException</code>.
   * 
   * @param pLeft The left parser index.
   * @param pRight The right parser index.
   * @param pMessage The message of the parser.
   */
  public ParserException ( int pLeft, int pRight, String pMessage )
  {
    super ( pLeft, pRight, pMessage );
  }
}
