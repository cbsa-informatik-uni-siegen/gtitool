package de.unisiegen.gtitool.ui.logic;


import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.JScrollBar;
import javax.swing.table.JTableHeader;

import de.unisiegen.gtitool.core.entities.InputEntity.EntityType;
import de.unisiegen.gtitool.core.entities.listener.ModifyStatusChangedListener;
import de.unisiegen.gtitool.core.exceptions.CoreException.ErrorType;
import de.unisiegen.gtitool.core.exceptions.alphabet.AlphabetException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarException;
import de.unisiegen.gtitool.core.exceptions.grammar.GrammarValidationException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineException;
import de.unisiegen.gtitool.core.exceptions.machine.MachineValidationException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbol.NonterminalSymbolException;
import de.unisiegen.gtitool.core.exceptions.nonterminalsymbolset.NonterminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.state.StateException;
import de.unisiegen.gtitool.core.exceptions.symbol.SymbolException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbol.TerminalSymbolException;
import de.unisiegen.gtitool.core.exceptions.terminalsymbolset.TerminalSymbolSetException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionException;
import de.unisiegen.gtitool.core.exceptions.transition.TransitionSymbolOnlyOneTimeException;
import de.unisiegen.gtitool.core.grammars.Grammar.GrammarType;
import de.unisiegen.gtitool.core.machines.Machine;
import de.unisiegen.gtitool.core.machines.Machine.MachineType;
import de.unisiegen.gtitool.core.preferences.listener.LanguageChangedListener;
import de.unisiegen.gtitool.core.storage.Element;
import de.unisiegen.gtitool.core.storage.exceptions.StoreException;
import de.unisiegen.gtitool.core.util.ObjectPair;
import de.unisiegen.gtitool.logger.Logger;
import de.unisiegen.gtitool.ui.Version;
import de.unisiegen.gtitool.ui.exchange.Exchange;
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.jgraph.JGTIGraph;
import de.unisiegen.gtitool.ui.logic.interfaces.EditorPanel;
import de.unisiegen.gtitool.ui.logic.interfaces.LogicClass;
import de.unisiegen.gtitool.ui.model.DefaultGrammarModel;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.model.DefaultModel;
import de.unisiegen.gtitool.ui.netbeans.MainWindowForm;
import de.unisiegen.gtitool.ui.popup.TabPopupMenu;
import de.unisiegen.gtitool.ui.popup.TabPopupMenu.TabPopupMenuType;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.preferences.item.OpenedFilesItem;
import de.unisiegen.gtitool.ui.preferences.item.RecentlyUsedFilesItem;
import de.unisiegen.gtitool.ui.storage.Storage;
import de.unisiegen.gtitool.ui.swing.JGTITabbedPane;
import de.unisiegen.gtitool.ui.swing.JGTITable;
import de.unisiegen.gtitool.ui.swing.specialized.JGTIEditorPanelTabbedPane;
import de.unisiegen.gtitool.ui.swing.specialized.JGTIMainSplitPane;
import de.unisiegen.gtitool.ui.swing.specialized.JGTIMainSplitPane.ActiveEditor;
import de.unisiegen.gtitool.ui.utils.LayoutManager;
import de.unisiegen.gtitool.ui.utils.RecentlyUsedMenuItem;


/**
 * The main programm window.
 * 
 * @author Benjamin Mies
 * @author Christian Fehler
 * @version $Id$
 */
public final class MainWindow implements LogicClass < MainWindowForm >,
    LanguageChangedListener
{

  /**
   * The {@link ButtonState} enum.
   * 
   * @author Christian Fehler
   */
  public enum ButtonState
  {
    /**
     * The general enabled button state.
     */
    ENABLED_GENERAL,

    /**
     * The undo enabled button state.
     */
    ENABLED_UNDO,

    /**
     * The redo enabled button state.
     */
    ENABLED_REDO,

    /**
     * The history enabled button state.
     */
    ENABLED_HISTORY,

    /**
     * The machine table enabled button state.
     */
    ENABLED_MACHINE_TABLE,

    /**
     * The console table enabled button state.
     */
    ENABLED_CONSOLE_TABLE,

    /**
     * The validate enabled button state.
     */
    ENABLED_VALIDATE,

    /**
     * The draft for enabled button state.
     */
    ENABLED_DRAFT_FOR,

    /**
     * The draft for machine enabled button state.
     */
    ENABLED_DRAFT_FOR_MACHINE,

    /**
     * The draft for grammar enabled button state.
     */
    ENABLED_DRAFT_FOR_GRAMMAR,

    /**
     * The edit document enabled button state.
     */
    ENABLED_EDIT_DOCUMENT,

    /**
     * The edit machine enabled button state.
     */
    ENABLED_EDIT_MACHINE,

    /**
     * The enter word enabled button state.
     */
    ENABLED_ENTER_WORD,

    /**
     * The auto layout enabled button state.
     */
    ENABLED_AUTO_LAYOUT,

    /**
     * The recently used enabled button state.
     */
    ENABLED_RECENTLY_USED,

    /**
     * The machine edit items enabled button state.
     */
    ENABLED_MACHINE_EDIT_ITEMS,

    /**
     * The navigation deactive enabled button state.
     */
    ENABLED_NAVIGATION_DEACTIVE,

    /**
     * The navigation start enabled button state.
     */
    ENABLED_NAVIGATION_START,

    /**
     * The navigation steps enabled button state.
     */
    ENABLED_NAVIGATION_STEPS,

    /**
     * The convert to enabled button state.
     */
    ENABLED_CONVERT_TO,

    /**
     * The convert to source dfa enabled button state.
     */
    ENABLED_CONVERT_TO_SOURCE_DFA,

    /**
     * The convert to source nfa enabled button state.
     */
    ENABLED_CONVERT_TO_SOURCE_NFA,

    /**
     * The convert to source enfa enabled button state.
     */
    ENABLED_CONVERT_TO_SOURCE_ENFA,

    /**
     * The convert to source pda enabled button state.
     */
    ENABLED_CONVERT_TO_SOURCE_PDA,

    /**
     * The convert to source rg enabled button state.
     */
    ENABLED_CONVERT_TO_SOURCE_RG,

    /**
     * The convert to source cfg enabled button state.
     */
    ENABLED_CONVERT_TO_SOURCE_CFG,

    /**
     * The minimize enabled button state.
     */
    ENABLED_MINIMIZE,

    /**
     * The save enabled button state.
     */
    ENABLED_SAVE,

    /**
     * The machine table selected button state.
     */
    SELECTED_MACHINE_TABLE,

    /**
     * The console table selected button state.
     */
    SELECTED_CONSOLE_TABLE,

    /**
     * The mouse selected button state.
     */
    SELECTED_MOUSE,

    /**
     * The auto step selected button state.
     */
    SELECTED_AUTO_STEP,

    /**
     * The machine visible button state.
     */
    VISIBLE_MACHINE,

    /**
     * The grammar visible button state.
     */
    VISIBLE_GRAMMAR;
  }


  /**
   * The {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger ( MainWindow.class );


  /**
   * The {@link MainWindowForm}.
   */
  private MainWindowForm gui;


  /**
   * The {@link ModifyStatusChangedListener}.
   */
  private ModifyStatusChangedListener modifyStatusChangedListener;


  /**
   * List contains the recently used files
   */
  private ArrayList < RecentlyUsedMenuItem > recentlyUsedFiles = new ArrayList < RecentlyUsedMenuItem > ();


  /**
   * The {@link ButtonState} list.
   */
  private ArrayList < ButtonState > buttonStateList = new ArrayList < ButtonState > ();


  /**
   * The {@link JGTIMainSplitPane}.
   */
  private JGTIMainSplitPane jGTIMainSplitPane;


  /**
   * Creates new form {@link MainWindow}.
   */
  public MainWindow ()
  {
    this.gui = new MainWindowForm ( this );

    // second view
    this.jGTIMainSplitPane = this.gui.getJGTIMainSplitPane ();
    this.jGTIMainSplitPane.setMainWindowForm ( this.gui );

    try
    {
      this.gui.setIconImage ( ImageIO.read ( getClass ().getResource (
          "/de/unisiegen/gtitool/ui/icon/gtitool.png" ) ) ); //$NON-NLS-1$
    }
    catch ( Exception exc )
    {
      exc.printStackTrace ();
    }
    this.gui.setTitle ( "GTI Tool " + Version.VERSION ); //$NON-NLS-1$
    this.gui.setBounds ( PreferenceManager.getInstance ()
        .getMainWindowBounds () );

    removeButtonState ( ButtonState.ENABLED_GENERAL );
    removeButtonState ( ButtonState.ENABLED_EDIT_DOCUMENT );
    removeButtonState ( ButtonState.ENABLED_CONSOLE_TABLE );
    removeButtonState ( ButtonState.ENABLED_MACHINE_TABLE );
    removeButtonState ( ButtonState.ENABLED_SAVE );
    removeButtonState ( ButtonState.ENABLED_HISTORY );
    removeButtonState ( ButtonState.ENABLED_CONVERT_TO );
    removeButtonState ( ButtonState.ENABLED_VALIDATE );
    removeButtonState ( ButtonState.ENABLED_ENTER_WORD );
    removeButtonState ( ButtonState.ENABLED_EDIT_MACHINE );
    removeButtonState ( ButtonState.ENABLED_UNDO );
    removeButtonState ( ButtonState.ENABLED_REDO );
    removeButtonState ( ButtonState.ENABLED_AUTO_LAYOUT );
    removeButtonState ( ButtonState.ENABLED_RECENTLY_USED );
    removeButtonState ( ButtonState.ENABLED_MINIMIZE );
    removeButtonState ( ButtonState.ENABLED_DRAFT_FOR );
    removeButtonState ( ButtonState.VISIBLE_MACHINE );
    removeButtonState ( ButtonState.VISIBLE_GRAMMAR );

    // Console and table visibility
    this.gui.getJCheckBoxMenuItemConsole ().setSelected (
        PreferenceManager.getInstance ().getVisibleConsole () );
    if ( PreferenceManager.getInstance ().getVisibleTable () )
    {
      addButtonState ( ButtonState.SELECTED_MACHINE_TABLE );
    }
    else
    {
      removeButtonState ( ButtonState.SELECTED_MACHINE_TABLE );
    }

    this.gui.setVisible ( true );
    if ( PreferenceManager.getInstance ().getMainWindowMaximized () )
    {
      this.gui.setExtendedState ( this.gui.getExtendedState ()
          | Frame.MAXIMIZED_BOTH );
    }
    // Language changed listener
    PreferenceManager.getInstance ().addLanguageChangedListener ( this );

    for ( File file : PreferenceManager.getInstance ()
        .getRecentlyUsedFilesItem ().getFiles () )
    {
      this.recentlyUsedFiles.add ( new RecentlyUsedMenuItem ( this, file ) );
    }
    organizeRecentlyUsedFilesMenu ();

    this.modifyStatusChangedListener = new ModifyStatusChangedListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void modifyStatusChanged ( boolean modified )
      {
        if ( modified )
        {
          addButtonState ( ButtonState.ENABLED_SAVE );
        }
        else
        {
          removeButtonState ( ButtonState.ENABLED_SAVE );
        }
      }
    };

    // second view
    boolean secondViewUsed = PreferenceManager.getInstance ()
        .getSeconsViewUsed ();
    if ( this.gui.getJCheckBoxMenuItemSecondView ().isSelected () == secondViewUsed )
    {
      this.gui.getJCheckBoxMenuItemSecondView ().setSelected ( secondViewUsed );
      handleSecondViewStateChanged ();
    }
    else
    {
      this.gui.getJCheckBoxMenuItemSecondView ().setSelected ( secondViewUsed );
    }
  }


  /**
   * Adds the given {@link ButtonState}.
   * 
   * @param buttonState The {@link ButtonState} to add.
   */
  public final void addButtonState ( ButtonState buttonState )
  {
    // enabled
    if ( ( buttonState.equals ( ButtonState.ENABLED_GENERAL ) )
        && ( !this.buttonStateList.contains ( ButtonState.ENABLED_GENERAL ) ) )
    {
      this.buttonStateList.add ( ButtonState.ENABLED_GENERAL );
      this.gui.getJGTIToolBarButtonSaveAs ().setEnabled ( true );
      this.gui.getJMenuItemSaveAs ().setEnabled ( true );
      this.gui.getJMenuItemSaveAll ().setEnabled ( true );
      this.gui.getJMenuItemClose ().setEnabled ( true );
      this.gui.getJMenuItemCloseAll ().setEnabled ( true );
    }
    else if ( ( buttonState.equals ( ButtonState.ENABLED_UNDO ) )
        && ( !this.buttonStateList.contains ( ButtonState.ENABLED_UNDO ) ) )
    {
      this.buttonStateList.add ( ButtonState.ENABLED_UNDO );
      this.gui.getJMenuItemUndo ().setEnabled ( true );
      this.gui.getJGTIToolBarButtonUndo ().setEnabled ( true );
    }
    else if ( ( buttonState.equals ( ButtonState.ENABLED_REDO ) )
        && ( !this.buttonStateList.contains ( ButtonState.ENABLED_REDO ) ) )
    {
      this.buttonStateList.add ( ButtonState.ENABLED_REDO );
      this.gui.getJMenuItemRedo ().setEnabled ( true );
      this.gui.getJGTIToolBarButtonRedo ().setEnabled ( true );
    }
    else if ( ( buttonState.equals ( ButtonState.ENABLED_HISTORY ) )
        && ( !this.buttonStateList.contains ( ButtonState.ENABLED_HISTORY ) ) )
    {
      this.buttonStateList.add ( ButtonState.ENABLED_HISTORY );
      this.gui.getJMenuItemHistory ().setEnabled ( true );
    }
    else if ( ( buttonState.equals ( ButtonState.ENABLED_MACHINE_TABLE ) )
        && ( !this.buttonStateList
            .contains ( ButtonState.ENABLED_MACHINE_TABLE ) ) )
    {
      this.buttonStateList.add ( ButtonState.ENABLED_MACHINE_TABLE );
      this.gui.getJCheckBoxMenuItemTable ().setEnabled ( true );
    }
    else if ( ( buttonState.equals ( ButtonState.ENABLED_CONSOLE_TABLE ) )
        && ( !this.buttonStateList
            .contains ( ButtonState.ENABLED_CONSOLE_TABLE ) ) )
    {
      this.buttonStateList.add ( ButtonState.ENABLED_CONSOLE_TABLE );
      this.gui.getJCheckBoxMenuItemConsole ().setEnabled ( true );
    }
    else if ( ( buttonState.equals ( ButtonState.ENABLED_VALIDATE ) )
        && ( !this.buttonStateList.contains ( ButtonState.ENABLED_VALIDATE ) ) )
    {
      this.buttonStateList.add ( ButtonState.ENABLED_VALIDATE );
      this.gui.getJMenuItemValidate ().setEnabled ( true );
    }
    else if ( ( buttonState.equals ( ButtonState.ENABLED_DRAFT_FOR ) )
        && ( !this.buttonStateList.contains ( ButtonState.ENABLED_DRAFT_FOR ) ) )
    {
      this.buttonStateList.add ( ButtonState.ENABLED_DRAFT_FOR );
      this.gui.getJMenuDraft ().setEnabled ( true );
    }
    else if ( ( buttonState.equals ( ButtonState.ENABLED_DRAFT_FOR_MACHINE ) )
        && ( !this.buttonStateList
            .contains ( ButtonState.ENABLED_DRAFT_FOR_MACHINE ) ) )
    {
      this.buttonStateList.add ( ButtonState.ENABLED_DRAFT_FOR_MACHINE );
      this.buttonStateList.remove ( ButtonState.ENABLED_DRAFT_FOR_GRAMMAR );
      this.gui.getJMenuItemDFA ().setEnabled ( true );
      this.gui.getJMenuItemNFA ().setEnabled ( true );
      this.gui.getJMenuItemENFA ().setEnabled ( true );
      this.gui.getJMenuItemPDA ().setEnabled ( true );
      this.gui.getJMenuItemRG ().setEnabled ( false );
      this.gui.getJMenuItemCFG ().setEnabled ( false );
    }
    else if ( ( buttonState.equals ( ButtonState.ENABLED_DRAFT_FOR_GRAMMAR ) )
        && ( !this.buttonStateList
            .contains ( ButtonState.ENABLED_DRAFT_FOR_GRAMMAR ) ) )
    {
      this.buttonStateList.add ( ButtonState.ENABLED_DRAFT_FOR_GRAMMAR );
      this.buttonStateList.remove ( ButtonState.ENABLED_DRAFT_FOR_MACHINE );
      this.gui.getJMenuItemDFA ().setEnabled ( false );
      this.gui.getJMenuItemNFA ().setEnabled ( false );
      this.gui.getJMenuItemENFA ().setEnabled ( false );
      this.gui.getJMenuItemPDA ().setEnabled ( false );
      this.gui.getJMenuItemRG ().setEnabled ( true );
      this.gui.getJMenuItemCFG ().setEnabled ( true );
    }
    else if ( ( buttonState.equals ( ButtonState.ENABLED_EDIT_DOCUMENT ) )
        && ( !this.buttonStateList
            .contains ( ButtonState.ENABLED_EDIT_DOCUMENT ) ) )
    {
      this.buttonStateList.add ( ButtonState.ENABLED_EDIT_DOCUMENT );
      this.gui.getJGTIToolBarButtonEditDocument ().setEnabled ( true );
    }
    else if ( ( buttonState.equals ( ButtonState.ENABLED_EDIT_MACHINE ) )
        && ( !this.buttonStateList.contains ( ButtonState.ENABLED_EDIT_MACHINE ) ) )
    {
      this.buttonStateList.add ( ButtonState.ENABLED_EDIT_MACHINE );
      this.gui.getJMenuItemEditMachine ().setEnabled ( true );
    }
    else if ( ( buttonState.equals ( ButtonState.ENABLED_ENTER_WORD ) )
        && ( !this.buttonStateList.contains ( ButtonState.ENABLED_ENTER_WORD ) ) )
    {
      this.buttonStateList.add ( ButtonState.ENABLED_ENTER_WORD );
      this.gui.getJMenuItemEnterWord ().setEnabled ( true );
    }
    else if ( ( buttonState.equals ( ButtonState.ENABLED_AUTO_LAYOUT ) )
        && ( !this.buttonStateList.contains ( ButtonState.ENABLED_AUTO_LAYOUT ) ) )
    {
      this.buttonStateList.add ( ButtonState.ENABLED_AUTO_LAYOUT );
      this.gui.getJMenuItemAutoLayout ().setEnabled ( true );
    }
    else if ( ( buttonState.equals ( ButtonState.ENABLED_RECENTLY_USED ) )
        && ( !this.buttonStateList
            .contains ( ButtonState.ENABLED_RECENTLY_USED ) ) )
    {
      this.buttonStateList.add ( ButtonState.ENABLED_RECENTLY_USED );
      this.gui.getJMenuRecentlyUsed ().setEnabled ( true );
    }
    else if ( ( buttonState.equals ( ButtonState.ENABLED_MACHINE_EDIT_ITEMS ) )
        && ( !this.buttonStateList
            .contains ( ButtonState.ENABLED_MACHINE_EDIT_ITEMS ) ) )
    {
      this.buttonStateList.add ( ButtonState.ENABLED_MACHINE_EDIT_ITEMS );
      this.gui.getJGTIToolBarToggleButtonAddState ().setEnabled ( true );
      this.gui.getJGTIToolBarToggleButtonAddTransition ().setEnabled ( true );
      this.gui.getJGTIToolBarToggleButtonFinalState ().setEnabled ( true );
      this.gui.getJGTIToolBarToggleButtonMouse ().setEnabled ( true );
      this.gui.getJGTIToolBarToggleButtonStartState ().setEnabled ( true );
    }
    else if ( ( buttonState.equals ( ButtonState.ENABLED_NAVIGATION_DEACTIVE ) )
        && ( !this.buttonStateList
            .contains ( ButtonState.ENABLED_NAVIGATION_DEACTIVE ) ) )
    {
      this.buttonStateList.add ( ButtonState.ENABLED_NAVIGATION_DEACTIVE );
      this.buttonStateList.remove ( ButtonState.ENABLED_NAVIGATION_START );
      this.buttonStateList.remove ( ButtonState.ENABLED_NAVIGATION_STEPS );
      this.gui.getJGTIToolBarButtonStart ().setEnabled ( false );
      this.gui.getJGTIToolBarButtonPrevious ().setEnabled ( false );
      this.gui.getJGTIToolBarButtonNextStep ().setEnabled ( false );
      this.gui.getJGTIToolBarToggleButtonAutoStep ().setEnabled ( false );
      this.gui.getJGTIToolBarButtonStop ().setEnabled ( false );
    }
    else if ( ( buttonState.equals ( ButtonState.ENABLED_NAVIGATION_START ) )
        && ( !this.buttonStateList
            .contains ( ButtonState.ENABLED_NAVIGATION_START ) ) )
    {
      this.buttonStateList.remove ( ButtonState.ENABLED_NAVIGATION_DEACTIVE );
      this.buttonStateList.add ( ButtonState.ENABLED_NAVIGATION_START );
      this.buttonStateList.remove ( ButtonState.ENABLED_NAVIGATION_STEPS );
      this.gui.getJGTIToolBarButtonStart ().setEnabled ( true );
      this.gui.getJGTIToolBarButtonPrevious ().setEnabled ( false );
      this.gui.getJGTIToolBarButtonNextStep ().setEnabled ( false );
      this.gui.getJGTIToolBarToggleButtonAutoStep ().setEnabled ( false );
      this.gui.getJGTIToolBarButtonStop ().setEnabled ( false );
    }
    else if ( ( buttonState.equals ( ButtonState.ENABLED_NAVIGATION_STEPS ) )
        && ( !this.buttonStateList
            .contains ( ButtonState.ENABLED_NAVIGATION_STEPS ) ) )
    {
      this.buttonStateList.remove ( ButtonState.ENABLED_NAVIGATION_DEACTIVE );
      this.buttonStateList.remove ( ButtonState.ENABLED_NAVIGATION_START );
      this.buttonStateList.add ( ButtonState.ENABLED_NAVIGATION_STEPS );
      this.gui.getJGTIToolBarButtonStart ().setEnabled ( false );
      this.gui.getJGTIToolBarButtonPrevious ().setEnabled ( true );
      this.gui.getJGTIToolBarButtonNextStep ().setEnabled ( true );
      this.gui.getJGTIToolBarToggleButtonAutoStep ().setEnabled ( true );
      this.gui.getJGTIToolBarButtonStop ().setEnabled ( true );
    }
    else if ( ( buttonState.equals ( ButtonState.ENABLED_CONVERT_TO ) )
        && ( !this.buttonStateList.contains ( ButtonState.ENABLED_CONVERT_TO ) ) )
    {
      this.buttonStateList.add ( ButtonState.ENABLED_CONVERT_TO );
      this.gui.getJMenuConvertTo ().setEnabled ( true );
    }
    else if ( ( buttonState.equals ( ButtonState.ENABLED_CONVERT_TO_SOURCE_DFA ) )
        && ( !this.buttonStateList
            .contains ( ButtonState.ENABLED_CONVERT_TO_SOURCE_DFA ) ) )
    {
      this.buttonStateList.add ( ButtonState.ENABLED_CONVERT_TO_SOURCE_DFA );
      this.buttonStateList.remove ( ButtonState.ENABLED_CONVERT_TO_SOURCE_NFA );
      this.buttonStateList.remove ( ButtonState.ENABLED_CONVERT_TO_SOURCE_ENFA );
      this.buttonStateList.remove ( ButtonState.ENABLED_CONVERT_TO_SOURCE_PDA );
      this.buttonStateList.remove ( ButtonState.ENABLED_CONVERT_TO_SOURCE_RG );
      this.buttonStateList.remove ( ButtonState.ENABLED_CONVERT_TO_SOURCE_CFG );
      this.gui.getJMenuItemConvertToDFA ().setEnabled ( false );
      this.gui.getJMenuItemConvertToNFA ().setEnabled ( false );
      this.gui.getJMenuItemConvertToENFA ().setEnabled ( false );
      this.gui.getJMenuItemConvertToPDA ().setEnabled ( false );
    }
    else if ( ( buttonState.equals ( ButtonState.ENABLED_CONVERT_TO_SOURCE_NFA ) )
        && ( !this.buttonStateList
            .contains ( ButtonState.ENABLED_CONVERT_TO_SOURCE_NFA ) ) )
    {
      this.buttonStateList.remove ( ButtonState.ENABLED_CONVERT_TO_SOURCE_DFA );
      this.buttonStateList.add ( ButtonState.ENABLED_CONVERT_TO_SOURCE_NFA );
      this.buttonStateList.remove ( ButtonState.ENABLED_CONVERT_TO_SOURCE_ENFA );
      this.buttonStateList.remove ( ButtonState.ENABLED_CONVERT_TO_SOURCE_PDA );
      this.buttonStateList.remove ( ButtonState.ENABLED_CONVERT_TO_SOURCE_RG );
      this.buttonStateList.remove ( ButtonState.ENABLED_CONVERT_TO_SOURCE_CFG );
      this.gui.getJMenuItemConvertToDFA ().setEnabled ( true );
      this.gui.getJMenuItemConvertToNFA ().setEnabled ( false );
      this.gui.getJMenuItemConvertToENFA ().setEnabled ( false );
      this.gui.getJMenuItemConvertToPDA ().setEnabled ( false );
    }
    else if ( ( buttonState
        .equals ( ButtonState.ENABLED_CONVERT_TO_SOURCE_ENFA ) )
        && ( !this.buttonStateList
            .contains ( ButtonState.ENABLED_CONVERT_TO_SOURCE_ENFA ) ) )
    {
      this.buttonStateList.remove ( ButtonState.ENABLED_CONVERT_TO_SOURCE_DFA );
      this.buttonStateList.remove ( ButtonState.ENABLED_CONVERT_TO_SOURCE_NFA );
      this.buttonStateList.add ( ButtonState.ENABLED_CONVERT_TO_SOURCE_ENFA );
      this.buttonStateList.remove ( ButtonState.ENABLED_CONVERT_TO_SOURCE_PDA );
      this.buttonStateList.remove ( ButtonState.ENABLED_CONVERT_TO_SOURCE_RG );
      this.buttonStateList.remove ( ButtonState.ENABLED_CONVERT_TO_SOURCE_CFG );
      this.gui.getJMenuItemConvertToDFA ().setEnabled ( true );
      this.gui.getJMenuItemConvertToNFA ().setEnabled ( true );
      this.gui.getJMenuItemConvertToENFA ().setEnabled ( false );
      this.gui.getJMenuItemConvertToPDA ().setEnabled ( false );
    }
    else if ( ( buttonState.equals ( ButtonState.ENABLED_CONVERT_TO_SOURCE_PDA ) )
        && ( !this.buttonStateList
            .contains ( ButtonState.ENABLED_CONVERT_TO_SOURCE_PDA ) ) )
    {
      this.buttonStateList.remove ( ButtonState.ENABLED_CONVERT_TO_SOURCE_DFA );
      this.buttonStateList.remove ( ButtonState.ENABLED_CONVERT_TO_SOURCE_NFA );
      this.buttonStateList.remove ( ButtonState.ENABLED_CONVERT_TO_SOURCE_ENFA );
      this.buttonStateList.add ( ButtonState.ENABLED_CONVERT_TO_SOURCE_PDA );
      this.buttonStateList.remove ( ButtonState.ENABLED_CONVERT_TO_SOURCE_RG );
      this.buttonStateList.remove ( ButtonState.ENABLED_CONVERT_TO_SOURCE_CFG );
      this.gui.getJMenuItemConvertToDFA ().setEnabled ( false );
      this.gui.getJMenuItemConvertToNFA ().setEnabled ( false );
      this.gui.getJMenuItemConvertToENFA ().setEnabled ( false );
      this.gui.getJMenuItemConvertToPDA ().setEnabled ( false );
    }
    else if ( ( buttonState.equals ( ButtonState.ENABLED_CONVERT_TO_SOURCE_RG ) )
        && ( !this.buttonStateList
            .contains ( ButtonState.ENABLED_CONVERT_TO_SOURCE_RG ) ) )
    {
      this.buttonStateList.remove ( ButtonState.ENABLED_CONVERT_TO_SOURCE_DFA );
      this.buttonStateList.remove ( ButtonState.ENABLED_CONVERT_TO_SOURCE_NFA );
      this.buttonStateList.remove ( ButtonState.ENABLED_CONVERT_TO_SOURCE_ENFA );
      this.buttonStateList.remove ( ButtonState.ENABLED_CONVERT_TO_SOURCE_PDA );
      this.buttonStateList.add ( ButtonState.ENABLED_CONVERT_TO_SOURCE_RG );
      this.buttonStateList.remove ( ButtonState.ENABLED_CONVERT_TO_SOURCE_CFG );
      this.gui.getJMenuItemConvertToDFA ().setEnabled ( false );
      this.gui.getJMenuItemConvertToNFA ().setEnabled ( true );
      this.gui.getJMenuItemConvertToENFA ().setEnabled ( false );
      this.gui.getJMenuItemConvertToPDA ().setEnabled ( false );
    }
    else if ( ( buttonState.equals ( ButtonState.ENABLED_CONVERT_TO_SOURCE_CFG ) )
        && ( !this.buttonStateList
            .contains ( ButtonState.ENABLED_CONVERT_TO_SOURCE_CFG ) ) )
    {
      this.buttonStateList.remove ( ButtonState.ENABLED_CONVERT_TO_SOURCE_DFA );
      this.buttonStateList.remove ( ButtonState.ENABLED_CONVERT_TO_SOURCE_NFA );
      this.buttonStateList.remove ( ButtonState.ENABLED_CONVERT_TO_SOURCE_ENFA );
      this.buttonStateList.remove ( ButtonState.ENABLED_CONVERT_TO_SOURCE_PDA );
      this.buttonStateList.remove ( ButtonState.ENABLED_CONVERT_TO_SOURCE_RG );
      this.buttonStateList.add ( ButtonState.ENABLED_CONVERT_TO_SOURCE_CFG );
      this.gui.getJMenuItemConvertToDFA ().setEnabled ( false );
      this.gui.getJMenuItemConvertToNFA ().setEnabled ( false );
      this.gui.getJMenuItemConvertToENFA ().setEnabled ( false );
      this.gui.getJMenuItemConvertToPDA ().setEnabled ( true );
    }
    else if ( ( buttonState.equals ( ButtonState.ENABLED_MINIMIZE ) )
        && ( !this.buttonStateList.contains ( ButtonState.ENABLED_MINIMIZE ) ) )
    {
      this.buttonStateList.add ( ButtonState.ENABLED_MINIMIZE );
      this.gui.getJMenuItemMinimize ().setEnabled ( true );
    }
    else if ( buttonState.equals ( ButtonState.ENABLED_SAVE ) )
    {
      if ( !this.buttonStateList.contains ( ButtonState.ENABLED_SAVE ) )
      {
        this.buttonStateList.add ( ButtonState.ENABLED_SAVE );
      }
      logger.debug ( "setSaveState", "set save status to " + Messages.QUOTE //$NON-NLS-1$//$NON-NLS-2$
          + true + Messages.QUOTE );
      EditorPanel panel = this.jGTIMainSplitPane
          .getJGTIEditorPanelTabbedPane ().getSelectedEditorPanel ();
      if ( panel != null )
      {
        this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
            .setEditorPanelTitle ( panel, "*" //$NON-NLS-1$
                + panel.getName () );
      }
      else
      {
        throw new IllegalArgumentException (
            "the save status should be false if no panel is selected" ); //$NON-NLS-1$
      }
      this.gui.getJGTIToolBarButtonSave ().setEnabled ( true );
      this.gui.getJMenuItemSave ().setEnabled ( true );
    }
    // selected
    else if ( ( buttonState.equals ( ButtonState.SELECTED_MACHINE_TABLE ) )
        && ( !this.buttonStateList
            .contains ( ButtonState.SELECTED_MACHINE_TABLE ) ) )
    {
      this.buttonStateList.add ( ButtonState.SELECTED_MACHINE_TABLE );
      this.gui.getJCheckBoxMenuItemTable ().setSelected ( true );
    }
    else if ( ( buttonState.equals ( ButtonState.SELECTED_CONSOLE_TABLE ) )
        && ( !this.buttonStateList
            .contains ( ButtonState.SELECTED_CONSOLE_TABLE ) ) )
    {
      this.buttonStateList.add ( ButtonState.SELECTED_CONSOLE_TABLE );
      this.gui.getJCheckBoxMenuItemConsole ().setSelected ( true );
    }
    else if ( buttonState.equals ( ButtonState.SELECTED_MOUSE ) )
    {
      if ( !this.buttonStateList.contains ( ButtonState.SELECTED_MOUSE ) )
      {
        this.buttonStateList.add ( ButtonState.SELECTED_MOUSE );
      }
      this.gui.getJGTIToolBarToggleButtonMouse ().setSelected ( true );
    }
    else if ( buttonState.equals ( ButtonState.SELECTED_AUTO_STEP ) )
    {
      if ( !this.buttonStateList.contains ( ButtonState.SELECTED_AUTO_STEP ) )
      {
        this.buttonStateList.add ( ButtonState.SELECTED_AUTO_STEP );
      }
      this.gui.getJGTIToolBarToggleButtonAutoStep ().setSelected ( true );
    }
    // visible
    else if ( ( buttonState.equals ( ButtonState.VISIBLE_MACHINE ) )
        && ( !this.buttonStateList.contains ( ButtonState.VISIBLE_MACHINE ) ) )
    {
      this.buttonStateList.add ( ButtonState.VISIBLE_MACHINE );
      this.gui.getJSeparatorNavigation ().setVisible ( true );
      this.gui.getJGTIToolBarToggleButtonMouse ().setVisible ( true );
      this.gui.getJGTIToolBarToggleButtonAddState ().setVisible ( true );
      this.gui.getJGTIToolBarToggleButtonStartState ().setVisible ( true );
      this.gui.getJGTIToolBarToggleButtonFinalState ().setVisible ( true );
      this.gui.getJGTIToolBarToggleButtonAddTransition ().setVisible ( true );
      this.gui.getJGTIToolBarButtonStart ().setVisible ( true );
      this.gui.getJGTIToolBarButtonPrevious ().setVisible ( true );
      this.gui.getJGTIToolBarButtonNextStep ().setVisible ( true );
      this.gui.getJGTIToolBarToggleButtonAutoStep ().setVisible ( true );
      this.gui.getJGTIToolBarButtonStop ().setVisible ( true );
    }
    else if ( ( buttonState.equals ( ButtonState.VISIBLE_GRAMMAR ) )
        && ( !this.buttonStateList.contains ( ButtonState.VISIBLE_GRAMMAR ) ) )
    {
      this.buttonStateList.add ( ButtonState.VISIBLE_GRAMMAR );
      this.gui.getJGTIToolBarButtonAddProduction ().setVisible ( true );
      this.gui.getJGTIToolBarButtonEditProduction ().setVisible ( true );
      this.gui.getJGTIToolBarButtonDeleteProduction ().setVisible ( true );
    }
  }


  /**
   * Handle auto layout action performed.
   */
  public final void doAutoLayout ()
  {
    EditorPanel panel = this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( panel instanceof MachinePanel )
    {
      MachinePanel machinePanel = ( MachinePanel ) panel;
      new LayoutManager ( machinePanel.getModel (), machinePanel
          .getRedoUndoHandler () ).doLayout ();
    }
    else
    {
      throw new RuntimeException ( "unsupported panel" ); //$NON-NLS-1$
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see LogicClass#getGUI()
   */
  public final MainWindowForm getGUI ()
  {
    return this.gui;
  }


  /**
   * Getter for the {@link ModifyStatusChangedListener}.
   * 
   * @return The {@link ModifyStatusChangedListener}.
   */
  public final ModifyStatusChangedListener getModifyStatusChangedListener ()
  {
    return this.modifyStatusChangedListener;
  }


  /**
   * Handles the action event of the about item.
   */
  public final void handleAbout ()
  {
    AboutDialog aboutDialog = new AboutDialog ( this.gui );
    aboutDialog.show ();
  }


  /**
   * Handle add production button pressed.
   */
  public final void handleAddProduction ()
  {
    EditorPanel panel = this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( panel instanceof GrammarPanel )
    {
      GrammarPanel grammarPanel = ( GrammarPanel ) panel;
      grammarPanel.handleAddProduction ();
    }
    else
    {
      throw new RuntimeException ( "unsupported panel" ); //$NON-NLS-1$
    }
  }


  /**
   * Handles the auto step stopped by exception event.
   */
  public final void handleAutoStepStopped ()
  {
    removeButtonState ( ButtonState.SELECTED_AUTO_STEP );
  }


  /**
   * Closes the selected {@link EditorPanel}.
   * 
   * @return True if the panel is closed, false if the close was canceled.
   */
  public final boolean handleClose ()
  {
    if ( this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .getSelectedEditorPanel () == null )
    {
      throw new RuntimeException ( "no selected editor panel" ); //$NON-NLS-1$
    }
    return handleClose ( this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .getSelectedEditorPanel () );
  }


  /**
   * Closes the {@link EditorPanel}.
   * 
   * @param panel The {@link EditorPanel} to close.
   * @return True if the panel is closed, false if the close was canceled.
   */
  public final boolean handleClose ( EditorPanel panel )
  {
    if ( panel.isModified () )
    {
      ConfirmDialog confirmDialog = new ConfirmDialog ( this.gui,
          Messages.getString (
              "MainWindow.CloseModifyMessage", panel.getName () ), Messages //$NON-NLS-1$
              .getString ( "MainWindow.CloseModifyTitle" ), true, true, true ); //$NON-NLS-1$
      confirmDialog.show ();

      if ( confirmDialog.isConfirmed () )
      {
        handleSave ( panel );
      }
      else if ( confirmDialog.isCanceled () )
      {
        return false;
      }
    }

    this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .removeSelectedEditorPanel ();

    // check if all editor panels are closed
    handleTabbedPaneStateChanged ();

    return true;
  }


  /**
   * Handle the close all files event
   */
  public final void handleCloseAll ()
  {
    for ( EditorPanel current : this.jGTIMainSplitPane
        .getJGTIEditorPanelTabbedPane () )
    {
      // Check if the close was canceled
      if ( !handleClose ( current ) )
      {
        break;
      }
    }
  }


  /**
   * Handles console state changes.
   */
  public final void handleConsoleStateChanged ()
  {
    EditorPanel panel = this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();

    boolean state = this.gui.getJCheckBoxMenuItemConsole ().getState ();
    if ( PreferenceManager.getInstance ().getVisibleConsole () != state )
    {
      if ( state )
      {
        addButtonState ( ButtonState.SELECTED_CONSOLE_TABLE );
      }
      else
      {
        removeButtonState ( ButtonState.SELECTED_CONSOLE_TABLE );
      }

      panel.setVisibleConsole ( state );
      PreferenceManager.getInstance ().setVisibleConsole ( state );
    }
  }


  /**
   * Handles the convert to action performed.
   * 
   * @param entityType The {@link EntityType} to convert to.
   */
  public final void handleConvertTo ( EntityType entityType )
  {
    EditorPanel panel = this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();

    // if there are no validation errors perform the action
    if ( handleValidate ( false ) )
    {
      // MachinePanel
      if ( panel instanceof MachinePanel )
      {
        MachinePanel machinePanel = ( MachinePanel ) panel;
        if ( machinePanel.getMachine ().getMachineType ().equals (
            MachineType.DFA ) )
        {
          panel.getConverter ().convert ( MachineType.DFA, entityType );
        }
        else if ( machinePanel.getMachine ().getMachineType ().equals (
            MachineType.NFA ) )
        {
          panel.getConverter ().convert ( MachineType.NFA, entityType );
        }
        else if ( machinePanel.getMachine ().getMachineType ().equals (
            MachineType.ENFA ) )
        {
          panel.getConverter ().convert ( MachineType.ENFA, entityType );
        }
        else if ( machinePanel.getMachine ().getMachineType ().equals (
            MachineType.PDA ) )
        {
          panel.getConverter ().convert ( MachineType.PDA, entityType );
        }
        else
        {
          throw new RuntimeException ( "unsupported machine type" ); //$NON-NLS-1$
        }
      }
      // GrammarPanel
      else if ( panel instanceof GrammarPanel )
      {
        GrammarPanel grammarPanel = ( GrammarPanel ) panel;
        if ( grammarPanel.getGrammar ().getGrammarType ().equals (
            GrammarType.RG ) )
        {
          panel.getConverter ().convert ( GrammarType.RG, entityType );
        }
        else if ( grammarPanel.getGrammar ().getGrammarType ().equals (
            GrammarType.CFG ) )
        {
          panel.getConverter ().convert ( GrammarType.CFG, entityType );
        }
        else
        {
          throw new RuntimeException ( "unsupported grammar type" ); //$NON-NLS-1$
        }
      }
      else
      {
        throw new RuntimeException ( "unsupported panel" ); //$NON-NLS-1$
      }
    }
  }


  /**
   * Handle delete production button pressed.
   */
  public final void handleDeleteProduction ()
  {
    EditorPanel panel = this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( panel instanceof GrammarPanel )
    {
      GrammarPanel grammarPanel = ( GrammarPanel ) panel;
      grammarPanel.handleDeleteProduction ();
    }
  }


  /**
   * Uses the active {@link EditorPanel} as draft for a new file.
   * 
   * @param grammarType The type of the new file.
   */
  public final void handleDraftFor ( GrammarType grammarType )
  {
    try
    {
      DefaultGrammarModel model = new DefaultGrammarModel (
          this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
              .getSelectedEditorPanel ().getModel ().getElement (), grammarType
              .toString () );
      EditorPanel newEditorPanel = new GrammarPanel ( this.gui, model, null );

      TreeSet < String > nameList = new TreeSet < String > ();
      int count = 0;
      for ( EditorPanel current : this.jGTIMainSplitPane
          .getJGTIEditorPanelTabbedPane () )
      {
        if ( current.getFile () == null )
        {
          nameList.add ( current.getName () );
          count++ ;
        }
      }

      String name = Messages.getString ( "MainWindow.NewFile" ) + count //$NON-NLS-1$
          + "." + grammarType.toString ().toLowerCase (); //$NON-NLS-1$
      while ( nameList.contains ( name ) )
      {
        count++ ;
        name = Messages.getString ( "MainWindow.NewFile" ) + count //$NON-NLS-1$
            + "." + grammarType.toString ().toLowerCase (); //$NON-NLS-1$
      }

      newEditorPanel.setName ( name );
      this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ().addEditorPanel (
          newEditorPanel );
      newEditorPanel
          .addModifyStatusChangedListener ( this.modifyStatusChangedListener );
      this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
          .setSelectedEditorPanel ( newEditorPanel );

      addButtonState ( ButtonState.ENABLED_GENERAL );
      addButtonState ( ButtonState.ENABLED_EDIT_DOCUMENT );
      addButtonState ( ButtonState.ENABLED_VALIDATE );
      addButtonState ( ButtonState.ENABLED_DRAFT_FOR );
    }
    catch ( StoreException exc )
    {
      InfoDialog infoDialog = new InfoDialog ( this.gui, exc.getMessage (),
          Messages.getString ( "MainWindow.ErrorLoad" ) ); //$NON-NLS-1$
      infoDialog.show ();
    }
    catch ( NonterminalSymbolSetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( NonterminalSymbolException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( TerminalSymbolSetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( TerminalSymbolException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }


  /**
   * Uses the active {@link EditorPanel} as draft for a new file.
   * 
   * @param machineType The type of the new file.
   */
  public final void handleDraftFor ( MachineType machineType )
  {
    try
    {
      DefaultMachineModel model = new DefaultMachineModel (
          this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
              .getSelectedEditorPanel ().getModel ().getElement (), machineType
              .toString () );
      EditorPanel newEditorPanel = new MachinePanel ( this.gui, model, null );

      TreeSet < String > nameList = new TreeSet < String > ();
      int count = 0;
      for ( EditorPanel current : this.jGTIMainSplitPane
          .getJGTIEditorPanelTabbedPane () )
      {
        if ( current.getFile () == null )
        {
          nameList.add ( current.getName () );
          count++ ;
        }
      }

      String name = Messages.getString ( "MainWindow.NewFile" ) + count //$NON-NLS-1$
          + "." + machineType.toString ().toLowerCase (); //$NON-NLS-1$
      while ( nameList.contains ( name ) )
      {
        count++ ;
        name = Messages.getString ( "MainWindow.NewFile" ) + count //$NON-NLS-1$
            + "." + machineType.toString ().toLowerCase (); //$NON-NLS-1$
      }

      newEditorPanel.setName ( name );
      this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ().addEditorPanel (
          newEditorPanel );
      newEditorPanel
          .addModifyStatusChangedListener ( this.modifyStatusChangedListener );
      this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
          .setSelectedEditorPanel ( newEditorPanel );

      addButtonState ( ButtonState.ENABLED_GENERAL );
      addButtonState ( ButtonState.ENABLED_EDIT_DOCUMENT );
      addButtonState ( ButtonState.ENABLED_VALIDATE );
      addButtonState ( ButtonState.ENABLED_DRAFT_FOR );
    }
    catch ( StoreException exc )
    {
      InfoDialog infoDialog = new InfoDialog ( this.gui, exc.getMessage (),
          Messages.getString ( "MainWindow.ErrorLoad" ) ); //$NON-NLS-1$
      infoDialog.show ();
    }
    catch ( TransitionSymbolOnlyOneTimeException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( StateException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( SymbolException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( TransitionException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
  }


  /**
   * Handle edit document action in the toolbar.
   */
  public final void handleEditDocument ()
  {
    EditorPanel panel = this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    panel.handleToolbarEditDocument ();
  }


  /**
   * Handles the edit machine event.
   */
  public final void handleEditMachine ()
  {
    EditorPanel panel = this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( ! ( panel instanceof MachinePanel ) )
    {
      throw new IllegalArgumentException ( "not a machine panel" ); //$NON-NLS-1$
    }

    MachinePanel machinePanel = ( MachinePanel ) panel;
    machinePanel.handleEditMachine ();
    machinePanel.setVisibleConsole ( this.gui.getJCheckBoxMenuItemConsole ()
        .getState () );

    addButtonState ( ButtonState.ENABLED_MACHINE_EDIT_ITEMS );
    addButtonState ( ButtonState.ENABLED_NAVIGATION_DEACTIVE );
    addButtonState ( ButtonState.ENABLED_CONSOLE_TABLE );
    addButtonState ( ButtonState.ENABLED_ENTER_WORD );
    addButtonState ( ButtonState.ENABLED_VALIDATE );
    addButtonState ( ButtonState.ENABLED_AUTO_LAYOUT );
    addButtonState ( ButtonState.ENABLED_CONVERT_TO );
    addButtonState ( ButtonState.ENABLED_DRAFT_FOR );

    removeButtonState ( ButtonState.ENABLED_EDIT_MACHINE );
  }


  /**
   * Handle edit production button pressed.
   */
  public final void handleEditProduction ()
  {
    EditorPanel panel = this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( panel instanceof GrammarPanel )
    {
      GrammarPanel grammarPanel = ( GrammarPanel ) panel;
      grammarPanel.handleEditProduction ();
    }
  }


  /**
   * Handle the action event of the enter word item.
   */
  public final void handleEnterWord ()
  {
    EditorPanel panel = this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( ! ( panel instanceof MachinePanel ) )
    {
      throw new IllegalArgumentException ( "not a machine panel" ); //$NON-NLS-1$
    }
    MachinePanel machinePanel = ( MachinePanel ) panel;

    // if there are no validation errors perform the action
    if ( handleValidate ( false ) )
    {
      removeButtonState ( ButtonState.ENABLED_EDIT_DOCUMENT );
      removeButtonState ( ButtonState.ENABLED_CONSOLE_TABLE );
      removeButtonState ( ButtonState.ENABLED_MACHINE_EDIT_ITEMS );
      addButtonState ( ButtonState.ENABLED_NAVIGATION_START );
      machinePanel.handleEnterWord ();
      removeButtonState ( ButtonState.SELECTED_CONSOLE_TABLE );
      machinePanel.setVisibleConsole ( false );
      removeButtonState ( ButtonState.ENABLED_ENTER_WORD );
      addButtonState ( ButtonState.ENABLED_EDIT_MACHINE );
      removeButtonState ( ButtonState.ENABLED_VALIDATE );
      removeButtonState ( ButtonState.ENABLED_AUTO_LAYOUT );
      removeButtonState ( ButtonState.ENABLED_CONVERT_TO );
      removeButtonState ( ButtonState.ENABLED_DRAFT_FOR );
    }
  }


  /**
   * Handles the {@link Exchange}.
   */
  public final void handleExchange ()
  {
    EditorPanel panel = this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( panel == null )
    {
      ExchangeDialog exchangeDialog = new ExchangeDialog ( this, null, null );
      exchangeDialog.show ();
    }
    else
    {
      panel.handleExchange ();
    }
  }


  /**
   * Handles the history event.
   */
  public final void handleHistory ()
  {
    if ( this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .getSelectedEditorPanel () instanceof MachinePanel )
    {
      MachinePanel machinePanel = ( MachinePanel ) this.jGTIMainSplitPane
          .getJGTIEditorPanelTabbedPane ().getSelectedEditorPanel ();
      machinePanel.handleHistory ();
    }
    else
    {
      throw new RuntimeException ( "the selected panel is not a machine panel" ); //$NON-NLS-1$
    }
  }


  /**
   * Minimize the active {@link Machine}.
   */
  public void handleMinimize ()
  {
    EditorPanel panel = this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( panel instanceof MachinePanel )
    {
      // if there are no validation errors perform the action
      if ( handleValidate ( false ) )
      {
        MachinePanel machinePanel = ( MachinePanel ) panel;
        MinimizeMachineDialog dialog = new MinimizeMachineDialog ( this.gui,
            machinePanel );
        dialog.show ();
      }
    }
    else
    {
      throw new RuntimeException ( "unsupported panel" ); //$NON-NLS-1$
    }
  }


  /**
   * Handles the new event.
   */
  public final void handleNew ()
  {
    NewDialog newDialog = new NewDialog ( this.gui );
    newDialog.show ();
    EditorPanel newEditorPanel = newDialog.getEditorPanel ();
    if ( newEditorPanel != null )
    {
      TreeSet < String > nameList = new TreeSet < String > ();
      int count = 0;
      for ( EditorPanel current : this.jGTIMainSplitPane
          .getJGTIEditorPanelTabbedPane () )
      {
        if ( current.getFile () == null )
        {
          nameList.add ( current.getName () );
          count++ ;
        }
      }

      String name = Messages.getString ( "MainWindow.NewFile" ) + count //$NON-NLS-1$
          + newDialog.getEditorPanel ().getFileEnding ();
      while ( nameList.contains ( name ) )
      {
        count++ ;
        name = Messages.getString ( "MainWindow.NewFile" ) + count //$NON-NLS-1$
            + newDialog.getEditorPanel ().getFileEnding ();
      }

      newEditorPanel.setName ( name );
      this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ().addEditorPanel (
          newEditorPanel );
      newEditorPanel
          .addModifyStatusChangedListener ( this.modifyStatusChangedListener );
      this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
          .setSelectedEditorPanel ( newEditorPanel );

      addButtonState ( ButtonState.ENABLED_GENERAL );
      addButtonState ( ButtonState.ENABLED_EDIT_DOCUMENT );
      addButtonState ( ButtonState.ENABLED_VALIDATE );
      addButtonState ( ButtonState.ENABLED_DRAFT_FOR );
      addButtonState ( ButtonState.ENABLED_MACHINE_EDIT_ITEMS );
    }
  }


  /**
   * Handle the new event with a given {@link DefaultModel}
   * 
   * @param model The {@link DefaultModel}.
   */
  public void handleNew ( DefaultModel model )
  {
    if ( model != null )
    {
      EditorPanel newEditorPanel;
      if ( model instanceof DefaultMachineModel )
      {
        newEditorPanel = new MachinePanel ( this.gui,
            ( DefaultMachineModel ) model, null );
      }
      else if ( model instanceof DefaultGrammarModel )
      {
        newEditorPanel = new GrammarPanel ( this.gui,
            ( DefaultGrammarModel ) model, null );
      }
      else
      {
        throw new RuntimeException ( "incorrect model" ); //$NON-NLS-1$
      }

      TreeSet < String > nameList = new TreeSet < String > ();
      int count = 0;
      for ( EditorPanel current : this.jGTIMainSplitPane
          .getJGTIEditorPanelTabbedPane () )
      {
        if ( current.getFile () == null )
        {
          nameList.add ( current.getName () );
          count++ ;
        }
      }

      String name = Messages.getString ( "MainWindow.NewFile" ) + count //$NON-NLS-1$
          + newEditorPanel.getFileEnding ();
      while ( nameList.contains ( name ) )
      {
        count++ ;
        name = Messages.getString ( "MainWindow.NewFile" ) + count //$NON-NLS-1$
            + newEditorPanel.getFileEnding ();
      }

      newEditorPanel.setName ( name );
      this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ().addEditorPanel (
          newEditorPanel );
      newEditorPanel
          .addModifyStatusChangedListener ( this.modifyStatusChangedListener );
      this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
          .setSelectedEditorPanel ( newEditorPanel );

      addButtonState ( ButtonState.ENABLED_GENERAL );
      addButtonState ( ButtonState.ENABLED_EDIT_DOCUMENT );
      addButtonState ( ButtonState.ENABLED_VALIDATE );
      addButtonState ( ButtonState.ENABLED_DRAFT_FOR );
      addButtonState ( ButtonState.ENABLED_MACHINE_EDIT_ITEMS );
    }
  }


  /**
   * Handles the new event with a given {@link Element}.
   * 
   * @param element The {@link Element}.
   * @param autoLayout The auto layout flag, if true the new file is auto
   *          layouted.
   */
  public final void handleNew ( Element element, boolean autoLayout )
  {
    DefaultModel model = null;
    try
    {
      if ( element.getName ().equals ( "MachineModel" ) ) //$NON-NLS-1$
      {
        model = new DefaultMachineModel ( element, null );

        if ( autoLayout )
        {
          new LayoutManager ( ( DefaultMachineModel ) model, null ).doLayout ();
        }
      }
      else if ( element.getName ().equals ( "GrammarModel" ) ) //$NON-NLS-1$
      {
        model = new DefaultGrammarModel ( element, null );
      }
      else
      {
        throw new IllegalArgumentException ( "unsupported model" ); //$NON-NLS-1$
      }
    }
    catch ( TransitionSymbolOnlyOneTimeException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( StateException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( SymbolException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( AlphabetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( TransitionException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( StoreException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( NonterminalSymbolSetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( NonterminalSymbolException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( TerminalSymbolSetException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    catch ( TerminalSymbolException exc )
    {
      exc.printStackTrace ();
      System.exit ( 1 );
    }
    handleNew ( model );
  }


  /**
   * Handles the open event.
   */
  public final void handleOpen ()
  {
    OpenDialog openDialog = new OpenDialog ( this.gui, PreferenceManager
        .getInstance ().getWorkingPath () );
    openDialog.show ();
    if ( ( !openDialog.isConfirmed () )
        || ( openDialog.getSelectedFile () == null ) )
    {
      return;
    }

    for ( File file : openDialog.getSelectedFiles () )
    {
      openFile ( file, true );
    }
    PreferenceManager.getInstance ().setWorkingPath (
        openDialog.getSelectedFile ().getParentFile ().getAbsolutePath () );
  }


  /**
   * Handle the action event of the preferences item.
   */
  public final void handlePreferences ()
  {
    PreferencesDialog preferencesDialog = new PreferencesDialog ( this.gui );
    preferencesDialog.show ();
  }


  /**
   * Handles the quit event.
   */
  public final void handleQuit ()
  {
    // Active file
    File activeFile = this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .getSelectedEditorPanel () == null ? null : this.jGTIMainSplitPane
        .getJGTIEditorPanelTabbedPane ().getSelectedEditorPanel ().getFile ();

    // Opened file
    ArrayList < ObjectPair < File, ActiveEditor > > openedFiles = new ArrayList < ObjectPair < File, ActiveEditor > > ();
    for ( EditorPanel current : this.jGTIMainSplitPane
        .getJGTIEditorPanelTabbedPaneLeft () )
    {
      if ( current.getFile () != null )
      {
        openedFiles.add ( new ObjectPair < File, ActiveEditor > ( current
            .getFile (), ActiveEditor.LEFT_EDITOR ) );
      }
    }
    for ( EditorPanel current : this.jGTIMainSplitPane
        .getJGTIEditorPanelTabbedPaneRight () )
    {
      if ( current.getFile () != null )
      {
        openedFiles.add ( new ObjectPair < File, ActiveEditor > ( current
            .getFile (), ActiveEditor.RIGHT_EDITOR ) );
      }
    }

    // close the left tabs
    for ( int i = this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPaneLeft ()
        .getComponentCount () - 1 ; i >= 0 ; i-- )
    {
      EditorPanel current = this.jGTIMainSplitPane
          .getJGTIEditorPanelTabbedPaneLeft ().getEditorPanel ( i );
      if ( current.isModified () )
      {
        this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPaneLeft ()
            .setSelectedEditorPanel ( current );

        ConfirmDialog confirmDialog = new ConfirmDialog ( this.gui,
            Messages.getString (
                "MainWindow.CloseModifyMessage", current.getName () ), Messages //$NON-NLS-1$
                .getString ( "MainWindow.CloseModifyTitle" ), true, true, true ); //$NON-NLS-1$
        confirmDialog.show ();

        if ( confirmDialog.isConfirmed () )
        {
          File file = current.handleSave ();
          if ( file != null )
          {
            this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPaneLeft ()
                .setEditorPanelTitle ( current, file.getName () );
          }
        }
        else if ( confirmDialog.isCanceled () )
        {
          return;
        }
      }
      this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPaneLeft ()
          .removeEditorPanel ( current );
    }

    // close the right tabs
    for ( int i = this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPaneRight ()
        .getComponentCount () - 1 ; i >= 0 ; i-- )
    {
      EditorPanel current = this.jGTIMainSplitPane
          .getJGTIEditorPanelTabbedPaneRight ().getEditorPanel ( i );
      if ( current.isModified () )
      {
        this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPaneRight ()
            .setSelectedEditorPanel ( current );

        ConfirmDialog confirmDialog = new ConfirmDialog ( this.gui,
            Messages.getString (
                "MainWindow.CloseModifyMessage", current.getName () ), Messages //$NON-NLS-1$
                .getString ( "MainWindow.CloseModifyTitle" ), true, true, true ); //$NON-NLS-1$
        confirmDialog.show ();

        if ( confirmDialog.isConfirmed () )
        {
          File file = current.handleSave ();
          if ( file != null )
          {
            this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPaneRight ()
                .setEditorPanelTitle ( current, file.getName () );
          }
        }
        else if ( confirmDialog.isCanceled () )
        {
          return;
        }
      }
      this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPaneRight ()
          .removeEditorPanel ( current );
    }

    PreferenceManager.getInstance ().setMainWindowPreferences ( this.gui );
    PreferenceManager.getInstance ().setOpenedFilesItem (
        new OpenedFilesItem ( openedFiles, activeFile ) );

    ArrayList < File > files = new ArrayList < File > ();
    for ( RecentlyUsedMenuItem item : this.recentlyUsedFiles )
    {
      files.add ( item.getFile () );
    }
    PreferenceManager.getInstance ().setRecentlyUsedFilesItem (
        new RecentlyUsedFilesItem ( files ) );

    // second view
    PreferenceManager.getInstance ().setSecondViewUsed (
        this.gui.getJCheckBoxMenuItemSecondView ().isSelected () );

    // system exit
    System.exit ( 0 );
  }


  /**
   * Handles the reachable states event.
   */
  public final void handleReachableStates ()
  {
    if ( this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .getSelectedEditorPanel () instanceof MachinePanel )
    {
      MachinePanel machinePanel = ( MachinePanel ) this.jGTIMainSplitPane
          .getJGTIEditorPanelTabbedPane ().getSelectedEditorPanel ();
      machinePanel.handleReachableStates ();
    }
    else
    {
      throw new RuntimeException ( "the selected panel is not a machine panel" ); //$NON-NLS-1$
    }
  }


  /**
   * Handle redo button pressed
   */
  public final void handleRedo ()
  {
    EditorPanel panel = this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( panel != null )
    {
      panel.handleRedo ();
    }
  }


  /**
   * Handles the save file event on the selected editor panel.
   */
  public final void handleSave ()
  {
    if ( this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .getSelectedEditorPanel () == null )
    {
      throw new RuntimeException ( "no selected editor panel" ); //$NON-NLS-1$
    }
    handleSave ( this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .getSelectedEditorPanel () );
  }


  /**
   * Handles the save file event.
   * 
   * @param panel The {@link EditorPanel} to be saved
   */
  public final void handleSave ( EditorPanel panel )
  {
    File file = panel.handleSave ();
    if ( file != null )
    {
      RecentlyUsedMenuItem item = new RecentlyUsedMenuItem ( this, file );
      this.recentlyUsedFiles.remove ( item );
      this.recentlyUsedFiles.add ( 0, item );
      organizeRecentlyUsedFilesMenu ();

      for ( EditorPanel current : this.jGTIMainSplitPane
          .getJGTIEditorPanelTabbedPane () )
      {
        if ( ( !current.equals ( this.jGTIMainSplitPane
            .getJGTIEditorPanelTabbedPane ().getSelectedEditorPanel () ) && file
            .equals ( current.getFile () ) ) )
        {
          this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
              .removeEditorPanel ( current );
        }
      }
      this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
          .setEditorPanelTitle ( panel, file.getName () );
    }
  }


  /**
   * Handle the save all files event
   */
  public final void handleSaveAll ()
  {
    EditorPanel active = this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    for ( EditorPanel current : this.jGTIMainSplitPane
        .getJGTIEditorPanelTabbedPane () )
    {
      this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
          .setSelectedEditorPanel ( current );
      handleSave ( current );
    }
    this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .setSelectedEditorPanel ( active );
  }


  /**
   * Handle the save as event.
   */
  public final void handleSaveAs ()
  {
    EditorPanel panel = this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    File file = panel.handleSaveAs ();
    if ( file != null )
    {
      RecentlyUsedMenuItem item = new RecentlyUsedMenuItem ( this, file );
      this.recentlyUsedFiles.remove ( item );
      this.recentlyUsedFiles.add ( 0, item );
      organizeRecentlyUsedFilesMenu ();
      for ( EditorPanel current : this.jGTIMainSplitPane
          .getJGTIEditorPanelTabbedPane () )
      {
        if ( ( !current.equals ( this.jGTIMainSplitPane
            .getJGTIEditorPanelTabbedPane ().getSelectedEditorPanel () ) && file
            .equals ( current.getFile () ) ) )
        {
          this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
              .removeEditorPanel ( current );
        }
      }
      this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
          .setEditorPanelTitle ( panel, file.getName () );
    }
  }


  /**
   * Handles the mouse released event on a second view component.
   * 
   * @param event The {@link MouseEvent}.
   */
  public final void handleSecondViewMouseReleased ( MouseEvent event )
  {
    logger.debug ( "handleSecondViewMouseReleased", //$NON-NLS-1$
        "handle second view mouse released" );//$NON-NLS-1$

    if ( event.getSource () instanceof JGTITabbedPane )
    {
      if ( this.gui.getJGTIEditorPanelTabbedPaneLeft () == event.getSource () )
      {
        if ( !this.jGTIMainSplitPane.getActiveEditor ().equals (
            ActiveEditor.LEFT_EDITOR ) )
        {
          this.jGTIMainSplitPane.setActiveEditor ( ActiveEditor.LEFT_EDITOR );
          handleTabbedPaneStateChanged ();
        }
        return;
      }
      if ( this.gui.getJGTIEditorPanelTabbedPaneRight () == event.getSource () )
      {
        if ( !this.jGTIMainSplitPane.getActiveEditor ().equals (
            ActiveEditor.RIGHT_EDITOR ) )
        {
          this.jGTIMainSplitPane.setActiveEditor ( ActiveEditor.RIGHT_EDITOR );
          handleTabbedPaneStateChanged ();
        }
        return;
      }
      if ( this.gui.getJGTIEditorPanelTabbedPaneLeft ()
          .getSelectedEditorPanel () instanceof MachinePanel )
      {
        MachinePanel machinePanel = ( MachinePanel ) this.gui
            .getJGTIEditorPanelTabbedPaneLeft ().getSelectedEditorPanel ();

        if ( machinePanel.getJTabbedPaneConsole () == event.getSource () )
        {
          if ( !this.jGTIMainSplitPane.getActiveEditor ().equals (
              ActiveEditor.LEFT_EDITOR ) )
          {
            this.jGTIMainSplitPane.setActiveEditor ( ActiveEditor.LEFT_EDITOR );
            handleTabbedPaneStateChanged ();
          }
          return;
        }
      }
      if ( this.gui.getJGTIEditorPanelTabbedPaneRight ()
          .getSelectedEditorPanel () instanceof MachinePanel )
      {
        MachinePanel machinePanel = ( MachinePanel ) this.gui
            .getJGTIEditorPanelTabbedPaneRight ().getSelectedEditorPanel ();

        if ( machinePanel.getJTabbedPaneConsole () == event.getSource () )
        {
          if ( !this.jGTIMainSplitPane.getActiveEditor ().equals (
              ActiveEditor.RIGHT_EDITOR ) )
          {
            this.jGTIMainSplitPane.setActiveEditor ( ActiveEditor.RIGHT_EDITOR );
            handleTabbedPaneStateChanged ();
          }
          return;
        }
      }
      if ( this.gui.getJGTIEditorPanelTabbedPaneLeft ()
          .getSelectedEditorPanel () instanceof GrammarPanel )
      {
        GrammarPanel grammarPanel = ( GrammarPanel ) this.gui
            .getJGTIEditorPanelTabbedPaneLeft ().getSelectedEditorPanel ();

        if ( grammarPanel.getJTabbedPaneConsole () == event.getSource () )
        {
          if ( !this.jGTIMainSplitPane.getActiveEditor ().equals (
              ActiveEditor.LEFT_EDITOR ) )
          {
            this.jGTIMainSplitPane.setActiveEditor ( ActiveEditor.LEFT_EDITOR );
            handleTabbedPaneStateChanged ();
          }
          return;
        }
      }
      if ( this.gui.getJGTIEditorPanelTabbedPaneRight ()
          .getSelectedEditorPanel () instanceof GrammarPanel )
      {
        GrammarPanel grammarPanel = ( GrammarPanel ) this.gui
            .getJGTIEditorPanelTabbedPaneRight ().getSelectedEditorPanel ();

        if ( grammarPanel.getJTabbedPaneConsole () == event.getSource () )
        {
          if ( !this.jGTIMainSplitPane.getActiveEditor ().equals (
              ActiveEditor.RIGHT_EDITOR ) )
          {
            this.jGTIMainSplitPane.setActiveEditor ( ActiveEditor.RIGHT_EDITOR );
            handleTabbedPaneStateChanged ();
          }
          return;
        }
      }
    }
    else if ( event.getSource () instanceof JGTIGraph )
    {
      if ( this.gui.getJGTIEditorPanelTabbedPaneLeft ()
          .getSelectedEditorPanel () instanceof MachinePanel )
      {
        MachinePanel machinePanel = ( MachinePanel ) this.gui
            .getJGTIEditorPanelTabbedPaneLeft ().getSelectedEditorPanel ();

        if ( machinePanel.getJGTIGraph () == event.getSource () )
        {
          if ( !this.jGTIMainSplitPane.getActiveEditor ().equals (
              ActiveEditor.LEFT_EDITOR ) )
          {
            this.jGTIMainSplitPane.setActiveEditor ( ActiveEditor.LEFT_EDITOR );
            handleTabbedPaneStateChanged ();
          }
          return;
        }
      }
      if ( this.gui.getJGTIEditorPanelTabbedPaneRight ()
          .getSelectedEditorPanel () instanceof MachinePanel )
      {
        MachinePanel machinePanel = ( MachinePanel ) this.gui
            .getJGTIEditorPanelTabbedPaneRight ().getSelectedEditorPanel ();

        if ( machinePanel.getJGTIGraph () == event.getSource () )
        {
          if ( !this.jGTIMainSplitPane.getActiveEditor ().equals (
              ActiveEditor.RIGHT_EDITOR ) )
          {
            this.jGTIMainSplitPane.setActiveEditor ( ActiveEditor.RIGHT_EDITOR );
            handleTabbedPaneStateChanged ();
          }
          return;
        }
      }
    }
    else if ( event.getSource () instanceof JGTITable )
    {
      if ( this.gui.getJGTIEditorPanelTabbedPaneLeft ()
          .getSelectedEditorPanel () instanceof MachinePanel )
      {
        MachinePanel machinePanel = ( MachinePanel ) this.gui
            .getJGTIEditorPanelTabbedPaneLeft ().getSelectedEditorPanel ();

        if ( ( machinePanel.getGUI ().jGTITableMachine == event.getSource () )
            || ( machinePanel.getGUI ().jGTITableMachinePDA == event
                .getSource () )
            || ( machinePanel.getGUI ().jGTITableErrors == event.getSource () )
            || ( machinePanel.getGUI ().jGTITableWarnings == event.getSource () ) )
        {
          if ( !this.jGTIMainSplitPane.getActiveEditor ().equals (
              ActiveEditor.LEFT_EDITOR ) )
          {
            this.jGTIMainSplitPane.setActiveEditor ( ActiveEditor.LEFT_EDITOR );
            handleTabbedPaneStateChanged ();
          }
          return;
        }
      }
      if ( this.gui.getJGTIEditorPanelTabbedPaneRight ()
          .getSelectedEditorPanel () instanceof MachinePanel )
      {
        MachinePanel machinePanel = ( MachinePanel ) this.gui
            .getJGTIEditorPanelTabbedPaneRight ().getSelectedEditorPanel ();

        if ( ( machinePanel.getGUI ().jGTITableMachine == event.getSource () )
            || ( machinePanel.getGUI ().jGTITableMachinePDA == event
                .getSource () )
            || ( machinePanel.getGUI ().jGTITableErrors == event.getSource () )
            || ( machinePanel.getGUI ().jGTITableWarnings == event.getSource () ) )
        {
          if ( !this.jGTIMainSplitPane.getActiveEditor ().equals (
              ActiveEditor.RIGHT_EDITOR ) )
          {
            this.jGTIMainSplitPane.setActiveEditor ( ActiveEditor.RIGHT_EDITOR );
            handleTabbedPaneStateChanged ();
          }
          return;
        }
      }
      if ( this.gui.getJGTIEditorPanelTabbedPaneLeft ()
          .getSelectedEditorPanel () instanceof GrammarPanel )
      {
        GrammarPanel grammarPanel = ( GrammarPanel ) this.gui
            .getJGTIEditorPanelTabbedPaneLeft ().getSelectedEditorPanel ();

        if ( ( grammarPanel.getGUI ().jGTITableGrammar == event.getSource () )
            || ( grammarPanel.getGUI ().jGTITableErrors == event.getSource () )
            || ( grammarPanel.getGUI ().jGTITableWarnings == event.getSource () ) )
        {
          if ( !this.jGTIMainSplitPane.getActiveEditor ().equals (
              ActiveEditor.LEFT_EDITOR ) )
          {
            this.jGTIMainSplitPane.setActiveEditor ( ActiveEditor.LEFT_EDITOR );
            handleTabbedPaneStateChanged ();
          }
          return;
        }
      }
      if ( this.gui.getJGTIEditorPanelTabbedPaneRight ()
          .getSelectedEditorPanel () instanceof GrammarPanel )
      {
        GrammarPanel grammarPanel = ( GrammarPanel ) this.gui
            .getJGTIEditorPanelTabbedPaneRight ().getSelectedEditorPanel ();

        if ( ( grammarPanel.getGUI ().jGTITableGrammar == event.getSource () )
            || ( grammarPanel.getGUI ().jGTITableErrors == event.getSource () )
            || ( grammarPanel.getGUI ().jGTITableWarnings == event.getSource () ) )
        {
          if ( !this.jGTIMainSplitPane.getActiveEditor ().equals (
              ActiveEditor.RIGHT_EDITOR ) )
          {
            this.jGTIMainSplitPane.setActiveEditor ( ActiveEditor.RIGHT_EDITOR );
            handleTabbedPaneStateChanged ();
          }
          return;
        }
      }
    }
    else if ( event.getSource () instanceof JScrollBar )
    {
      if ( this.gui.getJGTIEditorPanelTabbedPaneLeft ()
          .getSelectedEditorPanel () instanceof MachinePanel )
      {
        MachinePanel machinePanel = ( MachinePanel ) this.gui
            .getJGTIEditorPanelTabbedPaneLeft ().getSelectedEditorPanel ();

        if ( ( machinePanel.getGUI ().jGTIScrollPaneErrors
            .getHorizontalScrollBar () == event.getSource () )
            || ( machinePanel.getGUI ().jGTIScrollPaneErrors
                .getVerticalScrollBar () == event.getSource () )
            || ( machinePanel.getGUI ().jGTIScrollPaneGraph
                .getHorizontalScrollBar () == event.getSource () )
            || ( machinePanel.getGUI ().jGTIScrollPaneGraph
                .getVerticalScrollBar () == event.getSource () )
            || ( machinePanel.getGUI ().jGTIScrollPaneMachine
                .getHorizontalScrollBar () == event.getSource () )
            || ( machinePanel.getGUI ().jGTIScrollPaneMachine
                .getVerticalScrollBar () == event.getSource () )
            || ( machinePanel.getGUI ().jGTIScrollPaneMachinePDA
                .getHorizontalScrollBar () == event.getSource () )
            || ( machinePanel.getGUI ().jGTIScrollPaneMachinePDA
                .getVerticalScrollBar () == event.getSource () )
            || ( machinePanel.getGUI ().jGTIScrollPaneWarnings
                .getHorizontalScrollBar () == event.getSource () )
            || ( machinePanel.getGUI ().jGTIScrollPaneWarnings
                .getVerticalScrollBar () == event.getSource () ) )
        {
          if ( !this.jGTIMainSplitPane.getActiveEditor ().equals (
              ActiveEditor.LEFT_EDITOR ) )
          {
            this.jGTIMainSplitPane.setActiveEditor ( ActiveEditor.LEFT_EDITOR );
            handleTabbedPaneStateChanged ();
          }
          return;
        }
      }
      if ( this.gui.getJGTIEditorPanelTabbedPaneRight ()
          .getSelectedEditorPanel () instanceof MachinePanel )
      {
        MachinePanel machinePanel = ( MachinePanel ) this.gui
            .getJGTIEditorPanelTabbedPaneRight ().getSelectedEditorPanel ();

        if ( ( machinePanel.getGUI ().jGTIScrollPaneErrors
            .getHorizontalScrollBar () == event.getSource () )
            || ( machinePanel.getGUI ().jGTIScrollPaneErrors
                .getVerticalScrollBar () == event.getSource () )
            || ( machinePanel.getGUI ().jGTIScrollPaneGraph
                .getHorizontalScrollBar () == event.getSource () )
            || ( machinePanel.getGUI ().jGTIScrollPaneGraph
                .getVerticalScrollBar () == event.getSource () )
            || ( machinePanel.getGUI ().jGTIScrollPaneMachine
                .getHorizontalScrollBar () == event.getSource () )
            || ( machinePanel.getGUI ().jGTIScrollPaneMachine
                .getVerticalScrollBar () == event.getSource () )
            || ( machinePanel.getGUI ().jGTIScrollPaneMachinePDA
                .getHorizontalScrollBar () == event.getSource () )
            || ( machinePanel.getGUI ().jGTIScrollPaneMachinePDA
                .getVerticalScrollBar () == event.getSource () )
            || ( machinePanel.getGUI ().jGTIScrollPaneWarnings
                .getHorizontalScrollBar () == event.getSource () )
            || ( machinePanel.getGUI ().jGTIScrollPaneWarnings
                .getVerticalScrollBar () == event.getSource () ) )
        {
          if ( !this.jGTIMainSplitPane.getActiveEditor ().equals (
              ActiveEditor.RIGHT_EDITOR ) )
          {
            this.jGTIMainSplitPane.setActiveEditor ( ActiveEditor.RIGHT_EDITOR );
            handleTabbedPaneStateChanged ();
          }
          return;
        }
      }
      if ( this.gui.getJGTIEditorPanelTabbedPaneLeft ()
          .getSelectedEditorPanel () instanceof GrammarPanel )
      {
        GrammarPanel grammarPanel = ( GrammarPanel ) this.gui
            .getJGTIEditorPanelTabbedPaneLeft ().getSelectedEditorPanel ();

        if ( ( grammarPanel.getGUI ().jGTIScrollPaneGrammar
            .getHorizontalScrollBar () == event.getSource () )
            || ( grammarPanel.getGUI ().jGTIScrollPaneGrammar
                .getVerticalScrollBar () == event.getSource () )
            || ( grammarPanel.getGUI ().jGTIScrollPaneErrors
                .getHorizontalScrollBar () == event.getSource () )
            || ( grammarPanel.getGUI ().jGTIScrollPaneErrors
                .getVerticalScrollBar () == event.getSource () )
            || ( grammarPanel.getGUI ().jGTIScrollPaneWarnings
                .getHorizontalScrollBar () == event.getSource () )
            || ( grammarPanel.getGUI ().jGTIScrollPaneWarnings
                .getVerticalScrollBar () == event.getSource () ) )
        {
          if ( !this.jGTIMainSplitPane.getActiveEditor ().equals (
              ActiveEditor.LEFT_EDITOR ) )
          {
            this.jGTIMainSplitPane.setActiveEditor ( ActiveEditor.LEFT_EDITOR );
            handleTabbedPaneStateChanged ();
          }
          return;
        }
      }
      if ( this.gui.getJGTIEditorPanelTabbedPaneRight ()
          .getSelectedEditorPanel () instanceof GrammarPanel )
      {
        GrammarPanel grammarPanel = ( GrammarPanel ) this.gui
            .getJGTIEditorPanelTabbedPaneRight ().getSelectedEditorPanel ();

        if ( ( grammarPanel.getGUI ().jGTIScrollPaneGrammar
            .getHorizontalScrollBar () == event.getSource () )
            || ( grammarPanel.getGUI ().jGTIScrollPaneGrammar
                .getVerticalScrollBar () == event.getSource () )
            || ( grammarPanel.getGUI ().jGTIScrollPaneErrors
                .getHorizontalScrollBar () == event.getSource () )
            || ( grammarPanel.getGUI ().jGTIScrollPaneErrors
                .getVerticalScrollBar () == event.getSource () )
            || ( grammarPanel.getGUI ().jGTIScrollPaneWarnings
                .getHorizontalScrollBar () == event.getSource () )
            || ( grammarPanel.getGUI ().jGTIScrollPaneWarnings
                .getVerticalScrollBar () == event.getSource () ) )
        {
          if ( !this.jGTIMainSplitPane.getActiveEditor ().equals (
              ActiveEditor.RIGHT_EDITOR ) )
          {
            this.jGTIMainSplitPane.setActiveEditor ( ActiveEditor.RIGHT_EDITOR );
            handleTabbedPaneStateChanged ();
          }
          return;
        }
      }
    }
    else if ( event.getSource () instanceof JTableHeader )
    {
      if ( this.gui.getJGTIEditorPanelTabbedPaneLeft ()
          .getSelectedEditorPanel () instanceof MachinePanel )
      {
        MachinePanel machinePanel = ( MachinePanel ) this.gui
            .getJGTIEditorPanelTabbedPaneLeft ().getSelectedEditorPanel ();

        if ( ( machinePanel.getGUI ().jGTITableMachine.getTableHeader () == event
            .getSource () )
            || ( machinePanel.getGUI ().jGTITableMachinePDA.getTableHeader () == event
                .getSource () )
            || ( machinePanel.getGUI ().jGTITableErrors.getTableHeader () == event
                .getSource () )
            || ( machinePanel.getGUI ().jGTITableWarnings.getTableHeader () == event
                .getSource () ) )
        {
          if ( !this.jGTIMainSplitPane.getActiveEditor ().equals (
              ActiveEditor.LEFT_EDITOR ) )
          {
            this.jGTIMainSplitPane.setActiveEditor ( ActiveEditor.LEFT_EDITOR );
            handleTabbedPaneStateChanged ();
          }
          return;
        }
      }
      if ( this.gui.getJGTIEditorPanelTabbedPaneRight ()
          .getSelectedEditorPanel () instanceof MachinePanel )
      {
        MachinePanel machinePanel = ( MachinePanel ) this.gui
            .getJGTIEditorPanelTabbedPaneRight ().getSelectedEditorPanel ();

        if ( ( machinePanel.getGUI ().jGTITableMachine.getTableHeader () == event
            .getSource () )
            || ( machinePanel.getGUI ().jGTITableMachinePDA.getTableHeader () == event
                .getSource () )
            || ( machinePanel.getGUI ().jGTITableErrors.getTableHeader () == event
                .getSource () )
            || ( machinePanel.getGUI ().jGTITableWarnings.getTableHeader () == event
                .getSource () ) )
        {
          if ( !this.jGTIMainSplitPane.getActiveEditor ().equals (
              ActiveEditor.RIGHT_EDITOR ) )
          {
            this.jGTIMainSplitPane.setActiveEditor ( ActiveEditor.RIGHT_EDITOR );
            handleTabbedPaneStateChanged ();
          }
          return;
        }
      }
      if ( this.gui.getJGTIEditorPanelTabbedPaneLeft ()
          .getSelectedEditorPanel () instanceof GrammarPanel )
      {
        GrammarPanel grammarPanel = ( GrammarPanel ) this.gui
            .getJGTIEditorPanelTabbedPaneLeft ().getSelectedEditorPanel ();

        if ( ( grammarPanel.getGUI ().jGTITableGrammar.getTableHeader () == event
            .getSource () )
            || ( grammarPanel.getGUI ().jGTITableErrors.getTableHeader () == event
                .getSource () )
            || ( grammarPanel.getGUI ().jGTITableWarnings.getTableHeader () == event
                .getSource () ) )
        {
          if ( !this.jGTIMainSplitPane.getActiveEditor ().equals (
              ActiveEditor.LEFT_EDITOR ) )
          {
            this.jGTIMainSplitPane.setActiveEditor ( ActiveEditor.LEFT_EDITOR );
            handleTabbedPaneStateChanged ();
          }
          return;
        }
      }
      if ( this.gui.getJGTIEditorPanelTabbedPaneRight ()
          .getSelectedEditorPanel () instanceof GrammarPanel )
      {
        GrammarPanel grammarPanel = ( GrammarPanel ) this.gui
            .getJGTIEditorPanelTabbedPaneRight ().getSelectedEditorPanel ();

        if ( ( grammarPanel.getGUI ().jGTITableGrammar.getTableHeader () == event
            .getSource () )
            || ( grammarPanel.getGUI ().jGTITableErrors.getTableHeader () == event
                .getSource () )
            || ( grammarPanel.getGUI ().jGTITableWarnings.getTableHeader () == event
                .getSource () ) )
        {
          if ( !this.jGTIMainSplitPane.getActiveEditor ().equals (
              ActiveEditor.RIGHT_EDITOR ) )
          {
            this.jGTIMainSplitPane.setActiveEditor ( ActiveEditor.RIGHT_EDITOR );
            handleTabbedPaneStateChanged ();
          }
          return;
        }
      }
    }
    else
    {
      throw new IllegalArgumentException ( "unsupported source: " //$NON-NLS-1$
          + event.getSource () );
    }
  }


  /**
   * Handles second view state changes.
   */
  public final void handleSecondViewStateChanged ()
  {
    boolean state = this.gui.getJCheckBoxMenuItemSecondView ().getState ();
    logger.debug ( "handleSecondViewStateChanged", //$NON-NLS-1$
        "handle second view state change to " + Messages.QUOTE + state //$NON-NLS-1$
            + Messages.QUOTE );
    this.jGTIMainSplitPane.setSecondViewActive ( state );
  }


  /**
   * Handles the tabbed pane mouse released event.
   * 
   * @param event The {@link MouseEvent}.
   */
  public final void handleTabbedPaneMouseReleased ( MouseEvent event )
  {
    if ( ! ( event.getSource () instanceof JGTIEditorPanelTabbedPane ) )
    {
      throw new IllegalArgumentException ( "unsupported source" ); //$NON-NLS-1$
    }

    // second view
    handleSecondViewMouseReleased ( event );

    int tabIndex = this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .getUI ().tabForCoordinate (
            this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane (),
            event.getX (), event.getY () );

    if ( ( event.getButton () == MouseEvent.BUTTON1 )
        && ( event.getClickCount () >= 2 ) && ( tabIndex == -1 ) )
    {
      handleNew ();
    }
    else if ( event.getButton () == MouseEvent.BUTTON3 )
    {
      TabPopupMenu popupMenu;
      if ( tabIndex == -1 )
      {
        popupMenu = new TabPopupMenu ( this, TabPopupMenuType.TAB_DEACTIVE );
      }
      else
      {
        popupMenu = new TabPopupMenu ( this, TabPopupMenuType.TAB_ACTIVE );
      }
      popupMenu.show ( ( Component ) event.getSource (), event.getX (), event
          .getY () );
    }
  }


  /**
   * Handles the tabbed pane state changed event.
   */
  public final void handleTabbedPaneStateChanged ()
  {
    logger.debug ( "handleTabbedPaneStateChanged", //$NON-NLS-1$
        "handle tabbed pane state changed" ); //$NON-NLS-1$

    EditorPanel panel = this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();

    // no panel
    if ( panel == null )
    {
      removeButtonState ( ButtonState.ENABLED_GENERAL );
      removeButtonState ( ButtonState.ENABLED_EDIT_DOCUMENT );
      removeButtonState ( ButtonState.ENABLED_VALIDATE );
      removeButtonState ( ButtonState.ENABLED_CONSOLE_TABLE );
      removeButtonState ( ButtonState.ENABLED_MACHINE_TABLE );
      removeButtonState ( ButtonState.ENABLED_ENTER_WORD );
      removeButtonState ( ButtonState.ENABLED_EDIT_MACHINE );
      removeButtonState ( ButtonState.ENABLED_UNDO );
      removeButtonState ( ButtonState.ENABLED_REDO );
      removeButtonState ( ButtonState.ENABLED_AUTO_LAYOUT );
      removeButtonState ( ButtonState.VISIBLE_MACHINE );
      removeButtonState ( ButtonState.VISIBLE_GRAMMAR );
      removeButtonState ( ButtonState.ENABLED_CONVERT_TO );
      removeButtonState ( ButtonState.ENABLED_DRAFT_FOR );
      removeButtonState ( ButtonState.ENABLED_HISTORY );
      removeButtonState ( ButtonState.ENABLED_MINIMIZE );
      removeButtonState ( ButtonState.ENABLED_SAVE );
    }
    // MachinePanel
    else
    {
      if ( panel instanceof MachinePanel )
      {
        MachinePanel machinePanel = ( MachinePanel ) panel;

        addButtonState ( ButtonState.VISIBLE_MACHINE );
        removeButtonState ( ButtonState.VISIBLE_GRAMMAR );
        addButtonState ( ButtonState.ENABLED_DRAFT_FOR_MACHINE );

        if ( machinePanel.getMachine ().getMachineType ().equals (
            MachineType.DFA ) )
        {
          addButtonState ( ButtonState.ENABLED_CONVERT_TO_SOURCE_DFA );
          addButtonState ( ButtonState.ENABLED_MINIMIZE );
        }
        else if ( machinePanel.getMachine ().getMachineType ().equals (
            MachineType.NFA ) )
        {
          addButtonState ( ButtonState.ENABLED_CONVERT_TO_SOURCE_NFA );
          removeButtonState ( ButtonState.ENABLED_MINIMIZE );
        }
        else if ( machinePanel.getMachine ().getMachineType ().equals (
            MachineType.ENFA ) )
        {
          addButtonState ( ButtonState.ENABLED_CONVERT_TO_SOURCE_ENFA );
          removeButtonState ( ButtonState.ENABLED_MINIMIZE );
        }
        else if ( machinePanel.getMachine ().getMachineType ().equals (
            MachineType.PDA ) )
        {
          addButtonState ( ButtonState.ENABLED_CONVERT_TO_SOURCE_PDA );
          removeButtonState ( ButtonState.ENABLED_MINIMIZE );
        }
        else
        {
          throw new RuntimeException ( "unsupported machine type" ); //$NON-NLS-1$
        }

        machinePanel.setVisibleConsole ( this.gui
            .getJCheckBoxMenuItemConsole ().getState ()
            && !machinePanel.isWordEnterMode () );
        machinePanel.setVisibleTable ( this.gui.getJCheckBoxMenuItemTable ()
            .getState () );
        addButtonState ( ButtonState.ENABLED_MACHINE_TABLE );

        // word navigation mode
        if ( machinePanel.isWordNavigation () )
        {
          addButtonState ( ButtonState.ENABLED_HISTORY );
          addButtonState ( ButtonState.ENABLED_EDIT_MACHINE );

          removeButtonState ( ButtonState.ENABLED_EDIT_DOCUMENT );
          removeButtonState ( ButtonState.ENABLED_MACHINE_EDIT_ITEMS );
          removeButtonState ( ButtonState.ENABLED_CONSOLE_TABLE );
          removeButtonState ( ButtonState.ENABLED_VALIDATE );
          removeButtonState ( ButtonState.ENABLED_ENTER_WORD );
          removeButtonState ( ButtonState.ENABLED_AUTO_LAYOUT );
          removeButtonState ( ButtonState.ENABLED_CONVERT_TO );
          removeButtonState ( ButtonState.ENABLED_DRAFT_FOR );

          addButtonState ( ButtonState.ENABLED_NAVIGATION_STEPS );
        }
        // word enter mode
        else if ( machinePanel.isWordEnterMode () )
        {
          addButtonState ( ButtonState.ENABLED_EDIT_MACHINE );

          removeButtonState ( ButtonState.ENABLED_EDIT_DOCUMENT );
          removeButtonState ( ButtonState.ENABLED_HISTORY );
          removeButtonState ( ButtonState.ENABLED_MACHINE_EDIT_ITEMS );
          removeButtonState ( ButtonState.ENABLED_CONSOLE_TABLE );
          removeButtonState ( ButtonState.ENABLED_VALIDATE );
          removeButtonState ( ButtonState.ENABLED_ENTER_WORD );
          removeButtonState ( ButtonState.ENABLED_AUTO_LAYOUT );
          removeButtonState ( ButtonState.ENABLED_CONVERT_TO );
          removeButtonState ( ButtonState.ENABLED_DRAFT_FOR );

          addButtonState ( ButtonState.ENABLED_NAVIGATION_START );
        }
        // normal mode
        else
        {
          removeButtonState ( ButtonState.ENABLED_HISTORY );
          removeButtonState ( ButtonState.ENABLED_EDIT_MACHINE );

          addButtonState ( ButtonState.ENABLED_EDIT_DOCUMENT );
          addButtonState ( ButtonState.ENABLED_MACHINE_EDIT_ITEMS );
          addButtonState ( ButtonState.ENABLED_CONSOLE_TABLE );
          addButtonState ( ButtonState.ENABLED_VALIDATE );
          addButtonState ( ButtonState.ENABLED_ENTER_WORD );
          addButtonState ( ButtonState.ENABLED_AUTO_LAYOUT );
          addButtonState ( ButtonState.ENABLED_CONVERT_TO );
          addButtonState ( ButtonState.ENABLED_DRAFT_FOR );

          addButtonState ( ButtonState.ENABLED_NAVIGATION_DEACTIVE );
        }
      }
      // GrammarPanel
      else if ( panel instanceof GrammarPanel )
      {
        GrammarPanel grammarPanel = ( GrammarPanel ) panel;

        if ( grammarPanel.getGrammar ().getGrammarType ().equals (
            GrammarType.RG ) )
        {
          addButtonState ( ButtonState.ENABLED_CONVERT_TO_SOURCE_RG );
        }
        else if ( grammarPanel.getGrammar ().getGrammarType ().equals (
            GrammarType.CFG ) )
        {
          addButtonState ( ButtonState.ENABLED_CONVERT_TO_SOURCE_CFG );
        }
        else
        {
          throw new RuntimeException ( "unsupported grammar type" ); //$NON-NLS-1$
        }

        removeButtonState ( ButtonState.VISIBLE_MACHINE );
        addButtonState ( ButtonState.VISIBLE_GRAMMAR );
        addButtonState ( ButtonState.ENABLED_CONVERT_TO );
        addButtonState ( ButtonState.ENABLED_DRAFT_FOR );
        addButtonState ( ButtonState.ENABLED_EDIT_DOCUMENT );
        removeButtonState ( ButtonState.ENABLED_MACHINE_TABLE );
        addButtonState ( ButtonState.ENABLED_CONSOLE_TABLE );
        panel.setVisibleConsole ( this.gui.getJCheckBoxMenuItemConsole ()
            .getState () );
        removeButtonState ( ButtonState.ENABLED_ENTER_WORD );
        addButtonState ( ButtonState.ENABLED_DRAFT_FOR_GRAMMAR );
        removeButtonState ( ButtonState.ENABLED_HISTORY );
        removeButtonState ( ButtonState.ENABLED_AUTO_LAYOUT );
        removeButtonState ( ButtonState.ENABLED_MINIMIZE );
      }
      else
      {
        throw new RuntimeException ( "unsupported panel" ); //$NON-NLS-1$
      }

      if ( panel.isUndoAble () )
      {
        addButtonState ( ButtonState.ENABLED_UNDO );
      }
      else
      {
        removeButtonState ( ButtonState.ENABLED_UNDO );
      }

      if ( panel.isRedoAble () )
      {
        addButtonState ( ButtonState.ENABLED_REDO );
      }
      else
      {
        removeButtonState ( ButtonState.ENABLED_REDO );
      }

      // Save status
      if ( panel.isModified () )
      {
        addButtonState ( ButtonState.ENABLED_SAVE );
      }
      else
      {
        removeButtonState ( ButtonState.ENABLED_SAVE );
      }
    }
  }


  /**
   * Handles table state changes.
   */
  public final void handleTableStateChanged ()
  {
    EditorPanel panel = this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();

    if ( panel instanceof MachinePanel )
    {
      MachinePanel machinePanel = ( MachinePanel ) panel;
      boolean state = this.gui.getJCheckBoxMenuItemTable ().getState ();
      if ( PreferenceManager.getInstance ().getVisibleTable () != state )
      {
        if ( state )
        {
          addButtonState ( ButtonState.SELECTED_MACHINE_TABLE );
        }
        else
        {
          removeButtonState ( ButtonState.SELECTED_MACHINE_TABLE );
        }

        PreferenceManager.getInstance ().setVisibleTable ( state );
        machinePanel.setVisibleTable ( state );
      }
    }
  }


  /**
   * Handle Toolbar Add State button value changed
   * 
   * @param state The new State of the Add State Toolbar button
   */
  public final void handleToolbarAddState ( boolean state )
  {
    for ( EditorPanel panel : this.jGTIMainSplitPane
        .getJGTIEditorPanelTabbedPane () )
    {
      if ( panel instanceof MachinePanel )
      {
        MachinePanel machinePanel = ( MachinePanel ) panel;
        machinePanel.handleToolbarAddState ( state );
      }
    }
  }


  /**
   * Handle Toolbar End button value changed
   * 
   * @param state The new State of the End Toolbar button
   */
  public final void handleToolbarEnd ( boolean state )
  {
    for ( EditorPanel panel : this.jGTIMainSplitPane
        .getJGTIEditorPanelTabbedPane () )
    {
      if ( ( panel instanceof MachinePanel ) )
      {
        MachinePanel machinePanel = ( MachinePanel ) panel;
        machinePanel.handleToolbarEnd ( state );
      }
    }
  }


  /**
   * Handle Toolbar Mouse button value changed
   * 
   * @param state The new State of the Mouse Toolbar button
   */
  public final void handleToolbarMouse ( boolean state )
  {
    for ( EditorPanel panel : this.jGTIMainSplitPane
        .getJGTIEditorPanelTabbedPane () )
    {
      if ( ( panel instanceof MachinePanel ) )
      {
        MachinePanel machinePanel = ( MachinePanel ) panel;
        machinePanel.handleToolbarMouse ( state );
      }
    }
  }


  /**
   * Handle Toolbar Start button value changed
   * 
   * @param state The new State of the Start Toolbar button
   */
  public final void handleToolbarStart ( boolean state )
  {
    for ( EditorPanel panel : this.jGTIMainSplitPane
        .getJGTIEditorPanelTabbedPane () )
    {
      if ( ( panel instanceof MachinePanel ) )
      {
        MachinePanel machinePanel = ( MachinePanel ) panel;
        machinePanel.handleToolbarStart ( state );
      }
    }
  }


  /**
   * Handle Toolbar Transition button value changed
   * 
   * @param state The new State of the Transition Toolbar button
   */
  public final void handleToolbarTransition ( boolean state )
  {
    for ( EditorPanel panel : this.jGTIMainSplitPane
        .getJGTIEditorPanelTabbedPane () )
    {
      if ( ( panel instanceof MachinePanel ) )
      {
        MachinePanel machinePanel = ( MachinePanel ) panel;

        machinePanel.handleToolbarTransition ( state );
      }
    }
  }


  /**
   * Handle undo button pressed
   */
  public final void handleUndo ()
  {
    EditorPanel panel = this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( panel != null )
    {
      panel.handleUndo ();
    }
  }


  /**
   * Handle the action event of the enter word item.
   */
  public final void handleValidate ()
  {
    handleValidate ( true );
  }


  /**
   * Handle the action event of the enter word item.
   * 
   * @param showDialogIfWarning If true the dialog is shown if there a no errors
   *          but warnings.
   * @return True if the validating finished without errors, otherwise false.
   */
  public final boolean handleValidate ( boolean showDialogIfWarning )
  {
    EditorPanel panel = this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();

    int errorCount = 0;
    int warningCount = 0;
    boolean machinePanelSelected;

    if ( panel instanceof MachinePanel )
    {
      machinePanelSelected = true;
      MachinePanel machinePanel = ( MachinePanel ) panel;
      try
      {
        panel.clearValidationMessages ();
        machinePanel.getMachine ().validate ();
      }
      catch ( MachineValidationException e )
      {

        for ( MachineException error : e.getMachineException () )
        {
          if ( error.getType ().equals ( ErrorType.ERROR ) )
          {
            machinePanel.addError ( error );
            errorCount++ ;
          }
          else if ( error.getType ().equals ( ErrorType.WARNING ) )
          {
            machinePanel.addWarning ( error );
            warningCount++ ;
          }
        }
      }
    }
    else if ( panel instanceof GrammarPanel )
    {
      machinePanelSelected = false;
      GrammarPanel grammarPanel = ( GrammarPanel ) panel;
      try
      {
        panel.clearValidationMessages ();
        grammarPanel.getGrammar ().validate ();
      }
      catch ( GrammarValidationException e )
      {

        for ( GrammarException error : e.getGrammarException () )
        {
          if ( error.getType ().equals ( ErrorType.ERROR ) )
          {
            grammarPanel.addError ( error );
            errorCount++ ;
          }
          else if ( error.getType ().equals ( ErrorType.WARNING ) )
          {
            grammarPanel.addWarning ( error );
            warningCount++ ;
          }
        }
      }
    }
    else
    {
      throw new RuntimeException (
          "the select panel is not a machine or grammar panel" ); //$NON-NLS-1$
    }

    // Return if only errors should be displayes
    if ( !showDialogIfWarning && ( errorCount == 0 ) )
    {
      if ( warningCount > 0 )
      {
        // Select the warning tab
        panel.getJTabbedPaneConsole ().setSelectedIndex ( 1 );

        // Update the titles
        panel.getJTabbedPaneConsole ().setTitleAt ( 0,
            machinePanelSelected ? Messages.getString ( "MachinePanel.Error" ) //$NON-NLS-1$
                : Messages.getString ( "GrammarPanel.Error" ) ); //$NON-NLS-1$

        panel.getJTabbedPaneConsole ().setTitleAt (
            1,
            Messages.getString (
                machinePanelSelected ? "MachinePanel.WarningFound" //$NON-NLS-1$
                    : "GrammarPanel.WarningFound", false, new Integer ( //$NON-NLS-1$
                    warningCount ) ) );
      }
      return true;
    }

    InfoDialog infoDialog = null;
    // Error and warning
    if ( ( errorCount > 0 ) && ( warningCount > 0 ) )
    {
      String message = null;
      if ( ( errorCount == 1 ) && ( warningCount == 1 ) )
      {
        message = Messages
            .getString ( machinePanelSelected ? "MainWindow.ErrorWarningMachineCount0" //$NON-NLS-1$
                : "MainWindow.ErrorWarningGrammarCount0" ); //$NON-NLS-1$
      }
      else if ( ( errorCount == 1 ) && ( warningCount > 1 ) )
      {
        message = Messages.getString (
            machinePanelSelected ? "MainWindow.ErrorWarningMachineCount1" //$NON-NLS-1$
                : "MainWindow.ErrorWarningGrammarCount1", false, String //$NON-NLS-1$
                .valueOf ( warningCount ) );
      }
      else if ( ( errorCount > 1 ) && ( warningCount == 1 ) )
      {
        message = Messages.getString (
            machinePanelSelected ? "MainWindow.ErrorWarningMachineCount2" //$NON-NLS-1$
                : "MainWindow.ErrorWarningGrammarCount2", false, String //$NON-NLS-1$
                .valueOf ( errorCount ) );
      }
      else
      {
        message = Messages.getString (
            machinePanelSelected ? "MainWindow.ErrorWarningMachineCount3" //$NON-NLS-1$
                : "MainWindow.ErrorWarningGrammarCount3", false, String //$NON-NLS-1$
                .valueOf ( errorCount ), String.valueOf ( warningCount ) );
      }

      // Update the titles
      panel.getJTabbedPaneConsole ().setTitleAt ( 0,
          Messages.getString ( machinePanelSelected ? "MachinePanel.ErrorFound" //$NON-NLS-1$
              : "GrammarPanel.ErrorFound", false, new Integer ( errorCount ) ) ); //$NON-NLS-1$
      panel.getJTabbedPaneConsole ().setTitleAt (
          1,
          Messages.getString (
              machinePanelSelected ? "MachinePanel.WarningFound" //$NON-NLS-1$
                  : "GrammarPanel.WarningFound", false, new Integer ( //$NON-NLS-1$
                  warningCount ) ) );

      // Select the error tab
      panel.getJTabbedPaneConsole ().setSelectedIndex ( 0 );

      infoDialog = new InfoDialog ( this.gui, message, Messages
          .getString ( machinePanelSelected ? "MainWindow.ErrorWarningMachine" //$NON-NLS-1$
              : "MainWindow.ErrorWarningGrammar" ) ); //$NON-NLS-1$
    }
    // Only error
    else if ( errorCount > 0 )
    {
      String message;
      if ( errorCount == 1 )
      {
        message = Messages
            .getString ( machinePanelSelected ? "MainWindow.ErrorMachineCountOne"//$NON-NLS-1$
                : "MainWindow.ErrorGrammarCountOne" );//$NON-NLS-1$
      }
      else
      {
        message = Messages.getString (
            machinePanelSelected ? "MainWindow.ErrorMachineCount"//$NON-NLS-1$
                : "MainWindow.ErrorGrammarCount", false, String//$NON-NLS-1$
                .valueOf ( errorCount ) );
      }

      // Update the titles
      panel.getJTabbedPaneConsole ().setTitleAt ( 0,
          Messages.getString ( machinePanelSelected ? "MachinePanel.ErrorFound"//$NON-NLS-1$
              : "GrammarPanel.ErrorFound", false, new Integer ( errorCount ) ) );//$NON-NLS-1$
      panel.getJTabbedPaneConsole ().setTitleAt ( 1,
          Messages.getString ( machinePanelSelected ? "MachinePanel.Warning"//$NON-NLS-1$
              : "GrammarPanel.Warning" ) );//$NON-NLS-1$

      panel.getJTabbedPaneConsole ().setSelectedIndex ( 0 );

      infoDialog = new InfoDialog ( this.gui, message, Messages
          .getString ( machinePanelSelected ? "MainWindow.ErrorMachine"//$NON-NLS-1$
              : "MainWindow.ErrorGrammar" ) );//$NON-NLS-1$
    }
    // Only warning
    else if ( warningCount > 0 )
    {
      String message;
      if ( warningCount == 1 )
      {
        message = Messages
            .getString ( machinePanelSelected ? "MainWindow.WarningMachineCountOne"//$NON-NLS-1$
                : "MainWindow.WarningGrammarCountOne" );//$NON-NLS-1$
      }
      else
      {
        message = Messages.getString (
            machinePanelSelected ? "MainWindow.WarningMachineCount"//$NON-NLS-1$
                : "MainWindow.WarningGrammarCount", false, String//$NON-NLS-1$
                .valueOf ( warningCount ) );
      }

      // Update the titles
      panel.getJTabbedPaneConsole ().setTitleAt ( 0,
          Messages.getString ( machinePanelSelected ? "MachinePanel.Error"//$NON-NLS-1$
              : "GrammarPanel.Error" ) );//$NON-NLS-1$
      panel.getJTabbedPaneConsole ().setTitleAt (
          1,
          Messages.getString (
              machinePanelSelected ? "MachinePanel.WarningFound"//$NON-NLS-1$
                  : "GrammarPanel.WarningFound", false, new Integer ( //$NON-NLS-1$
                  warningCount ) ) );

      // Select the warning tab
      panel.getJTabbedPaneConsole ().setSelectedIndex ( 1 );

      infoDialog = new InfoDialog ( this.gui, message, Messages
          .getString ( machinePanelSelected ? "MainWindow.WarningMachine"//$NON-NLS-1$
              : "MainWindow.WarningGrammar" ) );//$NON-NLS-1$
    }
    // No error and no warning
    else
    {
      // Update the titles
      panel.getJTabbedPaneConsole ().setTitleAt ( 0,
          Messages.getString ( machinePanelSelected ? "MachinePanel.Error"//$NON-NLS-1$
              : "GrammarPanel.Error" ) );//$NON-NLS-1$
      panel.getJTabbedPaneConsole ().setTitleAt ( 1,
          Messages.getString ( machinePanelSelected ? "MachinePanel.Warning"//$NON-NLS-1$
              : "GrammarPanel.Warning" ) );//$NON-NLS-1$

      infoDialog = new InfoDialog (
          this.gui,
          Messages
              .getString ( machinePanelSelected ? "MainWindow.NoErrorNoWarningMachineCount"//$NON-NLS-1$
                  : "MainWindow.NoErrorNoWarningGrammarCount" ),//$NON-NLS-1$
          Messages
              .getString ( machinePanelSelected ? "MainWindow.NoErrorNoWarningMachine"//$NON-NLS-1$
                  : "MainWindow.NoErrorNoWarningGrammar" ) ); //$NON-NLS-1$
    }

    addButtonState ( ButtonState.SELECTED_CONSOLE_TABLE );
    panel.setVisibleConsole ( true );

    infoDialog.show ();
    return false;
  }


  /**
   * Handles the word auto step action in the word enter mode.
   * 
   * @param event The {@link ItemEvent}.
   */
  public final void handleWordAutoStep ( ItemEvent event )
  {
    EditorPanel panel = this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( ! ( panel instanceof MachinePanel ) )
    {
      throw new IllegalArgumentException ( "not a machine panel" ); //$NON-NLS-1$
    }
    MachinePanel machinePanel = ( MachinePanel ) panel;

    machinePanel.handleWordAutoStep ( event );
  }


  /**
   * Handles the word next step action in the word enter mode.
   */
  public final void handleWordNextStep ()
  {
    EditorPanel panel = this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( ! ( panel instanceof MachinePanel ) )
    {
      throw new IllegalArgumentException ( "not a machine panel" ); //$NON-NLS-1$
    }
    MachinePanel machinePanel = ( MachinePanel ) panel;
    machinePanel.handleWordNextStep ();
  }


  /**
   * Handles the word previous step action in the word enter mode.
   */
  public final void handleWordPreviousStep ()
  {
    EditorPanel panel = this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( ! ( panel instanceof MachinePanel ) )
    {
      throw new IllegalArgumentException ( "not a machine panel" ); //$NON-NLS-1$
    }
    MachinePanel machinePanel = ( MachinePanel ) panel;

    machinePanel.handleWordPreviousStep ();
  }


  /**
   * Handles the word start action in the word enter mode.
   */
  public final void handleWordStart ()
  {
    EditorPanel panel = this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( ! ( panel instanceof MachinePanel ) )
    {
      throw new IllegalArgumentException ( "not a machine panel" ); //$NON-NLS-1$
    }
    MachinePanel machinePanel = ( MachinePanel ) panel;

    if ( machinePanel.handleWordStart () )
    {
      addButtonState ( ButtonState.ENABLED_NAVIGATION_STEPS );
    }

    addButtonState ( ButtonState.ENABLED_HISTORY );
  }


  /**
   * Handles the word stop action in the word enter mode.
   */
  public final void handleWordStop ()
  {
    EditorPanel panel = this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
        .getSelectedEditorPanel ();
    if ( ! ( panel instanceof MachinePanel ) )
    {
      throw new IllegalArgumentException ( "not a machine panel" ); //$NON-NLS-1$
    }
    MachinePanel machinePanel = ( MachinePanel ) panel;

    addButtonState ( ButtonState.ENABLED_NAVIGATION_START );

    machinePanel.handleWordStop ();

    removeButtonState ( ButtonState.ENABLED_HISTORY );
  }


  /**
   * Returns the close enabled state.
   * 
   * @return The close enabled state.
   */
  public final boolean isEnabledClose ()
  {
    return this.gui.getJMenuItemClose ().isEnabled ();
  }


  /**
   * Returns the close all enabled state.
   * 
   * @return The close all enabled state.
   */
  public final boolean isEnabledCloseAll ()
  {
    return this.gui.getJMenuItemCloseAll ().isEnabled ();
  }


  /**
   * Returns the new enabled state.
   * 
   * @return The new enabled state.
   */
  public final boolean isEnabledNew ()
  {
    return this.gui.getJMenuItemNew ().isEnabled ();
  }


  /**
   * Returns the open enabled state.
   * 
   * @return The open enabled state.
   */
  public final boolean isEnabledOpen ()
  {
    return this.gui.getJMenuItemNew ().isEnabled ();
  }


  /**
   * Returns the save enabled state.
   * 
   * @return The save enabled state.
   */
  public final boolean isEnabledSave ()
  {
    return this.gui.getJMenuItemSave ().isEnabled ();
  }


  /**
   * Returns the save as enabled state.
   * 
   * @return The save as enabled state.
   */
  public final boolean isEnabledSaveAs ()
  {
    return this.gui.getJMenuItemSaveAs ().isEnabled ();
  }


  /**
   * {@inheritDoc}
   * 
   * @see LanguageChangedListener#languageChanged()
   */
  public final void languageChanged ()
  {
    /*
     * File
     */
    MainWindow.this.gui.getJMenuFile ().setText (
        Messages.getString ( "MainWindow.File" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuFile ().setMnemonic (
        Messages.getString ( "MainWindow.FileMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // New
    MainWindow.this.gui.getJMenuItemNew ().setText (
        Messages.getString ( "MainWindow.New" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemNew ().setMnemonic (
        Messages.getString ( "MainWindow.NewMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJGTIToolBarButtonNew ().setToolTipText (
        Messages.getString ( "MainWindow.NewToolTip" ) ); //$NON-NLS-1$
    // Open
    MainWindow.this.gui.getJMenuItemOpen ().setText (
        Messages.getString ( "MainWindow.Open" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemOpen ().setMnemonic (
        Messages.getString ( "MainWindow.OpenMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJGTIToolBarButtonOpen ().setToolTipText (
        Messages.getString ( "MainWindow.OpenToolTip" ) ); //$NON-NLS-1$
    // Close
    MainWindow.this.gui.getJMenuItemClose ().setText (
        Messages.getString ( "MainWindow.Close" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemClose ().setMnemonic (
        Messages.getString ( "MainWindow.CloseMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // CloseAll
    MainWindow.this.gui.getJMenuItemCloseAll ().setText (
        Messages.getString ( "MainWindow.CloseAll" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemCloseAll ().setMnemonic (
        Messages.getString ( "MainWindow.CloseAllMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Save
    MainWindow.this.gui.getJMenuItemSave ().setText (
        Messages.getString ( "MainWindow.Save" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemSave ().setMnemonic (
        Messages.getString ( "MainWindow.SaveMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJGTIToolBarButtonSave ().setToolTipText (
        Messages.getString ( "MainWindow.SaveToolTip" ) ); //$NON-NLS-1$
    // SaveAs
    MainWindow.this.gui.getJMenuItemSaveAs ().setText (
        Messages.getString ( "MainWindow.SaveAs" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemSaveAs ().setMnemonic (
        Messages.getString ( "MainWindow.SaveAsMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJGTIToolBarButtonSaveAs ().setToolTipText (
        Messages.getString ( "MainWindow.SaveAsToolTip" ) ); //$NON-NLS-1$
    // SaveAll
    MainWindow.this.gui.getJMenuItemSaveAll ().setText (
        Messages.getString ( "MainWindow.SaveAll" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemSaveAll ().setMnemonic (
        Messages.getString ( "MainWindow.SaveAllMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // RecentlyUsed
    MainWindow.this.gui.getJMenuRecentlyUsed ().setText (
        Messages.getString ( "MainWindow.RecentlyUsed" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuRecentlyUsed ().setMnemonic (
        Messages.getString ( "MainWindow.RecentlyUsedMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Quit
    MainWindow.this.gui.getJMenuItemQuit ().setText (
        Messages.getString ( "MainWindow.Quit" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemQuit ().setMnemonic (
        Messages.getString ( "MainWindow.QuitMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$

    /*
     * Edit
     */
    MainWindow.this.gui.getJMenuEdit ().setText (
        Messages.getString ( "MainWindow.Edit" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuEdit ().setMnemonic (
        Messages.getString ( "MainWindow.EditMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Undo
    MainWindow.this.gui.getJMenuItemUndo ().setText (
        Messages.getString ( "MainWindow.Undo" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemUndo ().setMnemonic (
        Messages.getString ( "MainWindow.UndoMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Redo
    MainWindow.this.gui.getJMenuItemRedo ().setText (
        Messages.getString ( "MainWindow.Redo" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemRedo ().setMnemonic (
        Messages.getString ( "MainWindow.RedoMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Preferences
    MainWindow.this.gui.getJMenuItemPreferences ().setText (
        Messages.getString ( "MainWindow.Preferences" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemPreferences ().setMnemonic (
        Messages.getString ( "MainWindow.PreferencesMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$

    /*
     * View
     */
    MainWindow.this.gui.getJMenuView ().setText (
        Messages.getString ( "MainWindow.View" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuView ().setMnemonic (
        Messages.getString ( "MainWindow.ViewMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Console
    MainWindow.this.gui.getJCheckBoxMenuItemConsole ().setText (
        Messages.getString ( "MainWindow.Console" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJCheckBoxMenuItemConsole ().setMnemonic (
        Messages.getString ( "MainWindow.ConsoleMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Table
    MainWindow.this.gui.getJCheckBoxMenuItemTable ().setText (
        Messages.getString ( "MainWindow.Table" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJCheckBoxMenuItemTable ().setMnemonic (
        Messages.getString ( "MainWindow.TableMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // SecondView
    MainWindow.this.gui.getJCheckBoxMenuItemSecondView ().setText (
        Messages.getString ( "MainWindow.SecondView" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJCheckBoxMenuItemSecondView ().setMnemonic (
        Messages.getString ( "MainWindow.SecondViewMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$

    /*
     * Execute
     */
    MainWindow.this.gui.getJMenuExecute ().setText (
        Messages.getString ( "MainWindow.Execute" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuExecute ().setMnemonic (
        Messages.getString ( "MainWindow.ExecuteMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // EnterWord
    MainWindow.this.gui.getJMenuItemEnterWord ().setText (
        Messages.getString ( "MainWindow.EnterWord" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemEnterWord ().setMnemonic (
        Messages.getString ( "MainWindow.EnterWordMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // EditMachine
    MainWindow.this.gui.getJMenuItemEditMachine ().setText (
        Messages.getString ( "MainWindow.EditMachine" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemEditMachine ().setMnemonic (
        Messages.getString ( "MainWindow.EditMachineMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Validate
    MainWindow.this.gui.getJMenuItemValidate ().setText (
        Messages.getString ( "MainWindow.Validate" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemValidate ().setMnemonic (
        Messages.getString ( "MainWindow.ValidateMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // ConvertTo
    MainWindow.this.gui.getJMenuConvertTo ().setText (
        Messages.getString ( "MainWindow.ConvertTo" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuConvertTo ().setMnemonic (
        Messages.getString ( "MainWindow.ConvertToMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemConvertToDFA ().setText (
        Messages.getString ( "MainWindow.DFA" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemConvertToNFA ().setText (
        Messages.getString ( "MainWindow.NFA" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemConvertToENFA ().setText (
        Messages.getString ( "MainWindow.ENFA" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemConvertToPDA ().setText (
        Messages.getString ( "MainWindow.PDA" ) ); //$NON-NLS-1$
    // Draft for
    MainWindow.this.gui.getJMenuDraft ().setText (
        Messages.getString ( "MainWindow.DraftFor" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuDraft ().setMnemonic (
        Messages.getString ( "MainWindow.DraftForMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemDFA ().setText (
        Messages.getString ( "MainWindow.DFA" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemNFA ().setText (
        Messages.getString ( "MainWindow.NFA" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemENFA ().setText (
        Messages.getString ( "MainWindow.ENFA" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemPDA ().setText (
        Messages.getString ( "MainWindow.PDA" ) ); //$NON-NLS-1$
    // AutoLayout
    MainWindow.this.gui.getJMenuItemAutoLayout ().setText (
        Messages.getString ( "MainWindow.AutoLayout" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemAutoLayout ().setMnemonic (
        Messages.getString ( "MainWindow.AutoLayoutMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Minimize
    MainWindow.this.gui.getJMenuItemMinimize ().setText (
        Messages.getString ( "MainWindow.Minimize" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemMinimize ().setMnemonic (
        Messages.getString ( "MainWindow.MinimizeMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$

    /*
     * Extras
     */
    MainWindow.this.gui.getJMenuExtras ().setText (
        Messages.getString ( "MainWindow.Extras" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuExtras ().setMnemonic (
        Messages.getString ( "MainWindow.ExtrasMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // Exchange
    MainWindow.this.gui.getJMenuItemExchange ().setText (
        Messages.getString ( "MainWindow.Exchange" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemExchange ().setMnemonic (
        Messages.getString ( "MainWindow.ExchangeMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // History
    MainWindow.this.gui.getJMenuItemHistory ().setText (
        Messages.getString ( "MainWindow.History" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemHistory ().setMnemonic (
        Messages.getString ( "MainWindow.HistoryMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // ReachableStates
    MainWindow.this.gui.getJMenuItemReachableStates ().setText (
        Messages.getString ( "MainWindow.ReachableStates" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemReachableStates ().setMnemonic (
        Messages.getString ( "MainWindow.ReachableStatesMnemonic" ).charAt ( //$NON-NLS-1$
            0 ) );

    /*
     * Help
     */
    MainWindow.this.gui.getJMenuHelp ().setText (
        Messages.getString ( "MainWindow.Help" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuHelp ().setMnemonic (
        Messages.getString ( "MainWindow.HelpMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$
    // About
    MainWindow.this.gui.getJMenuItemAbout ().setText (
        Messages.getString ( "MainWindow.About" ) ); //$NON-NLS-1$
    MainWindow.this.gui.getJMenuItemAbout ().setMnemonic (
        Messages.getString ( "MainWindow.AboutMnemonic" ).charAt ( 0 ) ); //$NON-NLS-1$

    // Mouse
    MainWindow.this.gui.getJGTIToolBarToggleButtonMouse ().setToolTipText (
        Messages.getString ( "MachinePanel.Mouse" ) ); //$NON-NLS-1$
    // Add state
    MainWindow.this.gui.getJGTIToolBarToggleButtonAddState ().setToolTipText (
        Messages.getString ( "MachinePanel.AddState" ) ); //$NON-NLS-1$
    // Add transition
    MainWindow.this.gui.getJGTIToolBarToggleButtonAddTransition ()
        .setToolTipText ( Messages.getString ( "MachinePanel.AddTransition" ) ); //$NON-NLS-1$
    // Start state
    MainWindow.this.gui.getJGTIToolBarToggleButtonStartState ().setToolTipText (
        Messages.getString ( "MachinePanel.StartState" ) ); //$NON-NLS-1$
    // Final state
    MainWindow.this.gui.getJGTIToolBarToggleButtonFinalState ().setToolTipText (
        Messages.getString ( "MachinePanel.FinalState" ) ); //$NON-NLS-1$
    // Edit Document
    MainWindow.this.gui.getJGTIToolBarButtonEditDocument ().setToolTipText (
        Messages.getString ( "MachinePanel.EditDocument" ) ); //$NON-NLS-1$
    // Previous Step
    MainWindow.this.gui.getJGTIToolBarButtonPrevious ().setToolTipText (
        Messages.getString ( "MachinePanel.WordModePreviousStep" ) ); //$NON-NLS-1$
    // Start Word
    MainWindow.this.gui.getJGTIToolBarButtonStart ().setToolTipText (
        Messages.getString ( "MachinePanel.WordModeStart" ) ); //$NON-NLS-1$
    // Next Step
    MainWindow.this.gui.getJGTIToolBarButtonNextStep ().setToolTipText (
        Messages.getString ( "MachinePanel.WordModeNextStep" ) ); //$NON-NLS-1$
    // Auto Step
    MainWindow.this.gui.getJGTIToolBarToggleButtonAutoStep ().setToolTipText (
        Messages.getString ( "MachinePanel.WordModeAutoStep" ) ); //$NON-NLS-1$
    // Stop Word
    MainWindow.this.gui.getJGTIToolBarButtonStop ().setToolTipText (
        Messages.getString ( "MachinePanel.WordModeStop" ) ); //$NON-NLS-1$
    // Add production
    MainWindow.this.gui.getJGTIToolBarButtonAddProduction ().setToolTipText (
        Messages.getString ( "GrammarPanel.AddProduction" ) ); //$NON-NLS-1$
    // Edit production
    MainWindow.this.gui.getJGTIToolBarButtonEditProduction ().setToolTipText (
        Messages.getString ( "GrammarPanel.ProductionProperties" ) ); //$NON-NLS-1$
    // Delete production
    MainWindow.this.gui.getJGTIToolBarButtonDeleteProduction ().setToolTipText (
        Messages.getString ( "GrammarPanel.DeleteProduction" ) ); //$NON-NLS-1$
  }


  /**
   * Try to open the given file
   * 
   * @param file The file to open
   * @param addToRecentlyUsed Flag signals if file should be added to recently
   *          used files
   */
  public final void openFile ( File file, boolean addToRecentlyUsed )
  {
    openFile ( file, addToRecentlyUsed, this.jGTIMainSplitPane
        .getActiveEditor () );
  }


  /**
   * Try to open the given file
   * 
   * @param file The file to open
   * @param addToRecentlyUsed Flag signals if file should be added to recently
   *          used files
   * @param activeEditor The {@link ActiveEditor} which should be used.
   */
  public final void openFile ( File file, boolean addToRecentlyUsed,
      ActiveEditor activeEditor )
  {
    JGTIEditorPanelTabbedPane jGTIEditorPanelTabbedPane;
    switch ( activeEditor )
    {
      case LEFT_EDITOR :
      {
        jGTIEditorPanelTabbedPane = this.jGTIMainSplitPane
            .getJGTIEditorPanelTabbedPaneLeft ();
        break;
      }
      case RIGHT_EDITOR :
      {
        jGTIEditorPanelTabbedPane = this.jGTIMainSplitPane
            .getJGTIEditorPanelTabbedPaneRight ();
        break;
      }
      default :
      {
        throw new RuntimeException ( "unsupported editor" ); //$NON-NLS-1$
      }
    }

    // check if we already have an editor panel for the file in the left editor
    for ( EditorPanel current : this.jGTIMainSplitPane
        .getJGTIEditorPanelTabbedPaneLeft () )
    {
      if ( file.equals ( current.getFile () ) )
      {
        this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPaneLeft ()
            .setSelectedEditorPanel ( current );

        // reorganize recently used files
        if ( addToRecentlyUsed )
        {
          RecentlyUsedMenuItem item = new RecentlyUsedMenuItem ( this, file );
          this.recentlyUsedFiles.remove ( item );
          this.recentlyUsedFiles.add ( 0, item );
          if ( this.recentlyUsedFiles.size () > 10 )
          {
            this.recentlyUsedFiles.remove ( 10 );
          }
          organizeRecentlyUsedFilesMenu ();
        }
        return;
      }
    }

    // check if we already have an editor panel for the file in the right editor
    for ( EditorPanel current : this.jGTIMainSplitPane
        .getJGTIEditorPanelTabbedPaneRight () )
    {
      if ( file.equals ( current.getFile () ) )
      {
        this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPaneRight ()
            .setSelectedEditorPanel ( current );

        // reorganize recently used files
        if ( addToRecentlyUsed )
        {
          RecentlyUsedMenuItem item = new RecentlyUsedMenuItem ( this, file );
          this.recentlyUsedFiles.remove ( item );
          this.recentlyUsedFiles.add ( 0, item );
          if ( this.recentlyUsedFiles.size () > 10 )
          {
            this.recentlyUsedFiles.remove ( 10 );
          }
          organizeRecentlyUsedFilesMenu ();
        }
        return;
      }
    }

    try
    {
      DefaultModel element = ( DefaultModel ) Storage.getInstance ().load (
          file );

      if ( element instanceof DefaultMachineModel )
      {
        DefaultMachineModel model = ( DefaultMachineModel ) element;
        EditorPanel newEditorPanel = new MachinePanel ( this.gui, model, file );

        jGTIEditorPanelTabbedPane.addEditorPanel ( newEditorPanel );
        newEditorPanel
            .addModifyStatusChangedListener ( this.modifyStatusChangedListener );
        jGTIEditorPanelTabbedPane.setSelectedEditorPanel ( newEditorPanel );
        jGTIEditorPanelTabbedPane.setEditorPanelTitle ( newEditorPanel, file
            .getName () );

        addButtonState ( ButtonState.ENABLED_GENERAL );
        addButtonState ( ButtonState.ENABLED_EDIT_DOCUMENT );
        addButtonState ( ButtonState.ENABLED_VALIDATE );
        addButtonState ( ButtonState.ENABLED_MACHINE_EDIT_ITEMS );
        addButtonState ( ButtonState.ENABLED_DRAFT_FOR );
      }
      else if ( element instanceof DefaultGrammarModel )
      {
        DefaultGrammarModel model = ( DefaultGrammarModel ) element;
        EditorPanel newEditorPanel = new GrammarPanel ( this.gui, model, file );

        jGTIEditorPanelTabbedPane.addEditorPanel ( newEditorPanel );
        newEditorPanel
            .addModifyStatusChangedListener ( this.modifyStatusChangedListener );
        jGTIEditorPanelTabbedPane.setSelectedEditorPanel ( newEditorPanel );
        jGTIEditorPanelTabbedPane.setEditorPanelTitle ( newEditorPanel, file
            .getName () );

        addButtonState ( ButtonState.ENABLED_GENERAL );
        addButtonState ( ButtonState.ENABLED_EDIT_DOCUMENT );
        addButtonState ( ButtonState.ENABLED_VALIDATE );
        addButtonState ( ButtonState.ENABLED_DRAFT_FOR );
      }
      else
      {
        throw new RuntimeException ( "not supported element" ); //$NON-NLS-1$
      }

      // reorganize recently used files
      if ( addToRecentlyUsed )
      {
        RecentlyUsedMenuItem item = new RecentlyUsedMenuItem ( this, file );
        this.recentlyUsedFiles.remove ( item );
        this.recentlyUsedFiles.add ( 0, item );
        if ( this.recentlyUsedFiles.size () > 10 )
        {
          this.recentlyUsedFiles.remove ( 10 );
        }
        organizeRecentlyUsedFilesMenu ();
      }
    }
    catch ( StoreException exc )
    {
      InfoDialog infoDialog = new InfoDialog ( this.gui, exc.getMessage (),
          Messages.getString ( "MainWindow.ErrorLoad" ) ); //$NON-NLS-1$
      infoDialog.show ();
    }
    PreferenceManager.getInstance ().setWorkingPath (
        file.getParentFile ().getAbsolutePath () );
  }


  /**
   * Organizes the recently used files in the menu.
   */
  private final void organizeRecentlyUsedFilesMenu ()
  {
    ArrayList < RecentlyUsedMenuItem > notExistingFiles = new ArrayList < RecentlyUsedMenuItem > ();

    this.gui.getJMenuRecentlyUsed ().removeAll ();

    for ( RecentlyUsedMenuItem item : this.recentlyUsedFiles )
    {
      if ( item.getFile ().exists () )
      {
        this.gui.getJMenuRecentlyUsed ().add ( item );
      }
      else
      {
        notExistingFiles.add ( item );
      }
    }

    this.recentlyUsedFiles.removeAll ( notExistingFiles );

    if ( this.recentlyUsedFiles.size () > 0 )
    {
      addButtonState ( ButtonState.ENABLED_RECENTLY_USED );
    }
    else
    {
      removeButtonState ( ButtonState.ENABLED_RECENTLY_USED );
    }
  }


  /**
   * Removes the given {@link ButtonState}.
   * 
   * @param buttonState The {@link ButtonState} to remove.
   */
  public final void removeButtonState ( ButtonState buttonState )
  {
    if ( buttonState.equals ( ButtonState.ENABLED_GENERAL ) )
    {
      this.buttonStateList.remove ( ButtonState.ENABLED_GENERAL );
      this.gui.getJGTIToolBarButtonSaveAs ().setEnabled ( false );
      this.gui.getJMenuItemSaveAs ().setEnabled ( false );
      this.gui.getJMenuItemSaveAll ().setEnabled ( false );
      this.gui.getJMenuItemClose ().setEnabled ( false );
      this.gui.getJMenuItemCloseAll ().setEnabled ( false );
    }
    else if ( buttonState.equals ( ButtonState.ENABLED_UNDO ) )
    {
      this.buttonStateList.remove ( ButtonState.ENABLED_UNDO );
      this.gui.getJMenuItemUndo ().setEnabled ( false );
      this.gui.getJGTIToolBarButtonUndo ().setEnabled ( false );
    }
    else if ( buttonState.equals ( ButtonState.ENABLED_REDO ) )
    {
      this.buttonStateList.remove ( ButtonState.ENABLED_REDO );
      this.gui.getJMenuItemRedo ().setEnabled ( false );
      this.gui.getJGTIToolBarButtonRedo ().setEnabled ( false );
    }
    else if ( buttonState.equals ( ButtonState.ENABLED_HISTORY ) )
    {
      this.buttonStateList.remove ( ButtonState.ENABLED_HISTORY );
      this.gui.getJMenuItemHistory ().setEnabled ( false );
    }
    else if ( buttonState.equals ( ButtonState.ENABLED_MACHINE_TABLE ) )
    {
      this.buttonStateList.remove ( ButtonState.ENABLED_MACHINE_TABLE );
      this.gui.getJCheckBoxMenuItemTable ().setEnabled ( false );
    }
    else if ( buttonState.equals ( ButtonState.ENABLED_CONSOLE_TABLE ) )
    {
      this.buttonStateList.remove ( ButtonState.ENABLED_CONSOLE_TABLE );
      this.gui.getJCheckBoxMenuItemConsole ().setEnabled ( false );
    }
    else if ( buttonState.equals ( ButtonState.ENABLED_VALIDATE ) )
    {
      this.buttonStateList.remove ( ButtonState.ENABLED_VALIDATE );
      this.gui.getJMenuItemValidate ().setEnabled ( false );
    }
    else if ( buttonState.equals ( ButtonState.ENABLED_DRAFT_FOR ) )
    {
      this.buttonStateList.remove ( ButtonState.ENABLED_DRAFT_FOR );
      this.gui.getJMenuDraft ().setEnabled ( false );
    }
    else if ( buttonState.equals ( ButtonState.ENABLED_DRAFT_FOR_MACHINE ) )
    {
      this.buttonStateList.remove ( ButtonState.ENABLED_DRAFT_FOR_MACHINE );
      this.gui.getJMenuItemDFA ().setEnabled ( false );
      this.gui.getJMenuItemNFA ().setEnabled ( false );
      this.gui.getJMenuItemENFA ().setEnabled ( false );
      this.gui.getJMenuItemPDA ().setEnabled ( false );
    }
    else if ( buttonState.equals ( ButtonState.ENABLED_DRAFT_FOR_GRAMMAR ) )
    {
      this.buttonStateList.remove ( ButtonState.ENABLED_DRAFT_FOR_GRAMMAR );
      this.gui.getJMenuItemRG ().setEnabled ( false );
      this.gui.getJMenuItemCFG ().setEnabled ( false );
    }
    else if ( buttonState.equals ( ButtonState.ENABLED_EDIT_DOCUMENT ) )
    {
      this.buttonStateList.remove ( ButtonState.ENABLED_EDIT_DOCUMENT );
      this.gui.getJGTIToolBarButtonEditDocument ().setEnabled ( false );
    }
    else if ( buttonState.equals ( ButtonState.ENABLED_EDIT_MACHINE ) )
    {
      this.buttonStateList.remove ( ButtonState.ENABLED_EDIT_MACHINE );
      this.gui.getJMenuItemEditMachine ().setEnabled ( false );
    }
    else if ( buttonState.equals ( ButtonState.ENABLED_ENTER_WORD ) )
    {
      this.buttonStateList.remove ( ButtonState.ENABLED_ENTER_WORD );
      this.gui.getJMenuItemEnterWord ().setEnabled ( false );
    }
    else if ( buttonState.equals ( ButtonState.ENABLED_AUTO_LAYOUT ) )
    {
      this.buttonStateList.remove ( ButtonState.ENABLED_AUTO_LAYOUT );
      this.gui.getJMenuItemAutoLayout ().setEnabled ( false );
    }
    else if ( buttonState.equals ( ButtonState.ENABLED_RECENTLY_USED ) )
    {
      this.buttonStateList.remove ( ButtonState.ENABLED_RECENTLY_USED );
      this.gui.getJMenuRecentlyUsed ().setEnabled ( false );
    }
    else if ( buttonState.equals ( ButtonState.ENABLED_MACHINE_EDIT_ITEMS ) )
    {
      this.buttonStateList.remove ( ButtonState.ENABLED_MACHINE_EDIT_ITEMS );
      this.gui.getJGTIToolBarToggleButtonAddState ().setEnabled ( false );
      this.gui.getJGTIToolBarToggleButtonAddTransition ().setEnabled ( false );
      this.gui.getJGTIToolBarToggleButtonFinalState ().setEnabled ( false );
      this.gui.getJGTIToolBarToggleButtonMouse ().setEnabled ( false );
      this.gui.getJGTIToolBarToggleButtonStartState ().setEnabled ( false );
    }
    else if ( buttonState.equals ( ButtonState.ENABLED_NAVIGATION_DEACTIVE ) )
    {
      throw new IllegalArgumentException (
          "remove navigation state not supported, use add instead" );//$NON-NLS-1$
    }
    else if ( buttonState.equals ( ButtonState.ENABLED_NAVIGATION_START ) )
    {
      throw new IllegalArgumentException (
          "remove navigation state not supported, use add instead" );//$NON-NLS-1$
    }
    else if ( buttonState.equals ( ButtonState.ENABLED_NAVIGATION_STEPS ) )
    {
      throw new IllegalArgumentException (
          "remove navigation state not supported, use add instead" ); //$NON-NLS-1$
    }
    else if ( buttonState.equals ( ButtonState.ENABLED_CONVERT_TO ) )
    {
      this.buttonStateList.remove ( ButtonState.ENABLED_CONVERT_TO );
      this.gui.getJMenuConvertTo ().setEnabled ( false );
    }
    else if ( buttonState.equals ( ButtonState.ENABLED_MINIMIZE ) )
    {
      this.buttonStateList.remove ( ButtonState.ENABLED_MINIMIZE );
      this.gui.getJMenuItemMinimize ().setEnabled ( false );
    }
    else if ( buttonState.equals ( ButtonState.ENABLED_SAVE ) )
    {
      this.buttonStateList.remove ( ButtonState.ENABLED_SAVE );
      logger.debug ( "setSaveState", "set save status to " + Messages.QUOTE //$NON-NLS-1$//$NON-NLS-2$
          + false + Messages.QUOTE );
      EditorPanel panel = this.jGTIMainSplitPane
          .getJGTIEditorPanelTabbedPane ().getSelectedEditorPanel ();
      if ( panel != null )
      {
        this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPane ()
            .setEditorPanelTitle ( panel, panel.getName () );
      }
      this.gui.getJGTIToolBarButtonSave ().setEnabled ( false );
      this.gui.getJMenuItemSave ().setEnabled ( false );
    }
    // selected
    else if ( buttonState.equals ( ButtonState.SELECTED_MACHINE_TABLE ) )
    {
      this.buttonStateList.remove ( ButtonState.SELECTED_MACHINE_TABLE );
      this.gui.getJCheckBoxMenuItemTable ().setSelected ( false );
    }
    else if ( buttonState.equals ( ButtonState.SELECTED_CONSOLE_TABLE ) )
    {
      this.buttonStateList.remove ( ButtonState.SELECTED_CONSOLE_TABLE );
      this.gui.getJCheckBoxMenuItemConsole ().setSelected ( false );
    }
    else if ( buttonState.equals ( ButtonState.SELECTED_MOUSE ) )
    {
      this.buttonStateList.remove ( ButtonState.SELECTED_MOUSE );
      this.gui.getJGTIToolBarToggleButtonMouse ().setSelected ( false );
    }
    else if ( buttonState.equals ( ButtonState.SELECTED_AUTO_STEP ) )
    {
      this.buttonStateList.remove ( ButtonState.SELECTED_AUTO_STEP );
      this.gui.getJGTIToolBarToggleButtonAutoStep ().setSelected ( false );
    }
    // visible
    else if ( buttonState.equals ( ButtonState.VISIBLE_MACHINE ) )
    {
      this.buttonStateList.remove ( ButtonState.VISIBLE_MACHINE );
      this.gui.getJSeparatorNavigation ().setVisible ( false );
      this.gui.getJGTIToolBarToggleButtonMouse ().setVisible ( false );
      this.gui.getJGTIToolBarToggleButtonAddState ().setVisible ( false );
      this.gui.getJGTIToolBarToggleButtonStartState ().setVisible ( false );
      this.gui.getJGTIToolBarToggleButtonFinalState ().setVisible ( false );
      this.gui.getJGTIToolBarToggleButtonAddTransition ().setVisible ( false );
      this.gui.getJGTIToolBarButtonStart ().setVisible ( false );
      this.gui.getJGTIToolBarButtonPrevious ().setVisible ( false );
      this.gui.getJGTIToolBarButtonNextStep ().setVisible ( false );
      this.gui.getJGTIToolBarToggleButtonAutoStep ().setVisible ( false );
      this.gui.getJGTIToolBarButtonStop ().setVisible ( false );
    }
    else if ( buttonState.equals ( ButtonState.VISIBLE_GRAMMAR ) )
    {
      this.buttonStateList.remove ( ButtonState.VISIBLE_GRAMMAR );
      this.gui.getJGTIToolBarButtonAddProduction ().setVisible ( false );
      this.gui.getJGTIToolBarButtonEditProduction ().setVisible ( false );
      this.gui.getJGTIToolBarButtonDeleteProduction ().setVisible ( false );
    }
    else
    {
      throw new IllegalArgumentException ( "unsupported button state: " //$NON-NLS-1$
          + buttonState );
    }
  }


  /**
   * Open all files which was open at last session
   */
  public final void restoreOpenFiles ()
  {
    for ( ObjectPair < File, ActiveEditor > current : PreferenceManager
        .getInstance ().getOpenedFilesItem ().getFiles () )
    {
      openFile ( current.getFirst (), false, current.getSecond () );
    }

    File activeFile = PreferenceManager.getInstance ().getOpenedFilesItem ()
        .getActiveFile ();

    if ( activeFile != null )
    {
      // left editor
      for ( EditorPanel current : this.jGTIMainSplitPane
          .getJGTIEditorPanelTabbedPaneLeft () )
      {
        if ( current.getFile ().getAbsolutePath ().equals (
            activeFile.getAbsolutePath () ) )
        {
          this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPaneLeft ()
              .setSelectedEditorPanel ( current );
          this.jGTIMainSplitPane.setActiveEditor ( ActiveEditor.LEFT_EDITOR );
          break;
        }
      }
      // right editor
      for ( EditorPanel current : this.jGTIMainSplitPane
          .getJGTIEditorPanelTabbedPaneRight () )
      {
        if ( current.getFile ().getAbsolutePath ().equals (
            activeFile.getAbsolutePath () ) )
        {
          this.jGTIMainSplitPane.getJGTIEditorPanelTabbedPaneRight ()
              .setSelectedEditorPanel ( current );
          this.jGTIMainSplitPane.setActiveEditor ( ActiveEditor.RIGHT_EDITOR );
          break;
        }
      }
    }
  }
}
