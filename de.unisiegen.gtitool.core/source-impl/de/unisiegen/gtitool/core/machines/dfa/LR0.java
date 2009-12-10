package de.unisiegen.gtitool.core.machines.dfa;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.machines.AbstractMachine;
import de.unisiegen.gtitool.core.machines.Machine.MachineType;


/**
 * TODO
 */
public class LR0 extends AbstractMachine implements DFA
{

  public LR0 ( Alphabet alphabet )
  {
    super ( alphabet, new DefaultAlphabet(), false,
        ValidationElement.ALL_SYMBOLS, ValidationElement.FINAL_STATE,
        ValidationElement.STATE_NAME, ValidationElement.SYMBOL_ONLY_ONE_TIME );
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.machines.AbstractMachine#getMachineType()
   */
  @Override
  public MachineType getMachineType ()
  {
    return MachineType.DFA;
  }
}
