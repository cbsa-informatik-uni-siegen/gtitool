package de.unisiegen.gtitool.ui.convert;


import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.machines.AbstractStateMachine;
import de.unisiegen.gtitool.core.machines.dfa.LR1;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;


/**
 * TODO
 */
public class ConvertToLALR1 extends ConvertToLR
{

  public ConvertToLALR1 ( final MainWindowForm mainWindow, final LR1 lr1 )
  {
    super ( mainWindow, lr1.getGrammar (), lr1.getAlphabet () );
    this.source = lr1;
  }


  private LR1 source;


  private LR1 result;


  /**
   * TODO
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
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.ui.convert.ConvertToLR#getMachine()
   */
  @Override
  protected AbstractStateMachine getMachine ()
  {
    return this.source;
  }

}
