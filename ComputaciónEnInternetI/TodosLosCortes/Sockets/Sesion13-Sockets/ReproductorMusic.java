//package cliente;

import java.io.*;
import java.net.*;

import javax.sound.sampled.*;

public class ReproductorMusic {

	AudioInputStream in; //datos de entrada
	SourceDataLine out;  //salida a la tarjeta de audio
	private String route = "./songs/Song1_16k.wav";

	public ReproductorMusic() {
		initiateAudio();
	}

	public void initiateAudio() {
		try {
			File file = new File(route);
			in = AudioSystem.getAudioInputStream(file);

			out = AudioSystem.getSourceDataLine(in.getFormat());
			out.open(in.getFormat());
			out.start();
			playAudio();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void playAudio() {
		byte[] buffer = new byte[1024];
		try {
			int count;
			while ((count = in.read(buffer, 0, buffer.length)) != -1) {
				if (count > 0) {
					out.write(buffer, 0, count);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public static void main(String[] args) {
		new ReproductorMusic().initiateAudio();
	}
}
