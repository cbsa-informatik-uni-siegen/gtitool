package de.unisiegen.gtitool.ui.style;


import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.listener.TerminalSymbolSetChangedListener;


/**
 * The test class of the {@link StyledTerminalSymbolSetParserPanel}.
 * 
 * @author Christian Fehler
 * @version $Id: StyledAlphabetParserPanelTest.java 547 2008-02-10 22:24:57Z
 *          fehler $
 */
@SuppressWarnings (
{ "all" } )
public class StyledTerminalSymbolSetParserPanelTest
{

  public static void main ( String [] arguments )
  {
    JFrame jFrame = new JFrame ( "TerminalSymbolSetPanelTest" );
    StyledTerminalSymbolSetParserPanel styledTerminalSymbolSetParserPanel = new StyledTerminalSymbolSetParserPanel ();
    styledTerminalSymbolSetParserPanel
        .addTerminalSymbolSetChangedListener ( new TerminalSymbolSetChangedListener ()
        {

          public void terminalSymbolSetChanged (
              TerminalSymbolSet newTerminalSymbolSet )
          {
            if ( newTerminalSymbolSet != null )
            {
              System.out.println ( newTerminalSymbolSet );
            }
          }
        } );
    jFrame.add ( styledTerminalSymbolSetParserPanel );
    jFrame.setBounds ( 300, 300, 400, 300 );
    jFrame.setDefaultCloseOperation ( jFrame.DISPOSE_ON_CLOSE );
    jFrame.setVisible ( true );
  }
}
