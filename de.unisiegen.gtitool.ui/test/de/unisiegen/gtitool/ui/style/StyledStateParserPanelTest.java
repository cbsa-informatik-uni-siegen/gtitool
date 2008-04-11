package de.unisiegen.gtitool.ui.style;


import javax.swing.JFrame;
import javax.swing.WindowConstants;

import de.unisiegen.gtitool.core.entities.Stack;
import de.unisiegen.gtitool.core.entities.listener.StackChangedListener;


/**
 * The test class of the {@link StyledStateParserPanel}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class StyledStateParserPanelTest
{

  /**
   * The main methos.
   * 
   * @param arguments The arguments.
   */
  public static void main ( String [] arguments )
  {
    JFrame jFrame = new JFrame ( "StackPanelTest" ); //$NON-NLS-1$
    StyledStackParserPanel styledStackParserPanel = new StyledStackParserPanel ();
    styledStackParserPanel
        .addStackChangedListener ( new StackChangedListener ()
        {

          public void stackChanged ( Stack stack )
          {
            if ( stack != null )
            {
              System.out.println ( stack );
            }
          }
        } );
    jFrame.add ( styledStackParserPanel );
    jFrame.setBounds ( 300, 300, 400, 300 );
    jFrame.setDefaultCloseOperation ( WindowConstants.DISPOSE_ON_CLOSE );
    jFrame.setVisible ( true );
  }
}
