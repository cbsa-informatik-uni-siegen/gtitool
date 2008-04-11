package de.unisiegen.gtitool.ui.style;


import javax.swing.JFrame;
import javax.swing.WindowConstants;

import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.listener.TerminalSymbolChangedListener;


/**
 * The test class of the {@link StyledTerminalSymbolParserPanel}.
 * 
 * @author Christian Fehler
 * @version $Id: StyledTerminalSymbolParserPanelTest.java 547 2008-02-10
 *          22:24:57Z fehler $
 */
public class StyledTerminalSymbolParserPanelTest
{

  /**
   * The main methos.
   * 
   * @param arguments The arguments.
   */
  public static void main ( String [] arguments )
  {
    JFrame jFrame = new JFrame ( "TerminalSymbolPanelTest" ); //$NON-NLS-1$
    StyledTerminalSymbolParserPanel styledTerminalSymbolParserPanel = new StyledTerminalSymbolParserPanel ();
    styledTerminalSymbolParserPanel
        .addTerminalSymbolChangedListener ( new TerminalSymbolChangedListener ()
        {

          public void terminalSymbolChanged ( TerminalSymbol terminalSymbol )
          {
            if ( terminalSymbol != null )
            {
              System.out.println ( terminalSymbol );
            }
          }
        } );
    jFrame.add ( styledTerminalSymbolParserPanel );
    jFrame.setBounds ( 300, 300, 400, 300 );
    jFrame.setDefaultCloseOperation ( WindowConstants.DISPOSE_ON_CLOSE );
    jFrame.setVisible ( true );
  }
}
