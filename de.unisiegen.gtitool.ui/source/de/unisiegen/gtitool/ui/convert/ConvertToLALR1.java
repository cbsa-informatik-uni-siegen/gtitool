package de.unisiegen.gtitool.ui.convert;


import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.machines.AbstractStateMachine;
import de.unisiegen.gtitool.core.machines.dfa.LR1;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


/**
 * Converts an LR1 automaton into an LALR1 automaton
 */
public class ConvertToLALR1 extends ConvertToLR
{

  /**
   * Creates a converter out of the mainWindow and an LR1 automaton
   * 
   * @param mainWindow
   * @param lr1
   */
  public ConvertToLALR1 ( final MainWindowForm mainWindow, final LR1 lr1 )
  {
    super ( mainWindow, lr1.getGrammar (), lr1.getAlphabet () );
    this.source = lr1;
  }


  /**
   * The source parser
   */
  private LR1 source;


  /**
   * The resulting parser
   */
  private LR1 result;


  /**
   * {@inheritDoc}
   * 
   * @see de.unisiegen.gtitool.ui.convert.AbstractConvertGrammar#createMachine()
   */
  @Override
  protected void createMachine ()
  {
    try
    {
      this.result = this.source.toLALR1 ();

      createMachinePanel ( this.result );
    }
    catch ( StateException exc )
    {
      exc.printStackTrace ();
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
    }
  }


  /**
   * {@inheritDoc} s
   * 
   * @see de.unisiegen.gtitool.ui.convert.ConvertToLR#getMachine()
   */
  @Override
  protected AbstractStateMachine getMachine ()
  {
    return this.source;
  }

}
