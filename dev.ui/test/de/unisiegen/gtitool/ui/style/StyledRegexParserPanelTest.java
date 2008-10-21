package de.unisiegen.gtitool.ui.style;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import de.unisiegen.gtitool.core.entities.regex.RegexNode;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;


/**
 * TODO
 *
 */
public class StyledRegexParserPanelTest
{

  /**
   * The main method.
   * 
   * @param arguments The arguments.
   */
  public static void main ( String [] arguments )
  {
    JFrame jFrame = new JFrame ( "RegexPanelTest" ); //$NON-NLS-1$
    StyledRegexParserPanel styledRegexParserPanel = new StyledRegexParserPanel ();
    styledRegexParserPanel
        .addParseableChangedListener ( new ParseableChangedListener < RegexNode > ()
        {

          public void parseableChanged ( RegexNode newRegex )
          {
            if ( newRegex != null )
            {
              System.out.println ( newRegex );
            }
          }
        } );
    jFrame.add ( styledRegexParserPanel );
    jFrame.setBounds ( 300, 300, 400, 300 );
    jFrame.setDefaultCloseOperation ( WindowConstants.DISPOSE_ON_CLOSE );
    jFrame.setVisible ( true );
  }

}