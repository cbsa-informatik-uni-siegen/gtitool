package de.unisiegen.gtitool.core;


import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;


/**
 * The class to get the messages.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class Messages
{

  /**
   * The {@link Logger} for this class.
   */
  private static final Logger LOGGER = Logger.getLogger ( Messages.class );


  /**
   * The quotation mark.
   */
  public static final String QUOTE = "'"; //$NON-NLS-1$


  /**
   * Gets a string for the given key from the resource bundle of the core
   * project.
   * 
   * @param key The key for the desired string.
   * @param useQuote Flag that indicates if the quotation marks should be used.
   * @param arguments The optional arguments.
   * @return The string for the given key.
   */
  public final static String getString ( String key, boolean useQuote,
      Object ... arguments )
  {
    try
    {
      ResourceBundle resourceBundle = ResourceBundle
          .getBundle ( "de.unisiegen.gtitool.core.messages" ); //$NON-NLS-1$
      String message = resourceBundle.getString ( key );
      for ( int i = 0 ; i < arguments.length ; i++ )
      {
        if ( arguments [ i ] == null )
        {
          continue;
        }
        if ( useQuote )
        {
          message = message.replace ( "{" + i + "}", //$NON-NLS-1$ //$NON-NLS-2$
              QUOTE + arguments [ i ].toString () + QUOTE );
        }
        else
        {
          message = message.replace ( "{" + i + "}", //$NON-NLS-1$ //$NON-NLS-2$
              arguments [ i ].toString () );
        }
      }
      return message;
    }
    catch ( MissingResourceException e )
    {
      LOGGER.error ( "key not found", e ); //$NON-NLS-1$
      return key;
    }
    catch ( IllegalArgumentException e )
    {
      LOGGER.error ( "illegal argument exception", e ); //$NON-NLS-1$
      return key;
    }
  }


  /**
   * Gets a string for the given key from the resource bundle of the core
   * project.
   * 
   * @param key The key for the desired string.
   * @param arguments The optional arguments.
   * @return The string for the given key.
   */
  public final static String getString ( String key, Object ... arguments )
  {
    return getString ( key, true, arguments );
  }
}
