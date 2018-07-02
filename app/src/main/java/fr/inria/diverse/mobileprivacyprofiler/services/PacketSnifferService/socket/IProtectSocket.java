package fr.inria.diverse.mobileprivacyprofiler.services.PacketSnifferService.socket;

import java.net.DatagramSocket;
import java.net.Socket;

public interface IProtectSocket {
	void protectSocket(Socket socket);
	void protectSocket(int socket);
	void protectSocket(DatagramSocket socket);
}
