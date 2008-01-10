package de.unisiegen.gtitool.ui.style;


import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.Stack;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.ui.style.listener.StackChangedListener;


/**
 * The test class of the {@link StyledStackParserPanel}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings (
{ "all" } )
public class StyledStackParserPanelTest
{

  public static void main ( String [] arguments )
  {
    try
    {
      Alphabet pushDownalphabet = new DefaultAlphabet (
          new DefaultSymbol ( "0" ), new DefaultSymbol ( "1" ),
          new DefaultSymbol ( "2" ) );
      JFrame jFrame = new JFrame ( "WordPanelFormTest" );
      final StyledStackParserPanel styledStackParserPanel = new StyledStackParserPanel ();
      styledStackParserPanel.setPushDownAlphabet ( pushDownalphabet );
      styledStackParserPanel
          .addStackChangedListener ( new StackChangedListener ()
          {

            public void stackChanged ( Stack newStack )
            {
              if ( newStack != null )
              {
                styledStackParserPanel.setHighlightedParseableEntity ( newStack
                    .get ( 0 ) );
              }
            }
          } );
      jFrame.add ( styledStackParserPanel );
      jFrame.setBounds ( 300, 300, 400, 300 );
      jFrame.setVisible ( true );
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
    }
    catch ( SymbolException exc )
    {
      exc.printStackTrace ();
    }
  }
}
