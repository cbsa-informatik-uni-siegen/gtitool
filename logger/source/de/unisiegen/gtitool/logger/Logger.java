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
  public final static Logger getLogger ( Class clazz )
  {
    return new Logger ( clazz );
  }


  /**
   * The class.
   */
  private Class clazz;


  /**
   * Allocates a new {@link Logger}.
   * 
   * @param clazz The class.
   */
  private Logger ( Class clazz )
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
    System.out.println ( "[" + Thread.currentThread ().getName () + ": " //$NON-NLS-1$ //$NON-NLS-2$
        + this.clazz.getSimpleName () + "." + method + "]" ); //$NON-NLS-1$//$NON-NLS-2$
    System.err.println ( message );
  }


  /**
   * Prints the error message.
   * 
   * @param method The method name.
   * @param message The message.
   * @param exc The {@link Exception}.
   */
  public final void error ( String method, String message, Exception exc )
  {
    System.out.println ( "[" + Thread.currentThread ().getName () + ": " //$NON-NLS-1$ //$NON-NLS-2$
        + this.clazz.getSimpleName () + "." + method + "]" ); //$NON-NLS-1$//$NON-NLS-2$
    System.err.println ( message );
    exc.printStackTrace ();
  }
}
