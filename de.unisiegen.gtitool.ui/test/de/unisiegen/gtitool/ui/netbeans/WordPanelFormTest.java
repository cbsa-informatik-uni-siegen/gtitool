package de.unisiegen.gtitool.ui.netbeans;


import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;


/**
 * The test class of the word panel.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings (
{ "all" } )
public class WordPanelFormTest
{

  public static void main ( String [] args )
  {
    try
    {
      Alphabet alphabet = new Alphabet ( new Symbol ( "0" ),
          new Symbol ( "1" ), new Symbol ( "2" ) );
      JFrame jFrame = new JFrame ( "WordPanelFormTest" );
      WordPanelForm wordPanelForm = new WordPanelForm ();
      wordPanelForm.setAlphabet ( alphabet );
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
