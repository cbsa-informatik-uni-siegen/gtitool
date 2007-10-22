package de.unisiegen.gtitool.ui.logic;


import java.awt.Component;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;

import de.unisiegen.gtitool.ui.ColorItem;
import de.unisiegen.gtitool.ui.PreferenceManager;
import de.unisiegen.gtitool.ui.netbeans.AboutDialogForm;
import de.unisiegen.gtitool.ui.netbeans.PreferencesDialogForm;


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
      ColorItem item = ( ColorItem ) value;
      label.setIcon ( item.getIcon () );
      label.setText ( item.getCaption () );
      return label;
    }
  }


  /**
   * The color {@link ListModel}.
   * 
   * @author Christian Fehler
   * @param <E> The item.
   */
  protected class ColorListModel < E > extends AbstractListModel
  {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = -4310954235069928120L;


    /**
     * The item list.
     */
    private ArrayList < E > list;


    /**
     * Allocates a new <code>ColorListModel</code>.
     */
    public ColorListModel ()
    {
      this.list = new ArrayList < E > ();
    }


    /**
     * Adds the given item.
     * 
     * @param pItem The item to add.
     */
    public void add ( E pItem )
    {
      this.list.add ( pItem );
      int index = this.list.size () - 1;
      fireIntervalAdded ( this, index, index );
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


    /**
     * Removes the given item.
     * 
     * @param pItem The item to remove.
     */
    public void remove ( E pItem )
    {
      int index = this.list.indexOf ( pItem );
      this.list.remove ( index );
      fireIntervalRemoved ( this, index, index );
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
  private ColorListModel < ColorItem > colorListModel;


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
    this.colorListModel = new ColorListModel < ColorItem > ();
    this.colorListModel
        .add ( PreferenceManager.getInstance ().getColorState () );
    this.preferencesDialogForm.jListColor.setModel ( this.colorListModel );
  }


  /**
   * Closes the {@link AboutDialogForm}.
   */
  public void close ()
  {
    this.preferencesDialogForm.dispose ();
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
