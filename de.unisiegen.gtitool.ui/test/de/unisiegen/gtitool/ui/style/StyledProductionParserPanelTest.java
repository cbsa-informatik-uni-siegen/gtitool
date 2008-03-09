package de.unisiegen.gtitool.ui.style;


import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.listener.ProductionChangedListener;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbol.NonterminalSymbolException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbol.TerminalSymbolException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;


/**
 * The test class of the {@link StyledProductionParserPanel}.
 * 
 * @author Christian Fehler
 * @version $Id: StyledProductionParserPanelTest.java 547 2008-02-10 22:24:57Z
 *          fehler $
 */
@SuppressWarnings (
{ "all" } )
public class StyledProductionParserPanelTest
{

  public static void main ( String [] arguments )
  {
    try
    {
      NonterminalSymbolSet nonterminalSymbolSet = new DefaultNonterminalSymbolSet (
          new DefaultNonterminalSymbol ( "E" ), new DefaultNonterminalSymbol (
              "F" ), new DefaultNonterminalSymbol ( "G" ) );

      TerminalSymbolSet terminalSymbolSet = new DefaultTerminalSymbolSet (
          new DefaultTerminalSymbol ( "a" ), new DefaultTerminalSymbol ( "b" ),
          new DefaultTerminalSymbol ( "c" ) );

      JFrame jFrame = new JFrame ( "ProductionPanelTest" );
      StyledProductionParserPanel styledProductionParserPanel = new StyledProductionParserPanel ();

      styledProductionParserPanel
          .setNonterminalSymbolSet ( nonterminalSymbolSet );
      styledProductionParserPanel.setTerminalSymbolSet ( terminalSymbolSet );

      styledProductionParserPanel
          .addProductionChangedListener ( new ProductionChangedListener ()
          {

            public void productionChanged ( Production production )
            {
              if ( production != null )
              {
                System.out.println ( production.toStringDebug () );
              }
            }
          } );
      jFrame.add ( styledProductionParserPanel );
      jFrame.setBounds ( 300, 300, 400, 300 );
      jFrame.setDefaultCloseOperation ( jFrame.DISPOSE_ON_CLOSE );
      jFrame.setVisible ( true );
    }
    catch ( NonterminalSymbolSetException exc )
    {
      exc.printStackTrace ();
    }
    catch ( TerminalSymbolSetException exc )
    {
      exc.printStackTrace ();
    }
    catch ( NonterminalSymbolException exc )
    {
      exc.printStackTrace ();
    }
    catch ( TerminalSymbolException exc )
    {
      exc.printStackTrace ();
    }
  }
}
