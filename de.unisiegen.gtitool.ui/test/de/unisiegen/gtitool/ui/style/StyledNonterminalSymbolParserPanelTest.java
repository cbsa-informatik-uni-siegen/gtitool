package de.unisiegen.gtitool.ui.style;


import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.listener.NonterminalSymbolChangedListener;


/**
 * The test class of the {@link StyledNonterminalSymbolParserPanel}.
 * 
 * @author Christian Fehler
 * @version $Id: StyledNonterminalSymbolParserPanelTest.java 547 2008-02-10
 *          22:24:57Z fehler $
 */
@SuppressWarnings (
{ "all" } )
public class StyledNonterminalSymbolParserPanelTest
{

  public static void main ( String [] arguments )
  {
    JFrame jFrame = new JFrame ( "NonterminalSymbolPanelTest" );
    StyledNonterminalSymbolParserPanel styledNonterminalSymbolParserPanel = new StyledNonterminalSymbolParserPanel ();
    styledNonterminalSymbolParserPanel
        .addNonterminalSymbolChangedListener ( new NonterminalSymbolChangedListener ()
        {

          public void nonterminalSymbolChanged (
              NonterminalSymbol nonterminalSymbol )
          {
            if ( nonterminalSymbol != null )
            {
              System.out.println ( nonterminalSymbol );
            }
          }
        } );
    jFrame.add ( styledNonterminalSymbolParserPanel );
    jFrame.setBounds ( 300, 300, 400, 300 );
    jFrame.setDefaultCloseOperation ( jFrame.DISPOSE_ON_CLOSE );
    jFrame.setVisible ( true );
  }
}
