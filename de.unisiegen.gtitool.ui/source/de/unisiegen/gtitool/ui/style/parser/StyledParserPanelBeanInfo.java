package de.unisiegen.gtitool.ui.style.parser;

import java.beans.BeanDescriptor;
import java.beans.EventSetDescriptor;
import java.beans.IndexedPropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * The styled parser panel bean info class.
 * 
 * @author Christian Fehler
 * @version $Id$
 */
@SuppressWarnings({ "all" })
public class StyledParserPanelBeanInfo extends SimpleBeanInfo {
    
    // Bean descriptor//GEN-FIRST:BeanDescriptor
    /*lazy BeanDescriptor*/
    private static BeanDescriptor getBdescriptor(){
        BeanDescriptor beanDescriptor = new BeanDescriptor  ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class , null ); // NOI18N//GEN-HEADEREND:BeanDescriptor
        
        // Here you can add code for customizing the BeanDescriptor.
        
        return beanDescriptor;     }//GEN-LAST:BeanDescriptor
    
    
    // Property identifiers//GEN-FIRST:Properties
    private static final int PROPERTY_accessibleContext = 0;
    private static final int PROPERTY_actionMap = 1;
    private static final int PROPERTY_alignmentX = 2;
    private static final int PROPERTY_alignmentY = 3;
    private static final int PROPERTY_ancestorListeners = 4;
    private static final int PROPERTY_autoscrolls = 5;
    private static final int PROPERTY_background = 6;
    private static final int PROPERTY_backgroundSet = 7;
    private static final int PROPERTY_border = 8;
    private static final int PROPERTY_bounds = 9;
    private static final int PROPERTY_colorModel = 10;
    private static final int PROPERTY_component = 11;
    private static final int PROPERTY_componentCount = 12;
    private static final int PROPERTY_componentListeners = 13;
    private static final int PROPERTY_componentOrientation = 14;
    private static final int PROPERTY_componentPopupMenu = 15;
    private static final int PROPERTY_components = 16;
    private static final int PROPERTY_containerListeners = 17;
    private static final int PROPERTY_copyable = 18;
    private static final int PROPERTY_cursor = 19;
    private static final int PROPERTY_cursorSet = 20;
    private static final int PROPERTY_debugGraphicsOptions = 21;
    private static final int PROPERTY_displayable = 22;
    private static final int PROPERTY_doubleBuffered = 23;
    private static final int PROPERTY_dropTarget = 24;
    private static final int PROPERTY_editable = 25;
    private static final int PROPERTY_enabled = 26;
    private static final int PROPERTY_focusable = 27;
    private static final int PROPERTY_focusCycleRoot = 28;
    private static final int PROPERTY_focusCycleRootAncestor = 29;
    private static final int PROPERTY_focusListeners = 30;
    private static final int PROPERTY_focusOwner = 31;
    private static final int PROPERTY_focusTraversable = 32;
    private static final int PROPERTY_focusTraversalKeys = 33;
    private static final int PROPERTY_focusTraversalKeysEnabled = 34;
    private static final int PROPERTY_focusTraversalPolicy = 35;
    private static final int PROPERTY_focusTraversalPolicyProvider = 36;
    private static final int PROPERTY_focusTraversalPolicySet = 37;
    private static final int PROPERTY_font = 38;
    private static final int PROPERTY_fontSet = 39;
    private static final int PROPERTY_foreground = 40;
    private static final int PROPERTY_foregroundSet = 41;
    private static final int PROPERTY_graphics = 42;
    private static final int PROPERTY_graphicsConfiguration = 43;
    private static final int PROPERTY_height = 44;
    private static final int PROPERTY_hierarchyBoundsListeners = 45;
    private static final int PROPERTY_hierarchyListeners = 46;
    private static final int PROPERTY_ignoreRepaint = 47;
    private static final int PROPERTY_inheritsPopupMenu = 48;
    private static final int PROPERTY_inputContext = 49;
    private static final int PROPERTY_inputMap = 50;
    private static final int PROPERTY_inputMethodListeners = 51;
    private static final int PROPERTY_inputMethodRequests = 52;
    private static final int PROPERTY_inputVerifier = 53;
    private static final int PROPERTY_insets = 54;
    private static final int PROPERTY_keyListeners = 55;
    private static final int PROPERTY_layout = 56;
    private static final int PROPERTY_lightweight = 57;
    private static final int PROPERTY_locale = 58;
    private static final int PROPERTY_location = 59;
    private static final int PROPERTY_locationOnScreen = 60;
    private static final int PROPERTY_managingFocus = 61;
    private static final int PROPERTY_maximumSize = 62;
    private static final int PROPERTY_maximumSizeSet = 63;
    private static final int PROPERTY_minimumSize = 64;
    private static final int PROPERTY_minimumSizeSet = 65;
    private static final int PROPERTY_mouseListeners = 66;
    private static final int PROPERTY_mouseMotionListeners = 67;
    private static final int PROPERTY_mousePosition = 68;
    private static final int PROPERTY_mouseWheelListeners = 69;
    private static final int PROPERTY_name = 70;
    private static final int PROPERTY_nextFocusableComponent = 71;
    private static final int PROPERTY_opaque = 72;
    private static final int PROPERTY_optimizedDrawingEnabled = 73;
    private static final int PROPERTY_paintingTile = 74;
    private static final int PROPERTY_parent = 75;
    private static final int PROPERTY_peer = 76;
    private static final int PROPERTY_preferredSize = 77;
    private static final int PROPERTY_preferredSizeSet = 78;
    private static final int PROPERTY_propertyChangeListeners = 79;
    private static final int PROPERTY_registeredKeyStrokes = 80;
    private static final int PROPERTY_requestFocusEnabled = 81;
    private static final int PROPERTY_rootPane = 82;
    private static final int PROPERTY_showing = 83;
    private static final int PROPERTY_size = 84;
    private static final int PROPERTY_toolkit = 85;
    private static final int PROPERTY_toolTipText = 86;
    private static final int PROPERTY_topLevelAncestor = 87;
    private static final int PROPERTY_transferHandler = 88;
    private static final int PROPERTY_treeLock = 89;
    private static final int PROPERTY_UI = 90;
    private static final int PROPERTY_UIClassID = 91;
    private static final int PROPERTY_valid = 92;
    private static final int PROPERTY_validateRoot = 93;
    private static final int PROPERTY_verifyInputWhenFocusTarget = 94;
    private static final int PROPERTY_vetoableChangeListeners = 95;
    private static final int PROPERTY_visible = 96;
    private static final int PROPERTY_visibleRect = 97;
    private static final int PROPERTY_width = 98;
    private static final int PROPERTY_x = 99;
    private static final int PROPERTY_y = 100;

    // Property array 
    /*lazy PropertyDescriptor*/
    private static PropertyDescriptor[] getPdescriptor(){
        PropertyDescriptor[] properties = new PropertyDescriptor[101];
    
        try {
            properties[PROPERTY_accessibleContext] = new PropertyDescriptor ( "accessibleContext", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getAccessibleContext", null ); // NOI18N
            properties[PROPERTY_actionMap] = new PropertyDescriptor ( "actionMap", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getActionMap", "setActionMap" ); // NOI18N
            properties[PROPERTY_alignmentX] = new PropertyDescriptor ( "alignmentX", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getAlignmentX", "setAlignmentX" ); // NOI18N
            properties[PROPERTY_alignmentY] = new PropertyDescriptor ( "alignmentY", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getAlignmentY", "setAlignmentY" ); // NOI18N
            properties[PROPERTY_ancestorListeners] = new PropertyDescriptor ( "ancestorListeners", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getAncestorListeners", null ); // NOI18N
            properties[PROPERTY_autoscrolls] = new PropertyDescriptor ( "autoscrolls", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getAutoscrolls", "setAutoscrolls" ); // NOI18N
            properties[PROPERTY_background] = new PropertyDescriptor ( "background", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getBackground", "setBackground" ); // NOI18N
            properties[PROPERTY_backgroundSet] = new PropertyDescriptor ( "backgroundSet", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "isBackgroundSet", null ); // NOI18N
            properties[PROPERTY_border] = new PropertyDescriptor ( "border", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getBorder", "setBorder" ); // NOI18N
            properties[PROPERTY_bounds] = new PropertyDescriptor ( "bounds", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getBounds", "setBounds" ); // NOI18N
            properties[PROPERTY_colorModel] = new PropertyDescriptor ( "colorModel", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getColorModel", null ); // NOI18N
            properties[PROPERTY_component] = new IndexedPropertyDescriptor ( "component", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, null, null, "getComponent", null ); // NOI18N
            properties[PROPERTY_componentCount] = new PropertyDescriptor ( "componentCount", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getComponentCount", null ); // NOI18N
            properties[PROPERTY_componentListeners] = new PropertyDescriptor ( "componentListeners", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getComponentListeners", null ); // NOI18N
            properties[PROPERTY_componentOrientation] = new PropertyDescriptor ( "componentOrientation", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getComponentOrientation", "setComponentOrientation" ); // NOI18N
            properties[PROPERTY_componentPopupMenu] = new PropertyDescriptor ( "componentPopupMenu", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getComponentPopupMenu", "setComponentPopupMenu" ); // NOI18N
            properties[PROPERTY_components] = new PropertyDescriptor ( "components", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getComponents", null ); // NOI18N
            properties[PROPERTY_containerListeners] = new PropertyDescriptor ( "containerListeners", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getContainerListeners", null ); // NOI18N
            properties[PROPERTY_copyable] = new PropertyDescriptor ( "copyable", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "isCopyable", "setCopyable" ); // NOI18N
            properties[PROPERTY_copyable].setDisplayName ( "copyable" );
            properties[PROPERTY_copyable].setShortDescription ( "the copyable value" );
            properties[PROPERTY_cursor] = new PropertyDescriptor ( "cursor", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getCursor", "setCursor" ); // NOI18N
            properties[PROPERTY_cursorSet] = new PropertyDescriptor ( "cursorSet", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "isCursorSet", null ); // NOI18N
            properties[PROPERTY_debugGraphicsOptions] = new PropertyDescriptor ( "debugGraphicsOptions", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getDebugGraphicsOptions", "setDebugGraphicsOptions" ); // NOI18N
            properties[PROPERTY_displayable] = new PropertyDescriptor ( "displayable", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "isDisplayable", null ); // NOI18N
            properties[PROPERTY_doubleBuffered] = new PropertyDescriptor ( "doubleBuffered", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "isDoubleBuffered", "setDoubleBuffered" ); // NOI18N
            properties[PROPERTY_dropTarget] = new PropertyDescriptor ( "dropTarget", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getDropTarget", "setDropTarget" ); // NOI18N
            properties[PROPERTY_editable] = new PropertyDescriptor ( "editable", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "isEditable", "setEditable" ); // NOI18N
            properties[PROPERTY_editable].setDisplayName ( "editable" );
            properties[PROPERTY_editable].setShortDescription ( "the editable value" );
            properties[PROPERTY_enabled] = new PropertyDescriptor ( "enabled", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "isEnabled", "setEnabled" ); // NOI18N
            properties[PROPERTY_focusable] = new PropertyDescriptor ( "focusable", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "isFocusable", "setFocusable" ); // NOI18N
            properties[PROPERTY_focusCycleRoot] = new PropertyDescriptor ( "focusCycleRoot", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "isFocusCycleRoot", "setFocusCycleRoot" ); // NOI18N
            properties[PROPERTY_focusCycleRootAncestor] = new PropertyDescriptor ( "focusCycleRootAncestor", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getFocusCycleRootAncestor", null ); // NOI18N
            properties[PROPERTY_focusListeners] = new PropertyDescriptor ( "focusListeners", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getFocusListeners", null ); // NOI18N
            properties[PROPERTY_focusOwner] = new PropertyDescriptor ( "focusOwner", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "isFocusOwner", null ); // NOI18N
            properties[PROPERTY_focusTraversable] = new PropertyDescriptor ( "focusTraversable", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "isFocusTraversable", null ); // NOI18N
            properties[PROPERTY_focusTraversalKeys] = new IndexedPropertyDescriptor ( "focusTraversalKeys", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, null, null, "getFocusTraversalKeys", "setFocusTraversalKeys" ); // NOI18N
            properties[PROPERTY_focusTraversalKeysEnabled] = new PropertyDescriptor ( "focusTraversalKeysEnabled", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getFocusTraversalKeysEnabled", "setFocusTraversalKeysEnabled" ); // NOI18N
            properties[PROPERTY_focusTraversalPolicy] = new PropertyDescriptor ( "focusTraversalPolicy", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getFocusTraversalPolicy", "setFocusTraversalPolicy" ); // NOI18N
            properties[PROPERTY_focusTraversalPolicyProvider] = new PropertyDescriptor ( "focusTraversalPolicyProvider", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "isFocusTraversalPolicyProvider", "setFocusTraversalPolicyProvider" ); // NOI18N
            properties[PROPERTY_focusTraversalPolicySet] = new PropertyDescriptor ( "focusTraversalPolicySet", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "isFocusTraversalPolicySet", null ); // NOI18N
            properties[PROPERTY_font] = new PropertyDescriptor ( "font", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getFont", "setFont" ); // NOI18N
            properties[PROPERTY_fontSet] = new PropertyDescriptor ( "fontSet", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "isFontSet", null ); // NOI18N
            properties[PROPERTY_foreground] = new PropertyDescriptor ( "foreground", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getForeground", "setForeground" ); // NOI18N
            properties[PROPERTY_foregroundSet] = new PropertyDescriptor ( "foregroundSet", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "isForegroundSet", null ); // NOI18N
            properties[PROPERTY_graphics] = new PropertyDescriptor ( "graphics", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getGraphics", null ); // NOI18N
            properties[PROPERTY_graphicsConfiguration] = new PropertyDescriptor ( "graphicsConfiguration", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getGraphicsConfiguration", null ); // NOI18N
            properties[PROPERTY_height] = new PropertyDescriptor ( "height", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getHeight", null ); // NOI18N
            properties[PROPERTY_hierarchyBoundsListeners] = new PropertyDescriptor ( "hierarchyBoundsListeners", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getHierarchyBoundsListeners", null ); // NOI18N
            properties[PROPERTY_hierarchyListeners] = new PropertyDescriptor ( "hierarchyListeners", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getHierarchyListeners", null ); // NOI18N
            properties[PROPERTY_ignoreRepaint] = new PropertyDescriptor ( "ignoreRepaint", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getIgnoreRepaint", "setIgnoreRepaint" ); // NOI18N
            properties[PROPERTY_inheritsPopupMenu] = new PropertyDescriptor ( "inheritsPopupMenu", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getInheritsPopupMenu", "setInheritsPopupMenu" ); // NOI18N
            properties[PROPERTY_inputContext] = new PropertyDescriptor ( "inputContext", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getInputContext", null ); // NOI18N
            properties[PROPERTY_inputMap] = new PropertyDescriptor ( "inputMap", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getInputMap", null ); // NOI18N
            properties[PROPERTY_inputMethodListeners] = new PropertyDescriptor ( "inputMethodListeners", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getInputMethodListeners", null ); // NOI18N
            properties[PROPERTY_inputMethodRequests] = new PropertyDescriptor ( "inputMethodRequests", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getInputMethodRequests", null ); // NOI18N
            properties[PROPERTY_inputVerifier] = new PropertyDescriptor ( "inputVerifier", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getInputVerifier", "setInputVerifier" ); // NOI18N
            properties[PROPERTY_insets] = new PropertyDescriptor ( "insets", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getInsets", null ); // NOI18N
            properties[PROPERTY_keyListeners] = new PropertyDescriptor ( "keyListeners", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getKeyListeners", null ); // NOI18N
            properties[PROPERTY_layout] = new PropertyDescriptor ( "layout", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getLayout", "setLayout" ); // NOI18N
            properties[PROPERTY_lightweight] = new PropertyDescriptor ( "lightweight", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "isLightweight", null ); // NOI18N
            properties[PROPERTY_locale] = new PropertyDescriptor ( "locale", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getLocale", "setLocale" ); // NOI18N
            properties[PROPERTY_location] = new PropertyDescriptor ( "location", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getLocation", "setLocation" ); // NOI18N
            properties[PROPERTY_locationOnScreen] = new PropertyDescriptor ( "locationOnScreen", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getLocationOnScreen", null ); // NOI18N
            properties[PROPERTY_managingFocus] = new PropertyDescriptor ( "managingFocus", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "isManagingFocus", null ); // NOI18N
            properties[PROPERTY_maximumSize] = new PropertyDescriptor ( "maximumSize", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getMaximumSize", "setMaximumSize" ); // NOI18N
            properties[PROPERTY_maximumSizeSet] = new PropertyDescriptor ( "maximumSizeSet", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "isMaximumSizeSet", null ); // NOI18N
            properties[PROPERTY_minimumSize] = new PropertyDescriptor ( "minimumSize", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getMinimumSize", "setMinimumSize" ); // NOI18N
            properties[PROPERTY_minimumSizeSet] = new PropertyDescriptor ( "minimumSizeSet", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "isMinimumSizeSet", null ); // NOI18N
            properties[PROPERTY_mouseListeners] = new PropertyDescriptor ( "mouseListeners", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getMouseListeners", null ); // NOI18N
            properties[PROPERTY_mouseMotionListeners] = new PropertyDescriptor ( "mouseMotionListeners", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getMouseMotionListeners", null ); // NOI18N
            properties[PROPERTY_mousePosition] = new PropertyDescriptor ( "mousePosition", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getMousePosition", null ); // NOI18N
            properties[PROPERTY_mouseWheelListeners] = new PropertyDescriptor ( "mouseWheelListeners", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getMouseWheelListeners", null ); // NOI18N
            properties[PROPERTY_name] = new PropertyDescriptor ( "name", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getName", "setName" ); // NOI18N
            properties[PROPERTY_nextFocusableComponent] = new PropertyDescriptor ( "nextFocusableComponent", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getNextFocusableComponent", "setNextFocusableComponent" ); // NOI18N
            properties[PROPERTY_opaque] = new PropertyDescriptor ( "opaque", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "isOpaque", "setOpaque" ); // NOI18N
            properties[PROPERTY_optimizedDrawingEnabled] = new PropertyDescriptor ( "optimizedDrawingEnabled", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "isOptimizedDrawingEnabled", null ); // NOI18N
            properties[PROPERTY_paintingTile] = new PropertyDescriptor ( "paintingTile", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "isPaintingTile", null ); // NOI18N
            properties[PROPERTY_parent] = new PropertyDescriptor ( "parent", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getParent", null ); // NOI18N
            properties[PROPERTY_peer] = new PropertyDescriptor ( "peer", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getPeer", null ); // NOI18N
            properties[PROPERTY_preferredSize] = new PropertyDescriptor ( "preferredSize", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getPreferredSize", "setPreferredSize" ); // NOI18N
            properties[PROPERTY_preferredSizeSet] = new PropertyDescriptor ( "preferredSizeSet", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "isPreferredSizeSet", null ); // NOI18N
            properties[PROPERTY_propertyChangeListeners] = new PropertyDescriptor ( "propertyChangeListeners", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getPropertyChangeListeners", null ); // NOI18N
            properties[PROPERTY_registeredKeyStrokes] = new PropertyDescriptor ( "registeredKeyStrokes", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getRegisteredKeyStrokes", null ); // NOI18N
            properties[PROPERTY_requestFocusEnabled] = new PropertyDescriptor ( "requestFocusEnabled", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "isRequestFocusEnabled", "setRequestFocusEnabled" ); // NOI18N
            properties[PROPERTY_rootPane] = new PropertyDescriptor ( "rootPane", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getRootPane", null ); // NOI18N
            properties[PROPERTY_showing] = new PropertyDescriptor ( "showing", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "isShowing", null ); // NOI18N
            properties[PROPERTY_size] = new PropertyDescriptor ( "size", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getSize", "setSize" ); // NOI18N
            properties[PROPERTY_toolkit] = new PropertyDescriptor ( "toolkit", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getToolkit", null ); // NOI18N
            properties[PROPERTY_toolTipText] = new PropertyDescriptor ( "toolTipText", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getToolTipText", "setToolTipText" ); // NOI18N
            properties[PROPERTY_topLevelAncestor] = new PropertyDescriptor ( "topLevelAncestor", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getTopLevelAncestor", null ); // NOI18N
            properties[PROPERTY_transferHandler] = new PropertyDescriptor ( "transferHandler", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getTransferHandler", "setTransferHandler" ); // NOI18N
            properties[PROPERTY_treeLock] = new PropertyDescriptor ( "treeLock", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getTreeLock", null ); // NOI18N
            properties[PROPERTY_UI] = new PropertyDescriptor ( "UI", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getUI", "setUI" ); // NOI18N
            properties[PROPERTY_UIClassID] = new PropertyDescriptor ( "UIClassID", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getUIClassID", null ); // NOI18N
            properties[PROPERTY_valid] = new PropertyDescriptor ( "valid", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "isValid", null ); // NOI18N
            properties[PROPERTY_validateRoot] = new PropertyDescriptor ( "validateRoot", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "isValidateRoot", null ); // NOI18N
            properties[PROPERTY_verifyInputWhenFocusTarget] = new PropertyDescriptor ( "verifyInputWhenFocusTarget", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getVerifyInputWhenFocusTarget", "setVerifyInputWhenFocusTarget" ); // NOI18N
            properties[PROPERTY_vetoableChangeListeners] = new PropertyDescriptor ( "vetoableChangeListeners", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getVetoableChangeListeners", null ); // NOI18N
            properties[PROPERTY_visible] = new PropertyDescriptor ( "visible", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "isVisible", "setVisible" ); // NOI18N
            properties[PROPERTY_visibleRect] = new PropertyDescriptor ( "visibleRect", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getVisibleRect", null ); // NOI18N
            properties[PROPERTY_width] = new PropertyDescriptor ( "width", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getWidth", null ); // NOI18N
            properties[PROPERTY_x] = new PropertyDescriptor ( "x", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getX", null ); // NOI18N
            properties[PROPERTY_y] = new PropertyDescriptor ( "y", de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "getY", null ); // NOI18N
        }
        catch(IntrospectionException e) {
            e.printStackTrace();
        }//GEN-HEADEREND:Properties
        
        // Here you can add code for customizing the properties array.
        
        return properties;     }//GEN-LAST:Properties
    
    // EventSet identifiers//GEN-FIRST:Events
    private static final int EVENT_ancestorListener = 0;
    private static final int EVENT_componentListener = 1;
    private static final int EVENT_containerListener = 2;
    private static final int EVENT_focusListener = 3;
    private static final int EVENT_hierarchyBoundsListener = 4;
    private static final int EVENT_hierarchyListener = 5;
    private static final int EVENT_inputMethodListener = 6;
    private static final int EVENT_keyListener = 7;
    private static final int EVENT_mouseListener = 8;
    private static final int EVENT_mouseMotionListener = 9;
    private static final int EVENT_mouseWheelListener = 10;
    private static final int EVENT_propertyChangeListener = 11;
    private static final int EVENT_vetoableChangeListener = 12;

    // EventSet array
    /*lazy EventSetDescriptor*/
    private static EventSetDescriptor[] getEdescriptor(){
        EventSetDescriptor[] eventSets = new EventSetDescriptor[13];
    
        try {
            eventSets[EVENT_ancestorListener] = new EventSetDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "ancestorListener", javax.swing.event.AncestorListener.class, new String[] {"ancestorAdded", "ancestorMoved", "ancestorRemoved"}, "addAncestorListener", "removeAncestorListener" ); // NOI18N
            eventSets[EVENT_componentListener] = new EventSetDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "componentListener", java.awt.event.ComponentListener.class, new String[] {"componentHidden", "componentMoved", "componentResized", "componentShown"}, "addComponentListener", "removeComponentListener" ); // NOI18N
            eventSets[EVENT_containerListener] = new EventSetDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "containerListener", java.awt.event.ContainerListener.class, new String[] {"componentAdded", "componentRemoved"}, "addContainerListener", "removeContainerListener" ); // NOI18N
            eventSets[EVENT_focusListener] = new EventSetDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "focusListener", java.awt.event.FocusListener.class, new String[] {"focusGained", "focusLost"}, "addFocusListener", "removeFocusListener" ); // NOI18N
            eventSets[EVENT_hierarchyBoundsListener] = new EventSetDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "hierarchyBoundsListener", java.awt.event.HierarchyBoundsListener.class, new String[] {"ancestorMoved", "ancestorResized"}, "addHierarchyBoundsListener", "removeHierarchyBoundsListener" ); // NOI18N
            eventSets[EVENT_hierarchyListener] = new EventSetDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "hierarchyListener", java.awt.event.HierarchyListener.class, new String[] {"hierarchyChanged"}, "addHierarchyListener", "removeHierarchyListener" ); // NOI18N
            eventSets[EVENT_inputMethodListener] = new EventSetDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "inputMethodListener", java.awt.event.InputMethodListener.class, new String[] {"caretPositionChanged", "inputMethodTextChanged"}, "addInputMethodListener", "removeInputMethodListener" ); // NOI18N
            eventSets[EVENT_keyListener] = new EventSetDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "keyListener", java.awt.event.KeyListener.class, new String[] {"keyPressed", "keyReleased", "keyTyped"}, "addKeyListener", "removeKeyListener" ); // NOI18N
            eventSets[EVENT_mouseListener] = new EventSetDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "mouseListener", java.awt.event.MouseListener.class, new String[] {"mouseClicked", "mouseEntered", "mouseExited", "mousePressed", "mouseReleased"}, "addMouseListener", "removeMouseListener" ); // NOI18N
            eventSets[EVENT_mouseMotionListener] = new EventSetDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "mouseMotionListener", java.awt.event.MouseMotionListener.class, new String[] {"mouseDragged", "mouseMoved"}, "addMouseMotionListener", "removeMouseMotionListener" ); // NOI18N
            eventSets[EVENT_mouseWheelListener] = new EventSetDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "mouseWheelListener", java.awt.event.MouseWheelListener.class, new String[] {"mouseWheelMoved"}, "addMouseWheelListener", "removeMouseWheelListener" ); // NOI18N
            eventSets[EVENT_propertyChangeListener] = new EventSetDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "propertyChangeListener", java.beans.PropertyChangeListener.class, new String[] {"propertyChange"}, "addPropertyChangeListener", "removePropertyChangeListener" ); // NOI18N
            eventSets[EVENT_vetoableChangeListener] = new EventSetDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class, "vetoableChangeListener", java.beans.VetoableChangeListener.class, new String[] {"vetoableChange"}, "addVetoableChangeListener", "removeVetoableChangeListener" ); // NOI18N
        }
        catch(IntrospectionException e) {
            e.printStackTrace();
        }//GEN-HEADEREND:Events
        
        // Here you can add code for customizing the event sets array.
        
        return eventSets;     }//GEN-LAST:Events
    
    // Method identifiers//GEN-FIRST:Methods
    private static final int METHOD_action0 = 0;
    private static final int METHOD_add1 = 1;
    private static final int METHOD_addNotify2 = 2;
    private static final int METHOD_addPropertyChangeListener3 = 3;
    private static final int METHOD_applyComponentOrientation4 = 4;
    private static final int METHOD_areFocusTraversalKeysSet5 = 5;
    private static final int METHOD_bounds6 = 6;
    private static final int METHOD_checkImage7 = 7;
    private static final int METHOD_computeVisibleRect8 = 8;
    private static final int METHOD_contains9 = 9;
    private static final int METHOD_countComponents10 = 10;
    private static final int METHOD_createImage11 = 11;
    private static final int METHOD_createToolTip12 = 12;
    private static final int METHOD_createVolatileImage13 = 13;
    private static final int METHOD_deliverEvent14 = 14;
    private static final int METHOD_disable15 = 15;
    private static final int METHOD_dispatchEvent16 = 16;
    private static final int METHOD_doLayout17 = 17;
    private static final int METHOD_enable18 = 18;
    private static final int METHOD_enableInputMethods19 = 19;
    private static final int METHOD_findComponentAt20 = 20;
    private static final int METHOD_firePropertyChange21 = 21;
    private static final int METHOD_getActionForKeyStroke22 = 22;
    private static final int METHOD_getBounds23 = 23;
    private static final int METHOD_getClientProperty24 = 24;
    private static final int METHOD_getComponentAt25 = 25;
    private static final int METHOD_getComponentZOrder26 = 26;
    private static final int METHOD_getConditionForKeyStroke27 = 27;
    private static final int METHOD_getDefaultLocale28 = 28;
    private static final int METHOD_getFontMetrics29 = 29;
    private static final int METHOD_getInsets30 = 30;
    private static final int METHOD_getListeners31 = 31;
    private static final int METHOD_getLocation32 = 32;
    private static final int METHOD_getMousePosition33 = 33;
    private static final int METHOD_getPopupLocation34 = 34;
    private static final int METHOD_getPropertyChangeListeners35 = 35;
    private static final int METHOD_getSize36 = 36;
    private static final int METHOD_getToolTipLocation37 = 37;
    private static final int METHOD_getToolTipText38 = 38;
    private static final int METHOD_gotFocus39 = 39;
    private static final int METHOD_grabFocus40 = 40;
    private static final int METHOD_handleEvent41 = 41;
    private static final int METHOD_hasFocus42 = 42;
    private static final int METHOD_hide43 = 43;
    private static final int METHOD_imageUpdate44 = 44;
    private static final int METHOD_insets45 = 45;
    private static final int METHOD_inside46 = 46;
    private static final int METHOD_invalidate47 = 47;
    private static final int METHOD_isAncestorOf48 = 48;
    private static final int METHOD_isFocusCycleRoot49 = 49;
    private static final int METHOD_isLightweightComponent50 = 50;
    private static final int METHOD_keyDown51 = 51;
    private static final int METHOD_keyUp52 = 52;
    private static final int METHOD_layout53 = 53;
    private static final int METHOD_list54 = 54;
    private static final int METHOD_locate55 = 55;
    private static final int METHOD_location56 = 56;
    private static final int METHOD_lostFocus57 = 57;
    private static final int METHOD_minimumSize58 = 58;
    private static final int METHOD_mouseDown59 = 59;
    private static final int METHOD_mouseDrag60 = 60;
    private static final int METHOD_mouseEnter61 = 61;
    private static final int METHOD_mouseExit62 = 62;
    private static final int METHOD_mouseMove63 = 63;
    private static final int METHOD_mouseUp64 = 64;
    private static final int METHOD_move65 = 65;
    private static final int METHOD_nextFocus66 = 66;
    private static final int METHOD_paint67 = 67;
    private static final int METHOD_paintAll68 = 68;
    private static final int METHOD_paintComponents69 = 69;
    private static final int METHOD_paintImmediately70 = 70;
    private static final int METHOD_postEvent71 = 71;
    private static final int METHOD_preferredSize72 = 72;
    private static final int METHOD_prepareImage73 = 73;
    private static final int METHOD_print74 = 74;
    private static final int METHOD_printAll75 = 75;
    private static final int METHOD_printComponents76 = 76;
    private static final int METHOD_putClientProperty77 = 77;
    private static final int METHOD_registerKeyboardAction78 = 78;
    private static final int METHOD_remove79 = 79;
    private static final int METHOD_removeAll80 = 80;
    private static final int METHOD_removeNotify81 = 81;
    private static final int METHOD_removePropertyChangeListener82 = 82;
    private static final int METHOD_repaint83 = 83;
    private static final int METHOD_requestDefaultFocus84 = 84;
    private static final int METHOD_requestFocus85 = 85;
    private static final int METHOD_requestFocusInWindow86 = 86;
    private static final int METHOD_resetKeyboardActions87 = 87;
    private static final int METHOD_reshape88 = 88;
    private static final int METHOD_resize89 = 89;
    private static final int METHOD_revalidate90 = 90;
    private static final int METHOD_scrollRectToVisible91 = 91;
    private static final int METHOD_setBounds92 = 92;
    private static final int METHOD_setComponentZOrder93 = 93;
    private static final int METHOD_setDefaultLocale94 = 94;
    private static final int METHOD_show95 = 95;
    private static final int METHOD_size96 = 96;
    private static final int METHOD_toString97 = 97;
    private static final int METHOD_transferFocus98 = 98;
    private static final int METHOD_transferFocusBackward99 = 99;
    private static final int METHOD_transferFocusDownCycle100 = 100;
    private static final int METHOD_transferFocusUpCycle101 = 101;
    private static final int METHOD_unregisterKeyboardAction102 = 102;
    private static final int METHOD_update103 = 103;
    private static final int METHOD_updateUI104 = 104;
    private static final int METHOD_validate105 = 105;

    // Method array 
    /*lazy MethodDescriptor*/
    private static MethodDescriptor[] getMdescriptor(){
        MethodDescriptor[] methods = new MethodDescriptor[106];
    
        try {
            methods[METHOD_action0] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("action", new Class[] {java.awt.Event.class, java.lang.Object.class})); // NOI18N
            methods[METHOD_action0].setDisplayName ( "" );
            methods[METHOD_add1] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("add", new Class[] {java.awt.Component.class})); // NOI18N
            methods[METHOD_add1].setDisplayName ( "" );
            methods[METHOD_addNotify2] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("addNotify", new Class[] {})); // NOI18N
            methods[METHOD_addNotify2].setDisplayName ( "" );
            methods[METHOD_addPropertyChangeListener3] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("addPropertyChangeListener", new Class[] {java.lang.String.class, java.beans.PropertyChangeListener.class})); // NOI18N
            methods[METHOD_addPropertyChangeListener3].setDisplayName ( "" );
            methods[METHOD_applyComponentOrientation4] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("applyComponentOrientation", new Class[] {java.awt.ComponentOrientation.class})); // NOI18N
            methods[METHOD_applyComponentOrientation4].setDisplayName ( "" );
            methods[METHOD_areFocusTraversalKeysSet5] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("areFocusTraversalKeysSet", new Class[] {Integer.TYPE})); // NOI18N
            methods[METHOD_areFocusTraversalKeysSet5].setDisplayName ( "" );
            methods[METHOD_bounds6] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("bounds", new Class[] {})); // NOI18N
            methods[METHOD_bounds6].setDisplayName ( "" );
            methods[METHOD_checkImage7] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("checkImage", new Class[] {java.awt.Image.class, java.awt.image.ImageObserver.class})); // NOI18N
            methods[METHOD_checkImage7].setDisplayName ( "" );
            methods[METHOD_computeVisibleRect8] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("computeVisibleRect", new Class[] {java.awt.Rectangle.class})); // NOI18N
            methods[METHOD_computeVisibleRect8].setDisplayName ( "" );
            methods[METHOD_contains9] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("contains", new Class[] {Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_contains9].setDisplayName ( "" );
            methods[METHOD_countComponents10] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("countComponents", new Class[] {})); // NOI18N
            methods[METHOD_countComponents10].setDisplayName ( "" );
            methods[METHOD_createImage11] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("createImage", new Class[] {java.awt.image.ImageProducer.class})); // NOI18N
            methods[METHOD_createImage11].setDisplayName ( "" );
            methods[METHOD_createToolTip12] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("createToolTip", new Class[] {})); // NOI18N
            methods[METHOD_createToolTip12].setDisplayName ( "" );
            methods[METHOD_createVolatileImage13] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("createVolatileImage", new Class[] {Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_createVolatileImage13].setDisplayName ( "" );
            methods[METHOD_deliverEvent14] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("deliverEvent", new Class[] {java.awt.Event.class})); // NOI18N
            methods[METHOD_deliverEvent14].setDisplayName ( "" );
            methods[METHOD_disable15] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("disable", new Class[] {})); // NOI18N
            methods[METHOD_disable15].setDisplayName ( "" );
            methods[METHOD_dispatchEvent16] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("dispatchEvent", new Class[] {java.awt.AWTEvent.class})); // NOI18N
            methods[METHOD_dispatchEvent16].setDisplayName ( "" );
            methods[METHOD_doLayout17] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("doLayout", new Class[] {})); // NOI18N
            methods[METHOD_doLayout17].setDisplayName ( "" );
            methods[METHOD_enable18] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("enable", new Class[] {})); // NOI18N
            methods[METHOD_enable18].setDisplayName ( "" );
            methods[METHOD_enableInputMethods19] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("enableInputMethods", new Class[] {Boolean.TYPE})); // NOI18N
            methods[METHOD_enableInputMethods19].setDisplayName ( "" );
            methods[METHOD_findComponentAt20] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("findComponentAt", new Class[] {Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_findComponentAt20].setDisplayName ( "" );
            methods[METHOD_firePropertyChange21] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, Boolean.TYPE, Boolean.TYPE})); // NOI18N
            methods[METHOD_firePropertyChange21].setDisplayName ( "" );
            methods[METHOD_getActionForKeyStroke22] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("getActionForKeyStroke", new Class[] {javax.swing.KeyStroke.class})); // NOI18N
            methods[METHOD_getActionForKeyStroke22].setDisplayName ( "" );
            methods[METHOD_getBounds23] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("getBounds", new Class[] {java.awt.Rectangle.class})); // NOI18N
            methods[METHOD_getBounds23].setDisplayName ( "" );
            methods[METHOD_getClientProperty24] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("getClientProperty", new Class[] {java.lang.Object.class})); // NOI18N
            methods[METHOD_getClientProperty24].setDisplayName ( "" );
            methods[METHOD_getComponentAt25] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("getComponentAt", new Class[] {Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_getComponentAt25].setDisplayName ( "" );
            methods[METHOD_getComponentZOrder26] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("getComponentZOrder", new Class[] {java.awt.Component.class})); // NOI18N
            methods[METHOD_getComponentZOrder26].setDisplayName ( "" );
            methods[METHOD_getConditionForKeyStroke27] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("getConditionForKeyStroke", new Class[] {javax.swing.KeyStroke.class})); // NOI18N
            methods[METHOD_getConditionForKeyStroke27].setDisplayName ( "" );
            methods[METHOD_getDefaultLocale28] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("getDefaultLocale", new Class[] {})); // NOI18N
            methods[METHOD_getDefaultLocale28].setDisplayName ( "" );
            methods[METHOD_getFontMetrics29] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("getFontMetrics", new Class[] {java.awt.Font.class})); // NOI18N
            methods[METHOD_getFontMetrics29].setDisplayName ( "" );
            methods[METHOD_getInsets30] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("getInsets", new Class[] {java.awt.Insets.class})); // NOI18N
            methods[METHOD_getInsets30].setDisplayName ( "" );
            methods[METHOD_getListeners31] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("getListeners", new Class[] {java.lang.Class.class})); // NOI18N
            methods[METHOD_getListeners31].setDisplayName ( "" );
            methods[METHOD_getLocation32] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("getLocation", new Class[] {java.awt.Point.class})); // NOI18N
            methods[METHOD_getLocation32].setDisplayName ( "" );
            methods[METHOD_getMousePosition33] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("getMousePosition", new Class[] {Boolean.TYPE})); // NOI18N
            methods[METHOD_getMousePosition33].setDisplayName ( "" );
            methods[METHOD_getPopupLocation34] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("getPopupLocation", new Class[] {java.awt.event.MouseEvent.class})); // NOI18N
            methods[METHOD_getPopupLocation34].setDisplayName ( "" );
            methods[METHOD_getPropertyChangeListeners35] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("getPropertyChangeListeners", new Class[] {java.lang.String.class})); // NOI18N
            methods[METHOD_getPropertyChangeListeners35].setDisplayName ( "" );
            methods[METHOD_getSize36] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("getSize", new Class[] {java.awt.Dimension.class})); // NOI18N
            methods[METHOD_getSize36].setDisplayName ( "" );
            methods[METHOD_getToolTipLocation37] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("getToolTipLocation", new Class[] {java.awt.event.MouseEvent.class})); // NOI18N
            methods[METHOD_getToolTipLocation37].setDisplayName ( "" );
            methods[METHOD_getToolTipText38] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("getToolTipText", new Class[] {java.awt.event.MouseEvent.class})); // NOI18N
            methods[METHOD_getToolTipText38].setDisplayName ( "" );
            methods[METHOD_gotFocus39] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("gotFocus", new Class[] {java.awt.Event.class, java.lang.Object.class})); // NOI18N
            methods[METHOD_gotFocus39].setDisplayName ( "" );
            methods[METHOD_grabFocus40] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("grabFocus", new Class[] {})); // NOI18N
            methods[METHOD_grabFocus40].setDisplayName ( "" );
            methods[METHOD_handleEvent41] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("handleEvent", new Class[] {java.awt.Event.class})); // NOI18N
            methods[METHOD_handleEvent41].setDisplayName ( "" );
            methods[METHOD_hasFocus42] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("hasFocus", new Class[] {})); // NOI18N
            methods[METHOD_hasFocus42].setDisplayName ( "" );
            methods[METHOD_hide43] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("hide", new Class[] {})); // NOI18N
            methods[METHOD_hide43].setDisplayName ( "" );
            methods[METHOD_imageUpdate44] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("imageUpdate", new Class[] {java.awt.Image.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_imageUpdate44].setDisplayName ( "" );
            methods[METHOD_insets45] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("insets", new Class[] {})); // NOI18N
            methods[METHOD_insets45].setDisplayName ( "" );
            methods[METHOD_inside46] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("inside", new Class[] {Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_inside46].setDisplayName ( "" );
            methods[METHOD_invalidate47] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("invalidate", new Class[] {})); // NOI18N
            methods[METHOD_invalidate47].setDisplayName ( "" );
            methods[METHOD_isAncestorOf48] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("isAncestorOf", new Class[] {java.awt.Component.class})); // NOI18N
            methods[METHOD_isAncestorOf48].setDisplayName ( "" );
            methods[METHOD_isFocusCycleRoot49] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("isFocusCycleRoot", new Class[] {java.awt.Container.class})); // NOI18N
            methods[METHOD_isFocusCycleRoot49].setDisplayName ( "" );
            methods[METHOD_isLightweightComponent50] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("isLightweightComponent", new Class[] {java.awt.Component.class})); // NOI18N
            methods[METHOD_isLightweightComponent50].setDisplayName ( "" );
            methods[METHOD_keyDown51] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("keyDown", new Class[] {java.awt.Event.class, Integer.TYPE})); // NOI18N
            methods[METHOD_keyDown51].setDisplayName ( "" );
            methods[METHOD_keyUp52] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("keyUp", new Class[] {java.awt.Event.class, Integer.TYPE})); // NOI18N
            methods[METHOD_keyUp52].setDisplayName ( "" );
            methods[METHOD_layout53] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("layout", new Class[] {})); // NOI18N
            methods[METHOD_layout53].setDisplayName ( "" );
            methods[METHOD_list54] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("list", new Class[] {java.io.PrintStream.class, Integer.TYPE})); // NOI18N
            methods[METHOD_list54].setDisplayName ( "" );
            methods[METHOD_locate55] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("locate", new Class[] {Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_locate55].setDisplayName ( "" );
            methods[METHOD_location56] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("location", new Class[] {})); // NOI18N
            methods[METHOD_location56].setDisplayName ( "" );
            methods[METHOD_lostFocus57] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("lostFocus", new Class[] {java.awt.Event.class, java.lang.Object.class})); // NOI18N
            methods[METHOD_lostFocus57].setDisplayName ( "" );
            methods[METHOD_minimumSize58] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("minimumSize", new Class[] {})); // NOI18N
            methods[METHOD_minimumSize58].setDisplayName ( "" );
            methods[METHOD_mouseDown59] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("mouseDown", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_mouseDown59].setDisplayName ( "" );
            methods[METHOD_mouseDrag60] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("mouseDrag", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_mouseDrag60].setDisplayName ( "" );
            methods[METHOD_mouseEnter61] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("mouseEnter", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_mouseEnter61].setDisplayName ( "" );
            methods[METHOD_mouseExit62] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("mouseExit", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_mouseExit62].setDisplayName ( "" );
            methods[METHOD_mouseMove63] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("mouseMove", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_mouseMove63].setDisplayName ( "" );
            methods[METHOD_mouseUp64] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("mouseUp", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_mouseUp64].setDisplayName ( "" );
            methods[METHOD_move65] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("move", new Class[] {Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_move65].setDisplayName ( "" );
            methods[METHOD_nextFocus66] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("nextFocus", new Class[] {})); // NOI18N
            methods[METHOD_nextFocus66].setDisplayName ( "" );
            methods[METHOD_paint67] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("paint", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_paint67].setDisplayName ( "" );
            methods[METHOD_paintAll68] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("paintAll", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_paintAll68].setDisplayName ( "" );
            methods[METHOD_paintComponents69] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("paintComponents", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_paintComponents69].setDisplayName ( "" );
            methods[METHOD_paintImmediately70] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("paintImmediately", new Class[] {Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_paintImmediately70].setDisplayName ( "" );
            methods[METHOD_postEvent71] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("postEvent", new Class[] {java.awt.Event.class})); // NOI18N
            methods[METHOD_postEvent71].setDisplayName ( "" );
            methods[METHOD_preferredSize72] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("preferredSize", new Class[] {})); // NOI18N
            methods[METHOD_preferredSize72].setDisplayName ( "" );
            methods[METHOD_prepareImage73] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("prepareImage", new Class[] {java.awt.Image.class, java.awt.image.ImageObserver.class})); // NOI18N
            methods[METHOD_prepareImage73].setDisplayName ( "" );
            methods[METHOD_print74] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("print", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_print74].setDisplayName ( "" );
            methods[METHOD_printAll75] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("printAll", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_printAll75].setDisplayName ( "" );
            methods[METHOD_printComponents76] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("printComponents", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_printComponents76].setDisplayName ( "" );
            methods[METHOD_putClientProperty77] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("putClientProperty", new Class[] {java.lang.Object.class, java.lang.Object.class})); // NOI18N
            methods[METHOD_putClientProperty77].setDisplayName ( "" );
            methods[METHOD_registerKeyboardAction78] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("registerKeyboardAction", new Class[] {java.awt.event.ActionListener.class, java.lang.String.class, javax.swing.KeyStroke.class, Integer.TYPE})); // NOI18N
            methods[METHOD_registerKeyboardAction78].setDisplayName ( "" );
            methods[METHOD_remove79] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("remove", new Class[] {Integer.TYPE})); // NOI18N
            methods[METHOD_remove79].setDisplayName ( "" );
            methods[METHOD_removeAll80] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("removeAll", new Class[] {})); // NOI18N
            methods[METHOD_removeAll80].setDisplayName ( "" );
            methods[METHOD_removeNotify81] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("removeNotify", new Class[] {})); // NOI18N
            methods[METHOD_removeNotify81].setDisplayName ( "" );
            methods[METHOD_removePropertyChangeListener82] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("removePropertyChangeListener", new Class[] {java.lang.String.class, java.beans.PropertyChangeListener.class})); // NOI18N
            methods[METHOD_removePropertyChangeListener82].setDisplayName ( "" );
            methods[METHOD_repaint83] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("repaint", new Class[] {Long.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_repaint83].setDisplayName ( "" );
            methods[METHOD_requestDefaultFocus84] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("requestDefaultFocus", new Class[] {})); // NOI18N
            methods[METHOD_requestDefaultFocus84].setDisplayName ( "" );
            methods[METHOD_requestFocus85] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("requestFocus", new Class[] {})); // NOI18N
            methods[METHOD_requestFocus85].setDisplayName ( "" );
            methods[METHOD_requestFocusInWindow86] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("requestFocusInWindow", new Class[] {})); // NOI18N
            methods[METHOD_requestFocusInWindow86].setDisplayName ( "" );
            methods[METHOD_resetKeyboardActions87] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("resetKeyboardActions", new Class[] {})); // NOI18N
            methods[METHOD_resetKeyboardActions87].setDisplayName ( "" );
            methods[METHOD_reshape88] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("reshape", new Class[] {Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_reshape88].setDisplayName ( "" );
            methods[METHOD_resize89] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("resize", new Class[] {Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_resize89].setDisplayName ( "" );
            methods[METHOD_revalidate90] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("revalidate", new Class[] {})); // NOI18N
            methods[METHOD_revalidate90].setDisplayName ( "" );
            methods[METHOD_scrollRectToVisible91] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("scrollRectToVisible", new Class[] {java.awt.Rectangle.class})); // NOI18N
            methods[METHOD_scrollRectToVisible91].setDisplayName ( "" );
            methods[METHOD_setBounds92] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("setBounds", new Class[] {Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_setBounds92].setDisplayName ( "" );
            methods[METHOD_setComponentZOrder93] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("setComponentZOrder", new Class[] {java.awt.Component.class, Integer.TYPE})); // NOI18N
            methods[METHOD_setComponentZOrder93].setDisplayName ( "" );
            methods[METHOD_setDefaultLocale94] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("setDefaultLocale", new Class[] {java.util.Locale.class})); // NOI18N
            methods[METHOD_setDefaultLocale94].setDisplayName ( "" );
            methods[METHOD_show95] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("show", new Class[] {})); // NOI18N
            methods[METHOD_show95].setDisplayName ( "" );
            methods[METHOD_size96] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("size", new Class[] {})); // NOI18N
            methods[METHOD_size96].setDisplayName ( "" );
            methods[METHOD_toString97] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("toString", new Class[] {})); // NOI18N
            methods[METHOD_toString97].setDisplayName ( "" );
            methods[METHOD_transferFocus98] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("transferFocus", new Class[] {})); // NOI18N
            methods[METHOD_transferFocus98].setDisplayName ( "" );
            methods[METHOD_transferFocusBackward99] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("transferFocusBackward", new Class[] {})); // NOI18N
            methods[METHOD_transferFocusBackward99].setDisplayName ( "" );
            methods[METHOD_transferFocusDownCycle100] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("transferFocusDownCycle", new Class[] {})); // NOI18N
            methods[METHOD_transferFocusDownCycle100].setDisplayName ( "" );
            methods[METHOD_transferFocusUpCycle101] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("transferFocusUpCycle", new Class[] {})); // NOI18N
            methods[METHOD_transferFocusUpCycle101].setDisplayName ( "" );
            methods[METHOD_unregisterKeyboardAction102] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("unregisterKeyboardAction", new Class[] {javax.swing.KeyStroke.class})); // NOI18N
            methods[METHOD_unregisterKeyboardAction102].setDisplayName ( "" );
            methods[METHOD_update103] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("update", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_update103].setDisplayName ( "" );
            methods[METHOD_updateUI104] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("updateUI", new Class[] {})); // NOI18N
            methods[METHOD_updateUI104].setDisplayName ( "" );
            methods[METHOD_validate105] = new MethodDescriptor ( de.unisiegen.gtitool.ui.style.parser.StyledParserPanel.class.getMethod("validate", new Class[] {})); // NOI18N
            methods[METHOD_validate105].setDisplayName ( "" );
        }
        catch( Exception e) {}//GEN-HEADEREND:Methods
        
        // Here you can add code for customizing the methods array.
        
        return methods;     }//GEN-LAST:Methods
    
    
    private static final int defaultPropertyIndex = -1;//GEN-BEGIN:Idx
    private static final int defaultEventIndex = -1;//GEN-END:Idx
    
    
//GEN-FIRST:Superclass
    
    // Here you can add code for customizing the Superclass BeanInfo.
    
//GEN-LAST:Superclass
    
    /**
     * Gets the bean's <code>BeanDescriptor</code>s.
     *
     * @return BeanDescriptor describing the editable
     * properties of this bean.  May return null if the
     * information should be obtained by automatic analysis.
     */
    public BeanDescriptor getBeanDescriptor() {
        return getBdescriptor();
    }
    
    /**
     * Gets the bean's <code>PropertyDescriptor</code>s.
     *
     * @return An array of PropertyDescriptors describing the editable
     * properties supported by this bean.  May return null if the
     * information should be obtained by automatic analysis.
     * <p>
     * If a property is indexed, then its entry in the result array will
     * belong to the IndexedPropertyDescriptor subclass of PropertyDescriptor.
     * A client of getPropertyDescriptors can use "instanceof" to check
     * if a given PropertyDescriptor is an IndexedPropertyDescriptor.
     */
    public PropertyDescriptor[] getPropertyDescriptors() {
        return getPdescriptor();
    }
    
    /**
     * Gets the bean's <code>EventSetDescriptor</code>s.
     *
     * @return  An array of EventSetDescriptors describing the kinds of
     * events fired by this bean.  May return null if the information
     * should be obtained by automatic analysis.
     */
    public EventSetDescriptor[] getEventSetDescriptors() {
        return getEdescriptor();
    }
    
    /**
     * Gets the bean's <code>MethodDescriptor</code>s.
     *
     * @return  An array of MethodDescriptors describing the methods
     * implemented by this bean.  May return null if the information
     * should be obtained by automatic analysis.
     */
    public MethodDescriptor[] getMethodDescriptors() {
        return getMdescriptor();
    }
    
    /**
     * A bean may have a "default" property that is the property that will
     * mostly commonly be initially chosen for update by human's who are
     * customizing the bean.
     * @return  Index of default property in the PropertyDescriptor array
     * 		returned by getPropertyDescriptors.
     * <P>	Returns -1 if there is no default property.
     */
    public int getDefaultPropertyIndex() {
        return defaultPropertyIndex;
    }
    
    /**
     * A bean may have a "default" event that is the event that will
     * mostly commonly be used by human's when using the bean.
     * @return Index of default event in the EventSetDescriptor array
     *		returned by getEventSetDescriptors.
     * <P>	Returns -1 if there is no default event.
     */
    public int getDefaultEventIndex() {
        return defaultEventIndex;
    }
}

