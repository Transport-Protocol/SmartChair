package com.hawhamburg.sg.mwrp.gamectrl.webinterface;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hawhamburg.sg.mwrp.gamectrl.GameControllerProperties;
import com.hawhamburg.sg.mwrp.gamectrl.GameControllerPropertiesConstants;
import com.hawhamburg.sg.mwrp.gamectrl.InputCharacter;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response.Status;

public class GcHttpServer extends NanoHTTPD {
	private static final ObjectMapper jsonMapper = new ObjectMapper();
	private final Map<String, Map<String, DynContentHandler>> dynContent = new HashMap<>();
	private final Map<String, RestHandler> restHandlers = new HashMap<>();

	{
		Map<String, DynContentHandler> handlers = new HashMap<>();
		handlers.put("hostname", this::resolveHostName);
		handlers.put("keyUi", this::resolveKeyUi);
		dynContent.put("keymapping.html", handlers);

		restHandlers.put("keys", this::apiKeys);
	}

	private GameControllerProperties properties;

	public GcHttpServer(GameControllerProperties properties) throws IOException {
		super(8080);
		this.properties = properties;

		start(NanoHTTPD.SOCKET_READ_TIMEOUT, true);
		System.out.println("Game controller configuration @ http://localhost:8080/ ");
	}

	@Override
	public Response serve(IHTTPSession session) {

		if (session.getMethod() == Method.GET && "/".equals(session.getUri())) {
			Response resp = newFixedLengthResponse(Status.REDIRECT, "", "");
			resp.addHeader("Location", "/files/keymapping.html");
			return resp;
		} else if (session.getMethod() == Method.GET && session.getUri().startsWith("/files/"))
			return serveFile(session);
		else if ((session.getMethod() == Method.GET || session.getMethod() == Method.POST)
				&& session.getUri().startsWith("/api/"))
			return serveRest(session);
		return newFixedLengthResponse(Status.OK, "text/html",
				"<html><title>404!!1</title><body><h1>for oh for</h1></body></html>");
	}

	private Response serveRest(IHTTPSession session) {
		String path = session.getUri().substring("/api/".length());
		RestHandler h = restHandlers.get(path);
		if (h == null)
			return newFixedLengthResponse(Status.NOT_FOUND, "text/html", "V.V");
		return h.invoke(session);
	}

	private Response serveFile(IHTTPSession session) {
		try {
			String filename = session.getUri().substring("/files/".length());
			Map<String, DynContentHandler> dynContentHandlers = dynContent.get(filename);
			String file = "derp";
			try (BufferedReader buffer = new BufferedReader(
					new InputStreamReader(getClass().getClassLoader().getResourceAsStream("resources/" + filename)))) {
				file = buffer.lines().collect(Collectors.joining("\n"));
			}

			if (dynContentHandlers != null)
				for (Map.Entry<String, DynContentHandler> handler : dynContentHandlers.entrySet())
					file = file.replace(String.format("${%s}", handler.getKey()),
							handler.getValue().invoke(handler.getKey()));

			return newFixedLengthResponse(Status.OK, "text/html", file);
		} catch (Exception e) {
			e.printStackTrace();
			return newFixedLengthResponse(Status.NOT_FOUND, "text/html", "File not found");
		}

	}

	///////////////////////////////////
	private String resolveHostName(String key) {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			return "UnknownHost";
		}
	}

	private String resolveKeyUi(String key) {
		StringBuilder sb = new StringBuilder();
		Map<String, Integer> map = properties.getKeycodeMap();
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			char ch = (char) entry.getValue().intValue();
			InputCharacter c = InputCharacter.getCharByArduinoCode((char) entry.getValue().intValue());
			ch = c == null ? ch : c.getKeyCode();

			sb.append(String.format(
					"<div class=\"midalign keyButton\" id=\"%s\">%s：<input value=\"%s\" readonly></input></div>\n",
					entry.getKey(), GameControllerPropertiesConstants.MAPPING_KEY_NAME.get(entry.getKey()),
					KeyEvent.getKeyText(ch)));
		}

		return sb.toString();
	}

	private Response apiKeys(IHTTPSession session) {
		switch (session.getMethod()) {
		case GET:
			try {
				return newFixedLengthResponse(jsonMapper.writeValueAsString(properties.getKeycodeMap()));
			} catch (JsonProcessingException e) {
				return newFixedLengthResponse("{}");
			}
		case POST:
			try {
				Map<String,Integer> keys;
					HashMap<String, String> map = new HashMap<String, String>();
			        session.parseBody(map);
			        final String json = map.get("postData");
				    
					keys=jsonMapper.readValue(json,new TypeReference<HashMap<String,Integer>>(){});
					
				Map<String,String> result=new HashMap<>();
				for(Map.Entry<String,Integer> en:keys.entrySet())
				{
					properties.setKey(en.getKey(), InputCharacter.keycodeToArduino((char)en.getValue().intValue()));
					result.put(en.getKey(), KeyEvent.getKeyText((char)en.getValue().intValue()));
				}
				return newFixedLengthResponse(Status.OK,"application/json",jsonMapper.writeValueAsString(result));
			} catch (IOException|ResponseException e) {
				return newFixedLengthResponse(Status.INTERNAL_ERROR,"","");
			}
		default:
			return newFixedLengthResponse(Status.METHOD_NOT_ALLOWED, "", "");
		}
	}
}
