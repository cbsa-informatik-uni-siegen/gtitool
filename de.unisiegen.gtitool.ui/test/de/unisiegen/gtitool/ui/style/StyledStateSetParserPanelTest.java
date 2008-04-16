package de.unisiegen.gtitool.ui.style;


import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import de.unisiegen.gtitool.core.entities.DefaultState;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.StateSet;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;


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
    try
    {
      JFrame jFrame = new JFrame ( "StateSetPanelTest" ); //$NON-NLS-1$

      ArrayList < State > stateList = new ArrayList < State > ();
      stateList.add ( new DefaultState ( "z0" ) ); //$NON-NLS-1$
      stateList.add ( new DefaultState ( "z1" ) );//$NON-NLS-1$
      stateList.add ( new DefaultState ( "z2" ) );//$NON-NLS-1$

      StyledStateSetParserPanel styledStateSetParserPanel = new StyledStateSetParserPanel ();
      styledStateSetParserPanel.setStateList ( stateList );
      styledStateSetParserPanel
          .addParseableChangedListener ( new ParseableChangedListener < StateSet > ()
          {

            public void parseableChanged ( StateSet newStateSet )
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
    catch ( StateException exc )
    {
      exc.printStackTrace ();
    }
  }
}
