package de.unisiegen.gtitool.core.machines.dfa;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.exceptions.machine.MachineException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineValidationException;
import de.unisiegen.gtitool.core.machines.Machine;


/**
 * The class for <code>DFA</code> machines.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class DFA extends Machine
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 6284801477317314839L;


  /**
   * Allocates a new <code>DFA</code>.
   * 
   * @param pAlphabet The {@link Alphabet} of this <code>DFA</code>.
   */
  public DFA ( Alphabet pAlphabet )
  {
    super ( pAlphabet );
  }


  /**
   * {@inheritDoc}
   * 
   * @see Machine#validate()
   */
  @Override
  public final void validate () throws MachineValidationException
  {
    ArrayList < MachineException > machineExceptionList = new ArrayList < MachineException > ();

    // Start state
    machineExceptionList.addAll ( checkNoStartState () );
    machineExceptionList.addAll ( checkMoreThanOneStartState () );

    // State name
    machineExceptionList.addAll ( checkStateName () );

    // All symbols
    machineExceptionList.addAll ( checkAllSymbols () );

    // Epsilon transition
    machineExceptionList.addAll ( checkEpsilonTransition () );

    // Symbol only one time
    machineExceptionList.addAll ( checkSymbolOnlyOneTime () );

    // Warning: Final state
    machineExceptionList.addAll ( checkFinalState () );

    // Throw the exception if a warning or a error has occurred.
    if ( machineExceptionList.size () > 0 )
    {
      throw new MachineValidationException ( machineExceptionList );
    }
  }
}
