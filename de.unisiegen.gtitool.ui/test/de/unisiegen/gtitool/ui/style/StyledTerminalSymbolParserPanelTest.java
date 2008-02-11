package de.unisiegen.gtitool.ui.style;


import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.listener.TerminalSymbolChangedListener;


/**
 * The test class of the {@link StyledTerminalSymbolParserPanel}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings (
{ "all" } )
public class StyledTerminalSymbolParserPanelTest
{

  public static void main ( String [] arguments )
  {
    JFrame jFrame = new JFrame ( "TerminalSymbolPanelTest" );
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
    jFrame.setVisible ( true );
  }
}