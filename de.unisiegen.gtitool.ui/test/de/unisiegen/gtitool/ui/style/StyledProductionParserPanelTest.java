package de.unisiegen.gtitool.ui.style;


import javax.swing.JFrame;
import javax.swing.WindowConstants;

import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.MultiProduction;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;


/**
 * The test class of the {@link StyledProductionParserPanel}.
 * 
 * @author Christian Fehler
 * @version $Id: StyledProductionParserPanelTest.java 547 2008-02-10 22:24:57Z
 *          fehler $
 */
public class StyledProductionParserPanelTest
{

  /**
   * The main method.
   * 
   * @param arguments The arguments.
   */
  public static void main ( String [] arguments )
  {
    try
    {
      NonterminalSymbolSet nonterminalSymbolSet = new DefaultNonterminalSymbolSet (
          new DefaultNonterminalSymbol ( "E" ), new DefaultNonterminalSymbol ( //$NON-NLS-1$
              "F" ), new DefaultNonterminalSymbol ( "G" ) );//$NON-NLS-1$ //$NON-NLS-2$

      TerminalSymbolSet terminalSymbolSet = new DefaultTerminalSymbolSet (
          new DefaultTerminalSymbol ( "a" ), new DefaultTerminalSymbol ( "b" ),//$NON-NLS-1$ //$NON-NLS-2$
          new DefaultTerminalSymbol ( "c" ) ); //$NON-NLS-1$

      JFrame jFrame = new JFrame ( "ProductionPanelTest" ); //$NON-NLS-1$
      StyledProductionParserPanel styledProductionParserPanel = new StyledProductionParserPanel ();

      styledProductionParserPanel
          .setNonterminalSymbolSet ( nonterminalSymbolSet );
      styledProductionParserPanel.setTerminalSymbolSet ( terminalSymbolSet );

      styledProductionParserPanel
          .addParseableChangedListener ( new ParseableChangedListener < MultiProduction > ()
          {

            public void parseableChanged ( MultiProduction newProduction )
            {
              if ( newProduction != null )
              {
                System.out.println ( newProduction.toString () );
              }
            }
          } );
      jFrame.add ( styledProductionParserPanel );
      jFrame.setBounds ( 300, 300, 400, 300 );
      jFrame.setDefaultCloseOperation ( WindowConstants.DISPOSE_ON_CLOSE );
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
  }
}
