package de.unisiegen.gtitool.core.machines.lr;


import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.grammars.cfg.LR0Grammar;
import de.unisiegen.gtitool.core.machines.AbstractMachine;
import de.unisiegen.gtitool.core.machines.dfa.LR0;


/**
 * TODO
 */
public class DefaultLR0Parser extends AbstractMachine implements LR0Parser
{

  /**
   * TODO
   * 
   * @param alphabet
   * @param pushDownAlphabet
   * @param usePushDownAlphabet
   * @param validationElements
   */
  public DefaultLR0Parser ( LR0Grammar grammar ) throws AlphabetException
  {
    super ( grammar.makeAutomataAlphabet (), new DefaultAlphabet (), true,
        ValidationElement.FINAL_STATE, ValidationElement.STATE_NAME,
        ValidationElement.SYMBOL_ONLY_ONE_TIME );

    this.grammar = grammar;

    this.lr0Automaton = new LR0 ( grammar );
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
    return MachineType.LR0;
  }


  public void nextSymbol ( Transition transition )
  {
    //super.nextSymbol ( transition );

    this.lr0Automaton.nextSymbol ( transition );
  }


  private LR0Grammar grammar;


  private LR0 lr0Automaton;
}
