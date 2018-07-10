package fr.inria.diverse.mobileprivacyprofiler.services.PacketSnifferService.tls;

import java.nio.ByteBuffer;

public class ServerNameExtension extends Extension {

    private ServerNameIndicationExtension serverNameIndicationExtension;

    public ServerNameExtension(ByteBuffer stream, short type){
        super(stream,type);
        serverNameIndicationExtension = new ServerNameIndicationExtension(ByteBuffer.wrap(dataExtension));
    }

    public ServerNameIndicationExtension getServerNameIndicationExtension() {
        return serverNameIndicationExtension;
    }

    public void setServerNameIndicationExtension(ServerNameIndicationExtension serverNameIndicationExtension) {
        this.serverNameIndicationExtension = serverNameIndicationExtension;
    }


}
