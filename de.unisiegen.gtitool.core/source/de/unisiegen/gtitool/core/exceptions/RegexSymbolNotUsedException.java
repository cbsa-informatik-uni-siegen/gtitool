package de.unisiegen.gtitool.core.exceptions;


import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.i18n.Messages;


/**
 * A Warning that not all symbols of the alphabet are used
 */
public class RegexSymbolNotUsedException extends RegexException
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -8643561398436665032L;


  /**
   * Creates a new {@link RegexSymbolNotUsedException}
   * 
   * @param s The Symbol that is not used
   */
  public RegexSymbolNotUsedException ( Symbol s )
  {
    super ();
    setPrettyMessage ( Messages
        .getPrettyString ( "RegexException.NotUsedSymbolMessage" ) ); //$NON-NLS-1$
    setPrettyDescription ( Messages.getPrettyString (
        "RegexException.NotUsedSymbolDescription", s.toPrettyString () ) ); //$NON-NLS-1$
  }


  /**
   * {@inheritDoc}
   * 
   * @see CoreException#getType()
   */
  @Override
  public ErrorType getType ()
  {
    return ErrorType.WARNING;
  }

}
