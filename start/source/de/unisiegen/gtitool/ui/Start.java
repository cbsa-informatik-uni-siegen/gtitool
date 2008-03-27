package de.unisiegen.gtitool.ui;


import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


/**
 * This is the start class, which checks the java version and shows a error
 * message if the java version is to old, or if the java version is correct,
 * starts the GTI Tool project.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class Start
{

  /**
   * Allocates a new {@link Start}.
   * 
   * @param arguments The command line arguments.
   */
  public Start ( String [] arguments )
  {
    new GTITool ( arguments );

    SwingUtilities.invokeLater ( new Runnable ()
    {

      public void run ()
      {
        JFrame frame = new JFrame ();

        int screenWidth = Toolkit.getDefaultToolkit ().getScreenSize ().width;
        int screenHeight = Toolkit.getDefaultToolkit ().getScreenSize ().height;
        frame.pack ();
        frame.setBounds ( ( screenWidth / 2 ) - ( frame.getWidth () / 2 ),
            ( screenHeight / 2 ) - ( frame.getHeight () / 2 ), frame
                .getWidth (), frame.getHeight () );
        frame.setVisible ( true );
      }
    } );
  }


  /**
   * The main method.
   * 
   * @param arguments The command line arguments.
   */
  public final static void main ( String [] arguments )
  {
    new Start ( arguments );
  }
}
