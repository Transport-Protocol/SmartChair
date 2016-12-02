package com.hawhamburg.sg.mwrp.gamectrl.webinterface;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import com.hawhamburg.sg.mwrp.gamectrl.GameControllerProperties;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response.Status;

public class GcHttpServer extends NanoHTTPD
{
	private static final Map<String,Map<String,DynContentHandler>> dynContent=new HashMap<>();
	static
	{
		//dynContent.put("hostname",)
	}
	
	private GameControllerProperties properties;
	public GcHttpServer(GameControllerProperties properties) throws IOException {
		super(8080);
		this.properties=properties;
		
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, true);
        System.out.println("Game controller configuration @ http://localhost:8080/ ");
	}

	@Override
	public Response serve(IHTTPSession session) {
		if(session.getMethod()==Method.GET&&"/".equals(session.getUri()))
		{
			Response resp= newFixedLengthResponse(Status.REDIRECT, "", "");
			resp.addHeader("Location","/files/keymapping.html");
			return resp;
		}
		else if(session.getMethod()==Method.GET&&session.getUri().startsWith("/files/"))
			return serveFile(session);
		return newFixedLengthResponse(Status.OK,"text/html","<html><title>404!!1</title><body><h1>for oh for</h1></body></html>");
	}
	
	private Response serveRedirect(IHTTPSession session)
	{
		return null;
	}
	
	private Response serveFile(IHTTPSession session)
	{
		String filename=session.getUri().substring("/files/".length());
		Map<String, DynContentHandler>dynContentHandlers=dynContent.get(filename);
		if(dynContentHandlers!=null)
		{
			
		}
		return null;
		//return getClass().getClassLoader().getResourceAsStream(name);
	}
	
	///////////////////////////////////
	private String resolveHostName(String key)
	{
		return null;
		//return InetAddress.getLocalHost().getHostName();
	}
}
