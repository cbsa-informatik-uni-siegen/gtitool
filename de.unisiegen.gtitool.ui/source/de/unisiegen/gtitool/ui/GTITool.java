package de.unisiegen.gtitool.ui;


import java.io.File;
import java.util.Locale;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.logic.MainWindow;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


/**
 * The main starter class for the {@link GTITool} project.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class GTITool
{

  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger ( GTITool.class );


  /**
   * The main entry point for the {@link GTITool} project.
   * 
   * @param arguments The command line arguments.
   */
  public final static void main ( String [] arguments )
  {
    new GTITool ( arguments );
  }


  /**
   * Allocates a new {@link GTITool}, sets the look and feel and starts the gui.
   * 
   * @param arguments The command line arguments.
   */
  public GTITool ( final String [] arguments )
  {
    /*
     * Install TinyLaF, if it is not the last active look and feel. Otherwise it
     * will be installed while set it up.
     */
    if ( !PreferenceManager.getInstance ().getLookAndFeelItem ()
        .getClassName ().equals ( "de.muntjak.tinylookandfeel.TinyLookAndFeel" ) ) //$NON-NLS-1$
    {
      UIManager.installLookAndFeel ( "TinyLaF", //$NON-NLS-1$
          "de.muntjak.tinylookandfeel.TinyLookAndFeel" ); //$NON-NLS-1$
    }

    /*
     * Set the last active look and feel
     */
    try
    {
      UIManager.setLookAndFeel ( PreferenceManager.getInstance ()
          .getLookAndFeelItem ().getClassName () );
    }
    catch ( ClassNotFoundException exc )
    {
      logger.error ( "GTITool", "class not found exception", exc ); //$NON-NLS-1$//$NON-NLS-2$
    }
    catch ( InstantiationException exc )
    {
      logger.error ( "GTITool", "instantiation exception", exc ); //$NON-NLS-1$//$NON-NLS-2$
    }
    catch ( IllegalAccessException exc )
    {
      logger.error ( "GTITool", "illegal access exception", exc ); //$NON-NLS-1$//$NON-NLS-2$
    }
    catch ( UnsupportedLookAndFeelException exc )
    {
      logger.error ( "GTITool", "unsupported look and feel exception", exc ); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /*
     * Set the last active language
     */
    PreferenceManager.getInstance ().setSystemLocale ( Locale.getDefault () );
    Locale.setDefault ( PreferenceManager.getInstance ().getLanguageItem ()
        .getLocale () );

    /*
     * Start the GTI Tool
     */
    SwingUtilities.invokeLater ( new Runnable ()
    {

      // allocate the main window
      public void run ()
      {
        MainWindow window = new MainWindow ();

        // check if any files are specified
        if ( arguments.length > 0 )
        {
          // open any specified files
          for ( String fileName : arguments )
          {
            File file = new File ( fileName );
            window.openFile ( file, true );
          }
        }
        else
        {
          // restore the files from the previous session
          window.restoreOpenFiles ();
        }
      }
    } );
  }
}
