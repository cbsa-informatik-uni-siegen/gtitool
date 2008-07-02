package de.unisiegen.gtitool.ui.style;


import java.util.ArrayList;

import de.unisiegen.gtitool.core.entities.Entity;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.parser.exceptions.ParserException;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.core.parser.startnonterminalsymbol.StartNonterminalSymbolParseable;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.style.parser.StyledParserPanel;


/**
 * The styled start {@link NonterminalSymbol} panel class.
 * 
 * @author Christian Fehler
 * @version $Id: StyledNonterminalSymbolParserPanel.java 532 2008-02-04
 *          23:54:55Z fehler $
 */
public final class StyledStartNonterminalSymbolParserPanel extends
    StyledParserPanel < NonterminalSymbol >
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 7263996318450114482L;


  /**
   * The parsed {@link NonterminalSymbol} must be in this
   * {@link NonterminalSymbolSet}.
   */
  private NonterminalSymbolSet nonterminalSymbolSet = null;


  /**
   * Allocates a new {@link StyledStartNonterminalSymbolParserPanel}.
   */
  public StyledStartNonterminalSymbolParserPanel ()
  {
    super ( new StartNonterminalSymbolParseable () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see StyledParserPanel#checkParsedObject(Entity)
   */
  @Override
  protected final NonterminalSymbol checkParsedObject (
      NonterminalSymbol nonterminalSymbol )
  {
    ArrayList < ScannerException > exceptionList = new ArrayList < ScannerException > ();

    if ( ( this.nonterminalSymbolSet != null ) && ( nonterminalSymbol != null ) )
    {
      if ( !this.nonterminalSymbolSet.contains ( nonterminalSymbol ) )
      {
        exceptionList.add ( new ParserException ( nonterminalSymbol
            .getParserOffset ().getStart (), nonterminalSymbol
            .getParserOffset ().getEnd (), Messages.getString (
            "TerminalPanel.StartSymbolNoNonterminalSymbol", //$NON-NLS-1$
            nonterminalSymbol.getName (), this.nonterminalSymbolSet ) ) );
      }
    }

    if ( exceptionList.size () > 0 )
    {
      setException ( exceptionList );
      return null;
    }

    return nonterminalSymbol;
  }


  /**
   * Sets the {@link NonterminalSymbolSet}. The parsed {@link NonterminalSymbol}
   * must be in this {@link NonterminalSymbolSet}.
   * 
   * @param nonterminalSymbolSet The {@link NonterminalSymbolSet} to set.
   */
  public final void setNonterminalSymbolSet (
      NonterminalSymbolSet nonterminalSymbolSet )
  {
    this.nonterminalSymbolSet = nonterminalSymbolSet;
  }
}
