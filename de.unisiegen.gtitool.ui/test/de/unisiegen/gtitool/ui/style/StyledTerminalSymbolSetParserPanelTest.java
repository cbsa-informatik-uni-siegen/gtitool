package de.unisiegen.gtitool.ui.style;


import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.listener.TerminalSymbolSetChangedListener;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbol.NonterminalSymbolException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;


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
    try
    {
      NonterminalSymbolSet nonterminalSymbolSet = new DefaultNonterminalSymbolSet (
          new DefaultNonterminalSymbol ( "E" ), new DefaultNonterminalSymbol (
              "F" ), new DefaultNonterminalSymbol ( "G" ) );

      JFrame jFrame = new JFrame ( "TerminalSymbolSetPanelTest" );
      StyledTerminalSymbolSetParserPanel styledTerminalSymbolSetParserPanel = new StyledTerminalSymbolSetParserPanel ();
      styledTerminalSymbolSetParserPanel
          .setNonterminalSymbolSet ( nonterminalSymbolSet );
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
    catch ( NonterminalSymbolException exc )
    {
      exc.printStackTrace ();
    }
    catch ( NonterminalSymbolSetException exc )
    {
      exc.printStackTrace ();
    }
  }
}
