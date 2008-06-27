package de.unisiegen.gtitool.core.exceptions.machine;


import java.util.ArrayList;
import java.util.Iterator;

import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The {@link MachineValidationException} is thrown, if the validation of a
 * machine fails.
 * 
 * @author Christian Fehler
 * @version $Id: MachineValidationException.java 532 2008-02-04 23:54:55Z fehler
 *          $
 */
public final class MachineValidationException extends CoreException implements
    Iterable < MachineException >
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 3601157561422760883L;


  /**
   * The list of {@link MachineException}s.
   */
  private ArrayList < MachineException > machineExceptionList = new ArrayList < MachineException > ();


  /**
   * Allocates a new {@link MachineValidationException}.
   * 
   * @param machineExceptionList The list of {@link MachineException}s.
   */
  public MachineValidationException (
      ArrayList < MachineException > machineExceptionList )
  {
    if ( machineExceptionList == null )
    {
      throw new NullPointerException ( "machine exception list is null" ); //$NON-NLS-1$
    }
    if ( machineExceptionList.size () == 0 )
    {
      throw new IllegalArgumentException ( "machine exception list is empty" ); //$NON-NLS-1$
    }
    this.machineExceptionList = machineExceptionList;
  }


  /**
   * Returns the {@link MachineException} list.
   * 
   * @return The {@link MachineException} list.
   */
  public final ArrayList < MachineException > getMachineException ()
  {
    return this.machineExceptionList;
  }


  /**
   * Returns the {@link MachineException} at the specified position in the list
   * of {@link MachineException}s.
   * 
   * @param index The index of the {@link MachineException} to return.
   * @return The {@link MachineException} at the specified position in the list
   *         of {@link MachineException}s.
   */
  public final MachineException getMachineException ( int index )
  {
    return this.machineExceptionList.get ( index );
  }


  /**
   * {@inheritDoc}
   * 
   * @see CoreException#getType()
   */
  @Override
  public final ErrorType getType ()
  {
    return ErrorType.COLLECTION;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Iterable#iterator()
   */
  public final Iterator < MachineException > iterator ()
  {
    return this.machineExceptionList.iterator ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see Throwable#toString()
   */
  @Override
  public final String toString ()
  {
    String lineBreak = System.getProperty ( "line.separator" ); //$NON-NLS-1$
    StringBuilder result = new StringBuilder ();
    for ( int i = 0 ; i < this.machineExceptionList.size () ; i++ )
    {
      if ( i > 0 )
      {
        result.append ( lineBreak );
      }
      result.append ( this.machineExceptionList.get ( i ).getClass ()
          .getSimpleName ()
          + lineBreak );
      result.append ( this.machineExceptionList.get ( i ).toString () );
    }
    return result.toString ();
  }
}
