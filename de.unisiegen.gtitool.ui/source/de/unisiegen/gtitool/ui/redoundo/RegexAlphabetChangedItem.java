package de.unisiegen.gtitool.ui.redoundo;


import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultRegexAlphabet;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.regex.DefaultRegex;
import de.unisiegen.gtitool.ui.logic.RegexPanel;


/**
 * {@link RedoUndoItem} for Alphabet changed
 */
public class RegexAlphabetChangedItem extends RedoUndoItem
{

  /**
   * The {@link Alphabet}.
   */
  private DefaultRegexAlphabet alphabet;


  /**
   * The added symbols for the alphabet.
   */
  private TreeSet < Symbol > symbolsToAdd = new TreeSet < Symbol > ();


  /**
   * The removed symbols for the alphabet.
   */
  private TreeSet < Symbol > symbolsToRemove = new TreeSet < Symbol > ();


  /**
   * The {@link DefaultRegex}
   */
  private DefaultRegex regex;


  /**
   * The {@link RegexPanel}
   */
  private RegexPanel regexPanel;


  /**
   * Creates a new of {@link RegexAlphabetChangedItem}
   * 
   * @param regex The {@link DefaultRegex}
   * @param regexPanel The {@link RegexPanel}
   * @param newAlphabet The new {@link Alphabet}
   */
  public RegexAlphabetChangedItem ( DefaultRegex regex, RegexPanel regexPanel,
      Alphabet newAlphabet )
  {
    super ();
    this.regex = regex;
    this.alphabet = regex.getAlphabet ();
    this.regexPanel = regexPanel;
    performAlphabetCalculation ( this.alphabet, newAlphabet, this.symbolsToAdd,
        this.symbolsToRemove );
  }


  /**
   * Preforms the {@link Alphabet} calculation.
   * 
   * @param oldAlphabet The old {@link Alphabet}.
   * @param newAlphabet The new {@link Alphabet}.
   * @param symbolsAdd The {@link Symbol}s to add.
   * @param symbolsRemove The {@link Symbol}s to remove.
   */
  private final void performAlphabetCalculation ( Alphabet oldAlphabet,
      Alphabet newAlphabet, TreeSet < Symbol > symbolsAdd,
      TreeSet < Symbol > symbolsRemove )
  {

    for ( Symbol current : newAlphabet )
    {
      if ( !oldAlphabet.contains ( current ) )
      {
        symbolsAdd.add ( current );
      }
    }
    for ( Symbol current : oldAlphabet )
    {
      if ( !newAlphabet.contains ( current ) )
      {
        symbolsRemove.add ( current );
      }
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see RedoUndoItem#redo()
   */
  @Override
  public void redo ()
  {
    try
    {
      this.alphabet.add ( this.symbolsToAdd );
      this.alphabet.remove ( this.symbolsToRemove );
      this.regex.setAlphabet ( this.alphabet );
      this.regexPanel.initializeAlphabet ();
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see RedoUndoItem#undo()
   */
  @Override
  public void undo ()
  {
    try
    {
      this.alphabet.remove ( this.symbolsToAdd );
      this.alphabet.add ( this.symbolsToRemove );
      this.regex.setAlphabet ( this.alphabet );
      this.regexPanel.initializeAlphabet ();
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }

}
