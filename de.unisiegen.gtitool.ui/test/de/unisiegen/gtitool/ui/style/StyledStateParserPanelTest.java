package de.unisiegen.gtitool.ui.style;


import javax.swing.JFrame;
import javax.swing.WindowConstants;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;


/**
 * The test class of the {@link StyledStateParserPanel}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class StyledStateParserPanelTest
{

  /**
   * The main method.
   * 
   * @param arguments The arguments.
   */
  public static void main ( String [] arguments )
  {
    JFrame jFrame = new JFrame ( "StatePanelTest" ); //$NON-NLS-1$
    StyledStateParserPanel styledStateParserPanel = new StyledStateParserPanel ();
    styledStateParserPanel
        .addParseableChangedListener ( new ParseableChangedListener < State > ()
        {

          public void parseableChanged ( State newState )
          {
            if ( newState != null )
            {
              System.out.println ( newState );
            }
          }
        } );
    jFrame.add ( styledStateParserPanel );
    jFrame.setBounds ( 300, 300, 400, 300 );
    jFrame.setDefaultCloseOperation ( WindowConstants.DISPOSE_ON_CLOSE );
    jFrame.setVisible ( true );
  }
}
