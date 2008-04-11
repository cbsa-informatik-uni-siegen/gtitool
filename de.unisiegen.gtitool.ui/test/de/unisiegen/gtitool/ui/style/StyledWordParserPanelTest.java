package de.unisiegen.gtitool.ui.style;


import javax.swing.JFrame;
import javax.swing.WindowConstants;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.entities.listener.WordChangedListener;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;


/**
 * The test class of the {@link StyledWordParserPanel}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class StyledWordParserPanelTest
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
      Alphabet alphabet = new DefaultAlphabet ( new DefaultSymbol ( "0" ), //$NON-NLS-1$
          new DefaultSymbol ( "1" ), new DefaultSymbol ( "2" ) ); //$NON-NLS-1$ //$NON-NLS-2$

      JFrame jFrame = new JFrame ( "WordPanelTest" ); //$NON-NLS-1$
      StyledWordParserPanel styledWordParserPanel = new StyledWordParserPanel ();
      styledWordParserPanel.setAlphabet ( alphabet );
      styledWordParserPanel.addWordChangedListener ( new WordChangedListener ()
      {

        public void wordChanged ( Word newWord )
        {
          if ( newWord != null )
          {
            System.out.println ( newWord );
          }
        }
      } );
      jFrame.add ( styledWordParserPanel );
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
