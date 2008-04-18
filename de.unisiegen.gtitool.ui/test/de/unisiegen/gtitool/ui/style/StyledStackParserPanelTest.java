package de.unisiegen.gtitool.ui.style;


import javax.swing.JFrame;
import javax.swing.WindowConstants;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.Stack;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;


/**
 * The test class of the {@link StyledStackParserPanel}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class StyledStackParserPanelTest
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
      Alphabet pushDownalphabet = new DefaultAlphabet (
          new DefaultSymbol ( "0" ), new DefaultSymbol ( "1" ), //$NON-NLS-1$ //$NON-NLS-2$
          new DefaultSymbol ( "2" ) ); //$NON-NLS-1$
      JFrame jFrame = new JFrame ( "StackPanelTest" ); //$NON-NLS-1$
      StyledStackParserPanel styledStackParserPanel = new StyledStackParserPanel ();
      styledStackParserPanel.setPushDownAlphabet ( pushDownalphabet );
      styledStackParserPanel
          .addParseableChangedListener ( new ParseableChangedListener < Stack > ()
          {

            public void parseableChanged ( Stack newStack )
            {
              if ( newStack != null )
              {
                System.out.println ( newStack );
              }
            }
          } );
      jFrame.add ( styledStackParserPanel );
      jFrame.setBounds ( 300, 300, 400, 300 );
      jFrame.setDefaultCloseOperation ( WindowConstants.DISPOSE_ON_CLOSE );
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
