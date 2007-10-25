package de.unisiegen.gtitool.ui;


import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import de.unisiegen.gtitool.ui.logic.MainWindow;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


/**
 * The main starter class for the GTITool project.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class Start
{

  /**
   * The main entry point for the GTI Tool project. This method also sets up
   * look and feel for the platform if possible.
   * 
   * @param pArguments The command line arguments.
   */
  public static void main ( String [] pArguments )
  {
    try
    {
      UIManager.setLookAndFeel ( PreferenceManager.getInstance ()
          .getLookAndFeelItem ().getClassName () );
    }
    catch ( ClassNotFoundException e )
    {
      e.printStackTrace ();
    }
    catch ( InstantiationException e )
    {
      e.printStackTrace ();
    }
    catch ( IllegalAccessException e )
    {
      e.printStackTrace ();
    }
    catch ( UnsupportedLookAndFeelException e )
    {
      e.printStackTrace ();
    }
    SwingUtilities.invokeLater ( new Runnable ()
    {

      public void run ()
      {
        new MainWindow ();
      }
    } );
  }
}
