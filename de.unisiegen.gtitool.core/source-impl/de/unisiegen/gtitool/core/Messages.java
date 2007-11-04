package de.unisiegen.gtitool.core;


import java.text.MessageFormat;
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
  private static final Logger logger = Logger.getLogger ( Messages.class );


  /**
   * Gets a string for the given key from the resource bundle of the core
   * project.
   * 
   * @param pKey The key for the desired string.
   * @param pArguments The optional arguments.
   * @return The string for the given key.
   */
  public final static String getString ( String pKey, String ... pArguments )
  {
    try
    {
      ResourceBundle bundle = ResourceBundle
          .getBundle ( "de.unisiegen.gtitool.core.messages" ); //$NON-NLS-1$
      return MessageFormat.format ( bundle.getString ( pKey ),
          ( Object [] ) pArguments );
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
