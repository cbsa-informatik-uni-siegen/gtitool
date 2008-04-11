package de.unisiegen.gtitool.ui.style;


import javax.swing.JFrame;
import javax.swing.WindowConstants;

import de.unisiegen.gtitool.core.entities.StateSet;
import de.unisiegen.gtitool.core.entities.listener.StateSetChangedListener;


/**
 * The test class of the {@link StyledStateSetParserPanel}.
 * 
 * @author Christian Fehler
 * @version $Id: StyledStateSetParserPanelTest.java 547 2008-02-10 22:24:57Z
 *          fehler $
 */
public class StyledStateSetParserPanelTest
{

  /**
   * The main methos.
   * 
   * @param arguments The arguments.
   */
  public static void main ( String [] arguments )
  {
    JFrame jFrame = new JFrame ( "StateSetPanelTest" ); //$NON-NLS-1$
    StyledStateSetParserPanel styledStateSetParserPanel = new StyledStateSetParserPanel ();
    styledStateSetParserPanel
        .addStateSetChangedListener ( new StateSetChangedListener ()
        {

          public void stateSetChanged ( StateSet newStateSet )
          {
            if ( newStateSet != null )
            {
              System.out.println ( newStateSet );
            }
          }
        } );
    jFrame.add ( styledStateSetParserPanel );
    jFrame.setBounds ( 300, 300, 400, 300 );
    jFrame.setDefaultCloseOperation ( WindowConstants.DISPOSE_ON_CLOSE );
    jFrame.setVisible ( true );
  }
}
