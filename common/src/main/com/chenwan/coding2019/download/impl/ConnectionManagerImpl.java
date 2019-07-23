package main.com.chenwan.coding2019.download.impl;

import main.com.chenwan.coding2019.download.api.Connection;
import main.com.chenwan.coding2019.download.api.ConnectionException;
import main.com.chenwan.coding2019.download.api.ConnectionManager;

public class ConnectionManagerImpl implements ConnectionManager {

	@Override
	public Connection open(String url) throws ConnectionException {
		
		return new ConnectionImpl(url);
	}

}
