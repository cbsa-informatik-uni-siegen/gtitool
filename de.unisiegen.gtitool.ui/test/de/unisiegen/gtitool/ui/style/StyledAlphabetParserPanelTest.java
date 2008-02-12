package de.unisiegen.gtitool.ui.style;


import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.listener.AlphabetChangedListener;


/**
 * The test class of the {@link StyledAlphabetParserPanel}.
 * 
 * @author Christian Fehler
 * @version $Id: StyledAlphabetParserPanelTest.java 547 2008-02-10 22:24:57Z
 *          fehler $
 */
@SuppressWarnings (
{ "all" } )
public class StyledAlphabetParserPanelTest
{

  public static void main ( String [] arguments )
  {
    JFrame jFrame = new JFrame ( "AlphabetPanelTest" );
    StyledAlphabetParserPanel styledAlphabetParserPanel = new StyledAlphabetParserPanel ();
    styledAlphabetParserPanel
        .addAlphabetChangedListener ( new AlphabetChangedListener ()
        {

          public void alphabetChanged ( Alphabet alphabet )
          {
            if ( alphabet != null )
            {
              System.out.println ( alphabet );
            }
          }
        } );
    jFrame.add ( styledAlphabetParserPanel );
    jFrame.setBounds ( 300, 300, 400, 300 );
    jFrame.setDefaultCloseOperation ( jFrame.DISPOSE_ON_CLOSE );
    jFrame.setVisible ( true );
  }
}
