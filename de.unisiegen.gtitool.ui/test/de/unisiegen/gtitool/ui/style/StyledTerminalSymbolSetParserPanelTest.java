package de.unisiegen.gtitool.ui.style;


import javax.swing.JFrame;
import javax.swing.WindowConstants;

import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbol.NonterminalSymbolException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;


/**
 * The test class of the {@link StyledTerminalSymbolSetParserPanel}.
 * 
 * @author Christian Fehler
 * @version $Id: StyledAlphabetParserPanelTest.java 547 2008-02-10 22:24:57Z
 *          fehler $
 */
public class StyledTerminalSymbolSetParserPanelTest
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
              "F" ), new DefaultNonterminalSymbol ( "G" ) ); //$NON-NLS-1$//$NON-NLS-2$

      JFrame jFrame = new JFrame ( "TerminalSymbolSetPanelTest" ); //$NON-NLS-1$
      StyledTerminalSymbolSetParserPanel styledTerminalSymbolSetParserPanel = new StyledTerminalSymbolSetParserPanel ();
      styledTerminalSymbolSetParserPanel
          .setNonterminalSymbolSet ( nonterminalSymbolSet );
      styledTerminalSymbolSetParserPanel
          .addParseableChangedListener ( new ParseableChangedListener < TerminalSymbolSet > ()
          {

            public void parseableChanged (
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
      jFrame.setDefaultCloseOperation ( WindowConstants.DISPOSE_ON_CLOSE );
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
