package com.twentysixyoung.blog.modules.main.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.twentysixyoung.blog.modules.main.entity.InformationCounter;
import com.twentysixyoung.blog.modules.main.mapper.InformationCounterMapper;
import com.twentysixyoung.blog.modules.main.service.InformationCounterService;
import org.springframework.stereotype.Service;

@Service
public class InformationCounterServiceImpl extends ServiceImpl<InformationCounterMapper, InformationCounter> implements InformationCounterService {
}
