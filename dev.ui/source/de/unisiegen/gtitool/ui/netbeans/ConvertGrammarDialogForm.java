package de.unisiegen.gtitool.ui.netbeans;

import de.unisiegen.gtitool.ui.logic.ConvertGrammarDialog;
import javax.swing.JDialog;
import javax.swing.JFrame;

import de.unisiegen.gtitool.ui.logic.ConvertMachineDialog;
import de.unisiegen.gtitool.ui.logic.ConvertRegexToMachineDialog;
import de.unisiegen.gtitool.ui.netbeans.interfaces.GUIClass;


/**
 * The {@link ConvertMachineDialogForm}.
 * 
 * @author Christian Fehler
 * @version $Id: ConvertMachineDialogForm.java 1220 2008-08-01 09:22:03Z fehler $
 */
@SuppressWarnings({ "all" })
public class ConvertGrammarDialogForm extends JDialog implements GUIClass <ConvertGrammarDialog>
{
    
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 826473225784921404L;
    
    /**
     * The {@link ConvertMachineDialog}.
     */
    private ConvertGrammarDialog logic ;
    
    /**
     * Allocates a new {@link ConvertMachineDialogForm}.
     * 
     * @param logic The {@link ConvertMachineDialog}.
     * @param parent The parent {@link JFrame}.
     */
    public ConvertGrammarDialogForm(ConvertGrammarDialog logic, JFrame parent) {
        super(parent, true);
        this.logic = logic;
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jGTIToolBarMain = new de.unisiegen.gtitool.ui.swing.JGTIToolBar();
        jGTIToolBarButtonBeginStep = new de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton();
        jGTIToolBarButtonPreviousStep = new de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton();
        jGTIToolBarButtonNextStep = new de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton();
        jGTIToolBarToggleButtonAutoStep = new de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarToggleButton();
        jGTIToolBarButtonStop = new de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton();
        jGTIToolBarButtonEndStep = new de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton();
        jGTISplitPaneOutline = new de.unisiegen.gtitool.ui.swing.JGTISplitPane();
        jGTIScrollPaneOutline = new de.unisiegen.gtitool.ui.swing.JGTIScrollPane();
        jGTITableOutline = new de.unisiegen.gtitool.ui.swing.JGTITable();
        jGTISplitPaneGraph = new de.unisiegen.gtitool.ui.swing.JGTISplitPane();
        jGTIPanelOriginal = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIPanelGrammarOriginal = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIScrollPaneGrammarOriginal = new de.unisiegen.gtitool.ui.swing.JGTIScrollPane();
        jGTITableGrammarOriginal = new de.unisiegen.gtitool.ui.swing.JGTITable();
        styledTerminalSymbolSetParserPanelOriginal = new de.unisiegen.gtitool.ui.style.StyledTerminalSymbolSetParserPanel();
        jGTILabelTerminalSymbolsOriginal = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        styledStartNonterminalSymbolParserPanelOriginal = new de.unisiegen.gtitool.ui.style.StyledStartNonterminalSymbolParserPanel();
        jGTILabelStartSymbolOriginal = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        styledNonterminalSymbolSetParserPanelOriginal = new de.unisiegen.gtitool.ui.style.StyledNonterminalSymbolSetParserPanel();
        jGTILabelNonterminalSymbolsOriginal = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTILabelOriginal = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTIPanelConverted = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIPanelGrammarConverted = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIScrollPaneGrammarConverted = new de.unisiegen.gtitool.ui.swing.JGTIScrollPane();
        jGTITableGrammarConverted = new de.unisiegen.gtitool.ui.swing.JGTITable();
        styledTerminalSymbolSetParserPanelConverted = new de.unisiegen.gtitool.ui.style.StyledTerminalSymbolSetParserPanel();
        jGTILabelTerminalSymbolsConverted = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        styledStartNonterminalSymbolParserPanelConverted = new de.unisiegen.gtitool.ui.style.StyledStartNonterminalSymbolParserPanel();
        jGTILabelStartSymbolConverted = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTILabelNonterminalSymbolsConverted = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jScrollPaneNonterminalsConverted = new javax.swing.JScrollPane();
        jGTIListNonterminalsConverted = new de.unisiegen.gtitool.ui.swing.JGTIList();
        jGTIPanelPreferences = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTICheckBoxEpsilonProductions = new de.unisiegen.gtitool.ui.swing.JGTICheckBox();
        jGTICheckBoxEntityProductions = new de.unisiegen.gtitool.ui.swing.JGTICheckBox();
        jGTILabelConverted = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        styledNonterminalSymbolSetParserPanelConverted = new de.unisiegen.gtitool.ui.style.StyledNonterminalSymbolSetParserPanel();
        jGTIButtonCancel = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIButtonOk = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIButtonPrint = new de.unisiegen.gtitool.ui.swing.JGTIButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Eliminate left recursion");
        setModal(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jGTIToolBarButtonBeginStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/navigation/large/begin.png"))); // NOI18N
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages"); // NOI18N
        jGTIToolBarButtonBeginStep.setToolTipText(bundle.getString("ConvertMachineDialog.BeginStep")); // NOI18N
        jGTIToolBarButtonBeginStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonBeginStepActionPerformed(evt);
            }
        });
        jGTIToolBarMain.add(jGTIToolBarButtonBeginStep);

        jGTIToolBarButtonPreviousStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/navigation/large/backward.png"))); // NOI18N
        jGTIToolBarButtonPreviousStep.setToolTipText(bundle.getString("ConvertMachineDialog.PreviousStep")); // NOI18N
        jGTIToolBarButtonPreviousStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonPreviousStepActionPerformed(evt);
            }
        });
        jGTIToolBarMain.add(jGTIToolBarButtonPreviousStep);

        jGTIToolBarButtonNextStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/navigation/large/forward.png"))); // NOI18N
        jGTIToolBarButtonNextStep.setToolTipText(bundle.getString("ConvertMachineDialog.NextStep")); // NOI18N
        jGTIToolBarButtonNextStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonNextStepActionPerformed(evt);
            }
        });
        jGTIToolBarMain.add(jGTIToolBarButtonNextStep);

        jGTIToolBarToggleButtonAutoStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/navigation/large/autostep.png"))); // NOI18N
        jGTIToolBarToggleButtonAutoStep.setToolTipText(bundle.getString("ConvertMachineDialog.AutoStep")); // NOI18N
        jGTIToolBarToggleButtonAutoStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarToggleButtonAutoStepActionPerformed(evt);
            }
        });
        jGTIToolBarMain.add(jGTIToolBarToggleButtonAutoStep);

        jGTIToolBarButtonStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/navigation/large/stop.png"))); // NOI18N
        jGTIToolBarButtonStop.setToolTipText(bundle.getString("ConvertMachineDialog.Stop")); // NOI18N
        jGTIToolBarButtonStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonStopActionPerformed(evt);
            }
        });
        jGTIToolBarMain.add(jGTIToolBarButtonStop);

        jGTIToolBarButtonEndStep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/unisiegen/gtitool/ui/icon/navigation/large/end.png"))); // NOI18N
        jGTIToolBarButtonEndStep.setToolTipText(bundle.getString("ConvertMachineDialog.EndStep")); // NOI18N
        jGTIToolBarButtonEndStep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIToolBarButtonEndStepActionPerformed(evt);
            }
        });
        jGTIToolBarMain.add(jGTIToolBarButtonEndStep);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jGTIToolBarMain, gridBagConstraints);

        jGTISplitPaneOutline.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jGTISplitPaneOutline.setDividerLocation(600);
        jGTISplitPaneOutline.setResizeWeight(1.0);

        jGTIScrollPaneOutline.setBorder(null);
        jGTIScrollPaneOutline.setMinimumSize(new java.awt.Dimension(200, 200));
        jGTIScrollPaneOutline.setViewportView(jGTITableOutline);

        jGTISplitPaneOutline.setRightComponent(jGTIScrollPaneOutline);

        jGTISplitPaneGraph.setDividerLocation(300);
        jGTISplitPaneGraph.setResizeWeight(0.5);

        jGTIScrollPaneGrammarOriginal.setBorder(null);

        jGTITableGrammarOriginal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null}
            },
            new String [] {
                "Title 1", "Title 2"
            }
        ));
        jGTIScrollPaneGrammarOriginal.setViewportView(jGTITableGrammarOriginal);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jGTIPanelGrammarOriginal.add(jGTIScrollPaneGrammarOriginal, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        jGTIPanelOriginal.add(jGTIPanelGrammarOriginal, gridBagConstraints);

        styledTerminalSymbolSetParserPanelOriginal.setCopyable(true);
        styledTerminalSymbolSetParserPanelOriginal.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 16, 16);
        jGTIPanelOriginal.add(styledTerminalSymbolSetParserPanelOriginal, gridBagConstraints);

        jGTILabelTerminalSymbolsOriginal.setText(bundle.getString("TerminalPanel.TerminalSymbols")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        jGTIPanelOriginal.add(jGTILabelTerminalSymbolsOriginal, gridBagConstraints);

        styledStartNonterminalSymbolParserPanelOriginal.setCopyable(true);
        styledStartNonterminalSymbolParserPanelOriginal.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 16);
        jGTIPanelOriginal.add(styledStartNonterminalSymbolParserPanelOriginal, gridBagConstraints);

        jGTILabelStartSymbolOriginal.setText(bundle.getString("TerminalPanel.StartSymbol")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        jGTIPanelOriginal.add(jGTILabelStartSymbolOriginal, gridBagConstraints);

        styledNonterminalSymbolSetParserPanelOriginal.setCopyable(true);
        styledNonterminalSymbolSetParserPanelOriginal.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 16);
        jGTIPanelOriginal.add(styledNonterminalSymbolSetParserPanelOriginal, gridBagConstraints);

        jGTILabelNonterminalSymbolsOriginal.setText(bundle.getString("TerminalPanel.NonterminalSymbols")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        jGTIPanelOriginal.add(jGTILabelNonterminalSymbolsOriginal, gridBagConstraints);

        jGTILabelOriginal.setText(bundle.getString("ConvertGrammarDialog.Original")); // NOI18N
        jGTILabelOriginal.setFont(new java.awt.Font("Dialog", 1, 11));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(16, 16, 5, 16);
        jGTIPanelOriginal.add(jGTILabelOriginal, gridBagConstraints);

        jGTISplitPaneGraph.setTopComponent(jGTIPanelOriginal);

        jGTIScrollPaneGrammarConverted.setBorder(null);

        jGTITableGrammarConverted.setAutoCreateColumnsFromModel(false);
        jGTITableGrammarConverted.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null}
            },
            new String [] {
                "Title 1"
            }
        ));
        jGTIScrollPaneGrammarConverted.setViewportView(jGTITableGrammarConverted);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jGTIPanelGrammarConverted.add(jGTIScrollPaneGrammarConverted, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.75;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        jGTIPanelConverted.add(jGTIPanelGrammarConverted, gridBagConstraints);

        styledTerminalSymbolSetParserPanelConverted.setCopyable(true);
        styledTerminalSymbolSetParserPanelConverted.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 16, 16);
        jGTIPanelConverted.add(styledTerminalSymbolSetParserPanelConverted, gridBagConstraints);

        jGTILabelTerminalSymbolsConverted.setText(bundle.getString("TerminalPanel.TerminalSymbols")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        jGTIPanelConverted.add(jGTILabelTerminalSymbolsConverted, gridBagConstraints);

        styledStartNonterminalSymbolParserPanelConverted.setCopyable(true);
        styledStartNonterminalSymbolParserPanelConverted.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 16);
        jGTIPanelConverted.add(styledStartNonterminalSymbolParserPanelConverted, gridBagConstraints);

        jGTILabelStartSymbolConverted.setText(bundle.getString("TerminalPanel.StartSymbol")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        jGTIPanelConverted.add(jGTILabelStartSymbolConverted, gridBagConstraints);

        jGTILabelNonterminalSymbolsConverted.setText(bundle.getString("TerminalPanel.NonterminalSymbols")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(16, 16, 5, 16);
        jGTIPanelConverted.add(jGTILabelNonterminalSymbolsConverted, gridBagConstraints);

        jScrollPaneNonterminalsConverted.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPaneNonterminalsConverted.setAutoscrolls(true);

        jGTIListNonterminalsConverted.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jScrollPaneNonterminalsConverted.setViewportView(jGTIListNonterminalsConverted);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 16);
        jGTIPanelConverted.add(jScrollPaneNonterminalsConverted, gridBagConstraints);

        jGTIPanelPreferences.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("ConvertGrammarDialog.Preferences"))); // NOI18N

        jGTICheckBoxEpsilonProductions.setSelected(true);
        jGTICheckBoxEpsilonProductions.setText(bundle.getString("ConvertGrammarDialog.PreferencesEpsilon")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jGTIPanelPreferences.add(jGTICheckBoxEpsilonProductions, gridBagConstraints);

        jGTICheckBoxEntityProductions.setSelected(true);
        jGTICheckBoxEntityProductions.setText(bundle.getString("ConvertGrammarDialog.PreferencesEntity")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jGTIPanelPreferences.add(jGTICheckBoxEntityProductions, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 16, 0, 16);
        jGTIPanelConverted.add(jGTIPanelPreferences, gridBagConstraints);

        jGTILabelConverted.setText(bundle.getString("ConvertGrammarDialog.Converted")); // NOI18N
        jGTILabelConverted.setFont(new java.awt.Font("Dialog", 1, 11));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(16, 16, 0, 16);
        jGTIPanelConverted.add(jGTILabelConverted, gridBagConstraints);

        styledNonterminalSymbolSetParserPanelConverted.setCopyable(true);
        styledNonterminalSymbolSetParserPanelConverted.setEditable(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 16);
        jGTIPanelConverted.add(styledNonterminalSymbolSetParserPanelConverted, gridBagConstraints);

        jGTISplitPaneGraph.setRightComponent(jGTIPanelConverted);

        jGTISplitPaneOutline.setLeftComponent(jGTISplitPaneGraph);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(16, 16, 5, 16);
        getContentPane().add(jGTISplitPaneOutline, gridBagConstraints);

        jGTIButtonCancel.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("ConvertMachineDialog.CancelMnemonic").charAt(0));
        jGTIButtonCancel.setText(bundle.getString("ConvertMachineDialog.Cancel")); // NOI18N
        jGTIButtonCancel.setToolTipText(bundle.getString("ConvertMachineDialog.CancelToolTip")); // NOI18N
        jGTIButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonCancelActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 16, 16);
        getContentPane().add(jGTIButtonCancel, gridBagConstraints);

        jGTIButtonOk.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("ConvertMachineDialog.OkMnemonic").charAt(0));
        jGTIButtonOk.setText(bundle.getString("ConvertMachineDialog.Ok")); // NOI18N
        jGTIButtonOk.setToolTipText(bundle.getString("ConvertMachineDialog.OkToolTip")); // NOI18N
        jGTIButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonOkActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 16, 5);
        getContentPane().add(jGTIButtonOk, gridBagConstraints);

        jGTIButtonPrint.setText(bundle.getString("PrintDialog.Print")); // NOI18N
        jGTIButtonPrint.setToolTipText(bundle.getString("PrintDialog.PrintToolTip")); // NOI18N
        jGTIButtonPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonPrintActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 16, 5);
        getContentPane().add(jGTIButtonPrint, gridBagConstraints);

        setSize(new java.awt.Dimension(960, 600));
    }// </editor-fold>//GEN-END:initComponents

    private void jGTIToolBarToggleButtonAutoStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIToolBarToggleButtonAutoStepActionPerformed
        this.logic.handleAutoStep();
    }//GEN-LAST:event_jGTIToolBarToggleButtonAutoStepActionPerformed

    private void jGTIToolBarButtonEndStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIToolBarButtonEndStepActionPerformed
        this.logic.handleEndStep();
    }//GEN-LAST:event_jGTIToolBarButtonEndStepActionPerformed

    private void jGTIToolBarButtonBeginStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIToolBarButtonBeginStepActionPerformed
        this.logic.handleBeginStep();
    }//GEN-LAST:event_jGTIToolBarButtonBeginStepActionPerformed

    private void jGTIToolBarButtonStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIToolBarButtonStopActionPerformed
        this.logic.handleStop();
    }//GEN-LAST:event_jGTIToolBarButtonStopActionPerformed

    private void jGTIToolBarButtonPreviousStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIToolBarButtonPreviousStepActionPerformed
        this.logic.handlePreviousStep();
    }//GEN-LAST:event_jGTIToolBarButtonPreviousStepActionPerformed

    private void jGTIToolBarButtonNextStepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIToolBarButtonNextStepActionPerformed
        this.logic.handleNextStep();
    }//GEN-LAST:event_jGTIToolBarButtonNextStepActionPerformed

    private void jGTIButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonOkActionPerformed
      this.logic.handleOk();
    }//GEN-LAST:event_jGTIButtonOkActionPerformed

    private void jGTIButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonCancelActionPerformed
      this.logic.handleCancel();
    }//GEN-LAST:event_jGTIButtonCancelActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
      this.logic.handleCancel();
    }//GEN-LAST:event_formWindowClosing

private void jGTIButtonPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonPrintActionPerformed
    this.logic.handlePrint();
}//GEN-LAST:event_jGTIButtonPrintActionPerformed
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonCancel;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonOk;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonPrint;
    public de.unisiegen.gtitool.ui.swing.JGTICheckBox jGTICheckBoxEntityProductions;
    public de.unisiegen.gtitool.ui.swing.JGTICheckBox jGTICheckBoxEpsilonProductions;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelConverted;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelNonterminalSymbolsConverted;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelNonterminalSymbolsOriginal;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelOriginal;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelStartSymbolConverted;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelStartSymbolOriginal;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelTerminalSymbolsConverted;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelTerminalSymbolsOriginal;
    public de.unisiegen.gtitool.ui.swing.JGTIList jGTIListNonterminalsConverted;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelConverted;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelGrammarConverted;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelGrammarOriginal;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelOriginal;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelPreferences;
    public de.unisiegen.gtitool.ui.swing.JGTIScrollPane jGTIScrollPaneGrammarConverted;
    public de.unisiegen.gtitool.ui.swing.JGTIScrollPane jGTIScrollPaneGrammarOriginal;
    public de.unisiegen.gtitool.ui.swing.JGTIScrollPane jGTIScrollPaneOutline;
    public de.unisiegen.gtitool.ui.swing.JGTISplitPane jGTISplitPaneGraph;
    public de.unisiegen.gtitool.ui.swing.JGTISplitPane jGTISplitPaneOutline;
    public de.unisiegen.gtitool.ui.swing.JGTITable jGTITableGrammarConverted;
    public de.unisiegen.gtitool.ui.swing.JGTITable jGTITableGrammarOriginal;
    public de.unisiegen.gtitool.ui.swing.JGTITable jGTITableOutline;
    public de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton jGTIToolBarButtonBeginStep;
    public de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton jGTIToolBarButtonEndStep;
    public de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton jGTIToolBarButtonNextStep;
    public de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton jGTIToolBarButtonPreviousStep;
    public de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarButton jGTIToolBarButtonStop;
    public de.unisiegen.gtitool.ui.swing.JGTIToolBar jGTIToolBarMain;
    public de.unisiegen.gtitool.ui.swing.specialized.JGTIToolBarToggleButton jGTIToolBarToggleButtonAutoStep;
    public javax.swing.JScrollPane jScrollPaneNonterminalsConverted;
    public de.unisiegen.gtitool.ui.style.StyledNonterminalSymbolSetParserPanel styledNonterminalSymbolSetParserPanelConverted;
    public de.unisiegen.gtitool.ui.style.StyledNonterminalSymbolSetParserPanel styledNonterminalSymbolSetParserPanelOriginal;
    public de.unisiegen.gtitool.ui.style.StyledStartNonterminalSymbolParserPanel styledStartNonterminalSymbolParserPanelConverted;
    public de.unisiegen.gtitool.ui.style.StyledStartNonterminalSymbolParserPanel styledStartNonterminalSymbolParserPanelOriginal;
    public de.unisiegen.gtitool.ui.style.StyledTerminalSymbolSetParserPanel styledTerminalSymbolSetParserPanelConverted;
    public de.unisiegen.gtitool.ui.style.StyledTerminalSymbolSetParserPanel styledTerminalSymbolSetParserPanelOriginal;
    // End of variables declaration//GEN-END:variables

    /**
     * TODO
     *
     * @return
     * @see de.unisiegen.gtitool.ui.netbeans.interfaces.GUIClass#getLogic()
     */
    public ConvertGrammarDialog getLogic ()
    {
      return this.logic;
    }
    
}
