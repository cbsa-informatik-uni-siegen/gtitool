package de.unisiegen.gtitool.ui.logic;


import java.awt.event.FocusEvent;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.ListModel;

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
    private ArrayList < Symbol > list;


    /**
     * Allocates a new <code>ColorListModel</code>.
     */
    public SymbolListModel ()
    {
      this.list = new ArrayList < Symbol > ();
    }


    /**
     * Adds the given item.
     * 
     * @param pItem The item to add.
     */
    public final void add ( Symbol pItem )
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
     * Get the List containing all {@link Symbol}s
     * 
     * @return List with all Symbols
     */
    public ArrayList < Symbol > getList ()
    {
      return this.list;
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
    public final void remove ( Symbol pItem )
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
  public Symbol epsilon;


  /**
   * Create a new {@link TransitionDialog}
   * 
   * @param pParent the parent frame
   * @param pAlphabet the alphabet available for the new Transition
   */
  @SuppressWarnings ( "nls" )
  public TransitionDialog ( JFrame pParent, Alphabet pAlphabet )
  {
    this.parent = pParent;
    this.alphabet = pAlphabet;
    this.transitionDialog = new TransitionDialogForm ( pParent, true );
    this.transitionDialog.setLogic ( this );

    try
    {
      this.epsilon = new Symbol ( "\u03B5" ); //$NON-NLS-1$
    }
    catch ( SymbolException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
    }
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
    Symbol symbol = ( Symbol ) this.transitionDialog.jListChangeOverSet
        .getSelectedValue ();
    this.modelAlphabet.add ( symbol );
    this.modelChangeOverSet.remove ( symbol );
    this.transitionDialog.jListChangeOverSet.repaint ();
    if ( this.modelChangeOverSet.getSize () == 0 )
      this.modelChangeOverSet.add ( this.epsilon );
    this.transitionDialog.jListChangeOverSet.clearSelection ();
    this.transitionDialog.jButtonMoveLeft.setEnabled ( false );

  }


  /**
   * Handle MoveRight Button pressed
   */
  public void handleActionPerformedMoveRight ()
  {
    this.modelChangeOverSet.remove ( this.epsilon );
    Symbol symbol = ( Symbol ) this.transitionDialog.jListAlphabet
        .getSelectedValue ();
    this.modelChangeOverSet.add ( symbol );
    this.modelAlphabet.remove ( symbol );
    this.transitionDialog.jListAlphabet.repaint ();
    this.transitionDialog.jListAlphabet.clearSelection ();
    this.transitionDialog.jButtonMoveRight.setEnabled ( false );
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
      if ( this.modelChangeOverSet.getList().contains ( this.epsilon ) )
        this.alphabet = null ;
      else
        this.alphabet = new Alphabet ( this.modelChangeOverSet.getList () );
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
  public void handleFocusGained ( FocusEvent evt )
  {
    JList selectedList = ( JList ) evt.getSource ();
    if ( selectedList.equals ( this.transitionDialog.jListAlphabet ) )
    {
      this.transitionDialog.jListChangeOverSet.clearSelection ();
      this.transitionDialog.jButtonMoveLeft.setEnabled ( false );
      this.transitionDialog.jButtonMoveRight.setEnabled ( true );
    }
    else
    {
      this.transitionDialog.jListAlphabet.clearSelection ();
      this.transitionDialog.jButtonMoveLeft.setEnabled ( true );
      this.transitionDialog.jButtonMoveRight.setEnabled ( false );
    }

    if ( selectedList.getSelectedValue () != null
        && this.epsilon.equals ( selectedList.getSelectedValue () ) )
      this.transitionDialog.jButtonMoveLeft.setEnabled ( false );

  }


  /**
   * Initialize the Jlists
   */
  private void initialize ()
  {
    this.modelAlphabet = new SymbolListModel ();

    for ( Symbol symbol : this.alphabet )
      this.modelAlphabet.add ( symbol );
    this.transitionDialog.jListAlphabet.setModel ( this.modelAlphabet );

    this.modelChangeOverSet = new SymbolListModel ();
    this.modelChangeOverSet.add ( this.epsilon );
    this.transitionDialog.jListChangeOverSet
        .setModel ( this.modelChangeOverSet );
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

}
