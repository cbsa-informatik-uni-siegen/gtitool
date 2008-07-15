package de.unisiegen.gtitool.ui.redoundo;


import java.util.TreeSet;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.machines.Machine;


/**
 * Representation of {@link RedoUndoItem} for edit {@link Alphabet} action.
 * 
 * @author Benjamin Mies
 * @version $Id: StateAddedItem.java 960 2008-06-03 23:32:54Z fehler $
 */
public final class MachineAlphabetChangedItem extends RedoUndoItem
{

  /**
   * The {@link Alphabet}.
   */
  private Alphabet alphabet;


  /**
   * The push down {@link Alphabet}.
   */
  private Alphabet pushDownAlphabet;


  /**
   * The added symbols for the alphabet.
   */
  private TreeSet < Symbol > symbolsToAdd = new TreeSet < Symbol > ();


  /**
   * The removed symbols for the push down alphabet.
   */
  private TreeSet < Symbol > symbolsToRemovePushDown = new TreeSet < Symbol > ();


  /**
   * The added symbols for the push down alphabet.
   */
  private TreeSet < Symbol > symbolsToAddPushDown = new TreeSet < Symbol > ();


  /**
   * The removed symbols for the alphabet.
   */
  private TreeSet < Symbol > symbolsToRemove = new TreeSet < Symbol > ();


  /**
   * The old use push down alphabet value.
   */
  private boolean oldUsePushDown;


  /**
   * The new use push down alphabet value.
   */
  private boolean newUsePushDown;


  /**
   * The {@link Machine}.
   */
  private Machine machine;


  /**
   * Allocates a new {@link MachineAlphabetChangedItem}.
   * 
   * @param machine The {@link Machine}.
   * @param newAlphabet The new {@link Alphabet}.
   * @param newPushDownAlphabet The new push down {@link Alphabet}.
   * @param oldUsePushDown The old use push down alphabet value.
   * @param newUsePushDown The new use push down alphabet value.
   */
  public MachineAlphabetChangedItem ( Machine machine, Alphabet newAlphabet,
      Alphabet newPushDownAlphabet, boolean oldUsePushDown,
      boolean newUsePushDown )
  {
    super ();
    this.machine = machine;
    this.alphabet = machine.getAlphabet ();
    this.pushDownAlphabet = machine.getPushDownAlphabet ();
    performAlphabetCalculation ( this.alphabet, newAlphabet, this.symbolsToAdd,
        this.symbolsToRemove );
    performAlphabetCalculation ( this.pushDownAlphabet, newPushDownAlphabet,
        this.symbolsToAddPushDown, this.symbolsToRemovePushDown );
    this.oldUsePushDown = oldUsePushDown;
    this.newUsePushDown = newUsePushDown;
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
  public final void redo ()
  {
    try
    {
      this.alphabet.add ( this.symbolsToAdd );
      this.alphabet.remove ( this.symbolsToRemove );
      this.pushDownAlphabet.add ( this.symbolsToAddPushDown );
      this.pushDownAlphabet.remove ( this.symbolsToRemovePushDown );
      this.machine.setUsePushDownAlphabet ( this.newUsePushDown );
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
      this.pushDownAlphabet.remove ( this.symbolsToAddPushDown );
      this.pushDownAlphabet.add ( this.symbolsToRemovePushDown );
      this.machine.setUsePushDownAlphabet ( this.oldUsePushDown );
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }
}
