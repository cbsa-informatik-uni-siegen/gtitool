package de.unisiegen.gtitool.ui;


import java.awt.EventQueue;

import javax.swing.UIManager;


/**
 * The main starter class for the GTITool project.
 * 
 * @author Christian Fehler
 * @version $Id$
 */

public class Start
{

  /**
   * The main entry point for the GTITool project. This method also sets up
   * native look and feel for the platform if possible.
   * 
   * @param pArguments The command line arguments.
   */
  public static void main ( String [] pArguments )
  {
    try
    {
      /*
       * Try to setup native look and feel for the platform, if the native look
       * and feel is not GTKLookAndFeel, because of different problems with this
       * look abd feel.
       */
      String nativeLAF = UIManager.getSystemLookAndFeelClassName ();
      if ( nativeLAF.contains ( "GTK" ) ) //$NON-NLS-1$
      {
        UIManager.setLookAndFeel ( "javax.swing.plaf.metal.MetalLookAndFeel" ); //$NON-NLS-1$
      }
      else
      {
        UIManager.setLookAndFeel ( UIManager.getSystemLookAndFeelClassName () );
      }
    }
    catch ( Exception e )
    {
      // Do nothing
    }
    EventQueue.invokeLater ( new Runnable ()
    {

      public void run ()
      {
        new MainWindow ();
      }
    } );
  }
}
