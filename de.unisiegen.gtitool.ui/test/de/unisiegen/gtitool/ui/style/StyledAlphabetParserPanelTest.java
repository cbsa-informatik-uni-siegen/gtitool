package de.unisiegen.gtitool.ui.style;


import javax.swing.JFrame;
import javax.swing.WindowConstants;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;


/**
 * The test class of the {@link StyledAlphabetParserPanel}.
 * 
 * @author Christian Fehler
 * @version $Id: StyledAlphabetParserPanelTest.java 547 2008-02-10 22:24:57Z
 *          fehler $
 */
public class StyledAlphabetParserPanelTest
{

  /**
   * The main method.
   * 
   * @param arguments The arguments.
   */
  public static void main ( String [] arguments )
  {
    JFrame jFrame = new JFrame ( "AlphabetPanelTest" ); //$NON-NLS-1$
    StyledAlphabetParserPanel styledAlphabetParserPanel = new StyledAlphabetParserPanel ();
    styledAlphabetParserPanel
        .addParseableChangedListener ( new ParseableChangedListener < Alphabet > ()
        {

          public void parseableChanged ( Alphabet newAlphabet )
          {
            if ( newAlphabet != null )
            {
              System.out.println ( newAlphabet );
            }
          }
        } );
    jFrame.add ( styledAlphabetParserPanel );
    jFrame.setBounds ( 300, 300, 400, 300 );
    jFrame.setDefaultCloseOperation ( WindowConstants.DISPOSE_ON_CLOSE );
    jFrame.setVisible ( true );
  }
}
