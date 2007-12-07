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
   * @param pKey The key for the desired string.
   * @param pArguments The optional arguments.
   * @return The string for the given key.
   */
  public final static String getString ( String pKey, Object ... pArguments )
  {
    try
    {
      ResourceBundle resourceBundle = ResourceBundle
          .getBundle ( "de.unisiegen.gtitool.ui.messages" ); //$NON-NLS-1$
      return MessageFormat.format ( resourceBundle.getString ( pKey ),
          pArguments );
    }
    catch ( MissingResourceException e )
    {
      return getStringCore ( pKey, pArguments );
    }
    catch ( IllegalArgumentException e )
    {
      return getStringCore ( pKey, pArguments );
    }
  }


  /**
   * Gets a string for the given key from the resource bundle of the core
   * project.
   * 
   * @param pKey The key for the desired string.
   * @param pArguments The optional arguments.
   * @return The string for the given key.
   */
  private final static String getStringCore ( String pKey,
      Object ... pArguments )
  {
    try
    {
      ResourceBundle resourceBundle = ResourceBundle
          .getBundle ( "de.unisiegen.gtitool.core.messages" ); //$NON-NLS-1$
      return MessageFormat.format ( resourceBundle.getString ( pKey ),
          pArguments );
    }
    catch ( MissingResourceException e )
    {
      logger.error ( "key not found", e ); //$NON-NLS-1$
      return pKey;
    }
    catch ( IllegalArgumentException e )
    {
      logger.error ( "illegal argument exception", e ); //$NON-NLS-1$
      return pKey;
    }
  }
}
