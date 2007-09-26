package de.unisiegen.gtitool.ui ;


import java.awt.EventQueue ;
import javax.swing.UIManager ;


/**
 * The main starter class for the GTITool project.
 * 
 * @author Christian Fehler
 */
public class Start
{
  /**
   * The main entry point for the GTITool project. This method also sets up
   * native look and feel for the platform if possible.
   * 
   * @param pArguments The command line arguments.
   */
  public static void main ( String [ ] pArguments )
  {
    try
    {
      UIManager.setLookAndFeel ( UIManager.getSystemLookAndFeelClassName ( ) ) ;
    }
    catch ( Exception e )
    {
      // Do nothing
    }
    EventQueue.invokeLater ( new Runnable ( )
    {
      public void run ( )
      {
        System.out.println ( "Implement this ..." ) ; //$NON-NLS-1$
      }
    } ) ;
  }
}
