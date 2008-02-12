package de.unisiegen.gtitool.ui.netbeans;


import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.listener.NonterminalSymbolSetChangedListener;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbol.NonterminalSymbolException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbol.TerminalSymbolException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;


/**
 * The test class of the {@link TerminalPanelForm}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings (
{ "all" } )
public class TerminalPanelFormTest
{

  public static void main ( String [] arguments )
  {
    try
    {
      JFrame jFrame = new JFrame ( "TerminalPanelFormTest" );

      NonterminalSymbolSet nonterminalSymbolSet = new DefaultNonterminalSymbolSet (
          new DefaultNonterminalSymbol ( "E" ), new DefaultNonterminalSymbol (
              "F" ), new DefaultNonterminalSymbol ( "G" ) );

      TerminalSymbolSet terminalSymbolSet = new DefaultTerminalSymbolSet (
          new DefaultTerminalSymbol ( "a" ), new DefaultTerminalSymbol ( "b" ),
          new DefaultTerminalSymbol ( "c" ) );

      TerminalPanelForm terminalPanelForm = new TerminalPanelForm ();

      terminalPanelForm.setNonterminalSymbolSet ( nonterminalSymbolSet );
      terminalPanelForm.setTerminalSymbolSet ( terminalSymbolSet );

      terminalPanelForm.styledNonterminalSymbolSetParserPanel
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

      jFrame.add ( terminalPanelForm );
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
