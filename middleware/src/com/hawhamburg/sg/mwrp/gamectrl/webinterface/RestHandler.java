package com.hawhamburg.sg.mwrp.gamectrl.webinterface;

import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import fi.iki.elonen.NanoHTTPD.Response;

public interface RestHandler {
	Response invoke(IHTTPSession session);
}
