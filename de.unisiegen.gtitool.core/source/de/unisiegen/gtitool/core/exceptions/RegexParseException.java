package de.unisiegen.gtitool.core.exceptions;

import de.unisiegen.gtitool.core.i18n.Messages;


/**
 * TODO
 *
 */
public class RegexParseException extends RegexException
{

  
  /**
   * TODO
   *
   */
  public RegexParseException ()
  {
    super();
    setPrettyMessage ( Messages
        .getPrettyString ( "RegexException.ParseErrorMessage" ) ); //$NON-NLS-1$
    setPrettyDescription ( Messages.getPrettyString (
        "RegexException.ParseErrorDescription" ) ); //$NON-NLS-1$
  }
  
  /**
   * TODO
   */
  private static final long serialVersionUID = -7383851862526202117L;

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
