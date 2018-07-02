package fr.inria.diverse.mobileprivacyprofiler.services.PacketSnifferService;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import fr.inria.diverse.mobileprivacyprofiler.services.PacketSnifferService.socket.IReceivePacket;

public class PacketManager {

    public static final int PACKET = 0;

    @NonNull
    //private static final List<Packet> list = new ArrayList<>();

    public static synchronized void add(@NonNull final Packet packet, Context context) {
            packet.addPacketToDB(context);
    }

  /*  @NonNull
    public List<Packet> getList() {
        return list;
    }*/

}
