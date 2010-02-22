package de.unisiegen.gtitool.core.machines.dfa;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.ProductionWordMember;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.machines.AbstractStateMachine;


/**
 * TODO
 */
public class AbstractLR extends AbstractStateMachine implements DFA
{

  /**
   * TODO
   */
  private static final long serialVersionUID = 1L;


  /**
   * TODO
   * 
   * @param alphabet
   */
  protected AbstractLR ( final Alphabet alphabet )
  {
    super ( alphabet, new DefaultAlphabet (), false,
        ValidationElement.FINAL_STATE, ValidationElement.STATE_NAME,
        ValidationElement.SYMBOL_ONLY_ONE_TIME );
  }


  /**
   * Read the next production word member
   * 
   * @param symbol
   */
  public void nextSymbol ( final ProductionWordMember symbol )
  {
    this.nextSymbol ( this.getMatchingTransition ( this.getCurrentState (),
        new DefaultSymbol ( symbol.toString () ) ) );
  }


  /**
   * Gets the current matching transition for a given state and symbol
   * 
   * @param fromState - The state to go from
   * @param symbol - The symbol to read
   * @return The matching transition or null
   */
  private Transition getMatchingTransition ( final State fromState,
      final Symbol symbol )
  {
    Transition ret = null;

    for ( Transition transition : this.getTransition () )
      if ( transition.getStateBegin ().equals ( fromState )
          && transition.getSymbol ().contains ( symbol ) )
      {
        if ( ret != null )
        {
          System.err.println ( "Multiple matching transitions!" ); //$NON-NLS-1$
          System.exit ( 1 );
        }
        ret = transition;
      }

    return ret;
  }


  /**
   * Returns the machine's type
   * 
   * @return The machine's type
   * @see de.unisiegen.gtitool.core.machines.AbstractStateMachine#getMachineType()
   */
  @Override
  public MachineType getMachineType ()
  {
    return MachineType.DFA;
  }
}
