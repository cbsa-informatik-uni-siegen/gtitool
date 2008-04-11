package de.unisiegen.gtitool.ui.style;


import javax.swing.JFrame;
import javax.swing.WindowConstants;

import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.listener.TransitionChangedListener;


/**
 * The test class of the {@link StyledTransitionParserPanel}.
 * 
 * @author Christian Fehler
 * @version $Id: StyledTransitionParserPanelTest.java 547 2008-02-10 22:24:57Z
 *          fehler $
 */
public class StyledTransitionParserPanelTest
{

  /**
   * The main methos.
   * 
   * @param arguments The arguments.
   */
  public static void main ( String [] arguments )
  {
    JFrame jFrame = new JFrame ( "TransitionPanelTest" ); //$NON-NLS-1$
    StyledTransitionParserPanel styledTransitionParserPanel = new StyledTransitionParserPanel ();
    styledTransitionParserPanel
        .addTransitionChangedListener ( new TransitionChangedListener ()
        {

          public void transitionChanged ( Transition transition )
          {
            if ( transition != null )
            {
              System.out.println ( transition );
            }
          }
        } );
    jFrame.add ( styledTransitionParserPanel );
    jFrame.setBounds ( 300, 300, 400, 300 );
    jFrame.setDefaultCloseOperation ( WindowConstants.DISPOSE_ON_CLOSE );
    jFrame.setVisible ( true );
  }
}
