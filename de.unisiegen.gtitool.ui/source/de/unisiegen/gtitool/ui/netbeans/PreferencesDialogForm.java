package de.unisiegen.gtitool.ui.netbeans;

import javax.swing.JDialog;
import javax.swing.JFrame;

import de.unisiegen.gtitool.ui.logic.PreferencesDialog;
import de.unisiegen.gtitool.ui.netbeans.interfaces.GUIClass;


/**
 * The {@link PreferencesDialogForm}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings({ "all" })
public class PreferencesDialogForm extends JDialog implements GUIClass <PreferencesDialog>
{
    
    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = -8194161182214638312L;
    
    /**
     * The {@link PreferencesDialog}.
     */
    private PreferencesDialog logic ;
    
    /**
     * Allocates a new {@link PreferencesDialogForm}
     * 
     * @param logic The {@link PreferencesDialog}.
     * @param parent The parent {@link JFrame}.
     */
    public PreferencesDialogForm(PreferencesDialog logic, JFrame parent) {
        super(parent, true);
        this.logic = logic;
        initComponents();
    }
    
    /**
     * {@inheritDoc}
     * 
     * @see GUIClass#getLogic()
     */
    public final PreferencesDialog getLogic ()
    {
      return this.logic;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jGTITabbedPane = new de.unisiegen.gtitool.ui.swing.JGTITabbedPane();
        jGTIPanelGeneral = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTILabelLanguage = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTIComboBoxLanguage = new de.unisiegen.gtitool.ui.swing.JGTIComboBox();
        jGTILabelLookAndFeel = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTIComboBoxLookAndFeel = new de.unisiegen.gtitool.ui.swing.JGTIComboBox();
        jGTILabelWordMode = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTIComboBoxWordMode = new de.unisiegen.gtitool.ui.swing.JGTIComboBox();
        jGTILabelZoom = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTISliderZoom = new de.unisiegen.gtitool.ui.swing.JGTISlider();
        jGTIPanelGeneralColumn0 = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIPanelGeneralColumn1 = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTICheckBoxShowErrorState = new de.unisiegen.gtitool.ui.swing.JGTICheckBox();
        jGTIPanelView = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTILabelTransition = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTIComboBoxTransition = new de.unisiegen.gtitool.ui.swing.JGTIComboBox();
        jGTILabelMouseSelection = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTIComboBoxMouseSelection = new de.unisiegen.gtitool.ui.swing.JGTIComboBox();
        jGTILabelPDAMode = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTIComboBoxPDAMode = new de.unisiegen.gtitool.ui.swing.JGTIComboBox();
        jGTILabelAutoStep = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTISliderAutoStep = new de.unisiegen.gtitool.ui.swing.JGTISlider();
        jGTIPanelViewColumn0 = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIPanelViewColumn1 = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIPanelColor = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIScrollPaneColors = new de.unisiegen.gtitool.ui.swing.JGTIScrollPane();
        jGTITreeColors = new de.unisiegen.gtitool.ui.swing.JGTITree();
        jGTIPanelAlphabet = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        alphabetPanelForm = new de.unisiegen.gtitool.ui.netbeans.AlphabetPanelForm();
        jGTIPanelGrammar = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        terminalPanelForm = new de.unisiegen.gtitool.ui.netbeans.TerminalPanelForm();
        jGTIButtonRestore = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIButtonOk = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIButtonAccept = new de.unisiegen.gtitool.ui.swing.JGTIButton();
        jGTIButtonCancel = new de.unisiegen.gtitool.ui.swing.JGTIButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages"); // NOI18N
        setTitle(bundle.getString("PreferencesDialog.Title")); // NOI18N
        setModal(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jGTILabelLanguage.setDisplayedMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("PreferencesDialog.LanguageMnemonic").charAt(0));
        jGTILabelLanguage.setLabelFor(jGTIComboBoxLanguage);
        jGTILabelLanguage.setText(bundle.getString("PreferencesDialog.Language")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(16, 16, 5, 5);
        jGTIPanelGeneral.add(jGTILabelLanguage, gridBagConstraints);

        jGTIComboBoxLanguage.setToolTipText(bundle.getString("PreferencesDialog.LanguageToolTip")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(16, 5, 5, 16);
        jGTIPanelGeneral.add(jGTIComboBoxLanguage, gridBagConstraints);

        jGTILabelLookAndFeel.setDisplayedMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("PreferencesDialog.LookAndFeelMnemonic").charAt(0));
        jGTILabelLookAndFeel.setLabelFor(jGTIComboBoxLookAndFeel);
        jGTILabelLookAndFeel.setText(bundle.getString("PreferencesDialog.LookAndFeel")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 5);
        jGTIPanelGeneral.add(jGTILabelLookAndFeel, gridBagConstraints);

        jGTIComboBoxLookAndFeel.setToolTipText(bundle.getString("PreferencesDialog.LookAndFeelToolTip")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 16);
        jGTIPanelGeneral.add(jGTIComboBoxLookAndFeel, gridBagConstraints);

        jGTILabelWordMode.setDisplayedMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("PreferencesDialog.WordModeMnemonic").charAt(0));
        jGTILabelWordMode.setLabelFor(jGTIComboBoxWordMode);
        jGTILabelWordMode.setText(bundle.getString("PreferencesDialog.WordMode")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 5);
        jGTIPanelGeneral.add(jGTILabelWordMode, gridBagConstraints);

        jGTIComboBoxWordMode.setToolTipText(bundle.getString("PreferencesDialog.WordModeToolTip")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 16);
        jGTIPanelGeneral.add(jGTIComboBoxWordMode, gridBagConstraints);

        jGTILabelZoom.setDisplayedMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("PreferencesDialog.ZoomMnemonic").charAt(0));
        jGTILabelZoom.setLabelFor(jGTISliderAutoStep);
        jGTILabelZoom.setText(bundle.getString("PreferencesDialog.Zoom")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 5);
        jGTIPanelGeneral.add(jGTILabelZoom, gridBagConstraints);

        jGTISliderZoom.setMajorTickSpacing(50);
        jGTISliderZoom.setMaximum(150);
        jGTISliderZoom.setMinimum(50);
        jGTISliderZoom.setMinorTickSpacing(50);
        jGTISliderZoom.setToolTipText(bundle.getString("PreferencesDialog.ZoomToolTip")); // NOI18N
        jGTISliderZoom.setValue(100);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 16);
        jGTIPanelGeneral.add(jGTISliderZoom, gridBagConstraints);

        jGTIPanelGeneralColumn0.setMinimumSize(new java.awt.Dimension(200, 0));
        jGTIPanelGeneralColumn0.setPreferredSize(new java.awt.Dimension(200, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jGTIPanelGeneral.add(jGTIPanelGeneralColumn0, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jGTIPanelGeneral.add(jGTIPanelGeneralColumn1, gridBagConstraints);

        jGTICheckBoxShowErrorState.setText(bundle.getString("PreferencesDialog.MachineShowErrorStateToolTip")); // NOI18N
        jGTICheckBoxShowErrorState.setToolTipText(bundle.getString("PreferencesDialog.MachineShowErrorStateToolTip")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 16, 16);
        jGTIPanelGeneral.add(jGTICheckBoxShowErrorState, gridBagConstraints);

        jGTITabbedPane.addTab(bundle.getString("PreferencesDialog.TabGeneral"), null, jGTIPanelGeneral, bundle.getString("PreferencesDialog.TabGeneralToolTip")); // NOI18N

        jGTILabelTransition.setDisplayedMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("PreferencesDialog.TransitionMnemonic").charAt(0));
        jGTILabelTransition.setLabelFor(jGTIComboBoxTransition);
        jGTILabelTransition.setText(bundle.getString("PreferencesDialog.Transition")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(16, 16, 5, 5);
        jGTIPanelView.add(jGTILabelTransition, gridBagConstraints);

        jGTIComboBoxTransition.setToolTipText(bundle.getString("PreferencesDialog.TransitionToolTip")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(16, 5, 5, 16);
        jGTIPanelView.add(jGTIComboBoxTransition, gridBagConstraints);

        jGTILabelMouseSelection.setDisplayedMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("PreferencesDialog.MouseSelectionMnemonic").charAt(0));
        jGTILabelMouseSelection.setLabelFor(jGTIComboBoxMouseSelection);
        jGTILabelMouseSelection.setText(bundle.getString("PreferencesDialog.MouseSelection")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 5);
        jGTIPanelView.add(jGTILabelMouseSelection, gridBagConstraints);

        jGTIComboBoxMouseSelection.setToolTipText(bundle.getString("PreferencesDialog.MouseSelectionToolTip")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 16);
        jGTIPanelView.add(jGTIComboBoxMouseSelection, gridBagConstraints);

        jGTILabelPDAMode.setDisplayedMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("PreferencesDialog.PDAModeMnemonic").charAt(0));
        jGTILabelPDAMode.setLabelFor(jGTIComboBoxPDAMode);
        jGTILabelPDAMode.setText(bundle.getString("PreferencesDialog.PDAMode")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 5);
        jGTIPanelView.add(jGTILabelPDAMode, gridBagConstraints);

        jGTIComboBoxPDAMode.setToolTipText(bundle.getString("PreferencesDialog.PDAModeToolTip")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 16);
        jGTIPanelView.add(jGTIComboBoxPDAMode, gridBagConstraints);

        jGTILabelAutoStep.setDisplayedMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("PreferencesDialog.AutoStepMnemonic").charAt(0));
        jGTILabelAutoStep.setLabelFor(jGTISliderAutoStep);
        jGTILabelAutoStep.setText(bundle.getString("PreferencesDialog.AutoStep")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 16, 5);
        jGTIPanelView.add(jGTILabelAutoStep, gridBagConstraints);

        jGTISliderAutoStep.setMajorTickSpacing(1000);
        jGTISliderAutoStep.setMaximum(5000);
        jGTISliderAutoStep.setMinorTickSpacing(500);
        jGTISliderAutoStep.setToolTipText(bundle.getString("PreferencesDialog.AutoStepToolTip")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 16, 16);
        jGTIPanelView.add(jGTISliderAutoStep, gridBagConstraints);

        jGTIPanelViewColumn0.setMinimumSize(new java.awt.Dimension(200, 0));
        jGTIPanelViewColumn0.setPreferredSize(new java.awt.Dimension(200, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jGTIPanelView.add(jGTIPanelViewColumn0, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jGTIPanelView.add(jGTIPanelViewColumn1, gridBagConstraints);

        jGTITabbedPane.addTab(bundle.getString("PreferencesDialog.TabView"), null, jGTIPanelView, bundle.getString("PreferencesDialog.TabViewToolTip")); // NOI18N

        jGTITreeColors.setRootVisible(false);
        jGTITreeColors.setShowsRootHandles(true);
        jGTITreeColors.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jGTITreeColorsMouseReleased(evt);
            }
        });
        jGTIScrollPaneColors.setViewportView(jGTITreeColors);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(16, 16, 16, 16);
        jGTIPanelColor.add(jGTIScrollPaneColors, gridBagConstraints);

        jGTITabbedPane.addTab(bundle.getString("PreferencesDialog.TabColors"), null, jGTIPanelColor, bundle.getString("PreferencesDialog.TabColorsToolTip")); // NOI18N

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(16, 0, 16, 16);
        jGTIPanelAlphabet.add(alphabetPanelForm, gridBagConstraints);

        jGTITabbedPane.addTab(bundle.getString("PreferencesDialog.TabAlphabet"), null, jGTIPanelAlphabet, bundle.getString("PreferencesDialog.TabAlphabetToolTip")); // NOI18N

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(16, 0, 16, 16);
        jGTIPanelGrammar.add(terminalPanelForm, gridBagConstraints);

        jGTITabbedPane.addTab(bundle.getString("PreferencesDialog.TabGrammar"), null, jGTIPanelGrammar, bundle.getString("PreferencesDialog.TabGrammarToolTip")); // NOI18N

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jGTITabbedPane, gridBagConstraints);

        jGTIButtonRestore.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("PreferencesDialog.RestoreMnemonic").charAt(0));
        jGTIButtonRestore.setText(bundle.getString("PreferencesDialog.Restore")); // NOI18N
        jGTIButtonRestore.setToolTipText(bundle.getString("PreferencesDialog.RestoreToolTip")); // NOI18N
        jGTIButtonRestore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonRestoreActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 16, 16, 5);
        getContentPane().add(jGTIButtonRestore, gridBagConstraints);

        jGTIButtonOk.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("PreferencesDialog.OkMnemonic").charAt(0));
        jGTIButtonOk.setText(bundle.getString("PreferencesDialog.Ok")); // NOI18N
        jGTIButtonOk.setToolTipText(bundle.getString("PreferencesDialog.OkToolTip")); // NOI18N
        jGTIButtonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonOkActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 16, 5);
        getContentPane().add(jGTIButtonOk, gridBagConstraints);

        jGTIButtonAccept.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("PreferencesDialog.AcceptMnemonic").charAt(0));
        jGTIButtonAccept.setText(bundle.getString("PreferencesDialog.Accept")); // NOI18N
        jGTIButtonAccept.setToolTipText(bundle.getString("PreferencesDialog.AcceptToolTip")); // NOI18N
        jGTIButtonAccept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonAcceptActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 16, 5);
        getContentPane().add(jGTIButtonAccept, gridBagConstraints);

        jGTIButtonCancel.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("PreferencesDialog.CancelMnemonic").charAt(0));
        jGTIButtonCancel.setText(bundle.getString("PreferencesDialog.Cancel")); // NOI18N
        jGTIButtonCancel.setToolTipText(bundle.getString("PreferencesDialog.CancelToolTip")); // NOI18N
        jGTIButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonCancelActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 16, 16);
        getContentPane().add(jGTIButtonCancel, gridBagConstraints);

        setBounds(0, 0, 560, 350);
    }// </editor-fold>//GEN-END:initComponents

    private void jGTITreeColorsMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jGTITreeColorsMouseReleased
       this.logic.handleColorTreeMouseReleased(evt);
    }//GEN-LAST:event_jGTITreeColorsMouseReleased

    private void jGTIButtonRestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonRestoreActionPerformed
       this.logic.handleRestore();
    }//GEN-LAST:event_jGTIButtonRestoreActionPerformed

    private void jGTIButtonAcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonAcceptActionPerformed
       this.logic.handleAccept();
    }//GEN-LAST:event_jGTIButtonAcceptActionPerformed

    private void jGTIButtonOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonOkActionPerformed
       this.logic.handleOk();
    }//GEN-LAST:event_jGTIButtonOkActionPerformed

    private void jGTIButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonCancelActionPerformed
       this.logic.handleCancel();
    }//GEN-LAST:event_jGTIButtonCancelActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.logic.handleCancel();
    }//GEN-LAST:event_formWindowClosing
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public de.unisiegen.gtitool.ui.netbeans.AlphabetPanelForm alphabetPanelForm;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonAccept;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonCancel;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonOk;
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonRestore;
    public de.unisiegen.gtitool.ui.swing.JGTICheckBox jGTICheckBoxShowErrorState;
    public de.unisiegen.gtitool.ui.swing.JGTIComboBox jGTIComboBoxLanguage;
    public de.unisiegen.gtitool.ui.swing.JGTIComboBox jGTIComboBoxLookAndFeel;
    public de.unisiegen.gtitool.ui.swing.JGTIComboBox jGTIComboBoxMouseSelection;
    public de.unisiegen.gtitool.ui.swing.JGTIComboBox jGTIComboBoxPDAMode;
    public de.unisiegen.gtitool.ui.swing.JGTIComboBox jGTIComboBoxTransition;
    public de.unisiegen.gtitool.ui.swing.JGTIComboBox jGTIComboBoxWordMode;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelAutoStep;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelLanguage;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelLookAndFeel;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelMouseSelection;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelPDAMode;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelTransition;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelWordMode;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelZoom;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelAlphabet;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelColor;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelGeneral;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelGeneralColumn0;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelGeneralColumn1;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelGrammar;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelView;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelViewColumn0;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelViewColumn1;
    public de.unisiegen.gtitool.ui.swing.JGTIScrollPane jGTIScrollPaneColors;
    public de.unisiegen.gtitool.ui.swing.JGTISlider jGTISliderAutoStep;
    public de.unisiegen.gtitool.ui.swing.JGTISlider jGTISliderZoom;
    public de.unisiegen.gtitool.ui.swing.JGTITabbedPane jGTITabbedPane;
    public de.unisiegen.gtitool.ui.swing.JGTITree jGTITreeColors;
    public de.unisiegen.gtitool.ui.netbeans.TerminalPanelForm terminalPanelForm;
    // End of variables declaration//GEN-END:variables
    
}
