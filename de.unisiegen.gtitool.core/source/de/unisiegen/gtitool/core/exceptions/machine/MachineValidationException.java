package de.unisiegen.gtitool.core.exceptions.machine;


import java.util.ArrayList;
import java.util.Iterator;

import de.unisiegen.gtitool.core.exceptions.CoreException;


/**
 * The <code>MachineValidationException</code> is thrown, if the validation of
 * a machine fails.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class MachineValidationException extends CoreException implements
    Iterable < MachineException >
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -5114547200702807944L;


  /**
   * The list of {@link MachineException}s.
   */
  private ArrayList < MachineException > machineExceptionList = new ArrayList < MachineException > ();


  /**
   * Allocates a new <code>MachineValidationException</code>.
   * 
   * @param pMachineExceptionList The list of {@link MachineException}s.
   */
  public MachineValidationException (
      ArrayList < MachineException > pMachineExceptionList )
  {
    if ( pMachineExceptionList == null )
    {
      throw new NullPointerException ( "machine exception list is null" ); //$NON-NLS-1$
    }
    if ( pMachineExceptionList.size () == 0 )
    {
      throw new IllegalArgumentException ( "machine exception list is empty" ); //$NON-NLS-1$
    }
    this.machineExceptionList = pMachineExceptionList;
  }


  /**
   * Returns the {@link MachineException} at the specified position in the list
   * of {@link MachineException}s.
   * 
   * @param pIndex The index of the {@link MachineException} to return.
   * @return The {@link MachineException} at the specified position in the list
   *         of {@link MachineException}s.
   */
  public final MachineException getMachineException ( int pIndex )
  {
    return this.machineExceptionList.get ( pIndex );
  }


  /**
   * Returns the {@link MachineException} list.
   * 
   * @return The {@link MachineException} list.
   */
  public final ArrayList < MachineException > getMachineExceptionList ()
  {
    return this.machineExceptionList;
  }


  /**
   * {@inheritDoc}
   * 
   * @see Iterable#iterator()
   */
  public Iterator < MachineException > iterator ()
  {
    return this.machineExceptionList.iterator ();
  }


  /**
   * Returns the number of {@link MachineException}s in the list of
   * {@link MachineException}s.
   * 
   * @return The number of {@link MachineException}s in the list of
   *         {@link MachineException}s.
   */
  public final int machineExceptionSize ()
  {
    return this.machineExceptionList.size ();
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
