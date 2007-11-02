package de.unisiegen.gtitool.ui.logic;
import javax.swing.JFrame;

import de.unisiegen.gtitool.ui.EditorPanel;
import de.unisiegen.gtitool.ui.netbeans.AboutDialogForm;
import de.unisiegen.gtitool.ui.netbeans.NewDialogForm;


/**
 * The <code>NewDialogg</code>.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public class NewDialog
{

  /**
   * The {@link NewDialogForm}.
   */
  private NewDialogForm newDialogForm;


  /**
   * The parent {@link JFrame}.
   */
  private JFrame parent;


  /**
   * Creates a new <code>NewDialog</code>.
   * 
   * @param pParent The parent {@link JFrame}.
   */
  public NewDialog ( JFrame pParent )
  {
    this.parent = pParent;
    this.newDialogForm = new NewDialogForm ( pParent, true );
    this.newDialogForm.setLogic ( this );
  }


  /**
   * Shows the {@link AboutDialogForm}.
   */
  public void show ()
  {
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( this.newDialogForm.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( this.newDialogForm.getHeight () / 2 );
    this.newDialogForm.setBounds ( x, y, this.newDialogForm.getWidth (),
        this.newDialogForm.getHeight () );
    this.newDialogForm.setVisible ( true );
  }
  
  public EditorPanel getEditorPanel ()
  {
    if (this.newDialogForm.isCanceled ())
      return null;
    if (this.newDialogForm.tabbedPane.getSelectedComponent () == this.newDialogForm.machinesPanel)
      return new MachinePanel(this.parent, newDialogForm.alphabetPanel);
    return new GrammarPanel(newDialogForm.alphabetPanel);
  }
}
