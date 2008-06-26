package de.unisiegen.gtitool.logger;


/**
 * The logger class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class Logger
{

  /**
   * Returns the single {@link Logger}.
   * 
   * @param clazz The class.
   * @return The single {@link Logger}.
   */
  public final static Logger getLogger ( Class < ? extends Object > clazz )
  {
    return new Logger ( clazz );
  }


  /**
   * The class.
   */
  private Class < ? extends Object > clazz;


  /**
   * Allocates a new {@link Logger}.
   * 
   * @param clazz The class.
   */
  private Logger ( Class < ? extends Object > clazz )
  {
    this.clazz = clazz;
  }


  /**
   * Prints the debug message.
   * 
   * @param method The method name.
   * @param message The message.
   */
  public final void debug ( String method, String message )
  {
    if ( method == null )
    {
      throw new IllegalArgumentException ( "method name is null" );//$NON-NLS-1$
    }
    if ( method.equals ( "" ) )//$NON-NLS-1$
    {
      throw new IllegalArgumentException ( "method name is empty" );//$NON-NLS-1$
    }
    if ( message == null )
    {
      throw new IllegalArgumentException ( "message name is null" );//$NON-NLS-1$
    }
    if ( message.equals ( "" ) )//$NON-NLS-1$
    {
      throw new IllegalArgumentException ( "message name is empty" );//$NON-NLS-1$
    }

    System.out.println ( "[" + Thread.currentThread ().getName () + ": " //$NON-NLS-1$ //$NON-NLS-2$
        + this.clazz.getSimpleName () + "." + method + "]" ); //$NON-NLS-1$//$NON-NLS-2$
    System.out.println ( message );
  }


  /**
   * Prints the error message.
   * 
   * @param method The method name.
   * @param message The message.
   */
  public final void error ( String method, String message )
  {
    if ( method == null )
    {
      throw new IllegalArgumentException ( "method name is null" );//$NON-NLS-1$
    }
    if ( method.equals ( "" ) )//$NON-NLS-1$
    {
      throw new IllegalArgumentException ( "method name is empty" );//$NON-NLS-1$
    }
    if ( message == null )
    {
      throw new IllegalArgumentException ( "message name is null" );//$NON-NLS-1$
    }
    if ( message.equals ( "" ) )//$NON-NLS-1$
    {
      throw new IllegalArgumentException ( "message name is empty" );//$NON-NLS-1$
    }

    System.out.println ( "[" + Thread.currentThread ().getName () + ": " //$NON-NLS-1$ //$NON-NLS-2$
        + this.clazz.getSimpleName () + "." + method + "]" ); //$NON-NLS-1$//$NON-NLS-2$
    System.err.println ( message );
  }


  /**
   * Prints the error message.
   * 
   * @param method The method name.
   * @param message The message.
   * @param exception The {@link Exception}.
   */
  public final void error ( String method, String message, Exception exception )
  {
    if ( method == null )
    {
      throw new IllegalArgumentException ( "method name is null" );//$NON-NLS-1$
    }
    if ( method.equals ( "" ) )//$NON-NLS-1$
    {
      throw new IllegalArgumentException ( "method name is empty" );//$NON-NLS-1$
    }
    if ( message == null )
    {
      throw new IllegalArgumentException ( "message name is null" );//$NON-NLS-1$
    }
    if ( message.equals ( "" ) )//$NON-NLS-1$
    {
      throw new IllegalArgumentException ( "message name is empty" );//$NON-NLS-1$
    }
    if ( exception == null )
    {
      throw new IllegalArgumentException ( "exception name is null" );//$NON-NLS-1$
    }

    System.out.println ( "[" + Thread.currentThread ().getName () + ": " //$NON-NLS-1$ //$NON-NLS-2$
        + this.clazz.getSimpleName () + "." + method + "]" ); //$NON-NLS-1$//$NON-NLS-2$
    System.err.println ( message );
    exception.printStackTrace ();
  }
}
