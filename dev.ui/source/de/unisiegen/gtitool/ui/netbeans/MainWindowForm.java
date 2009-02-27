package de.unisiegen.gtitool.ui.netbeans;



import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JToolBar;

import de.unisiegen.gtitool.core.grammars.Grammar.GrammarType;
import de.unisiegen.gtitool.core.machines.Machine.MachineType;
import de.unisiegen.gtitool.core.regex.DefaultRegex.RegexType;
import de.unisiegen.gtitool.ui.logic.MainWindow;
import de.unisiegen.gtitool.ui.netbeans.interfaces.GUIClass;
import de.unisiegen.gtitool.ui.swing.JGTIPanel;
import de.unisiegen.gtitool.ui.swing.specialized.JGTIEditorPanelTabbedPane;
import de.unisiegen.gtitool.ui.swing.specialized.JGTIMainSplitPane;
import de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton;
import de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarToggleButton;

/**
 * The {@link MainWindowForm}.
 *
 * @author Benjamin Mies
 * @author Christian Fehler
 * @version $Id: MainWindowForm.java 1356 2008-10-19 17:30:12Z fehler $
 */
@SuppressWarnings({ "all" })
public class MainWindowForm extends JFrame implements GUIClass <MainWindow>
{
 
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = -5068944082893352104L;


    /**
     * The {@link MainWindow}.
     */
    private MainWindow logic;
    

    /**
     * Creates new form MainWindow
     * 
     * @param logic The {@link MainWindow}.
     */
    public MainWindowForm(MainWindow logic) {
        this.logic = logic;
        initComponents();
    }
    
    /**
     * {@inheritDoc}
     * 
     * @see GUIClass#getLogic()
     */
    public final MainWindow getLogic ()
    {
      return this.logic;
    }
    
    /**
     * Returns the jMenuItemNew.
     *
     * @return The jMenuItemNew.
     * @see #jMenuItemNew
     */
    public final JMenuItem getJMenuItemNew ()
    {
      return this.jMenuItemNew;
    }
    
    /**
     * Returns the jMenuItemOpen.
     *
     * @return The jMenuItemOpen.
     * @see #jMenuItemOpen
     */
    public final JMenuItem getJMenuItemOpen ()
    {
      return this.jMenuItemOpen;
    }
    
    /**
     * Returns the jMenuItemClose.
     *
     * @return The jMenuItemClose.
     * @see #jMenuItemClose
     */
    public final JMenuItem getJMenuItemClose ()
    {
      return this.jMenuItemClose;
    }

    
    
    /**
     * Returns the jMenuItemExportLatex.
     *
     * @return The jMenuItemExportLatex.
     * @see #jMenuItemExportLatex
     */
    public JMenuItem getJMenuItemExportLatex() {
        return this.jMenuItemExportLatex;
    }
    
    /**
     * Returns the jMenuItemCloseAll.
     *
     * @return The jMenuItemCloseAll.
     * @see #jMenuItemCloseAll
     */
    public final JMenuItem getJMenuItemCloseAll ()
    {
      return this.jMenuItemCloseAll;
    }

    /**
     * Returns the jMenuItemPrint.
     *
     * @return The jMenuItemCloseAll.
     * @see #jMenuItemPrint
     */
    public final JMenuItem getJMenuItemPrint ()
    {
      return this.jMenuItemPrint;
    }

    /**
     * Returns the jMenuItemConvertToDFA.
     *
     * @return The jMenuItemConvertToDFA.
     * @see #jMenuItemConvertToDFA
     */
    public final javax.swing.JMenuItem getJMenuItemConvertToDFA ()
    {
      return this.jMenuItemConvertToDFA;
    }

    
    /**
     * Returns the jMenuItemConvertToENFA.
     *
     * @return The jMenuItemConvertToENFA.
     * @see #jMenuItemConvertToENFA
     */
    public final javax.swing.JMenuItem getJMenuItemConvertToENFA ()
    {
      return this.jMenuItemConvertToENFA;
    }

    
    /**
     * Returns the jMenuItemConvertToNFA.
     *
     * @return The jMenuItemConvertToNFA.
     * @see #jMenuItemConvertToNFA
     */
    public final javax.swing.JMenuItem getJMenuItemConvertToNFA ()
    {
      return this.jMenuItemConvertToNFA;
    }

    /**
     * Returns the jMenuItemConvertToPDA.
     *
     * @return The jMenuItemConvertToPDA.
     * @see #jMenuItemConvertToPDA
     */
    public final javax.swing.JMenuItem getJMenuItemConvertToPDA ()
    {
      return this.jMenuItemConvertToPDA;
    }

    /**
     * Returns the jMenuItemConvertToCompleteDFA.
     *
     * @return The jMenuItemConvertToCompleteDFA.
     * @see #jMenuItemConvertToCompleteDFA
     */
    public final javax.swing.JMenuItem getJMenuItemConvertToCompleteDFA ()
    {
      return this.jMenuItemConvertToCompleteDFA;
    }

    
    /**
     * Returns the jMenuItemConvertToCompleteENFA.
     *
     * @return The jMenuItemConvertToCompleteENFA.
     * @see #jMenuItemConvertToCompleteENFA
     */
    public final javax.swing.JMenuItem getJMenuItemConvertToCompleteENFA ()
    {
      return this.jMenuItemConvertToCompleteENFA;
    }

    
    /**
     * Returns the jMenuItemConvertToCompleteNFA.
     *
     * @return The jMenuItemConvertToCompleteNFA.
     * @see #jMenuItemConvertToCompleteNFA
     */
    public final javax.swing.JMenuItem getJMenuItemConvertToCompleteNFA ()
    {
      return this.jMenuItemConvertToCompleteNFA;
    }

    /**
     * Returns the jMenuItemConvertToCompletePDA.
     *
     * @return The jMenuItemConvertToCompletePDA.
     * @see #jMenuItemConvertToCompletePDA
     */
    public final javax.swing.JMenuItem getJMenuItemConvertToCompletePDA ()
    {
      return this.jMenuItemConvertToCompletePDA;
    }

    /**
     * Returns the jMenuItemToCoreSyntax.
     * 
     * @return The jMenuItemToCoreSyntax.
     */
    public JMenuItem getJMenuItemToCoreSyntax() {
        return this.jMenuItemToCoreSyntax;
    }

    /**
     * Returns the jMenuItemSave.
     *
     * @return The jMenuItemSave.
     * @see #jMenuItemSave
     */
    public final JMenuItem getJMenuItemSave ()
    {
      return this.jMenuItemSave;
    }
    
    /**
     * Returns the jMenuItemSaveAs.
     *
     * @return The jMenuItemSaveAs.
     * @see #jMenuItemSave
     */
    public final JMenuItem getJMenuItemSaveAs ()
    {
      return this.jMenuItemSaveAs;
    }
    
    /**
     * Returns the jMenuItemSaveAll.
     *
     * @return The jMenuItemSaveAll.
     * @see #jMenuItemSave
     */
    public final JMenuItem getJMenuItemSaveAll ()
    {
      return this.jMenuItemSaveAll;
    }
    
    /**
     * Returns the jMenuItemUndo.
     *
     * @return The jMenuItemUndo.
     * @see #jMenuItemUndo
     */
    public final JMenuItem getJMenuItemUndo ()
    {
      return this.jMenuItemUndo;
    }
    
    /**
     * Returns the jMenuItemRedo.
     *
     * @return The jMenuItemRedo.
     * @see #jMenuItemRedo
     */
    public final JMenuItem getJMenuItemRedo ()
    {
      return this.jMenuItemRedo;
    }
    
    /**
     * Returns the jMenuItemPreferences.
     *
     * @return The jMenuItemPreferences.
     * @see #jMenuItemPreferences
     */
    public final JMenuItem getJMenuItemPreferences ()
    {
      return this.jMenuItemPreferences;
    }
    
    /**
     * Returns the jMenuItemValidate.
     *
     * @return The jMenuItemValidate.
     * @see #jMenuItemValidate
     */
    public final JMenuItem getJMenuItemValidate ()
    {
      return this.jMenuItemValidate;
    }
    
    /**
     * Returns the left {@link JGTIEditorPanelTabbedPane}.
     *
     * @return The left {@link JGTIEditorPanelTabbedPane}.
     * @see #jGTIEditorPanelTabbedPaneLeft
     */
    public final JGTIEditorPanelTabbedPane getJGTIEditorPanelTabbedPaneLeft ()
    {
      return this.jGTIEditorPanelTabbedPaneLeft;
    }

    /**
     * Returns the right {@link JGTIEditorPanelTabbedPane}.
     *
     * @return The right {@link JGTIEditorPanelTabbedPane}.
     * @see #jGTIEditorPanelTabbedPaneRight
     */
    public final JGTIEditorPanelTabbedPane getJGTIEditorPanelTabbedPaneRight ()
    {
      return this.jGTIEditorPanelTabbedPaneRight;
    }

    /**
     * Returns the outer left {@link JGTIPanel}.
     *
     * @return The outer left  {@link JGTIPanel}.
     * @see #jGTIPanelLeftOuter
     */
    public final JGTIPanel getJGTIPanelLeftOuter ()
    {
      return this.jGTIPanelLeftOuter;
    }

    public JMenuItem getJMenuItemEliminateLeftRecursion() {
        return this.jMenuItemEliminateLeftRecursion;
    }
    
    

    /**
     * Returns the outer right {@link JGTIPanel}.
     *
     * @return The outer right  {@link JGTIPanel}.
     * @see #jGTIPanelRightOuter
     */
    public final JGTIPanel getJGTIPanelRightOuter ()
    {
      return this.jGTIPanelRightOuter;
    }

    /**
     * Returns the inner left {@link JGTIPanel}.
     *
     * @return The inner left  {@link JGTIPanel}.
     * @see #jGTIPanelLeftInner
     */
    public final JGTIPanel getJGTIPanelLeftInner ()
    {
      return this.jGTIPanelLeftInner;
    }

    /**
     * Returns the inner right {@link JGTIPanel}.
     *
     * @return The inner right  {@link JGTIPanel}.
     * @see #jGTIPanelRightInner
     */
    public final JGTIPanel getJGTIPanelRightInner ()
    {
      return this.jGTIPanelRightInner;
    }

    /**
     * Returns the {@link JGTIMainSplitPane}.
     *
     * @return The {@link JGTIMainSplitPane}.
     * @see #jGTIMainSplitPane
     */
    public final JGTIMainSplitPane getJGTIMainSplitPane ()
    {
      return this.jGTIMainSplitPane;
    }

    /**
     * Returns the jCheckBoxMenuItemConsole.
     *
     * @return The jCheckBoxMenuItemConsole.
     * @see #jCheckBoxMenuItemConsole
     */
    public final JCheckBoxMenuItem getJCheckBoxMenuItemConsole ()
    {
      return this.jCheckBoxMenuItemConsole;
    }

    
    /**
     * Returns the jCheckBoxMenuItemTable.
     *
     * @return The jCheckBoxMenuItemTable.
     * @see #jCheckBoxMenuItemTable
     */
    public final JCheckBoxMenuItem getJCheckBoxMenuItemTable ()
    {
      return this.jCheckBoxMenuItemTable;
    }


    /**
     * Returns the jCheckBoxMenuItemSecondView.
     *
     * @return The jCheckBoxMenuItemSecondView.
     * @see #jCheckBoxMenuItemSecondView
     */
    public final JCheckBoxMenuItem getJCheckBoxMenuItemSecondView ()
    {
      return this.jCheckBoxMenuItemSecondView;
    }
    
    /**
     * Returns the jGTIToolBarButtonAddProduction.
     *
     * @return The jGTIToolBarButtonAddProduction.
     * @see #jGTIToolBarButtonAddProduction
     */
    public final JGTIToolBarButton getJGTIToolBarButtonAddProduction ()
    {
      return this.jGTIToolBarButtonAddProduction;
    }

    
    /**
     * Returns the jGTIToolBarButtonEditDocument.
     *
     * @return The jGTIToolBarButtonEditDocument.
     * @see #jGTIToolBarButtonEditDocument
     */
    public final JGTIToolBarButton getJGTIToolBarButtonEditDocument ()
    {
      return this.jGTIToolBarButtonEditDocument;
    }


    /**
     * Returns the jGTIToolBarButtonNew.
     *
     * @return The jGTIToolBarButtonNew.
     * @see #jGTIToolBarButtonNew
     */
    public final JGTIToolBarButton getJGTIToolBarButtonNew ()
    {
      return this.jGTIToolBarButtonNew;
    }

    
    /**
     * Returns the jGTIToolBarButtonNextStep.
     *
     * @return The jGTIToolBarButtonNextStep.
     * @see #jGTIToolBarButtonNextStep
     */
    public final JGTIToolBarButton getJGTIToolBarButtonNextStep ()
    {
      return this.jGTIToolBarButtonNextStep;
    }

    
    /**
     * Returns the jGTIToolBarButtonOpen.
     *
     * @return The jGTIToolBarButtonOpen.
     * @see #jGTIToolBarButtonOpen
     */
    public final JGTIToolBarButton getJGTIToolBarButtonOpen ()
    {
      return this.jGTIToolBarButtonOpen;
    }

    
    /**
     * Returns the jGTIToolBarButtonPreviousStep.
     *
     * @return The jGTIToolBarButtonPreviousStep.
     * @see #jGTIToolBarButtonPreviousStep
     */
    public final JGTIToolBarButton getJGTIToolBarButtonPreviousStep ()
    {
      return this.jGTIToolBarButtonPreviousStep;
    }

    
    /**
     * Returns the jGTIToolBarButtonRedo.
     *
     * @return The jGTIToolBarButtonRedo.
     * @see #jGTIToolBarButtonRedo
     */
    public final JGTIToolBarButton getJGTIToolBarButtonRedo ()
    {
      return this.jGTIToolBarButtonRedo;
    }

    
    /**
     * Returns the jGTIToolBarButtonSave.
     *
     * @return The jGTIToolBarButtonSave.
     * @see #jGTIToolBarButtonSave
     */
    public final JGTIToolBarButton getJGTIToolBarButtonSave ()
    {
      return this.jGTIToolBarButtonSave;
    }

    
    /**
     * Returns the jGTIToolBarButtonSaveAs.
     *
     * @return The jGTIToolBarButtonSaveAs.
     * @see #jGTIToolBarButtonSaveAs
     */
    public final JGTIToolBarButton getJGTIToolBarButtonSaveAs ()
    {
      return this.jGTIToolBarButtonSaveAs;
    }

    
    /**
     * Returns the jGTIToolBarButtonStart.
     *
     * @return The jGTIToolBarButtonStart.
     * @see #jGTIToolBarButtonStart
     */
    public final JGTIToolBarButton getJGTIToolBarButtonStart ()
    {
      return this.jGTIToolBarButtonStart;
    }

    
    /**
     * Returns the jGTIToolBarButtonStop.
     *
     * @return The jGTIToolBarButtonStop.
     * @see #jGTIToolBarButtonStop
     */
    public final JGTIToolBarButton getJGTIToolBarButtonStop ()
    {
      return this.jGTIToolBarButtonStop;
    }

    
    /**
     * Returns the jGTIToolBarButtonUndo.
     *
     * @return The jGTIToolBarButtonUndo.
     * @see #jGTIToolBarButtonUndo
     */
    public final JGTIToolBarButton getJGTIToolBarButtonUndo ()
    {
      return this.jGTIToolBarButtonUndo;
    }

    
    /**
     * Returns the jGTIToolBarToggleButtonAddState.
     *
     * @return The jGTIToolBarToggleButtonAddState.
     * @see #jGTIToolBarToggleButtonAddState
     */
    public final JGTIToolBarToggleButton getJGTIToolBarToggleButtonAddState ()
    {
      return this.jGTIToolBarToggleButtonAddState;
    }

    
    /**
     * Returns the jGTIToolBarToggleButtonAddTransition.
     *
     * @return The jGTIToolBarToggleButtonAddTransition.
     * @see #jGTIToolBarToggleButtonAddTransition
     */
    public final JGTIToolBarToggleButton getJGTIToolBarToggleButtonAddTransition ()
    {
      return this.jGTIToolBarToggleButtonAddTransition;
    }

    
    /**
     * Returns the jGTIToolBarToggleButtonAutoStep.
     *
     * @return The jGTIToolBarToggleButtonAutoStep.
     * @see #jGTIToolBarToggleButtonAutoStep
     */
    public final JGTIToolBarToggleButton getJGTIToolBarToggleButtonAutoStep ()
    {
      return this.jGTIToolBarToggleButtonAutoStep;
    }

    
    /**
     * Returns the jGTIToolBarToggleButtonFinalState.
     *
     * @return The jGTIToolBarToggleButtonFinalState.
     * @see #jGTIToolBarToggleButtonFinalState
     */
    public final JGTIToolBarToggleButton getJGTIToolBarToggleButtonFinalState ()
    {
      return this.jGTIToolBarToggleButtonFinalState;
    }

    /**
     * Returns the jGTIToolBarToggleButtonEnterWord.
     *
     * @return The jGTIToolBarToggleButtonEnterWord.
     * @see #jGTIToolBarToggleButtonEnterWord
     */
    public final JGTIToolBarToggleButton getJGTIToolBarToggleButtonEnterWord ()
    {
      return this.jGTIToolBarToggleButtonEnterWord;
    }

    /**
     * Returns the jGTIToolBarToggleButtonMouse.
     *
     * @return The jGTIToolBarToggleButtonMouse.
     * @see #jGTIToolBarToggleButtonMouse
     */
    public final JGTIToolBarToggleButton getJGTIToolBarToggleButtonMouse ()
    {
      return this.jGTIToolBarToggleButtonMouse;
    }

    
    /**
     * Returns the jGTIToolBarToggleButtonStartState.
     *
     * @return The jGTIToolBarToggleButtonStartState.
     * @see #jGTIToolBarToggleButtonStartState
     */
    public final JGTIToolBarToggleButton getJGTIToolBarToggleButtonStartState ()
    {
      return this.jGTIToolBarToggleButtonStartState;
    }

    
    /**
     * Returns the jMenuBarMain.
     *
     * @return The jMenuBarMain.
     * @see #jMenuBarMain
     */
    public final JMenuBar getJMenuBarMain ()
    {
      return this.jMenuBarMain;
    }

    /**
     * Returns the jMenuConvertTo.
     *
     * @return The jMenuConvertTo.
     * @see #jMenuConvertTo
     */
    public final JMenuItem getJMenuConvertTo ()
    {
      return this.jMenuConvertTo;
    }

    /**
     * Returns the jMenuConvertToComplete.
     *
     * @return The jMenuConvertToComplete.
     * @see #jMenuConvertToComplete
     */
    public final JMenuItem getJMenuConvertToComplete ()
    {
      return this.jMenuConvertToComplete;
    }

    /**
     * Returns the jMenuDraft.
     *
     * @return The jMenuDraft.
     * @see #jMenuDraft
     */
    public final JMenu getJMenuDraft ()
    {
      return this.jMenuDraft;
    }

    
    /**
     * Returns the jMenuEdit.
     *
     * @return The jMenuEdit.
     * @see #jMenuEdit
     */
    public final JMenu getJMenuEdit ()
    {
      return this.jMenuEdit;
    }

    
    /**
     * Returns the jMenuExecute.
     *
     * @return The jMenuExecute.
     * @see #jMenuExecute
     */
    public final JMenu getJMenuExecute ()
    {
      return this.jMenuExecute;
    }

    
    /**
     * Returns the jMenuExtras.
     *
     * @return The jMenuExtras.
     * @see #jMenuExtras
     */
    public final JMenu getJMenuExtras ()
    {
      return this.jMenuExtras;
    }

    
    /**
     * Returns the jMenuFile.
     *
     * @return The jMenuFile.
     * @see #jMenuFile
     */
    public final JMenu getJMenuFile ()
    {
      return this.jMenuFile;
    }

    
    /**
     * Returns the jMenuHelp.
     *
     * @return The jMenuHelp.
     * @see #jMenuHelp
     */
    public final JMenu getJMenuHelp ()
    {
      return this.jMenuHelp;
    }

    
    /**
     * Returns the jMenuItemAbout.
     *
     * @return The jMenuItemAbout.
     * @see #jMenuItemAbout
     */
    public final JMenuItem getJMenuItemAbout ()
    {
      return this.jMenuItemAbout;
    }
    
    /**
     * Returns the jMenuItemAutoLayout.
     *
     * @return The jMenuItemAutoLayout.
     * @see #jMenuItemAutoLayout
     */
    public final JMenuItem getJMenuItemAutoLayout ()
    {
      return this.jMenuItemAutoLayout;
    }

    
    /**
     * Returns the jMenuItemCFG.
     *
     * @return The jMenuItemCFG.
     * @see #jMenuItemCFG
     */
    public final JMenuItem getJMenuItemCFG ()
    {
      return this.jMenuItemCFG;
    }

    
    /**
     * Returns the jMenuItemDFA.
     *
     * @return The jMenuItemDFA.
     * @see #jMenuItemDFA
     */
    public final JMenuItem getJMenuItemDFA ()
    {
      return this.jMenuItemDFA;
    }

    
    /**
     * Returns the jMenuItemENFA.
     *
     * @return The jMenuItemENFA.
     * @see #jMenuItemENFA
     */
    public final JMenuItem getJMenuItemENFA ()
    {
      return this.jMenuItemENFA;
    }

    
    /**
     * Returns the jMenuItemEditMachine.
     *
     * @return The jMenuItemEditMachine.
     * @see #jMenuItemEditMachine
     */
    public final JMenuItem getJMenuItemEditMachine ()
    {
      return this.jMenuItemEditMachine;
    }

    
    /**
     * Returns the jMenuItemEnterWord.
     *
     * @return The jMenuItemEnterWord.
     * @see #jMenuItemEnterWord
     */
    public final JMenuItem getJMenuItemEnterWord ()
    {
      return this.jMenuItemEnterWord;
    }

    
    /**
     * Returns the jMenuItemExchange.
     *
     * @return The jMenuItemExchange.
     * @see #jMenuItemExchange
     */
    public final JMenuItem getJMenuItemExchange ()
    {
      return this.jMenuItemExchange;
    }

    
    /**
     * Returns the jMenuItemHistory.
     *
     * @return The jMenuItemHistory.
     * @see #jMenuItemHistory
     */
    public final JMenuItem getJMenuItemHistory ()
    {
      return this.jMenuItemHistory;
    }

    
    /**
     * Returns the jCheckBoxMenuItemRegexInfo.
     *
     * @return The jCheckBoxMenuItemRegexInfo.
     * @see #jCheckBoxMenuItemRegexInfo
     */
    public final JCheckBoxMenuItem getJCheckBoxMenuItemRegexInfo ()
    {
      return this.jCheckBoxMenuItemRegexInfo;
    }


    /**
     * Returns the jMenuItemReachableStates.
     *
     * @return The jMenuItemReachableStates.
     * @see #jMenuItemReachableStates
     */
    public final JMenuItem getJMenuItemReachableStates ()
    {
      return this.jMenuItemReachableStates;
    }


    /**
     * Returns the jMenuItemExportPicture.
     *
     * @return The jMenuItemExportPicture.
     * @see #jMenuItemExportPicture
     */
    public final JMenuItem getJMenuItemExportPicture ()
    {
      return this.jMenuItemExportPicture;
    }


    /**
     * Returns the jMenuItemReorderStateNames.
     *
     * @return The jMenuItemReorderStateNames.
     * @see #jMenuItemReorderStateNames
     */
    public final JMenuItem getJMenuItemReorderStateNames ()
    {
      return this.jMenuItemReorderStateNames;
    }


    /**
     * Returns the jMenuItemNFA.
     *
     * @return The jMenuItemNFA.
     * @see #jMenuItemNFA
     */
    public final JMenuItem getJMenuItemNFA ()
    {
      return this.jMenuItemNFA;
    }

    
    /**
     * Returns the jMenuItemPDA.
     *
     * @return The jMenuItemPDA.
     * @see #jMenuItemPDA
     */
    public final JMenuItem getJMenuItemPDA ()
    {
      return this.jMenuItemPDA;
    }

    
    /**
     * Returns the jMenuItemQuit.
     *
     * @return The jMenuItemQuit.
     * @see #jMenuItemQuit
     */
    public final JMenuItem getJMenuItemQuit ()
    {
      return this.jMenuItemQuit;
    }

    
    /**
     * Returns the jMenuItemRG.
     *
     * @return The jMenuItemRG.
     * @see #jMenuItemRG
     */
    public final JMenuItem getJMenuItemRG ()
    {
      return this.jMenuItemRG;
    }

    
    /**
     * Returns the jMenuRecentlyUsed.
     *
     * @return The jMenuRecentlyUsed.
     * @see #jMenuRecentlyUsed
     */
    public final JMenu getJMenuRecentlyUsed ()
    {
      return this.jMenuRecentlyUsed;
    }

    
    /**
     * Returns the jMenuView.
     *
     * @return The jMenuView.
     * @see #jMenuView
     */
    public final JMenu getJMenuView ()
    {
      return this.jMenuView;
    }

    
    /**
     * Returns the jToolBarEdit.
     *
     * @return The jToolBarEdit.
     * @see #jToolBarEdit
     */
    public final JToolBar getJToolBarEdit ()
    {
      return this.jToolBarEdit;
    }

    public JMenuItem getJMenuItemLeftfactoring() {
        return this.jMenuItemLeftfactoring;
    }
    
    

    
    /**
     * Returns the jToolBarFile.
     *
     * @return The jToolBarFile.
     * @see #jToolBarFile
     */
    public final JToolBar getJToolBarFile ()
    {
      return this.jToolBarFile;
    }

    
    /**
     * Returns the jToolBarMain.
     *
     * @return The jToolBarMain.
     * @see #jToolBarMain
     */
    public final JToolBar getJToolBarMain ()
    {
      return this.jToolBarMain;
    }

    
    /**
     * Returns the jToolBarNavigation.
     *
     * @return The jToolBarNavigation.
     * @see #jToolBarNavigation
     */
    public final JToolBar getJToolBarNavigation ()
    {
      return this.jToolBarNavigation;
    }
    
    /**
     * Returns the jSeparatorNavigation.
     *
     * @return The jSeparatorNavigation.
     * @see #jSeparatorNavigation
     */
    public final JSeparator getJSeparatorNavigation ()
    {
      return this.jSeparatorNavigation;
    }
    
    /**
     * Returns the jMenuItemMinimize.
     *
     * @return The jMenuItemMinimize.
     * @see #jMenuItemMinimize
     */
    public final JMenuItem getJMenuItemMinimize ()
    {
      return this.jMenuItemMinimize;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        this.modeSettingsGroup = new javax.swing.ButtonGroup();
        this.toolbarButton = new javax.swing.ButtonGroup();
        this.jToolBarMain = new javax.swing.JToolBar();
        this.jToolBarFile = new javax.swing.JToolBar();
        this.jGTIToolBarButtonNew = new de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton();
        this.jGTIToolBarButtonOpen = new de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton();
        this.jGTIToolBarButtonSave = new de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton();
        this.jGTIToolBarButtonSaveAs = new de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton();
        this.jSeparatorFileEdit = new javax.swing.JSeparator();
        this.jToolBarEdit = new javax.swing.JToolBar();
        this.jGTIToolBarButtonUndo = new de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton();
        this.jGTIToolBarButtonRedo = new de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton();
        this.jSeparatorEditNavigation = new javax.swing.JSeparator();
        this.jToolBarNavigation = new javax.swing.JToolBar();
        this.jGTIToolBarButtonEditDocument = new de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton();
        this.jGTIToolBarToggleButtonMouse = new de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarToggleButton();
        this.jGTIToolBarToggleButtonAddState = new de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarToggleButton();
        this.jGTIToolBarToggleButtonAddTransition = new de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarToggleButton();
        this.jGTIToolBarToggleButtonStartState = new de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarToggleButton();
        this.jGTIToolBarToggleButtonFinalState = new de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarToggleButton();
        this.jGTIToolBarToggleButtonEnterWord = new de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarToggleButton();
        this.jGTIToolBarButtonAddProduction = new de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton();
        this.jSeparatorNavigation = new javax.swing.JSeparator();
        this.jGTIToolBarButtonStart = new de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton();
        this.jGTIToolBarButtonPreviousStep = new de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton();
        this.jGTIToolBarButtonNextStep = new de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton();
        this.jGTIToolBarToggleButtonAutoStep = new de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarToggleButton();
        this.jGTIToolBarButtonStop = new de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton();
        this.jGTIMainSplitPane = new de.unisiegen.gtitool.ui.swing.specialized.JGTIMainSplitPane();
        this.jGTIPanelLeftOuter = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        this.jGTIPanelLeftInner = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        this.jGTIEditorPanelTabbedPaneLeft = new de.unisiegen.gtitool.ui.swing.specialized.JGTIEditorPanelTabbedPane();
        this.jGTIPanelRightOuter = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        this.jGTIPanelRightInner = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        this.jGTIEditorPanelTabbedPaneRight = new de.unisiegen.gtitool.ui.swing.specialized.JGTIEditorPanelTabbedPane();
        this.jMenuBarMain = new javax.swing.JMenuBar();
        this.jMenuFile = new javax.swing.JMenu();
        this.jMenuItemNew = new javax.swing.JMenuItem();
        this.jMenuItemOpen = new javax.swing.JMenuItem();
        this.jMenuItemClose = new javax.swing.JMenuItem();
        this.jMenuItemCloseAll = new javax.swing.JMenuItem();
        this.jSeparatorFile1 = new javax.swing.JSeparator();
        this.jMenuItemPrint = new javax.swing.JMenuItem();
        this.jSeparatorFile2 = new javax.swing.JSeparator();
        this.jMenuItemSave = new javax.swing.JMenuItem();
        this.jMenuItemSaveAs = new javax.swing.JMenuItem();
        this.jMenuItemSaveAll = new javax.swing.JMenuItem();
        this.jSeparatorFile3 = new javax.swing.JSeparator();
        this.jMenuItemExportPicture = new javax.swing.JMenuItem();
        this.jMenuItemExportLatex = new javax.swing.JMenuItem();
        this.jSeparatorFile4 = new javax.swing.JSeparator();
        this.jMenuRecentlyUsed = new javax.swing.JMenu();
        this.jSeparatorFile5 = new javax.swing.JSeparator();
        this.jMenuItemQuit = new javax.swing.JMenuItem();
        this.jMenuEdit = new javax.swing.JMenu();
        this.jMenuItemUndo = new javax.swing.JMenuItem();
        this.jMenuItemRedo = new javax.swing.JMenuItem();
        this.jSeparatorEdit = new javax.swing.JSeparator();
        this.jMenuItemPreferences = new javax.swing.JMenuItem();
        this.jMenuView = new javax.swing.JMenu();
        this.jCheckBoxMenuItemConsole = new javax.swing.JCheckBoxMenuItem();
        this.jCheckBoxMenuItemTable = new javax.swing.JCheckBoxMenuItem();
        this.jCheckBoxMenuItemRegexInfo = new javax.swing.JCheckBoxMenuItem();
        this.jSeparatorView = new javax.swing.JSeparator();
        this.jCheckBoxMenuItemSecondView = new javax.swing.JCheckBoxMenuItem();
        this.jMenuExecute = new javax.swing.JMenu();
        this.jMenuItemEnterWord = new javax.swing.JMenuItem();
        this.jMenuItemEditMachine = new javax.swing.JMenuItem();
        this.jSeparatorExecute0 = new javax.swing.JSeparator();
        this.jMenuItemValidate = new javax.swing.JMenuItem();
        this.jMenuConvertTo = new javax.swing.JMenu();
        this.jMenuItemConvertToDFA = new javax.swing.JMenuItem();
        this.jMenuItemConvertToNFA = new javax.swing.JMenuItem();
        this.jMenuItemConvertToENFA = new javax.swing.JMenuItem();
        this.jMenuItemConvertToPDA = new javax.swing.JMenuItem();
        this.jMenuItemConvertToRegex = new javax.swing.JMenuItem();
        this.jMenuConvertToComplete = new javax.swing.JMenu();
        this.jMenuItemConvertToCompleteDFA = new javax.swing.JMenuItem();
        this.jMenuItemConvertToCompleteNFA = new javax.swing.JMenuItem();
        this.jMenuItemConvertToCompleteENFA = new javax.swing.JMenuItem();
        this.jMenuItemConvertToCompletePDA = new javax.swing.JMenuItem();
        this.jMenuDraft = new javax.swing.JMenu();
        this.jMenuItemDFA = new javax.swing.JMenuItem();
        this.jMenuItemNFA = new javax.swing.JMenuItem();
        this.jMenuItemENFA = new javax.swing.JMenuItem();
        this.jMenuItemPDA = new javax.swing.JMenuItem();
        this.jMenuItemRG = new javax.swing.JMenuItem();
        this.jMenuItemCFG = new javax.swing.JMenuItem();
        this.jSeparatorExecute1 = new javax.swing.JSeparator();
        this.jMenuItemAutoLayout = new javax.swing.JMenuItem();
        this.jMenuItemMinimize = new javax.swing.JMenuItem();
        this.jMenuItemToCoreSyntax = new javax.swing.JMenuItem();
        this.jMenuItemEliminateLeftRecursion = new javax.swing.JMenuItem();
        this.jMenuItemLeftfactoring = new javax.swing.JMenuItem();
        this.jMenuExtras = new javax.swing.JMenu();
        this.jMenuItemExchange = new javax.swing.JMenuItem();
        this.jSeparatorExtras = new javax.swing.JSeparator();
        this.jMenuItemHistory = new javax.swing.JMenuItem();
        this.jMenuItemReachableStates = new javax.swing.JMenuItem();
        this.jMenuItemReorderStateNames = new javax.swing.JMenuItem();
        this.jMenuHelp = new javax.swing.JMenu();
        this.jMenuItemAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        this.jToolBarMain.setBorder(null);
        this.jToolBarMain.setFloatable(false);
        this.jToolBarMain.setBorderPainted(false);

        this.jToolBarFile.setBorder(null);
        this.jToolBarFile.setFloatable(false);
        this.jToolBarFile.setBorderPainted(false);
        this.jToolBarFile.setOpaque(false);

        this.jGTIToolBarButtonNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/large/new.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages"); // NOI18N
        this.jGTIToolBarButtonNew.setToolTipText(bundle.getString("MainWindow.NewToolTip")); // NOI18N
        this.jGTIToolBarButtonNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonNewActionPerformed(evt);
            }
        });
        this.jToolBarFile.add(this.jGTIToolBarButtonNew);

        this.jGTIToolBarButtonOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/large/open.png"))); // NOI18N
        this.jGTIToolBarButtonOpen.setToolTipText(bundle.getString("MainWindow.OpenToolTip")); // NOI18N
        this.jGTIToolBarButtonOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonOpenActionPerformed(evt);
            }
        });
        this.jToolBarFile.add(this.jGTIToolBarButtonOpen);

        this.jGTIToolBarButtonSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/large/save.png"))); // NOI18N
        this.jGTIToolBarButtonSave.setToolTipText(bundle.getString("MainWindow.SaveToolTip")); // NOI18N
        this.jGTIToolBarButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonSaveActionPerformed(evt);
            }
        });
        this.jToolBarFile.add(this.jGTIToolBarButtonSave);

        this.jGTIToolBarButtonSaveAs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/large/saveAs.png"))); // NOI18N
        this.jGTIToolBarButtonSaveAs.setToolTipText(bundle.getString("MainWindow.SaveAsToolTip")); // NOI18N
        this.jGTIToolBarButtonSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonSaveAsActionPerformed(evt);
            }
        });
        this.jToolBarFile.add(this.jGTIToolBarButtonSaveAs);

        this.jToolBarMain.add(this.jToolBarFile);

        this.jSeparatorFileEdit.setOrientation(javax.swing.SwingConstants.VERTICAL);
        this.jSeparatorFileEdit.setMaximumSize(new java.awt.Dimension(5, 32));
        this.jToolBarMain.add(this.jSeparatorFileEdit);

        this.jToolBarEdit.setBorder(null);
        this.jToolBarEdit.setFloatable(false);
        this.jToolBarEdit.setBorderPainted(false);
        this.jToolBarEdit.setOpaque(false);

        this.jGTIToolBarButtonUndo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/large/undo.png"))); // NOI18N
        this.jGTIToolBarButtonUndo.setToolTipText(bundle.getString("MainWindow.UndoToolTip")); // NOI18N
        this.jGTIToolBarButtonUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonUndoActionPerformed(evt);
            }
        });
        this.jToolBarEdit.add(this.jGTIToolBarButtonUndo);

        this.jGTIToolBarButtonRedo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/large/redo.png"))); // NOI18N
        this.jGTIToolBarButtonRedo.setToolTipText(bundle.getString("MainWindow.RedoToolTip")); // NOI18N
        this.jGTIToolBarButtonRedo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonRedoActionPerformed(evt);
            }
        });
        this.jToolBarEdit.add(this.jGTIToolBarButtonRedo);

        this.jToolBarMain.add(this.jToolBarEdit);

        this.jSeparatorEditNavigation.setOrientation(javax.swing.SwingConstants.VERTICAL);
        this.jSeparatorEditNavigation.setMaximumSize(new java.awt.Dimension(5, 32));
        this.jToolBarMain.add(this.jSeparatorEditNavigation);

        this.jToolBarNavigation.setBorder(null);
        this.jToolBarNavigation.setFloatable(false);
        this.jToolBarNavigation.setBorderPainted(false);
        this.jToolBarNavigation.setOpaque(false);

        this.jGTIToolBarButtonEditDocument.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/large/document.png"))); // NOI18N
        this.jGTIToolBarButtonEditDocument.setToolTipText(bundle.getString("MachinePanel.EditDocument")); // NOI18N
        this.jGTIToolBarButtonEditDocument.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonEditDocumentActionPerformed(evt);
            }
        });
        this.jToolBarNavigation.add(this.jGTIToolBarButtonEditDocument);

        this.toolbarButton.add(this.jGTIToolBarToggleButtonMouse);
        this.jGTIToolBarToggleButtonMouse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/machine/mouse.png"))); // NOI18N
        this.jGTIToolBarToggleButtonMouse.setSelected(true);
        this.jGTIToolBarToggleButtonMouse.setToolTipText(bundle.getString("MachinePanel.Mouse")); // NOI18N
        this.jGTIToolBarToggleButtonMouse.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jGTIToolBarToggleButtonMouseItemStateChanged(evt);
            }
        });
        this.jToolBarNavigation.add(this.jGTIToolBarToggleButtonMouse);

        this.toolbarButton.add(this.jGTIToolBarToggleButtonAddState);
        this.jGTIToolBarToggleButtonAddState.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/machine/state.png"))); // NOI18N
        this.jGTIToolBarToggleButtonAddState.setToolTipText(bundle.getString("MachinePanel.AddState")); // NOI18N
        this.jGTIToolBarToggleButtonAddState.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jGTIToolBarToggleButtonAddStateItemStateChanged(evt);
            }
        });
        this.jToolBarNavigation.add(this.jGTIToolBarToggleButtonAddState);

        this.toolbarButton.add(this.jGTIToolBarToggleButtonAddTransition);
        this.jGTIToolBarToggleButtonAddTransition.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/machine/transition.png"))); // NOI18N
        this.jGTIToolBarToggleButtonAddTransition.setToolTipText(bundle.getString("MachinePanel.AddTransition")); // NOI18N
        this.jGTIToolBarToggleButtonAddTransition.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jGTIToolBarToggleButtonAddTransitionItemStateChanged(evt);
            }
        });
        this.jToolBarNavigation.add(this.jGTIToolBarToggleButtonAddTransition);

        this.toolbarButton.add(this.jGTIToolBarToggleButtonStartState);
        this.jGTIToolBarToggleButtonStartState.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/machine/start.png"))); // NOI18N
        this.jGTIToolBarToggleButtonStartState.setToolTipText(bundle.getString("MachinePanel.AddStartState")); // NOI18N
        this.jGTIToolBarToggleButtonStartState.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jGTIToolBarToggleButtonStartStateItemStateChanged(evt);
            }
        });
        this.jToolBarNavigation.add(this.jGTIToolBarToggleButtonStartState);

        this.toolbarButton.add(this.jGTIToolBarToggleButtonFinalState);
        this.jGTIToolBarToggleButtonFinalState.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/machine/final.png"))); // NOI18N
        this.jGTIToolBarToggleButtonFinalState.setToolTipText(bundle.getString("MachinePanel.AddFinalState")); // NOI18N
        this.jGTIToolBarToggleButtonFinalState.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jGTIToolBarToggleButtonFinalStateItemStateChanged(evt);
            }
        });
        this.jToolBarNavigation.add(this.jGTIToolBarToggleButtonFinalState);

        this.jGTIToolBarToggleButtonEnterWord.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/machine/word.png"))); // NOI18N
        this.jGTIToolBarToggleButtonEnterWord.setToolTipText(bundle.getString("MainWindow.EnterWord")); // NOI18N
        this.jGTIToolBarToggleButtonEnterWord.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jGTIToolBarToggleButtonEnterWordItemStateChanged(evt);
            }
        });
        this.jToolBarNavigation.add(this.jGTIToolBarToggleButtonEnterWord);

        this.jGTIToolBarButtonAddProduction.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/grammar/large/add.png"))); // NOI18N
        this.jGTIToolBarButtonAddProduction.setToolTipText(bundle.getString("GrammarPanel.AddProduction")); // NOI18N
        this.jGTIToolBarButtonAddProduction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonAddProductionActionPerformed(evt);
            }
        });
        this.jToolBarNavigation.add(this.jGTIToolBarButtonAddProduction);

        this.jSeparatorNavigation.setOrientation(javax.swing.SwingConstants.VERTICAL);
        this.jSeparatorNavigation.setMaximumSize(new java.awt.Dimension(5, 32));
        this.jToolBarNavigation.add(this.jSeparatorNavigation);

        this.jGTIToolBarButtonStart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/navigation/large/start.png"))); // NOI18N
        this.jGTIToolBarButtonStart.setToolTipText(bundle.getString("MachinePanel.WordModeStart")); // NOI18N
        this.jGTIToolBarButtonStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonStartActionPerformed(evt);
            }
        });
        this.jToolBarNavigation.add(this.jGTIToolBarButtonStart);

        this.jGTIToolBarButtonPreviousStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/navigation/large/backward.png"))); // NOI18N
        this.jGTIToolBarButtonPreviousStep.setToolTipText(bundle.getString("MachinePanel.WordModePreviousStep")); // NOI18N
        this.jGTIToolBarButtonPreviousStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonPreviousStepActionPerformed(evt);
            }
        });
        this.jToolBarNavigation.add(this.jGTIToolBarButtonPreviousStep);

        this.jGTIToolBarButtonNextStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/navigation/large/forward.png"))); // NOI18N
        this.jGTIToolBarButtonNextStep.setToolTipText(bundle.getString("MachinePanel.WordModeNextStep")); // NOI18N
        this.jGTIToolBarButtonNextStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonNextStepActionPerformed(evt);
            }
        });
        this.jToolBarNavigation.add(this.jGTIToolBarButtonNextStep);

        this.jGTIToolBarToggleButtonAutoStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/navigation/large/autostep.png"))); // NOI18N
        this.jGTIToolBarToggleButtonAutoStep.setToolTipText(bundle.getString("MachinePanel.WordModeAutoStep")); // NOI18N
        this.jGTIToolBarToggleButtonAutoStep.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jGTIToolBarToggleButtonAutoStepItemStateChanged(evt);
            }
        });
        this.jToolBarNavigation.add(this.jGTIToolBarToggleButtonAutoStep);

        this.jGTIToolBarButtonStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/navigation/large/stop.png"))); // NOI18N
        this.jGTIToolBarButtonStop.setToolTipText(bundle.getString("MachinePanel.WordModeStop")); // NOI18N
        this.jGTIToolBarButtonStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonStopActionPerformed(evt);
            }
        });
        this.jToolBarNavigation.add(this.jGTIToolBarButtonStop);

        this.jToolBarMain.add(this.jToolBarNavigation);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(this.jToolBarMain, gridBagConstraints);

        this.jGTIMainSplitPane.setDividerLocation(380);
        this.jGTIMainSplitPane.setResizeWeight(0.5);

        this.jGTIPanelLeftInner.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(50, 150, 250), 3, true));

        this.jGTIEditorPanelTabbedPaneLeft.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jGTIEditorPanelTabbedPaneLeftMouseReleased(evt);
            }
        });
        this.jGTIEditorPanelTabbedPaneLeft.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jGTIEditorPanelTabbedPaneLeftStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        this.jGTIPanelLeftInner.add(this.jGTIEditorPanelTabbedPaneLeft, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        this.jGTIPanelLeftOuter.add(this.jGTIPanelLeftInner, gridBagConstraints);

        this.jGTIMainSplitPane.setLeftComponent(this.jGTIPanelLeftOuter);

        this.jGTIPanelRightInner.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(100, 200, 250), 3, true));

        this.jGTIEditorPanelTabbedPaneRight.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jGTIEditorPanelTabbedPaneRightMouseReleased(evt);
            }
        });
        this.jGTIEditorPanelTabbedPaneRight.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jGTIEditorPanelTabbedPaneRightStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        this.jGTIPanelRightInner.add(this.jGTIEditorPanelTabbedPaneRight, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        this.jGTIPanelRightOuter.add(this.jGTIPanelRightInner, gridBagConstraints);

        this.jGTIMainSplitPane.setRightComponent(this.jGTIPanelRightOuter);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(this.jGTIMainSplitPane, gridBagConstraints);

        this.jMenuFile.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.FileMnemonic").charAt(0));
        this.jMenuFile.setText(bundle.getString("MainWindow.File")); // NOI18N

        this.jMenuItemNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        this.jMenuItemNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/small/new.png"))); // NOI18N
        this.jMenuItemNew.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.NewMnemonic").charAt(0));
        this.jMenuItemNew.setText(bundle.getString("MainWindow.New")); // NOI18N
        this.jMenuItemNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleNew(evt);
            }
        });
        this.jMenuFile.add(this.jMenuItemNew);

        this.jMenuItemOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        this.jMenuItemOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/small/open.png"))); // NOI18N
        this.jMenuItemOpen.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.OpenMnemonic").charAt(0));
        this.jMenuItemOpen.setText(bundle.getString("MainWindow.Open")); // NOI18N
        this.jMenuItemOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleOpen(evt);
            }
        });
        this.jMenuFile.add(this.jMenuItemOpen);

        this.jMenuItemClose.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        this.jMenuItemClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/small/empty.png"))); // NOI18N
        this.jMenuItemClose.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.CloseMnemonic").charAt(0));
        this.jMenuItemClose.setText(bundle.getString("MainWindow.Close")); // NOI18N
        this.jMenuItemClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleClose(evt);
            }
        });
        this.jMenuFile.add(this.jMenuItemClose);

        this.jMenuItemCloseAll.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        this.jMenuItemCloseAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/small/empty.png"))); // NOI18N
        this.jMenuItemCloseAll.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.CloseAllMnemonic").charAt(0));
        this.jMenuItemCloseAll.setText(bundle.getString("MainWindow.CloseAll")); // NOI18N
        this.jMenuItemCloseAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleCloseAll(evt);
            }
        });
        this.jMenuFile.add(this.jMenuItemCloseAll);
        this.jMenuFile.add(this.jSeparatorFile1);

        this.jMenuItemPrint.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        this.jMenuItemPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/small/print.png"))); // NOI18N
        this.jMenuItemPrint.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.PrintMnemonic").charAt(0));
        this.jMenuItemPrint.setText(bundle.getString("MainWindow.Print")); // NOI18N
        this.jMenuItemPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPrinthandleClose(evt);
            }
        });
        this.jMenuFile.add(this.jMenuItemPrint);
        this.jMenuFile.add(this.jSeparatorFile2);

        this.jMenuItemSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        this.jMenuItemSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/small/save.png"))); // NOI18N
        this.jMenuItemSave.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.SaveMnemonic").charAt(0));
        this.jMenuItemSave.setText(bundle.getString("MainWindow.Save")); // NOI18N
        this.jMenuItemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleSave(evt);
            }
        });
        this.jMenuFile.add(this.jMenuItemSave);

        this.jMenuItemSaveAs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/small/saveAs.png"))); // NOI18N
        this.jMenuItemSaveAs.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.SaveAsMnemonic").charAt(0));
        this.jMenuItemSaveAs.setText(bundle.getString("MainWindow.SaveAs")); // NOI18N
        this.jMenuItemSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleSaveAs(evt);
            }
        });
        this.jMenuFile.add(this.jMenuItemSaveAs);

        this.jMenuItemSaveAll.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        this.jMenuItemSaveAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/small/saveAll.png"))); // NOI18N
        this.jMenuItemSaveAll.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.SaveAllMnemonic").charAt(0));
        this.jMenuItemSaveAll.setText(bundle.getString("MainWindow.SaveAll")); // NOI18N
        this.jMenuItemSaveAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleSaveAll(evt);
            }
        });
        this.jMenuFile.add(this.jMenuItemSaveAll);
        this.jMenuFile.add(this.jSeparatorFile3);

        this.jMenuItemExportPicture.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.ExportPictureMnemonic").charAt(0));
        this.jMenuItemExportPicture.setText(bundle.getString("MainWindow.ExportPicture")); // NOI18N
        this.jMenuItemExportPicture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExportPictureActionPerformed(evt);
            }
        });
        this.jMenuFile.add(this.jMenuItemExportPicture);

        this.jMenuItemExportLatex.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        this.jMenuItemExportLatex.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.LatexExportMnemonic").charAt(0));
        this.jMenuItemExportLatex.setText(bundle.getString("MainWindow.LatexExport")); // NOI18N
        this.jMenuItemExportLatex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExportLatexActionPerformed(evt);
            }
        });
        this.jMenuFile.add(this.jMenuItemExportLatex);
        this.jMenuFile.add(this.jSeparatorFile4);

        this.jMenuRecentlyUsed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/small/empty.png"))); // NOI18N
        this.jMenuRecentlyUsed.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.RecentlyUsedMnemonic").charAt(0));
        this.jMenuRecentlyUsed.setText(bundle.getString("MainWindow.RecentlyUsed")); // NOI18N
        this.jMenuFile.add(this.jMenuRecentlyUsed);
        this.jMenuFile.add(this.jSeparatorFile5);

        this.jMenuItemQuit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        this.jMenuItemQuit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/small/empty.png"))); // NOI18N
        this.jMenuItemQuit.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.QuitMnemonic").charAt(0));
        this.jMenuItemQuit.setText(bundle.getString("MainWindow.Quit")); // NOI18N
        this.jMenuItemQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleQuit(evt);
            }
        });
        this.jMenuFile.add(this.jMenuItemQuit);

        this.jMenuBarMain.add(this.jMenuFile);

        this.jMenuEdit.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.EditMnemonic").charAt(0));
        this.jMenuEdit.setText(bundle.getString("MainWindow.Edit")); // NOI18N

        this.jMenuItemUndo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        this.jMenuItemUndo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/small/undo.png"))); // NOI18N
        this.jMenuItemUndo.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.UndoMnemonic").charAt(0));
        this.jMenuItemUndo.setText(bundle.getString("MainWindow.Undo")); // NOI18N
        this.jMenuItemUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleUndo(evt);
            }
        });
        this.jMenuEdit.add(this.jMenuItemUndo);

        this.jMenuItemRedo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        this.jMenuItemRedo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/small/redo.png"))); // NOI18N
        this.jMenuItemRedo.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.RedoMnemonic").charAt(0));
        this.jMenuItemRedo.setText(bundle.getString("MainWindow.Redo")); // NOI18N
        this.jMenuItemRedo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleRedo(evt);
            }
        });
        this.jMenuEdit.add(this.jMenuItemRedo);
        this.jMenuEdit.add(this.jSeparatorEdit);

        this.jMenuItemPreferences.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/small/preferences.png"))); // NOI18N
        this.jMenuItemPreferences.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.PreferencesMnemonic").charAt(0));
        this.jMenuItemPreferences.setText(bundle.getString("MainWindow.Preferences")); // NOI18N
        this.jMenuItemPreferences.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPreferencesActionPerformed(evt);
            }
        });
        this.jMenuEdit.add(this.jMenuItemPreferences);

        this.jMenuBarMain.add(this.jMenuEdit);

        this.jMenuView.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.ViewMnemonic").charAt(0));
        this.jMenuView.setText(bundle.getString("MainWindow.View")); // NOI18N

        this.jCheckBoxMenuItemConsole.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.ConsoleMnemonic").charAt(0));
        this.jCheckBoxMenuItemConsole.setSelected(true);
        this.jCheckBoxMenuItemConsole.setText(bundle.getString("MainWindow.Console")); // NOI18N
        this.jCheckBoxMenuItemConsole.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxMenuItemConsoleItemStateChanged(evt);
            }
        });
        this.jMenuView.add(this.jCheckBoxMenuItemConsole);

        this.jCheckBoxMenuItemTable.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.TableMnemonic").charAt(0));
        this.jCheckBoxMenuItemTable.setSelected(true);
        this.jCheckBoxMenuItemTable.setText(bundle.getString("MainWindow.Table")); // NOI18N
        this.jCheckBoxMenuItemTable.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxMenuItemTableItemStateChanged(evt);
            }
        });
        this.jMenuView.add(this.jCheckBoxMenuItemTable);

        this.jCheckBoxMenuItemRegexInfo.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.RegexInfoMnemonic").charAt(0));
        this.jCheckBoxMenuItemRegexInfo.setSelected(true);
        this.jCheckBoxMenuItemRegexInfo.setText(bundle.getString("MainWindow.RegexInfo")); // NOI18N
        this.jCheckBoxMenuItemRegexInfo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxMenuItemRegexInfoItemStateChanged(evt);
            }
        });
        this.jMenuView.add(this.jCheckBoxMenuItemRegexInfo);
        this.jMenuView.add(this.jSeparatorView);

        this.jCheckBoxMenuItemSecondView.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.SecondViewMnemonic").charAt(0));
        this.jCheckBoxMenuItemSecondView.setText(bundle.getString("MainWindow.SecondView")); // NOI18N
        this.jCheckBoxMenuItemSecondView.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxMenuItemSecondViewItemStateChanged(evt);
            }
        });
        this.jMenuView.add(this.jCheckBoxMenuItemSecondView);

        this.jMenuBarMain.add(this.jMenuView);

        this.jMenuExecute.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.ExecuteMnemonic").charAt(0));
        this.jMenuExecute.setText(bundle.getString("MainWindow.Execute")); // NOI18N

        this.jMenuItemEnterWord.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.EnterWordMnemonic").charAt(0));
        this.jMenuItemEnterWord.setText(bundle.getString("MainWindow.EnterWord")); // NOI18N
        this.jMenuItemEnterWord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEnterWordActionPerformed(evt);
            }
        });
        this.jMenuExecute.add(this.jMenuItemEnterWord);

        this.jMenuItemEditMachine.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.EditMachineMnemonic").charAt(0));
        this.jMenuItemEditMachine.setText(bundle.getString("MainWindow.EditMachine")); // NOI18N
        this.jMenuItemEditMachine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEditMachineActionPerformed(evt);
            }
        });
        this.jMenuExecute.add(this.jMenuItemEditMachine);
        this.jMenuExecute.add(this.jSeparatorExecute0);

        this.jMenuItemValidate.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.ValidateMnemonic").charAt(0));
        this.jMenuItemValidate.setText(bundle.getString("MainWindow.Validate")); // NOI18N
        this.jMenuItemValidate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemValidateActionPerformed(evt);
            }
        });
        this.jMenuExecute.add(this.jMenuItemValidate);

        this.jMenuConvertTo.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.ConvertToMnemonic").charAt(0));
        this.jMenuConvertTo.setText(bundle.getString("MainWindow.ConvertTo")); // NOI18N

        this.jMenuItemConvertToDFA.setText(bundle.getString("MainWindow.DFA")); // NOI18N
        this.jMenuItemConvertToDFA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemConvertToDFAActionPerformed(evt);
            }
        });
        this.jMenuConvertTo.add(this.jMenuItemConvertToDFA);

        this.jMenuItemConvertToNFA.setText(bundle.getString("MainWindow.NFA")); // NOI18N
        this.jMenuItemConvertToNFA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemConvertToNFActionPerformed(evt);
            }
        });
        this.jMenuConvertTo.add(this.jMenuItemConvertToNFA);

        this.jMenuItemConvertToENFA.setText(bundle.getString("MainWindow.ENFA")); // NOI18N
        this.jMenuItemConvertToENFA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemConvertToENFAActionPerformed(evt);
            }
        });
        this.jMenuConvertTo.add(this.jMenuItemConvertToENFA);

        this.jMenuItemConvertToPDA.setText(bundle.getString("MainWindow.PDA")); // NOI18N
        this.jMenuItemConvertToPDA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemConvertToPDAActionPerformed(evt);
            }
        });
        this.jMenuConvertTo.add(this.jMenuItemConvertToPDA);

        this.jMenuItemConvertToRegex.setText("Regex");
        this.jMenuItemConvertToRegex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemConvertToRegexActionPerformed(evt);
            }
        });
        this.jMenuConvertTo.add(this.jMenuItemConvertToRegex);

        this.jMenuExecute.add(this.jMenuConvertTo);

        this.jMenuConvertToComplete.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.ConvertToCompleteMnemonic").charAt(0));
        this.jMenuConvertToComplete.setText(bundle.getString("MainWindow.ConvertToComplete")); // NOI18N

        this.jMenuItemConvertToCompleteDFA.setText(bundle.getString("MainWindow.DFA")); // NOI18N
        this.jMenuItemConvertToCompleteDFA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemConvertToCompleteDFAActionPerformed(evt);
            }
        });
        this.jMenuConvertToComplete.add(this.jMenuItemConvertToCompleteDFA);

        this.jMenuItemConvertToCompleteNFA.setText(bundle.getString("MainWindow.NFA")); // NOI18N
        this.jMenuItemConvertToCompleteNFA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemConvertToCompleteNFActionPerformed(evt);
            }
        });
        this.jMenuConvertToComplete.add(this.jMenuItemConvertToCompleteNFA);

        this.jMenuItemConvertToCompleteENFA.setText(bundle.getString("MainWindow.ENFA")); // NOI18N
        this.jMenuItemConvertToCompleteENFA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemConvertToCompleteENFAActionPerformed(evt);
            }
        });
        this.jMenuConvertToComplete.add(this.jMenuItemConvertToCompleteENFA);

        this.jMenuItemConvertToCompletePDA.setText(bundle.getString("MainWindow.PDA")); // NOI18N
        this.jMenuItemConvertToCompletePDA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemConvertToCompletePDAActionPerformed(evt);
            }
        });
        this.jMenuConvertToComplete.add(this.jMenuItemConvertToCompletePDA);

        this.jMenuExecute.add(this.jMenuConvertToComplete);

        this.jMenuDraft.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.DraftForMnemonic").charAt(0));
        this.jMenuDraft.setText(bundle.getString("MainWindow.DraftFor")); // NOI18N

        this.jMenuItemDFA.setText(bundle.getString("MainWindow.DFA")); // NOI18N
        this.jMenuItemDFA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDFAhandleDraftFor(evt);
            }
        });
        this.jMenuDraft.add(this.jMenuItemDFA);

        this.jMenuItemNFA.setText(bundle.getString("MainWindow.NFA")); // NOI18N
        this.jMenuItemNFA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNFAhandleDraftFor(evt);
            }
        });
        this.jMenuDraft.add(this.jMenuItemNFA);

        this.jMenuItemENFA.setText(bundle.getString("MainWindow.ENFA")); // NOI18N
        this.jMenuItemENFA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemENFAhandleDraftFor(evt);
            }
        });
        this.jMenuDraft.add(this.jMenuItemENFA);

        this.jMenuItemPDA.setText(bundle.getString("MainWindow.PDA")); // NOI18N
        this.jMenuItemPDA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPDAhandleDraftFor(evt);
            }
        });
        this.jMenuDraft.add(this.jMenuItemPDA);

        this.jMenuItemRG.setText(bundle.getString("MainWindow.RG")); // NOI18N
        this.jMenuItemRG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRGhandleDraftFor(evt);
            }
        });
        this.jMenuDraft.add(this.jMenuItemRG);

        this.jMenuItemCFG.setText(bundle.getString("MainWindow.CFG")); // NOI18N
        this.jMenuItemCFG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCFGhandleDraftFor(evt);
            }
        });
        this.jMenuDraft.add(this.jMenuItemCFG);

        this.jMenuExecute.add(this.jMenuDraft);
        this.jMenuExecute.add(this.jSeparatorExecute1);

        this.jMenuItemAutoLayout.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.AutoLayoutMnemonic").charAt(0));
        this.jMenuItemAutoLayout.setText(bundle.getString("MainWindow.AutoLayout")); // NOI18N
        this.jMenuItemAutoLayout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAutoLayoutActionPerformed(evt);
            }
        });
        this.jMenuExecute.add(this.jMenuItemAutoLayout);

        this.jMenuItemMinimize.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.MinimizeMnemonic").charAt(0));
        this.jMenuItemMinimize.setText(bundle.getString("MainWindow.Minimize")); // NOI18N
        this.jMenuItemMinimize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemMinimizeActionPerformed(evt);
            }
        });
        this.jMenuExecute.add(this.jMenuItemMinimize);

        this.jMenuItemToCoreSyntax.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.ToCoreSyntaxMnemonic").charAt(0));
        this.jMenuItemToCoreSyntax.setText(bundle.getString("MainWindow.ToCoreSyntax")); // NOI18N
        this.jMenuItemToCoreSyntax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemToCoreSyntaxActionPerformed(evt);
            }
        });
        this.jMenuExecute.add(this.jMenuItemToCoreSyntax);

        this.jMenuItemEliminateLeftRecursion.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.EliminateLeftRecursionMnemonic").charAt(0));
        this.jMenuItemEliminateLeftRecursion.setText(bundle.getString("MainWindow.EliminateLeftRecursion")); // NOI18N
        this.jMenuItemEliminateLeftRecursion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEliminateLeftRecursionActionPerformed(evt);
            }
        });
        this.jMenuExecute.add(this.jMenuItemEliminateLeftRecursion);

        this.jMenuItemLeftfactoring.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.LeftFactoringMnemonic").charAt(0));
        this.jMenuItemLeftfactoring.setText(bundle.getString("MainWindow.LeftFactoring")); // NOI18N
        this.jMenuItemLeftfactoring.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemLeftfactoringActionPerformed(evt);
            }
        });
        this.jMenuExecute.add(this.jMenuItemLeftfactoring);

        this.jMenuBarMain.add(this.jMenuExecute);

        this.jMenuExtras.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.ExtrasMnemonic").charAt(0));
        this.jMenuExtras.setText(bundle.getString("MainWindow.Extras")); // NOI18N

        this.jMenuItemExchange.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.Exchange").charAt(0));
        this.jMenuItemExchange.setText(bundle.getString("MainWindow.Exchange")); // NOI18N
        this.jMenuItemExchange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExchangeActionPerformed(evt);
            }
        });
        this.jMenuExtras.add(this.jMenuItemExchange);
        this.jMenuExtras.add(this.jSeparatorExtras);

        this.jMenuItemHistory.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.HistoryMnemonic").charAt(0));
        this.jMenuItemHistory.setText(bundle.getString("MainWindow.History")); // NOI18N
        this.jMenuItemHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemHistoryActionPerformed(evt);
            }
        });
        this.jMenuExtras.add(this.jMenuItemHistory);

        this.jMenuItemReachableStates.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.ReachableStatesMnemonic").charAt(0));
        this.jMenuItemReachableStates.setText(bundle.getString("MainWindow.ReachableStates")); // NOI18N
        this.jMenuItemReachableStates.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemReachableStatesActionPerformed(evt);
            }
        });
        this.jMenuExtras.add(this.jMenuItemReachableStates);

        this.jMenuItemReorderStateNames.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.ReorderStateNamesMnemonic").charAt(0));
        this.jMenuItemReorderStateNames.setText(bundle.getString("MainWindow.ReorderStateNames")); // NOI18N
        this.jMenuItemReorderStateNames.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemReorderStateNamesActionPerformed(evt);
            }
        });
        this.jMenuExtras.add(this.jMenuItemReorderStateNames);

        this.jMenuBarMain.add(this.jMenuExtras);

        this.jMenuHelp.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.HelpMnemonic").charAt(0));
        this.jMenuHelp.setText(bundle.getString("MainWindow.Help")); // NOI18N

        this.jMenuItemAbout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        this.jMenuItemAbout.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("MainWindow.AboutMnemonic").charAt(0));
        this.jMenuItemAbout.setText(bundle.getString("MainWindow.About")); // NOI18N
        this.jMenuItemAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAboutActionPerformed(evt);
            }
        });
        this.jMenuHelp.add(this.jMenuItemAbout);

        this.jMenuBarMain.add(this.jMenuHelp);

        setJMenuBar(this.jMenuBarMain);

        setBounds(0, 0, 762, 462);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemReorderStateNamesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemReorderStateNamesActionPerformed
        this.logic.handleReorderStateNames();
    }//GEN-LAST:event_jMenuItemReorderStateNamesActionPerformed

    private void jMenuItemConvertToCompletePDAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemConvertToCompletePDAActionPerformed
         this.logic.handleConvertToComplete(MachineType.PDA);
    }//GEN-LAST:event_jMenuItemConvertToCompletePDAActionPerformed

    private void jMenuItemConvertToCompleteENFAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemConvertToCompleteENFAActionPerformed
         this.logic.handleConvertToComplete(MachineType.ENFA);
    }//GEN-LAST:event_jMenuItemConvertToCompleteENFAActionPerformed

    private void jMenuItemConvertToCompleteNFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemConvertToCompleteNFActionPerformed
         this.logic.handleConvertToComplete(MachineType.NFA);
    }//GEN-LAST:event_jMenuItemConvertToCompleteNFActionPerformed

    private void jMenuItemConvertToCompleteDFAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemConvertToCompleteDFAActionPerformed
         this.logic.handleConvertToComplete(MachineType.DFA);
    }//GEN-LAST:event_jMenuItemConvertToCompleteDFAActionPerformed

    private void jGTIToolBarToggleButtonEnterWordItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jGTIToolBarToggleButtonEnterWordItemStateChanged
        this.logic.handleEnterWordToggleButton();
    }//GEN-LAST:event_jGTIToolBarToggleButtonEnterWordItemStateChanged

    private void jMenuItemReachableStatesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemReachableStatesActionPerformed
        this.logic.handleReachableStates();
    }//GEN-LAST:event_jMenuItemReachableStatesActionPerformed

    private void jGTIEditorPanelTabbedPaneRightStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jGTIEditorPanelTabbedPaneRightStateChanged
        this.logic.handleTabbedPaneStateChanged(evt);
    }//GEN-LAST:event_jGTIEditorPanelTabbedPaneRightStateChanged

    private void jGTIEditorPanelTabbedPaneRightMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jGTIEditorPanelTabbedPaneRightMouseReleased
        this.logic.handleTabbedPaneMouseReleased(evt);
    }//GEN-LAST:event_jGTIEditorPanelTabbedPaneRightMouseReleased

    private void jCheckBoxMenuItemSecondViewItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItemSecondViewItemStateChanged
        this.logic.handleSecondViewStateChanged();
    }//GEN-LAST:event_jCheckBoxMenuItemSecondViewItemStateChanged

    private void jMenuItemMinimizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemMinimizeActionPerformed
        this.logic.handleMinimize();
    }//GEN-LAST:event_jMenuItemMinimizeActionPerformed

    private void jMenuItemAutoLayoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAutoLayoutActionPerformed
        this.logic.doAutoLayout();
    }//GEN-LAST:event_jMenuItemAutoLayoutActionPerformed

    private void jMenuItemConvertToPDAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemConvertToPDAActionPerformed
        this.logic.handleConvertTo(MachineType.PDA);
    }//GEN-LAST:event_jMenuItemConvertToPDAActionPerformed

    private void jMenuItemConvertToENFAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemConvertToENFAActionPerformed
        this.logic.handleConvertTo(MachineType.ENFA);
    }//GEN-LAST:event_jMenuItemConvertToENFAActionPerformed

    private void jMenuItemConvertToNFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemConvertToNFActionPerformed
        this.logic.handleConvertTo(MachineType.NFA);
    }//GEN-LAST:event_jMenuItemConvertToNFActionPerformed

    private void jMenuItemConvertToDFAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemConvertToDFAActionPerformed
         this.logic.handleConvertTo(MachineType.DFA);
    }//GEN-LAST:event_jMenuItemConvertToDFAActionPerformed

    private void jGTIEditorPanelTabbedPaneLeftMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jGTIEditorPanelTabbedPaneLeftMouseReleased
        this.logic.handleTabbedPaneMouseReleased(evt);
    }//GEN-LAST:event_jGTIEditorPanelTabbedPaneLeftMouseReleased

    private void jMenuItemHistoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemHistoryActionPerformed
        this.logic.handleHistory();
    }//GEN-LAST:event_jMenuItemHistoryActionPerformed

    private void jMenuItemCFGhandleDraftFor(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCFGhandleDraftFor
        this.logic.handleDraftFor(GrammarType.CFG);
    }//GEN-LAST:event_jMenuItemCFGhandleDraftFor

    private void jMenuItemRGhandleDraftFor(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRGhandleDraftFor
        this.logic.handleDraftFor(GrammarType.RG);
    }//GEN-LAST:event_jMenuItemRGhandleDraftFor

    private void jGTIToolBarButtonAddProductionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIToolBarButtonAddProductionActionPerformed
        this.logic.handleAddProduction();
    }//GEN-LAST:event_jGTIToolBarButtonAddProductionActionPerformed

    private void jGTIEditorPanelTabbedPaneLeftStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jGTIEditorPanelTabbedPaneLeftStateChanged
        this.logic.handleTabbedPaneStateChanged(evt);
    }//GEN-LAST:event_jGTIEditorPanelTabbedPaneLeftStateChanged

    private void jGTIToolBarToggleButtonFinalStateItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jGTIToolBarToggleButtonFinalStateItemStateChanged
        this.logic.handleToolbarEnd(this.jGTIToolBarToggleButtonFinalState.isSelected());
    }//GEN-LAST:event_jGTIToolBarToggleButtonFinalStateItemStateChanged

    private void jGTIToolBarToggleButtonStartStateItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jGTIToolBarToggleButtonStartStateItemStateChanged
        this.logic.handleToolbarStart(this.jGTIToolBarToggleButtonStartState.isSelected());
    }//GEN-LAST:event_jGTIToolBarToggleButtonStartStateItemStateChanged

    private void jGTIToolBarToggleButtonAddTransitionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jGTIToolBarToggleButtonAddTransitionItemStateChanged
        this.logic.handleToolbarTransition(this.jGTIToolBarToggleButtonAddTransition.isSelected());
    }//GEN-LAST:event_jGTIToolBarToggleButtonAddTransitionItemStateChanged

    private void jGTIToolBarToggleButtonAutoStepItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jGTIToolBarToggleButtonAutoStepItemStateChanged
        this.logic.handleWordAutoStep(evt);
    }//GEN-LAST:event_jGTIToolBarToggleButtonAutoStepItemStateChanged

    private void jGTIToolBarToggleButtonAddStateItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jGTIToolBarToggleButtonAddStateItemStateChanged
        this.logic.handleToolbarAddState(this.jGTIToolBarToggleButtonAddState.isSelected());
    }//GEN-LAST:event_jGTIToolBarToggleButtonAddStateItemStateChanged

    private void jGTIToolBarToggleButtonMouseItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jGTIToolBarToggleButtonMouseItemStateChanged
        this.logic.handleToolbarMouse(this.jGTIToolBarToggleButtonMouse.isSelected());
    }//GEN-LAST:event_jGTIToolBarToggleButtonMouseItemStateChanged

    private void jGTIToolBarButtonStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIToolBarButtonStopActionPerformed
        this.logic.handleWordStop();
    }//GEN-LAST:event_jGTIToolBarButtonStopActionPerformed

    private void jGTIToolBarButtonNextStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIToolBarButtonNextStepActionPerformed
        this.logic.handleWordNextStep();
    }//GEN-LAST:event_jGTIToolBarButtonNextStepActionPerformed

    private void jGTIToolBarButtonPreviousStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIToolBarButtonPreviousStepActionPerformed
        this.logic.handleWordPreviousStep();
}//GEN-LAST:event_jGTIToolBarButtonPreviousStepActionPerformed

    private void jGTIToolBarButtonStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIToolBarButtonStartActionPerformed
        this.logic.handleWordStart();
    }//GEN-LAST:event_jGTIToolBarButtonStartActionPerformed

    private void jGTIToolBarButtonEditDocumentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIToolBarButtonEditDocumentActionPerformed
        this.logic.handleEditDocument();
    }//GEN-LAST:event_jGTIToolBarButtonEditDocumentActionPerformed

    private void jGTIToolBarButtonRedoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIToolBarButtonRedoActionPerformed
        this.logic.handleRedo();
    }//GEN-LAST:event_jGTIToolBarButtonRedoActionPerformed

    private void jGTIToolBarButtonUndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIToolBarButtonUndoActionPerformed
        this.logic.handleUndo();
    }//GEN-LAST:event_jGTIToolBarButtonUndoActionPerformed

    private void jGTIToolBarButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIToolBarButtonSaveActionPerformed
        this.logic.handleSave();
    }//GEN-LAST:event_jGTIToolBarButtonSaveActionPerformed

    private void jGTIToolBarButtonSaveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIToolBarButtonSaveAsActionPerformed
        this.logic.handleSaveAs();
    }//GEN-LAST:event_jGTIToolBarButtonSaveAsActionPerformed

    private void jGTIToolBarButtonOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIToolBarButtonOpenActionPerformed
        this.logic.handleOpen();
    }//GEN-LAST:event_jGTIToolBarButtonOpenActionPerformed

    private void jGTIToolBarButtonNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIToolBarButtonNewActionPerformed
        this.logic.handleNew();
    }//GEN-LAST:event_jGTIToolBarButtonNewActionPerformed

    private void jMenuItemExchangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExchangeActionPerformed
        this.logic.handleExchange();
    }//GEN-LAST:event_jMenuItemExchangeActionPerformed

    private void jMenuItemDFAhandleDraftFor(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemDFAhandleDraftFor
        this.logic.handleDraftFor(MachineType.DFA);
    }//GEN-LAST:event_jMenuItemDFAhandleDraftFor

    private void jMenuItemNFAhandleDraftFor(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNFAhandleDraftFor
        this.logic.handleDraftFor(MachineType.NFA);
    }//GEN-LAST:event_jMenuItemNFAhandleDraftFor

    private void jMenuItemPDAhandleDraftFor(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPDAhandleDraftFor
        this.logic.handleDraftFor(MachineType.PDA);
    }//GEN-LAST:event_jMenuItemPDAhandleDraftFor

    private void jMenuItemENFAhandleDraftFor(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemENFAhandleDraftFor
        this.logic.handleDraftFor(MachineType.ENFA);
    }//GEN-LAST:event_jMenuItemENFAhandleDraftFor

    private void handleCloseAll(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handleCloseAll
        this.logic.handleCloseAll();
    }//GEN-LAST:event_handleCloseAll

    private void handleSaveAll(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handleSaveAll
        this.logic.handleSaveAll();
    }//GEN-LAST:event_handleSaveAll

    private void handleRedo(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handleRedo
        this.logic.handleRedo();
    }//GEN-LAST:event_handleRedo

    private void handleUndo(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handleUndo
        this.logic.handleUndo();
    }//GEN-LAST:event_handleUndo

    private void jMenuItemEditMachineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEditMachineActionPerformed
        this.logic.handleEditMachine();
    }//GEN-LAST:event_jMenuItemEditMachineActionPerformed

    private void handleClose(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handleClose
        this.logic.handleClose();
    }//GEN-LAST:event_handleClose

    private void jCheckBoxMenuItemTableItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItemTableItemStateChanged
        this.logic.handleTableStateChanged();
    }//GEN-LAST:event_jCheckBoxMenuItemTableItemStateChanged

    private void jCheckBoxMenuItemConsoleItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItemConsoleItemStateChanged
        this.logic.handleConsoleStateChanged();
    }//GEN-LAST:event_jCheckBoxMenuItemConsoleItemStateChanged

    private void handleSaveAs(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handleSaveAs
        this.logic.handleSaveAs();
    }//GEN-LAST:event_handleSaveAs

    private void handleSave(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handleSave
        this.logic.handleSave();
    }//GEN-LAST:event_handleSave

    private void jMenuItemValidateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemValidateActionPerformed
        this.logic.handleValidate();
    }//GEN-LAST:event_jMenuItemValidateActionPerformed

    private void jMenuItemEnterWordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEnterWordActionPerformed
        this.logic.handleEnterWord();
    }//GEN-LAST:event_jMenuItemEnterWordActionPerformed

    private void handleNew(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handleNew
        this.logic.handleNew();
    }//GEN-LAST:event_handleNew

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.logic.handleQuit();
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItemAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAboutActionPerformed
        this.logic.handleAbout();
    }//GEN-LAST:event_jMenuItemAboutActionPerformed

    private void jMenuItemPreferencesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPreferencesActionPerformed
        this.logic.handlePreferences();
    }//GEN-LAST:event_jMenuItemPreferencesActionPerformed

    private void handleQuit(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handleQuit
        this.logic.handleQuit();
    }//GEN-LAST:event_handleQuit

    private void handleOpen(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handleOpen
        this.logic.handleOpen();
    }//GEN-LAST:event_handleOpen

private void jMenuItemPrinthandleClose(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPrinthandleClose
  this.logic.handlePrint();
}//GEN-LAST:event_jMenuItemPrinthandleClose

private void jMenuItemExportPictureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExportPictureActionPerformed
  this.logic.handleExportPicture();
}//GEN-LAST:event_jMenuItemExportPictureActionPerformed

private void jMenuItemExportLatexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExportLatexActionPerformed
  this.logic.handleToLatex();
}//GEN-LAST:event_jMenuItemExportLatexActionPerformed

private void jMenuItemToCoreSyntaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemToCoreSyntaxActionPerformed
    this.logic.handleToCoreSyntax();
}//GEN-LAST:event_jMenuItemToCoreSyntaxActionPerformed

private void jCheckBoxMenuItemRegexInfoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItemRegexInfoItemStateChanged
    this.logic.handleRegexInfoChanged(this.jCheckBoxMenuItemRegexInfo.isSelected());
}//GEN-LAST:event_jCheckBoxMenuItemRegexInfoItemStateChanged

private void jMenuItemEliminateLeftRecursionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEliminateLeftRecursionActionPerformed
    this.logic.handleEliminateLeftRecursion();
}//GEN-LAST:event_jMenuItemEliminateLeftRecursionActionPerformed

private void jMenuItemLeftfactoringActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemLeftfactoringActionPerformed
    this.logic.handleLeftFactoring();
}//GEN-LAST:event_jMenuItemLeftfactoringActionPerformed

private void jMenuItemConvertToRegexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemConvertToRegexActionPerformed
    this.logic.handleConvertTo ( RegexType.REGEX );
}//GEN-LAST:event_jMenuItemConvertToRegexActionPerformed
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemConsole;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemRegexInfo;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemSecondView;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemTable;
    private de.unisiegen.gtitool.ui.swing.specialized.JGTIEditorPanelTabbedPane jGTIEditorPanelTabbedPaneLeft;
    private de.unisiegen.gtitool.ui.swing.specialized.JGTIEditorPanelTabbedPane jGTIEditorPanelTabbedPaneRight;
    private de.unisiegen.gtitool.ui.swing.specialized.JGTIMainSplitPane jGTIMainSplitPane;
    private de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelLeftInner;
    private de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelLeftOuter;
    private de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelRightInner;
    private de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelRightOuter;
    private de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton jGTIToolBarButtonAddProduction;
    private de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton jGTIToolBarButtonEditDocument;
    private de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton jGTIToolBarButtonNew;
    private de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton jGTIToolBarButtonNextStep;
    private de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton jGTIToolBarButtonOpen;
    private de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton jGTIToolBarButtonPreviousStep;
    private de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton jGTIToolBarButtonRedo;
    private de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton jGTIToolBarButtonSave;
    private de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton jGTIToolBarButtonSaveAs;
    private de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton jGTIToolBarButtonStart;
    private de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton jGTIToolBarButtonStop;
    private de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton jGTIToolBarButtonUndo;
    private de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarToggleButton jGTIToolBarToggleButtonAddState;
    private de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarToggleButton jGTIToolBarToggleButtonAddTransition;
    private de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarToggleButton jGTIToolBarToggleButtonAutoStep;
    private de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarToggleButton jGTIToolBarToggleButtonEnterWord;
    private de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarToggleButton jGTIToolBarToggleButtonFinalState;
    private de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarToggleButton jGTIToolBarToggleButtonMouse;
    private de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarToggleButton jGTIToolBarToggleButtonStartState;
    private javax.swing.JMenuBar jMenuBarMain;
    private javax.swing.JMenu jMenuConvertTo;
    private javax.swing.JMenu jMenuConvertToComplete;
    private javax.swing.JMenu jMenuDraft;
    private javax.swing.JMenu jMenuEdit;
    private javax.swing.JMenu jMenuExecute;
    private javax.swing.JMenu jMenuExtras;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenu jMenuHelp;
    private javax.swing.JMenuItem jMenuItemAbout;
    private javax.swing.JMenuItem jMenuItemAutoLayout;
    private javax.swing.JMenuItem jMenuItemCFG;
    private javax.swing.JMenuItem jMenuItemClose;
    private javax.swing.JMenuItem jMenuItemCloseAll;
    private javax.swing.JMenuItem jMenuItemConvertToCompleteDFA;
    private javax.swing.JMenuItem jMenuItemConvertToCompleteENFA;
    private javax.swing.JMenuItem jMenuItemConvertToCompleteNFA;
    private javax.swing.JMenuItem jMenuItemConvertToCompletePDA;
    private javax.swing.JMenuItem jMenuItemConvertToDFA;
    private javax.swing.JMenuItem jMenuItemConvertToENFA;
    private javax.swing.JMenuItem jMenuItemConvertToNFA;
    private javax.swing.JMenuItem jMenuItemConvertToPDA;
    private javax.swing.JMenuItem jMenuItemConvertToRegex;
    private javax.swing.JMenuItem jMenuItemDFA;
    private javax.swing.JMenuItem jMenuItemENFA;
    private javax.swing.JMenuItem jMenuItemEditMachine;
    private javax.swing.JMenuItem jMenuItemEliminateLeftRecursion;
    private javax.swing.JMenuItem jMenuItemEnterWord;
    private javax.swing.JMenuItem jMenuItemExchange;
    private javax.swing.JMenuItem jMenuItemExportLatex;
    private javax.swing.JMenuItem jMenuItemExportPicture;
    private javax.swing.JMenuItem jMenuItemHistory;
    private javax.swing.JMenuItem jMenuItemLeftfactoring;
    private javax.swing.JMenuItem jMenuItemMinimize;
    private javax.swing.JMenuItem jMenuItemNFA;
    private javax.swing.JMenuItem jMenuItemNew;
    private javax.swing.JMenuItem jMenuItemOpen;
    private javax.swing.JMenuItem jMenuItemPDA;
    private javax.swing.JMenuItem jMenuItemPreferences;
    private javax.swing.JMenuItem jMenuItemPrint;
    private javax.swing.JMenuItem jMenuItemQuit;
    private javax.swing.JMenuItem jMenuItemRG;
    private javax.swing.JMenuItem jMenuItemReachableStates;
    private javax.swing.JMenuItem jMenuItemRedo;
    private javax.swing.JMenuItem jMenuItemReorderStateNames;
    private javax.swing.JMenuItem jMenuItemSave;
    private javax.swing.JMenuItem jMenuItemSaveAll;
    private javax.swing.JMenuItem jMenuItemSaveAs;
    private javax.swing.JMenuItem jMenuItemToCoreSyntax;
    private javax.swing.JMenuItem jMenuItemUndo;
    private javax.swing.JMenuItem jMenuItemValidate;
    private javax.swing.JMenu jMenuRecentlyUsed;
    private javax.swing.JMenu jMenuView;
    private javax.swing.JSeparator jSeparatorEdit;
    private javax.swing.JSeparator jSeparatorEditNavigation;
    private javax.swing.JSeparator jSeparatorExecute0;
    private javax.swing.JSeparator jSeparatorExecute1;
    private javax.swing.JSeparator jSeparatorExtras;
    private javax.swing.JSeparator jSeparatorFile1;
    private javax.swing.JSeparator jSeparatorFile2;
    private javax.swing.JSeparator jSeparatorFile3;
    private javax.swing.JSeparator jSeparatorFile4;
    private javax.swing.JSeparator jSeparatorFile5;
    private javax.swing.JSeparator jSeparatorFileEdit;
    private javax.swing.JSeparator jSeparatorNavigation;
    private javax.swing.JSeparator jSeparatorView;
    private javax.swing.JToolBar jToolBarEdit;
    private javax.swing.JToolBar jToolBarFile;
    private javax.swing.JToolBar jToolBarMain;
    private javax.swing.JToolBar jToolBarNavigation;
    private javax.swing.ButtonGroup modeSettingsGroup;
    private javax.swing.ButtonGroup toolbarButton;
    // End of variables declaration//GEN-END:variables

}
