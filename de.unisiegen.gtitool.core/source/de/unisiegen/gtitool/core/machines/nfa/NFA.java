package de.unisiegen.gtitool.core.machines.nfa;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.exceptions.machine.MachineException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineValidationException;
import de.unisiegen.gtitool.core.machines.Machine;


/**
 * The class for <code>NFA</code> machines.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class NFA extends Machine
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 6284801477317314839L;


  /**
   * Allocates a new <code>NFA</code>.
   * 
   * @param pAlphabet The {@link Alphabet} of this <code>NFA</code>.
   */
  public NFA ( Alphabet pAlphabet )
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

    // Epsilon transition
    machineExceptionList.addAll ( checkEpsilonTransition () );

    // Warning: Final state
    machineExceptionList.addAll ( checkFinalState () );

    // Throw the exception if a warning or a error has occurred.
    if ( machineExceptionList.size () > 0 )
    {
      throw new MachineValidationException ( machineExceptionList );
    }
  }
}
