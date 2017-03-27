package com.revanow.news.service;

import com.revanow.news.bean.MediaBean;
import com.revanow.news.domain.dte.Mediadte;

public interface MediaService {

	public Mediadte getMediaBySourceId(String source_id);
	
	public int insertMedia(Mediadte news);
	
	public int updateMedia(Mediadte news);
}
