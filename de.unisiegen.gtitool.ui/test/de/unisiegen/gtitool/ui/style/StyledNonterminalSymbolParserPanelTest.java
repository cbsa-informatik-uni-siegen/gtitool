package de.unisiegen.gtitool.ui.style;


import javax.swing.JFrame;
import javax.swing.WindowConstants;

import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;


/**
 * The test class of the {@link StyledNonterminalSymbolParserPanel}.
 * 
 * @author Christian Fehler
 * @version $Id: StyledNonterminalSymbolParserPanelTest.java 547 2008-02-10
 *          22:24:57Z fehler $
 */
public class StyledNonterminalSymbolParserPanelTest
{

  /**
   * The main method.
   * 
   * @param arguments The arguments.
   */
  public static void main ( String [] arguments )
  {
    JFrame jFrame = new JFrame ( "NonterminalSymbolPanelTest" ); //$NON-NLS-1$
    StyledNonterminalSymbolParserPanel styledNonterminalSymbolParserPanel = new StyledNonterminalSymbolParserPanel ();
    styledNonterminalSymbolParserPanel
        .addParseableChangedListener ( new ParseableChangedListener < NonterminalSymbol > ()
        {

          public void parseableChanged ( NonterminalSymbol newNonterminalSymbol )
          {
            if ( newNonterminalSymbol != null )
            {
              System.out.println ( newNonterminalSymbol );
            }
          }
        } );
    jFrame.add ( styledNonterminalSymbolParserPanel );
    jFrame.setBounds ( 300, 300, 400, 300 );
    jFrame.setDefaultCloseOperation ( WindowConstants.DISPOSE_ON_CLOSE );
    jFrame.setVisible ( true );
  }
}
