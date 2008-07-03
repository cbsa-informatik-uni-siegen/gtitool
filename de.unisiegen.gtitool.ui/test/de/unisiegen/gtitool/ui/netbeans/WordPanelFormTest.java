package de.unisiegen.gtitool.ui.netbeans;


import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;


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
      JFrame jFrame = new JFrame ( "WordPanelFormTest" );

      Alphabet alphabet = new DefaultAlphabet ( new DefaultSymbol ( "0" ),
          new DefaultSymbol ( "1" ), new DefaultSymbol ( "2" ) );

      Alphabet pushDownAlphabet = new DefaultAlphabet (
          new DefaultSymbol ( "a" ), new DefaultSymbol ( "b" ) );

      WordPanelForm wordPanelForm = new WordPanelForm ();

      wordPanelForm.setAlphabet ( alphabet );
      wordPanelForm.setPushDownAlphabet ( pushDownAlphabet );

      wordPanelForm.styledWordParserPanel
          .addParseableChangedListener ( new ParseableChangedListener < Word > ()
          {

            public void parseableChanged ( Word newWord )
            {
              if ( newWord != null )
              {
                System.out.println ( newWord );
              }
            }
          } );

      jFrame.add ( wordPanelForm );
      jFrame.setBounds ( 300, 300, 400, 300 );
      jFrame.setDefaultCloseOperation ( jFrame.DISPOSE_ON_CLOSE );
      jFrame.setVisible ( true );
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
    }
  }
}
