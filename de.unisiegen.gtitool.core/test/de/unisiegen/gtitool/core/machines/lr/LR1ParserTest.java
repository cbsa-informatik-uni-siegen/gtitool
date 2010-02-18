package de.unisiegen.gtitool.core.machines.lr;


import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.grammars.LR0Test;
import de.unisiegen.gtitool.core.grammars.cfg.LR1Grammar;


/**
 * TODO
 */
public class LR1ParserTest
{

  public static void main ( String [] args )
  {
    LR1Grammar testGrammar = new LR1Grammar ( LR0Test.testGrammar () );

    try
    {
      LR1Parser parser = new DefaultLR1Parser ( testGrammar );

      Word word = new DefaultWord ();

      TerminalSymbolSet terminals = testGrammar.getTerminalSymbolSet ();

      word.add ( new DefaultSymbol ( terminals.get ( "id" ).toString () ) );
      word.add ( new DefaultSymbol ( terminals.get ( "*" ).toString () ) );
      word.add ( new DefaultSymbol ( terminals.get ( "id" ).toString () ) );

      parser.start ( word );

      while ( !parser.isWordAccepted () )
      {
        parser.autoTransit ();
      }

    }
    catch ( AlphabetException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
    }
  }
}
