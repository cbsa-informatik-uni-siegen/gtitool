package de.unisiegen.gtitool.core;


import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import de.unisiegen.gtitool.core.parser.style.PrettyPrintable;
import de.unisiegen.gtitool.core.parser.style.PrettyString;
import de.unisiegen.gtitool.core.parser.style.PrettyToken;
import de.unisiegen.gtitool.core.parser.style.Style;


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
  private static final Logger logger = Logger.getLogger ( Messages.class );


  /**
   * The quotation mark.
   */
  public static final String QUOTE = "'"; //$NON-NLS-1$


  /**
   * Gets a {@link PrettyString} for the given key from the resource bundle of
   * the core project.
   * 
   * @param key The key for the desired string.
   * @param useQuote Flag that indicates if the quotation marks should be used.
   * @param arguments The optional arguments.
   * @return The string for the given key.
   */
  public final static PrettyString getPrettyString ( String key,
      boolean useQuote, PrettyPrintable ... arguments )
  {
    PrettyString [] prettyStrings = new PrettyString [ arguments.length ];
    for ( int i = 0 ; i < arguments.length ; i++ )
    {
      prettyStrings [ i ] = arguments [ i ].toPrettyString ();
    }
    return getPrettyString ( key, useQuote, prettyStrings );
  }


  /**
   * Gets a {@link PrettyString} for the given key from the resource bundle of
   * the core project.
   * 
   * @param key The key for the desired string.
   * @param useQuote Flag that indicates if the quotation marks should be used.
   * @param arguments The optional arguments.
   * @return The string for the given key.
   */
  public final static PrettyString getPrettyString ( String key,
      boolean useQuote, PrettyString ... arguments )
  {
    try
    {
      ResourceBundle resourceBundle = ResourceBundle
          .getBundle ( "de.unisiegen.gtitool.core.messages" ); //$NON-NLS-1$
      PrettyString message = new PrettyString ( new PrettyToken (
          resourceBundle.getString ( key ), Style.NONE ) );
      for ( int i = 0 ; i < arguments.length ; i++ )
      {
        if ( arguments [ i ] == null )
        {
          continue;
        }
        if ( useQuote )
        {
          PrettyString newPrettyString = new PrettyString ();
          newPrettyString
              .addPrettyToken ( new PrettyToken ( QUOTE, Style.NONE ) );
          newPrettyString.addPrettyString ( arguments [ i ] );
          newPrettyString
              .addPrettyToken ( new PrettyToken ( QUOTE, Style.NONE ) );

          message.replace ( "{" + i + "}", newPrettyString ); //$NON-NLS-1$//$NON-NLS-2$
        }
        else
        {
          message.replace ( "{" + i + "}", arguments [ i ] ); //$NON-NLS-1$//$NON-NLS-2$
        }
      }
      return message;
    }
    catch ( MissingResourceException e )
    {
      logger.error ( "key not found", e ); //$NON-NLS-1$
      return new PrettyString ( new PrettyToken ( key, Style.NONE ) );
    }
    catch ( IllegalArgumentException e )
    {
      logger.error ( "illegal argument exception", e ); //$NON-NLS-1$
      return new PrettyString ( new PrettyToken ( key, Style.NONE ) );
    }
  }


  /**
   * Gets a {@link PrettyString} for the given key from the resource bundle of
   * the core project.
   * 
   * @param key The key for the desired string.
   * @param arguments The optional arguments.
   * @return The string for the given key.
   */
  public final static PrettyString getPrettyString ( String key,
      PrettyPrintable ... arguments )
  {
    return getPrettyString ( key, true, arguments );
  }


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
      logger.error ( "key not found", e ); //$NON-NLS-1$
      return key;
    }
    catch ( IllegalArgumentException e )
    {
      logger.error ( "illegal argument exception", e ); //$NON-NLS-1$
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
