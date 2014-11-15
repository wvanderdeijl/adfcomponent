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
    if (target && target.tagName == "BUTTON") {
      // clicked the button, queue a SelectItem event that will propagate to server
      var selectItem = target.firstChild.nodeValue; // name of selected item
      AdfAssert.assertString(selectItem);
      AdfLogger.LOGGER.logMessage(AdfLogger.FINEST, "tag :" + selectItem);
      // fire a select event
      RhItemSelectEvent.queue(component, selectItem); // TODO: delete and select should probably have different triggers ;-)
      //cancel the native dom onclick to prevent browser actions based on the
      //'#' hyperlink. The event is of type AdfIEUIInputEvent. This event
      //will cancel the native dom event by calling AdfAgent.AGENT.preventDefault(Event)
      //componentEvent.cancel();
    } else if (target && target.tagName == "SPAN") {
      // clicked the delete icon, queue ItemDeleteEvent that will be handled client side
      // TODO: would be better if we look at styleClass but that can be minified
      var deleteItem = target.parentElement; // li item being deleted
      AdfAssert.assertDomElement(deleteItem);
      var deleteIndex = deleteItem.getAttribute('rh-li'); // index of item being deleted
      AdfAssert.assertNumeric(deleteIndex);
      // remove deleting index from array of indices in component "value" property
      var hiddenInputId = component.getClientId() + ":value";
      var hiddenInput = AdfAgent.AGENT.getElementById(hiddenInputId);
      var compValue = JSON.parse(hiddenInput.value);
      AdfLogger.LOGGER.logMessage(AdfLogger.FINEST, "value before removing " + deleteIndex + ": " + compValue);
      //var idxToRemove = compValue.indexOf(deleteIndex);
      //compValue = compValue.splice(idxToRemove, /*deleteCount*/ 1);
      AdfCollections.removeArrayValue(compValue, deleteIndex);
      AdfLogger.LOGGER.logMessage(AdfLogger.FINEST, "value after removal: " + compValue);
      hiddenInput.value = JSON.stringify(compValue);
      // visually hide element
      deleteItem.style.display = 'none';
    }
  }
}

// Register the peer with the component. This bit of script must run
// after the javascript component class itself and any supporting
// event js classes. This is enforced by the ordering of the script
// files in adf-js-features.xml
// see AdfLookAndFeel
AdfPage.PAGE.getLookAndFeel().registerPeerConstructor("com.redheap.rh.RhMultiSelect", "RhMultiSelectPeer");