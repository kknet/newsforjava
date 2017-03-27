package com.revanow.news.service;

import com.revanow.news.domain.dte.Regipdte;

public interface RegipService {

	Regipdte getRegForIp( long ip);
}
