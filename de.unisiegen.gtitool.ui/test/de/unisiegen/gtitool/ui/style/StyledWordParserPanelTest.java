package de.unisiegen.gtitool.ui.style;


import javax.swing.JFrame;

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
@SuppressWarnings (
{ "all" } )
public class StyledWordParserPanelTest
{

  public static void main ( String [] arguments )
  {
    try
    {
      Alphabet alphabet = new DefaultAlphabet ( new DefaultSymbol ( "0" ),
          new DefaultSymbol ( "1" ), new DefaultSymbol ( "2" ) );
      JFrame jFrame = new JFrame ( "WordPanelTest" );
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
      jFrame.setDefaultCloseOperation ( jFrame.DISPOSE_ON_CLOSE );
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
