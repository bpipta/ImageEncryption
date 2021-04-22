package image_encryption;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Steganography {
	
	public static Color[][] extractSecretImage(String s) throws IOException {
		Color[][] secretImage = BMPIO.readBMPFile(s);
		for(int i = 0; i < secretImage.length; i++) {
			for(int j = 0; j < secretImage[i].length; j++) {
				int r = secretImage[i][j].getRed()%16;
				int g = secretImage[i][j].getGreen()%16;
				int b = secretImage[i][j].getBlue()%16;
				
				r *= 16;
				g *= 16;
				b *= 16;
				
				secretImage[i][j] = new Color(r, g, b);
				
			}
		}
		return secretImage;
	}
	
	public static Color[][] embedSecretImage(String p, String s) throws IOException{
		Color[][] hiddenImage = BMPIO.readBMPFile(s);
		Color[][] publicImage = BMPIO.readBMPFile(p);
		
		RandomAccessFile raf = new RandomAccessFile(new File(s), "rw");
		
		if(hiddenImage.length < publicImage.length) {
			raf.seek(54);
			for(int i = 0; i < hiddenImage.length; i++) {
				for(int j = 0; j < hiddenImage[i].length; j++) {
					int r = hiddenImage[i][j].getRed();
					int g = hiddenImage[i][j].getGreen();
					int b = hiddenImage[i][j].getBlue();
					
					r = r - (r % 16);
					g = g - (g % 16);
					b = b - (b % 16);
					
					if(i < hiddenImage.length && j < hiddenImage[i].length) {
						int sR = hiddenImage[i][j].getRed()/16;
						int sG = hiddenImage[i][j].getGreen()/16;
						int sB = hiddenImage[i][j].getBlue()/16;
						
						r += sR;
						g += sG;
						b += sB;
					
					}
					hiddenImage[i][j] = new Color(r, g, b);
					raf.writeByte(b);
					raf.writeByte(g);
					raf.writeByte(r);
				}
				raf.close();
			}
			return publicImage;
			
		}
		
		else {
			return null;
		}

	}

}
