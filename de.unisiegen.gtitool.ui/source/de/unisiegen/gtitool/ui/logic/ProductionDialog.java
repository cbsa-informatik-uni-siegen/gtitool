package de.unisiegen.gtitool.ui.logic;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import javax.swing.AbstractListModel;
import javax.swing.JFrame;
import javax.swing.ListModel;

import de.unisiegen.gtitool.core.entities.DefaultProduction;
import de.unisiegen.gtitool.core.entities.NonterminalSymbol;
import de.unisiegen.gtitool.core.entities.NonterminalSymbolSet;
import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.ProductionWord;
import de.unisiegen.gtitool.core.entities.TerminalSymbolSet;
import de.unisiegen.gtitool.core.entities.listener.ProductionWordChangedListener;
import de.unisiegen.gtitool.ui.model.DefaultGrammarModel;
import de.unisiegen.gtitool.ui.netbeans.ProductionDialogForm;


/**
 * The logic class for the create new production dialog.
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public final class ProductionDialog
{

  /**
   * The nonterminal symbol {@link ListModel}.
   * 
   * @author Benjamin Mies
   */
  public final class SymbolListModel extends AbstractListModel implements
      Iterable < NonterminalSymbol >
  {

    /**
     * The serial version uid.
     */
    private static final long serialVersionUID = -3937680826576568992L;


    /**
     * The item list.
     */
    private ArrayList < NonterminalSymbol > list;


    /**
     * Allocates a new {@link SymbolListModel}.
     */
    public SymbolListModel ()
    {
      this.list = new ArrayList < NonterminalSymbol > ();
    }


    /**
     * Adds the given item.
     * 
     * @param pItem The item to add.
     */
    public final void add ( NonterminalSymbol pItem )
    {
      this.list.add ( pItem );
      Collections.sort ( this.list );
      fireContentsChanged ( this, 0, this.list.size () - 1 );
    }


    /**
     * Clears the model.
     */
    public final void clear ()
    {
      int size = this.list.size ();
      this.list.clear ();
      fireIntervalRemoved ( this, 0, size - 1 < 0 ? 0 : size - 1 );
    }


    /**
     * Returns the value at the specified index.
     * 
     * @param index The requested index.
     * @return The value at the specified index.
     * @see ListModel#getElementAt(int)
     */
    public final Object getElementAt ( int index )
    {
      if ( ( index < 0 ) || ( index >= this.list.size () ) )
      {
        throw new IllegalArgumentException ( "index incorrect" ); //$NON-NLS-1$
      }
      return this.list.get ( index );
    }


    /**
     * Returns the length of the list.
     * 
     * @return The length of the list.
     * @see ListModel#getSize()
     */
    public final int getSize ()
    {
      return this.list.size ();
    }


    /**
     * {@inheritDoc}
     * 
     * @see Iterable#iterator()
     */
    public final Iterator < NonterminalSymbol > iterator ()
    {
      return this.list.iterator ();
    }


    /**
     * Removes the item with the given index.
     * 
     * @param index The item index.
     */
    public final void remove ( int index )
    {
      this.list.remove ( index );
      fireIntervalRemoved ( this, index, index );
    }


    /**
     * Removes the given item.
     * 
     * @param pItem The item to remove.
     */
    public final void remove ( NonterminalSymbol pItem )
    {
      int index = this.list.indexOf ( pItem );
      this.list.remove ( pItem );
      fireIntervalRemoved ( this, index, index );
    }


    /**
     * Returns the list of {@link NonterminalSymbol}s.
     * 
     * @return the list of {@link NonterminalSymbol}s.
     */
    public ArrayList < NonterminalSymbol > getList ()
    {
      return this.list;
    }
  }


  /**
   * The {@link ProductionDialogForm}.
   */
  private ProductionDialogForm gui;


  /**
   * The parent frame.
   */
  private JFrame parent;


  /**
   * The {@link SymbolListModel} for teh nonterminal symbols
   */
  private SymbolListModel listModel = new SymbolListModel ();


  /**
   * The old production, if we are in configure mode.
   */
  private Production oldProduction;


  /**
   * The {@link DefaultGrammarModel}.
   */
  private DefaultGrammarModel model;


  /**
   * Creates a new {@link ProductionDialog}.
   * 
   * @param parent The parent frame.
   * @param nonterminalSymbolSet The {@link NonterminalSymbolSet}.
   * @param terminalSymbolSet The {@link TerminalSymbolSet}.
   * @param model The {@link DefaultGrammarModel}.
   * @param production The production to configure.
   */
  public ProductionDialog ( JFrame parent,
      NonterminalSymbolSet nonterminalSymbolSet,
      TerminalSymbolSet terminalSymbolSet, DefaultGrammarModel model,
      Production production )
  {
    this.parent = parent;
    this.gui = new ProductionDialogForm ( this, this.parent );
    this.model = model;
    this.oldProduction = production;

    this.gui.styledNonterminalSymbolSetParserPanel
        .setNonterminalSymbolSet ( nonterminalSymbolSet );
    this.gui.styledTerminalSymbolSetParserPanel
        .setTerminalSymbolSet ( terminalSymbolSet );

    this.gui.styledProductionWordParserPanel
        .setNonterminalSymbolSet ( nonterminalSymbolSet );
    this.gui.styledProductionWordParserPanel
        .setTerminalSymbolSet ( terminalSymbolSet );

    this.gui.styledProductionParserPanel
        .setNonterminalSymbolSet ( nonterminalSymbolSet );
    this.gui.styledProductionParserPanel
        .setTerminalSymbolSet ( terminalSymbolSet );

    for ( NonterminalSymbol current : nonterminalSymbolSet )
    {
      this.listModel.add ( current );
    }
    this.gui.jGTIList.setModel ( this.listModel );
    this.gui.jGTIList.setSelectedIndex ( 0 );

    this.gui.styledProductionWordParserPanel
        .addProductionWordChangedListener ( new ProductionWordChangedListener ()
        {

          public void productionWordChanged ( ProductionWord newProductionWord )
          {
            if ( newProductionWord == null )
            {
              setButtonStatus ( false );
              getGui ().styledProductionParserPanel.setProduction ( null );
            }

            else
            {
              setButtonStatus ( true );
              getGui ().styledProductionParserPanel
                  .setProduction ( new DefaultProduction (
                      ( NonterminalSymbol ) getGui ().jGTIList
                          .getSelectedValue (), newProductionWord ) );
            }
          }
        } );

    if ( this.oldProduction != null )
    {
      this.gui.styledProductionWordParserPanel
          .setProductionWord ( this.oldProduction.getProductionWord () );

      for ( NonterminalSymbol current : this.listModel.getList () )
      {
        if ( current.equals ( this.oldProduction.getNonterminalSymbol () ) )
        {
          this.gui.jGTIList.setSelectedIndex ( this.listModel.getList ()
              .indexOf ( current ) );
        }
      }
    }
    this.gui.jGTIButtonOk.setEnabled ( this.oldProduction != null );

  }


  /**
   * Set the ok button status.
   * 
   * @param status the new button status.
   */
  public void setButtonStatus ( boolean status )
  {
    this.gui.jGTIButtonOk.setEnabled ( status );
  }


  /**
   * Handle dialog canceled.
   */
  public void handleCancel ()
  {
    this.gui.dispose ();

  }


  /**
   * Handle dialog confirmed.
   */
  public void handleOk ()
  {
    Production production = new DefaultProduction (
        ( NonterminalSymbol ) this.gui.jGTIList.getSelectedValue (),
        this.gui.styledProductionWordParserPanel.getProductionWord () );

    if ( this.oldProduction != null )
    {
      this.oldProduction.setNonterminalSymbol ( production.getNonterminalSymbol () );
      this.oldProduction.setProductionWord ( production.getProductionWord () );
    }
    else
    {
      this.model.addProduction ( production );
    }

    this.gui.dispose ();
    this.parent.repaint ();
  }


  /**
   * Show this dialog.
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


  /**
   * Returns the {@link ProductionDialogForm}.
   * 
   * @return the {@link ProductionDialogForm}.
   */
  public ProductionDialogForm getGui ()
  {
    return this.gui;
  }
}
