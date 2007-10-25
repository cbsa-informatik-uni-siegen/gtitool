package de.unisiegen.gtitool.ui.logic;


import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.AbstractListModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.netbeans.AboutDialogForm;
import de.unisiegen.gtitool.ui.netbeans.PreferencesDialogForm;
import de.unisiegen.gtitool.ui.preferences.ColorItem;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;


/**
 * The <code>PreferencesDialog</code>.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public class PreferencesDialog
{

  /**
   * The color {@link ListCellRenderer}.
   * 
   * @author Christian Fehler
   */
  protected class ColorItemCellRenderer extends DefaultListCellRenderer
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
    public Component getListCellRendererComponent ( JList list, Object value,
        int index, boolean isSelected, boolean cellHasFocus )
    {
      JLabel label = ( JLabel ) super.getListCellRendererComponent ( list,
          value, index, isSelected, cellHasFocus );
      ColorItem colorItem = ( ColorItem ) value;
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
  protected class ColorListModel extends AbstractListModel
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
    public void add ( ColorItem pItem )
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
    public Object getElementAt ( int pIndex )
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
    public int getSize ()
    {
      return this.list.size ();
    }
  }


  /**
   * The {@link AboutDialogForm}.
   */
  private PreferencesDialogForm preferencesDialogForm;


  /**
   * The parent {@link JFrame}.
   */
  private JFrame parent;


  /**
   * The {@link ColorListModel}.
   */
  private ColorListModel colorListModel;


  /**
   * The {@link ColorListModel}.
   */
  private DefaultComboBoxModel languageComboBoxModel;


  /**
   * The {@link ColorItemCellRenderer}.
   */
  private ColorItemCellRenderer colorItemCellRenderer;


  /**
   * Creates a new <code>AboutDialog</code>.
   * 
   * @param pParent The parent {@link JFrame}.
   */
  public PreferencesDialog ( JFrame pParent )
  {
    this.parent = pParent;
    this.preferencesDialogForm = new PreferencesDialogForm ( this, pParent );
    this.colorItemCellRenderer = new ColorItemCellRenderer ();
    this.preferencesDialogForm.jListColor
        .setCellRenderer ( this.colorItemCellRenderer );
    this.colorListModel = new ColorListModel ();
    this.colorListModel.add ( PreferenceManager.getInstance ()
        .getPreferencesDialogColor ( "State", Color.GREEN ) ); //$NON-NLS-1$
    this.colorListModel.add ( PreferenceManager.getInstance ()
        .getPreferencesDialogColor ( "SelectedState", Color.RED ) ); //$NON-NLS-1$   
    this.preferencesDialogForm.jListColor.setModel ( this.colorListModel );
    this.preferencesDialogForm.jTabbedPane.setSelectedIndex ( PreferenceManager
        .getInstance ().getPreferencesDialogLastActiveTab () );
    // Languages
    this.languageComboBoxModel = new DefaultComboBoxModel ();
    ResourceBundle languageBundle = ResourceBundle
        .getBundle ( "de.unisiegen.gtitool.ui.languages" ); //$NON-NLS-1$
    this.languageComboBoxModel.addElement ( languageBundle
        .getString ( "DefaultLanguage" ) ); //$NON-NLS-1$
    try
    {
      int index = 0;
      while ( true )
      {
        String title = languageBundle
            .getString ( "Language" + index + ".Title" ); //$NON-NLS-1$ //$NON-NLS-2$
        String language = languageBundle
            .getString ( "Language" + index + ".Language" ); //$NON-NLS-1$//$NON-NLS-2$
        this.languageComboBoxModel.addElement ( title + " (" + language + ")" ); //$NON-NLS-1$ //$NON-NLS-2$
        index++ ;
      }
    }
    catch ( Exception e )
    {
      // Do nothing
    }
    this.preferencesDialogForm.jComboBoxLanguage
        .setModel ( this.languageComboBoxModel );
    
    // Look and feel
    
    
  }


  /**
   * Closes the {@link AboutDialogForm}.
   */
  public void close ()
  {
    this.preferencesDialogForm.setVisible ( false );
    PreferenceManager.getInstance ().setPreferencesDialogLastActiveTab (
        this.preferencesDialogForm.jTabbedPane.getSelectedIndex () );
    this.preferencesDialogForm.dispose ();
  }


  /**
   * Handles the {@link MouseEvent} on the color list.
   * 
   * @param pEvent The {@link MouseEvent}.
   */
  public void handleColorListMouseClicked ( MouseEvent pEvent )
  {
    if ( pEvent.getClickCount () == 2 )
    {
      ColorItem colorItem = ( ColorItem ) this.preferencesDialogForm.jListColor
          .getSelectedValue ();
      if ( colorItem != null )
      {
        Color color = JColorChooser.showDialog ( this.preferencesDialogForm,
            Messages.getString ( "PreferencesDialog.ColorChooser.Title" ), //$NON-NLS-1$
            colorItem.getColor () );
        if ( color != null )
        {
          colorItem.setColor ( color );
          PreferenceManager.getInstance ().setPreferencesDialogColor (
              colorItem );
          this.preferencesDialogForm.jListColor.repaint ();
        }
      }
    }
  }


  /**
   * Shows the {@link AboutDialogForm}.
   */
  public void show ()
  {
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( this.preferencesDialogForm.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( this.preferencesDialogForm.getHeight () / 2 );
    this.preferencesDialogForm.setBounds ( x, y, this.preferencesDialogForm
        .getWidth (), this.preferencesDialogForm.getHeight () );
    this.preferencesDialogForm.setVisible ( true );
  }
}
