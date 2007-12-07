package de.unisiegen.gtitool.core.storage.exceptions;


/**
 * The <code>StoreWarningException</code> is used if the store or load has a
 * warning.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class StoreWarningException extends Exception
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -7247769761688842412L;


  /**
   * Allocates a new <code>StoreWarningException</code>.
   * 
   * @param pMessage The detail message.
   */
  public StoreWarningException ( String pMessage )
  {
    super ( pMessage );
  }
}
