package com.xfzcode.utils.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * @Author: XMLee
 * @Date: 2023/4/12 10:29
 * @Description: 生成验证码的data image 格式图片
 */
public class VerifyCodeUtil {
    private final static Logger logger = LoggerFactory.getLogger(VerifyCodeUtil.class);

    // 生成随机数的源
    private final static String VERIFY_CODE_SRC = "0123456789ZXCVBNMASDFGHJKLQWERTYUIOPzxcvbnmlkjhgfdsaqwertyuiop";

    /**
     * 生成验证码
     *
     * @param width          图片宽度
     * @param height         图片长度
     * @param verifyCodeSize 验证码个数
     * @param outputStream   输出流
     * @return 验证码
     */
    public static String generateVerifyCode(int width, int height, int verifyCodeSize, OutputStream outputStream) {
        // 生成 verifyCodeSize 个随机数
        String verifyCode = VerifyCodeUtil.getRandomVerifyCode(verifyCodeSize, VERIFY_CODE_SRC);
        logger.debug("【验证码】生成 {} 位验证码：{}", verifyCodeSize, verifyCode);

        // 创建指定长宽的图片
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 设置边框颜色
        Color randomColor = VerifyCodeUtil.getRandomColor(100, 150);
        graphics2D.setColor(Color.GRAY);
        graphics2D.fillRect(0, 0, width, height);

        // 设置背景颜色
        randomColor = VerifyCodeUtil.getRandomColor(200, 250);
        graphics2D.setColor(randomColor);
        graphics2D.fillRect(0, 2, width, height - 3);

        // 设置字体
        graphics2D.setColor(VerifyCodeUtil.getRandomColor(100, 150));
        graphics2D.setFont(new Font("Consolas", Font.ITALIC, (int) (height * 0.9)));

        //绘制干扰线
        VerifyCodeUtil.drawInterferenceLine(graphics2D, width, height);

        // 添加噪点
        VerifyCodeUtil.drawNoise(bufferedImage, width, height);

        // 扭曲图片
        VerifyCodeUtil.twist(graphics2D, width, height, randomColor);

        // 写入验证码
        VerifyCodeUtil.writeVerifyCode(graphics2D, verifyCode, width, height);

        try {
            graphics2D.dispose();
            ImageIO.write(bufferedImage, "jpg", outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("【验证码】生成异常：{}", e);
        }

        return verifyCode;
    }

    /**
     * 写入验证码
     *
     * @param graphics2D
     * @param verifyCode
     * @param width
     * @param height
     */
    private static void writeVerifyCode(Graphics2D graphics2D, String verifyCode, int width, int height) {
        Random rand = new Random();
        char[] chars = verifyCode.toCharArray();
        for (int i = 0; i < verifyCode.length(); i++) {
            // 为每个验证码指定不同的颜色
            graphics2D.setColor(VerifyCodeUtil.getRandomColor(80, 150));

            // 旋转验证码
            AffineTransform affineTransform = new AffineTransform();
            double angle = Math.PI / 8 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1);
            affineTransform.setToRotation(angle, (new Integer(width).doubleValue() / verifyCode.length()) * i, height * 0.1 / 2);
            graphics2D.setTransform(affineTransform);

            // 绘制字符数组
            graphics2D.drawChars(chars, i, 1, (int) (width / (double) (verifyCode.length() + 2) * (i + 1)), (int) (height * 0.75));
        }
    }

    /**
     * 绘制噪点
     *
     * @param image
     * @param width
     * @param height
     */
    private static void drawNoise(BufferedImage image, int width, int height) {
        // 噪声率
        float yawpRate = 0.1f;
        int area = (int) (yawpRate * width * height);
        for (int i = 0; i < area; i++) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);
            int rgb = VerifyCodeUtil.getRandomIntColor();
            image.setRGB(x, y, rgb);
        }
    }

    /**
     * 绘制干扰线
     *
     * @param graphics2D
     * @param width
     * @param height
     */
    private static void drawInterferenceLine(Graphics2D graphics2D, int width, int height) {
        // 设置线条的颜色
        graphics2D.setColor(VerifyCodeUtil.getRandomColor(160, 200));
        // 20条干扰线
        for (int i = 0; i < 20; i++) {
            int x1 = (int) (Math.random() * width * 0.7);
            int y1 = (int) (Math.random() * height * 0.7);
            graphics2D.drawLine(x1, y1, (int) (x1 + x1 * 0.3), (int) (y1 + y1 * 0.3));
        }
    }

    /**
     * 扭曲图片
     *
     * @param graphics
     * @param width
     * @param height
     * @param color
     */
    private static void twist(Graphics graphics, int width, int height, Color color) {
        int period = (int) ((Math.random() * height * 0.3) + height * 0.2);
        for (int i = 0; i < width; i++) {
            double sin = (double) (period >> 1) * Math.sin(i / (double) period + Math.PI);
            graphics.copyArea(i, 0, 1, height, 0, (int) sin);
            graphics.setColor(color);
            graphics.drawLine(i, (int) sin, i, 0);
            graphics.drawLine(i, (int) sin + height, i, height);
        }
    }

    /**
     * 产生随机颜色，但指定范围
     *
     * @param fc
     * @param bc
     * @return
     */
    private static Color getRandomColor(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        } else if (bc > 255) {
            bc = 255;
        }

        int r = fc + (int) (Math.random() * (bc - fc));
        int g = fc + (int) (Math.random() * (bc - fc));
        int b = fc + (int) (Math.random() * (bc - fc));

        return new Color(r, g, b);
    }

    /**
     * 获取 int 类型颜色
     *
     * @return
     */
    private static int getRandomIntColor() {
        int[] rgb = new int[3];
        for (int i = 0; i < 3; i++) {
            rgb[i] = (int) (Math.random() * 255);
        }

        int color = 0;
        for (int c : rgb) {
            color = color << 8;
            color = color | c;
        }

        return color;
    }

    /**
     * 生成随机数字符串，指定大小和源
     *
     * @param verifyCodeSize
     * @param verifyCodeSrc
     * @return
     */
    private static String getRandomVerifyCode(int verifyCodeSize, String verifyCodeSrc) {
        StringBuffer stringBuffer = new StringBuffer(verifyCodeSize);
        for (int i = 0; i < verifyCodeSize; i++) {
            stringBuffer.append(verifyCodeSrc.charAt((int) (Math.random() * verifyCodeSrc.length())));
        }
        return stringBuffer.toString();
    }

    /**
     * 生成验证码
     *
     * @param verifyCodeSize 验证码个数
     * @return 验证码
     */
    public static String generateVerifyCode(int verifyCodeSize) {
        // 生成 verifyCodeSize 个随机数
        String verifyCode = VerifyCodeUtil.getRandomVerifyCode(verifyCodeSize, VERIFY_CODE_SRC);
        logger.debug("【验证码】生成 {} 位验证码：{}", verifyCodeSize, verifyCode);
        return verifyCode;
    }

    public static void main(String[] args) {
        System.out.println(VerifyCodeUtil.generateVerifyCode(200,50,6,new ByteArrayOutputStream()));
    }
}
