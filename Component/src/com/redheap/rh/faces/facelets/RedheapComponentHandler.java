package com.redheap.rh.faces.facelets;


import javax.faces.view.facelets.ComponentConfig;
import javax.faces.view.facelets.MetaRuleset;

import oracle.adfinternal.view.faces.facelets.rich.RichComponentHandler;

public class RedheapComponentHandler extends RichComponentHandler {

    public RedheapComponentHandler(ComponentConfig config) {
        super(config);
    }

    /**
     *
     * @param type
     * @return A set of rules to be used in auto-wiring state to a particular object instance.
     */
    @Override
    protected MetaRuleset createMetaRuleset(Class type) {
        MetaRuleset retval = super.createMetaRuleset(type);
        retval.addRule(ListenersTagRule.INSTANCE);
        //retval.addRule();
        return retval;
    }
}
