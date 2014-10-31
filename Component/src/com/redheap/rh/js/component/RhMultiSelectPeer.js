////FIXME: PlaceHolder JS from Oracle Example.
//
//AdfRichUIPeer.createPeerClass(AdfRichUIPeer, "AcmeTagPanePeer", true);
//AcmeTagPanePeer.InitSubclass = function()
//{ 
//AdfLogger.LOGGER.logMessage(AdfLogger.FINEST,
// "AcmeTagPanePeer.InitSubclass()");
//  AdfRichUIPeer.addComponentEventHandlers(this,
//     AdfUIInputEvent.CLICK_EVENT_TYPE);
//}
//
//AcmeTagPanePeer.prototype.HandleComponentClick = function(componentEvent)
//{  
//AdfLogger.LOGGER.logMessage(AdfLogger.FINEST, "AcmeTagPanePeer.HandleComponentClick(componentEvent)");
//  // if the left mouse button was pressed 
//if (componentEvent.isLeftButtonPressed())
// {
//   // find component for the peer
//  var component = this.getComponent();
//  AdfAssert.assertPrototype(component, AcmeTagPane);
//   // find the native dom element for the click event  
//var target = componentEvent.getNativeEventTarget(); 
//    if (target && target.tagName == "A")
//   { 
//   AdfLogger.LOGGER.logMessage(AdfLogger.FINEST, "File type element (A)
// found: " + componentEvent.toString());
//     var tag = target.firstChild.nodeValue;
//   AdfAssert.assertString(tag);
//
//
//   AdfLogger.LOGGER.logMessage(AdfLogger.FINEST, "tag :" + tag);
//   // fire a select event   
//AcmeTagSelectEvent.queue(component, tag);
//   //cancel the native dom onclick to prevent browser actions based on the
//   //'#' hyperlink. The event is of type AdfIEUIInputEvent. This event
//   //will cancle the native dom event by calling
//    //AdfAgent.AGENT.preventDefault(Event)
//      componentEvent.cancel();
//  } 
//// event has dom node  
//  }
//}
//// Register the peer with the component. This bit of script must
//// be invoked after the AcmeTagPane and AcmeTagSelectEvent objects
//// are created. This is enforced by the ordering of the script files
//// in the
// oracle.asfdemo.acme.faces.resource.AcmeResourceLoader.
// AcmeScriptsResourceLoader.AdfPage.PAGE.getLookAndFeel()
//.registerPeerConstructor("oracle.adfdemo.acme.TagPane",
//        "AcmeTagPanePeer");