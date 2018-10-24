package com.sharegoods.inth3rship.services;

import com.sharegoods.inth3rship.models.Image;
import com.sharegoods.inth3rship.models.Item;
import com.sharegoods.inth3rship.repositories.ImageRepository;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ItemService itemService;

    public void setImageRepository(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<Image> getImagesByItemId(Long itemId) {
        Item item = itemService.getItemById(itemId);
        return imageRepository.findByItemAndThumbnail(item, false);
    }

    public List<Image> createImages(Item item, List<MultipartFile> imageFiles) {
        List<Image> images = new ArrayList();
        byte[] arrayImg = null;
        if (imageFiles != null && !imageFiles.isEmpty()) {
            for (MultipartFile imageFile : imageFiles) {
                arrayImg = new byte[(int) imageFile.getSize()];
                try {
                    imageFile.getInputStream().read(arrayImg);
                    images.add(new Image(item, imageFile.getOriginalFilename(), arrayImg, false));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            createThumbnail(images.get(0));
        } else {
            // if item has no images, save noimage available
            ClassPathResource noImage = new ClassPathResource("images/noimage.png");
            try {
                arrayImg = new byte[(int) noImage.contentLength()];
                noImage.getInputStream().read(arrayImg);
                createThumbnail(new Image(item, "noimage.png", arrayImg, false));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return imageRepository.saveAll(images);
    }

    public void deleteImagesByItem(Item item) {
        imageRepository.deleteByItem(item);
    }

    public void updateItemImages(Item item, List<MultipartFile> imageFiles) {
        deleteImagesByItem(item);
        createImages(item, imageFiles);
    }

    public void createThumbnail(Image image) {
        ByteArrayInputStream in = new ByteArrayInputStream(image.getImageData());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            BufferedImage img = ImageIO.read(in);
            BufferedImage thumbImage = Scalr.resize(img, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC,
                    300, Scalr.OP_ANTIALIAS);
            String type = image.getName().substring(image.getName().lastIndexOf(".") + 1);
            ImageIO.write(thumbImage, type, out);
            byte[] thumbImageData = out.toByteArray();
            imageRepository.save(new Image(image.getItem(), "thumb" + image.getName(), thumbImageData, true));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Image getThumbnail(Item item) {
        List<Image> itemImages = imageRepository.findByItemAndThumbnail(item, true);
        if (!itemImages.isEmpty()) {
            return itemImages.get(0);
        }
        return null;
    }

}
