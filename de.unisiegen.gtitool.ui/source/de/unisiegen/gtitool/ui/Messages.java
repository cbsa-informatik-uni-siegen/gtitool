package de.unisiegen.gtitool.ui;


import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import de.unisiegen.gtitool.ui.preferences.item.LanguageItem;


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
    catch ( MissingResourceException e )
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
   * @param arguments The optional arguments.
   * @return The string for the given key.
   */
  public final static String getString ( String key, Object ... arguments )
  {
    try
    {
      ResourceBundle resourceBundle = ResourceBundle
          .getBundle ( "de.unisiegen.gtitool.ui.messages" ); //$NON-NLS-1$
      return MessageFormat
          .format ( resourceBundle.getString ( key ), arguments );
    }
    catch ( MissingResourceException e )
    {
      return de.unisiegen.gtitool.core.Messages.getString ( key, arguments );
    }
    catch ( IllegalArgumentException e )
    {
      logger.error ( "illegal argument exception", e ); //$NON-NLS-1$
      return key;
    }
  }
}
