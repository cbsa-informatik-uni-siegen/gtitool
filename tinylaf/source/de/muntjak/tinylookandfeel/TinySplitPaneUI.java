/*******************************************************************************
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Tiny Look and Feel * * (C) Copyright 2003 - 2007 Hans Bickel * * For
 * licensing information and credits, please refer to the * comment in file
 * de.muntjak.tinylookandfeel.TinyLookAndFeel * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package de.muntjak.tinylookandfeel;


import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.metal.MetalSplitPaneUI;


/**
 * TinySplitPaneUI
 * 
 * @version 1.0
 * @author Hans Bickel
 */
@SuppressWarnings (
{ "all" } )
public class TinySplitPaneUI extends MetalSplitPaneUI
{

  /**
   * Creates a new MetalSplitPaneUI instance
   */
  public static ComponentUI createUI ( JComponent x )
  {
    return new TinySplitPaneUI ();
  }


  /**
   * Creates the default divider.
   */
  public BasicSplitPaneDivider createDefaultDivider ()
  {
    return new TinySplitPaneDivider ( this );
  }
}