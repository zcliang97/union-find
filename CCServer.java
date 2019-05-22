// NOTE: changed shuf to gshuf due to using brew, change back when using ubuntu
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

class CCServer {
	// max size of block for each process to run
	public static final int BLOCK_SIZE = 10;

	public Byte[] toObjects(byte[] bytesPrim) {
    	Byte[] bytes = new Byte[bytesPrim.length];
    	Arrays.setAll(bytes, n -> bytesPrim[n]);
    	return bytes;
	}

	public static void main(String args[]) throws Exception {
		if (args.length != 1) {
			System.out.println("usage: java CCServer port");
			System.exit(-1);
		}
		int port = Integer.parseInt(args[0]);

		/*
			YOUR CODE GOES HERE
			- accept connection from server socket
			- read requests from connection repeatedly
			- for each request, compute an output and send a response
			- each message has a 4-byte header followed by a payload
			- the header is the length of the payload
				(signed, two's complement, big-endian)
			- the payload is a string
				(UTF-8, big-endian)
		*/

		ServerSocket ssock = new ServerSocket(port);
		System.out.println("listening on port " + port);
		while(true) {
			try {
				// Read input stream bytes completely then input into buffered reader
				Socket socket = ssock.accept();
				DataInputStream dis = new DataInputStream(socket.getInputStream());
				byte[] bytes = new byte[dis.readInt()];
				dis.readFully(bytes);
				ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

				// get components
				UnionFind uf = new UnionFind();

				int p, q, b;
				p = q = b = 0;
				while (bais.available() > 0){
					b = bais.read();
					while (b != 32){
						p = 10*p + (b-'0');
						b = bais.read();
					}
					b = bais.read();
					while (b != 10){
						q = 10*q + (b-'0');
						b = bais.read();
					}
					uf.addEdge(p, q);
					p = q = 0;
				}

				String components = uf.outputComponents();
				
				// output components
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				byte[] output = components.getBytes("UTF-8");
				dos.writeInt(output.length);
				dos.write(output);
				dos.flush();

				socket.close();
			}
			
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
