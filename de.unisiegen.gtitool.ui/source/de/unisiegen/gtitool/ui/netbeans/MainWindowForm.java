package de.unisiegen.gtitool.ui.netbeans;



import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JToolBar;

import de.unisiegen.gtitool.ui.logic.MainWindow;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel;
import de.unisiegen.gtitool.ui.model.DefaultGrammarModel.GrammarType;
import de.unisiegen.gtitool.ui.model.DefaultMachineModel.MachineType;
import de.unisiegen.gtitool.ui.netbeans.interfaces.GUIClass;
import de.unisiegen.gtitool.ui.swing.JGTIToolBarButton;
import de.unisiegen.gtitool.ui.swing.JGTIToolBarToggleButton;
import de.unisiegen.gtitool.ui.swing.specialized.EditorPanelTabbedPane;

/**
 * The {@link MainWindowForm}.
 *
 * @author Benjamin Mies
 * @author Christian Fehler
 * @version $Id$
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
     * Returns the editorPanelTabbedPane.
     *
     * @return The editorPanelTabbedPane.
     * @see #editorPanelTabbedPane
     */
    public final EditorPanelTabbedPane getEditorPanelTabbedPane ()
    {
      return this.editorPanelTabbedPane;
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
     * Returns the jGTIToolBarButtonDeleteProduction.
     *
     * @return The jGTIToolBarButtonDeleteProduction.
     * @see #jGTIToolBarButtonDeleteProduction
     */
    public final JGTIToolBarButton getJGTIToolBarButtonDeleteProduction ()
    {
      return this.jGTIToolBarButtonDeleteProduction;
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
     * Returns the jGTIToolBarButtonEditProduction.
     *
     * @return The jGTIToolBarButtonEditProduction.
     * @see #jGTIToolBarButtonEditProduction
     */
    public final JGTIToolBarButton getJGTIToolBarButtonEditProduction ()
    {
      return this.jGTIToolBarButtonEditProduction;
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
     * Returns the jGTIToolBarButtonPrevious.
     *
     * @return The jGTIToolBarButtonPrevious.
     * @see #jGTIToolBarButtonPrevious
     */
    public final JGTIToolBarButton getJGTIToolBarButtonPrevious ()
    {
      return this.jGTIToolBarButtonPrevious;
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
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        modeSettingsGroup = new javax.swing.ButtonGroup();
        toolbarButton = new javax.swing.ButtonGroup();
        jToolBarMain = new javax.swing.JToolBar();
        jToolBarFile = new javax.swing.JToolBar();
        jGTIToolBarButtonNew = new de.unisiegen.gtitool.ui.swing.JGTIToolBarButton();
        jGTIToolBarButtonOpen = new de.unisiegen.gtitool.ui.swing.JGTIToolBarButton();
        jGTIToolBarButtonSave = new de.unisiegen.gtitool.ui.swing.JGTIToolBarButton();
        jGTIToolBarButtonSaveAs = new de.unisiegen.gtitool.ui.swing.JGTIToolBarButton();
        jSeparatorFileEdit = new javax.swing.JSeparator();
        jToolBarEdit = new javax.swing.JToolBar();
        jGTIToolBarButtonUndo = new de.unisiegen.gtitool.ui.swing.JGTIToolBarButton();
        jGTIToolBarButtonRedo = new de.unisiegen.gtitool.ui.swing.JGTIToolBarButton();
        jSeparatorEditNavigation = new javax.swing.JSeparator();
        jToolBarNavigation = new javax.swing.JToolBar();
        jGTIToolBarButtonEditDocument = new de.unisiegen.gtitool.ui.swing.JGTIToolBarButton();
        jGTIToolBarToggleButtonMouse = new de.unisiegen.gtitool.ui.swing.JGTIToolBarToggleButton();
        jGTIToolBarToggleButtonAddState = new de.unisiegen.gtitool.ui.swing.JGTIToolBarToggleButton();
        jGTIToolBarToggleButtonAddTransition = new de.unisiegen.gtitool.ui.swing.JGTIToolBarToggleButton();
        jGTIToolBarToggleButtonStartState = new de.unisiegen.gtitool.ui.swing.JGTIToolBarToggleButton();
        jGTIToolBarToggleButtonFinalState = new de.unisiegen.gtitool.ui.swing.JGTIToolBarToggleButton();
        jGTIToolBarButtonAddProduction = new de.unisiegen.gtitool.ui.swing.JGTIToolBarButton();
        jGTIToolBarButtonEditProduction = new de.unisiegen.gtitool.ui.swing.JGTIToolBarButton();
        jGTIToolBarButtonDeleteProduction = new de.unisiegen.gtitool.ui.swing.JGTIToolBarButton();
        jSeparatorNavigation = new javax.swing.JSeparator();
        jGTIToolBarButtonStart = new de.unisiegen.gtitool.ui.swing.JGTIToolBarButton();
        jGTIToolBarButtonPrevious = new de.unisiegen.gtitool.ui.swing.JGTIToolBarButton();
        jGTIToolBarButtonNextStep = new de.unisiegen.gtitool.ui.swing.JGTIToolBarButton();
        jGTIToolBarToggleButtonAutoStep = new de.unisiegen.gtitool.ui.swing.JGTIToolBarToggleButton();
        jGTIToolBarButtonStop = new de.unisiegen.gtitool.ui.swing.JGTIToolBarButton();
        editorPanelTabbedPane = new de.unisiegen.gtitool.ui.swing.specialized.EditorPanelTabbedPane();
        jMenuBarMain = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemNew = new javax.swing.JMenuItem();
        jMenuItemOpen = new javax.swing.JMenuItem();
        jMenuItemClose = new javax.swing.JMenuItem();
        jMenuItemCloseAll = new javax.swing.JMenuItem();
        jSeparatorFile1 = new javax.swing.JSeparator();
        jMenuItemSave = new javax.swing.JMenuItem();
        jMenuItemSaveAs = new javax.swing.JMenuItem();
        jMenuItemSaveAll = new javax.swing.JMenuItem();
        jMenuDraft = new javax.swing.JMenu();
        jMenuItemDFA = new javax.swing.JMenuItem();
        jMenuItemNFA = new javax.swing.JMenuItem();
        jMenuItemENFA = new javax.swing.JMenuItem();
        jMenuItemPDA = new javax.swing.JMenuItem();
        jMenuItemRG = new javax.swing.JMenuItem();
        jMenuItemCFG = new javax.swing.JMenuItem();
        jSeparatorFile2 = new javax.swing.JSeparator();
        jMenuRecentlyUsed = new javax.swing.JMenu();
        jSeparatorFile3 = new javax.swing.JSeparator();
        jMenuItemQuit = new javax.swing.JMenuItem();
        jMenuEdit = new javax.swing.JMenu();
        jMenuItemUndo = new javax.swing.JMenuItem();
        jMenuItemRedo = new javax.swing.JMenuItem();
        jSeparatorEdit = new javax.swing.JSeparator();
        jMenuItemPreferences = new javax.swing.JMenuItem();
        jMenuView = new javax.swing.JMenu();
        jCheckBoxMenuItemConsole = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItemTable = new javax.swing.JCheckBoxMenuItem();
        jMenuExecute = new javax.swing.JMenu();
        jMenuItemValidate = new javax.swing.JMenuItem();
        jMenuItemEnterWord = new javax.swing.JMenuItem();
        jMenuItemHistory = new javax.swing.JMenuItem();
        jMenuItemEditMachine = new javax.swing.JMenuItem();
        jMenuItemAutoLayout = new javax.swing.JMenuItem();
        jMenuItemMinimize = new javax.swing.JMenuItem();
        jMenuConvertTo = new javax.swing.JMenu();
        jMenuItemConvertToDFA = new javax.swing.JMenuItem();
        jMenuItemConvertToNFA = new javax.swing.JMenuItem();
        jMenuItemConvertToENFA = new javax.swing.JMenuItem();
        jMenuItemConvertToPDA = new javax.swing.JMenuItem();
        jMenuExtras = new javax.swing.JMenu();
        jMenuItemExchange = new javax.swing.JMenuItem();
        jMenuHelp = new javax.swing.JMenu();
        jMenuItemAbout = new javax.swing.JMenuItem();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jToolBarMain.setBorder(null);
        jToolBarMain.setFloatable(false);
        jToolBarMain.setBorderPainted(false);
        jToolBarFile.setBorder(null);
        jToolBarFile.setFloatable(false);
        jToolBarFile.setBorderPainted(false);
        jToolBarFile.setOpaque(false);
        jGTIToolBarButtonNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/new24.png")));
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages"); // NOI18N
        jGTIToolBarButtonNew.setToolTipText(bundle.getString("MainWindow.NewToolTip")); // NOI18N
        jGTIToolBarButtonNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonNewActionPerformed(evt);
            }
        });

        jToolBarFile.add(jGTIToolBarButtonNew);

        jGTIToolBarButtonOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/open24.png")));
        jGTIToolBarButtonOpen.setToolTipText(bundle.getString("MainWindow.OpenToolTip")); // NOI18N
        jGTIToolBarButtonOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonOpenActionPerformed(evt);
            }
        });

        jToolBarFile.add(jGTIToolBarButtonOpen);

        jGTIToolBarButtonSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/save24.png")));
        jGTIToolBarButtonSave.setToolTipText(bundle.getString("MainWindow.SaveToolTip")); // NOI18N
        jGTIToolBarButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonSaveActionPerformed(evt);
            }
        });

        jToolBarFile.add(jGTIToolBarButtonSave);

        jGTIToolBarButtonSaveAs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/saveas24.png")));
        jGTIToolBarButtonSaveAs.setToolTipText(bundle.getString("MainWindow.SaveAsToolTip")); // NOI18N
        jGTIToolBarButtonSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonSaveAsActionPerformed(evt);
            }
        });

        jToolBarFile.add(jGTIToolBarButtonSaveAs);

        jToolBarMain.add(jToolBarFile);

        jSeparatorFileEdit.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparatorFileEdit.setMaximumSize(new java.awt.Dimension(5, 32));
        jToolBarMain.add(jSeparatorFileEdit);

        jToolBarEdit.setBorder(null);
        jToolBarEdit.setFloatable(false);
        jToolBarEdit.setBorderPainted(false);
        jToolBarEdit.setOpaque(false);
        jGTIToolBarButtonUndo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/edit-undo.png")));
        jGTIToolBarButtonUndo.setToolTipText(bundle.getString("MainWindow.UndoToolTip")); // NOI18N
        jGTIToolBarButtonUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonUndoActionPerformed(evt);
            }
        });

        jToolBarEdit.add(jGTIToolBarButtonUndo);

        jGTIToolBarButtonRedo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/edit-redo.png")));
        jGTIToolBarButtonRedo.setToolTipText(bundle.getString("MainWindow.RedoToolTip")); // NOI18N
        jGTIToolBarButtonRedo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonRedoActionPerformed(evt);
            }
        });

        jToolBarEdit.add(jGTIToolBarButtonRedo);

        jToolBarMain.add(jToolBarEdit);

        jSeparatorEditNavigation.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparatorEditNavigation.setMaximumSize(new java.awt.Dimension(5, 32));
        jToolBarMain.add(jSeparatorEditNavigation);

        jToolBarNavigation.setBorder(null);
        jToolBarNavigation.setFloatable(false);
        jToolBarNavigation.setBorderPainted(false);
        jToolBarNavigation.setOpaque(false);
        jGTIToolBarButtonEditDocument.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/edit-document.png")));
        jGTIToolBarButtonEditDocument.setToolTipText(bundle.getString("MachinePanel.EditDocument")); // NOI18N
        jGTIToolBarButtonEditDocument.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonEditDocumentActionPerformed(evt);
            }
        });

        jToolBarNavigation.add(jGTIToolBarButtonEditDocument);

        toolbarButton.add(jGTIToolBarToggleButtonMouse);
        jGTIToolBarToggleButtonMouse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/toolbar_mouse.gif")));
        jGTIToolBarToggleButtonMouse.setSelected(true);
        jGTIToolBarToggleButtonMouse.setToolTipText(bundle.getString("MachinePanel.Mouse")); // NOI18N
        jGTIToolBarToggleButtonMouse.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jGTIToolBarToggleButtonMouseItemStateChanged(evt);
            }
        });

        jToolBarNavigation.add(jGTIToolBarToggleButtonMouse);

        toolbarButton.add(jGTIToolBarToggleButtonAddState);
        jGTIToolBarToggleButtonAddState.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/state.png")));
        jGTIToolBarToggleButtonAddState.setToolTipText(bundle.getString("MachinePanel.AddState")); // NOI18N
        jGTIToolBarToggleButtonAddState.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jGTIToolBarToggleButtonAddStateItemStateChanged(evt);
            }
        });

        jToolBarNavigation.add(jGTIToolBarToggleButtonAddState);

        toolbarButton.add(jGTIToolBarToggleButtonAddTransition);
        jGTIToolBarToggleButtonAddTransition.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/transition.png")));
        jGTIToolBarToggleButtonAddTransition.setToolTipText(bundle.getString("MachinePanel.AddTransition")); // NOI18N
        jGTIToolBarToggleButtonAddTransition.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jGTIToolBarToggleButtonAddTransitionItemStateChanged(evt);
            }
        });

        jToolBarNavigation.add(jGTIToolBarToggleButtonAddTransition);

        toolbarButton.add(jGTIToolBarToggleButtonStartState);
        jGTIToolBarToggleButtonStartState.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/startState.png")));
        jGTIToolBarToggleButtonStartState.setToolTipText(bundle.getString("MachinePanel.StartState")); // NOI18N
        jGTIToolBarToggleButtonStartState.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jGTIToolBarToggleButtonStartStateItemStateChanged(evt);
            }
        });

        jToolBarNavigation.add(jGTIToolBarToggleButtonStartState);

        toolbarButton.add(jGTIToolBarToggleButtonFinalState);
        jGTIToolBarToggleButtonFinalState.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/finalState.png")));
        jGTIToolBarToggleButtonFinalState.setToolTipText(bundle.getString("MachinePanel.FinalState")); // NOI18N
        jGTIToolBarToggleButtonFinalState.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jGTIToolBarToggleButtonFinalStateItemStateChanged(evt);
            }
        });

        jToolBarNavigation.add(jGTIToolBarToggleButtonFinalState);

        jGTIToolBarButtonAddProduction.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/add-production.png")));
        jGTIToolBarButtonAddProduction.setToolTipText(bundle.getString("GrammarPanel.AddProduction")); // NOI18N
        jGTIToolBarButtonAddProduction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonAddProductionActionPerformed(evt);
            }
        });

        jToolBarNavigation.add(jGTIToolBarButtonAddProduction);

        jGTIToolBarButtonEditProduction.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/edit-production.png")));
        jGTIToolBarButtonEditProduction.setToolTipText(bundle.getString("GrammarPanel.ProductionProperties")); // NOI18N
        jGTIToolBarButtonEditProduction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonEditProductionActionPerformed(evt);
            }
        });

        jToolBarNavigation.add(jGTIToolBarButtonEditProduction);

        jGTIToolBarButtonDeleteProduction.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/delete-production.png")));
        jGTIToolBarButtonDeleteProduction.setToolTipText(bundle.getString("GrammarPanel.DeleteProduction")); // NOI18N
        jGTIToolBarButtonDeleteProduction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonDeleteProductionActionPerformed(evt);
            }
        });

        jToolBarNavigation.add(jGTIToolBarButtonDeleteProduction);

        jSeparatorNavigation.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparatorNavigation.setMaximumSize(new java.awt.Dimension(5, 32));
        jToolBarNavigation.add(jSeparatorNavigation);

        jGTIToolBarButtonStart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/enterword/word-start.png")));
        jGTIToolBarButtonStart.setToolTipText(bundle.getString("MachinePanel.WordModeStart")); // NOI18N
        jGTIToolBarButtonStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonStartActionPerformed(evt);
            }
        });

        jToolBarNavigation.add(jGTIToolBarButtonStart);

        jGTIToolBarButtonPrevious.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/enterword/word-backward.png")));
        jGTIToolBarButtonPrevious.setToolTipText(bundle.getString("MachinePanel.WordModePreviousStep")); // NOI18N
        jGTIToolBarButtonPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonPreviousActionPerformed(evt);
            }
        });

        jToolBarNavigation.add(jGTIToolBarButtonPrevious);

        jGTIToolBarButtonNextStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/enterword/word-forward.png")));
        jGTIToolBarButtonNextStep.setToolTipText(bundle.getString("MachinePanel.WordModeNextStep")); // NOI18N
        jGTIToolBarButtonNextStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonNextStepActionPerformed(evt);
            }
        });

        jToolBarNavigation.add(jGTIToolBarButtonNextStep);

        jGTIToolBarToggleButtonAutoStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/enterword/word-autostep.png")));
        jGTIToolBarToggleButtonAutoStep.setToolTipText(bundle.getString("MachinePanel.WordModeAutoStep")); // NOI18N
        jGTIToolBarToggleButtonAutoStep.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jGTIToolBarToggleButtonAutoStepItemStateChanged(evt);
            }
        });

        jToolBarNavigation.add(jGTIToolBarToggleButtonAutoStep);

        jGTIToolBarButtonStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/toolbar/enterword/word-stop.png")));
        jGTIToolBarButtonStop.setToolTipText(bundle.getString("MachinePanel.WordModeStop")); // NOI18N
        jGTIToolBarButtonStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonStopActionPerformed(evt);
            }
        });

        jToolBarNavigation.add(jGTIToolBarButtonStop);

        jToolBarMain.add(jToolBarNavigation);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jToolBarMain, gridBagConstraints);

        editorPanelTabbedPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                editorPanelTabbedPaneMouseReleased(evt);
            }
        });
        editorPanelTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                editorPanelTabbedPaneStateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(editorPanelTabbedPane, gridBagConstraints);

        jMenuFile.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.FileMnemonic").charAt(0));
        jMenuFile.setText(bundle.getString("MainWindow.File")); // NOI18N
        jMenuItemNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemNew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/new16.gif")));
        jMenuItemNew.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.NewMnemonic").charAt(0));
        jMenuItemNew.setText(bundle.getString("MainWindow.New")); // NOI18N
        jMenuItemNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleNew(evt);
            }
        });

        jMenuFile.add(jMenuItemNew);

        jMenuItemOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemOpen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/open16.png")));
        jMenuItemOpen.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.OpenMnemonic").charAt(0));
        jMenuItemOpen.setText(bundle.getString("MainWindow.Open")); // NOI18N
        jMenuItemOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleOpen(evt);
            }
        });

        jMenuFile.add(jMenuItemOpen);

        jMenuItemClose.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/empty16.gif")));
        jMenuItemClose.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.CloseMnemonic").charAt(0));
        jMenuItemClose.setText(bundle.getString("MainWindow.Close")); // NOI18N
        jMenuItemClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleClose(evt);
            }
        });

        jMenuFile.add(jMenuItemClose);

        jMenuItemCloseAll.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemCloseAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/empty16.gif")));
        jMenuItemCloseAll.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.CloseAllMnemonic").charAt(0));
        jMenuItemCloseAll.setText(bundle.getString("MainWindow.CloseAll")); // NOI18N
        jMenuItemCloseAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleCloseAll(evt);
            }
        });

        jMenuFile.add(jMenuItemCloseAll);

        jMenuFile.add(jSeparatorFile1);

        jMenuItemSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/save16.png")));
        jMenuItemSave.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.SaveMnemonic").charAt(0));
        jMenuItemSave.setText(bundle.getString("MainWindow.Save")); // NOI18N
        jMenuItemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleSave(evt);
            }
        });

        jMenuFile.add(jMenuItemSave);

        jMenuItemSaveAs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/saveas16.png")));
        jMenuItemSaveAs.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.SaveAsMnemonic").charAt(0));
        jMenuItemSaveAs.setText(bundle.getString("MainWindow.SaveAs")); // NOI18N
        jMenuItemSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleSaveAs(evt);
            }
        });

        jMenuFile.add(jMenuItemSaveAs);

        jMenuItemSaveAll.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemSaveAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/saveAll16.gif")));
        jMenuItemSaveAll.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.SaveAllMnemonic").charAt(0));
        jMenuItemSaveAll.setText(bundle.getString("MainWindow.SaveAll")); // NOI18N
        jMenuItemSaveAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleSaveAll(evt);
            }
        });

        jMenuFile.add(jMenuItemSaveAll);

        jMenuDraft.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/empty16.gif")));
        jMenuDraft.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.DraftForMnemonic").charAt(0));
        jMenuDraft.setText(bundle.getString("MainWindow.DraftFor")); // NOI18N
        jMenuItemDFA.setText(bundle.getString("MainWindow.DFA")); // NOI18N
        jMenuItemDFA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemDFAhandleDraftFor(evt);
            }
        });

        jMenuDraft.add(jMenuItemDFA);

        jMenuItemNFA.setText(bundle.getString("MainWindow.NFA")); // NOI18N
        jMenuItemNFA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemNFAhandleDraftFor(evt);
            }
        });

        jMenuDraft.add(jMenuItemNFA);

        jMenuItemENFA.setText(bundle.getString("MainWindow.ENFA")); // NOI18N
        jMenuItemENFA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemENFAhandleDraftFor(evt);
            }
        });

        jMenuDraft.add(jMenuItemENFA);

        jMenuItemPDA.setText(bundle.getString("MainWindow.PDA")); // NOI18N
        jMenuItemPDA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPDAhandleDraftFor(evt);
            }
        });

        jMenuDraft.add(jMenuItemPDA);

        jMenuItemRG.setText(bundle.getString("MainWindow.RG")); // NOI18N
        jMenuItemRG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRGhandleDraftFor(evt);
            }
        });

        jMenuDraft.add(jMenuItemRG);

        jMenuItemCFG.setText(bundle.getString("MainWindow.CFG")); // NOI18N
        jMenuItemCFG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCFGhandleDraftFor(evt);
            }
        });

        jMenuDraft.add(jMenuItemCFG);

        jMenuFile.add(jMenuDraft);

        jMenuFile.add(jSeparatorFile2);

        jMenuRecentlyUsed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/empty16.gif")));
        jMenuRecentlyUsed.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.RecentlyUsedMnemonic").charAt(0));
        jMenuRecentlyUsed.setText(bundle.getString("MainWindow.RecentlyUsed")); // NOI18N
        jMenuFile.add(jMenuRecentlyUsed);

        jMenuFile.add(jSeparatorFile3);

        jMenuItemQuit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemQuit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/empty16.gif")));
        jMenuItemQuit.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.QuitMnemonic").charAt(0));
        jMenuItemQuit.setText(bundle.getString("MainWindow.Quit")); // NOI18N
        jMenuItemQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleQuit(evt);
            }
        });

        jMenuFile.add(jMenuItemQuit);

        jMenuBarMain.add(jMenuFile);

        jMenuEdit.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.EditMnemonic").charAt(0));
        jMenuEdit.setText(bundle.getString("MainWindow.Edit")); // NOI18N
        jMenuItemUndo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemUndo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/edit-undo.png")));
        jMenuItemUndo.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.UndoMnemonic").charAt(0));
        jMenuItemUndo.setText(bundle.getString("MainWindow.Undo")); // NOI18N
        jMenuItemUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleUndo(evt);
            }
        });

        jMenuEdit.add(jMenuItemUndo);

        jMenuItemRedo.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemRedo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/edit-redo.png")));
        jMenuItemRedo.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.RedoMnemonic").charAt(0));
        jMenuItemRedo.setText(bundle.getString("MainWindow.Redo")); // NOI18N
        jMenuItemRedo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleRedo(evt);
            }
        });

        jMenuEdit.add(jMenuItemRedo);

        jMenuEdit.add(jSeparatorEdit);

        jMenuItemPreferences.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/preferences16.png")));
        jMenuItemPreferences.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.PreferencesMnemonic").charAt(0));
        jMenuItemPreferences.setText(bundle.getString("MainWindow.Preferences")); // NOI18N
        jMenuItemPreferences.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemPreferencesActionPerformed(evt);
            }
        });

        jMenuEdit.add(jMenuItemPreferences);

        jMenuBarMain.add(jMenuEdit);

        jMenuView.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.ViewMnemonic").charAt(0));
        jMenuView.setText(bundle.getString("MainWindow.View")); // NOI18N
        jCheckBoxMenuItemConsole.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.ConsoleMnemonic").charAt(0));
        jCheckBoxMenuItemConsole.setSelected(true);
        jCheckBoxMenuItemConsole.setText(bundle.getString("MainWindow.Console")); // NOI18N
        jCheckBoxMenuItemConsole.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxMenuItemConsoleItemStateChanged(evt);
            }
        });

        jMenuView.add(jCheckBoxMenuItemConsole);

        jCheckBoxMenuItemTable.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.TableMnemonic").charAt(0));
        jCheckBoxMenuItemTable.setSelected(true);
        jCheckBoxMenuItemTable.setText(bundle.getString("MainWindow.Table")); // NOI18N
        jCheckBoxMenuItemTable.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBoxMenuItemTableItemStateChanged(evt);
            }
        });

        jMenuView.add(jCheckBoxMenuItemTable);

        jMenuBarMain.add(jMenuView);

        jMenuExecute.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.ExecuteMnemonic").charAt(0));
        jMenuExecute.setText(bundle.getString("MainWindow.Execute")); // NOI18N
        jMenuItemValidate.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.ValidateMnemonic").charAt(0));
        jMenuItemValidate.setText(bundle.getString("MainWindow.Validate")); // NOI18N
        jMenuItemValidate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemValidateActionPerformed(evt);
            }
        });

        jMenuExecute.add(jMenuItemValidate);

        jMenuItemEnterWord.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.EnterWordMnemonic").charAt(0));
        jMenuItemEnterWord.setText(bundle.getString("MainWindow.EnterWord")); // NOI18N
        jMenuItemEnterWord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEnterWordActionPerformed(evt);
            }
        });

        jMenuExecute.add(jMenuItemEnterWord);

        jMenuItemHistory.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.HistoryMnemonic").charAt(0));
        jMenuItemHistory.setText(bundle.getString("MainWindow.History")); // NOI18N
        jMenuItemHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemHistoryActionPerformed(evt);
            }
        });

        jMenuExecute.add(jMenuItemHistory);

        jMenuItemEditMachine.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.EditMachineMnemonic").charAt(0));
        jMenuItemEditMachine.setText(bundle.getString("MainWindow.EditMachine")); // NOI18N
        jMenuItemEditMachine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEditMachineActionPerformed(evt);
            }
        });

        jMenuExecute.add(jMenuItemEditMachine);

        jMenuItemAutoLayout.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.AutoLayoutMnemonic").charAt(0));
        jMenuItemAutoLayout.setText(bundle.getString("MainWindow.AutoLayout")); // NOI18N
        jMenuItemAutoLayout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAutoLayoutActionPerformed(evt);
            }
        });

        jMenuExecute.add(jMenuItemAutoLayout);

        jMenuItemMinimize.setText(bundle.getString("MainWindow.Minimize")); // NOI18N
        jMenuItemMinimize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemMinimizeActionPerformed(evt);
            }
        });

        jMenuExecute.add(jMenuItemMinimize);

        jMenuConvertTo.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.ConvertToMnemonic").charAt(0));
        jMenuConvertTo.setText(bundle.getString("MainWindow.ConvertTo")); // NOI18N
        jMenuItemConvertToDFA.setText(bundle.getString("MainWindow.DFA")); // NOI18N
        jMenuItemConvertToDFA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemConvertToDFAActionPerformed(evt);
            }
        });

        jMenuConvertTo.add(jMenuItemConvertToDFA);

        jMenuItemConvertToNFA.setText(bundle.getString("MainWindow.NFA")); // NOI18N
        jMenuItemConvertToNFA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemConvertToNFActionPerformed(evt);
            }
        });

        jMenuConvertTo.add(jMenuItemConvertToNFA);

        jMenuItemConvertToENFA.setText(bundle.getString("MainWindow.ENFA")); // NOI18N
        jMenuItemConvertToENFA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemConvertToENFAActionPerformed(evt);
            }
        });

        jMenuConvertTo.add(jMenuItemConvertToENFA);

        jMenuItemConvertToPDA.setText(bundle.getString("MainWindow.PDA")); // NOI18N
        jMenuItemConvertToPDA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemConvertToPDAActionPerformed(evt);
            }
        });

        jMenuConvertTo.add(jMenuItemConvertToPDA);

        jMenuExecute.add(jMenuConvertTo);

        jMenuBarMain.add(jMenuExecute);

        jMenuExtras.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.ExtrasMnemonic").charAt(0));
        jMenuExtras.setText(bundle.getString("MainWindow.Extras")); // NOI18N
        jMenuItemExchange.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.ExchangeMnemonic").charAt(0));
        jMenuItemExchange.setText(bundle.getString("MainWindow.Exchange")); // NOI18N
        jMenuItemExchange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExchangeActionPerformed(evt);
            }
        });

        jMenuExtras.add(jMenuItemExchange);

        jMenuBarMain.add(jMenuExtras);

        jMenuHelp.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.HelpMnemonic").charAt(0));
        jMenuHelp.setText(bundle.getString("MainWindow.Help")); // NOI18N
        jMenuItemAbout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItemAbout.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/messages").getString("MainWindow.AboutMnemonic").charAt(0));
        jMenuItemAbout.setText(bundle.getString("MainWindow.About")); // NOI18N
        jMenuItemAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAboutActionPerformed(evt);
            }
        });

        jMenuHelp.add(jMenuItemAbout);

        jMenuBarMain.add(jMenuHelp);

        setJMenuBar(jMenuBarMain);

        setBounds(0, 0, 762, 462);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemMinimizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemMinimizeActionPerformed
        this.logic.minimize();
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

    private void editorPanelTabbedPaneMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editorPanelTabbedPaneMouseReleased
        this.logic.handleTabbedPaneMouseReleased(evt);
    }//GEN-LAST:event_editorPanelTabbedPaneMouseReleased

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

    private void jGTIToolBarButtonEditProductionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIToolBarButtonEditProductionActionPerformed
        this.logic.handleEditProduction();
    }//GEN-LAST:event_jGTIToolBarButtonEditProductionActionPerformed

    private void jGTIToolBarButtonDeleteProductionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIToolBarButtonDeleteProductionActionPerformed
        this.logic.handleDeleteProduction();
    }//GEN-LAST:event_jGTIToolBarButtonDeleteProductionActionPerformed

    private void editorPanelTabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_editorPanelTabbedPaneStateChanged
        this.logic.handleTabbedPaneStateChanged();
    }//GEN-LAST:event_editorPanelTabbedPaneStateChanged

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

    private void jGTIToolBarButtonPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIToolBarButtonPreviousActionPerformed
        this.logic.handleWordPreviousStep();
    }//GEN-LAST:event_jGTIToolBarButtonPreviousActionPerformed

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
        this.logic.handleSave(this.editorPanelTabbedPane.getSelectedEditorPanel ());
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
        this.logic.handleDraftFor(DefaultMachineModel.MachineType.DFA);
    }//GEN-LAST:event_jMenuItemDFAhandleDraftFor

    private void jMenuItemNFAhandleDraftFor(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemNFAhandleDraftFor
        this.logic.handleDraftFor(DefaultMachineModel.MachineType.NFA);
    }//GEN-LAST:event_jMenuItemNFAhandleDraftFor

    private void jMenuItemPDAhandleDraftFor(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemPDAhandleDraftFor
        this.logic.handleDraftFor(DefaultMachineModel.MachineType.PDA);
    }//GEN-LAST:event_jMenuItemPDAhandleDraftFor

    private void jMenuItemENFAhandleDraftFor(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemENFAhandleDraftFor
        this.logic.handleDraftFor(DefaultMachineModel.MachineType.ENFA);
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
        this.logic.handleClose(this.editorPanelTabbedPane.getSelectedEditorPanel ());
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
        this.logic.handleSave(this.editorPanelTabbedPane.getSelectedEditorPanel ());
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
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.unisiegen.gtitool.ui.swing.specialized.EditorPanelTabbedPane editorPanelTabbedPane;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemConsole;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemTable;
    private de.unisiegen.gtitool.ui.swing.JGTIToolBarButton jGTIToolBarButtonAddProduction;
    private de.unisiegen.gtitool.ui.swing.JGTIToolBarButton jGTIToolBarButtonDeleteProduction;
    private de.unisiegen.gtitool.ui.swing.JGTIToolBarButton jGTIToolBarButtonEditDocument;
    private de.unisiegen.gtitool.ui.swing.JGTIToolBarButton jGTIToolBarButtonEditProduction;
    private de.unisiegen.gtitool.ui.swing.JGTIToolBarButton jGTIToolBarButtonNew;
    private de.unisiegen.gtitool.ui.swing.JGTIToolBarButton jGTIToolBarButtonNextStep;
    private de.unisiegen.gtitool.ui.swing.JGTIToolBarButton jGTIToolBarButtonOpen;
    private de.unisiegen.gtitool.ui.swing.JGTIToolBarButton jGTIToolBarButtonPrevious;
    private de.unisiegen.gtitool.ui.swing.JGTIToolBarButton jGTIToolBarButtonRedo;
    private de.unisiegen.gtitool.ui.swing.JGTIToolBarButton jGTIToolBarButtonSave;
    private de.unisiegen.gtitool.ui.swing.JGTIToolBarButton jGTIToolBarButtonSaveAs;
    private de.unisiegen.gtitool.ui.swing.JGTIToolBarButton jGTIToolBarButtonStart;
    private de.unisiegen.gtitool.ui.swing.JGTIToolBarButton jGTIToolBarButtonStop;
    private de.unisiegen.gtitool.ui.swing.JGTIToolBarButton jGTIToolBarButtonUndo;
    private de.unisiegen.gtitool.ui.swing.JGTIToolBarToggleButton jGTIToolBarToggleButtonAddState;
    private de.unisiegen.gtitool.ui.swing.JGTIToolBarToggleButton jGTIToolBarToggleButtonAddTransition;
    private de.unisiegen.gtitool.ui.swing.JGTIToolBarToggleButton jGTIToolBarToggleButtonAutoStep;
    private de.unisiegen.gtitool.ui.swing.JGTIToolBarToggleButton jGTIToolBarToggleButtonFinalState;
    private de.unisiegen.gtitool.ui.swing.JGTIToolBarToggleButton jGTIToolBarToggleButtonMouse;
    private de.unisiegen.gtitool.ui.swing.JGTIToolBarToggleButton jGTIToolBarToggleButtonStartState;
    private javax.swing.JMenuBar jMenuBarMain;
    private javax.swing.JMenu jMenuConvertTo;
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
    private javax.swing.JMenuItem jMenuItemConvertToDFA;
    private javax.swing.JMenuItem jMenuItemConvertToENFA;
    private javax.swing.JMenuItem jMenuItemConvertToNFA;
    private javax.swing.JMenuItem jMenuItemConvertToPDA;
    private javax.swing.JMenuItem jMenuItemDFA;
    private javax.swing.JMenuItem jMenuItemENFA;
    private javax.swing.JMenuItem jMenuItemEditMachine;
    private javax.swing.JMenuItem jMenuItemEnterWord;
    private javax.swing.JMenuItem jMenuItemExchange;
    private javax.swing.JMenuItem jMenuItemHistory;
    private javax.swing.JMenuItem jMenuItemMinimize;
    private javax.swing.JMenuItem jMenuItemNFA;
    private javax.swing.JMenuItem jMenuItemNew;
    private javax.swing.JMenuItem jMenuItemOpen;
    private javax.swing.JMenuItem jMenuItemPDA;
    private javax.swing.JMenuItem jMenuItemPreferences;
    private javax.swing.JMenuItem jMenuItemQuit;
    private javax.swing.JMenuItem jMenuItemRG;
    private javax.swing.JMenuItem jMenuItemRedo;
    private javax.swing.JMenuItem jMenuItemSave;
    private javax.swing.JMenuItem jMenuItemSaveAll;
    private javax.swing.JMenuItem jMenuItemSaveAs;
    private javax.swing.JMenuItem jMenuItemUndo;
    private javax.swing.JMenuItem jMenuItemValidate;
    private javax.swing.JMenu jMenuRecentlyUsed;
    private javax.swing.JMenu jMenuView;
    private javax.swing.JSeparator jSeparatorEdit;
    private javax.swing.JSeparator jSeparatorEditNavigation;
    private javax.swing.JSeparator jSeparatorFile1;
    private javax.swing.JSeparator jSeparatorFile2;
    private javax.swing.JSeparator jSeparatorFile3;
    private javax.swing.JSeparator jSeparatorFileEdit;
    private javax.swing.JSeparator jSeparatorNavigation;
    private javax.swing.JToolBar jToolBarEdit;
    private javax.swing.JToolBar jToolBarFile;
    private javax.swing.JToolBar jToolBarMain;
    private javax.swing.JToolBar jToolBarNavigation;
    private javax.swing.ButtonGroup modeSettingsGroup;
    private javax.swing.ButtonGroup toolbarButton;
    // End of variables declaration//GEN-END:variables

}
