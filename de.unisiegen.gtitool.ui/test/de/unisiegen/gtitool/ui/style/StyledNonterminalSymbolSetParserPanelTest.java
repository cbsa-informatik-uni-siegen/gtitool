package de.unisiegen.gtitool.ui.style;


import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.listener.NonterminalSymbolSetChangedListener;
import de.unisiegen.gtitool.core.exceptions.terminalsymbol.TerminalSymbolException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;


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
    try
    {
      TerminalSymbolSet terminalSymbolSet = new DefaultTerminalSymbolSet (
          new DefaultTerminalSymbol ( "a" ), new DefaultTerminalSymbol ( "b" ),
          new DefaultTerminalSymbol ( "c" ) );

      JFrame jFrame = new JFrame ( "NonterminalSymbolSetPanelTest" );
      StyledNonterminalSymbolSetParserPanel styledNonterminalSymbolSetParserPanel = new StyledNonterminalSymbolSetParserPanel ();
      styledNonterminalSymbolSetParserPanel
          .setTerminalSymbolSet ( terminalSymbolSet );
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
    catch ( TerminalSymbolException exc )
    {
      exc.printStackTrace ();
    }
    catch ( TerminalSymbolSetException exc )
    {
      exc.printStackTrace ();
    }
  }
}
