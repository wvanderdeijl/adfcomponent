function RhItemDeleteEvent(source, item) {
  AdfAssert.assertPrototype(source, RhMultiSelect);
  AdfAssert.assertString(item);
  this.Init(source, item);
}

// make RhItemDeleteEventa subclass of AdfComponentEvent
AdfObject.createSubclass(RhItemDeleteEvent, AdfComponentEvent);

///**
// * The event type
//*/
RhItemDeleteEvent.DELETE_EVENT_TYPE = "itemDelete";

///**
// * Event Object constructor
//*/
RhItemDeleteEvent.prototype.Init = function(source, item) {
  AdfAssert.assertPrototype(source, RhMultiSelect);
  AdfAssert.assertString(item);
  this._item = item;
  // call super.init()
  RhItemDeleteEvent.superclass.Init.call(this, source, RhItemDeleteEvent.DELETE_EVENT_TYPE);
}

///**
// * Indicates this event should be sent to the server (default from superclass is false)
//*/
//RhItemDeleteEvent.prototype.propagatesToServer = function() {
//  return true;
//}

///**
// * Override of AddMarshalledProperties to add parameters * sent server side.
//*/
RhItemDeleteEvent.prototype.AddMarshalledProperties = function(properties) {
  properties.item = this._item;
}

///**
// * Convenient method for queue a RhItemDeleteEvent.
// */
RhItemDeleteEvent.queue = function(component, item) {
  AdfAssert.assertPrototype(component, RhMultiSelect);
  AdfAssert.assertString(item);
  AdfLogger.LOGGER.logMessage(AdfLogger.FINEST, "RhItemDeleteEvent.queue(component, item)");
  new RhItemDeleteEvent(component, item).queue(/* isPartial=*/true);
}

///**
// * returns the selected file type
//*/
RhItemDeleteEvent.prototype.getItem = function() {
  return this._item;
}

///**
// * returns a debug string
//*/
RhItemDeleteEvent.prototype.toDebugString = function()
{
  var superString = RhItemDeleteEvent.superclass.toDebugString.call(this);
  return superString.substring(0, superString.length-1) + ", item=" + this._item + "]";
}

///*
//*
//* Make sure that this event only invokes immediate validators
//* on the client.
//*/
RhItemDeleteEvent.prototype.isImmediate = function() {
  return true;
}