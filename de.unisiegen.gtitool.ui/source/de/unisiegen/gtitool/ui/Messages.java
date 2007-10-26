package de.unisiegen.gtitool.ui;


import java.util.ResourceBundle;


/**
 * The class to get the messages.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class Messages
{

  /**
   * Gets a string for the given key from the resource bundle.
   * 
   * @param pKey The key for the desired string.
   * @return The string for the given key.
   */
  public static String getString ( String pKey )
  {
    return ResourceBundle.getBundle ( "de.unisiegen.gtitool.ui.messages" ) //$NON-NLS-1$
        .getString ( pKey );
  }
}
