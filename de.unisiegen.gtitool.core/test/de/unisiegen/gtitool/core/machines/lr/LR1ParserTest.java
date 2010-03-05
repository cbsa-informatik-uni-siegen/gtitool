package de.unisiegen.gtitool.core.machines.lr;


import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineAmbigiousActionException;
import de.unisiegen.gtitool.core.grammars.LR0Test;
import de.unisiegen.gtitool.core.grammars.cfg.LR1Grammar;


/**
 * Test the LR1 parser
 */
public class LR1ParserTest
{

  /**
   * main method
   * 
   * @param args
   */
  public static void main ( final String [] args )
  {
    LR1Grammar testGrammar = new LR1Grammar ( LR0Test.testGrammar () );

    try
    {
      LR1Parser parser = new DefaultLR1Parser ( testGrammar );

      Word word = new DefaultWord ();

      TerminalSymbolSet terminals = testGrammar.getTerminalSymbolSet ();

      word.add ( new DefaultSymbol ( terminals.get ( "id" ).toString () ) ); //$NON-NLS-1$
      word.add ( new DefaultSymbol ( terminals.get ( "*" ).toString () ) ); //$NON-NLS-1$
      word.add ( new DefaultSymbol ( terminals.get ( "id" ).toString () ) ); //$NON-NLS-1$

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
    catch ( MachineAmbigiousActionException exc )
    {
      exc.printStackTrace ();
    }
  }
}