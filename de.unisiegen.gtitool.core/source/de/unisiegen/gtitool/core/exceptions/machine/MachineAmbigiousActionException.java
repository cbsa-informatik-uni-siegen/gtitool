package de.unisiegen.gtitool.core.exceptions.machine;


/**
 * TODO
 */
public final class MachineAmbigiousActionException extends MachineException
{

  /**
   * TODO
   */
  private static final long serialVersionUID = 1L;

  /**
   * The error type
   * 
   * @return the error type
   * @see de.unisiegen.gtitool.core.exceptions.CoreException#getType()
   */
  @Override
  public ErrorType getType ()
  {
    return ErrorType.ERROR;
  }

}
