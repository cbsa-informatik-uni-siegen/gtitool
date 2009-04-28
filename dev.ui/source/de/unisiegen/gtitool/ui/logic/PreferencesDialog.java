package de.unisiegen.gtitool.ui.logic;


import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import de.unisiegen.gtitool.core.entities.Alphabet;
import de.unisiegen.gtitool.core.entities.DefaultRegexAlphabet;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.State;
import de.unisiegen.gtitool.core.entities.Symbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbol;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.core.entities.Word;
import de.unisiegen.gtitool.core.machines.pda.PDA;
import de.unisiegen.gtitool.core.preferences.item.AlphabetItem;
import de.unisiegen.gtitool.core.preferences.item.ColorItem;
import de.unisiegen.gtitool.core.preferences.item.LanguageItem;
import de.unisiegen.gtitool.core.preferences.item.NonterminalSymbolItem;
import de.unisiegen.gtitool.core.preferences.item.NonterminalSymbolSetItem;
import de.unisiegen.gtitool.core.preferences.item.TerminalSymbolSetItem;
import de.unisiegen.gtitool.core.preferences.listener.LanguageChangedListener;
import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.netbeans.PreferencesDialogForm;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.preferences.item.AutoStepItem;
import de.unisiegen.gtitool.ui.preferences.item.LookAndFeelItem;
import de.unisiegen.gtitool.ui.preferences.item.MouseSelectionItem;
import de.unisiegen.gtitool.ui.preferences.item.PDAModeItem;
import de.unisiegen.gtitool.ui.preferences.item.TransitionItem;
import de.unisiegen.gtitool.ui.preferences.item.WordModeItem;
import de.unisiegen.gtitool.ui.preferences.item.ZoomFactorItem;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;


/**
 * The {@link PreferencesDialog}.
 * 
 * @author Christian Fehler
 * @version $Id: PreferencesDialog.java 1169 2008-07-18 00:20:56Z fehler $
 */
public final class PreferencesDialog implements
    LogicClass < PreferencesDialogForm >, LanguageChangedListener
{

  /**
   * The {@link Color} tree cell renderer.
   * 
   * @author Christian Fehler
   */
  protected final class ColorTreeCellRenderer extends DefaultTreeCellRenderer
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
        @SuppressWarnings ( "unused" ) boolean focus )
    {
      super.getTreeCellRendererComponent ( tree, value, sel, expanded, leaf,
          row, sel );
      ColorItem colorItem = ( ColorItem ) value;
      if ( leaf )
      {
        setIcon ( colorItem.getIcon () );
        setToolTipText ( colorItem.getDescription () );
      }
      else
      {
        setIcon ( null );
        setToolTipText ( colorItem.getDescription () );
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
    public final void addElement ( @SuppressWarnings ( "unused" ) Object object )
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
    public final void addElement ( @SuppressWarnings ( "unused" ) Object object )
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
    public final void addElement ( @SuppressWarnings ( "unused" ) Object object )
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
   * The {@link PDA} mode {@link ComboBoxModel}.
   * 
   * @author Christian Fehler
   */
  protected final class PDAModeComboBoxModel extends DefaultComboBoxModel
  {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 3629792679640859661L;


    /**
     * {@inheritDoc}
     * 
     * @see DefaultComboBoxModel#addElement(Object)
     */
    @Override
    public final void addElement ( @SuppressWarnings ( "unused" ) Object object )
    {
      throw new IllegalArgumentException ( "do not use this method" ); //$NON-NLS-1$
    }


    /**
     * Adds the given item.
     * 
     * @param pdaModeItem The {@link PDAModeItem} to add.
     */
    public final void addElement ( PDAModeItem pdaModeItem )
    {
      super.addElement ( pdaModeItem );
    }


    /**
     * {@inheritDoc}
     * 
     * @see DefaultComboBoxModel#getElementAt(int)
     */
    @Override
    public final PDAModeItem getElementAt ( int index )
    {
      return ( PDAModeItem ) super.getElementAt ( index );
    }


    /**
     * {@inheritDoc}
     * 
     * @see DefaultComboBoxModel#getSelectedItem()
     */
    @Override
    public final PDAModeItem getSelectedItem ()
    {
      return ( PDAModeItem ) super.getSelectedItem ();
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
    public final void addElement ( @SuppressWarnings ( "unused" ) Object object )
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
   * The {@link Word} mode {@link ComboBoxModel}.
   * 
   * @author Christian Fehler
   */
  protected final class WordModeComboBoxModel extends DefaultComboBoxModel
  {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = -8664380439078177232L;


    /**
     * {@inheritDoc}
     * 
     * @see DefaultComboBoxModel#addElement(Object)
     */
    @Override
    public final void addElement ( @SuppressWarnings ( "unused" ) Object object )
    {
      throw new IllegalArgumentException ( "do not use this method" ); //$NON-NLS-1$
    }


    /**
     * Adds the given item.
     * 
     * @param wordModeItem The {@link WordModeItem} to add.
     */
    public final void addElement ( WordModeItem wordModeItem )
    {
      super.addElement ( wordModeItem );
    }


    /**
     * {@inheritDoc}
     * 
     * @see DefaultComboBoxModel#getElementAt(int)
     */
    @Override
    public final WordModeItem getElementAt ( int index )
    {
      return ( WordModeItem ) super.getElementAt ( index );
    }


    /**
     * {@inheritDoc}
     * 
     * @see DefaultComboBoxModel#getSelectedItem()
     */
    @Override
    public final WordModeItem getSelectedItem ()
    {
      return ( WordModeItem ) super.getSelectedItem ();
    }
  }


  /**
   * The index of the alphabet tab.
   */
  private static final int ALPHABET_TAB_INDEX = 3;


  /**
   * The index of the colors tab.
   */
  private static final int COLOR_TAB_INDEX = 2;


  /**
   * The index of the general tab.
   */
  private static final int GENERAL_TAB_INDEX = 0;


  /**
   * The index of the grammar tab.
   */
  private static final int GRAMMAR_TAB_INDEX = 4;


  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger
      .getLogger ( PreferencesDialog.class );


  /**
   * The index of the view tab.
   */
  private static final int VIEW_TAB_INDEX = 1;


  /**
   * The {@link AlphabetItem}.
   */
  private AlphabetItem alphabetItem;


  /**
   * The {@link AlphabetItem}.
   */
  private AlphabetItem regexAlphabetItem;


  /**
   * The {@link ColorItem} of the parser {@link NonterminalSymbol}.
   */
  private ColorItem colorItemNonterminalSymbol;


  /**
   * The {@link ColorItem} of the error {@link NonterminalSymbol}.
   */
  private ColorItem colorItemNonterminalSymbolError;


  /**
   * The {@link ColorItem} of the highlight {@link NonterminalSymbol}.
   */
  private ColorItem colorItemNonterminalSymbolHighlight;


  /**
   * The {@link ColorItem} of the parser error.
   */
  private ColorItem colorItemParserError;


  /**
   * The {@link ColorItem} of the parser highlighting.
   */
  private ColorItem colorItemParserHighlighting;


  /**
   * The {@link ColorItem} of the parser keyword.
   */
  private ColorItem colorItemParserKeyword;


  /**
   * The {@link ColorItem} of the parser warning.
   */
  private ColorItem colorItemParserWarning;


  /**
   * The {@link ColorItem} of the error {@link Production}.
   */
  private ColorItem colorItemProductionError;


  /**
   * The {@link ColorItem} of the highlight {@link Production}.
   */
  private ColorItem colorItemProductionHighlight;


  /**
   * The {@link ColorItem} of the regex.
   */
  private ColorItem colorItemRegex;


  /**
   * The {@link ColorItem} of the regex position.
   */
  private ColorItem colorItemRegexPosition;


  /**
   * The {@link ColorItem} of the regex symbol.
   */
  private ColorItem colorItemRegexSymbol;


  /**
   * The {@link ColorItem} of the regex token.
   */
  private ColorItem colorItemRegexToken;


  /**
   * The {@link ColorItem} of the regex token.
   */
  private ColorItem colorItemRegexNode;


  /**
   * The {@link ColorItem} of the regex token.
   */
  private ColorItem colorItemRegexMarkedNode;


  /**
   * The {@link ColorItem} of the regex token.
   */
  private ColorItem colorItemRegexSelectedNode;


  /**
   * The {@link ColorItem} of the regex tool tip.
   */
  private ColorItem colorItemRegexToolTip;


  /**
   * The {@link ColorItem} of the regex tool tip.
   */
  private ColorItem colorItemRegexComment;


  /**
   * The {@link ColorItem} of the parser start {@link NonterminalSymbol}.
   */
  private ColorItem colorItemStartNonterminalSymbol;


  /**
   * The {@link ColorItem} of the parser {@link State}.
   */
  private ColorItem colorItemState;


  /**
   * The {@link ColorItem} of the active {@link State}.
   */
  private ColorItem colorItemStateActive;


  /**
   * The {@link ColorItem} of the {@link State}.
   */
  private ColorItem colorItemStateBackground;


  /**
   * The {@link ColorItem} of the error {@link State}.
   */
  private ColorItem colorItemStateError;


  /**
   * The {@link ColorItem} of the final {@link State}.
   */
  private ColorItem colorItemStateFinal;


  /**
   * The {@link ColorItem} of the selected {@link State}.
   */
  private ColorItem colorItemStateSelected;


  /**
   * The {@link ColorItem} of the start {@link State}.
   */
  private ColorItem colorItemStateStart;


  /**
   * The {@link ColorItem} of the parser {@link Symbol}.
   */
  private ColorItem colorItemSymbol;


  /**
   * The {@link ColorItem} of the active {@link Symbol}.
   */
  private ColorItem colorItemSymbolActive;


  /**
   * The {@link ColorItem} of the error {@link Symbol}.
   */
  private ColorItem colorItemSymbolError;


  /**
   * The {@link ColorItem} of the parser {@link TerminalSymbol}.
   */
  private ColorItem colorItemTerminalSymbol;


  /**
   * The {@link ColorItem} of the error {@link TerminalSymbol}.
   */
  private ColorItem colorItemTerminalSymbolError;


  /**
   * The {@link ColorItem} of the {@link Transition}.
   */
  private ColorItem colorItemTransition;


  /**
   * The {@link ColorItem} of the active {@link Transition}.
   */
  private ColorItem colorItemTransitionActive;


  /**
   * The {@link ColorItem} of the error {@link Transition}.
   */
  private ColorItem colorItemTransitionError;


  /**
   * The {@link ColorItem} of the selected {@link Transition}.
   */
  private ColorItem colorItemTransitionSelected;


  /**
   * The {@link PreferencesDialogForm}.
   */
  private PreferencesDialogForm gui;


  /**
   * The initial {@link AlphabetItem}.
   */
  private AlphabetItem initialAlphabetItem;


  /**
   * The initial {@link AlphabetItem}.
   */
  private AlphabetItem initialRegexAlphabetItem;


  /**
   * The initial {@link AutoStepItem}.
   */
  private AutoStepItem initialAutoStepItem;


  /**
   * The initial {@link ColorItem} of the parser {@link NonterminalSymbol}.
   */
  private ColorItem initialColorItemNonterminalSymbol;


  /**
   * The initial {@link ColorItem} of the error {@link NonterminalSymbol}.
   */
  private ColorItem initialColorItemNonterminalSymbolError;


  /**
   * The initial {@link ColorItem} of the error {@link NonterminalSymbol}.
   */
  private ColorItem initialColorItemNonterminalSymbolHighlight;


  /**
   * The initial {@link ColorItem} of the parser error.
   */
  private ColorItem initialColorItemParserError;


  /**
   * The initial {@link ColorItem} of the parser highlighting.
   */
  private ColorItem initialColorItemParserHighlighting;


  /**
   * The initial {@link ColorItem} of the parser keyword.
   */
  private ColorItem initialColorItemParserKeyword;


  /**
   * The initial {@link ColorItem} of the parser warning.
   */
  private ColorItem initialColorItemParserWarning;


  /**
   * The initial {@link ColorItem} of the error {@link Production}.
   */
  private ColorItem initialColorItemProductionError;


  /**
   * The initial {@link ColorItem} of the highlight {@link Production}.
   */
  private ColorItem initialColorItemProductionHighlight;


  /**
   * The initial {@link ColorItem} of the Regex position.
   */
  private ColorItem initialColorItemRegexPosition;


  /**
   * The initial {@link ColorItem} of the Regex symbol.
   */
  private ColorItem initialColorItemRegexSymbol;


  /**
   * The initial {@link ColorItem} of the Regex token.
   */
  private ColorItem initialColorItemRegexToken;


  /**
   * The initial {@link ColorItem} of the Regex tool tip.
   */
  private ColorItem initialColorItemRegexToolTip;


  /**
   * The initial {@link ColorItem} of the Regex tool tip.
   */
  private ColorItem initialColorItemRegexNode;


  /**
   * The initial {@link ColorItem} of the Regex tool tip.
   */
  private ColorItem initialColorItemRegexMarkedNode;


  /**
   * The initial {@link ColorItem} of the Regex tool tip.
   */
  private ColorItem initialColorItemRegexSelectedNode;


  /**
   * The initial {@link ColorItem} of the Regex tool tip.
   */
  private ColorItem initialColorItemRegexComment;


  /**
   * The initial {@link ColorItem} of the parser start {@link NonterminalSymbol}
   * .
   */
  private ColorItem initialColorItemStartNonterminalSymbol;


  /**
   * The initial {@link ColorItem} of the parser {@link State}.
   */
  private ColorItem initialColorItemState;


  /**
   * The initial {@link ColorItem} of the active {@link State}.
   */
  private ColorItem initialColorItemStateActive;


  /**
   * The initial {@link ColorItem} of the {@link State}.
   */
  private ColorItem initialColorItemStateBackground;


  /**
   * The initial {@link ColorItem} of the error {@link State}.
   */
  private ColorItem initialColorItemStateError;


  /**
   * The initial {@link ColorItem} of the final {@link State}.
   */
  private ColorItem initialColorItemStateFinal;


  /**
   * The initial {@link ColorItem} of the selected {@link State}.
   */
  private ColorItem initialColorItemStateSelected;


  /**
   * The initial {@link ColorItem} of the start {@link State}.
   */
  private ColorItem initialColorItemStateStart;


  /**
   * The initial {@link ColorItem} of the parser {@link Symbol}.
   */
  private ColorItem initialColorItemSymbol;


  /**
   * The initial {@link ColorItem} of the active {@link Symbol}.
   */
  private ColorItem initialColorItemSymbolActive;


  /**
   * The initial {@link ColorItem} of the error {@link Symbol}.
   */
  private ColorItem initialColorItemSymbolError;


  /**
   * The initial {@link ColorItem} of the parser {@link TerminalSymbol}.
   */
  private ColorItem initialColorItemTerminalSymbol;


  /**
   * The initial {@link ColorItem} of the error {@link TerminalSymbol}.
   */
  private ColorItem initialColorItemTerminalSymbolError;


  /**
   * The initial {@link ColorItem} of the {@link Transition}.
   */
  private ColorItem initialColorItemTransition;


  /**
   * The initial {@link ColorItem} of the active {@link Transition}.
   */
  private ColorItem initialColorItemTransitionActive;


  /**
   * The initial {@link ColorItem} of the error {@link Transition}.
   */
  private ColorItem initialColorItemTransitionError;


  /**
   * The initial {@link ColorItem} of the selected {@link Transition}.
   */
  private ColorItem initialColorItemTransitionSelected;


  /**
   * The initial {@link LanguageItem}.
   */
  private LanguageItem initialLanguageItem;


  /**
   * The initial last active tab.
   */
  private int initialLastActiveTab;


  /**
   * The initial {@link LookAndFeelItem}.
   */
  private LookAndFeelItem initialLookAndFeel;


  /**
   * The initial {@link MouseSelectionItem}.
   */
  private MouseSelectionItem initialMouseSelectionItem;


  /**
   * The initial {@link NonterminalSymbolSetItem}.
   */
  private NonterminalSymbolSetItem initialNonterminalSymbolSetItem;


  /**
   * The initial {@link PDAModeItem}.
   */
  private PDAModeItem initialPDAModeItem;


  /**
   * The initial push down {@link AlphabetItem}.
   */
  private AlphabetItem initialPushDownAlphabetItem;


  /**
   * The initial start {@link NonterminalSymbolItem}.
   */
  private NonterminalSymbolItem initialStartSymbolItem;


  /**
   * The initial {@link TerminalSymbolSetItem}.
   */
  private TerminalSymbolSetItem initialTerminalSymbolSetItem;


  /**
   * The initial {@link MouseSelectionItem}.
   */
  private TransitionItem initialTransitionItem;


  /**
   * The initial use push down {@link Alphabet}.
   */
  private boolean initialUsePushDownAlphabet;


  /**
   * The initial {@link WordModeItem}.
   */
  private WordModeItem initialWordModeItem;


  /**
   * The initial {@link ZoomFactorItem}.
   */
  private ZoomFactorItem initialZoomFactorItem;


  /**
   * The auto interval {@link JPopupMenu}.
   */
  private JPopupMenu jPopupMenuAutoStep;


  /**
   * The color list {@link JPopupMenu}.
   */
  private JPopupMenu jPopupMenuColorList;


  /**
   * The language {@link JPopupMenu}.
   */
  private JPopupMenu jPopupMenuLanguage;


  /**
   * The look and feel {@link JPopupMenu}.
   */
  private JPopupMenu jPopupMenuLookAndFeel;


  /**
   * The mouse selection {@link JPopupMenu}.
   */
  private JPopupMenu jPopupMenuMouseSelection;


  /**
   * The {@link PDA} mode {@link JPopupMenu}.
   */
  private JPopupMenu jPopupMenuPDAMode;


  /**
   * The {@link Transition} {@link JPopupMenu}.
   */
  private JPopupMenu jPopupMenuTransition;


  /**
   * The push down {@link Alphabet} {@link JPopupMenu}.
   */
  private JPopupMenu jPopupMenuUsePushDownAlphabet;


  /**
   * The {@link Word} mode {@link JPopupMenu}.
   */
  private JPopupMenu jPopupMenuWordMode;


  /**
   * The zoom factor {@link JPopupMenu}.
   */
  private JPopupMenu jPopupMenuZoomFactor;


  /**
   * The {@link LanguageComboBoxModel}.
   */
  private LanguageComboBoxModel languageComboBoxModel;


  /**
   * The {@link DefaultComboBoxModel} of the languages.
   */
  private LookAndFeelComboBoxModel lookAndFeelComboBoxModel;


  /**
   * The {@link MouseSelectionComboBoxModel}.
   */
  private MouseSelectionComboBoxModel mouseSelectionComboBoxModel;


  /**
   * The color tree {@link NonterminalSymbol} node.
   */
  private ColorItem nonterminalSymbolNode;


  /**
   * The {@link NonterminalSymbolSetItem}.
   */
  private NonterminalSymbolSetItem nonterminalSymbolSetItem;


  /**
   * The parent {@link JFrame}.
   */
  private JFrame parent;


  /**
   * The color tree parser node.
   */
  private ColorItem parserNode;


  /**
   * The {@link PDAModeComboBoxModel}.
   */
  private PDAModeComboBoxModel pdaModeComboBoxModel;


  /**
   * The color tree {@link Production} node.
   */
  private ColorItem productionNode;


  /**
   * The push down {@link AlphabetItem}.
   */
  private AlphabetItem pushDownAlphabetItem;


  /**
   * The color tree Regex node.
   */
  private ColorItem regexNode;


  /**
   * The color tree root node.
   */
  private ColorItem rootNode;


  /**
   * The start {@link NonterminalSymbolItem}.
   */
  private NonterminalSymbolItem startSymbolItem;


  /**
   * The color tree {@link State} node.
   */
  private ColorItem stateNode;


  /**
   * The color tree {@link Symbol} node.
   */
  private ColorItem symbolNode;


  /**
   * The color tree {@link TerminalSymbol} node.
   */
  private ColorItem terminalSymbolNode;


  /**
   * The {@link TerminalSymbolSetItem}.
   */
  private TerminalSymbolSetItem terminalSymbolSetItem;


  /**
   * The {@link TransitionComboBoxModel}.
   */
  private TransitionComboBoxModel transitionComboBoxModel;


  /**
   * The color tree {@link Transition} node.
   */
  private ColorItem transitionNode;


  /**
   * The {@link WordModeComboBoxModel}.
   */
  private WordModeComboBoxModel wordModeComboBoxModel;


  /**
   * Allocates a new {@link PreferencesDialog}.
   * 
   * @param parent The parent {@link JFrame}.
   */
  public PreferencesDialog ( JFrame parent )
  {
    logger.debug ( "PreferencesDialog", "allocate a new preferences dialog" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.parent = parent;
    this.gui = new PreferencesDialogForm ( this, parent );
    init ();
    PreferenceManager.getInstance ().addLanguageChangedListener ( this );
  }


  /**
   * {@inheritDoc}
   * 
   * @see LogicClass#getGUI()
   */
  public final PreferencesDialogForm getGUI ()
  {
    return this.gui;
  }


  /**
   * Handles the action on the accept button.
   */
  public final void handleAccept ()
  {
    logger.debug ( "handleAccept", "handle accept" ); //$NON-NLS-1$ //$NON-NLS-2$
    save ();
  }


  /**
   * Handles the action on the cancel button.
   */
  public final void handleCancel ()
  {
    logger.debug ( "handleCancel", "handle cancel" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.gui.setVisible ( false );
    if ( this.initialLastActiveTab != this.gui.jGTITabbedPane
        .getSelectedIndex () )
    {
      this.initialLastActiveTab = this.gui.jGTITabbedPane.getSelectedIndex ();
      PreferenceManager.getInstance ().setPreferencesDialogLastActiveTab (
          this.gui.jGTITabbedPane.getSelectedIndex () );
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
      TreePath selectedPath = this.gui.jGTITreeColors.getSelectionModel ()
          .getSelectionPath ();
      if ( selectedPath != null )
      {
        ColorItem selectedColorItem = ( ColorItem ) selectedPath
            .getLastPathComponent ();
        if ( selectedColorItem.isLeaf () )
        {
          logger.debug ( "handleColorTreeMouseReleased", //$NON-NLS-1$
              "handle color chooser dialog" ); //$NON-NLS-1$
          ColorChooserDialog colorChooserDialog = new ColorChooserDialog (
              this.parent, selectedColorItem.getColor () );
          colorChooserDialog.show ();
          if ( colorChooserDialog.isConfirmed () )
          {
            selectedColorItem.setColor ( colorChooserDialog.getColor () );
            this.gui.jGTITreeColors.repaint ();
          }
        }
      }
    }
  }


  /**
   * Handles the action on the ok button.
   */
  public final void handleOk ()
  {
    logger.debug ( "handleOk", "handle ok" ); //$NON-NLS-1$ //$NON-NLS-2$
    this.gui.setVisible ( false );
    save ();
    this.gui.dispose ();
  }


  /**
   * Handles the action on the restore defaults button.
   */
  public final void handleRestore ()
  {
    logger.debug ( "handleRestore", "handle restore" ); //$NON-NLS-1$ //$NON-NLS-2$

    /*
     * General
     */
    this.gui.jGTIComboBoxLanguage
        .setSelectedItem ( new LanguageItem (
            de.unisiegen.gtitool.core.preferences.PreferenceManager.DEFAULT_LANGUAGE_TITLE,
            new Locale (
                de.unisiegen.gtitool.core.preferences.PreferenceManager.DEFAULT_LANGUAGE_LANGUAGE ) ) );
    this.gui.jGTIComboBoxLookAndFeel.setSelectedItem ( new LookAndFeelItem (
        PreferenceManager.DEFAULT_LOOK_AND_FEEL_NAME,
        PreferenceManager.DEFAULT_LOOK_AND_FEEL_CLASS_NAME ) );
    this.gui.jGTIComboBoxWordMode
        .setSelectedIndex ( PreferenceManager.DEFAULT_WORD_MODE_ITEM
            .getIndex () );
    this.gui.jGTISliderZoom
        .setValue ( PreferenceManager.DEFAULT_ZOOM_FACTOR_ITEM.getFactor () );

    /*
     * View
     */
    this.gui.jGTIComboBoxTransition
        .setSelectedIndex ( PreferenceManager.DEFAULT_TRANSITION_ITEM
            .getIndex () );
    this.gui.jGTIComboBoxMouseSelection
        .setSelectedIndex ( PreferenceManager.DEFAULT_MOUSE_SELECTION_ITEM
            .getIndex () );
    this.gui.jGTIComboBoxPDAMode
        .setSelectedIndex ( PreferenceManager.DEFAULT_PDA_MODE_ITEM.getIndex () );
    this.gui.jGTISliderAutoStep
        .setValue ( PreferenceManager.DEFAULT_AUTO_STEP_INTERVAL_ITEM
            .getAutoStepInterval () );

    /*
     * Color
     */
    this.colorItemState.restore ();
    this.colorItemStateBackground.restore ();
    this.colorItemStateSelected.restore ();
    this.colorItemStateStart.restore ();
    this.colorItemStateFinal.restore ();
    this.colorItemStateActive.restore ();
    this.colorItemStateError.restore ();

    this.colorItemRegex.restore ();
    this.colorItemRegexToolTip.restore ();
    this.colorItemRegexComment.restore ();
    this.colorItemRegexPosition.restore ();
    this.colorItemRegexToken.restore ();
    this.colorItemRegexSymbol.restore ();
    this.colorItemRegexNode.restore ();
    this.colorItemRegexMarkedNode.restore ();
    this.colorItemRegexSelectedNode.restore ();

    this.colorItemTransition.restore ();
    this.colorItemTransitionSelected.restore ();
    this.colorItemTransitionActive.restore ();
    this.colorItemTransitionError.restore ();

    this.colorItemSymbol.restore ();
    this.colorItemSymbolActive.restore ();
    this.colorItemSymbolError.restore ();

    this.colorItemProductionError.restore ();
    this.colorItemProductionHighlight.restore ();

    this.colorItemNonterminalSymbol.restore ();
    this.colorItemNonterminalSymbolError.restore ();
    this.colorItemNonterminalSymbolHighlight.restore ();
    this.colorItemStartNonterminalSymbol.restore ();

    this.colorItemTerminalSymbol.restore ();
    this.colorItemTerminalSymbolError.restore ();

    this.colorItemParserKeyword.restore ();
    this.colorItemParserError.restore ();
    this.colorItemParserWarning.restore ();
    this.colorItemParserHighlighting.restore ();

    this.gui.jGTITreeColors.repaint ();

    /*
     * Alphabet
     */
    this.alphabetItem.restore ();
    this.gui.alphabetPanelForm.styledAlphabetParserPanelInput
        .setText ( this.alphabetItem.getAlphabet () );
    this.regexAlphabetItem.restore ();
    this.gui.alphabetPanelForm.styledRegexAlphabetParserPanelInput
        .setText ( ( ( DefaultRegexAlphabet ) this.regexAlphabetItem
            .getAlphabet () ).toClassPrettyString () );
    this.gui.alphabetPanelForm.jGTICheckBoxPushDownAlphabet
        .setSelected ( de.unisiegen.gtitool.core.preferences.PreferenceManager.DEFAULT_USE_PUSH_DOWN_ALPHABET );
    this.pushDownAlphabetItem.restore ();
    this.gui.alphabetPanelForm.styledAlphabetParserPanelPushDown
        .setText ( this.pushDownAlphabetItem.getAlphabet () );

    /*
     * Grammar
     */
    this.nonterminalSymbolSetItem.restore ();
    this.gui.terminalPanelForm
        .setNonterminalSymbolSet ( this.nonterminalSymbolSetItem
            .getNonterminalSymbolSet () );
    this.startSymbolItem.restore ();
    this.gui.terminalPanelForm.styledStartNonterminalSymbolParserPanel
        .setText ( this.startSymbolItem.getNonterminalSymbol () );
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
    initWordMode ();
    initZoomFactor ();
    /*
     * View
     */
    initTransition ();
    initMouseSelection ();
    initPDAMode ();
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
    initStartSymbol ();
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
        .setText ( this.alphabetItem.getAlphabet () );
    this.regexAlphabetItem = PreferenceManager.getInstance ()
        .getRegexAlphabetItem ();
    this.initialRegexAlphabetItem = this.regexAlphabetItem.clone ();
    this.gui.alphabetPanelForm.styledRegexAlphabetParserPanelInput
        .setText ( ( ( DefaultRegexAlphabet ) this.regexAlphabetItem
            .getAlphabet () ).toClassPrettyString () );

    // PopupMenu
    JPopupMenu jPopupMenu = this.gui.alphabetPanelForm.styledAlphabetParserPanelInput
        .getJPopupMenu ();
    jPopupMenu.addSeparator ();
    final JMenuItem jMenuItemRestoreAlphabet = new JMenuItem ( Messages
        .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
    jMenuItemRestoreAlphabet.setMnemonic ( Messages.getString (
        "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    jMenuItemRestoreAlphabet.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/small/refresh.png" ) ) ); //$NON-NLS-1$
    jMenuItemRestoreAlphabet.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        PreferencesDialog.this.alphabetItem.restore ();
        PreferencesDialog.this.gui.alphabetPanelForm.styledAlphabetParserPanelInput
            .setText ( PreferencesDialog.this.alphabetItem.getAlphabet () );
      }
    } );
    PreferenceManager.getInstance ().addLanguageChangedListener (
        new LanguageChangedListener ()
        {

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
        .addParseableChangedListener ( new ParseableChangedListener < Alphabet > ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void parseableChanged ( Alphabet newAlphabet )
          {
            setButtonStatus ();
            if ( newAlphabet != null )
            {
              PreferencesDialog.this.alphabetItem.setAlphabet ( newAlphabet );
            }
          }
        } );

    /*
     * Regex Alphabet changed listener
     */
    this.gui.alphabetPanelForm.styledRegexAlphabetParserPanelInput
        .addParseableChangedListener ( new ParseableChangedListener < Alphabet > ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void parseableChanged ( Alphabet newAlphabet )
          {
            setButtonStatus ();
            if ( newAlphabet != null )
            {
              PreferencesDialog.this.regexAlphabetItem
                  .setAlphabet ( newAlphabet );
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
    this.gui.jGTISliderAutoStep.setValue ( this.initialAutoStepItem
        .getAutoStepInterval () );

    // PopupMenu
    this.jPopupMenuAutoStep = new JPopupMenu ();
    final JMenuItem jMenuItemRestoreAutoStep = new JMenuItem ( Messages
        .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
    jMenuItemRestoreAutoStep.setMnemonic ( Messages.getString (
        "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    jMenuItemRestoreAutoStep.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/small/refresh.png" ) ) ); //$NON-NLS-1$
    jMenuItemRestoreAutoStep.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        PreferencesDialog.this.gui.jGTISliderAutoStep
            .setValue ( PreferenceManager.DEFAULT_AUTO_STEP_INTERVAL_ITEM
                .getAutoStepInterval () );
      }
    } );
    PreferenceManager.getInstance ().addLanguageChangedListener (
        new LanguageChangedListener ()
        {

          public void languageChanged ()
          {
            jMenuItemRestoreAutoStep.setText ( Messages
                .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
            jMenuItemRestoreAutoStep.setMnemonic ( Messages.getString (
                "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
          }
        } );
    this.jPopupMenuAutoStep.add ( jMenuItemRestoreAutoStep );
    this.gui.jGTISliderAutoStep.addMouseListener ( new MouseAdapter ()
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
    // State final
    this.colorItemStateFinal = PreferenceManager.getInstance ()
        .getColorItemStateFinal ();
    this.initialColorItemStateFinal = this.colorItemStateFinal.clone ();
    // State active
    this.colorItemStateActive = PreferenceManager.getInstance ()
        .getColorItemStateActive ();
    this.initialColorItemStateActive = this.colorItemStateActive.clone ();
    // State error
    this.colorItemStateError = PreferenceManager.getInstance ()
        .getColorItemStateError ();
    this.initialColorItemStateError = this.colorItemStateError.clone ();

    // Regex
    this.colorItemRegexToolTip = PreferenceManager.getInstance ()
        .getColorItemRegexToolTipText ();
    this.initialColorItemRegexToolTip = this.colorItemRegexToolTip.clone ();
    this.colorItemRegexToken = PreferenceManager.getInstance ()
        .getColorItemRegexToken ();
    this.initialColorItemRegexToken = this.colorItemRegexToken.clone ();
    this.colorItemRegexPosition = PreferenceManager.getInstance ()
        .getColorItemRegexPosition ();
    this.initialColorItemRegexPosition = this.colorItemRegexPosition.clone ();
    this.colorItemRegexSymbol = PreferenceManager.getInstance ()
        .getColorItemRegexSymbol ();
    this.initialColorItemRegexSymbol = this.colorItemRegexSymbol.clone ();
    this.colorItemRegexComment = PreferenceManager.getInstance ()
        .getColorItemRegexComment ();
    this.initialColorItemRegexComment = this.colorItemRegexComment.clone ();
    this.colorItemRegexNode = PreferenceManager.getInstance ()
        .getColorItemRegexNode ();
    this.initialColorItemRegexNode = this.colorItemRegexNode.clone ();
    this.colorItemRegexMarkedNode = PreferenceManager.getInstance ()
        .getColorItemRegexMarkedNode ();
    this.initialColorItemRegexMarkedNode = this.colorItemRegexMarkedNode
        .clone ();
    this.colorItemRegexSelectedNode = PreferenceManager.getInstance ()
        .getColorItemRegexSelectedNode ();
    this.initialColorItemRegexSelectedNode = this.colorItemRegexSelectedNode
        .clone ();

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

    // Production
    this.colorItemProductionError = PreferenceManager.getInstance ()
        .getColorItemProductionError ();
    this.initialColorItemProductionError = this.colorItemProductionError
        .clone ();
    this.colorItemProductionHighlight = PreferenceManager.getInstance ()
        .getColorItemProductionHighlight ();
    this.initialColorItemProductionHighlight = this.colorItemProductionHighlight
        .clone ();

    // NonterminalSymbol
    this.colorItemNonterminalSymbol = PreferenceManager.getInstance ()
        .getColorItemNonterminalSymbol ();
    this.initialColorItemNonterminalSymbol = this.colorItemNonterminalSymbol
        .clone ();
    this.colorItemNonterminalSymbolError = PreferenceManager.getInstance ()
        .getColorItemNonterminalSymbolError ();
    this.initialColorItemNonterminalSymbolError = this.colorItemNonterminalSymbolError
        .clone ();
    this.colorItemNonterminalSymbolHighlight = PreferenceManager.getInstance ()
        .getColorItemNonterminalSymbolHighlight ();
    this.initialColorItemNonterminalSymbolHighlight = this.colorItemNonterminalSymbolHighlight
        .clone ();
    this.colorItemStartNonterminalSymbol = PreferenceManager.getInstance ()
        .getColorItemStartNonterminalSymbol ();
    this.initialColorItemStartNonterminalSymbol = this.colorItemStartNonterminalSymbol
        .clone ();

    // TerminalSymbol
    this.colorItemTerminalSymbol = PreferenceManager.getInstance ()
        .getColorItemTerminalSymbol ();
    this.initialColorItemTerminalSymbol = this.colorItemTerminalSymbol.clone ();
    this.colorItemTerminalSymbolError = PreferenceManager.getInstance ()
        .getColorItemTerminalSymbolError ();
    this.initialColorItemTerminalSymbolError = this.colorItemTerminalSymbolError
        .clone ();

    // Parser keyword
    this.colorItemParserKeyword = PreferenceManager.getInstance ()
        .getColorItemParserKeyword ();
    this.initialColorItemParserKeyword = this.colorItemParserKeyword.clone ();
    // Parser error
    this.colorItemParserError = PreferenceManager.getInstance ()
        .getColorItemParserError ();
    this.initialColorItemParserError = this.colorItemParserError.clone ();
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
        .getResource ( "/de/unisiegen/gtitool/ui/icon/small/refresh.png" ) ) ); //$NON-NLS-1$
    jMenuItemRestoreColorList.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        TreePath selectedPath = PreferencesDialog.this.gui.jGTITreeColors
            .getSelectionModel ().getSelectionPath ();
        if ( selectedPath != null )
        {
          ColorItem selectedColorItem = ( ColorItem ) selectedPath
              .getLastPathComponent ();
          selectedColorItem.restore ();
        }
      }
    } );
    this.jPopupMenuColorList.add ( jMenuItemRestoreColorList );

    this.gui.jGTITreeColors.addMouseListener ( new MouseAdapter ()
    {

      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mousePressed ( MouseEvent event )
      {
        if ( event.isPopupTrigger () )
        {
          TreePath selectedPath = PreferencesDialog.this.gui.jGTITreeColors
              .getPathForLocation ( event.getX (), event.getY () );
          if ( selectedPath != null )
          {
            ColorItem selectedColorItem = ( ColorItem ) selectedPath
                .getLastPathComponent ();
            if ( selectedColorItem.isLeaf () )
            {
              PreferencesDialog.this.gui.jGTITreeColors
                  .setSelectionPath ( selectedPath );
              PreferencesDialog.this.jPopupMenuColorList.show ( event
                  .getComponent (), event.getX (), event.getY () );
            }
          }
        }
      }


      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mouseReleased ( MouseEvent event )
      {
        if ( event.isPopupTrigger () )
        {
          TreePath selectedPath = PreferencesDialog.this.gui.jGTITreeColors
              .getPathForLocation ( event.getX (), event.getY () );
          if ( selectedPath != null )
          {
            ColorItem selectedColorItem = ( ColorItem ) selectedPath
                .getLastPathComponent ();
            if ( selectedColorItem.isLeaf () )
            {
              PreferencesDialog.this.gui.jGTITreeColors
                  .setSelectionPath ( selectedPath );
              PreferencesDialog.this.jPopupMenuColorList.show ( event
                  .getComponent (), event.getX (), event.getY () );
            }
          }
        }
      }
    } );

    PreferenceManager.getInstance ().addLanguageChangedListener (
        new LanguageChangedListener ()
        {

          public void languageChanged ()
          {
            jMenuItemRestoreColorList.setText ( Messages
                .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
            jMenuItemRestoreColorList.setMnemonic ( Messages.getString (
                "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
          }
        } );

    // Items
    this.rootNode = new ColorItem ( "root", "root", true ); //$NON-NLS-1$ //$NON-NLS-2$

    this.stateNode = PreferenceManager.getInstance ().getColorItemStateGroup ();
    this.stateNode.add ( this.colorItemState );
    this.stateNode.add ( this.colorItemStateBackground );
    this.stateNode.add ( this.colorItemStateSelected );
    this.stateNode.add ( this.colorItemStateStart );
    this.stateNode.add ( this.colorItemStateFinal );
    this.stateNode.add ( this.colorItemStateActive );
    this.stateNode.add ( this.colorItemStateError );

    this.regexNode = PreferenceManager.getInstance ().getColorItemRegexGroup ();
    this.regexNode.add ( this.colorItemRegexToken );
    this.regexNode.add ( this.colorItemRegexSymbol );
    this.regexNode.add ( this.colorItemRegexPosition );
    this.regexNode.add ( this.colorItemRegexToolTip );
    this.regexNode.add ( this.colorItemRegexComment );
    this.regexNode.add ( this.colorItemRegexNode );
    this.regexNode.add ( this.colorItemRegexMarkedNode );
    this.regexNode.add ( this.colorItemRegexSelectedNode );

    this.transitionNode = PreferenceManager.getInstance ()
        .getColorItemTransitionGroup ();
    this.transitionNode.add ( this.colorItemTransition );
    this.transitionNode.add ( this.colorItemTransitionSelected );
    this.transitionNode.add ( this.colorItemTransitionActive );
    this.transitionNode.add ( this.colorItemTransitionError );

    this.symbolNode = PreferenceManager.getInstance ()
        .getColorItemSymbolGroup ();
    this.symbolNode.add ( this.colorItemSymbol );
    this.symbolNode.add ( this.colorItemSymbolActive );
    this.symbolNode.add ( this.colorItemSymbolError );

    this.productionNode = PreferenceManager.getInstance ()
        .getColorItemProductionGroup ();

    this.productionNode.add ( this.colorItemProductionError );
    this.productionNode.add ( this.colorItemProductionHighlight );

    this.nonterminalSymbolNode = PreferenceManager.getInstance ()
        .getColorItemNonterminalSymbolGroup ();
    this.nonterminalSymbolNode.add ( this.colorItemNonterminalSymbol );
    this.nonterminalSymbolNode.add ( this.colorItemNonterminalSymbolError );
    this.nonterminalSymbolNode.add ( this.colorItemNonterminalSymbolHighlight );
    this.nonterminalSymbolNode.add ( this.colorItemStartNonterminalSymbol );

    this.terminalSymbolNode = PreferenceManager.getInstance ()
        .getColorItemTerminalSymbolGroup ();
    this.terminalSymbolNode.add ( this.colorItemTerminalSymbol );
    this.terminalSymbolNode.add ( this.colorItemTerminalSymbolError );

    this.parserNode = PreferenceManager.getInstance ()
        .getColorItemParserGroup ();
    this.parserNode.add ( this.colorItemParserKeyword );
    this.parserNode.add ( this.colorItemParserError );
    this.parserNode.add ( this.colorItemParserWarning );
    this.parserNode.add ( this.colorItemParserHighlighting );

    this.rootNode.add ( this.stateNode );
    this.rootNode.add ( this.transitionNode );
    this.rootNode.add ( this.symbolNode );
    this.rootNode.add ( this.productionNode );
    this.rootNode.add ( this.nonterminalSymbolNode );
    this.rootNode.add ( this.terminalSymbolNode );
    this.rootNode.add ( this.parserNode );
    this.rootNode.add ( this.regexNode );

    DefaultTreeModel model = new DefaultTreeModel ( this.rootNode );
    this.gui.jGTITreeColors.setModel ( model );
    // Expand the items
    if ( this.stateNode.isExpanded () )
    {
      this.gui.jGTITreeColors.expandPath ( new TreePath ( this.stateNode
          .getPath () ) );
    }
    if ( this.regexNode.isExpanded () )
    {
      this.gui.jGTITreeColors.expandPath ( new TreePath ( this.regexNode
          .getPath () ) );
    }
    if ( this.transitionNode.isExpanded () )
    {
      this.gui.jGTITreeColors.expandPath ( new TreePath ( this.transitionNode
          .getPath () ) );
    }
    if ( this.symbolNode.isExpanded () )
    {
      this.gui.jGTITreeColors.expandPath ( new TreePath ( this.symbolNode
          .getPath () ) );
    }
    if ( this.productionNode.isExpanded () )
    {
      this.gui.jGTITreeColors.expandPath ( new TreePath ( this.productionNode
          .getPath () ) );
    }
    if ( this.nonterminalSymbolNode.isExpanded () )
    {
      this.gui.jGTITreeColors.expandPath ( new TreePath (
          this.nonterminalSymbolNode.getPath () ) );
    }
    if ( this.terminalSymbolNode.isExpanded () )
    {
      this.gui.jGTITreeColors.expandPath ( new TreePath (
          this.terminalSymbolNode.getPath () ) );
    }
    if ( this.parserNode.isExpanded () )
    {
      this.gui.jGTITreeColors.expandPath ( new TreePath ( this.parserNode
          .getPath () ) );
    }

    this.gui.jGTITreeColors.setRowHeight ( 0 );
    this.gui.jGTITreeColors.getSelectionModel ().setSelectionMode (
        TreeSelectionModel.SINGLE_TREE_SELECTION );
    this.gui.jGTITreeColors.setCellRenderer ( new ColorTreeCellRenderer () );

    ToolTipManager.sharedInstance ().registerComponent (
        this.gui.jGTITreeColors );
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
    this.gui.jGTIComboBoxLanguage.setModel ( this.languageComboBoxModel );
    this.initialLanguageItem = PreferenceManager.getInstance ()
        .getLanguageItem ();
    this.gui.jGTIComboBoxLanguage.setSelectedItem ( this.initialLanguageItem );

    // PopupMenu
    this.jPopupMenuLanguage = new JPopupMenu ();
    final JMenuItem jMenuItemRestoreLanguage = new JMenuItem ( Messages
        .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
    jMenuItemRestoreLanguage.setMnemonic ( Messages.getString (
        "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    jMenuItemRestoreLanguage.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/small/refresh.png" ) ) ); //$NON-NLS-1$
    jMenuItemRestoreLanguage.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        PreferencesDialog.this.gui.jGTIComboBoxLanguage
            .setSelectedItem ( new LanguageItem (
                de.unisiegen.gtitool.core.preferences.PreferenceManager.DEFAULT_LANGUAGE_TITLE,
                new Locale (
                    de.unisiegen.gtitool.core.preferences.PreferenceManager.DEFAULT_LANGUAGE_LANGUAGE ) ) );
      }
    } );
    this.jPopupMenuLanguage.add ( jMenuItemRestoreLanguage );
    this.gui.jGTIComboBoxLanguage.addMouseListener ( new MouseAdapter ()
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
    this.gui.jGTITabbedPane.setSelectedIndex ( this.initialLastActiveTab );
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
    this.gui.jGTIComboBoxLookAndFeel.setModel ( this.lookAndFeelComboBoxModel );
    this.initialLookAndFeel = PreferenceManager.getInstance ()
        .getLookAndFeelItem ();
    this.gui.jGTIComboBoxLookAndFeel.setSelectedItem ( this.initialLookAndFeel );

    // PopupMenu
    this.jPopupMenuLookAndFeel = new JPopupMenu ();
    final JMenuItem jMenuItemRestoreLookAndFeel = new JMenuItem ( Messages
        .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
    jMenuItemRestoreLookAndFeel.setMnemonic ( Messages.getString (
        "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    jMenuItemRestoreLookAndFeel.setIcon ( new ImageIcon ( getClass ()
        .getResource ( "/de/unisiegen/gtitool/ui/icon/small/refresh.png" ) ) ); //$NON-NLS-1$
    jMenuItemRestoreLookAndFeel.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        PreferencesDialog.this.gui.jGTIComboBoxLookAndFeel
            .setSelectedItem ( new LookAndFeelItem (
                PreferenceManager.DEFAULT_LOOK_AND_FEEL_NAME,
                PreferenceManager.DEFAULT_LOOK_AND_FEEL_CLASS_NAME ) );
      }
    } );
    this.jPopupMenuLookAndFeel.add ( jMenuItemRestoreLookAndFeel );
    this.gui.jGTIComboBoxLookAndFeel.addMouseListener ( new MouseAdapter ()
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
    this.gui.jGTIComboBoxMouseSelection
        .setModel ( this.mouseSelectionComboBoxModel );
    this.initialMouseSelectionItem = PreferenceManager.getInstance ()
        .getMouseSelectionItem ();
    this.gui.jGTIComboBoxMouseSelection
        .setSelectedItem ( this.initialMouseSelectionItem );

    // PopupMenu
    this.jPopupMenuMouseSelection = new JPopupMenu ();
    final JMenuItem jMenuItemRestoreMouseSelection = new JMenuItem ( Messages
        .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
    jMenuItemRestoreMouseSelection.setMnemonic ( Messages.getString (
        "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    jMenuItemRestoreMouseSelection.setIcon ( new ImageIcon ( getClass ()
        .getResource ( "/de/unisiegen/gtitool/ui/icon/small/refresh.png" ) ) ); //$NON-NLS-1$
    jMenuItemRestoreMouseSelection.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        PreferencesDialog.this.gui.jGTIComboBoxMouseSelection
            .setSelectedIndex ( PreferenceManager.DEFAULT_MOUSE_SELECTION_ITEM
                .getIndex () );
      }
    } );
    this.jPopupMenuMouseSelection.add ( jMenuItemRestoreMouseSelection );
    this.gui.jGTIComboBoxMouseSelection.addMouseListener ( new MouseAdapter ()
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
        .getResource ( "/de/unisiegen/gtitool/ui/icon/small/refresh.png" ) ) ); //$NON-NLS-1$
    jMenuItemRestoreNonterminalSymbolSet
        .addActionListener ( new ActionListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void actionPerformed (
              @SuppressWarnings ( "unused" ) ActionEvent event )
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
        .addParseableChangedListener ( new ParseableChangedListener < NonterminalSymbolSet > ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void parseableChanged (
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
   * Initializes the {@link PDA} mode.
   */
  private final void initPDAMode ()
  {
    this.pdaModeComboBoxModel = new PDAModeComboBoxModel ();
    this.pdaModeComboBoxModel.addElement ( PDAModeItem.create ( 0 ) );
    this.pdaModeComboBoxModel.addElement ( PDAModeItem.create ( 1 ) );
    this.gui.jGTIComboBoxPDAMode.setModel ( this.pdaModeComboBoxModel );

    this.initialPDAModeItem = PreferenceManager.getInstance ()
        .getPDAModeItem ();
    this.gui.jGTIComboBoxPDAMode.setSelectedItem ( this.initialPDAModeItem );

    // PopupMenu
    this.jPopupMenuPDAMode = new JPopupMenu ();
    final JMenuItem jMenuItemRestorePDAMode = new JMenuItem ( Messages
        .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
    jMenuItemRestorePDAMode.setMnemonic ( Messages.getString (
        "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    jMenuItemRestorePDAMode.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/small/refresh.png" ) ) ); //$NON-NLS-1$
    jMenuItemRestorePDAMode.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        PreferencesDialog.this.gui.jGTIComboBoxPDAMode
            .setSelectedIndex ( PreferenceManager.DEFAULT_PDA_MODE_ITEM
                .getIndex () );
      }
    } );
    this.jPopupMenuPDAMode.add ( jMenuItemRestorePDAMode );
    this.gui.jGTIComboBoxPDAMode.addMouseListener ( new MouseAdapter ()
    {

      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mousePressed ( MouseEvent event )
      {
        if ( event.isPopupTrigger () )
        {
          PreferencesDialog.this.jPopupMenuPDAMode.show (
              event.getComponent (), event.getX (), event.getY () );
        }
      }


      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mouseReleased ( MouseEvent event )
      {
        if ( event.isPopupTrigger () )
        {
          PreferencesDialog.this.jPopupMenuPDAMode.show (
              event.getComponent (), event.getX (), event.getY () );
        }
      }
    } );
    PreferenceManager.getInstance ().addLanguageChangedListener (
        new LanguageChangedListener ()
        {

          public void languageChanged ()
          {
            jMenuItemRestorePDAMode.setText ( Messages
                .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
            jMenuItemRestorePDAMode.setMnemonic ( Messages.getString (
                "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
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
        .setText ( this.pushDownAlphabetItem.getAlphabet () );
    this.initialUsePushDownAlphabet = PreferenceManager.getInstance ()
        .getUsePushDownAlphabet ();
    this.gui.alphabetPanelForm.jGTICheckBoxPushDownAlphabet
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
        .getResource ( "/de/unisiegen/gtitool/ui/icon/small/refresh.png" ) ) ); //$NON-NLS-1$
    jMenuItemRestorePushDownAlphabet.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        PreferencesDialog.this.pushDownAlphabetItem.restore ();
        PreferencesDialog.this.gui.alphabetPanelForm.styledAlphabetParserPanelPushDown
            .setText ( PreferencesDialog.this.pushDownAlphabetItem
                .getAlphabet () );
      }
    } );
    PreferenceManager.getInstance ().addLanguageChangedListener (
        new LanguageChangedListener ()
        {

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
        .addParseableChangedListener ( new ParseableChangedListener < Alphabet > ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void parseableChanged ( Alphabet newAlphabet )
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
        .getResource ( "/de/unisiegen/gtitool/ui/icon/small/refresh.png" ) ) ); //$NON-NLS-1$
    jMenuItemRestoreUsePushDownAlphabet
        .addActionListener ( new ActionListener ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void actionPerformed (
              @SuppressWarnings ( "unused" ) ActionEvent event )
          {
            PreferencesDialog.this.gui.alphabetPanelForm.jGTICheckBoxPushDownAlphabet
                .setSelected ( de.unisiegen.gtitool.core.preferences.PreferenceManager.DEFAULT_USE_PUSH_DOWN_ALPHABET );
          }
        } );
    this.jPopupMenuUsePushDownAlphabet
        .add ( jMenuItemRestoreUsePushDownAlphabet );
    this.gui.alphabetPanelForm.jGTICheckBoxPushDownAlphabet
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
   * Initializes the start {@link NonterminalSymbol}.
   */
  private final void initStartSymbol ()
  {
    this.startSymbolItem = PreferenceManager.getInstance ()
        .getStartSymbolItem ();
    this.initialStartSymbolItem = this.startSymbolItem.clone ();
    this.gui.terminalPanelForm.styledStartNonterminalSymbolParserPanel
        .setText ( this.startSymbolItem.getNonterminalSymbol () );

    // PopupMenu
    JPopupMenu jPopupMenu = this.gui.terminalPanelForm.styledStartNonterminalSymbolParserPanel
        .getJPopupMenu ();
    jPopupMenu.addSeparator ();
    final JMenuItem jMenuItemRestoreTerminalSymbolSet = new JMenuItem (
        Messages.getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
    jMenuItemRestoreTerminalSymbolSet.setMnemonic ( Messages.getString (
        "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    jMenuItemRestoreTerminalSymbolSet.setIcon ( new ImageIcon ( getClass ()
        .getResource ( "/de/unisiegen/gtitool/ui/icon/small/refresh.png" ) ) ); //$NON-NLS-1$
    jMenuItemRestoreTerminalSymbolSet.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        PreferencesDialog.this.startSymbolItem.restore ();
        PreferencesDialog.this.gui.terminalPanelForm.styledStartNonterminalSymbolParserPanel
            .setText ( PreferencesDialog.this.startSymbolItem
                .getNonterminalSymbol () );
      }
    } );
    PreferenceManager.getInstance ().addLanguageChangedListener (
        new LanguageChangedListener ()
        {

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
    this.gui.terminalPanelForm.styledStartNonterminalSymbolParserPanel
        .addParseableChangedListener ( new ParseableChangedListener < NonterminalSymbol > ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void parseableChanged ( NonterminalSymbol newNonterminalSymbol )
          {
            setButtonStatus ();
            if ( newNonterminalSymbol != null )
            {
              PreferencesDialog.this.startSymbolItem
                  .setNonterminalSymbol ( newNonterminalSymbol );
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
        .getResource ( "/de/unisiegen/gtitool/ui/icon/small/refresh.png" ) ) ); //$NON-NLS-1$
    jMenuItemRestoreTerminalSymbolSet.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
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
        .addParseableChangedListener ( new ParseableChangedListener < TerminalSymbolSet > ()
        {

          @SuppressWarnings ( "synthetic-access" )
          public void parseableChanged ( TerminalSymbolSet newTerminalSymbolSet )
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
    this.gui.jGTIComboBoxTransition.setModel ( this.transitionComboBoxModel );
    this.initialTransitionItem = PreferenceManager.getInstance ()
        .getTransitionItem ();
    this.gui.jGTIComboBoxTransition
        .setSelectedItem ( this.initialTransitionItem );

    // PopupMenu
    this.jPopupMenuTransition = new JPopupMenu ();
    final JMenuItem jMenuItemRestoreTranstion = new JMenuItem ( Messages
        .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
    jMenuItemRestoreTranstion.setMnemonic ( Messages.getString (
        "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    jMenuItemRestoreTranstion.setIcon ( new ImageIcon ( getClass ()
        .getResource ( "/de/unisiegen/gtitool/ui/icon/small/refresh.png" ) ) ); //$NON-NLS-1$
    jMenuItemRestoreTranstion.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        PreferencesDialog.this.gui.jGTIComboBoxTransition
            .setSelectedIndex ( PreferenceManager.DEFAULT_TRANSITION_ITEM
                .getIndex () );
      }
    } );
    this.jPopupMenuTransition.add ( jMenuItemRestoreTranstion );
    this.gui.jGTIComboBoxTransition.addMouseListener ( new MouseAdapter ()
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
   * Initializes the {@link Word} mode.
   */
  private final void initWordMode ()
  {
    this.wordModeComboBoxModel = new WordModeComboBoxModel ();
    this.wordModeComboBoxModel.addElement ( WordModeItem.create ( 0 ) );
    this.wordModeComboBoxModel.addElement ( WordModeItem.create ( 1 ) );
    this.gui.jGTIComboBoxWordMode.setModel ( this.wordModeComboBoxModel );

    this.initialWordModeItem = PreferenceManager.getInstance ()
        .getWordModeItem ();
    this.gui.jGTIComboBoxWordMode.setSelectedItem ( this.initialWordModeItem );

    // PopupMenu
    this.jPopupMenuWordMode = new JPopupMenu ();
    final JMenuItem jMenuItemRestoreWordMode = new JMenuItem ( Messages
        .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
    jMenuItemRestoreWordMode.setMnemonic ( Messages.getString (
        "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    jMenuItemRestoreWordMode.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/small/refresh.png" ) ) ); //$NON-NLS-1$
    jMenuItemRestoreWordMode.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        PreferencesDialog.this.gui.jGTIComboBoxWordMode
            .setSelectedIndex ( PreferenceManager.DEFAULT_WORD_MODE_ITEM
                .getIndex () );
      }
    } );
    this.jPopupMenuWordMode.add ( jMenuItemRestoreWordMode );
    this.gui.jGTIComboBoxWordMode.addMouseListener ( new MouseAdapter ()
    {

      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mousePressed ( MouseEvent event )
      {
        if ( event.isPopupTrigger () )
        {
          PreferencesDialog.this.jPopupMenuWordMode.show ( event
              .getComponent (), event.getX (), event.getY () );
        }
      }


      @SuppressWarnings ( "synthetic-access" )
      @Override
      public void mouseReleased ( MouseEvent event )
      {
        if ( event.isPopupTrigger () )
        {
          PreferencesDialog.this.jPopupMenuWordMode.show ( event
              .getComponent (), event.getX (), event.getY () );
        }
      }
    } );
    PreferenceManager.getInstance ().addLanguageChangedListener (
        new LanguageChangedListener ()
        {

          public void languageChanged ()
          {
            jMenuItemRestoreWordMode.setText ( Messages
                .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
            jMenuItemRestoreWordMode.setMnemonic ( Messages.getString (
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
    this.gui.jGTISliderZoom.setValue ( this.initialZoomFactorItem.getFactor () );

    // PopupMenu
    this.jPopupMenuZoomFactor = new JPopupMenu ();
    final JMenuItem jMenuItemRestoreZoomFactor = new JMenuItem ( Messages
        .getString ( "PreferencesDialog.RestoreShort" ) ); //$NON-NLS-1$
    jMenuItemRestoreZoomFactor.setMnemonic ( Messages.getString (
        "PreferencesDialog.RestoreShortMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    jMenuItemRestoreZoomFactor.setIcon ( new ImageIcon ( getClass ()
        .getResource ( "/de/unisiegen/gtitool/ui/icon/small/refresh.png" ) ) ); //$NON-NLS-1$
    jMenuItemRestoreZoomFactor.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        PreferencesDialog.this.gui.jGTISliderZoom
            .setValue ( PreferenceManager.DEFAULT_ZOOM_FACTOR_ITEM.getFactor () );
      }
    } );
    this.jPopupMenuZoomFactor.add ( jMenuItemRestoreZoomFactor );
    this.gui.jGTISliderZoom.addMouseListener ( new MouseAdapter ()
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
    this.gui.jGTITabbedPane.setTitleAt ( GENERAL_TAB_INDEX, Messages
        .getString ( "PreferencesDialog.TabGeneral" ) ); //$NON-NLS-1$
    this.gui.jGTITabbedPane.setToolTipTextAt ( GENERAL_TAB_INDEX, Messages
        .getString ( "PreferencesDialog.TabGeneralToolTip" ) ); //$NON-NLS-1$

    // View
    this.gui.jGTITabbedPane.setTitleAt ( VIEW_TAB_INDEX, Messages
        .getString ( "PreferencesDialog.TabView" ) ); //$NON-NLS-1$
    this.gui.jGTITabbedPane.setToolTipTextAt ( VIEW_TAB_INDEX, Messages
        .getString ( "PreferencesDialog.TabViewToolTip" ) ); //$NON-NLS-1$

    // Colors
    this.gui.jGTITabbedPane.setTitleAt ( COLOR_TAB_INDEX, Messages
        .getString ( "PreferencesDialog.TabColors" ) ); //$NON-NLS-1$
    this.gui.jGTITabbedPane.setToolTipTextAt ( COLOR_TAB_INDEX, Messages
        .getString ( "PreferencesDialog.TabColorsToolTip" ) ); //$NON-NLS-1$

    // Alphabet
    this.gui.jGTITabbedPane.setTitleAt ( ALPHABET_TAB_INDEX, Messages
        .getString ( "PreferencesDialog.TabAlphabet" ) ); //$NON-NLS-1$
    this.gui.jGTITabbedPane.setToolTipTextAt ( ALPHABET_TAB_INDEX, Messages
        .getString ( "PreferencesDialog.TabAlphabetToolTip" ) ); //$NON-NLS-1$
    this.gui.alphabetPanelForm.jGTILabelInputAlphabet.setText ( Messages
        .getString ( "PreferencesDialog.InputAlphabet" ) ); //$NON-NLS-1$
    this.gui.alphabetPanelForm.jGTICheckBoxPushDownAlphabet.setText ( Messages
        .getString ( "PreferencesDialog.PushDownAlphabet" ) ); //$NON-NLS-1$

    // Grammar
    this.gui.jGTITabbedPane.setTitleAt ( GRAMMAR_TAB_INDEX, Messages
        .getString ( "PreferencesDialog.TabGrammar" ) ); //$NON-NLS-1$
    this.gui.jGTITabbedPane.setToolTipTextAt ( GRAMMAR_TAB_INDEX, Messages
        .getString ( "PreferencesDialog.TabGrammarToolTip" ) ); //$NON-NLS-1$
    this.gui.terminalPanelForm.jGTILabelNonterminalSymbols.setText ( Messages
        .getString ( "TerminalPanel.NonterminalSymbols" ) ); //$NON-NLS-1$
    this.gui.terminalPanelForm.jGTILabelStartSymbol.setText ( Messages
        .getString ( "TerminalPanel.StartSymbol" ) ); //$NON-NLS-1$
    this.gui.terminalPanelForm.jGTILabelTerminalSymbols.setText ( Messages
        .getString ( "TerminalPanel.TerminalSymbols" ) ); //$NON-NLS-1$

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
    this.gui.jGTILabelLanguage.setText ( Messages
        .getString ( "PreferencesDialog.Language" ) ); //$NON-NLS-1$
    this.gui.jGTILabelLanguage.setDisplayedMnemonic ( Messages.getString (
        "PreferencesDialog.LanguageMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    this.gui.jGTIComboBoxLanguage.setToolTipText ( Messages
        .getString ( "PreferencesDialog.LanguageToolTip" ) ); //$NON-NLS-1$

    // Look and feel
    this.gui.jGTILabelLookAndFeel.setText ( Messages
        .getString ( "PreferencesDialog.LookAndFeel" ) ); //$NON-NLS-1$
    this.gui.jGTILabelLookAndFeel.setDisplayedMnemonic ( Messages.getString (
        "PreferencesDialog.LookAndFeelMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    this.gui.jGTIComboBoxLookAndFeel.setToolTipText ( Messages
        .getString ( "PreferencesDialog.LookAndFeelToolTip" ) ); //$NON-NLS-1$

    // word mode
    this.gui.jGTILabelWordMode.setText ( Messages
        .getString ( "PreferencesDialog.WordMode" ) ); //$NON-NLS-1$
    this.gui.jGTILabelWordMode.setDisplayedMnemonic ( Messages.getString (
        "PreferencesDialog.WordModeMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    this.gui.jGTIComboBoxWordMode.setToolTipText ( Messages
        .getString ( "PreferencesDialog.WordModeToolTip" ) ); //$NON-NLS-1$

    // Zoom
    this.gui.jGTILabelZoom.setText ( Messages
        .getString ( "PreferencesDialog.Zoom" ) ); //$NON-NLS-1$
    this.gui.jGTILabelZoom.setDisplayedMnemonic ( Messages.getString (
        "PreferencesDialog.ZoomMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    this.gui.jGTISliderZoom.setToolTipText ( Messages
        .getString ( "PreferencesDialog.ZoomToolTip" ) ); //$NON-NLS-1$

    // Auto Step
    this.gui.jGTILabelAutoStep.setText ( Messages
        .getString ( "PreferencesDialog.AutoStep" ) ); //$NON-NLS-1$
    this.gui.jGTILabelAutoStep.setDisplayedMnemonic ( Messages.getString (
        "PreferencesDialog.AutoStepMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    this.gui.jGTISliderAutoStep.setToolTipText ( Messages
        .getString ( "PreferencesDialog.AutoStepToolTip" ) ); //$NON-NLS-1$

    // Mouse selection
    this.gui.jGTILabelMouseSelection.setText ( Messages
        .getString ( "PreferencesDialog.MouseSelection" ) ); //$NON-NLS-1$
    this.gui.jGTILabelMouseSelection.setDisplayedMnemonic ( Messages.getString (
        "PreferencesDialog.MouseSelectionMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    this.gui.jGTIComboBoxMouseSelection.setToolTipText ( Messages
        .getString ( "PreferencesDialog.MouseSelectionToolTip" ) ); //$NON-NLS-1$

    // pda mode
    this.gui.jGTILabelPDAMode.setText ( Messages
        .getString ( "PreferencesDialog.PDAMode" ) ); //$NON-NLS-1$
    this.gui.jGTILabelPDAMode.setDisplayedMnemonic ( Messages.getString (
        "PreferencesDialog.PDAModeMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    this.gui.jGTIComboBoxPDAMode.setToolTipText ( Messages
        .getString ( "PreferencesDialog.PDAModeToolTip" ) ); //$NON-NLS-1$

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

    // State final
    this.colorItemStateFinal.setCaption ( Messages
        .getString ( "Preferences.ColorStateFinalCaption" ) ); //$NON-NLS-1$
    this.colorItemStateFinal.setDescription ( Messages
        .getString ( "Preferences.ColorStateFinalDescription" ) ); //$NON-NLS-1$

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

    // Production
    this.colorItemProductionError.setCaption ( Messages
        .getString ( "Preferences.ColorProductionErrorCaption" ) ); //$NON-NLS-1$
    this.colorItemProductionError.setDescription ( Messages
        .getString ( "Preferences.ColorProductionErrorDescription" ) ); //$NON-NLS-1$
    this.colorItemProductionHighlight.setCaption ( Messages
        .getString ( "Preferences.ColorProductionHighlightCaption" ) ); //$NON-NLS-1$
    this.colorItemProductionHighlight.setDescription ( Messages
        .getString ( "Preferences.ColorProductionHighlightDescription" ) ); //$NON-NLS-1$

    // NonterminalSymbol
    this.colorItemNonterminalSymbol.setCaption ( Messages
        .getString ( "Preferences.ColorNonterminalSymbolCaption" ) ); //$NON-NLS-1$
    this.colorItemNonterminalSymbol.setDescription ( Messages
        .getString ( "Preferences.ColorNonterminalSymbolDescription" ) ); //$NON-NLS-1$
    this.colorItemNonterminalSymbolError.setCaption ( Messages
        .getString ( "Preferences.ColorNonterminalSymbolErrorCaption" ) ); //$NON-NLS-1$
    this.colorItemNonterminalSymbolError.setDescription ( Messages
        .getString ( "Preferences.ColorNonterminalSymbolErrorDescription" ) ); //$NON-NLS-1$
    this.colorItemNonterminalSymbolHighlight.setCaption ( Messages
        .getString ( "Preferences.ColorNonterminalSymbolHighlightCaption" ) ); //$NON-NLS-1$
    this.colorItemNonterminalSymbolHighlight
        .setDescription ( Messages
            .getString ( "Preferences.ColorNonterminalSymbolHighlightDescription" ) ); //$NON-NLS-1$
    this.colorItemStartNonterminalSymbol.setCaption ( Messages
        .getString ( "Preferences.ColorStartNonterminalSymbolCaption" ) ); //$NON-NLS-1$
    this.colorItemStartNonterminalSymbol.setDescription ( Messages
        .getString ( "Preferences.ColorStartNonterminalSymbolDescription" ) ); //$NON-NLS-1$

    // TerminalSymbol
    this.colorItemTerminalSymbol.setCaption ( Messages
        .getString ( "Preferences.ColorTerminalSymbolCaption" ) ); //$NON-NLS-1$
    this.colorItemTerminalSymbol.setDescription ( Messages
        .getString ( "Preferences.ColorTerminalSymbolDescription" ) ); //$NON-NLS-1$
    this.colorItemTerminalSymbolError.setCaption ( Messages
        .getString ( "Preferences.ColorTerminalSymbolErrorCaption" ) ); //$NON-NLS-1$
    this.colorItemTerminalSymbolError.setDescription ( Messages
        .getString ( "Preferences.ColorTerminalSymbolErrorDescription" ) ); //$NON-NLS-1$

    // Parser keyword
    this.colorItemParserKeyword.setCaption ( Messages
        .getString ( "Preferences.ColorParserKeywordCaption" ) ); //$NON-NLS-1$
    this.colorItemParserKeyword.setDescription ( Messages
        .getString ( "Preferences.ColorParserKeywordDescription" ) ); //$NON-NLS-1$

    // Parser error
    this.colorItemParserError.setCaption ( Messages
        .getString ( "Preferences.ColorParserErrorCaption" ) ); //$NON-NLS-1$
    this.colorItemParserError.setDescription ( Messages
        .getString ( "Preferences.ColorParserErrorDescription" ) ); //$NON-NLS-1$

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
    this.stateNode.setCaption ( Messages
        .getString ( "Preferences.ColorStateGroupCaption" ) ); //$NON-NLS-1$
    this.stateNode.setDescription ( Messages
        .getString ( "Preferences.ColorStateGroupDescription" ) ); //$NON-NLS-1$

    this.transitionNode.setCaption ( Messages
        .getString ( "Preferences.ColorTransitionGroupCaption" ) ); //$NON-NLS-1$
    this.transitionNode.setDescription ( Messages
        .getString ( "Preferences.ColorTransitionGroupDescription" ) ); //$NON-NLS-1$

    this.symbolNode.setCaption ( Messages
        .getString ( "Preferences.ColorSymbolGroupCaption" ) ); //$NON-NLS-1$
    this.symbolNode.setDescription ( Messages
        .getString ( "Preferences.ColorSymbolGroupDescription" ) ); //$NON-NLS-1$

    this.productionNode.setCaption ( Messages
        .getString ( "Preferences.ColorProductionGroupCaption" ) ); //$NON-NLS-1$
    this.productionNode.setDescription ( Messages
        .getString ( "Preferences.ColorProductionGroupDescription" ) ); //$NON-NLS-1$

    this.nonterminalSymbolNode.setCaption ( Messages
        .getString ( "Preferences.ColorNonterminalSymbolGroupCaption" ) ); //$NON-NLS-1$
    this.nonterminalSymbolNode.setDescription ( Messages
        .getString ( "Preferences.ColorNonterminalSymbolGroupDescription" ) ); //$NON-NLS-1$

    this.terminalSymbolNode.setCaption ( Messages
        .getString ( "Preferences.ColorTerminalSymbolGroupCaption" ) ); //$NON-NLS-1$
    this.terminalSymbolNode.setDescription ( Messages
        .getString ( "Preferences.ColorTerminalSymbolGroupDescription" ) ); //$NON-NLS-1$

    this.parserNode.setCaption ( Messages
        .getString ( "Preferences.ColorParserGroupCaption" ) ); //$NON-NLS-1$
    this.parserNode.setDescription ( Messages
        .getString ( "Preferences.ColorParserGroupDescription" ) ); //$NON-NLS-1$
    nodeChanged ( this.rootNode );
  }


  /**
   * Calls the node changed method on the given node and all child nodes.
   * 
   * @param node The changed node.
   */
  private final void nodeChanged ( TreeNode node )
  {
    ( ( DefaultTreeModel ) this.gui.jGTITreeColors.getModel () )
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
    logger.debug ( "save", "save" ); //$NON-NLS-1$//$NON-NLS-2$

    // General
    saveLanguage ();
    saveLookAndFeel ();
    saveWordMode ();
    saveZoomFactor ();

    // View
    saveTransition ();
    saveMouseSelection ();
    savePDAMode ();
    saveAutoStep ();

    // Color
    saveColor ();

    // Alphabet
    saveAlphabet ();
    savePushDownAlphabet ();

    // Grammar
    saveNonterminalSymbolSet ();
    saveStartSymbol ();
    saveTerminalSymbolSet ();

    // Tab
    saveLastActiveTab ();
  }


  /**
   * Saves the data of the {@link AlphabetItem}.
   */
  private final void saveAlphabet ()
  {
    if ( !this.initialAlphabetItem.equals ( this.alphabetItem ) )
    {
      this.initialAlphabetItem = this.alphabetItem.clone ();
      PreferenceManager.getInstance ().setAlphabetItem ( this.alphabetItem );
    }
    if ( !this.initialRegexAlphabetItem.equals ( this.regexAlphabetItem ) )
    {
      this.initialRegexAlphabetItem = this.regexAlphabetItem.clone ();
      PreferenceManager.getInstance ().setRegexAlphabetItem (
          this.regexAlphabetItem );
    }
  }


  /**
   * Saves the data of the auto step .
   */
  private final void saveAutoStep ()
  {
    if ( this.initialAutoStepItem.getAutoStepInterval () != this.gui.jGTISliderAutoStep
        .getValue () )
    {
      this.initialAutoStepItem = AutoStepItem
          .create ( this.gui.jGTISliderAutoStep.getValue () );
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
    if ( this.gui.jGTITreeColors.isExpanded ( new TreePath ( this.stateNode
        .getPath () ) ) != this.stateNode.isExpanded () )
    {
      this.stateNode.setExpanded ( this.gui.jGTITreeColors
          .isExpanded ( new TreePath ( this.stateNode.getPath () ) ) );
      PreferenceManager.getInstance ().setColorItemStateGroup ( this.stateNode );
    }
    // State normal
    if ( !this.initialColorItemState.getColor ().equals (
        this.colorItemState.getColor () ) )
    {
      this.initialColorItemState = this.colorItemState.clone ();
      PreferenceManager.getInstance ().setColorItemState ( this.colorItemState );
      PreferenceManager.getInstance ().fireColorChangedState (
          this.colorItemState.getColor () );
    }
    // State background
    if ( !this.initialColorItemStateBackground.getColor ().equals (
        this.colorItemStateBackground.getColor () ) )
    {
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
      this.initialColorItemStateStart = this.colorItemStateStart.clone ();
      PreferenceManager.getInstance ().setColorItemStateStart (
          this.colorItemStateStart );
      PreferenceManager.getInstance ().fireColorChangedStateStart (
          this.colorItemStateStart.getColor () );
    }
    // State final
    if ( !this.initialColorItemStateFinal.getColor ().equals (
        this.colorItemStateFinal.getColor () ) )
    {
      this.initialColorItemStateFinal = this.colorItemStateFinal.clone ();
      PreferenceManager.getInstance ().setColorItemStateFinal (
          this.colorItemStateFinal );
      PreferenceManager.getInstance ().fireColorChangedStateFinal (
          this.colorItemStateFinal.getColor () );
    }
    // State active
    if ( !this.initialColorItemStateActive.getColor ().equals (
        this.colorItemStateActive.getColor () ) )
    {
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
      this.initialColorItemStateError = this.colorItemStateError.clone ();
      PreferenceManager.getInstance ().setColorItemStateError (
          this.colorItemStateError );
      PreferenceManager.getInstance ().fireColorChangedStateError (
          this.colorItemStateError.getColor () );
    }

    // Transition
    if ( this.gui.jGTITreeColors.isExpanded ( new TreePath (
        this.transitionNode.getPath () ) ) != this.transitionNode.isExpanded () )
    {
      this.transitionNode.setExpanded ( this.gui.jGTITreeColors
          .isExpanded ( new TreePath ( this.transitionNode.getPath () ) ) );
      PreferenceManager.getInstance ().setColorItemTransitionGroup (
          this.transitionNode );
    }
    // Transition normal
    if ( !this.initialColorItemTransition.getColor ().equals (
        this.colorItemTransition.getColor () ) )
    {
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
      this.initialColorItemTransitionError = this.colorItemTransitionError
          .clone ();
      PreferenceManager.getInstance ().setColorItemTransitionError (
          this.colorItemTransitionError );
      PreferenceManager.getInstance ().fireColorChangedTransitionError (
          this.colorItemTransitionError.getColor () );
    }

    // Regex
    if ( this.gui.jGTITreeColors.isExpanded ( new TreePath ( this.regexNode
        .getPath () ) ) != this.regexNode.isExpanded () )
    {

      this.regexNode.setExpanded ( this.gui.jGTITreeColors
          .isExpanded ( new TreePath ( this.regexNode.getPath () ) ) );
      PreferenceManager.getInstance ().setColorItemRegexGroup ( this.regexNode );
    }
    if ( !this.initialColorItemRegexToolTip.getColor ().equals (
        this.colorItemRegexToolTip.getColor () ) )
    {
      PreferenceManager.getInstance ().setColorItemRegexToolTip (
          this.colorItemRegexToolTip );
      PreferenceManager.getInstance ().fireColorChangedRegexToolTip (
          this.colorItemRegexToolTip.getColor () );
    }
    if ( !this.initialColorItemRegexComment.getColor ().equals (
        this.colorItemRegexComment.getColor () ) )
    {
      PreferenceManager.getInstance ().setColorItemRegexComment (
          this.colorItemRegexComment );
      PreferenceManager.getInstance ().fireColorChangedRegexComment (
          this.colorItemRegexComment.getColor () );
    }
    if ( !this.initialColorItemRegexNode.getColor ().equals (
        this.colorItemRegexNode.getColor () ) )
    {
      PreferenceManager.getInstance ().setColorItemRegexNode (
          this.colorItemRegexNode );
      PreferenceManager.getInstance ().fireColorChangedRegexNode (
          this.colorItemRegexNode.getColor () );
    }
    if ( !this.initialColorItemRegexMarkedNode.getColor ().equals (
        this.colorItemRegexMarkedNode.getColor () ) )
    {
      PreferenceManager.getInstance ().setColorItemRegexMarkedNode (
          this.colorItemRegexMarkedNode );
      PreferenceManager.getInstance ().fireColorChangedRegexMarkedNode (
          this.colorItemRegexMarkedNode.getColor () );
    }
    if ( !this.initialColorItemRegexSelectedNode.getColor ().equals (
        this.colorItemRegexSelectedNode.getColor () ) )
    {
      PreferenceManager.getInstance ().setColorItemRegexSelectedNode (
          this.colorItemRegexSelectedNode );
      PreferenceManager.getInstance ().fireColorChangedRegexSelectedNode (
          this.colorItemRegexSelectedNode.getColor () );
    }
    if ( !this.initialColorItemRegexToken.getColor ().equals (
        this.colorItemRegexToken.getColor () ) )
    {
      PreferenceManager.getInstance ().setColorItemRegexToken (
          this.colorItemRegexToken );
      PreferenceManager.getInstance ().fireColorChangedRegexToken (
          this.colorItemRegexToken.getColor () );
    }
    if ( !this.initialColorItemRegexPosition.getColor ().equals (
        this.colorItemRegexPosition.getColor () ) )
    {
      PreferenceManager.getInstance ().setColorItemRegexPosition (
          this.colorItemRegexPosition );
      PreferenceManager.getInstance ().fireColorChangedRegexPosition (
          this.colorItemRegexPosition.getColor () );
    }
    if ( !this.initialColorItemRegexSymbol.getColor ().equals (
        this.colorItemRegexSymbol.getColor () ) )
    {
      PreferenceManager.getInstance ().setColorItemRegexSymbol (
          this.colorItemRegexSymbol );
      PreferenceManager.getInstance ().fireColorChangedRegexSymbol (
          this.colorItemRegexSymbol.getColor () );
    }

    // Symbol
    if ( this.gui.jGTITreeColors.isExpanded ( new TreePath ( this.symbolNode
        .getPath () ) ) != this.symbolNode.isExpanded () )
    {
      this.symbolNode.setExpanded ( this.gui.jGTITreeColors
          .isExpanded ( new TreePath ( this.symbolNode.getPath () ) ) );
      PreferenceManager.getInstance ().setColorItemSymbolGroup (
          this.symbolNode );
    }
    // Symbol normal
    if ( !this.initialColorItemSymbol.getColor ().equals (
        this.colorItemSymbol.getColor () ) )
    {
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
      this.initialColorItemSymbolError = this.colorItemSymbolError.clone ();
      PreferenceManager.getInstance ().setColorItemSymbolError (
          this.colorItemSymbolError );
      PreferenceManager.getInstance ().fireColorChangedSymbolError (
          this.colorItemSymbolError.getColor () );
    }

    // Production
    if ( this.gui.jGTITreeColors.isExpanded ( new TreePath (
        this.productionNode.getPath () ) ) != this.productionNode.isExpanded () )
    {
      this.productionNode.setExpanded ( this.gui.jGTITreeColors
          .isExpanded ( new TreePath ( this.productionNode.getPath () ) ) );
      PreferenceManager.getInstance ().setColorItemProductionGroup (
          this.productionNode );
    }
    // Production error
    if ( !this.initialColorItemProductionError.getColor ().equals (
        this.colorItemProductionError.getColor () ) )
    {
      this.initialColorItemProductionError = this.colorItemProductionError
          .clone ();
      PreferenceManager.getInstance ().setColorItemProductionError (
          this.colorItemProductionError );
      PreferenceManager.getInstance ().fireColorChangedProductionError (
          this.colorItemProductionError.getColor () );
    }
    // Production highlight
    if ( !this.initialColorItemProductionHighlight.getColor ().equals (
        this.colorItemProductionHighlight.getColor () ) )
    {
      this.initialColorItemProductionHighlight = this.colorItemProductionHighlight
          .clone ();
      PreferenceManager.getInstance ().setColorItemProductionHighlight (
          this.colorItemProductionHighlight );
      PreferenceManager.getInstance ().fireColorChangedProductionHighlight (
          this.colorItemProductionHighlight.getColor () );
    }

    // NonterminalSymbol
    if ( this.gui.jGTITreeColors.isExpanded ( new TreePath (
        this.nonterminalSymbolNode.getPath () ) ) != this.nonterminalSymbolNode
        .isExpanded () )
    {
      this.nonterminalSymbolNode
          .setExpanded ( this.gui.jGTITreeColors.isExpanded ( new TreePath (
              this.nonterminalSymbolNode.getPath () ) ) );
      PreferenceManager.getInstance ().setColorItemNonterminalSymbolGroup (
          this.nonterminalSymbolNode );
    }
    // NonterminalSymbol normal
    if ( !this.initialColorItemNonterminalSymbol.getColor ().equals (
        this.colorItemNonterminalSymbol.getColor () ) )
    {
      this.initialColorItemNonterminalSymbol = this.colorItemNonterminalSymbol
          .clone ();
      PreferenceManager.getInstance ().setColorItemNonterminalSymbol (
          this.colorItemNonterminalSymbol );
      PreferenceManager.getInstance ().fireColorChangedNonterminalSymbol (
          this.colorItemNonterminalSymbol.getColor () );
    }
    // NonterminalSymbol error
    if ( !this.initialColorItemNonterminalSymbolError.getColor ().equals (
        this.colorItemNonterminalSymbolError.getColor () ) )
    {
      this.initialColorItemNonterminalSymbolError = this.colorItemNonterminalSymbolError
          .clone ();
      PreferenceManager.getInstance ().setColorItemNonterminalSymbolError (
          this.colorItemNonterminalSymbolError );
      PreferenceManager.getInstance ().fireColorChangedNonterminalSymbolError (
          this.colorItemNonterminalSymbolError.getColor () );
    }
    // NonterminalSymbol highlight
    if ( !this.initialColorItemNonterminalSymbolHighlight.getColor ().equals (
        this.colorItemNonterminalSymbolHighlight.getColor () ) )
    {
      this.initialColorItemNonterminalSymbolHighlight = this.colorItemNonterminalSymbolHighlight
          .clone ();
      PreferenceManager.getInstance ().setColorItemNonterminalSymbolHighlight (
          this.colorItemNonterminalSymbolHighlight );
      PreferenceManager.getInstance ()
          .fireColorChangedNonterminalSymbolHighlight (
              this.colorItemNonterminalSymbolHighlight.getColor () );
    }
    // NonterminalSymbol start
    if ( !this.initialColorItemStartNonterminalSymbol.getColor ().equals (
        this.colorItemStartNonterminalSymbol.getColor () ) )
    {
      this.initialColorItemStartNonterminalSymbol = this.colorItemStartNonterminalSymbol
          .clone ();
      PreferenceManager.getInstance ().setColorItemStartNonterminalSymbol (
          this.colorItemStartNonterminalSymbol );
      PreferenceManager.getInstance ().fireColorChangedStartNonterminalSymbol (
          this.colorItemStartNonterminalSymbol.getColor () );
    }

    // TerminalSymbol
    if ( this.gui.jGTITreeColors.isExpanded ( new TreePath (
        this.terminalSymbolNode.getPath () ) ) != this.terminalSymbolNode
        .isExpanded () )
    {
      this.terminalSymbolNode.setExpanded ( this.gui.jGTITreeColors
          .isExpanded ( new TreePath ( this.terminalSymbolNode.getPath () ) ) );
      PreferenceManager.getInstance ().setColorItemTerminalSymbolGroup (
          this.terminalSymbolNode );
    }
    // TerminalSymbol normal
    if ( !this.initialColorItemTerminalSymbol.getColor ().equals (
        this.colorItemTerminalSymbol.getColor () ) )
    {
      this.initialColorItemTerminalSymbol = this.colorItemTerminalSymbol
          .clone ();
      PreferenceManager.getInstance ().setColorItemTerminalSymbol (
          this.colorItemTerminalSymbol );
      PreferenceManager.getInstance ().fireColorChangedTerminalSymbol (
          this.colorItemTerminalSymbol.getColor () );
    }
    // TerminalSymbol error
    if ( !this.initialColorItemTerminalSymbolError.getColor ().equals (
        this.colorItemTerminalSymbolError.getColor () ) )
    {
      this.initialColorItemTerminalSymbolError = this.colorItemTerminalSymbolError
          .clone ();
      PreferenceManager.getInstance ().setColorItemTerminalSymbolError (
          this.colorItemTerminalSymbolError );
      PreferenceManager.getInstance ().fireColorChangedTerminalSymbolError (
          this.colorItemTerminalSymbolError.getColor () );
    }

    // Parser
    if ( this.gui.jGTITreeColors.isExpanded ( new TreePath ( this.parserNode
        .getPath () ) ) != this.parserNode.isExpanded () )
    {
      this.parserNode.setExpanded ( this.gui.jGTITreeColors
          .isExpanded ( new TreePath ( this.parserNode.getPath () ) ) );
      PreferenceManager.getInstance ().setColorItemParserGroup (
          this.parserNode );
    }
    // Parser keyword
    if ( !this.initialColorItemParserKeyword.getColor ().equals (
        this.colorItemParserKeyword.getColor () ) )
    {
      this.initialColorItemParserKeyword = this.colorItemParserKeyword.clone ();
      PreferenceManager.getInstance ().setColorItemParserKeyword (
          this.colorItemParserKeyword );
      PreferenceManager.getInstance ().fireColorChangedParserKeyword (
          this.colorItemParserKeyword.getColor () );
    }
    // Parser error
    if ( !this.initialColorItemParserError.getColor ().equals (
        this.colorItemParserError.getColor () ) )
    {
      this.initialColorItemParserError = this.colorItemParserError.clone ();
      PreferenceManager.getInstance ().setColorItemParserError (
          this.colorItemParserError );
      PreferenceManager.getInstance ().fireColorChangedParserError (
          this.colorItemParserError.getColor () );
    }
    // Parser warning
    if ( !this.initialColorItemParserWarning.getColor ().equals (
        this.colorItemParserWarning.getColor () ) )
    {
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
    if ( this.initialLastActiveTab != this.gui.jGTITabbedPane
        .getSelectedIndex () )
    {
      this.initialLastActiveTab = this.gui.jGTITabbedPane.getSelectedIndex ();
      PreferenceManager.getInstance ().setPreferencesDialogLastActiveTab (
          this.gui.jGTITabbedPane.getSelectedIndex () );
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
        catch ( ClassNotFoundException exc )
        {
          logger.error ( "saveLookAndFeel", "class not found exception", exc );//$NON-NLS-1$//$NON-NLS-2$
        }
        catch ( InstantiationException exc )
        {
          logger.error ( "saveLookAndFeel", "instantiation exception", exc );//$NON-NLS-1$//$NON-NLS-2$
        }
        catch ( IllegalAccessException exc )
        {
          logger.error ( "saveLookAndFeel", "illegal access exception", exc );//$NON-NLS-1$//$NON-NLS-2$
        }
        catch ( UnsupportedLookAndFeelException exc )
        {
          logger.error ( "saveLookAndFeel",//$NON-NLS-1$
              "unsupported look and feel exception", exc );//$NON-NLS-1$
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
    if ( this.initialMouseSelectionItem.getIndex () != this.gui.jGTIComboBoxMouseSelection
        .getSelectedIndex () )
    {
      this.initialMouseSelectionItem = MouseSelectionItem
          .create ( this.gui.jGTIComboBoxMouseSelection.getSelectedIndex () );
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
      this.initialNonterminalSymbolSetItem = this.nonterminalSymbolSetItem
          .clone ();
      PreferenceManager.getInstance ().setNonterminalSymbolSetItem (
          this.nonterminalSymbolSetItem );
    }
  }


  /**
   * Saves the data of the {@link PDA} mode.
   */
  private final void savePDAMode ()
  {
    if ( this.initialPDAModeItem.getIndex () != this.gui.jGTIComboBoxPDAMode
        .getSelectedIndex () )
    {
      this.initialPDAModeItem = PDAModeItem
          .create ( this.gui.jGTIComboBoxPDAMode.getSelectedIndex () );
      PreferenceManager.getInstance ()
          .setPDAModeItem ( this.initialPDAModeItem );
      PreferenceManager.getInstance ().firePDAModeChanged (
          this.initialPDAModeItem );
    }
  }


  /**
   * Saves the data of the push down {@link AlphabetItem}.
   */
  private final void savePushDownAlphabet ()
  {
    if ( !this.initialPushDownAlphabetItem.equals ( this.pushDownAlphabetItem ) )
    {
      this.initialPushDownAlphabetItem = this.pushDownAlphabetItem.clone ();
      PreferenceManager.getInstance ().setPushDownAlphabetItem (
          this.pushDownAlphabetItem );
    }
    if ( this.initialUsePushDownAlphabet != this.gui.alphabetPanelForm.jGTICheckBoxPushDownAlphabet
        .isSelected () )
    {
      this.initialUsePushDownAlphabet = this.gui.alphabetPanelForm.jGTICheckBoxPushDownAlphabet
          .isSelected ();
      PreferenceManager.getInstance ()
          .setUsePushDownAlphabet (
              this.gui.alphabetPanelForm.jGTICheckBoxPushDownAlphabet
                  .isSelected () );
    }
  }


  /**
   * Saves the data of the start {@link NonterminalSymbolItem}.
   */
  private final void saveStartSymbol ()
  {
    if ( !this.initialStartSymbolItem.equals ( this.startSymbolItem ) )
    {
      this.initialStartSymbolItem = this.startSymbolItem.clone ();
      PreferenceManager.getInstance ().setStartSymbolItem (
          this.startSymbolItem );
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
    if ( this.initialTransitionItem.getIndex () != this.gui.jGTIComboBoxTransition
        .getSelectedIndex () )
    {
      this.initialTransitionItem = TransitionItem
          .create ( this.gui.jGTIComboBoxTransition.getSelectedIndex () );
      PreferenceManager.getInstance ().setTransitionItem (
          this.initialTransitionItem );
    }
  }


  /**
   * Saves the data of the {@link Word} mode.
   */
  private final void saveWordMode ()
  {
    if ( this.initialWordModeItem.getIndex () != this.gui.jGTIComboBoxWordMode
        .getSelectedIndex () )
    {
      this.initialWordModeItem = WordModeItem
          .create ( this.gui.jGTIComboBoxWordMode.getSelectedIndex () );
      PreferenceManager.getInstance ().setWordModeItem (
          this.initialWordModeItem );
      PreferenceManager.getInstance ().fireWordModeChanged (
          this.initialWordModeItem );
    }
  }


  /**
   * Saves the data of the zoom factor.
   */
  private final void saveZoomFactor ()
  {
    if ( this.initialZoomFactorItem.getFactor () != this.gui.jGTISliderZoom
        .getValue () )
    {
      this.initialZoomFactorItem = ZoomFactorItem
          .create ( this.gui.jGTISliderZoom.getValue () );
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
        .getParsedObject () == null )
        || ( this.gui.alphabetPanelForm.styledAlphabetParserPanelPushDown
            .getParsedObject () == null )
        || this.gui.alphabetPanelForm.styledRegexAlphabetParserPanelInput
            .getParsedObject () == null )
    {
      enabled = false;
      this.gui.jGTITabbedPane.setForegroundAt ( ALPHABET_TAB_INDEX, Color.RED );
    }
    else
    {
      this.gui.jGTITabbedPane.setForegroundAt ( ALPHABET_TAB_INDEX, null );
    }

    // Grammar
    if ( ( this.gui.terminalPanelForm.styledNonterminalSymbolSetParserPanel
        .getParsedObject () == null )
        || ( this.gui.terminalPanelForm.styledStartNonterminalSymbolParserPanel
            .getParsedObject () == null )
        || ( this.gui.terminalPanelForm.styledTerminalSymbolSetParserPanel
            .getParsedObject () == null ) )
    {
      enabled = false;
      this.gui.jGTITabbedPane.setForegroundAt ( GRAMMAR_TAB_INDEX, Color.RED );
    }
    else
    {
      this.gui.jGTITabbedPane.setForegroundAt ( GRAMMAR_TAB_INDEX, null );
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
    logger.debug ( "show", "show the preferences dialog" ); //$NON-NLS-1$ //$NON-NLS-2$
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
    this.gui.setVisible ( true );
  }
}
