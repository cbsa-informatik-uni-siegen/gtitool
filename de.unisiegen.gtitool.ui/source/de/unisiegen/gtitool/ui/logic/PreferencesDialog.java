package de.unisiegen.gtitool.ui.logic;


import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.apache.log4j.Logger;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.listener.AlphabetChangedListener;
import de.unisiegen.gtitool.core.entities.listener.NonterminalSymbolSetChangedListener;
import de.unisiegen.gtitool.core.entities.listener.TerminalSymbolSetChangedListener;
import de.unisiegen.gtitool.core.preferences.item.AlphabetItem;
import de.unisiegen.gtitool.core.preferences.item.ColorItem;
import de.unisiegen.gtitool.core.preferences.item.LanguageItem;
import de.unisiegen.gtitool.core.preferences.item.NonterminalSymbolSetItem;
import de.unisiegen.gtitool.core.preferences.item.TerminalSymbolSetItem;
import de.unisiegen.gtitool.core.preferences.listener.LanguageChangedListener;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.netbeans.PreferencesDialogForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.preferences.item.AutoStepItem;
import de.unisiegen.gtitool.ui.preferences.item.LookAndFeelItem;
import de.unisiegen.gtitool.ui.preferences.item.MouseSelectionItem;
import de.unisiegen.gtitool.ui.preferences.item.TransitionItem;
import de.unisiegen.gtitool.ui.preferences.item.ZoomFactorItem;


/**
 * The {@link PreferencesDialog}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class PreferencesDialog implements LanguageChangedListener
{

  /**
   * The {@link Color} tree cell renderer.
   * 
   * @author Christian Fehler
   */
  protected class ColorTreeCellRenderer extends DefaultTreeCellRenderer
  {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = -989208191259975641L;


    /**
     * Allocates a new {@link ColorTreeCellRenderer}.
     */
    public ColorTreeCellRenderer ()
    {
      // Do nothing
    }


    /**
     * {@inheritDoc}
     * 
     * @see DefaultTreeCellRenderer#getTreeCellRendererComponent(JTree, Object,
     *      boolean, boolean, boolean, int, boolean)
     */
    @Override
    public final Component getTreeCellRendererComponent ( JTree tree,
        Object value, boolean sel, boolean expanded, boolean leaf, int row,
        @SuppressWarnings ( "unused" )
        boolean focus )
    {
      super.getTreeCellRendererComponent ( tree, value, sel, expanded, leaf,
          row, sel );
      if ( leaf )
      {
        ColorItem colorItem = ( ColorItem ) value;
        setIcon ( colorItem.getIcon () );
        setToolTipText ( colorItem.getDescription () );
      }
      else
      {
        setIcon ( null );
        setToolTipText ( null );
      }
      return this;
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
     * @param languageItem The {@link LanguageItem} to add.
     */
    public final void addElement ( LanguageItem languageItem )
    {
      super.addElement ( languageItem );
    }


    /**
     * {@inheritDoc}
     * 
     * @see DefaultComboBoxModel#addElement(Object)
     */
    @Override
    public final void addElement ( @SuppressWarnings ( "unused" )
    Object object )
    {
      throw new IllegalArgumentException ( "do not use this method" ); //$NON-NLS-1$
    }


    /**
     * {@inheritDoc}
     * 
     * @see DefaultComboBoxModel#getElementAt(int)
     */
    @Override
    public final LanguageItem getElementAt ( int index )
    {
      return ( LanguageItem ) super.getElementAt ( index );
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
     * @param lookAndFeelItem The {@link LookAndFeelItem} to add.
     */
    public final void addElement ( LookAndFeelItem lookAndFeelItem )
    {
      super.addElement ( lookAndFeelItem );
    }


    /**
     * {@inheritDoc}
     * 
     * @see DefaultComboBoxModel#addElement(Object)
     */
    @Override
    public final void addElement ( @SuppressWarnings ( "unused" )
    Object object )
    {
      throw new IllegalArgumentException ( "do not use this method" ); //$NON-NLS-1$
    }


    /**
     * {@inheritDoc}
     * 
     * @see DefaultComboBoxModel#getElementAt(int)
     */
    @Override
    public final LookAndFeelItem getElementAt ( int index )
    {
      return ( LookAndFeelItem ) super.getElementAt ( index );
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
   * The mouse selection {@link ComboBoxModel}.
   * 
   * @author Christian Fehler
   */
  protected final class MouseSelectionComboBoxModel extends
      DefaultComboBoxModel
  {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 9167257578795363897L;


    /**
     * Adds the given item.
     * 
     * @param mouseSelectionItem The {@link MouseSelectionItem} to add.
     */
    public final void addElement ( MouseSelectionItem mouseSelectionItem )
    {
      super.addElement ( mouseSelectionItem );
    }


    /**
     * {@inheritDoc}
     * 
     * @see DefaultComboBoxModel#addElement(Object)
     */
    @Override
    public final void addElement ( @SuppressWarnings ( "unused" )
    Object object )
    {
      throw new IllegalArgumentException ( "do not use this method" ); //$NON-NLS-1$
    }


    /**
     * {@inheritDoc}
     * 
     * @see DefaultComboBoxModel#getElementAt(int)
     */
    @Override
    public final MouseSelectionItem getElementAt ( int index )
    {
      return ( MouseSelectionItem ) super.getElementAt ( index );
    }


    /**
     * {@inheritDoc}
     * 
     * @see DefaultComboBoxModel#getSelectedItem()
     */
    @Override
    public final MouseSelectionItem getSelectedItem ()
    {
      return ( MouseSelectionItem ) super.getSelectedItem ();
    }
  }


  /**
   * The transition {@link ComboBoxModel}.
   * 
   * @author Christian Fehler
   */
  protected final class TransitionComboBoxModel extends DefaultComboBoxModel
  {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 480693066999856887L;


    /**
     * {@inheritDoc}
     * 
     * @see DefaultComboBoxModel#addElement(Object)
     */
    @Override
    public final void addElement ( @SuppressWarnings ( "unused" )
    Object object )
    {
      throw new IllegalArgumentException ( "do not use this method" ); //$NON-NLS-1$
    }


    /**
     * Adds the given item.
     * 
     * @param transitionItem The {@link TransitionItem} to add.
     */
    public final void addElement ( TransitionItem transitionItem )
    {
      super.addElement ( transitionItem );
    }


    /**
     * {@inheritDoc}
     * 
     * @see DefaultComboBoxModel#getElementAt(int)
     */
    @Override
    public final TransitionItem getElementAt ( int index )
    {
      return ( TransitionItem ) super.getElementAt ( index );
    }


    /**
     * {@inheritDoc}
     * 
     * @see DefaultComboBoxModel#getSelectedItem()
     */
    @Override
    public final TransitionItem getSelectedItem ()
    {
      return ( TransitionItem ) super.getSelectedItem ();
    }
  }


  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger
      .getLogger ( PreferencesDialog.class );


  /**
   * The index of the general tab.
   */
  private static final int GENERAL_TAB_INDEX = 0;


  /**
   * The index of the view tab.
   */
  private static final int VIEW_TAB_INDEX = 1;


  /**
   * The index of the colors tab.
   */
  private static final int COLOR_TAB_INDEX = 2;


  /**
   * The index of the alphabet tab.
   */
  private static final int ALPHABET_TAB_INDEX = 3;


  /**
   * The index of the grammar tab.
   */
  private static final int GRAMMAR_TAB_INDEX = 4;


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
   * The {@link MouseSelectionComboBoxModel}.
   */
  private MouseSelectionComboBoxModel mouseSelectionComboBoxModel;


  /**
   * The {@link TransitionComboBoxModel}.
   */
  private TransitionComboBoxModel transitionComboBoxModel;


  /**
   * The {@link DefaultComboBoxModel} of the languages.
   */
  private LookAndFeelComboBoxModel lookAndFeelComboBoxModel;


  /**
   * The {@link ColorItem} of the {@link State}.
   */
  private ColorItem colorItemStateBackground;


  /**
   * The {@link ColorItem} of the selected {@link State}.
   */
  private ColorItem colorItemStateSelected;


  /**
   * The {@link ColorItem} of the selected {@link Transition}.
   */
  private ColorItem colorItemTransitionSelected;


  /**
   * The {@link ColorItem} of the active {@link State}.
   */
  private ColorItem colorItemStateActive;


  /**
   * The {@link ColorItem} of the active {@link Symbol}.
   */
  private ColorItem colorItemSymbolActive;


  /**
   * The {@link ColorItem} of the active {@link Transition}.
   */
  private ColorItem colorItemTransitionActive;


  /**
   * The {@link ColorItem} of the start {@link State}.
   */
  private ColorItem colorItemStateStart;


  /**
   * The {@link ColorItem} of the error {@link State}.
   */
  private ColorItem colorItemStateError;


  /**
   * The {@link ColorItem} of the error {@link Symbol}.
   */
  private ColorItem colorItemSymbolError;


  /**
   * The {@link ColorItem} of the {@link Transition}.
   */
  private ColorItem colorItemTransition;


  /**
   * The {@link ColorItem} of the error {@link Transition}.
   */
  private ColorItem colorItemTransitionError;


  /**
   * The {@link ColorItem} of the parser warning.
   */
  private ColorItem colorItemParserWarning;


  /**
   * The {@link ColorItem} of the parser highlighting.
   */
  private ColorItem colorItemParserHighlighting;


  /**
   * The {@link ColorItem} of the parser {@link State}.
   */
  private ColorItem colorItemState;


  /**
   * The {@link ColorItem} of the parser {@link Symbol}.
   */
  private ColorItem colorItemSymbol;


  /**
   * The {@link AlphabetItem}.
   */
  private AlphabetItem alphabetItem;


  /**
   * The push down {@link AlphabetItem}.
   */
  private AlphabetItem pushDownAlphabetItem;


  /**
   * The {@link NonterminalSymbolSetItem}.
   */
  private NonterminalSymbolSetItem nonterminalSymbolSetItem;


  /**
   * The {@link TerminalSymbolSetItem}.
   */
  private TerminalSymbolSetItem terminalSymbolSetItem;


  /**
   * The initial last active tab.
   */
  private int initialLastActiveTab;


  /**
   * The initial {@link LanguageItem}.
   */
  private LanguageItem initialLanguageItem;


  /**
   * The initial {@link MouseSelectionItem}.
   */
  private MouseSelectionItem initialMouseSelectionItem;


  /**
   * The initial {@link MouseSelectionItem}.
   */
  private TransitionItem initialTransitionItem;


  /**
   * The initial {@link LookAndFeelItem}.
   */
  private LookAndFeelItem initialLookAndFeel;


  /**
   * The initial {@link ColorItem} of the {@link State}.
   */
  private ColorItem initialColorItemStateBackground;


  /**
   * The initial {@link ColorItem} of the selected {@link State}.
   */
  private ColorItem initialColorItemStateSelected;


  /**
   * The initial {@link ColorItem} of the selected {@link Transition}.
   */
  private ColorItem initialColorItemTransitionSelected;


  /**
   * The initial {@link ColorItem} of the active {@link State}.
   */
  private ColorItem initialColorItemStateActive;


  /**
   * The initial {@link ColorItem} of the active {@link Symbol}.
   */
  private ColorItem initialColorItemSymbolActive;


  /**
   * The initial {@link ColorItem} of the active {@link Transition}.
   */
  private ColorItem initialColorItemTransitionActive;


  /**
   * The initial {@link ColorItem} of the start {@link State}.
   */
  private ColorItem initialColorItemStateStart;


  /**
   * The initial {@link ColorItem} of the error {@link State}.
   */
  private ColorItem initialColorItemStateError;


  /**
   * The initial {@link ColorItem} of the error {@link Symbol}.
   */
  private ColorItem initialColorItemSymbolError;


  /**
   * The initial {@link ColorItem} of the {@link Transition}.
   */
  private ColorItem initialColorItemTransition;


  /**
   * The initial {@link ColorItem} of the error {@link Transition}.
   */
  private ColorItem initialColorItemTransitionError;


  /**
   * The initial {@link ColorItem} of the parser warning.
   */
  private ColorItem initialColorItemParserWarning;


  /**
   * The initial {@link ColorItem} of the parser highlighting.
   */
  private ColorItem initialColorItemParserHighlighting;


  /**
   * The initial {@link ColorItem} of the parser {@link State}.
   */
  private ColorItem initialColorItemState;


  /**
   * The initial {@link ColorItem} of the parser {@link Symbol}.
   */
  private ColorItem initialColorItemSymbol;


  /**
   * The initial {@link ZoomFactorItem}.
   */
  private ZoomFactorItem initialZoomFactorItem;


  /**
   * The initial {@link AutoStepItem}.
   */
  private AutoStepItem initialAutoStepItem;


  /**
   * The initial {@link AlphabetItem}.
   */
  private AlphabetItem initialAlphabetItem;


  /**
   * The initial push down {@link AlphabetItem}.
   */
  private AlphabetItem initialPushDownAlphabetItem;


  /**
   * The initial use push down {@link Alphabet}.
   */
  private boolean initialUsePushDownAlphabet;


  /**
   * The initial {@link NonterminalSymbolSetItem}.
   */
  private NonterminalSymbolSetItem initialNonterminalSymbolSetItem;


  /**
   * The initial {@link TerminalSymbolSetItem}.
   */
  private TerminalSymbolSetItem initialTerminalSymbolSetItem;


  /**
   * The language {@link JPopupMenu}.
   */
  private JPopupMenu jPopupMenuLanguage;


  /**
   * The look and feel {@link JPopupMenu}.
   */
  private JPopupMenu jPopupMenuLookAndFeel;


  /**
   * The zoom factor {@link JPopupMenu}.
   */
  private JPopupMenu jPopupMenuZoomFactor;


  /**
   * The {@link Transition} {@link JPopupMenu}.
   */
  private JPopupMenu jPopupMenuTransition;


  /**
   * The mouse selection {@link JPopupMenu}.
   */
  private JPopupMenu jPopupMenuMouseSelection;


  /**
   * The auto interval {@link JPopupMenu}.
   */
  private JPopupMenu jPopupMenuAutoStep;


  /**
   * The push down {@link Alphabet} {@link JPopupMenu}.
   */
  private JPopupMenu jPopupMenuUsePushDownAlphabet;


  /**
   * The color list {@link JPopupMenu}.
   */
  private JPopupMenu jPopupMenuColorList;


  /**
   * The color tree root node.
   */
  private DefaultMutableTreeNode rootNode;


  /**
   * The color tree {@link State} node.
   */
  private DefaultMutableTreeNode stateNode;


  /**
   * The color tree {@link Transition} node.
   */
  private DefaultMutableTreeNode transitionNode;


  /**
   * The color tree {@link Symbol} node.
   */
  private DefaultMutableTreeNode symbolNode;


  /**
   * The color tree parser node.
   */
  private DefaultMutableTreeNode parserNode;


  /**
   * Allocates a new {@link PreferencesDialog}.
   * 
   * @param parent The parent {@link JFrame}.
   */
  public PreferencesDialog ( JFrame parent )
  {
    logger.debug ( "allocate a new preferences dialog" ); //$NON-NLS-1$
    this.parent = parent;
    this.gui = new PreferencesDialogForm ( this, parent );
    init ();
    PreferenceManager.getInstance ().addLanguageChangedListener ( this );
  }


  /**
   * Handles the action on the accept button.
   */
  public final void handleAccept ()
  {
    logger.debug ( "handle accept" ); //$NON-NLS-1$
    save ();
  }


  /**
   * Handles the action on the cancel button.
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
   * Handles the {@link MouseEvent} on the color tree.
   * 
   * @param event The {@link MouseEvent}.
   */
  public final void handleColorTreeMouseReleased ( MouseEvent event )
  {
    if ( ( event.getButton () == MouseEvent.BUTTON1 )
        && ( event.getClickCount () > 1 ) )
    {
      logger.debug ( "handle color tree mouse released" ); //$NON-NLS-1$ 
      TreePath selectedPath = this.gui.jTreeColors.getSelectionModel ()
          .getSelectionPath ();
      if ( ( selectedPath != null )
          && ( selectedPath.getLastPathComponent () instanceof ColorItem ) )
      {
        ColorItem selectedColorItem = ( ColorItem ) selectedPath
            .getLastPathComponent ();
        Color color = JColorChooser.showDialog ( this.gui, Messages
            .getString ( "PreferencesDialog.ColorChooser.Title" ), //$NON-NLS-1$
            selectedColorItem.getColor () );
        if ( color != null )
        {
          selectedColorItem.setColor ( color );
          this.gui.jTreeColors.repaint ();
        }
      }
    }
  }


  /**
   * Handles the action on the ok button.
   */
  public final void handleOk ()
  {
    logger.debug ( "handle ok" ); //$NON-NLS-1$
    this.gui.setVisible ( false );
    save ();
    this.gui.dispose ();
  }


  /**
   * Handles the action on the restore defaults button.
   */
  public final void handleRestore ()
  {
    logger.debug ( "handle restore" ); //$NON-NLS-1$

    /*
     * General
     */
    this.gui.jComboBoxLanguage.setSelectedIndex ( 0 );
    this.gui.jComboBoxLookAndFeel.setSelectedIndex ( 0 );
    this.gui.jSliderZoom.setValue ( PreferenceManager.DEFAULT_ZOOM_FACTOR_ITEM
        .getFactor () );

    /*
     * View
     */
    this.gui.jComboBoxTransition
        .setSelectedIndex ( PreferenceManager.DEFAULT_TRANSITION_ITEM
            .getIndex () );
    this.gui.jComboBoxMouseSelection
        .setSelectedIndex ( PreferenceManager.DEFAULT_MOUSE_SELECTION_ITEM
            .getIndex () );
    this.gui.jSliderAutoStep
        .setValue ( PreferenceManager.DEFAULT_AUTO_STEP_INTERVAL_ITEM
            .getAutoStepInterval () );

    /*
     * Color
     */
    this.colorItemState.restore ();
    this.colorItemStateBackground.restore ();
    this.colorItemStateSelected.restore ();
    this.colorItemStateStart.restore ();
    this.colorItemStateActive.restore ();
    this.colorItemStateError.restore ();

    this.colorItemTransition.restore ();
    this.colorItemTransitionSelected.restore ();
    this.colorItemTransitionActive.restore ();
    this.colorItemTransitionError.restore ();

    this.colorItemSymbol.restore ();
    this.colorItemSymbolActive.restore ();
    this.colorItemSymbolError.restore ();

    this.colorItemParserWarning.restore ();
    this.colorItemParserHighlighting.restore ();

    this.gui.jTreeColors.repaint ();

    /*
     * Alphabet
     */
    this.alphabetItem.restore ();
    this.gui.alphabetPanelForm.styledAlphabetParserPanelInput
        .setAlphabet ( this.alphabetItem.getAlphabet () );
    this.gui.alphabetPanelForm.jCheckBoxPushDownAlphabet
        .setSelected ( de.unisiegen.gtitool.core.preferences.PreferenceManager.DEFAULT_USE_PUSH_DOWN_ALPHABET );
    this.pushDownAlphabetItem.restore ();
    this.gui.alphabetPanelForm.styledAlphabetParserPanelPushDown
        .setAlphabet ( this.pushDownAlphabetItem.getAlphabet () );

    /*
     * Grammar
     */
    this.nonterminalSymbolSetItem.restore ();
    this.gui.terminalPanelForm
        .setNonterminalSymbolSet ( this.nonterminalSymbolSetItem
            .getNonterminalSymbolSet () );
    this.terminalSymbolSetItem.restore ();
    this.gui.terminalPanelForm
        .setTerminalSymbolSet ( this.terminalSymbolSetItem
            .getTerminalSymbolSet () );
  }


  /**
   * Initializes the data.
   */
  private final void init ()
  {
    /*
     * General
     */
    initLanguage ();
    initLookAndFeel ();
    initZoomFactor ();
    /*
     * View
     */
    initTransition ();
    initMouseSelection ();
    initAutoStep ();
    /*
     * Color
     */
    initColor ();
    /*
     * Alphabet
     */
    initAlphabet ();
    initPushDownAlphabet ();
    /*
     * Grammar
     */
    initNonterminalSymbolSet ();
    initTerminalSymbolSet ();
    /*
     * Tab
     */
    initLastActiveTab ();
  }


  /**
   * Initializes the {@link Alphabet}.
   */
  private final void initAlphabet ()
  {
    this.alphabetItem = PreferenceManager.getInstance ().getAlphabetItem ();
    this.initialAlphabetItem = this.alphabetItem.clone ();
    this.gui.alphabetPanelForm.styledAlphabetParserPanelInput
        .setAlphabet ( this.alphabetItem.getAlphabet () );

    // PopupMenu
    JPopupMenu jPopupMenu = this.gui.alphabetPanelForm.styledAlphabetParserPanelInput
        .getJPopupMenu ();
    jPopupMenu.addSeparator ();
    final JMenuItem jMenuItemRestoreAlphabet = new JMenuItem ( Messages
        .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
    jMenuItemRestoreAlphabet.setMnemonic ( Messages.getString (
        "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    jMenuItemRestoreAlphabet.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/refresh16.png" ) ) ); //$NON-NLS-1$
    jMenuItemRestoreAlphabet.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent event )
      {
        PreferencesDialog.this.alphabetItem.restore ();
        PreferencesDialog.this.gui.alphabetPanelForm.styledAlphabetParserPanelInput
            .setAlphabet ( PreferencesDialog.this.alphabetItem.getAlphabet () );
      }
    } );
    PreferenceManager.getInstance ().addLanguageChangedListener (
        new LanguageChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void languageChanged ()
          {
            jMenuItemRestoreAlphabet.setText ( Messages
                .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
            jMenuItemRestoreAlphabet.setMnemonic ( Messages.getString (
                "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
          }
        } );
    jPopupMenu.add ( jMenuItemRestoreAlphabet );

    /*
     * Alphabet changed listener
     */
    this.gui.alphabetPanelForm.styledAlphabetParserPanelInput
        .addAlphabetChangedListener ( new AlphabetChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void alphabetChanged ( Alphabet newAlphabet )
          {
            setButtonStatus ();
            if ( newAlphabet != null )
            {
              PreferencesDialog.this.alphabetItem.setAlphabet ( newAlphabet );
            }
          }
        } );
  }


  /**
   * Initializes the auto step interval.
   */
  private final void initAutoStep ()
  {
    this.initialAutoStepItem = PreferenceManager.getInstance ()
        .getAutoStepItem ();
    this.gui.jSliderAutoStep.setValue ( this.initialAutoStepItem
        .getAutoStepInterval () );

    // PopupMenu
    this.jPopupMenuAutoStep = new JPopupMenu ();
    final JMenuItem jMenuItemRestoreAutoStep = new JMenuItem ( Messages
        .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
    jMenuItemRestoreAutoStep.setMnemonic ( Messages.getString (
        "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    jMenuItemRestoreAutoStep.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/refresh16.png" ) ) ); //$NON-NLS-1$
    jMenuItemRestoreAutoStep.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent event )
      {
        PreferencesDialog.this.gui.jSliderAutoStep
            .setValue ( PreferenceManager.DEFAULT_AUTO_STEP_INTERVAL_ITEM
                .getAutoStepInterval () );
      }
    } );
    PreferenceManager.getInstance ().addLanguageChangedListener (
        new LanguageChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void languageChanged ()
          {
            jMenuItemRestoreAutoStep.setText ( Messages
                .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
            jMenuItemRestoreAutoStep.setMnemonic ( Messages.getString (
                "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
          }
        } );
    this.jPopupMenuAutoStep.add ( jMenuItemRestoreAutoStep );
    this.gui.jSliderAutoStep.addMouseListener ( new MouseAdapter ()
    {

      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mousePressed ( MouseEvent event )
      {
        if ( event.isPopupTrigger () )
        {
          PreferencesDialog.this.jPopupMenuAutoStep.show ( event
              .getComponent (), event.getX (), event.getY () );
        }
      }


      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mouseReleased ( MouseEvent event )
      {
        if ( event.isPopupTrigger () )
        {
          PreferencesDialog.this.jPopupMenuAutoStep.show ( event
              .getComponent (), event.getX (), event.getY () );
        }
      }
    } );
  }


  /**
   * Initializes the color.
   */
  private final void initColor ()
  {
    // State
    this.colorItemState = PreferenceManager.getInstance ().getColorItemState ();
    this.initialColorItemState = this.colorItemState.clone ();
    // StateBackground
    this.colorItemStateBackground = PreferenceManager.getInstance ()
        .getColorItemStateBackground ();
    this.initialColorItemStateBackground = this.colorItemStateBackground
        .clone ();
    // State selected
    this.colorItemStateSelected = PreferenceManager.getInstance ()
        .getColorItemStateSelected ();
    this.initialColorItemStateSelected = this.colorItemStateSelected.clone ();
    // State start
    this.colorItemStateStart = PreferenceManager.getInstance ()
        .getColorItemStateStart ();
    this.initialColorItemStateStart = this.colorItemStateStart.clone ();
    // State active
    this.colorItemStateActive = PreferenceManager.getInstance ()
        .getColorItemStateActive ();
    this.initialColorItemStateActive = this.colorItemStateActive.clone ();
    // State error
    this.colorItemStateError = PreferenceManager.getInstance ()
        .getColorItemStateError ();
    this.initialColorItemStateError = this.colorItemStateError.clone ();

    // Transition
    this.colorItemTransition = PreferenceManager.getInstance ()
        .getColorItemTransition ();
    this.initialColorItemTransition = this.colorItemTransition.clone ();
    // Transition selected
    this.colorItemTransitionSelected = PreferenceManager.getInstance ()
        .getColorItemTransitionSelected ();
    this.initialColorItemTransitionSelected = this.colorItemTransitionSelected
        .clone ();
    // Active transition
    this.colorItemTransitionActive = PreferenceManager.getInstance ()
        .getColorItemTransitionActive ();
    this.initialColorItemTransitionActive = this.colorItemTransitionActive
        .clone ();
    // Transition error
    this.colorItemTransitionError = PreferenceManager.getInstance ()
        .getColorItemTransitionError ();
    this.initialColorItemTransitionError = this.colorItemTransitionError
        .clone ();

    // Symbol
    this.colorItemSymbol = PreferenceManager.getInstance ()
        .getColorItemSymbol ();
    this.initialColorItemSymbol = this.colorItemSymbol.clone ();
    // Symbol active
    this.colorItemSymbolActive = PreferenceManager.getInstance ()
        .getColorItemSymbolActive ();
    this.initialColorItemSymbolActive = this.colorItemSymbolActive.clone ();
    // Symbol error
    this.colorItemSymbolError = PreferenceManager.getInstance ()
        .getColorItemSymbolError ();
    this.initialColorItemSymbolError = this.colorItemSymbolError.clone ();

    // Parser warning
    this.colorItemParserWarning = PreferenceManager.getInstance ()
        .getColorItemParserWarning ();
    this.initialColorItemParserWarning = this.colorItemParserWarning.clone ();
    // Parser highlighting
    this.colorItemParserHighlighting = PreferenceManager.getInstance ()
        .getColorItemParserHighlighting ();
    this.initialColorItemParserHighlighting = this.colorItemParserHighlighting
        .clone ();

    // PopupMenu
    this.jPopupMenuColorList = new JPopupMenu ();
    final JMenuItem jMenuItemRestoreColorList = new JMenuItem ( Messages
        .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
    jMenuItemRestoreColorList.setMnemonic ( Messages.getString (
        "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    jMenuItemRestoreColorList.setIcon ( new ImageIcon ( getClass ()
        .getResource ( "/de/unisiegen/gtitool/ui/icon/refresh16.png" ) ) ); //$NON-NLS-1$
    jMenuItemRestoreColorList.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent event )
      {
        TreePath selectedPath = PreferencesDialog.this.gui.jTreeColors
            .getSelectionModel ().getSelectionPath ();
        if ( ( selectedPath != null )
            && ( selectedPath.getLastPathComponent () instanceof ColorItem ) )
        {
          ColorItem selectedColorItem = ( ColorItem ) selectedPath
              .getLastPathComponent ();
          selectedColorItem.restore ();
        }
      }
    } );
    this.jPopupMenuColorList.add ( jMenuItemRestoreColorList );

    this.gui.jTreeColors.addMouseListener ( new MouseAdapter ()
    {

      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mousePressed ( MouseEvent event )
      {
        if ( event.isPopupTrigger () )
        {
          TreePath selectedPath = PreferencesDialog.this.gui.jTreeColors
              .getPathForLocation ( event.getX (), event.getY () );
          if ( ( selectedPath != null )
              && ( selectedPath.getLastPathComponent () instanceof ColorItem ) )
          {
            PreferencesDialog.this.gui.jTreeColors
                .setSelectionPath ( selectedPath );
            PreferencesDialog.this.jPopupMenuColorList.show ( event
                .getComponent (), event.getX (), event.getY () );
          }
        }
      }


      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mouseReleased ( MouseEvent event )
      {
        if ( event.isPopupTrigger () )
        {
          TreePath selectedPath = PreferencesDialog.this.gui.jTreeColors
              .getPathForLocation ( event.getX (), event.getY () );
          if ( ( selectedPath != null )
              && ( selectedPath.getLastPathComponent () instanceof ColorItem ) )
          {
            PreferencesDialog.this.gui.jTreeColors
                .setSelectionPath ( selectedPath );
            PreferencesDialog.this.jPopupMenuColorList.show ( event
                .getComponent (), event.getX (), event.getY () );
          }
        }
      }
    } );

    PreferenceManager.getInstance ().addLanguageChangedListener (
        new LanguageChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void languageChanged ()
          {
            jMenuItemRestoreColorList.setText ( Messages
                .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
            jMenuItemRestoreColorList.setMnemonic ( Messages.getString (
                "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
          }
        } );

    // Items
    this.rootNode = new DefaultMutableTreeNode ( null );

    this.stateNode = new DefaultMutableTreeNode ( Messages
        .getString ( "PreferencesDialog.ColorTreeState" ) ); //$NON-NLS-1$
    this.stateNode.add ( this.colorItemState );
    this.stateNode.add ( this.colorItemStateBackground );
    this.stateNode.add ( this.colorItemStateSelected );
    this.stateNode.add ( this.colorItemStateStart );
    this.stateNode.add ( this.colorItemStateActive );
    this.stateNode.add ( this.colorItemStateError );

    this.transitionNode = new DefaultMutableTreeNode ( Messages
        .getString ( "PreferencesDialog.ColorTreeTransition" ) ); //$NON-NLS-1$
    this.transitionNode.add ( this.colorItemTransition );
    this.transitionNode.add ( this.colorItemTransitionSelected );
    this.transitionNode.add ( this.colorItemTransitionActive );
    this.transitionNode.add ( this.colorItemTransitionError );

    this.symbolNode = new DefaultMutableTreeNode ( Messages
        .getString ( "PreferencesDialog.ColorTreeSymbol" ) ); //$NON-NLS-1$
    this.symbolNode.add ( this.colorItemSymbol );
    this.symbolNode.add ( this.colorItemSymbolActive );
    this.symbolNode.add ( this.colorItemSymbolError );

    this.parserNode = new DefaultMutableTreeNode ( Messages
        .getString ( "PreferencesDialog.ColorTreeParser" ) ); //$NON-NLS-1$
    this.parserNode.add ( this.colorItemParserWarning );
    this.parserNode.add ( this.colorItemParserHighlighting );

    this.rootNode.add ( this.stateNode );
    this.rootNode.add ( this.transitionNode );
    this.rootNode.add ( this.symbolNode );
    this.rootNode.add ( this.parserNode );

    DefaultTreeModel model = new DefaultTreeModel ( this.rootNode );
    this.gui.jTreeColors.setModel ( model );
    this.gui.jTreeColors.setRowHeight ( 0 );
    this.gui.jTreeColors.getSelectionModel ().setSelectionMode (
        TreeSelectionModel.SINGLE_TREE_SELECTION );
    this.gui.jTreeColors.setCellRenderer ( new ColorTreeCellRenderer () );

    ToolTipManager.sharedInstance ().registerComponent ( this.gui.jTreeColors );
  }


  /**
   * Initializes the language.
   */
  private final void initLanguage ()
  {
    this.languageComboBoxModel = new LanguageComboBoxModel ();
    this.languageComboBoxModel.addElement ( new LanguageItem (
        "Default", PreferenceManager.getInstance ().getSystemLocale () ) ); //$NON-NLS-1$
    for ( LanguageItem current : Messages.getLanguageItems () )
    {
      this.languageComboBoxModel.addElement ( current );
    }
    this.gui.jComboBoxLanguage.setModel ( this.languageComboBoxModel );
    this.initialLanguageItem = PreferenceManager.getInstance ()
        .getLanguageItem ();
    this.gui.jComboBoxLanguage.setSelectedItem ( this.initialLanguageItem );

    // PopupMenu
    this.jPopupMenuLanguage = new JPopupMenu ();
    final JMenuItem jMenuItemRestoreLanguage = new JMenuItem ( Messages
        .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
    jMenuItemRestoreLanguage.setMnemonic ( Messages.getString (
        "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    jMenuItemRestoreLanguage.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/refresh16.png" ) ) ); //$NON-NLS-1$
    jMenuItemRestoreLanguage.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent event )
      {
        PreferencesDialog.this.gui.jComboBoxLanguage.setSelectedIndex ( 0 );
      }
    } );
    this.jPopupMenuLanguage.add ( jMenuItemRestoreLanguage );
    this.gui.jComboBoxLanguage.addMouseListener ( new MouseAdapter ()
    {

      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mousePressed ( MouseEvent event )
      {
        if ( event.isPopupTrigger () )
        {
          PreferencesDialog.this.jPopupMenuLanguage.show ( event
              .getComponent (), event.getX (), event.getY () );
        }
      }


      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mouseReleased ( MouseEvent event )
      {
        if ( event.isPopupTrigger () )
        {

          PreferencesDialog.this.jPopupMenuLanguage.show ( event
              .getComponent (), event.getX (), event.getY () );
        }
      }
    } );
    PreferenceManager.getInstance ().addLanguageChangedListener (
        new LanguageChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void languageChanged ()
          {
            jMenuItemRestoreLanguage.setText ( Messages
                .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
            jMenuItemRestoreLanguage.setMnemonic ( Messages.getString (
                "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
          }
        } );
  }


  /**
   * Initializes the last active tab.
   */
  private final void initLastActiveTab ()
  {
    this.initialLastActiveTab = PreferenceManager.getInstance ()
        .getPreferencesDialogLastActiveTab ();
    this.gui.jTabbedPane.setSelectedIndex ( this.initialLastActiveTab );
  }


  /**
   * Initializes the look and feel.
   */
  private final void initLookAndFeel ()
  {
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
    this.gui.jComboBoxLookAndFeel.setModel ( this.lookAndFeelComboBoxModel );
    this.initialLookAndFeel = PreferenceManager.getInstance ()
        .getLookAndFeelItem ();
    this.gui.jComboBoxLookAndFeel.setSelectedItem ( this.initialLookAndFeel );

    // PopupMenu
    this.jPopupMenuLookAndFeel = new JPopupMenu ();
    final JMenuItem jMenuItemRestoreLookAndFeel = new JMenuItem ( Messages
        .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
    jMenuItemRestoreLookAndFeel.setMnemonic ( Messages.getString (
        "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    jMenuItemRestoreLookAndFeel.setIcon ( new ImageIcon ( getClass ()
        .getResource ( "/de/unisiegen/gtitool/ui/icon/refresh16.png" ) ) ); //$NON-NLS-1$
    jMenuItemRestoreLookAndFeel.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent event )
      {
        PreferencesDialog.this.gui.jComboBoxLookAndFeel.setSelectedIndex ( 0 );
      }
    } );
    this.jPopupMenuLookAndFeel.add ( jMenuItemRestoreLookAndFeel );
    this.gui.jComboBoxLookAndFeel.addMouseListener ( new MouseAdapter ()
    {

      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mousePressed ( MouseEvent event )
      {
        if ( event.isPopupTrigger () )
        {
          PreferencesDialog.this.jPopupMenuLookAndFeel.show ( event
              .getComponent (), event.getX (), event.getY () );
        }
      }


      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mouseReleased ( MouseEvent event )
      {
        if ( event.isPopupTrigger () )
        {

          PreferencesDialog.this.jPopupMenuLookAndFeel.show ( event
              .getComponent (), event.getX (), event.getY () );
        }
      }
    } );
    PreferenceManager.getInstance ().addLanguageChangedListener (
        new LanguageChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void languageChanged ()
          {
            jMenuItemRestoreLookAndFeel.setText ( Messages
                .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
            jMenuItemRestoreLookAndFeel.setMnemonic ( Messages.getString (
                "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
          }
        } );
  }


  /**
   * Initializes the mouse selection.
   */
  private final void initMouseSelection ()
  {
    this.mouseSelectionComboBoxModel = new MouseSelectionComboBoxModel ();
    this.mouseSelectionComboBoxModel.addElement ( MouseSelectionItem
        .create ( 0 ) );
    this.mouseSelectionComboBoxModel.addElement ( MouseSelectionItem
        .create ( 1 ) );
    this.gui.jComboBoxMouseSelection
        .setModel ( this.mouseSelectionComboBoxModel );
    this.initialMouseSelectionItem = PreferenceManager.getInstance ()
        .getMouseSelectionItem ();
    this.gui.jComboBoxMouseSelection
        .setSelectedItem ( this.initialMouseSelectionItem );

    // PopupMenu
    this.jPopupMenuMouseSelection = new JPopupMenu ();
    final JMenuItem jMenuItemRestoreMouseSelection = new JMenuItem ( Messages
        .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
    jMenuItemRestoreMouseSelection.setMnemonic ( Messages.getString (
        "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    jMenuItemRestoreMouseSelection.setIcon ( new ImageIcon ( getClass ()
        .getResource ( "/de/unisiegen/gtitool/ui/icon/refresh16.png" ) ) ); //$NON-NLS-1$
    jMenuItemRestoreMouseSelection.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent event )
      {
        PreferencesDialog.this.gui.jComboBoxMouseSelection
            .setSelectedIndex ( PreferenceManager.DEFAULT_MOUSE_SELECTION_ITEM
                .getIndex () );
      }
    } );
    this.jPopupMenuMouseSelection.add ( jMenuItemRestoreMouseSelection );
    this.gui.jComboBoxMouseSelection.addMouseListener ( new MouseAdapter ()
    {

      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mousePressed ( MouseEvent event )
      {
        if ( event.isPopupTrigger () )
        {
          PreferencesDialog.this.jPopupMenuMouseSelection.show ( event
              .getComponent (), event.getX (), event.getY () );
        }
      }


      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mouseReleased ( MouseEvent event )
      {
        if ( event.isPopupTrigger () )
        {
          PreferencesDialog.this.jPopupMenuMouseSelection.show ( event
              .getComponent (), event.getX (), event.getY () );
        }
      }
    } );
    PreferenceManager.getInstance ().addLanguageChangedListener (
        new LanguageChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void languageChanged ()
          {
            jMenuItemRestoreMouseSelection.setText ( Messages
                .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
            jMenuItemRestoreMouseSelection.setMnemonic ( Messages.getString (
                "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
          }
        } );
  }


  /**
   * Initializes the {@link NonterminalSymbolSet}.
   */
  private final void initNonterminalSymbolSet ()
  {
    this.nonterminalSymbolSetItem = PreferenceManager.getInstance ()
        .getNonterminalSymbolSetItem ();
    this.initialNonterminalSymbolSetItem = this.nonterminalSymbolSetItem
        .clone ();
    this.gui.terminalPanelForm
        .setNonterminalSymbolSet ( this.nonterminalSymbolSetItem
            .getNonterminalSymbolSet () );

    // PopupMenu
    JPopupMenu jPopupMenu = this.gui.terminalPanelForm.styledNonterminalSymbolSetParserPanel
        .getJPopupMenu ();
    jPopupMenu.addSeparator ();
    final JMenuItem jMenuItemRestoreNonterminalSymbolSet = new JMenuItem (
        Messages.getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
    jMenuItemRestoreNonterminalSymbolSet.setMnemonic ( Messages.getString (
        "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    jMenuItemRestoreNonterminalSymbolSet.setIcon ( new ImageIcon ( getClass ()
        .getResource ( "/de/unisiegen/gtitool/ui/icon/refresh16.png" ) ) ); //$NON-NLS-1$
    jMenuItemRestoreNonterminalSymbolSet
        .addActionListener ( new ActionListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void actionPerformed ( @SuppressWarnings ( "unused" )
          ActionEvent event )
          {
            PreferencesDialog.this.nonterminalSymbolSetItem.restore ();
            PreferencesDialog.this.gui.terminalPanelForm
                .setNonterminalSymbolSet ( PreferencesDialog.this.nonterminalSymbolSetItem
                    .getNonterminalSymbolSet () );
          }
        } );
    PreferenceManager.getInstance ().addLanguageChangedListener (
        new LanguageChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void languageChanged ()
          {
            jMenuItemRestoreNonterminalSymbolSet.setText ( Messages
                .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
            jMenuItemRestoreNonterminalSymbolSet
                .setMnemonic ( Messages.getString (
                    "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
          }
        } );
    jPopupMenu.add ( jMenuItemRestoreNonterminalSymbolSet );

    /*
     * NonterminalSymbolSet changed listener
     */
    this.gui.terminalPanelForm.styledNonterminalSymbolSetParserPanel
        .addNonterminalSymbolSetChangedListener ( new NonterminalSymbolSetChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void nonterminalSymbolSetChanged (
              NonterminalSymbolSet newNonterminalSymbolSet )
          {
            setButtonStatus ();
            if ( newNonterminalSymbolSet != null )
            {
              PreferencesDialog.this.nonterminalSymbolSetItem
                  .setNonterminalSymbolSet ( newNonterminalSymbolSet );
            }
          }
        } );
  }


  /**
   * Initializes the push down alphabet.
   */
  private final void initPushDownAlphabet ()
  {
    this.pushDownAlphabetItem = PreferenceManager.getInstance ()
        .getPushDownAlphabetItem ();
    this.initialPushDownAlphabetItem = this.pushDownAlphabetItem.clone ();
    this.gui.alphabetPanelForm.styledAlphabetParserPanelPushDown
        .setAlphabet ( this.pushDownAlphabetItem.getAlphabet () );
    this.initialUsePushDownAlphabet = PreferenceManager.getInstance ()
        .getUsePushDownAlphabet ();
    this.gui.alphabetPanelForm.jCheckBoxPushDownAlphabet
        .setSelected ( this.initialUsePushDownAlphabet );

    // PopupMenu
    JPopupMenu jPopupMenu = this.gui.alphabetPanelForm.styledAlphabetParserPanelPushDown
        .getJPopupMenu ();
    jPopupMenu.addSeparator ();
    final JMenuItem jMenuItemRestorePushDownAlphabet = new JMenuItem ( Messages
        .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
    jMenuItemRestorePushDownAlphabet.setMnemonic ( Messages.getString (
        "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    jMenuItemRestorePushDownAlphabet.setIcon ( new ImageIcon ( getClass ()
        .getResource ( "/de/unisiegen/gtitool/ui/icon/refresh16.png" ) ) ); //$NON-NLS-1$
    jMenuItemRestorePushDownAlphabet.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent event )
      {
        PreferencesDialog.this.pushDownAlphabetItem.restore ();
        PreferencesDialog.this.gui.alphabetPanelForm.styledAlphabetParserPanelPushDown
            .setAlphabet ( PreferencesDialog.this.pushDownAlphabetItem
                .getAlphabet () );
      }
    } );
    PreferenceManager.getInstance ().addLanguageChangedListener (
        new LanguageChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void languageChanged ()
          {
            jMenuItemRestorePushDownAlphabet.setText ( Messages
                .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
            jMenuItemRestorePushDownAlphabet.setMnemonic ( Messages.getString (
                "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
          }
        } );
    jPopupMenu.add ( jMenuItemRestorePushDownAlphabet );

    /*
     * Alphabet changed listener
     */
    this.gui.alphabetPanelForm.styledAlphabetParserPanelPushDown
        .addAlphabetChangedListener ( new AlphabetChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void alphabetChanged ( Alphabet newAlphabet )
          {
            setButtonStatus ();
            if ( newAlphabet != null )
            {
              PreferencesDialog.this.pushDownAlphabetItem
                  .setAlphabet ( newAlphabet );
            }
          }
        } );

    // PopupMenu
    this.jPopupMenuUsePushDownAlphabet = new JPopupMenu ();
    // RestoreUsePushDownAlphabet
    JMenuItem jMenuItemRestoreUsePushDownAlphabet = new JMenuItem ( Messages
        .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
    jMenuItemRestoreUsePushDownAlphabet.setMnemonic ( Messages.getString (
        "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    jMenuItemRestoreUsePushDownAlphabet.setIcon ( new ImageIcon ( getClass ()
        .getResource ( "/de/unisiegen/gtitool/ui/icon/refresh16.png" ) ) ); //$NON-NLS-1$
    jMenuItemRestoreUsePushDownAlphabet
        .addActionListener ( new ActionListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void actionPerformed ( @SuppressWarnings ( "unused" )
          ActionEvent event )
          {
            PreferencesDialog.this.gui.alphabetPanelForm.jCheckBoxPushDownAlphabet
                .setSelected ( de.unisiegen.gtitool.core.preferences.PreferenceManager.DEFAULT_USE_PUSH_DOWN_ALPHABET );
          }
        } );
    this.jPopupMenuUsePushDownAlphabet
        .add ( jMenuItemRestoreUsePushDownAlphabet );
    this.gui.alphabetPanelForm.jCheckBoxPushDownAlphabet
        .addMouseListener ( new MouseAdapter ()
        {

          @SuppressWarnings ( "synthetic-access" )
          @Override
          public void mousePressed ( MouseEvent event )
          {
            if ( event.isPopupTrigger () )
            {
              PreferencesDialog.this.jPopupMenuUsePushDownAlphabet.show ( event
                  .getComponent (), event.getX (), event.getY () );
            }
          }


          @SuppressWarnings ( "synthetic-access" )
          @Override
          public void mouseReleased ( MouseEvent event )
          {
            if ( event.isPopupTrigger () )
            {
              PreferencesDialog.this.jPopupMenuUsePushDownAlphabet.show ( event
                  .getComponent (), event.getX (), event.getY () );
            }
          }
        } );
  }


  /**
   * Initializes the {@link TerminalSymbolSet}.
   */
  private final void initTerminalSymbolSet ()
  {
    this.terminalSymbolSetItem = PreferenceManager.getInstance ()
        .getTerminalSymbolSetItem ();
    this.initialTerminalSymbolSetItem = this.terminalSymbolSetItem.clone ();
    this.gui.terminalPanelForm
        .setTerminalSymbolSet ( this.terminalSymbolSetItem
            .getTerminalSymbolSet () );

    // PopupMenu
    JPopupMenu jPopupMenu = this.gui.terminalPanelForm.styledTerminalSymbolSetParserPanel
        .getJPopupMenu ();
    jPopupMenu.addSeparator ();
    final JMenuItem jMenuItemRestoreTerminalSymbolSet = new JMenuItem (
        Messages.getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
    jMenuItemRestoreTerminalSymbolSet.setMnemonic ( Messages.getString (
        "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    jMenuItemRestoreTerminalSymbolSet.setIcon ( new ImageIcon ( getClass ()
        .getResource ( "/de/unisiegen/gtitool/ui/icon/refresh16.png" ) ) ); //$NON-NLS-1$
    jMenuItemRestoreTerminalSymbolSet.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent event )
      {
        PreferencesDialog.this.terminalSymbolSetItem.restore ();
        PreferencesDialog.this.gui.terminalPanelForm
            .setTerminalSymbolSet ( PreferencesDialog.this.terminalSymbolSetItem
                .getTerminalSymbolSet () );
      }
    } );
    PreferenceManager.getInstance ().addLanguageChangedListener (
        new LanguageChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void languageChanged ()
          {
            jMenuItemRestoreTerminalSymbolSet.setText ( Messages
                .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
            jMenuItemRestoreTerminalSymbolSet.setMnemonic ( Messages.getString (
                "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
          }
        } );
    jPopupMenu.add ( jMenuItemRestoreTerminalSymbolSet );

    /*
     * TerminalSymbolSet changed listener
     */
    this.gui.terminalPanelForm.styledTerminalSymbolSetParserPanel
        .addTerminalSymbolSetChangedListener ( new TerminalSymbolSetChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void terminalSymbolSetChanged (
              TerminalSymbolSet newTerminalSymbolSet )
          {
            setButtonStatus ();
            if ( newTerminalSymbolSet != null )
            {
              PreferencesDialog.this.terminalSymbolSetItem
                  .setTerminalSymbolSet ( newTerminalSymbolSet );
            }
          }
        } );
  }


  /**
   * Initializes the transition.
   */
  private final void initTransition ()
  {
    this.transitionComboBoxModel = new TransitionComboBoxModel ();
    this.transitionComboBoxModel.addElement ( TransitionItem.create ( 0 ) );
    this.transitionComboBoxModel.addElement ( TransitionItem.create ( 1 ) );
    this.gui.jComboBoxTransition.setModel ( this.transitionComboBoxModel );
    this.initialTransitionItem = PreferenceManager.getInstance ()
        .getTransitionItem ();
    this.gui.jComboBoxTransition.setSelectedItem ( this.initialTransitionItem );

    // PopupMenu
    this.jPopupMenuTransition = new JPopupMenu ();
    final JMenuItem jMenuItemRestoreTranstion = new JMenuItem ( Messages
        .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
    jMenuItemRestoreTranstion.setMnemonic ( Messages.getString (
        "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    jMenuItemRestoreTranstion.setIcon ( new ImageIcon ( getClass ()
        .getResource ( "/de/unisiegen/gtitool/ui/icon/refresh16.png" ) ) ); //$NON-NLS-1$
    jMenuItemRestoreTranstion.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent event )
      {
        PreferencesDialog.this.gui.jComboBoxTransition
            .setSelectedIndex ( PreferenceManager.DEFAULT_TRANSITION_ITEM
                .getIndex () );
      }
    } );
    this.jPopupMenuTransition.add ( jMenuItemRestoreTranstion );
    this.gui.jComboBoxTransition.addMouseListener ( new MouseAdapter ()
    {

      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mousePressed ( MouseEvent event )
      {
        if ( event.isPopupTrigger () )
        {
          PreferencesDialog.this.jPopupMenuTransition.show ( event
              .getComponent (), event.getX (), event.getY () );
        }
      }


      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mouseReleased ( MouseEvent event )
      {
        if ( event.isPopupTrigger () )
        {
          PreferencesDialog.this.jPopupMenuTransition.show ( event
              .getComponent (), event.getX (), event.getY () );
        }
      }
    } );
    PreferenceManager.getInstance ().addLanguageChangedListener (
        new LanguageChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void languageChanged ()
          {
            jMenuItemRestoreTranstion.setText ( Messages
                .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
            jMenuItemRestoreTranstion.setMnemonic ( Messages.getString (
                "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
          }
        } );
  }


  /**
   * Initializes the zoom factor.
   */
  private final void initZoomFactor ()
  {
    this.initialZoomFactorItem = PreferenceManager.getInstance ()
        .getZoomFactorItem ();
    this.gui.jSliderZoom.setValue ( this.initialZoomFactorItem.getFactor () );

    // PopupMenu
    this.jPopupMenuZoomFactor = new JPopupMenu ();
    final JMenuItem jMenuItemRestoreZoomFactor = new JMenuItem ( Messages
        .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
    jMenuItemRestoreZoomFactor.setMnemonic ( Messages.getString (
        "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    jMenuItemRestoreZoomFactor.setIcon ( new ImageIcon ( getClass ()
        .getResource ( "/de/unisiegen/gtitool/ui/icon/refresh16.png" ) ) ); //$NON-NLS-1$
    jMenuItemRestoreZoomFactor.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent event )
      {
        PreferencesDialog.this.gui.jSliderZoom
            .setValue ( PreferenceManager.DEFAULT_ZOOM_FACTOR_ITEM.getFactor () );
      }
    } );
    this.jPopupMenuZoomFactor.add ( jMenuItemRestoreZoomFactor );
    this.gui.jSliderZoom.addMouseListener ( new MouseAdapter ()
    {

      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mousePressed ( MouseEvent event )
      {
        if ( event.isPopupTrigger () )
        {
          PreferencesDialog.this.jPopupMenuZoomFactor.show ( event
              .getComponent (), event.getX (), event.getY () );
        }
      }


      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mouseReleased ( MouseEvent event )
      {
        if ( event.isPopupTrigger () )
        {
          PreferencesDialog.this.jPopupMenuZoomFactor.show ( event
              .getComponent (), event.getX (), event.getY () );
        }
      }
    } );
    PreferenceManager.getInstance ().addLanguageChangedListener (
        new LanguageChangedListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void languageChanged ()
          {
            jMenuItemRestoreZoomFactor.setText ( Messages
                .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
            jMenuItemRestoreZoomFactor.setMnemonic ( Messages.getString (
                "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
          }
        } );
  }


  /**
   * {@inheritDoc}
   * 
   * @see LanguageChangedListener#languageChanged()
   */
  public final void languageChanged ()
  {
    // Title
    this.gui.setTitle ( Messages.getString ( "PreferencesDialog.Title" ) ); //$NON-NLS-1$
    // General
    this.gui.jTabbedPane.setTitleAt ( GENERAL_TAB_INDEX, Messages
        .getString ( "PreferencesDialog.TabGeneral" ) ); //$NON-NLS-1$
    this.gui.jTabbedPane.setToolTipTextAt ( GENERAL_TAB_INDEX, Messages
        .getString ( "PreferencesDialog.TabGeneralToolTip" ) ); //$NON-NLS-1$
    // View
    this.gui.jTabbedPane.setTitleAt ( VIEW_TAB_INDEX, Messages
        .getString ( "PreferencesDialog.TabView" ) ); //$NON-NLS-1$
    this.gui.jTabbedPane.setToolTipTextAt ( VIEW_TAB_INDEX, Messages
        .getString ( "PreferencesDialog.TabViewToolTip" ) ); //$NON-NLS-1$
    // Colors
    this.gui.jTabbedPane.setTitleAt ( COLOR_TAB_INDEX, Messages
        .getString ( "PreferencesDialog.TabColors" ) ); //$NON-NLS-1$
    this.gui.jTabbedPane.setToolTipTextAt ( COLOR_TAB_INDEX, Messages
        .getString ( "PreferencesDialog.TabColorsToolTip" ) ); //$NON-NLS-1$
    // Alphabet
    this.gui.jTabbedPane.setTitleAt ( ALPHABET_TAB_INDEX, Messages
        .getString ( "PreferencesDialog.TabAlphabet" ) ); //$NON-NLS-1$
    this.gui.jTabbedPane.setToolTipTextAt ( ALPHABET_TAB_INDEX, Messages
        .getString ( "PreferencesDialog.TabAlphabetToolTip" ) ); //$NON-NLS-1$
    this.gui.alphabetPanelForm.jLabelInputAlphabet.setText ( Messages
        .getString ( "PreferencesDialog.InputAlphabet" ) ); //$NON-NLS-1$
    this.gui.alphabetPanelForm.jCheckBoxPushDownAlphabet.setText ( Messages
        .getString ( "PreferencesDialog.PushDownAlphabet" ) ); //$NON-NLS-1$
    // Grammar
    this.gui.jTabbedPane.setTitleAt ( GRAMMAR_TAB_INDEX, Messages
        .getString ( "PreferencesDialog.TabGrammar" ) ); //$NON-NLS-1$
    this.gui.jTabbedPane.setToolTipTextAt ( GRAMMAR_TAB_INDEX, Messages
        .getString ( "PreferencesDialog.TabGrammarToolTip" ) ); //$NON-NLS-1$
    this.gui.terminalPanelForm.jLabelNonterminalSymbols.setText ( Messages
        .getString ( "TerminalPanel.NonterminalSymbols" ) ); //$NON-NLS-1$
    this.gui.terminalPanelForm.jLabelTerminalSymbols.setText ( Messages
        .getString ( "TerminalPanel.NonterminalSymbols" ) ); //$NON-NLS-1$
    // Accept
    this.gui.jGTIButtonAccept.setText ( Messages
        .getString ( "PreferencesDialog.Accept" ) ); //$NON-NLS-1$
    this.gui.jGTIButtonAccept.setMnemonic ( Messages.getString (
        "PreferencesDialog.AcceptMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    this.gui.jGTIButtonAccept.setToolTipText ( Messages
        .getString ( "PreferencesDialog.AcceptToolTip" ) ); //$NON-NLS-1$
    // Ok
    this.gui.jGTIButtonOk.setText ( Messages
        .getString ( "PreferencesDialog.Ok" ) ); //$NON-NLS-1$
    this.gui.jGTIButtonOk.setMnemonic ( Messages.getString (
        "PreferencesDialog.OkMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    this.gui.jGTIButtonOk.setToolTipText ( Messages
        .getString ( "PreferencesDialog.OkToolTip" ) ); //$NON-NLS-1$
    // Cancel
    this.gui.jGTIButtonCancel.setText ( Messages
        .getString ( "PreferencesDialog.Cancel" ) ); //$NON-NLS-1$
    this.gui.jGTIButtonCancel.setMnemonic ( Messages.getString (
        "PreferencesDialog.CancelMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    this.gui.jGTIButtonCancel.setToolTipText ( Messages
        .getString ( "PreferencesDialog.CancelToolTip" ) ); //$NON-NLS-1$
    // Language
    this.gui.jLabelLanguage.setText ( Messages
        .getString ( "PreferencesDialog.Language" ) ); //$NON-NLS-1$
    this.gui.jLabelLanguage.setDisplayedMnemonic ( Messages.getString (
        "PreferencesDialog.LanguageMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    this.gui.jComboBoxLanguage.setToolTipText ( Messages
        .getString ( "PreferencesDialog.LanguageToolTip" ) ); //$NON-NLS-1$
    // Look and feel
    this.gui.jLabelLookAndFeel.setText ( Messages
        .getString ( "PreferencesDialog.LookAndFeel" ) ); //$NON-NLS-1$    
    this.gui.jLabelLookAndFeel.setDisplayedMnemonic ( Messages.getString (
        "PreferencesDialog.LookAndFeelMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$           
    this.gui.jComboBoxLookAndFeel.setToolTipText ( Messages
        .getString ( "PreferencesDialog.LookAndFeelToolTip" ) ); //$NON-NLS-1$
    // Zoom
    this.gui.jLabelZoom.setText ( Messages
        .getString ( "PreferencesDialog.Zoom" ) ); //$NON-NLS-1$    
    this.gui.jLabelZoom.setDisplayedMnemonic ( Messages.getString (
        "PreferencesDialog.ZoomMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$           
    this.gui.jSliderZoom.setToolTipText ( Messages
        .getString ( "PreferencesDialog.ZoomToolTip" ) ); //$NON-NLS-1$
    // Auto Step
    this.gui.jLabelAutoStep.setText ( Messages
        .getString ( "PreferencesDialog.AutoStep" ) ); //$NON-NLS-1$    
    this.gui.jLabelAutoStep.setDisplayedMnemonic ( Messages.getString (
        "PreferencesDialog.AutoStepMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$           
    this.gui.jSliderAutoStep.setToolTipText ( Messages
        .getString ( "PreferencesDialog.AutoStepToolTip" ) ); //$NON-NLS-1$
    // Mouse selection
    this.gui.jLabelMouseSelection.setText ( Messages
        .getString ( "PreferencesDialog.MouseSelection" ) ); //$NON-NLS-1$    
    this.gui.jLabelMouseSelection.setDisplayedMnemonic ( Messages.getString (
        "PreferencesDialog.MouseSelectionMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$           
    this.gui.jComboBoxMouseSelection.setToolTipText ( Messages
        .getString ( "PreferencesDialog.MouseSelectionToolTip" ) ); //$NON-NLS-1$
    // Restore
    this.gui.jGTIButtonRestore.setText ( Messages
        .getString ( "PreferencesDialog.Restore" ) ); //$NON-NLS-1$
    this.gui.jGTIButtonRestore.setMnemonic ( Messages.getString (
        "PreferencesDialog.RestoreMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    this.gui.jGTIButtonRestore.setToolTipText ( Messages
        .getString ( "PreferencesDialog.RestoreToolTip" ) ); //$NON-NLS-1$
    // State
    this.colorItemState.setCaption ( Messages
        .getString ( "Preferences.ColorStateCaption" ) ); //$NON-NLS-1$
    this.colorItemState.setDescription ( Messages
        .getString ( "Preferences.ColorStateDescription" ) ); //$NON-NLS-1$
    // State background
    this.colorItemStateBackground.setCaption ( Messages
        .getString ( "Preferences.ColorStateBackgroundCaption" ) ); //$NON-NLS-1$
    this.colorItemStateBackground.setDescription ( Messages
        .getString ( "Preferences.ColorStateBackgroundDescription" ) ); //$NON-NLS-1$
    // State selected
    this.colorItemStateSelected.setCaption ( Messages
        .getString ( "Preferences.ColorStateSelectedCaption" ) ); //$NON-NLS-1$
    this.colorItemStateSelected.setDescription ( Messages
        .getString ( "Preferences.ColorStateSelectedDescription" ) ); //$NON-NLS-1$
    // State start
    this.colorItemStateStart.setCaption ( Messages
        .getString ( "Preferences.ColorStateStartCaption" ) ); //$NON-NLS-1$
    this.colorItemStateStart.setDescription ( Messages
        .getString ( "Preferences.ColorStateStartDescription" ) ); //$NON-NLS-1$
    // State active
    this.colorItemStateActive.setCaption ( Messages
        .getString ( "Preferences.ColorStateActiveCaption" ) ); //$NON-NLS-1$
    this.colorItemStateActive.setDescription ( Messages
        .getString ( "Preferences.ColorStateActiveDescription" ) ); //$NON-NLS-1$
    // State error
    this.colorItemStateError.setCaption ( Messages
        .getString ( "Preferences.ColorStateErrorCaption" ) ); //$NON-NLS-1$
    this.colorItemStateError.setDescription ( Messages
        .getString ( "Preferences.ColorStateErrorDescription" ) ); //$NON-NLS-1$
    // Transition
    this.colorItemTransition.setCaption ( Messages
        .getString ( "Preferences.ColorTransitionCaption" ) ); //$NON-NLS-1$
    this.colorItemTransition.setDescription ( Messages
        .getString ( "Preferences.ColorTransitionDescription" ) ); //$NON-NLS-1$
    // Transition selected
    this.colorItemTransitionSelected.setCaption ( Messages
        .getString ( "Preferences.ColorTransitionSelectedCaption" ) ); //$NON-NLS-1$
    this.colorItemTransitionSelected.setDescription ( Messages
        .getString ( "Preferences.ColorTransitionSelectedDescription" ) ); //$NON-NLS-1$
    // Transition active
    this.colorItemTransitionActive.setCaption ( Messages
        .getString ( "Preferences.ColorTransitionActiveCaption" ) ); //$NON-NLS-1$
    this.colorItemTransitionActive.setDescription ( Messages
        .getString ( "Preferences.ColorTransitionActiveDescription" ) ); //$NON-NLS-1$
    // Transition error
    this.colorItemTransitionError.setCaption ( Messages
        .getString ( "Preferences.ColorTransitionErrorCaption" ) ); //$NON-NLS-1$
    this.colorItemTransitionError.setDescription ( Messages
        .getString ( "Preferences.ColorTransitionErrorDescription" ) ); //$NON-NLS-1$
    // Symbol
    this.colorItemSymbol.setCaption ( Messages
        .getString ( "Preferences.ColorSymbolCaption" ) ); //$NON-NLS-1$
    this.colorItemSymbol.setDescription ( Messages
        .getString ( "Preferences.ColorSymbolDescription" ) ); //$NON-NLS-1$
    // Symbol active
    this.colorItemSymbolActive.setCaption ( Messages
        .getString ( "Preferences.ColorSymbolActiveCaption" ) ); //$NON-NLS-1$
    this.colorItemSymbolActive.setDescription ( Messages
        .getString ( "Preferences.ColorSymbolActiveDescription" ) ); //$NON-NLS-1$
    // Symbol error
    this.colorItemSymbolError.setCaption ( Messages
        .getString ( "Preferences.ColorSymbolErrorCaption" ) ); //$NON-NLS-1$
    this.colorItemSymbolError.setDescription ( Messages
        .getString ( "Preferences.ColorSymbolErrorDescription" ) ); //$NON-NLS-1$
    // Parser warning
    this.colorItemParserWarning.setCaption ( Messages
        .getString ( "Preferences.ColorParserWarningCaption" ) ); //$NON-NLS-1$
    this.colorItemParserWarning.setDescription ( Messages
        .getString ( "Preferences.ColorParserWarningDescription" ) ); //$NON-NLS-1$
    // Parser highlighting
    this.colorItemParserHighlighting.setCaption ( Messages
        .getString ( "Preferences.ColorParserHighlightingCaption" ) ); //$NON-NLS-1$
    this.colorItemParserHighlighting.setDescription ( Messages
        .getString ( "Preferences.ColorParserHighlightingDescription" ) ); //$NON-NLS-1$
    // Color tree
    this.stateNode.setUserObject ( Messages
        .getString ( "PreferencesDialog.ColorTreeState" ) ); //$NON-NLS-1$
    this.transitionNode.setUserObject ( Messages
        .getString ( "PreferencesDialog.ColorTreeTransition" ) ); //$NON-NLS-1$
    this.symbolNode.setUserObject ( Messages
        .getString ( "PreferencesDialog.ColorTreeSymbol" ) ); //$NON-NLS-1$
    this.parserNode.setUserObject ( Messages
        .getString ( "PreferencesDialog.ColorTreeParser" ) ); //$NON-NLS-1$
    nodeChanged ( this.rootNode );
  }


  /**
   * Calls the node changed method on the given node and all child nodes.
   * 
   * @param node The changed node.
   */
  private final void nodeChanged ( TreeNode node )
  {
    ( ( DefaultTreeModel ) this.gui.jTreeColors.getModel () )
        .nodeChanged ( node );
    for ( int i = 0 ; i < node.getChildCount () ; i++ )
    {
      nodeChanged ( node.getChildAt ( i ) );
    }
  }


  /**
   * Saves the data.
   */
  private final void save ()
  {
    logger.debug ( "save data" ); //$NON-NLS-1$
    /*
     * General
     */
    saveLanguage ();
    saveLookAndFeel ();
    saveZoomFactor ();
    /*
     * View
     */
    saveTransition ();
    saveMouseSelection ();
    saveAutoStep ();
    /*
     * Color
     */
    saveColor ();
    /*
     * Alphabet
     */
    saveAlphabet ();
    savePushDownAlphabet ();
    /*
     * Grammar
     */
    saveNonterminalSymbolSet ();
    saveTerminalSymbolSet ();
    /*
     * Tab
     */
    saveLastActiveTab ();
  }


  /**
   * Saves the data of the {@link AlphabetItem}.
   */
  private final void saveAlphabet ()
  {
    if ( !this.initialAlphabetItem.equals ( this.alphabetItem ) )
    {
      logger.debug ( "alphabet changed to \"" //$NON-NLS-1$
          + this.alphabetItem.getAlphabet () + "\"" ); //$NON-NLS-1$
      this.initialAlphabetItem = this.alphabetItem.clone ();
      PreferenceManager.getInstance ().setAlphabetItem ( this.alphabetItem );
    }
  }


  /**
   * Saves the data of the auto step .
   */
  private final void saveAutoStep ()
  {
    if ( this.initialAutoStepItem.getAutoStepInterval () != this.gui.jSliderAutoStep
        .getValue () )
    {
      logger.debug ( "auto step changed to \"" //$NON-NLS-1$
          + this.gui.jSliderAutoStep.getValue () + "\"" ); //$NON-NLS-1$
      this.initialAutoStepItem = AutoStepItem.create ( this.gui.jSliderAutoStep
          .getValue () );
      PreferenceManager.getInstance ().setAutoStepItem (
          this.initialAutoStepItem );
    }
  }


  /**
   * Saves the data of the color.
   */
  private final void saveColor ()
  {
    // State
    if ( !this.initialColorItemState.getColor ().equals (
        this.colorItemState.getColor () ) )
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
    // State background
    if ( !this.initialColorItemStateBackground.getColor ().equals (
        this.colorItemStateBackground.getColor () ) )
    {
      logger.debug ( "color of the state background changed to \"" //$NON-NLS-1$
          + "r=" + this.colorItemStateBackground.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "g=" + this.colorItemStateBackground.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "b=" + this.colorItemStateBackground.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
      this.initialColorItemStateBackground = this.colorItemStateBackground
          .clone ();
      PreferenceManager.getInstance ().setColorItemStateBackground (
          this.colorItemStateBackground );
      PreferenceManager.getInstance ().fireColorChangedStateBackground (
          this.colorItemStateBackground.getColor () );
    }
    // State selected
    if ( !this.initialColorItemStateSelected.getColor ().equals (
        this.colorItemStateSelected.getColor () ) )
    {
      logger.debug ( "color of the selected state changed to \"" //$NON-NLS-1$
          + "r=" + this.colorItemStateSelected.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "g=" + this.colorItemStateSelected.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "b=" + this.colorItemStateSelected.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
      this.initialColorItemStateSelected = this.colorItemStateSelected.clone ();
      PreferenceManager.getInstance ().setColorItemStateSelected (
          this.colorItemStateSelected );
      PreferenceManager.getInstance ().fireColorChangedStateSelected (
          this.colorItemStateSelected.getColor () );
    }
    // State start
    if ( !this.initialColorItemStateStart.getColor ().equals (
        this.colorItemStateStart.getColor () ) )
    {
      logger.debug ( "color of the start state changed to \"" //$NON-NLS-1$
          + "r=" + this.colorItemStateStart.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "g=" + this.colorItemStateStart.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "b=" + this.colorItemStateStart.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
      this.initialColorItemStateStart = this.colorItemStateStart.clone ();
      PreferenceManager.getInstance ().setColorItemStateStart (
          this.colorItemStateStart );
      PreferenceManager.getInstance ().fireColorChangedStateStart (
          this.colorItemStateStart.getColor () );
    }
    // State active
    if ( !this.initialColorItemStateActive.getColor ().equals (
        this.colorItemStateActive.getColor () ) )
    {
      logger.debug ( "color of the active state changed to \"" //$NON-NLS-1$
          + "r=" + this.colorItemStateActive.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "g=" + this.colorItemStateActive.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "b=" + this.colorItemStateActive.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
      this.initialColorItemStateActive = this.colorItemStateActive.clone ();
      PreferenceManager.getInstance ().setColorItemStateActive (
          this.colorItemStateActive );
      PreferenceManager.getInstance ().fireColorChangedStateActive (
          this.colorItemStateActive.getColor () );
    }
    // State error
    if ( !this.initialColorItemStateError.getColor ().equals (
        this.colorItemStateError.getColor () ) )
    {
      logger.debug ( "color of the error state changed to \"" //$NON-NLS-1$
          + "r=" + this.colorItemStateError.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "g=" + this.colorItemStateError.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "b=" + this.colorItemStateError.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
      this.initialColorItemStateError = this.colorItemStateError.clone ();
      PreferenceManager.getInstance ().setColorItemStateError (
          this.colorItemStateError );
      PreferenceManager.getInstance ().fireColorChangedStateError (
          this.colorItemStateError.getColor () );
    }

    // Transition
    if ( !this.initialColorItemTransition.getColor ().equals (
        this.colorItemTransition.getColor () ) )
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
    // Transition selected
    if ( !this.initialColorItemTransitionSelected.getColor ().equals (
        this.colorItemTransitionSelected.getColor () ) )
    {
      logger
          .debug ( "color of the selected transition changed to \"" //$NON-NLS-1$
              + "r=" + this.colorItemTransitionSelected.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
              + "g=" + this.colorItemTransitionSelected.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
              + "b=" + this.colorItemTransitionSelected.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
      this.initialColorItemTransitionSelected = this.colorItemTransitionSelected
          .clone ();
      PreferenceManager.getInstance ().setColorItemTransitionSelected (
          this.colorItemTransitionSelected );
      PreferenceManager.getInstance ().fireColorChangedTransitionSelected (
          this.colorItemTransitionSelected.getColor () );
    }
    // Transition active
    if ( !this.initialColorItemTransitionActive.getColor ().equals (
        this.colorItemTransitionActive.getColor () ) )
    {
      logger
          .debug ( "color of the active transition changed to \"" //$NON-NLS-1$
              + "r=" + this.colorItemTransitionActive.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
              + "g=" + this.colorItemTransitionActive.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
              + "b=" + this.colorItemTransitionActive.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
      this.initialColorItemTransitionActive = this.colorItemTransitionActive
          .clone ();
      PreferenceManager.getInstance ().setColorItemTransitionActive (
          this.colorItemTransitionActive );
      PreferenceManager.getInstance ().fireColorChangedTransitionActive (
          this.colorItemTransitionActive.getColor () );
    }
    // Transition error
    if ( !this.initialColorItemTransitionError.getColor ().equals (
        this.colorItemTransitionError.getColor () ) )
    {
      logger.debug ( "color of the error transition changed to \"" //$NON-NLS-1$
          + "r=" + this.colorItemTransitionError.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "g=" + this.colorItemTransitionError.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "b=" + this.colorItemTransitionError.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
      this.initialColorItemTransitionError = this.colorItemTransitionError
          .clone ();
      PreferenceManager.getInstance ().setColorItemTransitionError (
          this.colorItemTransitionError );
      PreferenceManager.getInstance ().fireColorChangedTransitionError (
          this.colorItemTransitionError.getColor () );
    }

    // Symbol
    if ( !this.initialColorItemSymbol.getColor ().equals (
        this.colorItemSymbol.getColor () ) )
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
    // Symbol active
    if ( !this.initialColorItemSymbolActive.getColor ().equals (
        this.colorItemSymbolActive.getColor () ) )
    {
      logger.debug ( "color of the active symbol changed to \"" //$NON-NLS-1$
          + "r=" + this.colorItemSymbolActive.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "g=" + this.colorItemSymbolActive.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "b=" + this.colorItemSymbolActive.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
      this.initialColorItemSymbolActive = this.colorItemSymbolActive.clone ();
      PreferenceManager.getInstance ().setColorItemSymbolActive (
          this.colorItemSymbolActive );
      PreferenceManager.getInstance ().fireColorChangedSymbolActive (
          this.colorItemSymbolActive.getColor () );
    }
    // Symbol error
    if ( !this.initialColorItemSymbolError.getColor ().equals (
        this.colorItemSymbolError.getColor () ) )
    {
      logger.debug ( "color of the error symbol changed to \"" //$NON-NLS-1$
          + "r=" + this.colorItemSymbolError.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "g=" + this.colorItemSymbolError.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "b=" + this.colorItemSymbolError.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
      this.initialColorItemSymbolError = this.colorItemSymbolError.clone ();
      PreferenceManager.getInstance ().setColorItemSymbolError (
          this.colorItemSymbolError );
      PreferenceManager.getInstance ().fireColorChangedSymbolError (
          this.colorItemSymbolError.getColor () );
    }

    // Parser warning
    if ( !this.initialColorItemParserWarning.getColor ().equals (
        this.colorItemParserWarning.getColor () ) )
    {
      logger.debug ( "color of the parser warning changed to \"" //$NON-NLS-1$
          + "r=" + this.colorItemParserWarning.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "g=" + this.colorItemParserWarning.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
          + "b=" + this.colorItemParserWarning.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
      this.initialColorItemParserWarning = this.colorItemParserWarning.clone ();
      PreferenceManager.getInstance ().setColorItemParserWarning (
          this.colorItemParserWarning );
      PreferenceManager.getInstance ().fireColorChangedParserWarning (
          this.colorItemParserWarning.getColor () );
    }
    // Parser highlighting
    if ( !this.initialColorItemParserHighlighting.getColor ().equals (
        this.colorItemParserHighlighting.getColor () ) )
    {
      logger
          .debug ( "color of the parser highlighting changed to \"" //$NON-NLS-1$
              + "r=" + this.colorItemParserHighlighting.getColor ().getRed () + ", " //$NON-NLS-1$ //$NON-NLS-2$
              + "g=" + this.colorItemParserHighlighting.getColor ().getGreen () + ", " //$NON-NLS-1$ //$NON-NLS-2$
              + "b=" + this.colorItemParserHighlighting.getColor ().getBlue () + "\"" ); //$NON-NLS-1$ //$NON-NLS-2$
      this.initialColorItemParserHighlighting = this.colorItemParserHighlighting
          .clone ();
      PreferenceManager.getInstance ().setColorItemParserHighlighting (
          this.colorItemParserHighlighting );
      PreferenceManager.getInstance ().fireColorChangedParserHighlighting (
          this.colorItemParserHighlighting.getColor () );
    }
  }


  /**
   * Saves the data of the language.
   */
  private final void saveLanguage ()
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
  private final void saveLastActiveTab ()
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
  private final void saveLookAndFeel ()
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
            {
              SwingUtilities.updateComponentTreeUI ( w );
            }
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
   * Saves the data of the mouse selection.
   */
  private final void saveMouseSelection ()
  {
    if ( this.initialMouseSelectionItem.getIndex () != this.gui.jComboBoxMouseSelection
        .getSelectedIndex () )
    {
      logger.debug ( "mouse selection item changed to \"" //$NON-NLS-1$
          + this.gui.jComboBoxMouseSelection.getSelectedIndex () + "\"" ); //$NON-NLS-1$
      this.initialMouseSelectionItem = MouseSelectionItem
          .create ( this.gui.jComboBoxMouseSelection.getSelectedIndex () );
      PreferenceManager.getInstance ().setMouseSelectionItem (
          this.initialMouseSelectionItem );
    }
  }


  /**
   * Saves the data of the {@link NonterminalSymbolSetItem}.
   */
  private final void saveNonterminalSymbolSet ()
  {
    if ( !this.initialNonterminalSymbolSetItem
        .equals ( this.nonterminalSymbolSetItem ) )
    {
      logger.debug ( "nonterminal symbol set changed to \"" //$NON-NLS-1$
          + this.nonterminalSymbolSetItem.getNonterminalSymbolSet () + "\"" ); //$NON-NLS-1$
      this.initialNonterminalSymbolSetItem = this.nonterminalSymbolSetItem
          .clone ();
      PreferenceManager.getInstance ().setNonterminalSymbolSetItem (
          this.nonterminalSymbolSetItem );
    }
  }


  /**
   * Saves the data of the push down {@link AlphabetItem}.
   */
  private final void savePushDownAlphabet ()
  {
    if ( !this.initialPushDownAlphabetItem.equals ( this.pushDownAlphabetItem ) )
    {
      logger.debug ( "push down alphabet changed to \"" //$NON-NLS-1$
          + this.pushDownAlphabetItem.getAlphabet () + "\"" ); //$NON-NLS-1$
      this.initialPushDownAlphabetItem = this.pushDownAlphabetItem.clone ();
      PreferenceManager.getInstance ().setPushDownAlphabetItem (
          this.pushDownAlphabetItem );
    }
    if ( this.initialUsePushDownAlphabet != this.gui.alphabetPanelForm.jCheckBoxPushDownAlphabet
        .isSelected () )
    {
      logger.debug ( "use push down alphabet changed to \"" //$NON-NLS-1$
          + this.gui.alphabetPanelForm.jCheckBoxPushDownAlphabet.isSelected ()
          + "\"" ); //$NON-NLS-1$
      this.initialUsePushDownAlphabet = this.gui.alphabetPanelForm.jCheckBoxPushDownAlphabet
          .isSelected ();
      PreferenceManager.getInstance ().setUsePushDownAlphabet (
          this.gui.alphabetPanelForm.jCheckBoxPushDownAlphabet.isSelected () );
    }
  }


  /**
   * Saves the data of the {@link TerminalSymbolSetItem}.
   */
  private final void saveTerminalSymbolSet ()
  {
    if ( !this.initialTerminalSymbolSetItem
        .equals ( this.terminalSymbolSetItem ) )
    {
      logger.debug ( "terminal symbol set changed to \"" //$NON-NLS-1$
          + this.terminalSymbolSetItem.getTerminalSymbolSet () + "\"" ); //$NON-NLS-1$
      this.initialTerminalSymbolSetItem = this.terminalSymbolSetItem.clone ();
      PreferenceManager.getInstance ().setTerminalSymbolSetItem (
          this.terminalSymbolSetItem );
    }
  }


  /**
   * Saves the data of the {@link Transition}.
   */
  private final void saveTransition ()
  {
    if ( this.initialTransitionItem.getIndex () != this.gui.jComboBoxTransition
        .getSelectedIndex () )
    {
      logger.debug ( "transition item changed to \"" //$NON-NLS-1$
          + this.gui.jComboBoxTransition.getSelectedIndex () + "\"" ); //$NON-NLS-1$
      this.initialTransitionItem = TransitionItem
          .create ( this.gui.jComboBoxTransition.getSelectedIndex () );
      PreferenceManager.getInstance ().setTransitionItem (
          this.initialTransitionItem );
    }
  }


  /**
   * Saves the data of the zoom factor.
   */
  private final void saveZoomFactor ()
  {
    if ( this.initialZoomFactorItem.getFactor () != this.gui.jSliderZoom
        .getValue () )
    {
      logger.debug ( "zoom factor changed to \"" //$NON-NLS-1$
          + this.gui.jSliderZoom.getValue () + "\"" ); //$NON-NLS-1$
      this.initialZoomFactorItem = ZoomFactorItem.create ( this.gui.jSliderZoom
          .getValue () );
      PreferenceManager.getInstance ().setZoomFactorItem (
          this.initialZoomFactorItem );
      PreferenceManager.getInstance ().fireZoomFactorChanged (
          this.initialZoomFactorItem );
    }
  }


  /**
   * Sets the status of the buttons.
   */
  private final void setButtonStatus ()
  {
    boolean enabled = true;

    // Alphabet
    if ( ( this.gui.alphabetPanelForm.styledAlphabetParserPanelInput
        .getAlphabet () == null )
        || ( this.gui.alphabetPanelForm.styledAlphabetParserPanelPushDown
            .getAlphabet () == null ) )
    {
      enabled = false;
      this.gui.jTabbedPane.setForegroundAt ( ALPHABET_TAB_INDEX, Color.RED );
    }
    else
    {
      this.gui.jTabbedPane.setForegroundAt ( ALPHABET_TAB_INDEX, null );
    }

    // Grammar
    if ( ( this.gui.terminalPanelForm.styledNonterminalSymbolSetParserPanel
        .getNonterminalSymbolSet () == null )
        || ( this.gui.terminalPanelForm.styledTerminalSymbolSetParserPanel
            .getTerminalSymbolSet () == null ) )
    {
      enabled = false;
      this.gui.jTabbedPane.setForegroundAt ( GRAMMAR_TAB_INDEX, Color.RED );
    }
    else
    {
      this.gui.jTabbedPane.setForegroundAt ( GRAMMAR_TAB_INDEX, null );
    }

    // Enable or disable the buttons
    this.gui.jGTIButtonOk.setEnabled ( enabled );
    this.gui.jGTIButtonAccept.setEnabled ( enabled );
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
