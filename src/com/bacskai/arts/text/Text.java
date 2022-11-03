package com.bacskai.arts.text;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.util.Random;

import javax.imageio.ImageIO;

import com.bacskai.arts.Utils;

public class Text {

    public static void main(String[] args) throws Exception {
        int blackRGB = Color.white.getRGB();

		int w = 1920;
        BufferedImage templ = ImageIO.read(new File("potty.png"));
        float scale = (float) w / templ.getWidth();
        int h = (int) (templ.getHeight() * scale);
		
        int r = (int) (3 * scale) | 1, r2 = r / 2;
        
        Image scaled = templ.getScaledInstance(w, h, BufferedImage.SCALE_DEFAULT);
        templ = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics tg = templ.getGraphics();
        tg.drawImage(scaled, 0, 0, null);
        tg.dispose();

        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();
        
        g.setColor(Color.black);
        g.fillRect(0, 0, w, h);

		Utils.setRenderingHints(g);

        long seed = System.currentTimeMillis();
        Random rand = new Random(seed);

        for (int i = 0; i < 5000; i++) {
            float x = rand.nextFloat(), y = rand.nextFloat();
            
            int xi = (int) (x * w), yi = (int) (y * h);

            int color = templ.getRGB(xi, yi);
            if (color == blackRGB) {
                g.setColor(Color.getHSBColor(x, y, 1));
                g.fillOval(xi - r2, yi - r2, r, r);
            }
        }

        ImageIO.write(img, "PNG", new File("text.png"));
        System.err.print("Done with seed ");
        System.err.println(seed);
    }
}
