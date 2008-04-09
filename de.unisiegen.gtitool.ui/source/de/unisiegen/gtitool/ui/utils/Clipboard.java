package de.unisiegen.gtitool.ui.utils;


import java.awt.Toolkit;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import de.unisiegen.gtitool.core.Messages;
import de.unisiegen.gtitool.logger.Logger;


/**
 * Can copy a text into the clipboard and can read the string, which is
 * currently saved in the clipboard.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class Clipboard implements ClipboardOwner
{

  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger ( Clipboard.class );


  /**
   * The empty string.
   */
  private static final String EMPTY = ""; //$NON-NLS-1$


  /**
   * The unused string for the {@link SuppressWarnings}.
   */
  private static final String UNUSED = "unused"; //$NON-NLS-1$


  /**
   * The single object of the {@link Clipboard}.
   */
  private static Clipboard singleClipboard = null;


  /**
   * Returns the single object of {@link Clipboard}.
   * 
   * @return The single object of {@link Clipboard}.
   */
  public final static Clipboard getInstance ()
  {
    if ( singleClipboard == null )
    {
      singleClipboard = new Clipboard ();
    }
    return singleClipboard;
  }


  /**
   * The clipboard.
   */
  private java.awt.datatransfer.Clipboard systemClipboard;


  /**
   * Initializes the {@link Clipboard}.
   */
  private Clipboard ()
  {
    this.systemClipboard = Toolkit.getDefaultToolkit ().getSystemClipboard ();
  }


  /**
   * Copies the given text into the clipboard.
   * 
   * @param text The text, which should be copied into the clipboard.
   */
  public final void copy ( String text )
  {
    try
    {
      logger.debug ( "copy", "copy " + Messages.QUOTE + text + Messages.QUOTE //$NON-NLS-1$ //$NON-NLS-2$
          + " into the clipboard" ); //$NON-NLS-1$
      this.systemClipboard.setContents ( new StringSelection ( text ), this );
    }
    catch ( IllegalStateException e )
    {
      // Do nothing
    }
  }


  /**
   * {@inheritDoc}
   */
  public final void lostOwnership ( @SuppressWarnings ( UNUSED )
  java.awt.datatransfer.Clipboard clipboard, @SuppressWarnings ( UNUSED )
  Transferable contents )
  {
    // Do Nothing
  }


  /**
   * Returns the string, which is currently saved in the clipboard.
   * 
   * @return The string, which is currently saved in the clipboard.
   */
  public final String paste ()
  {
    Transferable transfer = this.systemClipboard.getContents ( null );
    try
    {
      String text = ( String ) transfer
          .getTransferData ( DataFlavor.stringFlavor );
      logger.debug ( "paste", "paste " + Messages.QUOTE + text + Messages.QUOTE //$NON-NLS-1$//$NON-NLS-2$
          + " from the clipboard" ); //$NON-NLS-1$
      return text;
    }
    catch ( UnsupportedFlavorException e )
    {
      // Do nothing
    }
    catch ( IOException e )
    {
      // Do nothing
    }
    return EMPTY;
  }
}
