package de.unisiegen.gtitool.ui.redoundo;


import java.util.TreeSet;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultRegexAlphabet;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.regex.DefaultRegex;
import de.unisiegen.gtitool.ui.logic.RegexPanel;


/**
 * {@link AbstractUndoableEdit} for {@link Alphabet} changes in Regex
 */
public class RegexAlphabetRedoUndoItem extends AbstractUndoableEdit
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 5499266466163902412L;


  /**
   * The {@link Alphabet}.
   */
  private DefaultRegexAlphabet alphabet;


  /**
   * The {@link DefaultRegex}
   */
  private DefaultRegex regex;


  /**
   * The {@link RegexPanel}
   */
  private RegexPanel regexPanel;


  /**
   * The added symbols for the alphabet.
   */
  private TreeSet < Symbol > symbolsToAdd = new TreeSet < Symbol > ();


  /**
   * The removed symbols for the alphabet.
   */
  private TreeSet < Symbol > symbolsToRemove = new TreeSet < Symbol > ();


  /**
   * Creates a new {@link RegexAlphabetChangedItem}
   * 
   * @param regex The {@link DefaultRegex}
   * @param regexPanel The {@link RegexPanel}
   * @param newAlphabet The new {@link Alphabet}
   */
  public RegexAlphabetRedoUndoItem ( DefaultRegex regex, RegexPanel regexPanel,
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
   * {@inheritDoc}
   * 
   * @see UndoableEdit#canRedo()
   */
  @Override
  public boolean canRedo ()
  {
    return true;
  }


  /**
   * {@inheritDoc}
   * 
   * @see UndoableEdit#canUndo()
   */
  @Override
  public boolean canUndo ()
  {
    return true;
  }


  /**
   * {@inheritDoc}
   * 
   * @see UndoableEdit#isSignificant()
   */
  @Override
  public boolean isSignificant ()
  {
    return true;
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
   * @see UndoableEdit#redo()
   */
  @Override
  public void redo () throws CannotRedoException
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
   * @see UndoableEdit#undo()
   */
  @Override
  public void undo () throws CannotUndoException
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
