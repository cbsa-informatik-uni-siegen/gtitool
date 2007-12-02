package de.unisiegen.gtitool.ui.logic;


import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeSet;

import javax.swing.AbstractListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.dnd.SymbolTransferHandler;
import de.unisiegen.gtitool.ui.jgraphcomponents.DefaultStateView;
import de.unisiegen.gtitool.ui.netbeans.TransitionDialogForm;


/**
 * The logic class for the create new transition dialog.
 * 
 * @author Benjamin Mies
 * @author Christian Fehler
 * @version $Id$
 */
public final class TransitionDialog
{

  /**
   * The color {@link ListModel}.
   * 
   * @author Christian Fehler
   */
  public final class SymbolListModel extends AbstractListModel implements
      Iterable < Symbol >
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
      Collections.sort ( this.list );
      fireContentsChanged ( this, 0, this.list.size () - 1 );
    }


    /**
     * Clears the model.
     */
    public final void clear ()
    {
      int size = this.list.size ();
      this.list.clear ();
      fireIntervalRemoved ( this, 0, size - 1 < 0 ? 0 : size - 1 );
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
     * {@inheritDoc}
     * 
     * @see Iterable#iterator()
     */
    public final Iterator < Symbol > iterator ()
    {
      return this.list.iterator ();
    }


    /**
     * Removes the item with the given index.
     * 
     * @param pIndex The item index.
     */
    public final void remove ( int pIndex )
    {
      this.list.remove ( pIndex );
      fireIntervalRemoved ( this, pIndex, pIndex );
    }


    /**
     * Removes the given item.
     * 
     * @param pItem The item to remove.
     */
    public final void remove ( Symbol pItem )
    {
      int index = this.list.indexOf ( pItem );
      this.list.remove ( pItem );
      fireIntervalRemoved ( this, index, index );
    }
  }


  /** result value if dialog was canceled */
  public static int DIALOG_CANCELED = -1;


  /** result value if dialog was confirmed */
  public static int DIALOG_CONFIRMED = 1;


  /** The epsilon {@link Symbol} */
  public final static String EPSILON = "\u03B5"; //$NON-NLS-1$


  /** result value of this dialog */
  public int DIALOG_RESULT = DIALOG_CANCELED;


  /** The {@link TransitionDialogForm} */
  private TransitionDialogForm gui;


  /** The parent frame */
  private JFrame parent;


  /** The {@link Alphabet} for this dialog */
  private Alphabet alphabet;


  /** The model for the change over set JList */
  private SymbolListModel modelChangeOverSet;


  /** The model for the alphabet JList */
  private SymbolListModel modelAlphabet;


  /**
   * The {@link SymbolTransferHandler}.
   */
  private SymbolTransferHandler transferHandler;


  /**
   * Create a new {@link TransitionDialog}
   * 
   * @param pParent the parent frame
   * @param pAlphabet the alphabet available for the new Transition
   * @param pSource The source {@link DefaultStateView}.
   * @param pTarget The target {@link DefaultStateView}.
   */
  public TransitionDialog ( JFrame pParent, Alphabet pAlphabet,
      DefaultStateView pSource, DefaultStateView pTarget )
  {
    this.parent = pParent;
    this.alphabet = pAlphabet;
    this.gui = new TransitionDialogForm ( this, pParent );
    String targetName = pTarget == null ? Messages
        .getString ( "TransitionDialog.NewState" ) : pTarget.getState () //$NON-NLS-1$
        .getName ();
    this.gui.JLabelHeadline.setText ( Messages.getString (
        "TransitionDialog.Header", pSource //$NON-NLS-1$
            .getState ().getName (), targetName ) );
    this.transferHandler = new SymbolTransferHandler ( this );
    this.gui.jListChangeOverSet.setTransferHandler ( this.transferHandler );
    this.gui.jListChangeOverSet.setDragEnabled ( true );
    this.gui.jListAlphabet.setTransferHandler ( this.transferHandler );
    this.gui.jListAlphabet.setDragEnabled ( true );
    this.modelAlphabet = new SymbolListModel ();
    for ( Symbol symbol : this.alphabet )
    {
      this.modelAlphabet.add ( symbol );
    }
    this.gui.jListAlphabet.setModel ( this.modelAlphabet );
    this.modelChangeOverSet = new SymbolListModel ();
    this.gui.jListChangeOverSet.setModel ( this.modelChangeOverSet );
    this.gui.styledAlphabetParserPanel.setAlphabet ( new Alphabet () );
  }


  /**
   * Adds the given {@link Symbol}s to the change over set.
   * 
   * @param pList The {@link Symbol}s to add.
   */
  public final void addToChangeOver ( ArrayList < Symbol > pList )
  {
    for ( Symbol current : pList )
    {
      this.modelChangeOverSet.add ( current );
      this.modelAlphabet.remove ( current );
    }
    ArrayList < Symbol > changeOverSymbols = new ArrayList < Symbol > ();
    for ( Symbol current : this.modelChangeOverSet )
    {
      changeOverSymbols.add ( current );
    }
    this.gui.jListAlphabet.clearSelection ();
    this.gui.jButtonMoveRight.setEnabled ( false );
    try
    {
      this.gui.styledAlphabetParserPanel.setAlphabet ( new Alphabet (
          changeOverSymbols ) );
    }
    catch ( AlphabetException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
    }
  }


  /**
   * Dispose the Dialog
   */
  public final void dispose ()
  {
    this.gui.dispose ();
  }


  /**
   * Get the {@link Alphabet} of this Dialog
   * 
   * @return The {@link Alphabet}
   */
  public final Alphabet getAlphabet ()
  {
    return this.alphabet;
  }


  /**
   * Returns the gui.
   * 
   * @return The gui.
   * @see #gui
   */
  public final TransitionDialogForm getGui ()
  {
    return this.gui;
  }


  /**
   * Handle Cancel Button pressed.
   */
  public final void handleActionPerformedCancel ()
  {
    this.gui.dispose ();
  }


  /**
   * Handle MoveLeft Button pressed
   */
  public final void handleActionPerformedMoveLeft ()
  {
    Object [] selectedValues = this.gui.jListChangeOverSet.getSelectedValues ();
    ArrayList < Symbol > removeList = new ArrayList < Symbol > ();
    for ( Object current : selectedValues )
    {
      removeList.add ( ( Symbol ) current );
    }
    removeFromChangeOver ( removeList );
  }


  /**
   * Handle MoveRight Button pressed
   */
  public final void handleActionPerformedMoveRight ()
  {
    Object [] selectedValues = this.gui.jListAlphabet.getSelectedValues ();
    ArrayList < Symbol > addList = new ArrayList < Symbol > ();
    for ( Object current : selectedValues )
    {
      addList.add ( ( Symbol ) current );
    }
    addToChangeOver ( addList );
  }


  /**
   * Handle Ok Button pressed.
   */
  public final void handleActionPerformedOk ()
  {
    this.DIALOG_RESULT = DIALOG_CONFIRMED;
    this.gui.setVisible ( false );
    try
    {
      ArrayList < Symbol > symbols = new ArrayList < Symbol > ();
      for ( Symbol symbol : this.modelChangeOverSet )
      {
        symbols.add ( symbol );
      }
      if ( symbols.size () == 0 )
      {
        this.alphabet = null;
      }
      else
      {
        this.alphabet = new Alphabet ( symbols );
      }
    }
    catch ( AlphabetException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
    }
  }


  /**
   * Handle JList gain Focus
   * 
   * @param evt the {@link FocusEvent}
   */
  public final void handleFocusGained ( ListSelectionEvent evt )
  {

    JList selectedList = ( JList ) evt.getSource ();
    Symbol selected = ( Symbol ) selectedList.getSelectedValue ();
    if ( selected == null )
    {
      return;
    }
    if ( selectedList.equals ( this.gui.jListAlphabet ) )
    {
      this.gui.jListChangeOverSet.clearSelection ();
      this.gui.jButtonMoveLeft.setEnabled ( false );
      if ( selectedList.getSelectedValues ().length > 0 )
        this.gui.jButtonMoveRight.setEnabled ( true );
    }
    else
    {
      this.gui.jListAlphabet.clearSelection ();
      if ( selectedList.getSelectedValues ().length > 0 )
        this.gui.jButtonMoveLeft.setEnabled ( true );
      this.gui.jButtonMoveRight.setEnabled ( false );
    }
  }


  /**
   * Removes the given {@link Symbol}s from the change over set.
   * 
   * @param pList The {@link Symbol}s to remove.
   */
  public final void removeFromChangeOver ( ArrayList < Symbol > pList )
  {
    for ( Symbol current : pList )
    {
      this.modelAlphabet.add ( current );
      this.modelChangeOverSet.remove ( current );
    }
    ArrayList < Symbol > changeOverSymbols = new ArrayList < Symbol > ();
    for ( Symbol current : this.modelChangeOverSet )
    {
      changeOverSymbols.add ( current );
    }
    this.gui.jListChangeOverSet.clearSelection ();
    this.gui.jButtonMoveLeft.setEnabled ( false );
    try
    {
      this.gui.styledAlphabetParserPanel.setAlphabet ( new Alphabet (
          changeOverSymbols ) );
    }
    catch ( AlphabetException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
    }
  }


  /**
   * Set the Symbols of the Over change Set
   * 
   * @param symbols the Symbols of the Over change Set
   */
  public final void setOverChangeSet ( TreeSet < Symbol > symbols )
  {
    if ( symbols.size () > 0 )
    {
      this.modelChangeOverSet.clear ();
    }
    for ( Symbol symbol : symbols )
    {
      this.modelAlphabet.remove ( symbol );
      this.modelChangeOverSet.add ( symbol );
    }
    try
    {
      if ( symbols.size () > 0 )
        this.gui.styledAlphabetParserPanel
            .setAlphabet ( new Alphabet ( symbols ) );
      else
        this.gui.styledAlphabetParserPanel.setAlphabet ( new Alphabet () );
    }
    catch ( AlphabetException e )
    {
      e.printStackTrace ();
      System.exit ( 1 );
    }
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
