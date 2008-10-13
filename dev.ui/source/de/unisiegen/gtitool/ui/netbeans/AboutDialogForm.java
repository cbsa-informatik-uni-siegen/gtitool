package de.unisiegen.gtitool.ui.netbeans;

import javax.swing.JDialog;
import javax.swing.JFrame;

import de.unisiegen.gtitool.ui.logic.AboutDialog;
import de.unisiegen.gtitool.ui.netbeans.interfaces.GUIClass;


/**
 * The {@link AboutDialogForm}.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings({ "all" })
public class AboutDialogForm extends JDialog implements GUIClass <AboutDialog>
{

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 8264731535784921404L;

    /**
     * The {@link AboutDialog}.
     */
    private AboutDialog logic ;

    /**
     * Allocates a new {@link AboutDialogForm}.
     * 
     * @param logic The {@link AboutDialog}.
     * @param parent The parent {@link JFrame}.
     */
    public AboutDialogForm(AboutDialog logic, JFrame parent) {
        super(parent, true);
        this.logic = logic ;
        initComponents();
    }

    /**
     * {@inheritDoc}
     * 
     * @see GUIClass#getLogic()
     */
    public final AboutDialog getLogic ()
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

        jGTITabbedPaneMain = new de.unisiegen.gtitool.ui.swing.JGTITabbedPane();
        jGTIPanelMain = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTIPanelNorth = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTILabelName = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTILabelCopyright = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTISeparator = new de.unisiegen.gtitool.ui.swing.JGTISeparator();
        jGTIPanelSouth = new de.unisiegen.gtitool.ui.swing.JGTIPanel();
        jGTILabelWebpage = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTILabelWebpageEntry = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTILabelVersion = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTILabelVersionEntry = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTILabelDeveloper = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTILabelDeveloper0 = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTILabelDeveloper1 = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTILabelDeveloper2 = new de.unisiegen.gtitool.ui.swing.JGTILabel();
        jGTITabbedPaneLicenses = new de.unisiegen.gtitool.ui.swing.JGTITabbedPane();
        jGTIButtonClose = new de.unisiegen.gtitool.ui.swing.JGTIButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages"); // NOI18N
        setTitle(bundle.getString("AboutDialog.Title")); // NOI18N
        setModal(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jGTIPanelNorth.setBackground(new java.awt.Color(255, 255, 255));

        jGTILabelName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jGTILabelName.setText("GTI Tool ?.?.?.?");
        jGTILabelName.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jGTILabelName.setFont(new java.awt.Font("Dialog", 1, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 5, 0);
        jGTIPanelNorth.add(jGTILabelName, gridBagConstraints);

        jGTILabelCopyright.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jGTILabelCopyright.setText(bundle.getString("AboutDialog.Copyright")); // NOI18N
        jGTILabelCopyright.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 15, 0);
        jGTIPanelNorth.add(jGTILabelCopyright, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jGTIPanelMain.add(jGTIPanelNorth, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jGTIPanelMain.add(jGTISeparator, gridBagConstraints);

        jGTILabelWebpage.setText(bundle.getString("AboutDialog.Webpage")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 16, 5, 10);
        jGTIPanelSouth.add(jGTILabelWebpage, gridBagConstraints);

        jGTILabelWebpageEntry.setForeground(java.awt.Color.blue);
        jGTILabelWebpageEntry.setText("http://theoinf.math.uni-siegen.de/gtitool");
        jGTILabelWebpageEntry.setToolTipText(bundle.getString("AboutDialog.WebpageEntryToolTip")); // NOI18N
        jGTILabelWebpageEntry.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jGTILabelWebpageEntryMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 5, 10);
        jGTIPanelSouth.add(jGTILabelWebpageEntry, gridBagConstraints);

        jGTILabelVersion.setText(bundle.getString("AboutDialog.Version")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 10);
        jGTIPanelSouth.add(jGTILabelVersion, gridBagConstraints);

        jGTILabelVersionEntry.setText("?.?.?.?_????");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        jGTIPanelSouth.add(jGTILabelVersionEntry, gridBagConstraints);

        jGTILabelDeveloper.setText(bundle.getString("AboutDialog.Developer")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 5, 10);
        jGTIPanelSouth.add(jGTILabelDeveloper, gridBagConstraints);

        jGTILabelDeveloper0.setText("Christian Fehler");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        jGTIPanelSouth.add(jGTILabelDeveloper0, gridBagConstraints);

        jGTILabelDeveloper1.setText("Benjamin Mies");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        jGTIPanelSouth.add(jGTILabelDeveloper1, gridBagConstraints);

        jGTILabelDeveloper2.setText("Simon Meurer");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 10);
        jGTIPanelSouth.add(jGTILabelDeveloper2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 5, 0);
        jGTIPanelMain.add(jGTIPanelSouth, gridBagConstraints);

        jGTITabbedPaneMain.addTab(bundle.getString("AboutDialog.General"), jGTIPanelMain); // NOI18N
        jGTITabbedPaneMain.addTab(bundle.getString("AboutDialog.Licenses"), jGTITabbedPaneLicenses); // NOI18N

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        getContentPane().add(jGTITabbedPaneMain, gridBagConstraints);
        jGTITabbedPaneMain.getAccessibleContext().setAccessibleName("");

        jGTIButtonClose.setMnemonic(java.util.ResourceBundle.getBundle("de/unisiegen/gtitool/ui/i18n/messages").getString("AboutDialog.CloseMnemonic").charAt(0));
        jGTIButtonClose.setText(bundle.getString("AboutDialog.Close")); // NOI18N
        jGTIButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jGTIButtonCloseActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 16, 16, 16);
        getContentPane().add(jGTIButtonClose, gridBagConstraints);

        setSize(new java.awt.Dimension(560, 350));
    }// </editor-fold>//GEN-END:initComponents

    private void jGTILabelWebpageEntryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jGTILabelWebpageEntryMouseClicked
      this.logic.handleWebpageEntry();
    }//GEN-LAST:event_jGTILabelWebpageEntryMouseClicked

    private void jGTIButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jGTIButtonCloseActionPerformed
      this.logic.handleClose();
    }//GEN-LAST:event_jGTIButtonCloseActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
      this.logic.handleClose();
    }//GEN-LAST:event_formWindowClosing
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public de.unisiegen.gtitool.ui.swing.JGTIButton jGTIButtonClose;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelCopyright;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelDeveloper;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelDeveloper0;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelDeveloper1;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelDeveloper2;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelName;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelVersion;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelVersionEntry;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelWebpage;
    public de.unisiegen.gtitool.ui.swing.JGTILabel jGTILabelWebpageEntry;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelMain;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelNorth;
    public de.unisiegen.gtitool.ui.swing.JGTIPanel jGTIPanelSouth;
    public de.unisiegen.gtitool.ui.swing.JGTISeparator jGTISeparator;
    public de.unisiegen.gtitool.ui.swing.JGTITabbedPane jGTITabbedPaneLicenses;
    public de.unisiegen.gtitool.ui.swing.JGTITabbedPane jGTITabbedPaneMain;
    // End of variables declaration//GEN-END:variables
    
}