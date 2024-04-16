package com.zeltang.it.tools.metadata;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class MetadataServiceImpl implements IMetadataService {

    @Override
    public void getMetadata () {
        try {
            File file = new File("/Users/tangzelong/Desktop/个人/B93DF1CF-B30B-41B8-829A-4CF9F8696ACC_1_105_c.jpeg");
            Metadata metadata = ImageMetadataReader.readMetadata(file);
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    String tagName = tag.getTagName();
                    String description = tag.getDescription();
                    if (tagName.equals("Image Height")) {
                        System.out.println("图片高度：" + description);
                    }
                    if (tagName.equals("Image Width")) {
                        System.out.println("图片宽度：" + description);
                    }
                    if (tagName.equals("Profile Date/Time")) {
                        System.out.println("拍摄时间：" + description);
                    }
                    if (tagName.equals("GPS Latitude")) {
                        System.out.println("纬度：" + description);
                    }
                    if (tagName.equals("GPS Longitude")) {
                        System.out.println("经度：" + description);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("异常：" + e.getMessage());
        }
    }


}
