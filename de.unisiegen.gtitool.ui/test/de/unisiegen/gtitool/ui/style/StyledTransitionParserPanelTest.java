package de.unisiegen.gtitool.ui.style;


import javax.swing.JFrame;
import javax.swing.WindowConstants;

import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;


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
   * The main method.
   * 
   * @param arguments The arguments.
   */
  public static void main ( String [] arguments )
  {
    JFrame jFrame = new JFrame ( "TransitionPanelTest" ); //$NON-NLS-1$
    StyledTransitionParserPanel styledTransitionParserPanel = new StyledTransitionParserPanel ();
    styledTransitionParserPanel
        .addParseableChangedListener ( new ParseableChangedListener < Transition > ()
        {

          public void parseableChanged ( Transition newTransition )
          {
            if ( newTransition != null )
            {
              System.out.println ( newTransition );
            }
          }
        } );
    jFrame.add ( styledTransitionParserPanel );
    jFrame.setBounds ( 300, 300, 400, 300 );
    jFrame.setDefaultCloseOperation ( WindowConstants.DISPOSE_ON_CLOSE );
    jFrame.setVisible ( true );
  }
}
