package de.unisiegen.gtitool.ui.logic;


import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;

import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.netbeans.AboutDialogForm;
import de.unisiegen.gtitool.ui.netbeans.PreferencesDialogForm;
import de.unisiegen.gtitool.ui.preferences.ColorItem;
import de.unisiegen.gtitool.ui.preferences.LanguageItem;
import de.unisiegen.gtitool.ui.preferences.LookAndFeelItem;
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
   * The language {@link ComboBoxModel}.
   * 
   * @author Christian Fehler
   */
  protected class LanguageComboBoxModel extends DefaultComboBoxModel
  {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 2992377445617042603L;


    /**
     * Adds the given item.
     * 
     * @param pLanguageItem The {@link LanguageItem} to add.
     */
    public void addElement ( LanguageItem pLanguageItem )
    {
      super.addElement ( pLanguageItem );
    }


    /**
     * {@inheritDoc}
     * 
     * @see DefaultComboBoxModel#addElement(Object)
     */
    @Override
    public void addElement ( @SuppressWarnings ( "unused" )
    Object pObject )
    {
      throw new IllegalArgumentException ( "do not use this method" ); //$NON-NLS-1$
    }


    /**
     * {@inheritDoc}
     * 
     * @see DefaultComboBoxModel#getElementAt(int)
     */
    @Override
    public LanguageItem getElementAt ( int pIndex )
    {
      return ( LanguageItem ) super.getElementAt ( pIndex );
    }


    /**
     * {@inheritDoc}
     * 
     * @see DefaultComboBoxModel#getSelectedItem()
     */
    @Override
    public LanguageItem getSelectedItem ()
    {
      return ( LanguageItem ) super.getSelectedItem ();
    }
  }


  /**
   * The look and feel {@link ComboBoxModel}.
   * 
   * @author Christian Fehler
   */
  protected class LookAndFeelComboBoxModel extends DefaultComboBoxModel
  {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = 5754474473687367844L;


    /**
     * Adds the given {@link LookAndFeelItem}.
     * 
     * @param pLookAndFeelItem The {@link LookAndFeelItem} to add.
     */
    public void addElement ( LookAndFeelItem pLookAndFeelItem )
    {
      super.addElement ( pLookAndFeelItem );
    }


    /**
     * {@inheritDoc}
     * 
     * @see DefaultComboBoxModel#addElement(Object)
     */
    @Override
    public void addElement ( @SuppressWarnings ( "unused" )
    Object pObject )
    {
      throw new IllegalArgumentException ( "do not use this method" ); //$NON-NLS-1$
    }


    /**
     * {@inheritDoc}
     * 
     * @see DefaultComboBoxModel#getElementAt(int)
     */
    @Override
    public LookAndFeelItem getElementAt ( int pIndex )
    {
      return ( LookAndFeelItem ) super.getElementAt ( pIndex );
    }


    /**
     * {@inheritDoc}
     * 
     * @see DefaultComboBoxModel#getSelectedItem()
     */
    @Override
    public LookAndFeelItem getSelectedItem ()
    {
      return ( LookAndFeelItem ) super.getSelectedItem ();
    }
  }


  /**
   * The {@link AboutDialogForm}.
   */
  private PreferencesDialogForm gui;


  /**
   * The parent {@link JFrame}.
   */
  private JFrame parent;


  /**
   * The {@link LanguageComboBoxModel}.
   */
  private LanguageComboBoxModel languageComboBoxModel;


  /**
   * The {@link DefaultComboBoxModel} of the languages.
   */
  private LookAndFeelComboBoxModel lookAndFeelComboBoxModel;


  /**
   * The {@link ColorListModel}.
   */
  private ColorListModel colorListModel;


  /**
   * The {@link ColorItemCellRenderer}.
   */
  private ColorItemCellRenderer colorItemCellRenderer;


  /**
   * Creates a new <code>PreferencesDialog</code>.
   * 
   * @param pParent The parent {@link JFrame}.
   */
  public PreferencesDialog ( JFrame pParent )
  {
    this.parent = pParent;
    this.gui = new PreferencesDialogForm ( this, pParent );

    /*
     * Languages
     */
    this.languageComboBoxModel = new LanguageComboBoxModel ();
    ResourceBundle languageBundle = ResourceBundle
        .getBundle ( "de.unisiegen.gtitool.ui.languages" ); //$NON-NLS-1$
    this.languageComboBoxModel
        .addElement ( new LanguageItem ( "Default", null ) ); //$NON-NLS-1$
    try
    {
      int index = 0;
      while ( true )
      {
        String title = languageBundle
            .getString ( "Language" + index + ".Title" ); //$NON-NLS-1$ //$NON-NLS-2$
        String language = languageBundle
            .getString ( "Language" + index + ".Language" ); //$NON-NLS-1$//$NON-NLS-2$
        this.languageComboBoxModel.addElement ( new LanguageItem ( title,
            language ) );
        index++ ;
      }
    }
    catch ( MissingResourceException e )
    {
      // Do nothing
    }
    this.gui.jComboBoxLanguage.setModel ( this.languageComboBoxModel );
    this.gui.jComboBoxLanguage.setSelectedItem ( PreferenceManager
        .getInstance ().getLanguageItem () );

    /*
     * Look and feel
     */
    this.lookAndFeelComboBoxModel = new LookAndFeelComboBoxModel ();

    this.lookAndFeelComboBoxModel.addElement ( new LookAndFeelItem ( "System", //$NON-NLS-1$
        UIManager.getSystemLookAndFeelClassName () ) );

    LookAndFeelInfo [] lookAndFeels = UIManager.getInstalledLookAndFeels ();
    for ( LookAndFeelInfo current : lookAndFeels )
    {
      this.lookAndFeelComboBoxModel.addElement ( new LookAndFeelItem ( current
          .getName (), current.getClassName () ) );
    }
    this.gui.jComboBoxLookAndFeel.setModel ( this.lookAndFeelComboBoxModel );
    this.gui.jComboBoxLookAndFeel.setSelectedItem ( PreferenceManager
        .getInstance ().getLookAndFeelItem () );

    /*
     * Color
     */
    this.colorItemCellRenderer = new ColorItemCellRenderer ();
    this.gui.jListColor.setCellRenderer ( this.colorItemCellRenderer );
    this.colorListModel = new ColorListModel ();
    this.colorListModel.add ( this.colorItemState );
    this.colorListModel.add ( this.colorItemSelectedState );
    this.gui.jListColor.setModel ( this.colorListModel );
    this.gui.jTabbedPane.setSelectedIndex ( PreferenceManager.getInstance ()
        .getPreferencesDialogLastActiveTab () );
  }


  /**
   * The {@link ColorItem} state.
   */
  private ColorItem colorItemState = PreferenceManager.getInstance ()
      .getColorItem ( "State", Color.GREEN ); //$NON-NLS-1$


  /**
   * The {@link ColorItem} selected state.
   */
  private ColorItem colorItemSelectedState = PreferenceManager.getInstance ()
      .getColorItem ( "SelectedState", Color.RED ); //$NON-NLS-1$


  /**
   * Handles the action on the <code>Accept</code> button.
   */
  public void handleAccept ()
  {
    saveData ();
  }


  /**
   * Handles the action on the <code>Cancel</code> button.
   */
  public void handleCancel ()
  {
    this.gui.setVisible ( false );
    PreferenceManager.getInstance ().setPreferencesDialogLastActiveTab (
        this.gui.jTabbedPane.getSelectedIndex () );
    this.gui.dispose ();
  }


  /**
   * Handles the {@link MouseEvent} on the color list.
   * 
   * @param pEvent The {@link MouseEvent}.
   */
  public void handleColorListMouseClicked ( MouseEvent pEvent )
  {
    if ( pEvent.getClickCount () >= 2 )
    {
      ColorItem colorItem = ( ColorItem ) this.gui.jListColor
          .getSelectedValue ();
      if ( colorItem != null )
      {

        Color color = JColorChooser.showDialog ( this.gui, Messages
            .getString ( "PreferencesDialog.ColorChooser.Title" ), //$NON-NLS-1$
            colorItem.getColor () );

        if ( color != null )
        {
          if ( colorItem.equals ( this.colorItemState ) )
          {
            this.colorItemState.setColor ( color );
          }
          if ( colorItem.equals ( this.colorItemSelectedState ) )
          {
            this.colorItemSelectedState.setColor ( color );
          }
          // PreferenceManager.getInstance ().setPreferencesDialogColor (
          // colorItem );
          this.gui.jListColor.repaint ();
        }
      }
    }
  }


  /**
   * Handles the action on the <code>OK</code> button.
   */
  public void handleOk ()
  {
    this.gui.setVisible ( false );
    saveData ();
    this.gui.dispose ();
  }


  /**
   * Saves the data
   */
  private void saveData ()
  {
    /*
     * Last active tab
     */
    PreferenceManager.getInstance ().setPreferencesDialogLastActiveTab (
        this.gui.jTabbedPane.getSelectedIndex () );

    /*
     * Languages
     */
    PreferenceManager.getInstance ().setLanguageItem (
        this.languageComboBoxModel.getSelectedItem () );
    // TODOChristian Implement the language listener

    /*
     * Look and feel
     */
    LookAndFeelItem lookAndFeelItem = this.lookAndFeelComboBoxModel
        .getSelectedItem ();

    PreferenceManager.getInstance ().setLookAndFeelItem (
        this.lookAndFeelComboBoxModel.getSelectedItem () );
    try
    {
      UIManager.setLookAndFeel ( lookAndFeelItem.getClassName () );
      SwingUtilities.updateComponentTreeUI ( this.parent );
      for ( Frame current : Frame.getFrames () )
      {
        SwingUtilities.updateComponentTreeUI ( current );
        for ( Window w : current.getOwnedWindows () )
          SwingUtilities.updateComponentTreeUI ( w );
      }
    }
    catch ( ClassNotFoundException e )
    {
      e.printStackTrace ();
    }
    catch ( InstantiationException e )
    {
      e.printStackTrace ();
    }
    catch ( IllegalAccessException e )
    {
      e.printStackTrace ();
    }
    catch ( UnsupportedLookAndFeelException e )
    {
      e.printStackTrace ();
    }

    /*
     * Color
     */
    PreferenceManager.getInstance ().setColorItem ( this.colorItemState );
    PreferenceManager.getInstance ()
        .setColorItem ( this.colorItemSelectedState );
    // TODOChristian Implement the color item listener
  }


  /**
   * Shows the {@link AboutDialogForm}.
   */
  public void show ()
  {
    int x = this.parent.getBounds ().x + ( this.parent.getWidth () / 2 )
        - ( this.gui.getWidth () / 2 );
    int y = this.parent.getBounds ().y + ( this.parent.getHeight () / 2 )
        - ( this.gui.getHeight () / 2 );
    this.gui.setBounds ( x, y, this.gui.getWidth (), this.gui.getHeight () );
    this.gui.setVisible ( true );
  }
}
