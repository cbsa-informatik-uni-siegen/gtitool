package de.unisiegen.gtitool.ui;


import java.util.Locale;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.Logger;

import de.unisiegen.gtitool.ui.logic.MainWindow;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


/**
 * The main starter class for the GTITool project.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class Start
{

  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger ( Start.class );


  /**
   * The main entry point for the GTI Tool project. This method also sets up
   * look and feel for the platform if possible.
   * 
   * @param arguments The command line arguments.
   */
  public final static void main ( String [] arguments )
  {
    /*
     * Set the last active look and feel
     */
    try
    {
      UIManager.setLookAndFeel ( PreferenceManager.getInstance ()
          .getLookAndFeelItem ().getClassName () );
    }
    catch ( ClassNotFoundException e )
    {
      logger.error ( "class not found exception", e ); //$NON-NLS-1$
    }
    catch ( InstantiationException e )
    {
      logger.error ( "instantiation exception", e ); //$NON-NLS-1$
    }
    catch ( IllegalAccessException e )
    {
      logger.error ( "illegal access exception", e ); //$NON-NLS-1$
    }
    catch ( UnsupportedLookAndFeelException e )
    {
      logger.error ( "unsupported look and feel exception", e ); //$NON-NLS-1$
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

      public void run ()
      {
        new MainWindow ();
      }
    } );
  }
}
