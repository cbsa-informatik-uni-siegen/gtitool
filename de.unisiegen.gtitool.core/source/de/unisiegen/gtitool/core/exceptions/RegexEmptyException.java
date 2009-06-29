package de.unisiegen.gtitool.core.exceptions;


import de.unisiegen.gtitool.core.i18n.Messages;


/**
 * A Warning that not all symbols of the alphabet are used
 */
public class RegexEmptyException extends RegexException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -8643561398436665032L;


  /**
   * Creates a new {@link RegexEmptyException}
   */
  public RegexEmptyException ()
  {
    super ();
    setPrettyMessage ( Messages
        .getPrettyString ( "RegexException.RegexEmptyMessage" ) ); //$NON-NLS-1$
    setPrettyDescription ( Messages
        .getPrettyString ( "RegexException.RegexEmptyDescription" ) ); //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see CoreException#getType()
   */
  @Override
  public ErrorType getType ()
  {
    return ErrorType.ERROR;
  }

}
