package com.zeltang.it.tools.metadata;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/metadata")
public class MetadataController {

    @Resource
    private IMetadataService metadataService;

    @RequestMapping("/getMetadata")
    public void getMetadata() {
        metadataService.getMetadata();
    }

}
