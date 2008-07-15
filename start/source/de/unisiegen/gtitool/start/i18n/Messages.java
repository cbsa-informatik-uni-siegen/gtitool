package de.unisiegen.gtitool.start.i18n;


import java.util.MissingResourceException;
import java.util.ResourceBundle;


/**
 * The class to get the messages.
 * 
 * @author Christian Fehler
 * @version $Id:MessagesStart.java 761 2008-04-10 22:22:51Z fehler $
 */
public final class Messages
{

  /**
   * Gets a string for the given key from the resource bundle of the start
   * project.
   * 
   * @param key The key for the desired string.
   * @param arguments The optional arguments.
   * @return The string for the given key.
   */
  public final static String getString ( String key, Object [] arguments )
  {
    try
    {
      ResourceBundle resourceBundle = ResourceBundle
          .getBundle ( "de.unisiegen.gtitool.start.i18n.messages" ); //$NON-NLS-1$
      String message = resourceBundle.getString ( key );
      for ( int i = 0 ; i < arguments.length ; i++ )
      {
        if ( arguments [ i ] == null )
        {
          continue;
        }
        message = message.replace ( "{" + i + "}", //$NON-NLS-1$ //$NON-NLS-2$
            arguments [ i ].toString () );
      }
      return message;
    }
    catch ( MissingResourceException exc )
    {
      exc.printStackTrace ();
      return key;
    }
    catch ( IllegalArgumentException exc )
    {
      exc.printStackTrace ();
      return key;
    }
  }
}
