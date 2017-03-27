package com.revanow.news.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revanow.news.bean.MediaBean;
import com.revanow.news.domain.dte.Mediadte;
import com.revanow.news.persistence.mapper.MediaMapper;
import com.revanow.news.service.MediaService;

@Service("mediaService")
public class MediaDaoImpl implements MediaService{

	@Autowired
	private MediaMapper mediaMap;
	
	@Override
	public Mediadte getMediaBySourceId(String source_id) {
		// TODO Auto-generated method stub
		return mediaMap.getMediaBySourceId(source_id);
	}

	@Override
	public int insertMedia(Mediadte news) {
		// TODO Auto-generated method stub
		return mediaMap.insertMedia(news);
	}

	@Override
	public int updateMedia(Mediadte news) {
		// TODO Auto-generated method stub
		return mediaMap.updateMedia(news);
	}

}
