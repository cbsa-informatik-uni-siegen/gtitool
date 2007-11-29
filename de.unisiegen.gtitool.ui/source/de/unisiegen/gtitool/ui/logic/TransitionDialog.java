package de.unisiegen.gtitool.ui.logic;


import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

import javax.swing.AbstractListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.ui.netbeans.TransitionDialogForm;


/**
 * The Logic class for the create new transition dialog
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public class TransitionDialog
{

  /**
   * The color {@link ListModel}.
   * 
   * @author Christian Fehler
   */
  protected final class SymbolListModel extends AbstractListModel
  {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = -4310954235069928120L;


    /**
     * The item list.
     */
    ArrayList < String > list;


    /**
     * Allocates a new <code>ColorListModel</code>.
     */
    public SymbolListModel ()
    {
      this.list = new ArrayList < String > ();
    }


    /**
     * Adds the given item.
     * 
     * @param pItem The item to add.
     */
    public final void add ( String pItem )
    {
      this.list.add ( pItem );
      fireIntervalAdded ( this, this.list.size () - 1, this.list.size () - 1 );
    }


    /**
     * Returns the value at the specified index.
     * 
     * @param pIndex The requested index.
     * @return The value at <code>pIndex</code>
     * @see ListModel#getElementAt(int)
     */
    public final Object getElementAt ( int pIndex )
    {
      if ( pIndex < 0 || pIndex >= this.list.size () )
      {
        throw new IllegalArgumentException ( "index incorrect" ); //$NON-NLS-1$
      }
      return this.list.get ( pIndex );
    }


    /**
     * Returns the length of the list.
     * 
     * @return The length of the list.
     * @see ListModel#getSize()
     */
    public final int getSize ()
    {
      return this.list.size ();
    }


    /**
     * Adds the given item.
     * 
     * @param pItem The item to add.
     */
    public final void remove ( String pItem )
    {
      int index = this.list.indexOf ( pItem );
      this.list.remove ( pItem );
      if ( index > 0 )
        fireIntervalRemoved ( this, index, index );
    }
  }


  /** result value if dialog was canceled */
  public static int DIALOG_CANCELED = -1;


  /** result value if dialog was confirmed */
  public static int DIALOG_CONFIRMED = 1;


  /** result value of this dialog */
  public int DIALOG_RESULT = DIALOG_CANCELED;


  /** The {@link TransitionDialogForm} */
  private TransitionDialogForm transitionDialog;


  /** The parent frame */
  private JFrame parent;


  /** The {@link Alphabet} for this dialog */
  private Alphabet alphabet;


  /** The model for the change over set JList */
  private SymbolListModel modelChangeOverSet;


  /** The model for the alphabet JList */
  private SymbolListModel modelAlphabet;


  /** The epsilon {@link Symbol} */
  public final static String EPSILON = "\u03B5"; //$NON-NLS-1$


  /**
   * Create a new {@link TransitionDialog}
   * 
   * @param pParent the parent frame
   * @param pAlphabet the alphabet available for the new Transition
   */
  public TransitionDialog ( JFrame pParent, Alphabet pAlphabet )
  {
    this.parent = pParent;
    this.alphabet = pAlphabet;
    this.transitionDialog = new TransitionDialogForm ( this, pParent );
    initialize ();
  }


  /**
   * Dispose the Dialog
   */
  public void dispose ()
  {
    this.transitionDialog.dispose ();
  }


  /**
   * Get the {@link Alphabet} of this Dialog
   * 
   * @return The {@link Alphabet}
   */
  public Alphabet getAlphabet ()
  {
    return this.alphabet;
  }


  /**
   * Handle Cancel Button pressed.
   */
  public void handleActionPerformedCancel ()
  {
    this.transitionDialog.dispose ();
  }


  /**
   * Handle MoveLeft Button pressed
   */
  public void handleActionPerformedMoveLeft ()
  {
    ArrayList < Symbol > sym = new ArrayList < Symbol > ();
    Object [] objects = this.transitionDialog.jListChangeOverSet
        .getSelectedValues ();
    for ( Object object : objects )
    {
      String symbol = ( String ) object;
      this.modelAlphabet.add ( symbol );
      this.modelChangeOverSet.remove ( symbol );
    }
    try
    {
      for ( String symbol : this.modelChangeOverSet.list )
      {
        sym.add ( new Symbol ( symbol ) );
      }
    }
    catch ( SymbolException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
    }
    this.transitionDialog.jListChangeOverSet.repaint ();
    if ( this.modelChangeOverSet.getSize () == 0 )
      this.modelChangeOverSet.add ( EPSILON );
    this.transitionDialog.jListChangeOverSet.clearSelection ();
    Collections.sort ( this.modelChangeOverSet.list );
    Collections.sort ( this.modelAlphabet.list );
    this.transitionDialog.jButtonMoveLeft.setEnabled ( false );

    try
    {

      this.transitionDialog.styledAlphabetParserPanel
          .setAlphabet ( sym.size () > 0 ? new Alphabet ( sym )
              : new Alphabet () );
    }
    catch ( AlphabetException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
    }
  }


  /**
   * Handle MoveRight Button pressed
   */
  public void handleActionPerformedMoveRight ()
  {
    ArrayList < Symbol > sym = new ArrayList < Symbol > ();
    this.modelChangeOverSet.remove ( EPSILON );
    Object [] symbols = this.transitionDialog.jListAlphabet
        .getSelectedValues ();
    if ( symbols == null )
      return;
    for ( Object object : symbols )
    {
      String symbol = ( String ) object;
      this.modelChangeOverSet.add ( symbol );
      this.modelAlphabet.remove ( symbol );
    }
    try
    {
      for ( String symbol : this.modelChangeOverSet.list )
      {
        sym.add ( new Symbol ( symbol ) );
      }
    }
    catch ( SymbolException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
    }
    this.transitionDialog.jListAlphabet.repaint ();
    this.transitionDialog.jListAlphabet.clearSelection ();
    this.transitionDialog.jButtonMoveRight.setEnabled ( false );
    Collections.sort ( this.modelChangeOverSet.list );
    Collections.sort ( this.modelAlphabet.list );
    try
    {
      this.transitionDialog.styledAlphabetParserPanel
          .setAlphabet ( new Alphabet ( sym ) );
    }
    catch ( AlphabetException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
    }
  }


  /**
   * Handle Ok Button pressed.
   */
  public void handleActionPerformedOk ()
  {
    this.DIALOG_RESULT = DIALOG_CONFIRMED;
    this.transitionDialog.setVisible ( false );
    try
    {
      if ( this.modelChangeOverSet.list.contains ( EPSILON ) )
        this.alphabet = null;
      else
      {
        try
        {
          ArrayList < Symbol > symbols = new ArrayList < Symbol > ();
          for ( String symbol : this.modelChangeOverSet.list )
            symbols.add ( new Symbol ( symbol ) );
          this.alphabet = new Alphabet ( symbols );
        }
        catch ( SymbolException e )
        {
          e.printStackTrace ();
          System.exit ( 1 );
        }
      }

    }
    catch ( AlphabetException e )
    {
      e.printStackTrace ();
    }
  }


  /**
   * Handle JList gain Focus
   * 
   * @param evt the {@link FocusEvent}
   */
  public void handleFocusGained ( ListSelectionEvent evt )
  {

    JList selectedList = ( JList ) evt.getSource ();
    String selected = ( String ) selectedList.getSelectedValue ();
    if ( selected == null || selected.equals ( EPSILON )
        || selected.equals ( " " ) ) //$NON-NLS-1$
      return;
    if ( selectedList.equals ( this.transitionDialog.jListAlphabet ) )
    {
      this.transitionDialog.jListChangeOverSet.clearSelection ();
      this.transitionDialog.jButtonMoveLeft.setEnabled ( false );
      if ( selectedList.getSelectedValues ().length > 0 )
        this.transitionDialog.jButtonMoveRight.setEnabled ( true );
    }
    else
    {
      this.transitionDialog.jListAlphabet.clearSelection ();
      if ( selectedList.getSelectedValues ().length > 0 )
        this.transitionDialog.jButtonMoveLeft.setEnabled ( true );
      this.transitionDialog.jButtonMoveRight.setEnabled ( false );
    }
  }


  /**
   * Initialize the Jlists
   */
  private void initialize ()
  {
    this.transitionDialog.jListChangeOverSet.setDragEnabled ( true );
    this.transitionDialog.jListAlphabet.setDragEnabled ( true );
    this.modelAlphabet = new SymbolListModel ();

    for ( Symbol symbol : this.alphabet )
      this.modelAlphabet.add ( symbol.getName () );
    this.transitionDialog.jListAlphabet.setModel ( this.modelAlphabet );

    this.modelChangeOverSet = new SymbolListModel ();
    this.modelChangeOverSet.add ( EPSILON );
    this.transitionDialog.jListChangeOverSet
        .setModel ( this.modelChangeOverSet );

    this.transitionDialog.styledAlphabetParserPanel
        .setAlphabet ( new Alphabet () );
  }


  /**
   * Show the dialog for creating a new transition
   */
  public void show ()
  {
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( this.transitionDialog.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( this.transitionDialog.getHeight () / 2 );
    this.transitionDialog.setBounds ( x, y, this.transitionDialog.getWidth (),
        this.transitionDialog.getHeight () );
    this.transitionDialog.setVisible ( true );
  }


  /**
   * Set the Symbols of the Over change Set
   * 
   * @param symbols the Symbols of the Over change Set
   */
  public void setOverChangeSet ( TreeSet < Symbol > symbols )
  {
    if ( symbols.size () > 0 )
      this.modelChangeOverSet.list.clear ();
    for ( Symbol symbol : symbols )
    {
      this.modelAlphabet.remove ( symbol.toString () );
      this.modelChangeOverSet.add ( symbol.toString () );

    }
    try
    {
      this.transitionDialog.styledAlphabetParserPanel
          .setAlphabet ( new Alphabet ( symbols ) );
    }
    catch ( AlphabetException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
    }

  }

}
