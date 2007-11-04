package de.unisiegen.gtitool.core.exceptions;


/**
 * The <code>MachineException</code> is used to be collected in a
 * {@link MachineValidationException}
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public abstract class MachineException extends Exception
{

  /**
   * The detail message
   */
  private String message;


  /**
   * The detail description.
   */
  private String description;


  /**
   * Allocates a new <code>MachineException</code>.
   */
  public MachineException ()
  {
    super ();
  }


  /**
   * Allocates a new <code>MachineException</code>.
   * 
   * @param pMessage The detail message.
   * @param pDescription The detail description.
   */
  public MachineException ( String pMessage, String pDescription )
  {
    // Message
    if ( pMessage == null )
    {
      throw new NullPointerException ( "message is null" ); //$NON-NLS-1$
    }
    this.message = pMessage;
    // Description
    if ( pDescription == null )
    {
      throw new NullPointerException ( "description is null" ); //$NON-NLS-1$
    }
    this.description = pDescription;
  }


  /**
   * Returns the detail description.
   * 
   * @return The detail description.
   * @see #description
   */
  public final String getDescription ()
  {
    return this.description;
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
   * Sets the detail description.
   * 
   * @param pDescription The description to set.
   */
  public final void setDescription ( String pDescription )
  {
    if ( pDescription == null )
    {
      throw new NullPointerException ( "description is null" ); //$NON-NLS-1$
    }
    this.description = pDescription;
  }


  /**
   * Sets the detail message.
   * 
   * @param pMessage The message to set.
   */
  public final void setMessage ( String pMessage )
  {
    if ( pMessage == null )
    {
      throw new NullPointerException ( "message is null" ); //$NON-NLS-1$
    }
    this.message = pMessage;
  }
}
