package de.unisiegen.gtitool.core.parser.exceptions;


/**
 * The scanner exception class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class ScannerException extends RuntimeException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -6373246200988406713L;


  /**
   * The left parser index.
   */
  private int left;


  /**
   * The right parser index.
   */
  private int right;


  /**
   * Allocates a new <code>ScannerException</code>.
   * 
   * @param pLeft The left parser index.
   * @param pRight The right parser index.
   * @param pMessage The message of the parser.
   */
  public ScannerException ( int pLeft, int pRight, String pMessage )
  {
    super ( pMessage );
    this.left = pLeft;
    this.right = pRight;
  }


  /**
   * Allocates a new <code>ScannerException</code>.
   * 
   * @param pLeft The left parser index.
   * @param pRight The right parser index.
   * @param pMessage The message of the parser.
   * @param pThrowable The {@link Throwable}.
   */
  public ScannerException ( int pLeft, int pRight, String pMessage,
      Throwable pThrowable )
  {
    super ( pMessage, pThrowable );
    this.left = pLeft;
    this.right = pRight;
  }


  /**
   * Returns the left parser index.
   * 
   * @return The left parser index.
   */
  public int getLeft ()
  {
    return this.left;
  }


  /**
   * Returns the right parser index.
   * 
   * @return The right parser index.
   */
  public int getRight ()
  {
    return this.right;
  }
}
