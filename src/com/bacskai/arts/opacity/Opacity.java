package com.bacskai.arts.opacity;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Color;
import java.io.File;

import javax.imageio.ImageIO;

import com.bacskai.arts.Utils;

public class Opacity {

    private static float func(float w, float h, float x) {
        float p = x / w * 100;
        p = (float) Math.sqrt(p);
        return p * 100;
    }

    public static void main(String[] args) throws Exception {
        int w = 1920, h = 1080;
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        
		Graphics g = img.getGraphics();

        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, w, h);

        Utils.setRenderingHints(g);

        for (int x = 0; x < w + 200; x += 20) {
            float fx = (float) x;
            float fy = h - func(w, h, x);
            float xw = fx / w;

            int y = (int) fy;
            
            float H = xw * .1f, S = fy / h * .25f + .75f, B = 1;
            float[] cc = Color.getHSBColor(H, S, B).getRGBColorComponents(null);

            float aaf = xw * 4f / 3f;
            if (aaf > 1)
                aaf = 1;

            int rr = (int) (cc[0] * 255);
            int gg = (int) (cc[1] * 255);
            int bb = (int) (cc[2] * 255);
            int aa = (int) (aaf * 255);

            Color c = new Color(rr, gg, bb, aa);

            int d = (int) (w / fx * 100);

            g.setColor(c);
            g.fillOval(x - d / 2, y - d / 2 - 30, d, d);
        }

        g.dispose();

        ImageIO.write(img, "PNG", new File("opacity.png"));
    }
}
