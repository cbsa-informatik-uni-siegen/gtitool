package de.unisiegen.gtitool.ui.style.parser;


import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import java_cup.runtime.Symbol;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.apache.log4j.Logger;

import de.unisiegen.gtitool.core.parser.GTIParser;
import de.unisiegen.gtitool.core.parser.Parseable;
import de.unisiegen.gtitool.core.parser.exceptions.ParserException;
import de.unisiegen.gtitool.core.parser.exceptions.ParserMultiException;
import de.unisiegen.gtitool.core.parser.exceptions.ParserWarningException;
import de.unisiegen.gtitool.core.parser.exceptions.ScannerException;
import de.unisiegen.gtitool.core.parser.scanner.AbstractScanner;
import de.unisiegen.gtitool.core.parser.scanner.GTIScanner;
import de.unisiegen.gtitool.core.parser.style.Style;
import de.unisiegen.gtitool.ui.preferences.PreferenceManager;
import de.unisiegen.gtitool.ui.preferences.listener.ColorChangedAdapter;
import de.unisiegen.gtitool.ui.preferences.listener.ExceptionsChangedListener;
import de.unisiegen.gtitool.ui.style.listener.ParseableChangedListener;


/**
 * An implementation of the {@link StyledDocument} interface to enable syntax
 * highlighting using the lexer.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
public final class StyledParserDocument extends DefaultStyledDocument
{

  /**
   * The serial version uid.
   */
  private static final long serialVersionUID = -2546142812930077554L;


  /**
   * The {@link Logger} for this class.
   * 
   * @see Logger
   */
  private static final Logger logger = Logger
      .getLogger ( StyledParserDocument.class );


  /**
   * The list of {@link ParseableChangedListener}.
   */
  private ArrayList < ParseableChangedListener > parseableChangedListenerList = new ArrayList < ParseableChangedListener > ();


  /**
   * The {@link Parseable} for which this document was allocated.
   */
  private Parseable parseable;


  /**
   * The current exceptions from the scanner and parser.
   * 
   * @see #getExceptionList()
   */
  private ArrayList < ScannerException > exceptionList;


  /**
   * The attributes default style.
   */
  private SimpleAttributeSet normalSet = new SimpleAttributeSet ();


  /**
   * The attributes for the various {@link Style}s.
   */
  private HashMap < Style, SimpleAttributeSet > attributes = new HashMap < Style, SimpleAttributeSet > ();


  /**
   * The list of {@link ExceptionsChangedListener}.
   */
  private ArrayList < ExceptionsChangedListener > exceptionsChangedListenerList = new ArrayList < ExceptionsChangedListener > ();


  /**
   * The parser warning color.
   */
  private Color parserWarningColor;


  /**
   * Allocates a new <code>StyledParserDocument</code> for the given
   * <code>pParseable</code>, where the <code>pParseable</code> is used to
   * determine the scanner for the documents content and thereby dictates the
   * syntax highlighting.
   * 
   * @param pParseable The {@link Parseable} for which to allocate a document.
   * @throws NullPointerException if the <code>pParseable</code> is
   *           <code>null</code>.
   */
  public StyledParserDocument ( Parseable pParseable )
  {
    if ( pParseable == null )
    {
      throw new NullPointerException ( "parseable is null" ); //$NON-NLS-1$
    }
    this.parseable = pParseable;
    StyleConstants.setForeground ( this.normalSet, Color.BLACK );
    StyleConstants.setBold ( this.normalSet, false );
    SimpleAttributeSet stateSet = new SimpleAttributeSet ();
    StyleConstants.setBold ( stateSet, true );
    this.attributes.put ( Style.STATE, stateSet );
    SimpleAttributeSet symbolSet = new SimpleAttributeSet ();
    StyleConstants.setBold ( symbolSet, true );
    this.attributes.put ( Style.SYMBOL, symbolSet );
    initAttributes ();
    this.parserWarningColor = PreferenceManager.getInstance ()
        .getColorItemParserWarning ().getColor ();

    /*
     * ColorChangedListener
     */
    PreferenceManager.getInstance ().addColorChangedListener (
        new ColorChangedAdapter ()
        {

          /**
           * {@inheritDoc}
           */
          @SuppressWarnings ( "synthetic-access" )
          @Override
          public void colorChangedParserWarning ( Color pNewColor )
          {
            StyledParserDocument.this.parserWarningColor = pNewColor;
            try
            {
              processChanged ();
            }
            catch ( BadLocationException e )
            {
              e.printStackTrace ();
            }
          }


          /**
           * {@inheritDoc}
           */
          @SuppressWarnings ( "synthetic-access" )
          @Override
          public void colorChangedSymbol ( @SuppressWarnings ( "unused" )
          Color pNewColor )
          {
            initAttributes ();
            try
            {
              processChanged ();
            }
            catch ( BadLocationException e )
            {
              e.printStackTrace ();
            }
          }
        } );
  }


  /**
   * Adds the given {@link ExceptionsChangedListener}.
   * 
   * @param pListener The {@link ExceptionsChangedListener}.
   */
  public final synchronized void addExceptionsChangedListener (
      ExceptionsChangedListener pListener )
  {
    this.exceptionsChangedListenerList.add ( pListener );
  }


  /**
   * Adds the given {@link ParseableChangedListener}.
   * 
   * @param pListener The {@link ParseableChangedListener}.
   */
  public final synchronized void addParseableChangedListener (
      ParseableChangedListener pListener )
  {
    this.parseableChangedListenerList.add ( pListener );
  }


  /**
   * Let the listeners know that the exceptions has changed.
   */
  public final void fireExceptionsChanged ()
  {
    for ( ExceptionsChangedListener current : this.exceptionsChangedListenerList )
    {
      current.exceptionsChanged ();
    }
  }


  /**
   * Let the listeners know that the {@link Object} has changed.
   * 
   * @param pNewObject The new {@link Object}.
   */
  public final void fireParseableChanged ( Object pNewObject )
  {
    for ( ParseableChangedListener current : this.parseableChangedListenerList )
    {
      current.parseableChanged ( pNewObject );
    }
  }


  /**
   * Returns the error set.
   * 
   * @return The error set.
   */
  private SimpleAttributeSet getAttributeSetError ()
  {
    SimpleAttributeSet errorSet = new SimpleAttributeSet ();
    StyleConstants.setForeground ( errorSet, Color.RED );
    StyleConstants.setBold ( errorSet, true );
    StyleConstants.setUnderline ( errorSet, true );
    return errorSet;
  }


  /**
   * Returns the warning set.
   * 
   * @return The warning set.
   */
  private SimpleAttributeSet getAttributeSetWarning ()
  {
    SimpleAttributeSet warningSet = new SimpleAttributeSet ();
    StyleConstants.setBackground ( warningSet, this.parserWarningColor );
    return warningSet;
  }


  /**
   * Returns the current {@link ScannerException}s that were detected while
   * trying to interpret the token stream.
   * 
   * @return The exceptions.
   */
  public final ArrayList < ScannerException > getExceptionList ()
  {
    return this.exceptionList;
  }


  /**
   * Returns the {@link Object} for the program text within this document.
   * Throws an exception if a parsing error occurred.
   * 
   * @return The {@link Object} for the program text.
   * @throws Exception If a parsing error occurred.
   */
  public final Object getParsedObject () throws Exception
  {
    return this.parseable.newParser ( getText ( 0, getLength () ) ).parse ();
  }


  /**
   * Initializes the attributes.
   */
  private final void initAttributes ()
  {
    StyleConstants
        .setForeground ( this.attributes.get ( Style.STATE ), PreferenceManager
            .getInstance ().getColorItemParserState ().getColor () );
    StyleConstants.setForeground ( this.attributes.get ( Style.SYMBOL ),
        PreferenceManager.getInstance ().getColorItemParserSymbol ()
            .getColor () );
  }


  /**
   * {@inheritDoc}
   * 
   * @see AbstractDocument#insertString(int, String, AttributeSet)
   */
  @Override
  public final void insertString ( int pOffset, String pString,
      AttributeSet pAttributeSet ) throws BadLocationException
  {
    super.insertString ( pOffset, pString, pAttributeSet );
    processChanged ();
    Object newObject;
    try
    {
      newObject = getParsedObject ();
    }
    catch ( Exception e )
    {
      newObject = null;
    }
    fireParseableChanged ( newObject );
  }


  /**
   * Processes the document content after a change.
   * 
   * @throws BadLocationException if the processing failed.
   */
  @SuppressWarnings (
  { "unused", "null" } )
  public final void processChanged () throws BadLocationException
  {
    setCharacterAttributes ( 0, getLength (), this.normalSet, true );
    ArrayList < ScannerException > collectedExceptions = new ArrayList < ScannerException > ();
    try
    {
      /*
       * Start scanner
       */
      int offset = 0;
      String content = getText ( offset, getLength () );
      final GTIScanner scanner = this.parseable.newScanner ( content );
      final LinkedList < Symbol > symbols = new LinkedList < Symbol > ();
      while ( true )
      {
        try
        {
          Symbol symbol = scanner.nextSymbol ();
          if ( symbol == null )
          {
            break;
          }
          symbols.add ( symbol );
          SimpleAttributeSet set = this.attributes.get ( scanner
              .getStyleBySymbol ( symbol ) );
          if ( set == null )
          {
            set = this.normalSet;
          }
          setCharacterAttributes ( offset + symbol.left, symbol.right
              - symbol.left, set, true );
        }
        catch ( ScannerException ecx )
        {
          int newOffset = offset + ecx.getRight ();
          content = content.substring ( ecx.getRight () );
          ecx = new ScannerException ( offset + ecx.getLeft (), offset
              + ecx.getRight (), ecx.getMessage (), ecx.getCause () );
          SimpleAttributeSet errorSet = getAttributeSetError ();
          errorSet.addAttribute ( "exception", ecx ); //$NON-NLS-1$
          setCharacterAttributes ( ecx.getLeft (), ecx.getRight ()
              - ecx.getLeft (), errorSet, false );
          offset = newOffset;
          scanner.restart ( content );
          collectedExceptions.add ( ecx );
        }
      }
      /*
       * Start parser only if the scanner has no exceptions
       */
      if ( collectedExceptions.size () == 0 )
      {
        GTIParser parser = this.parseable.newParser ( new AbstractScanner ()
        {

          @Override
          public Style getStyleBySymbolId ( int pId )
          {
            return ( ( AbstractScanner ) scanner ).getStyleBySymbolId ( pId );
          }


          public Symbol nextSymbol () throws IOException, ScannerException
          {
            return ( !symbols.isEmpty () ) ? symbols.poll () : null;
          }


          public void restart ( String pText )
          {
            throw new UnsupportedOperationException ();
          }
        } );
        try
        {
          parser.parse ();
        }
        catch ( ParserMultiException ecx )
        {
          String [] message = ecx.getMessages ();
          int [] startOffset = ecx.getParserStartOffset ();
          int [] endOffset = ecx.getParserEndOffset ();
          for ( int i = 0 ; i < startOffset.length ; i++ )
          {
            ParserException newException = new ParserException (
                startOffset [ i ], endOffset [ i ], message [ i ] );
            SimpleAttributeSet errorSet = getAttributeSetError ();
            errorSet.addAttribute ( "exception", newException ); //$NON-NLS-1$
            setCharacterAttributes ( startOffset [ i ], endOffset [ i ]
                - startOffset [ i ], errorSet, false );
            collectedExceptions.add ( newException );
          }
        }
        catch ( ParserWarningException ecx )
        {
          SimpleAttributeSet warningSet = getAttributeSetWarning ();
          warningSet.addAttribute ( "warning", ecx ); //$NON-NLS-1$
          if ( ecx.getLeft () < 0 && ecx.getRight () < 0 )
          {
            setCharacterAttributes ( getLength (), getLength (), warningSet,
                false );
          }
          else
          {
            setCharacterAttributes ( ecx.getLeft (), ecx.getRight ()
                - ecx.getLeft (), warningSet, false );
          }
          collectedExceptions.add ( new ParserWarningException ( ecx
              .getRight (), ecx.getRight (), ecx.getMessage (), ecx
              .getInsertText () ) );
        }
        catch ( ParserException ecx )
        {
          SimpleAttributeSet errorSet = getAttributeSetError ();
          errorSet.addAttribute ( "exception", ecx ); //$NON-NLS-1$
          if ( ecx.getLeft () < 0 && ecx.getRight () < 0 )
          {
            setCharacterAttributes ( getLength (), getLength (), errorSet,
                false );
          }
          else
          {
            setCharacterAttributes ( ecx.getLeft (), ecx.getRight ()
                - ecx.getLeft (), errorSet, false );
          }
          collectedExceptions.add ( ecx );
        }
      }
    }
    catch ( Exception exc )
    {
      logger.error ( "failed to process changes", exc ); //$NON-NLS-1$
    }
    if ( !collectedExceptions.equals ( this.exceptionList ) )
    {
      this.exceptionList = collectedExceptions;
      fireExceptionsChanged ();
    }
  }


  /**
   * {@inheritDoc}
   * 
   * @see Document#remove(int, int)
   */
  @Override
  public final void remove ( int pOffset, int pLength )
      throws BadLocationException
  {
    super.remove ( pOffset, pLength );
    processChanged ();
    Object newObject;
    try
    {
      newObject = getParsedObject ();
    }
    catch ( Exception e )
    {
      newObject = null;
    }
    fireParseableChanged ( newObject );
  }


  /**
   * Removes the given {@link ExceptionsChangedListener}.
   * 
   * @param pListener The {@link ExceptionsChangedListener}.
   * @return <tt>true</tt> if the list contained the specified element.
   */
  public final synchronized boolean removeExceptionsChangedListener (
      ExceptionsChangedListener pListener )
  {
    return this.exceptionsChangedListenerList.remove ( pListener );
  }


  /**
   * Removes the given {@link ParseableChangedListener}.
   * 
   * @param pListener The {@link ParseableChangedListener}.
   * @return <tt>true</tt> if the list contained the specified element.
   */
  public final synchronized boolean removeParseableChangedListener (
      ParseableChangedListener pListener )
  {
    return this.parseableChangedListenerList.remove ( pListener );
  }
}
