package de.unisiegen.gtitool.core.storage.exceptions;


/**
 * The <code>StoreException</code> is used if the store or load fails.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class StoreException extends Exception
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 7430208490292655003L;


  /**
   * The detail message
   */
  private String message;


  /**
   * Allocates a new <code>StoreException</code>.
   * 
   * @param pMessage The detail message.
   */
  public StoreException ( String pMessage )
  {
    // Message
    if ( pMessage == null )
    {
      throw new NullPointerException ( "message is null" ); //$NON-NLS-1$
    }
    this.message = pMessage;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Throwable#getLocalizedMessage()
   */
  @Override
  public final String getLocalizedMessage ()
  {
    return this.message;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Throwable#getMessage()
   */
  @Override
  public final String getMessage ()
  {
    return this.message;
  }


  /**
   * Sets the detail message.
   * 
   * @param pMessage The message to set.
   */
  protected final void setMessage ( String pMessage )
  {
    if ( pMessage == null )
    {
      throw new NullPointerException ( "message is null" ); //$NON-NLS-1$
    }
    this.message = pMessage;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Throwable#toString()
   */
  @Override
  public final String toString ()
  {
    String lineBreak = System.getProperty ( "line.separator" ); //$NON-NLS-1$
    StringBuilder result = new StringBuilder ();
    result.append ( "Message:     " + getMessage () + lineBreak ); //$NON-NLS-1$
    return result.toString ();
  }
}
