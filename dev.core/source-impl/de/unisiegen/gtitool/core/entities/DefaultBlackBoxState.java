package de.unisiegen.gtitool.core.entities;


import de.unisiegen.gtitool.core.exceptions.state.StateException;


/**
 * {@link DefaultState} without a name
 */
public class DefaultBlackBoxState extends DefaultState
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -7052595983183170428L;


  /**
   * Creates a new of {@link DefaultBlackBoxState}
   * 
   * @throws StateException When State creator has a problem
   */
  public DefaultBlackBoxState () throws StateException
  {
    super ( "" ); //$NON-NLS-1$
  }
}
