package com.sharegoods.inth3rship.dto;

import com.sharegoods.inth3rship.models.Image;
import org.springframework.util.Base64Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ImageDto {

    private Long id;
    private String name;
    private String imageBase64;

    public ImageDto(Image image) {
        this.id = image.getId();
        this.name = image.getName();
        this.imageBase64 = Base64Utils.encodeToString(image.getImageData());
    }

    public static List<ImageDto> getImageDtoList(List<Image> imageList) {
        List<ImageDto> imageDtoList = new ArrayList<>();
        ListIterator<Image> imagesIterator = imageList.listIterator();
        while (imagesIterator.hasNext()) {
            ImageDto imageDto = new ImageDto(imagesIterator.next());
            imageDtoList.add(imageDto);
        }
        return imageDtoList;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageBase64() { return imageBase64; }

    public void setImageBase64(String imageBase64) { this.imageBase64 = imageBase64; }
}

