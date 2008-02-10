package de.unisiegen.gtitool.ui.style;


import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.listener.NonterminalSymbolChangedListener;


/**
 * The test class of the {@link StyledNonterminalSymbolParserPanel}.
 * 
 * @author Christian Fehler
 * @version $Id$
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
    jFrame.setVisible ( true );
  }
}
