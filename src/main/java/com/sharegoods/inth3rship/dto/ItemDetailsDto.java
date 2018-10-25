package com.sharegoods.inth3rship.dto;

import java.util.List;

public class ItemDetailsDto {
    private ItemDto itemDto;
    private List<ImageDto> imageDtoList;
    private List<CommentDto> commentDtoList;

    public ItemDetailsDto(ItemDto itemDto, List<ImageDto> imageDtoList, List<CommentDto> commentDtoList) {
        this.itemDto = itemDto;
        this.imageDtoList = imageDtoList;
        this.commentDtoList = commentDtoList;
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

    public List<CommentDto> getCommentDtoList() {
        return commentDtoList;
    }

    public void setCommentDtoList(List<CommentDto> commentDtoList) {
        this.commentDtoList = commentDtoList;
    }
}
