package de.unisiegen.gtitool.ui.style;


import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.listener.NonterminalSymbolSetChangedListener;


/**
 * The test class of the {@link StyledNonterminalSymbolSetParserPanel}.
 * 
 * @author Christian Fehler
 * @version $Id: StyledAlphabetParserPanelTest.java 547 2008-02-10 22:24:57Z
 *          fehler $
 */
@SuppressWarnings (
{ "all" } )
public class StyledNonterminalSymbolSetParserPanelTest
{

  public static void main ( String [] arguments )
  {
    JFrame jFrame = new JFrame ( "NonterminalSymbolSetPanelTest" );
    StyledNonterminalSymbolSetParserPanel styledNonterminalSymbolSetParserPanel = new StyledNonterminalSymbolSetParserPanel ();
    styledNonterminalSymbolSetParserPanel
        .addNonterminalSymbolSetChangedListener ( new NonterminalSymbolSetChangedListener ()
        {

          public void nonterminalSymbolSetChanged (
              NonterminalSymbolSet newNonterminalSymbolSet )
          {
            if ( newNonterminalSymbolSet != null )
            {
              System.out.println ( newNonterminalSymbolSet );
            }
          }
        } );
    jFrame.add ( styledNonterminalSymbolSetParserPanel );
    jFrame.setBounds ( 300, 300, 400, 300 );
    jFrame.setDefaultCloseOperation ( jFrame.DISPOSE_ON_CLOSE );
    jFrame.setVisible ( true );
  }
}
