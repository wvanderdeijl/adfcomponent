//FIXME: PlaceHolder JS from Oracle Example.

/**
 * Fires a select type event to the server for the source component 
* when a tag is clicked. 
*/
function AcmeTagSelectEvent(source, tag)
{
 AdfAssert.assertPrototype(source, AdfUIComponent);
 AdfAssert.assertString(tag); this.Init(source, tag);
}
// make AcmeTagSelectEvent a subclass of AdfComponentEvent

AdfObject.createSubclass(AcmeTagSelectEvent, AdfComponentEvent);
/**
 * The event type 
*/
AcmeTagSelectEvent.SELECT_EVENT_TYPE = "tagSelect";
/**
 * Event Object constructor 
*/
AcmeTagSelectEvent.prototype.Init = function(source, tag)
{
  AdfAssert.assertPrototype(source, AdfUIComponent);
  AdfAssert.assertString(tag);
  this._tag = tag;
 AcmeTagSelectEvent.superclass.Init.call(this, source, AcmeTagSelectEvent.SELECT_EVENT_TYPE);}
/**
 * Indicates this event should be sent to the server 
*/
AcmeTagSelectEvent.prototype.propagatesToServer = function()
{ 
  return true;
}
/**
 * Override of AddMarshalledProperties to add parameters * sent server side. 
*/
AcmeTagSelectEvent.prototype.AddMarshalledProperties = function( properties) 
{ 
  properties.tag = this._tag;


 }
/**
 * Convenient method for queue a AcmeTagSelectEvent.
 */
AcmeTagSelectEvent.queue = function(component, tag)
{  
AdfAssert.assertPrototype(component, AdfUIComponent);
 AdfAssert.assertString(tag);
 AdfLogger.LOGGER.logMessage(AdfLogger.FINEST,     "AcmeTagSelectEvent.queue(component, tag)");
 new AcmeTagSelectEvent(component, tag).queue(true);
}
/**
 * returns the selected file type 
*/
AcmeTagSelectEvent.prototype.getTag = function()
{
  return this._tag;}
/**
 * returns a debug string 
*/
AcmeTagSelectEvent.prototype.toDebugString = function()
{ 
 var superString = AcmeTagSelectEvent.superclass.toDebugString.call(this);
 return superString.substring(0, superString.length - 1)
  +     ", tag=" 
  + this._tag     + "]";
}
/*
*
* Make sure that this event only invokes immediate validators 
* on the client. 
*/
AcmeTagSelectEvent.prototype.isImmediate = function()
{ 
  return true;
}