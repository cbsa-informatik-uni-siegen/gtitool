package de.unisiegen.gtitool.core.machines.lr;


import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineAmbigiousActionException;
import de.unisiegen.gtitool.core.grammars.LR0Test;
import de.unisiegen.gtitool.core.grammars.cfg.LR0Grammar;


/**
 * TODO
 */
public class LR0ParserTest
{

  public static void main ( String [] args )
  {
    LR0Grammar testGrammar = LR0Test.testGrammar ();

    try
    {
      LR0Parser parser = new DefaultLR0Parser ( testGrammar );

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
    catch ( MachineAmbigiousActionException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( AlphabetException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
    }
  }
}
