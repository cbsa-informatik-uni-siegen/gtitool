package de.unisiegen.gtitool.ui.exchange;


/**
 * The {@link Exchange} {@link Exception}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class ExchangeException extends Exception
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -8856879948873617909L;


  /**
   * The detail message
   */
  private String message;


  /**
   * Allocates a new {@link ExchangeException}.
   * 
   * @param message The message.
   */
  public ExchangeException ( String message )
  {
    // Message
    if ( message == null )
    {
      throw new NullPointerException ( "message is null" ); //$NON-NLS-1$
    }
    this.message = message;
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
   * {@inheritDoc}
   * 
   * @see Throwable#toString()
   */
  @Override
  public final String toString ()
  {
    return this.message;
  }
}
