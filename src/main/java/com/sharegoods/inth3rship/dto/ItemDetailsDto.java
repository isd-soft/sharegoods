package com.sharegoods.inth3rship.dto;

import com.sharegoods.inth3rship.models.Rating;

import java.util.List;

public class ItemDetailsDto {
    private ItemDto itemDto;
    private List<ImageDto> imageDtoList;

    public ItemDetailsDto(ItemDto itemDto, List<ImageDto> imageDtoList) {
        this.itemDto = itemDto;
        this.imageDtoList = imageDtoList;
    }

    public ItemDto getItemDto() { return itemDto; }

    public void setItemDto(ItemDto itemDto) {
        this.itemDto = itemDto;
    }

    public List<ImageDto> getImageDtoList() {
        return imageDtoList;
    }

    public void setImageDtoList(List<ImageDto> imageDtoList) {
        this.imageDtoList = imageDtoList;
    }
}
