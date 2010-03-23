package de.unisiegen.gtitool.core.machines.lr;


import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultWord;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineAmbigiousActionException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.LR0Test;
import de.unisiegen.gtitool.core.grammars.cfg.LR0Grammar;


/**
 * Test the LR0 Parser
 */
public class LR0ParserTest
{

  /**
   * main method
   * 
   * @param args
   * @throws NonterminalSymbolSetException
   * @throws TerminalSymbolSetException
   * @throws MachineAmbigiousActionException
   * @throws AlphabetException
   */
  public static void main ( final String [] args )
      throws TerminalSymbolSetException, NonterminalSymbolSetException,
      MachineAmbigiousActionException, AlphabetException
  {
    LR0Grammar testGrammar = LR0Test.testGrammar ();

    LR0Parser parser = new DefaultLR0Parser ( testGrammar );

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
}
