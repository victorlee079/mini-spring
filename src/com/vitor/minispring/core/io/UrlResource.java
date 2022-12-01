package com.vitor.minispring.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import cn.hutool.core.lang.Assert;

public class UrlResource implements Resource {

	private final URL url;

	public UrlResource(URL url) {
		Assert.notNull(url, "URL must not be null");
		this.url = url;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		URLConnection con = url.openConnection();
		try {
			return con.getInputStream();
		} catch (IOException ex) {
			if (con instanceof HttpURLConnection) {
				((HttpURLConnection) con).disconnect();
			}
			throw ex;
		}
	}

}
