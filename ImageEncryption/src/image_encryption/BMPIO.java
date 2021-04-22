package image_encryption;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import edu.princeton.cs.introcs.StdDraw;

public class BMPIO {
	
	public static Color[][] readBMPFile(String file) {
		
		Color[][] temppic = null;
		
		try {
			RandomAccessFile raf = new RandomAccessFile(new File(file), "r");
			byte temp1 = raf.readByte();
			byte temp2 = raf.readByte();
			
			if(temp1 != 66 && temp2 != 77) {
				raf.close();
			}
			
			raf.seek(10);
			
			int temp3 = Integer.reverseBytes(raf.readInt());
			
			if(temp3 != 54) {
				raf.close();
			}
			
			int temp4 = Integer.reverseBytes(raf.readInt());
			
			if(temp4 != 40) {
				raf.close();
			}
			
			int width = Integer.reverseBytes(raf.readInt());
			
			if(width % 4 != 0) {
				raf.close();
			}
			
			int height = Integer.reverseBytes(raf.readInt());
			
			raf.seek(28);
			
			short pixel = Short.reverseBytes(raf.readShort());
			
			if(pixel != 24) {
				raf.close();
			}
			
			StdDraw.setCanvasSize(width, height);
			StdDraw.setXscale(0, width);
			StdDraw.setYscale(0, height);
			
			raf.seek(54);
			
			Color[][] pic = new Color[height][width];
			
			for(int i = 0; i < pic.length; i++) {
				for(int j = 0; j < pic[i].length; j++) {
					int b = raf.readUnsignedByte();
					int g = raf.readUnsignedByte();
					int r = raf.readUnsignedByte();
					pic[i][j] = new Color(r, g, b);
					temppic = pic;
				}
			}
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return temppic;
	}

}
