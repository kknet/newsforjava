package com.revanow.news.service;

import com.revanow.news.domain.dte.Mapipdte;

public interface MapipService {

	public Mapipdte getLocationForIp(long ip);
}
