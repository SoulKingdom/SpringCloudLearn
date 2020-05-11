package com.springcloud.sceurekaclient.demo.service;

import com.util.util.http.HttpClientResult;
import com.util.util.http.HttpClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 *  @dept 上海软件研发中心
 *  @description http服务方法
 *  @author HaoXin.Liu
 *  @date 2020/3/10 15:05
 **/
@Service
public class HttpService {
    public static final Logger logger = LoggerFactory.getLogger(HttpService.class);
    @Resource
    private RestTemplate restTemplate;

    /**
     * 调用外部api
     *
     * @return
     */
    public Boolean callExternalApi() {
        try {
            String url = "http://" + "" + "/ops_agent/v1/box/id";
          /*  String param = "{\n" +
                    "  \"custom_fields\": [],\n" +
                    "  \"device_name\": \"" + deviceBasicDo.getDeviceName() + "\",\n" +
                    "  \"branch_network_name\": \"\"\n" +
                    "}";*/
            Map<String, String> param = new HashMap<>(10);
            HttpClientResult httpClientResult = HttpClientUtils.doGet(url, null, param);
            //HttpClientResult httpClientResult = HttpClientUtils.doPut(url, getHeader(token), param);
            int code = httpClientResult.getCode();
            //判斷数据修改成功
            if (200 == code) {
                logger.info("盒子名称修改成功");
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.info("盒子名称修改失败");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 调用内部服务
     */
    public String callInteriorService() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> parts = new LinkedMultiValueMap<>();
        //设置传递参数
        parts.add("audience", "web");
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(parts, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("设置需要访问的controller连接", httpEntity, String.class);
        return responseEntity.getBody();
    }
}