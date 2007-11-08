package de.unisiegen.gtitool.ui.logic;


import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.event.ListSelectionEvent;

import org.apache.log4j.Logger;

import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.netbeans.PreferencesDialogForm;
import de.unisiegen.gtitool.ui.preferences.ColorItem;
import de.unisiegen.gtitool.ui.preferences.LanguageChangedListener;
import de.unisiegen.gtitool.ui.preferences.LanguageItem;
import de.unisiegen.gtitool.ui.preferences.LookAndFeelItem;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.preferences.ZoomFactor;


/**
 * The <code>PreferencesDialog</code>.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class PreferencesDialog
{

  /**
   * The color {@link ListCellRenderer}.
   * 
   * @author Christian Fehler
   */
  protected final class ColorItemCellRenderer extends DefaultListCellRenderer
  {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 7412269853613306482L;


    /**
     * {@inheritDoc}
     * 
     * @see DefaultListCellRenderer#getListCellRendererComponent( JList, Object,
     *      int, boolean, boolean)
     */
    @Override
    public Component getListCellRendererComponent ( JList pJList,
        Object pValue, int pIndex, boolean pIsSelected,
        @SuppressWarnings ( "unused" )
        boolean pCellHasFocus )
    {
      // The pCellHasFocus value is not used any more
      JLabel label = ( JLabel ) super.getListCellRendererComponent ( pJList,
          pValue, pIndex, pIsSelected, pIsSelected );
      ColorItem colorItem = ( ColorItem ) pValue;
      label.setIcon ( colorItem.getIcon () );
      label.setText ( colorItem.getCaption () );
      label.setToolTipText ( colorItem.getDescription () );
      return label;
    }
  }


  /**
   * The color {@link ListModel}.
   * 
   * @author Christian Fehler
   */
  protected final class ColorListModel extends AbstractListModel
  {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = -4310954235069928120L;


    /**
     * The item list.
     */
    private ArrayList < ColorItem > list;


    /**
     * Allocates a new <code>ColorListModel</code>.
     */
    public ColorListModel ()
    {
      this.list = new ArrayList < ColorItem > ();
    }


    /**
     * Adds the given item.
     * 
     * @param pItem The item to add.
     */
    public final void add ( ColorItem pItem )
    {
      this.list.add ( pItem );
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
  }


  /**
   * The {@link ListCellRenderer} of the {@link JComboBox}.
   * 
   * @author Christian Fehler
   */
  protected final class ComboBoxListCellRenderer extends
      DefaultListCellRenderer
  {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 7193233782672236696L;


    /**
     * {@inheritDoc}
     * 
     * @see ListCellRenderer#getListCellRendererComponent(JList, Object, int,
     *      boolean, boolean)
     */
    @Override
    public Component getListCellRendererComponent ( JList pJList,
        Object pValue, int pIndex, boolean pIsSelected, boolean pCellHasFocus )
    {
      // Change nothing
      return super.getListCellRendererComponent ( pJList, pValue, pIndex,
          pIsSelected, pCellHasFocus );
    }
  }


  /**
   * The language {@link ComboBoxModel}.
   * 
   * @author Christian Fehler
   */
  protected final class LanguageComboBoxModel extends DefaultComboBoxModel
  {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 2992377445617042603L;


    /**
     * Adds the given item.
     * 
     * @param pLanguageItem The {@link LanguageItem} to add.
     */
    public final void addElement ( LanguageItem pLanguageItem )
    {
      super.addElement ( pLanguageItem );
    }


    /**
     * {@inheritDoc}
     * 
     * @see DefaultComboBoxModel#addElement(Object)
     */
    @Override
    public final void addElement ( @SuppressWarnings ( "unused" )
    Object pObject )
    {
      throw new IllegalArgumentException ( "do not use this method" ); //$NON-NLS-1$
    }


    /**
     * {@inheritDoc}
     * 
     * @see DefaultComboBoxModel#getElementAt(int)
     */
    @Override
    public final LanguageItem getElementAt ( int pIndex )
    {
      return ( LanguageItem ) super.getElementAt ( pIndex );
    }


    /**
     * {@inheritDoc}
     * 
     * @see DefaultComboBoxModel#getSelectedItem()
     */
    @Override
    public final LanguageItem getSelectedItem ()
    {
      return ( LanguageItem ) super.getSelectedItem ();
    }
  }


  /**
   * The look and feel {@link ComboBoxModel}.
   * 
   * @author Christian Fehler
   */
  protected final class LookAndFeelComboBoxModel extends DefaultComboBoxModel
  {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 5754474473687367844L;


    /**
     * Adds the given {@link LookAndFeelItem}.
     * 
     * @param pLookAndFeelItem The {@link LookAndFeelItem} to add.
     */
    public final void addElement ( LookAndFeelItem pLookAndFeelItem )
    {
      super.addElement ( pLookAndFeelItem );
    }


    /**
     * {@inheritDoc}
     * 
     * @see DefaultComboBoxModel#addElement(Object)
     */
    @Override
    public final void addElement ( @SuppressWarnings ( "unused" )
    Object pObject )
    {
      throw new IllegalArgumentException ( "do not use this method" ); //$NON-NLS-1$
    }


    /**
     * {@inheritDoc}
     * 
     * @see DefaultComboBoxModel#getElementAt(int)
     */
    @Override
    public final LookAndFeelItem getElementAt ( int pIndex )
    {
      return ( LookAndFeelItem ) super.getElementAt ( pIndex );
    }


    /**
     * {@inheritDoc}
     * 
     * @see DefaultComboBoxModel#getSelectedItem()
     */
    @Override
    public final LookAndFeelItem getSelectedItem ()
    {
      return ( LookAndFeelItem ) super.getSelectedItem ();
    }
  }


  /**
   * Selects the item with the given index in the color list or clears the
   * selection after a delay.
   * 
   * @author Christian Fehler
   */
  protected final class SleepTimerTask extends TimerTask
  {

    /**
     * The index.
     */
    protected int index;


    /**
     * The color list.
     */
    protected JList colorList;


    /**
     * Initilizes the <code>SleepTimerTask</code>.
     * 
     * @param pColorList The color list.
     * @param pIndex The index.
     */
    public SleepTimerTask ( JList pColorList, int pIndex )
    {
      this.index = pIndex;
      this.colorList = pColorList;
    }


    /**
     * Selects the item with the given index in the color list or clears the
     * selection after a delay.
     * 
     * @see TimerTask#run()
     */
    @Override
    public final void run ()
    {
      if ( this.index == -1 )
      {
        SwingUtilities.invokeLater ( new Runnable ()
        {

          public void run ()
          {
            SleepTimerTask.this.colorList.getSelectionModel ()
                .clearSelection ();
            SleepTimerTask.this.colorList.repaint ();
          }
        } );
      }
      else
      {
        SwingUtilities.invokeLater ( new Runnable ()
        {

          public void run ()
          {
            SleepTimerTask.this.colorList
                .setSelectedIndex ( SleepTimerTask.this.index );
            SleepTimerTask.this.colorList.repaint ();
          }
        } );
      }
    }
  }


  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger
      .getLogger ( PreferencesDialog.class );


  /**
   * The {@link PreferencesDialogForm}.
   */
  private PreferencesDialogForm gui;


  /**
   * The parent {@link JFrame}.
   */
  private JFrame parent;


  /**
   * The {@link LanguageComboBoxModel}.
   */
  private LanguageComboBoxModel languageComboBoxModel;


  /**
   * The {@link DefaultComboBoxModel} of the languages.
   */
  private LookAndFeelComboBoxModel lookAndFeelComboBoxModel;


  /**
   * The {@link ColorListModel}.
   */
  private ColorListModel colorListModel;


  /**
   * The {@link ColorItemCellRenderer}.
   */
  private ColorItemCellRenderer colorItemCellRenderer;


  /**
   * The {@link ColorItem} of the {@link State}.
   */
  private ColorItem colorItemState;


  /**
   * The {@link ColorItem} of the selected {@link State}.
   */
  private ColorItem colorItemSelectedState;


  /**
   * The {@link ColorItem} of the active {@link State}.
   */
  private ColorItem colorItemActiveState;


  /**
   * The {@link ColorItem} of the start {@link State}.
   */
  private ColorItem colorItemStartState;


  /**
   * The {@link ColorItem} of the final {@link State}.
   */
  private ColorItem colorItemFinalState;


  /**
   * The {@link ColorItem} of the {@link Symbol}.
   */
  private ColorItem colorItemSymbol;


  /**
   * The {@link ColorItem} of the {@link Transition}.
   */
  private ColorItem colorItemTransition;


  /**
   * The initial last active tab.
   */
  private int initialLastActiveTab;


  /**
   * The initial {@link LanguageItem}.
   */
  private LanguageItem initialLanguageItem;


  /**
   * The initial {@link LookAndFeelItem}.
   */
  private LookAndFeelItem initialLookAndFeel;


  /**
   * The initial {@link ColorItem} of the {@link State}.
   */
  private ColorItem initialColorItemState;


  /**
   * The initial {@link ColorItem} of the selected {@link State}.
   */
  private ColorItem initialColorItemSelectedState;


  /**
   * The initial {@link ColorItem} of the active {@link State}.
   */
  private ColorItem initialColorItemActiveState;


  /**
   * The initial {@link ColorItem} of the start {@link State}.
   */
  private ColorItem initialColorItemStartState;


  /**
   * The initial {@link ColorItem} of the final {@link State}.
   */
  private ColorItem initialColorItemFinalState;


  /**
   * The initial {@link ColorItem} of the {@link Symbol}.
   */
  private ColorItem initialColorItemSymbol;


  /**
   * The initial {@link ColorItem} of the {@link Transition}.
   */
  private ColorItem initialColorItemTransition;


  /**
   * The initial {@link ZoomFactor}.
   */
  private ZoomFactor initialZoomFactor;


  /**
   * The {@link Timer} of the color list.
   */
  private Timer colorTimer = null;


  /**
   * Allocates a new <code>PreferencesDialog</code>.
   * 
   * @param pParent The parent {@link JFrame}.
   */
  public PreferencesDialog ( JFrame pParent )
  {
    logger.debug ( "allocate a new preferences dialog" ); //$NON-NLS-1$
    this.parent = pParent;
    this.gui = new PreferencesDialogForm ( this, pParent );
    this.gui.jComboBoxLanguage.setCursor ( new Cursor ( Cursor.HAND_CURSOR ) );
    this.gui.jComboBoxLookAndFeel
        .setCursor ( new Cursor ( Cursor.HAND_CURSOR ) );

    /*
     * Language
     */
    this.languageComboBoxModel = new LanguageComboBoxModel ();
    this.languageComboBoxModel.addElement ( new LanguageItem (
        "Default", PreferenceManager.getInstance ().getSystemLocale () ) ); //$NON-NLS-1$
    for ( LanguageItem current : Messages.getLanguageItems () )
    {
      this.languageComboBoxModel.addElement ( current );
    }
    this.gui.jComboBoxLanguage.setRenderer ( new ComboBoxListCellRenderer () );
    this.gui.jComboBoxLanguage.setModel ( this.languageComboBoxModel );
    this.initialLanguageItem = PreferenceManager.getInstance ()
        .getLanguageItem ();
    this.gui.jComboBoxLanguage.setSelectedItem ( this.initialLanguageItem );

    /*
     * Look and feel
     */
    this.lookAndFeelComboBoxModel = new LookAndFeelComboBoxModel ();

    LookAndFeelInfo [] lookAndFeels = UIManager.getInstalledLookAndFeels ();
    String name = "System"; //$NON-NLS-1$
    String className = UIManager.getSystemLookAndFeelClassName ();
    loop : for ( LookAndFeelInfo current : lookAndFeels )
    {
      if ( current.getClassName ().equals ( className ) )
      {
        name += " (" + current.getName () + ")"; //$NON-NLS-1$//$NON-NLS-2$
        break loop;
      }
    }
    this.lookAndFeelComboBoxModel.addElement ( new LookAndFeelItem ( name,
        className ) );
    for ( LookAndFeelInfo current : lookAndFeels )
    {
      this.lookAndFeelComboBoxModel.addElement ( new LookAndFeelItem ( current
          .getName (), current.getClassName () ) );
    }
    this.gui.jComboBoxLookAndFeel
        .setRenderer ( new ComboBoxListCellRenderer () );
    this.gui.jComboBoxLookAndFeel.setModel ( this.lookAndFeelComboBoxModel );
    this.initialLookAndFeel = PreferenceManager.getInstance ()
        .getLookAndFeelItem ();
    this.gui.jComboBoxLookAndFeel.setSelectedItem ( this.initialLookAndFeel );

    /*
     * Zoom factor
     */
    this.initialZoomFactor = PreferenceManager.getInstance ().getZoomFactor ();
    this.gui.jSliderZoom.setValue ( this.initialZoomFactor.getFactor () );

    /*
     * Color
     */
    // State
    this.colorItemState = PreferenceManager.getInstance ().getColorItemState ();
    this.initialColorItemState = this.colorItemState.clone ();
    // Selected state
    this.colorItemSelectedState = PreferenceManager.getInstance ()
        .getColorItemSelectedState ();
    this.initialColorItemSelectedState = this.colorItemSelectedState.clone ();
    // Active state
    this.colorItemActiveState = PreferenceManager.getInstance ()
        .getColorItemActiveState ();
    this.initialColorItemActiveState = this.colorItemActiveState.clone ();
    // Start state
    this.colorItemStartState = PreferenceManager.getInstance ()
        .getColorItemStartState ();
    this.initialColorItemStartState = this.colorItemStartState.clone ();
    // Final state
    this.colorItemFinalState = PreferenceManager.getInstance ()
        .getColorItemFinalState ();
    this.initialColorItemFinalState = this.colorItemFinalState.clone ();
    // Symbol
    this.colorItemSymbol = PreferenceManager.getInstance ()
        .getColorItemSymbol ();
    this.initialColorItemSymbol = this.colorItemSymbol.clone ();
    // Transition
    this.colorItemTransition = PreferenceManager.getInstance ()
        .getColorItemTransition ();
    this.initialColorItemTransition = this.colorItemTransition.clone ();
    // Renderer
    this.colorItemCellRenderer = new ColorItemCellRenderer ();
    this.gui.jListColor.setCellRenderer ( this.colorItemCellRenderer );
    // Model
    this.colorListModel = new ColorListModel ();
    // Items
    this.colorListModel.add ( this.colorItemState );
    this.colorListModel.add ( this.colorItemSelectedState );
    this.colorListModel.add ( this.colorItemActiveState );
    this.colorListModel.add ( this.colorItemStartState );
    this.colorListModel.add ( this.colorItemFinalState );
    this.colorListModel.add ( this.colorItemSymbol );
    this.colorListModel.add ( this.colorItemTransition );
    this.gui.jListColor.setModel ( this.colorListModel );
    this.initialLastActiveTab = PreferenceManager.getInstance ()
        .getPreferencesDialogLastActiveTab ();
    this.gui.jTabbedPane.setSelectedIndex ( this.initialLastActiveTab );

    /*
     * Language changed listener
     */
    PreferenceManager.getInstance ().addLanguageChangedListener (
        new LanguageChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void languageChanged ()
          {
            // Title
            PreferencesDialog.this.gui.setTitle ( Messages
                .getString ( "PreferencesDialog.Title" ) ); //$NON-NLS-1$
            // General
            PreferencesDialog.this.gui.jTabbedPane.setTitleAt ( 0, Messages
                .getString ( "PreferencesDialog.TabGeneral" ) ); //$NON-NLS-1$
            PreferencesDialog.this.gui.jTabbedPane.setToolTipTextAt ( 0,
                Messages.getString ( "PreferencesDialog.TabGeneralToolTip" ) ); //$NON-NLS-1$
            // Colors
            PreferencesDialog.this.gui.jTabbedPane.setTitleAt ( 1, Messages
                .getString ( "PreferencesDialog.TabColors" ) ); //$NON-NLS-1$
            PreferencesDialog.this.gui.jTabbedPane.setToolTipTextAt ( 1,
                Messages.getString ( "PreferencesDialog.TabColorsToolTip" ) ); //$NON-NLS-1$
            // Accept
            PreferencesDialog.this.gui.jButtonAccept.setText ( Messages
                .getString ( "PreferencesDialog.Accept" ) ); //$NON-NLS-1$
            PreferencesDialog.this.gui.jButtonAccept.setMnemonic ( Messages
                .getString ( "PreferencesDialog.AcceptMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
            PreferencesDialog.this.gui.jButtonAccept.setToolTipText ( Messages
                .getString ( "PreferencesDialog.AcceptToolTip" ) ); //$NON-NLS-1$
            // Ok
            PreferencesDialog.this.gui.jButtonOk.setText ( Messages
                .getString ( "PreferencesDialog.Ok" ) ); //$NON-NLS-1$
            PreferencesDialog.this.gui.jButtonOk.setMnemonic ( Messages
                .getString ( "PreferencesDialog.OkMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
            PreferencesDialog.this.gui.jButtonOk.setToolTipText ( Messages
                .getString ( "PreferencesDialog.OkToolTip" ) ); //$NON-NLS-1$
            // Cancel
            PreferencesDialog.this.gui.jButtonCancel.setText ( Messages
                .getString ( "PreferencesDialog.Cancel" ) ); //$NON-NLS-1$
            PreferencesDialog.this.gui.jButtonCancel.setMnemonic ( Messages
                .getString ( "PreferencesDialog.CancelMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
            PreferencesDialog.this.gui.jButtonCancel.setToolTipText ( Messages
                .getString ( "PreferencesDialog.CancelToolTip" ) ); //$NON-NLS-1$
            // Language
            PreferencesDialog.this.gui.jLabelLanguage.setText ( Messages
                .getString ( "PreferencesDialog.Language" ) ); //$NON-NLS-1$
            PreferencesDialog.this.gui.jLabelLanguage
                .setDisplayedMnemonic ( Messages.getString (
                    "PreferencesDialog.LanguageMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
            PreferencesDialog.this.gui.jComboBoxLanguage
                .setToolTipText ( Messages
                    .getString ( "PreferencesDialog.LanguageToolTip" ) ); //$NON-NLS-1$
            // Look and feel
            PreferencesDialog.this.gui.jLabelLookAndFeel.setText ( Messages
                .getString ( "PreferencesDialog.LookAndFeel" ) ); //$NON-NLS-1$    
            PreferencesDialog.this.gui.jLabelLookAndFeel
                .setDisplayedMnemonic ( Messages.getString (
                    "PreferencesDialog.LookAndFeelMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$           
            PreferencesDialog.this.gui.jComboBoxLookAndFeel
                .setToolTipText ( Messages
                    .getString ( "PreferencesDialog.LookAndFeelToolTip" ) ); //$NON-NLS-1$
            // Zoom factor
            PreferencesDialog.this.gui.jLabelZoom.setText ( Messages
                .getString ( "PreferencesDialog.Zoom" ) ); //$NON-NLS-1$    
            PreferencesDialog.this.gui.jLabelZoom
                .setDisplayedMnemonic ( Messages.getString (
                    "PreferencesDialog.ZoomMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$           
            PreferencesDialog.this.gui.jSliderZoom.setToolTipText ( Messages
                .getString ( "PreferencesDialog.ZoomToolTip" ) ); //$NON-NLS-1$
            // Restore
            PreferencesDialog.this.gui.jButtonRestore.setText ( Messages
                .getString ( "PreferencesDialog.Restore" ) ); //$NON-NLS-1$
            PreferencesDialog.this.gui.jButtonRestore
                .setMnemonic ( Messages.getString (
                    "PreferencesDialog.RestoreMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
            PreferencesDialog.this.gui.jButtonRestore.setToolTipText ( Messages
                .getString ( "PreferencesDialog.RestoreToolTip" ) ); //$NON-NLS-1$
            // State
            PreferencesDialog.this.colorItemState.setCaption ( Messages
                .getString ( "PreferencesDialog.ColorStateCaption" ) ); //$NON-NLS-1$
            PreferencesDialog.this.colorItemState.setDescription ( Messages
                .getString ( "PreferencesDialog.ColorStateDescription" ) ); //$NON-NLS-1$
            // Selected state
            PreferencesDialog.this.colorItemSelectedState.setCaption ( Messages
                .getString ( "PreferencesDialog.ColorSelectedStateCaption" ) ); //$NON-NLS-1$
            PreferencesDialog.this.colorItemSelectedState
                .setDescription ( Messages
                    .getString ( "PreferencesDialog.ColorSelectedStateDescription" ) ); //$NON-NLS-1$
            // Active state
            PreferencesDialog.this.colorItemActiveState.setCaption ( Messages
                .getString ( "PreferencesDialog.ColorActiveStateCaption" ) ); //$NON-NLS-1$
            PreferencesDialog.this.colorItemActiveState
                .setDescription ( Messages
                    .getString ( "PreferencesDialog.ColorActiveStateDescription" ) ); //$NON-NLS-1$
            // Start state
            PreferencesDialog.this.colorItemStartState.setCaption ( Messages
                .getString ( "PreferencesDialog.ColorStartStateCaption" ) ); //$NON-NLS-1$
            PreferencesDialog.this.colorItemStartState
                .setDescription ( Messages
                    .getString ( "PreferencesDialog.ColorStartStateDescription" ) ); //$NON-NLS-1$
            // Final state
            PreferencesDialog.this.colorItemFinalState.setCaption ( Messages
                .getString ( "PreferencesDialog.ColorFinalStateCaption" ) ); //$NON-NLS-1$
            PreferencesDialog.this.colorItemFinalState
                .setDescription ( Messages
                    .getString ( "PreferencesDialog.ColorFinalStateDescription" ) ); //$NON-NLS-1$
            // Symbol
            PreferencesDialog.this.colorItemSymbol.setCaption ( Messages
                .getString ( "PreferencesDialog.ColorSymbolCaption" ) ); //$NON-NLS-1$
            PreferencesDialog.this.colorItemSymbol.setDescription ( Messages
                .getString ( "PreferencesDialog.ColorSymbolDescription" ) ); //$NON-NLS-1$
            // Transition
            PreferencesDialog.this.colorItemTransition.setCaption ( Messages
                .getString ( "PreferencesDialog.ColorTransitionCaption" ) ); //$NON-NLS-1$
            PreferencesDialog.this.colorItemTransition
                .setDescription ( Messages
                    .getString ( "PreferencesDialog.ColorTransitionDescription" ) ); //$NON-NLS-1$
          }
        } );
  }


  /**
   * Handles the action on the <code>Accept</code> button.
   */
  public final void handleAccept ()
  {
    logger.debug ( "handle accept" ); //$NON-NLS-1$
    saveData ();
  }


  /**
   * Handles the action on the <code>Cancel</code> button.
   */
  public final void handleCancel ()
  {
    logger.debug ( "handle cancel" ); //$NON-NLS-1$
    this.gui.setVisible ( false );
    if ( this.initialLastActiveTab != this.gui.jTabbedPane.getSelectedIndex () )
    {
      this.initialLastActiveTab = this.gui.jTabbedPane.getSelectedIndex ();
      PreferenceManager.getInstance ().setPreferencesDialogLastActiveTab (
          this.gui.jTabbedPane.getSelectedIndex () );
    }
    this.gui.dispose ();
  }


  /**
   * Handles the mouse exited event on the color list.
   * 
   * @param pEvent The {@link MouseEvent}.
   */
  public final void handleColorListMouseExited ( @SuppressWarnings ( "unused" )
  MouseEvent pEvent )
  {
    logger.debug ( "handle color list mouse exited" ); //$NON-NLS-1$
    if ( this.colorTimer != null )
    {
      this.colorTimer.cancel ();
    }
  }


  /**
   * Handles the mouse moved event on the color list.
   * 
   * @param pEvent The {@link MouseEvent}.
   */
  public final void handleColorListMouseMoved ( MouseEvent pEvent )
  {
    int index = this.gui.jListColor.locationToIndex ( pEvent.getPoint () );
    Rectangle rect = this.gui.jListColor.getCellBounds ( index, index );
    // Mouse is not over an item
    if ( this.colorTimer != null )
    {
      this.colorTimer.cancel ();
    }
    this.colorTimer = new Timer ();
    if ( pEvent.getY () > rect.y + rect.height )
    {
      this.gui.jListColor.setCursor ( new Cursor ( Cursor.DEFAULT_CURSOR ) );
      this.colorTimer.schedule (
          new SleepTimerTask ( this.gui.jListColor, -1 ), 250 );
    }
    else
    {
      this.gui.jListColor.setCursor ( new Cursor ( Cursor.HAND_CURSOR ) );
      this.colorTimer.schedule ( new SleepTimerTask ( this.gui.jListColor,
          index ), 250 );
    }
  }


  /**
   * Handles the {@link MouseEvent} on the color list.
   * 
   * @param pEvent The {@link MouseEvent}.
   */
  public final void handleColorListMouseReleased ( MouseEvent pEvent )
  {
    logger.debug ( "handle color list mouse released" ); //$NON-NLS-1$
    if ( pEvent.getButton () == MouseEvent.BUTTON1 )
    {
      int index = this.gui.jListColor.locationToIndex ( pEvent.getPoint () );
      Rectangle rect = this.gui.jListColor.getCellBounds ( index, index );
      // Mouse is not over an item
      if ( pEvent.getY () > rect.y + rect.height )
      {
        return;
      }
      ColorItem selectedColorItem = ( ColorItem ) this.gui.jListColor
          .getSelectedValue ();
      if ( selectedColorItem != null )
      {
        Color color = JColorChooser.showDialog ( this.gui, Messages
            .getString ( "PreferencesDialog.ColorChooser.Title" ), //$NON-NLS-1$
            selectedColorItem.getColor () );
        if ( color != null )
        {
          selectedColorItem.setColor ( color );
          this.gui.jListColor.repaint ();
        }
      }
    }
  }


  /**
   * Handles {@link ListSelectionEvent}s on the color list.
   * 
   * @param pEvent The {@link ListSelectionEvent}.
   */
  public final void handleColorListValueChanged ( @SuppressWarnings ( "unused" )
  ListSelectionEvent pEvent )
  {
    logger.debug ( "handle color list value changed" ); //$NON-NLS-1$
    ColorItem colorItem = ( ColorItem ) this.gui.jListColor.getSelectedValue ();
    if ( colorItem == null )
    {
      this.gui.jTextPaneDescription.setText ( "" ); //$NON-NLS-1$
    }
    else
    {
      this.gui.jTextPaneDescription.setText ( colorItem.getDescription () );
    }
  }


  /**
   * Handles the action on the <code>OK</code> button.
   */
  public final void handleOk ()
  {
    logger.debug ( "handle ok" ); //$NON-NLS-1$
    this.gui.setVisible ( false );
    saveData ();
    this.gui.dispose ();
  }


  /**
   * Handles the action on the <code>Restore defaults</code> button.
   */
  public final void handleRestore ()
  {
    logger.debug ( "handle restore" ); //$NON-NLS-1$
    // Language
    this.gui.jComboBoxLanguage.setSelectedIndex ( 0 );
    // Look and feel
    this.gui.jComboBoxLookAndFeel.setSelectedIndex ( 0 );
    // Color
    this.colorItemState.restore ();
    this.colorItemSelectedState.restore ();
    this.colorItemActiveState.restore ();
    this.colorItemStartState.restore ();
    this.colorItemActiveState.restore ();
    this.colorItemSymbol.restore ();
    this.colorItemTransition.restore ();
  }


  /**
   * Saves the data.
   */
  private final void saveData ()
  {
    logger.debug ( "save data" ); //$NON-NLS-1$
    // Last active tab
    saveDataLastActiveTab ();
    // Language
    saveDataLanguage ();
    // Look and feel
    saveDataLookAndFeel ();
    // Zoom factor
    saveDataZoomFactor ();
    // Color
    saveDataColor ();
  }


  /**
   * Saves the data of the color.
   */
  private final void saveDataColor ()
  {
    // State
    if ( !this.initialColorItemState.equals ( this.colorItemState ) )
    {
      logger.debug ( "color of the state changed to \"" //$NON-NLS-1$
          + "r=" + this.colorItemState.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "g=" + this.colorItemState.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "b=" + this.colorItemState.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
      this.initialColorItemState = this.colorItemState.clone ();
      PreferenceManager.getInstance ().setColorItemState ( this.colorItemState );
      PreferenceManager.getInstance ().fireColorChangedState (
          this.colorItemState.getColor () );
    }
    // Selected state
    if ( !this.initialColorItemSelectedState
        .equals ( this.colorItemSelectedState ) )
    {
      logger.debug ( "color of the selected state changed to \"" //$NON-NLS-1$
          + "r=" + this.colorItemSelectedState.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "g=" + this.colorItemSelectedState.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "b=" + this.colorItemSelectedState.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
      this.initialColorItemSelectedState = this.colorItemSelectedState.clone ();
      PreferenceManager.getInstance ().setColorItemSelectedState (
          this.colorItemSelectedState );
      PreferenceManager.getInstance ().fireColorChangedSelectedState (
          this.colorItemSelectedState.getColor () );
    }
    // Active state
    if ( !this.initialColorItemActiveState.equals ( this.colorItemActiveState ) )
    {
      logger.debug ( "color of the active state changed to \"" //$NON-NLS-1$
          + "r=" + this.colorItemActiveState.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "g=" + this.colorItemActiveState.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "b=" + this.colorItemActiveState.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
      this.initialColorItemActiveState = this.colorItemActiveState.clone ();
      PreferenceManager.getInstance ().setColorItemActiveState (
          this.colorItemActiveState );
      PreferenceManager.getInstance ().fireColorChangedActiveState (
          this.colorItemActiveState.getColor () );
    }
    // Start state
    if ( !this.initialColorItemStartState.equals ( this.colorItemStartState ) )
    {
      logger.debug ( "color of the start state changed to \"" //$NON-NLS-1$
          + "r=" + this.colorItemStartState.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "g=" + this.colorItemStartState.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "b=" + this.colorItemStartState.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
      this.initialColorItemStartState = this.colorItemStartState.clone ();
      PreferenceManager.getInstance ().setColorItemStartState (
          this.colorItemStartState );
      PreferenceManager.getInstance ().fireColorChangedStartState (
          this.colorItemStartState.getColor () );
    }
    // Final state
    if ( !this.initialColorItemFinalState.equals ( this.colorItemFinalState ) )
    {
      logger.debug ( "color of the final state changed to \"" //$NON-NLS-1$
          + "r=" + this.colorItemFinalState.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "g=" + this.colorItemFinalState.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "b=" + this.colorItemFinalState.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
      this.initialColorItemFinalState = this.colorItemFinalState.clone ();
      PreferenceManager.getInstance ().setColorItemFinalState (
          this.colorItemFinalState );
      PreferenceManager.getInstance ().fireColorChangedFinalState (
          this.colorItemFinalState.getColor () );
    }
    // Symbol
    if ( !this.initialColorItemSymbol.equals ( this.colorItemSymbol ) )
    {
      logger.debug ( "color of the symbol changed to \"" //$NON-NLS-1$
          + "r=" + this.colorItemSymbol.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "g=" + this.colorItemSymbol.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "b=" + this.colorItemSymbol.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
      this.initialColorItemSymbol = this.colorItemSymbol.clone ();
      PreferenceManager.getInstance ().setColorItemSymbol (
          this.colorItemSymbol );
      PreferenceManager.getInstance ().fireColorChangedSymbol (
          this.colorItemSymbol.getColor () );
    }
    // Transition
    if ( !this.initialColorItemTransition.equals ( this.colorItemTransition ) )
    {
      logger.debug ( "color of the transition changed to \"" //$NON-NLS-1$
          + "r=" + this.colorItemTransition.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "g=" + this.colorItemTransition.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "b=" + this.colorItemTransition.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
      this.initialColorItemTransition = this.colorItemTransition.clone ();
      PreferenceManager.getInstance ().setColorItemTransition (
          this.colorItemTransition );
      PreferenceManager.getInstance ().fireColorChangedTransition (
          this.colorItemTransition.getColor () );
    }
  }


  /**
   * Saves the data of the language.
   */
  private final void saveDataLanguage ()
  {
    LanguageItem selectedLanguageItem = this.languageComboBoxModel
        .getSelectedItem ();
    if ( !this.initialLanguageItem.equals ( selectedLanguageItem ) )
    {
      PreferenceManager.getInstance ().setLanguageItem ( selectedLanguageItem );
      if ( !this.initialLanguageItem.getLocale ().getLanguage ().equals (
          selectedLanguageItem.getLocale ().getLanguage () ) )
      {
        logger.debug ( "language changed to \"" //$NON-NLS-1$
            + selectedLanguageItem.getLocale ().getLanguage () + "\"" ); //$NON-NLS-1$
        PreferenceManager.getInstance ().fireLanguageChanged (
            selectedLanguageItem.getLocale () );
      }
      this.initialLanguageItem = selectedLanguageItem;
    }
  }


  /**
   * Saves the data of the last active tab.
   */
  private final void saveDataLastActiveTab ()
  {
    if ( this.initialLastActiveTab != this.gui.jTabbedPane.getSelectedIndex () )
    {
      this.initialLastActiveTab = this.gui.jTabbedPane.getSelectedIndex ();
      PreferenceManager.getInstance ().setPreferencesDialogLastActiveTab (
          this.gui.jTabbedPane.getSelectedIndex () );
    }
  }


  /**
   * Saves the data of the look and feel.
   */
  private final void saveDataLookAndFeel ()
  {
    LookAndFeelItem selectedLookAndFeelItem = this.lookAndFeelComboBoxModel
        .getSelectedItem ();
    if ( !this.initialLookAndFeel.equals ( selectedLookAndFeelItem ) )
    {
      PreferenceManager.getInstance ().setLookAndFeelItem (
          selectedLookAndFeelItem );
      if ( !this.initialLookAndFeel.getClassName ().equals (
          selectedLookAndFeelItem.getClassName () ) )
      {
        logger.debug ( "look and feel changed to \"" //$NON-NLS-1$
            + selectedLookAndFeelItem.getName () + "\"" ); //$NON-NLS-1$
        try
        {
          UIManager.setLookAndFeel ( selectedLookAndFeelItem.getClassName () );
          SwingUtilities.updateComponentTreeUI ( this.parent );
          for ( Frame current : Frame.getFrames () )
          {
            SwingUtilities.updateComponentTreeUI ( current );
            for ( Window w : current.getOwnedWindows () )
              SwingUtilities.updateComponentTreeUI ( w );
          }
        }
        catch ( ClassNotFoundException e )
        {
          logger.error ( "class not found exception", e ); //$NON-NLS-1$
        }
        catch ( InstantiationException e )
        {
          logger.error ( "instantiation exception", e ); //$NON-NLS-1$
        }
        catch ( IllegalAccessException e )
        {
          logger.error ( "illegal access exception", e ); //$NON-NLS-1$
        }
        catch ( UnsupportedLookAndFeelException e )
        {
          logger.error ( "unsupported look and feel exception", e ); //$NON-NLS-1$
        }
      }
      this.initialLookAndFeel = selectedLookAndFeelItem;
    }
  }


  /**
   * Saves the data of the zoom factor.
   */
  private final void saveDataZoomFactor ()
  {
    if ( this.initialZoomFactor.getFactor () != this.gui.jSliderZoom
        .getValue () )
    {
      logger.debug ( "zoom factor changed to \"" //$NON-NLS-1$
          + this.gui.jSliderZoom.getValue () + "\"" ); //$NON-NLS-1$
      this.initialZoomFactor = ZoomFactor.createFactor ( this.gui.jSliderZoom
          .getValue () );
      PreferenceManager.getInstance ().setZoomFactor ( this.initialZoomFactor );
      PreferenceManager.getInstance ().fireZoomFactorChanged (
          this.initialZoomFactor );
    }
  }


  /**
   * Shows the {@link PreferencesDialogForm}.
   */
  public final void show ()
  {
    logger.debug ( "show the preferences dialog" ); //$NON-NLS-1$
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
    this.gui.setVisible ( true );
  }
}
