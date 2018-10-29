package com.sharegoods.inth3rship.dto;

import java.util.List;

public class ItemDetailsDto {

    private ItemDto itemDto;
    private List<ImageDto> imageDtoList;
    private Boolean userIsOnline;

    public ItemDetailsDto(ItemDto itemDto, List<ImageDto> imageDtoList, boolean userIsOnline) {
        this.itemDto = itemDto;
        this.imageDtoList = imageDtoList;
        this.userIsOnline = userIsOnline;
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

    public boolean getUserIsOnline() { return userIsOnline;}

    public void setUserIsOnline(boolean userIsOnline) { this.userIsOnline = userIsOnline; }
}
