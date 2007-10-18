package de.unisiegen.gtitool.core;


import java.util.MissingResourceException;
import java.util.ResourceBundle;


/**
 * The class to get the messages.
 * 
 * @author Christian Fehler
 */
public class Messages
{

  /**
   * The resource bundle.
   */
  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
      .getBundle ( "de.unisiegen.gtitool.core.messages" ); //$NON-NLS-1$


  /**
   * Gets a string for the given key from the resource bundle.
   * 
   * @param pKey The key for the desired string.
   * @return The string for the given key.
   */
  public static String getString ( String pKey )
  {
    try
    {
      return RESOURCE_BUNDLE.getString ( pKey );
    }
    catch ( MissingResourceException e )
    {
      e.printStackTrace ();
      return '!' + pKey + '!';
    }
  }
}
