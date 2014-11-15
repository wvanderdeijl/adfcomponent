function RhItemSelectEvent(source, item) {
  AdfAssert.assertPrototype(source, RhMultiSelect);
  AdfAssert.assertString(item);
  this.Init(source, item);
}

// make RhItemSelectEvent a subclass of AdfComponentEvent
AdfObject.createSubclass(RhItemSelectEvent, AdfComponentEvent);

///**
// * The event type
//*/
RhItemSelectEvent.SELECT_EVENT_TYPE = "itemSelect";

///**
// * Event Object constructor
//*/
RhItemSelectEvent.prototype.Init = function(source, item) {
  AdfAssert.assertPrototype(source, RhMultiSelect);
  AdfAssert.assertString(item);
  this._item = item;
  // call super.init()
  RhItemSelectEvent.superclass.Init.call(this, source, RhItemSelectEvent.SELECT_EVENT_TYPE);
}

///**
// * Indicates this event should be sent to the server (default from superclass is false)
//*/
RhItemSelectEvent.prototype.propagatesToServer = function() {
  return true;
}

///**
// * Override of AddMarshalledProperties to add parameters * sent server side.
//*/
RhItemSelectEvent.prototype.AddMarshalledProperties = function(properties) {
  properties.item = this._item;
}

///**
// * Convenient method for queue a RhItemSelectEvent. Is invoked by Peer object
// */
RhItemSelectEvent.queue = function(component, item) {
  AdfAssert.assertPrototype(component, RhMultiSelect);
  AdfAssert.assertString(item);
  AdfLogger.LOGGER.logMessage(AdfLogger.FINEST, "RhItemSelectEvent.queue(component, item)");
  new RhItemSelectEvent(component, item).queue(/* isPartial=*/true);
}

///**
// * returns the selected file type
//*/
RhItemSelectEvent.prototype.getItem = function() {
  return this._item;
}

///**
// * returns a debug string
//*/
RhItemSelectEvent.prototype.toDebugString = function()
{
  var superString = RhItemSelectEvent.superclass.toDebugString.call(this);
  return superString.substring(0, superString.length-1) + ", item=" + this._item + "]";
}

///*
//*
//* Make sure that this event only invokes immediate validators
//* on the client.
//*/
RhItemSelectEvent.prototype.isImmediate = function() {
  return true;
}