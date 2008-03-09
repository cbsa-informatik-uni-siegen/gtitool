package de.unisiegen.gtitool.ui.popup;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.logic.ConfirmDialog;
import de.unisiegen.gtitool.ui.logic.ProductionDialog;
import de.unisiegen.gtitool.ui.model.DefaultGrammarModel;
import de.unisiegen.gtitool.ui.netbeans.GrammarPanelForm;


/**
 * A Popup Menu for {@link Transition}s
 * 
 * @author Benjamin Mies
 * @version $Id$
 */
public final class ProductionPopupMenu extends JPopupMenu
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = 3541518527653662496L;

  /**
   * The {@link GrammarPanelForm}.
   */
  private GrammarPanelForm parent;

  /**
   * The {@link DefaultGrammarModel}.
   */
  private DefaultGrammarModel model;
  
  /**
   * The selected {@link Production}.
   */
  private Production production;


  /**
   * The delete item.
   */
  private JMenuItem delete;


  /**
   * The configure item.
   */
  private JMenuItem config;
  
  /**
   * The add item.
   */
  private JMenuItem add;


  /**
   * Allocates a new {@link StatePopupMenu}.
   * 
   * @param parent The parent panel.
   * @param model the model containing the production.
   * @param production the selected {@link Production}
   */
  public ProductionPopupMenu ( GrammarPanelForm parent,
      DefaultGrammarModel model, Production production )
  {
    this.parent = parent;
    this.model = model;
    this.production = production;
    populateMenues ();

  }


  /**
   * Populates the menues of this popup menu.
   */
  private final void populateMenues ()
  {
    
    this.add = new JMenuItem ( Messages.getString ( "ProductionPopupMenu.Add" ) ); //$NON-NLS-1$
    this.add.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/popupMenu/new-production.png" ) ) ); //$NON-NLS-1$
    this.add.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent event )
      {
        ProductionDialog dialog = new ProductionDialog (
            ProductionPopupMenu.this.parent.getLogic ().getParent (),
            ProductionPopupMenu.this.model.getGrammar ()
                .getNonterminalSymbolSet (), ProductionPopupMenu.this.model
                .getGrammar ().getTerminalSymbolSet (),
            ProductionPopupMenu.this.model, null);
        dialog.show();
      }
    } );
    add ( this.add );



    this.config = new JMenuItem ( Messages
        .getString ( "ProductionPopupMenu.Properties" ) ); //$NON-NLS-1$
    this.config.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/popupMenu/properties.png" ) ) ); //$NON-NLS-1$
    this.config.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent event )
      {
        
        
        JFrame window = ( JFrame ) SwingUtilities
            .getWindowAncestor ( ProductionPopupMenu.this.parent );
        ProductionDialog productionDialog = new ProductionDialog ( window, 
            ProductionPopupMenu.this.model.getGrammar ().getNonterminalSymbolSet (), ProductionPopupMenu.this.model.getGrammar ().getTerminalSymbolSet (), ProductionPopupMenu.this.model, ProductionPopupMenu.this.production);
        productionDialog.show ();
      }
    } );
    this.config.setEnabled ( this.production != null );
    add ( this.config );
    
    this.delete = new JMenuItem ( Messages.getString ( "ProductionPopupMenu.Delete" ) ); //$NON-NLS-1$
    this.delete.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/popupMenu/delete.png" ) ) ); //$NON-NLS-1$
    this.delete.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent event )
      {
        ConfirmDialog confirmedDialog = new ConfirmDialog (
            ProductionPopupMenu.this.parent.getLogic ().getParent (), Messages
                .getString ( "ProductionPopupMenu.DeleteProductionQuestion", //$NON-NLS-1$
                    ProductionPopupMenu.this.production ), Messages
                .getString ( "ProductionPopupMenu.DeleteProductionTitle" ), true, //$NON-NLS-1$
            true, false );
        confirmedDialog.show ();
        if ( confirmedDialog.isConfirmed () )
        {
          ProductionPopupMenu.this.model.removeProduction (
              ProductionPopupMenu.this.production );
          ProductionPopupMenu.this.parent.repaint ();
        }
      }
    } );
    this.delete.setEnabled ( this.production != null );
    add ( this.delete );
  }
}
