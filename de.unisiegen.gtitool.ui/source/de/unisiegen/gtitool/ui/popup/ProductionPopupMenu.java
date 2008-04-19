package de.unisiegen.gtitool.ui.popup;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import de.unisiegen.gtitool.core.entities.Production;
import de.unisiegen.gtitool.core.entities.Transition;
import de.unisiegen.gtitool.ui.Messages;
import de.unisiegen.gtitool.ui.logic.ConfirmDialog;
import de.unisiegen.gtitool.ui.logic.GrammarPanel;
import de.unisiegen.gtitool.ui.logic.ProductionDialog;
import de.unisiegen.gtitool.ui.model.DefaultGrammarModel;
import de.unisiegen.gtitool.ui.netbeans.GrammarPanelForm;
import de.unisiegen.gtitool.ui.redoundo.ProductionsListChangedItem;
import de.unisiegen.gtitool.ui.redoundo.RedoUndoItem;


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
   * The {@link GrammarPanel}.
   */
  private GrammarPanel grammarPanel;


  /**
   * The {@link DefaultGrammarModel}.
   */
  private DefaultGrammarModel model;


  /**
   * The selected {@link Production}.
   */
  private ArrayList < Production > productions;


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
   * The validate item.
   */
  private JMenuItem validate;


  /**
   * Allocates a new {@link StatePopupMenu}.
   * 
   * @param parent The {@link GrammarPanel}.
   * @param model the model containing the production.
   * @param productions the selected {@link Production}s
   */
  public ProductionPopupMenu ( GrammarPanel parent, DefaultGrammarModel model,
      ArrayList < Production > productions )
  {
    this.grammarPanel = parent;
    this.model = model;
    this.productions = productions;
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
            ProductionPopupMenu.this.grammarPanel.getParent (),
            ProductionPopupMenu.this.model.getGrammar ()
                .getNonterminalSymbolSet (), ProductionPopupMenu.this.model
                .getGrammar ().getTerminalSymbolSet (),
            ProductionPopupMenu.this.model, null,
            ProductionPopupMenu.this.grammarPanel.getRedoUndoHandler () );
        dialog.show ();
      }
    } );
    add ( this.add );

    this.config = new JMenuItem ( Messages
        .getString ( "ProductionPopupMenu.Properties" ) ); //$NON-NLS-1$
    this.config.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/popupMenu/rename.png" ) ) ); //$NON-NLS-1$
    this.config.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent event )
      {

        JFrame window = ( JFrame ) SwingUtilities
            .getWindowAncestor ( ( GrammarPanelForm ) ProductionPopupMenu.this.grammarPanel
                .getGui () );
        ProductionDialog productionDialog = new ProductionDialog ( window,
            ProductionPopupMenu.this.model.getGrammar ()
                .getNonterminalSymbolSet (), ProductionPopupMenu.this.model
                .getGrammar ().getTerminalSymbolSet (),
            ProductionPopupMenu.this.model,
            ProductionPopupMenu.this.productions.get ( 0 ),
            ProductionPopupMenu.this.grammarPanel.getRedoUndoHandler () );
        productionDialog.show ();
      }
    } );
    this.config.setEnabled ( this.productions.size () == 1 );
    add ( this.config );

    this.delete = new JMenuItem ( Messages
        .getString ( "ProductionPopupMenu.Delete" ) ); //$NON-NLS-1$
    this.delete.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/popupMenu/delete.png" ) ) ); //$NON-NLS-1$
    this.delete.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent event )
      {
        String message = null;
        if ( ProductionPopupMenu.this.productions.size () == 1 )
        {
          message = Messages.getString (
              "ProductionPopupMenu.DeleteProductionQuestion", //$NON-NLS-1$
              ProductionPopupMenu.this.productions.get ( 0 ) );
        }
        else
        {
          message = Messages
              .getString ( "ProductionPopupMenu.DeleteProductionsQuestion" ); //$NON-NLS-1$
        }

        ConfirmDialog confirmDialog = new ConfirmDialog (
            ProductionPopupMenu.this.grammarPanel.getParent (),
            message,
            Messages.getString ( "ProductionPopupMenu.DeleteProductionTitle" ), true, //$NON-NLS-1$
            true, false );
        confirmDialog.show ();
        if ( confirmDialog.isConfirmed () )
        {
          ArrayList < Production > oldProductions = new ArrayList < Production > ();
          oldProductions.addAll ( ProductionPopupMenu.this.grammarPanel.getGrammar ().getProductions () );
          
          for ( Production production : ProductionPopupMenu.this.productions )
          {
            ProductionPopupMenu.this.model.removeProduction ( production );
          }
          RedoUndoItem item = new ProductionsListChangedItem ( ProductionPopupMenu.this.grammarPanel.getGrammar (),
              oldProductions );
          ProductionPopupMenu.this.grammarPanel.getRedoUndoHandler ().addItem ( item );
          
          ( ( GrammarPanelForm ) ProductionPopupMenu.this.grammarPanel
              .getGui () ).repaint ();
        }
      }
    } );
    this.delete.setEnabled ( this.productions.size () > 0 );
    add ( this.delete );

    this.validate = new JMenuItem ( "Validate" ); //$NON-NLS-1$
    this.validate.addActionListener ( new ActionListener ()
    {

      @SuppressWarnings ( "synthetic-access" )
      public void actionPerformed ( @SuppressWarnings ( "unused" )
      ActionEvent event )
      {
        ProductionPopupMenu.this.grammarPanel.getMainWindow ()
            .handleValidate ();
      }
    } );
    add ( this.validate );
  }
}
