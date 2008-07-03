package de.unisiegen.gtitool.ui.preferences;


import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultAlphabet;
import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultNonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.DefaultSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbol;
import de.unisiegen.gtitool.core.entities.DefaultTerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.preferences.item.NonterminalSymbolSetItem;
import de.unisiegen.gtitool.core.preferences.item.TerminalSymbolSetItem;


/**
 * The test class of the {@link PreferenceManager}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings (
{ "all" } )
public class PreferenceManagerTest
{

  public static void main ( String [] arguments )
  {
    Alphabet alphabet = null;
    Alphabet pushDownAlphabet = null;
    NonterminalSymbolSet nonterminalSymbolSet = null;
    TerminalSymbolSet terminalSymbolSet = null;

    try
    {
      alphabet = new DefaultAlphabet (
          new DefaultSymbol ( "2" ), new DefaultSymbol ( "3" ) ); //$NON-NLS-1$ //$NON-NLS-2$

      pushDownAlphabet = new DefaultAlphabet (
          new DefaultSymbol ( "4" ), new DefaultSymbol ( "5" ) ); //$NON-NLS-1$ //$NON-NLS-2$

      nonterminalSymbolSet = new DefaultNonterminalSymbolSet (
          new DefaultNonterminalSymbol ( "A" ), new DefaultNonterminalSymbol ( //$NON-NLS-1$
              "B" ) ); //$NON-NLS-1$

      terminalSymbolSet = new DefaultTerminalSymbolSet (
          new DefaultTerminalSymbol ( "e" ), new DefaultTerminalSymbol ( "f" ) ); //$NON-NLS-1$ //$NON-NLS-2$
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( NonterminalSymbolSetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( TerminalSymbolSetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }

    PreferenceManager preferences = PreferenceManager.getInstance ();

    System.out.println ( preferences.getNonterminalSymbolSetItem () );
    System.out.println ( preferences.getTerminalSymbolSetItem () );

    preferences.setNonterminalSymbolSetItem ( new NonterminalSymbolSetItem (
        nonterminalSymbolSet, nonterminalSymbolSet ) );
    preferences.setTerminalSymbolSetItem ( new TerminalSymbolSetItem (
        terminalSymbolSet, terminalSymbolSet ) );

    System.out.println ( preferences.getNonterminalSymbolSetItem () );
    System.out.println ( preferences.getTerminalSymbolSetItem () );
  }
}
