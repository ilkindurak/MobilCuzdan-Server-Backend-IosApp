package com.mobilcuzdan.spring.services;

import com.google.common.base.Preconditions;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.UUID;

/**
 * Created by fikricanca on 20.12.2016.
 */

@SuppressWarnings("unchecked")
@Component
public class UploadService {

    private String imagePath = "/var/www/html/uploaded";
    private String imageDomain = "http://128.199.50.73/uploaded/";
    private static final Logger log = LogManager.getLogger();

    public String persistFileWithName(String name, MultipartFile file) {
        try {
            int extensionStartIndex = file.getOriginalFilename().lastIndexOf(".");
            if (extensionStartIndex <= 0) {
                return null;
            }
            String extension = file.getOriginalFilename().substring(extensionStartIndex);
            Preconditions.checkNotNull(extension);
            String filename = name + extension;
            File imageFile = new File(imagePath, filename);

            OutputStream imageFileStream = Files.asByteSink(imageFile).openStream();
            ByteStreams.copy(file.getInputStream(), imageFileStream);
            imageFileStream.close();

            String imageUrl = UriComponentsBuilder.fromHttpUrl(imageDomain).path(filename).build().toUriString();
            return imageUrl;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public String persistFile(MultipartFile file) {
        return persistFileWithName(UUID.randomUUID().toString(), file);
    }

    public String persistFile(BufferedImage image) {
        try {
            String fileName = UUID.randomUUID().toString() + ".jpg";
            File imageFile = new File(imagePath, fileName);
            ImageIO.write(image, "jpg", imageFile);

            String imageUrl = UriComponentsBuilder.fromHttpUrl(imageDomain).path(fileName).build().toUriString();
            return imageUrl;
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public BufferedImage createQRImage(String qrCodeText, int size) throws WriterException, IOException {
        Hashtable hintMap = new Hashtable();
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix byteMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
        int matrixWidth = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        graphics.setColor(Color.BLACK);

        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
        return image;
    }


}
