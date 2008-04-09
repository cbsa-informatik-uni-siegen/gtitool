package de.unisiegen.gtitool.ui;


import java.util.ArrayList;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import de.unisiegen.gtitool.core.preferences.item.LanguageItem;
import de.unisiegen.gtitool.logger.Logger;


/**
 * The class to get the messages and the available languages.
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
   * Returns the available {@link LanguageItem}s.
   * 
   * @return The available {@link LanguageItem}s.
   */
  public final static LanguageItem [] getLanguageItems ()
  {
    ArrayList < LanguageItem > list = new ArrayList < LanguageItem > ();
    try
    {
      ResourceBundle bundle = ResourceBundle
          .getBundle ( "de.unisiegen.gtitool.ui.languages" ); //$NON-NLS-1$
      int index = 0;
      while ( true )
      {
        String title = bundle.getString ( "Language" + index + ".Title" ); //$NON-NLS-1$ //$NON-NLS-2$
        String language = bundle.getString ( "Language" + index + ".Language" ); //$NON-NLS-1$//$NON-NLS-2$
        list.add ( new LanguageItem ( title, new Locale ( language ) ) );
        index++ ;
      }
    }
    catch ( MissingResourceException exc )
    {
      /*
       * Happens after the last language is added.
       */
    }
    return list.toArray ( new LanguageItem [] {} );
  }


  /**
   * Gets a string for the given key from the resource bundle of the ui project.
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
          .getBundle ( "de.unisiegen.gtitool.ui.messages" ); //$NON-NLS-1$
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
    catch ( MissingResourceException exc )
    {
      return de.unisiegen.gtitool.core.Messages.getString ( key, arguments );
    }
    catch ( IllegalArgumentException exc )
    {
      logger.error ( "getString", "illegal argument exception", exc ); //$NON-NLS-1$ //$NON-NLS-2$
      return key;
    }
  }


  /**
   * Gets a string for the given key from the resource bundle of the ui project.
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
