package image_encryption;

import java.awt.Color;
import java.io.IOException;
import java.util.Scanner;

import edu.princeton.cs.introcs.StdDraw;

public class Driver {

	public static void main(String[] args) {
		
		try {
			Scanner in = new Scanner(System.in);
			System.out.println("Do you want to extract(1) or embed(2)?");
			int num = in.nextInt();
			
			if(num == 1) {
				System.out.println("Enter the name of a file: ");
				String fileIn = in.next();
				
				Color[][] originalImage = BMPIO.readBMPFile(fileIn);
				Color[][] secretImage = Steganography.extractSecretImage(fileIn);
				
				StdDraw.setCanvasSize(originalImage[0].length * 2, originalImage.length);
				StdDraw.setXscale(0, originalImage[0].length * 2);
				StdDraw.setYscale(0, originalImage.length);
				
				StdDraw.enableDoubleBuffering();
				
				for(int i = 0; i < originalImage.length; i++) {
					for(int j = 0; j < originalImage[i].length; j++) {
						StdDraw.setPenColor(originalImage[i][j]);
						StdDraw.filledSquare(j, i, 1);
						StdDraw.setPenColor(secretImage[i][j]);
						StdDraw.filledSquare(j + originalImage[0].length, i, 1);
					}
				}
				StdDraw.show();
			}
			
			if(num == 2) {
				System.out.println("Enter the original file: ");
				String file1 = in.next();
				System.out.println("Enter the file you would like to embed: ");
				String file2 = in.next();
				
				Color[][] pColor = BMPIO.readBMPFile(file1);
				Color[][] sColor = Steganography.embedSecretImage(file1, file2);
				
				StdDraw.setCanvasSize(pColor[0].length * 2, pColor.length);
				StdDraw.setXscale(0, pColor[0].length * 2);
				StdDraw.setYscale(0, pColor.length);
				
				StdDraw.enableDoubleBuffering();
				
				for(int i = 0; i < pColor.length; i++) {
					for(int j = 0; j < pColor[i].length; j++) {
						StdDraw.setPenColor(pColor[i][j]);
						StdDraw.filledSquare(j, i, 1);
						StdDraw.setPenColor(sColor[i][j]);
						StdDraw.filledSquare(j + pColor[0].length, i, 1);
					}
				}
				StdDraw.show();
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		

	}

}
