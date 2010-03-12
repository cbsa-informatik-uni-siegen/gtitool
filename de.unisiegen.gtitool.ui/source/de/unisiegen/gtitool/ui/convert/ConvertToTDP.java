package de.unisiegen.gtitool.ui.convert;


import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarInvalidNonterminalException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.grammars.cfg.CFG;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.machines.pda.DefaultTDP;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


/**
 * Convert a {@link CFG} to a {@link DefaultTDP}
 * 
 * @author Christian Uhrhan
 */
public class ConvertToTDP extends AbstractConvertGrammarStatelessMachine
{

  /**
   * Allocate a new {@link ConvertToTDP}.
   * 
   * @param mainWindowForm The {@link MainWindowForm}.
   * @param cfg The {@link CFG}.
   * @throws AlphabetException
   */
  public ConvertToTDP ( MainWindowForm mainWindowForm, CFG cfg )
      throws AlphabetException
  {
    super ( mainWindowForm, cfg );

    // if the end marker is not part of our grammar
    if ( !cfg.getTerminalSymbolSet ().contains (
        DefaultTerminalSymbol.EndMarker ) )
      try
      {
        // add it
        cfg.getTerminalSymbolSet ().add ( DefaultTerminalSymbol.EndMarker );
      }
      catch ( TerminalSymbolSetException exc )
      {
        exc.printStackTrace ();
        System.exit ( 1 );
      }
  }


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.convert.AbstractConvertGrammarStateMachine#createMachine()
   */
  @Override
  protected void createMachine ()
  {
    Machine machine;
    try
    {
      machine = new DefaultTDP ( ( CFG ) getGrammar () );
      createMachinePanel ( machine );
    }
    catch ( GrammarInvalidNonterminalException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( TerminalSymbolSetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }
}
