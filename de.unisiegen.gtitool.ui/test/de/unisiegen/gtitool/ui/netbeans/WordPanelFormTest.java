package de.unisiegen.gtitool.ui.netbeans;


import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.ui.style.listener.WordChangedListener;


/**
 * The test class of the {@link WordPanelForm}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings (
{ "all" } )
public class WordPanelFormTest
{

  public static void main ( String [] arguments )
  {
    try
    {
      Alphabet alphabet = new DefaultAlphabet ( new DefaultSymbol ( "0" ),
          new DefaultSymbol ( "1" ), new DefaultSymbol ( "2" ) );
      JFrame jFrame = new JFrame ( "WordPanelFormTest" );
      final WordPanelForm wordPanelForm = new WordPanelForm ();
      wordPanelForm.setAlphabet ( alphabet );
      wordPanelForm.styledWordParserPanel
          .addWordChangedListener ( new WordChangedListener ()
          {

            public void wordChanged ( Word newWord )
            {
              if ( newWord != null )
              {
                wordPanelForm.styledWordParserPanel
                    .setHighlightedParseableEntity ( newWord.get ( 0 ) );
              }
            }
          } );
      jFrame.add ( wordPanelForm );
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
