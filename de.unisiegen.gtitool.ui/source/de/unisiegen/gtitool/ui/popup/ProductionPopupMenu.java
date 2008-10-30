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
import de.unisiegen.gtitool.ui.i18n.Messages;
import de.unisiegen.gtitool.ui.logic.GrammarPanel;
import de.unisiegen.gtitool.ui.logic.ProductionDialog;
import de.unisiegen.gtitool.ui.model.DefaultGrammarModel;


/**
 * A popup menu for {@link Transition}s.
 * 
 * @author Benjamin Mies
 * @author Christian Fehler
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
  protected GrammarPanel grammarPanel;


  /**
   * The {@link DefaultGrammarModel}.
   */
  protected DefaultGrammarModel model;


  /**
   * The selected {@link Production}.
   */
  protected ArrayList < Production > productions;


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
   * @param productions the selected {@link Production}s.
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
    // add
    this.add = new JMenuItem ( Messages.getString ( "ProductionPopupMenu.Add" ) ); //$NON-NLS-1$
    this.add.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/grammar/small/add.png" ) ) ); //$NON-NLS-1$
    this.add.addActionListener ( new ActionListener ()
    {

      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
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

    // config
    this.config = new JMenuItem ( Messages
        .getString ( "ProductionPopupMenu.Properties" ) ); //$NON-NLS-1$
    this.config.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/small/rename.png" ) ) ); //$NON-NLS-1$
    this.config.addActionListener ( new ActionListener ()
    {

      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {

        JFrame window = ( JFrame ) SwingUtilities
            .getWindowAncestor ( ProductionPopupMenu.this.grammarPanel
                .getGUI () );
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

    // delete
    this.delete = new JMenuItem ( Messages
        .getString ( "ProductionPopupMenu.Delete" ) ); //$NON-NLS-1$
    this.delete.setIcon ( new ImageIcon ( getClass ().getResource (
        "/de/unisiegen/gtitool/ui/icon/small/delete.png" ) ) ); //$NON-NLS-1$
    this.delete.addActionListener ( new ActionListener ()
    {

      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        ProductionPopupMenu.this.grammarPanel.handleDeleteProduction ();
      }
    } );
    this.delete.setEnabled ( this.productions.size () > 0 );
    add ( this.delete );

    // validate
    this.validate = new JMenuItem ( Messages
        .getString ( "MachinePanel.Validate" ) ); //$NON-NLS-1$
    this.validate.addActionListener ( new ActionListener ()
    {

      public void actionPerformed (
          @SuppressWarnings ( "unused" ) ActionEvent event )
      {
        ProductionPopupMenu.this.grammarPanel.getMainWindow ()
            .handleValidate ();
      }
    } );
    add ( this.validate );
  }
}
