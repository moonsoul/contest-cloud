package com.app.xmlconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by Administrator on 2017/10/4.
 */
@Configuration
@ImportResource(locations={"classpath:/springConfig/applicationContext-*.xml"})
public class XMLConfig {
}
