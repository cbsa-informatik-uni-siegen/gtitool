package de.unisiegen.gtitool.ui.logic;


import java.util.ArrayList;
import java.util.TreeSet;

import javax.swing.JFrame;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.parser.exceptions.ParserException;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.netbeans.AlphabetDialogForm;
import de.unisiegen.gtitool.ui.style.listener.AlphabetChangedListener;


/**
 * The logic class for the create new transition dialog.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class AlphabetDialog
{

  /**
   * Result value if dialog was canceled.
   */
  public static int DIALOG_CANCELED = -1;


  /**
   * Result value if dialog was confirmed.
   */
  public static int DIALOG_CONFIRMED = 1;


  /**
   * Result value of this dialog.
   */
  public int DIALOG_RESULT = DIALOG_CANCELED;


  /**
   * The {@link AlphabetDialogForm}.
   */
  private AlphabetDialogForm gui;


  /**
   * The parent frame.
   */
  private JFrame parent;


  /**
   * The {@link Alphabet} of this dialog.
   */
  private Alphabet alphabet;


  /**
   * The {@link Machine} of this dialog.
   */
  private Machine machine;


  /**
   * Create a new {@link AlphabetDialog}
   * 
   * @param pParent The parent frame.
   * @param pAlphabet The {@link Alphabet} of this dialog.
   * @param pMachine The {@link Machine} of this dialog.
   */
  public AlphabetDialog ( JFrame pParent, Alphabet pAlphabet, Machine pMachine )
  {
    this.parent = pParent;
    this.alphabet = pAlphabet;
    this.machine = pMachine;
    this.gui = new AlphabetDialogForm ( this, pParent );
    this.gui.styledAlphabetParserPanel.setAlphabet ( this.alphabet );
    this.gui.styledAlphabetParserPanel
        .addAlphabetChangedListener ( new AlphabetChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void alphabetChanged ( Alphabet pNewAlphabet )
          {
            if ( pNewAlphabet == null )
            {
              AlphabetDialog.this.gui.jButtonOk.setEnabled ( false );
            }
            else
            {
              TreeSet < Symbol > notRemoveableSymbols = getNotRemoveableSymbols ( pNewAlphabet );
              if ( notRemoveableSymbols.size () == 0 )
              {
                AlphabetDialog.this.gui.jButtonOk.setEnabled ( true );
              }
              else
              {
                AlphabetDialog.this.gui.jButtonOk.setEnabled ( false );
                ArrayList < ScannerException > exceptionList = new ArrayList < ScannerException > ();
                for ( Symbol current : notRemoveableSymbols )
                {
                  exceptionList.add ( new ParserException ( 0, 0, Messages
                      .getString ( "AlphabetDialog.SymbolUsed", current ) ) ); //$NON-NLS-1$
                }
                AlphabetDialog.this.gui.styledAlphabetParserPanel
                    .setException ( exceptionList );
              }
            }
          }
        } );
  }


  /**
   * Returns the set of not removeable {@link Symbol}s.
   * 
   * @param pNewAlphabet The new {@link Alphabet}.
   * @return The set of not removeable {@link Symbol}s.
   */
  private final TreeSet < Symbol > getNotRemoveableSymbols (
      Alphabet pNewAlphabet )
  {
    if ( pNewAlphabet == null )
    {
      throw new IllegalArgumentException ( "new alphabet is null" ); //$NON-NLS-1$
    }
    TreeSet < Symbol > notRemoveableSymbols = new TreeSet < Symbol > ();
    TreeSet < Symbol > symbolsToRemove = new TreeSet < Symbol > ();
    for ( Symbol current : this.alphabet )
    {
      if ( !pNewAlphabet.contains ( current ) )
      {
        symbolsToRemove.add ( current );
      }
    }
    for ( Symbol current : symbolsToRemove )
    {
      if ( !this.machine.isSymbolRemoveable ( current ) )
      {
        notRemoveableSymbols.add ( current );
      }
    }
    return notRemoveableSymbols;
  }


  /**
   * Handles cancel button pressed.
   */
  public final void handleCancel ()
  {
    this.DIALOG_RESULT = DIALOG_CANCELED;
    this.gui.dispose ();
  }


  /**
   * Handle ok button pressed.
   */
  public final void handleOk ()
  {
    this.gui.setVisible ( false );
    Alphabet newAlphabet = this.gui.styledAlphabetParserPanel.getAlphabet ();
    TreeSet < Symbol > symbolsToAdd = new TreeSet < Symbol > ();
    TreeSet < Symbol > symbolsToRemove = new TreeSet < Symbol > ();
    for ( Symbol current : newAlphabet )
    {
      if ( !this.alphabet.contains ( current ) )
      {
        symbolsToAdd.add ( current );
      }
    }
    for ( Symbol current : this.alphabet )
    {
      if ( !newAlphabet.contains ( current ) )
      {
        symbolsToRemove.add ( current );
      }
    }
    try
    {
      // TODOChristian Do not use the alphabet
      this.alphabet.add ( symbolsToAdd );
      this.alphabet.remove ( symbolsToRemove );
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    this.DIALOG_RESULT = DIALOG_CONFIRMED;
    this.gui.dispose ();
  }


  /**
   * Show the dialog for creating a new transition
   */
  public final void show ()
  {
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
    this.gui.setVisible ( true );
  }
}
