package de.unisiegen.gtitool.ui.style;


import javax.swing.JFrame;
import javax.swing.WindowConstants;

import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.exceptions.terminalsymbol.TerminalSymbolException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;


/**
 * The test class of the {@link StyledNonterminalSymbolSetParserPanel}.
 * 
 * @author Christian Fehler
 * @version $Id: StyledAlphabetParserPanelTest.java 547 2008-02-10 22:24:57Z
 *          fehler $
 */
public class StyledNonterminalSymbolSetParserPanelTest
{

  /**
   * The main methos.
   * 
   * @param arguments The arguments.
   */
  public static void main ( String [] arguments )
  {
    try
    {
      TerminalSymbolSet terminalSymbolSet = new DefaultTerminalSymbolSet (
          new DefaultTerminalSymbol ( "a" ), new DefaultTerminalSymbol ( "b" ), //$NON-NLS-1$//$NON-NLS-2$
          new DefaultTerminalSymbol ( "c" ) ); //$NON-NLS-1$

      JFrame jFrame = new JFrame ( "NonterminalSymbolSetPanelTest" ); //$NON-NLS-1$
      StyledNonterminalSymbolSetParserPanel styledNonterminalSymbolSetParserPanel = new StyledNonterminalSymbolSetParserPanel ();
      styledNonterminalSymbolSetParserPanel
          .setTerminalSymbolSet ( terminalSymbolSet );
      styledNonterminalSymbolSetParserPanel
          .addParseableChangedListener ( new ParseableChangedListener < NonterminalSymbolSet > ()
          {

            public void parseableChanged (
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
      jFrame.setDefaultCloseOperation ( WindowConstants.DISPOSE_ON_CLOSE );
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
