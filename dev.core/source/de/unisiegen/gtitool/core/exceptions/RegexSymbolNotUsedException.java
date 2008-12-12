package de.unisiegen.gtitool.core.exceptions;


import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.i18n.Messages;


/**
 * TODO
 */
public class RegexSymbolNotUsedException extends RegexException
{

  /**
   * TODO
   */
  public RegexSymbolNotUsedException ( Symbol s )
  {
    super ();
    setPrettyMessage ( Messages
        .getPrettyString ( "RegexException.NotUsedSymbolMessage" ) );
    setPrettyDescription ( Messages.getPrettyString (
        "RegexException.NotUsedSymbolDescription", s.toPrettyString () ) );
  }


  /**
   * TODO
   * 
   * @return
   * @see de.unisiegen.gtitool.core.exceptions.CoreException#getType()
   */
  @Override
  public ErrorType getType ()
  {
    return ErrorType.WARNING;
  }

}
