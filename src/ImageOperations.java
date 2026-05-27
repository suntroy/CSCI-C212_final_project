import java.awt.*;
import java.awt.image.BufferedImage;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

class ImageOperations {

    /**
     * removes the red channel from inputted img
     *
     * @param img image to remove red from
     * @return new instance of img with the red channel at zero
     */
    static BufferedImage zeroRed(BufferedImage img) {
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < newImg.getHeight(); y++) {
            for (int x = 0; x < newImg.getWidth(); x++) {
                Color pxl = new Color(img.getRGB(x, y));
                Color newColor = new Color(0, pxl.getGreen(), pxl.getBlue());
                newImg.setRGB(x, y, newColor.getRGB());
            }
        }
        return newImg;
    }

    /**
     * converts the image to grayscale via average method (takes average of all color values)
     *
     * @param img image to convert to grey
     * @return new instance of img in grayscale
     */
    static BufferedImage grayscale(BufferedImage img) {
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < newImg.getHeight(); y++) {
            for (int x = 0; x < newImg.getWidth(); x++) {
                Color pixel = new Color(img.getRGB(x, y));
                int grayValue = (pixel.getRed() + pixel.getGreen() + pixel.getBlue()) / 3;
                Color newColor = new Color(grayValue, grayValue, grayValue);
                newImg.setRGB(x, y, newColor.getRGB());
            }
        }
        return newImg;
    }

    /**
     * inverts the colors of the pixel data
     *
     * @param img image to invert
     * @return new instance of img with the colors inverted
     */
    static BufferedImage invert(BufferedImage img) {
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < newImg.getHeight(); y++) {
            for (int x = 0; x < newImg.getWidth(); x++) {
                Color pixel = new Color(img.getRGB(x, y));
                Color newColor = new Color(255 - pixel.getRed(), 255 - pixel.getGreen(), 255 - pixel.getBlue());
                newImg.setRGB(x, y, newColor.getRGB());
            }
        }
        return newImg;
    }

    /**
     * mirrors the given image in the specified direction
     *
     * @param img image as a BufferedImage
     * @param dir states whether to mirror the image horizontally or vertically
     * @return new instance of img mirrored in the given direction
     */
    static BufferedImage mirror(BufferedImage img, MirrorMenuItem.MirrorDirection dir) {
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        if (dir == MirrorMenuItem.MirrorDirection.VERTICAL) {
            if (img.getWidth() % 2 == 0) {
                int leftSide = (newImg.getWidth() / 2);
                for (int y = 0; y < newImg.getHeight(); y++) {
                    for (int x = 0; x < leftSide; x++) {
                        Color pxl = new Color(img.getRGB(x, y));
                        newImg.setRGB(x, y, pxl.getRGB());
                    }
                }
                for (int y = 0; y < newImg.getHeight(); y++) {
                    for (int x = newImg.getWidth() - 1; x >= leftSide; x--) {
                        Color pxl = new Color(img.getRGB(Math.abs(x - (img.getWidth() - 1)), y));
                        newImg.setRGB(x, y, pxl.getRGB());
                    }
                }
                return newImg;
            } else {
                int leftSideAndMiddle = (newImg.getWidth() / 2);
                for (int y = 0; y < newImg.getHeight(); y++) {
                    for (int x = 0; x <= leftSideAndMiddle; x++) {
                        Color pxl = new Color(img.getRGB(x, y));
                        newImg.setRGB(x, y, pxl.getRGB());
                    }
                }
                for (int y = 0; y < newImg.getHeight(); y++) {
                    for (int x = newImg.getWidth() - 1; x > leftSideAndMiddle; x--) {
                        Color pxl = new Color(img.getRGB(Math.abs(x - (img.getWidth() - 1)), y));
                        newImg.setRGB(x, y, pxl.getRGB());
                    }
                }
                return newImg;
            }
        } else {
            if (img.getHeight() % 2 == 0) {
                int topSide = (newImg.getHeight() / 2);
                for (int y = 0; y < topSide; y++) {
                    for (int x = 0; x < newImg.getWidth(); x++) {
                        Color pxl = new Color(img.getRGB(x, y));
                        newImg.setRGB(x, y, pxl.getRGB());
                    }
                }
                for (int y = newImg.getHeight() - 1; y >= topSide; y--) {
                    for (int x = 0; x < newImg.getWidth(); x++) {
                        Color pxl = new Color(img.getRGB(x, Math.abs(y - (img.getHeight() - 1))));
                        newImg.setRGB(x, y, pxl.getRGB());
                    }
                }
                return newImg;
            } else {
                int topSideAndMiddle = (newImg.getHeight() / 2);
                for (int y = 0; y <= topSideAndMiddle; y++) {
                    for (int x = 0; x < newImg.getWidth(); x++) {
                        Color pxl = new Color(img.getRGB(x, y));
                        newImg.setRGB(x, y, pxl.getRGB());
                    }
                }
                for (int y = newImg.getHeight() - 1; y > topSideAndMiddle; y--) {
                    for (int x = 0; x < newImg.getWidth(); x++) {
                        Color pxl = new Color(img.getRGB(x, Math.abs(y - (img.getHeight() - 1))));
                        newImg.setRGB(x, y, pxl.getRGB());
                    }
                }
                return newImg;
            }

        }
    }

    /**
     * rotates an image 90 degrees in the given direction
     *
     * @param img given image to rotate as a BufferedImage
     * @param dir states whether to rotate the image clockwise or counterclockwise
     * @return new instance of img rotated 90 degrees in the given direction
     */
    static BufferedImage rotate(BufferedImage img, RotateMenuItem.RotateDirection dir) {
        BufferedImage newImg = new BufferedImage(img.getHeight(), img.getWidth(), BufferedImage.TYPE_INT_RGB);
        if (dir == RotateMenuItem.RotateDirection.CLOCKWISE) {
            for (int y = 0; y < newImg.getHeight(); y++) {
                for (int x = 0; x < newImg.getWidth(); x++) {
                    Color pxl = new Color(img.getRGB(x, y));
                    newImg.setRGB(Math.abs(y - (newImg.getHeight() - 1)), x, pxl.getRGB());
                }
            }
        } else {
            for (int y = 0; y < newImg.getHeight(); y++) {
                for (int x = 0; x < newImg.getWidth(); x++) {
                    Color pxl = new Color(img.getRGB(x, y));
                    newImg.setRGB(y, Math.abs(x - (newImg.getWidth() - 1)), pxl.getRGB());
                }
            }
        }
        return newImg;
    }

    /**
     * repeats the given image in the given direction
     * n amount of times
     *
     * @param img image to repeat
     * @param n   number of times to repeat
     * @param dir states whether to repeat the image side by side or top to bottom
     * @return new instance of img repeated n amount of times in the direction dir
     */
    static BufferedImage repeat(BufferedImage img, int n, RepeatMenuItem.RepeatDirection dir) {
        BufferedImage newImg;
        if (dir == RepeatMenuItem.RepeatDirection.HORIZONTAL) {
            newImg = new BufferedImage(img.getWidth() * n, img.getHeight(), BufferedImage.TYPE_INT_RGB);
            for (int imgN = 0; imgN < n; imgN++) {
                int toAdd = img.getWidth();
                for (int y = 0; y < img.getHeight(); y++) {
                    for (int x = 0; x < img.getWidth(); x++) {
                        Color pixel = new Color(img.getRGB(x, y));
                        newImg.setRGB(x + (toAdd * imgN), y, pixel.getRGB());
                    }
                }
            }
        } else {
            newImg = new BufferedImage(img.getWidth(), img.getHeight() * n, BufferedImage.TYPE_INT_RGB);
            for (int imgN = 0; imgN < n; imgN++) {
                int toAdd = img.getHeight();
                for (int y = 0; y < img.getHeight(); y++) {
                    for (int x = 0; x < img.getWidth(); x++) {
                        Color pixel = new Color(img.getRGB(x, y));
                        newImg.setRGB(x, y + (toAdd * imgN), pixel.getRGB());
                    }
                }
            }
        }
        return newImg;
    }

    /**
     * Zooms in on the image. The zoom factor increases in multiplicatives of 10% and
     * decreases in multiplicatives of 10%.
     *
     * @param img        the original image to zoom in on. The image cannot be already zoomed in
     *                   or out because then the image will be distorted.
     * @param zoomFactor The factor to zoom in by.
     * @return the zoomed in image.
     */
    static BufferedImage zoom(BufferedImage img, double zoomFactor) {
        int newImageWidth = (int) (img.getWidth() * zoomFactor);
        int newImageHeight = (int) (img.getHeight() * zoomFactor);
        BufferedImage newImg = new BufferedImage(newImageWidth, newImageHeight, TYPE_INT_RGB);
        Graphics2D g2d = newImg.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(img, 0, 0, newImageWidth, newImageHeight, null);
        g2d.dispose();
        return newImg;
    }
}
