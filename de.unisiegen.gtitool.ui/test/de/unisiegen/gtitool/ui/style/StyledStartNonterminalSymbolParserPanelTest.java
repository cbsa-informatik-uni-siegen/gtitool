package de.unisiegen.gtitool.ui.style;


import javax.swing.JFrame;
import javax.swing.WindowConstants;

import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;


/**
 * The test class of the {@link StyledStartNonterminalSymbolParserPanel}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class StyledStartNonterminalSymbolParserPanelTest
{

  /**
   * The main method.
   * 
   * @param arguments The arguments.
   */
  public static void main ( String [] arguments )
  {
    JFrame jFrame = new JFrame ( "StartNonterminalSymbolPanelTest" ); //$NON-NLS-1$
    StyledStartNonterminalSymbolParserPanel styledStartNonterminalSymbolParserPanel = new 
    StyledStartNonterminalSymbolParserPanel ();
    styledStartNonterminalSymbolParserPanel
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
    jFrame.add ( styledStartNonterminalSymbolParserPanel );
    jFrame.setBounds ( 300, 300, 400, 300 );
    jFrame.setDefaultCloseOperation ( WindowConstants.DISPOSE_ON_CLOSE );
    jFrame.setVisible ( true );
  }
}
