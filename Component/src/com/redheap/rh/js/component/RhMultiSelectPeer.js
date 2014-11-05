// register out class as a peer class
AdfRichUIPeer.createPeerClass(AdfRichUIPeer /* constructor of superclass */
                             ,"RhMultiSelectPeer" /* name of constructor to create for this peer */
                             ,true /* stateless - all component instances can share the same peer instance */);

RhMultiSelectPeer.InitSubclass = function() {
  AdfLogger.LOGGER.logMessage(AdfLogger.FINEST, "RhMultiSelect.InitSubclass()");
  // register client component events. The callback is assumed by using a naming
  // convention of (<Peer>.prototype.HandleComponent<Event>)
  AdfRichUIPeer.addComponentEventHandlers(this, AdfUIInputEvent.CLICK_EVENT_TYPE);
}

RhMultiSelectPeer.prototype.HandleComponentClick = function(componentEvent) {
  AdfAssert.assertPrototype(componentEvent, AdfUIInputEvent);
  AdfLogger.LOGGER.logMessage(AdfLogger.FINEST, "RhMultiSelectPeer.HandleComponentClick(componentEvent)");
  // if the left mouse button was pressed
  if (componentEvent.isLeftButtonPressed()) {
    // find component for the peer
    var component = this.getComponent();
    AdfAssert.assertPrototype(component, RhMultiSelect);
    // find the native dom element for the click event
    var target = componentEvent.getNativeEventTarget();
    if (target /*&& target.tagName == "A"*/) {
      AdfLogger.LOGGER.logMessage(AdfLogger.FINEST, "File type element (A) found: " + componentEvent.toString());
      var item = target.firstChild.nodeValue;
      AdfAssert.assertString(item);
      AdfLogger.LOGGER.logMessage(AdfLogger.FINEST, "tag :" + item);
      // fire a delete event
      RhItemDeleteEvent.queue(component, item);
      RhItemSelectEvent.queue(component, item); // TODO: delete and select should probably have different triggers ;-)
      //cancel the native dom onclick to prevent browser actions based on the
      //'#' hyperlink. The event is of type AdfIEUIInputEvent. This event
      //will cancel the native dom event by calling AdfAgent.AGENT.preventDefault(Event)
      //componentEvent.cancel();
    }
  }
}

// Register the peer with the component. This bit of script must run
// after the javascript component class itself and any supporting
// event js classes. This is enforced by the ordering of the script
// files in adf-js-features.xml
// see AdfLookAndFeel
AdfPage.PAGE.getLookAndFeel().registerPeerConstructor("com.redheap.rh.RhMultiSelect", "RhMultiSelectPeer");